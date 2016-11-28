/**
 * Created by geng on 2016/11/13.
 */
var cabinet_id = GetQueryString("cabinet_id");
var direction = GetQueryString("direction");

DCMSBusi.Api.invoke("cabinet/getPositionByResourceCode",{resourceCode:'A'}).then(function (data) {
    if(data.status=='1'){
        console.log(data);
    }
});

function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

//弹出未上架设备查询模态框
function noShelfEquModal() {
    document.getElementById("noShelfEquForm").reset();
    $("#noShelfEquModal").modal();
}

//未上架设备查询
function searchNoShelfEqu() {
    var equipmentCode = $("#equ-code").val();
    DCMSBusi.Api.invoke("cabinet/getCabinetResultById",{id:equipmentCode}).then(function (data) {
        if(data.status=='1'){
            var storeHtml = '';
            var noShelfEqu = data.data;
            for (i in noShelfEqu) {
                storeHtml += '<tr><td class="has ui-draggable" _sid="165220" style="border: 0px; position: relative;"><h6>A06接入交换机-3</h6>'+
                    '<img class="img-polaroid" onerror="this.src=\'/images/__utm.gif\';" style="width: 150px;" src="/product/image/?front=1&amp;id=100050" title="A06接入交换机-3">'+
                    '</td></tr>';
            }
            $("#store").html(storeHtml);
            $("#store-container").show();
            $('#store').find( ".has" ).draggable({
                cancel: "a.ui-icon", // clicking an icon won't initiate dragging
                revert: "invalid", // when not dropped, the item will revert back to its initial position
                containment: $( "#demo-frame" ).length ? "#demo-frame" : "document", // stick to demo-frame if present
                //helper: 'clone',
                cursor: "move",
                cursorAt: {left:5, top:5}
            });
        }
    });
}

function selectBoardDialog(equId,equName) {
    var el = document.createElement("a");
    document.body.appendChild(el);
    el.href = 'selectboards.html'; //url 是你得到的连接
    el.click();
    document.body.removeChild(el);
}

(function() {
    $( ".has" ).draggable({
        cancel: "a.ui-icon", // clicking an icon won't initiate dragging
        revert: "invalid", // when not dropped, the item will revert back to its initial position
        containment: $( "#demo-frame" ).length ? "#demo-frame" : "document", // stick to demo-frame if present
        //helper: 'clone',
        cursor: "move",
        cursorAt: {left:5, top:5}
    });
    $('.empty').droppable({
        accept: '.has',
        tolerance: 'pointer',
        activeClass: "ui-state-highlight",
        drop: function( event, ui ) {
            try {
                var $this = $(this);
                var pos = parseInt($this.attr('_cid'));
                var eid = parseInt(ui.draggable.attr('_eid'));
                var sid = parseInt(ui.draggable.attr('_sid'));
                if (eid) {
                    $.post('/cabinet/change_pos/', {
                            'cid': cabinet_id, 'pos': pos, 'eid': eid,
                            'direction': direction
                        },
                        function(ret) {
                            if(ret.code == 1) {
                            } else {
                                alert(ret.message);
                            }
                            location.reload();
                        }, 'json');
                } else if (sid) {
                    $.post('/cabinet/change_pos/',
                        {
                            'cid': cabinet_id, 'pos':pos, 'eid':sid,
                            'direction': direction
                        }, function(ret) {
                            if(ret.code != 1) {
                                alert(ret.message);
                            }
                            location.reload();
                        }, 'json');
                }

            }catch(e) {
                location.reload();
            }

        }
    });

    window.search = function(offset) {
        $.ajax({
            url: '/equipment/search/',
            data: {
                text: $('#search-text').val(),
                offset: offset
            },
            success: function(html) {
                $('#store').html(html);
                $('#store').find( ".has" ).draggable({
                    cancel: "a.ui-icon", // clicking an icon won't initiate dragging
                    revert: "invalid", // when not dropped, the item will revert back to its initial position
                    containment: $( "#demo-frame" ).length ? "#demo-frame" : "document", // stick to demo-frame if present
                    //helper: 'clone',
                    cursor: "move",
                    cursorAt: {left:5, top:5}
                });
            }
        });
    }

})();


(function($) {
    $(document).ready(function() {
        var equipment_name = top.$.trim(top.$('#space-unconfirm-e-name').val());
        if(equipment_name) {
            $('#search-text').val(equipment_name);
            $('#store-container').show();
            search(0);
        }
        /*var hash = self.location.hash;
        top.reset(hash);*/

        var cabinetView = '';
        if(!u_order){
            var last_pos = 0;
            for(var i=0;i < height;i++){
                var label = i+1;
                cabinetView += '<tr><td _cid="'+label+'" class="cabinet-label empty">'+label+'</td>';
                if( i in reversed_levels){
                    last_pos = i + reversed_levels[i]['height'];
                    var equipmentid = reversed_levels[i]['id'];
                    var equipmentname = reversed_levels[i]['name'];
                    var productid = reversed_levels[i]['product_id'];
                    cabinetView += '<td class="has" _eid="'+equipmentid+'" rowspan="'+last_pos+'"><img title="'+equipmentname+'" '+
                        'onerror="//this.src=\'/images/__utm.gif\';" ondblclick="new top.SelectBoardDialog('+equipmentid+','+equipmentname+').show()"'+
                        'class="has" _eid="'+equipmentid+'" src="{{createUrl(\'product/image\', id='+productid+', front=1)}}"'+
                        'alt="'+equipmentname+'" style="width:600px; max-height: '+last_pos+' * 52}}px" /></td>';
                }else{
                    if(i >= last_pos){
                        cabinetView += '<td _cid="'+label+'" class="empty">空</td>';
                    }
                }
                cabinetView += '<td class="cabinet-label">'+label+'</td></tr>';
            }
        }else {
            var last_pos = height;
            for(var i=height-1 ;i > -1;i--){
                var label = i+1;
                cabinetView += '<tr><td _cid="'+label+'" class="cabinet-label empty">'+label+'</td>';
                if( i in reversed_levels){
                    last_pos = i - reversed_levels[i]['height'];
                    var equipmentid = reversed_levels[i]['id'];
                    var equipmentname = reversed_levels[i]['name'];
                    var productid = reversed_levels[i]['product_id'];
                    cabinetView += '<td class="has" _eid="'+equipmentid+'" rowspan="'+last_pos+'"><img title="'+equipmentname+'" '+
                        'onerror="//this.src=\'/images/__utm.gif\';" ondblclick="selectBoardDialog('+equipmentid+','+equipmentname+')"'+
                        'class="has" _eid="'+equipmentid+'" src="{{createUrl(\'product/image\', id='+productid+', front=1)}}"'+
                        'alt="'+equipmentname+'" style="width:600px; max-height: '+last_pos+' * 52}}px" /></td>';
                }else{
                    if(i <= last_pos){
                        cabinetView += '<td _cid="'+label+'" class="empty">空</td>';
                    }
                }
                cabinetView += '<td class="cabinet-label">'+label+'</td></tr>';
            }
        }
        $("#cabinetView").html(cabinetView);
    });
    setTimeout(function(){
        new IScroll('#main',{
            zoom: true,
            zoomMin: 0.5,
            zoomMax: 1,
            scrollX: false,
            scrollY: true,
            mouseWheel: true,
            freeScroll: true,
            wheelAction: 'zoom'
        })
    }, 100);
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

})(jQuery);