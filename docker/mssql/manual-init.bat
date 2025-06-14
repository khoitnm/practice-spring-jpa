:: Setting up ./init-sql in docker-compose file doesn't work, so I have to use bat file to trigger it.
@echo off
setlocal

:: Install python libary `pyodbc` if it doesn't exist yet.
pip show pyodbc >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Installing pyodbc...
    pip install pyodbc
    if %ERRORLEVEL% neq 0 (
        echo Failed to install pyodbc. Exiting.
        exit /b 1
    )
)

:: Run the Python script
python manual-init.py

echo manual-init.bat is finished.
endlocal