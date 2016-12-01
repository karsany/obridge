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

  Procedure refcursor_test(p_refc Out Sys_Refcursor);

  Procedure raw_test(p_r In Out Raw);

  Procedure raw_in_type_test(p_tt Out sample_type_one);

  Procedure test_type_with_integer_field(p_tp In Out sample_type_three);

  Procedure raise_error;

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

  Procedure refcursor_test(p_refc Out Sys_Refcursor) Is
  Begin
    Open p_refc For
      Select * From dual;
  End refcursor_test;

  Procedure test_type_with_integer_field(p_tp In Out sample_type_three) Is
  Begin
    If p_tp Is Null Then
      p_tp := sample_type_three(field1 => 0,
                                field2 => '');
    End If;
    p_tp.field1 := p_tp.field1 + 1;
    p_tp.field2 := p_tp.field2 || 'ABC';
  
  End test_type_with_integer_field;

  Procedure raise_error Is
  Begin
    raise_application_error(-20001,
                            'Test Raise');
  End raise_error;

  Procedure raw_test(p_r In Out Raw) Is
  Begin
    Null;
  End raw_test;

  Procedure raw_in_type_test(p_tt Out sample_type_one) Is
  Begin
    p_tt := sample_type_one(attr_varchar  => 'Hello!',
                            attr_clob     => Null,
                            attr_int      => 0,
                            attr_bigdec_1 => Null,
                            attr_bigdec_2 => Null,
                            date_a        => Sysdate,
                            timest_b      => Null,
                            timest_c      => Null,
                            raw_col       => utl_raw.cast_to_raw('hello'));
  End raw_in_type_test;

End simple_procedures;
/
