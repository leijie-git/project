$(function () {
	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$("#newEmploy").css("overflow-y", "auto");
	//日期选择控件
	$("#newNotify-sendDate").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD hh:mm:ss",
        zIndex: 3000
    })
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


    $(".newAdd").click(function () {
    	$("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增");
        $("#newEmploy .btnSure").show();
        
        
        $("#preId").val("");
        $("#newNotify-title").val("");
        $("#newNotify-content").val("");
        $("#newNotify-sendDate").val("");
        Show();
        
        $("#preIdWrapRole > span").html("请点击选择接收人");
        //查询权限
        //initDataSource(1);
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
        var id = $("#indexId").val();
        
        var title = $("#newNotify-title").val();
        var content = $("#newNotify-content").val();
        var sendDate = $("#newNotify-sendDate").val();

        if (preId == "" || preId == null) {
        	layer.msg("请选择接收人");
        	return;
        }
        
        if(title == ""){
        	layer.msg("请输入主题");
        	return;
        }
        if(content == ""){
        	layer.msg("请输入内容");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
        	/*var checked = $("#receiverTable").bootstrapTable('getData');
        	
	        var valArr = new Array;
	        $(checked).each(function (i, item) {
	            valArr[i] = item.id;
	        });
	        var vals = valArr.join(',');
	        $("#preId").val(vals);*/
		        
	        
		    var receiver = $("#preId").val();
            var url = baseUrl + "/notify/addNotify";
            var data = {
            		title:title,
            		content:content,
            		sendDate:sendDate,
            		receiver:receiver
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
                        layer.msg("添加成功");
                        $("#userTable").bootstrapTable("refresh");
                        $("#newEmploy").modal("hide");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
        } else {
            var url = baseUrl + "/notify/updateNotify";
            var data = {
            		id:id,
            		title:title,
            		content:content,
            		sendDate:sendDate
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
                        layer.msg("修改成功");
                        $("#userTable").bootstrapTable("refresh");
                        $("#newEmploy").modal("hide");
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
    $("#sender").val('');
    $("#title").val('');
    $("#content").val('');
}
// 编辑
function editBaseInfo(_this) {
	
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑");
    $("#newEmploy .btnSure").show();
    $("#continue").hide();
    $("#preId").val();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    getNotifyRelUser(_this.id);
    $("#receiver").hide();
    $("#newNotify-title").val(_this.title);
    $("#newNotify-content").val(_this.content);
    $("#newNotify-sendDate").val(_this.sendDate);
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newNotifyView-title").val(_this.title);
    $("#newNotifyView-content").val(_this.content);
    $("#newNotifyView-sendDate").val(_this.sendDate);
    $("#newNotifyView-receiver").val(_this.receiver);
    getNotifyRelUserView(_this.id);
    
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/notify/deleteNotify",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                	notifyId: select
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                    	layer.msg("删除成功");
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                    	layer.msg("删除成功");
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

function deleteUser(_this){
	var notifyId = $("#indexId").val();;
	$.ajax({
        url: baseUrl + "/notify/deleteNotifyRelUser",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
        	notifyId: notifyId,
        	userId: _this.id
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
            	layer.msg("删除成功");
                getNotifyRelUser($("#indexId").val());
            } else {
            	layer.msg("删除成功");
            }
        },
        error: function (data) {

        }
    });
}


/*//资源管理（上级菜单）
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
        url: baseUrl + "/notify/getAllUserSelect",
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
                name: "username"
            },
            simpleData: {
                enable: true,
                idKey: "id",
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
*/




//展示
function Show(){
	$('#receiverTable').bootstrapTable("destroy");
	$('#receiverTable').bootstrapTable({
        url: baseUrl + '/notify/getNotifyRelUser',         //请求后台的URL（*）
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
                userIds: $("#preId").val()
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
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
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
            	 field: 'account',
                 title: '用户名'
             },
             {
                 field: 'username',
                 title: '姓名'
             },
             {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                    return str;
                },
                events: {
                    'click .del': function (e, value, row, index) {
                    	var arr = $("#preId").val().split(",");
                    	for(var i = 0;i<arr.length;i++){
                    		if(arr[i] == row.id){
                    			var index = arr.indexOf(row.id);
                    			if (index > -1) {
								    arr.splice(index, 1);
								}
                    			
                    		}
                    	}
				        var vals = arr.join(',');
				        $("#preId").val(vals);
				        Show();
                    }
                }
            }
        ]
    });
}

//导入
function Import(){
	$('#receiverTableImport').bootstrapTable("destroy");
	$("#addSite .modal-title").html("新增接收人");
    $("#addSite").modal();
    
	$('#receiverTableImport').bootstrapTable("destroy");
	$('#receiverTableImport').bootstrapTable({
        url: baseUrl + '/notify/getAllUserDisSelect',         //请求后台的URL（*）
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
                id: $("#indexId").val(),
                userIds: $("#preId").val()
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
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
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
             },
             {
            	 field: 'account',
                 title: '用户名'
             },
             {
                 field: 'username',
                 title: '姓名'
             }
        ]
    });
}


$(".SiteInto").click(function () {
	var editId = $("#editId").val();
	if(editId == 0){
		var checked = $("#receiverTableImport").bootstrapTable('getSelections');
		if (checked.length > 0) {
			var valArr = new Array;
			$(checked).each(function (i, item) {
				valArr[i] = item.id;
			});
			var vals = valArr.join(',');
			var preId = $("#preId").val();
			if(preId != null && preId != ''){
				$("#preId").val(preId+","+vals);
			}else{
				$("#preId").val(vals);
			}
			Show();
		} else {
			layer.msg("请选择一条记录！");
			return;
		}
	}else{
		var checked = $("#receiverTableImport").bootstrapTable('getSelections');
		if (checked.length > 0) {
			var valArr = new Array;
			$(checked).each(function (i, item) {
				valArr[i] = item.id;
			});
			var vals = valArr.join(',');
			
			var data = {
            		id:$("#indexId").val(),
            		receiver:vals
            };
			$.ajax({
                url: baseUrl + '/notify/addNotifyRelUser',
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: data,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        layer.msg("添加成功");
                        getNotifyRelUser($("#indexId").val());
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
			Show();
		} else {
			layer.msg("请选择一条记录！");
			return;
		}
	}
    //getNotifyRelUser($("#indexId").val());
});

//接收人回显
function getNotifyRelUser(id){
	$('.receiverTable').bootstrapTable("destroy");
	$('.receiverTable').bootstrapTable({
        url: baseUrl + '/notify/getNotifyRelUser',         //请求后台的URL（*）
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
                id: id
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
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
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
            	 field: 'account',
                 title: '用户名'
             },
             {
                 field: 'username',
                 title: '姓名'
             },
             {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                    return str;
                },
                events: {
                    'click .del': function (e, value, row, index) {
                        deleteUser(row);
                    }
                }
            }
        ]
    });
	
}

//接收人查看回显
function getNotifyRelUserView(id){
	$('.receiverTable').bootstrapTable("destroy");
	$('.receiverTable').bootstrapTable({
        url: baseUrl + '/notify/getNotifyRelUser',         //请求后台的URL（*）
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
                id: id
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
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
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
            	 field: 'account',
                 title: '用户名'
             },
             {
                 field: 'username',
                 title: '姓名'
             }
        ]
    });
	
}


//主页面查询
function mainSearch() {
    $('#userTable').bootstrapTable({
        url: baseUrl + '/notify/pageList',         //请求后台的URL（*）
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
                title: $("#title").val(),
                sender:$("#sender").val(),
                content:$("#content").val()
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
                field: 'sender',
                title: '发送人'
            },
            {
            	field: 'title',
            	title: '标题',
            	 formatter: function (value, row, index) {
 	 				if (value == null) {
 	 					return "";
 	 				}
 	 				return '<p title="'+value+'" style="width: 150px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
      			}
            },
            {
            	field: 'content',
            	title: '内容',
	        	 formatter: function (value, row, index) {
	 				if (value == null) {
	 					return "";
	 				}
	 				return '<p title="'+value+'" style="width: 200px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
     			}
            },
            {
            	field: 'sendDate',
            	title: '发送时间'
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
                    	editBaseInfo(row,index);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteBaseInfo(row);
                    },
                    'click .view': function (e, value, row, index) {
                        viewBaseInfo(row);
                    }
                }
            }
        ]
    });
}
