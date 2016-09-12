function disappear(e){
	var name  = e.getAttribute("data-name");
	console.log("disappear"+name);
	var data_name = document.getElementsByName(name);
	console.log(data_name);

	$("li[name=\""+name+"\"]").remove();
	for(var i=0,len=data_name.length;i<len;i++){
		data_name[i].style.display = "none";
	}
	var nextSize = $("i[data-name=\'"+name+"\']").parent().parent().nextAll(".active").length;
	console.log("next size:"+nextSize);
	console.log("nextAll"+$("i[data-name=\'"+name+"\']").parent().parent().nextAll());
	if(nextSize){
		$("i[data-name=\'"+name+"\']").parent().parent().next().addClass("active");
		return false;
	}
	var prevSize = $(this).parent().parent().prevAll().length;
	console.log("prev size:"+prevSize);
	if(prevSize){
		 $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass("active");
		 return false;
	}
	return true;

}

function appears(e){
	$(".J_menuTab").removeClass("active");
	var name = e.getAttribute("data-name");
	var val = e.getAttribute("data-id");
	console.log(val);
	console.log($("li[name=\""+name+"\"]").filter(".active"));
	if($("li[name=\""+name+"\"]").filter(".active").prevObject.length != 0){
		console.log("exist");
		$("li[name=\'"+name+"\']").addClass("active");
		return false;
	}
	else{
		var content = "<li name=\""+name+"\" class=\"J_menuTab active\" style=\"display:block;\">"+
						"<a data-toggle=\"tab\">"+
							"<span data-name=\""+name+"\" onclick=\"appears2(this)\">"+val+"</span>"+
							"<i class=\"glyphicon glyphicon-remove-sign\" data-name=\""+name+"\" onclick=\"disappear(this)\"></i>"+
						"</a>"+
					"</li>";
		$("#menu-head").append(content);
		console.log("appears"+name);
		var ifr = document.getElementsByTagName("iframe");
		for(var i=0,len=ifr.length;i<len;i++){
			ifr[i].style.display = "none";
		}
		
		var data_name = document.getElementsByName(name);
		for(var i=0,len=data_name.length;i<len;i++){
			data_name[i].style.display = "block";
		    // $("li[name=\'"+name+"\']").addClass("active");
		}

		var totalWidth1 = $("#menu-head").width();
		//var viewWidth;
		var width2 = $("#hrUp").width();
		console.log("menu-head"+totalWidth1);
		console.log("hrUp"+width2);
		if(totalWidth1>width2){
			$("li[name=\'00\']").nextAll().animate({marginLeft: -100+'px'});
		}
		return true;
	}
	
	// document.getElementById(name).style.display="block";
}

function appears2(e){
	var name = e.getAttribute("data-name");
	console.log("appears2"+name);
	var ifr = document.getElementsByTagName("iframe");
	for(var i=0,len=ifr.length;i<len;i++){
		ifr[i].style.display = "none";
	}
	var data_name = document.getElementsByName(name);
	for(var i=0,len=data_name.length;i<len;i++){
		data_name[i].style.display = "block";
	}
	// document.getElementById(name).style.display="block";
}

function backward(){
	var width2 = $("#hrUp").width();
	$("li[name=\'00\']").nextAll().animate({marginLeft: width2+'px'});
}

function forward(){
	var width2 = $("#hrUp").width();
	$("li[name=\'00\']").nextAll().animate({marginLeft: -width2+'px'});
}