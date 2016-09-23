var menuTree;
var elements;
var pageCount;


function loadMenuBody(){
		var htm_ele="";
		var htm_final_ele="";
		var ulContent="";//分页
		var getMenuData = {pageNum: 1, pageSize: 10};
		$.ajax({
			// url: "menu/tree",
			url: "menu/datagrid",
			dataType: "json",
			//data: "",
			data: getMenuData,
			type: "post"
		}).done((jsonData)=>{
			menuTree = jsonData["data"]["records"];
			pageCount = jsonData["data"]["pageCount"];
	
			elements = parseTreeJson(menuTree);
			for(var i=0,len=elements.length;i<len;i++){
				htm_ele+=elements[i];
			}
			var articleId = function(){
				var segments = htm_ele.split(",");
				var length = segments.length - 1;
				for(var i=0;i<=length;i++){
					htm_final_ele += segments[i];
				}
			}
			articleId();
			ulContent +=" <ul class=\"pagination\" style=\"margin:0;padding:0;float:right\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
			var pagination = function(){
				for(var i=1;i<=pageCount;i++){
					ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
				}
			}
			pagination();
			ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
			// console.log(htm_final_ele);
			var index = document.getElementById("menuBody");
			index.innerHTML = htm_final_ele;
			var index2 = document.getElementById("menuPagination");
			index2.innerHTML = ulContent;
			
		}).fail((err)=>{

		});
}

function parseTreeJson(treeNodes){
	if(treeNodes == null || treeNodes.length == 0){
		return;
	}
	var arr = [];
	var childElement = [];

	for(var j = 0; j < treeNodes.length; j++)
	{
		arr.push({
			name: treeNodes[j]["name"],
			iconId: treeNodes[j]["iconId"],
			level: treeNodes[j]["level"],
			id: treeNodes[j]["id"],
			childMenu: treeNodes[j]["childMenu"],
			rank: treeNodes[j]["rank"],
			type: treeNodes[j]["type"],
			parentId: treeNodes[j]["parentId"]	
		});
	
		var childs = treeNodes[j]["childMenu"];
		childElement[j] = parseTreeJson(childs);
	}
	var distance = arr[0]["level"]*20 - 20;
	// console.log("distance::"+distance);
	var left = "margin:auto auto 0 "+ distance +"px";
	var liStyle_level_1 = "margin: 0";
	var liStyle = "margin: 0;padding: 0";
	var ulStyle = "padding: 0;";
	// var listStyles = {margin: left};
	var temp = -1;
	var element = arr.map(function(e){
		temp++;
		var url = "/" + e["id"];

		// console.log("Collapse" + that.props.menuOpen[e["id"]]);

		if(e["childMenu"] != null){
			var index = e["id"];
			// console.log(e["name"]);
			if(e["level"]!=1){
				return(
					"<tr class=\"collapse "+e["parentId"]+"\">"+
						"<td><a style=\""+ left +"\" href=\"."+e["id"]+"\"class=\"table-header collapsed\" data-toggle=\"collapse\">"+e["name"]+"<span class=\"pull-right glyphicon glyphicon-chevron-toggle\"></span></a></td>"+
						"<td>"+e["iconId"]+"</td>"+
						"<td>"+e["type"]+"</td>"+
						"<td>"+e["id"]+"</td>"+
						"<td>"+e["level"]+"</td>"+
						"<td>"+e["rank"]+"</td>"+
						"<td>"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menuupdate\" data-value=\""+e["id"]+"\" onclick=\"menuUpdateInit(this)\">编辑</label>|"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menudelete\" data-value=\""+e["id"]+"\" onclick=\"menuDeleteInit(this)\">删除</label>"+
						"</td>"+
					"</tr>"+
					childElement[temp]
				);

			}else{
				return(
					"<tr>"+
						"<td><a style=\""+ left +"\" href=\"."+e["id"]+"\"class=\"table-header collapsed\" data-toggle=\"collapse\">"+e["name"]+"<span class=\"pull-right glyphicon glyphicon-chevron-toggle\"></span></a></td>"+
						"<td>"+e["iconId"]+"</td>"+
						"<td>"+e["type"]+"</td>"+
						"<td>"+e["id"]+"</td>"+
						"<td>"+e["level"]+"</td>"+
						"<td>"+e["rank"]+"</td>"+
						"<td>"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menuupdate\" data-value=\""+e["id"]+"\" onclick=\"menuUpdateInit(this)\">编辑</label>|"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menudelete\" data-value=\""+e["id"]+"\" onclick=\"menuDeleteInit(this)\">删除</label>"+
						"</td>"+
					"</tr>"+
					childElement[temp]
				);
			}
		}
		else{
			if(e["level"]!=1){
				
				return(
					"<tr class=\"collapse "+e["parentId"]+"\">"+
						"<td><a style=\""+ left +"\" href=\"."+e["id"]+"\">"+e["name"]+"</a></td>"+
						"<td>"+e["iconId"]+"</td>"+
						"<td>"+e["type"]+"</td>"+
						"<td>"+e["id"]+"</td>"+
						"<td>"+e["level"]+"</td>"+
						"<td>"+e["rank"]+"</td>"+
						"<td>"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menuupdate\" data-value=\""+e["id"]+"\" onclick=\"menuUpdateInit(this)\">编辑</label>|"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menudelete\" data-value=\""+e["id"]+"\" onclick=\"menuDeleteInit(this)\">删除</label>"+
						"</td>"+
					"</tr>"
				);
			}else{
				return(
					"<tr>"+
						"<td><a style=\""+ left +"\" href=\"."+e["id"]+"\">"+e["name"]+"</a></td>"+
						"<td>"+e["iconId"]+"</td>"+
						"<td>"+e["type"]+"</td>"+
						"<td>"+e["id"]+"</td>"+
						"<td>"+e["level"]+"</td>"+
						"<td>"+e["rank"]+"</td>"+
						"<td>"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menuupdate\" data-value=\""+e["id"]+"\" onclick=\"menuUpdateInit(this)\">编辑</label>|"+
							"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#menudelete\" data-value=\""+e["id"]+"\" onclick=\"menuDeleteInit(this)\">删除</label>"+
						"</td>"+
					"</tr>"
				);
			}
		}
	});
	// console.log(element);
	return element;
}

loadMenuBody();

