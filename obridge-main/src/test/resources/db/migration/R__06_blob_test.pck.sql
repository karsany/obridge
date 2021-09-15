Create Or Replace Package blob_test Is

  Procedure testcase1(blb    In Blob,
                      tp_blb Out tp_blob_test);

  Procedure testcase2(tp_blb In tp_blob_test,
                      siz    Out Number,
                      blb    Out Blob);

End blob_test;
/
Create Or Replace Package Body blob_test Is

  Procedure testcase1(blb    In Blob,
                      tp_blb Out tp_blob_test) Is
  
    v_tp_blb tp_blob_test;
  
    c Blob;
  
  Begin
  
    dbms_lob.createtemporary(c, True);
    dbms_lob.append(c, blb);
    dbms_lob.append(c, utl_raw.cast_to_raw('helo'));
  
    v_tp_blb := tp_blob_test(this_is_a_blob => c, blob_size => dbms_lob.getlength(c));
  
    tp_blb := v_tp_blb;
  
  End testcase1;

  Procedure testcase2(tp_blb In tp_blob_test,
                      siz    Out Number,
                      blb    Out Blob) Is
  
    c Blob;
  
  Begin
    dbms_lob.createtemporary(c, True);
    dbms_lob.append(c, tp_blb.this_is_a_blob);
    dbms_lob.append(c, utl_raw.cast_to_raw('helo'));
  
    blb := c;
    siz := dbms_lob.getlength(c);
  
  End testcase2;

End blob_test;
/
