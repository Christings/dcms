// roleAdd();
// roleGetAll();
// batchUsers();
// roleUpdate();
// roleGet();
// roleDelete();
batchUsers();
batchRoles();
roleGetAll();
roleGet();

function roleAdd(){
	console.log("roleAdd");
	var rolecode = "test_role_3";
	var rolename = "test_role_3";
	var roleInfo = {rolecode: rolecode,rolename: rolename};
	$.ajax({
		type:"post",
		url: "role/add",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: roleInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("添加角色成功" + res.data["id"]+res.data["rolename"]);
			}else{
				console.log("添加角色失败" + res.msg);
			}
		}
	});
}
function roleUpdate(){
	console.log("roleUpdate");
	var id = "23721b903494367b513b7e41d91a1f8";
	var rolecode = "testUpdate";
	var rolename = "testUpdate";
	var roleInfo = {id: id,rolecode: rolecode,rolename: rolename};
	$.ajax({
		type:"post",
		url: "role/update",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: roleInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("修改角色成功" + res.data["rolename"]);
			}else{
				console.log("修改角色失败" + res.msg);
			}
		}
	});
}

function roleDelete(){
	console.log("roleDelete");
	var id = "23721b903494367b513b7e41d91a1f8";
	var roleInfo = {id: id};
	$.ajax({
		type:"post",
		url: "role/delete",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: roleInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("删除角色成功");
			}else{
				console.log("删除角色失败" + res.msg);
			}
		}
	});
}

function roleGet(){
	console.log("roleGet");
	var id = "23721b903494367b513b7e41d91a1f8";
	var roleInfo = {id: id};
	$.ajax({
		type:"post",
		url: "role/get",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: roleInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("获得角色信息成功" + res.data);
			}else{
				console.log("获得角色信息失败" + res.msg);
			}
		}
	});
}

function roleGetAll(){
	console.log("roleGetAll");
	
	var roleInfo = "";
	$.ajax({
		type:"post",
		url: "role/getAll",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: roleInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("获得所有角色信息成功" + res.data);
				console.log(res.data[0]);
				console.log(res.data[0]["id"] + res.data[0]["rolename"]);
			}else{
				console.log("获得所有角色信息失败" + res.msg);
			}
		}
	});
}

function roleDataGrid(){
	var num = 1;
	var size = 10;
	console.log("roleLoad");
	var getUserData = {pageNum: num,pageSize: size};
	$.ajax({
		url:"role/datagrid",
		dataType:"json",
		data: getUserData,
		type: "post"
	}).done((jsonData)=>{
		var count = jsonData["data"]["count"];
		var userInfo = jsonData["data"]["records"];
		console.log(jsonData);
		console.log("roleGridLoad"+count);
		console.log("roleLoad"+userInfo);
	});
}

function batchUsers(){
	var roleId = "fe5775aff4714aec8ba32cf8c6282844";
	var userIds = "d459018a5f33417581187de4b511d0c7,6ad6653632a04947b090e094db2ae347";
	console.log("batchUsers");
	var batchUserData = {roleId: roleId,userIds: userIds};
	$.ajax({
		url:"user/role/batchUsers",
		dataType:"json",
		data: batchUserData,
		type: "post"
	}).done((jsonData)=>{
		console.log(jsonData["msg"]);
	});
}

function batchRoles(){
	var userId = "d459018a5f33417581187de4b511d0c7";
	var roleIds = "fe5775aff4714aec8ba32cf8c6282844";
	console.log("batchRoles");
	var batchUserData = {roleIds: roleIds,userId: userId};
	$.ajax({
		url:"user/role/batchRoles",
		dataType:"json",
		data: batchUserData,
		type: "post"
	}).done((jsonData)=>{
		console.log(jsonData["msg"]);
	});
}

function getRoleId(){

}

function getUserId(){

}

// function 