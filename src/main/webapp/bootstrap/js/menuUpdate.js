function menuUpdate(){
	console.log(1);
		// $("#form").submit(function(){
			// var type = document.getElementById("menuType").value;
	$("#menuUpdateForm").submit(function(){
		var name = $("#menuName").val();
		var rank = $("#menuRank").val();
		var level = $("#menuLevel").val();
		var url = $("#menuUrl").val();
		var parentId = $("#menuParentId").val();
		var iconId = $("#menuIcon").val();
		var type = $("#menuType").val();
		if(name == "")
		{
			$("#alertName").text("请输入菜单名称");
			return false;
		}
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

		// var getMenuData = {pageNum: 1, pageSize: 10};
		$.ajax({
			type:"post",
			url:"menu/update",
			dataType: 'json',
			data: menuInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("修改菜单成功");
					// $.ajax({
					// 	url: "menu/datagrid",
					// 	dataType: "json",
					// 	data: getMenuData,
					// 	type: "post"
					// }).done((jsonData)=>{
					// 	const menuData = jsonData["data"]["records"];
					// 	const totalPage = jsonData["data"]["pageCount"];

					// 	const menu = {menuData: menuData,totalPage:totalPage};

					// 	this.props.actions.menuAll(menu);
					// }).fail((err)=>{

					// });
				}else{
					console.log("修改菜单失败"+res.msg);
					return false;
				}
			}
		});


		console.log(menuInfo);
		return true;
		// });
	});
}

function menuUpdateInit(e){
	// $(document).ready(){
		var menu = e.getAttribute("data-value");
		var id = menu["id"];
		var name = menu["name"];
		var iconId = menu["iconId"];
		var level = menu["level"];
		var type = menu["type"];
		var rank = menu["rank"];
		var url = menu["url"];
		var parentId = menu["parentId"];
		console.log(menu);
		console.log(id);
		console.log(name);
		// $.ajax({
		// 	type:"post",
		// 	url:"menu/get",
		// 	dataType: 'json',
		// 	data: menuId,
		// 	success:function(res){
		// 		if(res.status == "1"){
		// 			console.log("修改菜单成功");
		// 			$.ajax({
		// 				url: "menu/datagrid",
		// 				dataType: "json",
		// 				data: getMenuData,
		// 				type: "post"
		// 			}).done((jsonData)=>{
		// 				const menuData = jsonData["data"]["records"];
		// 				const totalPage = jsonData["data"]["pageCount"];

		// 				const menu = {menuData: menuData,totalPage:totalPage};

		// 				this.props.actions.menuAll(menu);
		// 			}).fail((err)=>{

		// 			});
		// 		}else{
		// 			console.log("修改菜单失败"+res.msg);
		// 			return false;
		// 		}
		// 	}
		// }).then({
			
		// });
		var html = "<form role=\"form\" id=\"menuUpdateForm\">"+
						"<div class=\"form-group\">"+
						    "<label for=\"name\">菜单名称</label>"+
						    "<input type=\"text\" class=\"form-control\" id=\"menuName\""+ 
						     "value=\""+name+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label for=\"name\">菜单图标</label>"+
							"<input type=\"text\" class=\"form-control\" id=\"menuIcon\""+
							"value=\""+iconId+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label for=\"name\">菜单类型</label>"+
							"<input type=\"text\" class=\"form-control\" id=\"menuType\""+
							"value=\""+type+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label>菜单地址</label>"+
							"<input type=\"text\" class=\"form-control\" id=\"menuUrl\""+
						    "value=\""+url+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label>父菜单Id</label>"+
							"<input type=\"text\" class=\"form-control\" id=\"menuParentId\""+
						     "value=\""+parentId+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label>菜单等级</label>"+
							"<input type=\"number\" class=\"form-control\" id=\"menuLevel\""+
						     "value=\""+level+"\">"+
						"</div>"+
						"<div class=\"form-group\">"+
							"<label>菜单顺序</label>"+
							"<input type=\"number\" class=\"form-control\" id=\"menuRank\""+
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
		// }
	

}



