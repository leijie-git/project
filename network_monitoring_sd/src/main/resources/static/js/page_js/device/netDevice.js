var unitList;
var modelList;
var netDeviceID;
var _serverIp = "http://localhost:8085";

var SetPortValue = 0;
var smokePortValue = 0;		//防排烟系统，从setPortValue中分离
var otherPortValue = 0;
var threePortValue = 0;
var faultPortValue = 0;
var ElevenPortValue = 0;
var customPortValue = 0;
var fourPortValue = 0;

var buildList;
var buildAreaList;
var SystemList;
var classList;
var deviceList;
var portList;   //添加接口中端口信号类型的集合
var deviceIndex = "";
var detailId;
var Id;
var unitID;
var systemID;
var netDeviceId;

var changeurl;  //添加接口中用来区分编辑和新增

// 自定义编辑泵控制柜的模态框
var newHtmlPump = '<div class="pumpPort customPort" id="">\n' +
    '<div class="closePort" onclick="DeleteAddPort(this)">X</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>端口号类型</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortId">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>端口号</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdValue"\n' +
    'onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
    'onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14" style="display: none;">\n' +
    '<span style="color: red">*</span>\n' +
    '<span>数据类型</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31" style="display: none;">\n' +
    '<select class="form-control inputWidth PortIdType">\n' +
    '<option value=""></option>\n' +
    '<option value="0" selected="selected">数字量</option>\n' +
    '<option value="1">模拟量</option>\n' +
    '</select>\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color: red;">*</span>\n' +
    '<span>正常值</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<select class="form-control inputWidth PortIdNormalNum">\n' +
    '<option value="">--请选择--</option>\n' +
    '<option value="0">低电平0</option>\n' +
    '<option value="1">高电平1</option>\n' +
    '</select>\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>正常名</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdNormalName">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color: red">*</span>\n' +
    '<span>异常名</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdAbnormalName">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14" style="display: none;">\n' +
    '<span style="color: red">*</span>\n' +
    '<strong> :</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31" style="display: none;">\n' +
    '<input type="text" class="form-control inputWidth PortIdDetailId">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span>信号名称</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdSignal">\n' +
    '</div>\n' +
    '<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>\n' +
    '</div>';


// 自定义编辑防排烟系统的模态框
var newHtmlSmoke = '<div class="smokeControlPort customPort" id="">\n' +
    '<div class="closePort" onclick="DeleteAddPort(this)">X</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>端口号类型</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortId">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>端口号</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdValue"\n' +
    'onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
    'onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14" style="display: none;">\n' +
    '<span style="color: red">*</span>\n' +
    '<span>数据类型</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31" style="display: none;">\n' +
    '<select class="form-control inputWidth PortIdType">\n' +
    '<option value=""></option>\n' +
    '<option value="0" selected="selected">数字量</option>\n' +
    '<option value="1">模拟量</option>\n' +
    '</select>\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color: red;">*</span>\n' +
    '<span>正常值</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<select class="form-control inputWidth PortIdNormalNum">\n' +
    '<option value="">--请选择--</option>\n' +
    '<option value="0">低电平0</option>\n' +
    '<option value="1">高电平1</option>\n' +
    '</select>\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color:red">*</span>\n' +
    '<span>正常名</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdNormalName">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span style="color: red">*</span>\n' +
    '<span>异常名</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdAbnormalName">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14" style="display: none;">\n' +
    '<span style="color: red">*</span>\n' +
    '<strong> :</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31" style="display: none;">\n' +
    '<input type="text" class="form-control inputWidth PortIdDetailId">\n' +
    '</div>\n' +
    '<div class="text-right displayInline Width14">\n' +
    '<span>信号名称</span>\n' +
    '<strong>:</strong>\n' +
    '</div>\n' +
    '<div class="text-left displayInline Width31">\n' +
    '<input type="text" class="form-control inputWidth PortIdSignal">\n' +
    '</div>\n' +
    '<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>\n' +
    '</div>';


// 批量添加接口
var NewPort = '<tr>\n' +
    '<td>\n' +
    '<div onclick="deleteEqPort(this)" style="text-align: center;color: #ddd;cursor: pointer;">x</div>\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortBuild">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortArea">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortPosition">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortEqName">\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortEqSystem">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortEqClass">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortSignalType">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortId">\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortSignal">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortfireK">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortfireB">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortfireTop">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortfireLow">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortMNUint">\n' +
    '</td>\n' +
    '<td>\n' +
    '<select class="form-control allPortNormalNum">\n' +
    '<option value>--请选择--</option>\n' +
    '<option value="0">低电平</option>\n' +
    '<option value="1">高电平</option>\n' +
    '</select>\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortNormalName">\n' +
    '</td>\n' +
    '<td>\n' +
    '<input type="text" class="form-control allPortAbnormalName">\n' +
    '</td>\n' +
    '</tr>';

// 删除添加接口中的端口号
function deleteEqPort(obj){
    layer.open({
        content : "是否确定删除？",
        btn : [ "确认", "取消" ],
        yes : function(index, layero) {
            var code = $(obj).parents("tr").attr("code");
            if(code == "4" || code == "17" || code == "27" || code == "18"){
                var url = baseUrl + "/equipmentFac/delete";
                var data = {
                    eqId: $(obj).parents("tr").attr("eqid"),
                    netDevcieId: $(obj).parents("tr").attr("netdeviceid"),
                    detailid: $(obj).parents("tr").attr("detialid")
                };
                var type = "get";
            }else{
                var url = baseUrl + "/equipmentFac/remove";
                var data = {
                    id: $(obj).parents("tr").attr("eqid")
                };
                var type = "post";
            }
            if ($(obj).parents("tr").attr("detialid")) {
                $.ajax({
                    type: type,
                    url: url,
                    beforeSend: function(request){
                        request.setRequestHeader("Authorization","Bearer "+login.token);
                    },
                    dataType: "json",
                    data: data,
                    success: function (data) {
                        if (data.success) {
                            $(obj).parents("tr").remove();
                            layer.msg("删除成功！");
                        }
                    }
                });
            } else {
                $(obj).parents("tr").remove();
                layer.msg("删除成功！");
            }
        },
        no : function(index, layero) {

        }
    });
}

// 删除的自定义的端口号
function DeleteAddPort(obj) {
    index = $(obj).parent().index();
    $(obj).parent().remove();
}

// 新增时一键填充电气火灾的端口值
function oneKey() {
    // IA
    $("#firePort0").val("4");
    $("#fireKValue0").val("0.1");
    $("#fireBValue0").val("0");
    $("#fireTopValue0").val("60");
    $("#fireLowValue0").val("0");
    $("#fireMNUnit0").val("A");
    // IB
    $("#firePort1").val("5");
    $("#fireKValue1").val("0.1");
    $("#fireBValue1").val("0");
    $("#fireTopValue1").val("60");
    $("#fireLowValue1").val("0");
    $("#fireMNUnit1").val("A");
    // IC
    $("#firePort2").val("6");
    $("#fireKValue2").val("0.1");
    $("#fireBValue2").val("0");
    $("#fireTopValue2").val("60");
    $("#fireLowValue2").val("0");
    $("#fireMNUnit2").val("A");
    // UA
    $("#firePort3").val("1");
    $("#fireKValue3").val("0.1");
    $("#fireBValue3").val("0");
    $("#fireTopValue3").val("300");
    $("#fireLowValue3").val("0");
    $("#fireMNUnit3").val("V");
    // UB
    $("#firePort4").val("2");
    $("#fireKValue4").val("0.1");
    $("#fireBValue4").val("0");
    $("#fireTopValue4").val("300");
    $("#fireLowValue4").val("0");
    $("#fireMNUnit4").val("V");
    // UC
    $("#firePort5").val("3");
    $("#fireKValue5").val("0.1");
    $("#fireBValue5").val("0");
    $("#fireTopValue5").val("300");
    $("#fireLowValue5").val("0");
    $("#fireMNUnit5").val("V");
    // WA
    $("#firePort6").val("13");
    $("#fireKValue6").val("0.1");
    $("#fireBValue6").val("0");
    $("#fireTopValue6").val("60");
    $("#fireLowValue6").val("0");
    $("#fireMNUnit6").val("℃");
    // WB
    $("#firePort7").val("14");
    $("#fireKValue7").val("0.1");
    $("#fireBValue7").val("0");
    $("#fireTopValue7").val("60");
    $("#fireLowValue7").val("0");
    $("#fireMNUnit7").val("℃");
    // WC
    $("#firePort8").val("15");
    $("#fireKValue8").val("0.1");
    $("#fireBValue8").val("0");
    $("#fireTopValue8").val("60");
    $("#fireLowValue8").val("0");
    $("#fireMNUnit8").val("℃");
    // WD
    $("#firePort9").val("16");
    $("#fireKValue9").val("0.1");
    $("#fireBValue9").val("0");
    $("#fireTopValue9").val("60");
    $("#fireLowValue9").val("0");
    $("#fireMNUnit9").val("℃");
    // 漏电流
    $("#firePort10").val("12");
    $("#fireKValue10").val("0.1");
    $("#fireBValue10").val("0");
    $("#fireTopValue10").val("300");
    $("#fireLowValue10").val("0");
    $("#fireMNUnit10").val("mA");
}

$(function () {

    // 灭火系统泵控制柜新增端口号设置
    $(".btnAddPort").click(function () {
        $("#fourPort .portTable").append(newHtmlPump);
    });

    // 防排烟系统系统防排烟新增端口号设置
    $(".btnAddSmokePort").click(function () {
        $("#smokePort .portTable").append(newHtmlSmoke);
    });

    // 批量添加接口
    $(".AddPort").click(function () {
        $("#portTable233").prepend(NewPort);
    });

    $(".btnAddMNPortSure").hide();
    $(".btnAddSZPortSure").hide();
    $(".btnUpdateMNPortSure").hide();
    $(".btnUpdateSZPortSure").hide();

    //主页面查询
    mainSearch();

    //初始化单位下拉框
    initUnit();
    getUnitList();

    getDeviceList();    /*获取联网设备列表*/
    getClassList(); /*新增编辑系统类型改变*/
    // showDeviceList();
    //初始化系统类型下拉框
    initEqSystem();
    initEquipmentModel();

    // 重置
    $(".btnReset").click(function () {
        $("#ownercode").val('');
        $("#deviceIndex").val('');
        $("#deviceNo").val('');
        $("#deviceName").val('');
        $("#unitName").val('');
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

    // 根据设备类型变动设备类型
    $("#newnetDevice-deviceindex").change(function () {
        $("#newnetDevice-eqsystemid option").show();
        if($(this).val() == "1"){
            $("#newnetDevice-eqsystemid option[value='1']").hide();
            $("#newnetDevice-eqsystemid option[value='2']").hide();
            $("#newnetDevice-eqsystemid option[value='4']").hide();
            $("#newnetDevice-eqsystemid option[value='9']").hide();
            $("#newnetDevice-eqsystemid option[value='10']").hide();
        }else if($(this).val() == "2"){
            $("#newnetDevice-eqsystemid option[value='1']").hide();
            $("#newnetDevice-eqsystemid option[value='2']").hide();
            $("#newnetDevice-eqsystemid option[value='4']").hide();
            $("#newnetDevice-eqsystemid option[value='5']").hide();
            $("#newnetDevice-eqsystemid option[value='6']").hide();
            $("#newnetDevice-eqsystemid option[value='7']").hide();
            $("#newnetDevice-eqsystemid option[value='8']").hide();
            $("#newnetDevice-eqsystemid option[value='9']").hide();
            $("#newnetDevice-eqsystemid option[value='10']").hide();
        }else if($(this).val() == "3"){
            $("#newnetDevice-eqsystemid option[value='3']").hide();
            $("#newnetDevice-eqsystemid option[value='6']").hide();
            $("#newnetDevice-eqsystemid option[value='7']").hide();
            $("#newnetDevice-eqsystemid option[value='8']").hide();
            $("#newnetDevice-eqsystemid option[value='10']").hide();
        }
    });

    $(".newAdd").click(function () {
        showUnitList();
        showModelList();
        $("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增联网设备");
        $("#newEmploy .btnSure").show();
        $("#newnetDevice-deviceindex").removeAttr("disabled","disabled");

        $("#newnetDevice-deviceno").val("");
        $("#newnetDevice-deviceno").removeAttr("disabled","disabled");

        $("#newnetDevice-deviceindex").val("");
        $("#newnetDevice-unitid").val("");
        $("#newnetDevice-ownercode").empty();
        $("#newnetDevice-ownercode").siblings(".combo-dropdown").empty();
        $("#newnetDevice-ownercode").siblings(".combo-input").val("");
        $("#newnetDevice-name").val("");
        $("#newnetDevice-eqsystemid").val("");

        $("#newnetDevice-isIndependent1").removeAttr("checked");
        $("#newnetDevice-isIndependent0").removeAttr("checked");

        $("#editId").val("0");
        $("#indexId").val("");
        $(".bindNumber").show();
        $(".btnSure").html("<i class='fa fa-floppy-o'></i>&nbsp;保存并下一步");
        devicechange();
    });


    // 导入
    $(".import").click(function(){
        showUnitList();
        $('#mainSiteTable').bootstrapTable("destroy");
        $("#unitid").val("");
        var unitid = $("#unitid").val();
        $("#addSite .modal-title").html("导入联网设备");
        $("#addSite").modal();
        showTable();

    });

    // 切换选项改变按钮，仅在新增时生效
    function devicechange(){
        $("#newnetDevice-deviceindex").change(function () {
            if($(this).val() == "3"){
                $(".btnSure").html("<i class='fa fa-floppy-o'></i>&nbsp;保存");
            }else{
                $(".btnSure").html("<i class='fa fa-floppy-o'></i>&nbsp;保存并下一步");
            }
        });
    }

    // 新增或编辑
    $(".btnSure").click(function () {
        var editId = $("#editId").val();
        var deviceno = $("#newnetDevice-deviceno").val();
        var deviceindex = $("#newnetDevice-deviceindex").val();
        var deviceindexText = $('#newnetDevice-deviceindex option[value="' + deviceindex + '"]').text();
        var unitid = $("#newnetDevice-unitid").siblings(".combo-dropdown").find("li.option-selected").attr("data-value");
        var ownercode = $("#newnetDevice-ownercode").val();
        var id = $("#indexId").val();
        var name = $("#newnetDevice-name").val();
        var eqsystemid = $("#newnetDevice-eqsystemid").val();
        var equipmentmodel = $("#newnetDevice-equipmentmodel").siblings(".combo-dropdown").find("li.option-selected").attr("data-value");
        var isIndependent = $("input[name='newnetDevice-isIndependent']:checked").val();

        if (unitid == "" || unitid == null) {
            layer.msg("单位不能为空！");
            return;
        }
        if (ownercode == "" || ownercode == null) {
            layer.msg("设备编号不能为空！");
            return;
        }
        if (deviceindex == "" || deviceindex == null) {
            layer.msg("设备类型不能为空！");
            return;
        }
        if(isIndependent =="" || isIndependent == null){
            layer.msg("请勾选独立上线！");
            return;
        }
        if(deviceindex==2){
            deviceno = "1";
        }
        if(eqsystemid == "" || eqsystemid == null){
            layer.msg("系统类型不能为空！");
            return;
        }
        if(deviceno =="" || deviceno == null){
            layer.msg("设备子号不能为空！");
            return;
        }
        if(name =="" || name == null){
            layer.msg("设备名称不能为空！");
            return;
        }

        var editId = $("#editId").val();
        if (editId == 0) {
            var url = baseUrl + "/netDevice/add";
            var data = {
                deviceno:deviceno,
                deviceindex:deviceindex,
                unitid:unitid,
                ownercode:ownercode,
                name:name,
                eqsystemid:eqsystemid,
                equipmentmodel:equipmentmodel,
                isIndependent:isIndependent
            };

            var isAsync = true,lid = 0;
            if (isAsync)
                lid = layer.msg('正在加载...', { icon: 16, shade: [0.1, '#fff'], time: 90000 })

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
                        if(deviceindex != 3){
                            setTimeout(function(){
                                newAddPort(unitid,eqsystemid,deviceindex,name,deviceindexText);
                            },500);
                        }
                    } else {
                        layer.msg(d.msg);
                    }
                },
                error: function (data) {

                },
                complete: function () {
                    if (isAsync){
                        layer.close(lid);
                    }
                }
            });
        } else {
            var url = baseUrl + "/netDevice/update";
            var data = {
                id:id,
                deviceno:deviceno,
                deviceindex:deviceindex,
                unitid:unitid,
                ownercode:ownercode,
                name:name,
                eqsystemid:eqsystemid,
                equipmentmodel:equipmentmodel,
                isIndependent:isIndependent
            };
            var isAsync = true,lid = 0;
            if (isAsync)
                lid = layer.msg('正在加载...', { icon: 16, shade: [0.1, '#fff'], time: 90000 })
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

                },
                complete: function () {
                    if (isAsync){
                        layer.close(lid);
                    }
                }
            });
        }
    });

    // 最上层模态框关闭以后 底层模特框无法滚动问题
    //	$(".notLastModal").on("hidden.bs.modal", function() {
    //		// $("#newProject").addClass("modal-open");
    //		$("#newInterface").css("overflow-y", "auto");
    //	});


    // 绑定编号
    $(".bindNumber").click(function () {
        if($("#newnetDevice-unitid").val() == "" || $("#newnetDevice-unitid").val() == null){
            layer.msg("请先选择单位！");
            return;
        }
        $("#unitId").val($("#newnetDevice-unitid").val());
        bindingNumberSearch($("#newnetDevice-unitid").val());
        $("#bindNumber").modal("show");
        $("#soureaddress").val($(".combo-input").eq(1).val())
    });


    //  保存全部的接口
    $(".btnAllPort").click(function () {
        var data = [];
        var allPortActionType = "";
        for(var i=0;i<$("#portTable233 tr").length;i++){
            var list = [];
            var allPortNetDeviceId = $(".allPortNetDeviceId").eq(0).val();
            var allPortUnit = $(".allPortUnit").eq(0).val();
            var allPortBuild = $(".allPortBuild").eq(i).val();
            var allPortArea = $(".allPortArea").eq(i).val();
            var allPortPosition = $(".allPortPosition").eq(i).val();
            var allPortEqName = $(".allPortEqName").eq(i).val();
            var allPortEqSystem = $(".allPortEqSystem").eq(i).val();
            var allPortEqClass = $(".allPortEqClass").eq(i).find("option:selected").val();
            var allPortEqClassName = $(".allPortEqClass").eq(i).find("option:selected").text();
            var code = $(".allPortEqClass").eq(i).find("option:selected").attr("code");
            var allPortId = $(".allPortId").eq(i).val();
            var allPortSignalType = $(".allPortSignalType").eq(i).find("option:selected").text();
            allPortSignalType = "("+ allPortSignalType + ")";
            var allPortSignal = $(".allPortSignal").eq(i).val();
            var allPortfireK = $(".allPortfireK").eq(i).val();
            var allPortfireB = $(".allPortfireB").eq(i).val();
            var allPortfireTop = $(".allPortfireTop").eq(i).val();
            var allPortfireLow = $(".allPortfireLow").eq(i).val();
            var allPortMNUint = $(".allPortMNUint").eq(i).val();
            var allPortNormalNum = $(".allPortNormalNum").eq(i).val();
            var allPortNormalName = $(".allPortNormalName").eq(i).val();
            var allPortAbnormalName = $(".allPortAbnormalName").eq(i).val();
            var allid = $("#portTable233 tr").eq(i).attr("eqid");
            var alldetialid = $("#portTable233 tr").eq(i).attr("detialid");
            if(code == "17"){
                list.push({
                    detailid : alldetialid,
                    ioport : allPortId,
                    analogk : allPortfireK,
                    analogb : allPortfireB,
                    analogup : allPortfireTop,
                    analogdown : allPortfireLow,
                    analogunit : allPortMNUint,
                    maxValue:"",
                    minValue:"",
                    reserve : allPortSignal,
                    detialname : allPortSignalType
                });
            }else{
                list.push({
                    detailid : alldetialid,
                    ioport : allPortId,
                    digitalnormal : allPortNormalNum,
                    goodname : allPortNormalName,
                    badname : allPortAbnormalName,
                    detailname : allPortSignalType,
                    reserve:allPortSignal
                });
            }

            if(code == "27" || code == "18" || code == "4"){
                allPortActionType = "3";
            }else if(code == "17"){
                allPortActionType = "4";
            }else if(code == "28" || code == "20"){
                allPortActionType = "1";
            }else{
                allPortActionType = "2";
            }

            if(code == "2" || code == "3" || code == "5" || code == "6" || code == "7" || code == "8" || code == "19") {
                data.push({
                    unitid: allPortUnit,
                    buildid: allPortBuild,
                    buildareaid: allPortArea,
                    netdeviceid: allPortNetDeviceId,
                    installposition: allPortPosition,
                    eqname: allPortEqName,
                    eqsystemcode: allPortEqSystem,
                    eqclassid: allPortEqClass,
                    eqclassname: allPortEqClassName,
                    eqsystemid: allPortEqSystem,
                    actionType: allPortActionType,
                    deviceIndex: 3,
                    detailid : alldetialid,
                    ioport : allPortId,
                    analogk : allPortfireK,
                    analogb : allPortfireB,
                    analogup : allPortfireTop,
                    analogdown : allPortfireLow,
                    analogunit : allPortMNUint,
                    maxValue:"",
                    minValue:"",
                    reserve : allPortSignal,
                    detialname : allPortSignalType,
                    id: allid,
                    protocoltype: "",
                    eqmodel: "",
                    partcode: "",
                    loopcode: "",
                    positionnumber: "",
                    installdate: "",
                    pointx: "",
                    pointy: "",
                    manufacturer: "",
                    manufacturerphone: "",
                    brand: "",
                    validitydate: "",
                    productdate: "",
                    supplier: "",
                    pointcode: "",
                    status: "",
                    statustime: "",
                    usetime: "",
                    pipediameter: "",
                    eqcapacity: "",
                    eqflow: "",
                    eqlift: "",
                    pipetype: "",
                    eqspace: "",
                    airvolume: "",
                    eqpower: "",
                    eqchangedate: "",
                    lastupdate: "",
                    ownercode: null,
                    systemtype: "",
                    systemadd: "",
                    datafrom: "",
                    datafromid: "",
                    isneedinspect: "",
                    qrcode: "",
                    floors: "",
                    unitName: "",
                    remark: "",
                    eqid: ""
                });
            }else if(code == "4" || code == "18" || code == "27") {
                data.push({
                    unitid: allPortUnit,
                    buildid: allPortBuild,
                    buildareaid: allPortArea,
                    netdeviceid: allPortNetDeviceId,
                    installposition: allPortPosition,
                    eqname: allPortEqName,
                    eqsystemcode: allPortEqSystem,
                    eqclassid: allPortEqClass,
                    eqclassname: allPortEqClassName,
                    eqsystemid: allPortEqSystem,
                    actionType: allPortActionType,
                    deviceIndex: 3,
                    list: list,
                    id: allid,
                    protocoltype: "",
                    eqmodel: "",
                    partcode: "",
                    loopcode: "",
                    positionnumber: "",
                    installdate: "",
                    pointx: "",
                    pointy: "",
                    manufacturer: "",
                    manufacturerphone: "",
                    brand: "",
                    validitydate: "",
                    productdate: "",
                    supplier: "",
                    pointcode: "",
                    status: "",
                    statustime: "",
                    usetime: "",
                    pipediameter: "",
                    eqcapacity: "",
                    eqflow: "",
                    eqlift: "",
                    pipetype: "",
                    eqspace: "",
                    airvolume: "",
                    eqpower: "",
                    eqchangedate: "",
                    lastupdate: "",
                    ownercode: null,
                    systemtype: "",
                    systemadd: "",
                    datafrom: "",
                    datafromid: "",
                    isneedinspect: "",
                    qrcode: "",
                    floors: "",
                    unitName: "",
                    remark: "",
                    eqid: ""
                });
            }else if(code == "20" || code == "28"){
                data.push({
                    unitid: allPortUnit,
                    buildid: allPortBuild,
                    buildareaid: allPortArea,
                    netdeviceid: allPortNetDeviceId,
                    installposition: allPortPosition,
                    eqname: allPortEqName,
                    eqsystemcode: allPortEqSystem,
                    eqclassid: allPortEqClass,
                    eqclassname: allPortEqClassName,
                    eqsystemid: allPortEqSystem,
                    actionType: allPortActionType,
                    deviceIndex: 3,
                    detailid : alldetialid,
                    ioport : allPortId,
                    digitalnormal : allPortNormalNum,
                    goodname : allPortNormalName,
                    badname : allPortAbnormalName,
                    detialname : allPortSignalType,
                    reserve:allPortSignal,
                    id: allid,
                    protocoltype: "",
                    eqmodel: "",
                    partcode: "",
                    loopcode: "",
                    positionnumber: "",
                    installdate: "",
                    pointx: "",
                    pointy: "",
                    manufacturer: "",
                    manufacturerphone: "",
                    brand: "",
                    validitydate: "",
                    productdate: "",
                    supplier: "",
                    pointcode: "",
                    status: "",
                    statustime: "",
                    usetime: "",
                    pipediameter: "",
                    eqcapacity: "",
                    eqflow: "",
                    eqlift: "",
                    pipetype: "",
                    eqspace: "",
                    airvolume: "",
                    eqpower: "",
                    eqchangedate: "",
                    lastupdate: "",
                    ownercode: null,
                    systemtype: "",
                    systemadd: "",
                    datafrom: "",
                    datafromid: "",
                    isneedinspect: "",
                    qrcode: "",
                    floors: "",
                    unitName: "",
                    remark: "",
                    eqid: ""
                });
            }else if(code == "17"){
                data.push({
                    unitid: allPortUnit,
                    buildid: allPortBuild,
                    buildareaid: allPortArea,
                    netdeviceid: allPortNetDeviceId,
                    installposition: allPortPosition,
                    eqname: allPortEqName,
                    eqsystemcode: allPortEqSystem,
                    eqclassid: allPortEqClass,
                    eqclassname: allPortEqClassName,
                    eqsystemid: allPortEqSystem,
                    actionType: allPortActionType,
                    deviceIndex: 3,
                    portLists: list,
                    id: allid,
                    protocoltype: "",
                    eqmodel: "",
                    partcode: "",
                    loopcode: "",
                    positionnumber: "",
                    installdate: "",
                    pointx: "",
                    pointy: "",
                    manufacturer: "",
                    manufacturerphone: "",
                    brand: "",
                    validitydate: "",
                    productdate: "",
                    supplier: "",
                    pointcode: "",
                    status: "",
                    statustime: "",
                    usetime: "",
                    pipediameter: "",
                    eqcapacity: "",
                    eqflow: "",
                    eqlift: "",
                    pipetype: "",
                    eqspace: "",
                    airvolume: "",
                    eqpower: "",
                    eqchangedate: "",
                    lastupdate: "",
                    ownercode: null,
                    systemtype: "",
                    systemadd: "",
                    datafrom: "",
                    datafromid: "",
                    isneedinspect: "",
                    qrcode: "",
                    floors: "",
                    unitName: "",
                    remark: "",
                    eqid: ""
                });
            }
        }
        if (allPortBuild == "") {
            layer.msg("建筑不能为空！");
            return;
        }
        if (allPortArea == "") {
            layer.msg("区域不能为空！");
            return;
        }
        if (allPortPosition == "") {
            layer.msg("监测位置不能为空！");
            return;
        }
        if (allPortEqName == "") {
            layer.msg("设备设施不能为空！");
            return;
        }
        if (allPortEqSystem == "") {
            layer.msg("系统类型不能为空！");
            return;
        }
        if (allPortEqClass == "") {
            layer.msg("设备类型不能为空！");
            return;
        }
        if (allPortSignalType == "") {
            layer.msg("端口信号类型不能为空！");
            return;
        }
        data = JSON.stringify(data);
        $.ajax({
            type : "post",
            async : true,
            beforeSend: function(request){
                request.setRequestHeader("Authorization","Bearer "+login.token);
            },
            data : data,
            url : changeurl,
            contentType : "application/json",
            dataType : "json",
            success : function(d) {
                if (d.success) {
                    layer.msg("保存成功！");
                    $("#addAllPort").modal("hide");
                } else {
                    layer.msg(d.msg);
                }
            },
            error : function(e) {

            }
            // complete : function() {
            //     if (isAsync) {
            //         layer.close(lid);
            //     }
            // }
        });
    })
});

// 绑定编号
function bind() {
    var unitId = $("#unitId").val();
    var soureaddress = $("#soureaddress").val();

    if (soureaddress.length != 12) {
        layer.msg("请输入12位编号");
        return;
    }

    // 添加到选项里做准备
    var num = $("#newnetDevice-ownercode").siblings(".combo-dropdown").children().length;
    $(".combo-input").eq(1).val(soureaddress);
    var option = '<option value="'+ soureaddress +'">'+ soureaddress +'</option>';
    var li = '<li class="option-item" data-index="'+ num +'" data-value="'+ soureaddress +'" style="display: list-item;">'+ soureaddress +'</li>';

    var url = baseUrl + '/baseInfoRelation/add';
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            unitid: unitId,
            soureaddress: soureaddress
        },
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                bindingNumberSearch(unitId);
                layer.msg(d.msg);

                // 添加到选项里
                $("#newnetDevice-ownercode").append(option);
                $("#newnetDevice-ownercode").siblings(".combo-dropdown").append(li);
                $("#newnetDevice-ownercode").val(soureaddress);

                $("#soureaddress").val("");
            } else {
                layer.msg(d.msg);
            }

        }
    });
}

// 删除绑定编号
function deleteBinding(_this) {
    var unitId = $("#unitId").val();
    var select = _this.id;
    var address = _this.soureaddress;
    layer.open({
        content: '确认删除选中编号信息？',
        btn: ['确认', '取消'],
        yes: function () {
            // 删除选项里的内容
            $("#newnetDevice-ownercode").find("option[value='"+address+"']").remove();
            $("#newnetDevice-ownercode").siblings(".combo-dropdown").find("li[data-value='"+address+"']").remove();
            $.ajax({
                url: baseUrl + "/baseInfoRelation/delete",
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
                        bindingNumberSearch(unitId);
                        layer.msg(data.msg);
                    } else {
                        layer.msg(data.msg);
                    }
                },
                error: function (data) {

                }
            });
        },
        cancel: function () {
            // 右上角关闭回调
        }
    });
}

// 编号查询
function bindingNumberSearch(unitId) {
    $('#baseInfoRelTable').bootstrapTable('destroy');
    $('#baseInfoRelTable')
        .bootstrapTable({
            url: baseUrl + '/baseInfoRelation/list', // 请求后台的URL（*）
            method: 'get', // 请求方式（*）
            contentType: 'application/x-www-form-urlencoded',
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true, // 是否显示行间隔色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, // 是否显示分页（*）
            sortable: false, // 是否启用排序
            sortOrder: "asc", // 排序方式
            queryParamsType: "undefined", // 排序方式
            ajaxOptions:{
                headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
            },
            queryParams: function queryParams(params) { // 设置查询参数
                var param = {
                    pageNumber: this.pageNumber,
                    pageSize: this.pageSize,
                    unitId: unitId
                };
                $("#pageSize").val(this.pageSize);
                $("#pageNumber").val(this.pageNumber);
                return param;
            },
            dataField: 'list',
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: [10, 25, 50, 100, 200, 500], // 可供选择的每页的行数（*）
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            searchTimeOut: 1000,
            // : true,
            minimumCountColumns: 2, // 最少允许的列数
            clickToSelect: true, // 是否启用点击选中行
            singleSelect: false,
            // height: 570, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id", // 每一行的唯一标识，一般为主键列
            // showToggle: true, //是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表
            columns: [{
                field: 'soureaddress',
                title: '绑定编号'
            },
                {
                    field: 'unitname',
                    title: '单位名称'
                },
                {
                    field: '',
                    title: '操作',
                    edit: false,
                    formatter: function (value, row, index) {
                        var str = "";
                        /*
                         * str += '<button type="button"
                         * class="btn btn-new btn-xs cBtn-main
                         * edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>';
                         */
                        str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>';
                        return str;
                    },
                    events: {
                        'click .del': function (e, value, row,
                                                index) {
                            deleteBinding(row);
                        }

                    }
                }
            ],
        });
}




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
    // emptySearch();
    $("#ownercode").val('');
    $("#deviceIndex").val('');
    $("#deviceNo").val('');
    $("#deviceName").val('');
    $("#unitName").val('');
    if ($(this).val() == 'deviceIndex') {
        var html='<select id="searchContent" name="searchContent" class="form-control">'
            +'<option value="">全部</option>'
            +'<option value="1">主机</option>'
            +'<option value="2">用户传输装置</option>'
            +'<option value="3">数据传输模块(数字量、模拟量)</option>'
            +'</select>';
        $("#commonInput").html(html);
    } else {
        var id = $(this).val();
        if (id == "") {
            $("#userTable").bootstrapTable('refresh');
        }
        if(!$('#searchContent').hasClass('showInput')){
            $("#commonInput").html('<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
        }
    }
});


function emptySearch() {
    $("#ownercode").val('');
    $("#deviceIndex").val('');
    $("#deviceNo").val('');
    $("#deviceName").val('');
    $("#unitName").val('');
}
// 编辑
function editBaseInfo(_this) {
    $.ajax({
        url : baseUrl + "/netDevice/hasEquipments",
        type:"get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:{
            id : _this.id
        },
        dataType : "json",
        async:false,
        success:function(data){
            if(data.obj){
                $("#newnetDevice-deviceindex").attr("disabled","disabled");
            }else{
                $("#newnetDevice-deviceindex").removeAttr("disabled","disabled");
            }
        }

    });

    showUnitList(_this.unitid);
    $("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑联网设备");
    $("#newEmploy .btnSure").show();
    $("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    $("#newnetDevice-deviceno").val(_this.deviceno);
    var deviceindex = _this.deviceindex;
    if(deviceindex == 2){
        $("#newnetDevice-deviceno").attr("disabled","disabled");
    }else{
        $("#newnetDevice-deviceno").removeAttr("disabled","disabled");
    }
    $("#newnetDevice-deviceindex").val(deviceindex);
    ownerCode(_this);
    $("#newnetDevice-name").val(_this.name);
    $("#newnetDevice-eqsystemid").val(_this.eqsystemid);
    showModelList(_this.eqmodel);
    var isIndependent = _this.isIndependent;
    if (isIndependent == 1) {
        $("#newnetDevice-isIndependent1").prop("checked", "checked");
        $("#newnetDevice-isIndependent0").removeAttr("checked");
    } else if(isIndependent != "" && isIndependent == 0){
        $("#newnetDevice-isIndependent0").prop("checked", "checked");
        $("#newnetDevice-isIndependent1").removeAttr("checked");
    } else {
        $("#newnetDevice-isIndependent0").removeAttr("checked");
        $("#newnetDevice-isIndependent1").removeAttr("checked");
    }
    $(".bindNumber").hide();
    $(".btnSure").html("<i class='fa fa-floppy-o'></i>&nbsp;保存");
}
//查看
function viewBaseInfo(_this) {
    showUnitList(_this.unitid);
    $("#newEmployView").modal("show");
    $("#newnetDeviceView-deviceno").val(_this.deviceno);
    $("#newnetDeviceView-deviceindex").val(_this.deviceindex);
    $("#newnetDeviceView-unitid").val(_this.unitid);
    ownerCode(_this);
    $("#newnetDeviceView-name").val(_this.name);
    $("#newnetDeviceView-eqsystemid").val(_this.eqsystemid);
    showModelList(_this.eqmodel);
    var isIndependent = _this.isIndependent;
    if (isIndependent == 1) {
        $("#newnetDeviceView-isIndependent1").prop("checked", "checked");
        $("#newnetDeviceView-isIndependent0").removeAttr("checked");
    } else if(isIndependent != "" && isIndependent == 0){
        $("#newnetDeviceView-isIndependent0").prop("checked", "checked");
        $("#newnetDeviceView-isIndependent1").removeAttr("checked");
    } else {
        $("#newnetDeviceView-isIndependent0").removeAttr("checked");
        $("#newnetDeviceView-isIndependent1").removeAttr("checked");
    }

//    var userHeader = _this.userHeader;
//    if(userHeader){
//    	$("#loadImgView").html(
//    			" <li style='position: relative;margin: 0px;border: none;'> " +
//    			"  <img src='" + userHeader + "' " +
//    			" style = 'width:100%;height:100%;'/>" +
//    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
//    			" </a>"+
//    			" </li>"
//    	);
//    }else{
//    	$("#loadImgView").html("<li class='modalImg'><span>暂无图片</span></li>");
//    }
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中设备信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/netDevice/delete",
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
        url: baseUrl + '/netDevice/list',         //请求后台的URL（*）
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
                ownercode: $("#ownercode").val(),
                deviceindex: $("#deviceIndex").val(),
                deviceno: $("#deviceNo").val(),
                name: $("#deviceName").val(),
                unitname: $("#unitName").val()
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
                field: 'unitname',
                title: '单位名称'
            },
            {
                field: 'name',
                title: '设备名称'
            },
            {
                field: 'ownercode',
                title: '设备编号'
            },
            {
                field: 'deviceno',
                title: '设备子号'
            },
            {
                field: 'deviceindex',
                title: '设备类型',
                formatter:function(value, row, index){
                    if(row.deviceindex==1){
                        return "主机";
                    }
                    if(row.deviceindex==2){
                        return "用户传输装置";
                    }
                    if(row.deviceindex==3){
                        return "数据传输模块(数字量、模拟量)";
                    }else{
                        return "其他";
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
                    str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    if(row.deviceindex==1){
                        str += '<button type="button" class="btn btn-primary btn-xs cBtn-main rel" style="margin-right: 10px;"><i class="fa fa-pencil"></i>&nbsp;关联网关</button>'
                    }else if(row.deviceindex==2){
                        str += '<button type="button" disabled="disabled" class="btn btn-primary btn-xs cBtn-main rel" style="margin-right: 10px;"><i class="fa fa-pencil"></i>&nbsp;关联网关</button>'
                    }else if(row.deviceindex==3){
                        str += '<button type="button" class="btn btn-primary btn-xs cBtn-main add" style="margin-right: 10px;"><i class="fa fa-pencil"></i>&nbsp;添加接口</button>'
                    }
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
                    },
                    'click .rel': function (e, value, row, index) {
                        rel(row);
                    },
                    'click .add': function (e, value, row, index) {
                        add(row);
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

// 填充单位下拉框
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
    $(".unitid").html(wrap);
    $(".unitid").comboSelect();
    $("#newnetDeviceView-unitid").siblings("input").attr('disabled','disabled');
    $("#newnetDeviceView-unitid").siblings(".combo-arrow").hide();
}

//初始化单位下拉选择框
function initUnit(){
    /*$.ajax({
        url: baseUrl + "/xfzUser/unitSelect",
        type: "post",
        dataType: "json",
        success: function (data) {
        	if(data.success){
				for(var i=0;i<data.obj.length;i++){
					$(".unitid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].unitname+'</option>');
					$("#newnetDeviceView-unitid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].unitname+'</option>');
            	}
			}
        }
    })*/


    $("#newnetDevice-unitid").change(function(){
        $("#newnetDevice-ownercode").empty();
        $("#newnetDevice-ownercode").append('<option selected="selected" value="">---请选择---</option>');
        var unitid = $("#newnetDevice-unitid").val();
        $.ajax({
            url: baseUrl + "/baseInfoRelation/soureAddressSelect",
            data:{
                unitid:unitid
            },
            beforeSend: function(request){
                request.setRequestHeader("Authorization","Bearer "+login.token);
            },
            type: "post",
            success: function (data) {
                if(data.success){
                    for(var i=0;i<data.obj.length;i++){
                        $("#newnetDevice-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
                        $("#newnetDeviceView-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
                    }
                    $("#newnetDevice-ownercode").comboSelect();
                }
            }
        })
        // $.post(
        //     baseUrl + "/baseInfoRelation/soureAddressSelect",
        //     {
        //         unitid:unitid
        //     },
        //     function(data){
        //         if(data.success){
        //             for(var i=0;i<data.obj.length;i++){
        //                 $("#newnetDevice-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
        //                 $("#newnetDeviceView-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
        //             }
        //             $("#newnetDevice-ownercode").comboSelect();
        //         }
        //     }
        // );
    });

    $("#unitid").change(function(){
        $('#mainSiteTable').bootstrapTable("destroy");
        $("#addSite .modal-title").html("导入联网设备");
        $("#addSite").modal();
        var unitid = $("#unitid").val();
        $('#mainSiteTable').bootstrapTable({
            url: baseUrl + '/netDevice/getImportList',         //请求后台的URL（*）
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
                    unitid: unitid

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
                    field: 'devicename',
                    title: '设备名称'
                },
                {
                    field: 'ownercode',
                    title: '设备编号'
                },
                {
                    field: 'deviceno',
                    title: '设备子号'
                },
                {
                    field: 'deviceindex',
                    title: '设备类型'
                }
            ]
        });
    });

}
//初始化系统类型下拉框
function initEqSystem(){
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/eqClass/getSystemList",
        dataType: "json",
        success: function (data) {
            if (data.success) {
                for(var i=0;i<data.obj.length;i++){
                    $("#newnetDevice-eqsystemid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].eqsystemname+'</option>');
                    $("#newnetDeviceView-eqsystemid").append('<option value="'+data.obj[i].id+'">'+data.obj[i].eqsystemname+'</option>');
                }
                SystemList = data.obj;
                showSystemList();
            } else {
                layer.msg(data.desc);
            }
        },
        error: function (e) {

        }
    });
}
//初始化设备型号下拉框
function initEquipmentModel(){
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl+"/code/getListByGroupKey",
        data:{
            codeGroupKey:"EquipmentMode"
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
//				for(var i=0;i<data.obj.length;i++){
//					$("#newnetDevice-equipmentmodel").append('<option value="'+data.obj[i].codeid+'">'+data.obj[i].codename+'</option>');
//					$("#newnetDeviceView-equipmentmodel").append('<option value="'+data.obj[i].codeid+'">'+data.obj[i].codename+'</option>');
//				}
//				$(".equipmentmodel").comboSelect();
//				$("#newnetDeviceView-equipmentmodel").siblings("input").attr('disabled','disabled');
//				$("#newnetDeviceView-equipmentmodel").siblings(".combo-arrow").hide();
                modelList = data.obj;
            } else {
                layer.msg(data.desc);
            }
        },
        error: function (e) {

        }
    });
}

function showModelList(modelId){
    var option;
    var wrap='<option value="">--搜索--</option>';
    for(var i = 0;i<modelList.length;i++){
        if(modelId && modelId==modelList[i].codeid){
            option = '<option selected value="'+modelList[i].codeid+'" >'+modelList[i].codename+'</option>';
        }else{
            option = '<option value="'+modelList[i].codeid+'" >'+modelList[i].codename+'</option>';
        }
        wrap+=option;
    }
    $(".equipmentmodel").html(wrap);
    $(".equipmentmodel").comboSelect();
    $("#newnetDeviceView-equipmentmodel").siblings("input").attr('disabled','disabled');
    $("#newnetDeviceView-equipmentmodel").siblings(".combo-arrow").hide();

}

function showTable(){
    $('#mainSiteTable').bootstrapTable({
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
                field: 'devicename',
                title: '设备名称'
            },
            {
                field: 'ownercode',
                title: '设备编号'
            },
            {
                field: 'deviceno',
                title: '设备子号'
            },
            {
                field: 'deviceindex',
                title: '设备类型'
            }
        ]
    });
}




//导入按钮请求
$(".SiteInto").click(function () {
    var checked = $("#mainSiteTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/netDevice/addAll";
    var unitid = $("#unitid").val();
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            ids:vals,
            unitid:unitid
        },
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $('#inspectSiteTable').bootstrapTable("refresh");
                layer.msg("导入成功！");
                $("#userTable").bootstrapTable("refresh");
            } else {
                layer.msg(d.desc);
            }
            $("#addSite").modal("hide");
        }
    });
});

function ownerCode(_this){
    $("#newnetDevice-ownercode").empty();
    $("#newnetDeviceView-ownercode").empty();
    var unitid = _this.unitid;
    $.ajax({
        url: baseUrl + "/baseInfoRelation/soureAddressSelect",
        data:{
            unitid:unitid
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        type: "post",
        success: function (data) {
            if(data.success){
                for(var i=0;i<data.obj.length;i++){
                    $("#newnetDevice-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
                    $("#newnetDeviceView-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
                }
            }
            $("#newnetDevice-ownercode").val(_this.ownercode);
            $("#newnetDevice-ownercode").comboSelect();
            $("#newnetDeviceView-ownercode").val(_this.ownercode);
        }
    })
    // $.post(
    //     baseUrl + "/baseInfoRelation/soureAddressSelect",
    //     {
    //         unitid:unitid
    //     },
    //     function(data){
    //         if(data.success){
    //             for(var i=0;i<data.obj.length;i++){
    //                 $("#newnetDevice-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
    //                 $("#newnetDeviceView-ownercode").append('<option value="'+data.obj[i].soureaddress+'">'+data.obj[i].soureaddress+'</option>');
    //             }
    //         }
    //         $("#newnetDevice-ownercode").val(_this.ownercode);
    //         $("#newnetDevice-ownercode").comboSelect();
    //         $("#newnetDeviceView-ownercode").val(_this.ownercode);
    //     }
    // );

}
$("#newnetDevice-deviceindex").click(function(){
    var deviceIndex = $(this).val();
    if(deviceIndex == 2){
        $("#newnetDevice-deviceno").val(1);
        $("#newnetDevice-deviceno").attr("disabled","disabled");
    }else{
        $("#newnetDevice-deviceno").val("");
        $("#newnetDevice-deviceno").removeAttr("disabled","disabled");
    }
});

//关联网关
function rel(_this){
    netDeviceID = _this.id
    $("#gateway").modal("show");
    $("#AssociatedList").bootstrapTable("destroy");
    //加载未关联网关列表
    $("#UnrelatedList").bootstrapTable("destroy");
    //加载已关联网关列表
    AssociatedList("");
    //加载未关联网关列表
    UnrelatedList("");
    // 初始化地图
    loadMap();
}

//未关联网关列表
function UnrelatedList(code) {
    $('#UnrelatedList').bootstrapTable({
        url: baseUrl + '/wirelessDevice/getUnrelatedList',         //请求后台的URL（*）
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
                partcode:code,
                netdeviceid:netDeviceID
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
//        showColumns: true,                  //是否显示所有的列
//        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'selectItem',
                checkbox: true
            },
            {
                field: 'code',
                title: '设备ID',
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "";
                    }
                    return '<p title="'+value+'" style="width: 150px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
                }
            },
            {
                field: 'devicecode',
                title: '二维码编号',
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "";
                    }
                    return '<p title="'+value+'" style="width: 15\0px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
                }
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                    str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .view': function (e, value, row, index) {
                        viewGateway(row);
                    }
                }
            }
        ]
    });
}

//已关联网关列表
function AssociatedList(code) {
    $('#AssociatedList').bootstrapTable({
        url: baseUrl + '/wirelessDevice/getAssociatedList',         //请求后台的URL（*）
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
                netdeviceid:netDeviceID,
                partcode:code
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
//        showColumns: true,                  //是否显示所有的列
//        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'selectItem',
                checkbox: true
            },
            {
                field: 'code',
                title: '设备ID',
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "";
                    }
                    return '<p title="'+value+'" style="width: 150px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
                }
            },
            {
                field: 'devicecode',
                title: '二维码编号',
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "";
                    }
                    return '<p title="'+value+'" style="width: 150px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
                }
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                    str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    return str;
                },
                events: {
                    'click .view': function (e, value, row, index) {
                        viewGateway(row);
                    }
                }
            }
        ]
    });
}

//编辑网关
function editGateway(_this){
    netDeviceID = _this.id;
    $("#editGateWay").modal("show");
    $("#gatewaycode").val(_this.code);
    $("#importNetDevice").val(_this.netdeviceid);
    $("#installaddr").val(_this.installaddr);
    $("#notifyphone").val(_this.notifyphone);
    $("#indexId").val(_this.id);
    $("#devicecode").val(_this.devicecode);
    $("#newFirePower-pointX").val(_this.lon);
    $("#newFirePower-pointY").val(_this.lat);
    $("#heartbeat").val(_this.heartbeat);
    if(_this.isphone == "0"){
        $("#AutomaticDialing0").prop("checked",true);
    }else{
        $("#AutomaticDialing1").prop("checked",true);
    }
}
//编辑
$(".btnUpdate").click(function () {
    $("#editGateWay").modal("show");
    var installaddr = $("#installaddr").val();
    var notifyphone = $("#notifyphone").val();
    var lon = $("#newFirePower-pointX").val();
    var lat = $("#newFirePower-pointY").val();
    var id = $("#indexId").val();
    var heartbeat = $("#heartbeat").val();
    var isphone = $("input:radio[name='AutomaticDialing']:checked").val();
    var data = {
        id:id,
        installaddr:installaddr,
        notifyphone:notifyphone,
        netdeviceid:netDeviceID,
        lon:lon,
        lat:lat,
        netdeviceid:netDeviceID,
        heartbeat:heartbeat,
        isphone:isphone
    };
    $.ajax({
        url: baseUrl + "/wirelessDevice/edit",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: data,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                layer.msg(d.msg);
                $("#AssociatedList").bootstrapTable("refresh");
                $("#editGateWay").modal("hide");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (data) {

        }
    });
});

//取消关联
$(".delGateWay").click(function () {
    var checked = $("#AssociatedList").bootstrapTable('getSelections');
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

    var url = baseUrl + "/netDevice/removeGateWay";
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"gateWayID": vals},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $('#UnrelatedList').bootstrapTable("refresh");
                $('#AssociatedList').bootstrapTable("refresh");
                layer.msg("取消成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});
function reGateway(_this){

}

//查看网关
function viewGateway(_this){
    $("#gateWayView").modal("show");
    $("#gatewaycodeView").val(_this.code);
    $("#importNetDeviceView").val(_this.netdeviceid);
    $("#installaddrView").val(_this.installaddr);
    $("#notifyphoneView").val(_this.notifyphone);
    $("#indexId").val(_this.id);
    $("#devicecodeView").val(_this.devicecode);
    $("#heartbeatView").val(_this.heartbeat);
    $("#importNetDeviceView").attr("disabled","disabled");
    if(_this.isphone == "0"){
        $("#AutomaticDialingView0").prop("checked",true);
    }else{
        $("#AutomaticDialingView1").prop("checked",true);
    }
}

//根据网关ID查询(未关联)
$(".searchGateWay1").click(function () {
    var code = $("#partcode1").val();
    //加载未关联网关列表
    $("#UnrelatedList").bootstrapTable("destroy");
    UnrelatedList(code);
});

//根据网关ID查询(已关联)
$(".searchGateWay2").click(function () {
    var code = $("#partcode2").val();
    $("#AssociatedList").bootstrapTable("destroy");
    AssociatedList(code);
});

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

//批量添加关联
$(".addGateWay").click(function () {
    var checked = $("#UnrelatedList").bootstrapTable('getSelections');
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
    var url = baseUrl + "/netDevice/addGateWay";
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {"gateWayID": vals,
            "netDeviceID":netDeviceID
        },
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $('#UnrelatedList').bootstrapTable("refresh");
                $('#AssociatedList').bootstrapTable("refresh");
                layer.msg("关联成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

//地图
function loadMap() {
    setTimeout(function() {// 添加延时加载。解决问题
        map = new window.BMap.Map("maps");
        map.enableScrollWheelZoom(); // 鼠标滚动放大缩小
        // map.centerAndZoom("上海",12);//默认中心点坐标
        var myCity = new window.BMap.LocalCity();
        myCity.get(function(res) {
            map.centerAndZoom(res.center, res.level);
        });
        local = new BMap.LocalSearch(map, {
            //renderOptions:{map: map},
            onSearchComplete:function(results){
                map.clearOverlays();
                console.log(results)
                var len = results.getCurrentNumPois();
                for(var i = 0 ; i < len ; i++){
                    if(i > 0){
                        break;
                    }
                    var msg = results.getPoi(i)
                    var baidu_Point = new BMap.Point(msg.point.lng,msg.point.lat);
                    var poision = msg.point.lng + "," + msg.point.lat;
                    $("#newUnit-unitpoint").val(poision);
                    var marker = new BMap.Marker(baidu_Point); // 标记
                    map.addOverlay(marker);
                    marker.enableDragging();// 可以拖拽标点
                    marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
                        var p = marker.getPosition();// 获取marker的位置
                        var poision3 = p.lng + "," + p.lat;
                        $("#newUnit-unitpoint").val(poision3);
                        // alert("marker的位置是" + p.lng + "," + p.lat);
                    });
                    marker.addEventListener("click",function(e){
                            var content = '<div class="BMap_bubble_title" style="overflow: hidden; height: auto; line-height: 24px; white-space: nowrap; width: 200px;"><p style="width:210px;font:bold 14px/16px arial,sans-serif;margin:0;color:#cc5522;white-space:nowrap;overflow:hidden" title="'+msg.title+'">'+msg.title+'<a target="_blank" href="'+msg.url+'" style="margin-left:5px;font-size:12px;color:#3d6dcc;font-weight:normal;text-decoration:none;">详情»</a></p></div>'
                                +'<div class="BMap_bubble_content" style="width: auto; height: auto;"><div style="font:12px arial,sans-serif;margin-top:10px">'+
                                '<table cellspacing="0" style="overflow:hidden;table-layout:fixed;width:100%;font:12px arial,sans-serif"><tbody>'+
                                '<tr><td style="vertical-align:top;width:38px;white-space:nowrap;word-break:keep-all">地址：&nbsp;</td><td style="line-height:16px">'+(msg.address||"")+'</td></tr>'+
                                '<tr><td style="vertical-align:top;">电话：</td><td>'+(msg.phoneNumber||"")+'</td></tr>'+
                                '<tr><td style="vertical-align:top;"></td></tr>'+
                                //						'<tr><td style="vertical-align:top;"></td><td><div style="float:right;background:#337ab7;cursor:pointer;color:#fff;width:45px;height:20px;line-height:20px;text-align:center;border-radius:5px;" onclick="getThisPoint(\''+msg.point.lng+','+msg.point.lat+'\');">选中</div></td></tr>'+
                                '</tbody></table></div></div>'
                            var p = e.target;
                            var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                            var opts = {
                                width : 200,     // 信息窗口宽度
                                height: 80,     // 信息窗口高度
                            };
                            var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
                            map.openInfoWindow(infoWindow,point); //开启信息窗口
                        }
                    );
                    map.centerAndZoom(marker.getPosition(), 14);
                }
            }
        });
    }, 600);
}

function showMap() {
    $("#mapModal").modal("show");
    var myCity = new window.BMap.LocalCity();
    myCity.get(function (res) {
        map.centerAndZoom(res.center, res.level);
    });

    setTimeout(function () {
        map.clearOverlays(); // 清除地图上点

        var pointX = $("#newFirePower-pointX").val();
        var pointY = $("#newFirePower-pointY").val();
        if (pointX != "" && pointY != "") {
            var baidu_Point = new BMap.Point(pointX, pointY); // 经纬度坐标
            var marker = new BMap.Marker(baidu_Point); // 标记
            map.addOverlay(marker);
            map.panTo(baidu_Point);
            marker.enableDragging();// 可以拖拽标点
            marker.addEventListener("dragend", function (e) {// 拖拽获取marker位置
                var p = marker.getPosition();// 获取marker的位置
                $("#newFirePower-pointX").val(p.lng);
                $("#newFirePower-pointY").val(p.lat);
                // alert("marker的位置是" + p.lng + "," + p.lat);

                /*
                 * // 用经纬度设置地图中心点 function theLocation(){
                 * if(document.getElementById("longitude").value != "" &&
                 * document.getElementById("latitude").value != ""){
                 * map.clearOverlays(); var new_point = new
                 * BMap.Point(document.getElementById("longitude").value,document.getElementById("latitude").value);
                 * var marker = new BMap.Marker(new_point); // 创建标注
                 * map.addOverlay(marker); // 将标注添加到地图中
                 * map.panTo(new_point); } }
                 */
            });
        } else {
            var myCity = new window.BMap.LocalCity();
            myCity.get(function (res) {
                map.centerAndZoom(res.center, res.level);
            });
        }
    }, 500);

    map.disableDoubleClickZoom(); // 禁用鼠标双击放大
    map.enableKeyboard(); // 启用键盘上下左右键移动地图
    map.addEventListener('dblclick', function (e) {// 双击事件
        map.clearOverlays(); // 清除地图上点
        // alert("marker的位置是" + e.point.lng + "," + e.point.lat);
        $("#newFirePower-pointX").val(e.point.lng);
        $("#newFirePower-pointY").val(e.point.lat);
        var baidu_Point = new BMap.Point(e.point.lng, e.point.lat); // 经纬度坐标
        var marker = new BMap.Marker(baidu_Point); // 标记
        map.addOverlay(marker);
        marker.enableDragging();// 可以拖拽标点
        marker.addEventListener("dragend", function (e) {// 拖拽获取marker位置
            var p = marker.getPosition();// 获取marker的位置
            $("#newFirePower-pointX").val(p.lng);
            $("#newFirePower-pointY").val(p.lat);
            // alert("marker的位置是" + p.lng + "," + p.lat);
        });

    });
}





//  端口模态框
// 保存并下一步后，加载端口模态框
function newAddPort(unitid,eqsystemid,deviceindex,name,deviceindexText){
    $("#newAdd").modal();
    $(".oneKeyFilling").show();		//新增时显示电气火灾的一键填充按钮
    Id = "";
    SetPortValue = 0;
    smokePortValue = 0;
    otherPortValue = 0;
    threePortValue = 0;
    faultPortValue = 0;
    ElevenPortValue = 0;
    customPortValue = 0;
    fourPortValue = 0;
    emptySearch();
    emptyClassList();
    emptyPortMsg();
    emptyDeviceList();
    emptyBuildList();
    emptyBuildAreaList();
    emptyFoutPortMsg();
    emptyFirePortMsg();
    emptyOtherPortMsg();
    $("#SetPort").hide();
    $("#smokePort").hide();
    $("#otherPort").hide();
    $("#threePort").hide();
    $("#faultPort").hide();
    $("#ElevenPort").hide();
    $("#customPort").hide();
    $("#fourPort").hide();
    $('#portTable').bootstrapTable("destroy");
    portSearch("");
    getUnitList();
    // showSystemList();
    $("#EqSystemCode").find("option:selected").val("");
    $("#newAdd .modal-title").html("新增设备设施");
    $("input[name=indexId][type=hidden]").val("");
    $("#preId").val('');
    $("#MNPort").hide();
    $("#SZPort").hide();
    $(".customPort").remove();
    unitID = unitid;
    systemID = eqsystemid;
    deviceIndex = deviceindex;
    showUnitListPort();
    getBuildList();
    showSystemList();
    getClassList(systemID,deviceindexText);
    getDeviceList(deviceIndex,name);
    $("#EqName").val(name);
}

// 保存端口信息
$(".btnSurePort")
    .click(
        function() {
            var data;
            $("#EqClassID").removeAttr("disabled");
            var UnitID = $("#UnitIDPort").find("option:selected").val();
            var buildId = $("#BuildId").find("option:selected").val();
            var buildAreaId = $("#BuildAreaId").find("option:selected")
                .val();
            var EqClassID = $("#EqClassID").find("option:selected")
                .val();
            var EqClassName = $("#EqClassID").find("option:selected")
                .text();
            var code = $("#EqClassID").find("option:selected").attr("code");
            var NetDeviceID = $("#NetDeviceID").find("option:selected")
                .val();
            var systemID = $("#EqSystemCode").find("option:selected")
                .val();
            var Position = $("#Position").val();
            var eqname = $("#EqName").val();
            if (UnitID == "") {
                layer.msg("单位不能为空！");
                return;
            } else if (buildId == "") {
                layer.msg("建筑不能为空！");
                return;
            } else if (buildAreaId == "") {
                layer.msg("区域不能为空！");
                return;
            } else if (systemID == "") {
                layer.msg("系统类型不能为空！");
                return;
            } else if (EqClassID == "") {
                layer.msg("设备类型不能为空！");
                return;
            } else if (NetDeviceID == "") {
                layer.msg("联网设备不能为空！");
                return;
            } else if (eqname == "") {
                layer.msg("设备名称不能为空！");
                return;
            } else if (Position == "") {
                layer.msg("监测位置不能为空！");
                return;
            }
            if(code == 1 || code == 21 || code == 22 || code == 23 || code == 24){
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "deviceindex" : 1,
                    "buildareaid" : buildAreaId,
                    "eqclassid" : EqClassID,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 0,
                    "eqsystemid" : systemID,
                    // "buildImgbg" : buildImgbg,
                    // "pointx" : pointx,
                    // "pointy" : pointy,
                    "eqclassname" : EqClassName
                    // "pointVideoId" : pointVideo
                }
            }else if(code == 16){
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "deviceindex" : 2,
                    "buildareaid" : buildAreaId,
                    "eqclassid" : EqClassID,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 0,
                    "eqsystemid" : systemID,
                    // "buildImgbg" : buildImgbg,
                    // "pointx" : pointx,
                    // "pointy" : pointy,
                    "eqclassname" : EqClassName
                    // "pointVideoId" : pointVideo
                }
            }
            var url = baseUrl + '/equipmentFac/add';

            if (SetPortValue == 1) {// 三个端口
                console.log("SetPortValue");
                var list = [];
                for (var i = 0; i < 3; i++) {
                    var ename = $("#Name" + i).html();
                    var detailid = $("#BDetailID" + i).val();
                    var ioport = $("#Port" + i).val();
                    var iotype = $("#Type" + i).val();
                    var digitalnormal = $("#Value" + i).find(
                        "option:selected").val();
                    var goodname = $("#One" + i).val();
                    var badname = $("#Zero" + i).val();
                    var reserve = $("#ShReserve" + i).val();
                    if (ioport != "" && digitalnormal != ""
                        && goodname != "" && badname != "") {
                        list.push({
                            detailid : detailid,
                            ioport : ioport,
                            digitalnormal : digitalnormal,
                            goodname : goodname,
                            badname : badname,
                            detailname : ename,
                            reserve:reserve
                        });
                    } else if (ioport == "" && digitalnormal == ""
                        && goodname == "" && badname == "") {
                        continue;
                    } else {
                        layer.msg("新增的端口必须填写完整的信息！");
                        return;
                    }
                    /*
                     * if(ioport == ""){ layer.msg("端口不能为空！"); return; }
                     * if(digitalnormal == ""){ layer.msg("报警值不能为空！");
                     * return; } if(goodname == ""){
                     * layer.msg("1含义不能为空！"); return; } if(badname ==
                     * ""){ layer.msg("0含义不能为空！"); return; }
                     */
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 3,
                    "eqsystemid" : systemID,
                    "eqclassname" : EqClassName,
                    "list" : JSON.stringify(list)
                };
            }
            if (smokePortValue == 1) {// 防排烟系统
                console.log("smokePortValue");

                var list = [];
                var ename='';

                for (var i = 0; i < $('.smokeControlPort').length; i++) {
                    if(i < 3){
                        ename = $('.smokeControlPort').eq(i).find('.PortId').html();
                    }else{
                        ename = "(" + $('.smokeControlPort').eq(i).find('input.PortId').val() + ")";
                    }
                    var detailid = $('.smokeControlPort').eq(i).find('.PortIdDetailId').val();
                    var ioport= $('.smokeControlPort').eq(i).find('.PortIdValue').val();

                    var iotype = $('.smokeControlPort').eq(i).find('.PortIdType').val();
                    var digitalnormal = $('.smokeControlPort').eq(i).find('.PortIdNormalNum').find('option:selected').val();
                    var goodname = $('.smokeControlPort').eq(i).find('.PortIdNormalName').val();
                    var badname = $('.smokeControlPort').eq(i).find('.PortIdAbnormalName').val();
                    var reserve = $('.smokeControlPort').eq(i).find('.PortIdSignal').val();

                    if (ioport != "" && digitalnormal != ""
                        && goodname != "" && badname != "") {
                        list.push({
                            detailid : detailid,
                            ioport : ioport,
                            digitalnormal : digitalnormal,
                            goodname : goodname,
                            badname : badname,
                            detailname : ename,
                            reserve:reserve
                        });
                    } else if (ioport == "" && digitalnormal == ""
                        && goodname == "" && badname == "") {
                        continue;
                    } else {
                        layer.msg("新增的端口必须填写完整的信息！");
                        return;
                    }
                    /*
                     * if(ioport == ""){ layer.msg("端口不能为空！"); return; }
                     * if(digitalnormal == ""){ layer.msg("报警值不能为空！");
                     * return; } if(goodname == ""){
                     * layer.msg("1含义不能为空！"); return; } if(badname ==
                     * ""){ layer.msg("0含义不能为空！"); return; }
                     */
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 3,
                    "eqsystemid" : systemID,
                    "eqclassname" : EqClassName,
                    "list" : JSON.stringify(list)
                };
            }
            if(fourPortValue == 1) {// 泵控制柜
                var list = [];
                var ename='';

                for (var i = 0; i < $('.pumpPort').length; i++) {
                    if(i < 4){
                        ename = $('.pumpPort').eq(i).find('.PortId').html();
                    }else{
                        ename = "(" + $('.pumpPort').eq(i).find('input.PortId').val() + ")";
                    }
                    var detailid = $('.pumpPort').eq(i).find('.PortIdDetailId').val();
                    var ioport= $('.pumpPort').eq(i).find('.PortIdValue').val();

                    var iotype = $('.pumpPort').eq(i).find('.PortIdType').val();
                    var digitalnormal = $('.pumpPort').eq(i).find('.PortIdNormalNum').find('option:selected').val();
                    var goodname = $('.pumpPort').eq(i).find('.PortIdNormalName').val();
                    var badname = $('.pumpPort').eq(i).find('.PortIdAbnormalName').val();
                    var reserve = $('.pumpPort').eq(i).find('.PortIdSignal').val();

                    if (ioport != "" && digitalnormal != ""
                        && goodname != "" && badname != "") {
                        list.push({
                            detailid : detailid,
                            ioport : ioport,
                            digitalnormal : digitalnormal,
                            goodname : goodname,
                            badname : badname,
                            detailname : ename,
                            reserve:reserve
                        });
                    } else if (ioport == "" && digitalnormal == ""
                        && goodname == "" && badname == "") {
                        continue;
                    } else {
                        layer.msg("新增的端口必须填写完整的信息！");
                        return;
                    }
                    /*
                     * if(ioport == ""){ layer.msg("端口不能为空！"); return; }
                     * if(digitalnormal == ""){ layer.msg("报警值不能为空！");
                     * return; } if(goodname == ""){
                     * layer.msg("1含义不能为空！"); return; } if(badname ==
                     * ""){ layer.msg("0含义不能为空！"); return; }
                     */
                }
                // console.log(list,'list')
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 3,
                    "eqsystemid" : systemID,
                    "eqclassname" : EqClassName,
                    "list" : JSON.stringify(list)
                };
            }
            if (faultPortValue == 1) {// 故障
                console.log("faultPortValue");
                var ename = $("#FName").html();
                var detailid = $("#FDetailID").val();
                var ioport = $("#FPort").val();
                var iotype = $("#FType").val();
                var digitalnormal = $("#FValue")
                    .find("option:selected").val();
                var goodname = $("#FOne").val();
                var badname = $("#FZero").val();
                var reserve = $("#ShReserve").val();
                if (ioport == "") {
                    layer.msg("端口不能为空！");
                    return;
                }
                if (digitalnormal == "") {
                    layer.msg("报警值不能为空！");
                    return;
                }
                if (goodname == "") {
                    layer.msg("1含义不能为空！");
                    return;
                }
                if (badname == "") {
                    layer.msg("0含义不能为空！");
                    return;
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "eqclassname" : EqClassName,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "detailid" : detailid,
                    "ioport" : ioport,
                    "digitalnormal" : digitalnormal,
                    "goodname" : goodname,
                    "badname" : badname,
                    "detialname" : ename,
                    "eqsystemid" : systemID,
                    "reserve":reserve,
                    "actionType" : 1
                };
            }
            if (otherPortValue == 1) {// 单纯模拟量
                console.log("otherPortValue")
                var detailid = $("#OdetailID").val();
                var Port = $("#Port").val();
                var KValue = $("#KValue").val();
                var BValue = $("#BValue").val();
                var TopValue = $("#TopValue").val();
                var LowValue = $("#LowValue").val();
                var MNUnit = $("#MNUnit").val();
                var reserve = $("#reserve").val();
                var maxValue = $("#MaxValue").val();
                var minValue =  $("#MinValue").val();
                var detialname = $("#OtherPortName").html();
//						console.log("TopValue:"+TopValue);
//						console.log("LowValue:"+LowValue);
//						console.log("maxValue:"+maxValue);
//						console.log("minValue:"+minValue);
                if (Port == "") {
                    layer.msg("端口号不能为空！");
                    return;
                }
                if (KValue == "") {
                    layer.msg("K值不能为空！");
                    return;
                }
                if (BValue == "") {
                    layer.msg("B值不能为空！");
                    return;
                }
                if (TopValue == "") {
                    layer.msg("上限不能为空！");
                    return;
                }
                if (LowValue == "") {
                    layer.msg("下限不能为空！");
                    return;
                }
                if(parseFloat(TopValue)<=parseFloat(LowValue)){
                    layer.msg("上限必须大于下限！");
                    return;
                }
                if (maxValue == "") {
                    layer.msg("最大值不能为空！");
                    return;
                }
                if (minValue == "") {
                    layer.msg("最小值不能为空！");
                    return;
                }
                if(parseFloat(maxValue)<=parseFloat(minValue)){
                    layer.msg("最大值必须大于最小值！");
                    return;
                }
                if (MNUnit == "") {
                    layer.msg("模拟量单位不能为空！");
                    return;
                }
                if(parseFloat(maxValue)<=parseFloat(TopValue)){
                    layer.msg("最大值必须大于上限值！");
                    return;
                }
                if(parseFloat(minValue)>=parseFloat(LowValue)){
                    layer.msg("最小值必须小于下限值！");
                    return;
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "detailid" : detailid,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "eqclassname" : EqClassName,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "ioport" : Port,
                    "analogk" : KValue,
                    "analogb" : BValue,
                    "analogup" : TopValue,
                    "analogdown" : LowValue,
                    "eqsystemid" : systemID,
                    "analogunit" : MNUnit,
                    "reserve" : reserve,
                    "eqsystemid" : systemID,
                    "maxValue":maxValue,
                    "minValue":minValue,
                    "detialname" : detialname,
                    "actionType" : 2
                };
            }
            if (threePortValue == 1) {// 电流电压
                console.log("threePortValue")
                var list = [];
                for (var i = 0; i < 3; i++) {
                    var detailid = $("#TdetailID" + i).val();
                    var Port = $("#TPort" + i).val();
                    var KValue = $("#KValue" + i).val();
                    var BValue = $("#BValue" + i).val();
                    var TopValue = $("#TopValue" + i).val();
                    var LowValue = $("#LowValue" + i).val();
                    var MNUnit = $("#MNUnit" + i).val();
                    var maxValue = $("#MaxValue"+i).val();
                    var minValue =  $("#MinValue"+i).val();
                    var reserve = $("#reserve" + i).val();
                    var detialname = $("#portName" + i).html();
                    list.push({
                        detailid : detailid,
                        ioport : Port,
                        analogk : KValue,
                        analogb : BValue,
                        analogup : TopValue,
                        analogdown : LowValue,
                        analogunit : MNUnit,
                        maxValue:maxValue,
                        minValue:minValue,
                        reserve : reserve,
                        detialname : detialname
                    });
                    if (Port == "") {
                        layer.msg("端口号不能为空！");
                        return;
                    }
                    if (KValue == "") {
                        layer.msg("K值不能为空！");
                        return;
                    }
                    if (BValue == "") {
                        layer.msg("B值不能为空！");
                        return;
                    }
                    if (TopValue == "") {
                        layer.msg("上限不能为空！");
                        return;
                    }
                    if (LowValue == "") {
                        layer.msg("下限不能为空！");
                        return;
                    }
                    if(parseFloat(TopValue)<=parseFloat(LowValue)){
                        layer.msg("上限必须大于下限！");
                        return;
                    }
                    if (maxValue == "") {
                        layer.msg("最大值不能为空！");
                        return;
                    }
                    if (minValue == "") {
                        layer.msg("最小值不能为空！");
                        return;
                    }
                    if(parseFloat(maxValue)<=parseFloat(minValue)){
                        layer.msg("最大值必须大于最小值！");
                        return;
                    }
                    if (MNUnit == "") {
                        layer.msg("模拟量单位不能为空！");
                        return;
                    }
                    if(parseFloat(maxValue)<=parseFloat(TopValue)){
                        layer.msg("最大值必须大于上限值！");
                        return;
                    }
                    if(parseFloat(minValue)>=parseFloat(LowValue)){
                        layer.msg("最小值必须小于下限值！");
                        return;
                    }
                    /*
                     * if(reserve == ""){ layer.msg("模拟量采集类型不能为空！");
                     * return; }
                     */
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "eqclassname" : EqClassName,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 4,
                    "eqsystemid" : systemID,
                    "portLists" : JSON.stringify(list)
                };
            }
            if (ElevenPortValue == 1) {// 电气火灾11个端口
                console.log("ElevenPortValue");
                var list = [];
                for (var i = 0; i < 11; i++) {
                    var detailid = $("#firedetailID" + i).val();
                    var Port = $("#firePort" + i).val();
                    var KValue = $("#fireKValue" + i).val();
                    var BValue = $("#fireBValue" + i).val();
                    var TopValue = $("#fireTopValue" + i).val();
                    var LowValue = $("#fireLowValue" + i).val();
                    var MNUnit = $("#fireMNUnit" + i).val();
                    var maxValue = $("#fireMaxValue"+i).val();
                    var minValue =  $("#fireMinValue"+i).val();
                    var reserve = $("#fireReserve" + i).val();
                    var detialname = $("#fireName" + i).html();
                    if (Port != "" && KValue != "" && BValue != ""
                        && TopValue != "" && LowValue != ""
                        && MNUnit != "" &&maxValue!=""&&minValue!="") {
                        if(parseFloat(TopValue)<=parseFloat(LowValue)){
                            layer.msg("上限必须大于下限！");
                            return;
                        }
                        if(parseFloat(maxValue)<=parseFloat(minValue)){
                            layer.msg("最大值必须大于最小值！");
                            return;
                        }
                        if(parseFloat(maxValue)<=parseFloat(TopValue)){
                            layer.msg("最大值必须大于上限值！");
                            return;
                        }
                        if(parseFloat(minValue)>=parseFloat(LowValue)){
                            layer.msg("最小值必须小于下限值！");
                            return;
                        }

                        list.push({
                            detailid : detailid,
                            ioport : Port,
                            analogk : KValue,
                            analogb : BValue,
                            analogup : TopValue,
                            analogdown : LowValue,
                            analogunit : MNUnit,
                            maxValue:maxValue,
                            minValue:minValue,
                            reserve : reserve,
                            detialname : detialname
                        });
                    } else if (Port == "" && KValue == ""
                        && BValue == "" && TopValue == ""
                        && LowValue == "" && MNUnit == ""&&maxValue==""&&minValue=="") {
                        continue;
                    } else {
                        layer.msg("请将一个端口填写完整！");
                        return;
                    }
                    /*
                     * if(Port == ""){ layer.msg("端口号不能为空！"); return; }
                     * if(KValue == ""){ layer.msg("K值不能为空！"); return; }
                     * if(BValue == ""){ layer.msg("B值不能为空！"); return; }
                     * if(TopValue == ""){ layer.msg("上限不能为空！"); return; }
                     * if(LowValue == ""){ layer.msg("下限不能为空！"); return; }
                     * if(MNUnit == ""){ layer.msg("模拟量单位不能为空！");
                     * return; }
                     */
                    /*
                     * if(reserve == ""){ layer.msg("模拟量采集类型不能为空！");
                     * return; }
                     */
                }
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "deviceindex" : 3,
                    "eqclassid" : EqClassID,
                    "eqclassname" : EqClassName,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 4,
                    "eqsystemid" : systemID,
                    "portLists" : JSON.stringify(list)
                };
            }
            if (customPortValue == 1) {// 其他端口
                console.log("customPortValue");
                var allTableData = $('#portTable').bootstrapTable('getData');
                allTableData = allTableData.filter(function(item) {
                    return item.id == undefined;
                });
                data = {
                    "id" : Id,
                    "buildid" : buildId,
                    "unitid" : UnitID,
                    "buildareaid" : buildAreaId,
                    "eqclassid" : EqClassID,
                    "eqclassname" : EqClassName,
                    "netdeviceid" : NetDeviceID,
                    "eqsystemcode" : systemID,
                    "installposition" : Position,
                    "eqname" : eqname,
                    "actionType" : 5,
                    "eqsystemid" : systemID,
                    "portLists" : JSON.stringify(allTableData)
                };
            }
            var isAsync = true, lid = 0;
            if (isAsync)
                lid = layer.msg('正在加载...', {
                    icon : 16,
                    shade : [ 0.1, '#fff' ],
                    time : 90000
                })
            if($(".btnAddSZPortSure").css('display') == 'none'&&$(".btnAddMNPortSure").css('display') == 'none'&&$(".btnUpdateMNPortSure").css('display') == 'none'&&$(".btnUpdateSZPortSure").css('display') == 'none'){
                $.ajax({
                    type : "post",
                    async : true,
                    beforeSend: function(request){
                        request.setRequestHeader("Authorization","Bearer "+login.token);
                    },
                    data : data,
                    url : url,
                    dataType : "json",
                    success : function(d) {
                        if (d.success) {
                            layer.msg("保存成功！");
                            otherPortValue = 0;
                            SetPortValue = 0;
                            threePortValue = 0;
                            faultPortValue = 0;
                            ElevenPortValue = 0;
                            fourPortValue = 0;
                            customPortValue = 0;
                            customPortList = [];
                            $("#newAdd").modal("hide");
                            emptyFoutPortMsg();
                            emptyPortMsg();
                            emptySearch();
                            $('#equipmentTable').bootstrapTable("refresh");
                        } else {
                            layer.msg(d.msg);
                        }
                    },
                    error : function(e) {

                    },
                    complete : function() {
                        if (isAsync) {
                            layer.close(lid);
                        }
                    }
                });
            }else{
                layer.msg("请先更新或者保存当前接口！");
                return;
            }

        });
// 清空所属单位，建筑物，区域等内容填充
function emptySearch() {
    $("#UnitID").val("");
    $("#BuildId").val("");
    $("#BuildAreaId").val("");
    $("#EqClassID").val("");
    $("#NetDeviceID").val("");
    $("#Position").val("");
    $("#EqName").val("");
}
// 清空设备类型列表
function emptyClassList() {
    $("#EqClassID").empty();
    $("#EqClassID").html('<option value="">--请选择--</option>');
    $("#EqClassIDView").empty();
    $("#EqClassIDView").html('<option value="">--请选择--</option>');
}
// 清空接口数值，此处还需更改，接口数量问题
function emptyPortMsg() {
    for (var i = 0; i < 3; i++) {
        $(".smokeControlPort").eq(i).find(".PortIdDetailId").val("");
        $(".smokeControlPort").eq(i).find(".PortIdValue").val("");
        $(".smokeControlPort").eq(i).find(".PortIdType").val("");
        $(".smokeControlPort").eq(i).find(".PortIdNormalNum").val("");
        $(".smokeControlPort").eq(i).find(".PortIdNormalName").val("");
        $(".smokeControlPort").eq(i).find(".PortIdAbnormalName").val("");
        $(".smokeControlPort").eq(i).find(".PortIdSignal").val("");

        $("#BDetailID" + i).val("");
        $("#Port" + i).val("");
        $("#Type" + i).val("");
        $("#Value" + i).val("");
        $("#One" + i).val("");
        $("#smokeControlPort" + i).val("");

        $("#TPort" + i).val("");
        $("#KValue" + i).val("");
        $("#BValue" + i).val("");
        $("#TopValue" + i).val("");
        $("#LowValue" + i).val("");
        $("#MaxValue"+i).val("");
        $("#MinValue"+i).val("");
        $("#MNUnit" + i).val("");
        $("#TdetailID" + i).val("");
        $("#reserve" + i).val("");
    }
}
// 清空联网设备列表下拉
function emptyDeviceList() {
    $("#NetDeviceID").empty();
    $("#NetDeviceID").html('<option value="">--请选择--</option>');
    $("#NetDeviceIDView").empty();
    $("#NetDeviceIDView").html('<option value="">--请选择--</option>');
}
// 清空建筑物下拉列表
function emptyBuildList() {
    $("#BuildId").empty();
    $("#BuildId").html('<option value="">--请选择--</option>');
    $("#BuildIdView").empty();
    $("#BuildIdView").html('<option value="">--请选择--</option>');
}
// 清空分区下拉框
function emptyBuildAreaList() {
    $("#BuildAreaId").empty();
    $("#BuildAreaId").html('<option value="">--请选择--</option>');
    $("#BuildAreaIdView").empty();
    $("#BuildAreaIdView").html('<option value="">--请选择--</option>');
}
// 清空四个端口号的数值，有点问题，待解决
function emptyFoutPortMsg() {
    for (var i = 0; i < 4; i++) {
        // $("#FBDetailID" + i).val("");
        // $("#FPort" + i).val("");
        // $("#FType" + i).val("");
        // $("#FValue" + i).val("");
        // $("#FOne" + i).val("");
        // $("#FZero" + i).val("");
        // $("#FShReserve" + i).val("");
        $(".pumpPort").eq(i).find(".PortIdDetailId").val("");
        $(".pumpPort").eq(i).find(".PortIdValue").val("");
        $(".pumpPort").eq(i).find(".PortIdType").val("");
        $(".pumpPort").eq(i).find(".PortIdNormalNum").val("");
        $(".pumpPort").eq(i).find(".PortIdNormalName").val("");
        $(".pumpPort").eq(i).find(".PortIdAbnormalName").val("");
        $(".pumpPort").eq(i).find(".PortIdSignal").val("");
    }
}
// 清空电气火灾11个接口的数值
function emptyFirePortMsg() {
    for (var i = 0; i < 11; i++) {
        $("#fireName" + i).val("");
        $("#firePort" + i).val("");
        $("#fireKValue" + i).val("");
        $("#fireBValue" + i).val("");
        $("#fireTopValue" + i).val("");
        $("#fireLowValue" + i).val("");
        $("#fireMaxValue"+i).val("");
        $("#fireMinValue"+i).val("");
        $("#fireMNUnit" + i).val("");
        $("#firedetailID" + i).val("");
        $("#fireReserve" + i).val("");
    }
}
// 清空其他端口数值
function emptyOtherPortMsg() {
    $("#Port").val("");
    $("#KValue").val("");
    $("#BValue").val("");
    $("#TopValue").val("");
    $("#LowValue").val("");
    $("#MNUnit").val("");
    $("#OdetailID").val("");
    $("#reserve").val("");
    $("#MaxValue").val("");
    $("#MinValue").val("");
    $("#FDetailID").val("");
    $("#FPort").val("");
    $("#FType").val("");
    $("#FValue").val("");
    $("#FOne").val("");
    $("#FZero").val("");
}

// 填充端口模态框的单位列表
function showUnitListPort() {
    var a = '<option value="%unitID%" >%unitName%</option>';
    var option;
    var wrap = '<option value="">--搜索--</option>';
    for (var i = 0; i < unitList.length; i++) {
        if (unitID && unitID == unitList[i].id) {
            option = '<option selected value="' + unitList[i].id + '" >' + unitList[i].unitname + '</option>';
        } else {
            option = a.replace("%unitID%", unitList[i].id);
            option = option.replace("%unitName%", unitList[i].unitname);
        }
        wrap += option;
    }
    $("#UnitIDPort").html(wrap);
    $("#UnitIDPort").comboSelect();
}

// 切换单位
$("#UnitIDPort").bind("change", function() {
    emptyBuildList();
    emptyBuildAreaList();
    unitID = $("#UnitIDPort").val();
    getBuildList();
    getDeviceList(deviceIndex);
});

// 获取建筑物列表
function getBuildList() {
    var UnitID = $("#UnitIDPort").find("option:selected").val();
    var data = {
        UnitID : UnitID
    }
    $.ajax({
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : baseUrl + "/build/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildList = d.obj;
                if(UnitID != null && UnitID != ""){
                    showBuildList();
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 填充建筑物下拉框
function showBuildList() {
    var a = '<option value="%buildId%">%buildName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (buildList) {
        for (var i = 0; i < buildList.length; i++) {
            option = a.replace("%buildId%", buildList[i].id);
            option = option.replace("%buildName%", buildList[i].buildingName);
            wrap += option;
        }
        $("#BuildId").html(wrap);
        $("#BuildIdView").html(wrap);
    }
}

// 切换建筑物
$("#BuildId").bind("change", function() {
    emptyBuildAreaList();
    getBuildAreaList();
});

// 获取区域列表
function getBuildAreaList() {
    var buildId = $("#BuildId").find("option:selected").val();
    if(buildId == ""){
        buildId = $("#BuildIdView").find("option:selected").val();
    }
    var data = {
        buildID : buildId
    }
    $.ajax({
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : baseUrl + "/buildArea/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildAreaList = d.obj
                if(buildId != null && buildId != ""){
                    showBuildAreaList();
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}
// 填充分区下拉框
function showBuildAreaList() {
    var a = '<option value="%BuildAreaId%">%BuildAreaName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (buildAreaList) {
        for (var i = 0; i < buildAreaList.length; i++) {
            option = a.replace("%BuildAreaId%", buildAreaList[i].id);
            option = option.replace("%BuildAreaName%",
                buildAreaList[i].buildAreaName);
            wrap += option;
        }
        $("#BuildAreaId").html(wrap);
        $("#BuildAreaIdView").html(wrap);
    }
}

// 填充系统类型下拉框（新增页面）
function showSystemList() {
    var a = '<option value="%SystemId%">%SystemName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (SystemList) {
        for (var i = 0; i < SystemList.length; i++) {
            if (systemID && systemID == SystemList[i].id) {
                option = '<option selected value="' + SystemList[i].id + '" >' + SystemList[i].eqsystemname + '</option>';
            } else{
                option = a.replace("%SystemId%", SystemList[i].id);
                option = option.replace("%SystemName%", SystemList[i].eqsystemname);
            }
            wrap += option;
        }
        $("#EqSystemCode").html(wrap);
        $("#EqSystemCodeView").html(wrap);
    }
}

// 新增编辑系统类型改变
$("#EqSystemCode").bind("change", function() {
    getClassList($("#EqSystemCode").val());
    emptyDeviceList();
});

// 新增编辑设备分类
function getClassList(EqSystemCode,deviceindexText) {
    $.ajax({
        type : "get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : {
            "systemID" : EqSystemCode
        },
        async : true,
        url : baseUrl + "/eqClass/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                classList = d.obj;
                showClassList(deviceindexText);
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 填充设备类型下拉框（新增编辑页）
function showClassList(deviceindexText) {
    var a = '<option value="%classId%" code="%code%">%className%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (classList) {
        for (var i = 0; i < classList.length; i++) {
            // option = '<option value="'+classList[i].id+'" code="'+ classList[i].classcode +'">'+ classList[i].classname +'</option>'
            option = a.replace("%classId%", classList[i].id);
            option = option.replace("%code%", classList[i].classcode);
            option = option.replace("%className%", classList[i].classname);
            wrap += option;
        }
        $("#EqClassID").html(wrap);
    }

    //  选中对应的项
    if(deviceindexText == "主机"){
        deviceindexText = "报警" + deviceindexText;
    }else if(deviceindexText == "用户传输装置"){
        deviceindexText = "用户信息传输装置";
    }
    $('#EqClassID option:contains("' + deviceindexText + '")').each(function(){
        if ($(this).text() == deviceindexText) {
            $(this).attr('selected', true);
        }
    });
}

// 切换设备类型
$("#EqClassID").bind(
    "change",
    function() {

        var eqClass = $("#EqClassID").find("option:selected").attr("code");
        var eqName = $("#EqClassID").find("option:selected").text();
        if (eqClass == "27") {// 三个端口的防火分割
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#faultPort").hide();
            $("#threePort").hide();
            $("#fourPort").hide();
            $("#SetPort").show();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            SetPortValue = 1;
            deviceIndex = 3;
            // switch (eqClass) {
            // 	case "18":
            // 		$("#Name0").html("端口号(手自动)");
            // 		$("#Name1").html("端口号(启停)");
            // 		$("#Name2").html("端口号(电源)");
            // 		break;
            // 	case "27":
            // 		$("#Name0").html("端口号(电源)");
            // 		$("#Name1").html("端口号(半降)");
            // 		$("#Name2").html("端口号(全降)");
            // 		break;
            // 	default:
            // 		break;
            // }
        }else if(eqClass == "18") {// 三个端口的防排烟系统
            $("#smokePort").show();
            $("#otherPort").hide();
            $("#faultPort").hide();
            $("#threePort").hide();
            $("#fourPort").hide();
            $("#SetPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            smokePortValue = 1;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0
            SetPortValue = 0;
            deviceIndex = 3;
            // switch (eqClass) {
            // 	case "18":
            // 		$("#Name0").html("端口号(手自动)");
            // 		$("#Name1").html("端口号(启停)");
            // 		$("#Name2").html("端口号(电源)");
            // 		break;
            // 	case "27":
            // 		$("#Name0").html("端口号(电源)");
            // 		$("#Name1").html("端口号(半降)");
            // 		$("#Name2").html("端口号(全降)");
            // 		break;
            // 	default:
            // 		break;
            // }
        } else if(eqClass == "4" ){//泵控制柜
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#faultPort").hide();
            $("#fourPort").show();
            $("#threePort").hide();
            $("#SetPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            otherPortValue = 0;
            smokePortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue  = 1;
            SetPortValue = 0;
            deviceIndex = 3;
        }else if (eqClass == "19" || eqClass == "2" || eqClass == "3"
            || eqClass == "5" || eqClass == "6" || eqClass == "7"
            || eqClass == "8" || eqClass == "9" || eqClass == "10"
            || eqClass == "11" || eqClass == "12") {
            $("#smokePort").hide();
            $("#SetPort").hide();
            $("#threePort").hide();
            $("#fourPort").hide();
            $("#faultPort").hide();
            $("#threeUPort").hide();
            $("#otherPort").show();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            otherPortValue = 1;
            deviceIndex = 3;
            switch (eqClass) {
                case "2":
                    $("#OtherPortName").html("端口号(水箱)");
                    break;
                case "3":
                    $("#OtherPortName").html("端口号(水池)");
                    break;
                case "5":
                    $("#OtherPortName").html("端口号(温度)");
                    break;
                case "6":
                    $("#OtherPortName").html("端口号(湿度)");
                    break;
                case "7":
                    $("#OtherPortName").html("端口号(压力)");
                    break;
                case "8":
                    $("#OtherPortName").html("端口号(溢水值)");
                    break;
                case "10":
                    $("#OtherPortName").html("端口号(电压)");
                    break;
                case "11":
                    $("#OtherPortName").html("端口号(电弧)");
                    break;
                case "12":
                    $("#OtherPortName").html("端口号(电流)");
                    break;
                default:
                    $("#OtherPortName").html("端口号(压力)");
                    break;
            }
        } else if (eqClass == "15") {// 三相电流
            $("#smokePort").hide();
            $("#SetPort").hide();
            $("#otherPort").hide();
            $("#faultPort").hide();
            $("#threePort").show();
            $("#fourPort").hide();
            $("#portName0").html("端口号(IA)");
            $("#portName1").html("端口号(IB)");
            $("#portName2").html("端口号(IC)");
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            threePortValue = 1;
            deviceIndex = 3;
        } else if (eqClass == "14") {// 三相电压
            $("#smokePort").hide();
            $("#SetPort").hide();
            $("#otherPort").hide();
            $("#threePort").show();
            $("#portName0").html("端口号(UA)");
            $("#portName1").html("端口号(UB)");
            $("#portName2").html("端口号(UC)");
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#customPort").hide();
            $("#ElevenPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 1;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            deviceIndex = 3;
        } else if (eqClass == "13" || eqClass == "28") {// 故障
            $("#smokePort").hide();
            $("#SetPort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").show();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            faultPortValue = 1;
            deviceIndex = 3;
            if (eqClass == "13") {
                $("#FName").html("端口号(故障)");
            } else {
                $("#FName").html("端口号(状态)");
            }
        } else if (eqClass == "20") {// 称重（气体系统）
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").show();
            $("#ElevenPort").hide();
            $("#fourPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            $("#FName").html("端口号(称重)");
            faultPortValue = 1;
            deviceIndex = 3;
        } else if (eqClass == "--请选择--") {
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
            deviceIndex = "";
        } else if (eqName == "报警主机") {// 报警主机
            deviceIndex = 1;
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
        } else if (eqClass == "16") {// 用户信息传输装置
            deviceIndex = 2;
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
            fourPortValue = 0;
        } else if (eqClass == "17") {// 电气火灾
            deviceIndex = 3;
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").show();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 1;
            customPortValue = 0;
            fourPortValue = 0;
        }else if (eqClass == "100"|| eqClass == "101" || eqClass == "102" || eqClass == "103" || eqClass == "104") {//其他端口
            deviceIndex = 3;
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").show();
            $(".btAddMNPort").show();
            $(".btAddSZPort").show();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 1;
            fourPortValue = 0;
        } else if (eqClass == undefined) {
            $("#SetPort").hide();
            $("#smokePort").hide();
            $("#otherPort").hide();
            $("#threePort").hide();
            $("#faultPort").hide();
            $("#fourPort").hide();
            $("#ElevenPort").hide();
            $("#customPort").hide();
            SetPortValue = 0;
            smokePortValue = 0;
            otherPortValue = 0;
            threePortValue = 0;
            fourPortValue = 0;
            faultPortValue = 0;
            ElevenPortValue = 0;
            customPortValue = 0;
        }
        emptyDeviceList();
        if (unitID == "" || unitID == undefined) {
            return;
        }
        getDeviceList(deviceIndex);

    });

// 获取联网设备列表
function getDeviceList(deviceIndex,name) {
    var UnitID = unitID;
    var data = {
        deviceindex : deviceIndex,
        unitid : UnitID
    }
    $.ajax({
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : baseUrl + "/netDevice/getArrayList",
        dataType : "json",
        success : function(data) {
            if (data.success) {
                deviceList = data.obj;
                showDeviceList(deviceList,name);
            } else {
                layer.msg(data.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 填充联网设备下拉框
function showDeviceList(deviceList,name) {
    var a = '<option value="%netDeviceId%">%deviceName%</option>';
    var option;
    var wrap = '<option value="">--搜索--</option>'

    for (var i = 0; i < deviceList.length; i++) {
        if (netDeviceId && netDeviceId == deviceList[i].id) {
            option = '<option selected value="' + deviceList[i].id + '" >'
                + deviceList[i].name + '</option>';
        } else {
            option = a.replace("%netDeviceId%", deviceList[i].id);
            option = option.replace("%deviceName%", deviceList[i].name);
        }
        wrap += option;
    }
    $("#NetDeviceID").html(wrap);

    $('#NetDeviceID option:contains("' + name + '")').each(function(){
        if ($(this).text() == name) {
            $(this).attr('selected', true);
        }
    });

}

//新增模拟量端口信息
$(".btAddMNPort").click(function() {
    emptyMNPortMsg();
    $("#MNPort").show();
    $("#SZPort").hide();
    $(".btnAddMNPortSure").show();
    $(".btnUpdateMNPortSure").hide();
    $(".btnAddSZPortSure").hide();
    $(".btnUpdateSZPortSure").hide();
});

//更新模拟量端口信息
$(".btnUpdateMNPortSure").click(function() {
    var detialname = $("#MNIOPortName").val();
    var detailId = $("#MNIOPortDetailID").val();
    var KValue = $("#MNIOPortKValue").val();
    var BValue = $("#MNIOPortBValue").val();
    var TopValue = $("#MNIOPortTopValue").val();
    var LowValue = $("#MNIOPortLowValue").val();
    var MNUnit = $("#MNIOPortUnit").val();
    var maxValue = $("#MNIOPortMaxValue").val();
    var minValue =  $("#MNIOPortMinValue").val();
    var ioport = $("#MNIOPortValue").val();
    var iotype = 1;
    var digitalnormal = '';
    var goodname = '';
    var badname = '';
    var reserve = $("#MNIOPortReserve").val();
    if (detialname == "") {
        layer.msg("端口名不能为空！");
        return;
    }
    if (ioport == "") {
        layer.msg("端口号不能为空！");
        return;
    }
    if (KValue == "") {
        layer.msg("K值不能为空！");
        return;
    }
    if (BValue == "") {
        layer.msg("B值不能为空！");
        return;
    }
    if (TopValue == "") {
        layer.msg("上限不能为空！");
        return;
    }
    if (LowValue == "") {
        layer.msg("下限不能为空！");
        return;
    }
    if(parseFloat(TopValue)<=parseFloat(LowValue)){
        layer.msg("上限必须大于下限！");
        return;
    }
    if (maxValue == "") {
        layer.msg("最大值不能为空！");
        return;
    }
    if (minValue == "") {
        layer.msg("最小值不能为空！");
        return;
    }
    if(parseFloat(maxValue)<=parseFloat(minValue)){
        layer.msg("最大值必须大于最小值！");
        return;
    }
    if (MNUnit == "") {
        layer.msg("模拟量单位不能为空！");
        return;
    }
    if(parseFloat(maxValue)<=parseFloat(TopValue)){
        layer.msg("最大值必须大于上限值！");
        return;
    }
    if(parseFloat(minValue)>=parseFloat(LowValue)){
        layer.msg("最小值必须小于下限值！");
        return;
    }
    var data={
        netdeviceid:netDeviceId,
        detailid:detailId,
        ioport : ioport,
        iotype :iotype,
        analogk : KValue,
        analogb : BValue,
        analogup : TopValue,
        analogdown : LowValue,
        analogunit : MNUnit,
        maxValue:maxValue,
        minValue:minValue,
        reserve : reserve,
        goodname:goodname,
        badname : badname,
        digitalnormal:digitalnormal,
        detialname : detialname
    }
    $.ajax({
        type:"post",
        url:baseUrl + "/eqDetail/editPort",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        dataType:"json",
        success:function(data){
            if(data.success){
                layer.msg("更新成功！")
                emptyMNPortMsg();
                $('#portTable').bootstrapTable("refresh");
                $("#MNPort").hide();
                $(".btnAddMNPortSure").hide();
                $(".btnUpdateMNPortSure").hide();
            }else{
                layer.msg(data.msg);
            }
        },
        error: function (e) {
            layer.msg("更新失败！")
        }
    });
});

//新增数字量端口信息
$(".btAddSZPort").click(function() {
    emptySZPortMsg();
    $("#SZPort").show();
    $("#MNPort").hide();
    $(".btnAddSZPortSure").show();
    $(".btnAddMNPortSure").hide();
    $(".btnUpdateMNPortSure").hide();
    $(".btnUpdateSZPortSure").hide();
});

//更新数字量端口信息
$(".btnUpdateSZPortSure").click(function() {
    var detailId = $("#SZIOPortDetailID").val();
    var detialname = $("#SZIOPortName").val();
    var KValue = '';
    var BValue = '';
    var TopValue = '';
    var LowValue = '';
    var MNUnit = '';
    var maxValue = '';
    var minValue =  '';
    var ioport = $("#SZIOPort").val();
    var iotype = 2;
    var digitalnormal = $("#SZIOPortNormalValue").val();
    var goodname = $("#SZIOPortGoodName").val();
    var badname = $("#SZIOPortBadName").val();
    var reserve = $("#SZIOPortReserve").val();
    if (detialname == "") {
        layer.msg("端口名不能为空！");
        return;
    }
    if (ioport == "") {
        layer.msg("端口号不能为空！");
        return;
    }
    if (digitalnormal == "") {
        layer.msg("报警值不能为空！");
        return;
    }
    if (goodname == "") {
        layer.msg("1含义不能为空！");
        return;
    }
    if (badname == "") {
        layer.msg("0含义不能为空！");
        return;
    }
    var data={
        netdeviceid:netDeviceId,
        detailid:detailId,
        ioport : ioport,
        iotype :iotype,
        analogk : KValue,
        analogb : BValue,
        analogup : TopValue,
        analogdown : LowValue,
        analogunit : MNUnit,
        maxValue:maxValue,
        minValue:minValue,
        reserve : reserve,
        goodname:goodname,
        badname : badname,
        digitalnormal:digitalnormal,
        detialname : detialname
    }
    $.ajax({
        type:"post",
        url:baseUrl + "/eqDetail/editPort",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        dataType:"json",
        success:function(data){
            if(data.success){
                layer.msg("更新成功！")
                emptyMNPortMsg();
                $('#portTable').bootstrapTable("refresh");
                $("#SZPort").hide();
                $(".btnAddSZPortSure").hide();
                $(".btnUpdateSZPortSure").hide();
            }else{
                layer.msg(data.msg);
            }
        },
        error: function (e) {
            layer.msg("更新失败！")
        }
    });
});

//保存模拟量端口信息
$(".btnAddMNPortSure").click(function() {
    var detialname = $("#MNIOPortName").val();
    var KValue = $("#MNIOPortKValue").val();
    var BValue = $("#MNIOPortBValue").val();
    var TopValue = $("#MNIOPortTopValue").val();
    var LowValue = $("#MNIOPortLowValue").val();
    var MNUnit = $("#MNIOPortUnit").val();
    var maxValue = $("#MNIOPortMaxValue").val();
    var minValue =  $("#MNIOPortMinValue").val();
    var ioport = $("#MNIOPortValue").val();
    var iotype = 1;
    var digitalnormal = '';
    var goodname = '';
    var badname = '';
    var reserve = $("#MNIOPortReserve").val();
    if (detialname == "") {
        layer.msg("端口名不能为空！");
        return;
    }
    if (ioport == "") {
        layer.msg("端口号不能为空！");
        return;
    }
    if (KValue == "") {
        layer.msg("K值不能为空！");
        return;
    }
    if (BValue == "") {
        layer.msg("B值不能为空！");
        return;
    }
    if (TopValue == "") {
        layer.msg("上限不能为空！");
        return;
    }
    if (LowValue == "") {
        layer.msg("下限不能为空！");
        return;
    }
    if(parseFloat(TopValue)<=parseFloat(LowValue)){
        layer.msg("上限必须大于下限！");
        return;
    }
    if (maxValue == "") {
        layer.msg("最大值不能为空！");
        return;
    }
    if (minValue == "") {
        layer.msg("最小值不能为空！");
        return;
    }
    if(parseFloat(maxValue)<=parseFloat(minValue)){
        layer.msg("最大值必须大于最小值！");
        return;
    }
    if (MNUnit == "") {
        layer.msg("模拟量单位不能为空！");
        return;
    }
    if(parseFloat(maxValue)<=parseFloat(TopValue)){
        layer.msg("最大值必须大于上限值！");
        return;
    }
    if(parseFloat(minValue)>=parseFloat(LowValue)){
        layer.msg("最小值必须小于下限值！");
        return;
    }
    data = {
        ioport : ioport,
        iotype :iotype,
        analogk : KValue,
        analogb : BValue,
        analogup : TopValue,
        analogdown : LowValue,
        analogunit : MNUnit,
        maxValue:maxValue,
        minValue:minValue,
        reserve : reserve,
        goodname:goodname,
        badname : badname,
        digitalnormal:digitalnormal,
        detialname : detialname
    }
//	customPortList.push(data);
    addRow(data);
    $(".btnAddMNPortSure").hide();
    $("#MNPort").hide();
    $(".btAddMNPort").hide();
    $(".btAddSZPort").hide();
    emptyMNPortMsg();
});

function addRow(data){
    var count = $('#portTable').bootstrapTable('getData').length;
    // newFlag == 1的数据为新规的数据
    var ioType;
    if(data.iotype == 1){
        ioType = "模拟量";
    }else{
        ioType = "数字量";
    }
    $('#portTable').bootstrapTable('insertRow',
        {index:count,
            row:{
                detialname : data.detialname,
                iotype :data.iotype,
                ioport : data.ioport,
                analogup : data.analogup,
                analogdown : data.analogdown,
                analogunit : data.analogunit,
                analogk : data.analogk,
                analogb : data.analogb,
                digitalnormal:data.digitalnormal,
                goodname:data.goodname,
                badname : data.badname,
                reserve : data.reserve,
                maxValue:data.maxValue,
                minValue:data.minValue
            }
        });
}

//清空模拟量端口信息
function emptyMNPortMsg(){
    $("#MNIOPortName").val('');
    $("#MNIOPortKValue").val('');
    $("#MNIOPortBValue").val('');
    $("#MNIOPortTopValue").val('');
    $("#MNIOPortLowValue").val('');
    $("#MNIOPortUnit").val('');
    $("#MNIOPortMaxValue").val('');
    $("#MNIOPortMinValue").val('');
    $("#MNIOPortValue").val('');
    $("#MNIOPortReserve").val('');
    $("#MNIOPortDetailID").val('');
}

//保存数字量量端口信息
$(".btnAddSZPortSure").click(function() {
    var detialname = $("#SZIOPortName").val();
    var KValue = '';
    var BValue = '';
    var TopValue = '';
    var LowValue = '';
    var MNUnit = '';
    var maxValue = '';
    var minValue =  '';
    var ioport = $("#SZIOPort").val();
    var iotype = 2;
    var digitalnormal = $("#SZIOPortNormalValue").val();
    var goodname = $("#SZIOPortGoodName").val();
    var badname = $("#SZIOPortBadName").val();
    var reserve = $("#SZIOPortReserve").val();
    if (detialname == "") {
        layer.msg("端口名不能为空！");
        return;
    }
    if (ioport == "") {
        layer.msg("端口号不能为空！");
        return;
    }
    if (digitalnormal == "") {
        layer.msg("报警值不能为空！");
        return;
    }
    if (goodname == "") {
        layer.msg("1含义不能为空！");
        return;
    }
    if (badname == "") {
        layer.msg("0含义不能为空！");
        return;
    }
    data = {
        ioport : ioport,
        iotype :iotype,
        analogk : KValue,
        analogb : BValue,
        analogup : TopValue,
        analogdown : LowValue,
        analogunit : MNUnit,
        maxValue:maxValue,
        minValue:minValue,
        reserve : reserve,
        goodname:goodname,
        badname : badname,
        digitalnormal:digitalnormal,
        detialname : detialname
    }
//	customPortList.push(data);
    addRow(data);
    $("#SZPort").hide();
    $(".btnAddSZPortSure").hide();
    $(".btAddMNPort").hide();
    $(".btAddSZPort").hide();
    emptySZPortMsg();
});

//清空数字量端口信息
function emptySZPortMsg(){
    $("#SZIOPort").val('');
    $("#SZIOPortName").val('');
    $("#SZIOPortNormalValue").val('');
    $("#SZIOPortGoodName").val('');
    $("#SZIOPortBadName").val('');
    $("#SZIOPortReserve").val('');
    $("#SZIOPortDetailID").val('');
}

//关闭数字量端口
$(".btnSZPortClose").click(function() {
    $("#SZPort").hide();
    emptySZPortMsg();
    $(".btnAddMNPortSure").hide();
    $(".btnAddSZPortSure").hide();
    $(".btnUpdateMNPortSure").hide();
    $(".btnUpdateSZPortSure").hide();
})

//关闭模拟量端口
$(".btnMNPortClose").click(function() {
    $("#MNPort").hide();
    emptyMNPortMsg();
    $(".btnAddMNPortSure").hide();
    $(".btnAddSZPortSure").hide();
    $(".btnUpdateMNPortSure").hide();
    $(".btnUpdateSZPortSure").hide();
})
function portSearch(Id) {
    $('#portTable')
        .bootstrapTable(
            {
                url : baseUrl + '/eqDetail/getPortListPage', // 请求后台的URL（*）
                method : 'get', // 请求方式（*）
                contentType : 'application/x-www-form-urlencoded',
                toolbar : '#toolbar', // 工具按钮用哪个容器
                striped : true, // 是否显示行间隔色
                cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, // 是否显示分页（*）
                sortable : false, // 是否启用排序
                sortOrder : "asc", // 排序方式
                queryParamsType : "undefined", // 排序方式
                ajaxOptions:{
                    headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
                },
                queryParams : function queryParams(params) { // 设置查询参数
                    var param = {
                        pageNumber : this.pageNumber,
                        pageSize : this.pageSize,
                        "id" : Id
                    };
                    return param;
                },
                dataField : 'list',
                sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, // 初始化加载第一页，默认第一页
                pageSize : 10, // 每页的记录行数（*）
                pageList : [ 10, 25, 50, 100, 200, 500 ], // 可供选择的每页的行数（*）
                search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                searchTimeOut : 1000,
                // : true,
                showColumns : true, // 是否显示所有的列
                showRefresh : true, // 是否显示刷新按钮
                minimumCountColumns : 2, // 最少允许的列数
                clickToSelect : true, // 是否启用点击选中行
                singleSelect : false,
                // height: dataHeight,
                // //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", // 每一行的唯一标识，一般为主键列
                // showToggle: true, //是否显示详细视图和列表视图的切换按钮
                cardView : false, // 是否显示详细视图
                detailView : false, // 是否显示父子表
                columns : [
                    {
                        field : '',
                        title : '操作',
                        formatter : function(value, row, index) {
                            var str = "";
                            str += '<button type="button" class="btn btn-new btn-xs cBtn-main editPort"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
                            str += '<button type="button" class="btn btn-danger btn-xs cBtn-main delPort"><i class="fa fa-trash"></i>&nbsp;删除</button>'
                            return str;
                        },
                        events : {
                            'click .editPort' : function(e, value, row,
                                                         index) {
                                editPort(row);
                            },
                            'click .delPort' : function(e, value, row,
                                                        index) {
                                deletePort(row,index);
                            }
                        }
                    },
                    {
                        field : 'detialname',
                        title : '信号名称'
                    },
                    {
                        field : 'iotype',
                        title : '端口类别',
                        sortable: true,
                        formatter: function (iotype) {
                            var value = iotype;
                            if (value == '1') {
                                return '模拟量';
                            } else if(value=='2') {
                                return '数字量';
                            }
                        }
                    },
                    {
                        field : 'ioport',
                        title : '端口号'
                    },
                    {
                        field : 'analogup',
                        title : '最大值'
                    },
                    {
                        field : 'analogdown',
                        title : '最小值'
                    },
                    {
                        field : 'analogunit',
                        title : '单位',
                    },
                    {
                        field : 'analogk',
                        title : 'K值'
                    },
                    {
                        field : 'analogb',
                        title : 'B值'
                    },
                    {
                        field : 'digitalnormal',
                        title : '数字量正常值'
                    },
                    {
                        field : 'goodname',
                        title : '正常名称'
                    },
                    {
                        field : 'badname',
                        title : '异常名称'
                    },
                    {
                        field : 'reserve',
                        title : '采集类型'
                    },
                    {
                        field : 'maxValue',
                        title : '预警最大值'
                    },
                    {
                        field : 'minValue',
                        title : '预警最小值'
                    }
                ]
            });
}

//其他端口编辑端口信息
function editPort(_this){
    $(".btnAddMNPortSure").hide();
    $(".btnAddSZPortSure").hide();
    if(_this.iotype == 1){
        $("#MNPort").show();
        $("#SZPort").hide();

        $("#MNIOPortDetailID").val(_this.id);
        $("#MNIOPortName").val(_this.detialname);
        $("#MNIOPortKValue").val(_this.analogk);
        $("#MNIOPortBValue").val(_this.analogb);
        $("#MNIOPortTopValue").val(_this.analogup);
        $("#MNIOPortLowValue").val(_this.analogdown);
        $("#MNIOPortUnit").val(_this.analogunit);
        $("#MNIOPortMaxValue").val(_this.maxValue);
        $("#MNIOPortMinValue").val(_this.minValue);
        $("#MNIOPortValue").val(_this.ioport);
        $("#MNIOPortReserve").val(_this.reserve);
        if(_this.id==undefined){
            $(".btnAddMNPortSure").show();
            $(".btnUpdateMNPortSure").hide();
            deleteRow(_this);
        }else{
            $(".btnAddMNPortSure").hide();
            $(".btnUpdateMNPortSure").show();
        }
    }else{
        $("#SZPort").show();
        $("#MNPort").hide();
        $(".btnAddSZPortSure").hide();
        $(".btnUpdateSZPortSure").show();
        $("#SZIOPort").val(_this.ioport);
        $("#SZIOPortDetailID").val(_this.id);
        $("#SZIOPortName").val(_this.detialname);
        $("#SZIOPortNormalValue").val(_this.digitalnormal);
        $("#SZIOPortGoodName").val(_this.goodname);
        $("#SZIOPortBadName").val(_this.badname);
        $("#SZIOPortReserve").val(_this.reserve);
        if(_this.id==undefined){
            $(".btnAddSZPortSure").show();
            $(".btnUpdateSZPortSure").hide();
            deleteRow(_this);
        }else{
            $(".btnAddSZPortSure").hide();
            $(".btnUpdateSZPortSure").show();

        }
    }
}

//删除端口信息
function deletePort(_this,index){
    if(_this.id==undefined){
        deleteRow(_this);
    }else{
        $.ajax({
            type : "post",
            async : true,
            beforeSend: function(request){
                request.setRequestHeader("Authorization","Bearer "+login.token);
            },
            data : {id:_this.id},
            url : baseUrl + '/eqDetail/deletePort',
            dataType : "json",
            success : function(d) {
                if (d.success) {
                    layer.msg("删除成功！");
                    $("#addPort").modal("hide");
                    $('#portTable').bootstrapTable("refresh");
                    $(".btAddMNPort").show();
                    $(".btAddSZPort").show();
                    emptyPortMsg();
                } else {
                    layer.msg(d.msg);
                }
            },
            error : function(e) {
            }
        });
    }
}
function deleteRow(row){
    $(".btAddMNPort").show();
    $(".btAddSZPort").show();
    var newFlag = row.newFlag;
    if(newFlag == '1'){
        // 新规的数据直接删除
        row.deleteFlag = "true";
        $('#portTable').bootstrapTable('remove',{field:"deleteFlag", values:["true"]});
        // 数据删除后合计值区域自动重新计算
        autoCalculate();
    }else{
        row.deleteFlag = "true";
        $('#portTable').bootstrapTable('remove',{field:"deleteFlag", values:["true"]});
        // 删除flag设置为删除
        row['deletionFlag'] = '1';
        deletedData.push(row);
        // 数据删除后合计值区域自动重新计算
        autoCalculate();
    }
}









//  添加接口所需要的操作
function emptyBuildAreaListAllPort(i) {
    $(".allPortArea").eq(i).empty();
    $(".allPortArea").eq(i).html('<option value="">--请选择--</option>');
}
function emptyClassListAllPort(i) {
    $(".allPortEqClass").eq(i).empty();
    $(".allPortEqClass").eq(i).html('<option value="">--请选择--</option>');
}
function emptySignalTypeAllPort(i) {
    $(".allPortSignalType").eq(i).empty();
    $(".allPortSignalType").eq(i).html('<option value="">--请选择--</option>');
}
function emptyPortIdAllPort(i) {
    $(".allPortId").eq(i).empty();
    $(".allPortId").eq(i).html('<option value="">--请选择--</option>');
}
function emptyPortIdType1AllPort(i){
    $(".allPortfireK").eq(i).val("");
    $(".allPortfireB").eq(i).val("");
    $(".allPortfireTop").eq(i).val("");
    $(".allPortfireLow").eq(i).val("");
    $(".allPortMNUint").eq(i).val("");
}
function emptyPortIdType2AllPort(i){
    $(".allPortNormalNum").eq(i).val("");
    $(".allPortNormalName").eq(i).val("");
    $(".allPortAbnormalName").eq(i).val("");
}


// 点击添加接口按钮后执行
function add(_this) {
    $("#addAllPort").modal("show");
    $("#portTable233").empty();
    $("#addAllPort").find(".modal-title").html("联网设备名称："+ _this.name +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设备编号："+ _this.ownercode +"");
    $(".allPortNetDeviceId").eq(0).val(_this.id);
    unitID = _this.unitid;
    systemID = _this.id;
    showUnitListAllPort();
    $.ajax({
        url: baseUrl + "/netDevice/getNetEq",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            netDeviceId: systemID
        },
        dataType: "json",
        type: "post",
        success: function (data) {
            if(data.success){
                var dataList = data.obj;
                if(data.obj.length == 0){
                    changeurl = baseUrl + '/equipmentFac/addAll';
                }else{
                    changeurl = baseUrl + '/equipmentFac/updateAll';
                }
                for(var i=0;i<dataList.length;i++){
                    if(dataList[i].classcode == "2" || dataList[i].classcode == "3" || dataList[i].classcode == "5" || dataList[i].classcode == "6" || dataList[i].classcode == "7" || dataList[i].classcode == "8"
                        || dataList[i].classcode == "17" || dataList[i].classcode == "19"){
                        $("#portTable233").append(NewPort);

                        $(".allPortBuild").eq(i).append('<option value="'+ dataList[i].buildid +'">'+ dataList[i].buildingname +'</option>');
                        $(".allPortArea").eq(i).append('<option value="'+ dataList[i].buildareaid +'">'+ dataList[i].buildAreaName +'</option>');
                        $(".allPortPosition").eq(i).val(dataList[i].installposition);
                        $(".allPortEqName").eq(i).val(dataList[i].eqname);
                        $(".allPortEqName").eq(i).parents("tr").attr("detialid",dataList[i].detialId);
                        $(".allPortEqName").eq(i).parents("tr").attr("eqid",dataList[i].eqid);
                        $(".allPortEqName").eq(i).parents("tr").attr("code",dataList[i].classcode);
                        $(".allPortEqName").eq(i).parents("tr").attr("netdeviceid",dataList[i].netdeviceid);
                        $(".allPortEqSystem").eq(i).append('<option value="'+ dataList[i].eqsystemcode +'">'+ dataList[i].eqSystemName +'</option>');
                        $(".allPortEqClass").eq(i).append('<option value="'+ dataList[i].eqclassid +'" code="'+ dataList[i].classcode +'">'+ dataList[i].classname +'</option>');
                        $(".allPortSignalType").eq(i).append('<option value="'+ dataList[i].classcode +'">'+ dataList[i].detialname +'</option>');
                        $(".allPortId").eq(i).append('<option value="'+ dataList[i].ioport +'">'+ dataList[i].ioport +'</option>');

                        $(".allPortSignal").eq(i).val(dataList[i].reserve);

                        $(".allPortfireK").eq(i).val(dataList[i].analogk);
                        $(".allPortfireB").eq(i).val(dataList[i].analogb);
                        $(".allPortfireTop").eq(i).val(dataList[i].analogup);
                        $(".allPortfireLow").eq(i).val(dataList[i].analogdown);
                        $(".allPortMNUint").eq(i).val(dataList[i].analogunit);

                        $(".allPortNormalNum").eq(i).attr("disabled","disabled");
                        $(".allPortNormalName").eq(i).attr("disabled","disabled");
                        $(".allPortAbnormalName").eq(i).attr("disabled","disabled");


                    }else{
                        $("#portTable233").append(NewPort);

                        $(".allPortBuild").eq(i).append('<option value="'+ dataList[i].buildid +'">'+ dataList[i].buildingname +'</option>');
                        $(".allPortArea").eq(i).append('<option value="'+ dataList[i].buildareaid +'">'+ dataList[i].buildAreaName +'</option>');
                        $(".allPortPosition").eq(i).val(dataList[i].installposition);
                        $(".allPortEqName").eq(i).val(dataList[i].eqname);
                        $(".allPortEqName").eq(i).parents("tr").attr("eqid",dataList[i].eqid);
                        $(".allPortEqName").eq(i).parents("tr").attr("detialid",dataList[i].detialId);
                        $(".allPortEqName").eq(i).parents("tr").attr("code",dataList[i].classcode);
                        $(".allPortEqName").eq(i).parents("tr").attr("netdeviceid",dataList[i].netdeviceid);
                        $(".allPortEqSystem").eq(i).append('<option value="'+ dataList[i].eqsystemcode +'">'+ dataList[i].eqSystemName +'</option>');
                        $(".allPortEqClass").eq(i).append('<option value="'+ dataList[i].eqclassid +'" code="'+ dataList[i].classcode +'">'+ dataList[i].classname +'</option>');
                        $(".allPortSignalType").eq(i).append('<option value="'+ dataList[i].classcode +'">'+ dataList[i].detialname +'</option>');
                        $(".allPortId").eq(i).append('<option value="'+ dataList[i].ioport +'">'+ dataList[i].ioport +'</option>');

                        $(".allPortSignal").eq(i).val(dataList[i].reserve);

                        $(".allPortfireK").eq(i).attr("disabled","disabled");
                        $(".allPortfireB").eq(i).attr("disabled","disabled");
                        $(".allPortfireTop").eq(i).attr("disabled","disabled");
                        $(".allPortfireLow").eq(i).attr("disabled","disabled");
                        $(".allPortMNUint").eq(i).attr("disabled","disabled");

                        $(".allPortNormalNum").eq(i).val(dataList[i].digitalnormal);
                        $(".allPortNormalName").eq(i).val(dataList[i].goodname);
                        $(".allPortAbnormalName").eq(i).val(dataList[i].badname);
                    }
                }
            }
        }
    })
}
// 填充添加接口按钮的的单位列表
function showUnitListAllPort() {
    var a = '<option value="%unitID%" >%unitName%</option>';
    var option;
    var wrap = '<option value="">--搜索--</option>';
    for (var i = 0; i < unitList.length; i++) {
        if (unitID && unitID == unitList[i].id) {
            option = '<option selected value="' + unitList[i].id + '" >' + unitList[i].unitname + '</option>';
        } else {
            option = a.replace("%unitID%", unitList[i].id);
            option = option.replace("%unitName%", unitList[i].unitname);
        }
        wrap += option;
    }
    $(".allPortUnit").html(wrap);
}

// 添加接口按钮获取建筑物列表
function getBuildListAllPort(j,codeval) {
    var UnitID = $(".allPortUnit").eq(0).find("option:selected").val();
    var data = {
        UnitID : UnitID
    }

    $.ajax({
        type : "get",
        async : false,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : baseUrl + "/build/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildList = d.obj;
                if(UnitID != null && UnitID != ""){
                    showBuildListAllPort(j,codeval);
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 添加接口按钮填充建筑物下拉框
function showBuildListAllPort(j,codeval) {
    var a = '<option value="%buildId%">%buildName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    var valindex;
    if (buildList) {
        for (var i = 0; i < buildList.length; i++) {
            option = a.replace("%buildId%", buildList[i].id);
            option = option.replace("%buildName%", buildList[i].buildingName);
            wrap += option;
            if(buildList[i].buildingName == codeval){
                valindex = i;
            }
        }
        $(".allPortBuild").eq(j).html(wrap);
        $(".allPortBuild").get(0).selectedIndex=++valindex;
    }
}

// 添加接口按钮系统类型改变 jz
$("#portTable233").on("mousedown",".allPortEqClass",function () {
    var abcd = $(".allPortEqClass").index(this);
    // var codeval = $(".allPortEqClass").eq(abcd).find("option:selected").attr("code");
    var codeval = $(".allPortEqClass").eq(abcd).find("option:selected").text();
    getPortEqClassList(abcd,codeval);
});

//添加接口按钮获取设备类型列表 jz 2.1
function getPortEqClassList(j,codeval){
    var UnitID = $(".allPortEqSystem").eq(j).find("option:selected").val();
    var data = {
        systemID : UnitID
    }
    $.ajax({
        type : "get",
        async : false,
        data : data,
        url : baseUrl + "/eqClass/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                classList = d.obj;
                if(UnitID != null && UnitID != ""){
                    showPortEqClassList(j,codeval);
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 添加接口按钮填充系统类型下拉框 jz 2.2
function showPortEqClassList(j,codeval) {
    var a = '<option value="%classId%" code="%code%">%className%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    var valindex;
    if (classList) {
        for (var i = 0; i < classList.length; i++) {
            option = a.replace("%classId%", classList[i].id);
            option = option.replace("%code%", classList[i].classcode);
            option = option.replace("%className%", classList[i].classname);
            wrap += option;
            if(classList[i].classname == codeval){
                valindex = i;
            }
        }

        $(".allPortEqClass").eq(j).html(wrap);
        $(".allPortEqClass").get(0).selectedIndex=++valindex;
        if($(".allPortEqClass").eq(j).find("option[code='21']")){
            $(".allPortEqClass").eq(j).find("option[code='21']").remove();
        }
    }
}

// 添加接口按钮端口信号类型改变 jzh
$("#portTable233").on("mousedown",".allPortSignalType",function () {
    var abcd = $(".allPortSignalType").index(this);
    var codeval = $(".allPortSignalType").eq(abcd).find("option:selected").text();
    getPortSignalTypeList(abcd,codeval);

    // $(".allPortSignalType").eq(abcd).unbind("change").bind("change",function () {
    //     getPortSignalTypeAll($(".allPortSignalType").eq(abcd).val(),abcd);
    // });
});

// 获取端口信号类型 jzh  2.1
function getPortSignalTypeList(j,codeval) {
    var UnitID = $(".allPortEqClass").eq(j).find("option:selected").attr("code");
    var data = {
        classCode : UnitID
    }
    $.ajax({
        type : "get",
        async : false,
        data : data,
        url : baseUrl + "/eqClass/getportNameArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildList = d.obj;
                if(UnitID != null && UnitID != ""){
                    showPortSignalTypeList(j,codeval);
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}


// 添加接口按钮填充端口信号类型下拉框 jzh 2.2
function showPortSignalTypeList(j,codeval) {
    var a = '<option value="%id%">%portName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    var valindex;
    if (buildList) {
        for (var i = 0; i < buildList.length; i++) {
            option = a.replace("%id%", buildList[i].id);
            option = option.replace("%portName%", buildList[i].portName);
            wrap += option;
            if(buildList[i].portName == codeval){
                valindex = i;
            }
        }
        $(".allPortSignalType").eq(j).html(wrap);
        $(".allPortSignalType").get(0).selectedIndex=++valindex;
    }
}


// 添加接口切换建筑物
$("#portTable233").on("mousedown",".allPortBuild",function () {
    var abc = $(".allPortBuild").index(this);
    var codeval = $(".allPortBuild").eq(abc).find("option:selected").text();
    getBuildListAllPort(abc,codeval);
    $(".allPortBuild").eq(abc).unbind("change").bind("change",function () {
        getBuildAreaListAllPort(abc);
        $(".allPortBuild").eq(abc).find("option:selected").attr("selected",true).siblings().removeAttr("selected");
    });
});

// 添加接口按钮获取区域列表       获取区域列表也要重新做判断，用循环来做
function getBuildAreaListAllPort(j) {
    var buildId = $(".allPortBuild").eq(j).find("option:selected").val();
    // if(buildId == ""){
    //     buildId = $("#BuildIdView").find("option:selected").val();
    // }
    var data = {
        buildID : buildId
    }
    $.ajax({
        type : "get",
        async : false,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data : data,
        url : baseUrl + "/buildArea/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                buildAreaList = d.obj;
                if(buildId != null && buildId != ""){
                    showBuildAreaListAllPort(j);
                }
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 添加接口按钮填充分区下拉框      用j是为了与循环里的i分开
function showBuildAreaListAllPort(j) {
    var a = '<option value="%BuildAreaId%">%BuildAreaName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (buildAreaList) {
        for (var i = 0; i < buildAreaList.length; i++) {
            option = a.replace("%BuildAreaId%", buildAreaList[i].id);
            option = option.replace("%BuildAreaName%",
                buildAreaList[i].buildAreaName);
            wrap += option;
        }
        $(".allPortArea").eq(j).html(wrap);
        // $("#BuildAreaIdView").html(wrap);
    }
}

// 给添加接口中的分区添加选择状态
$("#portTable233").on("change",".allPortArea",function () {
    var abc = $(".allPortArea").index(this);
    $(".allPortArea").eq(abc).find("option:selected").attr("selected",true).siblings().removeAttr("selected");
});

// 添加接口按钮填充系统类型下拉框
function showSystemListAllPort(j,syval) {
    var a = '<option value="%SystemId%">%SystemName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    var valindex;
    if (SystemList) {
        for (var i = 0; i < SystemList.length; i++) {
            if (systemID && systemID == SystemList[i].id) {
                option = '<option selected value="' + SystemList[i].id + '" >' + SystemList[i].eqsystemname + '</option>';
            } else{
                option = a.replace("%SystemId%", SystemList[i].id);
                option = option.replace("%SystemName%", SystemList[i].eqsystemname);
            }
            if(SystemList[i].eqsystemname==syval){
                valindex = i;
            }
            wrap += option;
        }
        $(".allPortEqSystem").eq(j).html(wrap);
        $(".allPortEqSystem").get(0).selectedIndex=++valindex;
        $(".allPortEqSystem").eq(j).find("option[value='3']").remove();
        $(".allPortEqSystem").eq(j).find("option[value='6']").remove();
        $(".allPortEqSystem").eq(j).find("option[value='7']").remove();
        $(".allPortEqSystem").eq(j).find("option[value='8']").remove();
        $(".allPortEqSystem").eq(j).find("option[value='10']").remove();
    }
}

// 添加接口按钮系统类型改变
$("#portTable233").on("mousedown",".allPortEqSystem",function () {
    var abcd = $(".allPortEqSystem").index(this);
    var syval = $(".allPortEqSystem").eq(abcd).find("option:selected").text();
    showSystemListAllPort(abcd,syval);
    $(".allPortEqSystem").eq(abcd).unbind("change").bind("change",function () {
        getClassListAllPort($(".allPortEqSystem").eq(abcd).val(),abcd);
        emptyClassListAllPort(abcd);
        emptySignalTypeAllPort(abcd);
    });
});

// 添加接口按钮新增编辑设备分类
function getClassListAllPort(EqSystemCode,j) {
    $.ajax({
        type : "get",
        data : {
            "systemID" : EqSystemCode
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        async : true,
        url : baseUrl + "/eqClass/getArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                classList = d.obj;
                showClassListAllPort(j);
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}

// 添加接口按钮填充设备类型下拉框（新增编辑页）
function showClassListAllPort(j) {
    var a = '<option value="%classId%" code="%code%">%className%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    if (classList) {
        for (var i = 0; i < classList.length; i++) {
            // option = '<option value="'+classList[i].id+'" code="'+ classList[i].classcode +'">'+ classList[i].classname +'</option>'
            option = a.replace("%classId%", classList[i].id);
            option = option.replace("%code%", classList[i].classcode);
            option = option.replace("%className%", classList[i].classname);
            wrap += option;
        }
        $(".allPortEqClass").eq(j).html(wrap);
        if($(".allPortEqClass").eq(j).find("option[code='21']")){
            $(".allPortEqClass").eq(j).find("option[code='21']").remove();
        }
    }
}

// 添加接口按钮切换设备类型
$("#portTable233").on("change",".allPortEqClass",function () {
    var abcd = $(".allPortEqClass").index(this);
    var code = $(".allPortEqClass").eq(abcd).find("option:selected").attr("code");
    emptySignalTypeAllPort(abcd);
    getSignalTypeAllPort(code,abcd);
    if(code == "2" || code == "3" || code == "5" || code == "6" || code == "7" || code == "8"
        || code == "17" || code == "19"){
        var option = "";
        for(var i=1;i<17;i++){
            option += '<option value="'+i+'">'+i+'</option>>';
        }
        $(".allPortId").eq(abcd).html(option);
        // 区分开关量和模拟量的接口
        emptyPortIdType2AllPort(abcd);
        $(".allPortfireK").eq(abcd).removeAttr("disabled");
        $(".allPortfireB").eq(abcd).removeAttr("disabled");
        $(".allPortfireTop").eq(abcd).removeAttr("disabled");
        $(".allPortfireLow").eq(abcd).removeAttr("disabled");
        $(".allPortMNUint").eq(abcd).removeAttr("disabled");
        $(".allPortNormalNum").eq(abcd).attr("disabled","disabled");
        $(".allPortNormalName").eq(abcd).attr("disabled","disabled");
        $(".allPortAbnormalName").eq(abcd).attr("disabled","disabled");
    }else{
        var option = "";
        for(var i=1;i<25;i++){
            option += '<option value="'+i+'">'+i+'</option>>';
        }
        $(".allPortId").eq(abcd).html(option);
        // 区分开关量和模拟量的接口
        emptyPortIdType1AllPort(abcd);
        $(".allPortfireK").eq(abcd).attr("disabled","disabled");
        $(".allPortfireB").eq(abcd).attr("disabled","disabled");
        $(".allPortfireTop").eq(abcd).attr("disabled","disabled");
        $(".allPortfireLow").eq(abcd).attr("disabled","disabled");
        $(".allPortMNUint").eq(abcd).attr("disabled","disabled");
        $(".allPortNormalNum").eq(abcd).removeAttr("disabled");
        $(".allPortNormalName").eq(abcd).removeAttr("disabled");
        $(".allPortAbnormalName").eq(abcd).removeAttr("disabled");
    }
    if(code == "4"){
        $(".allPortSignalType").eq(abcd).find("option[value='4']").attr("selected",true);
        for(var i=7;i>4;i--){
            var clone = $("#portTable233 tr").eq(abcd).clone(true);
            clone.find(".allPortEqSystem option[value='1']").attr("selected",true);
            clone.find(".allPortEqClass option[code='4']").attr("selected",true);
            clone.find(".allPortSignalType option[value='"+ i +"']").attr("selected",true);
            $("#portTable233 tr").eq(abcd).after(clone);
        }
    }
    if(code == "17"){
        $(".allPortSignalType").eq(abcd).find("option[value='12']").attr("selected",true);
        for(var i=22;i>12;i--){
            var clone = $("#portTable233 tr").eq(abcd).clone(true);
            clone.find(".allPortEqSystem option[value='2']").attr("selected",true);
            clone.find(".allPortEqClass option[code='17']").attr("selected",true);
            clone.find(".allPortSignalType option[value='"+ i +"']").attr("selected",true);
            $("#portTable233 tr").eq(abcd).after(clone);
        }
    }
    if(code == "27"){
        $(".allPortSignalType").eq(abcd).find("option[value='23']").attr("selected",true);
        for(var i=25;i>23;i--){
            var clone = $("#portTable233 tr").eq(abcd).clone(true);
            clone.find(".allPortEqSystem option[value='4']").attr("selected",true);
            clone.find(".allPortEqClass option[code='27']").attr("selected",true);
            clone.find(".allPortSignalType option[value='"+ i +"']").attr("selected",true);
            $("#portTable233 tr").eq(abcd).after(clone);
        }
    }
    if(code == "18"){
        $(".allPortSignalType").eq(abcd).find("option[value='29']").attr("selected",true);
        for(var i=31;i>29;i--){
            var clone = $("#portTable233 tr").eq(abcd).clone(true);
            clone.find(".allPortEqSystem option[value='9']").attr("selected",true);
            clone.find(".allPortEqClass option[code='18']").attr("selected",true);
            clone.find(".allPortSignalType option[value='"+ i +"']").attr("selected",true);
            $("#portTable233 tr").eq(abcd).after(clone);
        }
    }
});

// 添加接口按钮根据设备类型切换端口号 jz
$("#portTable233").on("mousedown",".allPortId",function () {
    var abcd = $(".allPortId").index(this);
    var portval = $(".allPortId").eq(abcd).find("option:selected").text();
    var valindex;
    var code = $(".allPortEqClass").eq(abcd).find("option:selected").attr("code");
    var option = "";
    if(code == "2" || code == "3" || code == "5" || code == "6" || code == "7" || code == "8"
        || code == "17" || code == "19"){
        for(var i=1;i<17;i++){
            option += '<option value="'+i+'">'+i+'</option>';
            if(i == portval){
                valindex = i;
            }
        }
    }else{
        for(var i=1;i<25;i++){
            option += '<option value="'+i+'">'+i+'</option>';
            if(i == portval){
                valindex = i;
            }
        }
    }
    $(".allPortId").eq(abcd).html(option);
    $(".allPortId").get(0).selectedIndex=--valindex;
});

// 获取端口信号类型
function getSignalTypeAllPort(code,j) {
    $.ajax({
        type : "get",
        data : {
            "classCode" : code
        },
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        async : false,
        url : baseUrl + "/eqClass/getportNameArrayList",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                portList = d.obj;
                showSignalTypeAllPort(j);
            } else {
                layer.msg(d.desc);
            }
        },
        error : function(e) {

        }
    });
}
function showSignalTypeAllPort(j) {
    var a = '<option value="%ID%">%portName%</option>';
    var option;
    var wrap = '<option value="">--请选择--</option>';
    for (var i = 0; i < portList.length; i++) {
        option = a.replace("%ID%", portList[i].id);
        option = option.replace("%portName%", portList[i].portName);
        wrap += option;
    }
    $(".allPortSignalType").eq(j).html(wrap);
}