# Railway Deployment Guide for Spring Boot Task Manager

## Issues Fixed

### 1. Java Version Compatibility
- **Problem**: Railway was using Java 21, but your project was configured for Java 17
- **Solution**: Created `nixpacks.toml` to explicitly specify Java 17

### 2. JAVA_HOME Configuration
- **Problem**: JAVA_HOME was not properly set during deployment
- **Solution**: Configured nixpacks to handle Java environment setup automatically

### 3. Maven Wrapper Permissions
- **Problem**: Maven wrapper (`./mvnw`) didn't have execute permissions
- **Solution**: Added `chmod +x ./mvnw` command in the build phase

### 4. Production Profile Configuration
- **Problem**: No production-specific configuration
- **Solution**: 
  - Added production profile in `pom.xml`
  - Created `application-prod.properties` for production settings
  - Configured build to use `-Pproduction` profile

## Files Created/Modified

### New Configuration Files:
1. **`nixpacks.toml`** - Railway-specific build configuration
2. **`railway.json`** - Railway deployment configuration
3. **`Procfile`** - Process definition for Railway
4. **`start.sh`** - Backup startup script
5. **`application-prod.properties`** - Production application settings

### Modified Files:
1. **`pom.xml`** - Added production profile

## Deployment Steps

### 1. Commit Changes
```bash
git add .
git commit -m "Fix Railway deployment configuration"
git push origin main
```

### 2. Railway Environment Variables (Optional)
If you need a database, add these environment variables in Railway:
- `DATABASE_URL` - Your database connection string
- `DATABASE_DRIVER` - Database driver class name
- `DATABASE_USERNAME` - Database username
- `DATABASE_PASSWORD` - Database password
- `DATABASE_DIALECT` - Hibernate dialect

### 3. Deploy
Railway will automatically detect the changes and redeploy your application.

## Configuration Details

### Build Process:
1. Uses Java 17 (OpenJDK)
2. Uses Maven 3.9.11
3. Runs `./mvnw clean package -DskipTests -Pproduction`
4. Creates executable JAR in `target/` directory

### Runtime Configuration:
- Server runs on port specified by `$PORT` environment variable
- Uses production Spring profile (`spring.profiles.active=prod`)
- Optimized JVM settings via `$JAVA_OPTS`
- H2 in-memory database by default (easily switchable to PostgreSQL)

### API Endpoints:
- Base URL: `https://your-app.railway.app/api`
- Swagger UI: `https://your-app.railway.app/api/swagger-ui.html`
- API Docs: `https://your-app.railway.app/api/api-docs`

## Troubleshooting

### If deployment still fails:
1. Check Railway logs for specific error messages
2. Ensure all files are committed and pushed to your repository
3. Verify that the Maven wrapper has proper line endings (LF, not CRLF)
4. Check that your Spring Boot application starts locally with the production profile

### Local Testing:
```bash
# Test with production profile
./mvnw clean package -DskipTests -Pproduction
java -Dspring.profiles.active=prod -jar target/task-management-system-0.0.1-SNAPSHOT.jar
```

## Next Steps After Successful Deployment:
1. Add a PostgreSQL database service in Railway
2. Configure environment variables for database connection
3. Set up monitoring and logging
4. Configure custom domain (if needed)
