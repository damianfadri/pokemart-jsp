function increment(i, s) 
{ 
	var x = parseInt(document.getElementsByName('amount')[i].value);
	if ((x + 1) <= s)
		document.getElementsByName('amount')[i].value = (x+1);
}

function decrement(i) 
{ 
	var x = document.getElementsByName('amount')[i].value;
	if ((x - 1) >= 0)
		document.getElementsByName('amount')[i].value	= (x - 1);
}