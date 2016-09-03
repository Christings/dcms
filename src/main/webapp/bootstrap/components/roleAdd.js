function roleAdd(){
	// var name = document.getElementById("userName").value;
	// var rank = document.getElementById("userRank").value;
	// var level = document.getElementById("userLevel").value;
	// // var url = document.getElementById("userName").value;
	// var parentId = document.getElementById("userParentId").value;
	// var iconId = document.getElementById("userIcon").value;
	// var type = document.getElementById("userType").value;
	console.log("roleAdd");
	$("#roleAddForm").submit(function(){
		var roleCode = $("#roleCode").val();
		var roleName = $("#roleName").val();
		console.log("roleAdd1");
		if(roleCode == "" || roleName == "")
		{
			//$("#alert").text("请输入登陆名称");
			return false;
		}

		var roleInfo = { rolecode:'', rolename:''};
		roleInfo['rolecode'] = roleCode;
		roleInfo['rolename'] = roleName;
		$.ajax({
			type:"post",
			url:"role/add",
			dataType: 'json',
			data: roleInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("添加用户"+roleName+"成功");
					alert("添加用户"+roleName+"成功");
				}else{
					console.log("添加用户"+roleName+"失败"+res.msg);
					alert("添加用户"+roleName+"失败");
				}
			}
		});
	});
}