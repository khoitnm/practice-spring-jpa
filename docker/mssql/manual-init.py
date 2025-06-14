import pyodbc

# Database connection details
db_server = 'localhost'
db_name = 'master'
db_user = 'sa'
db_password = 'Password1'
sql_file = './init-sql/create-login.sql'

# Read the SQL script
with open(sql_file, 'r') as file:
    sql_script = file.read()

# Connect to the database
connection_string = f"DRIVER={{ODBC Driver 17 for SQL Server}};SERVER={db_server};DATABASE={db_name};UID={db_user};PWD={db_password}"
try:
    with pyodbc.connect(connection_string) as connection:
        with connection.cursor() as cursor:
            # Execute the SQL script
            for statement in sql_script.split(';'):
                if statement.strip():
                    cursor.execute(statement)
            print("Script executed successfully.")
except Exception as e:
    print(f"Failed to execute the script: {e}")
