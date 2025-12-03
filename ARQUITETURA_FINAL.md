# 🏗️ Arquitetura Final - MySQL e RabbitMQ Centralizados

## 📋 Configuração Implementada

### Resumo
- **MySQL + RabbitMQ:** Apenas na **Privada B**
- **Backend Privada B:** Conecta localmente (localhost)
- **Backend Privada D:** Conecta remotamente ao MySQL e RabbitMQ da Privada B
- **Frontends:** Públicas A e C acessam backends via ALB

---

## 🗺️ Mapeamento de Instâncias

| Nome Lógico | Nome AWS        | Função                              |
|-------------|-----------------|-------------------------------------|
| Pública A   | EC2 Pública A   | Frontend + Jump Host                |
| Privada B   | EC2 Privada A   | **MySQL + RabbitMQ + Backend**      |
| Pública C   | EC2 Pública B   | Frontend + Jump Host                |
| Privada D   | EC2 Privada B   | Backend (conecta remotamente à B)   |

---

## 🏗️ Diagrama de Arquitetura

```
                    ┌─────────────────────┐
                    │  Internet Gateway   │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │        ALB          │
                    │  (Load Balancer)    │
                    └──────────┬──────────┘
                               │
                    ┌──────────┴──────────┐
                    │        VPC          │
                    │   (10.0.0.0/16)     │
                    └──────────┬──────────┘
                               │
        ┌──────────────────────┴──────────────────────┐
        │                                             │
┌───────▼────────┐                          ┌────────▼────────┐
│   Região A     │                          │   Região B      │
│  (us-east-1a)  │                          │  (us-east-1b)   │
└────────────────┘                          └─────────────────┘
        │                                             │
┌───────▼────────┐                          ┌────────▼────────┐
│  Pública A     │                          │   Pública C     │
│  (Frontend)    │                          │   (Frontend)    │
│  Jump Host     │                          │   Jump Host     │
└───────┬────────┘                          └────────┬────────┘
        │                                             │
        │ SSH                                         │ SSH
        │                                             │
┌───────▼────────────────────────┐          ┌────────▼────────┐
│      Privada B                 │          │   Privada D     │
│  ┌──────────────────────────┐ │          │  ┌─────────────┐│
│  │  MySQL Container         │ │          │  │   Backend   ││
│  │  - Port: 3306            │ │◄─────────┼──│   Container ││
│  └──────────────────────────┘ │  Remoto  │  │             ││
│  ┌──────────────────────────┐ │          │  │  DB_HOST=   ││
│  │  RabbitMQ Container      │ │◄─────────┼──│  10.0.x.x   ││
│  │  - Port: 5672, 15672     │ │  Remoto  │  │             ││
│  └──────────────────────────┘ │          │  │ RABBITMQ=   ││
│  ┌──────────────────────────┐ │          │  │  10.0.x.x   ││
│  │  Backend Container       │ │          │  └─────────────┘│
│  │  - DB_HOST: localhost    │ │          └─────────────────┘
│  │  - RABBITMQ: localhost   │ │
│  └──────────────────────────┘ │
└────────────────────────────────┘
          ▲
          │
     ÚNICO BANCO
     ÚNICO RABBITMQ
```

---

## 🔧 Configuração por Instância

### Privada B (10.0.x.x) - Região A
```bash
RUN_DB=true
DB_HOST=localhost
RABBITMQ_HOST=localhost

Containers:
✅ mimastore-db (MySQL)
✅ mimastore-rabbitmq (RabbitMQ)
✅ mimastore-backend
✅ consumers (comprovante + senha)
```

### Privada D (10.0.y.y) - Região B
```bash
RUN_DB=false
DB_HOST=10.0.x.x  (IP privado da Privada B)
RABBITMQ_HOST=10.0.x.x

Containers:
✅ mimastore-backend
✅ consumers (comprovante + senha)
❌ MySQL (conecta remotamente)
❌ RabbitMQ (conecta remotamente)
```

---

## 🔐 Secrets Necessários no GitHub

```bash
# Secrets obrigatórios
REMOTE_HOST_PRIVADO_B=10.0.x.x   # IP privado da Privada B (tem MySQL/RabbitMQ)

# Secrets existentes (manter)
EC2_SSH_KEY_AB
EC2_SSH_KEY_CD
REMOTE_USER
REMOTE_HOST
REMOTE_HOST_B
REMOTE_HOST_C
REMOTE_HOST_D
```

> ⚠️ **Importante:** Apenas 1 secret novo necessário: `REMOTE_HOST_PRIVADO_B`

---

## 🔐 Security Groups AWS

### Privada B (com MySQL e RabbitMQ)
```yaml
Inbound:
  - Port 22:    Source: Bastion SG
  - Port 8080:  Source: ALB SG
  - Port 3306:  Source: 10.0.0.0/16    # ← MySQL para toda VPC
  - Port 5672:  Source: 10.0.0.0/16    # ← RabbitMQ para toda VPC
  - Port 15672: Source: 10.0.0.0/16    # ← RabbitMQ Management
```

### Privada D (backend apenas)
```yaml
Inbound:
  - Port 22:    Source: Bastion SG
  - Port 8080:  Source: ALB SG

Outbound:
  - Port 3306:  Destination: 10.0.0.0/16    # ← Acesso MySQL
  - Port 5672:  Destination: 10.0.0.0/16    # ← Acesso RabbitMQ
  - All others: Destination: 0.0.0.0/0
```

---

## ✅ Checklist de Validação

### Pré-Deploy
- [ ] Secret `REMOTE_HOST_PRIVADO_B` configurado no GitHub
- [ ] Security Group permite portas 3306 e 5672 na VPC
- [ ] Instância Privada B tem espaço em disco suficiente (>20GB)

### Pós-Deploy na Privada B
```bash
# Conectar via jump host
ssh ec2-user@publica-a
ssh ec2-user@<IP_PRIVADO_B>

# Verificar containers
sudo docker ps

# Deve mostrar:
# ✅ mimastore-db
# ✅ mimastore-rabbitmq
# ✅ mimastore-backend

# Testar MySQL
sudo docker exec mimastore-db mysql -uroot -proot -e "SHOW DATABASES;"

# Testar RabbitMQ
curl http://localhost:15672/api/overview -u myuser:secret
```

### Pós-Deploy na Privada D
```bash
# Conectar via jump host
ssh ec2-user@publica-c
ssh ec2-user@<IP_PRIVADO_D>

# Verificar containers
sudo docker ps

# Deve mostrar:
# ✅ mimastore-backend
# ❌ NÃO deve ter mimastore-db
# ❌ NÃO deve ter mimastore-rabbitmq

# Testar conectividade MySQL
nc -zv <IP_PRIVADO_B> 3306

# Testar conectividade RabbitMQ
nc -zv <IP_PRIVADO_B> 5672

# Verificar logs do backend
sudo docker logs mimastore-backend 2>&1 | grep -E "jdbc:mysql|rabbitmq"

# Deve mostrar:
# jdbc:mysql://<IP_PRIVADO_B>:3306/MimaStore
# Connected to RabbitMQ at <IP_PRIVADO_B>:5672
```

### Teste Funcional
```bash
# Fazer requisições via ALB
for i in {1..10}; do
  curl https://seu-alb.amazonaws.com/api/health
  sleep 1
done

# Criar produto (ALB balanceia entre B e D)
curl -X POST https://seu-alb.amazonaws.com/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste Centralizado", "preco": 99.90}'

# Verificar em múltiplas requisições
curl https://seu-alb.amazonaws.com/api/produtos | jq .

# O produto deve aparecer em todas as respostas (mesmo banco)
```

---

## 🚀 Fluxo de Deploy

```
GitHub Actions
      │
      ├─► Pública A (rsync)
      │        │
      │        ├─► Privada B (RUN_DB=true, localhost)
      │        │   ✅ MySQL
      │        │   ✅ RabbitMQ
      │        │   ✅ Backend
      │
      └─► Pública C (rsync)
               │
               └─► Privada D (RUN_DB=false, DB_HOST=<IP_B>)
                   ❌ MySQL
                   ❌ RabbitMQ
                   ✅ Backend (remoto)
```

---

## 📊 Vantagens da Nova Arquitetura

| Aspecto         | Antes (4 instâncias) | Depois (1 instância) |
|-----------------|---------------------|----------------------|
| MySQL           | 4 bancos            | 1 banco              |
| RabbitMQ        | 4 instâncias        | 1 instância          |
| Consistência    | ❌ Dados isolados   | ✅ Dados unificados  |
| Custo           | 💰💰💰💰           | 💰                   |
| Backup          | 4 rotinas           | 1 rotina             |
| Manutenção      | Complexa            | Simples              |
| Migração p/ RDS | Difícil             | Fácil                |

---

## ⚠️ Limitações

1. **Ponto único de falha:** Se Privada B cair, todo sistema fica sem banco
2. **Latência entre regiões:** Privada D acessa MySQL remotamente (leve overhead)
3. **Não há replicação:** Sem failover automático

### Recomendações Futuras

1. **Curto prazo:** Implementar backups automáticos do MySQL
2. **Médio prazo:** Configurar monitoring e alertas
3. **Longo prazo:** Migrar para **AWS RDS Multi-AZ** (alta disponibilidade)

---

## 🆘 Troubleshooting Rápido

### Backend não conecta ao MySQL/RabbitMQ remoto
```bash
# 1. Testar conectividade
nc -zv <IP_PRIVADO_B> 3306
nc -zv <IP_PRIVADO_B> 5672

# 2. Verificar Security Group
# AWS Console → EC2 → Security Groups → Verificar portas 3306 e 5672

# 3. Ver logs do backend
sudo docker logs mimastore-backend --tail 100
```

### MySQL não inicia na Privada B
```bash
# Verificar espaço em disco
df -h

# Ver logs
sudo docker logs mimastore-db

# Reiniciar
cd ~/backend/Banco\ de\ Dados
sudo docker-compose restart db
```

---

**Status:** ✅ Implementado  
**Data:** Dezembro 2025  
**Versão:** 3.0 (Centralizado na Privada B)
