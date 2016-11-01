var initWidth = 100;
var bgWidth = 1833;
var bgHeight = 2048;
// $(document).ready(function(){
	//EquipmentFloat(1);
var times=1;  
var datas;
function roomPGWatch(id){
	$('#roomBody').append('<img id="roombg" style="width:100%;" src="../../../serviceRoomIcngph/getImage?id='+id+'">');
	DCMSUtils.Ajax.doPost('serviceRoomIcngph/getJson',{id:id}).then(function(data){
        if(data.status=='1'){
           datas = data.data;
           EquipmentFloat();
			var roomBody = document.getElementById('roomBody');
			if (roomBody.addEventListener) {   
		    // IE9, Chrome, Safari, Opera   
			    roomBody.addEventListener("mousewheel", MouseWheelHandler, false);   
			    // Firefox   
			    roomBody.addEventListener("DOMMouseScroll", MouseWheelHandler, false);   
			}   
			// IE 6/7/8   
			else roomBody.attachEvent("onmousewheel", MouseWheelHandler);
        }
        else{
            DCMSUtils.Modal.toast('json出错'+data.msg,'forbidden');
        }
    },function (error) {
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('json出错','forbidden');
    });


}

function MouseWheelHandler(e){
	var e = window.event || e; // old IE support   
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail))); 
    initWidth += 5 * delta;
    console.log(delta); 
    // roombg.style.zoom = Math.max(0.5, Math.min(1.5, roombg.width + (0.1 * delta))); 
    var width = Math.max(20, Math.min(150, initWidth));
    if(width != 150){
    	window.scrollTo(0,0);
    }
    times = width/100;
    roomBody.style.width = width + '%';
    EquipmentFloat();
    console.log(width);
    //var widths = width.toString;
    
    return false;   
}

function EquipmentFloat(){
	var data_id = JSON.parse(window.sessionStorage.getItem('data-id'));
	var eq='';
	var scrollHeight = $("#roomBody").height();//document.body.scrollHeight;
	console.log(scrollHeight);
	for(var i in datas){
		var data = datas[i];
		var eqWidth = ((data[7] - data[1])/bgWidth*times*100);
		var eqHeight = ((data[2] - data[6])/bgHeight*scrollHeight);
		// var eqHeight = (data[2] - data[6])*times;
		var eqLeft = (data[1]/bgWidth*times*100);
		var eqTop = (data[4]/bgHeight*scrollHeight);
		// var eqTop = data[4]*times;
		var color;
		switch(data[0]){
			case 0:
				color = '#6495ed';
				break;
			case 1:
				color = '#A52A2A';
				break;
			case 2:
				color = '#32b16c';
				break;
			case 3:
				color = '#f19ec2';
				break;
			case 4:
				color = 'purple';
				break;
			default:
				color = 'navy';
				break;
		}
		var style = 'style="cursor:pointer;position:absolute;left:'+eqLeft+'%;top:'+eqTop+'px;width:'+eqWidth+'%;height:'+eqHeight+'px;background-color:'+color+';border-left:2px solid #bcbcbc"'
		// var style = 'style="position:absolute;left:100px;top:60px;width:50px;height:60px;background-color:red;"'; 
		var mouseOverOut = 'onmouseover="this.style.backgroundColor=\'#c19288\'" onmouseout="this.style.backgroundColor=\''+color+'\'"';
		if(data_id == i){
			eq += '<span '+mouseOverOut+'class="equipment" id="'+i+'" data-id="'+i+'"'+style+'onclick=make(this)>'+i+'</span>';
		}else{
			eq += '<span '+mouseOverOut+'class="equipment" id="'+i+'" data-id="'+i+'"'+style+'onclick=make(this)>'+i+'</span>';
		}
	}
	// var datas = [{'A01':[1, 1728, 1175, 1728, 1055, 1775, 1055, 1775, 1175]}];
	
	document.getElementById('equipments').innerHTML = eq;
	// $('#A01').css({
	// 	'position':'absolute',
	// 	'left':eqLeft,
	// 	'right':eqRight,
	// 	'top':eqTop,
	// 	'width':eqWidth,
	// 	'height':eqHeight,
	// 	'background-color':'red'
	// });
}
$('#opLocToggle').click(function(){
	$('#operations_locate').toggleClass("appear_opLoc");
});
$('#opSeaToggle').click(function(){
	$('#operations_search').toggleClass("appear_opSea");
});
$('#expandSearch').click(function(){
	$('#operations_search_secondlevel').toggleClass("appear_opSea2");
});
// var cacheName='';
// var cacheColor='';
$('#operations_locate button').click(function(){
	var name = $('#operations_locate input').val();
	EquipmentFloat(times);
	console.log($('#'+name).length);
	if(name == ''){
		alert("请输入您要查找的设备！");
	}else if($('#'+name).length){
		$('#'+name).css({'background-color':'green'});
	}else{
		alert("您输入的设备不存在！");
	}
	
});
// });
function make(e){
	var id = e.getAttribute('data-id');
	var test = JSON.parse(window.sessionStorage.getItem('data-id'));
	if(test == id){
		window.sessionStorage.removeItem('data-id');
		document.getElementById(id).innerHTML=id;
	}else{
		window.sessionStorage.setItem('data-id',JSON.stringify(id));
		document.getElementById(id).append("x"); 
	}
}
function opLocToggle(){

}
function locateInput(){
}
function locateEquipment(){

}