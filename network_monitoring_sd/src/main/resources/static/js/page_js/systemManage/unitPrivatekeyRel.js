$(function () {
	//主页面查询
	mainSearch();
	initSelect();
    $(".btnReset").click(function () {
        emptySearch();
        $("#userTable").bootstrapTable("refresh");
        $("#searchContent").val('');
    });
    //查询按钮查询
    $(".btnSearch").click(function () {
        $("#userTable").bootstrapTable('destroy');
        mainSearch();
    });


    $(".newAdd").click(function () {
    	$("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增");
        $("#newEmploy .btnSure").show();
        //$("#continue").show();
        
        $("#privateKey").val("");
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    $("#continue").click(function(){
    	var privateKey = $("#privateKey").val();
        
        if (privateKey == "") {
        	layer.msg("私钥不能为空！");
        	return;
        }
        
        var url = baseUrl + "/unitPrivatekeyRel/addUnitPrivatekeyRel";
        var data = {
        		privateKey:privateKey
        };
        $.ajax({
            url: url,
            type: "post",
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg("添加成功！");
                    $("#userTable").bootstrapTable("refresh");
                    $("#privateKey").val("");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
        
    });
    // 新增
    $(".btnSure").click(function () {
        var id = $("#indexId").val();
        var privateKey = $("#privateKey").val();
        
        if (privateKey == "") {
        	layer.msg("私钥不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        var url = baseUrl + "/unitPrivatekeyRel/addUnitPrivatekeyRel";
        var data = {
        		privateKey:privateKey
        };
        
        $.ajax({
            url: url,
            type: "post",
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg("添加成功！");
                    $("#userTable").bootstrapTable("refresh");
                    $("#newEmploy").modal("hide");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
    });
    
    
});


$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    $("#userTable").bootstrapTable('destroy');
    mainSearch();
});
$("#menuType").change(function () {
    emptySearch();
    var id = $(this).val();
    if (id == "") {
        $("#userTable").bootstrapTable('refresh');
    }
    if(!$('#searchContent').hasClass('showInput')){
        $("#commonInput").html('<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
    }

});


function emptySearch() {
    $("#Search-privateKey").val('');
}

function deletePrivateKey(_this) {
    var select = _this.privateKey;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/unitPrivatekeyRel/deleteUnitPrivatekeyRel",
                type: "post",
                data: {
                	privateKey: select
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.msg("删除成功！");
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                        layer.msg("删除失败！");
                    }
                },
                error: function (data) {

                }
            });
        }
        , cancel: function () {
            //右上角关闭回调
        }
    });
}


//主页面查询
function mainSearch() {
    $('#userTable').bootstrapTable({
        url: baseUrl + '/unitPrivatekeyRel/privatekeyList',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
            	pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                privateKey: $("#Search-privateKey").val()
            };
            $("#pageSize").val(this.pageSize);
            $("#pageNumber").val(this.pageNumber);
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
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "codeid",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [{
            field: 'selectItem',
            checkbox: true
        },
            {
                field: 'privateKey',
                title: '私钥',
                width: '50%'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                    str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main bindUnit"><i class="fa fa-pencil"></i>&nbsp;绑定单位</button>'
                    return str;
                },
                events: {
                    'click .del': function (e, value, row, index) {
                        deletePrivateKey(row);
                    },
                    'click .bindUnit': function (e, value, row, index) {
                    	bindUnit(row.privateKey);
                    }
                }
            }
        ]
    });
}
function initSelect(){
	$("#newCodeView-codegroupid").empty();
	$.ajax({
        url: baseUrl + "/codeGroup/getCodeGroupSelectList",
        type: "post",
        dataType: "json",
        success: function (data) {
        	if(data.success){
				for(var i=0;i<data.obj.length;i++){
					$("#newCode-codegroupid").append('<option value="'+data.obj[i].codegroupid+'">'+data.obj[i].codegroupname+'</option>');
					$("#newCodeView-codegroupid").append('<option value="'+data.obj[i].codegroupid+'">'+data.obj[i].codegroupname+'</option>');
            	}
			}
        }
    })
}

//////////////////////////////////绑定单位
function bindUnit(_privateKey){
	$("#linkApp").modal("show");
	$("#privateKey-hidden").val(_privateKey);
	$('#associatedTable').bootstrapTable("destroy");
	$('#unassociatedTable').bootstrapTable("destroy");
	$("#appName1").val("");
	$("#appName2").val("");
	AppListSearch1();
	AppListSearch2();
}
function AppListSearch1() {
	$('#associatedTable').bootstrapTable({
		url: baseUrl + '/unitPrivatekeyRel/getAssociatedUnit',         //请求后台的URL（*）
		method: 'get',                      //请求方式（*）
		contentType: 'application/x-www-form-urlencoded',
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true,                   //是否显示分页（*）
		sortable: false,                     //是否启用排序
		sortOrder: "asc",                   //排序方式
		queryParamsType: "undefined",   //排序方式
		queryParams: function queryParams(params) {   //设置查询参数
			var param = {
					pageNumber: this.pageNumber,
					pageSize: this.pageSize,
					unitName:$("#appName1").val(),
					privateKey:$("#privateKey-hidden").val()
			};
			$("#pageSize").val(this.pageSize);
			$("#pageNumber").val(this.pageNumber);
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
		minimumCountColumns: 2,             //最少允许的列数
		clickToSelect: true,                //是否启用点击选中行
		singleSelect: false,
		// height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId: "id",                     //每一行的唯一标识，一般为主键列
		//    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
		cardView: false,                    //是否显示详细视图
		detailView: false,                   //是否显示父子表
		columns: [{
			field: 'selectItem',
			checkbox: true
		},
		{
			field: 'unitName',
			title: '单位名称'
		}
		]
	});
}
function AppListSearch2() {
	$('#unassociatedTable').bootstrapTable({
		url: baseUrl + '/unitPrivatekeyRel/getUnassociatedUnit',         //请求后台的URL（*）
		method: 'get',                      //请求方式（*）
		contentType: 'application/x-www-form-urlencoded',
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true,                   //是否显示分页（*）
		sortable: false,                     //是否启用排序
		sortOrder: "asc",                   //排序方式
		queryParamsType: "undefined",   //排序方式
		queryParams: function queryParams(params) {   //设置查询参数
			var param = {
					pageNumber: this.pageNumber,
					pageSize: this.pageSize,
					unitName:$("#appName1").val(),
					privateKey:$("#privateKey-hidden").val()
			};
			$("#pageSize").val(this.pageSize);
			$("#pageNumber").val(this.pageNumber);
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
		minimumCountColumns: 2,             //最少允许的列数
		clickToSelect: true,                //是否启用点击选中行
		singleSelect: false,
		// height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId: "id",                     //每一行的唯一标识，一般为主键列
		//    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
		cardView: false,                    //是否显示详细视图
		detailView: false,                   //是否显示父子表
		columns: [{
			field: 'selectItem',
			checkbox: true
		},
		{
			field: 'unitName',
			title: '单位名称'
		}
		]
	});
}

//新增
$(".addAppBtn").click(function(){
	var rows = $('#unassociatedTable').bootstrapTable('getSelections');
	if(rows.length == 0){
		layer.msg("请勾选要关联的应用。")
		return;
	}
	var questionIds = "";
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		questionIds += row.unitId + ",";
	}
	 $.ajax({
	        async: true,
	        type: "post",
	        data: {
	        	privateKey:$("#privateKey-hidden").val(),
	        	unitIds:questionIds
	        	},
	        url: baseUrl+"/unitPrivatekeyRel/bindUnit",
	        dataType: "json",
	        success: function (d) {
	            if (d.success) {
	                layer.msg("操作成功！")
	                $('#associatedTable').bootstrapTable("refresh");
	                $('#unassociatedTable').bootstrapTable("refresh");
	            } else {
	                layer.msg(d.msg||"操作失败")
	            }
	        },
	        error: function (e) {

	        }
	    });
});
//移除
$(".delAppBtn").click(function(){
	var rows = $('#associatedTable').bootstrapTable('getSelections');
	if(rows.length == 0){
		layer.msg("请勾选要移除的应用。")
		return;
	}
	var questionIds = "";
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		questionIds += row.id + ",";
	}
	 $.ajax({
	        async: true,
	        type: "post",
	        data: {
	        	privateKey:$("#privateKey-hidden").val(),
	        	ids:questionIds
	        	},
	        url: baseUrl+"/unitPrivatekeyRel/unbindUnit",
	        dataType: "json",
	        success: function (d) {
	            if (d.success) {
	                layer.msg("操作成功！")
	                $('#associatedTable').bootstrapTable("refresh");
	                $('#unassociatedTable').bootstrapTable("refresh");
	            } else {
	                layer.msg(d.msg||"操作失败")
	            }
	        },
	        error: function (e) {

	        }
	    });
});
$(".btnSearchA1").click(function(){
	$('#associatedTable').bootstrapTable("destroy");
	AppListSearch1();
});
$(".btnSearchA2").click(function(){
	$('#unassociatedTable').bootstrapTable("destroy");
	AppListSearch2();
});


