/**
 * Created by Charles on 2016/9/10.
 */

function initMenuTree(){
    var user=DCMSBusi.USER.get();
    var userMenus=user.userMenus;
    if(userMenus){
        createTreeDom($("#side-menu"),userMenus,1);
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

/**
 *
 * @param container 存放菜单的容器
 * @param menuList  菜单数据
 * @param menuLevel 菜单级别
 */
function createTreeDom(container,menuList,menuLevel){
    for(var i=0;i<menuList.length;i++) {
        var menu = menuList[i];
        var li = document.createElement("li");
        li.id = menu.id;
        var liHtml = '';
        if (hasChildMenu(menu)) {
            liHtml += '<a href=\"#\">';
        } else {
            liHtml += '<a class="J_menuItem" onclick="clickAuth()" href=\"' + menu.url + '\">';
        }
        liHtml += '<i class=\"' + menu.iconId + '\"></i>';
        liHtml += '<span class="nav-label">' + menu.name + '</span>';
        if (hasChildMenu(menu)) {
            liHtml += '<span class="fa arrow"></span>';
        }
        liHtml += '</a>';

        if(hasChildMenu(menu)) {
            var classLevel='';
            if(menuLevel==1){
                classLevel='nav-second-level';
            }else if(menuLevel==2){
                classLevel='nav-third-level';
            }else if(menuLevel==3){
                classLevel='';
            }
            liHtml += '<ul id="'+menu.id+'_child_ul" class="nav '+classLevel+'"></ul>';
        }

        li.innerHTML=liHtml;
        container.append(li);
        if(hasChildMenu(menu)) {
            createTreeDom($("#" + menu.id + '_child_ul'), menu.childMenu,menuLevel+1);
        }
    }
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





