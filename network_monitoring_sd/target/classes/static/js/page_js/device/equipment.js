var selectList;
$(function () {
	//主页面查询
	mainSearch();
	initJeDate();

    showSelectList();
	
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
        deleteEquipment();
    });


    $(".newAdd").click(function () {
    	$("#newEquipment").modal("show");
        //$(".password").show();
        showSelectList();
        emptySearch();
        $("#newEquipment-extinguisherType").siblings("#newEquipment_extinguisherType_chosen").find(".chosen-single span").html("");
        $("#newEquipment .modal-title").html("新增灭火器");
        $("#newEquipment .btnSure").show();
        //$("#continue").show();
        $("#editId").val("0");
        $("#indexId").val("");

        $("#newEquipment-extinguisherPosition").val("");
        $("#newEquipment-productDate").val("");
        $("#newEquipment-jcDate").val("");
        $("#newEquipment-fillDate").val("");
        $("#newEquipment-validityDate").val("");
        $("#newEquipment-extinguisherNum").val("");
        $("#newEquipment-productUnitname").val("");
        $("#newEquipment-productUnitphone").val("");
        $("#newEquipment-expireDate").val("");
        $("#newEquipment-remark").val("");
        $("#newEquipment-extinguisherCode").val("");
    });
    
    
    // 新增或编辑
    $(".btnSure").click(function(){
    	var editId = $("#editId").val();
        var id = $("#indexId").val();
        
        var extinguisherType = $("#newEquipment-extinguisherType option:selected").text();
        var extinguisherPosition = $("#newEquipment-extinguisherPosition").val();
        var productDate = $("#newEquipment-productDate").val();
        var jcDate = $("#newEquipment-jcDate").val();
        var fillDate = $("#newEquipment-fillDate").val();
        var validityDate = $("#newEquipment-validityDate").val();
        //var extinguisherNum = $("#newEquipment-extinguisherNum").val();
        var productUnitname = $("#newEquipment-productUnitname").val();
        var productUnitphone = $("#newEquipment-productUnitphone").val();
        var expireDate = $("#newEquipment-expireDate").val();
        var remark = $("#newEquipment-remark").val();
        var extinguisherCode = $("#newEquipment-extinguisherCode").val();
        

        if (extinguisherType == "") {
        	layer.msg("灭火剂类型不能为空！");
        	return;
        }
        if(extinguisherCode == ""){
        	layer.msg("灭火器编号不能为空！");
        	return;
        }
        if (extinguisherPosition == "") {
        	layer.msg("灭火器位置不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/equipment/addExtinguisher";
            var data = {
            		extinguisherType:extinguisherType,
            		extinguisherPosition:extinguisherPosition,
            		productDate:productDate,
            		jcDate:jcDate,
            		fillDate:fillDate,
            		validityDate:validityDate,
            		extinguisherCode:extinguisherCode,
            		productUnitname:productUnitname,
            		productUnitphone:productUnitphone,
            		expireDate:expireDate,
            		remark:remark
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
                        $("#newEquipment").modal("hide");
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                }
            });
        } else {
            var url = baseUrl + "/equipment/updateExtinguisher";
            var data = {
            		id:id,
            		extinguisherType:extinguisherType,
            		extinguisherPosition:extinguisherPosition,
            		productDate:productDate,
            		jcDate:jcDate,
            		fillDate:fillDate,
            		validityDate:validityDate,
            		extinguisherCode:extinguisherCode,
            		productUnitname:productUnitname,
            		productUnitphone:productUnitphone,
            		expireDate:expireDate,
            		remark:remark
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
                        layer.msg("修改成功");
                        $("#userTable").bootstrapTable("refresh");
                        $("#newEquipment").modal("hide");
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
    emptySearch();
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
    $("#extinguisherType").val('');
    $("#extinguisherPosition").val('');
    $("#productUnitName").val('');
    $("#searchContent").val("");
}
// 编辑
function editEquipment(_this) {
	
    
	$("#newEquipment").modal("show");

    $("#newEquipment .modal-title").html("编辑单位绑定数据");
    $("#newEquipment .btnSure").show();
    $("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");

    showSelectList(_this.extinguisherType);
    $("#newEquipment-extinguisherType").siblings("input").val(_this.extinguisherType);
    $("#newEquipment-extinguisherPosition").val(_this.extinguisherPosition);
    $("#newEquipment-productDate").val(_this.productDate);
    $("#newEquipment-jcDate").val(_this.jcDate);
    $("#newEquipment-fillDate").val(_this.fillDate);
    $("#newEquipment-validityDate").val(_this.validityDate);
    $("#newEquipment-extinguisherNum").val(_this.extinguisherNum);
    $("#newEquipment-productUnitname").val(_this.productUnitname);
    $("#newEquipment-productUnitphone").val(_this.productUnitphone);
    $("#newEquipment-expireDate").val(_this.expireDate);
    $("#newEquipment-remark").val(_this.remark);
    $("#newEquipment-extinguisherCode").val(_this.extinguisherCode);
    
}
//查看
function viewEquipment(_this) {
	$("#newEquipmentView").modal("show");
    showSelectList(_this.extinguisherType);
	$("#newEquipment .modal-title").html("查看单位绑定数据");
    $("#newEquipment .btnSure").show();
    $("#continue").hide();

    $("#newEquipmentView-extinguisherType").val(_this.extinguisherType);
    $("#newEquipmentView-extinguisherPosition").val(_this.extinguisherPosition);
    $("#newEquipmentView-productDate").val(_this.productDate);
    $("#newEquipmentView-jcDate").val(_this.jcDate);
    $("#newEquipmentView-fillDate").val(_this.fillDate);
    $("#newEquipmentView-validityDate").val(_this.validityDate);
    $("#newEquipmentView-extinguisherNum").val(_this.extinguisherNum);
    $("#newEquipmentView-productUnitname").val(_this.productUnitname);
    $("#newEquipmentView-productUnitphone").val(_this.productUnitphone);
    $("#newEquipmentView-expireDate").val(_this.expireDate);
    $("#newEquipmentView-remark").val(_this.remark);
    $("#newEquipmentView-extinguisherCode").val(_this.extinguisherCode);

}

function deleteEquipment(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中灭火器？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/equipment/deleteExtinguisher",
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
                        layer.msg("删除成功");
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                        layer.msg("删除失败");
                    }
                },
                error: function (data) {
                	layer.msg(d.msg);
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
        url: baseUrl + '/equipment/getExtinguisherList',         //请求后台的URL（*）
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
                extinguisherType: $("#extinguisherType").val(),
                extinguisherPosition: $("#extinguisherPosition").val(),
                productUnitname: $("#productUnitName").val()
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
            	field: 'extinguisherType',
            	title: '灭火剂类型'
            },
            {
            	field: 'extinguisherPosition',
            	title: '灭火器位置'
            },
            {
            	field: 'extinguisherCode',
            	title: '灭火器编号'
            },
            {
            	field: 'fillDate',
            	title: '填充时间'
            },
            {
            	field: 'validityDate',
            	title: '报废时间'
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
                    	editEquipment(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteEquipment(row);
                    },
                    'click .view': function (e, value, row, index) {
                        viewEquipment(row);
                    }
                }
            }
        ]
    });
}


//初始化日期选择器
function initJeDate(){
	//日期选择控件
	$("#newEquipment-productDate").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
    //日期选择控件
    $("#newEquipment-jcDate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })
    //日期选择控件
    $("#newEquipment-fillDate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })
    //日期选择控件
    $("#newEquipment-validityDate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })
    //日期选择控件
    $("#newEquipment-expireDate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })
}

//导入弹窗按钮
function addressExport(){
	$("#importBuilding").modal();
}
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
        url: baseUrl + "/equipment/importExcel",
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
            $("#importBuilding").modal("hide");
            $("#exportPage").modal("hide");
            $('#userTable').bootstrapTable('refresh');
        }
    })
})

//获取灭火器类型
function showSelectList(extinguisherType){

    $("#newEquipment-extinguisherType").empty();

    // var a = '<option value="%unitID%" >%unitName%</option>';
    // var option;
    // var wrap='<option value="">--搜索--</option>';
    // for(var i = 0;i<selectList.length;i++){
    //     if(unitID && unitID==selectList[i].id){
    //         option = '<option selected value="'+selectList[i].id+'" >'+selectList[i].fireName+'</option>';
    //     }else{
    //         option = a.replace("%unitID%",selectList[i].id);
    //         option = option.replace("%unitName%",selectList[i].fireName);
    //     }
    //     wrap+=option;
    // }
    // $("#newEquipment-extinguisherType").html(wrap);
    // $("#newEquipment-extinguisherType").comboSelect();
    // $("#newEquipmentView-extinguisherType").siblings("input").attr('disabled','disabled');
    // $("#newEquipmentView-extinguisherType").siblings(".combo-arrow").hide();

    $.ajax({
        url: baseUrl + "/code/getListByGroupKey",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            codeGroupKey: "FireExtinguisher"
        },
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.success) {
                for (var i = 0; i < data.obj.length; i++) {
                    if(extinguisherType == data.obj[i].codename){
                        $("#newEquipment-extinguisherType").append(
                            '<option value="' + data.obj[i].codeid + '" selected>' +
                            data.obj[i].codename + '</option>');
                        $("#newEquipmentView-extinguisherType").append(
                            '<option value="' + data.obj[i].codeid + '" selected>' +
                            data.obj[i].codename + '</option>');
                        $("#newEquipment_extinguisherType_chosen").find("a:eq(0)").find("span").html(data.obj[i].codename);
                    }else{
                        $("#newEquipment-extinguisherType").append(
                            '<option value="' + data.obj[i].codeid + '">' +
                            data.obj[i].codename + '</option>');
                        $("#newEquipmentView-extinguisherType").append(
                            '<option value="' + data.obj[i].codeid + '">' +
                            data.obj[i].codename + '</option>');
                    }
                }
            }
        }
    });
}

function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}

// 批量生成二维码
$(".moreCode").click(function () {
    var checked = $("#userTable").bootstrapTable('getSelections');
    var valArr = [];    //灭火器id集合
    var extinguisherTypeArr = [];   //灭火器类型集合
    var extinguisherPositionArr = [];   //灭火器位置集合
    var extinguisherCodeArr = [];   //灭火器编号集合
    var productDateArr = [];   //灭火器出厂时间集合
    var fillDateArr = [];   //灭火器填充时间集合
    var jcDateArr = [];   //灭火器检测时间集合
    var validityDateArr = [];   //灭火器报废时间集合
    var expireDateArr = [];   //灭火器药剂到期时间集合
    var productUnitnameArr = [];   //灭火器生产单位名称集合
    var productUnitphoneArr = [];   //灭火器生产单位电话集合
    if(checked.length > 0){
        $(checked).each(function (i, item) {
            valArr[i] = item.id;
            extinguisherTypeArr[i] = item.extinguisherType;
            extinguisherCodeArr[i] = item.extinguisherCode;
            extinguisherPositionArr[i] = item.extinguisherPosition;
            productDateArr[i] = item.productDate;
            fillDateArr[i] = item.fillDate;
            jcDateArr[i] = item.jcDate;
            validityDateArr[i] = item.validityDate;
            expireDateArr[i] = item.expireDate;
            productUnitnameArr[i] = item.productUnitname;
            productUnitphoneArr[i] = item.productUnitphone;
        })
    }else{
        layer.msg("请选择一条记录！");
        return;
    }

    var html = "";
    var infoHtml = "";
    // var host = location.host;
    // var display = [];
    for(var i=0;i<valArr.length;i++){

        // display.push( '灭火器信息</div> ' +
        //     'id : '+ valArr[i] +'' +
        //     '灭火器类型 : '+ extinguisherTypeArr[i] +'' +
        //     '灭火器编号 : '+ extinguisherCodeArr[i] +'' +
        //     '灭火器位置 : '+ extinguisherPositionArr[i] +'' +
        //     '出厂时间 : '+ productDateArr[i] +'' +
        //     '填充时间 : '+ fillDateArr[i] +'' +
        //     '检测时间 : '+ jcDateArr[i] +'' +
        //     '报废时间 : '+ validityDateArr[i] +'' +
        //     '药剂到期时间 : '+ expireDateArr[i] +'' +
        //     '生产单位名称 : '+ productUnitnameArr[i] +'' +
        //     '生产单位电话 : '+ valArr[i] +''
        // );

        // display.push( '灭火器信息 id : '+ valArr[i] +'灭火器类型 : '+ extinguisherTypeArr[i] +'灭火器编号 : '+ extinguisherCodeArr[i] +'灭火器位置 : '+ extinguisherPositionArr[i] +'出厂时间 : '+ productDateArr[i] +'填充时间 : '+ fillDateArr[i] +'检测时间 : '+ jcDateArr[i] +'报废时间 : '+ validityDateArr[i] +' 药剂到期时间 : '+ expireDateArr[i] +'生产单位名称 : '+ productUnitnameArr[i] +' 生产单位电话 : '+ valArr[i]);

        infoHtml = "http://"+ location.host +"/html/View/extinguisher.html?id="+ valArr[i];

        $("#pic").empty();
        var qrcode = $("#pic").qrcode({
            render : "canvas",
            text : infoHtml,
            width : "150",               //二维码的宽度   
            height : "150",              //二维码的高度   
            background : "#ffffff",       //二维码的后景色   
            foreground : "#000000"        //二维码的前景色 
        }).hide();
        var canvas = qrcode.find('canvas').get(0);
        var src = canvas.toDataURL('image/jpg');
        html += '<div class="qrcode" style="margin-left:70px;margin-top:10px;width:150px;height:auto;text-align: center;border-bottom:1px solid black">'+
            '<div><img id="pic'+ i +'" src="'+ src +'" alt=""></div>'+
            '<div style="margin-top: 7px;">'+
            '<p class="fontchange">'+ extinguisherCodeArr[i] +'</p>'+
            '<p class="fontchange">'+ extinguisherTypeArr[i] +'</p>'+
            '<p class="fontchange">'+ extinguisherPositionArr[i] +'</p>'+
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

// 下载模板
function createObjectURL(object){return (window.URL) ? window.URL.createObjectURL(object) : window.webkitURL.createObjectURL(object);}
function downloadModel() {
    var xhr = new XMLHttpRequest();
    var formData = new FormData();
    xhr.open('get',baseUrl + "/common/download/extinguisher");  //url填写后台的接口地址，如果是post，在formData append参数（参考原文地址）
    xhr.setRequestHeader("Authorization", "Bearer "+login.token);
    xhr.responseType = 'blob';
    xhr.onload = function (e) {
        if (this.status == 200) {
            var blob = this.response;
            var filename = "灭火器管理.xls";  //这里的名字，可以按后端给的接口固定表单设置一下名字，如（费用单.xlsx,合同.doc等等）
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