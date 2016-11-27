/**
 * Created by Charles on 2016/11/15.
 */
var dtApi;
function pageInit() {
    // 初始化菜单树
    var dtTable=$("#tableList").dataTable({
        /**
         *
         * @param data DT 封装的向后台传递的参数
         * @param callback 回调函数，用户向DT传数据
         * @param settings 一些设置
         */
        ajax:function(data, callback, settings){
            console.log(data);
            var params=DCMSUtils.DataTables.handleParams(data);
            params.name=$("#searchName").val();
            params.brand=$("#searchBrand").val();
            params.typeName=$("#searchTypeName").val();
            params.power=$("#searchPower").val();
            params.height=$("#searchHeight").val();
            params.weight=$("#searchWeight").val();
            DCMSBusi.Api.invoke("product/datagrid",params).then(function (data) {
                if(data.status=='1'){
                    data=data.data;
                    var map={};
                    for(var i=0;i<data.records.length;i++){
                        map[data.records[i].id]=data.records[i];
                    }
                    DCMSUtils.SessionStorage.set("PRODUCT_MAP",map);
                    //组织DT标准的返回值
                    callback({
                        data:data.records,
                        recordsTotal:data.count,
                        recordsFiltered:data.count
                    });
                }
            });
        },
        columns: [
            {title: '名称', data: 'name',name:'name'},
            {title: '重量', data: 'weight',name:'weight'},
            {title: '高度', data: 'height',name:'height'},
            {title: '额定功率', data: 'power',name:'power'},
            {title: '生产厂商', data: 'brand',name:'brand'},
            {title: '设备分类', data: 'category.name',name:'category.name'},
            {title: '操作', data: 'id'}
        ],
        columnDefs:[{
            orderable:false,
            targets:6,
            render:function(data,type,row,meta){
                var html='';
                    // html+='<i class="glyphicon glyphicon-cog" title="菜单配置" onclick="setting(\'' + row.id + '\')"></i>&nbsp;&nbsp;';
                    html+='<i class="glyphicon glyphicon-pencil" title="编辑"     onclick="newUpdate(\'' + row.id + '\',\'update\')"></i>&nbsp;&nbsp;';
                    html+='<i class="glyphicon glyphicon-trash"  title="删除"     onclick="deleteFun(\'' + row.id + '\')"></i>&nbsp;&nbsp;';
                return html;
            }
        }]
    });
    dtApi=dtTable.api();

    dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

    $("#roleListTable tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            dtTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );

    $("#queryBtn").click(function(){
        dtApi.ajax.reload();
    });

    $("#resetBtn").click(function(){
        document.getElementById("queryForm").reset();
    });
}

function openUpload(){
    layer.open({
        title:'型号上传',
        type:2,
        area: ['600px', '430px'],
        fix: false, //不固定
        closeBtn:1,
        moveOut:true,
        shadeClose:false,//点击遮罩层关闭弹出框
        content: DCMSUtils.URL.getContentPath()+'webpages/xgtl/product/proUploader.html'
    });
}



function newUpdate(id,type) {
    if('update'==type){
        $("#modalTitle").text('编辑型号');
        document.getElementById('newUpdateForm').reset();

        var pro=DCMSUtils.SessionStorage.get("PRODUCT_MAP")[id];
        $("#proId").val(pro.id);
        $("#proName").val(pro.name);
        $("#categoryId").val(pro.categoryId);
        $("#typeName").val(pro.categoryId);
        $("#proBrand").val(pro.brand);
        $("#proHeight").val(pro.height);
        $("#proWeight").val(pro.weight);
        $("#proPower").val(pro.power);

        var pType=DCMSUtils.SessionStorage.get("PRODUCT_MAP")[type.parentId];
        if(pType){
            $("#typePId").val(pType.id);
            $("#typePName").html(pType.name);
            $(".typePDiv").css('display','block');
        }
    }
    $("#modal").modal();
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#newUpdateForm").validate({
    // debug:true,
    rules:{
        proName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        categoryId:{
            require:true
        },
        proBrand:{
            required:true,
            minlength:2,
            maxlength:50
        },
        proHeight:{
            required:true,
            digits:true
        },
        proWeight:{
            required:true,
            number:true
        },
        proPower:{
            required:true,
            number:true
        }
    },
    messages:{
        proName:icon + "请输入2-50个字符的型号名称",
        categoryId:icon + "请选择型号分类",
        proBrand:icon + "请输入2-50个字符的生产厂商",
        proHeight:icon + "请输入合法的整数",
        proWeight:icon + "请输入合法的数字(小数)",
        proPower:icon + "请输入合法的数字(小数)",
    },
    submitHandler:function(form){
        var obj={
            id:$("#proId").val(),
            name:$("#proName").val(),
            categoryId:$("#categoryId").val(),
            brand:$("#proBrand").val(),
            height:$("#proHeight").val(),
            weight:$("#proWeight").val(),
            power:$("#proPower").val()
        }

        var url='product/add';
        if(obj.id){
            url='product/update';
        }
        $("#modal").modal('hide');
        DCMSUtils.Modal.showLoading();
        DCMSBusi.Api.invoke(url,obj).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                //保存成功清空form
                document.getElementById("newUpdateForm").reset();
                DCMSUtils.Modal.toast('保存成功','');
                dtApi.ajax.reload();
            }else{
                DCMSUtils.Modal.toast('保存出错'+data.msg,'forbidden');
            }
        },function(error){
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('保存异常','forbidden');
        });
    }
});

function deleteFun(id){
    var obj=DCMSUtils.SessionStorage.get("PRODUCT_MAP")[id];

    DCMSUtils.Modal.confirm('确定删除型号['+obj.name+']吗？','',function () {
        DCMSUtils.Modal.showLoading('删除中...');
        DCMSBusi.Api.invoke('product/delete',{id:id}).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                DCMSUtils.Modal.toast('删除成功','');
                dtApi.ajax.reload();
            }else{
                DCMSUtils.Modal.toast('删除出错'+data.msg,'forbidden');
            }
        },function (error) {
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('删除异常','forbidden');
        });
    })
}

var zTreeObj;
var zTreeSetting={
    data:{
        simpleData:{
            enable:true,
            idKey:"id",
            pIdKey:"parentId"
        }
    },
    check:{
        enable:false,
        chkStyle:'checkbox'
    },
    view:{
        nameIsHTML:true
    },
    callback:{
        onClick:zTreeOnClick
    }
}

var jsTreeIndex=0;
function transDataToJsTree(menuList,jsIndex){
    for(var i=0;i<menuList.length;i++){
        var menu=menuList[i];
        if(menu.children && menu.children.length>0){
            menu.children=transDataToJsTree(menu.children,jsIndex);
        }
        if(jsIndex==0){
            menu.parentId='';
        }
    }
    return menuList;
}

function selectProType() {
    DCMSUtils.Modal.showLoading();
    DCMSBusi.Api.invoke('product/category/tree',null).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            console.log(data);
            // var TData={id:0,name:'分类',children:transDataToJsTree(data.data,jsTreeIndex)};
            var TData=transDataToJsTree(data.data,jsTreeIndex);
            console.log(TData);
            zTreeObj=$.fn.zTree.init($("#typeZtree"),zTreeSetting,TData);
            zTreeObj.expandAll(true);
            $("#typeModal").modal();
        }else{
            DCMSUtils.Modal.toast('获取分类数据出错'+data.msg,'forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('获取分类树异常','forbidden');
    });
}

function zTreeOnClick(event, treeId, treeNode) {

}

function selectTreeNodeAll(){
    zTreeObj.checkAllNodes(true);
}

function unSelectTreeNodeAll(){
    zTreeObj.checkAllNodes(false);
}