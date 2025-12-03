#!/bin/bash
# Script para corrigir e redeployar os consumers RabbitMQ
# Execute na instância AWS: bash fix-consumers.sh

echo "=============================================="
echo "  🔧 Corrigindo Consumers RabbitMQ - Mima Store"
echo "=============================================="
echo ""

# Cores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Diretórios
COMPROVANTE_DIR="$HOME/backend/Consumer - RabbitMQ/Envio de Comprovante"
SENHA_DIR="$HOME/backend/Consumer - RabbitMQ/Recuperação de Senha"

# 1. Atualizar código
echo -e "${YELLOW}[1/4] Atualizando código do repositório...${NC}"
cd ~/backend
git pull origin main
echo -e "${GREEN}✅ Código atualizado${NC}"
echo ""

# 2. Parar containers antigos
echo -e "${YELLOW}[2/4] Parando consumers antigos...${NC}"
docker stop consumer-comprovantes consumer-password-recovery 2>/dev/null || true
docker rm consumer-comprovantes consumer-password-recovery 2>/dev/null || true
echo -e "${GREEN}✅ Containers antigos removidos${NC}"
echo ""

# 3. Recriar consumer de comprovantes
echo -e "${YELLOW}[3/4] Recriando consumer de comprovantes...${NC}"
if [ -d "$COMPROVANTE_DIR" ]; then
    cd "$COMPROVANTE_DIR"
    docker-compose down 2>/dev/null || true
    docker-compose up -d --build
    echo -e "${GREEN}✅ Consumer de comprovantes iniciado${NC}"
    sleep 3
    docker logs --tail 10 consumer-comprovantes
else
    echo -e "${RED}❌ Diretório não encontrado: $COMPROVANTE_DIR${NC}"
fi
echo ""

# 4. Recriar consumer de recuperação de senha
echo -e "${YELLOW}[4/4] Recriando consumer de recuperação de senha...${NC}"
if [ -d "$SENHA_DIR" ]; then
    cd "$SENHA_DIR"
    docker-compose -f docker-compose.consumer.yml down 2>/dev/null || true
    docker-compose -f docker-compose.consumer.yml up -d --build
    echo -e "${GREEN}✅ Consumer de recuperação de senha iniciado${NC}"
    sleep 3
    docker logs --tail 10 consumer-password-recovery
else
    echo -e "${RED}❌ Diretório não encontrado: $SENHA_DIR${NC}"
fi
echo ""

# 5. Verificar status
echo "=============================================="
echo -e "${YELLOW}📊 Status dos Containers${NC}"
echo "=============================================="
docker ps --format "table {{.Names}}\t{{.Status}}" | grep -E "NAMES|consumer|rabbitmq|backend|db"
echo ""

# 6. Verificar conexão
echo "=============================================="
echo -e "${YELLOW}🔍 Verificando Conectividade${NC}"
echo "=============================================="

echo -n "RabbitMQ: "
if docker ps | grep -q "rabbitmq"; then
    echo -e "${GREEN}✅ Rodando${NC}"
else
    echo -e "${RED}❌ Não encontrado${NC}"
fi

echo -n "Consumer Comprovantes: "
if docker ps | grep -q "consumer-comprovantes"; then
    echo -e "${GREEN}✅ Rodando${NC}"
    # Verificar se está conectado ao RabbitMQ
    sleep 2
    if docker logs consumer-comprovantes 2>&1 | grep -q "PRONTO\|Aguardando"; then
        echo "   ${GREEN}✓ Conectado ao RabbitMQ${NC}"
    else
        echo "   ${RED}✗ Verificar logs: docker logs consumer-comprovantes${NC}"
    fi
else
    echo -e "${RED}❌ Não encontrado${NC}"
fi

echo -n "Consumer Recuperação Senha: "
if docker ps | grep -q "consumer-password-recovery"; then
    echo -e "${GREEN}✅ Rodando${NC}"
    # Verificar se está conectado ao RabbitMQ
    sleep 2
    if docker logs consumer-password-recovery 2>&1 | grep -q "PRONTO\|Aguardando"; then
        echo "   ${GREEN}✓ Conectado ao RabbitMQ${NC}"
    else
        echo "   ${RED}✗ Verificar logs: docker logs consumer-password-recovery${NC}"
    fi
else
    echo -e "${RED}❌ Não encontrado${NC}"
fi

echo ""
echo "=============================================="
echo -e "${GREEN}✅ Processo concluído!${NC}"
echo "=============================================="
echo ""
echo "📋 Próximos passos:"
echo "   1. Testar finalização de venda no frontend"
echo "   2. Verificar se o email chegou"
echo "   3. Ver logs: docker logs -f consumer-comprovantes"
echo ""
