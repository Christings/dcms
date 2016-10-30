/**
 * Created by geng on 2016/10/25.
 *
 */
/**
 * 页面初始化
 */
var dtApi;
function pageInit() {

	// 初始化菜单树
	var dtTable=$("#data-table");
	dtApi=dtTable.DataTable({
		/**
		 *
		 * @param data DT 封装的向后台传递的参数
		 * @param callback 回调函数，用户向DT传数据
		 * @param settings 一些设置
		 */
		ajax:function(data, callback, settings){
			//需要把分页参数转为DCMS接口规范的
			var pageNum=data.start/data.length+1,pageSize=data.length;
			var params={
				pageNum:pageNum,
				pageSize:pageSize,
				deviceName:$("#deviceName").val(),
				operUserName:$("#operUserName").val(),
				operDate:$("#operDate").val()
			};
			DCMSUtils.Ajax.doPost("operLog/datagrid",params).then(function (data) {
				if(data.status=='1'){
					//组织DT标准的返回值
					callback({
						data:data.data.records,
						recordsTotal:data.data.records.length,
						recordsFiltered:data.data.records.length
					});
				}
			});
		},
		columns: [
			{title: '设备名称', data: 'deviceName'},
			{title: '操作类型', data: 'operType'},
			{title: '日志级别', data: 'logLevel'},
			{title: '日志操作的具体类别', data: 'actonType'},
			{title: '操作的具体内容', data: 'operProp'},
			{title: '操作人姓名', data: 'operUserName'},
			{title: '操作日期', data: 'operDate'},
			{title: '日志类型', data: 'logType'}
		]
	});

	dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

	//点击查询
	$("#logQueryBtn").on("click", function () {
		dtApi.ajax.reload();
	});
}



