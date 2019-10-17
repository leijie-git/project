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
var numberVal = "";
var nameVal = "";
var dataHeight = 570;
$(function () {
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    mainSearch();
});
//高级搜索
$(".seniorSearch").click(function () {
    var clickNo = $(".jy_mainTile").attr("clickNo");
    if (clickNo == 0) {
        $(".jy_mainTile").attr("clickNo", "1");
        $(this).html("普通搜索");
        $(".jy_mainTile").show();
        dataHeight = 570;
        refreshTable();
    } else {
        $(".jy_mainTile").attr("clickNo", "0");
        $(this).html("高级搜索");
        $(".jy_mainTile").hide();
        dataHeight = 658;
        refreshTable();
    }
});
//普通搜索
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    $("#roleTable").bootstrapTable('destroy');
    mainSearch();
});
function refreshTable() {
    $("#roleTable").bootstrapTable('destroy');
    mainSearch();
}
$("#menuType").change(function () {
    if ($(this).val() == 'roelLevel') {
        $("#commonInput").html('<select name="searchContent" id="searchContent" class="form-control">' +
            '<option value="">全部</option><option value="XT">系统</option><option value="XX">学校</option>' +
            '<option value="BJ">班级</option></select>');
    } else {
        if(!$('#searchContent').hasClass('showInput')){
            $("#commonInput").html('<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
        }
    }
    emptySearch();
});
//查询条件查询
$(".btnSearch").click(function () {
    nameVal = $("#roleNames").val();
    $("#roleTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
    emptySearch();
    $("#searchContent").val("");
    refreshTable();
});
$(".btnNew").click(function () {
    $("#newAdd .modal-title").html("新增角色");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
    $("#preIdWrapRole > span").html("请点击选择权限");
    //查询权限
    initDataSource(1);
    $("#roleName").val("");
    $("#roelType").val("");
    $("#sortIndex").val("");
});
//新增或编辑角色
$(".btnSure").click(function () {
    var indexId = $("#indexId").val();
    var roleName = $("#roleName").val();
//    var roleType = $("#roleType").val();
    if (roleName == "") {
        layer.msg("名称不能为空！");
        return;
    } else {
        var roleNameLenth = $("#roleName").val().length;
        if (roleNameLenth > 10) {
            layer.msg("名称最多为10个字！");
            return;
        }
    }
    var sortIndex = $("#sortIndex").val();
    var resource = $("#preId").val();
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/role/addRole?type=1';
    }else{
    	var url = baseUrl + '/role/updateRole?type=1';
    }
   
    var data = {
    		"id":indexId,
		"rolename": roleName, 
//		"roletype": roleType, 
		"resourceId": resource, 
		"sortindex": sortIndex
    };
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
                layer.msg("保存成功！");
                $("#roleName").val("");
                $("#sortIndex").val("");
                $("#newAdd").modal("hide");
                $('#roleTable').bootstrapTable("refresh");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

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
$(".deleteMore").click(function () {
    var checked = $("#roleTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/role/deleteManySystemRole?type=1";
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"id": vals},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $('#roleTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});
//    授权
$(".btnGrantss").click(function () {
    var checked = $("#roleTable").bootstrapTable('getSelections');
    if (checked.length <= 0) {
        layer.msg("请选择一条记录！");
        return;
    } else {
        $("#grant").modal("show");
        $(".mainList").empty().append('<table class="table table-bordered" ' +
            'id="grantTable" data-click-to-select="true"></table>');
        doInitGrant();
    }
});
function emptySearch() {
    $("#numberId").val('');
    $("#roleNames").val('');
    $("#roelLevel").val('');
    numberVal = '';
    nameVal = '';

}
//编辑角色
function editRole(_this) {
    $("#newAdd .modal-title").html("编辑角色");
    // initDataSource(1);
    var indexId = _this.id;
    $("#edit").val('1');
    $("#indexId").val(indexId);
    $("#newAdd").modal();
    var url = baseUrl + '/role/editRole?type=1';
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
                $("#roleName").val(d.obj.roleName);
//                $("#roleType").val(d.obj.roleType);
                if (d.obj.resourceId) {
                    $("#preIdWrapRole > span").html('已选' + d.obj.resourceId.length + '项');
                } else {
                    $("#preIdWrapRole > span").html('已选0项');
                }
                $("#preId").val(setPreId(d.obj.resourceId));
                $("#indexId").val(d.obj.id);
                initDataSource(1);
                $("#sortIndex").val(d.obj.sortIndex);
                mainSearch();
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
}
//查看角色
function viewRole(_this) {
    $("#newAddView .modal-title").html("查看角色");
    var indexId = _this.id;
    $("#newAddView").modal();
    var url = baseUrl + '/role/editRole?type=1';
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
                var delFlag = d.obj.isDeleted;
                if (delFlag == 1) {
                    $("#roelTypeView span").eq(0).addClass("btnChoose").siblings().removeClass("btnChoose");
                } else if (delFlag == 0) {
                    $("#roelTypeView span").eq(1).addClass("btnChoose").siblings().removeClass("btnChoose");
                }
//                var level = d.obj.roleType;
                $("#roleNameView").val(d.obj.roleName);
//                $("#roleTypeView").val(level);
                if (d.obj.resourceId) {
                    $("#preView").val('已选' + d.obj.resourceId.length + '项');
                } else {
                    $("#preView").val('已选0项');
                }
                $("#sortIndexView").val(d.obj.sortIndex);
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
}
//删除角色
function deleteRole(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/role/deleteRole?type=1';
    layer.open({
        content: "是否确定删除？",
        btn: ["确认", "取消"],
        yes: function (index, layero) {
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
                        $('#roleTable').bootstrapTable("refresh");
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
//授权查询
function doInitGrant() {
    initGrant();
}
//授权提交
$(".btnGrant").click(function () {
    var checkvalues = $("#grantTable").bootstrapTable('getSelections');//获取选中的id
    var checkedRoles = $("#roleTable").bootstrapTable('getSelections');
    if (checkvalues == "") {
        layer.msg("请选择一条记录！");
        return;
    } else {
        var valArr = new Array;
        for (var i = 0; i < checkvalues.length; i++) {
            valArr[i] = checkvalues[i].id;
        }
        var vals = valArr.join(',');

        var valArr2 = new Array;
        $(checkedRoles).each(function (i, item) {
            valArr2[i] = item.id;
        });
        var roleId = valArr2.join(',');
        var url = baseUrl + "/roleManage/authorizeSystemUserRole?type=1";
        layer.open({
            content: '授权以后将撤销该员工的其他角色'
            , btn: ['确认', '取消']
            , yes: function () {
                $.ajax({
                    type: "post",
                    async: true,
                    beforeSend: function(request){
                        request.setRequestHeader("Authorization","Bearer "+login.token);
                    },
                    data: {"id": vals, "roleId": roleId},
                    url: url,
                    dataType: "json",
                    success: function (d) {
                        if (d.success) {
                            layer.msg("授权成功！");
                            $("#grant").modal("hide");
                            //刷新表格
                            $.fn.bootstrapTreeTable.methods.refresh($("#areaTable"));
                        } else {
                            layer.msg(d.msg);
                        }
                    },
                    error: function (e) {

                    }
                });
            }
            , cancel: function () {
                //右上角关闭回调
            }
        });
    }
});
//资源管理（上级菜单）
function initDataSource(index) {
    no = index;
    initTreeDataSource();
    $("#preIdWrapRole").click(function (e) {
        e.stopPropagation();
        $("#preIdRole").show();
    });
    $(document).click(function () {
        $("#preIdRole").hide();
    });
    $("#preIdRole").click(function (e) {
        e.stopPropagation();
    })
}
//初始化资源
function initTreeDataSource() {
    $.ajax({
        url: baseUrl + "/resource/resourceList?type=1",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {},
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
        check: {
            enable: true,
            chkStyle: "checkbox"
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
            onCheck: onCheck
        }
    };
    $.fn.zTree.init($("#preIdRole"), setting, nodes);
    function onCheck(e, treeId, treeNode) {
        count();
    }

    function count() {
        var zTree = $.fn.zTree.getZTreeObj("preIdRole");
        var arr = zTree.getChangeCheckedNodes();
        $("#preIdWrapRole>span").html("已选" + arr.length + "项");
        var newArr = [];
        for (var i = 0; i < arr.length; i++) {
            newArr.push(arr[i].id);
        }
        var preId = newArr.toString();
        $("#preId").val(preId);
    }

    var preId = $("#preId").val();
    if (preId != '') {
        var zTree2 = $.fn.zTree.getZTreeObj("preIdRole");
        var list;
        if (preId.split(',')) {
            list = preId.split(',')
        } else {
            list = preId;
        }
        for (var j = 0; j < list.length; j++) {
            zTree2.checkNode(zTree2.getNodeByParam("id", list[j]), true);
        }
    }
}
function setPreId(list) {
    var vals = '';
    $(list).each(function (i, item) {
        if (vals == '') {
            vals = vals + item;
        } else {
            vals = vals + ',' + item;
        }
    });
    return vals;
}
function mainSearch() {
    $('#roleTable').bootstrapTable({
        url: baseUrl + '/role/roleList?type=1',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions:{
            headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                roleName: $("#searchContent").val(),
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
                field: 'selectItemMain',
                checkbox: true
            },{
					//field: 'Number',//可不加
					title: '序号',//标题  可不加
					formatter: function (value, row, index) {
						return index+1;
					}
             },
            {
                field: 'roleName',
                title: '角色名'
            },
//            {
//                field: 'roleType',
//                title: '角色类型',
//                sortable: true,
//            	formatter: function (roleType) {
//            		var value = roleType;
//        			if (value == '1') {
//        				return '人员';
//        			} else {
//        				return '公司';
//        			} 
//                }
//            },
            {
                field: 'sortIndex',
                title: '排序'
            },
            {
                field: 'addDate',
                title: '添加时间'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
        			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        editRole(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteRole(row);
                    },
                    'click .view': function (e, value, row, index) {
                        viewRole(row);
                    }
                }
            }
        ]
    });
}
function initGrant() {
    $('#grantTable').bootstrapTable('destroy');
    $('#grantTable').bootstrapTable({
        url: baseUrl + '/personManage/employeeManage/findAllEmployee?type=1',         //请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions:{
            headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                id: $("#numberId").val(),
                roleName: $("#roleNames").val(),
                level: $("#roelLevel").val()
            };
            return param;
        },
        dataField: 'rows',
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
        // height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'selectItemMain',
                checkbox: true
            },
            {
                field: 'userNo',
                title: '工号'
            },
            {
                field: 'userName',
                title: '姓名'
            },
            {
                field: 'identityNo',
                title: '身份证号'
            },
            {
                field: 'phone',
                title: '电话'
            },
            {
                field: 'roleName',
                title: '角色'
            }
        ]
    });
}
