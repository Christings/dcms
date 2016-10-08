function equipmentDelete(e){
	var id = e.getAttribute("data-id");
	var equipmentData = {id: id};
	DCMSUtils.Ajax.doPost("fixed/equipment/delete",equipmentData).done((jsonData)=>{
		if(jsonData["status"] == "1"){
			console.log("添加机柜"+res.data.equName+"成功"+res.data.id);
		}else{
			console.log("添加机柜失败"+res.msg);
		}
	});
	// $.ajax({
	// 	url: "fixed/equipment/delete",
	// 	dataType: "json",
	// 	data: equipmentData,
	// 	type:"post",
	// 	success: function(res){
	// 		if(res.status == "1"){
	// 			console.log("修改机柜成功");
	// 		}else{
	// 			console.log("修改机柜失败"+res.msg);
	// 		}
	// 	}
	// });
}

function equipmentDeleteInit(e){
	// $(document).ready(){
		var id = e.getAttribute("data-value");
		var equId={id:""};
		equId["id"]=id;
		console.log("delete:"+id);

		// $.ajax({
		// 	type:"post",
		// 	url:"fixed/equipment/get",
		// 	dataType: 'json',
		// 	data: equId,
		// })
		DCMSUtils.Ajax.doPost("fixed/equipment/get",equId).done((jsonData)=>{
			var equInfo = jsonData["data"];
			var equName = equInfo["equName"];
			console.log("delete:"+equName);
			var html = ("<form role=\"form\" id=\"userDeleteForm\">"+
					"<label id=\"deleteUserName\">"+equName+"</label>"+
		            "<div class=\"modal-footer\">"+
			           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			           "</button>"+
			            "<button type=\"submit\" data-id=\""+id+"\"onclick=\"equipmentDelete(this)\" class=\"btn btn-primary\">"+
			              "确认删除"+
			           "</button>"+
			       " </div>"+
			       "</form>");
			var body = document.getElementById("equipmentDeleteBody");
			body.innerHTML = html;
		});
}