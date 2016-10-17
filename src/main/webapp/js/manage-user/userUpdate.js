function userUpdateInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var userId = {id:''};
	userId["id"] = id;
	
	// console.log(menu);
	 console.log(id);
	
	DCMSUtils.Ajax.doPost("user/get",userId).done((jsonData)=>{
		var userInfo = jsonData["data"];
		var userName = userInfo['username'];
		var realName = userInfo['realname'];
		var password = userInfo['password'];
		var identificationNo = userInfo['identificationno'];
		var phone = userInfo['phone'];
		var email = userInfo['email'];
		var mobile = userInfo['mobile'];
		var sex = userInfo['sex'];
		var status = userInfo['status'];
		var roleIds = userInfo['roleIds']?userInfo['roleIds']:[];
		console.log(roleIds);
		var sex_selected_0;
		var sex_selected_1;
		var status_selected_0;
		var status_selected_1;
		switch(sex){
			case 0:
				sex_selected_0 = "selected";
				sex_selected_1 = "";break;
			case 1:
				sex_selected_0 = "";
				sex_selected_1 = "selected";break;
		}
		console.log("sex:"+sex+"selected0:"+sex_selected_0+"selected1:"+sex_selected_1);
		switch(status){
			case 0:
				status_selected_0 = "selected";
				status_selected_1 = "";
				break;
			case 1:
				status_selected_0 = "";
				status_selected_1 = "selected";
				break;
		}var da = "";
		DCMSUtils.Ajax.doPost("role/getAll",da).done((jsonData)=>{
			var roles = jsonData["data"];
			var content = "";
			var e;
			for(var i=0,len=roles.length;i<len;i++){
				e = roles[i];
				var check = "";
				for(var j=0,len1=roleIds.length;j<len1;j++){
					if(roleIds[j] == e["id"]){
						check = "checked";
						break;
					}
				}
				content += "<input type=\"checkbox\" "+check+" value=\""+e["id"]+"\">"+e["rolename"];
			}
			var html = "<form role=\"form\" id=\"userUpdateForm\">"+
					"<div hidden=\"hidden\" class=\"form-group\">"+
					  "<input id=\"userId1\" value=\""+ id +"\"for=\"name\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">登陆账号</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"userName1\" value=\""+userName+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">用户名称</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"realName1\" value=\""+realName+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">用户角色</label>"+
					  "<div id=\"rolesContent1\">"+content+"</div>"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">身份证</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"identificationNo1\" value=\""+identificationNo+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">手机号</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"mobile1\" value=\""+mobile+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">邮箱</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"email1\" value=\""+email+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">电话</label>"+
					  "<input type=\"text\" class=\"form-control\" id=\"phone1\" value=\""+phone+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">性别</label>"+
					  	"<select id=\"sex1\">"+
							"<option "+sex_selected_0+" value = '0'>男</option>"+
							"<option "+sex_selected_1+" value = '1'>女</option>"+
						"</select>"+
					"</div>"+
					"<div class=\"form-group\">"+
					  "<label for=\"name\">状态</label>"+
					  "<select id=\"status1\">"+
							"<option "+status_selected_1+" value = '1'>未激活</option>"+
							"<option "+status_selected_0+" value = '0'>激活</option>"+
					  "</select>"+
					"</div>"+
					"<div class=\"modal-footer\">"+
		           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
		            "</button>"+
		            "<button type=\"submit\" onclick=\"userUpdate()\" class=\"btn btn-primary\">"+
		            "提交修改"+
		           "</button>"+
	        	"</div>"+
			"</form>";
			var body = document.getElementById("userUpdateBody");
			body.innerHTML = html;
		});
	});		// }
}

function userPasswordInit(e){
	var id = e.getAttribute("data-value");
	console.log("密码更新id："+id);
	var userId = {id:''};
	userId["id"] = id;
	DCMSUtils.Ajax.doPost("user/get",userId).done((jsonData)=>{
		var userInfo = jsonData["data"];
		var realName = userInfo['realname'];
		var html = '<form role="form" id="userEditPasswordForm">'+
			'<div hidden="hidden" class="form-group">'+
			  '<input  id="userId2" value="'+ id +'" for="name">'+
			'</div>'+
			'<label>用户：'+realName+'</label>'+
			'<div class="form-group">'+
		  		'<label for="name">输入新密码</label>'+
		  		'<input type="password" class="form-control" id="password1">'+
			'</div>'+
			'<div class="form-group">'+
		  		'<label for="name">再次输入新密码</label>'+
		  		'<input type="password" class="form-control" id="password2">'+
			'</div>'+
			'<div class="modal-footer">'+
	            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭'+
	            '</button>'+
	           ' <button type="submit" onclick="userEditPassword()" class="btn btn-primary">'+
	              ' 确认修改'+
	            '</button>'+    	
	        '</div>'+  
		'</form>';
		var index = document.getElementById('userEditPasswordBody');
		index.innerHTML = html;
	});
}

function userUpdate(){
	console.log(1);
		// $("#form").submit(function(){
			// var type = document.getElementById("menuType").value;
	$("#userUpdateForm").submit(function(){
		console.log(2);
		var roles = document.getElementsByTagName("input");
		var roleIds = "";
		for(var i=0,len=roles.length;i<len;i++){
			var role = roles[i];
			if(role.checked == true){
				console.log(role.value);
				roleIds += role.value + ",";
			}
		}
		var id = $("#userId1").val();
		var userName = $("#userName1").val();
		var realName = $("#realName1").val();
		var identificationNo = $("#identificationNo1").val();
		var phone = $("#phone1").val();
		var email = $("#email1").val();
		var mobile = $("#mobile1").val();
		var sex = $("#sex1").val();
		var status = $("#status1").val();
		if(realName == "")
		{
			$("#alertName").text("请输入真实姓名");
			return false;
		}
		var userInfo = {id:'',realname:'', identificationno:'', phone:'', email:'', mobile:'',sex:'',status:'',roleIds:''};
		userInfo['id'] = id;
		// userInfo['username'] = userName;
		userInfo['realname'] = realName;
		//userInfo['password'] = password;
		userInfo['identificationno'] = identificationNo;
		userInfo['phone'] = phone;
		userInfo['email'] = email;
		userInfo['mobile'] = mobile;
		userInfo['sex'] = sex;
		userInfo['status'] = status;
		userInfo['roleIds'] = roleIds;
		console.log('用户性别信息：'+userInfo['sex']);
		console.log('用户id信息：'+userInfo['id']);
		DCMSUtils.Ajax.doPost("user/update",userInfo).then(function (res){
			if(res.status === 1){
				console.log("更新用户"+userName+"成功");
				alert("更新用户"+userName+"成功");
			}else{
				console.log("更新用户"+userName+"失败"+res.msg);
				alert("更新用户"+userName+"失败");
			}
		});
		return true;
	});
}

function userEditPassword(){
	$("#userEditPasswordForm").submit(function(){
		var id = $("#userId2").val();
		console.log("userId2的value"+id);
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		if(password1 != password2){
			alert("两次输入的密码不一致！");
			return false;
		}
		var userInfo = {id:'',password:''};
		userInfo['id'] = id;
		userInfo['password'] = password1;
		DCMSUtils.Ajax.doPost("user/modifyPassword",userInfo).done((res)=>{
			if(res.status == "1"){
				console.log("更改用户"+userName+"的密码成功");
				alert("更改用户"+userName+"的密码成功");
				return true;
			}else{
				console.log("更改用户"+userName+"的密码失败"+res.msg);
				alert("更改用户"+userName+"的密码失败");
				return false;
			}
		});
	});
}


// function contains(obj,arr){
// 	for(var i=0,len=arr.length;i<len;i++){
// 		if(arr[i] === obj){
// 			return true;
// 		}
// 	}
// 	return false;
// }


