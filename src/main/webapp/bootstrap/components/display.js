function disappear(e){
	var name  = e.getAttribute("data-name");
	var data_name = document.getElementsByName(name);
	for(var i=0,len=data_name.length;i<len;i++){
		data_name[i].style.display = "none";
	}
}

function appears(e){
	var name = e.getAttribute("data-name");
	console.log(name);
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

function appears2(e){
	var name = e.getAttribute("data-name");
	console.log(name);
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