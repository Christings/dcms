/**
 * Created by geng on 2016/10/25.
 *
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
			//需要把分页参数转为DCMS接口规范的
			var pageNum=data.start/2+1,pageSize=data.length;
			var params={
				pageNum:pageNum,
				pageSize:pageSize,
				name:$("#cpRoom-name").val(),
				position:$("#cpRoom-position").val()
			};
			DCMSUtils.Ajax.doPost("serviceRoom/datagrid",params).then(function (data) {
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
			{title: '机房名称', data: 'name'},
			{title: '资源编码', data: 'resourceCode'},
			{title: '机房位置', data: 'position'},
			{title: '机房面积', data: 'area'},
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
						"<i class='glyphicon glyphicon-user' title='添加用户' onclick=\"addUser('" + row.id+"','"+row.name+"')\"'></i>&nbsp;&nbsp;" +
						"<i class='glyphicon glyphicon-edit' title='修改备注' onclick=\"editComment('" + row.id+"','"+row.comment+"','"+row.resourceCode+"')\"'></i>&nbsp;&nbsp;" +
						"<i class='glyphicon glyphicon-search' title='查看视图' onclick=\"checkView('" + row.id+"','"+row.imageUrl+"')\"'></i>";
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

//新增/修改机房信息
function editItem(id,type){
	if(type=='new'){
		$("#cpRoomModalTitle").text('增加机房');
		$("#file").parent().show();
		$("#cpRoomId").val();
		document.getElementById("cpRoomNewUpdateForm").reset();
	}else if(type=='update'){
		$("#file").parent().hide();
		$("#cpRoomModalTitle").text('机房属性编辑');
		DCMSUtils.Ajax.doPost("serviceRoom/selectById",{id:id}).then(function (data) {
			if(data.status=='1'){
				$("#cpRoomId").val(id);
				$("#name").val(data.data.name);
				$("#position").val(data.data.position);
				$("#comment").val(data.data.comment);
				$("#exterior").val(data.data.exterior);
				$("#resourceCode").val(data.data.resourceCode);
				$("#address").val(data.data.address);
				$("#area").val(data.data.area);
			}
		});
	}
	$("#cpRoomModal").modal();
	$("#cpRoomNewUpdateForm").submit(function(event){
		var name = $("#name").val();
		var position = $("#position").val();
		var comment = $("#comment").val();
		var exterior = $("#exterior").val();
		var resourceCode = $("#resourceCode").val();
		var address = $("#address").val();
		var obj = document.getElementById("file");
		var file = obj.files[0];
		var area = $("#area").val();

		if(name == null || name == ''){
			//DCMSUtils.Modal.toast('请输入机房名称！','cancel');
			alert('请输入机房名称！');
			$("#name").focus();
			return false;
		}
		if(position == null || position == ''){
			//DCMSUtils.Modal.toast('请输入位置！','cancel');
			alert('请输入位置！');
			$("#position").focus();
			return false;
		}
		if(resourceCode == null || resourceCode == ''){
			//DCMSUtils.Modal.toast('请输入资源编码！','cancel');
			alert('请输入资源编码！');
			$("#resourceCode").focus();
			return false;
		}

		var formData = new FormData($("#cpRoomNewUpdateForm"));
		formData.append('name',name);
		formData.append('position',position);
		formData.append('comment',comment);
		formData.append('exterior',exterior);
		formData.append('resourceCode',resourceCode);
		formData.append('address',address);
		formData.append('file',file);
		formData.append('area',area);
		if(type=='new'){
			$.ajax({
				url:"../../../serviceRoom/add",
				type:'post',
				data: formData,
				processData: false,  // 告诉jQuery不要去处理发送的数据
				contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
				success:function(data){
					dtApi.ajax.reload();
				}
			});
		}else if(type=='update'){
			formData.append('id',id);
			$.ajax({
				url:"../../../serviceRoom/update",
				type:'post',
				data: formData,
				processData: false,  // 告诉jQuery不要去处理发送的数据
				contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
				success:function(data){
					dtApi.ajax.reload();
				}
			});
		}
	});
}


//修改备注
function editComment(id,comment,resourceCode){
	$("#commentId").val(id);
	$("#comment1").val(comment);
	$("#commentModal").modal();
	$("#updateCommentForm").submit(function(event){
		var comment = $("#comment1").val();
		var formData = new FormData($("#updateCommentForm"));
		formData.append('id',id);
		formData.append('comment',comment);
		formData.append('resourceCode',resourceCode);
		$.ajax({
			url:"../../../serviceRoom/update",
			type:'post',
			data: formData,
			processData: false,  // 告诉jQuery不要去处理发送的数据
			contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
			success:function(data){
				dtApi.ajax.reload();
			}
		});
	});
}

//查看视图
function checkView(id,imageUrl){
	console.log(getContentPath());
	if(imageUrl == null || imageUrl == "" || imageUrl == "undefined"){
		DCMSUtils.Modal.toast('暂无视图！','cancel');
	}else{
		DCMSUtils.Modal.alert("<img src='"+getContentPath()+imageUrl+"' />","查看视图","");
	}
}


//添加用户
function addUser(id,name){
	DCMSUtils.Modal.showLoading();
	$("#addUser-cpRoomName").text(name);

	$.when(DCMSUtils.Ajax.doPost('user/getAll'),DCMSUtils.Ajax.doPost('serviceRoom/getServiceRoomUserRels',{serviceRoomId:id}))
		.then(function(userData, userRelsData){
			if(userData.status=='1' &&  userRelsData.status=='1'){
				console.log(userRelsData);
				var relev_userId =[];
				for (j in userRelsData.data) {
					relev_userId.push(userRelsData.data[j].userId);
				}
				console.log(relev_userId);
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

	$("#addUserModalForm").submit(function(event){
		var chk_value =[];
		$('input[name="addUser-checkbox"]:checked').each(function(){
			chk_value.push($(this).val());
		});
		console.log(chk_value);
		DCMSUtils.Modal.showLoading();
		DCMSUtils.Ajax.doPost("serviceRoom/updateServiceRoomUserRel",{serviceRoomId:id,userIds:chk_value}).then(function(data){
			DCMSUtils.Modal.hideLoading();
			if(data.status=='1'){
				DCMSUtils.Modal.toast('保存用户成功','');
				dtApi.ajax.reload();
			}else{
				DCMSUtils.Modal.toast('保存用户异常','forbidden');
			}
		},function(error){
			DCMSUtils.Modal.hideLoading();
			DCMSUtils.Modal.toast('保存用户异常','forbidden');
		});

	});
}


