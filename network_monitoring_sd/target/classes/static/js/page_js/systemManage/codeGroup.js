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


    $(".newAdd").click(function () {
    	$("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增代码组");
        $("#newEmploy .btnSure").show();
        //$("#continue").show();
        
        $("#newCodeGroup-codegroupkey").val("");
        $("#newCodeGroup-codegroupname").val("");
        $("#newCodeGroup-sortorder").val("");
        $("#newCodeGroup-memo").val("");
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    /*$("#continue").click(function(){
        var codegroupkey = $("#newCodeGroup-codegroupkey").val();
        var codegroupname = $("#newCodeGroup-codegroupname").val();
        var sortorder = $("#newCodeGroup-sortorder").val();
        var memo = $("#newCodeGroup-memo").val();
        
        
        if (codegroupkey == "") {
        	layer.msg("编码不能为空！");
        	return;
        }
        
        var url = baseUrl + "/codeGroup/add";
        var data = {
        		codegroupkey:codegroupkey,
        		codegroupname:codegroupname,
        		sortorder:sortorder,
        		memo:memo
        };
        $.ajax({
            url: url,
            type: "post",
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg(d.msg);
                    $("#userTable").bootstrapTable("refresh");
                    $("#newCodeGroup-codegroupkey").val("");
                    $("#newCodeGroup-codegroupname").val("");
                    $("#newCodeGroup-sortorder").val("");
                    $("#newCodeGroup-memo").val("");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
        
    });*/
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
        var id = $("#indexId").val();
        var codegroupkey = $("#newCodeGroup-codegroupkey").val();
        var codegroupname = $("#newCodeGroup-codegroupname").val();
        var sortorder = $("#newCodeGroup-sortorder").val();
        var memo = $("#newCodeGroup-memo").val();
    	
    	

        if (codegroupkey == "") {
        	layer.msg("编码不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/codeGroup/add";
            var data = {
            		codegroupkey:codegroupkey,
            		codegroupname:codegroupname,
            		sortorder:sortorder,
            		memo:memo
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
                        $("#newEmploy").modal("hide");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
        } else {
            var url = baseUrl + "/codeGroup/update";
            var data = {
            		codegroupid:id,
            		codegroupkey:codegroupkey,
            		codegroupname:codegroupname,
            		sortorder:sortorder,
            		memo:memo
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
    $("#codegroupname").val('');
    $("#codegroupkey").val('');
}
// 编辑
function editBaseInfo(_this) {
	
    
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑代码组");
    $("#newEmploy .btnSure").show();
    //$("#continue").hide();
    var id = _this.codegroupid;
    $("#indexId").val(id);
    $("#editId").val("1");
    $("#newCodeGroup-codegroupkey").val(_this.codegroupkey);
    $("#newCodeGroup-codegroupname").val(_this.codegroupname);
    $("#newCodeGroup-sortorder").val(_this.sortorder);
    $("#newCodeGroup-memo").val(_this.memo);
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newCodeGroupView-codegroupkey").val(_this.codegroupkey);
    $("#newCodeGroupView-codegroupname").val(_this.codegroupname);
    $("#newCodeGroupView-sortorder").val(_this.sortorder);
    $("#newCodeGroupView-memo").val(_this.memo);
    
//    var userHeader = _this.userHeader;
//    if(userHeader){
//    	$("#loadImgView").html(
//    			" <li style='position: relative;margin: 0px;border: none;'> " +
//    			"  <img src='" + userHeader + "' " +
//    			" style = 'width:100%;height:100%;'/>" +
//    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
//    			" </a>"+
//    			" </li>"
//    	);
//    }else{
//    	$("#loadImgView").html("<li class='modalImg'><span>暂无图片</span></li>");
//    }
}

function deleteBaseInfo(_this) {
    var select = _this.codegroupid;
    layer.open({
        content: '确认删除该条记录？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/codeGroup/delete",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                	codegroupid: select
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
        url: baseUrl + '/codeGroup/list',         //请求后台的URL（*）
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
                codegroupname: $("#codegroupname").val(),
                codegroupkey:$("#codegroupkey").val()
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
                field: 'codegroupkey',
                title: '编码'
            },
            {
            	field: 'codegroupname',
            	title: '中文名称'
            },
            {
            	field: 'memo',
            	title: '说明'
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
                    	editBaseInfo(row);
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




