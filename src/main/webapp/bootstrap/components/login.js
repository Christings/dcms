$(document).ready(function(){
	$("#form").submit(function(){
		login();
		return false;
	});
});

function login(){
	var username=$("#username").val();
	var password=$("#password").val();
	var userInformation = { username: '', password: ''};
	if(username == ""){
		$("#uconfirm").text("请输入登录用户名");
		$("#username").focus();
		return false;
	}
	if(password == ""){
		$("#pconfirm").text("请输入登录密码");
		$("#password").focus();
		return false;
	}
	userInformation['username'] = username;
	userInformation['password'] = password;
	// location.href = "index.html";
	$.ajax({
		type:"post",
		url:"main/login",
		dataType: 'json',
		data: userInformation,
		beforeSend:function(){
			$("#uconfirm").text("");
			$("#pconfirm").text("");
			$("#loading").text("登录中，请稍后");
		},
		success:function(res){
			if(res.status == "1"){
				location.href = "index.html?"+username;
			}
			else{
				$("#uconfirm").text("用户名或密码错误");
			}
		}
	});
}