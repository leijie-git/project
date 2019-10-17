var unitList;
$(function () {
	getUnitList();
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


    $(".newAdd").click(function () {
    	$("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增点位视频");
        $("#newEmploy .btnSure").show();
        
        $("#newPointVideo-name").val("");
        $("#newPointVideo-ip").val("");
        $("#newPointVideo-brand").val("");
        $("#newPointVideo-port").val("");
        $("#newPointVideo-userName").val("");
        $("#newPointVideo-password").val("");
        $("#newPointVideo-plugInType").val("");
        $("#newPointVideo-manufactor").val("");
        $("#newPointVideo-position").val("");
        $("#newPointVideo-poscode").val("");
        $("#newPointVideo-serialnumber").val("");
        showUnitList();
        $("#UnitID").siblings(".combo-arrow").hide();
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    $(".export").click(function(){
    	var htm = baseUrl+"/pointVideo/exportPointVideo?";
    	var name = $("#name").val();
    	var unitName = $("#unitName").val();
    	var ip = $("#ip").val();
    	var brand = $("#brand").val();
    	if(name != ''){
    		htm+='name='+name+'&';
    	}
    	if(unitName != ''){
    		htm+='unitName='+unitName+'&';
    	}
    	if(ip != ''){
    		htm+='ip='+ip+'&';
    	}
    	if(brand != ''){
    		htm+='brand='+brand;
    	}
		window.location.href=htm;
    });
    
    
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
        var id = $("#indexId").val();
        
    	var name = $("#newPointVideo-name").val();
        var ip = $("#newPointVideo-ip").val();
        var brand = $("#newPointVideo-brand").val();
        var port = $("#newPointVideo-port").val();
        var userName = $("#newPointVideo-userName").val();
        var password = $("#newPointVideo-password").val();
        var plugInType = $("#newPointVideo-plugInType").val();
        var manufactor = $("#newPointVideo-manufactor").val();
        var position = $("#newPointVideo-position").val();
        var poscode = $("#newPointVideo-poscode").val();
        var serialnumber = $("#newPointVideo-serialnumber").val();
        var UnitID =$("#UnitID").find("option:selected").val();
        
        if(UnitID ==""){
			layer.msg("所属单位不能为空！")
			return;
		}
        if (name == "") {
        	layer.msg("视频名称不能为空！");
        	return;
        }
        if (ip == "") {
        	layer.msg("ip不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/pointVideo/addPointVideo";
            var data = {
            		"unitId":UnitID,
            		name:name,
            		ip:ip,
            		brand:brand,
            		port:port,
            		username:userName,
            		password:password,
            		plugintype:plugInType,
            		manufactor:manufactor,
            		position:position,
            		poscode:poscode,
            		serialnumber:serialnumber
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
                        layer.msg("添加失败");
                    }
                },
                error: function (data) {

                }
            });
        } else {
            var url = baseUrl + "/pointVideo/updatePointVideo";
            var data = {
            		id:id,
            		"unitId":UnitID,
            		name:name,
            		ip:ip,
            		brand:brand,
            		port:port,
            		username:userName,
            		password:password,
            		plugintype:plugInType,
            		manufactor:manufactor,
            		position:position,
            		poscode:poscode,
            		serialnumber:serialnumber
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
                        layer.msg("更新成功");
                        $("#userTable").bootstrapTable("refresh");
                        $("#newEmploy").modal("hide");
                    } else {
                        layer.msg("更新失败");
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
    $("#ip").val('');
    $("#brand").val('');
    $("#unitName").val('');
}
// 编辑
function editBaseInfo(_this) {
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑点位视频");
    $("#newEmploy .btnSure").show();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    showUnitList(_this.unitId);
	$("#UnitID").siblings(".combo-arrow").show();
    
    $("#newPointVideo-name").val(_this.name);
    $("#newPointVideo-ip").val(_this.ip);
    $("#newPointVideo-brand").val(_this.brand);
    $("#newPointVideo-port").val(_this.port);
    $("#newPointVideo-userName").val(_this.username);
    $("#newPointVideo-password").val(_this.password);
    $("#newPointVideo-plugInType").val(_this.plugintype);
    $("#newPointVideo-manufactor").val(_this.manufactor);
    $("#newPointVideo-position").val(_this.position);
    $("#newPointVideo-poscode").val(_this.poscode);
    $("#newPointVideo-serialnumber").val(_this.serialnumber);
    
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	showUnitList(_this.unitId);
	$("#UnitIDView").val(_this.unitId);
	$("#newPointVideoView-name").val(_this.name);
    $("#newPointVideoView-ip").val(_this.ip);
    $("#newPointVideoView-brand").val(_this.brand);
    $("#newPointVideoView-port").val(_this.port);
    $("#newPointVideoView-userName").val(_this.username);
    $("#newPointVideoView-password").val(_this.password);
    $("#newPointVideoView-plugInType").val(_this.plugintype);
    $("#newPointVideoView-manufactor").val(_this.manufactor);
    $("#newPointVideoView-position").val(_this.position);
    $("#newPointVideoView-poscode").val(_this.poscode);
    $("#newPointVideoView-serialnumber").val(_this.serialnumber);
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中视频？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/pointVideo/deletePointVideo",
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
                        layer.msg(data.msg||"删除成功");
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                        layer.msg(data.msg||"删除失败");
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
        url: baseUrl + '/pointVideo/getPointVideoList',         //请求后台的URL（*）
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
                ip: $("#ip").val(),
                brand: $("#brand").val(),
                unitName: $("#unitName").val()
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
                field: 'unitName',
                title: '单位名称',
                
            },
            {
            	field: 'name',
            	title: '视频名称'
            },
            {
            	field: 'ip',
            	title: 'IP地址'
            },
            {
            	field: 'brand',
            	title: '视频品牌'
            },
            {
            	field: 'port',
            	title: '端口号'
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
function getUnitList(){
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/baseInfo/getArrayList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	unitList = d.obj;
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}

function showUnitList(unitID){
	var a = '<option value="%unitID%" >%unitName%</option>';
	var option;
	var wrap='<option value="">--搜索--</option>';
		for(var i = 0;i<unitList.length;i++){
			if(unitID && unitID==unitList[i].id){
				option = '<option selected value="'+unitList[i].id+'" >'+unitList[i].unitname+'</option>';
			}else{
				option = a.replace("%unitID%",unitList[i].id);
				option = option.replace("%unitName%",unitList[i].unitname);
			}
			wrap+=option;
		}
	$("#UnitID").html(wrap);
	$("#UnitID").comboSelect();
	$("#UnitIDView").html(wrap);
	$("#UnitIDView").comboSelect();
	$("#UnitIDView").siblings("input").attr('disabled','disabled');
	$("#UnitIDView").siblings(".combo-arrow").hide();
}