# Environment Configuration Setup

## ✅ Security Status

All sensitive configuration files are properly protected:

### Ignored Files (Never Committed to Git)
- ✅ `.env` - Main environment file
- ✅ `.env.local` - Local overrides
- ✅ `.env.dev` - Development environment
- ✅ `.env.prod` - Production environment
- ✅ `.env.staging` - Staging environment
- ✅ `*.env` - Any .env variant
- ✅ `secrets/` - Secrets directory
- ✅ `*.pem`, `*.key`, `*.crt` - SSL certificates

### Committed Files (Safe to Version Control)
- ✅ `.env.example` - Template with NO sensitive values

## 📋 Quick Setup

### 1. Copy Template
```bash
cp .env.example .env
```

### 2. Configure for Development (Default)
The default `.env` values are ready to use:
```bash
# No changes needed for local development
docker-compose up -d
```

### 3. Configure for Production
Edit `.env` and update:
```bash
# Generate secure JWT secret
JWT_SECRET=$(openssl rand -base64 64)

# Set strong passwords
DB_PASSWORD=$(openssl rand -base64 32)
REDIS_PASSWORD=$(openssl rand -base64 24)

# Set production CORS
CORS_ORIGINS=https://admin.yourdomain.com,https://app.yourdomain.com

# Use production profile
SPRING_PROFILE=prod
```

## 🔐 Environment Variables Reference

| Variable | Dev Default | Prod Required | Description |
|----------|-------------|---------------|-------------|
| `DB_HOST` | `postgres` | Yes | PostgreSQL host |
| `DB_PORT` | `5432` | Yes | PostgreSQL port |
| `DB_NAME` | `barber` | Yes | Database name |
| `DB_USERNAME` | `postgres` | Yes | Database username |
| `DB_PASSWORD` | `1` | ⚠️ **CHANGE** | Database password |
| `REDIS_HOST` | `redis` | Yes | Redis host |
| `REDIS_PORT` | `6379` | Yes | Redis port |
| `REDIS_PASSWORD` | (empty) | ⚠️ **SET** | Redis password |
| `JWT_SECRET` | Dev key | ⚠️ **CHANGE** | JWT signing key (256-bit min) |
| `SPRING_PROFILE` | `dev` | `prod` | Active Spring profile |
| `CORS_ORIGINS` | `localhost:3000,5173` | ⚠️ **UPDATE** | Allowed frontend origins |
| `RATE_LIMIT_GUEST_RPM` | `20` | Adjust | Guest requests/minute |
| `RATE_LIMIT_REGISTERED_RPM` | `100` | Adjust | User requests/minute |
| `SERVER_PORT` | `8080` | `8080` | Application port |

## ⚠️ Production Checklist

Before deploying to production:

- [ ] Copy `.env.example` to `.env`
- [ ] Generate new JWT secret (256-bit minimum)
- [ ] Set strong database password
- [ ] Set Redis password
- [ ] Update CORS_ORIGINS to production domains
- [ ] Set SPRING_PROFILE to `prod`
- [ ] Verify all sensitive values are NOT hardcoded
- [ ] Confirm `.env` is in `.gitignore`
- [ ] Never commit `.env` to version control
- [ ] Store production secrets in secure vault (AWS Secrets Manager, etc.)

## 🔄 Environment-Specific Files

You can create multiple environment files:

```bash
.env.dev          # Development (local)
.env.staging      # Staging server
.env.prod         # Production server
```

Load specific environment:
```bash
# Development
cp .env.dev .env
docker-compose up -d

# Production
cp .env.prod .env
docker-compose up -d
```

## 🚨 Security Best Practices

### ✅ DO
- Use `.env.example` as a template
- Generate unique secrets for each environment
- Store production secrets in a secure vault
- Rotate secrets regularly (every 90 days)
- Use strong, random passwords (24+ characters)
- Restrict CORS to specific domains
- Review `.gitignore` regularly

### ❌ DON'T
- Never commit `.env` to Git
- Never hardcode secrets in code
- Never share production credentials via chat/email
- Never use development secrets in production
- Never use wildcard (`*`) in CORS_ORIGINS
- Never reuse secrets across environments

## 🆘 If Credentials are Compromised

1. **Immediate Actions:**
   ```bash
   # Generate new JWT secret
   NEW_JWT=$(openssl rand -base64 64)

   # Update .env
   sed -i "s/^JWT_SECRET=.*/JWT_SECRET=$NEW_JWT/" .env

   # Restart services
   docker-compose restart backend
   ```

2. **Database Password Rotation:**
   ```bash
   # 1. Generate new password
   NEW_DB_PASS=$(openssl rand -base64 32)

   # 2. Update PostgreSQL
   docker exec -it bro-barber-postgres psql -U postgres -c "ALTER USER postgres WITH PASSWORD '$NEW_DB_PASS';"

   # 3. Update .env
   sed -i "s/^DB_PASSWORD=.*/DB_PASSWORD=$NEW_DB_PASS/" .env

   # 4. Restart backend
   docker-compose restart backend
   ```

3. **Revoke All Tokens:**
   ```sql
   -- Revoke all active JWT tokens
   INSERT INTO revoked_tokens (token, expires_at, revoked_at)
   SELECT token, expires_at, NOW() FROM active_tokens;
   ```

## 📞 Support

For security concerns or questions:
- Review: [SECURITY.md](SECURITY.md)
- Contact: Security Team

---

**Remember:** The `.env` file is the ONLY place for sensitive configuration. Never hardcode secrets in code!
