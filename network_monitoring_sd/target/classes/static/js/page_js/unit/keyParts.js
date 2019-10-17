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
var keyPartsId;
$(function () {
    //搜索控制
    $(".jy_mainTile").hide();
    //主页面查询
    getUnitList();
    mainSearch();
});
//普通搜索
$(".ordinarySearch").click(function () {
	var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#"+id).val(value);
    $("#keyPartsTable").bootstrapTable('destroy');
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
    $("#keyPartsTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#keyPartsTable").bootstrapTable('destroy');
    mainSearch();
});
function emptySearchMsg(){
	$("#importName").val("");
	$("#unitName").val("");
    $("#importSite").val("");
    $("#searchContent").val("");
}

$(".btnNew").click(function () {
	emptySearch();
    $("#newAdd .modal-title").html("新增重点部位");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
    showUnitList();
});
//新增或编辑角色
$(".btnSure").click(function () {
	var UnitID = $("#UnitID").find("option:selected").val();	
	var ImportName = $("#ImportName").val();
    var ImportSite = $("#ImportSite").val();
    var Floor = $("#Floor").val();
    var Height= $("#Height").val();
    var BuildingStructure= $("#BuildingStructure").val();
    var BuildId= $("#BuildId").find("option:selected").val();
    var DTCount= $("#DTCount").val();
    var OutCount= $("#OutCount").val();
    var HZWXX= $("#HZWXX").val();
    var BuildArea= $("#BuildArea").val();
    var NHLevel= $("#NHLevel").find("option:selected").val();
    var FireInfo= $("#FireInfo").val();
    var IsDXFYZDX;
    var IsHZFSHRYSWD;
    var IsHZHSSD;
    var IsYFSHZ;
    var IsOther;
    if($("#IsDXFYZDX").get(0).checked){
    	IsDXFYZDX =1;
    }else{
    	IsDXFYZDX =0;
    }
    if($("#IsHZFSHRYSWD").get(0).checked){
    	IsHZFSHRYSWD =1;
    }else{
    	IsHZFSHRYSWD =0;
    }
    if($("#IsHZHSSD").get(0).checked){
    	IsHZHSSD =1;
    }else{
    	IsHZHSSD =0;
    }
    if($("#IsYFSHZ").get(0).checked){
    	IsYFSHZ =1;
    }else{
    	IsYFSHZ =0;
    }
    if($("#IsOther").get(0).checked){
    	IsOther =1;
    }else{
    	IsOther =0;
    }
    var FHBZSLQK = $("#FHBZSLQK").val();
    var Used = $("#Used").val();
    var IsBZP;
    var IsYHQHYJHHW;
    var IsFSP;
    var IsYSQT;
    var IsYDP;
    var IsZXWXW;
    var IsYRYT;
    var IsFSX;
    var IsYRGT;
    var IsQT;
    

    if($("#IsZXWXW").get(0).checked){
    	IsZXWXW =1;
    }else{
    	IsZXWXW =0;
    }
    if($("#IsYRYT").get(0).checked){
    	IsYRYT =1;
    }else{
    	IsYRYT =0;
    }
    if($("#IsFSX").get(0).checked){
    	IsFSX =1;
    }else{
    	IsFSX =0;
    }
    if($("#IsYRGT").get(0).checked){
    	IsYRGT =1;
    }else{
    	IsYRGT =0;
    }
    if($("#IsQT").get(0).checked){
    	IsQT =1;
    }else{
    	IsQT =0;
    }
    if($("#IsBZP").get(0).checked){
    	IsBZP =1;
    }else{
    	IsBZP =0;
    }
    if($("#IsYHQHYJHHW").get(0).checked){
    	IsYHQHYJHHW =1;
    }else{
    	IsYHQHYJHHW =0;
    }
    if($("#IsFSP").get(0).checked){
    	IsFSP =1;
    }else{
    	IsFSP =0;
    }
    if($("#IsYSQT").get(0).checked){
    	IsYSQT =1;
    }else{
    	IsYSQT =0;
    }
    if($("#IsYDP").get(0).checked){
    	IsYDP =1;
    }else{
    	IsYDP =0;
    }
    if(UnitID==""){
    	layer.msg("单位不能为空！")
    	return;
    } 
    if(BuildId == ""){
    	layer.msg("所在建筑不能为空！");
    	return;
    } 
    if (ImportName == "") {
        layer.msg("部位名称不能为空！");
        return;
    } 
    if(ImportSite == ""){
    	layer.msg("所在位置不能为空！");
        return;
    } 
    
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/keyParts/add';
    }else{
    	var url = baseUrl + '/keyParts/update';
    }
   
    var data = {
    		"ID":keyPartsId,
    	"ImportName":ImportName,
		"ImportSite": ImportSite, 
		"Floor": Floor, 
		"Height": Height, 
		"BuildingStructure": BuildingStructure,
		"BuildId": BuildId,
		"DTCount": DTCount,
		"OutCount": OutCount,
		"HZWXX": HZWXX,
		"BuildArea": BuildArea,
		"NHLevel": NHLevel,
		"FireInfo": FireInfo,
		"IsDXFYZDX": IsDXFYZDX,
		"IsHZFSHRYSWD": IsHZFSHRYSWD,
		"IsHZHSSD": IsHZHSSD,
		"IsYFSHZ": IsYFSHZ,
		"IsOther": IsOther,
		"FHBZSLQK": FHBZSLQK,
		"Used": Used,
		"IsBZP": IsBZP,
		"IsYHQHYJHHW": IsYHQHYJHHW,
		"IsFSP": IsFSP,
		"IsYSQT": IsYSQT,
		"IsYDP": IsYDP,
		"IsZXWXW": IsZXWXW,
		"IsYRYT": IsYRYT,
		"IsFSX": IsFSX,
		"IsYRGT": IsYRGT,
		"IsQT": IsQT,
		"UnitId":UnitID
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
                $('#keyPartsTable').bootstrapTable("refresh");
            } else {
            	layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

function emptySearch() {
		$("#ImportName").val("");
	    $("#ImportSite").val("");
	    $("#Floor").val("");
	    $("#Height").val("");
	    $("#BuildingStructure").val("");
	    $("#BuildId").val("");
	    $("#UnitID").val("");
	    $("#DTCount").val("");
	    $("#OutCount").val("");
	    $("#HZWXX").val("");
	    $("#BuildArea").val("");
	    $("#NHLevel").val("");
	    $("#FireInfo").val("");
	    $("#IsDXFYZDX").get(0).checked = false;
	    $("#IsHZFSHRYSWD").get(0).checked = false;
	    $("#IsHZHSSD").get(0).checked = false;
	    $("#IsYFSHZ").get(0).checked = false;
	    $("#IsOther").get(0).checked = false;
	    $("#Position").val("");
	    $("#Used").val("");
	    $("#IsBZP").get(0).checked = false;
	    $("#IsYHQHYJHHW").get(0).checked = false;
	    $("#IsFSP").get(0).checked = false;
	    $("#IsYSQT").get(0).checked = false;
	    $("#IsYDP").get(0).checked = false;
	    $("#IsZXWXW").get(0).checked = false;
	    $("#IsYRYT").get(0).checked = false;
	    $("#IsFSX").get(0).checked = false;
	    $("#IsYRGT").get(0).checked = false;
	    $("#IsQT").get(0).checked = false;

}
//编辑角色
function editKeyParts(_this) {
	keyPartsId = _this.id;
	showUnitList(_this.unitId);
	$("#ImportName").val(_this.importName);
    $("#ImportSite").val(_this.importSite);
    $("#Floor").val(_this.floor);
    $("#Height").val(_this.height);
    $("#BuildingStructure").val(_this.buildingStructure);
    getBuildList(_this.unitId);
	setTimeout(function(){
		$("#BuildId").val(_this.buildId);
	},100);
    $("#DTCount").val(_this.dtCount);
    $("#OutCount").val(_this.outCount);
    $("#HZWXX").val(_this.hzwxx);
    $("#BuildArea").val(_this.buildArea);
    $("#NHLevel").val(_this.nhlevel);
    $("#FireInfo").val(_this.fireInfo);
    $("#FHBZSLQK").val(_this.fhbzslqk);
    if(_this.isDXFYZDX ==1){
    	$("#IsDXFYZDX").get(0).checked = true;
    }else{
    	$("#IsDXFYZDX").get(0).checked = false;
    }
    if(_this.isHZFSHRYSWD == 1){
    	$("#IsHZFSHRYSWD").get(0).checked = true;	
    }else{
    	$("#IsHZFSHRYSWD").get(0).checked = false;
    }
    if(_this.isHZHSSD == 1){
    	$("#IsHZHSSD").get(0).checked = true;	
    }else{
    	$("#IsHZHSSD").get(0).checked = false;
    }
    if(_this.isYFSHZ == 1){
    	$("#IsYFSHZ").get(0).checked = true;	
    }else{
    	$("#IsYFSHZ").get(0).checked = false;
    }
    if(_this.isOther == 1){
    	$("#IsOther").get(0).checked = true;	
    }else{
    	$("#IsOther").get(0).checked = false;
    }
    $("#Position").val(_this.position);
    $("#Used").val(_this.used);
    if(_this.isBZP == 1){
    	$("#IsBZP").get(0).checked = true;	
    }else{
    	$("#IsBZP").get(0).checked = false;
    }
    if(_this.isYHQHYJHHW == 1){
    	$("#IsYHQHYJHHW").get(0).checked = true;	
    }else{
    	$("#IsYHQHYJHHW").get(0).checked = false;
    }
    if(_this.isFSP == 1){
    	$("#IsFSP").get(0).checked = true;	
    }else{
    	$("#IsFSP").get(0).checked = false;
    }
    if(_this.isYSQT == 1){
    	$("#IsYSQT").get(0).checked = true;	
    }else{
    	$("#IsYSQT").get(0).checked = false;
    }
    if(_this.isYDP == 1){
    	$("#IsYDP").get(0).checked = true;	
    }else{
    	$("#IsYDP").get(0).checked = false;
    }
    if(_this.isZXWXW == 1){
    	$("#IsZXWXW").get(0).checked = true;	
    }else{
    	$("#IsZXWXW").get(0).checked = false;
    }
    if(_this.isYRYT == 1){
    	$("#IsYRYT").get(0).checked = true;	
    }else{
    	$("#IsYRYT").get(0).checked = false;
    }
    if(_this.isFSX == 1){
    	$("#IsFSX").get(0).checked = true;	
    }else{
    	$("#IsFSX").get(0).checked = false;
    }
    if(_this.isYRGT == 1){
    	$("#IsYRGT").get(0).checked = true;	
    }else{
    	$("#IsYRGT").get(0).checked = false;
    }
    if(_this.isQT == 1){
    	$("#IsQT").get(0).checked = true;	
    }else{
    	$("#IsQT").get(0).checked = false;
    }
    $("#newAdd .modal-title").html("编辑重点部位");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('1');
    $("#newAdd").modal();
}


function emptySearchView() {
		$("#ImportNameView").val("View");
	    $("#ImportSiteView").val("View");
	    $("#FloorView").val("View");
	    $("#HeightView").val("View");
	    $("#BuildingStructureView").val("View");
	    $("#BuildIdView").val("View");
	    $("#DTCountView").val("View");
	    $("#OutCountView").val("View");
	    $("#HZWXXView").val("View");
	    $("#BuildAreaView").val("View");
	    $("#NHLevelView").val("View");
	    $("#FireInfoView").val("View");
	    $("#IsDXFYZDXView").get(0).checked = false;
	    $("#IsHZFSHRYSWDView").get(0).checked = false;
	    $("#IsHZHSSDView").get(0).checked = false;
	    $("#IsYFSHZView").get(0).checked = false;
	    $("#IsOtherView").get(0).checked = false;
	    $("#PositionView").val("View");
	    $("#UsedView").val("View");
	    $("#IsBZPView").get(0).checked = false;
	    $("#IsYHQHYJHHWView").get(0).checked = false;
	    $("#IsFSPView").get(0).checked = false;
	    $("#IsYSQTView").get(0).checked = false;
	    $("#IsYDPView").get(0).checked = false;
	    $("#IsZXWXWView").get(0).checked = false;
	    $("#IsYRYTView").get(0).checked = false;
	    $("#IsFSXView").get(0).checked = false;
	    $("#IsYRGTView").get(0).checked = false;
	    $("#IsQTView").get(0).checked = false;

}
//查看角色
function showKeyParts(_this) {
	emptySearchView();
	keyPartsId = _this.id;
	showUnitList(_this.unitId);
	$("#ImportNameView").val(_this.importName);
    $("#ImportSiteView").val(_this.importSite);
    $("#FloorView").val(_this.floor);
    $("#HeightView").val(_this.height);
    $("#BuildingStructureView").val(_this.buildingStructure);
    getBuildListView(_this.unitId);
	setTimeout(function(){
		$("#BuildIdView").val(_this.buildId);
	},100);
    $("#DTCountView").val(_this.dtCount);
    $("#OutCountView").val(_this.outCount);
    $("#HZWXXView").val(_this.hzwxx);
    $("#BuildAreaView").val(_this.buildArea);
    $("#NHLevelView").val(_this.nhlevel);
    $("#FireInfoView").val(_this.fireInfo);
    $("#FHBZSLQKView").val(_this.fhbzslqk);
    if(_this.isDXFYZDX ==1){
    	$("#IsDXFYZDXView").get(0).checked = true;
    }else{
    	$("#IsDXFYZDXView").get(0).checked = false;
    }
    if(_this.isHZFSHRYSWD == 1){
    	$("#IsHZFSHRYSWDView").get(0).checked = true;	
    }else{
    	$("#IsHZFSHRYSWDView").get(0).checked = false;
    }
    if(_this.isHZHSSD == 1){
    	$("#IsHZHSSDView").get(0).checked = true;	
    }else{
    	$("#IsHZHSSDView").get(0).checked = false;
    }
    if(_this.isYFSHZ == 1){
    	$("#IsYFSHZView").get(0).checked = true;	
    }else{
    	$("#IsYFSHZView").get(0).checked = false;
    }
    if(_this.isOther == 1){
    	$("#IsOtherView").get(0).checked = true;	
    }else{
    	$("#IsOtherView").get(0).checked = false;
    }
    $("#PositionView").val(_this.position);
    $("#UsedView").val(_this.used);
    if(_this.isBZP == 1){
    	$("#IsBZPView").get(0).checked = true;	
    }else{
    	$("#IsBZPView").get(0).checked = false;
    }
    if(_this.isYHQHYJHHW == 1){
    	$("#IsYHQHYJHHWView").get(0).checked = true;	
    }else{
    	$("#IsYHQHYJHHWView").get(0).checked = false;
    }
    if(_this.isFSP == 1){
    	$("#IsFSPView").get(0).checked = true;	
    }else{
    	$("#IsFSPView").get(0).checked = false;
    }
    if(_this.isYSQT == 1){
    	$("#IsYSQTView").get(0).checked = true;	
    }else{
    	$("#IsYSQTView").get(0).checked = false;
    }
    if(_this.isYDP == 1){
    	$("#IsYDPView").get(0).checked = true;	
    }else{
    	$("#IsYDPView").get(0).checked = false;
    }
    if(_this.isZXWXW == 1){
    	$("#IsZXWXWView").get(0).checked = true;	
    }else{
    	$("#IsZXWXWView").get(0).checked = false;
    }
    if(_this.isYRYT == 1){
    	$("#IsYRYTView").get(0).checked = true;	
    }else{
    	$("#IsYRYTView").get(0).checked = false;
    }
    if(_this.isFSX == 1){
    	$("#IsFSXView").get(0).checked = true;	
    }else{
    	$("#IsFSXView").get(0).checked = false;
    }
    if(_this.isYRGT == 1){
    	$("#IsYRGTView").get(0).checked = true;	
    }else{
    	$("#IsYRGTView").get(0).checked = false;
    }
    if(_this.isQT == 1){
    	$("#IsQTView").get(0).checked = true;	
    }else{
    	$("#IsQTView").get(0).checked = false;
    }
    $("#newAddView .modal-title").html("查看重点部位");
    $("input[name=indexId][type=hidden]").val("");
    $("#newAddView").modal();
}


//删除角色
function deleteKeyParts(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/keyParts/remove';
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
                data: {"ID": indexId},
                url: url,
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        $('#keyPartsTable').bootstrapTable("refresh");
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
    $('#keyPartsTable').bootstrapTable({
        url: baseUrl + '/keyParts/getList',         //请求后台的URL（*）
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
                ImportName: $("#importName").val(),
                ImportSite: $("#importSite").val(),
                unitName:$("#unitName").val(),
                UnitId:$("#unitId").val()
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
                field: 'importName',
                title: '部位名称'
            },
            {
                field: 'importSite',
                title: '所在位置',
            },
            {
                field: 'used',
                title: '使用功能'
            },
            {
                field: 'buildingStructure',
                title: '建筑结构'
            },
            {
                field: 'buildArea',
                title: '建筑面积 m²'
            },
            {
                field: 'nhlevel',
                title: '耐火等级'
            },
            {
                field: 'dtCount',
                title: '电梯个数'
            },
            {
                field: 'outCount',
                title: '出口个数'
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
                        editKeyParts(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteKeyParts(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showKeyParts(row);
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
	var unit = $("#unitId").val();
	for(var i = 0;i<unitList.length;i++){
		if(unitID && unitID==unitList[i].id){
			option = '<option selected value="'+unitList[i].id+'" >'+unitList[i].unitname+'</option>';
		}else if(unit && unit == unitList[i].id){
			option = '<option selected value="'+unitList[i].id+'" >'+unitList[i].unitname+'</option>';
			getBuildList(unit);
		}else{
			option = a.replace("%unitID%",unitList[i].id);
			option = option.replace("%unitName%",unitList[i].unitname);
		}
		wrap+=option;
	}
	$("#UnitID").html(wrap);
	$("#UnitID").comboSelect();
	if(unit != null && unit != ''){
		$("#UnitID").siblings("input").attr('disabled','disabled');
		$("#UnitID").siblings(".combo-arrow").hide();
	}
	$("#UnitIDView").html(wrap);
	$("#UnitIDView").comboSelect();
	$("#UnitIDView").siblings("input").attr('disabled','disabled');
	$("#UnitIDView").siblings(".combo-arrow").hide();
}

$("#UnitID").bind("change",function(){
	emptyBuildList();
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
            	var a = '<option value="%buildId%">%buildName%</option>';
            	var option;
            	var wrap='<option value="">--请选择--</option>';
            		for(var i = 0;i<d.obj.length;i++){
            			option = a.replace("%buildId%",d.obj[i].id);
            			option = option.replace("%buildName%",d.obj[i].buildingName);
            			wrap+=option;
            		}
            		
            		$("#BuildId").html(wrap);
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
	};
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
            	var a = '<option value="%buildId%">%buildName%</option>';
            	var option;
            	var wrap='<option value="">--请选择--</option>';
            		for(var i = 0;i<d.obj.length;i++){
            			option = a.replace("%buildId%",d.obj[i].id);
            			option = option.replace("%buildName%",d.obj[i].buildingName);
            			wrap+=option;
            		}
            		$("#BuildIdView").html(wrap);
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
	
}
function emptyBuildList(){
	$("#BuildId").empty();
	$("#BuildId").html('<option value="">--请选择--</option>');
}
