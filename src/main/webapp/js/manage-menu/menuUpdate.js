function menuUpdate(){
	console.log(1);
		// $("#form").submit(function(){
			// var type = document.getElementById("menuType").value;
	$("#menuUpdateForm").submit(function(){
		console.log(2);
		var id = $("#menuId1").val();
		console.log(id);
		var name = $("#menuName1").val();
		console.log(name);
		var rank = $("#menuRank1").val();
		console.log(rank);
		var level = $("#menuLevel1").val();
		console.log(level);
		var url = $("#menuUrl1").val();
		console.log(url);
		var parentId = $("#menuParentId1").val();
		console.log(parentId);
		var iconId = $("#menuIcon1").val();
		console.log(iconId);
		var type = $("#menuType1").val();
		console.log(type);
		if(name == "")
		{
			$("#alertName").text("请输入菜单名称");
			return false;
		}
		console.log(3);
		// console.log(name);
		var menuInfo = { id:'', name:'', rank:'', level:'', url:'', parentId:'', iconId:'', type:''};
		menuInfo['id'] = id;
		menuInfo['name'] = name;
		menuInfo['rank'] = rank;
		if(level == ''){
			menuInfo['level'] = 1;
		}
		else{
			menuInfo['level'] = level;
		}
		menuInfo['url'] = url;
		menuInfo['parentId'] = parentId;
		menuInfo['iconId'] = iconId;
		menuInfo['type'] = type;
		console.log(4);
		// var getMenuData = {pageNum: 1, pageSize: 10};
		$.ajax({
			type:"post",
			url:"menu/update",
			dataType: "json",
			data: menuInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("修改菜单成功");
				}else{
					console.log("修改菜单失败"+res.msg);
					return false;
				}
			}
		});
		console.log(5);

		console.log(menuInfo);
		return true;
		// });
	});
}

function menuUpdateInit(e){
	// $(document).ready(){
	var id = e.getAttribute("data-value");
	var menuId = {key:''};
	menuId["key"] = id;
	
	// console.log(menu);
	 console.log(id);
	// console.log(name);
	$.ajax({
		type:"post",
		url:"menu/get",
		dataType: 'json',
		data: menuId,
		}).done((jsonData)=>{
			var menu = jsonData["data"];
			var name = menu["name"];
			var iconId = menu["iconId"];
			var level = menu["level"];
			var type = menu["type"];
			var rank = menu["rank"];
			var url = menu["url"];
			var parentId;
			console.log("update:"+name);
			if(menu["parentId"] == null){
				parentId = "";
			}else{
				parentId = menu["parentId"];
			}
			//console.log("parentId:"+menu["parentId"]);
			var html = "<form role=\"form\" id=\"menuUpdateForm\">"+
					"<div class=\"form-group\">"+
					    "<label for=\"name\">菜单名称</label>"+
					    "<input type=\"text\" class=\"form-control\" id=\"menuName1\""+ 
					     "value=\""+name+"\">"+
					     "<input hidden=\"hidden\"  id=\"menuId1\" value=\""+id+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label for=\"name\">菜单图标</label>"+
						"<input type=\"text\" class=\"form-control\" id=\"menuIcon1\""+
						"value=\""+iconId+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label for=\"name\">菜单类型</label>"+
						"<input type=\"text\" class=\"form-control\" id=\"menuType1\""+
						"value=\""+type+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label>菜单地址</label>"+
						"<input type=\"text\" class=\"form-control\" id=\"menuUrl1\""+
					    "value=\""+url+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label>父菜单Id</label>"+
						"<input type=\"text\" class=\"form-control\" id=\"menuParentId1\""+
					     "value=\""+parentId+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label>菜单等级</label>"+
						"<input type=\"number\" class=\"form-control\" id=\"menuLevel1\""+
					     "value=\""+level+"\">"+
					"</div>"+
					"<div class=\"form-group\">"+
						"<label>菜单顺序</label>"+
						"<input type=\"number\" class=\"form-control\" id=\"menuRank1\""+
					     "value=\""+rank+"\">"+
					"</div>"+
					"<div class=\"modal-footer\">"+
			           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			            "</button>"+
			            "<button type=\"submit\" onclick=\"menuUpdate()\" class=\"btn btn-primary\">"+
			            "提交修改"+
			           "</button>"+
		        	"</div>"+
				"</form>";
			var body = document.getElementById("menuUpdateBody");
			body.innerHTML = html;
	});		// }
}


