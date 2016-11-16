function userUpdateInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var userId = {id:''};
	userId["id"] = id;
	loadDomainRole();
	DCMSBusi.Api.invoke("user/get",userId).done((jsonData)=>{
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
		var roles = userInfo['roles']?userInfo['roles']:[];
		var domains = userInfo['domains']?userInfo['domains']:[];
		var rolePIds = '';
		var rolePNames = '';
		var domainPIds = '';
		var domainPNames = '';
		for(var i=0,len=roles.length;i<len;i++){
			var pRole=roles[i];
			if(i == (len - 1)){
				rolePIds += pRole.id;
				rolePNames += pRole.rolename;
			}else{
				rolePIds += pRole.id + ',';
				rolePNames += pRole.rolename + ',';
			}
		}
		for(var i=0,len=domains.length;i<len;i++){
			var pDomain=domains[i];
			if(i == (len - 1)){
				domainPIds += pDomain.id;
				domainPNames += pDomain.name;
			}else{
				domainPIds += pDomain.id + ',';
				domainPNames += pDomain.name + ',';
			}
		}
		var sex_selected_0;
		var sex_selected_1;
		var status_selected_0;
		var status_selected_1;
		switch(sex){
			case 0:
				sex_selected_0 = "selected";
				sex_selected_1 = "";break;
			case 1:
				sex_selected_0 = "";
				sex_selected_1 = "selected";break;
		}
		console.log("sex:"+sex+"selected0:"+sex_selected_0+"selected1:"+sex_selected_1);
		switch(status){
			case 0:
				status_selected_0 = "selected";
				status_selected_1 = "";
				break;
			case 1:
				status_selected_0 = "";
				status_selected_1 = "selected";
				break;
		}
		var html = 
			"<div hidden=\"hidden\" class=\"form-group\">"+
			  "<input id=\"userId1\" value=\""+ id +"\"for=\"name\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">登陆账号</label>"+
			  "<input type=\"text\" class=\"form-control\" name='userName1' id=\"userName1\" value=\""+userName+"\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">用户名称</label>"+
			  "<input type=\"text\" class=\"form-control\" name='realName1' id=\"realName1\" value=\""+realName+"\">"+
			"</div>"+
			"<div class='form-group' >"+
				"<label class='col-sm-3'>用户角色</label>"+
				'<input id="rolePId1" name="rolePId1" value="'+rolePIds+'"  type="hidden">'+
				"<div class='col-sm-9'>"+
					'<span id="rolePName1" name="rolePName1" style="width: 50%;">'+rolePNames+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<a id="selectRoleBtn1" onclick="selectRoleBtn1()" class="btn btn-primary btn-sm btn-outline">选择</a>'+
				"</div>"+
			"</div>"+
			"<div class='form-group' >"+
				"<label class='col-sm-3'>组织机构：</label>"+
				'<input id="domainPId1" name="domainPId1" value="'+domainPIds+'" type="hidden">'+
				"<div class='col-sm-9'>"+
					'<span id="domainPName1" name="domainPName1" style="width: 50%;">'+domainPNames+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<a id="selectDomainBtn1" onclick="selectDomainBtn1()" class="btn btn-primary btn-sm btn-outline">选择</a>'+
				'</div>'+
			'</div>'+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">身份证</label>"+
			  "<input type=\"text\" class=\"form-control\" name='identificationNo1' id=\"identificationNo1\" value=\""+identificationNo+"\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">手机号</label>"+
			  "<input type=\"text\" class=\"form-control\" name='mobile1' id=\"mobile1\" value=\""+mobile+"\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">邮箱</label>"+
			  "<input type=\"text\" class=\"form-control\" name='email1' id=\"email1\" value=\""+email+"\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">电话</label>"+
			  "<input type=\"text\" class=\"form-control\" name='phone1' id=\"phone1\" value=\""+phone+"\">"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">性别</label>"+
			  	"<select id=\"sex1\">"+
					"<option "+sex_selected_0+" value = '0'>男</option>"+
					"<option "+sex_selected_1+" value = '1'>女</option>"+
				"</select>"+
			"</div>"+
			"<div class=\"form-group\">"+
			  "<label for=\"name\">状态</label>"+
			  "<select id=\"status1\">"+
					"<option "+status_selected_1+" value = '1'>未激活</option>"+
					"<option "+status_selected_0+" value = '0'>激活</option>"+
			  "</select>"+
			"</div>";
			
		var body = document.getElementById("userUpdateBody");
		body.innerHTML = html;
		
			
	});		// }
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#userUpdateForm").validate({
    rules:{
        userName1:{
            required:true,
            minlength:2,
            maxlength:50
        },
        realName1:{
            required:true,
            minlength:2,
            maxlength:50
        },
        identificationNo1:{
            required:false,
            minlength:18,
            maxlength:18
        },
        phone1:{
            required:false,
            minlength:11,
            maxlength:11
        },
        email1:{
            required:false,
            minlength:2,
            maxlength:50
        },
        mobile1:{
            required:false,
            minlength:2,
            maxlength:50
        }
    },
    messages:{
        userName1:icon + "请输入2-50个字符的用户名",
        realName1:icon + "请输入2-50个字符的真实姓名",
        identificationNo1:icon + "请输入18个字符的身份证号",
        phone1:icon + "请输入11个字符的电话号码",
        email1:icon + "请输入2-50个字符的组织机构名称",
        mobile1:icon + "请输入2-50个字符的组织机构描述",
    },
    submitHandler:function(form){
        userUpdate();
    }
});

function userPasswordInit(e){
	var id = e.getAttribute("data-value");
	var userId = {id:''};
	userId["id"] = id;
	DCMSBusi.Api.invoke("user/get",userId).done((jsonData)=>{
		var userInfo = jsonData["data"];
		var realName = userInfo['realname'];
		var html = '<form role="form" id="userEditPasswordForm">'+
			'<div hidden="hidden" class="form-group">'+
			  '<input  id="userId2" value="'+ id +'" for="name">'+
			'</div>'+
			'<label>用户：'+realName+'</label>'+
			'<div class="form-group">'+
		  		'<label for="name">输入新密码</label>'+
		  		'<input type="password" class="form-control" id="password1">'+
			'</div>'+
			'<div class="form-group">'+
		  		'<label for="name">再次输入新密码</label>'+
		  		'<input type="password" class="form-control" id="password2">'+
			'</div>'+
			'<div class="modal-footer">'+
	            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭'+
	            '</button>'+
	           ' <button type="button" onclick="userEditPassword()" class="btn btn-primary">'+
	              ' 确认修改'+
	            '</button>'+    	
	        '</div>'+  
		'</form>';
		var index = document.getElementById('userEditPasswordBody');
		index.innerHTML = html;

	});
}

function userUpdate(){
	var id = $("#userId1").val();
	var userName = $("#userName1").val();
	var realName = $("#realName1").val();
	var identificationNo = $("#identificationNo1").val();
	var phone = $("#phone1").val();
	var email = $("#email1").val();
	var mobile = $("#mobile1").val();
	var sex = $("#sex1").val();
	var status = $("#status1").val();
	var domainIds = $("#domainPId1").val();
	var roleIds = $("#rolePId1").val();
	if(realName == "")
	{
		$("#alertName").text("请输入真实姓名");
		return false;
	}
	var userInfo = {id:'',realname:'', identificationno:'', phone:'', email:'', mobile:'',sex:'',status:'',roleIds:'',domainIds:''};
	userInfo['id'] = id;
	userInfo['username'] = userName;
	userInfo['realname'] = realName;
	//userInfo['password'] = password;
	userInfo['identificationno'] = identificationNo;
	userInfo['phone'] = phone;
	userInfo['email'] = email;
	userInfo['mobile'] = mobile;
	userInfo['sex'] = sex;
	userInfo['status'] = status;
	userInfo['roleIds'] = roleIds;
	userInfo['domainIds'] = domainIds;
	DCMSBusi.Api.invoke("user/update",userInfo).done((res)=>{
		if(res.status == "1"){
			dtApi.ajax.reload();
			DCMSUtils.Modal.toast("更新用户"+userName+"成功",'');
        	$("#userupdate").modal('hide');
			return true;
		}else{
			DCMSUtils.Modal.toast("更新用户失败"+res.msg,'');
			return false;
		}
	});
}

function userEditPassword(){
	var id = $("#userId2").val();
	console.log("userId2的value"+id);
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();

	if(password1 != password2){
		alert("两次输入的密码不一致！");
		return false;
	}
	var userInfo = {id:'',password:''};
	userInfo['id'] = id;
	userInfo['password'] = password1;
	DCMSBusi.Api.invoke("user/modifyPassword",userInfo).done((res)=>{
		if(res.status == "1"){
			dtApi.ajax.reload();
			DCMSUtils.Modal.toast("更改用户"+userName+"的密码成功",'');
			return true;
		}else{
			DCMSUtils.Modal.toast("更改用户"+userName+"的密码失败"+res.msg,'');
			return false;
		}
	});
}


