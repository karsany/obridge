docker run -d -p 1529:1521 -e ORACLE_PASSWORD=admin -e APP_USER=obridge -e APP_USER_PASSWORD=obridge gvenzl/oracle-xe
call mvn clean package install
