Select Distinct type_name || '_' || type_subname
From all_arguments t
Where type_subname Is Not Null
and owner = ?
