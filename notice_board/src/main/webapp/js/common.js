function checkValue(obj, msg){
	var val = obj.value;
	if(val ==""){
		alert(msg);
		obj.focus();
		return true;
	}
	return false;
}
