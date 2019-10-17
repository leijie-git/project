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
        
        $("#newCode-codename").val("");
        $("#newCode-codevalue").val("");
        $("#newCode-sortorder").val("");
        $("#newCode-memo").val("");
        $("#newCode-codegroupid").val("");
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
   /* $("#continue").click(function(){
    	var codename = $("#newCode-codename").val();
        var codevalue = $("#newCode-codevalue").val();
        var sortorder = $("#newCode-sortorder").val();
        var memo = $("#newCode-memo").val();
        var codegroupid = $("#newCode-codegroupid").val();
        
        
        if (codename == "") {
        	layer.msg("名称不能为空！");
        	return;
        }
        
        var url = baseUrl + "/code/add";
        var data = {
        		codename:codename,
        		codevalue:codevalue,
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
                    $("#newCode-codename").val("");
                    $("#newCode-codevalue").val("");
                    $("#newCode-sortorder").val("");
                    $("#newCode-memo").val("");
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
        var codename = $("#newCode-codename").val();
        var codevalue = $("#newCode-codevalue").val();
        var sortorder = $("#newCode-sortorder").val();
        var memo = $("#newCode-memo").val();
        var codegroupid = $("#newCode-codegroupid").val();
    	
    	

        if (codename == "") {
        	layer.msg("名称不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/code/add";
            var data = {
            		codename:codename,
            		codevalue:codevalue,
            		sortorder:sortorder,
            		codegroupid:codegroupid,
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
            var url = baseUrl + "/code/update";
            var data = {
            		codeid:id,
            		codename:codename,
            		codevalue:codevalue,
            		sortorder:sortorder,
            		codegroupid:codegroupid,
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
    $("#codeName").val('');
    $("#codeValue").val('');
    $("#codeGroupKey").val('');
}
// 编辑
function editBaseInfo(_this) {
	
    
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑");
    $("#newEmploy .btnSure").show();
    //$("#continue").hide();
    var id = _this.codeid;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    
    $("#newCode-codename").val(_this.codename);
    $("#newCode-codevalue").val(_this.codevalue);
    $("#newCode-sortorder").val(_this.sortorder);
    $("#newCode-memo").val(_this.memo);
    $("#newCode-codegroupid").val(_this.codegroupid);
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newCodeView-codename").val(_this.codename);
    $("#newCodeView-codevalue").val(_this.codevalue);
    $("#newCodeView-sortorder").val(_this.sortorder);
    $("#newCodeView-memo").val(_this.memo);
    $("#newCodeView-codegroupid").val(_this.codegroupid);    
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
    var select = _this.codeid;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/code/delete",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                	codeid: select
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
        url: baseUrl + '/code/list',         //请求后台的URL（*）
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
                codename: $("#codeName").val(),
                codevalue:$("#codeValue").val(),
                codegroupkey:$("#codeGroupKey").val()
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
                field: 'codename',
                title: '名称'
            },
            {
            	field: 'codevalue',
            	title: '值'
            },
            {
            	field: 'memo',
            	title: '说明'
            },
            {
            	field: 'codegroupkey',
            	title: '代码组编码'
            },
            {
            	field: 'codegroupname',
            	title: '代码组中文名'
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
function initSelect(){
	$("#newCodeView-codegroupid").empty();
	$.ajax({
        url: baseUrl + "/codeGroup/getCodeGroupSelectList",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
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



