function roomPGDownload(id,fileName){
	var params = {id:id,fileName:fileName};
	DCMSUtils.Ajax.doPost("serviceRoomIcngph/checkFileIsExist",params).then(function (data) {
		if(data.status == 1){
			window.location.href = '../../../serviceRoomIcngph/downloadFile?id='+id+'&fileName='+fileName;
		}else{
			DCMSUtils.Modal.toast('您要下载的文件不存在！','');
		}
	});
}