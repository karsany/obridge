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

End simple_procedures;
/
