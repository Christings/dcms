function pageInit(){

}

/**
 * 更改组织机构类型
 * @param obj
 */
function changeLevel(obj) {
    var level=obj.value;
    console.log(level);
    if(level=='1'){
        $(".organizationPDiv").css('display','none');
        // $("#menuPId").val('');
        $("#organizationPName").text('');
    }else{
        $(".organizationPDiv").css('display','block');
    }
}
/*
* 选择父菜单
*/
$("#selectParentBtn").click(function(){
    // DCMSUtils.Modal.showLoading();
    // DCMSUtils.Ajax.doPost('menu/tree').then(function(data){
    //     if(data.status=='1'){
    //         var treeData=transDataToJsTree(data.data,jsTreeIndex);
    //         console.log(treeData);
    $('#organizationJsTree').jstree({
        'core': {
            'check_callback': true,
             'data':[
                {
                    'text':'北京三源合众科技有限公司',
                    'children':[ 
                        {
                            'text':'研发部',
                            'children':[
                                {'text':'研发一组'},
                                {'text':'研发二组'},
                                {'text':'研发三组'}
                            ]
                        },
                        {'text':'测试部'},
                        {'text':'销售部'},
                        {'text':'人力资源部'},
                        {'text':'指挥部'}
                    ]
                }
            ]
        }
    });
    //DCMSUtils.Modal.hideLoading();
    $("#organizationAdd").modal('hide');
    $("#organizationModal").modal('show');
    //     }else{
    //         DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
    //     }
    // },function(error){
    //     DCMSUtils.Modal.hideLoading();
    //     DCMSUtils.Modal.toast('加载菜单树异常','forbidden');
    // });
});

$("#confirmParentOrganizationBtn").click(function(){
    var selected=$("#organizationJsTree").jstree(true).get_selected();
    console.log(selected[0]);
    if(selected.length==0){
        DCMSUtils.Modal.alert('请选择父级菜单','');
        return ;
    }
    // var pMenu=DCMSUtils.SessionStorage.get("MENU_TREE_MAP")[selected[0]];
    // $("#menuPId").val(pMenu.id);
    // $("#menuPName").text(pMenu.name);
    // $("#menuLevel").text(pMenu.rank+1);
     $("#organizationModal").modal('hide');
     $("#organizationAdd").modal('show');
});