Create Or Replace Package simple_procedures Is

  Procedure a;
  Procedure overload;
  Procedure overload(a In Varchar2);
  Function simple_func(a In Varchar2,
                       b In Out Varchar2,
                       c Out Varchar2) Return Number;

  Procedure func_with_types(p_param_1     In sample_type_one,
                            p_param_hello In sample_type_one_list,
                            p_param_two   Out sample_type_two);

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

  Procedure proc_with_lists(p1 In Out sample_type_one_list,
                            p2 In Out sample_type_two_group,
                            p3 In Out sample_type_two_list);

End simple_procedures;
/
Create Or Replace Package Body simple_procedures Is

  Procedure a Is
  Begin
    Null;
  End;

  Procedure overload Is
  Begin
    Null;
  End;

  Procedure overload(a In Varchar2) Is
  Begin
    Null;
  End;

  Function simple_func(a In Varchar2,
                       b In Out Varchar2,
                       c Out Varchar2) Return Number Is
  Begin
    b := b || 'a';
    c := 'c' || b;
    Return 0;
  End;

  Procedure func_with_types(p_param_1     In sample_type_one,
                            p_param_hello In sample_type_one_list,
                            p_param_two   Out sample_type_two) Is
  Begin
    Null;
  End func_with_types;

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
  End;

  Procedure proc_with_lists(p1 In Out sample_type_one_list,
                            p2 In Out sample_type_two_group,
                            p3 In Out sample_type_two_list) Is
  Begin
    Null;
  End proc_with_lists;

End simple_procedures;
/
