// $(document).ready(function(){
// 	var user=DCMS.Busi.getUser();
// 	// if(user){
// 	// }else{
// 		$("#loginBtn").click(login);
// 	// }
// });
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
			DCMSUtils.Modal.alert(data.msg,'温馨提示');
			return;
		}
	},function(error){
		DCMSUtils.Modal.alert('登录失败','温馨提示');
		return;
	}).then(function(data){
		console.log(data);
		console.log("login2");
		if(data){
			if(data.status=='1'){
				var user=DCMSBusi.USER.get();
				user.userMenus=data.data;
				DCMSBusi.USER.set(user);
			}
			console.log("login3");
			DCMSUtils.NAV.gotoPage("./index.html");
		}
	},function(error){
		if(error){
			DCMSUtils.Modal.alert('登录失败','温馨提示');
		}
	});
}