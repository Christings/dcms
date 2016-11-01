function roomPGAdd(){
	$("#roomPGAddForm").submit(function(event){
		var floorName = $("#floorName").val();
		var obj = document.getElementById("zipFile");
		var zipFile = obj.files[0];
		if(obj.value == "选择zip文件..." || obj.value == "未选择任何文件"){
			DCMSUtils.Modal.toast('请选择需要上传的文件!','');
			return false;
		}
		var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
		if(stuff != 'zip'){
			DCMSUtils.Modal.toast('请选择zip类型的文件上传!','');
			return false;
		}
		var formData = new FormData($("#roomPGAddForm"));
		formData.append('floorName',floorName);
		formData.append('zipFile',zipFile);

		var dtd=$.Deferred();
		$.ajax({
            url:"../../../serviceRoomIcngph/add",
            type:'post',
            data: formData,
            async:true,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false   // 告诉jQuery不要去设置Content-Type请求头
            // success:function(data){
            // 	window.location.reload();
            // }
        }).then(function(data){
			console.log(data);
		},function(error){
			dtd.reject(error);
			DCMSUtils.Modal.toast(error,'');
		});
	});
}