Create Or Replace Package blob_test Is

  Procedure testcase1(blb    In Blob,
                      tp_blb Out tp_blob_test);

End blob_test;
/
Create Or Replace Package Body blob_test Is

  Procedure testcase1(blb    In Blob,
                      tp_blb Out tp_blob_test) Is
  
    v_tp_blb tp_blob_test;
  
    c Blob;
  
  Begin
    dbms_lob.createtemporary(c,
                             True);
    dbms_lob.append(c,
                    blb);
    dbms_lob.append(c,
                    utl_raw.cast_to_raw('helo'));
  
    v_tp_blb := tp_blob_test(this_is_a_blob => c,
                             blob_size      => dbms_lob.getlength(c));
  
    tp_blb := v_tp_blb;
  
  End testcase1;

End blob_test;
/
