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
var buildAreaId;
var unitList;
var buildList;
$(function () {
    //搜索控制
    $(".jy_mainTile").hide();
    getUnitList();
    //主页面查询
    mainSearch();
    
});
//普通搜索
$(".ordinarySearch").click(function () {
	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#"+id).val(value);
    $("#buildAreaTable").bootstrapTable('destroy');
    mainSearch();
});

$(".btnSearch").click(function () {
    $("#buildAreaTable").bootstrapTable('destroy');
    mainSearch();
});
function emptySearchMsg(){
	$("#areaName").val("");
	$("#unitName").val("");
    $("#areaAddress").val("");
    $("#searchContent").val("");
}
$("#menuType").change(function () {
	emptySearchMsg();
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
    $("#buildAreaTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#buildAreaTable").bootstrapTable('destroy');
    mainSearch();
});
$(".btnNew").click(function () {
	emptyBuildList();
	emptySearch();
	showUnitList();
	$("#picurls-photo").attr("file_string","");
    $("#loadImg-photo").html('<li class="modalImg"><span>请选择上传图片</span></li>');
    
    $("#picurls-photos").attr("file_string","");
    $("#loadImg-photos").html('<li class="modalImg"><span>请选择上传图片</span></li>');
    $("#newAdd .modal-title").html("新增建筑物区域信息");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
});

$(".export").click(function(){
	var htm = baseUrl+"/buildArea/exportBuildArea?";
	var buildAreaName = $("#areaName").val();
    var buildAreaSite = $("#areaAddress").val();
    var unitName = $("#unitName").val();
	if(buildAreaName != ""){
		htm += 'buildAreaName='+buildAreaName+'&';
	}
	if(buildAreaSite != ""){
		htm += 'buildAreaSite='+buildAreaSite+'&';
	}
	if(unitName != ""){
		htm += 'unitName='+unitName;
	}
	window.location.href=htm;
});
//新增或编辑角色
$(".btnSure").click(function () {
	var BuildAreaName = $("#BuildAreaName").val();
    var BuildID = $("#BuildId").find("option:selected").val();
//    var UnitID = $("#UnitID").find("option:selected").val();
    var UnitID = $("#UnitID").siblings(".combo-dropdown").find("li.option-selected").attr("data-value");
    var BuildAreaSite = $("#BuildAreaSite").val();
    var photo = $("#picurls-photo").attr("file_string");
    var photos = $("#picurls-photos").attr("file_string");
    
    // 创建对象
	var img = new Image();
	// 改变图片的src
	img.src = photo;
	var BgWidth= img.width;
	var BgHeight= img.height;
	
	if (BuildAreaName == "") {
        layer.msg("区域名称不能为空！");
        return;
    }
	if (UnitID == "") {
        layer.msg("单位不能为空！");
        return;
    } 
	if(BuildId==""){
    	 layer.msg("所属建筑不能为空！");
         return;
    }
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/buildArea/add';
    }else{
    	var url = baseUrl + '/buildArea/update';
    }
   
    var data = {
    		"id":buildAreaId,
    	"buildAreaName":BuildAreaName,
		"buildID": BuildID,
		"unitID":UnitID,
		"buildAreaSite": BuildAreaSite, 
		"bgWidth": BgWidth,
		"bgHeight": BgHeight,
		"buildAreabg":photo,
		"buildImgbg":photos
		
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
                $('#buildAreaTable').bootstrapTable("refresh");
            } else {
            	layer.msg(d.msg);
            }
        },
        error: function (e) {
        }
    });
});

function emptySearch() {
    $("#BuildAreaName").val("");
    $("#BuildId").val("");
    $("#UnitID").val("");
    $("#BuildAreaSite").val(""); 
    $("#BgWidth").val("");
    $("#BgHeight").val(""); 
    $("#BuildAreabg").val("");
}
//编辑角色
function editBuildArea(_this) {
	
	buildAreaId = _this.id;
	showUnitList(_this.unitID);
	//$("#UnitID").val(_this.unitID);
	getBuildList();
	setTimeout(function(){
		$("#BuildId").val(_this.buildID);
		},100);
	$("#BuildAreaName").val(_this.buildAreaName);
	$("#BuildAreaSite").val(_this.buildAreaSite);
	
	$("#BgWidth").val(_this.bgWidth);
	$("#BgHeight").val(_this.bgHeight);
 	var photo = _this.buildAreabg;
 	console.log("photo:"+photo);
	if(photo){
    	$("#picurls-photo").attr("file_string",photo);
    	$("#loadImg-photo").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photo + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photo").attr("file_string","");
    	$("#loadImg-photo").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
	
	var photos = _this.buildImgbg;
	console.log("photos:"+photos);
	if(photos){
    	$("#picurls-photos").attr("file_string",photos);
    	$("#loadImg-photos").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photos + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photos").attr("file_string","");
    	$("#loadImg-photos").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
	$("#newAdd .modal-title").html("编辑建筑物区域信息");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('1');
	$("#newAdd").modal();
}

function showBuildArea(_this) {
	buildAreaId = _this.id;
	showUnitList(_this.unitID);
	//$("#UnitIDView").val(_this.unitID);
	getBuildList();
	setTimeout(function(){
		$("#BuildIdView").val(_this.buildID);
		},100);
	$("#IdView").val(_this.id);
	$("#BuildAreaNameView").val(_this.buildAreaName);
	$("#BuildAreaSiteView").val(_this.buildAreaSite);
	
	$("#BgWidthView").val(_this.bgWidth);
	$("#BgHeightView").val(_this.bgHeight);
 	var photo = _this.buildAreabg;
 	console.log("photo:"+photo);
	if(photo){
    	$("#picurls-photoView").attr("file_string",photo);
    	$("#loadImg-photoView").html(
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photo + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photoView").attr("file_string","");
    }
	
	var photos = _this.buildImgbg;
	if(photos){
    	$("#picurls-photosView").attr("file_string",photos);
    	$("#loadImg-photosView").html(
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photos + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photosView").attr("file_string","");
    }
	$("#newAddView .modal-title").html("编辑建筑物区域信息");
	$("input[name=indexId][type=hidden]").val("");
	$("#newAddView").modal();
}


//删除角色
function deleteBuildArea(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/buildArea/remove';
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
                        $('#buildAreaTable').bootstrapTable("refresh");
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

function mainSearch() {
    $('#buildAreaTable').bootstrapTable({
        url: baseUrl + '/buildArea/getList',         //请求后台的URL（*）
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
                buildAreaName:$("#areaName").val(),
                buildAreaSite:$("#areaAddress").val(),
                unitName:$("#unitName").val()
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
                field: 'buildAreaName',
                title: '区域名称'
            },
            {
                field: 'unitName',
                title: '单位名称'
            },
            {
                field: 'buildAreaSite',
                title: '区域地址',
            },
            {
                field: 'bgWidth',
                title: '缩略图宽度'
            },
            {
                field: 'bgHeight',
                title: '缩略图高度 '
            },
            {
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
                        editBuildArea(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteBuildArea(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showBuildArea(row);
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
$("#UnitID").bind("change",function(){
	getBuildList();
});
function getBuildList(){
	var UnitID =$("#UnitID").find("option:selected").val();
	if(UnitID==0){
		UnitID = "";
	}
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
            	buildList =  d.obj;
            	showBuildList();
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
		$("#BuildIdView").html(wrap);
}
function emptyBuildList(){
	$("#BuildId").empty();
	$("#BuildId").html('<option value="">--请选择--</option>');
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
    var checked = $("#buildAreaTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/buildArea/remove";
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
                $('#buildAreaTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
});
function doChangeProjectphoto() {
	uploadOne("picurls-photo", "loadImg-photo", "photoCover-photo", false);
};

function doChangeProjectphotos() {
	uploadOne("picurls-photos", "loadImg-photos", "photoCover-photos", false);
};