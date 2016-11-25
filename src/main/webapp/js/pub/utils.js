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
				url:url,
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
		doPostPermit:function(url,params){
			var dtd=$.Deferred();
			$.ajax({
				type:"post",
				url:getContentPath()+url,
				dataType: 'json',
				data: params,
				async:true
			}).then(function(data){
				if(data.status==1){
					data.resolve(data.data);
				}else{
					if(data.status==5){//如果需要单独处理就在此处处理
						DCMSUtils.Modal.toast(data.msg,'forbidden');
					}else{
						DCMSUtils.Modal.toast(data.msg,'forbidden');
					}
					data.resolve(data);
				}
			},function(error){
				DCMSUtils.Modal.toast('出小差了，稍后再试','forbidden');
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
			var app = pathName.substr(0, appIndex + 1)+"/";
			return window.location.origin+app;
		},
		getQueryString:function (name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if(r != null) return unescape(r[2]);
			return null;
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
			if(typeof value =='object'){
				window.sessionStorage.setItem(name,JSON.stringify(value));
			}else{
				window.sessionStorage.setItem(name,value);
			}
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
	},
	DataTables:{
		handleParams:function(data){
			var pageNum=data.start/data.length+1,pageSize=data.length;
			var params={
				pageNum:pageNum,
				pageSize:pageSize
			};
			if(data.order&&data.order.length && data.order[0]){
				params.sortName=data.columns[data.order[0].column].name;
				params.sortDesc=data.order[0].dir;
			}
			return params;
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
	},
	Api:{
		invoke:function (url,param) {
			DCMSUtils.Modal.showLoading();
			var dtd=$.Deferred();
			DCMSUtils.Ajax.doPost(getContentPath()+url,param).then(function(data){
				DCMSUtils.Modal.hideLoading();
				console.log(data);
				if(data){
					if(data.status==5){
						DCMSUtils.Modal.toast(data.msg,'forbidden');
						dtd.reject(data);
					}else if(data.status==999){
						window.top.location=getContentPath();
					}else{
						dtd.resolve(data);
					}
				}else{
					dtd.reject(data);
				}
			},function(error){
				DCMSUtils.Modal.hideLoading();
				console.log(error);
				dtd.reject(error);
			});
			return dtd.promise();
		}
	}
};



