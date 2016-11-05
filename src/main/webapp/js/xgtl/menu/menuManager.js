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
                        first:'<li class="first"><a href="javascript:;">首页</a></li>',
                        last:'<li class="last"><a href="javascript:;">末页</a></li>',
                        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
                        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
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
function changeLevel(obj) {
    var level=obj.value;
    console.log(level);
    if(level=='1'){
        $(".menuPDiv").css('display','none');
        $("#menuPId").val('');
        $("#menuPName").text('');
    }else{
        $(".menuPDiv").css('display','block');
    }
}

/**
 * 选择父菜单
 */
var jsTreeIndex=0;
$("#selectParentBtn").click(function(){
    DCMSUtils.Modal.showLoading();
    DCMSUtils.Ajax.doPost('menu/tree').then(function(data){
        if(data.status=='1'){
            var treeData=transDataToJsTree(data.data,jsTreeIndex);
            console.log(treeData);
            $('#menuJsTree').jstree({
                'core': {
                    'check_callback': true,
                     'data':treeData
                }
            });
            DCMSUtils.Modal.hideLoading();
            $("#menuModal").modal('hide');
            $("#menuTreeModal").modal('show');
        }else{
            DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
    });
});

$("#confirmParentMenuBtn").click(function(){
    var selected=$("#menuJsTree").jstree(true).get_selected();

    if(selected.length==0){
        DCMSUtils.Modal.alert('请选择父级菜单','');
        return ;
    }
    var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[selected[0]];
    $("#menuPId").val(pMenu.id);
    $("#menuPName").text(pMenu.name);
    $("#menuLevel").text(pMenu.rank+1);
    $("#menuTreeModal").modal('hide');
    $("#menuModal").modal('show');
});
/**
 * 转换数据适配js tree
 * @param menuList
 * @param container
 * @param pindex
 */
function transDataToJsTree(menuList,jsIndex){
    for(var i=0;i<menuList.length;i++){
        var menu=menuList[i];
        menu.text=menu.name
        menu.icon=menu.iconId;
        if(menu.childMenu && menu.childMenu.length>0){
            menu.state={'opened': true};
            menu.children=transDataToJsTree(menu.childMenu,jsIndex);
        }
    }
    return menuList;
}

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
        shadeClose:true,//点击遮罩层关闭弹出框
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
 * 新增或者更新菜单 模态框
 * @param menuId 新增时为父菜单，更新时为当前菜单
 * @param type 操作类型 new:新增，update:更新
 */
function menuNewUpdate(menuId,type){
    if(type=='new'){
        $("#menuModalTitle").text('新增菜单');
        if(menuId){
            document.getElementById("menuNewUpdateForm").reset();
            var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
            $(".menuPDiv").css('display','block');
            $("#menuLevel").val(pMenu.level+1);
            $("#mLevel").val('N');
            $("#menuPName").text(pMenu.name);
            $("#menuPId").val(pMenu.id);
        }else{
            $("#menuPName").text('');
            document.getElementById("menuNewUpdateForm").reset();
        }
        $("#menuModal").modal();
    }else if(type=='update'){
        $("#menuModalTitle").text('编辑菜单');
        var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
        $("#menuId").val(menuId);
        $("#menuName").val(menu.name);
        $("#menuIcon").attr('class',menu.iconId?menu.iconId:'glyphicon glyphicon-th-list');
        $("#menuType").val(menu.type);
        $("#menuLevel").val(menu.level);


        $("#menuUrl").val(menu.url);
        $("#menuRank").val(menu.rank);

        var lv=menu.level==1?menu.level:'N';
        $("#mLevel").val(lv);
        menu.value=lv;
        changeLevel(menu);
        var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menu.parentId];
        if(pMenu){
            $("#menuPId").val(pMenu.id);
            $("#menuPName").text(pMenu.name);
        }
        $("#menuModal").modal();
    }
}

/**
 * 新增、更新模态框校验
 * @type {string}
 */
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
    $("#operationBody").empty();
    if($("#menuMainDiv").attr('class')=='col-sm-12'){
        var menu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[menuId];
        $("#operationTitle").text('【'+menu.name+'】精细化权限控制');
        $("#operationModalTitle").html('【'+menu.name+'】精细化权限控制');
        DCMSUtils.Modal.showLoading('获取操作列表');
        DCMSUtils.Ajax.doPost('/menu/operation/getMenuId',{menuId:menuId}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            console.log(data);
            if(data.status==1){
                DCMSUtils.SessionStorage.set(menuId+'_OPERATION_LIST',data.data);
                for(var i=0;i<data.data.length;i++){
                    var opt=data.data[i];
                    var tr='<tr id="tr_'+opt.id+'">';
                    tr+='<td>'+opt.name+'</td>';
                    tr+='<td>'+opt.url+'</td>';
                    tr+='<td>'+
                        '<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="updateOperation(\''+opt.id+'\')"></i>&nbsp;&nbsp;'+
                        '<i class="glyphicon glyphicon-trash"  title="删除"     onclick="deleteOperation(\''+opt.id+'\')"></i>&nbsp;&nbsp;'
                        +'</td>';
                    tr+='</tr>';
                    $("#operationBody").append(tr);
                }
                $("#operationMenuId").val(menuId);
                optionSetting('show');
            }else{
                DCMSUtils.Modal.toast('获取操作列表出错','forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('获取操作列表出错','forbidden');
        });

    }else{
        optionSetting('hide');
    }
}

function addNewOperation(){
    document.getElementById("menuOperationForm").reset();
    //蛋疼的问题，hidden未被重置
    $("#operationId").val("");
    $("#menuOptionModal").modal('show');
}
/**
 * 保存、修改精细操作
 */
$("#menuOperationForm").validate({
    rules:{
        operationName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        operationUrl:{
            required:true,
            maxlength:100
        }
    },
    messages:{
        operationName:icon + "请输入2-50个字符的操作名称",
        operationUrl:icon+"请输入100字符以内的字符"
    },
    submitHandler:function(form){
        var operationId=$("#operationId").val();
        var ajaxUrl='menu/operation/add';
        if(operationId){
            ajaxUrl='menu/operation/update';
        }
        DCMSUtils.Modal.showLoading();
        var optData={
            menuId:$("#operationMenuId").val(),
            id:operationId,
            name:$("#operationName").val(),
            url:$("#operationUrl").val()
        }
        DCMSUtils.Ajax.doPost(ajaxUrl,optData).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status==1){
                if(operationId){
                    $("#tr_"+operationId).remove();
                }
                var opt=data.data;
                var tr='<tr id="tr_'+opt.id+'">';
                tr+='<td>'+opt.name+'</td>';
                tr+='<td>'+opt.url+'</td>';
                tr+='<td>'+
                    '<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="updateOperation(\''+opt.id+'\')"></i>&nbsp;&nbsp;'+
                    '<i class="glyphicon glyphicon-trash"  title="删除"     onclick="deleteOperation(\''+opt.id+'\')"></i>&nbsp;&nbsp;'
                    +'</td>';
                tr+='</tr>';
                $("#operationBody").append(tr);

                var optList=DCMSUtils.SessionStorage.get(optData.menuId+"_OPERATION_LIST");
                optList.push(data.data);
                DCMSUtils.SessionStorage.set(optData.menuId+"_OPERATION_LIST",optList);
                document.getElementById("menuOperationForm").reset();
                $("#menuOptionModal").modal('hide');
            }else{
                DCMSUtils.Modal.toast('保存精细控制出错','forbidden');
            }
        },function(error){
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('保存精细控制出错','forbidden');
        });
    }
});

/**
 * 编辑精细控制
 * @param optId
 */
function updateOperation(optId){
    var menuId=$("#operationMenuId").val();
    var opt;
    var optList=DCMSUtils.SessionStorage.get(menuId+"_OPERATION_LIST");
    if(optList){
        for(var i=0;i<optList.length;i++){
            var o=optList[i];
            if(o.id==optId){
                opt=o;
                break;
            }
        }
        $("#operationId").val(optId);
        $("#operationName").val(opt.name);
        $("#operationUrl").val(opt.url);
        $("#menuOptionModal").modal('show');
    }

}

function deleteOperation(optId) {
    var menuId=$("#operationMenuId").val();
    var opt;
    var optList=DCMSUtils.SessionStorage.get(menuId+"_OPERATION_LIST");
    if(optList){
        for(var i=0;i<optList.length;i++){
            var o=optList[i];
            if(o.id==optId){
                opt=o;
                break;
            }
        }
        DCMSUtils.Modal.confirm('确定删除操作['+opt.name+']吗？','',function () {
            DCMSUtils.Modal.showLoading('删除中...');
            DCMSUtils.Ajax.doPost('menu/operation/delete',{id:optId}).then(function(data){
                DCMSUtils.Modal.hideLoading();
                if(data.status=='1'){
                    $("#tr_"+optId).remove();
                    DCMSUtils.Modal.toast('删除成功','');
                }else{
                    DCMSUtils.Modal.toast('删除出错'+data.msg,'forbidden');
                }
            },function (error) {
                DCMSUtils.Modal.hideLoading();
                DCMSUtils.Modal.toast('删除出错','forbidden');
            });
        });
    }
}
