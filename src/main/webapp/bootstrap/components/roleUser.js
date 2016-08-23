function roleUserInit(e){
	var id = e.getAttribute("data-value");
	var roleId = {id: ''};
	$.ajax({
		type:"post",
		url:"user/role/getRoleId",
		dataType:'json',
		data: roleId
	}).done((jsonData)=>{
		var roleInfo = jsonData["data"];
	});
}

function roleBatchUsers(){
	
}