Create Or Replace Package plsql_type_example Is

  Type a Is Record(
    txt   Varchar2(100),
    num   Number(10),
    fract Number,
    dt    Date);

  Procedure test1(p_tst In a);

End plsql_type_example;
/
Create Or Replace Package Body plsql_type_example Is

  Procedure test1(p_tst In a) Is
  Begin
      Null;
  End test1;

End plsql_type_example;
/
