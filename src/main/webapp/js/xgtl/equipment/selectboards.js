function pageInt() {

// ---------- tab页 - start -------------

var myTabHtml = "";
if(image_width){
    myTabHtml += '<li class="active"><a href="#home">后视图</a></li>';
}
if(with_port){
    myTabHtml += '<li><a href="#dynamic">跳线信息</a></li> ';
}
if(fix_reticle_information){
    myTabHtml += '<li><a href="#fix">基础布线信息</a></li>';
}
for(patch in patch_list){
    myTabHtml += '<li><a href="#patch-'+patch.equipment_id+'">'+patch.name+'</a></li>';
}
if(with_port){
    myTabHtml += '<li><a href="#zhineng">其他设备</a></li>';
}
if(remark){
    myTabHtml += '<li><a href="#remark">设备信息</a></li>';
}
if(with_app){
    myTabHtml += '<li><a href="#app">应用</a></li>';
}
if(with_vm){
    myTabHtml += '<li><a href="#vms">虚拟机</a></li>';
}
if(is_vm){
    myTabHtml += '<li><a href="#vm">虚拟机</a></li>';
}
if(with_port){
    myTabHtml += '<li><a href="#topology">拓扑推演</a></li>';
}
$("#myTab").html(myTabHtml);

// ---------- tab页 - end -------------

// ---------- 跳线信息 - start -------------

if(image_width){
    var dynamicListHtml = '';
    for(patch in patch_list){
        dynamicListHtml += '<tr><td>A03-ODF1</td><td><div class="vr"></div></td>' +
            '<td>G-5/6</td><td><div class="vr"></div></td>' +
            '<td>A03-ODF1</td><td><div class="vr"></div></td>' +
            '<td>G-3/4</td><td><div class="vr"></div></td>' +
            '<td>0CM</td><td><div class="vr"></div></td>' +
            '<td><img style="cursor:pointer;" onclick="doCommand(\'showLineByPort\', {portId: 544794 });" src="http://localhost:8080/dcms/img/3D_location.png">' +
            '<img title="标签" onclick="showReticleDialog(544794);" src="http://localhost:8080/dcms/img/label.png">' +
            '<img style="cursor:pointer;" title="业务" onclick="createBusiness(544794);" src="http://localhost:8080/dcms/img/business.png">' +
            '<img style="cursor:pointer;" title="删除一跳" onclick="deleteReticle(\'A03-ODF1\',\'G-5/6\',565367, 0)" src="http://localhost:8080/dcms/img/e_delete.png">' +
            '<img style="cursor:pointer;" title="删除整个链路" onclick="deleteReticle(\'A03-ODF1\',\'G-5/6\',565367, 1)" src="http://localhost:8080/dcms/img/all_delete.png"></td><td><div class="vr"></div></td></tr>';
    }
    $("#dynamicList").html(dynamicListHtml);
}

// ---------- 跳线信息 - end -------------

// ---------- 基础布线信息 - start -------------

if(fix_reticle_information){
    var fixListHtml = '';
    for(reticle in fix_reticle_information){
        fixListHtml += '<td>{{reticle.equipmentname}}</td><td><div class="vr"></div></td>' +
            '<td>{{reticle.equipmentportname}}</td><td><div class="vr"></div></td>' +
            '<td>{{reticle.patchname}}</td><td><div class="vr"></div></td>' +
            '<td>{{reticle.patchportname}}</td>';
    }
    $("#fixList").html(fixListHtml);
}

// ---------- 基础布线信息 - end -------------

// ---------- patch_list - start -------------

for(patch in patch_list){
    var patchListHtml = '';
    patchListHtml += '<div class="tab-pane patch-list" id="patch-{{patch.equipment_id}}">'+
        '名称：{{patch.name}} <br/>';
    if(position_dict[patch.id]){
        patchListHtml += '<h3>{{position_dict[patch.id]["start"]}}-{{position_dict[patch.id]["end"]}}</h3>';
    }
    for(render in render_panel){
        if(render){
            patchListHtml += '<a data-port-id="101166" data-port-status="2" data-port-name="RJ-9" class="box port-in-use port" ' +
                'style="position:absolute;display:table-cell;top: 119px;left: 344px; width: 40px;height: 31px;" title="RJ-9"></a>';
        }else{
            patchListHtml += '<a data-port-id="101166" data-port-status="1" data-port-name="RJ-9" class="box port-idle port" ' +
                'style="position:absolute;display:table-cell;top: 119px;left: 344px; width: 40px;height: 31px;" title="RJ-9"></a>';
        }
    }
}

// ---------- patch_list - end -------------

// ---------- 其他设备 - start -------------

if(with_port){
    var otherSerialHtml = '';
    otherSerialHtml += '<input type="text" id="other-serial"><button id="other-serial-btn">加载</button><div id="custom-area"></div>';
}

// ---------- 其他设备 - end -------------

// ---------- 设备信息 - start -------------

if(remark){
    var remarkHtml = '';
    remarkHtml += '<pre>{{remark}}</pre>';
}

// ---------- 设备信息 - end -------------

// ---------- 应用 - start -------------

if(with_app){
    var appHtml = '<button onclick="doCommand(\'showApplicationInputDialog\', {equipment_id: {{equipment_id}} });">新增应用</button>';
    if(apps){
        appHtml += '<table align="center" class="b-table" style="margin-top:10px;width:75%;text-align:left;"><tr><th>名称</th> <th><div class="vr"></div></th><th>软件</th></tr>';
        for(app in apps){
            appHtml += '<tr><td>{{app.name}}</td> <td><div class="vr"></div></td><td>{{app.software}}</td></tr>';
        }
        appHtml += '</table>';
    }
}

// ---------- 应用 - end -------------

// ---------- 虚拟机 - start -------------

if(with_vm){
    var vmsHtml = '<button onclick="doCommand(\'showVirtualMachineInputDialog\', {equipment_id: {{equipment_id}} });">新增虚拟机</button>';
    if(virtual_machines){
        vmsHtml += '<table align="center" class="b-table" style="margin-top:10px;width:75%;text-align:left;"><tr><th>名称</th><th><div class="vr"></div></th>' +
            '<th>资源编码</th><th><div class="vr"></div></th><th>上级资源名称</th><th><div class="vr"></div></th><th>上级资源编码</th><th><div class="vr"></div></th>' +
            '<th>CPU</th><th><div class="vr"></div></th><th>IP</th><th><div class="vr"></div></th><th>内存(GB)</th><th><div class="vr"></div></th>' +
            '<th>磁盘(GB)</th><th> <div class="vr"></div></th><th>操作系统</th></tr>';
        for(vm in virtual_machines){
            vmsHtml += '<tr><td>{{vm.info.name}}</td><td><div class="vr"></div></td><td>{{vm.info.serial}}</td><td><div class="vr"></div></td>' +
                '<td>{{vm.e.name}}</td><td><div class="vr"></div></td><td>{{vm.e.serial}}</td><td><div class="vr"></div></td>' +
                '<td>{{vm.cpu or ""}}</td><td><div class="vr"></div></td><td>{{vm.ip or ""}}</td><td><div class="vr"></div></td>' +
                '<td>{{vm.memory or ""}}</td><td><div class="vr"></div></td><td>{{vm.disk or ""}}</td><td><div class="vr"></div></td><td>{{vm.os or ""}}</td></tr>';
        }
        vmsHtml += '</table>';
    }else {
        vmsHtml += '该设备还没有创建虚拟机。';
    }
}

// ---------- 虚拟机 - end -------------

// ---------- 虚拟机 - start -------------

if(is_vm){
    var vmHtml = '选择虚拟机对应的端口：';
    vmHtml += '<select onchange="updateVMPort(this)"><option value="0">未选择</option>';
    for(p in vm_ports){
        if(p.id== vm.port_id){
            vmHtml += '<option value="{{p.id}}" selected>{{p.name}}({{p.serial}})</option>';
        }else{
            vmHtml += '<option value="{{p.id}}">{{p.name}}({{p.serial}})</option>';
        }
    }
    vmHtml += '</select>';
}

// ---------- 虚拟机 - end -------------

// ---------- 拓扑推演 - start -------------

if(with_port){
    var topologyHtml = '<iframe style="margin:0px 0px 5px 0px; border: 0" height="700" width="800" src="saved_resource.html"></iframe>';
}

// ---------- 拓扑推演 - end -------------

}



(function (equipmentName, equipmentId, tabId, window, top, is_dc, $) {

    // 当前选中的端口
    var activePort = null;
    var activePatchPort = null;
    var activeBoardSerial = null;
    var activeBoardId = null;
    var activeBladePlace = null;

    var self = window;

    var inside = true;

    try {
        top.Ext.id();
    } catch (e) {
        inside = false;
    }

    function showAlert(title, content) {
        if (inside) {
            top.Ext.example.msg(title, content);
        } else {
            alert(content ? content: title);
        }
    }

    function showError(content) {
        if (inside) {
            top.MsgHelper.error(content);
        } else {
            alert(content);
        }
    }

    function showConfirm(message, success, error) {
        if (confirm(message)) {
            success();
        } else if (error) {
            error();
        }
    }

    function showPrompt(title, message, callback) {
        if (inside) {
            top.Ext.MessageBox.prompt(title, message, callback);
        } else {
            var value = prompt(message);
            if (value) {
                callback('yes', value);
            }
        }
    }

    window.addDC = function () { // <<<

        if (activePort != null && activePatchPort != null) {

            var portId = activePort.attr('data-port-id');
            var portName = activePort.attr('data-port-name');

            var patchPortId = activePatchPort.attr('data-port-id');
            var patchPortName = activePatchPort.attr('data-port-name');

            if (portId == patchPortId) {
                activePort.removeClass('active-port');
                activePatchPort.removeClass('active-port');
                activePort = null;
                activePatchPort = null;
                return;
            }

            var patchName = activePatchPort.attr('data-equipment-name') || equipmentName;

            var message = '您确定创建设备'
                + equipmentName + '的端口名称为 '
                + portName + " 和配线架 "
                + patchName + " 端口编号为 "
                + patchPortName + " 的跳线吗？";
            if (!confirm(message)) {

                activePort.removeClass('active-port');
                activePatchPort.removeClass('active-port');
                activePort = null;
                activePatchPort = null;

            } else {
                $.post(baseUrl + "/reticle/createReticle/",
                    {
                        'start_port_id': portId,
                        'end_port_id': patchPortId
                    },
                    function (ret) {
                        if (ret.code == 1) {
                            showAlert('成功', "创建设备 " + equipmentName + " 的端口编号为 "
                            + portName + " 和配线架 " + patchName
                            + " 端口编号为 " + patchPortName + " 的跳线成功");
                        } else {
                            showError(ret.message);
                        }
                        location.reload();

                    }, 'json');
            }
        }
    };
    // >>>

    function portClickCallback(_this) { // <<<
        if (activePort) {
            if (is_dc) {
                activePatchPort = activePort;
            } else {
                activePort.removeClass('active-port');
            }
        }

        activePort = $(_this);
        activePort.addClass('active-port')

        addDC();
        return false;
    } // >>>

    function patchPortClickCallback(_this) {// <<<

        if (activePatchPort) {
            activePatchPort.removeClass('active-port')
        }

        activePatchPort = $(_this);
        activePatchPort.addClass('active-port')

        addDC();
        return false;
    }// >>>

    window.deleteReticle = function (name, portName, reticleId, deleteAll) {// <<<
        var message = deleteAll ? "您是否需要删除与该端口相关的路径上所有关联端口的跳线信息？" : "您确定删除链接设备 " + name + " 上端口 " + portName + " 上的跳线吗？";
        showConfirm(message, function () {
            $.ajax({
                url: baseUrl + "/reticle/delreticle/",
                data: {
                    'delete_all': deleteAll,
                    'reticle_id': reticleId
                },
                dataType: 'json',
                type: 'post',
                success: function (ret) {
                    if (ret.code == 1) {
                        showAlert('成功', "删除链接设备 " + name + " 上端口 " + portName + " 上的跳线成功！");
                    } else {
                        showAlert('错误', ret.message);

                    }
                    location.reload();

                },
                error: function (ret) {
                    showAlert('错误', "发生错误，请稍后再试");
                    location.reload();
                }
            });
        });
    };
    // >>>

    window.deleteBoard = function (boardId, boardName) {// <<<
        var message = "您确定移除板卡" + boardName + "吗？";
        showConfirm(message, function () {
            $.post(baseUrl + "/board/del/", {'board_id': boardId}, function (ret) {
                if (ret.code === 1) {
                    showAlert('成功', "删除板卡" + boardName + "成功！");
                } else {
                    showError(ret.message);
                }
                location.reload();

            }, 'json');
        });
    }; // >>>

    window.deleteBlade = function (bladeId, bladeName) {// <<<
        var message = "您确定移除刀片" + bladeName + "吗？";
        showConfirm(message, function () {
            $.post(baseUrl + "/blade/del/", {'blade_id': bladeId}, function (ret) {
                if (ret.code == 1) {
                    showAlert('成功', "移除刀片" + bladeName + "成功！");
                } else {
                    showAlert('错误', ret.message);

                }
                location.reload();

            }, 'json');
        });
    }; // >>>

    window.showSelectArea = function (area, url, type, name) {// <<<

        area.show();

        $.ajax({
            url: url,
            data: {
                type: type,
                name: name
            },
            success: function (html) {
                area.html(html).show();
                document.body.scrollTop = 0;
            }
        });
    }; // >>>

    window.showBoard = function (serial, boardId, type) { // <<<
        activeBoardSerial = serial;
        activeBoardId = boardId;
        window.showSelectArea($('#select-area'), baseUrl + '/board/boardsbytype/', type);
    };
    // >>>

    window.showBlade = function (serial, bladeId, type) { // <<<
        activeBladePlace = serial;
        window.showSelectArea($('#select-area'), baseUrl + '/blade/bladesbytype/', type);
    };
    // >>>

    window.addBoard = function (boardId, boardName) {// <<<
        if (!activeBoardSerial) {
            showAlert('错误', "请先选择板卡");
            return;
        }

        var message = "确认为设备 `"
        + equipmentName + "` 的 第"
        + activeBoardSerial + "号槽位添加板卡 `"
        + boardName + "` ？";

        showConfirm(message, function () {
            $.post(baseUrl + "/board/create/", {
                  equipment_id: equipmentId,
                  serial: activeBoardSerial,
                  board_id: activeBoardId,
                  product_id: boardId
              }, function (ret) {
                  if (ret.code == 1) {
                      alert("插入成功");
                      location.reload();
                  } else {
                      showError(ret.message);
                  }
              }, 'json');
        });
        activeBoardSerial = null;
        $('#select-area').hide();
    };
    // >>>

    window.addBlade = function (bladeId, bladeName) {// <<<
        if (!activeBladePlace) {
            showError("请先选择刀片位置！");
            return;
        }

        var message = "确认为设备 `"
            + equipmentName + "` 的 第"
            + activeBladePlace + "号槽位添加刀片 `"
            + bladeName + "` ？";

        showConfirm(message, function() {
            $.post(baseUrl + "/blade/create/", {
                  equipment_id: equipmentId,
                  place: activeBladePlace,
                  product_id: bladeId
              },
              function (ret) {
                  if (ret.code == 1) {
                      alert("插入成功");
                      location.reload();
                  } else {
                      showError(ret.message);
                  }
              }, 'json');
        });
        activeBladePlace = null;
        $('#select-area').hide();
    };
    // >>>

    window.createBusiness = function (portId) {// <<<

        $.ajax({
            url: baseUrl + "/business/check_business/",
            type: "post",
            data: {
                port_id: portId
            },
            dataType: "json",
            success: function (ret) {
                if (ret.code != 1) {
                    showAlert('查看业务', ret.message);
                } else if (ret.message !== "ok") {
                    showAlert('查看业务', ret.message);
                } else {
                    showPrompt("创建业务", "请输入业务名称", function (btn, businessName) {
                        if (businessName) {
                            $.ajax({
                                url: baseUrl + "/business/createBusiness/",
                                type: "post",
                                data: {
                                    name: businessName,
                                    port_id: portId
                                },
                                dataType: "json",
                                success: function (ret) {
                                    if (ret.code == 1)
                                        showAlert("创建业务", ret.message);
                                    else
                                        showError(ret.message);

                                },
                                error: function () {
                                    showError("网络错误");
                                },
                                fail: function () {
                                    showError("网络错误");
                                }
                            });
                        }
                    });
                }
            },
            error: function () {
                showError("网络错误");
            },
            fail: function () {
                showError("网络错误");
            }
        });
    };
    // >>>

    window.showReticleDialog = function (id) {// <<<
        $.ajax({
            url: baseUrl + '/reticle/info/',
            data: {
                id: id
            },
            dataType: 'json',
            success: function (ret) {
                $('#reticle-label').text(ret.label);
                $('#reticle-label-input').val(ret.label);
                $('#show-reticle-dialog').html(ret.label).dialog('open');
            }
        });
    };
    // >>>

    $(document).ready(function () {

        $('#home').on('click', '.port', function () {

            if ($(this).attr("data-port-status") == '1') {
                portClickCallback(this);
            }

            if (tabId) {
                var portId = $(this).attr('data-port-id');
                doCommand('tabShowPort', {portId: portId, tabId: tabId});
            }

        }).on('dblclick', '.port', function () {
            if ($(this).attr("data-port-status") == '2') {
                var portId = $(this).attr('data-port-id');
                showReticleDialog(portId);
            }

        }).on('click', 'img.blade', function () {

            if (tabId) {
                var bladeId = $(this).parent().attr('blade-id');
                if (!bladeId) {
                    return;
                }
                doCommand('tabShowBlade', {bladeId: bladeId, tabId: tabId});
            }
        });


        $('.patch-list').on('click', '.port', function () {

            if ($(this).attr("data-port-status") == '1') {
                patchPortClickCallback(this);
            }

            if (tabId) {
                var portId = $(this).attr('data-port-id');
                doCommand('tabShowPort', {portId: portId, tabId: tabId});
            }
        });


        $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });

        $('#myTab a:first').tab('show');

        $('#other-serial-btn').click(function () {
            var serial = $('#other-serial').val();
            $.ajax({
                url: baseUrl + '/server/selectother/',
                data: {
                    serial: serial,
                    t: Math.random()
                },
                success: function (html) {
                    $('#custom-area').html(html);
                }
            });
        });

        $('#show-reticle-dialog').dialog({
            autoOpen: false,
            draggable: true,
            resizable: false,
            model: true,
            position: {my: "center", at: "center", of: self},
            width: 450,
            height: 500
        });

        $('#create-business-dialog').dialog({
            autoOpen: false,
            draggable: true,
            resizable: false,
            model: true,
            position: {my: "center", at: "center", of: self},
            width: 420,
            height: 220
        });

        doCommand('reset', {tabId: tabId});
    });

    window.updateVMPort = function ($this) {
        $this = $($this);
        var port_id = $this.val();
        $.ajax(
            {
                url: baseUrl + '/virtual_machine/update_port/',
                data: {
                    vm_id: equipmentId,
                    port_id: port_id
                },
                success: function (ret) {
                    showAlert('成功', ret.msg);
                }
            });
    };

    var origin = window.location.origin;

    window.addEventListener('message', function (event) {
        var command = event.command;
        var data = event.data;

        if (command === 'setOrigin') {
            origin = data['origin'];
        }

    });

    window.doCommand = function (command, params) {
        parent.postMessage({
            command: command,
            params: params
        }, origin);
    };

})(equipmentName, equipmentId, tabId, window, top, isDC, jQuery);

// vim600: ts=4 st=4 foldmethod=marker foldmarker=<<<,>>> syn=javascript
// vim600: commentstring=//\ %s
