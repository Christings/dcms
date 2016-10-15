/**
 * Created by Charles on 2016/9/26.
 */
var globlePageNum = 1;//页码
var globlePageSize = 10;//行数

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
                                //关闭精细化权限控制
                                optionSetting('hide');
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
                        '<i class="glyphicon glyphicon-plus"   title="新增"     onclick="menuNewUpdate(\''+menu.id+'\',\'new\')"></i>&nbsp;&nbsp;' +
                        '<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="menuNewUpdate(\''+menu.id+'\',\'update\')"></i>&nbsp;&nbsp;'+
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


function optionSetting(onOff){
    if(onOff=='show'){
        $("#menuMainDiv").attr('class','col-sm-7');
        $("#menuOperationDiv").attr('class','col-sm-5');
        $("#menuOperationDiv").css('display','block');
    }else if(onOff=='hide'){
        $("#menuMainDiv").attr('class','col-sm-12');
        $("#menuOperationDiv").removeAttr('class');
        $("#menuOperationDiv").css('display','none');
    }
}

$('.close-option-setting').click(function () {
    optionSetting('hide');
});

/**
 * 更改菜单级别
 * @param obj
 */
$("#mLevel").change(function(){
    var level=this.value;
    console.log(level);
    if(level=='1'){
        $(".menuPDiv").css('display','none');
    }else{
        $(".menuPDiv").css('display','block');
    }
});

$("#selectParentBtn").click(function(){
    DCMSUtils.Modal.showLoading();
    DCMSUtils.Ajax.doPost('menu/tree').then(function(data){
        DCMSUtils.Modal.hideLoading();

    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
    });
});

/**
 * 选择图标
 */
$("#selectIconBtn").click(function () {
    var icon=$(this).prev().attr('id');
    console.log(icon);
    layer.open({
        title:'图标',
        type:2,
        area: ['800px', '530px'],
        fix: false, //不固定
        closeBtn:1,
        moveOut:true,
        content: DCMSUtils.URL.getContentPath()+'webpages/pub/iconSelect.html'
    });
});
/**
 * 选择图标回调
 * @param iconClass
 */
function getIcon(iconClass){
    $("#menuIcon").removeClass();
    $("#menuIcon").addClass(iconClass);
}

/**
 * 新增或者更新菜单
 * @param menuId 新增时为父菜单，更新时为当前菜单
 * @param type 操作类型 new:新增，update:更新
 */
function menuNewUpdate(menuId,type){
    if(type=='new'){
        $(".modal-title").text('新增菜单');
        if(menuId){
            var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
            $(".menuPDiv").css('display','block');
            $("#menuLevel").val('N');
            $("#menuPName").text(pMenu.name);
            $("#menuPId").val(pMenu.id);
        }
        $("#menuModal").modal();
    }else if(type=='update'){
        $(".modal-title").text('编辑菜单');
        var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
        $("#menuId").val(menuId);
        $("#menuName").val(menu.name);
        $("#menuIcon").attr('class',menu.iconId?menu.iconId:'glyphicon glyphicon-th-list');
        $("#menuType").val(menu.type);
        $("#menuLevel").val(menu.level==1?menu.level:'N');
        $("#menuUrl").val(menu.url);
        $("#menuRank").val(menu.rank);
        $("#menuModal").modal();
    }
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#menuNewUpdateForm").validate({
    rules:{
        menuName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        menuType:{
            range:[0,999999]
        },
        menuUrl:{
            required:true,
            maxlength:100
        },
        menuRank:{
            required:true,
            range:[0,999999]
        }
    },
    messages:{
        menuName:icon + "请输入2-50个字符的菜单名称",
        menuType:icon + "请输入0-99999之间的整数",
        menuUrl:icon+"请输入100字符以内的字符",
        menuRank:icon + "请输入0-99999之间的整数",
    },
    submitHandler:function(form){
        saveMenu();
    }
});
//保存更改
function saveMenu(){
    var menu={
        id:$("#menuId").val(),
        name:$("#menuName").val(),
        rank:$("#menuRank").val(),
        level:$("#menuLevel").val(),
        url:$("#menuUrl").val(),
        type:$("#menuType").val(),
        iconId:$("#menuIcon").attr('class'),
    }
    if($("#menuLevel").val()!='1'){
        menu.parentId=$("#menuPId").val();
    }

    $("#menuModal").modal('hide');
    DCMSUtils.Modal.showLoading();
    var ajaxUrl='menu/add';
    if(menu.id){
        ajaxUrl='menu/update';
    }
    DCMSUtils.Ajax.doPost(ajaxUrl,menu).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            //保存成功清空form
            document.getElementById("menuNewUpdateForm").reset();
            getMenuList(globlePageNum, globlePageSize);
            DCMSUtils.Modal.toast('保存菜单成功','');
        }else{
            DCMSUtils.Modal.toast('保存菜单出错'+data.msg,'forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('保存菜单出错','forbidden');
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
                DCMSUtils.Modal.toast('删除菜单成功','');
            }else{
                DCMSUtils.Modal.toast('删除菜单出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除菜单出错','forbidden');
        });
    })
}

//当前正在配置权限的菜单
var currentOptionMenuId;
//菜单配置
function menuSetting(menuId) {
    if(currentOptionMenuId!=menuId){
        //当前显示的与点击要配置的按钮不是同一个时，先隐藏，便于配置的显示
        optionSetting('hide');
        currentOptionMenuId=menuId;
    }
    if($("#menuMainDiv").attr('class')=='col-sm-12'){
        var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
        $("#operationTitle").text('【'+menu.name+'】精细化权限控制');
        optionSetting('show');
    }else{
        optionSetting('hide');
    }
}
