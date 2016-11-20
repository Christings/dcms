var icon = "<i class='fa fa-times-circle'></i> ";
// $.validator.addMethod( "checkResourceCodes",function(value,element){     
// 	var pattern =/^[0-9]$/;
// 	if(value !=''){if(!pattern.exec(value)){return false;}};
// 	return true; 
// },icon +"请按照A01,A02,A03...格式输入" ); 

$("#districtAddForm").validate({
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
        districtAdd();
    }
});

function districtAdd(){
	var name = $('#districtName').val();
	var roomId = $('#districtRoomId').val();
	console.log('roomId'+roomId);
	var remark = $('#districtRemark').val();
	var cabinetResourceCodes = $('#cabinetResourceCodes').val();
	var disInfo = {name:name,roomId:roomId,remark:remark,cabinetResourceCodes:cabinetResourceCodes};
	DCMSBusi.Api.invoke("area/add",disInfo).done((res)=>{
		if(res.status == "1"){
			DCMSUtils.Modal.toast("添加区域"+name+"成功",'');
			dtApi.ajax.reload();
        	$("#districtAdd").modal('hide');
            districtAddFormReset();//重置表单
			return true;
		}else{
			DCMSUtils.Modal.toast("添加区域失败"+res.msg,'');
			return false;
		}
	});
}
//重置表单函数
function districtAddFormReset(){
    document.getElementById("districtAddForm").reset();
}
