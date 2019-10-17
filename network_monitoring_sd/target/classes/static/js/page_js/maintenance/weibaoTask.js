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
var inspectPlanId;
var unitList;
var buildList;
var buildAreaList;
var flag = 0;
var planUserName;
var planUserID;
var planNames;
var startTime;
var endTime;
var sitecode;
var taskStart;
var taskEnd;
var unitID;
var planUserList;
var planUserNameList;
var planDetailId;
var planID;
$(function() {

    //日期选择控件
    $("#StartTime").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })

    $("#EndTime").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })

    $("#planStart").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })
    $("#planEnd").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })

    $("#TaskStart").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })
    $("#TaskEnd").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    })
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    getUnitID();
    getUnitList();
    getBuildList();
  /*  showBuildList("","buildIdSearch");//高级搜索建筑
    $("#buildIdSearch").bind("change", function() {
        getBuildAreaList();
        showBuildAreaList();
    });*/
    personUnit();

    maintenancePlan(planID,"buildIdSearch");

    /*$("#Taskremove").click(function() {
        $("#select1 option:selected").appendTo("#select2");
    });

    $("#Taskremove_all").click(function() {
        $("#select1 option").appendTo("#select2");
    });

    $("#Taskadd").click(function() {
        $("#select2 option:selected").appendTo("#select1");
    });

    $("#Taskadd_all").click(function() {
        $("#select2 option").appendTo("#select1");
    });

    $("#select1").dblclick(function() {
        $("#select1 option:selected").appendTo("#select2");

    });

    $("#select2").dblclick(function() {
        $("#select2 option:selected").appendTo("#select1");
    });

    $("#Planremove").click(function() {
        $("#select3 option:selected").appendTo("#select4");
    });

    $("#Planremove_all").click(function() {
        $("#select3 option").appendTo("#select4");
    });

    $("#Planadd").click(function() {
        $("#select4 option:selected").appendTo("#select3");
    });

    $("#Planadd_all").click(function() {
        $("#select4 option").appendTo("#select3");
    });

    $("#select3").dblclick(function() {
        $("#select3 option:selected").appendTo("#select4");

    });

    $("#select4").dblclick(function() {
        $("#select4 option:selected").appendTo("#select3");
    });*/
});



//普通搜索
$(".ordinarySearch").click(function() {
    var id = $("#menuType").val();
    var value = $("#searchContent option:checked").val();
    $("#"+id).val(value);
    $("#inspectPlanTable").bootstrapTable('destroy');
    mainSearch();


});
//普通搜索切换
$("#menuType").bind("change", function() {
   // var planID = $("#maintenance option:checked").val();
   // maintenancePlan(planID,"maintenance");
  var  menuType = $(this).find('option:selected').val();
   // console.log(menuType);
    $("#searchContent").val("");
    $("#buildIdSearch").val("");
    $("#executor").val("");
  if(menuType=="buildIdSearch"){
      maintenancePlan("","searchContent");
  }else if(menuType=="executor"){
      personUnit("","searchContent");
  }


});

$(".seniorSearch").click(function() {
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

});

/*$("#buildId").bind("change", function() {
	getBuildAreaList($("#buildId").val());
});*/


//查询条件查询
$(".btnSearch").click(function() {
    $("#inspectPlanTable").bootstrapTable('destroy');
    mainSearch();
});


function emptySearchMsg(){
    $("#buildIdSearch").val("");
    $("#executor").val("");
    $("#planStart").val("");
    $("#planEnd").val("");
    $("#Position").val("");
}
//重置
$(".btnReset").click(function() {
    emptySearchMsg();
    $("#inspectPlanTable").bootstrapTable('destroy');
    mainSearch();
});



//新增
$(".btnNew").click(function() {
    var planID = $("#maintenance option:checked").val();
    maintenancePlan(planID,"maintenance");
    $("#Personliable").html("");
    emptySearch();
    inspectPlanId = null;
    planUserList="";
    //planUserNameList="";
    $('#inspectSiteTable').bootstrapTable("destroy");
    $("#newAdd .modal-title").html("新增维保计划录入");
    $("#UnitID").val($("#LastUnitID").val())
    $("input[name=indexId][type=hidden]").val("");
    $("#taskImport").hide();
    $("#edit").val('0');
    $("#preId").val('');
    $("#Personliable option:selected").val('');
    $("#buildAreaId").html("");
    $("#buildAreaId option:selected").val('');
    $("#PlanName").html('');
    $("#StartTime").html('');
    $("#EndTime").html('');

});

$(".removeMove").click(function () {
    var checked = $("#mainTaskTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/task/remove";
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
                $('#mainTaskTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

//新增或编辑计划
$(".btnSure").click(function() {
   var maintenance=$("#maintenance option:selected").val();
    var maintenanceObject=$("#maintenanceObject").val();
    var position=$("#position").val();
    if (maintenance == "") {
        layer.msg("维保计划不能为空！");
        return;
    }else if (maintenanceObject == "") {
        layer.msg("维保位置对象不能为空");
        return;
    } else if (position == "") {
        layer.msg("位置不能为空");
        return;
    }
    var edit = $("#edit").val();

    if (edit == "0") {
        var url = baseUrl + '/maintenanceTask/taskAdd';
    } else {
        var url = baseUrl + '/maintenanceTask/taskModify';
    }

    var data = {
        /*id:inspectPlanId,
        unitID :UnitID,
        buildID:buildId,
        buildAreaID:buildAreaId,
        executorID:Personliable,
        planStartTime:startTime,
        planEndTime:endTime,
        PlanName:planName*/

        ID:inspectPlanId,
        planID:maintenance,
        RepairSite:position,
        eqName:maintenanceObject
    };
    //console.log(data);
    var isAsync = true, lid = 0;
    if (isAsync)
        lid = layer.msg('正在加载...', {
            icon : 16,
            shade : [ 0.1, '#fff' ],
            time : 90000
        })
    $.ajax({

        type : "post",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : url,
        dataType : "json",
        success : function(d) {
            if (d.success) {
                layer.msg("保存成功！");
                $("#newAdd").modal("hide");
                $('#inspectPlanTable').bootstrapTable("refresh");
            } else {
                layer.msg(d.msg);
            }
        },
        error : function(e) {

        },
        complete : function() {
            if (isAsync) {
                layer.close(lid);
            }
        }
    });
});

function emptySearch() {
    $("#StartTime").val("");
    $("#PlanName").val("");
    $("#DefaultUserName").val("");
    $("#EndTime").val("");

}

//编辑
function editInspectPlan(_this) {
    inspectPlanId = _this.id;
    //maintenancePlan(_this.planID);
    $("#maintenance").val(_this.planID);
    console.log(_this);
    console.log(_this.planName);
    console.log(_this.planID);
    maintenancePlan(_this.planID,"maintenance");
    $("#maintenanceUnit").val(_this.unitName);
    $("#buildId").val(_this.buildingName);
    $("#buildAreaId").val(_this.buildAreaName);
    $("#Personliable").val(_this.userName);
    $("#maintenanceObject").val(_this.eqName);
    $("#position").val(_this.repairSite);
    $("#newAdd .modal-title").html("编辑维保计划录入");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('1');
    $("#newAdd").modal();

}
// 获取单位列表
function getUnitList() {
    $.ajax({
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url : baseUrl + "/baseInfo/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                unitList = d.obj;
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}
// 填充单位下拉框
function showUnitList(unitID) {
    var html = '<option value="">--请选择--</option>';
    for (var i = 0; i < unitList.length; i++) {
        if (unitID && unitID == unitList[i].id) {
            html += '<option selected value="' + unitList[i].id + '"  style="width: 70%">'
                + unitList[i].unitname + '</option>';
        } else {
            html += '<option value="'+ unitList[i].id +'" style="width: 70%">'+ unitList[i].unitname +'</option>';
        }

    }
   // $("#maintenanceUnit").html(html);
   // $("#maintenanceUnit").comboSelect();//(单位下拉框)
    //$("#unitname").html(html);
}
// 获取建筑物列表
function getBuildList() {
    var data = {
        UnitID : unitID
    };
    $.ajax({
        type : "get",
        async : false,
        data : data,
        url : baseUrl + "/build/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildList = d.obj;
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 填充建筑物下拉框
function showBuildList(val,element) {
    var arr = buildList;
    var wrap = '<option value="">--请选择--</option>';
    for (var i = 0; i < arr.length; i++) {
        if (val && val == arr[i].id) {
            option = '<option selected value="' + arr[i].id + '" >'
                + arr[i].buildingName + '</option>';
        } else {
            option = '<option value="' + arr[i].id + '" >'
                + arr[i].buildingName + '</option>';
        }
        wrap += option;
    }
    $("#"+ element +"").html(wrap);
   // $("#buildId").comboSelect();
}
$("#buildId").bind("change", function() {

    getBuildAreaList($("#buildId").val());
});
// 获取区域列表
function getBuildAreaList(buildId) {
    var buildId = $("#buildId").find("option:selected").val();
    var data = {
        buildID : buildId
    }
    $.ajax({
        type : "get",
        async : false,
        data : data,
        url : baseUrl + "/buildArea/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildAreaList = d.obj
                if(buildId != null && buildId != ""){
                    showBuildAreaList();
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}
// 填充区域下拉框
function showBuildAreaList(val) {
    var arr = buildAreaList;
    var wrap = '<option value="">--请选择--</option>';
    for (var i = 0; i < arr.length; i++) {
        if (val && val == arr[i].id) {
            option = '<option selected value="' + arr[i].id + '" >'
                + arr[i].buildAreaName + '</option>';
        } else {
            option = '<option value="' + arr[i].id + '" >'
                + arr[i].buildAreaName + '</option>';
        }
        wrap += option;
    }
    $("#buildAreaId").html(wrap);
    $("#buildAreaIdSearch").html(wrap);
    $("#buildAreaId").comboSelect();
    $("#buildAreaIdView").html(wrap);
}
//填充维保执行人列表
function personUnit(defaultUserID,element) {
    //var choose = $("#maintenanceUnit").val();
    //console.log(choose);
    // debugger;
    $.ajax({
        //debugger,
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url : baseUrl + "/repair/getMaintenanceUserBynetworkingID",
        data:{
            UnitID:unitID
            //userrole:1
        },
        success : function(d) {
            if (d.success) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < d.obj.length; i++) {

                    if(defaultUserID && (defaultUserID == d.obj[i].userID)){
                        // console.log(duty+'[=]'+d.obj[i].id);
                        html += '<option value="'+ d.obj[i].userID +'" selected>'+ d.obj[i].userName +'</option>';
                    }else{
                        html += '<option value="'+ d.obj[i].userID +'">'+ d.obj[i].userName +'</option>';
                    }
                }
                $("#"+ element +"").html(html);
                $("#Personliable").html(html);
                $("#searchContent").html(html);
                $("#executor").html(html);

            }
        },
        error : function(e) {

        }
    });
}
//更改维保计划填充建筑区域
$("#maintenance").bind("change", function() {
    var planID = $("#maintenance option:checked").val();
    maintenancePlan(planID,"maintenance");
});
$("#buildSearch").bind("change", function() {
    var planID = $("#maintenance option:checked").val();
    maintenancePlan(planID,"buildSearch");
});

//维保计划列表
function maintenancePlan(defaultUserID,element) {
    // var choose = $("#maintenanceUnit").val();
    //     //console.log(choose);
    //     // debugger;
    $.ajax({
        //debugger,
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url : baseUrl + "/maintenanceTask/getPlanList",
        data:{
            UnitID:unitID
            //userrole:1
        },
        success : function(d) {
            //debugger;
            if (d.success) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < d.obj.length; i++) {

                    if(defaultUserID && (defaultUserID == d.obj[i].planID)){
                        // console.log(duty+'[=]'+d.obj[i].id);
                        html += '<option value="'+ d.obj[i].planID +'" selected>'+ d.obj[i].planName +'</option>';

                    }else{
                        html += '<option value="'+ d.obj[i].planID +'">'+ d.obj[i].planName +'</option>';
                    }
                    if(defaultUserID && (defaultUserID == d.obj[i].planID)){
                    $("#maintenanceUnit").val(d.obj[i].unitName);
                    $("#buildId").val(d.obj[i].buildingName);
                    $("#buildAreaId").val(d.obj[i].buildAreaName);
                    $("#Personliable").val(d.obj[i].userName);
                    $("#buildSearch").val(d.obj[i].unitName);
                    };
                }
                //
                $("#"+ element +"").html(html);
                $("#searchContent").html(html);
               // $("#maintenance").html(html);


            }


        },
        error : function(e) {

        }
    });
}
//获取当前用户所属单位
function getUnitID() {
    $.ajax({
        type : "get",
        async : false,
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


function emptySearchView() {
    $("#StartTimeView").val("");
    $("#PlanNameView").val("");
    $("#DefaultUserNameView").val("");
    $("#EndTimeView").val("");
}

// 查看列表
function showInspectPlan(_this) {
    emptySearchView();
    showUnitList(_this.unitID);
    inspectPlanId = _this.id;
    startTime = _this.planStart;
    endTime = _this.planEnd;
    planUserID = _this.defaultUserID;
    planUserName = _this.defaultUserName;
    $("#StartTimeView").val(_this.planStart);
    $("#PlanNameView").val(_this.planName);
    planUserList = _this.DefaultUserID;
    planUserNameList = _this.defaultUserName;
    $("#DefaultUserNameView").val(_this.defaultUserName);
    $("#DefaultUserIDView").val(_this.DefaultUserID);
    $("#EndTimeView").val(_this.planEnd);
    $("#UnitIDView").val(_this.unitID);
    $("#unitName").val(_this.unitID);
    $("#duty").val(_this.supervisorName);


    $("#inspectSiteTableView").bootstrapTable('destroy');
    //getPlanSiteView(_this.id);
    $("#newAddView .modal-title").html("查看巡查计划");
    $("input[name=indexId][type=hidden]").val("");
    $("#newAddView").modal();
}
//删除维保计划录入
function deleteInspectPlan(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/maintenanceTask/taskDelete';
    layer.open({
        content : "是否确定删除？",
        btn : [ "确认", "取消" ],
        yes : function(index, layero) {
            var isAsync = true,lid = 0;
            if (isAsync)
                var	lid = layer.msg('正在加载...', {
                    icon : 16,
                    shade : [ 0.1, '#fff' ],
                    time : 90000
                });

            $.ajax({
                type : "post",
                async : true,
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data : {
                    "id" : indexId
                },
                url : url,
                dataType : "json",
                success : function(d) {
                    if (d.success) {
                        $('#inspectPlanTable').bootstrapTable("refresh");
                        layer.msg("删除成功！");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error : function(e) {

                },
                complete : function() {
                    if (isAsync) {
                        layer.close(lid);
                    }
                }
            });
        },
        no : function(index, layero) {
        }
    });
}

//重置维保计划录入
function resetInspectPlan(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/repair/repairReset';
    layer.open({
        content : "是否确定重置数据？",
        btn : [ "确认", "取消" ],
        yes : function(index, layero) {
            var isAsync = true, lid = 0;
            if (isAsync)
                lid = layer.msg('正在加载...', {
                    icon : 16,
                    shade : [ 0.1, '#fff' ],
                    time : 90000
                })
            $.ajax({
                type : "post",
                async : true,
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data : {
                    "id" : indexId
                },
                url : url,
                dataType : "json",
                success : function(d) {
                    if (d.success) {
                        $('#inspectPlanTable').bootstrapTable("refresh");
                        layer.msg("重置成功！");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error : function(e) {

                },
                complete : function() {
                    if (isAsync) {
                        layer.close(lid);
                    }
                }
            });
        },
        no : function(index, layero) {
        }
    });
}

//主页面加载
function mainSearch() {
    $('#inspectPlanTable')
        .bootstrapTable(
            {
                url : baseUrl + '/maintenanceTask/getList', //请求后台的URL（*）
                method : 'get', //请求方式（*）
                contentType : 'application/x-www-form-urlencoded',
                toolbar : '#toolbar', //工具按钮用哪个容器
                striped : true, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                sortable : false, //是否启用排序
                sortOrder : "asc", //排序方式
                queryParamsType : "undefined", //排序方式
                ajaxOptions: {
                    headers: {"Authorization": "Bearer "+login.token}       //token写入请求头
                },
                queryParams : function queryParams(params) { //设置查询参数
                    var param = {
                        pageNumber : this.pageNumber,
                        pageSize : this.pageSize,
                        /*PlanName : $("#planNames").val(),
                        supervisorName:$("#defaultUserName").val(),
                        PlanStart : $("#planStart").val(),
                        EndStart : $("#PlanEnd").val(),*/
                        //unitID :"606908978936410112"
                        unitID :unitID,
                        executorID:$("#executor").val(),
                        planID:$("#buildIdSearch ").val(),
                        RepairSite:$("#Position").val(),
                        //eqName:"",
                        taskStartTime:$("#planStart").val(),
                        taskEndTime:$("#planEnd").val()
                    };
                    return param;
                },
                dataField : 'list',
                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100, 200, 500 ], //可供选择的每页的行数（*）
                search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                searchTimeOut : 1000,
                //  	: true,
                showColumns : true, //是否显示所有的列
                showRefresh : true, //是否显示刷新按钮
                minimumCountColumns : 2, //最少允许的列数
                clickToSelect : true, //是否启用点击选中行
                singleSelect : false,
                // height: dataHeight,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
                cardView : false, //是否显示详细视图
                detailView : false, //是否显示父子表
                columns : [
                    {
                        field: 'selectItem',
                        checkbox: true
                    },
                    {
                        field : 'planName',
                        title : '维保计划'
                    },
                    {
                        field : 'unitName',
                        title : '联网单位'
                    },
                    {
                        field : 'userName',
                        title : '维保责任人'
                    },
                    {
                        field : 'eqName',
                        title : '维保对象',

                    },

                    {
                        field : 'repairSite',
                        title : '位置'
                    }
                    ,
                    {
                        field : 'taskStartTime',
                        title : '开始时间'
                    }
                    ,
                    {
                        field : 'taskEndTime',
                        title : '结束时间'
                    }
                    ,
                    {
                        field : '',
                        title : '操作',
                        formatter : function(value, row) {
                            var value = row.isGenerate;
                            var str = "";
                                str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
                                str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                            return str;
                        },
                        events : {
                            'click .edit' : function(e, value, row,
                                                     index) {
                                editInspectPlan(row);
                            },
                            'click .del' : function(e, value, row,
                                                    index) {
                                deleteInspectPlan(row);
                            },
                            'click .reset' : function(e, value, row,
                                                      index) {
                                resetInspectPlan(row);
                            }
                            ,
                            'click .view' : function(e, value, row,
                                                     index) {
                                showInspectPlan(row);
                            },
                            'click .addTask' : function(e, value,
                                                        row, index) {
                                Imports(row);
                            },
                            'click .LookTask' : function(e, value,
                                                         row, index) {
                                LookTask(row);
                            }
                        }
                    } ]
            });
}
//主界面生成任务操作
$(".generateTask").click(function() {
    var checked = $("#inspectPlanTable").bootstrapTable('getSelections');
    //console.log(checked);
    layer.open({
        //type: 1,
        //closeBtn: false,
        // closeBtn: 1,
        //shift: 2,
        // shadeClose: true,
        btn: ["生成任务", "取消"],
        content: "确定生成" + checked.length + "条任务？",
        yes : function(index, layero) {
            if(checked.length > 0){
                var valArr = new Array();
                $(checked).each(function (i, item) {
                    valArr[i]=item.id;
                });
                var siteID = valArr.join(',');
            }else{
                layer.msg("请选择一条记录");
                return;
            }
            var url = baseUrl + "/repair/repairGenerate";
            var isAsync = true, lid = 0;
            if (isAsync)
                lid = layer.msg('正在加载...', {
                    icon : 16,
                    shade : [ 0.1, '#fff' ],
                    time : 90000
                })
            $.ajax({
                type : "post",
                async : true,
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data : {
                    "id" : siteID
                },
                url : url,
                dataType : "json",
                success : function(d) {
                    if (d.success) {
                        $('#inspectPlanTable').bootstrapTable("refresh");
                        layer.msg("生成任务成功！");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error : function(e) {

                },
                complete : function() {
                    if (isAsync) {
                        layer.close(lid);
                    }
                }
            });
        },
        no : function(index, layero) { },
    });
});
//条件查询
function siteSearch() {
    Imports();
}
function siteClear() {
    $("#SiteNameSite").val("");
    $("#SiteCodeSite").val("");
    Imports();
}
