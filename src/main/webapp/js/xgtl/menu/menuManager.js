/**
 * Created by Charles on 2016/9/26.
 */
var globlePageNum = 1;//页码
var globlePageSize = 2;//行数
/**
 * 页面初始化
 */
function pageInit() {
    //初始化菜单树
    getMenuList(globlePageNum, globlePageSize);
}

//初始化菜单
function getMenuList(pageNum, pageSize) {
        console.log('pageNum:'+pageNum+";pageSize:"+pageSize);
        $("#menuTreeBody").empty();
        DCMSUtils.Modal.showLoading('菜单加载中...');
        DCMSUtils.Ajax.doPost('menu/datagrid', {pageNum: pageNum, pageSize: pageSize})
            .then(function (data) {
                DCMSUtils.Modal.hideLoading();
                console.log(data);
                if (data.status === 1) {
                    data=data.data;
                    initTreeGird($("#menuTreeBody"),data.records, 0);
                    $(".tree").treegrid({
                        initialState:'collapsed'
                    });
                    //分页
                    $("#menuTreePage").jqPaginator({
                        totalPages: data.pageCount,
                        visiblePages: 5,
                        currentPage: pageNum,
                        onPageChange: function (num, type) {
                            if(type=='change'){
                                globlePageNum=num;
                                getMenuList(globlePageNum,globlePageSize);
                            }
                        }
                    });
                } else {
                    DCMSUtils.Modal.toast(data.msg, 'forbidden');
                }
            }, function (error) {
                DCMSUtils.Modal.hideLoading();
                DCMSUtils.Modal.toast('获取菜单异常', 'forbidden');
            });
}

var menuIndex = 0;

function initTreeGird(container, menuTree, parentIndex) {
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
        trHtml += '<td>' +
                        '<i class="glyphicon glyphicon-cog"    title="操作配置" onclick="menuSetting(\''+menu.id+'\')"></i>&nbsp;&nbsp;' +
                        '<i class="glyphicon glyphicon-plus"   title="新增"     onclick="menuNew(\''+menu.id+'\')"></i>&nbsp;&nbsp;' +
                        '<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="menuUpdate(\''+menu.id+'\')"></i>&nbsp;&nbsp;'+
                        '<i class="glyphicon glyphicon-trash"  title="删除"     onclick="menuDelete(\''+menu.id+'\')"></i>&nbsp;&nbsp;' +
                    '</td>';
        trHtml += '</tr>';

        container.append((trHtml));

        //保存menuTree数据，方便编辑等
        var menuMap=DCMSUtils.SessionStorage.get("MENU_TREE_MAP");
        if(!menuMap){
            menuMap={};
        }
        menuMap[menu.id]=menu;
        DCMSUtils.SessionStorage.set("MENU_TREE_MAP",menuMap);

        //子节点初始化
        var childMenu=menu.childMenu;
        if(childMenu && childMenu.length>0){
            var pIndex=menuIndex;
            initTreeGird(container,childMenu,pIndex);
        }
    }
}

//显示编辑Modal
function menuUpdate(menuId){
    DCMSUtils.Modal.showLoading();
    var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];

    $("#updateMenuId").val(menuId);
    $("#updateMenuName").val(menu.name);
    $("#updateMenuIcon").attr('class',menu.iconId?menu.iconId:'glyphicon glyphicon-th-list');
    $("#updateMenuType").val(menu.type);
    $("#updateMenuLevel").val(menu.level==1?menu.level:'N');
    $("#updateMenuUrl").val(menu.url);
    $("#updateMenuRank").val(menu.rank);

    $("#menuUpdateModal").modal();
    DCMSUtils.Modal.hideLoading();
}
//保存更改
function saveMenuUpdate(){
    var menu={
        id:$("#updateMenuId").val(),
        name:$("#updateMenuName").val(),
        rank:$("#updateMenuRank").val(),
        level:$("#updateMenuLevel").val(),
        url:$("#updateMenuUrl").val(),
        type:$("#updateMenuType").val(),
        iconId:$("#updateMenuIcon").attr('class'),
    }
    if($("#updateMenuLevel").val()!='1'){
        menu.parentId=$("#updateMenuPId").val();
    }

    $("#menuUpdateModal").modal('hide');
    DCMSUtils.Modal.showLoading('菜单编辑中...');
    DCMSUtils.Ajax.doPost('menu/update',menu).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            getMenuList(globlePageNum, globlePageSize);
            DCMSUtils.Modal.alert(data.msg,'');
        }else{
            DCMSUtils.Modal.alert('编辑菜单出错'+data.msg,'');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.alert('编辑菜单出错');
    })
}

//显示新增Modal
function menuNew(pid){
    $("#menuNewModal").modal();
    if(pid){
        var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[pid];
        $(".menuPDiv").css('display','block');
        $("#newMenuLevel").val('N');
        $("#newMenuPName").val(pMenu.name);
        $("#newMenuPId").val(pMenu.id);
    }
}

//保存新增菜单
function saveMenuNew(){
    var menu={
        name:$("#newMenuName").val(),
        rank:$("#newMenuRank").val(),
        level:$("#newMenuLevel").val(),
        url:$("#newMenuUrl").val(),
        type:$("#newMenuType").val(),
        iconId:$("#newMenuIcon").attr('class'),
    }
    if($("#newMenuLevel").val()!=1){
        menu.parentId=$("#newMenuPId").val();
    }
    console.log(menu);
    $("#menuNewModal").modal('hide');
    DCMSUtils.Modal.showLoading('菜单保存中...');
    DCMSUtils.Ajax.doPost('menu/add',menu).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            getMenuList(globlePageNum, globlePageSize);
            DCMSUtils.Modal.alert(data.msg,'');
            $(".form-horizontal").reset();
        }else{
            DCMSUtils.Modal.alert('保存菜单出错'+data.msg,'');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.alert('保存菜单出错');
    });
}

//删除菜单
function menuDelete(menuId){
    var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];

    DCMSUtils.Modal.confirm('确定删除菜单['+menu.name+']吗？','',function () {
        DCMSUtils.Modal.showLoading('菜单删除中...');
        DCMSUtils.Ajax.doPost('menu/delete',{key:menuId}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                getMenuList(globlePageNum, globlePageSize);
                DCMSUtils.Modal.alert(data.msg,'');
            }else{
                DCMSUtils.Modal.alert('删除菜单出错'+data.msg,'');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.alert('删除菜单出错');
        })
    })
}

//菜单配置
function menuSetting(menuId) {
    if($("#menuMainDiv").attr('class')=='col-sm-12'){

        var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];

        $("#operationTitle").text('【'+menu.name+'】精细化权限控制');
        $("#menuMainDiv").attr('class','col-sm-7');
        $("#menuOperationDiv").attr('class','col-sm-5');
        $("#menuOperationDiv").css('display','block');
    }else{
        $("#menuMainDiv").attr('class','col-sm-12');
        $("#menuOperationDiv").removeAttr('class');
        $("#menuOperationDiv").css('display','none');
    }

}

//更改菜单级别
function changeLevel(obj){
    var level=obj.value;
    console.log(level);
    if(level=='1'){
        $(".menuPDiv").css('display','none');
    }else{
        $(".menuPDiv").css('display','block');
    }
}
