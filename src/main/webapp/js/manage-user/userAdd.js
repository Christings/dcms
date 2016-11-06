function roleGetAll(){
	//初始化用户对应的域信息
	$("#domainPId").val('');
	$("#domainPName").text('');
	var da = "";
	// var content = "";
	// var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
	// for(var i=0,len=rolesMap.length;i<len;i++){
	// 	var e = rolesMap[i];
	// 	content += "<input type=\"checkbox\" value=\""+e["id"]+"\">"+e["rolename"];
	// }
	DCMSUtils.Ajax.doPost("role/getAll",da).done((jsonData)=>{
		var roles = jsonData["data"];
		var content = "";
		var e;
		var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
        if(!rolesMap){
            rolesMap={};
        }
		for(var i=0,len=roles.length;i<len;i++){
			e = roles[i];
			content += "<input type=\"checkbox\" value=\""+e["id"]+"\">"+e["rolename"];
		}
		var index = document.getElementById("rolesContent");
		index.innerHTML = content;
	});
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#userAddForm").validate({
    rules:{
        userName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        realName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        identificationNo:{
            required:false,
            number:true,
            minlength:18,
            maxlength:18
        },
        phone:{
            required:false,
            number:true,
            minlength:11,
            maxlength:11
        },
        email:{
            required:false,
            email:true,
            minlength:2,
            maxlength:50
        },
        mobile:{
            required:false,
            number:true,
            minlength:7,
            maxlength:11
        }
    },
    messages:{
        userName:icon + "请输入2-50个字符的登录账名",
        realName:icon + "请输入2-50个字符的用户姓名",
        identificationNo:icon + "请输入18个字符的身份证号",
        phone:icon + "请输入11位数字的手机号码",
        email:icon + "请输入正确的电子邮件格式",
        mobile:icon + "请输入7-11位数字的电话号码",
    },
    submitHandler:function(form){
        userAdd();
    }
});
function userAdd(){
	console.log("userAdd");
	// var roles = document.getElementsByTagName("input");
	// var roleIds = "";
	// for(var i=0,len=roles.length;i<len;i++){
	// 	var role = roles[i];
	// 	if(role.checked == true){
	// 		console.log(role.value);
	// 		roleIds += role.value + ",";
	// 	}
	// }
	var userName = $("#userName").val();
	var realName = $("#realName").val();
	var password = $("#password").val();
	var identificationNo = $("#identificationNo").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	var mobile = $("#mobile").val();
	var sex = $("#sex").val();
	var status = $("#status").val();
	var roleIds = $("#rolePId").val();
	var domainIds = $("#domainPId").val();
	if(userName == "")
	{
		$("#alert").text("请输入登陆名称");
		return false;
	}
	var userInfo = { username:'', realname:'',password:'', identificationno:'', phone:'', email:'', mobile:'',sex:'',status:'',roleIds:'',domainIds:''};
	userInfo['username'] = userName;
	userInfo['realname'] = realName;
	userInfo['password'] = password;
	userInfo['identificationno'] = identificationNo;
	userInfo['phone'] = phone;
	userInfo['email'] = email;
	userInfo['mobile'] = mobile;
	userInfo['sex'] = sex;
	userInfo['roleIds'] = roleIds;
	userInfo['status'] = status;
	userInfo['domainIds'] = domainIds;
	DCMSUtils.Ajax.doPost("user/add",userInfo).done((res)=>{
		if(res.status == "1"){
			DCMSUtils.Modal.toast("添加用户"+userName+"成功",'');
			dtApi.ajax.reload();
        	$("#useradd").modal('hide');
			return true;
		}else{
			DCMSUtils.Modal.toast("添加用户失败"+res.msg,'');
			return false;
		}
	});
}