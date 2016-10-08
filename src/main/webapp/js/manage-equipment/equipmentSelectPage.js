var equ_num = 1;
var equ_pageCount = 1;
var equType = '0';
var equName = "";
loadEquipmentBody();
function loadEquipmentBody(){
	var pageNum = 1;
	var pageSize = 10;
	var html_content = "";
	var content = "";
	//console.log("userLoad");
	var getEquData = {pageNum: pageNum,pageSize: pageSize,equName: equName,equType:equType};
	// $.ajax({
	// 	url:"fixed/equipment/datagrid",
	// 	dataType:"json",
	// 	data: getEquData,
	// 	type: "post"
	// })
	DCMSUtils.Ajax.doPost("fixed/equipment/getAll",getEquData).done((jsonData)=>{
		// var count = jsonData["data"]["count"];
		var equInfo = jsonData["data"];
	    // equ_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=equInfo.length;i<len;i++){
			e = equInfo[i];
			var type;
			switch(e["equType"]){
				case '0':
					type = "机柜";
					break;
				case '1':
					type = "空调";
					break;
				case '2':
					type = "ups";
					break;
				case '3':
					type = "配电柜";
					break;
				case '4':
					type = "开关柜";
					break;
			}
			content = "<tr>"+
				"<td>"+e["equName"]+"</td>"+
				"<td>"+type+"</td>"+
				"<td>"+e["equVendor"]+"</td>"+
				"<td>"+e["rsoPath"]+"</td>"+
				"<td>"+e["maxPath"]+"</td>"+
				"<td>"+
					"<i style='margin:3px;cursor:pointer' title='编辑' class='fa fa-pencil' role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentUpdate\" data-value=\""+e["id"]+"\" onclick=\"equipmentUpdateInit(this)\"></i>"+
					"<i style='margin:3px;cursor:pointer' title='删除' class='fa fa-trash' role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentDelete\" data-value=\""+e["id"]+"\" onclick=\"equipmentDeleteInit(this)\"></i>"+
				"</td>"+
			"</tr>";
			html_content += content;
			num++;
		}
		
		// var ulContent =" <ul class=\"pagination\" style=\"margin:0;padding:0\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
		// var pagination = function(){
		// 	for(var i=1;i<=equ_pageCount;i++){
		// 		ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
		// 	}
		// }
		// pagination();
		// ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
		var index1 = document.getElementById("equipmentBody");
		index1.innerHTML = html_content;
		// var index2 = document.getElementById("equipmentPagination");
		// index2.innerHTML = ulContent;
		$('.dataTables-example').DataTable({
            "bFilter":true,
            "aLengthMenu": [10, 25, 50],
            "oLanguage":{
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "oPaginate": {
                    "sFirst":    "第一页",
                    "sPrevious": " 上一页 ",
                    "sNext":     " 下一页 ",
                    "sLast":     " 最后一页 "
                },
                "sInfoFiltered":"(从一共 _MAX_ 条记录中查找)",
                "sZeroRecords":"未找到任何相匹配记录",
            }
            
        });
       
        var A = $('#DataTables_Table_0_info').parent().parent();
        $('#DataTables_Table_0_length').children().insertBefore(A);
        $('#DataTables_Table_0_filter').parent().css('display','none');
        
        var title = $('.dataTables-example thead th').eq(0).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="0" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        title = $('.dataTables-example thead th').eq(1).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="1" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        title = $('.dataTables-example thead th').eq(2).text();
        $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="3" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        // title = $('.dataTables-example thead th').eq(8).text();
        // $( '<input type="text" style="padding:6px 12px;margin-left:12px;float:right" data-id="8" placeholder="Search '+title+'" />' ).prependTo('#upTable');
        $('<a onclick="" href="javascript:void(0);" style="float:right;margin-left:12px;" class="btn btn-primary ">查询</a>').prependTo('#upTable');

        var table = $('.dataTables-example').DataTable();
        $('input[data-id=\'0\']').on('keyup change',function(){
            table
                .column( 0 )
                .search( this.value )
                .draw();
        });
        $('input[data-id=\'1\']').on('keyup change',function(){
            table
                .column( 1 )
                .search( this.value )
                .draw();
        });
        $('input[data-id=\'2\']').on('keyup change',function(){
            table
                .column( 2 )
                .search( this.value )
                .draw();
        });
        // $('input[data-id=\'8\']').on('keyup change',function(){
        //     table
        //         .column( 8 )
        //         .search( this.value )
        //         .draw();
        // });
	});
}
function equipmentSelectPage(){
		// var obj = document.getElementById("userSelectPage");
		var html_content="";
		var htm_final_ele="";
		var ulContent="";//分页
		var size = $("#equipmentSelectPage option:selected").val();
		console.log("size:"+size);
		var getEquData = {pageNum: 1, pageSize: size,equType: equType};
		$.ajax({
			// url: "menu/tree",
			url: "fixed/equipment/datagrid",
			dataType: "json",
			//data: "",
			data: getEquData,
			type: "post"
		}).done((jsonData)=>{
			var equInfo = jsonData["data"]["records"];
			equ_pageCount = jsonData["data"]["pageCount"];
			var num = 1;
			var e;
			for(var i=0,len=equInfo.length;i<len;i++){
				e = equInfo[i];
				var type;
				switch(e["equType"]){
					case '0':
						type = "机柜";
						break;
					case '1':
						type = "空调";
						break;
					case '2':
						type = "ups";
						break;
					case '3':
						type = "配电柜";
						break;
					case '4':
						type = "开关柜";
						break;
				}
				content = "<tr>"+
					"<td>"+e["equName"]+"</td>"+
					"<td>"+type+"</td>"+
					"<td>"+e["equVendor"]+"</td>"+
					"<td>"+e["rsoPath"]+"</td>"+
					"<td>"+e["maxPath"]+"</td>"+
					"<td>"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentUpdate\" data-value=\""+e["id"]+"\" onclick=\"equipmentUpdateInit(this)\">编辑</label>|"+
						"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentDelete\" data-value=\""+e["id"]+"\" onclick=\"equipmentDeleteInit(this)\">删除</label>"+
					"</td>"+
				"<tr>";
				html_content += content;
				num++;
			}
			// console.log(jsonData);
			// console.log("userGridLoad"+count);
			var ulContent =" <ul class=\"pagination\">"+"<li onclick=\"pageMinus()\"><a>&laquo;</a></li>";
			var pagination = function(){
				for(var i=1;i<=equ_pageCount;i++){
					ulContent += "<li data-value=\""+i+"\" onclick=\"selectPage(this)\"><a>"+i+"</a></li>";
				}
			}
			pagination();
			ulContent += "<li onclick=\"pagePlus()\"><a>&raquo;</a></li></ul>";
			var index1 = document.getElementById("equipmentBody");
			index1.innerHTML = html_content;
			var index2 = document.getElementById("equipmentPagination");
			index2.innerHTML = ulContent;
		}).fail((err)=>{

		});
}

function tablePerfom(equ_num,size){
	var getEquData = {pageNum: equ_num, pageSize: size,equType:equType};
	var html_content="";
	$.ajax({
		// url: "menu/tree",
		url: "fixed/equipment/datagrid",
		dataType: "json",
		//data: "",
		data: getEquData,
		type: "post"
	}).done((jsonData)=>{
		var equInfo = jsonData["data"]["records"];
		equ_pageCount = jsonData["data"]["pageCount"];
		var num = 1;
		var e;
		for(var i=0,len=equInfo.length;i<len;i++){
			e = equInfo[i];
			var type;
			switch(e["equType"]){
				case '0':
					type = "机柜";
					break;
				case '1':
					type = "空调";
					break;
				case '2':
					type = "ups";
					break;
				case '3':
					type = "配电柜";
					break;
				case '4':
					type = "开关柜";
					break;
			}
			content = "<tr>"+
				"<td>"+e["equName"]+"</td>"+
				"<td>"+type+"</td>"+
				"<td>"+e["equVendor"]+"</td>"+
				"<td>"+e["rsoPath"]+"</td>"+
				"<td>"+e["maxPath"]+"</td>"+
				"<td>"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentUpdate\" data-value=\""+e["id"]+"\" onclick=\"equipmentUpdateInit(this)\">编辑</label>|"+
					"<label role=\"presentation\" data-toggle=\"modal\" data-target=\"#equipmentDelete\" data-value=\""+e["id"]+"\" onclick=\"equipmentDeleteInit(this)\">删除</label>"+
				"</td>"+
			"<tr>";
			html_content += content;
			num++;
		}
		var index = document.getElementById("equipmentBody");
		index.innerHTML = html_content;
	}).fail((err)=>{

	});
}

function equipmentSelectType(e){
	equType = $("#equipmentSelectType option:selected").val();
	var size = $("#equipmentSelectPage option:selected").val();
	console.log("type:"+equType);
	equ_num = 1;
	loadEquipmentBody();
}
function selectPage(e){
	var size = $("#equipmentSelectPage option:selected").val();
	console.log("size:"+size);
	equ_num = e.getAttribute("data-value");
	tablePerfom(equ_num,size);
}

function pagePlus(){
	var size = $("#equipmentSelectPage option:selected").val();
	console.log(equ_pageCount);
	if(equ_num < equ_pageCount){
		equ_num++;
	}else{
		equ_num = 1;
	}
	tablePerfom(equ_num,size,equType);
}

function pageMinus(){
	var size = $("#equipmentSelectPage option:selected").val();
	if(equ_num > 1){
		equ_num--;
	}else{
		equ_num = equ_pageCount;
	}
	tablePerfom(equ_num,size,equType);
}