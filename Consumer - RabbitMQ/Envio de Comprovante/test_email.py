import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# Configurações do Maileroo
SMTP_SERVER = 'smtp.maileroo.com'
SMTP_PORT = 587
SMTP_USER = 'mimatestecaue@92f8eb7ddd8b8d6c.maileroo.org'
SMTP_PASS = 'c2e619492e8587ac45944bb0'
EMAIL_FROM = 'mimatestecaue@92f8eb7ddd8b8d6c.maileroo.org'
EMAIL_TO = 'cauemoms@gmail.com'

print("=" * 60)
print("TESTE DE ENVIO DE EMAIL - MAILEROO")
print("=" * 60)
print(f"Servidor: {SMTP_SERVER}:{SMTP_PORT}")
print(f"De: {EMAIL_FROM}")
print(f"Para: {EMAIL_TO}")
print()

try:
    # Cria mensagem
    msg = MIMEMultipart()
    msg['From'] = f"Mima Store <{EMAIL_FROM}>"
    msg['To'] = EMAIL_TO
    msg['Subject'] = 'Teste de Email - Maileroo'
    
    body = """
    Olá!
    
    Este é um email de teste enviado via Maileroo.
    
    Se você recebeu esta mensagem, o envio está funcionando corretamente.
    
    Atenciosamente,
    Mima Store
    """
    
    msg.attach(MIMEText(body, 'plain', 'utf-8'))
    
    # Conecta e envia
    print("[1/4] Conectando ao servidor SMTP...")
    server = smtplib.SMTP(SMTP_SERVER, SMTP_PORT, timeout=30)
    server.set_debuglevel(1)  # Mostra debug completo
    
    print("\n[2/4] Iniciando STARTTLS...")
    server.starttls()
    server.ehlo()
    
    print("\n[3/4] Fazendo login...")
    server.login(SMTP_USER, SMTP_PASS)
    
    print("\n[4/4] Enviando email...")
    result = server.sendmail(EMAIL_FROM, [EMAIL_TO], msg.as_string())
    
    server.quit()
    
    print("\n" + "=" * 60)
    print("✅ EMAIL ENVIADO COM SUCESSO!")
    print("=" * 60)
    print(f"Resultado: {result if result else 'Aceito pelo servidor'}")
    print("\nVerifique o email (incluindo SPAM) em alguns minutos.")
    
except smtplib.SMTPAuthenticationError as e:
    print(f"\n❌ ERRO DE AUTENTICAÇÃO: {e}")
    print("Verifique o usuário e senha do Maileroo")
    
except smtplib.SMTPRecipientsRefused as e:
    print(f"\n❌ EMAIL RECUSADO: {e}")
    print("O servidor recusou o destinatário")
    
except smtplib.SMTPException as e:
    print(f"\n❌ ERRO SMTP: {e}")
    
except Exception as e:
    print(f"\n❌ ERRO: {e}")
    import traceback
    traceback.print_exc()
