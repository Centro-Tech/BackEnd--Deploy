# 🐰 Consumers RabbitMQ - Mima Store

Consumers Python para processamento assíncrono de emails no sistema Mima Store.

## 📦 Consumers Disponíveis

### 1. **Envio de Comprovante** 📧
- **Localização**: `Consumer - RabbitMQ/Envio de Comprovante/`
- **Fila**: `comprovante.venda.queue`
- **Função**: Envia comprovante de venda por email para o cliente
- **Container**: `consumer-comprovantes`

### 2. **Recuperação de Senha** 🔑
- **Localização**: `Consumer - RabbitMQ/Recuperação de Senha/`
- **Fila**: `password.recovery.queue`
- **Função**: Envia token de recuperação de senha por email
- **Container**: `consumer-password-recovery`

---

## 🚀 Como Testar Localmente

### **Pré-requisitos**
- Python 3.11+
- Docker e Docker Compose
- RabbitMQ rodando (pode usar o docker-compose local)

### **1. Subir RabbitMQ Local**

```bash
cd "Consumer - RabbitMQ/Recuperação de Senha"
docker compose up -d rabbitmq
```

Acesse a interface web: http://localhost:15672
- Usuário: `myuser`
- Senha: `secret`

### **2. Rodar Consumer de Comprovantes**

```bash
cd "Consumer - RabbitMQ/Envio de Comprovante"

# Instalar dependências
pip install -r requirements.txt

# Rodar consumer
python comprovante_venda_consumer.py
```

**OU com Docker:**

```bash
docker compose up --build
```

### **3. Rodar Consumer de Recuperação de Senha**

```bash
cd "Consumer - RabbitMQ/Recuperação de Senha"

# Instalar dependências
pip install -r requirements.txt

# Rodar consumer
python password_recovery_consumer_backup.py
```

**OU com Docker:**

```bash
docker compose -f docker-compose.consumer.yml up --build
```

### **4. Enviar Mensagem de Teste**

Você pode testar enviando mensagens via interface web do RabbitMQ ou via código Python:

```python
import pika
import json

# Conectar no RabbitMQ
connection = pika.BlockingConnection(
    pika.ConnectionParameters('localhost', 5672, '/',
        pika.PlainCredentials('myuser', 'secret'))
)
channel = connection.channel()

# Testar comprovante de venda
message_comprovante = {
    "vendaId": 123,
    "valorTotal": 199.90,
    "dataVenda": "2025-11-27",
    "cliente": {
        "nome": "João Silva",
        "email": "joao@example.com",
        "cpf": "123.456.789-00"
    },
    "funcionario": {
        "nome": "Maria Santos",
        "cargo": "Vendedora"
    },
    "itens": [
        {
            "nome": "Camiseta Rosa",
            "codigo": "CAM001",
            "quantidade": 2,
            "precoUnitario": 49.90,
            "subtotal": 99.80,
            "categoria": "Vestuário",
            "cor": "Rosa",
            "tamanho": "M"
        }
    ]
}

channel.basic_publish(
    exchange='',
    routing_key='comprovante.venda.queue',
    body=json.dumps(message_comprovante)
)

print("✅ Mensagem enviada para fila de comprovantes!")

# Testar recuperação de senha
message_senha = {
    "userId": 1,
    "email": "usuario@example.com",
    "nome": "Usuário Teste",
    "token": "abc123xyz789",
    "resetUrl": "https://mimastore.com/reset-password?token=abc123xyz789"
}

channel.basic_publish(
    exchange='',
    routing_key='password.recovery.queue',
    body=json.dumps(message_senha)
)

print("✅ Mensagem enviada para fila de recuperação de senha!")

connection.close()
```

---

## 🐳 Deploy Automático (CI/CD)

Quando você fizer **push para a branch `main`**, o GitHub Actions automaticamente:

1. ✅ Valida sintaxe dos arquivos Python
2. ✅ Faz build das imagens Docker
3. ✅ Envia para as instâncias AWS (zonas 1 e 2)
4. ✅ Inicia os containers automaticamente
5. ✅ Conecta à rede `mima-network` do backend

### **Verificar Deploy na AWS**

```bash
# SSH na instância privada
ssh -i chave.pem ubuntu@<IP_PRIVADO>

# Ver logs dos consumers
sudo docker logs -f consumer-comprovantes
sudo docker logs -f consumer-password-recovery

# Ver status
sudo docker ps | grep consumer

# Reiniciar se necessário
cd ~/backend/Consumer\ -\ RabbitMQ/Envio\ de\ Comprovante/
sudo docker compose restart
```

---

## 🔧 Configuração de Variáveis de Ambiente

### **Consumer de Comprovantes**

```env
# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=myuser
RABBITMQ_PASSWORD=secret
COMPROVANTE_VENDA_QUEUE=comprovante.venda.queue

# SMTP (Maileroo)
SMTP_SERVER=smtp.maileroo.com
SMTP_PORT=587
SMTP_USE_TLS=1
SMTP_USER=mima@0935a8e1530952e9.maileroo.org
SMTP_PASS=1cff35807ba2cc8b1ac2103e
EMAIL_FROM=mima@0935a8e1530952e9.maileroo.org
SENDER_NAME=Mima Store

# Dados da Loja
LOJA_NOME=Mima Store
LOJA_ENDERECO=Rua das Compras, 123 - Centro - São Paulo/SP
LOJA_TELEFONE=(11) 3000-0000
LOJA_CNPJ=12.345.678/0001-90
```

### **Consumer de Recuperação de Senha**

```env
# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=myuser
RABBITMQ_PASSWORD=secret
PASSWORD_RECOVERY_QUEUE=password.recovery.queue

# SMTP (Maileroo)
SMTP_SERVER=smtp.maileroo.com
SMTP_PORT=587
SMTP_USE_TLS=1
SMTP_USER=mima@0935a8e1530952e9.maileroo.org
SMTP_PASS=1cff35807ba2cc8b1ac2103e
EMAIL_FROM=mima@0935a8e1530952e9.maileroo.org

# URLs
FRONTEND_RESET_URL=https://mimastore.com/reset-password
BACKEND_BASE_URL=http://localhost:8080
```

---

## 📊 Monitoramento

### **Logs em Tempo Real**

```bash
# Comprovantes
sudo docker logs -f consumer-comprovantes

# Recuperação de Senha
sudo docker logs -f consumer-password-recovery
```

### **RabbitMQ Management**

- **Local**: http://localhost:15672
- **AWS**: http://<IP_PUBLICO>:15672

Monitore:
- Número de mensagens nas filas
- Taxa de processamento
- Erros e rejeições

---

## 🛠️ Troubleshooting

### **Consumer não conecta no RabbitMQ**
```bash
# Testar conexão
telnet localhost 5672

# Verificar se RabbitMQ está rodando
sudo docker ps | grep rabbitmq

# Ver logs do RabbitMQ
sudo docker logs mima-rabbitmq
```

### **Emails não estão sendo enviados**
1. Verifique credenciais SMTP
2. Teste conexão SMTP: `telnet smtp.maileroo.com 587`
3. Veja logs do consumer para erros de autenticação
4. Verifique se o email do destinatário é válido

### **Container crashando**
```bash
# Ver logs de erro
sudo docker logs consumer-comprovantes --tail 50

# Reconstruir imagem
cd ~/backend/Consumer\ -\ RabbitMQ/Envio\ de\ Comprovante/
sudo docker compose up --build -d
```

---

## 📝 Dependências Python

Ambos os consumers usam:
- **pika** 1.3.2 - Cliente RabbitMQ
- **python-dotenv** 1.0.0 - Carregar variáveis de ambiente

Instalação:
```bash
pip install -r requirements.txt
```

---

## 🎯 Próximos Passos

- [ ] Adicionar testes unitários
- [ ] Implementar métricas (Prometheus)
- [ ] Adicionar alertas de falha
- [ ] Implementar Dead Letter Queue (DLQ)
- [ ] Adicionar rate limiting

---

**📅 Última atualização**: Novembro 2025  
**👥 Mantenedores**: Centro-Tech
