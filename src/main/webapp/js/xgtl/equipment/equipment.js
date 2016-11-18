/**
 * Created by geng on 2016/11/7.
 */

/**
 * 页面初始化
 */
var dtApi;
function pageInit() {

    // 初始化菜单树
    var dtTable=$("#data-table");
    dtApi=dtTable.DataTable({
        /**
         *
         * @param data DT 封装的向后台传递的参数
         * @param callback 回调函数，用户向DT传数据
         * @param settings 一些设置
         */
        ajax:function(data, callback, settings){

            var params=DCMSUtils.DataTables.handleParams(data);
            params.name = $("#cabinetName").val();
            params.resourceCode = $("#cabinetResourceCode").val();
            params.status = $("#status").val();
            params.roomName = $("#cabinetRoomName").val();
            DCMSBusi.Api.invoke("cabinet/datagrid",params).then(function (data) {
                if(data.status=='1'){
                    //组织DT标准的返回值
                    callback({
                        data:data.data.records,
                        recordsTotal:data.data.count,
                        recordsFiltered:data.data.count
                    });
                }
            });
        },
        columns: [
            {title: '机柜名称', data: 'name', name: 'name'},
            {title: '资源编码', data: 'resourceCode', name: 'resourceCode'},
            {title: '状态', data: 'status', name: 'status'},
            {title: '所属机房', data: 'roomName', name: 'roomName'},
            {title: '操作', data: null}
        ],
        columnDefs: [
            {
                orderable:false,//禁用排序
                targets:[4]   //指定的列
            },
            {
                targets: 4,
                render: function (data,type,row,meta) {
                    var html = "<i class='glyphicon glyphicon-pencil' title='编辑' onclick=\"editItem('" + row.id+"','update')\"'></i>&nbsp;&nbsp;" +
                        "<i class='glyphicon glyphicon-user' title='添加用户' onclick=\"addUser('" + row.id+"','"+row.equName+"')\"'></i>&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/addserver.png' title='添加服务器' onclick=\"addEqu('" + row.id+"','" + row.name+"','server_add')\"' />&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/network_add.png' title='添加网络设备' onclick=\"addEqu('" + row.id+"','" + row.name+"','network_add')\"' />&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/distrb_add.png' title='添加配线架' onclick=\"addEqu('" + row.id+"','" + row.name+"','distrb_add')\"' />&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/storage_add.png' title='添加小机存储设备' onclick=\"addEqu('" + row.id+"','" + row.name+"','storage_add')\"' />&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/other_add.png' title='添加其他设备' onclick=\"addEqu('" + row.id+"','" + row.name+"','other_add')\"' />&nbsp;&nbsp;" +
                        "<a target='_blank' href='draganddrop.html?cabinet_id=100082&direction=1'><img class='click-icon' src='"+getContentPath()+"img/database_add.png' title='展开机柜' onclick=\"viewCabinet('" + row.resourceCode+"')\"' /></a>&nbsp;&nbsp;" +
                        "<img class='click-icon' src='"+getContentPath()+"img/3D_location.png' title='3D视图' onclick=\"location3D('" + row.id+"')\"' />&nbsp;&nbsp;" +
                        "<i class='glyphicon glyphicon-search' data-toggle=\"modal\" data-target=\"#roomPGWatch\" title='平面图' onclick=\"checkView('" + row.resourceCode+"')\"'></i>&nbsp;&nbsp;" +
                        "<i class='glyphicon glyphicon-eye-open' title='维修记录' onclick=\"logFixed('" + row.resourceCode+"')\"'></i>&nbsp;&nbsp;" +
                        "<i class='glyphicon glyphicon-remove' title='删除空机房' onclick=\"deleteCabinet('" + row.id+"')\"'></i>";
                    return html;
                }
            }
        ]
    });

    dtTable.on("draw.DT", datatablesMtr.freeTableHeight(dtTable));

    //点击查询
    $("#queryBtn").on("click", function () {
        dtApi.ajax.reload();
    });
}

//新增/修改机柜信息
function editItem(id,type){
    $("#saveType").val(type);
    getEquType("equipmentTypeId");
    getCPRoomList("roomName");
    if(type=='new'){
        $("#cabinetEditModalTitle").text('增加机柜');
        $("#cabinetId").val();
        document.getElementById("cabinetEditForm").reset();
    }else if(type=='update'){
        $("#cabinetEditModalTitle").text('机柜属性编辑');
        DCMSBusi.Api.invoke("cabinet/getCabinetResultById",{id:id}).then(function (data) {
            if(data.status=='1'){
                $("#cabinetId").val(id);
                $("#equipmentTypeId").val(data.data.equipmentTypeId);
                $("#name").val(data.data.name);
                $("#equHeight").val(data.data.height);
                $("#resourceCode").val(data.data.resourceCode);
                $("#warrantyTime").val(data.data.warrantyTime);
                $("#workTime").val(data.data.workTime);
                $("#roomName").val(data.data.roomId);
            }
        });
    }
    $("#cabinetEditModal").modal();
}

//获取机房列表
function getCPRoomList(selectId) {
    DCMSBusi.Api.invoke("room/selectForChoose").then(function (data) {
        if(data.status=='1'){
            var selectOptions = "<option value=''>请选择所属机房</option>";
            $.each(data.data, function(index, objVal) {
                selectOptions += "<option value='"+objVal["id"]+"'>"+objVal["name"]+"</option>";
            });
            $("#"+selectId).html(selectOptions);
        }
    });
}

//获取设备类型列表
function getEquType(selectId) {
    var selectOptions = "<option value=''>请选择设备类型</option>";
    /*DCMSBusi.Api.invoke("room/selectForChoose").then(function (data) {
        if(data.status=='1'){
            $.each(data.data, function(index, objVal) {
                selectOptions += "<option value='"+objVal["id"]+"'>"+objVal["name"]+"</option>";
            });
        }
    });*/
    selectOptions += "<option value='e52493fcd0a4400b92ca89a6118d00b4'>e52493fcd0a4400b92ca89a6118d00b4</option>";
    $("#"+selectId).html(selectOptions);
}

//保存机房信息
function saveCabinet() {
    DCMSUtils.Modal.showLoading();
    var type = $("#saveType").val();
    var equipmentTypeId = $("#equipmentTypeId").val();
    var name = $("#name").val();
    var resourceCode = $("#resourceCode").val();
    var warrantyTime = $("#warrantyTime").val();
    var workTime = $("#workTime").val();
    var roomId = $("#roomName").val();

    if(equipmentTypeId == null || equipmentTypeId == ''){
        DCMSUtils.Modal.toast('请选择设备类型！','cancel');
        $("#equipmentTypeId").focus();
        return false;
    }
    if(name == null || name == ''){
        DCMSUtils.Modal.toast('请输入机柜名称！','cancel');
        $("#name").focus();
        return false;
    }
    if(resourceCode == null || resourceCode == ''){
        DCMSUtils.Modal.toast('请输入资源编码！','cancel');
        $("#resourceCode").focus();
        return false;
    }
    if(roomId == null || roomId == ''){
        DCMSUtils.Modal.toast('请选择所属机房！','cancel');
        $("#roomName").focus();
        return false;
    }

    var params={
        equipmentTypeId:equipmentTypeId,
        name:name,
        resourceCode:resourceCode,
        warrantyTime:warrantyTime,
        workTime:workTime,
        roomId:roomId
    };

    if(type=='new'){
        DCMSBusi.Api.invoke("cabinet/add",params).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                $("#cabinetEditModal").modal('hide');
                DCMSUtils.Modal.toast('保存机柜信息成功','');
                dtApi.ajax.reload();
            }else{
                DCMSUtils.Modal.toast('保存机柜信息异常','forbidden');
            }
        },function(error){
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('保存机柜信息异常','forbidden');
        });
    }else if(type=='update'){
        var id = $("#cabinetId").val();
        params.id = id;
        DCMSBusi.Api.invoke("cabinet/update",params).then(function(data){
            DCMSUtils.Modal.hideLoading();
            if(data.status=='1'){
                $("#cabinetEditModal").modal('hide');
                DCMSUtils.Modal.toast('保存机柜信息成功','');
                dtApi.ajax.reload();
            }else{
                DCMSUtils.Modal.toast('保存机柜信息异常','forbidden');
            }
        },function(error){
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('保存机柜信息异常','forbidden');
        });
    }
}

//添加用户
function addUser(id,name){
    DCMSUtils.Modal.showLoading();
    $("#addUserId").val(id);
    $("#addUser-cpRoomName").text(name);

    $.when(DCMSBusi.Api.invoke('user/getAll'),DCMSBusi.Api.invoke('room/getServiceRoomUserRels',{serviceRoomId:id}))
        .then(function(userData, userRelsData){
            if(userData.status=='1' &&  userRelsData.status=='1'){
                var relev_userId =[];
                for (j in userRelsData.data) {
                    relev_userId.push(userRelsData.data[j].userId);
                }
                var html = "";
                for (i in userData.data) {
                    if( $.inArray(userData.data[i].id , relev_userId) > -1){
                        html += '<label class="checkbox-inline"><input type="checkbox" name="addUser-checkbox" value="'+userData.data[i].id+'" checked>'+userData.data[i].username+'</label>';
                    }else{
                        html += '<label class="checkbox-inline"><input type="checkbox" name="addUser-checkbox" value="'+userData.data[i].id+'">'+userData.data[i].username+'</label>';
                    }
                }
                $("#addUser-checkbox").html(html);
                DCMSUtils.Modal.hideLoading();
                $("#addUserModal").modal();
            }else{
                DCMSUtils.Modal.toast('加载用户信息出错','forbidden');
            }
        },function(error){
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('加载用户信息异常','forbidden');
        });
}

//添加服务器/网络设备/配线架/小机存储设备/其他设备
function addEqu(id,cabinetName,equCase){
    document.getElementById("addEquForm").reset();
    $("#addEqu-cabinetName").html(cabinetName);
    $("#equCase").val(equCase);
    var addEquModalTitle;
    switch (equCase)
    {
        case "server_add":
            addEquModalTitle="新建服务器";
            $("#group-IP").show();
            $("#group-system").show();
            $("#group-CPU").show();
            $("#group-disk").show();
            $("#group-memory").show();
            break;
        case "network_add":
            addEquModalTitle="新建网络设备";
            $("#group-IP").show();
            $("#group-system").hide();
            $("#group-CPU").hide();
            $("#group-disk").hide();
            $("#group-memory").hide();
            break;
        case "distrb_add":
            addEquModalTitle="新建配线架设备";
            $("#group-IP").hide();
            $("#group-system").hide();
            $("#group-CPU").hide();
            $("#group-disk").hide();
            $("#group-memory").hide();
            break;
        case "storage_add":
            addEquModalTitle="新建小机存储设备";
            $("#group-IP").show();
            $("#group-system").show();
            $("#group-CPU").show();
            $("#group-disk").show();
            $("#group-memory").show();
            break;
        case "other_add":
            addEquModalTitle="新建其它设备";
            $("#group-IP").hide();
            $("#group-system").hide();
            $("#group-CPU").hide();
            $("#group-disk").hide();
            $("#group-memory").hide();
            break;
    }
    $("#addEquModalTitle").html(addEquModalTitle);
    $("#addEquModal").modal();
}

//保存服务器/网络设备/配线架/小机存储设备/其他设备
function saveEqu() {
    DCMSUtils.Modal.showLoading();
    var equipmentTypeId = $("#equipmentTypeId").val();
    var equName = $("#equName").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();
    var roomId = $("#roomId").val();

    var formData = new FormData($("#addEquForm"));
    formData.append('equipmentTypeId',equipmentTypeId);
    formData.append('position',position);
    formData.append('comment',comment);
    formData.append('exterior',exterior);
    formData.append('resourceCode',resourceCode);
    formData.append('address',address);
    formData.append('file',file);
    formData.append('area',area);

    DCMSBusi.Api.invoke("cabinet/addEqu",formData).then(function(data){
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            $("#addEquModal").modal('hide');
            DCMSUtils.Modal.toast('保存设备成功','');
            dtApi.ajax.reload();
        }else{
            DCMSUtils.Modal.toast('保存设备异常','forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('保存设备异常','forbidden');
    });
}

//删除机柜
function deleteCabinet(id) {
    DCMSUtils.Modal.showLoading();
    DCMSBusi.Api.invoke("cabinet/delete",{id:id}).then(function (data) {
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){
            DCMSUtils.Modal.toast('删除机柜信息成功','');
            dtApi.ajax.reload();
        }else{
            DCMSUtils.Modal.toast('删除机柜信息失败','forbidden');
        }
    },function(error){
        DCMSUtils.Modal.hideLoading();
        DCMSUtils.Modal.toast('删除机柜信息失败','forbidden');
    });
}
//Added by ding 2016/11/17
//查看机柜所在机房平面视图
function checkView(resourceCode){
    DCMSBusi.Api.invoke('cabinet/getPositionByResourceCode',{resourceCode:resourceCode}).then(function(data){
        if(data.status=='1'){
            roomPGWatch(data.data.roomIcngphId,data.data.roomResourceCode,resourceCode);
        }else{
            DCMSUtils.Modal.toast('平面图资源异常','forbidden');
        }
    });
}
//Added by ding 2016/11/15
function logFixed(resourceCode){
    $("#logFixedModal").modal('show');
    var myDate = new Date();
    var contents = [];
    var content = {};
    content.fixedUserName = 'admin';
    content.fixedContent = '测试';
    content.fixedTime = myDate.toLocaleString();
    contents.push(content);
    // content.fixedUserName = 'admin';
    // content.fixedContent = '测试';
    // content.fixedTime = myDate.toLocaleString();
    contents.push(content);
    $('#logFixedModalTable').bootstrapTable('destroy');
    $('#logFixedModalTable').bootstrapTable({
            search:true,
            striped: true,
            pagination: true,
            singleSelect: false,
            pageNumber: 2,
            pageSize: 1,
            pageList: [10, 50, 100, 200, 500],
            columns: [
            {
                field: 'fixedUserName',
                title: '维护人'
            },
            {
                field: 'fixedContent',
                title: '维护内容'
            },
            {
                field: 'fixedTime',
                title: '维护时间'
            }
            ],
            data: contents
        });
    var button = '<button class="btn btn-primary" type="button" onclick="addLogFixed(\''+resourceCode+'\')">添加记录</button>';
    var x = document.getElementsByClassName('fixed-table-loading');
    x[0].innerHTML = button;
}

function addLogFixed(resourceCode){
    $('#logFixedModal').modal('hide');
    $('#addLogFixedModal').modal('show');
    var user=DCMSBusi.USER.get();
    var html = '<label>操作人：'+user.username+'</label><br />'+
                '<label>操作记录</label>'+
                '<textarea class="form-control" name="log" id="districtRemark" placeholder="请填写记录信息"></textarea>'+
                '<div id="addLogFixedFooter" class="modal-footer">'+
                    '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
                    '<button type="button" class="btn btn-primary" onclick="saveLogFixed(\''+resourceCode+'\')">保存</button>'+
               ' </div>';
    document.getElementById('addLogFixedModalForm').innerHTML = html;

}

function saveLogFixed(resourceCode){
    $('#addLogFixedModal').modal('hide');
    logFixed(resourceCode);
    document.getElementById('addLogFixedModalForm').reset();
}


//展开机柜
function viewCabinet(resourceCode) {
    DCMSUtils.Modal.showLoading();
    DCMSBusi.Api.invoke("cabinet/getPositionByResourceCode",{resourceCode:resourceCode}).then(function (data) {
        DCMSUtils.Modal.hideLoading();
        if(data.status=='1'){

        }else{
            DCMSUtils.Modal.toast(data.msg,'forbidden');
        }
    });
}
