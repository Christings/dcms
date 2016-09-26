function roleGetAll(){
	var da = "";
	DCMSUtils.Ajax.doPost("role/getAll",da).done((jsonData)=>{
		var roles = jsonData["data"];
		var content = "";
		var e;
		for(var i=0,len=roles.length;i<len;i++){
			e = roles[i];
			content += "<input type=\"checkbox\" value=\""+e["id"]+"\">"+e["rolename"];
		}
		var index = document.getElementById("rolesContent");
		index.innerHTML = content;
	});
}

function userAdd(){
	// var name = document.getElementById("userName").value;
	// var rank = document.getElementById("userRank").value;
	// var level = document.getElementById("userLevel").value;
	// // var url = document.getElementById("userName").value;
	// var parentId = document.getElementById("userParentId").value;
	// var iconId = document.getElementById("userIcon").value;
	// var type = document.getElementById("userType").value;
	console.log("userAdd");
	$("#userAddForm").submit(function(){
		var roles = document.getElementsByTagName("input");
		var roleIds = "";
		for(var i=0,len=roles.length;i<len;i++){
			var role = roles[i];
			if(role.checked == true){
				console.log(role.value);
				roleIds += role.value + ",";
			}
		}
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var password = $("#password").val();
		var identificationNo = $("#identificationNo").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		var mobile = $("#mobile").val();
		var sex = $("#sex").val();
		var status = $("#status").val();
		if(userName == "")
		{
			$("#alert").text("请输入登陆名称");
			return false;
		}

		var userInfo = { username:'', realname:'',password:'', identificationno:'', phone:'', email:'', mobile:'',sex:'',status:'',roleIds:''};
		userInfo['username'] = userName;
		userInfo['realname'] = realName;
		userInfo['password'] = password;
		userInfo['identificationno'] = identificationNo;
		userInfo['phone'] = phone;
		userInfo['email'] = email;
		userInfo['mobile'] = mobile;
		userInfo['sex'] = sex;
		userInfo['roleIds'] = roleIds;
		userInfo['status'] = status;
		DCMSUtils.Ajax.doPost("user/add",userInfo).done((res)=>{
			if(res.status == "1"){
					console.log("添加用户"+userName+"成功");
					alert("添加用户"+userName+"成功");
				}else{
					console.log("添加用户"+userName+"失败"+res.msg);
					alert("添加用户"+userName+"失败");
				}
		});
		// $.ajax({
		// 	type:"post",
		// 	url:"user/add",
		// 	dataType: 'json',
		// 	data: userInfo,
		// 	success:function(res){
		// 		if(res.status == "1"){
		// 			console.log("添加用户"+userName+"成功");
		// 			alert("添加用户"+userName+"成功");
		// 		}else{
		// 			console.log("添加用户"+userName+"失败"+res.msg);
		// 			alert("添加用户"+userName+"失败");
		// 		}
		// 	}
		// });
	});
}