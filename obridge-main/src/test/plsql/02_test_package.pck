Create Or Replace Package test_package Is

  Function get_sysdate Return Date;

  Procedure get_sysdate(p_sysdate Out Date);

  Procedure hello_world(p_name In Varchar2,
                        p_out  Out Varchar2);

  Procedure all_types(n    In Out Number,
                      bi   In Out Binary_Integer,
                      pi   In Out Pls_Integer,
                      vch  In Out Varchar2,
                      nvch In Out Nvarchar2,
                      ch   In Out Char,
                      nch  In Out Nchar,
                      d    In Out Date,
                      ts   In Out Timestamp,
                      cl   In Out Clob,
                      b    In Out Boolean,
                      tbl  In Out sample_type_one_list, -- table of object
                      o    In Out sample_type_one -- object
                      );

End test_package;
/
Create Or Replace Package Body test_package Is

  Function get_sysdate Return Date Is
  Begin
    Return Sysdate;
  End get_sysdate;

  Procedure get_sysdate(p_sysdate Out Date) Is
  Begin
    p_sysdate := trunc(Sysdate);
  End get_sysdate;

  Procedure hello_world(p_name In Varchar2,
                        p_out  Out Varchar2) Is
  Begin
    p_out := 'Hello ' || p_name || '!';
  End hello_world;

  Procedure all_types(n    In Out Number,
                      bi   In Out Binary_Integer,
                      pi   In Out Pls_Integer,
                      vch  In Out Varchar2,
                      nvch In Out Nvarchar2,
                      ch   In Out Char,
                      nch  In Out Nchar,
                      d    In Out Date,
                      ts   In Out Timestamp,
                      cl   In Out Clob,
                      b    In Out Boolean,
                      tbl  In Out sample_type_one_list, -- table of object
                      o    In Out sample_type_one -- object
                      ) Is
  Begin
    Null;
  End all_types;

End test_package;
/
