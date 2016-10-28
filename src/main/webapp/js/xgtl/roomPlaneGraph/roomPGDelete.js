function roomPGDelete(id,floorName){
	DCMSUtils.Modal.confirm('确定删除['+floorName+']吗？','',function () {
        DCMSUtils.Modal.showLoading('机房平面删除中...');
        DCMSUtils.Ajax.doPost('serviceRoomIcngph/delete',{id:id}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                getDomainList(globlePageNum, globlePageSize);
                DCMSUtils.Modal.toast('删除机房平面成功','');
            }else{
                DCMSUtils.Modal.toast('删除机房平面出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除机房平面出错','forbidden');
        });
    })
}