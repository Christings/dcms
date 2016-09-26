function roleDeleteInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var roleId={id:""};
	roleId["id"]=id;
	console.log("delete:"+id);
	// $.ajax({
	// 	type:"post",
	// 	url:"role/get",
	// 	dataType: 'json',
	// 	data: roleId,
	// })
	DCMSUtils.Ajax.doPost("role/get",roleId).done((jsonData)=>{
		var roleInfo = jsonData["data"];
		var roleName = roleInfo["rolename"];
		console.log("delete:"+roleName);
		var html = ("<form role=\"form\" id=\"roleDeleteForm\">"+
				"<label id=\"deleteRoleName\">"+roleName+"</label>"+
	            "<div class=\"modal-footer\">"+
		           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
		           "</button>"+
		            "<button type=\"submit\" data-id=\""+id+"\"onclick=\"roleDelete(this)\" class=\"btn btn-primary\">"+
		              "确认删除"+
		           "</button>"+
		       " </div>"+
		       "</form>");
		var body = document.getElementById("roleDeleteBody");
		body.innerHTML = html;
	});
}

function roleDelete(e){
	var id = e.getAttribute("data-id");
	console.log("delete:"+id);
	var roleInfo = {id:''};// 
	roleInfo['id'] = id;
	DCMSUtils.Ajax.doPost("role/delete",roleInfo).done((res)=>{
		if(res.status == "1"){
			console.log("删除角色成功" + res.data);
		}else{
			console.log("删除角色失败" + res.msg);
		}
	});
	// $.ajax({
	// 	type:"post",
	// 	url: "role/delete",
	// 	dataType: 'json',
	// 	// data: userInfo,
	// 	// contentType: "application/json",
	// 	data: roleInfo,
	// 	success: function(res){
	// 		if(res.status == "1"){
	// 			console.log("删除角色成功" + res.data);
	// 		}else{
	// 			console.log("删除角色失败" + res.msg);
	// 		}
	// 	}
	// });
}

