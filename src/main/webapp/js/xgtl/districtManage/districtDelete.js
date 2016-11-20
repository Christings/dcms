function districtDelete(e){
	var id = e.getAttribute("data-id");
	var name = e.getAttribute("data-name");
	var disInfo = {id:id};
	 DCMSUtils.Modal.confirm('确定删除['+name+']吗？','',function () {
        DCMSUtils.Modal.showLoading('区域删除中...');
        DCMSBusi.Api.invoke('area/deleteArea',disInfo).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                dtApi.ajax.reload();
                DCMSUtils.Modal.toast('删除区域成功','');
            }else{
                DCMSUtils.Modal.toast('删除区域出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除区域出错','forbidden');
        });
    })
}