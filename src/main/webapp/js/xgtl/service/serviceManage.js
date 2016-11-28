var dtApi;
var serviceId='';
function pageInit(){
	serviceListLoad();
}
function serviceListLoad(){
	var dtTable=$("#serviceListTable");
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
			record.serviceName = '监控系统';
			record.resourceCode = 'WA-1';
			record.descriptions = '监控镜头';
			// record.equipmentsBelonged = 'Watching-12A';
			// record.version = '1.0.0';
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
			{title: '业务名称', data: 'serviceName',name:'serviceName'},
			{title: '资源编码', data: 'resourceCode',name:'resourceCode'},
			{title: '描述信息', data: 'descriptions',name:'descriptions'},
			{title: '操作', data: ''}
		],
		columnDefs:[
			{
				targets:3,
				render:function(data,type,row,meta){
					var html = "<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#softwareUpdate\" data-id=\""+row.id+"\" onclick=\"serviceUpdateInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' title='关联软件列表' class='fa fa-desktop' role=\"presentation\" data-toggle=\"modal\" data-target=\"#softwareListRelated\" data-id=\""+row.id+"\" onclick=\"softwareListRelated(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' title='关联硬件列表' class='fa fa-suitcase' role=\"presentation\" data-toggle=\"modal\" data-target=\"#hardwareListRelated\" data-id=\""+row.id+"\" onclick=\"hardwareListRelated(this)\"></i>"+
						"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-id=\""+row.id+"\" data-name=\""+row.name+"\" onclick=\"serviceDelete(this)\"></i>";
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
//业务初次增加的软硬件关联
function relateHardware(){
	$('#serviceAdd').modal('hide');
	$('#hardwareModal').modal('show');
	$('#hardwareModal').addClass('hardFirst');
	// $('#softwareAdd').modal({backdrop: 'static', keyboard: false});
	renderHardwaresTable('');//业务id默认为空
}
function relateSoftware(){
	$('#serviceAdd').modal('hide');
	$('#softwareModal').modal('show');
	$('#softwareModal').addClass('softFirst');
	// $('#softwareAdd').modal({backdrop: 'static', keyboard: false});
	renderSoftwaresTable('');//业务id默认为空
}

//业务二次编辑软硬件关联列表
function addHardwares(){
	var selected = [];
	// var selectedFlag = 0;
	$('#hardwareListRelated').modal('hide');
	$('#hardwareModal').modal('show');
	$('#hardwareModal').removeClass('hardFirst');
	renderHardwaresTable('serviceId');//传入业务id   
}

function deleteHardwares(){
	var deleteIds = getSoftHardwaresSelected();
}
function addSoftwares(){
	$('#softwareListRelated').modal('hide');
	$('#softwareModal').modal('show');
	$('#softwareModal').removeClass('softFirst');
	renderSoftwaresTable('serviceId');//传入业务id
}
function deleteSoftwares(){
	var deleteIds = getSoftHardwaresSelected();
}
function getSoftHardwaresSelected(){
	var selected = [];
	$("tr[class='selected']").each(function(){
    	var tmp = {};
    	var id = $(this).find('td').get(0).innerHTML;
    	console.log('hardwareId'+id);
    	tmp.id=id;
    	selected.push(tmp);
    });
    var softOrHardwares = '';
    for(var i=0,len=selected.length;i<len;i++){
    	var hardware = selected[i];
    	if(i == (len -1)){
    		softOrHardwares += hardware['id'];
    	}else{
    		softOrHardwares += hardware['id']+',';
    	}
    }

    return softOrHardwares;
}
function softwareListRelated(e){//取出该项业务id
	//serviceId = e.getAttribute('data-id');
	var content = [];
	var softwares = {};
	softwares.id = 'asdf123as223dsxg367x';
	softwares.name = '软件名称1';
	softwares.productName = '产品名称1'; 
	softwares.resourceCode = 'A3212';
	softwares.serviceIp = '123.44.11.232';
	softwares.serverPort = '8001';


	content.push(softwares);
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
	$('#softwareListModalTable').bootstrapTable('destroy');
	$('#softwareListModalTable').bootstrapTable({
		search:true,
		striped: true,
		pagination: true,
		singleSelect: false,
		pageNumber: 1,
		pageSize: 8,
		pageList: [10, 50, 100, 200, 500],
		toolbar: '#toolbar-softwareList',
	    columns: [
	    {
	    	title: 'id',
	    	filed: 'id',
	    	visible: false,
	    },
	    {
            field: 'state',
            checkbox: true
        },
	    {
        field: 'name',
	        title: '软件名称'
	    },
	    {
        field: 'productName',
	        title: '产品名称'
	    },
	    {
        field: 'resourceCode',
	        title: '资源编码'
	    },
	    {
        field: 'serviceIp',
	        title: '服务IP'
	    },
	    {
        field: 'serverPort',
	        title: '服务器端口'
	    }],
	    data: content
	});
	return true;
}



function hardwareListRelated(e){//取出该项业务id
	//serviceId = e.getAttribute('data-id');
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
	$('#hardwareListModalTable').bootstrapTable('destroy');
	$('#hardwareListModalTable').bootstrapTable({
		search:true,
		striped: true,
		pagination: true,
		singleSelect: false,
		pageNumber: 1,
		pageSize: 8,
		pageList: [10, 50, 100, 200, 500],
		toolbar: '#toolbar-hardwareList',
	    columns: [
	    {
	    	title: 'id',
	    	filed: 'id',
	    	visible: false,
	    },
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
}

function renderHardwaresTable(id){
	// console.log('renderRoomId'+roomId);
	// DCMSBusi.Api.invoke("cabinet/getCabinetsByRoomId",{roomId:roomId}).done((res)=>{
	// 	if(res.status == "1"){
			// var cabinets = res['data'];
			var content = [];
			var hardwares = {};
			hardwares.id = '1sd8sc89asdf087sdxxa';
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
			    	title: 'id',
			    	filed: 'id',
			    	visible: false,
			    },
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

function renderSoftwaresTable(id){
	//需要根据传入业务id返回未绑定的软件
	// console.log('renderRoomId'+roomId);
	// DCMSBusi.Api.invoke("cabinet/getCabinetsByRoomId",{roomId:roomId}).done((res)=>{
	// 	if(res.status == "1"){
			// var cabinets = res['data'];
			var content = [];
			var softwares = {};
			softwares.id = 'asdf123as223dsxg367x';
			softwares.name = '软件名称1';
			softwares.productName = '产品名称1'; 
			softwares.resourceCode = 'A3212';
			softwares.serviceIp = '123.44.11.232';
			softwares.serverPort = '8001';


			content.push(softwares);
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
			$('#softwaresModalTable').bootstrapTable('destroy');
			$('#softwaresModalTable').bootstrapTable({
				search:true,
				striped: true,
	 			pagination: true,
	 			singleSelect: false,
	 			pageNumber: 1,
				pageSize: 8,
	 			pageList: [10, 50, 100, 200, 500],
			    columns: [
			    {
			    	title: 'id',
			    	filed: 'id',
			    	visible: false,
			    },
			    {
	                field: 'state',
	                checkbox: true
	            },
			    {
		        field: 'name',
			        title: '软件名称'
			    },
			    {
		        field: 'productName',
			        title: '产品名称'
			    },
			    {
		        field: 'resourceCode',
			        title: '资源编码'
			    },
			    {
		        field: 'serviceIp',
			        title: '服务IP'
			    },
			    {
		        field: 'serverPort',
			        title: '服务器端口'
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

$('.modal').draggable({
	handle:'.modal-header'
});

$('#confirmSoftwaresBtn').click(function(){
	$('#softwareModal').modal('hide');
	
	if($('#softwareModal').hasClass('softFirst')){
		// 记录选择项返回新增业务界面
		$('#serviceAdd').modal('show');
		var selectedIds = getSoftHardwaresSelected();
	}else{
		//直接完成关联操作
		var selectedIds = getSoftHardwaresSelected();
	}
});
$('#confirmHardwaresBtn').click(function(){
	$('#hardwareModal').modal('hide');
	if($('#hardwareModal').hasClass('hardFirst')){
		//记录选择项返回新增业务界面
		$('#serviceAdd').modal('show');
		var selectedIds = getSoftHardwaresSelected();
	}else{
		//直接完成关联操作
		var selectedIds = getSoftHardwaresSelected();
	}
});

