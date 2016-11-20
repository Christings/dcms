function roomPGDelete(id,floorName){
	DCMSUtils.Modal.confirm('确定删除['+floorName+']吗？','',function () {
        DCMSUtils.Modal.showLoading('机房平面删除中...');
        DCMSBusi.Api.invoke('roomIcngph/delete',{id:id}).then(function(data){
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