/**
 * Created by Charles on 2016/9/26.
 */
var pageNum = 1;//页码
var pageSize = 10;//行数
function pageInit() {
    //初始化菜单树
    getMenuList(pageNum, pageSize);

}
function getMenuList(pageNum, pageSize) {
    if (!DCMSUtils.SessionStorage.get("MENU_GRID_DATA")) {
        DCMSUtils.Modal.showLoading('菜单加载中...');
        DCMSUtils.Ajax.doPost('menu/datagrid', {pageNum: pageNum, pageSize: pageSize})
            .then(function (data) {
                DCMSUtils.Modal.hideLoading();
                console.log(data);
                if (data.status === 1) {
                    DCMSUtils.SessionStorage.set("MENU_GRID_DATA", data);
                    initTreeGird($("#menuTreeBody"), data.data.records, 0);
                    $(".tree").treegrid({
                        initialState:'collapsed'
                    });
                } else {
                    DCMSUtils.Modal.toast(data.msg, 'forbidden');
                }
            }, function (error) {
                DCMSUtils.Modal.hideLoading();
                DCMSUtils.Modal.toast('获取菜单异常', 'forbidden');
            });
    } else {
        initTreeGird($("#menuTreeBody"), DCMSUtils.SessionStorage.get("MENU_GRID_DATA").data.records, 0);
        $(".tree").treegrid({
            initialState:'collapsed'
        });
    }
}

var menuIndex = 0;
function initTreeGird(container, menuTree, parentIndex) {
    console.log(menuTree);
    for (var i = 0; i < menuTree.length; i++) {
        menuIndex++;
        var menu = menuTree[i];
        var trHtml = '<tr class="treegrid-' + menuIndex + '';
        if(parentIndex){
            trHtml+=' treegrid-parent-'+parentIndex;
        }
        trHtml += '">';
        trHtml += '<td>' + menu.name + '</td>';
        trHtml += '<td><i class="' + menu.iconId + '"></i></td>';
        trHtml += '<td>' + (menu.type?menu.type:'') + '</td>';
        trHtml += '<td>' + menu.url + '</td>';
        trHtml += '<td>' + menu.level + '</td>';
        trHtml += '<td>' + menu.rank + '</td>';
        trHtml += '<td>' + menu.rank + '</td>';
        trHtml += '</tr>';

        container.append((trHtml));

        var childMenu=menu.childMenu;
        if(childMenu && childMenu.length>0){
            var pIndex=menuIndex;
            initTreeGird(container,childMenu,pIndex);
        }

    }
}