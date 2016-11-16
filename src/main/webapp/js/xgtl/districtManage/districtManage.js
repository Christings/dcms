var dtApi;
function pageInit(){
	districtListLoad();
}
function districtListLoad(){
	var dtTable=$("#districtListTable");
	dtApi=dtTable.dataTable({
		/**
		 *
		 * @param data DT 封装的向后台传递的参数
		 * @param callback 回调函数，用户向DT传数据
		 * @param settings 一些设置
		 */
		ajax:function(data, callback, settings){
			//需要把分页参数转为DCMS接口规范的
			// console.log(data);
			// if($("th[aria-label^='登录号']").hasClass('sorting_asc')){
			// 	console.log('登录号'+'asc');
			// }else{
			// 	console.log('登录号'+'desc');
			// }
			var params=DCMSUtils.DataTables.handleParams(data);
			params.name = $("#searchDistrictname").val();
			params.roomName = $("#searchRoomName").val();
			DCMSBusi.Api.invoke("area/datagrid",params).then(function (data) {
				if(data.status=='1'){
					//组织DT标准的返回值
					// for(var i=0,len=data.data.records.length;i<len;i++){
					// 	var domains = data.data.records[i]?data.data.records[i]:[];
					// }
					callback({
						data:data.data.records,
						recordsTotal:data.data.count,
						recordsFiltered:data.data.count
					});
				}
			});
		},
		columns: [
			{title: '区域名称', data: 'name',name:'name'},
			{title: '所属机房', data: 'roomName',name:'roomName'},
			{title: '操作', data: ''}
		],
		columnDefs:[
			{
				targets:2,
				render:function(data,type,row,meta){
					var html = "<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#districtUpdate\" data-id=\""+row.id+"\" onclick=\"districtUpdateInit(this)\"></i>"+
						"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-id=\""+row.id+"\" data-name=\""+row.name+"\" onclick=\"districtDelete(this)\"></i>";
					return html;
				}
			}
		]
	}).api();

	dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

	$("#queryBtn").click(function(){
		dtApi.ajax.reload();
	});

	$("#resetBtn").click(function(){
		document.getElementById("queryForm").reset();
	});

}

//获取机房列表
function getCPRoomList(name, selectRoomId) {
    DCMSBusi.Api.invoke("room/selectForChoose").then(function (data) {
        if(data.status=='1'){
        	if(selectRoomId != ''){
				var selectOptions = "";
	            $.each(data.data, function(index, objVal) {
	            	if(selectRoomId === objVal["id"]){
	            		selectOptions += "<option selected value='"+objVal["id"]+"'>"+objVal["name"]+"</option>";
	            	}else{
	            		selectOptions += "<option value='"+objVal["id"]+"'>"+objVal["name"]+"</option>";
	            	}
	            });
        	}else{
        		var selectOptions = "<option value=''>请选择所属机房</option>";
	            $.each(data.data, function(index, objVal) {
	                selectOptions += "<option value='"+objVal["id"]+"'>"+objVal["name"]+"</option>";
	            });
        	}
            $("#"+name).html(selectOptions);
        }
    });
}