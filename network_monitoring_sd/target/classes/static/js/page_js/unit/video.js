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
var videoId;
var UnitClick = 0;
var BuildClick = 0;
var BuildAreaClick = 0;
var unitList;
var buildList;
var buildAreaList;
var maintenanceUnitList;    //维保单位
$(function () {
    //搜索控制
    $(".jy_mainTile").hide();
    getUnitList();
    getMaintenanceUnitList();
    //主页面查询
    mainSearch();
});
//普通搜索
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    $("#videoTable").bootstrapTable('destroy');
    mainSearch();
});
function emptySearchMsg(){
	$("#name").val("");
    $("#port").val("");
    $("#ip").val("");
    $("#userName").val("");
    $("#searchContent").val("");
    $("#unitName").val("");
}
$("#menuType").change(function () {
	emptySearchMsg();
});
//高级搜索
$(".btnSearch").click(function () {
    $("#videoTable").bootstrapTable('destroy');
    mainSearch();
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

//查询条件查询
$(".btnSearch").click(function () {
    $("#videoTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#videoTable").bootstrapTable('destroy');
    mainSearch();
});
$(".btnNew").click(function () {
	emptyBuildAreaList();
	emptyBuildList();
	emptySearch();
	showUnitList();
    $("#newAdd .modal-title").html("新增视频");
    $("#UnitID").siblings("input").attr('disabled','disabled');
    $("#UnitID").siblings(".combo-arrow").hide();
    $("input[name=indexId][type=hidden]").val("");
    $("#VideoName").val("");
    $("#edit").val('0');
    $("#preId").val('');

});
//新增或编辑角色
$(".btnSure").click(function () {
	var VideoIDType = $("#VideoIDType").find("option:selected").val();
	var UnitID =$("#UnitID").find("option:selected").val();
	var buildId =$("#BuildId").find("option:selected").val();
	var buildAreaId =$("#BuildAreaId").find("option:selected").val();
	var videoName = $("#VideoName").val();
    var ip = $("#IP").val();
    var port = $("#Port").val();
    var userName = $("#UserName").val();
    var password = $("#Password").val();
    var videoType = $("#VideoType").find("option:selected").val();
    var manufactor = $("#Manufactor").val();
    var brand = $("#Brand").val();
    var position = $("#Position").val();
    var posCode = $("#PosCode").val();
    var serialnumber = $("#serialnumber").val();
    if(VideoIDType == ""){
    	layer.msg("视频类型不能为空！")
		return;
	}else if(VideoIDType == "1"){
		if(UnitID ==""){
			layer.msg("所属单位不能为空！")
			return;
		}else if (videoName == "") {
	        layer.msg("视频名称不能为空！");
	        return;
	    }else if (ip == "") {
	        layer.msg("ip不能为空！");
	        return;
	    }
//	        else if(port == ""){
//	    	layer.msg("端口号不能为空！");
//	        return;
//	    } else if(userName == ""){
//	    	layer.msg("用户名不能为空！");
//	        return;
//	    } else if(password == ""){
//	    	layer.msg("密码不能为空！");
//	        return;
//	    }
	}else if(VideoIDType == "2"){
		if(UnitID ==""){
			layer.msg("所属单位不能为空！")
			return;
		}else if(buildId ==""){
			layer.msg("所属建筑不能为空！")
			return;
		}else if (videoName == "") {
	        layer.msg("视频名称不能为空！");
	        return;
	    }else if (ip == "") {
	        layer.msg("ip不能为空！");
	        return;
	    } 
//	    else if(port == ""){
//	    	layer.msg("端口号不能为空！");
//	        return;
//	    } else if(userName == ""){
//	    	layer.msg("用户名不能为空！");
//	        return;
//	    } else if(password == ""){
//	    	layer.msg("密码不能为空！");
//	        return;
//	    }
	}else if(VideoIDType == "3"){
		if(UnitID ==""){
			layer.msg("所属单位不能为空！")
			return;
		}
		if(buildId ==""){
			layer.msg("所属建筑不能为空！")
			return;
		}
		if (buildAreaId == "") {
	        layer.msg("所属区域不能为空！");
	        return;
	    }
		if (videoName == "") {
	        layer.msg("视频名称不能为空！");
	        return;
	    }
		if (ip == "") {
	        layer.msg("ip不能为空！");
	        return;
	    } 
//		if(port == ""){
//	    	layer.msg("端口号不能为空！");
//	        return;
//	    } 
//		if(userName == ""){
//	    	layer.msg("用户名不能为空！");
//	        return;
//	    } 
//		if(password == ""){
//	    	layer.msg("密码不能为空！");
//	        return;
//	    }
	} else if(VideoIDType == "4") {
        if(UnitID ==""){
            layer.msg("所属单位不能为空！")
            return;
        }else if (videoName == "") {
            layer.msg("视频名称不能为空！");
            return;
        }else if (ip == "") {
            layer.msg("ip不能为空！");
            return;
        }
    }
    var edit = $("#edit").val();
    if(edit =="0"){
        var url = baseUrl + '/video/add';
    }else{
        var url = baseUrl + '/video/update';
    }

    var data = {
    		"id":videoId,
    	"buildId":buildId,
    	"unitId":UnitID,
		"buildAreaId": buildAreaId, 
		"ip": ip, 
		"port": port, 
		"userName": userName,
		"password": password,
		"plugInType": videoType,
		"manufactor": manufactor,
		"brand": brand,
		"position": position,
		"videoType":VideoIDType,
		"name":videoName,
		"poscode":posCode,
		"serialnumber":serialnumber
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
                emptySearch();
                $("#newAdd").modal("hide");
                $('#videoTable').bootstrapTable("refresh");
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
    var checked = $("#videoTable").bootstrapTable('getSelections');
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
		$("UnitID").val("");
		$("#BuildId").val("");
	    $("#BuildAreaId").val("");
	    $("#IP").val("");
	    $("#Port").val("");
	    $("#UserName").val("");
	    $("#Password").val("");
	    $("#VideoType").val("");
	    $("#Manufactor").val("");
	    $("#Brand").val("");
	    $("#Position").val("");
	    $("#VideoIDType").val("");
	    $("#PosCode").val("");
	    $("#serialnumber").val("");

}
//编辑角色
function editVideo(_this) {
	videoId = _this.id;
	showUnitList(_this.unitId);
	emptySearch();
	$("#VideoIDType").val(_this.videoType);
	if(_this.videoType == "1"){
		$("#UnitID").siblings("input").removeAttr("disabled");
		$("#UnitID").siblings(".combo-arrow").show();
		$("#BuildId").attr("disabled", "disabled");
		$("#BuildAreaId").attr("disabled", "disabled");
	}
//	else if(_this.videoType == "2"){
//		$("#UnitID").siblings("input").removeAttr("disabled");
//		$("#UnitID").siblings(".combo-arrow").show();
//		$("#BuildId").removeAttr("disabled");
//		$("#BuildAreaId").attr("disabled", "disabled");
//	}
	else if(_this.videoType == "3"){
		$("#UnitID").siblings("input").removeAttr("disabled");
		$("#UnitID").siblings(".combo-arrow").show();
		$("#BuildId").removeAttr("disabled");
		$("#BuildAreaId").removeAttr("disabled");
	}else if(_this.videoType == "4"){
        $("#UnitID").siblings("input").removeAttr("disabled");
        $("#UnitID").siblings(".combo-arrow").show();
        $("#BuildId").attr("disabled", "disabled");
        $("#BuildAreaId").attr("disabled", "disabled");
	}else{
		$("#UnitID").siblings("input").attr("disabled");
		$("#UnitID").siblings(".combo-arrow").hide();
		$("#BuildId").attr("disabled", "disabled");
		$("#BuildAreaId").attr("disabled", "disabled");
	}
	getBuildList(_this.unitId);
	setTimeout(function(){
		$("#BuildId").val(_this.buildID);
	},100);
	getBuildAreaList(_this.buildID);
	setTimeout(function(){
		$("#BuildAreaId").val(_this.buildAreaID);
	},100);
    $("#IP").val(_this.ip);
    $("#VideoName").val(_this.name);
    $("#Port").val(_this.port);
    $("#UserName").val(_this.userName);
    $("#Password").val(_this.password);
    $("#VideoType").val(_this.plugInType);
    $("#Manufactor").val(_this.manufactor);
    $("#Brand").val(_this.brand);
    $("#Position").val(_this.position);
    $("#PosCode").val(_this.poscode);
    $("#serialnumber").val(_this.serialnumber);
	$("#newAdd .modal-title").html("编辑视频");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('1');
    $("#newAdd").modal();
}

function showVideo(_this) {
	videoId = _this.id;
	$("#VideoIDTypeView").val(_this.videoType);
	showUnitList(_this.unitId);
	$("#UnitIDView").val(_this.unitId);
	getBuildListView(_this.unitId);
	setTimeout(function(){
		$("#BuildIdView").val(_this.buildID);
	},100);
	getBuildAreaListView(_this.buildID);
	setTimeout(function(){
		$("#BuildAreaIdView").val(_this.buildAreaID);
	},100);
    $("#IPView").val(_this.ip);
    $("#VideoNameView").val(_this.name);
    $("#PortView").val(_this.port);
    $("#UserNameView").val(_this.userName);
    $("#PasswordView").val(_this.password);
    $("#VideoTypeView").val(_this.plugInType);
    $("#ManufactorView").val(_this.manufactor);
    $("#BrandView").val(_this.brand);
    $("#PositionView").val(_this.position);
    $("#PosCodeView").val(_this.poscode);
    $("#serialnumberView").val(_this.serialnumber);
	$("#newAddView .modal-title").html("编辑视频");
    $("input[name=indexId][type=hidden]").val("");
    $("#newAddView").modal();
}

//删除角色
function deleteVideo(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/video/remove';
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
                        $('#videoTable').bootstrapTable("refresh");
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

function mainSearch() {
    $('#videoTable').bootstrapTable({
        url: baseUrl + '/video/getList',         //请求后台的URL（*）
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
                port:$("#port").val(),
                ip:$("#ip").val(),
                unitName:$("#unitName").val(),
                userName:$("#userName").val()
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
                field: 'unitName',
                title: '单位名称',
                formatter: function (value, row, index) {
    				if (value == null) {
    					return "";
    				}
    				return '<p title="'+value+'" style="width: 100px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
    			}
            },
            {
            	field: 'name',
            	title: '视频名称'
            },
            {
                field: 'ip',
                title: 'IP',
            	formatter: function (value, row, index) {
    				if (value == null) {
    					return "";
    				}
    				return '<p title="'+value+'" style="width: 120px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
    			}
            },
            {
                field: 'port',
                title: '端口号'
            },
            {
                field: 'userName',
                title: '用户名'
            },
            {
                field: 'password',
                title: '密码'
            },
            {
                field: 'plugInType',
                title: '视频插件类型',
                sortable: true,
            	formatter: function (plugInType) {
            		var value = plugInType;
        			if (value == '0') {
        				return '海康Ocx控件';
        			} else if(value == '1')  {
        				return '海康Web组件';
        			} else if(value == '2')  {
        				return '萤石直播UIKit';
        			} else if(value == '3')  {
        				return 'iVMS7200';
        			} else if(value == '4')  {
        				return '萤石直播';
        			} else if(value == '5')  {
        				return '阿百讯视屏服务器';
        			} else if(value == '6')  {
        				return '阿百讯IPC';
        			} else if(value == '7')  {
        				return '宇视摄像头';
        			}
                    else if(value == '9')  {
                        return '乐橙云';
        			}

                }
            },
            {
                field: 'position',
                title: '安装位置'
            },{
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
                        editVideo(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteVideo(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showVideo(row);
                    }
                }
            }
            
        ]
    });
    getUnitList();
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
            	// console.log(unitList);
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function getMaintenanceUnitList() {
    // debugger;
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/maintenanceunit/getMaintenanceUnitSelectList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
                maintenanceUnitList = d.obj;
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
	var b = '<option value="%maintenanceunitID%" >%maintenanceunitName%</option>';
	var option0;
	var option1;
	var wrap='<option value="">--搜索--</option>';
	for(var i = 0;i<unitList.length;i++){
	    if(unitID && unitID==unitList[i].id){
        	option0 = '<option selected value="'+unitList[i].id+'" >'+unitList[i].unitname+'</option>';
        }else{
        option0 = a.replace("%unitID%",unitList[i].id);
        option0 = option0.replace("%unitName%",unitList[i].unitname);
        }
        wrap+=option0;
	}
    for(var i = 0;i<maintenanceUnitList.length;i++){
        option1 = b.replace("%maintenanceunitID%",maintenanceUnitList[i].id);
        option1 = option1.replace("%maintenanceunitName%",maintenanceUnitList[i].unitname);
        wrap+=option1;
    }
	$("#UnitID").html(wrap);
	$("#UnitID").comboSelect();
	$("#UnitIDView").html(wrap);
	// $("#UnitIDView").comboSelect();
	// $("#UnitIDView").siblings("input").attr('disabled','disabled');
	// $("#UnitIDView").siblings(".combo-arrow").hide();
}

$("#VideoIDType").bind("change",function(){
	var VideoIDType = $("#VideoIDType").find("option:selected").val();
	if(VideoIDType == "1"){
		$("#UnitID").siblings("input").removeAttr("disabled");
		$("#UnitID").siblings(".combo-arrow").show();
		//$("#UnitID").removeAttr("disabled");
		$("#BuildId").attr("disabled", "disabled");
		$("#BuildAreaId").attr("disabled", "disabled");
	}else if(VideoIDType == "2"){
		$("#UnitID").siblings("input").removeAttr("disabled");
		$("#UnitID").siblings(".combo-arrow").show();
		//$("#UnitID").removeAttr("disabled");
		$("#BuildId").removeAttr("disabled");
		$("#BuildAreaId").attr("disabled", "disabled");
	}else if(VideoIDType == "3"){
		$("#UnitID").siblings("input").removeAttr("disabled");
		$("#UnitID").siblings(".combo-arrow").show();
		//$("#UnitID").removeAttr("disabled");
		$("#BuildId").removeAttr("disabled");
		$("#BuildAreaId").removeAttr("disabled");
	}else if(VideoIDType == "4"){
        $("#UnitID").siblings("input").removeAttr("disabled");
        $("#UnitID").siblings(".combo-arrow").show();
        //$("#UnitID").removeAttr("disabled");
        $("#BuildId").attr("disabled", "disabled");
        $("#BuildAreaId").attr("disabled", "disabled");
	}else{
		$("#UnitID").siblings("input").attr("disabled");
		$("#UnitID").siblings(".combo-arrow").hide();
		//$("#UnitID").attr("disabled", "disabled");
		$("#BuildId").attr("disabled", "disabled");
		$("#BuildAreaId").attr("disabled", "disabled");
	}
});

$("#UnitID").bind("change",function(){
	emptyBuildList();
	emptyBuildAreaList();
	getBuildList($("#UnitID").find("option:selected").val());
});
function getBuildList(UnitID){
	var data = {
			UnitID :UnitID
	}
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/build/getArrayList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	buildList = d.obj;
            	showBuildList();
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function getBuildListView(UnitID){
	var data = {
			UnitID :UnitID
	}
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/build/getArrayList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	buildList = d.obj;
            	showBuildListView();
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function showBuildList(){
	var a = '<option value="%buildId%">%buildName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		for(var i = 0;i<buildList.length;i++){
			option = a.replace("%buildId%",buildList[i].id);
			option = option.replace("%buildName%",buildList[i].buildingName);
			wrap+=option;
		}
		$("#BuildId").html(wrap);
}
function showBuildListView(){
	var a = '<option value="%buildId%">%buildName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		for(var i = 0;i<buildList.length;i++){
			option = a.replace("%buildId%",buildList[i].id);
			option = option.replace("%buildName%",buildList[i].buildingName);
			wrap+=option;
		}
		$("#BuildIdView").html(wrap);
}

function emptyBuildList(){
	$("#BuildId").empty();
	$("#BuildId").html('<option value="">--请选择--</option>');
}
$("#BuildId").bind("change",function(){
	emptyBuildAreaList();
	getBuildAreaList($("#BuildId").find("option:selected").val());
});
function getBuildAreaList(buildId){
	var data = {
			buildID :buildId
	}
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/buildArea/getArrayList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	
            	buildAreaList = d.obj;
            	showBuildAreaList();
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function getBuildAreaListView(){
	var buildId =$("#BuildIdView").find("option:selected").val();
	var data = {
			buildID :buildId
	}
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/buildArea/getArrayList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	
            	buildAreaList = d.obj;
            	showBuildAreaListView();
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function showBuildAreaListView(){
	var a = '<option value="%BuildAreaId%">%BuildAreaName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		for(var i = 0;i<buildAreaList.length;i++){
			option = a.replace("%BuildAreaId%",buildAreaList[i].id);
			option = option.replace("%BuildAreaName%",buildAreaList[i].buildAreaName);
			wrap+=option;
		}
		
		$("#BuildAreaIdView").html(wrap);
}
function showBuildAreaList(){
	var a = '<option value="%BuildAreaId%">%BuildAreaName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		for(var i = 0;i<buildAreaList.length;i++){
			option = a.replace("%BuildAreaId%",buildAreaList[i].id);
			option = option.replace("%BuildAreaName%",buildAreaList[i].buildAreaName);
			wrap+=option;
		}
		
		$("#BuildAreaId").html(wrap);
}
function emptyBuildAreaList(){
	$("#BuildAreaId").empty();
	$("#BuildAreaId").html('<option value="">--请选择--</option>');
}
