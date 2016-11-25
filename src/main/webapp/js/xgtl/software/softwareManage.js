var dtApi;
function pageInit(){
	softwareListLoad();
}
function softwareListLoad(){
	var dtTable=$("#softwareListTable");
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
			params.name = $("#searchSoftwarename").val();
			params.roomName = $("#searchRoomName").val();
			var data = {};
			var records = [];
			var record = {};
			record.name = '监控系统';
			record.resourceCode = 'WA-1';
			record.productName = '监控镜头';
			record.equipmentsBelonged = 'Watching-12A';
			record.version = '1.0.0';
			records.push(record);
			data.count = 1;
			// DCMSBusi.Api.invoke("area/datagrid",params).then(function (data) {
			// 	if(data.status=='1'){
					//组织DT标准的返回值
					// for(var i=0,len=data.data.records.length;i<len;i++){
					// 	var domains = data.data.records[i]?data.data.records[i]:[];
					// }
			callback({
				data:records,
				recordsTotal:data.count,
				recordsFiltered:data.count
			});
			// 	}
			// });
		},
		columns: [
			{title: '软件名称', data: 'name',name:'name'},
			{title: '资源编码', data: 'resourceCode',name:'resourceCode'},
			{title: '产品名称', data: 'productName',name:'productName'},
			{title: '所属设备', data: 'euipmentsBelonged',name:'euipmentsBelonged'},
			{title: '软件版本', data: 'version',name:'version'},
			{title: '操作', data: ''}
		],
		columnDefs:[
			{
				targets:5,
				render:function(data,type,row,meta){
					var html = "<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#softwareUpdate\" data-id=\""+row.id+"\" onclick=\"softwareUpdateInit(this)\"></i>"+
						"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-id=\""+row.id+"\" data-name=\""+row.name+"\" onclick=\"softwareDelete(this)\"></i>";
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

function softwareAddFormReset(){
    document.getElementById("softwareAddForm").reset();
}

function relateHardware(){
	$('#softwareAdd').modal('hide');
	$('#softHardwareModal').modal('show');
	// $('#softwareAdd').modal({backdrop: 'static', keyboard: false});
	renderHardwaredsTable();
}

function renderHardwaredsTable(){
	// console.log('renderRoomId'+roomId);
	// DCMSBusi.Api.invoke("cabinet/getCabinetsByRoomId",{roomId:roomId}).done((res)=>{
	// 	if(res.status == "1"){
			// var cabinets = res['data'];
			var content = [];
			var hardwares = {};
			hardwares.ip = '123.44.11.232';
			hardwares.eqName = '交换机'; 
			hardwares.eqResourceCode = 'A3212';
			hardwares.roomName = '测试机房';
			hardwares.cabinetName = 'A1-11';

			content.push(hardwares);
			// for(var i=0,len=hardwares.length;i<len;i++){
			// 	e = hardwares[i];
			// 	var tmp = {};
			// 	tmp.ip = e.ip;
			// 	tmp.eqName = e.eqName;
			// 	tmp.eqResourceCode = e.eqResourceCode;
			// 	tmp.roomName = e.roomName;
			// 	tmp.cabinetName = e.cabinetName;
			// 	content.push(tmp);
			// }
			$('#hardwaresModalTable').bootstrapTable('destroy');
			$('#hardwaresModalTable').bootstrapTable({
				search:true,
				striped: true,
	 			pagination: true,
	 			singleSelect: false,
	 			pageNumber: 1,
				pageSize: 8,
	 			pageList: [10, 50, 100, 200, 500],
			    columns: [
			    {
	                field: 'state',
	                checkbox: true
	            },
			    {
		        field: 'ip',
			        title: 'IP地址'
			    },
			    {
		        field: 'eqName',
			        title: '设备名称'
			    },
			    {
		        field: 'eqResourceCode',
			        title: '资源编码'
			    },
			    {
		        field: 'roomName',
			        title: '所属机房'
			    },
			    {
		        field: 'cabinetName',
			        title: '所属机柜'
			    }],
			    data: content
			});
			return true;
	// 	}else{
	// 		DCMSUtils.Modal.toast("获取机柜列表失败"+res.msg,'');
	// 		return false;
	// 	}
	// });
}
// function softwareAdd(){
// 	$('#softHardwareModal').modal({backdrop: 'static', keyboard: false});
// }
$('.modal').draggable({
	handle:'.modal-header'
});


$('#confirmHardwaresBtn').click(function(){
	$('#softHardwareModal').modal('hide');
	$('#softwareAdd').modal('');
});