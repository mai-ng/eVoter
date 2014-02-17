@echo off


rem Enter the director installing mysql server
rem for example : C:\Program Files\MySQL\MySQL Server 5.6
echo Enter the director installing mysql server
echo For example : C:\Program Files\MySQL\MySQL Server 5.6
set /p MYSQL_HOME= Please enter MYSQL install directory?

rem Enter host name where database will be installed and run
echo Enter host name where database will be installed and run
echo For example : localhost
set /p HOST_NAME= Please enter host name?

rem Enter database user name that accesses database 
echo Enter database user name that accesses database
set /p USER= Please database username?

pause

echo "Creating database for running jnuit test....."
"%MYSQL_HOME%\bin\mysql" -h %HOST_NAME% -u %USER% -p < %~dp0db\create_tables_test.sql

echo "Creating database for running application....."

"%MYSQL_HOME%\bin\mysql" -h %HOST_NAME% -u %USER% -p < %~dp0db\create_tables.sql

pause