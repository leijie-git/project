var unitID;
var unitList;   /*单位列表*/
var index = "";      /*巡查点的位置*/
var siteClassIndex = "";    /*巡查设施的位置*/

$(function () {
    //日期选择控件
    $("#lastUpdate").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    });

    $("#Search-lastUpdate").jeDate({
        ishmsVal : false,
        minDate : '1900-01-01 00:00:00',
        maxDate : '2099-12-31 23:59:59',
        format : "YYYY-MM-DD",
        zIndex : 3000
    });

    $(".leadingIn").click(function () {
        $("#leadingInModal").modal();
    });

    //搜索控制
    $(".jy_mainTile").hide();

    getUnitID();
});

// 得到当登录用户的单位
function getUnitID(){
    $.ajax({
        type: "get",
        async: true,
        url: baseUrl+"/inspectPlan/getUnitID",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType: "json",
        success: function (d) {
            if (d.success) {
                unitID = d.obj;
                mainSearch();
                getUnitList();
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


// 高级搜索
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
});

// 清空搜索内容
function emptySearchMsg(){
    $("#lastUpdate").val("");
    $("#SearchContent").val("");
    $("#SearchInspectCycle").val("");
    $("#Search-lastUpdate").val("");
    $("#Search-InspectCycle").val("");
    $("#Search-InspectCount").val("");
}

$("#menuType").change(function () {
    if($(this).val() == "lastUpdate"){
        $("#lastUpdate").show().siblings("#SearchInspectCycle").hide().siblings("#SearchContent").hide();
    }else if($(this).val() == "InspectCycle"){
        $("#SearchInspectCycle").show().siblings("#lastUpdate").hide().siblings("#SearchContent").hide();
    }else if($(this).val() == "InspectCount"){
        $("#SearchContent").show().siblings("#SearchInspectCycle").hide().siblings("#lastUpdate").hide();
    }
    emptySearchMsg();
});

// 普通搜索
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = "";
    if(id == "lastUpdate"){
        var value1 = $("#lastUpdate").val();
        $("#Search-lastUpdate").val(value1);
    }else if(id == "InspectCycle"){
        value = $("#SearchInspectCycle").val();
        $("#Search-InspectCycle").val(value);
    }else if(id == "InspectCount"){
        value = $("#SearchContent").val();
        $("#Search-InspectCount").val(value);
    }
    $("#inspectCheckConfigureTable").bootstrapTable('destroy');
    mainSearch();
});

// 批量删除
$(".btnDeleteMany").click(function () {
    deleteEmploy();
});

//查询条件查询
$(".btnSearch").click(function () {
    $("#inspectCheckConfigureTable").bootstrapTable('destroy');
    mainSearch();
});
//重置
$(".btnReset").click(function () {
    emptySearchMsg();
    $("#inspectCheckConfigureTable").bootstrapTable('destroy');
    mainSearch();
});

// 保存关联关系
$(".btnSure").click(function () {
    var arr = [];
    var arr2 = {};
    var i = 0;
    $("#Cascader li .active-true").each(function () {
        arr2 = {};
        var site = $(this).siblings(".siteName").attr("data-id");
        arr2.siteid = site;
        index = $(this).parent().index();
        $("#Cascader-2 #siteClassList"+ index +" li .active-true").each(function () {
            arr2 = {};
            var siteClass = $(this).siblings(".siteName").attr("data-id");
            arr2.siteid = site;
            arr2.siteClassid = $(this).siblings(".siteName").attr("data-id");
            siteClassIndex = $(this).parent().index();
            $("#Cascader-3 #siteDetailList"+ index +"-"+ siteClassIndex +" li .active-true").each(function () {
                // arr3.push($(this).siblings(".siteName").attr("data-id"));
                arr2 = {};
                arr2.siteid = site;
                arr2.siteClassid = siteClass;
                arr2.siteClassDetailid = $(this).siblings(".siteName").attr("data-id");
                arr[i] = arr2;
                i+=1;
            });
        });
    });

    $("#Cascader li .active-reduce").each(function () {
        arr2 = {};
        var site = $(this).siblings(".siteName").attr("data-id");
        arr2.siteid = $(this).siblings(".siteName").attr("data-id");
        index = $(this).parent().index();
        $("#Cascader-2 #siteClassList"+ index +" li .active-true").each(function () {
            arr2 = {};
            var siteClass = $(this).siblings(".siteName").attr("data-id");
            arr2.siteid = site;
            arr2.siteClassid = $(this).siblings(".siteName").attr("data-id")
            siteClassIndex = $(this).parent().index();
            $("#Cascader-3 #siteDetailList"+ index +"-"+ siteClassIndex +" li .active-true").each(function () {
                // arr3.push($(this).siblings(".siteName").attr("data-id"));
                arr2 = {};
                arr2.siteid = site;
                arr2.siteClassid = siteClass;
                arr2.siteClassDetailid = $(this).siblings(".siteName").attr("data-id");
                arr[i] = arr2;
                i+=1;
            });
        });
    });
    $("#Cascader li .active-reduce").each(function () {
        arr2 = {};
        var site = $(this).siblings(".siteName").attr("data-id");
        index = $(this).parent().index();
        $("#Cascader-2 #siteClassList" + index + " li .active-reduce").each(function () {
            arr2 = {};
            var siteClass = $(this).siblings(".siteName").attr("data-id");
            arr2.siteid = site;
            arr2.siteClassid = $(this).siblings(".siteName").attr("data-id")
            siteClassIndex = $(this).parent().index();
            $("#Cascader-3 #siteDetailList" + index + "-" + siteClassIndex + " li .active-true").each(function () {
                // arr3.push($(this).siblings(".siteName").attr("data-id"));
                arr2 = {};
                arr2.siteid = site;
                arr2.siteClassid = siteClass;
                arr2.siteClassDetailid = $(this).siblings(".siteName").attr("data-id");
                arr[i] = arr2;
                i+=1;
            });
            // arr2.siteClassDetailids = arr3;
            // arr[i] = arr2;
            // i+=1;
            // arr3 = [];
        });
    });
    $.ajax({
        type: "post",
        url: baseUrl + "/inspectBaseRel/updateAll",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: JSON.stringify(arr),
        // dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.success){
                $("#patrolRelation").modal("hide");
                layer.msg("新增成功!")
                $('#inspectCheckConfigureTable').bootstrapTable("refresh");
            }else{
                layer.msg(data.msg);
            }
        }
    })
});

// 清空编辑内容
function emptyMsg() {
    $("#UnitID").val("");
    $("#BuildID").html('<option value="">请选择</option>');
    $("#BuildAreaID").html('<option value="">请选择</option>');
    $("#SiteId").val("");
    $("#SiteClassId").val("");
    $("#SiteClassDetialId").val("");
    $("#InspectCycle").val("");
    $("#TaskUserName").val("");
    $("#TaskStart").val("");
    $("#TaskEnd").val("");
    $("#inspectCount").val("");
}

// 清空查看内容
function emptyMsgView() {
    $("#UnitIDView").val("");
    $("#BuildIDView").val("");
    $("#BuildAreaIDView").val("");
    $("#SiteIdView").val("");
    $("#SiteClassIdView").val("");
    $("#SiteClassDetialIdView").val("");
    $("#InspectCycleView").val("");
    $("#TaskUserNameView").val("");
    $("#TaskStartView").val("");
    $("#TaskEndView").val("");
    $("#inspectCountView").val("");
}

//查看角色
function showCheckConfigure(_this) {
    emptyMsgView();
    $("#newAddView").modal();
    // showUnitList("UnitIDView",_this.unitName);
    // showBuildList("BuildIDView",_this.unitId,_this.buildName);
    // showBuildAreaList("BuildAreaIDView",_this.buildId,_this.buildAreaName);
    $("#UnitIDView").val(_this.unitName);
    $("#BuildIDView").val(_this.buildName);
    $("#BuildAreaIDView").val(_this.buildAreaName);
    $("#SiteIdView").val(_this.siteName);
    $("#SiteClassIdView").val(_this.siteClassName);
    $("#SiteClassDetialIdView").val(_this.checkInfo);
    $("#InspectCycleView").val(_this.inspectCycle);
    $("#InspectCountView").val(_this.inspectCount);
    $("#TaskUserNameView").val(_this.taskUserName);
    $("#TaskStartView").val(_this.taskStart);
    $("#TaskEndView").val(_this.taskEnd);
}

//删除角色
function deleteCheckConfigure(_this) {
    var indexId = _this.id;
    var url = baseUrl + '/inspectBaseRel/remove';
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
                        $('#inspectCheckConfigureTable').bootstrapTable("refresh");
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
    var checked = $("#inspectCheckConfigureTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/inspectBaseRel/remove";
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
                $('#inspectCheckConfigureTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

// 主页面查询
function mainSearch() {
    $('#inspectCheckConfigureTable').bootstrapTable({
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
                lastUpdate: $("#Search-lastUpdate").val(),
                inspectCycle:  $("#Search-InspectCycle").val(),
                inspectCount:  $("#Search-InspectCount").val(),
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
                checkbox: 'true'
            },
            {
                field: 'unitName',
                title: '单位名称'
            },
            {
                field: 'siteName',
                title: '巡查点'
            },
            {
                field: 'siteClassName',
                title: '巡查设施'
            },
            {
                field: 'checkInfo',
                title: '检查项'
            },
            {
                field: 'taskUserName',
                title: '责任人'
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
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                    str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                    str += '<button type="button" class="btn btn-new btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .del': function (e, value, row, index) {
                        deleteCheckConfigure(row);
                    },
                    'click .view': function (e, value, row, index) {
                        showCheckConfigure(row);
                    }
                    // 'click .item': function (e, value, row, index) {
                    //     $("#PatrolItemTable").bootstrapTable('destroy');
                    //     PatrolItem(row);
                    //     $("#PatrolItem").modal();
                    // }
//                    ,
//                    'click .lookPic': function (e, value, row, index) {
//                        lookPic(row);
//                    }
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
    var html = '<option value="">--请选择--</option>';
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
    element.indexOf("View") != -1 ? "" : $("#"+ element).comboSelect();
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
                    html += option;
                }
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



//下载默认模板
function createObjectURL(object){return (window.URL) ? window.URL.createObjectURL(object) : window.webkitURL.createObjectURL(object);}
function downloadModel() {
    var xhr = new XMLHttpRequest();
    var formData = new FormData();
    xhr.open('get',baseUrl + "/common/download/SiteExcelLoad");  //url填写后台的接口地址，如果是post，在formData append参数（参考原文地址）
    xhr.setRequestHeader("Authorization", "Bearer "+login.token);
    xhr.responseType = 'blob';
    xhr.onload = function (e) {
        if (this.status == 200) {
            var blob = this.response;
            var filename = "巡查点检查项配置.xls";  //这里的名字，可以按后端给的接口固定表单设置一下名字，如（费用单.xlsx,合同.doc等等）
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
        url: baseUrl + "/inspectConfig/importConfigExcel",
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
            $('#inspectCheckConfigureTable').bootstrapTable('refresh');
        }
    })
});


    // 关系绑定按钮（for太多，待优化）
$(".bind").click(function () {
    $("#Cascader").empty();
    $("#Cascader-2").empty();
    $("#Cascader-2").hide();
    $("#Cascader-3").empty();
    $("#Cascader-3").hide();
    $("#patrolRelation").modal();

    getsite();

    $.ajax({
        type: "post",
        data: {
            unitId: unitID
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        async: false,
        url: baseUrl + "/inspectBaseRel/getRelList",
        success: function (data) {
            if (data.success){
                var val = data.obj;
                for(var i=0;i<val.length;i++){
                    for(var x=0;x<$("#Cascader li .checkboxChange").length;x++){
                        if($("#Cascader li .checkboxChange").eq(x).siblings(".siteName").attr("data-id") == val[i].siteid){
                            index = x;
                            $("#Cascader li .checkboxChange").eq(x).hide().siblings(".checkbox-reduce").show().addClass("active-reduce");
                            if($("#siteClassList"+index+"").length == 0){
                                getsiteClass();
                            }
                            for(var y=0;y<$("#siteClassList"+index+" .checkboxChange").length;y++){
                                if($("#siteClassList"+index+" .checkboxChange").eq(y).siblings(".siteName").attr("data-id") == val[i].siteClassid){
                                    siteClassIndex = y;
                                    $("#siteClassList"+index+" .checkboxChange").eq(y).hide().siblings(".checkbox-reduce").show().addClass("active-reduce");
                                    if($("#siteDetailList"+index+"-"+siteClassIndex +"").length == 0){
                                        getsiteDetail();
                                    }
                                    for(var j=0;j<val[i].siteClassDetailids.length;j++){
                                        for(var z=0;z<$("#siteDetailList"+index+"-"+siteClassIndex+" .checkboxChange").length;z++){
                                            if($("#siteDetailList"+index+"-"+siteClassIndex+" .checkboxChange").eq(z).siblings(".siteName").attr("data-id") == val[i].siteClassDetailids[j]){
                                                $("#siteDetailList"+index+"-"+siteClassIndex+" .checkboxChange").eq(z).addClass("active-true");
                                            }
                                        }
                                    }
                                    if($("#siteDetailList"+ index +"-"+ siteClassIndex +" li").length == $("#siteDetailList"+ index +"-"+ siteClassIndex +" .active-true").length){
                                        $("#siteClassList"+ index +" .checkboxChange").eq(siteClassIndex).show().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
                                    }
                                }
                            }
                            if($("#siteClassList"+ index +" li").length == $("#siteClassList"+ index +" .active-true").length){
                                $("#Cascader li:eq("+ index +") .checkboxChange").show().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
                            }
                        }
                    }


                    // $("#Cascader li .checkboxChange").each(function () {
                    //     debugger;
                    //     if($(this).siblings(".siteName").attr("data-id") == val[i].siteid){
                    //         index = $(this).parent().index();
                    //         $(this).addClass("active-reduce");
                    //         getsiteClass();
                    //     }
                    // });
                    // $("#siteClassList"+index+" .checkboxChange").each(function () {
                    //     if($(this).siblings(".siteName").attr("data-id") == val[i].siteClassid){
                    //         siteClassIndex = $(this).index();
                    //         $(this).addClass("active-reduce");
                    //         getsiteDetail();
                    //         console.log(siteClassIndex);
                    //         console.log("val["+i+"].siteClassid:"+val[i].siteClassid);
                    //     }
                    // });
                    // for(var j=0;j<val[i].siteClassDetailids.length;j++){
                    //     $("#siteDetailList"+ index +"-"+ siteClassIndex +" .checkboxChange").each(function () {
                    //         if($(this).siblings(".siteName").attr("data-id") == val[i].siteClassDetailids[j]){
                    //             $(this).addClass("active-true");
                    //         }
                    //     });
                    //     console.log("index:"+index);
                    //     console.log("siteClassIndex:"+siteClassIndex);
                    // }


                }
            }
        }
    })
});

// 巡查点点击
$("#Cascader").on("click","li",function () {
    index = $(this).index();
    $("#Cascader-2").show();
    $("#Cascader-3").hide();
    // $(this).css("opacity","0.6").siblings().css("opacity","1");
    $(this).find(".glyphicon-menu-right").css({"color":"#409eff","opacity":"0.7"}).siblings(".siteName").css({"color":"#409eff","opacity":"0.7"});
    $(this).siblings().find(".siteName").css({"color":"","opacity":"1"});
    $(this).siblings().find(".glyphicon-menu-right").css({"color":"","opacity":"1"});
    if($("#siteClassList"+ index +"").length != 0){
        $("#siteClassList"+ index +"").slideDown().siblings().hide();
    }else{
        getsiteClass();
    }
})

// 巡查点空白框或对号框点击
$("#Cascader").delegate("li .checkboxChange","click",function () {
    index = $(this).parent().index();
    $("#Cascader-2").show();
    $("#Cascader-3").hide();
    $(this).siblings(".glyphicon-menu-right").css({"color":"#409eff","opacity":"0.7"}).siblings(".siteName").css({"color":"#409eff","opacity":"0.7"});
    $(this).parent().siblings().find(".siteName").css({"color":"","opacity":"1"});
    $(this).parent().siblings().find(".glyphicon-menu-right").css({"color":"","opacity":"1"});
    if($(this).hasClass("active-true")){
        $(this).removeClass("active-true");
        if($("#siteClassList"+ index +"").length != 0){
            $("#siteClassList"+ index +"").slideDown().siblings().hide();
        }else{
            getsiteClass();
        }
        $("#siteClassList"+ index +" .checkboxChange").removeClass("active-true");
    }else{
        $(this).addClass("active-true");
        if($("#siteClassList"+ index +"").length != 0){
            $("#siteClassList"+ index +"").slideDown().siblings().hide();
        }else{
            getsiteClass();
            $("#siteClassList"+ index +" li").each(function () {
                siteClassIndex = $(this).index();
                if($("#siteDetailList"+ index +"-"+ siteClassIndex +"").length == 0){
                    getsiteDetail();
                }
                $("#siteDetailList"+ index +"-"+ siteClassIndex +" li .checkboxChange").addClass("active-true");
            });
            // for(var i=0;i<$("#siteClassList"+ index +" li").length;i++){
            //     siteClassIndex = $("#siteClassList"+ index +"")
            // }
        }
        $("#siteClassList"+ index +" .checkboxChange").addClass("active-true");
    }
});

// 巡查点减号框点击
$("#Cascader").on("click","li .checkbox-reduce",function(){
    index = $(this).parent().index();
    $(this).hide().removeClass("active-reduce");
    $(this).siblings(".checkboxChange").show().addClass("active-true");
    $("#siteClassList" + index).slideDown().siblings().hide();
    $("#siteClassList"+ index +" .checkboxChange").slideDown().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
});

// 巡查设施点击
$("#Cascader-2").on("click","ul li",function () {
    siteClassIndex = $(this).index();
    $("#Cascader-3").slideDown();
    $(this).find(".glyphicon-menu-right").css({"color":"#409eff","opacity":"0.7"}).siblings(".siteName").css({"color":"#409eff","opacity":"0.7"});
    $(this).siblings().find(".siteName").css({"color":"","opacity":"1"});
    $(this).siblings().find(".glyphicon-menu-right").css({"color":"","opacity":"1"});
    if($("#siteDetailList"+ index +"-"+ siteClassIndex +"").length != 0){
        $("#siteDetailList"+ index +"-"+ siteClassIndex +"").slideDown().siblings().hide();
    }else{
        getsiteDetail();
    }
    if($(this).find(".checkboxChange").hasClass("active-true")){
        $("#siteDetailList"+ index +"-"+ siteClassIndex +" li .checkboxChange").slideDown().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
    }
});

// 巡查设施空白框和对号框点击
$("#Cascader-2").on("click","ul li .checkboxChange",function () {
    var parentid = $(this).parent().parent().attr("id");
    var parentindex = parentid.substr(parentid.length - 1,1);
    siteClassIndex = $(this).parent().index();
    if($(this).hasClass("active-true")) {
        $(this).removeClass("active-true");
        if($("#"+parentid+" li .active-true").length == 0){
            $("#Cascader li:eq("+ parentindex +") .checkbox-reduce").removeClass("active-reduce").siblings().removeClass("active-true");
        }else{
            $("#Cascader li:eq("+ parentindex +") .checkboxChange").hide().siblings(".checkbox-reduce").show().addClass("active-reduce");
        }
        if($("#siteDetailList"+ index +"-"+ siteClassIndex +"").length != 0){
            $("#siteDetailList"+ index +"-"+ siteClassIndex +"").slideDown().siblings().hide();
        }else{
            getsiteDetail();
        }
        $("#siteDetailList"+ index +"-"+ siteClassIndex +" li .checkboxChange").removeClass("active-true");
    }else{
        $(this).addClass("active-true");
        if($("#"+parentid+" li").length == $("#"+parentid+" li .active-true").length){
            $("#Cascader li:eq("+ parentindex +") .checkbox-reduce").hide().removeClass("active-reduce").siblings(".checkboxChange").show().addClass("active-true");
        }else{
            $("#Cascader li:eq("+ parentindex +") .checkbox-reduce").show().addClass("active-reduce").siblings(".checkboxChange").hide().removeClass("active-true");
        }
        if($("#siteDetailList"+ index +"-"+ siteClassIndex +"").length != 0){
            $("#siteDetailList"+ index +"-"+ siteClassIndex +"").slideDown().siblings().hide();
        }else{
            getsiteDetail();
        }
        $("#siteDetailList"+ index +"-"+ siteClassIndex +" li .checkboxChange").addClass("active-true");
    }
});

// 巡查设施减号框点击
$("#Cascader-2").on("click","li .checkbox-reduce",function () {
    siteClassIndex = $(this).parent().index();
    $(this).removeClass("active-reduce").hide().siblings(".checkboxChange").show().addClass("active-true");
    $($("#siteDetailList"+ index +"-"+ siteClassIndex +" li .checkboxChange").addClass("active-true"));
    $(this).siblings(".siteName, .glyphicon-menu-right").css({"color":"#409eff","opacity":"0.7"});
    $(this).parent().siblings().find(".glyphicon-menu-right").css({"color":"","opacity":"1"});
    $(this).parent().siblings().find(".siteName").css({"color":"","opacity":"1"});
    if($("#Cascader-2 li .active-true").length == $("#Cascader-2 li").length){
        $("#Cascader li:eq("+ index +") .checkboxChange").show().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
    }
});

// 检查项对号框或空白框点击
$("#Cascader-3").on("click","li .checkboxChange",function () {
    if($(this).hasClass("active-true")){
        $(this).removeClass("active-true");
        if($("#siteDetailList"+ index +"-"+ siteClassIndex +" li .active-true").length == 0){
            $("#siteClassList"+index+" li:eq("+ siteClassIndex +") .checkboxChange").removeClass("active-true").show().siblings(".checkbox-reduce").removeClass("active-reduce").hide();
            if($("#siteClassList"+index+" li .active-true").length == 0 && $("#siteClassList"+index+" li .active-reduce").length == 0){
                $("#Cascader li:eq("+ index +") .checkboxChange").show().removeClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
            }
        }else{
            $("#siteClassList"+index+" li:eq("+ siteClassIndex +") .checkboxChange").removeClass("active-true").hide().siblings(".checkbox-reduce").addClass("active-reduce").show();
            $("#Cascader li:eq("+ index +") .checkboxChange").hide().removeClass("active-true").siblings(".checkbox-reduce").show().addClass("active-reduce");
        }
    }else{
        $(this).addClass("active-true");
        if($("#siteDetailList"+ index +"-"+ siteClassIndex +" li .active-true").length == $("#siteDetailList"+ index +"-"+ siteClassIndex +" li").length){
            $("#siteClassList"+index+" li:eq("+ siteClassIndex +") .checkboxChange").show().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
            if($("#siteClassList"+index+" li .active-true").length == $("#siteClassList"+index+" li").length){
                $("#Cascader li:eq("+ index +") .checkboxChange").show().addClass("active-true").siblings(".checkbox-reduce").hide().removeClass("active-reduce");
            }else{
                $("#Cascader li:eq("+ index +") .checkboxChange").hide().removeClass("active-true").siblings(".checkbox-reduce").show().addClass("active-reduce");
            }
        }else{
            $("#siteClassList"+index+" li:eq("+ siteClassIndex +") .checkboxChange").hide().removeClass("active-true").siblings(".checkbox-reduce").show().addClass("active-reduce");
            $("#Cascader li:eq("+ index +") .checkboxChange").hide().removeClass("active-true").siblings(".checkbox-reduce").show().addClass("active-reduce");
        }
    }
});

// 获取巡查点
function getsite(){
    $.ajax({
        type: "get",
        data: {
            UnitID: unitID
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl + '/inspectSite/getArrayList',
        dataType: "json",
        async:false,
        success: function (data) {
            var obj = data.obj;
            var html = "";
            for(var i=0;i<obj.length;i++){
                var lis = '<li><span class="checkbox-reduce" style="display: none;"></span>'+
                    '<span class="checkboxChange"></span>'+
                    '<span data-id="'+ obj[i].id +'" class="siteName">'+ obj[i].siteName +'</span>'+
                    '<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>'+
                    '</li>';
                html += lis;
            }
            $("#Cascader").html(html);
        }
    });
}

// 获取巡查设施
function getsiteClass(){
    $.ajax({
        type: "get",
        url: baseUrl + "/inspectSiteClass/getArrayList",
        data:{
            UnitID: unitID
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        async: false,
        success:function (data) {
            var obj = data.obj;
            var html = "";
            var ul = "";
            for(var i=0;i<obj.length;i++){
                var lis = '<li><span class="checkbox-reduce" style="display: none;"></span>'+
                    '<span class="checkboxChange"></span>'+
                    '<span data-id="'+ obj[i].id +'" class="siteName">'+ obj[i].siteClassName +'</span>'+
                    '<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>'+
                    '</li>';
                html += lis;
            }
            ul = '<ul id="siteClassList'+ index +'">'+ html +'</ul>';
            $("#Cascader-2").append(ul);
            $("#siteClassList"+ index +"").slideDown().siblings().hide();
        }
    });
}

// 获取巡查检查项
function getsiteDetail(){
    $.ajax({
        type: "get",
        url: baseUrl + "/siteClassDetial/getArrayList",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:{
            UnitID: unitID
        },
        async: false,
        success:function (data) {
            var obj = data.obj;
            var html = "";
            var ul = "";
            for(var i=0;i<obj.length;i++){
                var lis = '<li><span class="checkbox-reduce" style="display: none;"></span>'+
                    '<span class="checkboxChange"></span>'+
                    '<span data-id="'+ obj[i].id +'" class="siteName">'+ obj[i].checkInfo +'</span>'+
                    '<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>'+
                    '</li>';
                html += lis;
            }
            ul = '<ul id="siteDetailList'+ index +'-'+ siteClassIndex +'">'+ html +'</ul>';
            $("#Cascader-3").append(ul);
            $("#siteDetailList"+ index +"-"+ siteClassIndex +"").slideDown().siblings().hide();
        }
    });
}