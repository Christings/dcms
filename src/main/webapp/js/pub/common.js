/**
 * Created by Charles on 2016/9/24.
 * 约定：common.js最先执行，其他页面每个页面必须丁一pageInit()方法，由common.js统一调用
 */
$(document).ready(function () {
    //先判断当前iframe页面的src与data-id是否一致,否则跳转到首页
    loadTopWindow();
    function loadTopWindow() {
        if (window.top != null && document.URL.indexOf('login.html')!=-1) {
            window.top.location = document.URL;
        }
    }

    var user=DCMSBusi.USER.get();
    if(!user){
        // window.location.href=DCMSUtils.URL.getContentPath()+"login.html";
        window.location.href="./";
    }else {
        pageInit();
    }
});