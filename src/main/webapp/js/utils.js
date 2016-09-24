//工具类
var _utils={
	Ajax:function(url,params){
		var dtd=$.Deferred();
		$.ajax({
			type:"post",
			url:DCMSConfig.API_SERVER+url,
			dataType: 'json',
			data: params,
			async:true
		}).then(function(data){
			console.log("ajax");
			dtd.resolve(data);
		},function(error){
			console.log(error);
			dtd.reject(error);
		});
		return dtd.promise();
	},
	//页面跳转
	gotoPage:function(pageUrl){
		window.location.href=pageUrl;
	},
	//设置Storage
	setSessionStorage:function(name,value){
		window.sessionStorage.setItem(name,JSON.stringify(value));
	},
	getSessionStorage:function(name){
		var val=window.sessionStorage.getItem(name);
		if(val){
			try{
				val=JSON.parse(val);
			}catch (e){

			}
		}
		return val;
	},
	removeSessionStorage:function(name){
		window.sessionStorage.removeItem(name);
	},
	//清空，慎用
	clearSessionStorage:function(){
		window.sessionStorage.clear();
	}
};
//业务相关,和工具类没关系
var _busi={
	setUser:function(user){
		_utils.setSessionStorage("_USER_INFO_",user);
	},
	getUser:function(){
		return _utils.getSessionStorage("_USER_INFO_");
	}
};

var DCMS={
	Utils:_utils,
	Busi:_busi
};


