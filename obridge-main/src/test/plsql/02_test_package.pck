Create Or Replace Package test_package Is

  Function get_sysdate Return Date;

  Procedure get_sysdate(p_sysdate Out Date);

  Procedure hello_world(p_name In Varchar2,
                        p_out  Out Varchar2);

End test_package;
/
Create Or Replace Package Body test_package Is

  Function get_sysdate Return Date Is
  Begin
    Return Sysdate;
  End get_sysdate;

  Procedure get_sysdate(p_sysdate Out Date) Is
  Begin
    p_sysdate := Sysdate;
  End get_sysdate;

  Procedure hello_world(p_name In Varchar2,
                        p_out  Out Varchar2) Is
  Begin
    p_out := 'Hello ' || p_name || '!';
  End hello_world;

End test_package;
/
