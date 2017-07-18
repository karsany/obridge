cd obridge-main
call mvn clean package assembly:single

pause

cd ..
cd obridge-maven-plugin
call mvn clean package install

pause

cd ..
cd obridge-generator-test
call mvn clean package

pause
