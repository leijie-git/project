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
	$("#Taskremove").click(function() {
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
	});
});



//普通搜索
$(".ordinarySearch").click(function() {

	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#"+id).val(value);
	$("#inspectPlanTable").bootstrapTable('destroy');
	mainSearch();
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
	// emptySearch();
});

//查询条件查询
$(".btnSearch").click(function() {
	$("#inspectPlanTable").bootstrapTable('destroy');
	mainSearch();
});
function emptySearchMsg(){
	$("#planNames").val("");
	$("#defaultUserName").val("");
	$("#planStart").val("");
	$("#planEnd").val("");
	$("#searchContent").val("");
}
//重置
$(".btnReset").click(function() {
	emptySearchMsg();
	$("#inspectPlanTable").bootstrapTable('destroy');
	mainSearch();
});
$(".btnNew").click(function() {
    showUnitList(unitID);
    personUnit(unitID);
	if(unitID){
        getUnitID();
    }
    $("#Personliable").html("");
    $("#createAllTask").hide();
	emptySearch();
	inspectPlanId = null;
	planUserList="";
	//planUserNameList="";
	$('#inspectSiteTable').bootstrapTable("destroy");
	$("#newAdd .modal-title").html("新增巡查计划");
	$("#UnitID").val($("#LastUnitID").val())
	$("input[name=indexId][type=hidden]").val("");
	$("#taskImport").hide();
	$("#edit").val('0');
	$("#preId").val('');
	$("#Personliable option:checked").val('');
    $("#PlanName").html('');
    $("#StartTime").html('');
    $("#EndTime").html('');

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
	// var UnitID = unitID;
	var UnitID = $("#PlanUnit").val();
	var supervisorID = $("#Personliable option:checked").val();
	var DefaultUserID = planUserList;
	planUserID = planUserList;
	//var DefaultUserName = $("#DefaultUserName").val();
	//var DefaultUserName = planUserNameList;
	planUserName = planUserNameList;
	startTime = $("#StartTime").val();
	var planName = $("#PlanName").val();
	endTime = $("#EndTime").val();
    var Personliable = $("#Personliable").val();
	if (planName == "") {
		layer.msg("计划名称不能为空！");
		return;
	}else if (startTime == "") {
		layer.msg("开始时间不能为空！");
		return;
	} else if (endTime == "") {
		layer.msg("结束时间不能为空！");
		return;
	}
    else if (Personliable == "") {
        layer.msg("计划责任人不能为空！");
        return;
    }
	var edit = $("#edit").val();

	if (edit == "0") {
		var url = baseUrl + '/inspectPlan/add';
	} else {
		var url = baseUrl + '/inspectPlan/update';
	}

	var data = {
		"ID" : inspectPlanId,
		//"DefaultUserName" : DefaultUserName,
		"DefaultUserID" : DefaultUserID,
		"PlanStart" : startTime,
		"PlanName" : planName,
		"PlanEnd" : endTime,
		"UnitID" : UnitID,
        "supervisorID":supervisorID
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

//编辑计划
function editInspectPlan(_this) {
	$("#createAllTask").show();
	$("#taskImport").show();
	inspectPlanId = _this.id;
	startTime = _this.planStart;
	endTime = _this.planEnd;
	planUserID = _this.defaultUserID;
	planUserName = _this.defaultUserName;
	$("#StartTime").val(_this.planStart);
	$("#PlanName").val(_this.planName);
	planUserList = _this.defaultUserID;
	planUserNameList = _this.defaultUserName;
	$("#DefaultUserName").val(_this.defaultUserName);
	$("#DefaultUserID").val(_this.DefaultUserID);
	$("#EndTime").val(_this.planEnd);
	// $("#UnitID").val(_this.unitID);
    showUnitList(_this.unitID);
    personUnit(_this.supervisorID);//

	//$("#inspectSiteTable").bootstrapTable('destroy');
	//getPlanSite(_this.id);
	$("#newAdd .modal-title").html("编辑巡查计划");
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
    $("#PlanUnit").html(html);
   // $("#PlanUnit").comboSelect();(单位下拉框)
    $("#unitname").html(html);
}
//填充计划责任人列表
function personUnit(defaultUserID) {
    var choose = $("#PlanUnit").val();
    $.ajax({
        type : "get",
        async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
        url : baseUrl + "/baseInfoRelation/selectAccount",
        data:{UnitID:choose,
			role:1
		},
        success : function(d) {
            if (d.success) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < d.obj.length; i++) {

                    if(defaultUserID && (defaultUserID == d.obj[i].id)){
                       // console.log(duty+'[=]'+d.obj[i].id);
                        html += '<option value="'+ d.obj[i].id +'" selected>'+ d.obj[i].account +'</option>';
                    }else{
                        html += '<option value="'+ d.obj[i].id +'">'+ d.obj[i].account +'</option>';
                    }
                }
                $("#Personliable").html(html);
            }
        },
        error : function(e) {

        }
    });
}
//编辑根据所在单位查询计划责任人
/*$("#PlanUnit").on("change", function () {
    personUnit("");
});*/



//获取当前用户所属单位
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
//查看巡查计划
function getPlanSiteView(id) {
	$('#inspectSiteTableView')
			.bootstrapTable(
					{
						url : baseUrl + '/inspectSite/getPlanSite', //请求后台的URL（*）
						method : 'post', //请求方式（*）
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
								planID : id
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
						showColumns : false, //是否显示所有的列
						showRefresh : false, //是否显示刷新按钮
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
									field : 'status',
									title : '操作',
									formatter : function(value, row, index) {
										var str = "";

										if (row.status == "1") {
											str += '<button type="button" class="btn btn-danger btn-xs cBtn-main"><i class="fa fa-eye"></i>&nbsp;已生成</button>'
										} else{
											str += '<button type="button" class="btn btn-danger btn-xs cBtn-main"><i class="fa fa-pencil"></i>&nbsp;未生成</button>'
										}

										return str;
									},
									events : {
										'click .edit' : function(e, value, row,
												index) {
											editInspectSite(row);
										},
										'click .del' : function(e, value, row,
												index) {
											deleteInspectSite(row);
										},
										'click .getTask' : function(e, value,
												row, index) {
											createTask(row);
}
									}
								}, {
									field : 'siteName',
									title : '巡查位置'
								}, {
									field : 'siteCode',
									title : '位置编号'
								}, {
									field : 'inspectCycleType',
									title : '周期类型',
									sortable : true,
									formatter : function(inspectCycleType) {
										var value = inspectCycleType;
										if (value == '0') {
											return '日';
										} else if (value == '1') {
											return '周';
										} else if (value == '2') {
											return '月';
										} else if (value == '3') {
											return '年';
										}
									}
								},
								{
									field : 'inspectCount',
									title : '巡查次数'
								}, {
									field : 'taskUserName',
									title : '巡检人'
								} ]
					});
}


//点位添加列表
function getPlanSite(name,code,id) {
	$('#inspectSiteTable')
			.bootstrapTable(
					{
						url : baseUrl + '/inspectSite/utInspectSiteOutList', //请求后台的URL（*）
						method : 'get', //请求方式（*）
						contentType : 'application/x-www-form-urlencoded',
						toolbar : '#toolbar', //工具按钮用哪个容器
						striped : true, //是否显示行间隔色
						cache : true, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
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
                               	siteCode: name,
                                siteName: code,
                                UnitID:unitID,
                                planId:id
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
						showColumns : false, //是否显示所有的列
						showRefresh : false, //是否显示刷新按钮
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
									field : 'selectItemMain',
									//checkbox : true,
                                    formatter : function(value, row, index) {
                                        var value = row.isPlaned;
                                        if (value == '已关联' && row.status == "1"){
                                        	//debugger;
											return '<input disabled data-index="'+index+'" name="btSelectItem" type="checkbox">';

                                        } else{
                                        	return '<input data-index="'+index+'" name="btSelectItem" type="checkbox">';

                                        }
                                    }
								},
								{
									field : 'status',
									title : '操作',
									formatter : function(value, row, index) {
										var str = "";
                                        var value = row.isPlaned;
										if (value == '已关联') {
                                            if ( row.status == "1") {
                                                str += '<button type="button" class="btn btn-danger btn-xs cBtn-main"><i class="fa fa-eye"></i>&nbsp;已生成</button>'
                                            }else{
                                                str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
                                                str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                                                str += '<button type="button" class="btn btn-danger btn-xs cBtn-main getTask"><i class="fa fa-pencil"></i>&nbsp;生成任务</button>'
											}
										}
										return str;
									},
									events : {
										'click .edit' : function(e, value, row,
												index) {
											editInspectSite(row);
										},
										'click .del' : function(e, value, row,
												index) {
											deleteInspectSite(row);
										},
										'click .getTask' : function(e, value,
												row, index) {
											createTask(row);
										}
									}
								}, {
									field : 'siteName',
									title : '点位名称'
								}, {
									field : 'siteCode',
									title : '位置编号'
								},
								{
									field : 'executorName',
									title : '执行人'
								}, {
									field : 'inspectcount',
									title : '巡查频数'
								},
                            {
                                field : 'inspectcycle',
                                title : '巡查周期',
                                /*sortable : true,
                                formatter : function(inspectcycle) {
                                    var value = inspectcycle;
                                    if (value == '0') {
                                        return '日';
                                    } else if (value == '1') {
                                        return '周';
                                    } else if (value == '2') {
                                        return '月';
                                    } else if (value == '3') {
                                        return '年';
                                    }
                                }*/
                            },
                            {
                                field : 'taskstart',
                                title : '开始时间'
                            }
                            ,
                            {
                                field : 'taskend',
                                title : '结束时间'
                            }
                            ,
                            {
                                field : 'isPlaned',
                                title : '是否关联',
								sortable : true
                            }

                            ]
					});
}

//删除计划
function deleteInspectPlan(_this) {
	var indexId = _this.id;
	var url = baseUrl + '/inspectPlan/remove';
	layer.open({
		content : "是否确定删除？",
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
//查看计划对应的任务列表
function LookTask(_this) {
	// taskClear();
	$('#mainTaskTable').bootstrapTable("destroy");
	$('#mainTaskTable')
			.bootstrapTable(
					{
						url : baseUrl + '/task/getList', //请求后台的URL（*）
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
								taskstart : $("#TaskStart").val(),
								taskend : $("#TaskEnd").val(),
								sitecode : $("#SiteCodeTask").val(),
								sitename:$("#SiteName").val(),
								inspectuserid : $("#inspectusername").val(),
								planid : _this.id
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
						showColumns : false, //是否显示所有的列
						showRefresh : false, //是否显示刷新按钮
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
									field : 'selectItemMain',
									checkbox : 'true'
								},
								{
									field : 'sitename',
									title : '巡查点'
								},
								{
									field : 'taskstart',
									title : '开始时间'
								},
								{
									field : 'taskend',
									title : '结束时间'
								},
								{
									field : 'inspectusername',
									title : '执行人'
								},
								{
									field : 'supervisorName',
									title : '计划责任人'
								},
								{
									field : 'siteCount',
									title : '检查项个数',
									sortable : true,
									/*formatter : function(isinspect) {
										var value = status;
										var inspect = isinspect;
										if (inspect == '1') {
											return '已巡查'
//											if (value == '0') {
//												return '<button onclick="lookTaskDetail()">不合格</button>';
//											} else if (value == '1') {
//												return '合格';
//											}
										} else {
											return '未巡查'
										}

									}*/
								}, {
									field : 'okCheckCount',
									title : '已完成检查个数'
								}, {
									field : 'receiveStatus',
									title : '转单状态'
								} ]
					});
	$("#LookTask").modal();
}
function taskSearch() {
//	$('#mainTaskTable').bootstrapTable("destroy");
	$('#mainTaskTable').bootstrapTable("refresh");
	$("#LookTask").modal();
}

function taskClear() {
	$("#SiteCodeTask").val("");
	$("#inspectusername").val("");
	$("#SiteName").val("");
	$("#TaskStart").val("");
	$("#TaskEnd").val("");
//	$('#mainTaskTable').bootstrapTable("destroy");
	$('#mainTaskTable').bootstrapTable("refresh");
	$("#LookTask").modal();
}

//巡查不合格问题查看
function lookTaskDetail() {

}

//主页面加载
function mainSearch() {
	$('#inspectPlanTable')
			.bootstrapTable(
					{
						url : baseUrl + '/inspectPlan/getList', //请求后台的URL（*）
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
								PlanName : $("#planNames").val(),
                                supervisorName:$("#defaultUserName").val(),
								PlanStart : $("#planStart").val(),
								EndStart : $("#PlanEnd").val(),
								UnitID : unitID

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
									field : 'planName',
									title : '计划名称'
								},
								{
									field : 'planStart',
									title : '开始时间'
								},
								{
									field : 'planEnd',
									title : '结束时间'
								},
								{
									field : 'status',
									title : '状态',
									sortable : true,
									formatter : function(status) {
										var value = status;
										if (value == '0') {
											return '保存';
										} else if (value == '1') {
											return '已生成';
										}
									}
								},

                            {
                                field : 'supervisorName',
                                title : '计划责任人'
                            }
                            ,
								{
									field : '',
									title : '操作',
									formatter : function(value, row, index) {
										var str = "";
										str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
										str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
										str += '<button type="button" class="btn btn-danger btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
										str += '<button type="button" id="LastUnitID" style="display:none" value="'
												+ row.unitID + '"></button>'
										str += '<button type="button" class="btn btn-danger btn-xs cBtn-main addTask" style="margin-right:10px;"><i class="fa fa-plus"></i>&nbsp;点位添加</button>'
										if (row.status == 1) {
											str += '<button type="button" class="btn btn-danger btn-xs cBtn-main"><i class="fa fa-trash"></i>&nbsp;查看任务</button>'
										} else {
											str += '<button type="button" class="btn btn-danger btn-xs cBtn-main LookTask"><i class="fa fa-pencil"></i>&nbsp;查看任务</button>'
										}
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
//点击点位添加
function Imports(_this) {
	var planid=_this.id;
    inspectPlanId = _this.id;
    var SiteName = $("#SiteNameSite").val();
    var SiteCode = $("#SiteCodeSite").val();
	$("#createAllTask").show();
    $("#taskImport").show();
    $("#inspectSiteTable").bootstrapTable('destroy');
    getPlanSite(SiteName,SiteCode,planid)//点位生成列表;
	//planUserID = _this.defaultUserID;
	//planUserName = _this.defaultUserName;
	// $('#mainSiteTable').bootstrapTable("destroy");
	$("#addSite .modal-title").html("添加巡查点");
	$("#addSite").modal();

}

/*//批量导入巡查点位
$("#checkAll").click(function() {
	if (this.checked) {
		$("[name=check]:checkbox").prop("checked", true);
	} else {
		$("[name=check]:checkbox").prop("checked", false);
	}
});
$("input[name=check]:checkbox").click(function() {
	allchk();
});
function allchk() {
	var chknum = $("input[name=check]").size();//选项总个数
	var chk = 0;
	$("input[name=check]:checkbox").each(function() {
		if ($(this).prop("checked") == true) {
			chk++;
		}
	});
	if (chknum == chk) {//全选
		$("#checkAll").prop("checked", true);
	} else {//不全选
		$("#checkAll").prop("checked", false);
	}
}*/
//批量未关联进行关联按钮请求
$(".SiteInto").click(function() {
	var checked = $("#inspectSiteTable").bootstrapTable('getSelections');
	var isPlaned = 0;
	if (checked.length > 0) {
		var valArr = new Array;
		$(checked).each(function(i, item) {
			valArr[i] = item.siteId;
			if(item.isPlaned == '已关联'){
                isPlaned = 1;
            }
		});
		if(isPlaned == 1){
            layer.msg("请取消已关联选项,请选择未关联选择!");
            return;
		}
		var siteID = valArr.join(',');
	} else {
		layer.msg("请选择一条记录！");
		return;
	}
	taskUserID = "";
    taskUserName = "";
	var url = baseUrl + "/inspectPlanDetail/add";
	var isAsync = true,lid = 0;
    if (isAsync)
        lid = layer.msg('正在加载...', { icon: 16, shade: [0.1, '#fff'], time: 90000 })
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"taskUserID" : taskUserID,
            "siteID" : siteID,
			"planID" : inspectPlanId,
			"taskUserName" : taskUserName
		},
		url : url,
		dataType : "json",
		success : function(d) {
			if (d.success) {
				$('#inspectSiteTable').bootstrapTable("refresh");
				layer.msg("关联成功！");
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		},complete: function () {
            if (isAsync){
                layer.close(lid);
            }
        }
	});
});


//编辑计划中的点位
function editInspectSite(_this) {
    //console.log(_this);
	$("#editSite>input").empty();
	$("#SiteCodes").val(_this.siteCode);
	$("#SiteNames").val(_this.siteName);
	$("#DefaultUserNames").val(_this.taskUserName);
	//planDetailId = _this.planDetailID;
    planDetailId = _this.planId;
	//console.log("planDetailId:" + planDetailId);
	$("#InspectCycles").val(_this.inspectCount);
	var value = _this.inspectCycleType;
	var inspectCycleType;
	if (value == '0') {
		inspectCycleType= '日';
	} else if (value == '1') {
		inspectCycleType= '周';
	} else if (value == '2') {
		inspectCycleType='月';
	} else if (value == '3') {
		inspectCycleType= '年';
	}
	$("#inspectCycleType").val(inspectCycleType);
	//showAddList();
	$("#editSite .modal-title").html("修改巡查点");
	$("#editSite").modal();
}

//删除计划中的点位
function deleteInspectSite(_this) {
	var indexId = _this.planDetialID;
	var url = baseUrl + '/inspectPlanDetail/remove';
	layer.open({
		content : "是否确定删除？",
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
					"planDetailId" : indexId
				},
				url : url,
				dataType : "json",
				success : function(d) {
					if (d.success) {
						$('#inspectSiteTable').bootstrapTable("refresh");
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

//获取计划对应人员列表（已分配人员，为分配人员）
function setRole() {
	$("#setPlanRoleModal").modal();
	$('#select3').empty();
	$('#select4').empty();
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"UnitID" : unitID,
			"PlanID" : inspectPlanId
		},
		url : baseUrl + "/maintenanceUser/getUserRoleList",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				planUserList = d.obj;
				var hasRoles = planUserList.hasRole;
				if (hasRoles) {
					for (var i = 0; i < hasRoles.length; i++) {
						var option = ' <option id="' + hasRoles[i].id + '">'
								+ hasRoles[i].name + '</option>'
						$('#select4').append(option);
					}
				}
				var noRoles = planUserList.noRole;
				if (noRoles) {
					for (var i = 0; i < noRoles.length; i++) {
						var option = ' <option id="' + noRoles[i].id + '">'
								+ noRoles[i].name + '</option>'
						$('#select3').append(option);
					}
				}
				//planUserID = planUserList.hasRole;
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		}
	});
}
//保存巡查人员
$(".btn-save_rolePlan").click(function() {
	var ids = "";
	var names = "";
	for (var i = 0; i < $("#select4 option").length; i++) {
		var id = $($("#select4 option").eq(i)).attr("id");
		var name = $($("#select4 option").eq(i)).text();
		names += name + ",";
		ids += id + ",";
	}
	if(ids==""&&names==""){
		layer.msg("请选择巡查人！")
		return;
	}
	planUserList = ids;
	planUserNameList = names;
	$("#setPlanRoleModal").modal("hide");
	$("#newAdd").modal();
	$("#DefaultUserName").val(names);
	$("#DefaultUserID").val(ids);

});

//修改某个点位巡查人员
function setRoles() {
	$("#setTaskRoleModal").modal("show");
	$('#select1').empty();
	$('#select2').empty();
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"id" : planDetailId,
			"UnitID":unitID
		},
		url : baseUrl + "/inspectPlanDetail/getUserList",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				taskUserList = d.obj;
				var hasRoles = taskUserList.hasRole;
				if (hasRoles) {
					for (var i = 0; i < hasRoles.length; i++) {
						var option = ' <option id="' + hasRoles[i].id + '">'
								+ hasRoles[i].name + '</option>'
						$('#select2').append(option);
					}
				}
				var noRoles = taskUserList.noRole;
				if (noRoles) {
					for (var i = 0; i < noRoles.length; i++) {
						var option = ' <option id="' + noRoles[i].id + '">'
								+ noRoles[i].name + '</option>'
						$('#select1').append(option);
					}
				}
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		}
	});
}
var ids = "";
var names = "";
//保存巡查人员
$(".btn-save_role").click(function() {
	for (var i = 0; i < $("#select2 option").length; i++) {
		var id = $($("#select2 option").eq(i)).attr("id");
		var name = $($("#select2 option").eq(i)).text();
		names += name + ",";
		ids += id + ",";
	}
	if(ids == ""||names==""){
		layer.msg("请选择巡查人！");
		return;
	}
	$("#setTaskRoleModal").modal("hide");
	$("#editSite .modal-title").html("修改巡查点");
	$("#editSite").modal("hide");
	$("#DefaultUserNames").val(names);
	$("#DefaultUserIDs").val(ids);
	siteEdit();

});

function siteEdit(){
	if(ids == ""||names==""){
		layer.msg("请选择巡查人！");
	}
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
			"ID" : planDetailId ,
			"taskUserID" : ids,
			"taskUserName" : names
		},
		url : baseUrl + "/inspectPlanDetail/update",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				$('#inspectSiteTable').bootstrapTable("refresh");
				$("#editSite").modal("hide");
				layer.msg("修改成功！");
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
}

//批量生成任务
$("#checkAll").click(function() {
	if (this.checked) {
		$("[name=check]:checkbox").prop("checked", true);
	} else {
		$("[name=check]:checkbox").prop("checked", false);
	}
});
$("input[name=check]:checkbox").click(function() {
	allchk();
});
function allchk() {
	var chknum = $("input[name=check]").size();//选项总个数
	var chk = 0;
	$("input[name=check]:checkbox").each(function() {
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
//批量生成任务按钮请求
function createAllTask() {
	//var all = $("#inspectSiteTable").bootstrapTable('getData');
    var checked = $("#inspectSiteTable").bootstrapTable('getSelections');
    var isPlaned = 1;
    if (checked.length > 0) {
		var valArr = new Array;
		$(checked).each(function(i, item) {
			valArr[i] = item.siteId;
            if(item.isPlaned == '未关联'){
                isPlaned = 0;
            }

		});
        if(isPlaned == 0){
            layer.msg("请取消未关联选项,请选择已关联选择!");
            return;
        }

        var vals = valArr.join(',');
	} else {
		layer.msg("请选择一条记录！");
		return;
	}
	var url = baseUrl + "/inspectPlanDetail/createAllTask";
	var isAsync = true,lid = 0;
    if (isAsync)
        lid = layer.msg('正在加载...', { icon: 16, shade: [0.1, '#fff'], time: 90000 })
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"siteID" : vals,//
			"planID":inspectPlanId
		},
		url : url,
		dataType : "json",
		success : function(d) {
			if (d.success) {
				$('#inspectSiteTable').bootstrapTable("refresh");
				layer.msg("生成任务成功！");
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		},complete: function () {
            if (isAsync){
                layer.close(lid);
            }
        }
	});
}

//生成任务
function createTask(_this) {
	var isAsync = true,lid = 0;
	//console.log(_this);
    //console.log(_this.startTime);
    //console.log(_this.endTime);

    if (isAsync)
        lid = layer.msg('正在加载...', { icon: 16, shade: [0.1, '#fff'], time: 90000 })
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"siteid" : _this.siteId,
			 "unitid" : _this.unitID,
			 "sitecode" : _this.siteCode,
			 "sitename" : _this.siteName,
			 "sitedesc" :  _this.siteDesc,//为空
			 "taskstart" :_this.taskstart,
			 "taskend" :_this.taskend,
			 "nfccode" :_this.nfccode,//为空
			 "plandetialid" : _this.planDetialID,
			 "siteclassid" : _this.siteClassID,//这个找不到对应的
			 "inspectuserid" : _this.executorID,//为空
			 "orderindex" : _this.inspectcount,
			 "supervisorID":_this.supervisorID//为空
		},
		url : baseUrl + "/inspectPlanDetail/createTask",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				layer.msg("成功！");
				//$("#inspectSiteTable").bootstrapTable('destroy');
				getPlanSite(inspectPlanId);
				$('#inspectSiteTable').bootstrapTable("refresh");
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		},
        complete: function () {
            if (isAsync){
                layer.close(lid);
            }
        }
	});

}
//条件查询
function siteSearch() {
    Imports();
}
function siteClear() {
	$("#SiteNameSite").val("");
	$("#SiteCodeSite").val("");
    Imports();
}
