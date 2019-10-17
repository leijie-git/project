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
var buildId;
var list;
var btype;
var utype;
var fgrade;
var unitid;
var BuildingTypeList;
var StructureTypeList;
var	NHDJList;
var	UseNatureList;
var	FireRiskGradeList;
$(function () {
	
	//日期选择控件
	$("#BuildDate").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
	
	
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    mainSearch();
    getCodeListS("UseNature");
	getCodeListS("BuildingType");
	getCodeListS("FireRiskGrade");
	
	getCodeList("UseNature");
	getCodeList("StructureType");
	getCodeList("BuildingType");
	getCodeList("NHDJ");
	getCodeList("FireRiskGrade");
	getUnitList();
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
    emptySearchMsg();
});
function emptySearchMsg(){
	$("#buildName").val("");
    $("#UnitIDs").val("");
    $("#BuildingTypes").val("");
    $("#UserTypes").val("");
    $("#FireRiskGrades").val("");
    $("#searchContent").val("");
}

$(".btnSearch").click(function(){
	 $("#buildTable").bootstrapTable('destroy');
	 mainSearch();
})
//普通搜索
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    $("#buildTable").bootstrapTable('destroy');
    mainSearch();
});
$("#menuType").change(function () {
	emptySearchMsg();
});
//查询条件查询
$(".btnSearch").click(function () {
	IP = $("#IP").val();
    $("#buildTable").bootstrapTable('destroy');
    mainSearch();
});

$(".btnNew").click(function () {
	emptySearch();
	showCodeList("UseNature");
	showCodeList("StructureType");
	showCodeList("BuildingType");
	showCodeList("NHDJ");
	showCodeList("FireRiskGrade");
	getAddList();
	$("#picurls-photo").attr("file_string","");
    $("#loadImg-photo").html('<li class="modalImg"><span>请选择上传图片</span></li>');
    $("#newAdd .modal-title").html("新增建筑物信息");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
});
//重置
$(".btnClear").click(function () {
	emptySearchMsg();
	$("#buildTable").bootstrapTable('destroy');
	mainSearch();
})
//新增或编辑角色
$(".btnSure").click(function () {
	var BuildingName = $("#BuildingName").val();
	var UnitID = $("#UnitID").find("option:selected").val();
	var UnitName = $("#UnitID").find("option:selected").text();
    var BuildingType = $("#BuildingType").find("option:selected").text();
    console.log("buildingType:"+BuildingType);
    var UserType = $("#UserType").find("option:selected").text();
    var FireRiskGrade= $("#FireRiskGrade").find("option:selected").text();
    var RefractoryGrade= $("#RefractoryGrade").find("option:selected").text();
    var StructureType= $("#StructureType").find("option:selected").text();
    if(UnitID==""){
    	layer.msg("单位不能为空！")
    	return;
    }
    if(BuildingType=="--请选择--"){ //建筑物名称
    	BuildingType=""; 
    }
    if(UnitName=="--请选择--"){
    	UnitID="";
    }
    if(UserType=="--请选择--"){
    	UserType="";
    }
    if(FireRiskGrade=="--请选择--"){
    	FireRiskGrade="";
    }
    if(RefractoryGrade=="--请选择--"){
    	RefractoryGrade="";
    }
    if(StructureType=="--请选择--"){
    	StructureType="";
    }
    var BuildDate= $("#BuildDate").val();
    var BuildingHeight= $("#BuildingHeight").val();
    var BuildingArea= $("#BuildingArea").val();
    var BuildingOccupyarea= $("#BuildingOccupyarea").val();
    var StdFloorArea= $("#StdFloorArea").val();
    $("#StdFloorArea").onblur=function(){
        if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
            alert('不是数字！')
        }
    }
    var OverGroundFloors= $("#OverGroundFloors").val(); 
    var OverGroundArea= $("#OverGroundArea").val();
    var UnderGroundFloors= $("#UnderGroundFloors").val(); 
    var UnderGroundArea= $("#UnderGroundArea").val();
    var TunnelHeight= $("#TunnelHeight").val();
    var TunnelLength= $("#TunnelLength").val();
    var FireRoomPosition= $("#FireRoomPosition").val();
    var RefugeNum= $("#RefugeNum").val();
    var RefugeArea= $("#RefugeArea").val(); 
    var RefugePosition= $("#RefugePosition").val();
    var SafeExitNum= $("#SafeExitNum").val(); 
    var SafeExitPosition= $("#SafeExitPosition").val();
    var SafeExitType= $("#SafeExitType").val();
    var FireElevatorNum= $("#FireElevatorNum").val();
    var FireElevatorWeight= $("#FireElevatorWeight").val();
    var DailyPersonNum= $("#DailyPersonNum").val();
    var AccommodatePersonMaxnum= $("#AccommodatePersonMaxnum").val(); 
    var StoreMaterialName= $("#StoreMaterialName").val();
    var StoreMaterialNum= $("#StoreMaterialNum").val(); 
    var StoreMaterialNature= $("#StoreMaterialNature").val();
    var StoreMaterialForm= $("#StoreMaterialForm").val();
    var StoreVolume= $("#StoreVolume").val();
    var MainMaterial= $("#MainMaterial").val();
    var MainProduct= $("#MainProduct").val();
    var NearBuildingSituation= $("#NearBuildingSituation").val(); 
    var BuildingElevationMap= $("#BuildingElevationMap").val();
    var address = $("#address").val();
    var photo = $("#picurls-photo").attr("file_string");
    var photoEQ = $("#picurlsEQ-photo").attr("file_string");
    if (BuildingName == "") {
        layer.msg("建筑物名称不能为空！");
        return;
    } 
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/build/add';
    }else{
    	var url = baseUrl + '/build/update';
    }
   
    var data = {
    		"ID":buildId,
    		"UnitID":UnitID,
    	"BuildingName":BuildingName,
		"BuildingType": BuildingType, 
		"UserType": UserType, 
		"FireRiskGrade": FireRiskGrade,
		"RefractoryGrade": RefractoryGrade,
		"StructureType": StructureType,
		"BuildDate": BuildDate,
		"BuildingHeight": BuildingHeight,
		"BuildingArea": BuildingArea,
		"BuildingOccupyarea": BuildingOccupyarea,
		"StdFloorArea": StdFloorArea,
		"OverGroundFloors": OverGroundFloors,
		"OverGroundArea": OverGroundArea,
		"UnderGroundArea": UnderGroundArea,
		"UnderGroundFloors": UnderGroundFloors,
		"TunnelHeight":TunnelHeight,
		"TunnelLength": TunnelLength, 
		"FireRoomPosition": FireRoomPosition, 
		"RefugeNum": RefugeNum,
		"RefugeArea": RefugeArea,
		"RefugePosition": RefugePosition,
		"SafeExitNum": SafeExitNum,
		"SafeExitPosition": SafeExitPosition,
		"SafeExitType": SafeExitType, 
		"FireElevatorNum": FireElevatorNum, 
		"FireElevatorWeight": FireElevatorWeight,
		"DailyPersonNum": DailyPersonNum,
		"AccommodatePersonMaxnum": AccommodatePersonMaxnum,
		"StoreMaterialName": StoreMaterialName,
		"StoreMaterialNum": StoreMaterialNum,
		"StoreMaterialNature": StoreMaterialNature,
		"StoreMaterialForm": StoreMaterialForm,
		"StoreVolume": StoreVolume,
		"MainMaterial": MainMaterial,
		"MainProduct": MainProduct,
		"NearBuildingSituation": NearBuildingSituation,
		"BuildingElevationMap": BuildingElevationMap,
		"Address":address,
		"BuildingPlan":photo,
		"FacilitiesPlan":photoEQ
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
                $('#buildTable').bootstrapTable("refresh");
            } else {
            	layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

function emptySearch() {
    $("#BuildingName").val("");
    $("#BuildingType").val("");
    $("#UserType").val("");
    $("#UnitID").val("");
    $("#FireRiskGrade").val("");
    $("#RefractoryGrade").val("");
    $("#StructureType").val("");
    $("#BuildDate").val("");
    $("#BuildingHeight").val("");
    $("#BuildingArea").val("");
    $("#BuildingOccupyarea").val("");
    $("#StdFloorArea").val("");
    $("#OverGroundFloors").val("");
	$("#OverGroundArea").val("");
    $("#UnderGroundFloors").val("");
    $("#UnderGroundArea").val("");
    $("#TunnelHeight").val("");
    $("#TunnelLength").val("");
    $("#FireRoomPosition").val(""); 
    $("#RefugeNum").val("");
    $("#RefugeArea").val(""); 
	$("#BuildingElevationMap").val("");
    $("#NearBuildingSituation").val("");
    $("#MainProduct").val("");
    $("#MainMaterial").val("");
    $("#StoreVolume").val("");
    $("#StoreMaterialForm").val("");
    $("#StoreMaterialNature").val("");
    $("#StoreMaterialNum").val("");
    $("#StoreMaterialName").val("");
	$("#AccommodatePersonMaxnum").val("");
    $("#DailyPersonNum").val("");
    $("#FireElevatorWeight").val("");
    $("#FireElevatorNum").val("");
    $("#SafeExitPosition").val("");
    $("#SafeExitType").val(""); 
    $("#SafeExitNum").val("");
    $("#RefugePosition").val(""); 
    $("#address").val(""); 
}
//编辑角色
function editBuild(_this) {
	emptySearch();
	showCodeList("UseNature");
	showCodeList("StructureType");
	showCodeList("BuildingType");
	showCodeList("NHDJ");
	showCodeList("FireRiskGrade");
	getAddList(_this.unitID);
	buildId = _this.id;
	$("#BuildingName").val(_this.buildingName);
	$("#BuildingType option").each(function(){ //
		if($(this).text() == _this.buildingType){
			$(this).attr("selected","selected");
		}
	});
	$("#UserType option").each(function(){
		if($(this).text() == _this.userType){
			$(this).attr("selected","selected");
		}
	});
	$("#FireRiskGrade option").each(function(){
		if($(this).text() == _this.fireRiskGrade){
			$(this).attr("selected","selected");
		}
	});
	$("#RefractoryGrade option").each(function(){
		if($(this).text() == _this.refractoryGrade){
			$(this).attr("selected","selected");
		}
	});
	$("#StructureType option").each(function(){
		if($(this).text() == _this.structureType){
			$(this).attr("selected","selected");
		}
	});
	$("#BuildingName").val(_this.buildingName);
	$("#BuildDate").val(_this.buildDate);
	$("#BuildingHeight").val(_this.buildingHeight);
	$("#BuildingArea").val(_this.buildingArea);
	$("#BuildingOccupyarea").val(_this.buildingOccupyarea);
	$("#StdFloorArea").val(_this.stdFloorArea);
	$("#OverGroundFloors").val(_this.overGroundFloors);
	$("#OverGroundArea").val(_this.overGroundArea);
	$("#UnderGroundFloors").val(_this.underGroundFloors);
	$("#UnderGroundArea").val(_this.underGroundArea);
	$("#TunnelHeight").val(_this.tunnelHeight);
	$("#TunnelLength").val(_this.tunnelLength);
	$("#FireRoomPosition").val(_this.fireRoomPosition); 
	$("#RefugeNum").val(_this.refugeNum);
	$("#RefugeArea").val(_this.refugeArea); 
	$("#BuildingElevationMap").val(_this.buildingElevationMap);
	$("#NearBuildingSituation").val(_this.nearBuildingSituation);
	$("#MainProduct").val(_this.mainProduct);
	$("#MainMaterial").val(_this.mainMaterial);
	$("#StoreVolume").val(_this.storeVolume);
	$("#StoreMaterialForm").val(_this.storeMaterialForm);
	$("#StoreMaterialNature").val(_this.storeMaterialNature);
	$("#StoreMaterialNum").val(_this.storeMaterialNum);
	$("#StoreMaterialName").val(_this.storeMaterialName);
	$("#AccommodatePersonMaxnum").val(_this.accommodatePersonMaxnum);
	$("#DailyPersonNum").val(_this.dailyPersonNum);
	$("#FireElevatorWeight").val(_this.fireElevatorWeight);
	$("#FireElevatorNum").val(_this.fireElevatorNum);
	$("#SafeExitPosition").val(_this.safeExitPosition);
	$("#SafeExitType").val(_this.safeExitType); 
	$("#SafeExitNum").val(_this.safeExitNum);
	$("#RefugePosition").val(_this.refugePosition); 
	$("#address").val(_this.address);
	var photo = _this.buildingPlan;
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
	
	var photoEQ = _this.facilitiesPlan;
	if(photoEQ){
    	$("#picurlsEQ-photo").attr("file_string",photoEQ);
    	$("#loadImgEQ-photo").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photoEQ + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurlsEQ-photo").attr("file_string","");
    	$("#loadImgEQ-photo").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
	$("#newAdd .modal-title").html("编辑建筑物信息");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('1');
	$("#newAdd").modal();
}

//删除角色
function deleteBuild(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/build/remove';
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
                        $('#buildTable').bootstrapTable("refresh");
                        layer.msg("删除成功！");
                    } else {
                    	layer.msg(d.msg);
                    }
                },
                error: function (e) {
                	layer.msg(d.msg);
                }
            });
        },
        no: function (index, layero) {
        }
    });
}

function mainSearch() { 
	var UnitID = $("#UnitID").find("option:selected").text();
	var BuildingType=$("#BuildingTypes").find("option:selected").text();
	console.log("Type:"+BuildingType);
	if(BuildingType =="--请选择--"){
		BuildingType = "";
	}
    var UserType=$("#UserType").find("option:selected").text();
    var FireRiskGrade=$("#FireRiskGrade").find("option:selected").text();
    if(BuildingType=="--请选择--"){
    	BuildingType = "";
    }if(UserType=="--请选择--"){
    	UserType="";
    }if(FireRiskGrade=="--请选择--"){
    	FireRiskGrade="";
    }if(UnitID=="--请选择--"){
    	UnitID="";
    }
    $('#buildTable').bootstrapTable({
        url: baseUrl + '/build/getList',         //请求后台的URL（*）
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
                buildingName: $("#buildName").val(),
                BuildingType:BuildingType,
//                UserType: $("#UserTypes").val(),
//                FireRiskGrade:$("#FireRiskGrades").val(),
                unitName: $("#UnitIDs").val()
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
                field: 'buildingName',
                title: '建筑物名称'
            },
            {
                field: 'unitName',
                title: '单位名称'
            },
            {
                field: 'buildingType',
                title: '建筑物类别',
            },
            {
                field: 'userType',
                title: '使用性质'
            },
            {
                field: 'fireRiskGrade',
                title: '火灾危险性 '
            },
            {
                field: 'refractoryGrade',
                title: '耐火等级'
            },
            {
                field: 'structureType',
                title: '结构类型'
            },
            {
                field: 'buildDate',
                title: '建造日期'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main view" style=""><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        editBuild(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteBuild(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showBuild(row);
                    }
                }
            }
        ]
    });
}
function showBuild(_this){
	emptySearch();
	showCodeListView("UseNature");
	showCodeListView("StructureType");
	showCodeListView("BuildingType");
	showCodeListView("NHDJ");
	showCodeListView("FireRiskGrade");
	getAddList(_this.unitID);
	buildId = _this.id;
	$("#BuildingNameView").val(_this.buildingName);
	$("#BuildingTypeView option").each(function(){
		if($(this).text() == _this.buildingType){
			$(this).attr("selected","selected");
		}
	});
	$("#UserTypeView option").each(function(){
		if($(this).text() == _this.userType){
			$(this).attr("selected","selected");
		}
	});
	$("#FireRiskGradeView option").each(function(){
		if($(this).text() == _this.fireRiskGrade){
			$(this).attr("selected","selected");
		}
	});
	$("#RefractoryGradeView option").each(function(){
		if($(this).text() == _this.refractoryGrade){
			$(this).attr("selected","selected");
		}
	});
	$("#StructureTypeView option").each(function(){
		if($(this).text() == _this.structureType){
			$(this).attr("selected","selected");
		}
	});
	$("#UnitIDView").val(_this.unitID);
	$("#BuildingNameView").val(_this.buildingName);
	$("#BuildDateView").val(_this.buildDate);
	$("#BuildingHeightView").val(_this.buildingHeight);
	$("#BuildingAreaView").val(_this.buildingArea);
	$("#BuildingOccupyareaView").val(_this.buildingOccupyarea);
	$("#StdFloorAreaView").val(_this.stdFloorArea);
	$("#OverGroundFloorsView").val(_this.overGroundFloors);
	$("#OverGroundAreaView").val(_this.overGroundArea);
	$("#UnderGroundFloorsView").val(_this.underGroundFloors);
	$("#UnderGroundAreaView").val(_this.underGroundArea);
	$("#TunnelHeightView").val(_this.tunnelHeight);
	$("#TunnelLengthView").val(_this.tunnelLength);
	$("#FireRoomPositionView").val(_this.fireRoomPosition); 
	$("#RefugeNumView").val(_this.refugeNum);
	$("#RefugeAreaView").val(_this.refugeArea); 
	$("#BuildingElevationMapView").val(_this.buildingElevationMap);
	$("#NearBuildingSituationView").val(_this.nearBuildingSituation);
	$("#MainProductView").val(_this.mainProduct);
	$("#MainMaterialView").val(_this.mainMaterial);
	$("#StoreVolumeView").val(_this.storeVolume);
	$("#StoreMaterialFormView").val(_this.storeMaterialForm);
	$("#StoreMaterialNatureView").val(_this.storeMaterialNature);
	$("#StoreMaterialNumView").val(_this.storeMaterialNum);
	$("#StoreMaterialNameView").val(_this.storeMaterialName);
	$("#AccommodatePersonMaxnumView").val(_this.accommodatePersonMaxnum);
	$("#DailyPersonNumView").val(_this.dailyPersonNum);
	$("#FireElevatorWeightView").val(_this.fireElevatorWeight);
	$("#FireElevatorNumView").val(_this.fireElevatorNum);
	$("#SafeExitPositionView").val(_this.safeExitPosition);
	$("#SafeExitTypeView").val(_this.safeExitType); 
	$("#SafeExitNumView").val(_this.safeExitNum);
	$("#RefugePositionView").val(_this.refugePosition); 
	$("#addressView").val(_this.address);
	var photo = _this.buildingPlan;
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
	
	var photoEQ = _this.facilitiesPlan;
	if(photoEQ){
    	$("#picurlsEQ-photoView").attr("file_string",photoEQ);
    	$("#loadImgEQ-photoView").html(
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photoEQ + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurlsEQ-photoView").attr("file_string","");
    }
	$("#newAddView .modal-title").html("编辑建筑物信息");
	$("input[name=indexId][type=hidden]").val("");
	$("#newAddView").modal();
}

function getCodeList(type){
	var data = {
			codeGroupKey:type
	}
	$.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/code/getListByGroupKey",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	if(type == "BuildingType"){
            		BuildingTypeList = d.obj;
        		}else if(type == "StructureType"){
        			StructureTypeList = d.obj;
        		}else if(type == "NHDJ"){
        			NHDJList = d.obj;
        		}else if(type == "UseNature"){
        			UseNatureList = d.obj;
        		}else if(type == "FireRiskGrade"){
        			FireRiskGradeList = d.obj;
        		}
            	
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function showCodeList(type){
	var a = '<option value="%codeType%">%codeName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		
		if(type == "BuildingType"){
			for(var i = 0;i<BuildingTypeList.length;i++){
				option = a.replace("%codeType%",BuildingTypeList[i].codeid);
				option = option.replace("%codeName%",BuildingTypeList[i].codename);
				wrap+=option;
			}
			$("#BuildingType").html(wrap);
		}else if(type == "StructureType"){
			for(var i = 0;i<StructureTypeList.length;i++){
				option = a.replace("%codeType%",StructureTypeList[i].codeid);
				option = option.replace("%codeName%",StructureTypeList[i].codename);
				wrap+=option;
			}
			$("#StructureType").html(wrap);
		}else if(type == "NHDJ"){
			for(var i = 0;i<NHDJList.length;i++){
				option = a.replace("%codeType%",NHDJList[i].codeid);
				option = option.replace("%codeName%",NHDJList[i].codename);
				wrap+=option;
			}
			$("#RefractoryGrade").html(wrap);
		}else if(type == "UseNature"){
			for(var i = 0;i<UseNatureList.length;i++){
				option = a.replace("%codeType%",UseNatureList[i].codeid);
				option = option.replace("%codeName%",UseNatureList[i].codename);
				wrap+=option;
			}
			$("#UserType").html(wrap);
		}else if(type == "FireRiskGrade"){
			for(var i = 0;i<FireRiskGradeList.length;i++){
				option = a.replace("%codeType%",FireRiskGradeList[i].codeid);
				option = option.replace("%codeName%",FireRiskGradeList[i].codename);
				wrap+=option;
			}
			$("#FireRiskGrade").html(wrap);
		}
		$("#BuildId>#BuildId").html(wrap);
}

function showCodeListView(type){
	var a = '<option value="%codeType%">%codeName%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
		
		if(type == "BuildingType"){
			for(var i = 0;i<BuildingTypeList.length;i++){
				option = a.replace("%codeType%",BuildingTypeList[i].codeid);
				option = option.replace("%codeName%",BuildingTypeList[i].codename);
				wrap+=option;
			}
			$("#BuildingTypeView").html(wrap);
		}else if(type == "StructureType"){
			for(var i = 0;i<StructureTypeList.length;i++){
				option = a.replace("%codeType%",StructureTypeList[i].codeid);
				option = option.replace("%codeName%",StructureTypeList[i].codename);
				wrap+=option;
			}
			$("#StructureTypeView").html(wrap);
		}else if(type == "NHDJ"){
			for(var i = 0;i<NHDJList.length;i++){
				option = a.replace("%codeType%",NHDJList[i].codeid);
				option = option.replace("%codeName%",NHDJList[i].codename);
				wrap+=option;
			}
			$("#RefractoryGradeView").html(wrap);
		}else if(type == "UseNature"){
			for(var i = 0;i<UseNatureList.length;i++){
				option = a.replace("%codeType%",UseNatureList[i].codeid);
				option = option.replace("%codeName%",UseNatureList[i].codename);
				wrap+=option;
			}
			$("#UserTypeView").html(wrap);
		}else if(type == "FireRiskGrade"){
			for(var i = 0;i<FireRiskGradeList.length;i++){
				option = a.replace("%codeType%",FireRiskGradeList[i].codeid);
				option = option.replace("%codeName%",FireRiskGradeList[i].codename);
				wrap+=option;
			}
			$("#FireRiskGradeView").html(wrap);
		}
		$("#BuildId>#BuildIdView").html(wrap);
}
function getAddList(unitID){
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

function getCodeListS(type){
	var data = {
			codeGroupKey:type
	}
	$.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/code/getListByGroupKey",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	var a = '<option value="%codeType%">%codeName%</option>';
            	var option;
            	var wrap='<option value="">--请选择--</option>';
            		for(var i = 0;i<d.obj.length;i++){
            			option = a.replace("%codeType%",d.obj[i].codeid);
            			option = option.replace("%codeName%",d.obj[i].codename);
            			wrap+=option;
            		}
            		if(type == "BuildingType"){
            			$("#BuildingTypes").html(wrap);
            		}else if(type == "UseNature"){
            			$("#UserTypes").html(wrap);
            		}else if(type == "FireRiskGrade"){
            			$("#FireRiskGrades").html(wrap);
            		}
            		
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}
function doChangeProjectphoto() {
	uploadOne("picurls-photo", "loadImg-photo", "photoCover-photo", false);
};
function doChangeProjectphotoEQ() {
	uploadOne("picurlsEQ-photo", "loadImgEQ-photo", "photoCoverEQ-photo", false);
};