/**
 * Created by Charles on 2016/10/13.
 */
$(document).ready(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var selectIcon=DCMSUtils.URL.getQueryString("selectIcon");
    console.log(selectIcon);
    if(selectIcon){
        var selector='i';
        var iconDetails=selectIcon.split(" ");
        for(var i=0;i<iconDetails.length;i++){
            selector+='.'+iconDetails[i];
        }
        $(selector).first().parent().css('background-color','red');
    }
    //选中
    $("a").click(function(){
        var icon=$(this).children('i').first().attr('class');
        parent.getIcon(icon);
        parent.layer.close(index);
    });
});