/**
 * datatables 表格mtr组件处理
 */
var datatablesMtr = function(){
    /**
     * 内部方法
     */
    //计算表格的宽高
    var caculateHeight = function($obj,noFootbar){
        var $table = $($obj.DataTable().table().container());
        var $scrollBody = $table.find(".dataTables_scrollBody");
        var scrollBodyTop = $scrollBody.offset().top;
        var screenHeight = $(window).height();
        if(noFootbar){
            var screenBodyHeight = screenHeight - scrollBodyTop;
        }else{
            var screenBodyHeight = screenHeight - scrollBodyTop - 78;
            var $footbar = $table.find(".footbar");
            $footbar.width($scrollBody.width() + 85);
            $footbar.children("div:eq(1)").addClass("footbar-secondDiv");
        }

        $scrollBody.css("max-height", screenBodyHeight);
        if($('.editArea').length > 0){
            $('.editArea').css('height', screenHeight - 40 + 'px');
            $('.editScrollWapper').css('height', screenHeight - 100 + 'px');
        }
    }

    //非Mac机处理初始化时滚动条的问题(待优化)
    var noMacScorll = function($obj){
        var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
        if (!isMac){
            $('.dataTables_scrollHeadInner').find(".dataTable").css('width', $obj.width() + 'px');
        }
    }

    /**
     * 暴露接口
     */
    //规划表格大小，可以自适应
    var freeTableHeight = function($obj,noFootbar){
        caculateHeight($obj,noFootbar);
        noMacScorll($obj);
        //窗口大小变化时，重新初始化表格
        $(window).resize(function(){
            $obj.resizeWindow = true;
            caculateHeight($obj,noFootbar);
        });
        $obj.on('draw.dt', function() {
            $($obj.DataTable().table().container()).find(".dataTables_scrollBody").scrollTop(0);
        })
    };


    return {
        freeTableHeight : freeTableHeight
    }
}();

