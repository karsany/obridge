call database_connection.cmd

sqlplus %database_connection% @../obridge-main/src/test/plsql/00_init.sql
sqlplus %database_connection% @../obridge-main/src/test/plsql/01_simple_procedures.pck
sqlplus %database_connection% @../obridge-main/src/test/plsql/02_test_package.pck
sqlplus %database_connection% @../obridge-main/src/test/plsql/03_test_procedure.prc
sqlplus %database_connection% @../obridge-main/src/test/plsql/04_exec_function.fnc
sqlplus %database_connection% @../obridge-main/src/test/plsql/05_plsql_type_example.pck
sqlplus %database_connection% @../obridge-main/src/test/plsql/06_blob_test.pck
sqlplus %database_connection% @../obridge-main/src/test/plsql/07_nullity_check.pck
