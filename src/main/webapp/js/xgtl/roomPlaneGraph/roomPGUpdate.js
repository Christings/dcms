function roomPGUpdate(id){
	$("#roomPGUpdateForm").submit(function(){
		var floorName = $("#floorName").val();
		var zipFile = document.getElementById("zipFile").files[0];
		console.log(zipFile);
		var params = {id:id,floorName:floorName,zipFile:zipFile};
		console.log(params);
		DCMSUtils.Ajax.doPost("serviceRoomIcngph/update",params).done((res)=>{
			if(res.status == "1"){
				console.log("修改平面图"+floorName+"成功");
				alert("修改平面图"+floorName+"成功");
				return true;
			}else{
				console.log("修改平面图"+floorName+"失败"+res.msg);
				alert("修改平面图"+floorName+"失败");
				return false;
			}
		});
	});
}