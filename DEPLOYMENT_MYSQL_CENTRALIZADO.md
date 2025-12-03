# 🗄️ Guia de Deploy - MySQL Centralizado

## 📋 Visão Geral

Este guia documenta a configuração para centralizar o MySQL em instâncias específicas, com múltiplos backends apontando para o banco centralizado.

### Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                      Application Load Balancer              │
└────────────┬──────────────────────────────┬─────────────────┘
             │                              │
   ┌─────────▼─────────┐        ┌──────────▼────────┐
   │   Público A       │        │   Público C       │
   │ (Jump Host AZ1)   │        │ (Jump Host AZ2)   │
   └─────────┬─────────┘        └──────────┬────────┘
             │                              │
   ┌─────────▼─────────┐        ┌──────────▼────────┐
   │   Privado A       │        │   Privado C       │
   │ MySQL + Backend   │        │ MySQL + Backend   │
   │ RabbitMQ          │        │ RabbitMQ          │
   └─────────┬─────────┘        └──────────┬────────┘
             │                              │
   ┌─────────▼─────────┐        ┌──────────▼────────┐
   │   Privado B       │        │   Privado D       │
   │ Backend apenas    │        │ Backend apenas    │
   │ (aponta para A)   │        │ (aponta para C)   │
   └───────────────────┘        └───────────────────┘
```

### Fluxo de Conexão

- **Privado A**: MySQL rodando localmente (`DB_HOST=localhost`)
- **Privado B**: Backend conecta ao MySQL no Privado A (`DB_HOST=10.0.x.x`)
- **Privado C**: MySQL rodando localmente (`DB_HOST=localhost`)
- **Privado D**: Backend conecta ao MySQL no Privado C (`DB_HOST=10.0.y.y`)

---

## 🔧 Configurações Necessárias

### 1️⃣ GitHub Secrets

Configure os seguintes secrets no repositório GitHub:

#### Secrets Existentes (manter)
- `EC2_SSH_KEY_AB` - Chave SSH para zona 1 (hosts A e B)
- `EC2_SSH_KEY_CD` - Chave SSH para zona 2 (hosts C e D)
- `REMOTE_USER` - Usuário SSH (normalmente `ec2-user`)
- `REMOTE_HOST` - IP público do host A
- `REMOTE_HOST_B` - IP público do host B (se usar múltiplas zonas)
- `REMOTE_HOST_C` - IP público do host C
- `REMOTE_HOST_D` - IP público do host D

#### **Novos Secrets Necessários** ⚠️
```bash
REMOTE_HOST_PRIVADO_A=10.0.1.10    # IP privado da instância A (com MySQL)
REMOTE_HOST_PRIVADO_C=10.0.3.10    # IP privado da instância C (com MySQL)
```

> **Importante**: Substitua pelos IPs privados reais das suas instâncias EC2 privadas.

---

## 🔐 Security Groups

### Configuração de Porta MySQL (3306)

Garanta que o Security Group das instâncias privadas permita tráfego MySQL:

```hcl
# Terraform example
ingress {
  description = "MySQL from VPC"
  from_port   = 3306
  to_port     = 3306
  protocol    = "tcp"
  cidr_blocks = ["10.0.0.0/16"]  # CIDR da sua VPC
}
```

### Verificação Manual (AWS Console)
1. Acesse **EC2 → Security Groups**
2. Selecione o SG das instâncias privadas
3. Adicione regra de entrada:
   - **Tipo**: MySQL/Aurora (3306)
   - **Origem**: CIDR da VPC (ex: `10.0.0.0/16`) ou SG do backend

---

## 📝 Checklist Pré-Deploy

Antes de fazer o deploy, verifique:

### ✅ Configuração do Repositório
- [ ] Secrets `REMOTE_HOST_PRIVADO_A` e `REMOTE_HOST_PRIVADO_C` configurados
- [ ] IPs privados corretos nas secrets
- [ ] Chaves SSH (`EC2_SSH_KEY_AB`, `EC2_SSH_KEY_CD`) configuradas

### ✅ Infraestrutura AWS
- [ ] Security Group permite porta 3306 dentro da VPC
- [ ] Instâncias privadas A e C têm espaço em disco suficiente (mínimo 20GB para MySQL)
- [ ] Instâncias privadas têm conectividade entre si
- [ ] Route Tables configuradas corretamente

### ✅ Código
- [ ] `application.properties` parametrizado com variáveis de ambiente
- [ ] `compose.yaml` usa `DB_HOST` variável
- [ ] Script `deploy_on_privado.sh` aceita `RUN_DB` e `DB_HOST`

---

## 🚀 Como Funciona o Deploy

### Zona 1 (Privado A e B)

1. **GitHub Actions → Host Público A**
   ```bash
   rsync código para /home/ec2-user/backend_temp_deploy
   ```

2. **Host Público A → Privado A** (COM banco)
   ```bash
   RUN_DB=true DB_HOST=localhost bash deploy_on_privado.sh
   ```
   - Sobe o MySQL container
   - Aguarda 30 segundos para inicialização
   - Cria arquivo `.env` com `DB_HOST=localhost`
   - Sobe backend e consumers

3. **Host Público A → Privado B** (SEM banco)
   ```bash
   RUN_DB=false DB_HOST=10.0.x.x bash deploy_on_privado.sh
   ```
   - Não sobe MySQL
   - Cria arquivo `.env` com `DB_HOST=<IP_PRIVADO_A>`
   - Sobe backend e consumers apontando para MySQL do Privado A

### Zona 2 (Privado C e D)

Mesmo processo, mas usando hosts C e D da zona 2.

---

## 🧪 Testes Pós-Deploy

### 1. Verificar Containers

**No Privado A (com banco):**
```bash
ssh -i key.pem ec2-user@<IP_PRIVADO_A>
sudo docker ps

# Deve mostrar:
# - mimastore-db (MySQL)
# - mimastore-backend
# - rabbitmq
# - consumers
```

**No Privado B (sem banco):**
```bash
ssh -i key.pem ec2-user@<IP_PRIVADO_B>
sudo docker ps

# Deve mostrar:
# - mimastore-backend
# - rabbitmq
# - consumers
# ❌ NÃO deve ter mimastore-db
```

### 2. Testar Conectividade ao MySQL

**Do Privado B para Privado A:**
```bash
ssh -i key.pem ec2-user@<IP_PRIVADO_B>

# Testar porta 3306
nc -zv <IP_PRIVADO_A> 3306

# Deve retornar: Connection to <IP_PRIVADO_A> 3306 port [tcp/mysql] succeeded!
```

### 3. Verificar Logs do Backend

**No Privado B (conectando remotamente):**
```bash
sudo docker logs mimastore-backend 2>&1 | grep -i datasource

# Deve mostrar:
# jdbc:mysql://<IP_PRIVADO_A>:3306/MimaStore
```

**No Privado A (conectando localmente):**
```bash
sudo docker logs mimastore-backend 2>&1 | grep -i datasource

# Deve mostrar:
# jdbc:mysql://localhost:3306/MimaStore
```

### 4. Teste Funcional via ALB

```bash
# Fazer várias requisições via ALB
for i in {1..10}; do
  curl -s https://seu-alb.amazonaws.com/api/health | jq .
  sleep 1
done

# Verificar que ambos backends respondem
# Verificar persistência de dados entre requisições
```

### 5. Verificar Persistência de Dados

```bash
# Criar um registro via API
curl -X POST https://seu-alb.amazonaws.com/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "preco": 10.0}'

# Verificar em múltiplas requisições (ALB vai balancear entre A e B)
for i in {1..5}; do
  curl https://seu-alb.amazonaws.com/api/produtos | jq .
done

# O registro deve aparecer em todas as respostas
```

---

## 🔍 Troubleshooting

### ❌ Backend não conecta ao MySQL remoto

**Sintoma:**
```
Communications link failure
The last packet sent successfully to the server was 0 milliseconds ago
```

**Solução:**
1. Verificar Security Group permite 3306
2. Testar conectividade: `nc -zv <IP_PRIVADO_A> 3306`
3. Verificar MySQL rodando: `sudo docker ps | grep mimastore-db`
4. Verificar logs do MySQL: `sudo docker logs mimastore-db`

### ❌ MySQL não inicia no Privado A

**Sintoma:**
```
Container mimastore-db keeps restarting
```

**Solução:**
1. Verificar espaço em disco: `df -h`
2. Verificar logs: `sudo docker logs mimastore-db`
3. Verificar permissões volume: `ls -la /home/ec2-user/app/db_data`

### ❌ Backend conecta mas queries falham

**Sintoma:**
```
Table 'MimaStore.produto' doesn't exist
```

**Solução:**
1. Verificar se o script SQL foi executado:
   ```bash
   sudo docker exec -it mimastore-db mysql -uroot -proot MimaStore -e "SHOW TABLES;"
   ```
2. Se necessário, executar manualmente:
   ```bash
   sudo docker exec -i mimastore-db mysql -uroot -proot MimaStore < /path/to/scriptpcro.sql
   ```

### ❌ Privado B sobe MySQL quando não deveria

**Sintoma:**
Container `mimastore-db` existe no Privado B

**Solução:**
1. Verificar que o CI/CD passa `RUN_DB=false` para o Privado B
2. Verificar logs do deploy no GitHub Actions
3. Remover manualmente se necessário:
   ```bash
   cd ~/backend/Banco\ de\ Dados
   sudo docker-compose down -v
   ```

---

## 📊 Monitoramento

### Métricas Importantes

1. **Conexões MySQL**
   ```bash
   sudo docker exec mimastore-db mysql -uroot -proot \
     -e "SHOW STATUS LIKE 'Threads_connected';"
   ```

2. **Tamanho do Banco**
   ```bash
   sudo docker exec mimastore-db mysql -uroot -proot \
     -e "SELECT table_schema AS 'Database', 
         ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)' 
         FROM information_schema.TABLES 
         WHERE table_schema = 'MimaStore';"
   ```

3. **Slow Queries**
   ```bash
   sudo docker exec mimastore-db mysql -uroot -proot \
     -e "SHOW STATUS LIKE 'Slow_queries';"
   ```

---

## ⚠️ Considerações de Produção

### Alta Disponibilidade

A configuração atual usa MySQL em EC2, criando um **ponto único de falha**:
- Se o Privado A cair, o backend no Privado B ficará sem banco
- Se o Privado C cair, o backend no Privado D ficará sem banco

### Recomendações para Produção

1. **AWS RDS Multi-AZ** (recomendado)
   - Alta disponibilidade automática
   - Backups automáticos
   - Failover automático
   - Menos gerenciamento

2. **Replicação MySQL Master-Slave**
   - Privado A = Master
   - Privado B = Slave (read replica)
   - Failover manual ou via Orchestrator

3. **Backups**
   ```bash
   # Backup manual do MySQL
   sudo docker exec mimastore-db mysqldump -uroot -proot MimaStore > backup.sql
   
   # Agendar via cron
   0 2 * * * cd /home/ec2-user && sudo docker exec mimastore-db mysqldump -uroot -proot MimaStore | gzip > backup_$(date +\%Y\%m\%d).sql.gz
   ```

### Reconexão Automática

O Spring Boot já tem reconexão configurada por padrão, mas você pode ajustar:

```properties
# application.properties
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.connection-test-query=SELECT 1
```

---

## 📚 Referências

- [Spring Boot DataSource Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data)
- [MySQL Docker Documentation](https://hub.docker.com/_/mysql)
- [AWS VPC Security Groups](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_SecurityGroups.html)
- [Docker Compose Environment Variables](https://docs.docker.com/compose/environment-variables/)

---

## 🆘 Suporte

Se encontrar problemas:

1. Verifique os logs do GitHub Actions
2. Verifique os logs dos containers: `sudo docker logs <container_name>`
3. Teste conectividade de rede: `nc -zv <host> <port>`
4. Verifique Security Groups no AWS Console
5. Consulte este guia de troubleshooting

---

**Última atualização:** Dezembro 2025  
**Versão:** 1.0
