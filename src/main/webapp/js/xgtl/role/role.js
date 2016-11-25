/**
 * Created by Charles on 2016/10/23.
 *
 */
var dtApi;
function pageInit() {

	// 初始化菜单树
	var dtTable=$("#roleListTable").dataTable({
		/**
		 *
		 * @param data DT 封装的向后台传递的参数
		 * @param callback 回调函数，用户向DT传数据
		 * @param settings 一些设置
		 */
		ajax:function(data, callback, settings){
			console.log(data);
			//需要把分页参数转为DCMS接口规范的,注意：DT的start是随着页数变的，不是页码。
			// var pageNum=data.start/data.length+1,pageSize=data.length;
			// var params={
			// 	pageNum:pageNum,
			// 	pageSize:pageSize,
			// 	codeQuery:$("#searchRoleCode").val(),
			// 	nameQuery:$("#searchRoleName").val()
			// };
			// if(data.order&&data.order.length && data.order[0]){
			// 	params.sortName=data.columns[data.order[0].column].name;
			// 	params.sortDesc=data.order[0].dir;
			// }
			var params=DCMSUtils.DataTables.handleParams(data);
			params.codeQuery=$("#searchRoleCode").val();
			params.nameQuery=$("#searchRoleName").val();
			DCMSBusi.Api.invoke("role/datagrid",params).then(function (data) {
				if(data.status=='1'){
					data=data.data;
					var roleMap={};
					for(var i=0;i<data.records.length;i++){
						roleMap[data.records[i].id]=data.records[i];
					}
					DCMSUtils.SessionStorage.set("ROLE_MAP",roleMap);
					//组织DT标准的返回值
					callback({
						data:data.records,
						recordsTotal:data.count,
						recordsFiltered:data.count
					});
				}
			});
		},
		columns: [
			{title: '角色编码', data: 'rolecode',name:'rolecode'},
			{title: '角色名称', data: 'rolename',name:'rolename'},
			{title: '操作', data: 'rolecode'}
		],
		columnDefs:[{
			orderable:false,
			targets:2,
			render:function(data,type,row,meta){
				var html='<i class="glyphicon glyphicon-cog" title="菜单配置" onclick="roleSetting(\'' + row.id + '\')"></i>&nbsp;&nbsp;' +
					'<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="roleNewUpdate(\'' + row.id + '\',\'update\')"></i>&nbsp;&nbsp;' +
					'<i class="glyphicon glyphicon-trash"  title="删除"     onclick="roleDelete(\'' + row.id + '\')"></i>&nbsp;&nbsp;';
				return html;
			}
		}]
	});
	dtApi=dtTable.api();

	dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

	$("#roleListTable tbody").on( 'click', 'tr', function () {
		if ( $(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		}else {
			dtTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	} );

	$("#queryBtn").click(function(){
		dtApi.ajax.reload();
	});

	$("#resetBtn").click(function(){
		document.getElementById("queryForm").reset();
	});
}

$("#roleModal").draggable();
/**
 * 点击新增/修改
 * @param roleId
 */
function roleNewUpdate(roleId){
	$("#roleId").val(roleId);
	if(roleId){
		var roleMap=DCMSUtils.SessionStorage.get("ROLE_MAP");
		if(roleMap && roleMap[roleId]){
			$("#roleCode").val(roleMap[roleId].rolecode);
			$("#roleName").val(roleMap[roleId].rolename);
		}
	}else{
		document.getElementById("roleNewUpdateForm").reset();
	}
	$("#roleModal").modal('show');
}

/**
 * 新增、更新模态框校验
 * @type {string}
 */
var icon = "<i class='fa fa-times-circle'></i> ";
$("#roleNewUpdateForm").validate({
	rules:{
		roleCode:{
			required:true,
			minlength:2,
			maxlength:20
		},
		roleName:{
			required:true,
			minlength:2,
			maxlength:100
		}
	},
	messages:{
		roleCode:icon + "请输入2-20个字符的角色编码",
		roleName:icon + "请输入2-50个字符的角色名称"
	},
	submitHandler:function(form){
		var roleId=$("#roleId").val();
		var url='role/add';
		if(roleId){
			url='role/update';
		}
		DCMSUtils.Modal.showLoading();
		DCMSBusi.Api.invoke(url,{rolecode:$("#roleCode").val(),rolename:$("#roleName").val(),id:roleId}).then(function(data){
			DCMSUtils.Modal.hideLoading();
			if(data.status=='1'){
				document.getElementById("roleNewUpdateForm").reset();
				$("#roleModal").modal('hide');
				DCMSUtils.Modal.toast('保存角色成功','');
				dtApi.ajax.reload();
			}else{
				DCMSUtils.Modal.toast('保存角色出错'+data.msg,'forbidden');
			}
		},function(error){
			DCMSUtils.Modal.hideLoading();
			DCMSUtils.Modal.toast('保存角色异常','forbidden');
		});
	}
});
/**
 * 删除角色
 * @param roleId
 */
function roleDelete(roleId){
	var roleMap=DCMSUtils.SessionStorage.get("ROLE_MAP");
	DCMSUtils.Modal.confirm('确定删除角色['+roleMap[roleId].rolename+']吗？','',function () {
		DCMSUtils.Modal.showLoading();
		DCMSBusi.Api.invoke('role/delete',{id:roleId}).then(function(data){
			DCMSUtils.Modal.hideLoading();
			if(data.status=='1'){
				DCMSUtils.Modal.toast('删除角色成功','');
				dtApi.ajax.reload();
			}else{
				DCMSUtils.Modal.toast('删除角色出错'+data.msg,'forbidden');
			}
		},function (error) {
			DCMSUtils.Modal.hideLoading();
			DCMSUtils.Modal.toast('删除角色异常','forbidden');
		});
	});
}

/**
 * 转换数据适配js tree
 * @param menuList
 * @param container
 * @param pindex
 */
var jsTreeIndex=0;
function transDataToJsTree(menuList,jsIndex,roleMenuMap){
	for(var i=0;i<menuList.length;i++){
		var menu=menuList[i];
		menu.text=menu.name
		menu.icon=menu.iconId;
		menu.state={};
		if(roleMenuMap[menu.id]){
			menu.state.selected=true;
		}
		if(menu.childMenu && menu.childMenu.length>0){
			menu.state.opened=true;
			menu.children=transDataToJsTree(menu.childMenu,jsIndex,roleMenuMap);
		}
	}
	return menuList;
}

var jsTreeIndex=0;
function transDataToJsTree(menuList,jsIndex,roleMenuMap){
	for(var i=0;i<menuList.length;i++){
		var menu=menuList[i];
		delete menu.url;
		if(roleMenuMap[menu.id]){
			menu.checked=true;
		}
		if(menu.childMenu && menu.childMenu.length>0){
			menu.children=transDataToJsTree(menu.childMenu,jsIndex,roleMenuMap);
		}
	}
	return menuList;
}


var zTreeObj;
var zTreeSetting={
	data:{
		simpleData:{
			enable:true,
			idKey:"id",
			pIdKey:"parentId"
		}
	},
	check:{
		enable:true,
		chkStyle:'checkbox'
	},
	view:{
		nameIsHTML:true
	},
	callback:{
		onClick:zTreeOnClick
	}
}

function roleSetting(roleId){
	$("#roleId").val(roleId);
	DCMSUtils.Modal.showLoading();
	$.when(DCMSBusi.Api.invoke('menu/tree'),DCMSBusi.Api.invoke('menu/role/getRoleId',{roleId:roleId}))
		.then(function(treeData,roleMenuData){
			if(treeData.status!='1'){
				DCMSUtils.Modal.toast('加载菜单树出错:'+treeData.msg,'forbidden');
				return;
			}
			if(roleMenuData.status!='1'){
				DCMSUtils.Modal.toast('加载菜单树出错:'+roleMenuData.msg,'forbidden');
				return;
			}

			var roleMenuMap={};
			if(roleMenuData.data){
				for(var i=0;i<roleMenuData.data.length;i++){
					var rm=roleMenuData.data[i];
					roleMenuMap[rm.menuId]=rm;
				}
			}
			zTreeObj=$.fn.zTree.init($("#roleZtree"),zTreeSetting,transDataToJsTree(treeData.data,jsTreeIndex,roleMenuMap));
			zTreeObj.expandAll(true);
			DCMSUtils.Modal.hideLoading();
			$("#roleSettingDiv").css('display','block');

		},function(error){
			DCMSUtils.Modal.hideLoading();
			DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
		});
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#menuId").val(treeNode.id);
	$("#roleMenuOptContent").css("display",'block');
	$("#menuOptTBody").empty();

	$.when(
		DCMSBusi.Api.invoke('menu/operation/getMenuId',{menuId:treeNode.id}),
		DCMSBusi.Api.invoke('menu/role/getRoleIdAndMenuId',{menuId:treeNode.id,roleId:$("#roleId").val()})
	).then(function(menuData,roleOptData){
		if(menuData.status=='1' && roleOptData.status=='1'){
			$("#menuOptTBody").empty();
			var roleOpts=[];
			if(roleOptData.data && roleOptData.data[0] && roleOptData.data[0].operationId){
				roleOpts=roleOptData.data[0].operationId.split(",");
			}
			if(roleOpts.length>0 && roleOpts.length==menuData.data.length){
				$("#optAllCkb").attr('checked',true);
			}
			for(var i=0;i<menuData.data.length;i++){
				var opt=menuData.data[i];
				var tr='<tr id="tr_'+opt.id+'">';
				tr+='<td><input type="checkbox" name="rmOpt" value="'+opt.id+'" '+ ($.inArray(opt.id,roleOpts)!=-1?'checked':'')+'></td>';
				tr+='<td>'+opt.name+'</td>';
				tr+='</tr>';
				$("#menuOptTBody").append(tr);
			}

		}else{
			DCMSUtils.Modal.toast('获取操作列表出错','forbidden');
		}
	},function(error){
		DCMSUtils.Modal.toast('获取操作列表出错','forbidden');
	});
}

function selectTreeNodeAll(){
	zTreeObj.checkAllNodes(true);
}

function unSelectTreeNodeAll(){
	zTreeObj.checkAllNodes(false);
}

function saveRoleMenus(){
	var selected=zTreeObj.getCheckedNodes(true);
	console.log(selected);
	if(selected.length==0){
		DCMSUtils.Modal.toast('请选择菜单','forbidden');
		return;
	}
	var menuIds="";
	for(var i=0;i<selected.length;i++){
		menuIds+=","+selected[i].id;
	}
	DCMSUtils.Modal.showLoading();
	DCMSBusi.Api.invoke('menu/role/batchMenus',{roleId:$("#roleId").val(),menuIds:menuIds.substring(1)}).then(function(data){
		DCMSUtils.Modal.hideLoading();
		if(data.status=='1'){
			DCMSUtils.Modal.toast('保存角色菜单成功');
		}else{
			DCMSUtils.Modal.toast('保存角色菜单出错'+data.msg,'forbidden');
		}
	},function(error){
		DCMSUtils.Modal.hideLoading();
		DCMSUtils.Modal.toast('保存角色菜单异常','forbidden');
	});
}

//选择菜单操作
function selectOpt(obj) {
	console.log(obj.value);
	if(obj.value=='0'){
		$("input[name='rmOpt']").each(function(e){
			this.checked=true;
		});
		obj.value='1';
	}else {
		obj.value='0';
		$("input[name='rmOpt']").each(function(e){
			this.checked=false;
		});
	}

}

function saveRoleMenuOpts() {
	var checked=$("input[name='rmOpt']:checked");
	if(checked.length==0){
		DCMSUtils.Modal.toast('请选择操作','forbidden');
		return;
	}
	var opts="";
	checked.each(function(e){
		opts+=','+$(this).val();
	});
	DCMSUtils.Modal.showLoading();
	DCMSBusi.Api.invoke('menu/role/add',{roleId:$("#roleId").val(),menuId:$("#menuId").val(),operationId:opts.substring(1)}).then(function(data){
		DCMSUtils.Modal.hideLoading();
		if(data.status=='1'){
			DCMSUtils.Modal.toast('保存操作成功');
		}else{
			DCMSUtils.Modal.toast('保存操作出错'+data.msg,'forbidden');
		}
	},function(error){
		DCMSUtils.Modal.hideLoading();
		DCMSUtils.Modal.toast('保存操作异常','forbidden');
	});
}



