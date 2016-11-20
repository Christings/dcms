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
            {title: '设备分类', data: 'typeId',name:'typeId'},
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

function newUpdate(id,type) {
    if('update'==type){
        $("#modalTitle").text('编辑型号');
        document.getElementById('newUpdateForm').reset();

        var pro=DCMSUtils.SessionStorage.get("PRODUCT_MAP")[id];
        $("#proId").val(pro.id);
        $("#proName").val(pro.name);
        $("#typeId").val(pro.typeId);
        $("#typeName").val(pro.typeId);
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
    rules:{
        proName:{
            required:true,
            minlength:2,
            maxlength:50
        },
        typeId:{
            require:true
        },
        proBrand:{
            required:true,
            minlength:2,
            maxlength:50
        },
        height:{
            require:true,
            digits:true
        },
        weight:{
            require:true,
            number:true
        },
        power:{
            require:true,
            number:true
        }
    },
    messages:{
        proName:icon + "请输入2-50个字符的型号名称",
        typeId:icon + "请选择型号分类",
        proBrand:icon + "请输入2-50个字符的生产厂商",
        proHeight:icon + "请输入合法的整数",
        proWeight:icon + "请输入合法的数字(小数)",
        proPower:icon + "请输入合法的数字(小数)",
    },
    submitHandler:function(form){
        var obj={
            id:$("#proId").val(),
            name:$("#proName").val(),
            typeId:$("#typeId").val(),
            brand:$("#proBrand").val(),
            height:$("#proHeight").val(),
            weight:$("#proWeight").val(),
            power:$("#proPower").val()
        }

        var url='product/add';
        if(pro.id){
            url='product/update';
        }
        $("#modal").modal('hide');
        DCMSUtils.Modal.showLoading();
        DCMSBusi.Api.invoke(url,type).then(function(data){
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