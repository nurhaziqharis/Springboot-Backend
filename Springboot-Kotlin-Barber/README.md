# Bro Barber Backend

Premium Spring Boot Kotlin backend for the Bro Barber SaaS platform.

## 🏗️ Architecture

- **Framework**: Spring Boot 3.2.2 with Kotlin 1.9.22
- **Database**: PostgreSQL 16 with JPA/Hibernate
- **Caching**: Redis for session management and API caching
- **Security**: JWT-based authentication with refresh tokens
- **Deployment**: Docker + Kubernetes (AWS EKS ready)

## 📦 Project Structure

```
src/main/kotlin/com/brobarber/backend/
├── entity/                 # JPA Entities with soft delete & optimistic locking
│   ├── base/              # BaseEntity with audit fields
│   ├── User.kt
│   ├── Barbershop.kt
│   ├── Barber.kt
│   ├── Queue.kt
│   ├── Rating.kt
│   └── ...
├── repository/            # Spring Data JPA Repositories
├── security/              # JWT authentication & authorization
│   ├── JwtUtils.kt
│   ├── UserDetailsImpl.kt
│   ├── JwtAuthenticationFilter.kt
│   └── AuthEntryPoint.kt
├── config/                # Application configuration
│   ├── SecurityConfig.kt
│   ├── RateLimitFilter.kt
│   └── RedisConfig.kt
└── enums/                 # Business enums (Role, QueueStatus, etc.)
```

## 🚀 Getting Started

### Prerequisites

- JDK 17+
- Docker & Docker Compose
- Gradle 8.5+

### Environment Setup

1. **Copy the environment template:**
   ```bash
   cp .env.example .env
   ```

2. **Configure your `.env` file:**
   ```bash
   # Edit .env with your preferred editor
   nano .env
   ```

3. **Important: Update these values for production:**
   - `JWT_SECRET` - Generate a secure 256-bit random string
   - `DB_PASSWORD` - Use a strong database password
   - `REDIS_PASSWORD` - Set a Redis password
   - `CORS_ORIGINS` - Add your frontend domains

4. **Generate a secure JWT secret (example):**
   ```bash
   # Using openssl
   openssl rand -base64 64
   ```

### Running with Docker Compose

```bash
# Start PostgreSQL + Redis + Backend
docker-compose up -d

# View logs
docker-compose logs -f backend
```

### Running Locally

```bash
# Start dependencies only
docker-compose up postgres redis -d

# Run the application
./gradlew bootRun
```

## 🔑 Key Features Implemented

### ✅ Core Infrastructure
- [x] Gradle build configuration with Kotlin DSL
- [x] Multi-environment configuration (dev/prod)
- [x] Docker containerization with multi-stage build
- [x] Health check endpoints via Spring Actuator

### ✅ Database Layer
- [x] Comprehensive domain model (15+ entities)
- [x] Soft delete support via `BaseEntity`
- [x] Optimistic locking (@Version) for concurrency control
- [x] JPA audit fields (createdAt, updatedAt)
- [x] Composite indexes for performance (e.g., shop_id + status)
- [x] Spring Data JPA repositories with custom queries

### ✅ Security & Authentication
- [x] JWT-based authentication with access & refresh tokens
- [x] Bcrypt password hashing
- [x] Token revocation table for immediate logout
- [x] Rate limiting (Guest: 20 req/min, Registered: 100 req/min)
- [x] Role-based access control (CLIENT, BARBER, OWNER, CP_ADMIN, MARA)
- [x] CORS configuration
- [x] Spring Security filter chain

### ✅ Redis Integration
- [x] RedisTemplate configuration
- [x] Rate limiting via Redis counters
- [x] Cache configuration for API responses

## 🧪 Testing

```bash
# Run tests
./gradlew test

# Generate coverage report
./gradlew jacocoTestReport

# Verify 80% coverage threshold
./gradlew jacocoTestCoverageVerification
```

**Coverage Target**: 80% (enforced in CI/CD)

## 📊 Database Schema

The backend implements the complete schema from `requirement.md` including:

- **users** - Multi-role user management
- **barbershops** - Shop details with ratings
- **barbers** - Barber profiles and sessions
- **queues** - Queue management with optimistic locking
- **ratings** - 1-10 rating system with surveys
- **services** - Service catalog with dynamic pricing
- **revoked_tokens** - Token blacklist for security
- **audit_logs** - 2-year retention for compliance

## 🔄 What's Next

### Pending Implementation

1. **DTOs & Mappers** - Request/Response objects
2. **Auth Controller** - Registration, Login, Refresh endpoints
3. **Core Business Services**:
   - BarbershopService
   - QueueService (with real-time updates)
   - BarberService
   - RatingService
4. **WebSocket Configuration** - Real-time queue updates
5. **Swagger/OpenAPI Documentation** - For frontend integration
6. **Comprehensive Test Suite** - 80% coverage target

## 🤝 Integration with Frontend (Agent Gemini)

### API Base URL
- **Development**: `http://localhost:8080`
- **Production**: TBD (EKS endpoint)

### Authentication Flow
1. `POST /api/auth/register` - User registration
2. `POST /api/auth/login` - Login (returns access + refresh token)
3. Include `Authorization: Bearer <token>` in all authenticated requests
4. `POST /api/auth/refresh` - Refresh access token

### Swagger Documentation
Once implemented, available at: `http://localhost:8080/swagger-ui.html`

## 📝 Configuration

### Environment Variables

All sensitive and environment-specific values are stored in `.env` file:

| Variable | Description | Default (Dev) | Production |
|----------|-------------|---------------|------------|
| `DB_HOST` | PostgreSQL host | `postgres` | Your DB host |
| `DB_PORT` | PostgreSQL port | `5432` | `5432` |
| `DB_NAME` | Database name | `barber` | `barber` |
| `DB_USERNAME` | Database user | `postgres` | Secure username |
| `DB_PASSWORD` | Database password | `1` | **Strong password** |
| `REDIS_HOST` | Redis host | `redis` | Your Redis host |
| `REDIS_PORT` | Redis port | `6379` | `6379` |
| `REDIS_PASSWORD` | Redis password | (empty) | **Strong password** |
| `JWT_SECRET` | JWT signing key | Dev key | **256-bit secure random** |
| `SPRING_PROFILE` | Active profile | `dev` | `prod` |
| `CORS_ORIGINS` | Allowed origins | `localhost:3000,5173` | `https://yourdomain.com` |
| `RATE_LIMIT_GUEST_RPM` | Guest rate limit | `20` | Adjust as needed |
| `RATE_LIMIT_REGISTERED_RPM` | User rate limit | `100` | Adjust as needed |
| `SERVER_PORT` | Server port | `8080` | `8080` |

**Security Notes:**
- ⚠️ **Never commit `.env` to version control** (already in `.gitignore`)
- ✅ Use `.env.example` as a template
- ✅ Generate strong passwords and secrets for production
- ✅ Rotate JWT secrets periodically

## 🏷️ Version

**v1.0.0** - Initial Backend Foundation

---

Built with ❤️ by Agent Claude for the Bro Barber Platform
