function equipmentAdd(){
	// var name = document.getElementById("userName").value;
	// var rank = document.getElementById("userRank").value;
	// var level = document.getElementById("userLevel").value;
	// // var url = document.getElementById("userName").value;
	// var parentId = document.getElementById("userParentId").value;
	// var iconId = document.getElementById("userIcon").value;
	// var type = document.getElementById("userType").value;
	console.log("equipmentAdd");
	$("#equipmentAddForm").submit(function(){
		var equName = $("#equName").val();
		var equType = $("#equTypeOp").val();
		var equVendor = $("#equVendor").val();
		var rsoPath = $("#rsoPath").val();
		var maxPath = $("#maxPath").val();
		if(equName == "" || equType == "")
		{
			$("#alert").text("请输入机柜名称或类型");
			console.log("请输入机柜名称或类型");
			return false;
		}
		var equipmentData = {equName:equName,equType:equType,equVendor:equVendor,rsoPath:rsoPath,maxPath:maxPath};
		DCMSUtils.Ajax.doPost("fixed/equipment/add",equipmentData).done((jsonData)=>{
			if(jsonData["status"] == "1"){
				console.log("添加机柜"+res.data.equName+"成功"+res.data.id);
			}else{
				console.log("添加机柜失败"+res.msg);
			}
		});
		// $.ajax({
		// 	url:"fixed/equipment/add",
		// 	dataType:"json",
		// 	data: equipmentData,
		// 	type:"post",
		// 	success:function(res){
		// 		console.log("good");
				// if(res.status == "1"){
				// 	console.log("添加机柜"+res.data.equName+"成功"+res.data.id);
				// }else{
				// 	console.log("添加机柜失败"+res.msg);
				// }
		// 	}
		// });
	});
}