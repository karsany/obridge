Select *
From (Select Distinct d.argument_name       attr_name,
                      d.data_type           attr_type_name,
                      d.position            attr_no,
                      nvl(d.data_scale, -1) data_scale,
                      0                     multi_type,
                      bb.typecode           typecode,
                      Null                  collection_base_type
      From (Select t.*
            From all_arguments t
            Where t.type_name || '_' || t.type_subname = ? AND t.owner = ? And rownum < 2) m
               Left Join (Select * From all_arguments t Where data_level = 1 and t.owner = ?) d
                         On m.object_name = d.object_name And m.package_name = d.package_name And
                            nvl(m.overload, -1) = nvl(d.overload, -1)
               Left Join all_types bb On d.data_type = bb.type_name)
where bb.owner = ?
Order By attr_no
