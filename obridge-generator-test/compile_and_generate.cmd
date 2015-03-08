cd ../obridge-main

call mvn package assembly:single -DskipTests

cd ../obridge-generator-test

java -jar ../obridge-main/target/obridge.jar -c obridge.xml

pause
