function menuDeleteInit(e){
	// $(document).ready(){
		var menu = e.getAttribute("data-value");
		var id = menu["id"];
		var name = menu["name"];
		console.log("delete:",id);
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
		var html =  "<form role=\"form\" id=\"menuDeleteForm\">"+
					"<label id=\"deleteMenuName\">"+ +"</label>"+
		            "<div class=\"modal-footer\">"+
			           "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			           "</button>"+
			            "<button type=\"submit\" data-id=\""+id+"\"onclick=\"menuDelete(this)\" class=\"btn btn-primary\">"+
			              "确认删除"+
			           "</button>"+
			       " </div>"+
			       "</form>";
		var body = document.getElementById("menuDeleteBody");
		body.innerHTML = html;
		// }
}

function menuDelete(e){
	var id = e.getAttribute("data-id");
	var menuInfo = {key:''};
	menuInfo["key"] = id;
	$.ajax({
		type:"post",
		url:"menu/delete",
		dataType: 'json',
		data: menuInfo,
		success:function(res){
			if(res.status == "1"){
				console.log("删除菜单成功");
			}else{
				console.log("删除菜单失败"+res.msg);
			}
		}
	});
}

