@echo off

echo %M2_HOME%

echo build and install evoter_share project

CD ..

CD  %CD%\evoter_share

echo %CD%

call "%M2_HOME%"\bin\mvn clean install

CD ..

CD  %CD%\evoter_server

echo %CD%

call "%M2_HOME%"\bin\mvn clean install test exec:java

echo press any key to stop server

pause



