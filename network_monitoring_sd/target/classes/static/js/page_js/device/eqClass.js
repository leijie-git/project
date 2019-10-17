$(function () {
	//主页面查询
	mainSearch();
	//initSelect();
	getSystemList();
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
        $("#newEmploy .modal-title").html("新增设备类型");
        $("#newEmploy .btnSure").show();
        $("#continue").show();
        
        $("#newEqClass-classcode").val("");
        $("#newEqClass-classname").val("");
        $("#newEqClass-classtype").val("");
        $("#newEqClass-areacode").val("");
        $("#newEqClass-monitornum").val("");
        $("#newEqClass-status").val("");
        $("#newEqClass-remark").val("");
        $("#newEqClass-cycle").val("");
        $("#newEqClass-cycleunit").val("");
        $("#newEqClass-frequency").val("");
        $("#newEqClass-cycleinfo").val("");
        $("#newEqClass-mincycleinfo").val("");
        $("#newEqClass-inspectcycle").val("");
        $("#newEqClass-inspectcycleunit").val("");
        $("#newEqClass-inspectfrequency").val("");
        $("#newEqClass-eqsystemid").val("");
        $("#picurls").attr("file_string","");
        $("#loadImg").html('<li class="modalImg"><span>请选择上传图片</span></li>');
        
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
    	var classcode = $("#newEqClass-classcode").val();
    	var classname = $("#newEqClass-classname").val();
        var classtype = $("#newEqClass-classtype").val();
        var areacode = $("#newEqClass-areacode").val();
        var monitornum = $("#newEqClass-monitornum").val();
        var status = $("#newEqClass-status").val();
        var remark = $("#newEqClass-remark").val();
        var cycle = $("#newEqClass-cycle").val();
        var cycleunit = $("#newEqClass-cycleunit").val();
        var frequency = $("#newEqClass-frequency").val();
        var cycleinfo = $("#newEqClass-cycleinfo").val();
        var mincycleinfo = $("#newEqClass-mincycleinfo").val();
        var inspectcycle = $("#newEqClass-inspectcycle").val();
        var inspectcycleunit = $("#newEqClass-inspectcycleunit").val();
        var inspectfrequency = $("#newEqClass-inspectfrequency").val();
        var eqsystemid = $("#newEqClass-eqsystemid").val();
        var classimg = $("#picurls").attr("file_string");
        var id = $("#indexId").val();
    	
    	

        if (classcode == "") {
        	layer.msg("类型编号不能为空！");
        	return;
        }
        if (classname == "") {
        	layer.msg("类型不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/eqClass/addEqClass";
            var data = {
            		classcode:classcode,
            		classname:classname,
            		classtype:classtype,
            		areacode:areacode,
            		monitornum:monitornum,
            		status:status,
            		remark:remark,
            		cycle:cycle,
            		cycleunit:cycleunit,
            		frequency:frequency,
            		cycleinfo:cycleinfo,
            		mincycleinfo:mincycleinfo,
            		inspectcycle:inspectcycle,
            		inspectcycleunit:inspectcycleunit,
            		inspectfrequency:inspectfrequency,
            		eqsystemid:eqsystemid,
            		classimg:classimg
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
            var url = baseUrl + "/eqClass/updateEqClass";
            var data = {
            		id:id,
            		classcode:classcode,
            		classname:classname,
            		classtype:classtype,
            		areacode:areacode,
            		monitornum:monitornum,
            		status:status,
            		remark:remark,
            		cycle:cycle,
            		cycleunit:cycleunit,
            		frequency:frequency,
            		cycleinfo:cycleinfo,
            		mincycleinfo:mincycleinfo,
            		inspectcycle:inspectcycle,
            		inspectcycleunit:inspectcycleunit,
            		inspectfrequency:inspectfrequency,
            		eqsystemid:eqsystemid,
            		classimg:classimg
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
    
    $("#remove").click(function () {
        $("#select1 option:selected").appendTo("#select2");
    });

    $("#remove_all").click(function () {
        $("#select1 option").appendTo("#select2");
    });

    $("#add").click(function () {
        $("#select2 option:selected").appendTo("#select1");
    });

    $("#add_all").click(function () {
        $("#select2 option").appendTo("#select1");
    });

    $("#select1").dblclick(function () {
        $("#select1 option:selected").appendTo("#select2");

    });

    $("#select2").dblclick(function () {
        $("#select2 option:selected").appendTo("#select1");
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
    $("#deviceno").val('');
}
// 编辑
function editBaseInfo(_this) {
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑设备类型");
    $("#newEmploy .btnSure").show();
    $("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    showSystemLists();
    
    $("#newEqClass-classcode").val(_this.classcode);
    $("#newEqClass-classname").val(_this.classname);
    $("#newEqClass-classtype").val(_this.classtype);
    $("#newEqClass-areacode").val(_this.areacode);
    $("#newEqClass-monitornum").val(_this.monitornum);
    $("#newEqClass-status").val(_this.status);
    $("#newEqClass-remark").val(_this.remark);
    $("#newEqClass-cycle").val(_this.cycle);
    $("#newEqClass-cycleunit").val(_this.cycleunit);
    $("#newEqClass-frequency").val(_this.frequency);
    $("#newEqClass-cycleinfo").val(_this.cycleinfo);
    $("#newEqClass-mincycleinfo").val(_this.mincycleinfo);
    $("#newEqClass-inspectcycle").val(_this.inspectcycle);
    $("#newEqClass-inspectcycleunit").val(_this.inspectcycleunit);
    $("#newEqClass-inspectfrequency").val(_this.inspectfrequency);
    $("#newEqClass-eqsystemid").val(_this.eqsystemid);
    
    var classimg = _this.classimg;
    if(classimg){
    	$("#picurls").attr("file_string",classimg);
    	$("#loadImg").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + classimg + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls").attr("file_string","");
    	$("#loadImg").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	
	
	$("#newEqClassView-classcode").val(_this.classcode);
    $("#newEqClassView-classname").val(_this.classname);
    $("#newEqClassView-classtype").val(_this.classtype);
    $("#newEqClassView-areacode").val(_this.areacode);
    $("#newEqClassView-monitornum").val(_this.monitornum);
    $("#newEqClassView-status").val(_this.status);
    $("#newEqClassView-remark").val(_this.remark);
    $("#newEqClassView-cycle").val(_this.cycle);
    $("#newEqClassView-cycleunit").val(_this.cycleunit);
    $("#newEqClassView-frequency").val(_this.frequency);
    $("#newEqClassView-cycleinfo").val(_this.cycleinfo);
    $("#newEqClassView-mincycleinfo").val(_this.mincycleinfo);
    $("#newEqClassView-inspectcycle").val(_this.inspectcycle);
    $("#newEqClassView-inspectcycleunit").val(_this.inspectcycleunit);
    $("#newEqClassView-inspectfrequency").val(_this.inspectfrequency);
    $("#newEqClassView-eqsystemid").val(_this.eqsystemid);
    
    
    var classimg = _this.classimg;
    if(classimg){
    	$("#loadImgView").html(
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + classimg + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#loadImgView").html("<li class='modalImg'><span>暂无图片</span></li>");
    }
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/eqClass/deleteEqClass",
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
        url: baseUrl + '/eqClass/pageList',         //请求后台的URL（*）
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
                classname: $("#classname").val()
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
                field: 'classcode',
                title: '类型编号'
            },
            {
            	field: 'classname',
            	title: '类型名称'
            },
            {
            	field: 'eqsystemname',
            	title: '系统名称'
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

/*
function initSelect(){
	    $.ajax({
    	url: baseUrl + "/netDevice/eqsystemidSelect",
    	type: "post",
    	dataType: "json",
    	success: function (data) {
    		if(data.success){
    			for(var i=0;i<data.obj.length;i++){
    				$("#newEqClass-eqsystemid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].eqsystemname+'</option>');
    				$("#newEqClassView-eqsystemid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].eqsystemname+'</option>');
    			}
    		}
    	}
    })

}*/


function doChangeProject() {
	uploadOne("picurls", "loadImg", "photoCover", false);
};


//获取新增页系统列表
function getSystemList(){
	$.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/eqClass/getSystemList",
        dataType: "json",
        success: function (d) {
            if (d.success) {
            	SystemList = d.obj;
            	showSystemLists();
            	//showSystemList();
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}

//填充系统下拉框（主页面）
function showSystemLists(){
	var a = '<option value="%SystemIds%">%SystemNames%</option>';
	var option;
	var wrap='<option value="">--请选择--</option>';
	if(SystemList){
		for(var i = 0;i<SystemList.length;i++){
			option = a.replace("%SystemIds%",SystemList[i].id);
			option = option.replace("%SystemNames%",SystemList[i].eqsystemname);
			wrap+=option;
		}
		$("#newEqClass-eqsystemid").html(wrap);
		$("#newEqClassView-eqsystemid").html(wrap);
	}
}




