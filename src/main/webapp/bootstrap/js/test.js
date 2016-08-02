function test(e){
	// var value = e.getAttribute("data-id");
	// // console.log(Object.getOwnPropertyNames(value));
	// var ca=JSON.parse(value);
	// console.log(value);
	// console.log(ca["id"]);
	console.log("duang!");
	$("#wa").submit(function(){
		var val = $("#label").val();
		console.log(val);
	});
}

function wa(){
	// var arr = {
	// 	info: 
	// 	[
	// 		{ 
	// 			id : "asadfasdf",
	// 			icon: "book"
	// 		}
	// 	]
	// }
	var arr = [];
	arr.push({
		id: "adsfasdf",
		name:"wakaka"
	});
	console.log(arr[0]);
	console.log(JSON.stringify(arr[0]));
	var a = JSON.stringify(arr[0]);
 	var b = "{id: \"adsfasdf\",name:\"wakaka\"}";
	var html = 	"<form role=\"form\" id=\"wa\">"+
					"<div class=\"modal-footer\">"+
			            "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭"+
			            "</button>"+
			            "<button type=\"button\" id=\"duang\"data-id=\""+a+"\" onclick=\"test()\" class=\"btn btn-primary\">"+
			               "提交更改"+
			            "</button>"+
			            "<label id=\"label\" value=\"a\" onclick=\"test(this)\">good</label>"+
			        "</div>"+
		        "</form>";
	var body = document.getElementById("modal_body");

	body.innerHTML = html;
}

function set(){
	var arr = [];
	arr.push({
		id: "adsfasdf",
		name:"wakaka"
	});
	console.log(arr[0]);
	console.log(JSON.stringify(arr[0]));
	var boss = document.getElementById("duang");
	boss.setAttribute("data-id",JSON.stringify(arr[0]));
}
wa();
// set();