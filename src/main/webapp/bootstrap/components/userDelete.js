function userDeleteInit(e){
	// $(document).ready(){
		var id = e.getAttribute("data-value");
		var userId={id:""};
		userId["id"]=id;
		console.log("delete:"+id);
		$.ajax({
			type:"post",
			url:"user/get",
			dataType: 'json',
			data: userId,
		}).done((jsonData)=>{
			var userInfo = jsonData["data"];
			var userName = userInfo["username"];
			console.log("delete:"+userName);
			var html = ("<form role=\"form\" id=\"userDeleteForm\">"+
					"<label id=\"deleteUserName\">"+userName+"</label>"+
		            "<div class=\"modal-footer\">"+
			           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			           "</button>"+
			            "<button type=\"submit\" data-id=\""+id+"\"onclick=\"userDelete(this)\" class=\"btn btn-primary\">"+
			              "确认删除"+
			           "</button>"+
			       " </div>"+
			       "</form>");
			var body = document.getElementById("userDeleteBody");
			body.innerHTML = html;
		});
}

function userDelete(e){
	var id = e.getAttribute("data-id");
	console.log("delete:"+id);
	var userInfo = {id:''};
	userInfo["id"] = id;
	$.ajax({
		type:"post",
		url:"menu/delete",
		dataType: 'json',
		data: userInfo,
		success:function(res){
			if(res.status == "1"){
				console.log("删除用户成功");
			}else{
				console.log("删除用户失败"+res.msg);
			}
		}
	});
}

