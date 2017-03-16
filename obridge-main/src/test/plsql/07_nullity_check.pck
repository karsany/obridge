Create Or Replace Package nullity_check Is

  Procedure check_out_null_list(p_list_object Out sample_type_one_list);

  Procedure check_out_empty_list(p_list_object Out sample_type_one_list);

End nullity_check;
/
Create Or Replace Package Body nullity_check Is

  Procedure check_out_null_list(p_list_object Out sample_type_one_list) Is
  Begin
    p_list_object := Null;
  End check_out_null_list;

  Procedure check_out_empty_list(p_list_object Out sample_type_one_list) Is
  Begin
    p_list_object := sample_type_one_list();
  End check_out_empty_list;

End nullity_check;
/
