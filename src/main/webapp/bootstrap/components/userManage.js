function userLoad(){
	var num = 1;
	var size = 10;
	var getUserData = {pageNum: num,pageSize: size};
	$.ajax({
		url:"user/getDataGrid",
		dataType:"json",
		data: getUserData,
		type: "post"
	}).done((jsonData)=>{
		var count = jsonData["data"]["count"];
		var userInfo = jsonData["data"]["records"];
		console.log(jsonData);
		console.log("userGridLoad"+count);
		console.log("userLoad"+userInfo);
	});
}

function userAdd(){
	var userName = "test";
	var realName = "test";
	var password = "test";
	var identificationNo = "2016_8_8";
	var mobile = "2016_8_8";
	var sex = "1";
	var enabled = "1";
	var remark = "test";
	var superAdmin = "1";
	// if (userName == "") {
	// 	$("#alert_1").text("请输入用户名");
	// 	return false;
	// };
	// if (realName == ""){
	// 	$("#alert_2").text("请输入真实姓名");
	// 	return false;
	// }
	// if (password == ""){
	// 	$("#alert_3").text("请输入用户密码");
	// 	return false;
	// }
	var userInfo = {userName: '', realName: '', password: '',identificationNo:'',mobile:'',sex:'',enabled: '',remark:'',superAdmin:''};// 
	userInfo['userName'] = userName;
	userInfo['realName'] = realName;
	userInfo['password'] = password;
	userInfo['identificationNo'] = identificationNo;
	userInfo['mobile'] = mobile;
	userInfo['sex'] = sex;
	userInfo['enabled'] = enabled;
	userInfo['remark'] = remark;
	userInfo['superAdmin'] = superAdmin;
	// console.log("userUpdate"+userInfo);
	$.ajax({
		type:"post",
		url: "user/addUser",
		dataType: 'json',
		// data: userInfo,
		contentType: "application/json",
		data: JSON.stringify(userInfo),
		success: function(res){
			if(res.status == "1"){
				console.log("添加用户成功" + res.data);
			}else{
				console.log("添加用户失败" + res.msg);
			}
		}
	});
}

userLoad();
userAdd();

function userUpdate(){

}