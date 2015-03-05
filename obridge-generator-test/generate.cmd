cd ../obridge-main

call mvn clean package assembly:single -DskipTests

cd ../obridge-generator-test

java -jar ../obridge-main/target/obridge.jar -c obridge.xml
