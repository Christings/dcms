function menuAdd(){
	// var name = document.getElementById("menuName").value;
	// var rank = document.getElementById("menuRank").value;
	// var level = document.getElementById("menuLevel").value;
	// // var url = document.getElementById("menuName").value;
	// var parentId = document.getElementById("menuParentId").value;
	// var iconId = document.getElementById("menuIcon").value;
	// var type = document.getElementById("menuType").value;
	console.log("menuAdd");
	$("#menuAddForm").submit(function(){
		console.log("menuAdd1");
		var name = $("#menuName").val();
		var rank = $("#menuRank").val();
		var level = $("#menuLevel").val();
		var url = $("#menuUrl").val();
		var parentId = $("#menuParentId").val();
		var iconId = $("#menuIcon").val();
		var type = $("#menuType").val();
		if(name == "")
		{
			$("#alert").text("请输入菜单名称");
			return false;
		}

		var menuInfo = { name:'', rank:'',parentId:'', level:'', url:'', iconId:'', type:''};
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

		$.ajax({
			type:"post",
			url:"menu/add",
			dataType: 'json',
			data: menuInfo,
			success:function(res){
				if(res.status == "1"){
					console.log("添加菜单成功");
					alert("添加菜单成功");
				}else{
					console.log("添加菜单失败"+res.msg);
					alert("添加菜单失败"+res.msg);
				}
			}
		});
	});
}