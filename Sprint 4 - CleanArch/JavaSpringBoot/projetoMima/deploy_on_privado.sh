#!/bin/bash
set -e

echo "🚀 Iniciando deploy do backend..."

# Parâmetros de configuração (podem ser passados via variáveis de ambiente)
RUN_DB=${RUN_DB:-true}                    # true para subir MySQL, false para não subir
DB_HOST=${DB_HOST:-localhost}             # Host do banco (localhost ou IP privado da instância B)
DB_USERNAME=${DB_USERNAME:-mimastore_user}
DB_PASSWORD=${DB_PASSWORD:-12345}
RABBITMQ_HOST=${RABBITMQ_HOST:-localhost} # Host do RabbitMQ (localhost ou IP privado da instância B)

echo "📋 Configurações:"
echo "  - RUN_DB: $RUN_DB"
echo "  - DB_HOST: $DB_HOST"
echo "  - DB_USERNAME: $DB_USERNAME"
echo "  - RABBITMQ_HOST: $RABBITMQ_HOST"

# Diretórios
BACKEND_DIR="$HOME/backend"
JAVA_DIR="$BACKEND_DIR/JavaSpringBoot/projetoMima"
CONSUMER_DIR="$BACKEND_DIR/Consumer - RabbitMQ"
NGINX_DIR="$BACKEND_DIR/nginx"

cd "$JAVA_DIR"

# 1. Configurar Nginx
echo "📝 Configurando Nginx..."
if [ -f "$NGINX_DIR/backend.conf" ]; then
    sudo cp "$NGINX_DIR/backend.conf" /etc/nginx/sites-available/default
    sudo nginx -t && sudo systemctl reload nginx
    echo "✅ Nginx configurado e recarregado"
else
    echo "⚠️ Arquivo nginx/backend.conf não encontrado, pulando configuração do nginx"
fi

# 2. Subir o banco de dados (apenas se RUN_DB=true)
if [ "$RUN_DB" = "true" ]; then
    echo "🗄️ Subindo MySQL na instância privada B..."
    DB_DIR="$BACKEND_DIR/Banco de Dados"
    if [ -d "$DB_DIR" ]; then
        cd "$DB_DIR"
        sudo docker-compose down || true
        sudo docker-compose up -d --build
        echo "✅ MySQL iniciado com sucesso"
        echo "⏳ Aguardando MySQL inicializar (30 segundos)..."
        sleep 30
    else
        echo "⚠️ Diretório do banco não encontrado: $DB_DIR"
    fi
    cd "$JAVA_DIR"
else
    echo "⏭️ Pulando criação do banco (RUN_DB=false)"
    echo "📍 Backend irá conectar ao banco em: $DB_HOST:3306"
    echo "📍 Backend irá conectar ao RabbitMQ em: $RABBITMQ_HOST:5672"
fi

# 3. Build do projeto Java
echo "🔨 Compilando projeto Spring Boot..."
./mvnw clean package -DskipTests

# 4. Criar arquivo .env para o docker-compose
echo "📝 Criando arquivo .env com configurações do banco..."
cat > .env << EOF
DB_HOST=$DB_HOST
DB_USERNAME=$DB_USERNAME
DB_PASSWORD=$DB_PASSWORD
RABBITMQ_HOST=$RABBITMQ_HOST
EOF

# 5. Parar container antigo
echo "🛑 Parando containers antigos..."
sudo docker-compose down || true

# 6. Subir nova versão (apenas backend)
echo "🐳 Iniciando container do Backend..."
sudo docker-compose up -d --build

# 7. Aguardar backend estar pronto
echo "⏳ Aguardando backend inicializar..."
sleep 15

# 8. Verificar saúde
if curl -f http://localhost:8080/actuator/health >/dev/null 2>&1; then
    echo "✅ Backend está respondendo"
else
    echo "⚠️ Backend pode ainda estar inicializando"
fi

# 9. Subir consumers RabbitMQ
echo "🐰 Iniciando consumers RabbitMQ..."

# Consumer de comprovantes
if [ -d "$CONSUMER_DIR/Envio de Comprovante" ]; then
    cd "$CONSUMER_DIR/Envio de Comprovante"
    sudo RABBITMQ_HOST=rabbitmq docker-compose down || true
    sudo RABBITMQ_HOST=rabbitmq docker-compose up -d --build
    echo "✅ Consumer de comprovantes iniciado"
fi

# Consumer de recuperação de senha
if [ -d "$CONSUMER_DIR/Recuperação de Senha" ]; then
    cd "$CONSUMER_DIR/Recuperação de Senha"
    sudo RABBITMQ_HOST=rabbitmq docker-compose -f docker-compose.consumer.yml down || true
    sudo RABBITMQ_HOST=rabbitmq docker-compose -f docker-compose.consumer.yml up -d --build
    echo "✅ Consumer de recuperação de senha iniciado"
fi

echo "✅ Deploy concluído com sucesso!"
echo "📊 Containers em execução:"
sudo docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
