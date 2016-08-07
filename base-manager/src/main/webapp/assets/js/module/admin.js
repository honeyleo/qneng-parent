$(document).ready(function () {

    LTS.colFormatter.stateLabel = function (v) {
        if (v) {
            return "正常";
        } else {
            return "禁用";
        }
    };
    LTS.colFormatter.optFormat = function (v, row) {
        return '<a href="javascript:void(0);" id="adminEdit" class="admin-info-icon" identity="'+ row.id +'" target="_blank" title="修改信息"><span class="label label-info"><i class="fa fa-info-circle"></i> 修改</span></a>';
    };
    var ltsTable = $("#ltstableContainer").ltsTable({
        url: '/api/admin/list',
        templateId: 'ltstable'
    });

    $(document).on("click", "#searchBtn", function () {
        var params = {};
        $.each($('#form').parent().find(".form-control"), function () {
            var name = $(this).attr("name");
            var value = $(this).val();
            if(name && value) {
            	params[name] = value;
            }
        });
        ltsTable.post(params, 1);
    });
    $("#searchBtn").trigger("click");
    $(document).on("click", "#addAdminBtn", function () {
    	$("#addAdmin-modal").modal("show");
    });
    $(document).on("click", "#adminEdit", function () {
    	$.ajax({
            url: '/api/admin/detail',
            type: 'POST',
            dataType: 'json',
            data: {id:1},
            success: function (json) {
                if (json.ret == 0) {
                	var html = template("admin-edit", {row: json.data});
                	$("#addAdmin-modal").html(html).modal("show");
                } else {
                	swal(json['msg']);
                }
            }

        });
    });
    $('#addAdmin-modal').on('hidden.bs.modal', function () {
    	$("#resetBtn").trigger("click");
    });
    $(document).on("click", "#addAdminSubmitBtn", function () {
    	var params = {};
    	$.each($('#addForm').parent().find(".form-control"), function () {
            var name = $(this).attr("name");
            var value = $(this).val();
            if(name && value) {
            	params[name] = value;
            }
        });
    	$.ajax({
            url: '/api/admin/add',
            type: 'POST',
            dataType: 'json',
            data: params,
            success: function (json) {
                if (json && json.success) {
                    swal('添加成功');
                    $("#resetBtn").trigger("click");
                    $("#addAdmin-modal").modal("hide");
                } else {
                    if (json) {
                        swal(json['msg']);
                    }
                }
            }

        });
    });
});