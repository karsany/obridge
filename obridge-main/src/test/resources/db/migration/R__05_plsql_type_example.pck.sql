Create Or Replace Package plsql_type_example Is

  Type a Is Record(
    txt   Varchar2(100),
    num   Number(10),
    fract Number,
    dt    Date);

  Procedure negative_testcase_dont_gen(p_all_tbl In Out all_tables%Rowtype);

  Procedure test1(p_tst In a);

End plsql_type_example;
/
Create Or Replace Package Body plsql_type_example Is

  Procedure negative_testcase_dont_gen(p_all_tbl In Out all_tables%Rowtype) Is
  Begin
    Null;
  End negative_testcase_dont_gen;

  Procedure test1(p_tst In a) Is
  Begin
    Null;
  End test1;

End plsql_type_example;
/
