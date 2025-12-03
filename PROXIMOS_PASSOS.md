# 🚀 Próximos Passos - Deploy MySQL Centralizado

## ✅ O que foi implementado

Todas as mudanças de código necessárias foram implementadas:

1. ✅ **application.properties** - Parametrizado para aceitar variáveis de ambiente
2. ✅ **compose.yaml** - Usa `DB_HOST` variável para conexão flexível
3. ✅ **deploy_on_privado.sh** - Aceita `RUN_DB` e `DB_HOST` como parâmetros
4. ✅ **CI/CD Workflow** - Deploy diferenciado para cada instância privada

---

## 📋 Ações Necessárias no GitHub

### 1. Configurar Secrets Obrigatórios

Acesse: `GitHub → Seu Repositório → Settings → Secrets and variables → Actions`

Adicione os seguintes secrets:

```bash
# IPs Privados das instâncias que rodam MySQL
REMOTE_HOST_PRIVADO_A=10.0.x.x    # Substitua pelo IP privado real da EC2 Privada A
REMOTE_HOST_PRIVADO_C=10.0.y.y    # Substitua pelo IP privado real da EC2 Privada C
```

### Como descobrir os IPs privados:
```bash
# Via AWS CLI
aws ec2 describe-instances \
  --filters "Name=tag:Name,Values=Privado-A" \
  --query 'Reservations[*].Instances[*].[PrivateIpAddress]' \
  --output text

# Ou pelo AWS Console
# EC2 → Instances → Selecionar instância → Ver "Private IPv4 addresses"
```

---

## 🔐 Ações Necessárias na AWS

### 2. Atualizar Security Groups

Garanta que o Security Group das instâncias privadas permite MySQL:

**Via AWS Console:**
1. Acesse: EC2 → Security Groups
2. Selecione o SG das instâncias privadas
3. Editar regras de entrada → Adicionar regra:
   - **Tipo**: MySQL/Aurora (3306)
   - **Origem**: `10.0.0.0/16` (CIDR da sua VPC)
   - Salvar

**Via AWS CLI:**
```bash
aws ec2 authorize-security-group-ingress \
  --group-id sg-xxxxxxxxx \
  --protocol tcp \
  --port 3306 \
  --cidr 10.0.0.0/16
```

**Via Terraform:**
```hcl
resource "aws_security_group_rule" "mysql_from_vpc" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  cidr_blocks       = ["10.0.0.0/16"]
  security_group_id = aws_security_group.private_instances.id
}
```

---

## 🧪 Testar o Deploy

### 3. Fazer o primeiro deploy

1. **Commit e push das alterações:**
   ```bash
   git add .
   git commit -m "feat: centralizar MySQL nas instâncias privadas A e C"
   git push origin main
   ```

2. **Acompanhar o deploy:**
   - Acesse: GitHub → Actions → Acompanhar workflow

3. **Verificar se o deploy foi bem-sucedido:**
   - Verificar logs do GitHub Actions
   - Verificar containers nas instâncias

---

## ✅ Checklist de Validação Pós-Deploy

### No Privado A (com banco)
```bash
# Conectar via jump host
ssh -i key.pem ec2-user@<IP_PUBLICO_A>
ssh ec2-user@<IP_PRIVADO_A>

# Verificar containers
sudo docker ps

# Deve ter:
# ✅ mimastore-db (MySQL)
# ✅ mimastore-backend
# ✅ rabbitmq
# ✅ consumers

# Verificar logs do backend
sudo docker logs mimastore-backend 2>&1 | grep "jdbc:mysql://localhost"

# Verificar MySQL funcionando
sudo docker exec mimastore-db mysql -uroot -proot -e "SHOW DATABASES;"
```

### No Privado B (sem banco)
```bash
# Conectar via jump host
ssh -i key.pem ec2-user@<IP_PUBLICO_A>
ssh ec2-user@<IP_PRIVADO_B>

# Verificar containers
sudo docker ps

# Deve ter:
# ✅ mimastore-backend
# ✅ rabbitmq
# ✅ consumers
# ❌ NÃO deve ter mimastore-db

# Testar conectividade ao MySQL do Privado A
nc -zv <IP_PRIVADO_A> 3306

# Verificar logs do backend (deve conectar ao IP do Privado A)
sudo docker logs mimastore-backend 2>&1 | grep "jdbc:mysql://<IP_PRIVADO_A>"
```

### Teste Funcional via ALB
```bash
# Fazer várias requisições
for i in {1..10}; do
  curl https://seu-alb.amazonaws.com/api/health
  sleep 1
done

# Criar um produto
curl -X POST https://seu-alb.amazonaws.com/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste Centralizado", "preco": 99.90}'

# Verificar produto (ALB vai balancear entre A e B)
curl https://seu-alb.amazonaws.com/api/produtos
```

---

## 🔍 Troubleshooting Rápido

### Problema: Backend não conecta ao MySQL

```bash
# 1. Verificar MySQL rodando
sudo docker ps | grep mimastore-db

# 2. Testar porta 3306
nc -zv <IP_PRIVADO_A> 3306

# 3. Verificar Security Group
# AWS Console → EC2 → Security Groups → Verificar regra porta 3306

# 4. Verificar logs
sudo docker logs mimastore-backend
sudo docker logs mimastore-db
```

### Problema: Privado B sobe MySQL quando não deveria

```bash
# Verificar variáveis de ambiente no deploy
# GitHub Actions → Ver logs → Procurar "RUN_DB=false"

# Remover MySQL do Privado B
cd ~/backend/Banco\ de\ Dados
sudo docker-compose down -v
```

---

## 📚 Documentação Completa

Para mais detalhes, consulte:
- **DEPLOYMENT_MYSQL_CENTRALIZADO.md** - Documentação completa com troubleshooting avançado

---

## 🆘 Dúvidas?

Se algo não funcionar:
1. Verifique os logs do GitHub Actions
2. Verifique os logs dos containers
3. Consulte o arquivo `DEPLOYMENT_MYSQL_CENTRALIZADO.md`
4. Verifique Security Groups na AWS

---

## 📊 Resumo da Arquitetura

```
┌─────────────────────────────────────────────────┐
│           Application Load Balancer             │
└────────────┬──────────────────┬─────────────────┘
             │                  │
   ┌─────────▼─────────┐    ┌──▼──────────┐
   │   Público A       │    │  Público C   │
   └─────────┬─────────┘    └──┬──────────┘
             │                  │
   ┌─────────▼─────────┐    ┌──▼──────────┐
   │ 🗄️ Privado A     │    │🗄️ Privado C │
   │ MySQL ✅          │    │MySQL ✅      │
   │ Backend ✅        │    │Backend ✅    │
   └─────────┬─────────┘    └──┬──────────┘
             │                  │
   ┌─────────▼─────────┐    ┌──▼──────────┐
   │   Privado B       │    │  Privado D   │
   │ Backend apenas ✅ │    │Backend apenas│
   │ (→ MySQL A)       │    │(→ MySQL C)   │
   └───────────────────┘    └──────────────┘
```

---

**Bom deploy! 🚀**
