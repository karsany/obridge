call database_connection.cmd

sqlplus %database_connection% @../obridge-main/src/test/plsql/00_init.sql
sqlplus %database_connection% @../obridge-main/src/test/plsql/01_simple_procedures.pck
sqlplus %database_connection% @../obridge-main/src/test/plsql/02_test_package.pck
