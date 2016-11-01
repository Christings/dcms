// var user_num = 1;
// var user_pageCount = 1;
var dtApi;
function pageInit(){
	loadOrganizationTree();
	// loadUserBody();
	userListLoad();
}
function loadOrganizationTree(){
	//加载域信息
    var jsTreeIndex=0;
	DCMSUtils.Modal.showLoading();
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
            DCMSUtils.Modal.hideLoading();
            initTreeGird(data.data, 0);
        }else{
            DCMSUtils.Modal.toast('加载组织机构树异常','forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('加载组织机构树异常','forbidden');
    });
    //加载角色信息
    var da = "";
	DCMSUtils.Ajax.doPost("role/getAll",da).done((jsonData)=>{
		var roles = jsonData["data"];
		var content = "";
		var e;
		var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
        if(!rolesMap){
            rolesMap={};
        }
		for(var i=0,len=roles.length;i<len;i++){
			e = roles[i];
	        rolesMap[e.id]=e;
	        DCMSUtils.SessionStorage.set("ROLES_MAP",rolesMap);
		}
	});

	$("#selectDomainBtn").click(function(){
        $("#useradd").modal('hide');
        $("#domainTreeModal").modal('show');
	});

	var domainIndex = 0;
	function initTreeGird(domainTree, parentIndex) {
	    for (var i = 0; i < domainTree.length; i++) {
	        domainIndex++;
	        var domain = domainTree[i];

	        //保存domainTree数据，方便编辑等
	        var domainMap=DCMSUtils.SessionStorage.get("Domain_TREE_MAP");
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
	    $("#domainPId").val(ids);
	    $("#domainPName").text(names);
	    // $("#domainLevel").text(pDomain.rank+1);
	    $("#domainTreeModal").modal('hide');
	    $("#userupdate").modal('hide');
	    $("#useradd").modal('show');
	});
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
			if($("th[aria-label^='登录号']").hasClass('sorting_asc')){
				console.log('登录号'+'asc');
			}else{
				console.log('登录号'+'desc');
			}
			var pageNum=data.start/data.length+1,pageSize=data.length;
			var params={
				pageNum:pageNum,
				pageSize:pageSize,
				// usernameSort:$("th[aria-label]"),
				username:$("#searchUsername").val(),
				realname:$("#searchRealname").val(),
				status:$("#searchStatus").val(),
				sex:$("#searchSex").val()
			};
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
			{title: '登录号', data: 'username'},
			{title: '用户名称', data: 'realname'},
			{title: '角色名称', data: ''},
			{title: '组织机构', data: ''},
			{title: '性别', data: ''},
			{title: '身份证', data: 'identificationno'},
			{title: '手机号', data: 'phone'},
			{title: '邮箱', data: 'email'},
			{title: '电话', data: 'mobile'},
			{title: '激活', data: ''},
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
						html += roles[i]['rolename'];
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
						html += domains[i]['name'];
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

	
	// $("th").click(function(){
	// 	var usernameSort = $("th[aria-label^='登录号']").attr("class");			
	// 	console.log('usernameSort'+usernameSort);
	// });
}

//-----------------------------
// function loadUserBody(){
// 	var pageNum = 1;
// 	var pageSize = 10;
// 	var username = "";
// 	var realname = "";
// 	var html_content = "";
// 	var content = "";
// 	console.log("userLoad");
// 	// var getUserData = {pageNum: pageNum,pageSize: pageSize,username: username,realname:realname};
// 	var getUserData = "";
// 	// DCMSUtils.Ajax.doPost("user/datagrid",getUserData).done((jsonData)=>{
// 	DCMSUtils.Ajax.doPost("user/getAll",getUserData).done((jsonData)=>{
// 		//var userInfo = jsonData["data"]["records"];
// 		var userInfo = jsonData["data"];
// 	    // user_pageCount = jsonData["data"]["pageCount"];
// 		var num = 1;
// 		var e;
// 		for(var i=0,len=userInfo.length;i<len;i++){
// 			e = userInfo[i];
// 			var sex;
// 			var status;
// 			switch(e["sex"]){
// 				case 0:
// 					sex = "男";
// 					break;
// 				case 1:
// 					sex = "女";
// 					break;
// 			}
// 			if(e["status"] == 2){
// 				console.log(e["realname"]+"已被删除");
// 				continue;
// 			}
// 			switch(e["status"]){
// 				case 0:
// 					status = "是";
// 					break;
// 				case 1:
// 					status = "否";
// 					break;
// 			}
// 			var roleIds = e["roleIds"];
// 			 var roleNames = '';
// 			// var rolesMap=DCMSUtils.SessionStorage.get("ROLES_MAP");
// 			// for(var j=0,lenj=roleIds.length;j<lenj;j++){
// 			// 	var e = roleIds[i];
// 			// 	if(j == (lenj - 1)){
// 			// 		roleNames += rolesMap[e]['rolename'];
// 			// 	}else{
// 			// 		roleNames += rolesMap[e]['rolename']+',';
// 			// 	}
				
// 			// }
// 			content = "<tr>"+
// 				"<td>"+e["username"]+"</td>"+
// 				"<td>"+e["realname"]+"</td>"+
// 				"<td>"+roleNames+"</td>"+
// 				"<td>"+sex+"</td>"+
// 				"<td>"+e["identificationno"]+"</td>"+
// 				"<td>"+e["phone"]+"</td>"+
// 				"<td>"+e["email"]+"</td>"+
// 				"<td>"+e["mobile"]+"</td>"+
// 				"<td>"+status+"</td>"+
// 				"<td>"+
// 					"<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\"></i>"+
// 					"<i style='margin:3px;cursor:pointer' title='修改密码' class='fa fa-key' role=\"presentation\" data-toggle=\"modal\" data-target=\"#usereditpassword\" data-value=\""+e["id"]+"\" onclick=\"userPasswordInit(this)\"></i>"+
// 					"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\"></i>"+
					
// 					// "<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
// 					// "<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
// 				"</td>"+
// 			"</tr>";
// 			html_content += content;
// 			num++;
// 		}

// 		// var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
// 		// var pagination = function(){
// 		// 	for(var i=1;i<=user_pageCount;i++){
// 		// 		ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
// 		// 	}
// 		// }
// 		// pagination();
// 		// ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
// 		var index1 = document.getElementById("userBody");
// 		index1.innerHTML = html_content;
// 		// var index2 = document.getElementById("userPagination");
// 		// index2.innerHTML = ulContent;
// 		// console.log("userLoad"+userInfo);
// 		// $('.dataTables-example').DataTable({
// 		// 	"bFilter":false
// 		// });
// 		$('.dataTables-example').DataTable({
//             "bFilter":true,
//             "aLengthMenu": [10, 25, 50],
//             "oLanguage":{
//                 "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
//                 "oPaginate": {
//                     "sFirst":    "第一页",
//                     "sPrevious": " 上一页 ",
//                     "sNext":     " 下一页 ",
//                     "sLast":     " 最后一页 "
//                 },
//                 "sInfoFiltered":"(从一共 _MAX_ 条记录中查找)",
//                 "sZeroRecords":"未找到任何相匹配记录",
//             }
//         });
       
//         var A = $('#DataTables_Table_0_info').parent().parent();
//         $('#DataTables_Table_0_length').children().insertBefore(A);
//         $('#DataTables_Table_0_filter').parent().css('display','none');
        
//         var title = $('.dataTables-example thead th').eq(0).text();
//         $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="0" placeholder="Search '+title+'" />' ).prependTo('#upTable');
//         title = $('.dataTables-example thead th').eq(1).text();
//         $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="1" placeholder="Search '+title+'" />' ).prependTo('#upTable');
//         title = $('.dataTables-example thead th').eq(3).text();
//         $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="3" placeholder="Search '+title+'" />' ).prependTo('#upTable');
//         title = $('.dataTables-example thead th').eq(8).text();
//         $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="8" placeholder="Search '+title+'" />' ).prependTo('#upTable');
//         $('<a onclick="" href="javascript:void(0);" style="float:right;margin-left:12px;" class="btn btn-primary ">查询</a>').prependTo('#upTable');

//         var table = $('.dataTables-example').DataTable();
//         $('input[data-id=\'0\']').on('keyup change',function(){
//             table
//                 .column( 0 )
//                 .search( this.value )
//                 .draw();
//         });
//         $('input[data-id=\'1\']').on('keyup change',function(){
//             table
//                 .column( 1 )
//                 .search( this.value )
//                 .draw();
//         });
//         $('input[data-id=\'3\']').on('keyup change',function(){
//             table
//                 .column( 3 )
//                 .search( this.value )
//                 .draw();
//         });
//         $('input[data-id=\'8\']').on('keyup change',function(){
//             table
//                 .column( 8 )
//                 .search( this.value )
//                 .draw();
//         });
// 	});
// }



//------------------------------
// function userSelectPage(){
// 		// var obj = document.getElementById("userSelectPage");
// 		var html_content="";
// 		var htm_final_ele="";
// 		var ulContent="";//分页
// 		var size = $("#userSelectPage option:selected").val();
// 		console.log("size:"+size);
// 		var getMenuData = {pageNum: 1, pageSize: size};
// 		// $.ajax({
// 		// 	// url: "menu/tree",
// 		// 	url: "user/datagrid",
// 		// 	dataType: "json",
// 		// 	//data: "",
// 		// 	data: getMenuData,
// 		// 	type: "post"
// 		// })
// 		DCMSUtils.Ajax.doPost("user/datagrid",getMenuData).done((jsonData)=>{
// 			var userInfo = jsonData["data"]["records"];
// 			user_pageCount = jsonData["data"]["pageCount"];
// 			var num = 1;
// 			var e;
// 			for(var i=0,len=userInfo.length;i<len;i++){
// 				e = userInfo[i];
// 				var sex;
// 				var status;
// 				switch(e["sex"]){
// 					case 0:
// 						sex = "男";
// 						break;
// 					case 1:
// 						sex = "女";
// 						break;
// 				}
// 				switch(e["status"]){
// 					case 0:
// 						status = "未激活";
// 						break;
// 					case 1:
// 						status = "激活";
// 						break;
// 				}
// 				content = "<tr>"+
// 					"<td>"+e["username"]+"</td>"+
// 					"<td>"+e["realname"]+"</td>"+
// 					"<td></td>"+
// 					"<td>"+sex+"</td>"+
// 					"<td>"+e["identificationno"]+"</td>"+
// 					"<td>"+e["phone"]+"</td>"+
// 					"<td>"+e["email"]+"</td>"+
// 					"<td>"+e["mobile"]+"</td>"+
// 					"<td>"+status+"</td>"+
// 					"<td>"+
// 						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
// 						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
// 					"</td>"+
// 				"<tr>";
// 				html_content += content;
// 				num++;
// 			}
// 			// console.log(jsonData);
// 			// console.log("userGridLoad"+count);
// 			var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
// 			var pagination = function(){
// 				for(var i=1;i<=user_pageCount;i++){
// 					ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
// 				}
// 			}
// 			pagination();
// 			ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
// 			var index1 = document.getElementById("userBody");
// 			index1.innerHTML = html_content;
// 			var index2 = document.getElementById("userPagination");
// 			index2.innerHTML = ulContent;
// 			console.log("userLoad"+userInfo);
// 		}).fail((err)=>{

// 		});
// }

// function tablePerfom(user_num,size){
// 	var getMenuData = {pageNum: user_num, pageSize: size};
// 	var html_content="";
// 	// $.ajax({
// 	// 	// url: "menu/tree",
// 	// 	url: "user/datagrid",
// 	// 	dataType: "json",
// 	// 	//data: "",
// 	// 	data: getMenuData,
// 	// 	type: "post"
// 	// })
// 	DCMSUtils.Ajax.doPost("user/datagrid",getMenuData).done((jsonData)=>{
// 		var userInfo = jsonData["data"]["records"];
// 		user_pageCount = jsonData["data"]["pageCount"];
// 		var num = 1;
// 		var e;
// 		for(var i=0,len=userInfo.length;i<len;i++){
// 			e = userInfo[i];
// 			var sex;
// 			var status;
// 			switch(e["sex"]){
// 				case 0:
// 					sex = "男";
// 					break;
// 				case 1:
// 					sex = "女";
// 					break;
// 			}
// 			switch(e["status"]){
// 				case 0:
// 					status = "未激活";
// 					break;
// 				case 1:
// 					status = "激活";
// 					break;
// 			}
// 			content = "<tr>"+
// 				"<td>"+e["username"]+"</td>"+
// 				"<td>"+e["realname"]+"</td>"+
// 				"<td></td>"+
// 				"<td>"+sex+"</td>"+
// 				"<td>"+e["identificationno"]+"</td>"+
// 				"<td>"+e["phone"]+"</td>"+
// 				"<td>"+e["email"]+"</td>"+
// 				"<td>"+e["mobile"]+"</td>"+
// 				"<td>"+status+"</td>"+
// 				"<td>"+
// 					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
// 					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
// 				"</td>"+
// 			"<tr>";
// 			html_content += content;
// 			num++;
// 		}
// 		var index = document.getElementById("userBody");
// 		index.innerHTML = html_content;
// 	}).fail((err)=>{

// 	});
// }

// function selectPage(e){
// 	var size = $("#userSelectPage option:selected").val();
// 	console.log("size:"+size);
// 	user_num = e.getAttribute("data-value");
// 	tablePerfom(user_num,size);
// }

// function pagePlus(){
// 	var size = $("#userSelectPage option:selected").val();
// 	console.log(user_pageCount);
// 	if(user_num < user_pageCount){
// 		user_num++;
// 	}else{
// 		user_num = 1;
// 	}
// 	tablePerfom(user_num,size);
// }

// function pageMinus(){
// 	var size = $("#userSelectPage option:selected").val();
// 	if(user_num > 1){
// 		user_num--;
// 	}else{
// 		user_num = user_pageCount;
// 	}
// 	tablePerfom(user_num,size);
// }