equipmentAdd();
// equipmentUpdate();
// equipmentDelete();

// equipmentGet();
// equipmentDataGrid();
// equipmentGetAll();
function equipmentAdd(){
	var equName = "test_001";
	var equType = "jg";
	var equVender = "test_001";
	var rsoPath = "test_001";
	var maxPath = "test_001";
	var equipmentData = {equName:equName,equType:equType,equVender:equVender,rsoPath:rsoPath,maxPath:maxPath};
	$.ajax({
		url:"fixed/equipment/add",
		dataType:"json",
		data:equipmentData,
		type:"post",
		success: function(res){
			if(res.status == "1"){
				console.log("添加机柜"+res.data.equName+"成功"+res.data.id);
			}else{
				console.log("添加机柜失败"+res.msg);
			}
		}
	});
}

function equipmentUpdate(){
	var id = "6e5677ff8c5f4743b5cd7c2eff887571";
	var equName = "test_01_update";
	var equType = "test_01_update";
	var equVender = "test_01_update";
	var rsoPath = "test_01_update";
	var maxPath = "test_01_update";
	var equipmentData = {id:id,equName:equName,equType:equType,equVender:equVender,rsoPath:rsoPath,maxPath:maxPath};
	$.ajax({
		url:"fixed/equipment/update",
		dataType:"json",
		data:equipmentData,
		type:"post",
		success: function(res){
			if(res.status == "1"){
				console.log("修改机柜"+res.data.equName+"成功");
			}else{
				console.log("修改机柜失败"+res.msg);
			}
		}
	});
}

function equipmentDelete(){
	var id = "";
	var equipmentData = {id: id};
	$.ajax({
		url: "fixed/equipment/delete",
		dataType: "json",
		data: equipmentData,
		type:"post",
		success: function(res){
			if(res.status == "1"){
				console.log("修改机柜成功");
			}else{
				console.log("修改机柜失败"+res.msg);
			}
		}
	});
}

function equipmentGet(){
	var id = "";
	var equipmentData = {id: id};
	$.ajax({
		url: "fixed/equipment/get",
		dataType: "json",
		data: equipmentData,
		type:"post",
		success: function(res){
			if(res.status == "1"){
				console.log("查询机柜成功");
				console.log(res.data);
			}else{
				console.log("修改机柜失败"+res.msg);
			}
		}
	});
} 

function equipmentDataGrid(){
	var pageNum = 1;
	var pageSize = 10;
	var equType = "";
	var equName = "";
	var equipmentData = {pageNum:pageNum,pageSize:pageSize,equType:equType,equName:equName};
	$.ajax({
		url: "fixed/equipment/datagrid",
		dataType: "json",
		data: equipmentData,
		type:"post",
		success: function(res){
			if(res.status == "1"){
				console.log("分页查询机柜成功");
				console.log(res.data);
			}else{
				console.log("分页查询机柜失败"+res.msg);
			}
		}
	});
} 

function equipmentGetAll(){
	$.ajax({
		url: "fixed/equipment/getAll",
		dataType: "json",
		data: '',
		type: 'post',
		success: function(res){
			if(res.status == "1"){
				console.log("查询所有机柜成功");
				console.log(res.data);
			}else{
				console.log("查询所有机柜失败");
				console.log(res.msg);
			}
		}
	});
}
