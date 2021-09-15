Create Or Replace Function execfunction(pnumber In Out Number, pintext In Varchar2, pouttext Out Varchar2)
  Return Varchar2 Is
  res Varchar2(200);
  nr  Number := pnumber;
Begin
  pouttext := 'Out: ' || pouttext || ' In: ' || pintext;

  pnumber := pnumber + pnumber;

  Return 'IN-Param Nr: ' || nr;
End execfunction;
/
