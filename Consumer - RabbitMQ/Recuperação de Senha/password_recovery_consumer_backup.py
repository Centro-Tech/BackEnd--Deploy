import os
import json
import re
import time
import smtplib
import argparse
import traceback
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

try:
    from dotenv import load_dotenv  # type: ignore
    load_dotenv()
except Exception:
    pass

EMAIL_REGEX = re.compile(r"^[^@\s]+@[^@\s]+\.[^@\s]+$")


def env_bool(name: str, default: str = "0") -> bool:
    return os.getenv(name, default).strip().lower() in ("1", "true", "yes", "on")


class PasswordRecoveryConsumer:
    # Esta classe pega mensagens da fila e envia email de recuperacao de senha
    # A mensagem tem que ter email, nome, token e resetUrl

    def __init__(self):
        # Configurações do RabbitMQ
        self.rabbitmq_host = os.getenv('RABBITMQ_HOST', 'localhost')
        self.rabbitmq_port = int(os.getenv('RABBITMQ_PORT', '5672'))
        self.rabbitmq_user = os.getenv('RABBITMQ_USER', 'myuser')
        self.rabbitmq_password = os.getenv('RABBITMQ_PASSWORD', 'secret')
        self.rabbitmq_vhost = os.getenv('RABBITMQ_VHOST', '/')
        self.queue_name = os.getenv('PASSWORD_RECOVERY_QUEUE', 'password.recovery.queue')
        self.prefetch_count = int(os.getenv('RABBITMQ_PREFETCH', '5'))
        self.reconnect_delay = int(os.getenv('RABBITMQ_RECONNECT_DELAY', '5'))

        # Configurações do email
        self.smtp_server = os.getenv('SMTP_SERVER', 'smtp.maileroo.com')
        self.smtp_port = int(os.getenv('SMTP_PORT', '587'))
        self.use_starttls = env_bool('SMTP_USE_TLS', '1')
        self.use_ssl = env_bool('SMTP_USE_SSL', '0')
        self.email_address = os.getenv('EMAIL_FROM', 'mima@0935a8e1530952e9.maileroo.org')
        self.sender_name = os.getenv('SENDER_NAME', 'Mima Store')
        self.smtp_user = os.getenv('SMTP_USER', 'mima@0935a8e1530952e9.maileroo.org')
        self.smtp_pass = os.getenv('SMTP_PASS', '1cff35807ba2cc8b1ac2103e')
        self.smtp_auth_required = env_bool('SMTP_AUTH_REQUIRED', '1')
        self.smtp_debug = env_bool('SMTP_DEBUG', '0')
        self.smtp_retry_attempts = int(os.getenv('SMTP_RETRY_ATTEMPTS', '3'))
        self.smtp_retry_delay = float(os.getenv('SMTP_RETRY_DELAY', '2'))

        # URLs do sistema
        self.frontend_reset_url = os.getenv('FRONTEND_RESET_URL', 'https://mimastore.com/reset-password')
        self.backend_base_url = os.getenv('BACKEND_BASE_URL', 'http://localhost:8080')
        self.force_backend_auth_link = env_bool('FORCE_BACKEND_AUTH_LINK', '0')

        # Opções do email
        self.include_plain = True
        self.include_html = True

        # Se SSL e TLS estao ativados, usa só SSL
        if self.use_ssl and self.use_starttls:
            print("[SMTP][WARN] SSL e TLS ativados juntos, usando só SSL")
            self.use_starttls = False

    # Monta os links do email
    def build_links(self, token: str, provided_reset_url: str | None):
        if provided_reset_url and provided_reset_url.strip():
            frontend_link = provided_reset_url.strip()
        else:
            frontend_link = f"{self.frontend_reset_url}?token={token}"

        backend_auth_link = f"{self.backend_base_url}/usuarios/recuperar-senha/autenticar?token={token}"
        backend_reset_endpoint = f"{self.backend_base_url}/usuarios/redefinir-senha"

        primary_link = backend_auth_link if self.force_backend_auth_link else frontend_link

        return {
            'primary_link': primary_link,
            'frontend_link': frontend_link,
            'backend_auth_link': backend_auth_link,
            'backend_reset_endpoint': backend_reset_endpoint
        }

    # Conecta no servidor de email e envia
    def _smtp_connect_and_send(self, email: str, msg: MIMEMultipart) -> bool:
        last_error = None
        for attempt in range(1, self.smtp_retry_attempts + 1):
            try:
                print(f"[SMTP][TENTATIVA {attempt}/{self.smtp_retry_attempts}] Conectando {self.smtp_server}:{self.smtp_port} SSL={self.use_ssl} STARTTLS={self.use_starttls}")
                if self.use_ssl:
                    server = smtplib.SMTP_SSL(self.smtp_server, self.smtp_port, timeout=30)
                else:
                    server = smtplib.SMTP(self.smtp_server, self.smtp_port, timeout=30)
                if self.smtp_debug:
                    server.set_debuglevel(1)
                server.ehlo()

                if self.use_starttls and not self.use_ssl:
                    try:
                        server.starttls()
                        server.ehlo()
                    except Exception as e_tls:
                        raise RuntimeError(f"Falha no STARTTLS: {e_tls}")

                if self.smtp_auth_required and self.smtp_user and self.smtp_pass:
                    try:
                        server.login(self.smtp_user, self.smtp_pass)
                    except smtplib.SMTPServerDisconnected as disc:
                        raise RuntimeError(f"Servidor desconectou durante login: {disc}")
                    except smtplib.SMTPAuthenticationError as auth_err:
                        raise RuntimeError(f"Falha de autenticação SMTP: {auth_err}")
                else:
                    if not (self.smtp_user and self.smtp_pass):
                        print("[SMTP][INFO] Sem credenciais definidas ou auth desabilitado; enviando sem LOGIN.")

                server.sendmail(self.email_address, [email], msg.as_string())
                server.quit()
                print(f"[EMAIL][OK] Enviado para {email} (tentativa {attempt})")
                return True
            except Exception as e:
                last_error = e
                print(f"[SMTP][ERRO] Tentativa {attempt} falhou: {e}")
                if self.smtp_debug:
                    traceback.print_exc()
                time.sleep(self.smtp_retry_delay)
        print(f"[SMTP][FATAL] Falha após {self.smtp_retry_attempts} tentativas. Último erro: {last_error}")
        return False

    # Envia o email de recuperacao
    def send_recovery_email(self, payload: dict) -> bool:
        email = payload.get('email')
        nome = payload.get('nome') or 'Usuário'
        token = payload.get('token')
        reset_url = payload.get('resetUrl')

        if not email or not EMAIL_REGEX.match(email):
            print(f"[EMAIL][INVALIDO] Ignorando envio para '{email}'")
            return False
        if not token:
            print("[EMAIL][ERRO] Payload sem token")
            return False

        links = self.build_links(token, reset_url)

        subject = 'Recuperação de Senha - Mima Store'
        from_header = f"{self.sender_name} <{self.email_address}>"

        msg = MIMEMultipart('alternative')
        msg['From'] = from_header
        msg['To'] = email
        msg['Subject'] = subject

        plain_body = f"""Olá {nome},\n\nVocê solicitou a recuperação de senha da sua conta na Mima Store.\n\nSeu token de recuperação:\n{token}\n\nCopie o token acima e utilize no Swagger ou na API para redefinir sua senha.\n\nSe você não fez esta solicitação, ignore este e-mail.\n\nEquipe Mima Store\n"""

        html_body = f"""
<!DOCTYPE html>
<html lang='pt-BR'>
<head>
  <meta charset='utf-8'>
  <meta name='viewport' content='width=device-width, initial-scale=1.0'>
  <title>Recuperação de Senha</title>
  <style>
    body {{
      font-family: Arial, sans-serif;
      background: #F8C8DC;
      margin: 0;
      padding: 24px;
    }}
    .container {{
      max-width: 640px;
      margin: 0 auto;
      background: #fff;
      border-radius: 8px;
      overflow: hidden;
      border: 1px solid #e0e0e0;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }}
    .header {{
      background: #863e76;
      color: #fff;
      padding: 24px;
      text-align: center;
    }}
    .header h1 {{
      margin: 0;
      font-size: 22px;
    }}
    .header p {{
      margin: 4px 0 0;
      font-size: 14px;
    }}
    .content {{
      padding: 24px;
    }}
    .content h2 {{
      margin: 0 0 12px;
      font-size: 18px;
      color: #222;
    }}
    .token-box {{
      margin: 24px 0;
      background: #f7e9f3;
      padding: 20px;
      border-radius: 6px;
      border: 2px dashed #863e76;
      text-align: center;
    }}
    .token-box span {{
      display: block;
      font-size: 14px;
      color: #863e76;
      margin-bottom: 8px;
      font-weight: bold;
    }}
    .token-box code {{
      display: block;
      font-size: 18px;
      color: #222;
      background: #fff;
      padding: 12px;
      border-radius: 4px;
      word-break: break-all;
      font-family: 'Courier New', monospace;
      letter-spacing: 1px;
    }}
    .instructions {{
      background: #f8f9fa;
      padding: 16px;
      border-radius: 6px;
      margin: 20px 0;
      font-size: 14px;
      color: #444;
    }}
    .instructions ol {{
      margin: 8px 0;
      padding-left: 20px;
    }}
    .instructions li {{
      margin: 6px 0;
    }}
    .footer {{
      margin: 28px 0 0;
      text-align: center;
      font-size: 13px;
      color: #444;
      background: #f8f9fa;
      padding: 16px;
      border-radius: 6px;
    }}
    .footer div {{
      margin: 4px 0;
    }}
    .warning {{
      background: #fff3cd;
      border-left: 4px solid #ffc107;
      padding: 12px;
      margin: 16px 0;
      font-size: 13px;
      color: #856404;
    }}
  </style>
</head>
<body>
  <div class='container'>
    <div class='header'>
      <h1>{self.loja_nome}</h1>
      <p>Recuperação de Senha</p>
    </div>
    <div class='content'>
      <h2>Olá, {nome}!</h2>
      <p>Você solicitou a recuperação de senha da sua conta na <strong>Mima Store</strong>.</p>
      
      <div class='token-box'>
        <span>SEU TOKEN DE RECUPERAÇÃO</span>
        <code>{token}</code>
      </div>

      <div class='instructions'>
        <strong>Como usar o token:</strong>
        <ol>
          <li>Copie o token acima</li>
          <li>Acesse o Swagger ou a API da Mima Store</li>
          <li>Cole o token no campo apropriado para validar a recuperação</li>
          <li>Defina sua nova senha</li>
        </ol>
      </div>

      <div class='warning'>
        <strong>⚠️ Atenção:</strong> Se você não solicitou esta recuperação de senha, ignore este e-mail. Seu acesso permanecerá seguro.
      </div>
    </div>
    <div class='footer'>
      <div><strong>{self.loja_nome}</strong></div>
      <div>Equipe de Suporte</div>
      <div>&copy; 2025 Mima Store. Todos os direitos reservados.</div>
    </div>
  </div>
</body>
</html>
"""

        if self.include_plain:
            msg.attach(MIMEText(plain_body, 'plain', 'utf-8'))
        if self.include_html:
            msg.attach(MIMEText(html_body, 'html', 'utf-8'))

        print(f"[EMAIL][ENVIANDO] Para {email} via {self.smtp_server}:{self.smtp_port} SSL={self.use_ssl} STARTTLS={self.use_starttls}")
        return self._smtp_connect_and_send(email, msg)

    # Processa mensagem que chega da fila
    def process_message(self, ch, method, properties, body: bytes):  # type: ignore
        try:
            message = json.loads(body.decode('utf-8'))
            if not message.get('email') or not message.get('token'):
                raise ValueError('Mensagem inválida: requer email e token')
            self.send_recovery_email(message)
            ch.basic_ack(delivery_tag=method.delivery_tag)
        except Exception as e:
            print(f"[QUEUE][ERRO] {e} | RAW={body!r}")
            traceback.print_exc()
            ch.basic_ack(delivery_tag=method.delivery_tag)

    # Fica rodando e esperando mensagens da fila
    def start_consuming(self):
        import pika  # importa aqui pra nao dar erro se nao tiver instalado
        while True:
            try:
                print(f"[RABBITMQ][CONECTANDO] {self.rabbitmq_host}:{self.rabbitmq_port} vhost='{self.rabbitmq_vhost}' queue='{self.queue_name}'")
                credentials = pika.PlainCredentials(self.rabbitmq_user, self.rabbitmq_password)
                params = pika.ConnectionParameters(
                    host=self.rabbitmq_host,
                    port=self.rabbitmq_port,
                    virtual_host=self.rabbitmq_vhost,
                    credentials=credentials,
                    heartbeat=30,
                    blocked_connection_timeout=60
                )
                connection = pika.BlockingConnection(params)
                channel = connection.channel()
                channel.queue_declare(queue=self.queue_name, durable=True)
                channel.basic_qos(prefetch_count=self.prefetch_count)
                channel.basic_consume(queue=self.queue_name, on_message_callback=self.process_message)
                print('[RABBITMQ][PRONTO] Aguardando mensagens de recuperação de senha...')
                channel.start_consuming()
            except KeyboardInterrupt:
                print('[RABBITMQ][STOP] Interrompido pelo usuário.')
                break
            except Exception as e:
                print(f"[RABBITMQ][ERRO] {e}. Reconnecting in {self.reconnect_delay}s...")
                time.sleep(self.reconnect_delay)


def send_test_email(recipient: str):
    consumer = PasswordRecoveryConsumer()
    fake_token = 'TOKEN_TESTE_ABC123'
    payload = {
        'userId': 0,
        'email': recipient,
        'nome': 'Teste',
        'token': fake_token,
        'resetUrl': f"{consumer.frontend_reset_url}?token={fake_token}"
    }
    consumer.send_recovery_email(payload)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Consumidor de recuperação de senha - Mima Store')
    parser.add_argument('--test', metavar='email', help='Envia um email de teste e sai')
    args = parser.parse_args()

    if args.test:
        send_test_email(args.test)
    else:
        PasswordRecoveryConsumer().start_consuming()
