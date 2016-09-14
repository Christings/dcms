
function roleUpdateInit(e){
	var id = e.getAttribute("data-value");
	var roleGetData = {id:id};
	$.ajax({
		url:"role/get",
		dataType:"json",
		data: roleGetData,
		type: "post"
	}).done((jsonData)=>{
		var roleInfo = jsonData["data"];
		var content = "<form role=\"form\" id=\"roleUpdateForm\">"+
						"<div class=\"form-group\" hidden=\"hidden\">"+
							"<input type=\"text\" class=\"form-control\" value=\"" + id + "\"id=\"roleId1\" >"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label for=\"name\">角色编码</label>"+
							"<input type=\"text\" class=\"form-control\" value=\"" + roleInfo["rolecode"] + "\"id=\"roleCode1\" >"+
						"</div>"+
						"<div class=\"form-group\">"+
						  	"<label for=\"name\">角色名称</label>"+
						  	"<input type=\"text\" class=\"form-control\" value=\"" + roleInfo["rolename"] + "\" id=\"roleName1\" >"+
						"</div>"+
						"<div class=\"modal-footer\">"+
				           " <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
				           " </button>"+
				           " <button type=\"submit\" onclick=\"roleUpdate()\" class=\"btn btn-primary\">"+
				           		"确认修改"+
				            "</button>"+
			        	"</div>"+
					"</form>";
		var index = document.getElementById("roleUpdateBody");
		index.innerHTML = content;
		console.log(jsonData["msg"]);
	});
}
function roleUpdate(){
	console.log("roleUpdate");
	$("#roleUpdateForm").submit(function(){
		var roleCode = $("#roleCode1").val();
		var roleName = $("#roleName1").val();
		var id = $("#roleId1").val();
		console.log("roleAdd1");
		if(roleCode == "" || roleName == "")
		{
			//$("#alert").text("请输入登陆名称");
			return false;
		}
		var roleInfo = { id:'',rolecode:'', rolename:''};
		roleInfo['id'] = id;
		roleInfo['rolecode'] = roleCode;
		roleInfo['rolename'] = roleName;
		$.ajax({
			type:"post",
			url:"role/update",
			dataType: 'json',
			data: roleInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("编辑用户"+roleName+"成功");
					alert("编辑用户"+roleName+"成功");
				}else{
					console.log("编辑用户"+roleName+"失败"+res.msg);
					alert("编辑用户"+roleName+"失败");
				}
			}
		});
	});

}
