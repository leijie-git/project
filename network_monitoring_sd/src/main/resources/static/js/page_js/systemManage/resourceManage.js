var numberVal = "";
var nameVal = "";
var dataHeight = 570;
var columns = [
//	{
//        field: 'selectItem',
//        checkbox: true
//	},
    {
        title: '名称',
        field: 'name',
        align: 'center',
        valign: 'middle',
        sortable: true,
        width: '100px'
    },
    {
        title: '操作',
        field: 'operation',
        visible: false,
        align: 'center',
        valign: 'middle',
        width: '80px',
        formatter: function (row) {
        	var project = JSON.stringify(row);
            var str = "";
            str += '<button type="button" class="btn btn-new btn-xs edit" data-toggle="modal" data-target="#newResouce" onclick="editResource(\''+row.id+'\')"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            str += '<button type="button" class="btn btn-danger btn-xs del" onclick="deleteResource(\''+row.id+'\')"><i class="fa fa-trash"></i>&nbsp;删除</button>'
            str += '<button type="button" class="btn btn-primary btn-xs view" data-toggle="modal" data-target="#viewResouce" onclick="viewResource(\''+row.id+'\')"><i class="fa fa-eye"></i>&nbsp;查看</button>'
            return str;
        }
    }
];
function mainSearch() {
    var url = baseUrl + "/resource/resourceList?type=0";
    var tableId = "resourceTable";
    var table = new TreeTable(tableId, url, columns);
    var name = $("#searchContent").val();
    var data = {"name": name};
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandColumn(0);
    table.setExpandAll(false);
    table.setData(data);
    table.init();
    

}
$(function () {
    //主页面列表查询
    mainSearch();
    //搜索控制
    $(".jy_mainTile").hide();
    initDataSource();
});
//高级搜索
$(".seniorSearch").click(function () {
    var clickNo = $(".jy_mainTile").attr("clickNo");
    if (clickNo == 0) {
        $(".jy_mainTile").attr("clickNo", "1");
        $(this).html("普通搜索");
        $(".jy_mainTile").show();
    } else {
        $(".jy_mainTile").attr("clickNo", "0");
        $(this).html("高级搜索");
        $(".jy_mainTile").hide();
    }
});
//普通搜索
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    refreshTable();
});
$("#menuType").change(function () {
//    if ($(this).val() == 'exampleInputEmail2') {
//        $("#commonInput").html('<select name="searchContent" id="searchContent" class="form-control">' +
//            '<option value="">全部</option><option value="1">启用</option><option value="0">关闭</option></select>');
//    } else {
//        if(!$('#searchContent').hasClass('showInput')){
//            $("#commonInput").html('<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
//        }
//    }
    emptySearch();
});
$(".btnReset").click(function () {
    emptySearch();
    $("#searchContent").val("");
    refreshTable();
});
function refreshTable() {
    $(".mainList").empty().append('<table class="table table-bordered" id="resourceTable" data-click-to-select="true"></table>');
    mainSearch();
}
function emptySearch() {
    $("#exampleInputName2").val('');
    $("#exampleInputEmail2").val('');
}
//查询条件查询
$(".btnSearch").click(function () {
    refreshTable();
});
$(".btnNew").click(function () {
    $("#newResouce .btnSureContinue").show();
    $(".modal-title").html("新增资源");
    $("#preIdWrapSource > span").attr("disabled", false);
    //查询组织
    $("input[name=edit][type=hidden]").val("0");
    $("#name").val("");
    $("#url").val("");
    $("#seq").val("");
    $("#pid").val("");
    $(".modal-footer .btnAdd").show();
    $("input[name=indexId][type=hidden]").val("");
    $("#preIdWrapSource > span").html("");
});
//新增
$(".btnAdd").click(function () {

    var editId = $("input[name=edit][type=hidden]").val();
    var id = $("input[name=indexId][type=hidden]").val();
    var name = $("#name").val();
    var url = $("#url").val();
    var seq = $("#seq").val();
    var resourceType = $("#resourceType option:selected").val();
    var pid = $("#pid").val();

    
    if (name == "") {
        layer.msg("资源名称不能为空！");
        return;
    }
    var data;
    if (editId == 0) {
        data = {"name": name, "url": url, "resourceType": resourceType, "pid": pid, "seq": seq};
        url = baseUrl + "/resource/addResource?type=0";
    } else {
    	if (id == "") {
            layer.msg("编号不能为空！");
            return;
        }
        data = {
            "id": id,
            "name": name,
            "url": url,
            "resourceType": resourceType,
            "pid": pid,
            "seq": seq

        };
        url = baseUrl + "/resource/updateResource?type=0";
    }
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: data,
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                layer.msg("保存成功！")
                refreshTable();
                //刷新表格
                $("#newResouce").modal("hide");
            } else {
                layer.msg(d.msg||"保存失败")
            }
        },
        error: function (e) {

        }
    });
});

//编辑
function editResource(indexId) {
    $("#newResouce .btnSureContinue").hide();
    $(".modal-title").html("编辑资源");
    $("#number").attr("readonly", "readonly");
    $("#preIdWrapSource > span").attr("disabled", false);
    $(".modal-footer .btnAdd").show();
    $("input[name=indexId][type=hidden]").val(indexId);
    $("input[name=edit][type=hidden]").val("1");
    var url = baseUrl + "/resource/editResource?type=0";
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"id": indexId},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $("#name").val(d.obj.name);
                var pidName = d.obj.pidName;
                if (pidName != null) {
                    pidName = d.obj.pidName;
                } else {
                    pidName = "无";
                }
                if (d.obj.pid) {
                    $("#pid").val(d.obj.pid);
                } else {
                    $("#pid").val('');
                }
                $("#preIdWrapSource > span").html(pidName);
                $("#url").val(d.obj.url);
                $("#resourceType").val(d.obj.resourceType);
                $("#seq").val(d.obj.seq);
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function viewResource(indexId) {
    $(".modal-title").html("查看资源");
    var url = baseUrl + "/resource/editResource?type=0";
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"id": indexId},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                var resourceType = d.obj.resourceType;
                if (resourceType == 1) {
                    resourceType = "功能";
                } else if (resourceType == 0) {
                    resourceType = "父菜单";
                }
                $("#nameView").val(d.obj.name);
                $("#urlView").val(d.obj.url);
                $("#resourceTypeView").val(resourceType);
                var pidName = d.obj.pidName;
                if (pidName != null) {
                    pidName = d.obj.pidName;
                } else {
                    pidName = "无";
                }
                $("#preMenuWrapView").val(pidName);
                $("#seqView").val(d.obj.seq);

            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
//删除
function deleteResource(_this) {
    var indexId =_this;
    var delete_url = baseUrl + "/resource/deleteResource?type=0";
    layer.open({
        content: '是否确定删除？',
        btn: ["确认", "取消"],
        yes: function () {
            $.ajax({
                type: "get",
                async: true,
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {id: indexId},
                url: delete_url,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        $(_this).parents("tr").remove();
                        mainSearch();
                        layer.msg("删除成功！");
                    } else {
                        layer.msg(d.desc);
                    }
                },
                error: function (e) {

                }
            });
        },
        no: function () {

        }
    })
}
//资源管理（上级菜单）
function initDataSource() {
    initTreeDataSource();
    $("#preIdWrapSource").click(function (e) {
        e.stopPropagation();
        $("#preIdSource").show();
    });
    $(document).click(function () {
        $("#preIdSource").hide();
    });
    $("#preIdSource").click(function (e) {
        e.stopPropagation();
    })
}
//初始化资源
function initTreeDataSource() {
    $.ajax({
        url: baseUrl + "/resource/listResource",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            type:0
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                convert(data.obj);
            } else {
                layer.msg(data.msg);
            }
        }
    })
}
//将返回数据转换成数组
function convert(result) {
    var nodes = [];
    for (var i in result) {
        nodes.push(result[i]);
    }
    doInitSecondSource(nodes);
}
//给组织添加点击事件
function doInitSecondSource(nodes) {
    var setting = {
        view: {
            dblClickExpand: true//屏蔽掉双击事件
        },
        data: {
            key: {
                url: "",
                name: "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: 'pid',
                rootPId: null
            }
        },
        callback: {
            onClick: zTreeOnC
        }
    };
    $.fn.zTree.init($("#preIdSource"), setting, nodes);

    function zTreeOnC(event, treeId, treeNode) {
        var name = treeNode.name;
        $("#preIdWrapSource>span").html(name);
        $(this).unbind();
        $("#pid").val(treeNode.id);
        $("#pidName").val(treeNode.name);
        $("#preIdSource").hide();
    }
}
