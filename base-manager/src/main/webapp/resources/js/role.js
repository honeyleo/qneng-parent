var roles = {
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
        if (result.ret != 0) {
            return;
        }
        var value = result.data;
        for (var i = 0; i < value.length; i++) {
            var createTime = new Date(value[i].createTime);
            value[i].createTime = createTime.format("yyyy-MM-dd hh:mm:ss");

            roles.sunNum = (i+1)+(currentPage*PAGE_SIZE);
            $opera = '<a href="javascript:void(0);" class="operation J_delete" data-toggle="modal" data-target="#myModal" data-value=' + value[i].id + '>删除</a>' + 
                '<a href="#" class="operation dialog-editor" data-toggle="modal" data-target="#editorDialog"  data-value=' + value[i].id + '>编辑</a>' + 
                '<a href="#" class="operation dialog-privilege" data-toggle="modal" data-target="#privilegeDialog"  data-value=' + value[i].id + '>分配权限</a>' +
                '</td>';
            arr.push([roles.sunNum,value[i].id,value[i].name,value[i].desc, value[i].state,value[i].createTime, $opera]);
        }
        self.num++;
        result.draw = self.num;
        result.recordsTotal = result.total;
        result.recordsFiltered = result.total;
        result.data = arr;
    },
    query: function (refresh) {
        var action = "/manager/role/api/list", argument;
            argument = [
                
            ];
        this.load(action, argument, refresh);
    },
    bindEvents: function () {
        var self = this;
        //新建
        $(".add_dialog").click(function () {
        	var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_add_sure").removeClass("none");
            self.clearData();
        	self.addSure();
        });
        $('#table').delegate(".J_delete","click",function(){
        	//删除
            $('.modal-body').empty().html("确定要删除吗？");
            $('#myModalLabel').text("删除用户");
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_delete_sure").removeClass("none");
            $('.modal-dialog .modal-body').css({'overflow':"auto",'height':''});
            $('.modal-dialog .modal-content').css({'width':'auto'});
            var id = $(this).attr("data-value");
            self.deleteSure(id);
        });
        // editor
        $("#table").on("click", ".dialog-editor", function(){
            $('#myModalLabel').text("编辑用户");
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_sure").removeClass("none");
            $("#updContent").show();
            var id = $(this).attr("data-value");
            $.getJSON("/manager/role/detail", {id: id}, function(result){
            	if (result.ret == 0) {
                    if (result.data.state == 1) {
                        $('#search_dropDown-status1').attr("value", '1').text("有效");
                    } else {
                        $('#search_dropDown-status1').attr("value",'0').text("禁止");
                    }
                    $("#id").val(result.data.id);
                    $("#name").val(result.data.name);
                    $("#desc").val(result.data.desc);
            	}
            });
        });
        // 分配权限
        $("#table").on("click", ".dialog-privilege", function(){
            var sure = $('.modal-footer .btn-primary');
            sure.addClass("none");
            $(".J_privilege_sure").removeClass("none");
            var id = $(this).attr("data-value");
            $(".J_privilege_sure").unbind().click(function () {
            	var treeObj=$.fn.zTree.getZTreeObj("privilegeTree");
                var nodes=treeObj.getCheckedNodes(true);
                
                var menuIds = [];
                for(var i = 0; i < nodes.length; i++) {
                	tmpNode = nodes[i];
					if(i!=nodes.length-1){
						menuIds += tmpNode.id+",";
					}else{
						menuIds += tmpNode.id;
					}
                }
                $.get("/manager/role_menu/saveMenu",{roleId:id, menuIds : menuIds},function(result){
                    if (result.ret == 0) {
                        $('#privilege').modal('hide');
                        roles.query();
                    } else {
                        asyncbox.alert("分配权限失败！\n"+result.msg,"提示");
                        $('#privilege').modal('hide');
                    }
                });
            });
            $.getJSON("/manager/role_menu/privileges", {id: id}, function(result){
            	if (result.ret == 0) {
            		var setting = {
        	            view: {
        	                selectedMulti: false
        	            },
        	            check: {
        	                enable: true
        	            },
        	            data: {
        	                simpleData: {
        	                    enable: true,
        	                    idKey: "id",
        	    				pIdKey: "parentId",
        	                }
        	            },
        	            callback: {
        	            	
        	            }
        	        };
            		var zNodes = result.data;
        			$.fn.zTree.init($("#privilegeTree"), setting, zNodes);
        			$('#privilege').modal('show');
            	}
            });
        });
        $("#updContent").click(function(){
            var param = {
                id: $("#id").val(),
                name: $("#name").val(),
                desc: $("#desc").val(),
                state:$("#search_dropDown-status1").attr("value")
            };
            $.post("/manager/role/update", param, function(result){
                if ( result.ret == 0 ) {
                    self.query();
                    $(".btn-default").trigger("click");
                } else {
                    asyncbox.alert(result.msg,"提示");
                }
            });

        });
        $("#editorDialog .close,#editorDialog .J_close_sure").click(function(){
            //location.reload();
        });
    },
    addSure: function () {
        var self = this;
        $(".J_add_sure").unbind('click').click(function () {
        	self.confirmSubmit();
        });
    },
    confirmSubmit:function(){
    	var self = this;
    	var param = {
                name: $("#name").val(),
                desc: $("#desc").val(),
                state:$("#search_dropDown-status1").attr("value")
            };
            $.post("/manager/role/add", param, function(result){
                if ( result.ret == 0 ) {
                    self.query();
                    $(".btn-default").trigger("click");
                } else {
                    asyncbox.alert(result.msg,"提示");
                }
            });
    },
    deleteSure:function(id){
        var self =this;
        $(".J_delete_sure").unbind('click');
        $(".J_delete_sure").click(function () {
            $.get("/manager/role/del",{"id":id},function(result){
                if (result.ret == 0) {
                    $('#myModal').modal('hide');
                    self.query();
                } else {
                    asyncbox.alert("删除失败！\n"+result.msg,"提示");
                    $('#myModal').modal('hide');
                }
            });
        });
        $('#myModal').modal('show');
    },
    clearData:function(){
    	$("#id").val("");
        $("#name").val("");
        $("#desc").val("");
        $('#search_dropDown-status1').attr("value", '1').text("有效");
    },
};
$(function () {
    roles.init();
});


