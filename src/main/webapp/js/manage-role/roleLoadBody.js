loadRoleBody();
var role_pageCount = 1;
var role_num = 1;
function loadRoleBody(){
	var pageNum = 1;
	var pageSize = 10;
	var html_content = "";
	var content = "";
	console.log("roleLoad");
	var getRoleData = "";
	// var getRoleData = {pageNum: pageNum,pageSize: pageSize};
	// $.ajax({
	// 	url:"role/datagrid",
	// 	dataType:"json",
	// 	data: getRoleData,
	// 	type: "post"
	// })
	DCMSUtils.Ajax.doPost("role/getAll",getRoleData).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		var roleInfo = jsonData["data"];
	    // role_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=roleInfo.length;i<len;i++){
			e = roleInfo[i];
			content = "<tr>"+
				"<td>"+e["rolecode"]+"</td>"+
				"<td>"+e["rolename"]+"</td>"+
				"<td>"+
					"<i style='margin:3px;cursor:pointer' role=\"presentation\" title='编辑' class='fa fa-pencil' data-toggle=\"modal\" data-target=\"#roleUpdate\" data-value=\""+e["id"]+"\" onclick=\"roleUpdateInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' role=\"presentation\" title='角色用户关联' class='fa fa-user' data-toggle=\"modal\" data-target=\"#roleUser\" data-value=\""+e["id"]+"\" onclick=\"roleUserInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' role=\"presentation\" title='角色菜单关联' class='fa fa-lock' data-toggle=\"modal\" data-target=\"#roleMenu\" data-value=\""+e["id"]+"\" onclick=\"roleMenuInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' role=\"presentation\" title='删除' class='fa fa-trash' data-toggle=\"modal\" data-target=\"#roleDelete\" data-value=\""+e["id"]+"\" onclick=\"roleDeleteInit(this)\"></i>"+
				"</td>"+
			"</tr>";
			html_content += content;
			// num++;
		}
		
		// var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
		// var pagination = function(){
		// 	for(var i=1;i<=role_pageCount;i++){
		// 		ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
		// 	}
		// }
		// pagination();
		// ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
		var index1 = document.getElementById("roleBody");
		index1.innerHTML = html_content;
		// var index2 = document.getElementById("rolePagination");
		// index2.innerHTML = ulContent;
		// console.log("roleLoad"+roleInfo);

		$('.dataTables-example').DataTable({
            "bFilter":true,
            "aLengthMenu": [10, 25, 50],
            "oLanguage":{
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "oPaginate": {
                    "sFirst":    "第一页",
                    "sPrevious": " 上一页 ",
                    "sNext":     " 下一页 ",
                    "sLast":     " 最后一页 "
                },
                "sInfoFiltered":"(从一共 _MAX_ 条记录中查找)",
                "sZeroRecords":"未找到任何相匹配记录",
            }
            
        });
       
        var A = $('#DataTables_Table_0_info').parent().parent();
        $('#DataTables_Table_0_length').children().insertBefore(A);
        $('#DataTables_Table_0_filter').parent().css('display','none');
        
        var title = $('.dataTables-example thead th').eq(0).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="0" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        title = $('.dataTables-example thead th').eq(1).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="1" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        // title = $('.dataTables-example thead th').eq(3).text();
        // $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="3" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        // title = $('.dataTables-example thead th').eq(8).text();
        // $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="8" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        $('<a onclick="" href="javascript:void(0);" style="float:right;margin-left:12px;" class="btn btn-primary ">查询</a>').prependTo('#upTable');

        var table = $('.dataTables-example').DataTable();
        $('input[data-id=\'0\']').on('keyup change',function(){
            table
                .column( 0 )
                .search( this.value )
                .draw();
        });
        $('input[data-id=\'1\']').on('keyup change',function(){
            table
                .column( 1 )
                .search( this.value )
                .draw();
        });
        // $('input[data-id=\'3\']').on('keyup change',function(){
        //     table
        //         .column( 3 )
        //         .search( this.value )
        //         .draw();
        // });
        // $('input[data-id=\'8\']').on('keyup change',function(){
        //     table
        //         .column( 8 )
        //         .search( this.value )
        //         .draw();
        // });
	});
}

function roleSelectPage(){
		// var obj = document.getElementById("userSelectPage");
		var html_content="";
		var htm_final_ele="";
		var ulContent="";//分页
		var size = $("#roleSelectPage option:selected").val();
		console.log("size:"+size);
		var getMenuData = {pageNum: 1, pageSize: size};
		// $.ajax({
		// 	// url: "menu/tree",
		// 	url: "role/datagrid",
		// 	dataType: "json",
		// 	//data: "",
		// 	data: getMenuData,
		// 	type: "post"
		// })
		DCMSUtils.Ajax.doPost("role/datagrid",getMenuData).done((jsonData)=>{
			var roleInfo = jsonData["data"]["records"];
			role_pageCount = jsonData["data"]["pageCount"];
			var num = 1;
			var e;
			for(var i=0,len=roleInfo.length;i<len;i++){
				e = roleInfo[i];
				// console.log("e:"+e);
				// console.log(e["username"]);
				content = "<tr>"+
					"<td>"+e["rolecode"]+"</td>"+
					"<td>"+e["rolename"]+"</td>"+
					"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleUpdate\" data-value=\""+e["id"]+"\" onclick=\"roleUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleUser\" data-value=\""+e["id"]+"\" onclick=\"roleUserInit(this)\">用户</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleMenu\" data-value=\""+e["id"]+"\" onclick=\"roleMenuInit(this)\">权限设置</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleDelete\" data-value=\""+e["id"]+"\" onclick=\"roleDeleteInit(this)\">删除</label>|"+
				"</td>"+
				"</tr>";
				html_content += content;
				num++;
			}
			// console.log(jsonData);
			// console.log("userGridLoad"+count);
			var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
			var pagination = function(){
				for(var i=1;i<=role_pageCount;i++){
					ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
				}
			}
			pagination();
			ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
			var index1 = document.getElementById("roleBody");
			index1.innerHTML = html_content;
			var index2 = document.getElementById("rolePagination");
			index2.innerHTML = ulContent;
			// console.log("userLoad"+userInfo);
		}).fail((err)=>{

		});
}

function tablePerfom(role_num,size){
	var getMenuData = {pageNum: role_num, pageSize: size};
	var html_content="";
	// $.ajax({
	// 	// url: "menu/tree",
	// 	url: "role/datagrid",
	// 	dataType: "json",
	// 	//data: "",
	// 	data: getMenuData,
	// 	type: "post"
	// })
	DCMSUtils.Ajax.doPost("role/datagrid",getMenuData).done((jsonData)=>{
		var roleInfo = jsonData["data"]["records"];
		role_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=roleInfo.length;i<len;i++){
			e = roleInfo[i];
			content = "<tr>"+
				"<td>"+e["rolecode"]+"</td>"+
				"<td>"+e["rolename"]+"</td>"+
				"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleUpdate\" data-value=\""+e["id"]+"\" onclick=\"roleUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleUser\" data-value=\""+e["id"]+"\" onclick=\"roleUserInit(this)\">用户</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleMenu\" data-value=\""+e["id"]+"\" onclick=\"roleMenuInit(this)\">权限设置</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#roleDelete\" data-value=\""+e["id"]+"\" onclick=\"roleDeleteInit(this)\">删除</label>|"+
				"</td>"+
			"<tr>";
			html_content += content;
			num++;
		}
		var index = document.getElementById("roleBody");
		index.innerHTML = html_content;
	}).fail((err)=>{

	});
}

function selectPage(e){
	var size = $("#roleSelectPage option:selected").val();
	console.log("size:"+size);
	role_num = e.getAttribute("data-value");
	tablePerfom(role_num,size);
}

function pagePlus(){
	var size = $("#roleSelectPage option:selected").val();
	console.log(role_pageCount);
	if(role_num < role_pageCount){
		role_num++;
	}else{
		role_num = 1;
	}
	tablePerfom(role_num,size);
}

function pageMinus(){
	var size = $("#roleSelectPage option:selected").val();
	if(role_num > 1){
		role_num--;
	}else{
		role_num = role_pageCount;
	}
	tablePerfom(role_num,size);
}
