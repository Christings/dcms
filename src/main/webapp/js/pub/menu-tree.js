/**
 * Created by Charles on 2016/9/10.
 */

function initMenuTree(){
    var user=DCMSBusi.USER.get();
    var userMenus=user.userMenus;
    if(userMenus){
        for(var i=0;i<userMenus.length;i++){
            var menu=userMenus[i];
            var li=document.createElement("li");
            li.id=menu.id;
            var liHtml='';
            if(hasChildMenu(menu)){
                liHtml+='<a href=\"#\">';
            }else{
                liHtml+='<a class="J_menuItem" onclick="clickAuth()" href=\"'+menu.url+'\">';
            }
            liHtml+='<i class=\"'+menu.iconId+'\"></i>';
            liHtml+='<span class="nav-label">'+menu.name+'</span>';
            if(hasChildMenu(menu)){
                liHtml+='<span class="fa arrow"></span>';
            }
            liHtml+='</a>';
            if(hasChildMenu(menu)){
                liHtml+='<ul class="nav nav-second-level">';
                    for(var j=0;j<menu.childMenu.length;j++){
                        var child=menu.childMenu[j];
                        liHtml+='<li id="'+child.id+'">';
                            liHtml+='<a onclick="clickAuth()" class="J_menuItem" href=\"'+child.url+'\">'+child.name+'</a>';
                        liHtml+='</li>';
                    }
                liHtml+='</ul>';
            }
            li.innerHTML=liHtml;
            $("#side-menu").append(li);
        }
    }

    // MetsiMenu
    $('#side-menu').metisMenu();

    // 打开右侧边栏
    $('.right-sidebar-toggle').click(function () {
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // 右侧边栏使用slimscroll
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });
}

function hasChildMenu(menu){
    if(menu.childMenu && menu.childMenu.length>0){
        return true;
    }
    return false;
}

function clickAuth(){
    var user=DCMSBusi.USER.get();
    if(!user){
        // window.location.href=DCMSUtils.URL.getContentPath()+"login.html";
        window.location.href='./';
    }
}





