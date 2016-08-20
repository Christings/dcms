loadUserBody();
function userGetAll(){
	console.log("userGetAll");
	$.ajax({
		url:"user/getAll",
		dataType:"json",
		data: '',
		type: "post"
	}).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		// var userInfo = jsonData["data"]["records"];
		console.log(jsonData);
		// console.log("userGridLoad"+count);
		// console.log("userLoad"+userInfo);
	});
}

function loadUserBody(){
	var pageNum = 1;
	var pageSize = 10;
	var username = "";
	var realname = "";
	var html_content = "";
	var content = "";
	console.log("userLoad");
	var getUserData = {pageNum: pageNum,pageSize: pageSize,username: username,realname:realname};
	$.ajax({
		url:"user/datagrid",
		dataType:"json",
		data: getUserData,
		type: "post"
	}).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		var userInfo = jsonData["data"]["records"];
	    user_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		for(var e in userInfo){
			content = "<tr>"+
				"<td>"+num+"</td>"+
				"<td>"+e["username"]+"</td>"+
				"<td>"+e["realname"]+"</td>"+
				"<td>"+e["sex"]+"</td>"+
				"<td>"+e["identificationno"]+"</td>"+
				"<td>"+e["phone"]+"</td>"+
				"<td>"+e["email"]+"</td>"+
				"<td>"+e["mobile"]+"</td>"+
				"<td>"+e["status"]+"</td>"+
				"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
				"</td>"+
			"<tr>";
			html_content += content;
			num++;
		}
		// console.log(jsonData);
		// console.log("userGridLoad"+count);
		var ulContent =" <ul class=\"pagination\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
		var pagination = function(){
			for(var i=1;i<=pageCount;i++){
				ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
			}
		}
		pagination();
		ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
		var index1 = document.getElementById("userBody");
		index1.innerHTML = html_content;
		var index2 = document.getElementById("userPagination");
		index2.innerHTML = ulContent;
		console.log("userLoad"+userInfo);
	});
}

function userAdd(){
	console.log("userAdd");
	var userName = "test_ding";
	var realName = "test_ding";
	var password = "test_ding";
	var identificationNo = "2016_8_8";
	var mobile = "2016_8_8";
	var phone = "2016_8_8";
	var email = "2016_8_8";
	var sex = "1";
	var enabled = "1";
	var remark = "test";
	// var superAdmin = "1";
	var status = "0";
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
	var userInfo = {username: '', realname: '', password: '',identificationno:'',phone:'',email:'',mobile:'',sex:'',status: '',remark:''};// 
	userInfo['username'] = userName;
	userInfo['realname'] = realName;
	userInfo['password'] = password;
	userInfo['identificationno'] = identificationNo;
	userInfo['phone'] = phone;
	userInfo['email'] = email;
	userInfo['mobile'] = mobile;
	userInfo['sex'] = sex;
	userInfo['status'] = status;
	userInfo['remark'] = remark;
	// userInfo['superAdmin'] = superAdmin;
	// console.log("userUpdate"+userInfo);
	$.ajax({
		type:"post",
		url: "user/add",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: userInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("添加用户成功" + res.data);
			}else{
				console.log("添加用户失败" + res.msg);
			}
		}
	});
}

// userGetAll();
// userLoad();
// userAdd();
// userUpdate();
// userDelete();
// userModifyPassword();
function userUpdate(){
	var id = "d459018a5f33417581187de4b511d0c7";
	var userName = "update";
	var realName = "update";
	var password = "update";
	var identificationNo = "update";
	var mobile = "update";
	var phone = "update";
	var email = "update";
	var sex = "1";
	var enabled = "1";
	var remark = "test";
	// var superAdmin = "1";
	var status = "0";
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
	var userInfo = {id:'',username: '', realname: '', password: '',identificationno:'',phone:'',email:'',mobile:'',sex:'',status: '',remark:''};// 
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
	userInfo['remark'] = remark;
	// userInfo['superAdmin'] = superAdmin;
	// console.log("userUpdate"+userInfo);
	$.ajax({
		type:"post",
		url: "user/update",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: userInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("修改用户成功" + res.data);
			}else{
				console.log("修改用户失败" + res.msg);
			}
		}
	});
}

function userDelete(){
	var id = "06d4c790a08a494a9d31fedf504a3aa1";
	
	var userInfo = {id:''};// 
	userInfo['id'] = id;
	$.ajax({
		type:"post",
		url: "user/delete",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: userInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("删除用户成功" + res.data);
			}else{
				console.log("删除用户失败" + res.msg);
			}
		}
	});
}

function userModifyPassword(){
	var id = "06d4c790a08a494a9d31fedf504a3aa1";
	var password = "modifyPassword";
	var userInfo = {id:'',password:''};// 
	userInfo['id'] = id;
	userInfo['password'] = password;
	$.ajax({
		type:"post",
		url: "user/modifyPassword",
		dataType: 'json',
		// data: userInfo,
		// contentType: "application/json",
		data: userInfo,
		success: function(res){
			if(res.status == "1"){
				console.log("更改用户密码成功" + res.data);
			}else{
				console.log("更改用户密码失败" + res.msg);
			}
		}
	});
}