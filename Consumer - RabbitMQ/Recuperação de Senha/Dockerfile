# Dockerfile para Consumer de Recuperação de Senha - Mima Store
FROM python:3.11-slim

LABEL maintainer="Centro-Tech"
LABEL description="Consumer RabbitMQ para envio de emails de recuperação de senha"

# Define diretório de trabalho
WORKDIR /app

# Copia requirements primeiro (cache layer do Docker)
COPY requirements.txt .

# Instala dependências Python
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Copia o código do consumer
COPY password_recovery_consumer_backup.py .

# Cria usuário não-root para segurança
RUN useradd -m -u 1000 consumeruser && \
    chown -R consumeruser:consumeruser /app

USER consumeruser

# Healthcheck simples (verifica se processo está rodando)
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD pgrep -f password_recovery_consumer || exit 1

# Comando para iniciar o consumer
CMD ["python", "-u", "password_recovery_consumer_backup.py"]
