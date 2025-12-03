# Dockerfile para Consumer de Comprovante de Venda - Mima Store
FROM python:3.11-slim

LABEL maintainer="Centro-Tech"
LABEL description="Consumer RabbitMQ para envio de comprovantes de venda por email"

# Define diretório de trabalho
WORKDIR /app

# Copia requirements primeiro (cache layer do Docker)
COPY requirements.txt .

# Instala dependências Python
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Copia o código do consumer
COPY comprovante_venda_consumer.py .

# Cria usuário não-root para segurança
RUN useradd -m -u 1000 consumeruser && \
    chown -R consumeruser:consumeruser /app

USER consumeruser

# Healthcheck simples (verifica se processo está rodando)
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD pgrep -f comprovante_venda_consumer || exit 1

# Comando para iniciar o consumer
CMD ["python", "-u", "comprovante_venda_consumer.py"]
