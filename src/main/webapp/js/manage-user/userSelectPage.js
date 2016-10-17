// var user_num = 1;
// var user_pageCount = 1;
function pageInit(){
	loadUserBody();
	loadOrganizationTree();
}
function loadOrganizationTree(){
	var st = '[{"id":1,'+
			'"text":"北京三源合众科技有限公司",'+
			'"children":[{'+
				'"id":12,'+
				'"text":"研发部",'+
				'"state":"closed",'+
				'"children":[{'+
					'"id":111,'+
					'"text":"研发一组"},{'+
					'"id":112,'+
					'"text":"研发二组"},{'+
					'"id":113,'+
					'"text":"研发三组"'+
				'}]'+
			'},{'+
				'"id":11,'+
				'"text":"测试部",'+
				'"children":[{'+
					'"id":121,'+
					'"text":"测试一组"},{'+
					'"id":122,'+
					'"text":"测试二组",'+
					'"attributes":{'+
						'"p1":"Custom Attribute1",'+
						'"p2":"Custom Attribute2"}'+
				'},{'+
					'"id":123,'+
					'"text":"测试三组"'+
				'},{'+
					'"id":124,'+
					'"text":"测试四组",'+
					'"checked":true'+
				'}]'+
			'},{'+
				'"id":13,'+
				'"text":"销售部"'+
			'},{'+
				'"id":14,'+
				'"text":"人力资源部"'+
			'},{'+
				'"id":15,'+
				'"text":"指挥部"'+
			'}]'+
		'}]';
		var myJson1 = eval(st);
         $('#tt').tree({
             data: myJson1
         });
}
function loadUserBody(){
	var pageNum = 1;
	var pageSize = 10;
	var username = "";
	var realname = "";
	var html_content = "";
	var content = "";
	console.log("userLoad");
	// var getUserData = {pageNum: pageNum,pageSize: pageSize,username: username,realname:realname};
	var getUserData = "";
	// DCMSUtils.Ajax.doPost("user/datagrid",getUserData).done((jsonData)=>{
	DCMSUtils.Ajax.doPost("user/getAll",getUserData).done((jsonData)=>{
		//var userInfo = jsonData["data"]["records"];
		var userInfo = jsonData["data"];
	    // user_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=userInfo.length;i<len;i++){
			e = userInfo[i];
			var sex;
			var status;
			switch(e["sex"]){
				case 0:
					sex = "男";
					break;
				case 1:
					sex = "女";
					break;
			}
			if(e["status"] == 2){
				console.log(e["realname"]+"已被删除");
				continue;
			}
			switch(e["status"]){
				case 0:
					status = "是";
					break;
				case 1:
					status = "否";
					break;
			}
			content = "<tr>"+
				"<td>"+e["username"]+"</td>"+
				"<td>"+e["realname"]+"</td>"+
				"<td></td>"+
				"<td>"+sex+"</td>"+
				"<td>"+e["identificationno"]+"</td>"+
				"<td>"+e["phone"]+"</td>"+
				"<td>"+e["email"]+"</td>"+
				"<td>"+e["mobile"]+"</td>"+
				"<td>"+status+"</td>"+
				"<td>"+
					"<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' title='修改密码' class='fa fa-key' role=\"presentation\" data-toggle=\"modal\" data-target=\"#usereditpassword\" data-value=\""+e["id"]+"\" onclick=\"userPasswordInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\"></i>"+
					
					// "<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
					// "<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
				"</td>"+
			"</tr>";
			html_content += content;
			num++;
		}

		// var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
		// var pagination = function(){
		// 	for(var i=1;i<=user_pageCount;i++){
		// 		ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
		// 	}
		// }
		// pagination();
		// ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
		var index1 = document.getElementById("userBody");
		index1.innerHTML = html_content;
		// var index2 = document.getElementById("userPagination");
		// index2.innerHTML = ulContent;
		// console.log("userLoad"+userInfo);
		// $('.dataTables-example').DataTable({
		// 	"bFilter":false
		// });
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
        title = $('.dataTables-example thead th').eq(3).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="3" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        title = $('.dataTables-example thead th').eq(8).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="8" placeholder="Search '+title+'" />' ).prependTo('#upTable');
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
        $('input[data-id=\'3\']').on('keyup change',function(){
            table
                .column( 3 )
                .search( this.value )
                .draw();
        });
        $('input[data-id=\'8\']').on('keyup change',function(){
            table
                .column( 8 )
                .search( this.value )
                .draw();
        });
	});
}
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