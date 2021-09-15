Create Or Replace Procedure test_procedure(p_name In Varchar2,
                                           p_out  Out Varchar2) Is
Begin
  p_out := 'Hello ' || p_name;
End;
/
