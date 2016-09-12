$(document).ready(function(){
	$("#sideButton").click(function(){
		 $("body").toggleClass("mini-sidebar");
		 console.log("click");
		 var content1 =
					"<ul class=\"list-group\" id=\"menutree\">"+
						"<li class=\"list-group-item\" data-name=\"01\" onclick=\"appears(this)\" data-id=\"菜单管理\">菜单管理</li>"+
						"<li class=\"list-group-item\" data-name=\"02\" onclick=\"appears(this)\" data-id=\"用户管理\">用户管理</li>"+
						"<li class=\"list-group-item\" data-name=\"03\" onclick=\"appears(this)\" data-id=\"角色管理\">角色管理</li>"+
						"<li class=\"list-group-item\" data-name=\"04\" onclick=\"appears(this)\" data-id=\"机柜管理\">机柜管理</li>"+
						"<li class=\"list-group-item\" data-name=\"05\" onclick=\"appears(this)\" data-id=\"系统管理\">系统管理</li>"+
						"<li class=\"list-group-item\" data-name=\"001\" onclick=\"appears(this)\" data-id=\"001管理\">001管理</li>"+
						"<li class=\"list-group-item\" data-name=\"002\" onclick=\"appears(this)\" data-id=\"002管理\">002管理</li>"+
						"<li class=\"list-group-item\" data-name=\"003\" onclick=\"appears(this)\" data-id=\"003管理\">003管理</li>"+
						"<li class=\"list-group-item\" data-name=\"004\" onclick=\"appears(this)\" data-id=\"004管理\">004管理</li>"+
						"<li class=\"list-group-item\" data-name=\"005\" onclick=\"appears(this)\" data-id=\"005管理\">005管理</li>"+
						"<li class=\"list-group-item\" data-name=\"006\" onclick=\"appears(this)\" data-id=\"006管理\">006管理</li>"+
						"<li class=\"list-group-item\" data-name=\"007\" onclick=\"appears(this)\" data-id=\"007管理\">007管理</li>"+
						"<li class=\"list-group-item\" data-name=\"008\" onclick=\"appears(this)\" data-id=\"008管理\">008管理</li>"+
						"<li class=\"list-group-item\" data-name=\"009\" onclick=\"appears(this)\" data-id=\"009管理\">009管理</li>"+
						"<li class=\"list-group-item\" data-name=\"010\" onclick=\"appears(this)\" data-id=\"010管理\">010管理</li>"+
					"</ul>";
		 var content2 =
					"<ul class=\"list-group\" id=\"menutree\">"+
						"<li class=\"list-group-item\" data-name=\"01\" onclick=\"appears(this)\" data-id=\"菜单管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"02\" onclick=\"appears(this)\" data-id=\"用户管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"03\" onclick=\"appears(this)\" data-id=\"角色管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"04\" onclick=\"appears(this)\" data-id=\"机柜管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"05\" onclick=\"appears(this)\" data-id=\"系统管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"001\" onclick=\"appears(this)\" data-id=\"001管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"002\" onclick=\"appears(this)\" data-id=\"002管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"003\" onclick=\"appears(this)\" data-id=\"003管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"004\" onclick=\"appears(this)\" data-id=\"004管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"005\" onclick=\"appears(this)\" data-id=\"005管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"006\" onclick=\"appears(this)\" data-id=\"006管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"007\" onclick=\"appears(this)\" data-id=\"007管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"008\" onclick=\"appears(this)\" data-id=\"008管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"009\" onclick=\"appears(this)\" data-id=\"009管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
						"<li class=\"list-group-item\" data-name=\"010\" onclick=\"appears(this)\" data-id=\"010管理\"><i class=\"glyphicon glyphicon-align-justify\"></i></li>"+
					"</ul>";
		$("#menutree").html(function(i,origText){
			return content1;
		});
		$(".mini-sidebar #menutree").html(function(i,origText){
			return content2;
		});
	});
});