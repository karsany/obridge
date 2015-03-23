cd ../obridge-main

call mvn clean package assembly:single

cd ../obridge-generator-test

java -jar ../obridge-main/target/obridge.jar -c obridge.xml

call mvn clean test

pause
