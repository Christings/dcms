var menuTree;
var elements;
var htm_ele="";
var htm_final_ele="";
var menuHead;
function loadMenuTree(){
		$.ajax({
			url: "menu/tree",
			dataType: "json",
			data: "",
			type: "post"
		}).done((jsonData)=>{
			menuTree = jsonData["data"];
			menuHead = "";//右侧菜单栏头
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
			// console.log(htm_final_ele);
			var index = document.getElementById("menutree");
			index.innerHTML = htm_final_ele;
			var index2 = document.getElementById("menu-head");
			index2.innerHTML = menuHead;
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
			childMenu: treeNodes[j]["childMenu"]	
		});
	
		var childs = treeNodes[j]["childMenu"];
		childElement[j] = parseTreeJson(childs);
	}
	var distance = arr[0]["level"]*20 - 20;
	// console.log("distance::"+distance);
	var left = "auto auto 0 "+ distance +"px";
	var liStyle_level_1 = "margin: 0;"
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
			console.log(e["name"]);
			if(e["level"]!=1){
				return(
					"<li style=\"" + liStyle + "\" class=\"list-group-item\" >"+
						"<a href=\"#" + e["id"] + "\"class=\"nav-header collapsed\" data-toggle=\"collapse\">"+
							"<i style=\"margin:" + left + "\" class=\"glyphicon glyphicon-"+e["iconId"]+"\"></i>&nbsp;"
				            +e["name"]+
				            "<span class=\"pull-right glyphicon glyphicon-chevron-toggle\"></span>"+
						"</a>"+
					"</li>"+
					"<ul style=\"" + ulStyle + "\" id=\"" + e["id"] + "\"class=\"nav nav-list collapse\">"
						+childElement[temp]+
					"</ul>"
				);
			}else{
				return(
					"<li style=\"" + liStyle_level_1 + "\" class=\"list-group-item\" >"+
						"<a href=\"#" + e["id"] + "\"class=\"nav-header collapsed\" data-toggle=\"collapse\">"+
							"<i style=\"margin:" + left + "\" class=\"glyphicon glyphicon-"+e["iconId"]+"\"></i>&nbsp;"
				            +e["name"]+
				            "<span class=\"pull-right glyphicon glyphicon-chevron-toggle\"></span>"+
						"</a>"+
					"</li>"+
					"<ul style=\"" + ulStyle + "\" id=\"" + e["id"] + "\"class=\"nav nav-list collapse\">"
						+childElement[temp]+
					"</ul>"
				);
			}
			
		}
		else{
			menuHead += "<li name =\""+e["id"]+"\" style=\"display:none;\">"+
					"<a>"+
						"<span data-name=\""+e["id"]+"\" onclick=\"appears2(this)\" class=\"glyphicon glyphicon-"+e["iconId"]+"\">"+e["name"]+"</span>"+
						"<i class=\"glyphicon glyphicon-remove-sign\" data-name=\""+e["id"]+"\" onclick=\"disappear(this)\"></i>"+
					"</a>"+
				"</li>";
			if(e["level"]!=1){
				return(
					"<li style=\"" + liStyle + "\" class=\"list-group-item\" data-name=\""+e["id"]+"\" onclick=\"appears(this)\">"+
						"<a>"+
							"<i style=\"margin:" + left + "\"class=\"glyphicon glyphicon-"+e["iconId"]+"\"></i>&nbsp;"
				            +e["name"]+
						"</a>"+
					"</li>"
				);
			}else{
				return(
					"<li style=\"" + liStyle_level_1 + "\" class=\"list-group-item\" data-name=\""+e["id"]+"\"onclick=\"appears(this)\">"+
						"<a>"+
							"<i style=\"margin:" + left + "\"class=\"glyphicon glyphicon-"+e["iconId"]+"\"></i>&nbsp;"
				            +e["name"]+  
						"</a>"+
					"</li>"
				);
			}
		}
	});
	console.log(element);
	return element;
}

loadMenuTree();

