# Local Testing Script for Task Manager
Write-Host "🧪 Testing Task Manager API Locally..." -ForegroundColor Green

# Test if application is running
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/tasks" -Method GET
    Write-Host "✅ Tasks endpoint working - Found $($response.Count) tasks" -ForegroundColor Green
} catch {
    Write-Host "❌ Tasks endpoint failed: $($_.Exception.Message)" -ForegroundColor Red
}

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method GET
    Write-Host "✅ Users endpoint working - Found $($response.Count) users" -ForegroundColor Green
} catch {
    Write-Host "❌ Users endpoint failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test Swagger UI availability
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/swagger-ui.html" -Method GET
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Swagger UI is accessible" -ForegroundColor Green
    }
} catch {
    Write-Host "❌ Swagger UI failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n🌐 Access your application at:" -ForegroundColor Cyan
Write-Host "   • Main API: http://localhost:8080/api" -ForegroundColor White
Write-Host "   • Swagger UI: http://localhost:8080/api/swagger-ui.html" -ForegroundColor White
Write-Host "   • H2 Console: http://localhost:8080/h2-console" -ForegroundColor White
