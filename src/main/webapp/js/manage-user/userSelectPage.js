// var user_num = 1;
// var user_pageCount = 1;
var dtApi;
function pageInit(){
	
	// loadUserBody();
	userListLoad();
	// loadDomainRole();
	// selectConfirmBtns();
}
function loadDomainRole(){
	//加载域信息
    var jsTreeIndex=0;
	DCMSUtils.Modal.showLoading();
	// var domainMap=DCMSUtils.SessionStorage.get("Domain_TREE_MAP");
	// if(!domainMap){
	 DCMSUtils.Ajax.doPost('domain/tree').then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            var treeData=transDataToJsTree(data.data,jsTreeIndex);
            // console.log(treeData);
            $('#domainJsTree').jstree({
            	'plugins':['wholerow','checkbox'],
                'core': {
                    'check_callback': true,
                    'data':treeData
                }
            });
            // DCMSUtils.Modal.hideLoading();
            initTreeGird(data.data, 0);
        }else{
            DCMSUtils.Modal.toast('加载组织机构树异常','forbidden');
        }
    },function(error){
        // DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('加载组织机构树异常','forbidden');
    });
	// }

	var domainIndex = 0;
	function initTreeGird(domainTree, parentIndex) {
	    for (var i = 0; i < domainTree.length; i++) {
	        domainIndex++;
	        var domain = domainTree[i];

	        //保存domainTree数据，方便编辑等
	        domainMap=DCMSUtils.SessionStorage.get("Domain_TREE_MAP");
	        if(!domainMap){
	            domainMap={};
	        }
	        domainMap[domain.id]=domain;
	        DCMSUtils.SessionStorage.set("Domain_TREE_MAP",domainMap);

	        //子节点初始化
	        var childDomain=domain.childDoMain;
	        if(childDomain && childDomain.length>0){
	            var pIndex=domainIndex;
	            initTreeGird(childDomain,pIndex);
	        }
	    }
	}
    //加载角色信息
    var da = "";
    // var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
    // if(!rolesMap){
	DCMSUtils.Ajax.doPost("role/getAll",da).done((jsonData)=>{
		var roles = jsonData["data"];
		var content = [];
		var e;
		// var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
        var rolesMap={};
       
		for(var i=0,len=roles.length;i<len;i++){
			e = roles[i];
	        rolesMap[e.rolename]=e;
	        rolesMap[e.id]=e;
	        DCMSUtils.SessionStorage.set("ROLES_MAP",rolesMap);
	        var tmp = {};
	        tmp.id = e.id;
	        tmp.roleName =e.rolename;

	        content.push(tmp);
		}
		$('#roleModalTable').bootstrapTable({
			search:true,
			striped: true,
 			pagination: true,
 			singleSelect: false,
 			pageNumber: 1,
			pageSize: 50,
 			pageList: [10, 50, 100, 200, 500],
		    columns: [
		    {
                field: 'state',
                checkbox: true
            },
		     {
		        field: 'roleName',
		        title: '角色名称'
		    }],
		    data: content
		});
	});
    // }
	
	/**
	 * 转换数据适配js tree
	 * @param domainList
	 * @param container
	 * @param pindex
	 */
	function transDataToJsTree(domainList,jsIndex){
	    for(var i=0;i<domainList.length;i++){
	        var domain=domainList[i];
	        domain.text=domain.name
	        if(domain.childDoMain && domain.childDoMain.length>0){
	            domain.state={'opened': true};
	            domain.children=transDataToJsTree(domain.childDoMain,jsIndex);
	        }
	    }
	    return domainList;
	}
}


function cancleDomainSelect(){
	$("#domainTreeModal").modal('hide');
    // $("#useradd").modal('hide');
    // $("#userupdate").modal('show');
}
function userListLoad(){
	var dtTable=$("#userListTable");
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
			params.username = $("#searchUsername").val();
			params.realname = $("#searchRealname").val();
			params.status = $("#searchStatus").val();
			params.sex = $("#searchSex").val();
			DCMSUtils.Ajax.doPost("user/datagrid",params).then(function (data) {
				
				if(data.status=='1'){
				
					//组织DT标准的返回值
					for(var i=0,len=data.data.records.length;i<len;i++){
						var domains = data.data.records[i].domains[0]?data.data.records[i].domains[0]:[];
						// if(data.data.records[i].domains.length!=0){
							//console.log(domains);
							// console.log(data.data.records.domains[0]["name"]);
						// }
					}
					
					
					callback({
						data:data.data.records,
						recordsTotal:data.data.count,
						recordsFiltered:data.data.count
					});
				}
			});
		},
		columns: [
			{title: '登录号', data: 'username',name:'username'},
			{title: '用户名称', data: 'realname',name:'realname'},
			{title: '角色名称', data: ''},
			{title: '组织机构', data: ''},
			{title: '性别', data: '',name:'sex'},
			{title: '身份证', data: 'identificationno'},
			{title: '手机号', data: 'phone'},
			{title: '邮箱', data: 'email'},
			{title: '电话', data: 'mobile'},
			{title: '激活', data: '',name:'status'},
			{title: '操作', data: ''}
		],
		columnDefs:[
			{
				targets:10,
				render:function(data,type,row,meta){
					var html = "<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+row.id+"\" onclick=\"userUpdateInit(this)\"></i>"+
						"<i style='margin:3px;cursor:pointer' title='修改密码' class='fa fa-key' role=\"presentation\" data-toggle=\"modal\" data-target=\"#usereditpassword\" data-value=\""+row.id+"\" onclick=\"userPasswordInit(this)\"></i>"+
						"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+row.id+"\" onclick=\"userDeleteInit(this)\"></i>";
					return html;
				}
			},
			{	
				targets:2,
				render:function(data,type,row,meta){
					var roles = row.roles;
					var html = '<span>';
					for(var i=0,len=roles.length;i<len;i++){
						if(i == len - 1){
							html += roles[i]['rolename'];
						}else{
							html += roles[i]['rolename'] + ',';
						}
					}
					html += '</span>';
					return html;
				}
			},
			{	
				targets:3,
				render:function(data,type,row,meta){
					var domains = row.domains;
					var html = '<span>';
					for(var i=0,len=domains.length;i<len;i++){
						if(i == len - 1){
							html += domains[i]['name'];
						}else{
							html += domains[i]['name']+',';
						}
					}
					html += '</span>';
					return html;
				}
			},
			{
				targets:4,
				render:function(data,type,row,meta){
					var sex = row.sex;
					var html = '<span>';
					if(sex == 0){
						html += '男';
					}else{
						html += '女';
					}
					return html;
				}
			},
			{
				targets:9,
				render:function(data,type,row,meta){
					var status = row.status;
					var html = '<span>';
					if(status == 0){
						html += '是';
					}else{
						html += '否';
					}
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
// function selectConfirmBtns(){
$("#selectRoleBtn").click(function(){
	if($("#confirmRoleBtn").hasClass('update')){
		$("#confirmRoleBtn").removeClass('update');
	}
	$("#useradd").modal('hide');
    $("#userRoleModal").modal('show');
});

$("#selectDomainBtn").click(function(){
	if($("#confirmDomainBtn").hasClass('update')){
		$("#confirmDomainBtn").removeClass('update');
	}
    $("#useradd").modal('hide');
    $("#domainTreeModal").modal('show');
});
function selectRoleBtn1(){
    $("#userupdate").modal('hide');
    $("#userRoleModal").modal('show');
    if(!$("#confirmRoleBtn").hasClass('update')){
    	$("#confirmRoleBtn").addClass('update');
    } 
}
function selectDomainBtn1(){
    $("#userupdate").modal('hide');
    $("#domainTreeModal").modal('show'); 
    if(!$("#confirmDomainBtn").hasClass('update')){
    	$("#confirmDomainBtn").addClass('update');  
    } 
}
$("#confirmRoleBtn").click(function(){
	var selected = [];
    $("tr[class='selected']").each(function(){
    	var tmp = {};
    	// var id = $(this).find('td').get(1).innerHTML;
    	var name = $(this).find('td').get(1).innerHTML;
    	// tmp.id =id;
    	tmp.name=name;
    	selected.push(tmp);
    });

    if(selected.length==0){
        DCMSUtils.Modal.alert('请选择用户角色','');
        return ;
    }
    var pRole;
    var ids = '';
    var names = '';
    var rolesMap = DCMSUtils.SessionStorage.get("ROLES_MAP");
    for(var i=0,len=selected.length;i<len;i++){
    	pRole = selected[i];
    	if(i == (len -1)){
    		ids += rolesMap[pRole.name]["id"];
    		names += pRole.name;
    	}else{
    		ids += rolesMap[pRole.name]["id"]+',';
	    	names += pRole.name+',';
    	}
    }
    if($("#confirmRoleBtn").hasClass('update')){
		$("#rolePId1").val(ids);
	    $("#rolePName1").text(names);
	    // $("#RoleLevel").text(pRole.rank+1);
	    $("#userRoleModal").modal('hide');
	    $("#useradd").modal('hide');
	    $("#userupdate").modal('show');
	}else{
		$("#rolePId").val(ids);
	    $("#rolePName").text(names);
	    // $("#RoleLevel").text(pRole.rank+1);
	    $("#userRoleModal").modal('hide');
	    $("#userupdate").modal('hide');
	    $("#useradd").modal('show');
	}
});

$("#confirmDomainBtn").click(function(){
    var selected=$("#domainJsTree").jstree(true).get_selected();

    if(selected.length==0){
        DCMSUtils.Modal.alert('请选择组织机构','');
        return ;
    }
    var pDomain;
    var ids = '';
    var names = '';
    for(var i=0,len=selected.length;i<len;i++){
    	pDomain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[selected[i]];
    	if(i == (len -1)){
    		ids += pDomain.id;
    		names += pDomain.name;
    	}else{
    		ids += pDomain.id+',';
	    	names += pDomain.name+' ';
    	}
    }
    if($("#confirmDomainBtn").hasClass('update')){
		$("#domainPId1").val(ids);
	    $("#domainPName1").text(names);
	    $("#domainTreeModal").modal('hide');
	    $("#useradd").modal('hide');
	    $("#userupdate").modal('show');
	}else{
		$("#domainPId").val(ids);
	    $("#domainPName").text(names);
	    $("#domainTreeModal").modal('hide');
	    $("#userupdate").modal('hide');
	    $("#useradd").modal('show');
	}
});
// }