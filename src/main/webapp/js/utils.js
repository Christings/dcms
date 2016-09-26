function getContentPath(){
	var pathName = window.location.pathname;
	var appIndex = pathName.substr(1).indexOf("/");
	var app = pathName.substr(0, appIndex + 1);
	return window.location.origin+app+"/";
}
//工具类
var DCMSUtils={
	//Ajax相关
	Ajax:{
		doPost:function(url,params){
			var dtd=$.Deferred();
			$.ajax({
				type:"post",
				url:getContentPath()+url,
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
		}
	},
	//URL操作相关
	URL:{
		getContentPath:function(){
			var pathName = window.location.pathname;
			var appIndex = pathName.substr(1).indexOf("/");
			var app = pathName.substr(0, appIndex + 1);
			var url = window.location.origin+app;
			console.log(url);
			return window.location.origin+app;
		}
	},
	//导航相关
	NAV:{
		//页面跳转
		gotoPage:function(pageUrl){
			window.location.href=pageUrl;
		},
	},
	SessionStorage:{
		set:function(name,value){
			window.sessionStorage.setItem(name,JSON.stringify(value));
		},
		get:function(name){
			var val=window.sessionStorage.getItem(name);
			if(val){
				try{
					val=JSON.parse(val);
				}catch (e){

				}
			}
			return val;
		},
		remove:function(name){
			window.sessionStorage.removeItem(name);
		},
		clear:function(){
			window.sessionStorage.clear();
		}
	},
	//模态框相关
	Modal:{
		alert:function(msg,title,onOk){
			$.alert(msg,title,onOk);
		},
		/**
		 *
		 * @param msg
		 * @param style：默认是success，还有cancel，forbidden
		 * @param callback
		 */
		toast:function(msg,style,callback){
			$.toast(msg,style,callback);
		},
		confirm:function(msg,title,onOk,onCancel){
			$.confirm(msg,title,onOk,onCancel);
		},
		showLoading:function(msg){
			$.showLoading(msg);
		},
		hideLoading:function(){
			$.hideLoading();
		}
	}
};
//业务相关,和工具类没关系
var DCMSBusi={
	USER:{
		set:function(user){
			DCMSUtils.SessionStorage.set("_USER_INFO_",user);
		},
		get:function(){
			return DCMSUtils.SessionStorage.get("_USER_INFO_");
		}
	}
};


document.write('<script charset="utf-8"  src="'+DCMSUtils.URL.getContentPath()+'/js/config.js"><\/script>');



