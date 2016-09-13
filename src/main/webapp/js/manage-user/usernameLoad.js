var username = "";
var usernameLoad = function(){
	var location = window.location.href;
	var segments = location.split("?");
	var length = segments.length - 1;
	username = segments[length];
}

usernameLoad();

var s = document.getElementById("username");
s.innerHTML = username+"<span class=\"caret\"></span>";