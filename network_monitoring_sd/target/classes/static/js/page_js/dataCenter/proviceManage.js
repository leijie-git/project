var numberVal = "";
var nameVal = "";
var dataHeight = 570;
var type = "";
var columns = [
//	{
//        field: 'selectItem',
//        checkbox: true
//	},
    {
        title: '城市名称',
        field: 'regionName'
    },
    {
        title: '城市编号',
        field: 'regionCode'
    },
    {
        title: '城市类型',
        field: 'proviceType',
        sortable: true,
    	formatter: function (row) {
    		var value = row.proviceType;
			if (value == '1') {
				return '省';
			} else if(value == '2'){
				return '市';
			} else if(value == '3'){
				return '区';
			} 
        }
    },
    {
        title: '操作',
        field: 'operation',
        visible: false,
        align: 'center',
        valign: 'middle',
        width: '280px',
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
    var url = baseUrl + "/provice/proviceList";
    var tableId = "resourceTable";
    var table = new TreeTable(tableId, url, columns);
    var name = $("#cityName").val();
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
});
//普通搜索
$(".ordinarySearch").click(function () {
	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    refreshTable();
});
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
        dataHeight = 658;
    }
    emptySearchMsg();
});
$(".btnReset").click(function () {
    emptySearch();
    $("#cityName").val("");
    refreshTable();
});
function refreshTable() {
    $(".mainList").empty().append('<table class="table table-bordered" id="resourceTable" data-click-to-select="true"></table>');
    mainSearch();
}
function emptySearch() {
    $("#cityName").val('');
    $("#searchContent").val('');
}
//查询条件查询
$(".btnSearch").click(function () {
    refreshTable();
});
$(".btnNew").click(function () {
    $("#newResouce .btnSureContinue").show();
    $(".modal-title").html("新增城市");
    $("#preIdWrapSource > span").attr("disabled", false);
    //查询组织
    initDataSource(1);
    $("input[name=edit][type=hidden]").val("0");
    $("#name").val("");
    $("#type").val("");
    $("#code").val("");
    $("#pid").val("");
    $(".modal-footer .btnAdd").show();
    $("input[name=indexId][type=hidden]").val("");
    $("#preIdWrapSource > span").html("");
});
//新增
$(".btnAdd").click(function () {

    var editId = $("input[name=edit][type=hidden]").val();
    var id = $("#indexId").val();
    var name = $("#name").val();
    var code = $("#code").val();
    var type = $("#type option:selected").val();
    var pid = $("#pid").val();
    if (name == "") {
        layer.msg("城市名称不能为空！");
        return;
    }
    var data;
    if (editId == 0) {
        data = {"regionName": name, "regionCode": code, "type": type, "pid": pid};
        url = baseUrl + "/provice/add";
    } else {
    	if (id == "") {
            layer.msg("编号不能为空！");
            return;
        }
        data = {
        		"regionName": name, 
        		"regionCode": code, 
        		"type": type, 
        		"pid": pid,
        		"id" : id
        };
        url = baseUrl + "/provice/update";
    }
    $.ajax({
        type: "post",
        async: true,
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
                layer.msg(d.desc)
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
    $("#preIdWrapSource > span").attr("disabled", false);
    $(".modal-footer .btnAdd").show();
    initDataSource(1);
    $("#indexId").val(indexId);
    $("#edit").val("1");
    var url = baseUrl + "/provice/edit";
    $.ajax({
        type: "get",
        async: true,
        data: {"id": indexId},
        url: url,
        async:false,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $("#name").val(d.obj.regionName);
                $("#code").val(d.obj.regionCode);
                $("#type").val(d.obj.proviceType);
                type = d.obj.proviceType;
                initDataSource(1);
                var pidName = d.obj.pname;
                if (pidName != "" || pidName != null) {
                    pidName = d.obj.pname;
                    
                } else {
                	pidName = "无";
                }
                if (d.obj.pid) {
                    $("#pid").val(d.obj.pid);
                } else {
                    $("#pid").val('');
                }
                $("#preIdWrapSource > span").html(pidName)
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
    var delete_url = baseUrl + "/provice/delete";
    layer.open({
        content: '是否确定删除？',
        btn: ["确认", "取消"],
        yes: function () {
            $.ajax({
                type: "get",
                async: true,
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

$("#type").bind("change",function(){
	$("#preIdWrapSource>span").html("");
	$("#pid").val("");
	type = $("#type").find("option:selected").val();
	initDataSource(1);
});
function initDataSource(index) {
    no = index;
    initTreeDataSource();
    var type1 = $("#type").val();
    $("#preIdWrapSource").click(function (e) {
    	if(type1 == ""){
    		layer.msg( "请选择地区类型！");
    		return ;
    	}
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
        url: baseUrl + "/provice/listProvice",
        type: "post",
        data: {type:type},
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
                name: "regionName"
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
        var name = treeNode.regionName;
        $("#preIdWrapSource>span").html(name);
        $(this).unbind();
        $("#pid").val(treeNode.id);
        $("#pidName").val(treeNode.name);
        $("#preIdSource").hide();
    }
}
