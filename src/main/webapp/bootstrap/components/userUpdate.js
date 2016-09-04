function userUpdate(){
	console.log(1);
		// $("#form").submit(function(){
			// var type = document.getElementById("menuType").value;
	$("#userUpdateForm").submit(function(){
		console.log(2);
		var id = $("#userId1").val();
		console.log(id);
		var userName = $("#userName1").val();
		console.log(userName);
		var realName = $("#realName1").val();
		console.log(realName);
		var password = $("#password1").val();
		console.log(password);
		var identificationNo = $("#identificationNo1").val();
		console.log(identificationNo);
		var phone = $("#phone1").val();
		console.log(phone);
		var email = $("#email1").val();
		console.log(email);
		var mobile = $("#mobile1").val();
		console.log(mobile);
		var sex = $("#sex1").val();
		console.log(sex);
		var status = $("#status1").val();
		console.log(status);
		if(realName == "")
		{
			$("#alertName").text("请输入真实名称");
			return false;
		}
		console.log(3);
		// console.log(name);
		var userInfo = {id:'',realname:'',password:'', identificationno:'', phone:'', email:'', mobile:'',sex:'',status:''};
		userInfo['id'] = id;
		userInfo['username'] = userName;
		userInfo['realname'] = realName;
		userInfo['password'] = password;
		userInfo['identificationno'] = identificationNo;
		userInfo['phone'] = phone;
		userInfo['email'] = email;
		userInfo['mobile'] = mobile;
		userInfo['sex'] = sex;
		userInfo['status'] = status;
		$.ajax({
			type:"post",
			url:"user/update",
			dataType: "json",
			data: userInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("更新用户"+userName+"成功");
					alert("更新用户"+userName+"成功");
				}else{
					console.log("更新用户"+userName+"失败"+res.msg);
					alert("更新用户"+userName+"失败");
				}
			}
		});
		return true;
		// });
	});
}

function userUpdateInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var userId = {id:''};
	userId["id"] = id;
	
	// console.log(menu);
	 console.log(id);
	// console.log(name);
	$.ajax({
		type:"post",
		url:"user/get",
		dataType: 'json',
		data: userId,
		}).done((jsonData)=>{
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
			var sex_selected_0;
			var sex_selected_1;
			var status_selected_0;
			var status_selected_1;
			switch(sex){
				case '0':
					sex_selected_0 = "selected";
					sex_selected_1 = "";
					break;
				case '1':
					sex_selected_0 = "";
					sex_selected_1 = "selected";
					break;
			}
			switch(status){
				case '0':
					status_selected_0 = "selected";
					status_selected_1 = "";
					break;
				case '1':
					status_selected_0 = "";
					status_selected_1 = "selected";
					break;
			}
			//console.log("parentId:"+menu["parentId"]);
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
						  "<label for=\"name\">用户密码</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"password1\" value=\""+password+"\">"+
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
								"<option selected=\""+sex_selected_0+"\"value = '0'>男</option>"+
								"<option selected=\""+sex_selected_1+"\"value = '1'>女</option>"+
							"</select>"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">状态</label>"+
						  "<select id=\"status1\">"+
								"<option selected=\""+status_selected_0+"\"value = '0'>未激活</option>"+
								"<option selected=\""+status_selected_1+"\"value = '1'>激活</option>"+
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
	});		// }
}



