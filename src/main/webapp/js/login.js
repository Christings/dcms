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
	DCMS.Utils.Ajax("main/login",userInformation)
	.then(function(data){
		console.log(data);
		if(data.status == "1"){
			console.log("login");
			DCMS.Busi.setUser(data.data);
			return DCMS.Utils.Ajax("menu/tree");
		}else{
			console.log("wrong");
			$("#uconfirm").text("用户名或密码错误");
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
		if(data.status=='1'){
			var user=DCMS.Busi.getUser();
			user.userMenus=data.data;
			DCMS.Busi.setUser(user);
		}
		console.log("login3");
		DCMS.Utils.gotoPage("./index.html");
	},function(error){
		console.log("login4");
		console.log("login1");
		if(error){
			//墨绿深蓝风
			layer.alert('登陆失败', {
				skin: 'layui-layer-molv' //样式类名
				,closeBtn: 0
			});
		}
	});
}