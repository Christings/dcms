

function roleUserInit(e){
	var id = e.getAttribute("data-value");

	var roleId = {roleId: id};
	// $.ajax({
	// 	type:"post",
	// 	url:"user/role/getUserAll",
	// 	dataType:'json',
	// 	data: roleId
	// })
	DCMSUtils.Ajax.doPost("user/role/getUserAll",roleId).done((jsonData)=>{
		var data = jsonData["data"];
		console.log(data);
		var users = jsonData["data"]["records"];
		var role = jsonData["data"]["role"];
		var roleUsers = "";
		var e;
		for(var i = 0,len=users.length;i<len;i++){
			e = users[i];
			var status;
			switch(e["status"]){
				case 1:
					status = "激活";
					break;
				case 2:
					status = "已删除";
					break;
				case 0:
					status = "未激活";
					break;
			}
			var check;
			if(e["checked"] == 1){
				check = "checked";
			}else{
				check = "";
			}

			roleUsers += "<tr class=\"form-group\">"+
							"<td><input type=\"checkbox\" " + check + " value=\""+e["id"]+"\"></td>"+
							"<td>"+e["username"]+"</td>"+
							"<td>"+e["realname"]+"</td>"+
							"<td>"+status+"</td>"+
						"</tr>";
		}
		var content = "<form role=\"form\" id=\"roleUserForm\">"+
			"<label>"+ role["rolecode"] +"</label>"+
			"<table class=\"table table-bordered table-hover\">"+
				"<th>"+
					"<tr>"+
						"<td></td>"+
						"<td>用户账号</td>"+
						"<td>真实姓名</td>"+
						"<td>状态</td>"+
					"</tr>"+
				"</th>"+
				"<tbody id=\"roleUserTable\">"+
					roleUsers+
				"</tbody>"+
			"</table>"+
			"<div class=\"modal-footer\">"+
	            "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
	            "</button>"+
	            "<button type=\"submit\" data-value=\""+role["id"]+"\"onclick=\"roleBatchUsers(this)\" class=\"btn btn-primary\">"+
	            "提交修改"+
	            "</button>"+
        	"</div>"+
		"</form>";
		var index = document.getElementById("roleUserBody");
		index.innerHTML = content;
	});
}

function roleBatchUsers(e){
	var roleId =  e.getAttribute("data-value");
	$("#roleUserForm").submit(function(){
		var users = document.getElementsByTagName("input");
		var userIds = "";
		for(var i=0,len=users.length;i<len;i++){
			var user = users[i];
			if(user.checked == true){
				console.log(user.value);
				userIds += user.value + ",";
			}
		}
		console.log("batchUsers");
		var batchUserData = {roleId: roleId,userIds: userIds};
		// $.ajax({
		// 	url:"user/role/batchUsers",
		// 	dataType:"json",
		// 	data: batchUserData,
		// 	type: "post"
		// })
		DCMSUtils.Ajax.doPost("user/role/batchUsers",batchUserData).done((jsonData)=>{
			console.log(jsonData["msg"]);
		});
	});	
}



