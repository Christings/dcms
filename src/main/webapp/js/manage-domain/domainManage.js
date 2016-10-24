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
    getDomainList(globlePageNum, globlePageSize);
}

//初始化菜单
function getDomainList(pageNum, pageSize) {
        console.log('pageNum:'+pageNum+";pageSize:"+pageSize);
        $("#domainTreeBody").empty();
        DCMSUtils.Modal.showLoading('菜单加载中...');
        DCMSUtils.Ajax.doPost('domain/datagrid', {pageNum: pageNum, pageSize: pageSize})
            .then(function (data) {
                DCMSUtils.Modal.hideLoading();
                console.log(data);
                if (data.status === 1) {
                    initTreeGird($("#domainTreeBody"),data.data, 0);
                    $(".tree").treegrid({
                        initialState:'collapsed'
                    });
                    //分页
                    $("#domainTreePage").jqPaginator({
                        totalPages: data.pageCount,
                        visiblePages: 5,
                        currentPage: pageNum,
                        onPageChange: function (num, type) {
                            if(type=='change'){
                                //关闭精细化权限控制
                                optionSetting('hide');
                                globlePageNum=num;
                                getDomainList(globlePageNum,globlePageSize);
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

var domainIndex = 0;

function initTreeGird(container, domainTree, parentIndex) {
    for (var i = 0; i < domainTree.length; i++) {
        domainIndex++;
        var domain = domainTree[i];

        var trHtml = '<tr class="treegrid-' + domainIndex + '';
        if(parentIndex){
            trHtml+=' treegrid-parent-'+parentIndex;
        }
        trHtml += '">';
        trHtml += '<td>' + domain.name + '</td>';
        trHtml += '<td>' + domain.description + '</td>';
        trHtml += '<td>' +
                        '<i class="glyphicon glyphicon-cog"    title="操作配置" onclick="domainSetting(\''+domain.id+'\')"></i>&nbsp;&nbsp;' +
                        '<i class="glyphicon glyphicon-plus"   title="新增"     onclick="domainNewUpdate(\''+domain.id+'\',\'new\')"></i>&nbsp;&nbsp;' +
                        '<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="domainNewUpdate(\''+domain.id+'\',\'update\')"></i>&nbsp;&nbsp;'+
                        '<i class="glyphicon glyphicon-trash"  title="删除"     onclick="domainDelete(\''+domain.id+'\')"></i>&nbsp;&nbsp;' +
                    '</td>';
        trHtml += '</tr>';

        container.append((trHtml));

        //保存domainTree数据，方便编辑等
        var domainMap=DCMSUtils.SessionStorage.get("Domain_TREE_MAP");
        if(!domainMap){
            domainMap={};
        }
        domainMap[domain.id]=domain;
        DCMSUtils.SessionStorage.set("Domain_TREE_MAP",domainMap);

        //子节点初始化
        var childDomain=domain.childDoMain;
        if(childDomain && childDomain.length>0){
            var pIndex=domainIndex;
            initTreeGird(container,childDomain,pIndex);
        }
    }
}


// function optionSetting(onOff){
//     if(onOff=='show'){
//         $("#domainMainDiv").attr('class','col-sm-7');
//         $("#domainOperationDiv").attr('class','col-sm-5');
//         $("#domainOperationDiv").css('display','block');
//     }else if(onOff=='hide'){
//         $("#domainMainDiv").attr('class','col-sm-12');
//         $("#domainOperationDiv").removeAttr('class');
//         $("#domainOperationDiv").css('display','none');
//     }
// }

// $('.close-option-setting').click(function () {
//     optionSetting('hide');
// });

// /**
//  * 更改菜单级别
//  * @param obj
//  */
// function changeLevel(obj) {
//     var level=obj.value;
//     console.log(level);
//     if(level=='1'){
//         $(".domainPDiv").css('display','none');
//         $("#domainPId").val('');
//         $("#domainPName").text('');
//     }else{
//         $(".domainPDiv").css('display','block');
//     }
// }

/**
 * 选择父菜单
 */
var jsTreeIndex=0;
$("#selectParentBtn").click(function(){
    DCMSUtils.Modal.showLoading();
    DCMSUtils.Ajax.doPost('domain/tree').then(function(data){
        if(data.status=='1'){
            var treeData=transDataToJsTree(data.data,jsTreeIndex);
            console.log(treeData);
            $('#domainJsTree').jstree({
                'core': {
                    'check_callback': true,
                    'data':treeData
                }
            });
            DCMSUtils.Modal.hideLoading();
            $("#domainModal").modal('hide');
            $("#domainTreeModal").modal('show');
        }else{
            DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
    });
});

$("#confirmParentDomainBtn").click(function(){
    var selected=$("#domainJsTree").jstree(true).get_selected();

    if(selected.length==0){
        DCMSUtils.Modal.alert('请选择父级菜单','');
        return ;
    }
    var pDomain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[selected[0]];
    $("#domainPId").val(pDomain.id);
    $("#domainPName").text(pDomain.name);
    // $("#domainLevel").text(pDomain.rank+1);
    $("#domainTreeModal").modal('hide');
    $("#domainModal").modal('show');
});
/**
 * 转换数据适配js tree
 * @param domainList
 * @param container
 * @param pindex
 */
function transDataToJsTree(domainList,jsIndex){
    for(var i=0;i<domainList.length;i++){
        var domain=domainList[i];
        domain.text=domain.name
        if(domain.childDoMain && domain.childDoMain.length>0){
            domain.state={'opened': true};
            domain.children=transDataToJsTree(domain.childDoMain,jsIndex);
        }
    }
    return domainList;
}

// /**
//  * 选择图标
//  */
// $("#selectIconBtn").click(function () {
//     var icon=$(this).prev().attr('id');
//     console.log(icon);
//     layer.open({
//         title:'图标',
//         type:2,
//         area: ['800px', '530px'],
//         fix: false, //不固定
//         closeBtn:1,
//         moveOut:true,
//         content: DCMSUtils.URL.getContentPath()+'webpages/pub/iconSelect.html'
//     });
// });

// /**
//  * 选择图标回调
//  * @param iconClass
//  */
// function getIcon(iconClass){
//     $("#domainIcon").removeClass();
//     $("#domainIcon").addClass(iconClass);
// }

/**
 * 新增或者更新菜单 模态框
 * @param domainId 新增时为父菜单，更新时为当前菜单
 * @param type 操作类型 new:新增，update:更新
 */
function domainNewUpdate(domainId,type){
    if(type=='new'){
        $("#domainModalTitle").text('新增组织机构');
        if(domainId){
            var pdomain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[domainId];
            $(".domainPDiv").css('display','block');
            $("#domainPName").text(pDomain.name);
            $("#domainPId").val(pDomain.id);
        }
        $("#domainModal").modal();
    }else if(type=='update'){
        $("#domainModalTitle").text('编辑组织机构');
        var domain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[domainId];
        $("#domainId").val(domainId);
        $("#domainName").val(domain.name);
        $("#domainDescript").val(domain.description);


        // $("#domainUrl").val(domain.url);
        // $("#domainRank").val(domain.rank);

        // var lv=domain.level==1?domain.level:'N';
        // $("#mLevel").val(lv);
        // domain.value=lv;
        // changeLevel(domain);
        var pDomain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[domain.parentId];
        if(pDomain){
            $("#domainPId").val(pDomain.id);
            $("#domainPName").text(pDomain.name);
        }
        $("#domainModal").modal();
    }
}

/**
 * 新增、更新模态框校验
 * @type {string}
 */
var icon = "<i class='fa fa-times-circle'></i> ";
$("#domainNewUpdateForm").validate({
    rules:{
        domainName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        domainDescript:{
            required:true,
            minlength:2,
            maxlength:50
        }
    },
    messages:{
        domainName:icon + "请输入2-50个字符的组织机构名称",
        domainDescript:icon + "请输入2-50个字符的组织机构描述"
    },
    submitHandler:function(form){
        saveDomain();
    }
});
//保存更改
function saveDomain(){
    var domain={
        id:$("#domainId").val(),
        name:$("#domainName").val(),
        description:$("#domainDescript").val(),
        parentId:$("#domainPId").val()
    }

    $("#domainModal").modal('hide');
    DCMSUtils.Modal.showLoading();
    var ajaxUrl='domain/add';
    if(domain.id){
        ajaxUrl='domain/update';
    }
    DCMSUtils.Ajax.doPost(ajaxUrl,domain).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            //保存成功清空form
            document.getElementById("domainNewUpdateForm").reset();
            getDomainList(globlePageNum, globlePageSize);
            DCMSUtils.Modal.toast('保存组织机构成功','');
        }else{
            DCMSUtils.Modal.toast('保存组织机构出错'+data.msg,'forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('保存组织机构出错','forbidden');
    });
}


//删除菜单
function domainDelete(domainId){
    var domain=DCMSUtils.SessionStorage.get("Domain_TREE_MAP")[domainId];

    DCMSUtils.Modal.confirm('确定删除['+domain.name+']吗？','',function () {
        DCMSUtils.Modal.showLoading('组织机构删除中...');
        DCMSUtils.Ajax.doPost('domain/delete',{key:domainId}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                getDomainList(globlePageNum, globlePageSize);
                DCMSUtils.Modal.toast('删除组织机构成功','');
            }else{
                DCMSUtils.Modal.toast('删除组织机构出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除组织机构出错','forbidden');
        });
    })
}

// //当前正在配置权限的菜单
// var currentOptionDomainId;
// //菜单配置
// function domainSetting(domainId) {
//     if(currentOptiondomainId!=domainId){
//         //当前显示的与点击要配置的按钮不是同一个时，先隐藏，便于配置的显示
//         optionSetting('hide');
//         currentOptiondomainId=domainId;
//     }
//     if($("#domainMainDiv").attr('class')=='col-sm-12'){
//         var domain=DCMSUtils.SessionStorage.get("domain_TREE_MAP")[domainId];
//         $("#operationTitle").text('【'+domain.name+'】精细化权限控制');
//         optionSetting('show');
//     }else{
//         optionSetting('hide');
//     }
// }
