loadRoleBody();
var role_pageCount = 1;
var role_num = 1;
function loadRoleBody(){
	var pageNum = 1;
	var pageSize = 10;
	var html_content = "";
	var content = "";
	console.log("roleLoad");
	var getRoleData = {pageNum: pageNum,pageSize: pageSize};
	$.ajax({
		url:"role/datagrid",
		dataType:"json",
		data: getRoleData,
		type: "post"
	}).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		var roleInfo = jsonData["data"]["records"];
	    role_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=roleInfo.length;i<len;i++){
			e = roleInfo[i];
			content = "<tr>"+
				"<td>"+num+"</td>"+
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
		// console.log(jsonData);
		// console.log("userGridLoad"+count);
		var ulContent =" <ul class=\"pagination\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
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
		console.log("roleLoad"+roleInfo);
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
		$.ajax({
			// url: "menu/tree",
			url: "role/datagrid",
			dataType: "json",
			//data: "",
			data: getMenuData,
			type: "post"
		}).done((jsonData)=>{
			var roleInfo = jsonData["data"]["records"];
			role_pageCount = jsonData["data"]["pageCount"];
			var num = 1;
			var e;
			for(var i=0,len=roleInfo.length;i<len;i++){
				e = roleInfo[i];
				// console.log("e:"+e);
				// console.log(e["username"]);
				content = "<tr>"+
					"<td>"+num+"</td>"+
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
			// console.log(jsonData);
			// console.log("userGridLoad"+count);
			var ulContent =" <ul class=\"pagination\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
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
	$.ajax({
		// url: "menu/tree",
		url: "role/datagrid",
		dataType: "json",
		//data: "",
		data: getMenuData,
		type: "post"
	}).done((jsonData)=>{
		var roleInfo = jsonData["data"]["records"];
		role_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=roleInfo.length;i<len;i++){
			e = roleInfo[i];
			content = "<tr>"+
				"<td>"+num+"</td>"+
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
