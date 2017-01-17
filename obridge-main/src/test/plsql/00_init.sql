-- drop all objects
Begin
  For r1 In (Select 'DROP ' || object_type || ' ' || object_name ||
                    decode(object_type, 'TABLE', ' CASCADE CONSTRAINTS PURGE', 'TYPE', ' FORCE') As v_sql
               From user_objects
              Where object_type In ('TABLE', 'VIEW', 'PACKAGE', 'TYPE', 'PROCEDURE', 'FUNCTION', 'TRIGGER', 'SEQUENCE')
              Order By object_type,
                       object_name)
  Loop
    Execute Immediate r1.v_sql;
  End Loop;

  Execute Immediate 'PURGE RECYCLEBIN';
End;
/

-- create sample types
create or replace type SAMPLE_TYPE_ONE as object (
  attr_varchar varchar2(30),
  attr_clob    clob,
  attr_int     number(10), -- integer
  attr_bigdec_1 number,
  attr_bigdec_2 number(5,2),
  date_a        date,
  timest_b      timestamp,
  timest_c      timestamp(6),
  raw_col       raw(100)
)
/

create or replace type SAMPLE_TYPE_ONE_LIST as table of SAMPLE_TYPE_ONE
/

create or replace type SAMPLE_TYPE_TWO as object (
  field1  varchar2(30),
  field2  sample_type_one,
  field3  sample_type_one_list
);
/

create or replace type SAMPLE_TYPE_TWO_GROUP as table of SAMPLE_TYPE_TWO
/

create or replace type SAMPLE_TYPE_TWO_LIST as table of SAMPLE_TYPE_TWO
/

create or replace type SIMPLE_STRING_LIST AS table of VARCHAR2(100)
/

create or replace type simple_integer_list force as table of number(10)
/

create or replace type simple_number_list force as table of number
/

create or replace type simple_date_list force as table of date
/

create or replace type SAMPLE_TYPE_LISTS as object (
  list1  sample_type_one_list,
  list2  sample_type_two_group,
  list3  sample_type_two_list,
  list4  SIMPLE_STRING_LIST,
  list5  SIMPLE_NUMBER_LIST,
  list6  SIMPLE_INTEGER_LIST,
  list7  SIMPLE_DATE_LIST
);
/

create or replace type sample_type_three as object (
  field1  INTEGER,
  field2  VARCHAR2(30)
);
/

Create Or Replace Type tp_blob_test As Object (
  this_is_a_blob Blob,
  blob_size      Integer
);
/
