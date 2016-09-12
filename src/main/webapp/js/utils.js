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
			dtd.resolve(data);
		},function(error){
			dtd.reject(error);
		});
		return dtd.promise();
	},
	//页面跳转
	gotoPage:function(pageUrl){
		window.location.href=pageUrl;
	},
	//设置Storage
	setStorage:function(name,value){
		window.localStorage.setItem(name,JSON.stringify(value));
	},
	getStorage:function(name){
		var val=window.localStorage.getItem(name);
		if(val){
			try{
				val=JSON.parse(val);
			}catch (e){

			}
		}
		return val;
	},
	removeStorage:function(name){
		window.localStorage.removeItem(name);
	},
	//清空，慎用
	clearStorage:function(){
		window.localStorage.clear();
	}
};
//业务相关,和工具类没关系
var _busi={
	setUser:function(user){
		_utils.setStorage("_USER_INFO_",user);
	},
	getUser:function(){
		return _utils.getStorage("_USER_INFO_");
	}
};

var DCMS={
	Utils:_utils,
	Busi:_busi
};


