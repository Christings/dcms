function userUpdate(){
	console.log(1);
		// $("#form").submit(function(){
			// var type = document.getElementById("menuType").value;
	$("#userUpdateForm").submit(function(){
		console.log(2);
		var id = $("#userId").val();
		console.log(id);
		var userName = $("#userName").val();
		console.log(userName);
		var realName = $("#realName").val();
		console.log(realName);
		var password = $("#password").val();
		console.log(password);
		var identificationNo = $("#identificationNo").val();
		console.log(identificationNo);
		var phone = $("#phone").val();
		console.log(phone);
		var email = $("#email").val();
		console.log(email);
		var mobile = $("#mobile").val();
		console.log(mobile);
		var sex = $("#sex").val();
		console.log(sex);
		var status = $("#status").val();
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
			dataType: 'json',
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
			//console.log("parentId:"+menu["parentId"]);
			var html = "<form role=\"form\" id=\"userUpdateForm\">"+
						"<div class=\"form-group\">"+
						  "<label id=\"userId\" value=\""+ id +"\"for=\"name\"></label>"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">登陆账号</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"userName\" value=\""+userName+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">用户名称</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"realName\" value=\""+realName+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">用户密码</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"password\" value=\""+password+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">身份证</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"identificationNo\" value=\""+identificationNo+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">手机号</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"mobile\" value=\""+mobile+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">邮箱</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"email\" value=\""+email+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">电话</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"phone\" value=\""+phone+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">性别</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"sex\" value=\""+sex+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">状态</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"status\" value=\""+status+"\">"+
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



