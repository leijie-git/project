$(function () {
	//主页面查询
	mainSearch();
	
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
    //批量删除
    $(".btnDeleteMany").click(function () {
        deleteEmploy();
    });
    
    //日期选择控件
    $("#startTimeNew").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
    
    //日期选择控件
    $("#newUserExperience-entryDate").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
    
    //日期选择控件
    $("#newUserExperience-quitDate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })

    $(".newAdd").click(function () {
    	$("#newUserInfo").modal("show");
        //$(".password").show();
        $("#newUserInfo .modal-title").html("新增行业管理人员");
        $("#newUserInfo .btnSure").show();
//        $("#continue").show();
        
        $("#nameNew").val("");
        $("#idCardNew").val("");
        $("#cardTypeNew").val("");
        $("#cardNumberNew").val("");
        $("#startTimeNew").val("");
        $("#certificateNew").val("");
        $("#phoneNew").val("");
        $("#certificateNew").val("");
        $("#sexNew1").prop("checked","checked");
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
        var id = $("#indexId").val();

        var name = $("#nameNew").val();
        var idCard = $("#idCardNew").val();
        var cardType = $("#cardTypeNew").val();
        var cardNumber = $("#cardNumberNew").val();
        var startTime = $("#startTimeNew").val();
        var phone = $("#phoneNew").val();
        var certificate = $("#certificateNew").val();
        var sex = $("input[name='sex']:checked").val();
        
        if (name == "") {
        	layer.msg("姓名不能为空！");
        	return;
        }
        if (idCard == "" ) {
        	layer.msg("身份证号不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/userExperience/add";
            var data = {
            		name:name,
            		idCard:idCard,
            		cardType:cardType,
            		cardNumber:cardNumber,
            		startTime:startTime,
            		certificate:certificate,
            		phone:phone,
            		sex:sex
            };
            $.ajax({
                url: url,
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: data,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        layer.msg(d.msg);
                        $("#userTable").bootstrapTable("refresh");
                        $("#newUserInfo").modal("hide");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
        } else {
            var url = baseUrl + "/userExperience/update";
            var data = {
            		id:id,
            		name:name,
            		idCard:idCard,
            		cardType:cardType,
            		cardNumber:cardNumber,
            		startTime:startTime,
            		certificate:certificate,
            		phone:phone,
            		sex:sex
            };
            $.ajax({
                url: url,
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: data,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        layer.msg(d.msg);
                        $("#userTable").bootstrapTable("refresh");
                        $("#newUserInfo").modal("hide");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
        }
    });
    
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
    $("#name").val('');
    $("#phone").val('');
    $("#idCard").val('');
    $("#cardNumber").val('');
}
// 编辑
function editBaseInfo(_this) {
	
	$("#newUserInfo").modal("show");
    $("#newUserInfo .modal-title").html("编辑行业管理人员");
    $("#newUserInfo .btnSure").show();
//    $("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    $("#nameNew").val(_this.name);
    $("#idCardNew").val(_this.idCard);
    $("#cardTypeNew").val(_this.cardType);
    $("#cardNumberNew").val(_this.cardNumber);
    $("#startTimeNew").val(_this.startTime);
    $("#certificateNew").val(_this.certificate);
    $("#phoneNew").val(_this.phone);
    $("#certificateNew").val(_this.certificate);
    var sex = _this.sex;
    if(sex == 1){
    	$("#sexNew1").prop("checked","checked");
    	$("#sexNew0").removeAttr("checked");
    }else{
    	$("#sexNew0").prop("checked","checked");
    	$("#sexNew1").removeAttr("checked");
    }
    
}
//查看
function viewBaseInfo(_this) {
	$("#newUserInfoView").modal("show");
	$("#nameView").val(_this.name);
    $("#idCardView").val(_this.idCard);
    $("#cardTypeView").val(_this.cardType);
    $("#cardNumberView").val(_this.cardNumber);
    $("#startTimeView").val(_this.startTime);
    $("#certificateView").val(_this.certificate);
    $("#phoneView").val(_this.phone);
    $("#certificateView").val(_this.certificate);
    var sex = _this.sex;
    if(sex == 1){
    	$("#sexView1").prop("checked","checked");
    	$("#sexView0").removeAttr("checked");
    }else{
    	$("#sexView0").prop("checked","checked");
    	$("#sexView1").removeAttr("checked");
    }
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/userExperience/delete",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                type: "post",
                data: {
                    id: select
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.msg(data.msg);
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                        layer.msg(data.msg);
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
        url: baseUrl + '/userExperience/pageList',         //请求后台的URL（*）
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
                name: $("#name").val(),
                phone: $("#phone").val(),
                idCard: $("#idCard").val(),
                cardNumber : $("#cardNumber").val()
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
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [{
            field: 'selectItem',
            checkbox: true
        },
            {
                field: 'name',
                title: '姓名'
            },
            {
            	field: 'phone',
            	title: '电话'
            },
            {
            	field: 'idCard',
            	title: '身份证号'
            },
            {
            	field: 'cardType',
            	title: '证书类别'
            },
            {
            	field: 'cardNumber',
            	title: '证书编号'
            },
            {
            	field: 'certificate',
            	title: '上岗证号'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
        			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
    				str += '<button type="button" class="btn btn-new btn-xs cBtn-main experience"><i class="fa fa-pencil"></i>&nbsp;工作经历</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                    	editBaseInfo(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteBaseInfo(row);
                    },
                    'click .view': function (e, value, row, index) {
                        viewBaseInfo(row);
                    },
                    'click .experience': function (e, value, row, index) {
                    	experience(row.id);
                    }
                }
            }
        ]
    });
}

function experience(userId){
	
	$("#indexId").val(userId);
	$("#userExperiece").modal("show");
	$('#experieceTable').bootstrapTable("destroy");
	getUserExperieces(userId);
}

//主页面查询
function getUserExperieces(userId) {
    $('#experieceTable').bootstrapTable({
        url: baseUrl + '/userExperience/getExperienceList',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
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
                userId: userId,
            };
//            $("#pageSize").val(this.pageSize);
//            $("#pageNumber").val(this.pageNumber);
            return param;
        },
        dataField: 'obj',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100, 200, 500],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        searchTimeOut: 1000,
        //  	: true,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'companyName',
                title: '公司名称'
            },
            {
            	field: 'companyAddr',
            	title: '公司地址'
            },
            {
            	field: 'job',
            	title: '职务'
            },
            {
            	field: 'remark',
            	title: '备注'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                    	editExperiece(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteExperiece(row);
                    }
                }
            }
        ]
    });
}
function editExperiece(_this){
	// 编辑
    var id = _this.id;
    $("#indexId1").val(id);
    
    $("#newUserExperience-companyName").val(_this.companyName);
    $("#newUserExperience-companyAddr").val(_this.companyAddr);
    $("#newUserExperience-job").val(_this.job);
    $("#newUserExperience-entryDate").val(_this.entryDate);
    $("#newUserExperience-quitDate").val(_this.quitDate);
    $("#newUserExperience-remark").val(_this.remark);
}

function deleteExperiece(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/userExperience/deleteUserExperience",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                    id: select
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.msg(data.msg);
                        $("#experieceTable").bootstrapTable('refresh');
                    } else {
                        layer.msg(data.msg);
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
//新增或编辑
function saveExperiece() {
    var id = $("#indexId1").val();
    var userId = $("#indexId").val();
    var companyName = $("#newUserExperience-companyName").val();
    var companyAddr = $("#newUserExperience-companyAddr").val();
    var job = $("#newUserExperience-job").val();
    var entryDate = $("#newUserExperience-entryDate").val();
    var quitDate = $("#newUserExperience-quitDate").val();
    var remark = $("#newUserExperience-remark").val();
	
	

    if (companyName == "") {
    	layer.msg("公司名称不能为空！");
    	return;
    }
    if (id == "") {
        var url = baseUrl + "/userExperience/addUserExperience";
        var data = {
        		companyName:companyName,
        		companyAddr:companyAddr,
        		job:job,
        		entryDate:entryDate,
        		quitDate:quitDate,
        		remark:remark,
        		userId:userId
        };
        $.ajax({
            url: url,
            type: "post",
            beforeSend: function(request){
                request.setRequestHeader("Authorization","Bearer "+login.token);
            },
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg(d.msg);
                    $("#experieceTable").bootstrapTable("refresh");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
    } else {
        var url = baseUrl + "/userExperience/updateUserExperience";
        var data = {
        		id:id,
        		companyName:companyName,
        		companyAddr:companyAddr,
        		job:job,
        		entryDate:entryDate,
        		quitDate:quitDate,
        		remark:remark,
        		// card:card,
        		userId:userId
        };
        $.ajax({
            url: url,
            type: "post",
            beforeSend: function(request){
                request.setRequestHeader("Authorization","Bearer "+login.token);
            },
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg(d.msg);
                    $("#experieceTable").bootstrapTable("refresh");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
    }
    clear();
}

function clear() {
    
    $("#newUserExperience-companyName").val("");
    $("#newUserExperience-companyAddr").val("");
    $("#newUserExperience-job").val("");
    $("#newUserExperience-entryDate").val("");
    $("#newUserExperience-quitDate").val("");
    $("#newUserExperience-remark").val("");
    $("#newUserExperience-card").val("");
    
    $("#indexId1").val("");
}