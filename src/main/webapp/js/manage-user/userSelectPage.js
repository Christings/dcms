var user_num = 1;
var user_pageCount = 1;
loadUserBody();
function loadUserBody(){
	var pageNum = 1;
	var pageSize = 10;
	var username = "";
	var realname = "";
	var html_content = "";
	var content = "";
	console.log("userLoad");
	var getUserData = {pageNum: pageNum,pageSize: pageSize,username: username,realname:realname};
	$.ajax({
		url:"user/datagrid",
		dataType:"json",
		data: getUserData,
		type: "post"
	}).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		var userInfo = jsonData["data"]["records"];
	    user_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=userInfo.length;i<len;i++){
			e = userInfo[i];
			var sex;
			var status;
			switch(e["sex"]){
				case '0':
					sex = "男";
					break;
				case '1':
					sex = "女";
					break;
			}
			switch(e["status"]){
				case '0':
					status = "未激活";
					break;
				case '1':
					status = "激活";
					break;
			}
			content = "<tr>"+
				"<td>"+e["username"]+"</td>"+
				"<td>"+e["realname"]+"</td>"+
				"<td>"+sex+"</td>"+
				"<td>"+e["identificationno"]+"</td>"+
				"<td>"+e["phone"]+"</td>"+
				"<td>"+e["email"]+"</td>"+
				"<td>"+e["mobile"]+"</td>"+
				"<td>"+status+"</td>"+
				"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
				"</td>"+
			"</tr>";
			html_content += content;
			num++;
		}
		// console.log(jsonData);
		// console.log("userGridLoad"+count);
		var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
		var pagination = function(){
			for(var i=1;i<=user_pageCount;i++){
				ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
			}
		}
		pagination();
		ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
		var index1 = document.getElementById("userBody");
		index1.innerHTML = html_content;
		var index2 = document.getElementById("userPagination");
		index2.innerHTML = ulContent;
		console.log("userLoad"+userInfo);
	});
}
function userSelectPage(){
		// var obj = document.getElementById("userSelectPage");
		var html_content="";
		var htm_final_ele="";
		var ulContent="";//分页
		var size = $("#userSelectPage option:selected").val();
		console.log("size:"+size);
		var getMenuData = {pageNum: 1, pageSize: size};
		$.ajax({
			// url: "menu/tree",
			url: "user/datagrid",
			dataType: "json",
			//data: "",
			data: getMenuData,
			type: "post"
		}).done((jsonData)=>{
			var userInfo = jsonData["data"]["records"];
			user_pageCount = jsonData["data"]["pageCount"];
			var num = 1;
			var e;
			for(var i=0,len=userInfo.length;i<len;i++){
				e = userInfo[i];
				var sex;
				var status;
				switch(e["sex"]){
					case '0':
						sex = "男";
						break;
					case '1':
						sex = "女";
						break;
				}
				switch(e["status"]){
					case '0':
						status = "未激活";
						break;
					case '1':
						status = "激活";
						break;
				}
				content = "<tr>"+
					"<td>"+e["username"]+"</td>"+
					"<td>"+e["realname"]+"</td>"+
					"<td>"+sex+"</td>"+
					"<td>"+e["identificationno"]+"</td>"+
					"<td>"+e["phone"]+"</td>"+
					"<td>"+e["email"]+"</td>"+
					"<td>"+e["mobile"]+"</td>"+
					"<td>"+status+"</td>"+
					"<td>"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
					"</td>"+
				"<tr>";
				html_content += content;
				num++;
			}
			// console.log(jsonData);
			// console.log("userGridLoad"+count);
			var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
			var pagination = function(){
				for(var i=1;i<=user_pageCount;i++){
					ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
				}
			}
			pagination();
			ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
			var index1 = document.getElementById("userBody");
			index1.innerHTML = html_content;
			var index2 = document.getElementById("userPagination");
			index2.innerHTML = ulContent;
			console.log("userLoad"+userInfo);
		}).fail((err)=>{

		});
}

function tablePerfom(user_num,size){
	var getMenuData = {pageNum: user_num, pageSize: size};
	var html_content="";
	$.ajax({
		// url: "menu/tree",
		url: "user/datagrid",
		dataType: "json",
		//data: "",
		data: getMenuData,
		type: "post"
	}).done((jsonData)=>{
		var userInfo = jsonData["data"]["records"];
		user_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=userInfo.length;i<len;i++){
			e = userInfo[i];
			var sex;
			var status;
			switch(e["sex"]){
				case '0':
					sex = "男";
					break;
				case '1':
					sex = "女";
					break;
			}
			switch(e["status"]){
				case '0':
					status = "未激活";
					break;
				case '1':
					status = "激活";
					break;
			}
			content = "<tr>"+
				"<td>"+e["username"]+"</td>"+
				"<td>"+e["realname"]+"</td>"+
				"<td>"+sex+"</td>"+
				"<td>"+e["identificationno"]+"</td>"+
				"<td>"+e["phone"]+"</td>"+
				"<td>"+e["email"]+"</td>"+
				"<td>"+e["mobile"]+"</td>"+
				"<td>"+status+"</td>"+
				"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userupdate\" data-value=\""+e["id"]+"\" onclick=\"userUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#userdelete\" data-value=\""+e["id"]+"\" onclick=\"userDeleteInit(this)\">删除</label>"+
				"</td>"+
			"<tr>";
			html_content += content;
			num++;
		}
		var index = document.getElementById("userBody");
		index.innerHTML = html_content;
	}).fail((err)=>{

	});
}

function selectPage(e){
	var size = $("#userSelectPage option:selected").val();
	console.log("size:"+size);
	user_num = e.getAttribute("data-value");
	tablePerfom(user_num,size);
}

function pagePlus(){
	var size = $("#userSelectPage option:selected").val();
	console.log(user_pageCount);
	if(user_num < user_pageCount){
		user_num++;
	}else{
		user_num = 1;
	}
	tablePerfom(user_num,size);
}

function pageMinus(){
	var size = $("#userSelectPage option:selected").val();
	if(user_num > 1){
		user_num--;
	}else{
		user_num = user_pageCount;
	}
	tablePerfom(user_num,size);
}