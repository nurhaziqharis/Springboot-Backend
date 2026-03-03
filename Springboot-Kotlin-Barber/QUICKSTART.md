# Quick Start Guide

Get the Bro Barber backend running in 5 minutes! 🚀

## Step 1: Environment Setup

```bash
# Navigate to backend directory
cd Springboot-Kotlin-Barber

# Copy environment template
cp .env.example .env
```

The default `.env` values are ready for local development!

## Step 2: Start the Stack

```bash
# Start PostgreSQL + Redis + Backend
docker-compose up -d

# This will:
# ✓ Start PostgreSQL on port 5432
# ✓ Start Redis on port 6379
# ✓ Build and start the Spring Boot backend on port 8080
```

## Step 3: Verify it's Running

```bash
# Check all services are healthy
docker-compose ps

# Should show:
# bro-barber-postgres   healthy
# bro-barber-redis      healthy
# bro-barber-backend    healthy
```

## Step 4: Test the Health Endpoint

```bash
# Check backend health
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP"}
```

## Step 5: View Logs

```bash
# Follow backend logs
docker-compose logs -f backend

# View all service logs
docker-compose logs -f
```

## Common Commands

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (fresh start)
docker-compose down -v

# Rebuild backend after code changes
docker-compose up -d --build backend

# Access PostgreSQL
docker exec -it bro-barber-postgres psql -U postgres -d barber

# Access Redis CLI
docker exec -it bro-barber-redis redis-cli
```

## Running Without Docker (Alternative)

If you prefer to run locally without Docker:

```bash
# 1. Start dependencies only
docker-compose up postgres redis -d

# 2. Run backend with Gradle
./gradlew bootRun

# Backend will start on http://localhost:8080
```

## Next Steps

1. **API Documentation:** Once implemented, visit `http://localhost:8080/swagger-ui.html`
2. **Database:** PostgreSQL accessible at `localhost:5432` (user: `postgres`, pass: `1`)
3. **Redis:** Redis accessible at `localhost:6379`

## Troubleshooting

### Port Already in Use

```bash
# Check what's using port 8080
lsof -ti:8080

# Kill the process
kill -9 $(lsof -ti:8080)
```

### Backend Fails to Start

```bash
# Check backend logs
docker-compose logs backend

# Common issues:
# - Database not ready: Wait for postgres healthcheck
# - Port conflict: Change SERVER_PORT in .env
# - Build error: Run docker-compose build --no-cache backend
```

### Database Connection Error

```bash
# Verify PostgreSQL is running
docker-compose ps postgres

# Check PostgreSQL logs
docker-compose logs postgres

# Restart PostgreSQL
docker-compose restart postgres
```

### Clean Slate

```bash
# Nuclear option: Remove everything and start fresh
docker-compose down -v
docker system prune -a
docker-compose up -d --build
```

## Development Workflow

```bash
# 1. Make code changes
# 2. Rebuild backend
docker-compose up -d --build backend

# 3. View logs
docker-compose logs -f backend

# 4. Test your changes
curl http://localhost:8080/api/your-endpoint
```

---

That's it! You're ready to develop. 🎉

For more details, see the [full README](README.md).
