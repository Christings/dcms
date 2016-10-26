/**
 * Created by Charles on 2016/10/23.
 *
 */
/**
 * 页面初始化
 */
var dtApi;
function pageInit() {

	// 初始化菜单树
	var dtTable=$("#roleListTable");
	dtApi=dtTable.dataTable({
		/**
		 *
		 * @param data DT 封装的向后台传递的参数
		 * @param callback 回调函数，用户向DT传数据
		 * @param settings 一些设置
		 */
		ajax:function(data, callback, settings){
			//需要把分页参数转为DCMS接口规范的
			var pageNum=data.start/2+1,pageSize=data.length;
			var params={
				pageNum:pageNum,
				pageSize:pageSize,
				codeQuery:$("#searchRoleCode").val(),
				nameQuery:$("#searchRoleName").val()
			};
			DCMSUtils.Ajax.doPost("role/datagrid",params).then(function (data) {
				if(data.status=='1'){
					//组织DT标准的返回值
					callback({
						data:data.data.records,
						recordsTotal:data.data.count,
						recordsFiltered:data.data.count
					});
				}
			});
		},
		columns: [
			{title: '角色编码', data: 'rolecode'},
			{title: '角色名称', data: 'rolename'},
			{title: '操作', data: 'rolecode'}
		],
		columnDefs:[{
			targets:2,
			render:function(data,type,row,meta){
				var html='<i class="glyphicon glyphicon-cog" title="菜单配置" onclick="roleSetting(\'' + row.id + '\')"></i>&nbsp;&nbsp;' +
					'<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="roleNewUpdate(\'' + row.id + '\',\'update\')"></i>&nbsp;&nbsp;' +
					'<i class="glyphicon glyphicon-trash"  title="删除"     onclick="roleDelete(\'' + row.id + '\')"></i>&nbsp;&nbsp;';
				return html;
			}
		}]
	}).api();

	dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

	$("#queryBtn").click(function(){
		dtApi.ajax.reload();
	});

	$("#resetBtn").click(function(){
		document.getElementById("queryForm").reset();
	});
}



