Create Or Replace Package nullity_check Is

  Procedure check_out_null_list(p_list_object Out sample_type_one_list);

  Procedure check_out_empty_list(p_list_object Out sample_type_one_list);

  Procedure check_out_null_object(p_sample_object Out sample_type_one);

  Procedure check_out_empty_object(p_sample_object Out sample_type_one);

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

  Procedure check_out_null_object(p_sample_object Out sample_type_one) Is
  Begin
    p_sample_object := Null;
  End check_out_null_object;

  Procedure check_out_empty_object(p_sample_object Out sample_type_one) Is
  Begin
    p_sample_object := sample_type_one(attr_varchar  => Null,
                                       attr_clob     => Null,
                                       attr_int      => Null,
                                       attr_bigdec_1 => Null,
                                       attr_bigdec_2 => Null,
                                       date_a        => Null,
                                       timest_b      => Null,
                                       timest_c      => Null,
                                       raw_col       => Null);
  End check_out_empty_object;

End nullity_check;
/
