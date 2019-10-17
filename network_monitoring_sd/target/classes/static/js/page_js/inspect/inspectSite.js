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
var inspectSiteId;
var list;
var list2;
var unitID;
var unitList;   /*单位列表*/
var planUserList;   /*人员id列表*/
var planUserNameList;   /*人员名字列表*/
var planUserName;
$(function () {
	//日期选择控件
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

    $(".leadingIn").click(function () {
        $("#leadingInModal").modal();
    });
	
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    getUnitID();

    // 选择人员操作
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

function emptySearchMsg(){
	$("#siteName-Search").val("");
	$("#siteDesc-Search").val("");
    $("#searchContent").val("");
}
$("#menuType").change(function () {
	emptySearchMsg();
});

// function showAddList(){
// 	var b = '<option value="%unitID%">%unitName%</option>';
// 	var option;
// 	var wrap='<option value="">--请选择--</option>';
// 		for(var i = 0;i<unitID.length;i++){
// 			option = b.replace("%unitID%",unitID[i].id);
// 			option = option.replace("%unitName%",unitID[i].unitname);
// 			wrap+=option;
// 		}
// 		$("#UnitID").html(wrap);
//
// }
// function showAddListView(){
// 	var b = '<option value="%unitID%">%unitName%</option>';
// 	var option;
// 	var wrap='<option value="">--请选择--</option>';
// 		for(var i = 0;i<unitID.length;i++){
// 			option = b.replace("%unitID%",unitID[i].id);
// 			option = option.replace("%unitName%",unitID[i].unitname);
// 			wrap+=option;
// 		}
// 		$("#UnitIDView").html(wrap);
// }

// function showSiteClass(){
// 	var a = '<option value="%SiteClassID%">%SiteClassName%</option>';
// 	var option;
// 	var wrap='<option value="">--请选择--</option>';
// 		for(var i = 0;i<list2.length;i++){
// 			option = a.replace("%SiteClassID%",list2[i].id);
// 			option = option.replace("%SiteClassName%",list2[i].siteClassName);
// 			wrap+=option;
// 		}
// 		$("#SiteClassName").html(wrap);
// }

// function showSiteClassView(){
// 	var a = '<option value="%SiteClassID%">%SiteClassName%</option>';
// 	var option;
// 	var wrap='<option value="">--请选择--</option>';
// 		for(var i = 0;i<list2.length;i++){
// 			option = a.replace("%SiteClassID%",list2[i].id);
// 			option = option.replace("%SiteClassName%",list2[i].siteClassName);
// 			wrap+=option;
// 		}
// 		$("#SiteClassNameView").html(wrap);
// }
//普通搜索
$(".ordinarySearch").click(function () {
	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#"+id).val(value);
    $("#inspectSiteTable").bootstrapTable('destroy');
    mainSearch();
});

//查询条件查询
$(".btnSearch").click(function () {
    $("#inspectSiteTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#inspectSiteTable").bootstrapTable('destroy');
    mainSearch();
});
$(".btnNew").click(function () {
	emptySearch();
	// showAddList();
	// showSiteClass();
    inspectSiteId = "";
	showUnitList("UnitID",unitID);
	if(unitID != ""){
	    $("#UnitID").attr("disabled","disabled");
    }
	// $("#InspectCycleType").removeAttr("disabled");
    $("#newAdd .modal-title").html("新增巡查点");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
});
//新增或编辑角色
$(".btnSure").click(function () {

    var UnitID = $("#UnitID").val();
    var BuildID = $("#BuildID").val();
    var BuildAreaID = $("#BuildAreaID").val();
    var SiteCode = $("#SiteCode").val();
	var SiteName = $("#SiteName").val();
	var SiteDesc = $("#SiteDesc").val();
	var InspectCycleType = $("#InspectCycleType").val();
	var NFCCode = $("#NFCCode").val();
	var QRCode = $("#QRCode").val();
	var InspectCount = $("#InspectCount").val();
	var TaskStart = $("#TaskStart").val();
	var TaskEnd = $("#TaskEnd").val();
	// var executorID = $("#executorID").val();
	var executorID = planUserList;
	var executorName = planUserNameList;
    if(UnitID==""){
	   	 layer.msg("非联网单位用户不能添加！");
	     return;
    }
    if(BuildID==""){
        layer.msg("建筑不能为空！");
        return;
    }
    if(BuildAreaID==""){
        layer.msg("区域不能为空！");
        return;
    }
    if(SiteCode==""){
	   	 layer.msg("位置编号不能为空！");
	     return;
    } 
    if(SiteName==""){
      	 layer.msg("位置名称不能为空！");
         return;
    }
    if(SiteDesc==""){
        layer.msg("位置描述不能为空！");
        return;
    }
    if(InspectCycleType==""){
      	 layer.msg("周期类型不能为空！");
         return;
    }
    if(InspectCount==""){
      	 layer.msg("巡查次数不能为空！");
         return;
    }
    if(TaskStart==""){
        layer.msg("执行开始时间不能为空！");
        return;
    }
    if(TaskEnd==""){
        layer.msg("执行结束时间不能为空！");
        return;
    }
    if(executorID=="" || executorID == undefined){
        layer.msg("执行人不能为空！");
        return;
    }
    if(executorName=="" || executorName == undefined){
        layer.msg("执行人不能为空！");
        return;
    }
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/inspectSite/add';
    }else{
    	var url = baseUrl + '/inspectSite/update';
    }
   
    var data = {
    		"ID":inspectSiteId,
		"SiteCode": SiteCode, 
		"UnitID": UnitID,
		"buildID": BuildID,
		"buildAreaID": BuildAreaID,
		"SiteName":SiteName,
    	"SiteDesc":SiteDesc,
		"InspectCycleType": InspectCycleType,
		"NFCCode":NFCCode,
		"qrCode":QRCode,
    	"InspectCount":InspectCount,
		"TaskStart": TaskStart,
		"TaskEnd": TaskEnd,
        "executorID": executorID,
        "executorName": executorName
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
                $('#inspectSiteTable').bootstrapTable("refresh");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

function emptySearch() {
	$("#SiteClassName").val("");
    $("#UnitID").val("");
    $("#BuildID").val("");
    $("#BuildAreaID").val("");
    $("#SiteCode").val("");
	$("#SiteName").val("");
	$("#SiteDesc").val("");
//	$("#InspectCycle").val();
	$("#InspectCycleType").val("");
	$("#NFCCode").val("");
	$("#QRCode").val("");
	$("#InspectCount").val("");
	$("#TaskStart").val("");
	$("#TaskEnd").val("");
	$("#executorID").val("");
	$("#executorName").val("");
}
//编辑角色
function editInspectSite(_this) {
	emptySearch();
	// showAddList();
	// showSiteClass();
	showUnitList("UnitID",_this.unitID);
	showBuildList("BuildID", _this.unitID, _this.buildID);
    if(_this.buildAreaID){
        showBuildAreaList("BuildAreaID",_this.buildID,_this.buildAreaID);
    }
	inspectSiteId = _this.id;
    planUserList = _this.executorID;
    planUserNameList = _this.executorName;
	// $("#SiteClassName").val(_this.siteClassID);
    $("#SiteCode").val(_this.siteCode);
	$("#SiteName").val(_this.siteName);
	$("#SiteDesc").val(_this.siteDesc);
//	$("#InspectCycle").val(_this.inspectCycle);
	$("#InspectCycleType").val(_this.inspectCycleType);
	// $("#InspectCycleType").attr("disabled","disabled");
	$("#NFCCode").val(_this.nfccode);
	$("#QRCode").val(_this.qrCode);
	$("#InspectCount").val(_this.inspectCount);
	$("#TaskStart").val(_this.taskStart);
	$("#TaskEnd").val(_this.taskEnd);
	$("#executorID").val(_this.executorID);
	$("#executorName").val(_this.executorName);
	$("#newAdd .modal-title").html("编辑巡查点");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('1');
	$("#newAdd").modal();
}
function emptySearchView() {
	$("#SiteClassNameView").val("");
    $("#UnitIDView").val("");
    $("#BuildIDView").val("");
    $("#BuildAreaIDView").val("");
    $("#SiteCodeView").val("");
	$("#SiteNameView").val("");
	$("#SiteDescView").val("");
//	$("#InspectCycleView").val();
	$("#InspectCycleTypeView").val("");
	$("#NFCCodeView").val("");
	$("#QRCodeView").val("");
	$("#InspectCountView").val("");
	$("#TaskStartView").val("");
	$("#TaskEndView").val("");
	$("#executorIDView").val("");
}
//查看角色
function showInspectSite(_this) {
	emptySearchView();
	// showAddListView();
    showUnitList("UnitIDView",_this.unitID);
    if(_this.buildID){
        showBuildList("BuildIDView",_this.unitID,_this.buildID);
    }
    if(_this.buildAreaID){
        showBuildAreaList("BuildAreaIDView",_this.buildID,_this.buildAreaID);
    }
	// showSiteClassView();
	inspectSiteId = _this.id;
    $("#SiteCodeView").val(_this.siteCode);
	$("#SiteNameView").val(_this.siteName);
	$("#SiteDescView").val(_this.siteDesc);
	$("#InspectCycleTypeView").val(_this.inspectCycleType);
	$("#NFCCodeView").val(_this.nfccode);
	$("#QRCodeView").val(_this.qrCode);
	$("#InspectCountView").val(_this.inspectCount);
	$("#TaskStartView").val(_this.taskStart);
	$("#TaskEndView").val(_this.taskEnd);
	$("#executorNameView").val(_this.executorName);
	$("#newAddView .modal-title").html("编辑巡查点");
	$("input[name=indexId][type=hidden]").val("");
	$("#newAddView").modal();
}

//删除角色
function deleteInspectSite(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/inspectSite/remove';
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
                        $('#inspectSiteTable').bootstrapTable("refresh");
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


// 获取当前巡查点下的检查内容
function PatrolItem(_this){
    $('#PatrolItemTable').bootstrapTable({
        url: baseUrl + '/inspectBaseRel/getList',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions: {
            headers: {"Authorization": "Bearer "+login.token}       //token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                siteId: _this.id,
                unitId:unitID,
                unitName: "",
                buildName: "",
                buildAreaName: ""
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
        clickToSelect: false,                //是否启用点击选中行
        singleSelect: false,
        // height: dataHeight,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'checkInfo',
                title: '检查内容'
            },
            {
                field: 'siteClassName',
                title: '巡查设施'
            }
        ]
    });
}


function mainSearch() {
    $('#inspectSiteTable').bootstrapTable({
        url: baseUrl + '/inspectSite/getList',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions: {
            headers: {"Authorization": "Bearer "+login.token}       //token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                SiteName: $("#siteName-Search").val(),
                SiteDesc: $("#siteDesc-Search").val(),
                UnitID:unitID
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
             },
            {
                field: 'siteCode',
                title: '位置编号'
            },
            {
                field: 'siteName',
                title: '位置名称'
            },
            {
                field: 'siteDesc',
                title: '位置描述'
            },
            {
                field: 'unitName',
                title: '单位'
            },
            // {
            //     field: 'inspectCycleType',
            //     title: '周期类型',
            // 	sortable: true,
            // 	formatter: function (inspectCycleType) {
            // 		var value = inspectCycleType;
        	// 		if (value == '0') {
        	// 			return '日';
        	// 		} else if(value=='1') {
        	// 			return '周';
        	// 		} else if(value=='2') {
        	// 			return '月';
        	// 		} else if(value=='3') {
        	// 			return '年';
        	// 		}
            //     }
            // },
            // {
            //     field: 'inspectCount',
            //     title: '巡查次数'
            // },
//            ,
//            {
//                field: 'taskStart',
//                title: '开始时间'
//            },
//            {
//                field: 'taskEnd',
//                title: '结束时间'
//            }
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
            		str += '<button type="button" class="btn btn-new btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    str += '<button type="button" class="btn btn-new btn-xs cBtn-main item"><i class="fa fa-eye"></i>&nbsp;巡查项</button>'
//            		str += '<button type="button" class="btn btn-new btn-xs cBtn-main lookPic"><i class="fa fa-eye"></i>&nbsp;查看二维码</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        editInspectSite(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteInspectSite(row);
                    },
                    'click .view': function (e, value, row, index) {
                    	showInspectSite(row);
                    },
                    'click .item': function (e, value, row, index) {
                        $("#PatrolItemTable").bootstrapTable('destroy');
                        PatrolItem(row);
                        $("#PatrolItem").modal();
                    }
                }
            }
        ]
    });
}

// 获取单位列表
function getUnitList() {
    $.ajax({
        type : "get",
        url : baseUrl + "/baseInfo/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        async: true,
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

// 展示单位列表
function showUnitList(element,unitid) {
    var html = '<option value="">请选择</option>';
    var option = "";
    for(var i=0;i<unitList.length;i++){
        if(unitid == unitList[i].id){
            option = '<option value="'+ unitList[i].id +'" selected>'+ unitList[i].unitname +'</option>'
        }else{
            html += '<option value="'+ unitList[i].id +'">'+ unitList[i].unitname +'</option>';
        }
        html += option;
    }
    $("#"+ element).html(html || "暂无数据");
    if(unitid == ""){
        element.indexOf("View") != -1 ? "" : $("#"+ element).comboSelect();
    }
}

// 加载建筑列表
function showBuildList(element,unitid,buildid) {
    var data = {
        UnitID : unitid
    }
    $.ajax({
        type : "get",
        url : baseUrl + "/build/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        data: data,
        async: true,
        success : function(d) {
            if (d.success) {
                var obj = d.obj;
                var html = '<option value="">请选择</option>';
                var option = "";
                for(var i=0;i<obj.length;i++){
                    if(buildid == obj[i].id){
                        option = '<option value="'+ obj[i].id +'" selected>'+ obj[i].buildingName +'</option>'
                    }else{
                        html += '<option value="'+ obj[i].id +'">'+ obj[i].buildingName +'</option>';
                    }
                }
                html += option;
                $("#"+ element).html(html || "暂无数据");
            }
        },
        error : function(e) {

        }
    });
}

// 加载区域列表
function showBuildAreaList(element,buildid,buildareaid) {
    var data = {
        BuildID : buildid
    }
    $.ajax({
        type : "get",
        url : baseUrl + "/buildArea/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        data: data,
        async: true,
        success : function(d) {
            if (d.success) {
                var obj = d.obj;
                var html = '<option value="">请选择</option>';
                var option = "";
                for(var i=0;i<obj.length;i++){
                    if(buildareaid == obj[i].id){
                        option = '<option value="'+ obj[i].id +'" selected>'+ obj[i].buildAreaName +'</option>'
                    }else{
                        html += '<option value="'+ obj[i].id +'">'+ obj[i].buildAreaName +'</option>';
                    }
                    html += option;
                }
                $("#"+ element).html(html || "暂无数据");
            }
        },
        error : function(e) {

        }
    });
}

// 切换单位，建筑
$("#UnitID").on("change",function () {
    unitID = $("#UnitID").val();
    showBuildList("BuildID",unitID,"");
    $("#BuildAreaID").html('<option value="">请选择</option>');
});
$("#BuildID").on("change",function () {
    var BuildID = $("#BuildID").val();
    showBuildAreaList("BuildAreaID",BuildID,"");
});

function lookPic(_this){
	$("#lookModel").modal();
	$("#pic").empty();
	var qrcode = jQuery("#pic").qrcode({    
		render    : "canvas",    
		text    : _this.id,    
		width : "200",               //二维码的宽度    
		height : "200",              //二维码的高度    
		background : "#ffffff",       //二维码的后景色    
		foreground : "#000000"        //二维码的前景色    
//		src: 'logo.png'             //二维码中间的图片
		}).hide();
	var canvas=qrcode.find('canvas').get(0);   
	$('#imgOne').attr('src',canvas.toDataURL('image/jpg'))
}

function downLoadPic(){
	//图片路径
	var src =$('#imgOne').attr('src');
	console.log("aaa:"+src);
	//下载保存文件名
	var imgname = 'site.png';
	  $('#downImg').attr('src', src);
      var alink = document.createElement("a");
      alink.href = src;
      alink.download = imgname;
      alink.click();
}
function getUnitID(){
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/inspectPlan/getUnitID",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	unitID = d.obj;
            	mainSearch();
                getUnitList();
                if(unitID != ""){
                    showBuildList("BuildID",unitID,"");
                }
            	// getSiteClassList();
            	// PatrolItem('');
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
}

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
    var checked = $("#inspectSiteTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/inspectSite/remove";
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
                $('#inspectSiteTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

$(".moreCode").click(function () {
    var checked = $("#inspectSiteTable").bootstrapTable('getSelections');
    var valArr = new Array;
    var siteNameArr = new Array;
    var siteDescArr = new Array;
    var siteClassNameArr = new Array;
    if (checked.length > 0) {
        $(checked).each(function (i, item) {
            valArr[i] = item.id;
            siteNameArr[i] = item.siteName;
            siteDescArr[i] = item.siteDesc;
            siteClassNameArr[i] = item.siteClassName;
        });
        var vals = valArr.join(',');
    } else {
        layer.msg("请选择一条记录！");
        return;
    }
    
    var html="";
	for(var i=0;i<valArr.length;i++){
	    $("#pic").empty();
	    var qrcode = jQuery("#pic").qrcode({    
	        render    : "canvas",    
	        text    : valArr[i],    
	        width : "150",               //二维码的宽度    
	        height : "150",              //二维码的高度    
	        background : "#ffffff",       //二维码的后景色    
	        foreground : "#000000"        //二维码的前景色    
	//      src: 'logo.png'             //二维码中间的图片
	        }).hide();
	    var canvas=qrcode.find('canvas').get(0);   
	    //$('#imgOne').attr('src',canvas.toDataURL('image/jpg'))
	    var src = canvas.toDataURL('image/jpg')
	    console.log("src:"+src);
	    html +='<div class="qrCode" style="margin-left:70px;margin-top:10px;width:150px;height:auto;text-align:center;border-bottom:1px solid black" >'+
	    '<div><img id="pic"+'+i+'; src='+src+'></img></div>'+
	    '<div style="margin-top: 7px;">'+
	    '<p class="fontchange">'+siteNameArr[i]+'</p>'+
	    '<p class="fontchange">'+siteDescArr[i]+'</p>'+
	    '<p class="fontchange">'+siteClassNameArr[i]+'</p>'+
	    '</div>'+
	    '</div>'
	}
	$(".allCode").html(html||"<tr><td>暂无数据...</td></tr>");
	$("#lookModel").modal();
    $("#fontSizeChange").val("14");
});


// 自行选择改变二维码字体的大小
function fontSize() {
    $(".fontchange").css({"font-size": $("#fontSizeChange").val()+"px","margin":"5px 0"});
}


function printCode() {
	$("#lookModel").modal("hide");
    var headhtml = "<html><head><title></title></head><body>";
    var foothtml = "</body>";
    // 获取div中的html内容
    var newhtml = document.all.item('allCode').innerHTML;
    // 获取div中的html内容，jquery写法如下
    // var newhtml= $("#" + printpage).html();

    // 获取原来的窗口界面body的html内容，并保存起来
    var oldhtml = document.body.innerHTML;

    // 给窗口界面重新赋值，赋自己拼接起来的html内容
    document.body.innerHTML = headhtml + newhtml + foothtml;
    // 调用window.print方法打印新窗口
    window.print();

    // 将原来窗口body的html值回填展示
    document.body.innerHTML = oldhtml;
    location.reload();
    return false;
}



//下载默认模板
function createObjectURL(object){return (window.URL) ? window.URL.createObjectURL(object) : window.webkitURL.createObjectURL(object);}
function downloadModel() {
    var xhr = new XMLHttpRequest();
    var formData = new FormData();
    xhr.open('get',baseUrl + "/common/download/patrolplan");  //url填写后台的接口地址，如果是post，在formData append参数（参考原文地址）
    xhr.setRequestHeader("Authorization", "Bearer "+login.token);
    xhr.responseType = 'blob';
    xhr.onload = function (e) {
        if (this.status == 200) {
            var blob = this.response;
            var filename = "巡查点管理.xls";  //这里的名字，可以按后端给的接口固定表单设置一下名字，如（费用单.xlsx,合同.doc等等）
            console.log(this.response)
            if (window.navigator.msSaveOrOpenBlob) {
                navigator.msSaveBlob(blob, filename);
            } else {
                var a = document.createElement('a');
                var url = createObjectURL(blob);
                a.href = url;
                a.download = filename;
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            }

        }
    };
    xhr.send(formData);
}
// function downloadModel() {
//     location.href= baseUrl + "/common/download/patrolplan";
// }

//上传按钮
function doChange(file) {
    var upload_file = $(file).val();    //获取上传文件
    $('.fileName').html(upload_file);     //赋值给自定义input框
};
//导入按钮
$(".btnImport").click(function(){
    var formData = new FormData($("#projectForm")[0]);
    var uploadExcel =$(".uploadExcel").val();
    if (uploadExcel == "") {
        layer.msg("请选择上传文件！");
        return;
    }
    $.ajax({
        url: baseUrl + "/inspectSite/importExcel",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success: function (data) {
            layer.msg(data.msg);
            layer.closeAll("loading");
            $("#leadingInModal").modal("hide");
            $("#exportPage").modal("hide");
            $('#inspectSiteTable').bootstrapTable('refresh');
        }
    })
});

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
            "siteID" : inspectSiteId,
            "userrole": 2
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
    $("#executorName").val(names);
    $("#executorID").val(ids);

});