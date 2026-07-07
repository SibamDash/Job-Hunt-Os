@echo off
echo Starting Job Hunting OS...
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%CD%\apache-maven-3.9.8\bin;%PATH%
mvn clean compile javafx:run
pause
