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
var DangerousId;
var unitList;
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
    $("#dangerousTable").bootstrapTable('destroy');
    mainSearch();
});

//查询条件查询
$(".btnSearch").click(function () {
    $("#dangerousTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
	emptySearchMsg();
    $("#dangerousTable").bootstrapTable('destroy');
    mainSearch();
});
function emptySearchMsg(){
	 $("#dangerousName").val("");
	 $("#unitName").val("");
     $("#site").val("");
     $("#jsfzr").val("");
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
$(".btnNew").click(function () {
	emptySearch();
	showUnitList();
	$("#IsXFBS0").prop("checked",false);
	$("#IsXFBS1").prop("checked",false);
    $("#newAdd .modal-title").html("新增危险品");
    $("#picurls-photo").attr("file_string","");
    $("#loadImg-photo").html('<li class="modalImg"><span>请选择上传图片</span></li>');
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('0');
    $("#preId").val('');
   

});
//新增或编辑角色
$(".btnSure").click(function () {
	
	var DangerousName = $("#DangerousName").val();
    var DangerousCount = $("#DangerousCount").val();
    var CountUnit = $("#CountUnit").val();
    var Site= $("#Site").val();
    var DangerousUsed= $("#DangerousUsed").val();
    var DangerousLevel= $("#DangerousLevel").find("option:selected").val();
    var DangerousCZCS= $("#DangerousCZCS").val();
    var JSFZR= $("#JSFZR").val();
    var JSFZRDH= $("#JSFZRDH").val();
    var AQGLR= $("#AQGLR").val();
    var AQGLRDH= $("#AQGLRDH").val();
    var photo = $("#picurls-photo").attr("file_string");
    var IsXFBS = $("input:radio[name='IsXFBS']:checked").val();
    	console.log("IsXFBS"+IsXFBS);
    var UnitID = $("#UnitID").find("option:selected").val();	
    var OperateDesc= $("#OperateDesc").val(); 
    var DangerousType= $("#DangerousType").val();
    var DangerousState= $("#DangerousState").find("option:selected").val(); 

    if(UnitID==""){
    	layer.msg("单位不能为空！")
    	return;
    } 
    if (DangerousName == "") {
        layer.msg("危险品名称不能为空！");
        return;
    }
    if(Site == ""){
    	layer.msg("存储位置不能为空！");
        return;
    } 
    if(AQGLR == ""){
    	layer.msg("安全管理人不能为空！");
        return;
    } 
    if(AQGLRDH == ""){
    	layer.msg("安全管理人电话不能为空！");
        return;
    }
    if(!(/^1[34578]\d{9}$/.test(AQGLRDH))){ 
    	layer.msg("安全管理人电话号码有误，请重填");  
        return; 
    }
    if(DangerousType == ""){
    	layer.msg("危险品类型不能为空！");
        return;
    }
    var edit = $("#edit").val();
    if(edit =="0"){
    	var url = baseUrl + '/dangerous/add';
    }else{
    	var url = baseUrl + '/dangerous/update';
    }
   
    var data = {
    		"ID":DangerousId,
    	"DangerousName":DangerousName,
		"DangerousCount": DangerousCount, 
		"CountUnit": CountUnit, 
		"Site": Site, 
		"DangerousLevel": DangerousLevel,
		"DangerousUsed": DangerousUsed,
		"DangerousCZCS": DangerousCZCS,
		"JSFZR": JSFZR,
		"JSFZRDH": JSFZRDH,
		"AQGLR": AQGLR,
		"AQGLRDH": AQGLRDH,
		"IsXFBS": IsXFBS,
		"OperateDesc": OperateDesc,
		"DangerousType": DangerousType,
		"DangerousState": DangerousState,
		"DangerousImageName": photo,
		"UnitID":UnitID
		
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
                $('#dangerousTable').bootstrapTable("refresh");
            } else {
            	layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

function emptySearch() {
	$("#DangerousName").val("");
    $("#DangerousCount").val("");
    $("#CountUnit").val("");
    $("#Site").val("");
    $("#UnitID").val("");
    $("#DangerousUsed").val("");
    $("#DangerousLevel").val(0);
    $("#DangerousCZCS").val("");
    $("#JSFZR").val("");
    $("#JSFZRDH").val("");
    $("#AQGLR").val("");
    $("#AQGLRDH").val("");
    $("#IsDXFYZDX").val("");
    $("#OperateDesc").val(""); 
    $("#DangerousType").val("");
    $("#DangerousState").val(""); 
}

//编辑角色
function editDangerous(_this) {
	emptySearch();
	showUnitList(_this.unitID);
	DangerousId = _this.id;
	if(_this.isXFBS ==1){
		$("#IsXFBS0").prop("checked",false);
		$("#IsXFBS1").prop("checked",true);
	}else if(_this.isXFBS ==0){
		$("#IsXFBS1").prop("checked",false);
		$("#IsXFBS0").prop("checked",true);
	}
	$("#DangerousName").val(_this.dangerousName);
    $("#DangerousCount").val(_this.dangerousCount);
    $("#CountUnit").val(_this.countUnit);
    $("#Site").val(_this.site);
    $("#DangerousUsed").val(_this.dangerousUsed);
    $("#DangerousLevel").val(_this.dangerousLevel);
    $("#DangerousCZCS").val(_this.dangerousCZCS);
    $("#JSFZR").val(_this.jsfzr);
    $("#JSFZRDH").val(_this.jsfzrdh);
    $("#AQGLR").val(_this.aqglr);
    $("#AQGLRDH").val(_this.aqglrdh);
    $("#OperateDesc").val(_this.operateDesc); 
    $("#DangerousType").val(_this.dangerousType);
    $("#DangerousState").val(_this.dangerousState); 
    var photo = _this.dangerousImageName;
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
    $("#newAdd .modal-title").html("编辑危险品");
    $("input[name=indexId][type=hidden]").val("");
    $("#edit").val('1');
    $("#newAdd").modal();
}

function showDangerous(_this) {
	emptySearch();
	showUnitList(_this.unitID);
	DangerousId = _this.id;
	if(_this.isXFBS ==1){
		$("input[name='IsXFBS'][value='1']").attr("checked",true);
	}else{
		$("input[name='IsXFBS'][value='0']").attr("checked",true);
	}
	$("#DangerousNameView").val(_this.dangerousName);
    $("#DangerousCountView").val(_this.dangerousCount);
    $("#CountUnitView").val(_this.countUnit);
    $("#SiteView").val(_this.site);
    $("#UnitIDView").val(_this.unitID);
    $("#DangerousUsedView").val(_this.dangerousUsed);
    $("#DangerousLevelView").val(_this.dangerousLevel);
    $("#DangerousCZCSView").val(_this.dangerousCZCS);
    $("#JSFZRView").val(_this.jsfzr);
    $("#JSFZRDHView").val(_this.jsfzrdh);
    $("#AQGLRView").val(_this.aqglr);
    $("#AQGLRDHView").val(_this.aqglrdh);
    $("#OperateDescView").val(_this.operateDesc); 
    $("#DangerousTypeView").val(_this.dangerousType);
    $("#DangerousStateView").val(_this.dangerousState); 
    var photo = _this.dangerousImageName;
	if(photo){
    	$("#picurls-photoView").attr("file_string",photo);
    	$("#loadImg-photoView").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photo + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photoView").attr("file_string","");
    	$("#loadImg-photoView").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
    $("#newAddView .modal-title").html("查看危险品");
    $("input[name=indexId][type=hidden]").val("");
    $("#newAddView").modal();
}


//删除角色
function deleteDangerous(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/dangerous/remove';
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
                        $('#dangerousTable').bootstrapTable("refresh");
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
    $('#dangerousTable').bootstrapTable({
        url: baseUrl + '/dangerous/getList',         //请求后台的URL（*）
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
                DangerousName: $("#dangerousName").val(),
                Site:$("#site").val(),
                JSFZR:$("#jsfzr").val(),
                unitName: $("#unitName").val(),
                UnitID : $("#unitId").val()
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
                field: 'dangerousName',
                title: '危险品名称'
            },
            {
                field: 'site',
                title: '存储位置'
            },
            {
                field: 'dangerousLevel',
                title: '危险品等级 ', 
                sortable: true,
            	formatter: function (dangerousLevel) {
            		var value = dangerousLevel;
        			if (value == '0') {
        				return '--请选择--';
        			} else if(value == '1')  {
        				return '一级';
        			} else if(value == '2')  {
        				return '二级';
        			} else if(value == '3')  {
        				return '三级';
        			} 
                }
            },
            {
                field: 'jsfzr',
                title: '技术负责人'
            },
            {
                field: 'aqglr',
                title: '安全管理人'
            },
            {
                field: 'dangerousState',
                title: '状态',
                sortable: true,
            	formatter: function (dangerousState) {
            		var value = dangerousState;
        			if (value == '0') {
        				return '气态';
        			} else if(value == '1')  {
        				return '液态';
        			} else if(value == '2')  {
        				return '固态';
        			} 
                }
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
                        editDangerous(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteDangerous(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showDangerous(row);
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
	$("#UnitIDView").siblings(".combo-arrow").hide();
}

function doChangeProjectphoto() {
	uploadOne("picurls-photo", "loadImg-photo", "photoCover-photo", false);
};