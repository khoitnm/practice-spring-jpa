@echo off
setlocal

:: Start the Docker container
docker-compose -f docker-compose-local-mssqlserver.yml up -d
if %ERRORLEVEL% neq 0 (
    echo Failed to start Docker container. Exiting.
    exit /b 1
)

:: Wait for 15 seconds
echo waiting for the SQL Server to start in 10 seconds before running ./init-sql ...
timeout /t 10 /nobreak >nul

:: Run the manual-init.bat script
call manual-init.bat

echo start-mssql.bat is finished.
endlocal