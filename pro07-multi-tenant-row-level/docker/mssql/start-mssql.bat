:: =================================================================
:: SUMMARY:
:: This script starts a Docker container for MS SQL Server using a specified Docker Compose file.
:: It waits for the SQL Server to initialize before running a manual initialization script (`manual-init.bat`).
:: =================================================================

@echo off
setlocal

:: Start the Docker container
docker-compose -f docker-compose-local-mssqlserver.yml up -d
if %ERRORLEVEL% neq 0 (
    echo Failed to start Docker container. Exiting.
    exit /b 1
)

:: Need to wait for SQL Server to finish starting, otherwise, the init-sql script will fail.
echo waiting for the SQL Server to finish starting in 10 seconds before running ./init-sql ...
timeout /t 10 /nobreak >nul

:: Run the manual-init.bat script
call manual-init.bat

echo start-mssql.bat is finished.
endlocal