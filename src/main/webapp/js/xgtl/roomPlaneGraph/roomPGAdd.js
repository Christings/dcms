function roomPGAdd(){
	$("#roomPGAddForm").submit(function(){
		var floorName = $("#floorName").val();
		var obj = document.getElementById("zipFile");
		if(obj.value == "选择zip文件..." || obj.value == "未选择任何文件"){
			DCMSUtils.Modal.toast('请选择需要上传的文件!','');
			return false;
		}
		var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
		if(stuff != 'zip'){
			DCMSUtils.Modal.toast('请选择zip类型的文件上传!','');
			return false;
		}
		var zipFile = obj.files[0];
		var reader = new FileReader();
		reader.onload = function(){
			var params = {floorName:floorName,zipFile:reader.result};
			console.log(params);
			DCMSUtils.Ajax.doPost("serviceRoomIcngph/add",params).done((res)=>{
				if(res.status == "1"){
					console.log("添加平面图"+floorName+"成功");
					alert("添加平面图"+floorName+"成功");
					return true;
				}else{
					console.log("添加平面图"+floorName+"失败"+res.msg);
					alert("添加平面图"+floorName+"失败");
					return false;
				}
			});
		}
		reader.readAsDataURL(zipFile);
		console.log(zipFile);
	});
}