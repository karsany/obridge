Select attr_name,
       aa.owner,
       attr_type_name,
       attr_no,
       nvl(nvl(scale, (Select scale From all_coll_types t Where t.type_name = aa.attr_type_name and t.owner=aa.owner)), -1) data_scale,
       Case
         When attr_type_owner Is Not Null Then
          1
         Else
          0
       End multi_type,
       bb.typecode,
       (Select elem_type_name From all_coll_types t Where t.type_name = aa.attr_type_name and t.owner=aa.owner) collection_base_type
  From all_type_attrs aa, all_types bb
 Where upper(aa.type_name) = ?
 and aa.owner = bb.owner(+) and aa.owner = ?
   And aa.attr_type_name = bb.type_name(+)
 Order By attr_no Asc
