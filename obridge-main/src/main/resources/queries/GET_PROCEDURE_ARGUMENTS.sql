select argument_name,
       data_type,
       nvl(
               (select max(elem_type_name) from all_coll_types w where owner = ? and w.TYPE_NAME = p.type_name),
               p.type_name || case when p.type_subname is not null then '_' || p.type_subname end) type_name,
       defaulted,
       in_out,
       rownum                                                                                      sequen,
       p.type_name                                                                                 orig_type_name
from (Select argument_name, data_type, type_name, type_subname, defaulted, in_out
      From all_arguments t
      Where owner = ?
        and nvl(t.package_name, '###') = nvl((?), '###')
        And t.object_name = (?)
        And nvl(t.overload, '###') = nvl(?, '###')
        And t.data_level = 0
        And not (pls_type is null and argument_name is null and data_type is null)
      Order By t.sequence) p
