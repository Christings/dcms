function equipmentUpdate(){
	$("#equipmentUpdateForm").submit(function(){
		var id = $("#equipmentId1").val();
		var equName = $("#equName1").val();
		var equType = $("#equType1").val();
		var equVendor = $("#equVendor1").val();
		var rsoPath = $("#rsoPath1").val();
		var maxPath =$("#maxPath1").val();
		console.log("equName:"+equName);
		console.log("equType:"+equType);
		console.log("equVendor:"+equVendor);
		console.log("rsoPath:"+rsoPath);
		console.log("maxPath:"+maxPath);
		if(equName == ""||equType == ""){
			console.log("机柜名称和机柜类型不能为空");
			return false;
		}
		var equipmentData = {id:id,equName:equName,equType:equType,equVendor:equVendor,rsoPath:rsoPath,maxPath:maxPath};
		DCMSUtils.Ajax.doPost("fixed/equipment/update",equipmentData).done((jsonData)=>{
			if(jsonData["status"] == "1"){
				console.log("更新机柜"+res.data.equName+"成功"+res.data.id);
			}else{
				console.log("更新机柜失败"+res.msg);
			}
		});
		// $.ajax({
		// 	url:"fixed/equipment/update",
		// 	dataType:"json",
		// 	data:equipmentData,
		// 	type:"post",
		// 	success: function(res){
		// 		if(res.status == "1"){
		// 			console.log("修改机柜"+res.data.equName+"成功");
		// 		}else{
		// 			console.log("修改机柜失败"+res.msg);
		// 		}
		// 	}
		// });
	});
}

function equipmentUpdateInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var equId = {id:''};
	equId["id"] = id;
	
	// console.log(menu);
	 console.log(id);
	// console.log(name);
	// $.ajax({
	// 	type:"post",
	// 	url:"fixed/equipment/get",
	// 	dataType: 'json',
	// 	data: equId,
	// 	})
	DCMSUtils.Ajax.doPost("fixed/equipment/get",equId).done((jsonData)=>{
			var equInfo = jsonData["data"];
			var equId = equInfo["id"];
			var equName = equInfo["equName"];
			var equType = equInfo['equType'];
			var equVendor = equInfo['equVendor'];
			var rsoPath = equInfo['rsoPath'];
			var maxPath = equInfo['maxPath'];
			var selected_0;
			var selected_1;
			var selected_2;
			var selected_3;
			var selected_4;
			switch(equType){
				case '0':
					selected_0 = "selected=\"\"";
					selected_1 = "";
					selected_2 = "";
					selected_3 = "";
					selected_4 = "";
					break;
				case '1':
					selected_0 = "";
					selected_1 = "selected=\"\"";
					selected_2 = "";
					selected_3 = "";
					selected_4 = "";
					break;
				case '2':
					selected_0 = "";
					selected_1 = "";
					selected_2 = "selected=\"\"";
					selected_3 = "";
					selected_4 = "";
					break;
				case '3':
					selected_0 = "";
					selected_1 = "";
					selected_2 = "";
					selected_3 = "selected=\"\"";
					selected_4 = "";
					break;
				case '4':
					selected_0 = "";
					selected_1 = "";
					selected_2 = "";
					selected_3 = "";
					selected_4 = "selected=\"\"";
					break;
			}
			//console.log("parentId:"+menu["parentId"]);
			var html = "<form role=\"form\" id=\"equipmentUpdateForm\">"+
						"<div class=\"form-group\">"+
						  "<input id=\"equipmentId1\" hidden=\"hidden\" value=\""+ equId +"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">机柜名称</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"equName1\" value=\""+equName+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  	"<label for=\"name\">机柜类型</label>"+
						 	"<select id=\"equType1\">"+
								"<option "+selected_0+"value = \'0\'>机柜</option>"+
								"<option "+selected_1+"value = \'1\'>空调</option>"+
								"<option "+selected_2+"value = \'2\'>ups</option>"+
								"<option "+selected_3+"value = \'3\'>配电柜</option>"+
								"<option "+selected_4+"value = \'4\'>开关柜</option>"+
							"</select>"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">厂商</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"equVendor1\" value=\""+equVendor+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">rso文件路径</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"rsoPath1\" value=\""+rsoPath+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
						  "<label for=\"name\">max文件路径</label>"+
						  "<input type=\"text\" class=\"form-control\" id=\"maxPath1\" value=\""+maxPath+"\">"+
						"</div>"+
						"<div class=\"modal-footer\">"+
			           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			            "</button>"+
			            "<button type=\"submit\" onclick=\"equipmentUpdate()\" class=\"btn btn-primary\">"+
			            "提交修改"+
			           "</button>"+
		        	"</div>"+
				"</form>";
			var body = document.getElementById("equipmentUpdateBody");
			body.innerHTML = html;
	});		// }
}



