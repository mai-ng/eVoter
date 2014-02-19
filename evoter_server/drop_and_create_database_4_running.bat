@echo off


rem Enter the director installing mysql server
rem for example : C:\Program Files\MySQL\MySQL Server 5.6
echo Enter the installed directorty of MySQL
echo For example : C:\Program Files\MySQL\MySQL Server 5.6
set /p MYSQL_HOME= MySQL Home=?

echo.
rem Enter host name where database will be installed and run
echo Enter the database host name
echo For example : localhost
set /p HOST_NAME= host name=?

echo.
rem Enter database user name that accesses database 
echo Enter user name to access the database
set /p USER= username=?

echo.
echo Creating a database to run the application.....

"%MYSQL_HOME%\bin\mysql" -h %HOST_NAME% -u %USER% -p < %~dp0db\create_tables.sql

pause