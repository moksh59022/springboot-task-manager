# Render Deployment Guide

## Prerequisites
1. Create a Render account at https://render.com
2. Connect your GitHub repository to Render

## Deployment Steps

### 1. Create PostgreSQL Database
1. Go to Render Dashboard
2. Click "New +" → "PostgreSQL"
3. Name: `task-management-postgres`
4. Database Name: `taskmanager_db`
5. User: `taskmanager_user`
6. Select "Free" plan
7. Click "Create Database"
8. **Important**: Copy the "External Database URL" from the database info page

### 2. Deploy Web Service
1. Go to Render Dashboard
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: `task-management-system`
   - **Environment**: `Docker` or `Native Environment`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -Dserver.port=$PORT -jar target/*.jar`
   - **Plan**: Free

### 3. Environment Variables
Add these environment variables in the Render web service settings:
- `SPRING_PROFILES_ACTIVE` = `prod`
- `DATABASE_URL` = [Paste the External Database URL from step 1]
- `JAVA_TOOL_OPTIONS` = `-XX:MaxRAMPercentage=75.0`

### 4. Access Your Application
- Main API: `https://your-app-name.onrender.com/api`
- Swagger UI: `https://your-app-name.onrender.com/api/swagger-ui.html`
- API Docs: `https://your-app-name.onrender.com/api/api-docs`

## Important Notes
- Free tier services may spin down after 15 minutes of inactivity
- First request after spin-down may take 30-60 seconds
- Database persists even when web service spins down
- Monitor logs in Render dashboard for any issues

## Troubleshooting
- Check logs in Render dashboard if deployment fails
- Ensure all environment variables are set correctly
- Verify database connection string format
- Make sure PostgreSQL service is running before web service starts
