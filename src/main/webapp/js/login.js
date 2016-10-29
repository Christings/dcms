$(document).ready(function(){
// 	var user=DCMS.Busi.getUser();
// 	// if(user){
// 	// }else{
// 		$("#loginBtn").click(login);
// 	// }
	document.onkeydown=function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			login();
		}
	}
});

function login(){
	var username=$("#username").val();
	var password=$("#password").val();
	var userInformation = { username: '', password: ''};
	console.log(userInformation);
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
	var inputCode = document.getElementById("input1").value;
	if (inputCode.length <= 0) {
		DCMSUtils.Modal.toast("请输入验证码！",'forbidden');
		return;
	}else if (inputCode != code2) {
		DCMSUtils.Modal.toast("验证码输入错误！",'forbidden');
		createCode(); //刷新验证码
		return;
	}

	userInformation['username'] = username;
	userInformation['password'] = password;
	$("#uconfirm").text("");
	$("#pconfirm").text("");
	DCMSUtils.Modal.showLoading("拼命登录中....");
	DCMSUtils.Ajax.doPost("main/login",userInformation)
	.then(function(data){
		console.log(data);
		DCMSUtils.Modal.hideLoading();
		if(data.status == "1"){
			console.log("login");
			DCMSBusi.USER.set(data.data);
			DCMSUtils.Modal.showLoading();
			return DCMSUtils.Ajax.doPost("menu/tree");
		}else{
			console.log("wrong");
			$("#uconfirm").text(data.msg);
			return;
		}
	},function(error){
		DCMSUtils.Modal.hideLoading();
		$("#uconfirm").text('登陆失败');
		return;
	}).then(function(data){
		console.log(data);
		if(data){
			if(data.status=='1'){
				var user=DCMSBusi.USER.get();
				user.userMenus=data.data;
				DCMSBusi.USER.set(user);
				DCMSUtils.NAV.gotoPage("./index.html");
			}else{
				DCMSUtils.Modal.hideLoading();
				$("#uconfirm").text('登陆失败');
			}
		}
	},function(error){
		DCMSUtils.Modal.hideLoading();
		$("#uconfirm").text('登陆失败');
	});
}