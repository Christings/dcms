$(document).ready(function(){
	var user=DCMS.Busi.getUser();
	// if(user){
    //
	// }else{
		$("#loginBtn").click(login);
	// }
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
	layer.load(0);
	DCMS.Utils.Ajax("main/login",userInformation)
	.then(function(data){
		console.log(data);
		if(data.status == "1"){
			DCMS.Busi.setUser(data.data);
			return DCMS.Utils.Ajax("menu/tree");
		}else{
			$("#uconfirm").text("用户名或密码错误");
		}
	},function(error){
		//墨绿深蓝风
		layer.alert('登陆失败', {
			skin: 'layui-layer-molv' //样式类名
			,closeBtn: 0
		});
	})
	.then(function(data){
		console.log(data);
		if(data.status=='1'){
			var user=DCMS.Busi.getUser();
			user.userMenus=data.data;
			DCMS.Busi.setUser(user);
		}
		DCMS.Utils.gotoPage("./index_v0.html");
	},function(error){
		DCMS.Utils.gotoPage("./index_v0.html");
	});
}