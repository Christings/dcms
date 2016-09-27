/**
 * Created by Charles on 2016/9/25.
 * 通用的script、link引入方法，减少相同页面的各种引入
 *
 */
function getContentPath(){
    var pathName = window.location.pathname;
    var appIndex = pathName.substr(1).indexOf("/");
    var app = pathName.substr(0, appIndex + 1);
    return window.location.origin+app;
}

document.write('<link href="'+getContentPath()+'/3rd-libs/jquery-weui/lib/weui.css" rel = "stylesheet">');
document.write('<link href="'+getContentPath()+'/3rd-libs/jquery-weui/css/jquery-weui.css" rel = "stylesheet">');

document.write('<script charset="utf-8"  src="'+getContentPath()+'/3rd-libs/jquery/jquery-2.1.4.min.js"><\/script>');

document.write('<script charset="utf-8"  src="'+getContentPath()+'/3rd-libs/jquery-weui/js/jquery-weui.min.js"><\/script>');
document.write('<script charset="utf-8"  src="'+getContentPath()+'/3rd-libs/bootstrap/js/bootstrap.min.js"><\/script>');
