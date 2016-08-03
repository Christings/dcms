var num = 5;
function t1(){
	var body = document.getElementById("menubody");
	body.innerHTML = "test1";
}

function load(){
	var body = document.getElementById("pagination");
	body.innerHTML = "<ul class=\"pagination\">"+
			 " <li data-value=\"6\" onclick=\"t2(this)\"><a>6</a></li>"+
		"</ul>;"
}

load();