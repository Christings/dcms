/**
 * Created by Charles on 2016/9/10.
 */
initMenuTree();
function initMenuTree(){
    var user=DCMS.Busi.getUser();
    var userMenus=user.userMenus;
    if(userMenus){
        for(var i=0;i<userMenus.length;i++){
            var menu=userMenus[i];
            var li=document.createElement("li");
            li.id=menu.id;
            var liHtml='';
            if(hasChildMenu(menu)){
                liHtml+='<a href="#">';
            }else{
                liHtml+='<a href="'+menu.url+'">';
            }
            liHtml+='<i class="fa fa-home"></i>';
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
                            liHtml+='<a class="J_menuItem" href="'+child.url+'">'+child.name+'</a>';
                        liHtml+='</li>';
                    }
                liHtml+='</ul>';
            }

            li.innerHTML=liHtml;
            $("#side-menu").append(li);
        }
    }
}

function hasChildMenu(menu){
    if(menu.childMenu && menu.childMenu.length>0){
        return true;
    }
    return false;
}