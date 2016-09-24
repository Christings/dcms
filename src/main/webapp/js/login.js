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
	var loader=layer.load(0);
	DCMSUtils.Ajax.doPost("main/login",userInformation)
	.then(function(data){
		console.log(data);
		layer.close(loader);
		if(data.status == "1"){
			console.log("login");
			DCMSBusi.USER.set(data.data);
			loader=layer.load(0);
			return DCMSUtils.Ajax.doPost("menu/tree");
		}else{
			console.log("wrong");
			$("#uconfirm").text(data.msg);
			//墨绿深蓝风
			layer.alert(data.msg, {
				skin: 'layui-layer-molv' //样式类名
				,closeBtn: 0
			});
			return;
		}
	},function(error){
		console.log("login1");
		layer.close(loader);
		//墨绿深蓝风
		layer.alert('登陆失败', {
			skin: 'layui-layer-molv' //样式类名
			,closeBtn: 0
		});
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
			//墨绿深蓝风
			layer.alert('登陆失败', {
				skin: 'layui-layer-molv' //样式类名
				,closeBtn: 0
			});
		}
	});
}