function roomPGDelete(id,floorName){
    DCMSUtils.Ajax.doPost('serviceRoomIcngph/getYml',{id:id}).then(function(data){
        if(data.status=='1'){
               console.log('yml'+data);
            }else{
                DCMSUtils.Modal.toast('yml出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('yml出错','forbidden');
        });

    DCMSUtils.Ajax.doPost('serviceRoomIcngph/getJson',{id:id}).then(function(data){
        if(data.status=='1'){
               console.log('json'+data);
            }else{
                DCMSUtils.Modal.toast('json出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('json出错','forbidden');
        });
	DCMSUtils.Modal.confirm('确定删除['+floorName+']吗？','',function () {
        DCMSUtils.Modal.showLoading('机房平面删除中...');
        DCMSUtils.Ajax.doPost('serviceRoomIcngph/delete',{id:id}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                DCMSUtils.Modal.toast('删除机房平面成功','');
                window.location.reload();
            }else{
                DCMSUtils.Modal.toast('删除机房平面出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除机房平面出错','forbidden');
        });
    })
}