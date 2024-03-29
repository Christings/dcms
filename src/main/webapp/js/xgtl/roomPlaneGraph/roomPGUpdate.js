function roomPGUpdateInit(id,floorName){
	// download();
	var html = '<form role="form" id="roomPGUpdateForm" enctype="multipart/form-data">'+
					'<div class="form-group">'+
					 ' <label for="name">楼层名称</label>'+
					 '<input id="floorId1" hidden="hidden" value="'+id+'">'+
					  '<input name="floorName1" type="text" class="form-control" id="floorName1" value="'+floorName+'"/>'+
					'</div>'+
					'<div class="form-group">'+
					  '<label for="zipFile">平面图zip文件：</label>'+
					  '<input id="zipFile1" name="zipFile"  type="file" accept="aplication/zip" value="选择zip文件..."/>'+
					'</div>'+
					'<div class="modal-footer">'+
			            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
			            '<button type="button" onclick="roomPGUpdate()"class="btn btn-primary">确认修改</button>'+
		        	'</div>'+
				'</form>';
	document.getElementById('roomPGUpdateBody').innerHTML = html;
}

var icon = "<i class='fa fa-times-circle'></i> ";
$('#roomPGUpdateForm').validate({
	rules:{
		floorName:{
			required:true,
			minlength:2,
			maxlength:50
		}
	},
	messages:{
		floorName:icon + "请输入2-50个字符的楼层信息"
	},
	submitHandler:function(form){
		roomUpdateAdd();
	}
});

function roomPGUpdate(){
	var floorName = $("#floorName1").val();
	var id = $("#floorId1").val();
	var obj = document.getElementById("zipFile1");
	var zipFile = obj.files[0];
	if(obj.value == "选择zip文件..." || obj.value == ""){
		DCMSUtils.Modal.toast('请选择需要上传的文件!','');
		return false;
	}
	var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
	if(stuff != 'zip'){
		DCMSUtils.Modal.toast('请选择zip类型的文件上传!','');
		return false;
	}
	var formData = new FormData($("#roomPGUpdateForm"));
	formData.append('id',id);
	formData.append('floorName',floorName);
	formData.append('zipFile',zipFile);
	$.ajax({
        url:"../../../roomIcngph/update",
        type:'post',
        data: formData,
        processData: false,  // 告诉jQuery不要去处理发送的数据
        contentType: false   // 告诉jQuery不要去设置Content-Type请求头
    }).done((jsonData)=>{
    	var data = jsonData;
	   	if(data.status == 1){
    		DCMSUtils.Modal.toast('机房平面图信息更新成功'+data.msg,'');
    		dtApi.ajax.reload();
    		$("#roomPGUpdate").modal('hide');
    	}else{
    		DCMSUtils.Modal.toast(data.msg,'');
    	}
    });
}