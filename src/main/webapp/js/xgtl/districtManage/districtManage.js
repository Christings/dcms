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

function selectRoom(){
	var roomId = $("#districtRoomId").val();
	console.log(roomId);
	$("#selectRoomId").val(roomId);
}
function selectRoom1(){
	var roomId = $("#districtRoomId1").val();
	console.log(roomId);
	$("#selectRoomId1").val(roomId);
}

$("#selectCabinetsBtn").click(function(){
	
    var roomId = $("#selectRoomId").val();
    if(roomId == ''){
		DCMSUtils.Modal.toast("请先选择区域所属机房",'');
		return false;
	}
	if($("#confirmCabinetsBtn").hasClass('update')){
		$("#confirmCabinetsBtn").removeClass('update');
	}
	$("#districtAdd").modal('hide');
    $("#districtCabinetsModal").modal('show');
    console.log('roomId'+roomId);
    renderCabinetsTable(roomId);
});

function selectCabinetsBtn1(){
	var roomId = $("#selectRoomId1").val();
	console.log('update-roomId'+roomId);
    if(roomId == ''){
		DCMSUtils.Modal.toast("请先选择区域所属机房",'');
		return false;
	}
	if($("#confirmCabinetsBtn").hasClass('update')){
		$("#confirmCabinetsBtn").removeClass('update');
	}
	$("#districtUpdate").modal('hide');
    $("#districtCabinetsModal").modal('show');
    if(!$("#confirmCabinetsBtn").hasClass('update')){
    	$("#confirmCabinetsBtn").addClass('update');
    } 
    renderCabinetsTable(roomId);
}

function renderCabinetsTable(roomId){
	// console.log('renderRoomId'+roomId);
	DCMSBusi.Api.invoke("cabinet/getCabinetsByRoomId",{roomId:roomId}).done((res)=>{
		if(res.status == "1"){
			var cabinets = res['data'];
			var content = [];

			for(var i=0,len=cabinets.length;i<len;i++){
				e = cabinets[i];
				var tmp = {};
				tmp.id = e.id;
				tmp.resourceCode = e.resourceCode;
				content.push(tmp);
			}
			console.log('cabinetsTbale'+cabinets.length);
			console.log('cabinetsTbaleContent'+content);
			$('#cabinetsModalTable').bootstrapTable('destroy');
			$('#cabinetsModalTable').bootstrapTable({
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
		        field: 'resourceCode',
			        title: '机柜资源编码'
			    }],
			    data: content
			});
			return true;
		}else{
			DCMSUtils.Modal.toast("获取机柜列表失败"+res.msg,'');
			return false;
		}
	});
}



$("#confirmCabinetsBtn").click(function(){
	var selected = [];
	var selectedFlag = 0;
    $("tr[class='selected']").each(function(){
    	var tmp = {};
    	// var id = $(this).find('td').get(1).innerHTML;
    	var resourceCode = $(this).find('td').get(1).innerHTML;
    	// tmp.id =id;
    	tmp.resourceCode=resourceCode;
    	selected.push(tmp);
    });

    if(selected.length > 0){
        selectedFlag = 1;
    }
    var cabinetsResourceCode = '';
    for(var i=0,len=selected.length;i<len;i++){
    	var cabinet = selected[i];
    	if(i == (len -1)){
    		cabinetsResourceCode += cabinet['resourceCode'];
    	}else{
    		cabinetsResourceCode += cabinet['resourceCode']+',';
    	}
    }

    if($("#confirmCabinetsBtn").hasClass('update')){
		$("#cabinetResourceCodes1").val(cabinetsResourceCode);
		if(selectedFlag == 1){
			$("#cRCName1").text('已选择');
		}else{
			$("#cRCName1").text('未选择');
		}
	    	
	    // $("#RoleLevel").text(pRole.rank+1);
	    $("#districtCabinetsModal").modal('hide');
	    $("#districtAdd").modal('hide');
	    $("#districtUpdate").modal('show');
	}else{
		$("#cabinetResourceCodes").val(cabinetResourceCodes);
		if(selectedFlag == 1){
			$("#cRCName").text('已选择');
		}else{
			$("#cRCName").text('未选择');
		}
	    // $("#RoleLevel").text(pRole.rank+1);
	    $("#districtCabinetsModal").modal('hide');
	    $("#districtUpdate").modal('hide');
	    $("#districtAdd").modal('show');
	}
});