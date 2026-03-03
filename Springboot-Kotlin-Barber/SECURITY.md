# Security Guidelines

## Environment Variables (.env)

### Development Setup

1. **Initial Setup:**
   ```bash
   cp .env.example .env
   ```

2. **Development Values:**
   - Use the default values in `.env.example` for local development
   - Never use production credentials locally

### Production Deployment

#### 1. JWT Secret Generation

```bash
# Generate a secure 256-bit JWT secret
openssl rand -base64 64

# Alternative using Python
python3 -c "import secrets; print(secrets.token_urlsafe(64))"
```

**Requirements:**
- Minimum 256 bits (32 bytes)
- Use cryptographically secure random generator
- Rotate every 90 days
- Never reuse across environments

#### 2. Database Credentials

```bash
# Generate strong password
openssl rand -base64 32
```

**Best Practices:**
- Minimum 24 characters
- Mix of uppercase, lowercase, numbers, special chars
- Different password for each environment
- Store in secure vault (AWS Secrets Manager, HashiCorp Vault)

#### 3. Redis Password

```bash
# Generate Redis password
openssl rand -base64 24
```

#### 4. Environment-Specific Configuration

**Development (.env):**
```bash
SPRING_PROFILE=dev
JWT_SECRET=dev-only-secret-never-use-in-prod
DB_PASSWORD=1
REDIS_PASSWORD=
CORS_ORIGINS=http://localhost:3000,http://localhost:5173
```

**Production (.env or environment variables):**
```bash
SPRING_PROFILE=prod
JWT_SECRET=<256-bit-secure-random-from-vault>
DB_PASSWORD=<secure-password-from-vault>
REDIS_PASSWORD=<secure-password-from-vault>
CORS_ORIGINS=https://admin.brobarber.com,https://app.brobarber.com
```

## AWS EKS Deployment

### Using Kubernetes Secrets

Instead of `.env` file, use Kubernetes secrets:

```bash
# Create secret from .env file
kubectl create secret generic bro-barber-env \
  --from-env-file=.env.prod \
  --namespace=bro-barber

# Or create individual secrets
kubectl create secret generic db-credentials \
  --from-literal=password='<secure-password>' \
  --namespace=bro-barber

kubectl create secret generic jwt-secret \
  --from-literal=secret='<256-bit-secret>' \
  --namespace=bro-barber
```

### Using AWS Secrets Manager

```kotlin
// Example: Fetch secrets at runtime
@Configuration
class SecretsConfig {
    @Bean
    fun jwtSecret(): String {
        // Fetch from AWS Secrets Manager
        return awsSecretsManager.getSecretValue("bro-barber/jwt-secret")
    }
}
```

## Rate Limiting Configuration

Adjust based on your infrastructure:

```bash
# Conservative (small instance)
RATE_LIMIT_GUEST_RPM=10
RATE_LIMIT_REGISTERED_RPM=50

# Standard (recommended)
RATE_LIMIT_GUEST_RPM=20
RATE_LIMIT_REGISTERED_RPM=100

# Relaxed (high-capacity)
RATE_LIMIT_GUEST_RPM=60
RATE_LIMIT_REGISTERED_RPM=300
```

## CORS Configuration

### Development
```bash
CORS_ORIGINS=http://localhost:3000,http://localhost:5173
```

### Production
```bash
# Only allow specific domains
CORS_ORIGINS=https://admin.brobarber.com,https://app.brobarber.com,https://barber.brobarber.com
```

## Token Revocation

The system uses a `revoked_tokens` table for immediate JWT revocation.

**Automatic Cleanup:**
- Expired tokens are automatically purged
- Schedule: Daily cleanup job (implement in production)

**Manual Revocation:**
```sql
-- Revoke specific token
INSERT INTO revoked_tokens (token, expires_at, revoked_at)
VALUES ('jwt-token-string', '2024-12-31 23:59:59', NOW());

-- Clean expired tokens
DELETE FROM revoked_tokens WHERE expires_at < NOW();
```

## Security Checklist for Production

- [ ] Change all default passwords
- [ ] Generate new JWT secret (256-bit minimum)
- [ ] Enable Redis password authentication
- [ ] Configure proper CORS origins (no wildcards)
- [ ] Set rate limits appropriate for your traffic
- [ ] Use HTTPS only (no HTTP)
- [ ] Enable SSL for PostgreSQL connections
- [ ] Enable SSL for Redis connections
- [ ] Set up automatic secret rotation
- [ ] Configure firewall rules (allow only necessary ports)
- [ ] Enable audit logging
- [ ] Set up monitoring and alerts
- [ ] Regular security updates (dependencies)

## Incident Response

If credentials are compromised:

1. **Immediate Actions:**
   - Rotate affected credentials immediately
   - Revoke all active JWT tokens
   - Review audit logs for unauthorized access
   - Force re-authentication for all users

2. **JWT Secret Rotation:**
   ```bash
   # 1. Generate new secret
   NEW_SECRET=$(openssl rand -base64 64)

   # 2. Update environment variable
   # 3. Restart application
   # 4. All users will be logged out and must re-authenticate
   ```

3. **Database Password Rotation:**
   ```bash
   # 1. Create new password
   # 2. Update database user
   # 3. Update application config
   # 4. Rolling restart of pods
   ```

## Compliance

- **Audit Logs:** Retained for 2 years (MARA role access)
- **Password Policy:** Bcrypt with cost factor 10
- **Token Expiry:**
  - Access Token: 15 minutes
  - Refresh Token: 30 days
- **Rate Limiting:** Prevents brute force attacks
- **Soft Delete:** Data retention for compliance

---

For questions or security concerns, contact the security team.
