Select owner,
       object_name,
       procedure_name,
       overload,
       (Select Count(*)
          From all_arguments a
         Where owner = t.owner
           And a.object_name = t.procedure_name
           And a.package_name = t.object_name
           And nvl(a.overload, '##NVL##') = nvl(t.overload, '##NVL##')
           And a.argument_name Is Null
           And a.data_level = 0
           And a.data_type Is Not Null) proc_or_func
  From all_procedures t
 Where owner = ?
   And object_name = ?
   And procedure_name Is Not Null
   And object_type = 'PACKAGE'
   And Not ((owner, object_name, procedure_name, nvl(overload, -1)) In
        (Select owner,
                    package_name,
                    object_name,
                    nvl(overload, -1)
               From all_arguments
              Where data_type In ('PL/SQL TABLE')
                 Or (data_type = 'REF CURSOR' And in_out Like '%IN%')
                 Or (data_type = 'PL/SQL RECORD')))
