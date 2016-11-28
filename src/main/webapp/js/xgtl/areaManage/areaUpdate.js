function districtUpdateInit(e){
	var id = e.getAttribute("data-id");
	var disInfo = {id:id};
	DCMSBusi.Api.invoke("area/getAreaById",disInfo).done((res)=>{
		if(res.status == "1"){
			var html = '<div class="form-group">'+
				  '<label for="districtName1">区域名称</label>'+
				  '<input hidden="hidden" id="districtId1" value="'+id+'">'+
				  '<input type="text" class="form-control" name="districtName1" value="'+res.data.name+'"id="districtName1">'+
				'</div>'+
				'<div class="form-group">'+
				  	'<label for="distictRoomId1">所属机房</label>'+
				 	'<select class="form-control m-b valid" onchange="selectRoom1()" name="districtRoomId1" id="districtRoomId1">'+
					'</select>'+
				'</div>'+
				'<div class="form-group" >'+
					'<label class="col-sm-3">机柜资产编码</label>'+
					'<input id="cabinetResourceCodes1" name="cabinetResourceCodes1"  style="display:none">'+
					'<input id="selectRoomId1" value="'+res.data.roomId+'"style="display:none">'+
					'<div class="col-sm-9">'+
						'<span id="cRCName1" name="cRCName1" style="width: 50%;"></span>&nbsp;&nbsp;&nbsp;&nbsp;<a id="selectCabinetsBtn1" onclick="selectCabinetsBtn1()" class="btn btn-primary btn-sm btn-outline">选择</a>'+
					'</div>'+
				'</div>'+
				'<div class="form-group">'+
				  '<label for="districtRemark">备注</label>'+
				  '<textarea class="form-control" name="districtRemark1"  id="districtRemark1">'+res.data.remark+'</textarea>'+
				'</div>';
			document.getElementById('districtUpdateBody').innerHTML = html;
			getCPRoomList('districtRoomId1',res.data.roomId);
			return true;
		}else{
			DCMSUtils.Modal.toast("修改区域失败"+res.msg,'');
			return false;
		}
	});
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#districtUpdateForm").validate({
    rules:{
        districtName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        cabinetResourceCodes:{
            required:false,
            // checkResourceCodes:true,
            minlength:2,
            maxlength:50
        }
    },
    messages:{
        districtName:icon + "请输入2-50个字符的登录账名",
        cabinetResourceCodes:icon + "请输入2-50个字符的用户姓名"
    },
    submitHandler:function(form){
        districtUpdate();
    }
});

function districtUpdate(){
	var id = $('#districtId1').val();
	var name = $('#districtName1').val();
	var roomId = $('#districtRoomId1').val();
	var remark = $('#districtRemark1').val();
	var disInfo = {id:id,name:name,roomId:roomId,remark:remark};
	console.log('update'+disInfo['id']);
	DCMSBusi.Api.invoke("area/update",disInfo).done((res)=>{
		if(res.status == "1"){
			DCMSUtils.Modal.toast("修改区域"+name+"成功",'');
			dtApi.ajax.reload();
        	$("#districtUpdate").modal('hide');
			return true;
		}else{
			DCMSUtils.Modal.toast("修改区域失败"+res.msg,'');
			return false;
		}
	});
}