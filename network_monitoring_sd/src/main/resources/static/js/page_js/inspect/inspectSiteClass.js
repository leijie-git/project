/**
 * Created by Kip Sheng 2017-11-7 09:06:33
 */
//var columns = [
//    {
//        field: 'selectItem',
//        checkbox: true
//    },
//    {
//        title: '角色',
//        field: 'roleName',
//        align: 'center',
//        valign: 'middle',
//        sortable: true,
//        width: '180px',
//        formatter: function (value) {
//            var val = value.roleName;
//            if (val == 0 || val == null || val == undefined || val == '') {
//                return "无"
//            } else {
//                return val;
//            }
//        }
//    }
//];
var IP;
var dataHeight = 570;
var siteClassId;
var list;
var unitID;
$(function () {
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    getUnitID();
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
    // emptySearch();
});


//普通搜索
$(".ordinarySearch").click(function () {
	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#"+id).val(value);
    $("#SiteClassTable").bootstrapTable('destroy');
    mainSearch();
});

//查询条件查询
$(".btnSearch").click(function () {
	// var id = $("#menuType").val();
    // var value = $("#searchContent").val();
    // $("#"+id).val(value);
    $("#SiteClassTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#SiteClassTable").bootstrapTable('destroy');
    mainSearch();
});

$("#menuType").change(function () {
	emptySearchMsg();
});


$(".btnNew").click(function () {
	emptySearch();
    $("#newAdd .modal-title").html("新增巡查设施");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
});
function emptySearchMsg(){
    $("#searchContent").val("");
    $("#siteNames").val("");
    $("#siteClassDesc").val("");
}
//新增或编辑角色
$(".btnSure").click(function () {
    var SiteClassName = $("#SiteClassName").val();
    var SiteClassDesc= $("#SiteClassDesc").val();
    if (SiteClassName == "") {
        layer.msg("巡查设施名称不能为空！");
        return;
    }
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/inspectSiteClass/add';
    }else{
    	var url = baseUrl + '/inspectSiteClass/update';
    }
   
    var data = {
    		"ID":siteClassId,
		"SiteClassName": SiteClassName, 
		"SiteClassDesc": SiteClassDesc,
		"UnitID":unitID
		
    };
    $.ajax({
        type: "post",
        async: true,
        data: data,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                layer.msg("保存成功！");
                emptySearch();
                $("#newAdd").modal("hide");
                $('#SiteClassTable').bootstrapTable("refresh");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

function emptySearch() {
	$("#SiteClassName").val("");
	$("#SiteClassDesc").val("");
}
function emptySearchView() {
	$("#SiteClassNameView").val("");
	$("#SiteClassDescView").val("");
}
//编辑角色
function editBuildArea(_this) {
	emptySearch();
	siteClassId = _this.id;
	$("#SiteClassName").val(_this.siteClassName);
	$("#SiteClassDesc").val(_this.siteClassDesc);
	$("#newAdd .modal-title").html("编辑巡查设施");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('1');
	$("#newAdd").modal();
}

//查看角色
function showBuildArea(_this) {
	emptySearchView();
	siteClassId = _this.id;
	$("#SiteClassNameView").val(_this.siteClassName);
	$("#SiteClassDescView").val(_this.siteClassDesc);
	$("#newAddView .modal-title").html("查看巡查设施");
	$("input[name=indexId][type=hidden]").val("");
	$("#newAddView").modal();
}

//删除角色
function deleteBuildArea(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/inspectSiteClass/remove';
    layer.open({
        content: "是否确定删除？",
        btn: ["确认", "取消"],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                async: true,
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {"id": indexId},
                url: url,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        $('#SiteClassTable').bootstrapTable("refresh");
                        layer.msg("删除成功！");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (e) {

                }
            });
        },
        no: function (index, layero) {
        }
    });
}

function getUnitID() {
	$.ajax({
		type : "get",
		async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		url : baseUrl + "/inspectPlan/getUnitID",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				unitID = d.obj;
				mainSearch();
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		}
	});
}

function mainSearch() {
    $('#SiteClassTable').bootstrapTable({
        url: baseUrl + '/inspectSiteClass/getList',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions: {
            headers: {"Authorization": "Bearer "+login.token}       //token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                SiteClassName: $("#siteNames").val(),
                SiteClassDesc:$("#siteClassDesc").val(),
                UnitID:unitID
            };
            return param;
        },
        dataField: 'list',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100, 200, 500],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        searchTimeOut: 1000,
        //  	: true,
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: dataHeight,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
        	
            {
                field: 'siteClassName',
                title: '巡查设施名称'
            },
            {
                field: 'siteClassDesc',
                title: '巡查设施描述'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        editBuildArea(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteBuildArea(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showBuildArea(row);
                    }
                }
            }
        ]
    });
}
//批量删除
$("#checkAll").click(function () {
    if (this.checked) {
        $("[name=check]:checkbox").prop("checked", true);
    } else {
        $("[name=check]:checkbox").prop("checked", false);
    }
});
$("input[name=check]:checkbox").click(function () {
    allchk();
});
function allchk() {
    var chknum = $("input[name=check]").size();//选项总个数
    var chk = 0;
    $("input[name=check]:checkbox").each(function () {
        if ($(this).prop("checked") == true) {
            chk++;
        }
    });
    if (chknum == chk) {//全选
        $("#checkAll").prop("checked", true);
    } else {//不全选
        $("#checkAll").prop("checked", false);
    }
}
$(".removeMove").click(function () {
    var checked = $("#SiteClassTable").bootstrapTable('getSelections');
    if (checked.length > 0) {
        var valArr = new Array;
        $(checked).each(function (i, item) {
            valArr[i] = item.id;
        });
        var vals = valArr.join(',');
    } else {
        layer.msg("请选择一条记录！");
        return;
    }
    var url = baseUrl + "/inspectSiteClass/remove";
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"id": vals},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $('#SiteClassTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});