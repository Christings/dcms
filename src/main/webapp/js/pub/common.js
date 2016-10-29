/**
 * Created by Charles on 2016/9/24.
 * 约定：common.js最先执行，其他页面每个页面必须丁一pageInit()方法，由common.js统一调用
 */
$(document).ready(function () {
    var user=DCMSBusi.USER.get();
    if(!user){
        // window.location.href=DCMSUtils.URL.getContentPath()+"login.html";
        window.location.href="./";
    }else {
        pageInit();
    }
});