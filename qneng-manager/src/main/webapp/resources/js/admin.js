var admins = {
    sunNum : 0,
    editor: null,
    pickerLoaded: true,
    sourceArray: [],
    textCon:null,
    init: function () {
        this.bindEvents();
        this.loadTable();
    },
    //加载表格数据
    loadTable: function () {
        var self = this;
        self.query(true);
    },
    load: function (action, argument, refresh) {
        var self = this;
        oTable.dataTable(action, argument, self.insertResult, {refresh: refresh, pageSize: PAGE_SIZE});
    },
    insertResult: function (result,currentPage) {
        var arr = [], $opera;
        if (result.code != 200) {
            return;
        }
        var value = result.value;
        for (var i = 0; i < value.length; i++) {
            var createTime = new Date(value[i].createTime);
            value[i].createTime = createTime.format("yyyy-MM-dd hh:mm:ss");

            admins.sunNum = (i+1)+(currentPage*PAGE_SIZE);
            $opera = '<a href="javascript:void(0);" class="operation J_delete" data-toggle="modal" data-target="#myModal" data-value=' + value[i].id + '>删除</a>' + 
            	'<a href="javascript:void(0);" class="operation J_strategyInfo" data-toggle="modal" data-target="#myModal" data-value=' + value[i].id + '>详情</a>'+
                '<a href="#" class="operation dialog-editor" data-toggle="modal" data-target="#editorDialog"  data-value=' + value[i].id + '>编辑</a></td>';
            arr.push([admins.sunNum,value[i].id,value[i].username,value[i].realName, '<div class="text_l">'+ value[i].phone +'</div>', value[i].state,value[i].createTime,value[i].roleId, $opera]);
        }
        self.num++;
        result.draw = self.num;
        result.recordsTotal = result.total;
        result.recordsFiltered = result.total;
        result.data = arr;
    },
    query: function (refresh) {
        var action = "/manager/admin/api/list", argument;
            argument = [
                
            ];
        this.load(action, argument, refresh);
    },
    bindEvents: function () {
        var self = this;
        $('.J_search').click(function () {
            self.query(true);
        });
        $('#table').delegate(".J_status", "click", function () {
            //改变状态
            var $this = $(this),
                status = $this.attr("data-status"),
                id = $this.attr("data-id");
            $.get("/console/notice/modify_status", {"status": status, "id": id}, function (result) {
                if (result.code == 200) {
                    if (status == 1) {
                        $this.attr("data-status", 0);
                        $this.text("下线");
                    } else {
                        $this.attr("data-status", 1);
                        $this.text("上线");
                    }
                    self.query();
                }
            });
        }).delegate(".J_strategyInfo","click",function(){
            //详情
            $('.modal-body').html($("#infoDialog").tmpl());
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_detail").removeClass("none");
            $('#myModalLabel').text("详情");
            $('.modal-dialog .modal-body').css({'overflow':"auto",'height':''});
            $('.modal-dialog .modal-content').css({'width':'800px'});
            var id = $(this).attr("data-value");
            $.get("/console/notice/detail",{"id":id}, function (result) {
                if (result.code == 200) {
                    self.insertInfo(result);
                }
            });
        }).delegate(".J_delete","click",function(){
            //删除
            $('.modal-body').empty().html("确定要删除吗？");
            $('#myModalLabel').text("删除");
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_delete_sure").removeClass("none");
            $('.modal-dialog .modal-body').css({'overflow':"auto",'height':''});
            $('.modal-dialog .modal-content').css({'width':'auto'});
            var id = $(this).attr("data-value");
        });
        // editor
        $("#table").on("click", ".dialog-editor", function(){
            $('#myModalLabel').text("编辑公告");
            notices.pickerLoaded = false;
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_sure").removeClass("none");
            notices.gameNameFunc();
            $("#updContent").show();
            var appId = $(this).attr("data-value");
            $("#contentAppId").val(appId);
            $.getJSON("/console/notice/detail", {id: appId}, function(data){
                
            });
        });
        $("#updContent").click(function(){
            var param = {
                id: $("#strategyId").val(),
                appId: $("#appId2").val(),
                title: $("#strategyTitle").val(),
                url: $("#noticeLink").val(),
                content: self.editor.html(),
                status:$("#search_dropDown-status1").attr("value"),
                startTime:$("#startTime1").val(),
                endTime:$("#endTime1").val(),
                portraitImg:portraitImg,
                landscapeImg:landscapeImg
            };
            $.post("/console/notice/modify", param, function(data){
                if ( data.code == 200 ) {
                    self.query();
                    $(".btn-default").trigger("click");
                } else {
                    asyncbox.alert(data.message,"提示");
                }
            });

        });
        //新建
        $(".add_dialog").click(function () {
        	self.addSure();
        });

        $("#editorDialog .close,#editorDialog .J_close_sure").click(function(){
            //location.reload();
        });
    },
    insertInfo: function (result) {
        
    },
    addSure: function () {
    	$("input[type='radio'][name='inlineRadioOptions'][value='0']").attr('checked', 'checked');// 设置
        var self = this;
        $(".J_add_sure").unbind('click').click(function () {
        	if($('#appId2').val() == '0') {
        		asyncbox.confirm("确认新增全局公告吗？","全局公告确认",function(result) {
            		if(!(result == 'cancel' || result == '取消')) {
            			self.confirmSubmit();
                	}
            	});
        	} else {
        		self.confirmSubmit();
        	}
        });
    },
    confirmSubmit:function(){
    	var self = this;
    	var param = {
                appId: $("#appId2").val(),
                url: $("#noticeLink").val(),
                title: $("#strategyTitle").val(),
                content: self.editor.html(),
                status:$("#search_dropDown-status1").attr("value"),
                startTime:$("#startTime1").val(),
                endTime:$("#endTime1").val(),
                portraitImg:$(".vertical .modify_icon .icon_img").attr("src"),
                landscapeImg:$(".landscape .modify_icon .icon_img1").attr("src")
            };
            $.post("/console/notice/create", param, function(data){
                if ( data.code == 200 ) {
                    self.query();
                    $(".btn-default").trigger("click");
                } else {
                    asyncbox.alert(data.message,"提示");
                }
            });
    },
    deleteSure:function(verId){
        var self =this;
        $(".J_delete_sure").unbind('click');
        $(".J_delete_sure").click(function () {
            $.get("/console/notice/delete",{"id":verId},function(result){
                if (result.code == 200) {
                    $('#myModal').modal('hide');
                    self.query(true);
                } else {
                    asyncbox.alert("删除失败！\n"+result.message,"提示");
                    $('#myModal').modal('hide');
                }
            });
        });
    },
    dropDown: function (id, text, inp) {
        $('.' + id).delegate('li a', 'click', function () {
            $("#" + text).text($(this).text());
            var value = $(this).attr('value');
            $("input[name =" + inp + "]").attr("value", value);
            $("#" + text).attr("value", value);
        });
    },
    clearData:function(){
        
    },
};
$(function () {
    admins.init();
});


