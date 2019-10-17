var dataHeight = 570;
var unitList;
var buildList;
var buildAreaList;
var SystemList;
var classList;
var deviceList;
var portList;
var equipmentId;
var deviceIndex = "";
var detailId;
var Id;
var SetPortValue = 0;
var smokePortValue = 0;		//防排烟系统，从setPortValue中分离
var otherPortValue = 0;
var threePortValue = 0;
var faultPortValue = 0;
var ElevenPortValue = 0;
var customPortValue = 0;
var fourPortValue = 0;
var twoPortValue = 0;       /*压力，水箱，水池系统，加入了电池*/
var eqID;
var unitID;
var customPortList = [];//其他端口数组
var flag;//用来区别是不是第一次获取到datialname的值
var isHasInterfaceTable=0;
var index;						/*新增的自定义端口的当前的序号*/
var netDeviceId;				//定义联网设备id
var _crtWidth = "",_crtHeight = "";		//放大缩小后给图片重新赋值宽高

// 自定义编辑泵控制柜的模态框
var newHtmlPump = '<div class="pumpPort customPort" id="">\n' +
	'<div class="closePort" onclick="removePumpPort(this)">X</div>\n' +
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
	'<div class="closePort" onclick="removeSmokePort(this)">X</div>\n' +
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


// 自定义查看泵控制柜的模态框
var newHtmlPumpView = '<div class="pumpPortView customPortView">\n' +
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


// 自定义查看防排烟系统的模态框
var newHtmlSmokeView = '<div class="smokeControlPortView customPortView">\n' +
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


// 删除泵控制柜的自定义的端口号
function DeleteAddPumpPort(obj) {
	index = $(obj).parent().index();
	$(obj).parent().remove();
	$(".pumpPortView").eq(index-1).remove();
}

// 删除该端口号
function removePumpPort(obj){
	if($(obj).parent().find(".PortIdDetailId").val() == ""){
		DeleteAddPumpPort(obj);
	}else{
		layer.open({
			content : "是否确定删除？",
			btn : [ "确认", "取消" ],
			yes : function(index, layero) {
				$.ajax({
					type : "get",
					async : true,
					beforeSend: function(request){
						request.setRequestHeader("Authorization","Bearer "+login.token);
					},
					data : 'id=' + $(obj).parent().attr("id"),
					url : baseUrl + "/eqDetail/deletePort",
					dataType : "json",
					success : function(d) {
						if (d.success) {
							DeleteAddPumpPort(obj);
							layer.msg("删除成功！");
						} else {
							layer.msg(d.msg);
						}
					},
					error : function(e) {

					}
				});
			},
			no : function(index, layero) {
			}
		});
	}
}

// 删除防排烟系统自定义的端口号
function DeleteAddSmokePort(obj) {
	index = $(obj).parent().index();
	$(obj).parent().remove();
	$(".smokeControlPortView").eq(index-1).remove();
}

// 删除该端口号
function removeSmokePort(obj){
	if($(obj).parent().find(".PortIdDetailId").val() == ""){
		DeleteAddSmokePort(obj);
	}else{
		layer.open({
			content : "是否确定删除？",
			btn : [ "确认", "取消" ],
			yes : function(index, layero) {
				$.ajax({
					type : "get",
					async : true,
					beforeSend: function(request){
						request.setRequestHeader("Authorization","Bearer "+login.token);
					},
					data : 'id=' + $(obj).parent().attr("id"),
					url : baseUrl + "/eqDetail/deletePort",
					dataType : "json",
					success : function(d) {
						if (d.success) {
							DeleteAddSmokePort(obj);
							layer.msg("删除成功！");
						} else {
							layer.msg(d.msg);
						}
					},
					error : function(e) {

					}
				});
			},
			no : function(index, layero) {
			}
		});
	}
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



// 获取新增的自定义的数据
function getAddPumpView() {
	var flag1 = true;		//新增自定义为启停的端口时，防止覆盖到原端口
	var flag2 = true;		//新增自定义为电源的端口时，防止覆盖到原端口
	var flag3 = true;		//新增自定义为手自动的端口时，防止覆盖到原端口
	var flag4 = true;		//新增自定义为故障的端口时，防止覆盖到原端口
	for(var i = 0;i < portList.length;i++){
		if(portList[i].iotype == 2){
			if(portList[i].eqSystemID !=4 && portList[i].eqSystemID != 9){
				var detialname = portList[i].detialname;
				if(flag1 && detialname == "启停"){
					flag1 = false;
					continue;
				}else if(flag2 && detialname == "电源") {
					flag2 = false;
					continue;
				}else if(flag3 && detialname == "手自动") {
					flag3 = false;
					continue;
				}else if(flag4 && detialname == "故障") {
					flag4 = false;
					continue;
				}else{
					$("#fourPort .portTable").append(newHtmlPump);
					$("#fourPortView .portTable").append(newHtmlPumpView);
				}
			}
		}
	}
}

// 获取防排烟系统自定义数据
function getAddSmokeView(){
    var flag1 = true;		//新增自定义为启停的端口时，防止覆盖到原端口
    var flag2 = true;		//新增自定义为电源的端口时，防止覆盖到原端口
    var flag3 = true;		//新增自定义为手自动的端口时，防止覆盖到原端口
	for(var i = 0;i < portList.length;i++){
		if(portList[i].iotype == 2){
			if(portList[i].eqSystemID == 9){
				var detialname = portList[i].detialname;
				if(flag1 && detialname == "启停"){
					flag1 = false;
					continue;
				}else if(flag2 && detialname == "电源") {
					flag2 = false;
					continue;
				}else if(flag3 && detialname == "手自动") {
					flag3 = false;
					continue;
				}else{
					$("#smokePort .portTable").append(newHtmlSmoke);
					$("#smokePortView .portTable").append(newHtmlSmokeView);
				}
			}
		}
	}
}


$(function() {

	// 灭火系统泵控制柜新增端口号设置
	$(".btnAddPort").click(function () {
		$("#fourPort .portTable").append(newHtmlPump);
		$("#fourPortView .portTable").append(newHtmlPumpView);
	});

	// 防排烟系统系统防排烟新增端口号设置
	$(".btnAddSmokePort").click(function () {
		$("#smokePort .portTable").append(newHtmlSmoke);
		$("#smokePortView .portTable").append(newHtmlSmokeView);
	});

	// 搜索控制
	$(".jy_mainTile").hide();
	$("#MNPort").hide();
	$("#SZPort").hide();
	$(".btnAddMNPortSure").hide();
	$(".btnAddSZPortSure").hide();
	$(".btnUpdateMNPortSure").hide();
	$(".btnUpdateSZPortSure").hide();
	// 主页面查询
	mainSearch();
	getSystemList();
	getBuildList();
	getClassList();
	getDeviceList();
	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$(".notLastModal").on("hidden.bs.modal", function() {
		// $("#newProject").addClass("modal-open");
		$("#exportAddress").css("overflow-y", "auto");
		$("#newInterface").css("overflow-y", "auto");
	});
});

// 普通搜索
$(".ordinarySearch").click(function() {
	var id = $("#menuType").val();
	var value = $("#searchContent").val();
	$("#" + id).val(value);
	$("#equipmentTable").bootstrapTable('destroy');
	mainSearch();
});

$(".btnSearch").click(function() {
	var id = $("#menuType").val();
	var value = $("#searchContent").val();
	$("#" + id).val(value);
	$("#equipmentTable").bootstrapTable('destroy');
	mainSearch();
});

$(".seniorSearch").click(function() {
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

function emptySearchMsg() {
	$("#EQMName").val("");
	$("#NdName").val("");
	$("#unitName").val("");
	$("#ntName").val("");
	$("#EqSystemCodes").val("");
	$("#EqClassIDs").val("");
	$("#searchContent").val("");
	$("#ownercode").val("");
}

$("#menuType").change(function() {
	emptySearchMsg();
});

// 导入图片
$("#leadingInImg").click(function () {
	$("#imgMark").modal();
});

// 查看导入图片
$("#leadingInImgView").click(function () {
	$("#imgMarkView").modal();
});

// 上传并预览图片
function doChangeProjectphoto() {
	uploadOne("picurls-photo", "", "photoCover-photo", false);
	var img = new Image();
	$("#imgSmall").remove();
	setTimeout(function () {
		var photo = $("#picurls-photo").attr("file_string");
		img.src = photo;
		$("#imgDiv").html(img);
	},1000);
};

// 增加标注
$(".new-mark").click(function () {
	if($("#imgDiv").find("img").length == 0){
		layer.msg("请先上传图片！");
		return;
	}
	if($("#imgSmall").length == 0){
		$("#imgDiv").append('<div id="imgSmall" style="background: url(../../../image/dingwei.png);background-size: cover;width: 24px;height: 32px;cursor: move;top: 50%;left: 50%;position: absolute"></div>');
		tuozhuai();
	}else{
		layer.msg("只能标注一个点！");
	}
});

// 点位删除
$(".del-mark").click(function(){
	if($("#imgSmall").length == 1){
		$("#imgSmall").remove();
	}else{
		layer.msg("无标注的点");
	}
});

// 缩放图片
function bbimg(o){
	var zoom=parseInt(o.style.zoom, 10)||100;
	zoom+=event.wheelDelta/12;
	if (zoom>0) o.style.zoom=zoom+'%';

	$('#imgDiv').css({ 'height': zoom / 100 * $('#imgDiv').attr('height') + 'px', 'width': zoom / 100 * $('#imgDiv').attr('width') + 'px' });
	// $('#imgSmall').css({ 'top': _pointPercentY + '%', 'left': _pointPercentX + '%' }).attr({'top':_pointPercentY,'left':_pointPercentX});

	_crtWidth = $('#imgDiv').width();
	_crtHeight = $('#imgDiv').height();

}

// 图片拖拽
$('#wrap_img').udraggable({
	drag: function (e, ui) {
		$('#imgDiv').css({ 'width': _crtWidth, 'height': _crtHeight });
	}
});

// 点位拖拽
function tuozhuai(){
	_crtWidth = $("#imgDiv").width();
	_crtHeight = $("#imgDiv").height();
	var domY = "";
	var domX = "";
	var pos = "";
	var reg = /px|%/;
	$("#imgSmall").udraggable({
		containment: 'parent',
		drag: function (e, ui) {
			pos = ui.position;
			var wrap_h = $('#imgDiv').height();
			var wrap_w = $('#imgDiv').width();

			pos.left = pos.left / wrap_w * 100 + "%";
			pos.top = pos.top / wrap_h * 100 + "%";

			domY = pos.top.replace(reg, " ")*$('#imgDiv').attr('height')/100;
			domX = pos.left.replace(reg, " ")*$('#imgDiv').attr('width')/100;

		},
		stop: function (ui) {
			$("#imgSmall").css({ 'top': pos.top, 'left': pos.left}).attr({'top': pos.top, 'left': pos.left})
		}
	});
	//重新给点位赋值，因为拖拽的插件问题，具体点位想x,y会被设置成px，为了方便缩放，所以要重新设置成%单位
}

function setDotPointAgain(dom, domTop, domLeft) {
	var wrap_h = $('#imgDiv').height();
	var wrap_w = $('#imgDiv').width();
	var reg = /px|%/;//去除px或%单位
	//console.log($('#imgDiv').attr('height'),domTop.replace(reg, " "),wrap_h)
	var px_t = domTop.replace(reg, " ");
	var px_l = domLeft.replace(reg, " ");

	var top = domTop.replace(reg, " ")/wrap_h*$('#imgDiv').attr('height');
	var left = domLeft.replace(reg, " ")/wrap_h*$('#imgDiv').attr('width');

	dom.css({ "top": px_t / wrap_h * 100 + "%", "left": px_l / wrap_w * 100 + "%"})
}

$("#imgSmall").on("mousedown",function (e) {
	// e ? e.stopPropagation() : e.cancelBubble;
	setDotPointAgain($(this),$(this).css('top'),$(this).css('left'));
});






// 重置
$(".btnReset").click(function() {
	emptySearchMsg();
	$("#equipmentTable").bootstrapTable('destroy');
	mainSearch();
});

// 新增按钮
$(".btnNew").click(function() {
	$(".oneKeyFilling").show();		//新增时显示电气火灾的一键填充按钮
	Id = "";
	showSystemList();
	showUnitList();
	SetPortValue = 0;
	smokePortValue = 0;
	otherPortValue = 0;
	threePortValue = 0;
	faultPortValue = 0;
	ElevenPortValue = 0;
	customPortValue = 0;
	fourPortValue = 0;
	twoPortValue = 0;
	emptyDeviceList();
	emptyBuildList();
	emptyBuildAreaList();
	emptyClassList();
	$("#EqClassID").removeAttr("disabled");
	$("#EqSystemCode").removeAttr("disabled");
	emptySearch();
	emptyPortMsg();
	emptyFirePortMsg();
	emptyOtherPortMsg();
	emptyFoutPortMsg();
	emptyTwoPortMsg();
	$("#SetPort").hide();
	$("#smokePort").hide();
	$("#otherPort").hide();
	$("#threePort").hide();
	$("#faultPort").hide();
	$("#ElevenPort").hide();
	$("#customPort").hide();
	$("#fourPort").hide();
	$("#twoPort").hide();
	$('#portTable').bootstrapTable("destroy");
	portSearch("");
	getUnitList();
	showSystemList();
	$("#EqSystemCode").find("option:selected").val("");
	$("#newAdd .modal-title").html("新增设备设施");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('0');
	$("#preId").val('');
	$("#MNPort").hide();
	$("#SZPort").hide();
	$(".customPort").remove();
	$(".customPortView").remove();
	$("#picurls-photo").attr("file_string","");
	$("#imgDiv img").remove();
	$("#imgSmall").remove();
	$("#wrap_img").html('<div style="position: relative;" id="imgDiv" onmousewheel="return bbimg(this)"></div>');
});

// 新增或编辑角色
$(".btnSure")
	.click(
		function() {
			var data;
			$("#EqClassID").removeAttr("disabled");
			var UnitID = $("#UnitID").find("option:selected").val();
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
			var buildImgbg = $("#picurls-photo").attr("file_string");
			var pointx = $("#imgSmall").attr("left");
			var pointy = $("#imgSmall").attr("top");
			var pointVideo = $("#pointVideo").val();
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

			var edit = $("#edit").val();
			if (edit == "0") {
				var url = baseUrl + '/equipmentFac/add';
			} else {
				var url = baseUrl + '/equipmentFac/update';
			}
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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

					// var list = [];
					// for (var i = 0; i < 3; i++) {
					// 	var ename = $("#Name" + i).html();
					// 	var detailid = $("#BDetailID" + i).val();
					// 	var ioport = $("#Port" + i).val();
					// 	var iotype = $("#Type" + i).val();
					// 	var digitalnormal = $("#Value" + i).find(
					// 		"option:selected").val();
					// 	var goodname = $("#One" + i).val();
					// 	var badname = $("#Zero" + i).val();
					// 	var reserve = $("#ShReserve" + i).val();
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
					"list" : JSON.stringify(list)
				};
			}
			if (twoPortValue == 1){//压力，水箱，水池，三个附带电量的接口
                var list = [];

                for (var i = 0; i < $('.twoPort').length; i++) {
                    var detailid = $(".twoPort").eq(i).find(".twoPortOdetailID").val();
                    var Port = $(".twoPort").eq(i).find(".twoPortValue").val();
                    var KValue = $(".twoPort").eq(i).find(".twoPortKValue").val();
                    var BValue = $(".twoPort").eq(i).find(".twoPortBValue").val();
                    var TopValue = $(".twoPort").eq(i).find(".twoPortTopValue").val();
                    var LowValue = $(".twoPort").eq(i).find(".twoPortLowValue").val();
                    var MNUnit = $(".twoPort").eq(i).find(".twoPortMNUnit").val();
                    var reserve = $(".twoPort").eq(i).find(".twoPortreserve").val();
                    var maxValue = $(".twoPort").eq(i).find(".twoPortMaxValue").val();
                    var minValue =  $(".twoPort").eq(i).find(".twoPortMinValue").val();
                    var detialname = $(".twoPort").eq(i).find(".twoPortName").html();
                    if (Port != "" && KValue != ""
                        && BValue != "" && TopValue != "" && LowValue != ""
                        && MNUnit != "") {
						if (parseFloat(TopValue) <= parseFloat(LowValue)) {
							layer.msg("上限必须大于下限！");
							return;
						}
						if (parseFloat(maxValue) <= parseFloat(minValue)) {
							layer.msg("最大值必须大于最小值！");
							return;
						}
						if (parseFloat(maxValue) <= parseFloat(TopValue)) {
							layer.msg("最大值必须大于上限值！");
							return;
						}
						if (parseFloat(minValue) >= parseFloat(LowValue)) {
							layer.msg("最小值必须小于下限值！");
							return;
						}
						list.push({
							detailid: detailid,
							ioport: Port,
							analogk: KValue,
							analogb: BValue,
							analogup: TopValue,
							analogdown: LowValue,
							analogunit: MNUnit,
							maxValue: maxValue,
							minValue: minValue,
							reserve: reserve,
							detialname: detialname
						});
					} else if (detailid == "" && Port == "" && KValue == ""
						&& BValue == "" && TopValue == ""
						&& LowValue == "" && MNUnit == "") {
						continue;
					} else if (detailid != "" && Port == "" && KValue == ""
                        && BValue == "" && TopValue == ""
                        && LowValue == "" && MNUnit == ""){
                        list.push({
                            detailid: detailid,
                            ioport: Port,
                            analogk: KValue,
                            analogb: BValue,
                            analogup: TopValue,
                            analogdown: LowValue,
                            analogunit: MNUnit,
                            maxValue: maxValue,
                            minValue: minValue,
                            reserve: reserve
                            // detialname: detialname
                        });
                    } else {
						layer.msg("请将一个端口填写完整！");
						return;
					}
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
                    "buildImgbg" : buildImgbg,
                    "pointx" : pointx,
                    "pointy" : pointy,
                    "pointVideoId" : pointVideo,
                    "portLists" : JSON.stringify(list)
                };
                console.log(data);
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
					"actionType" : 1,
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo
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
				// if (maxValue == "") {
				// 	layer.msg("最大值不能为空！");
				// 	return;
				// }
				// if (minValue == "") {
				// 	layer.msg("最小值不能为空！");
				// 	return;
				// }
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
					"maxValue":maxValue,
					"minValue":minValue,
					"detialname" : detialname,
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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
					// if (maxValue == "") {
					// 	layer.msg("最大值不能为空！");
					// 	return;
					// }
					// if (minValue == "") {
					// 	layer.msg("最小值不能为空！");
					// 	return;
					// }
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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
						&& MNUnit != "") {
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
						&& LowValue == "" && MNUnit == "") {
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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
					"buildImgbg" : buildImgbg,
					"pointx" : pointx,
					"pointy" : pointy,
                    "pointVideoId" : pointVideo,
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
							emptyTwoPortMsg();
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

// 清空设备信息
function emptySearch() {
	$("#UnitID").val("");
	$("#BuildId").val("");
	$("#BuildAreaId").val("");
	$("#EqClassID").val("");
	$("#NetDeviceID").html('<option value="">--请选择--</option>');
	$("#Position").val("");
	$("#EqName").val("");
	$("#pointVideo").html('<option value="">请选择</option>');
}
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
	$("#ShReserve").val("");
}
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
function emptyPortMsgView() {
	for(var i=0;i<3;i++){
		$(".smokeControlPortView").eq(i).find(".PortIdDetailId").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdValue").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdType").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdNormalNum").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdNormalName").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdAbnormalName").val("");
		$(".smokeControlPortView").eq(i).find(".PortIdSignal").val("");

		$("#BDetailIDView" + i).val("");
		$("#PortView" + i).val("");
		$("#TypeView" + i).val("");
		$("#ValueView" + i).val("");
		$("#OneView" + i).val("");

		$("#TPortView" + i).val("");
		$("#KValueView" + i).val("");
		$("#BValueView" + i).val("");
		$("#TopValueView" + i).val("");
		$("#LowValueView" + i).val("");
		$("#MaxValueView"+i).val("");
		$("#MinValueView"+i).val("");
		$("#MNUnitView" + i).val("");
		$("#TdetailIDView" + i).val("");
		$("#reserveView" + i).val("");
	}
}
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
function emptyFoutPortMsgView() {
	for(var i = 0; i < 4; i++){
		$(".pumpPortView").eq(i).find(".PortIdDetailId").val("");
		$(".pumpPortView").eq(i).find(".PortIdValue").val("");
		$(".pumpPortView").eq(i).find(".PortIdType").val("");
		$(".pumpPortView").eq(i).find(".PortIdNormalNum").val("");
		$(".pumpPortView").eq(i).find(".PortIdNormalName").val("");
		$(".pumpPortView").eq(i).find(".PortIdAbnormalName").val("");
		$(".pumpPortView").eq(i).find(".PortIdSignal").val("");
	}
}
function emptyTwoPortMsg() {
	for(var i = 0; i < 2;i++){
		$(".twoPort").eq(i).find(".twoPortOdetailID").val("");
		$(".twoPort").eq(i).find(".twoPortValue").val("");
		$(".twoPort").eq(i).find(".twoPortKValue").val("");
		$(".twoPort").eq(i).find(".twoPortBValue").val("");
		$(".twoPort").eq(i).find(".twoPortTopValue").val("");
		$(".twoPort").eq(i).find(".twoPortLowValue").val("");
		$(".twoPort").eq(i).find(".twoPortMNUnit").val("");
		$(".twoPort").eq(i).find(".twoPortMaxValue").val("");
		$(".twoPort").eq(i).find(".twoPortMinValue").val("");
		$(".twoPort").eq(i).find(".twoPortreserve").val("");
	}
}
function emptyTwoPortMsgView() {
	for(var i = 0; i < 2;i++){
		$(".twoPortView").eq(i).find(".twoPortOdetailIDView").val("");
		$(".twoPortView").eq(i).find(".twoPortValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortKValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortBValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortTopValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortLowValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortMNUnitView").val("");
		$(".twoPortView").eq(i).find(".twoPortMaxValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortMinValueView").val("");
		$(".twoPortView").eq(i).find(".twoPortreserveView").val("");
	}
}
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
function emptyFirePortMsgView() {
	for (var i = 0; i < 11; i++) {
		$("#fireNameView" + i).val("");
		$("#firePortView" + i).val("");
		$("#fireKValueView" + i).val("");
		$("#fireBValueView" + i).val("");
		$("#fireTopValueView" + i).val("");
		$("#fireLowValueView" + i).val("");
		$("#fireMaxValueView"+i).val("");
		$("#fireMinValueView"+i).val("");
		$("#fireMNUnitView" + i).val("");
		$("#firedetailIDView" + i).val("");
		$("#fireReserveView" + i).val("");
	}
}

// 编辑设备
function editVideo(_this) {
	$(".oneKeyFilling").hide();		//编辑时隐藏电气火灾的一键填充按钮
	netDeviceId = _this.netdeviceid;
	showSystemList();

	unitID = _this.unitid;
	showUnitList(unitID);
	customPortList = [];
	$("#EqClassID").attr("disabled", "disabled");
	$("#EqSystemCode").attr("disabled", "disabled");
	$("#MNPort").hide();
	$("#SZPort").hide();
	$("#SetPort").hide()
	$("#smokePort").hide();
	$("#otherPort").hide();
	$("#threePort").hide();
	$("#faultPort").hide();
	$("#fourPort").hide();
	$("#ElevenPort").hide();
	$("#customPort").hide();
	$("#twoPort").hide();
	Id = _this.id;
	emptySearch();
	emptyPortMsg();
	emptyOtherPortMsg();
	emptyFoutPortMsg();
	emptyTwoPortMsg();
	emptyFirePortMsg();
	detailId = _this.detailid;
	showBuildList();
	showBuildAreaList();
	showSystemList();
	$("#EqSystemCode").val(_this.eqsystemid);
	$("#UnitID").val(_this.unitid);
	getBuildList();
	if(_this.pointVideoId != "" || _this.pointVideoId != undefined){
		getPointVideoSelect('pointVideo',_this.unitid,_this.pointVideoId);
	}

	var code = _this.classcode;
	var name = _this.classname;

	if (code == "27") {
		$("#SetPort").show();
		SetPortValue = 1;
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		// switch (code) {
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
	}else if(code == "18"){
		$("#smokePort").show();
		smokePortValue = 1;
		$("#SetPort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		// switch (code) {
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
	} else if (code == "4"){//泵控制柜
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#faultPort").hide();
		$("#fourPort").show();
		$("#threePort").hide();
		$("#SetPort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue  = 1;
		SetPortValue = 0;
		twoPortValue = 0;
		deviceIndex = 3;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();

	}else if (code == "19" || code == "0" || code == "5"
		|| code == "6" || code == "8" || code == "9"
		|| code == "10" || code == "11" || code == "12") {
		$("#otherPort").show();
		$("#OtherPortName").html(_this.classname);
		otherPortValue = 1;
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#threePort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		smokePortValue = 0;
		SetPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		switch (code) {
			case "5":
				$("#OtherPortName").html("端口号(温度)");
				break;
			case "6":
				$("#OtherPortName").html("端口号(湿度)");
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
	} else if(code == "2" || code == "3" || code == "7"){
		$("#otherPort").hide();
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#threePort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").show();
		otherPortValue = 0;
		smokePortValue = 0;
		SetPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		twoPortValue = 1;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		switch (code) {
			case "2":
				$(".twoPortName:eq(0)").html("端口号(水箱)");
				break;
			case "3":
				$(".twoPortName:eq(0)").html("端口号(水池)");
				break;
			case "7":
				$(".twoPortName:eq(0)").html("端口号(压力)");
				break;
		}
	} else if (code == "15") {
		$("#threePort").show();
		threePortValue = 1;
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#ElevenPort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		faultPortValue = 0;
		$("#otherPort").hide();
		otherPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();

		$("#portName0").html("端口号(IA)");
		$("#portName1").html("端口号(IB)");
		$("#portName2").html("端口号(IC)");
	} else if (code == "14") {// 三相电压
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").show();
		$("#customPort").hide();
		$("#portName0").html("端口号(UA)");
		$("#portName1").html("端口号(UB)");
		$("#portName2").html("端口号(UC)");
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#ElevenPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 1;
		faultPortValue = 0;
		ElevenPortValue = 0;
		fourPortValue = 0;
		customPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();

	} else if (code == "13" || code == "28") {
		$("#ElevenPort").hide();
		$("#threePort").hide();
		$("#customPort").hide();
		threePortValue = 0;
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#faultPort").show();
		$("#fourPort").hide();
		$("#twoPort").hide();
		faultPortValue = 1;
		SetPortValue = 0;
		smokePortValue = 0;
		$("#otherPort").hide();
		otherPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").show();
		if (code == "13") {
			$("#FName").html("端口号(故障)");
		} else {
			$("#FName").html("端口号(状态)");
		}
		getDeviceList(3,_this.netdeviceid);

	} else if (code == "20") {
		$("#ElevenPort").hide();
		$("#threePort").hide();
		threePortValue = 0;
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#faultPort").show();
		$("#fourPort").hide();
		$("#twoPort").hide();
		faultPortValue = 1;
		SetPortValue = 0;
		smokePortValue = 0;
		$("#otherPort").hide();
		$("#customPort").hide();
		otherPortValue = 0;
		ElevenPortValue = 0;
		fourPortValue = 0;
		customPortValue = 0;
		twoPortValue = 0;
		$("#FName").html("端口号(称重)");
		getDeviceList(4,_this.netdeviceid);
		$(".leadingInImg").show();

	} else if (name == "报警主机") {
		getDeviceList(1,_this.netdeviceid);
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		fourPortValue = 0;
		customPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").hide();

	} else if (code == "16") {
		getDeviceList(2,_this.netdeviceid);
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#ElevenPort").hide();
		$("#customPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		fourPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").hide();

	} else if (code == "17") {
		getDeviceList(3,_this.netdeviceid);
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#customPort").hide();
		$("#ElevenPort").show();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		fourPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 1;
		customPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").show();

	}else if (code == "100"|| code == "101" || code == "102" || code == "103" || code == "104") {
		$('#portTable').bootstrapTable("destroy");
		portSearch(Id);
		getDeviceList(3,_this.netdeviceid);
		$("#SetPort").hide();
		$("#smokePort").hide();
		$("#otherPort").hide();
		$("#threePort").hide();
		$("#faultPort").hide();
		$("#fourPort").hide();
		$("#customPort").show();
		$("#ElevenPort").hide();
		$("#twoPort").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		fourPortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 1;
		twoPortValue = 0;
		$(".leadingInImg").show();
	}

	setTimeout(function() {
		$("#BuildId").val(_this.buildid);
		getBuildAreaList();
		setTimeout(function() {
			$("#BuildAreaId").val(_this.buildareaid);
		}, 100);
	}, 100);

	getClassList(_this.eqsystemid);
	$("#EqClassID").val(_this.eqclassid);
	// console.log("NetDeviceID:" + _this.netdeviceid);

	setTimeout(function() {
		$("#NetDeviceID").val(_this.netdeviceid);
	}, 100);
	$("#Position").val(_this.installposition);
	$("#EqName").val(_this.eqname);
	$("#reserve").val(_this.reserve);
	$("#picurls-photo").attr("file_string",_this.buildImgbg);
	var photo = $("#picurls-photo").attr("file_string");
	var img = new Image();
	img.src = photo;
	$("#imgDiv").html(img);
	if(_this.pointx && _this.pointy){
		$("#imgSmall").remove();
		$("#imgDiv").append('<div id="imgSmall" style="background: url(../../../image/dingwei.png);background-size: cover; width: 24px; height: 32px; cursor: move;position: absolute;"></div>');
		tuozhuai();
		$("#imgSmall").css({"top": _this.pointy,"left" : _this.pointx});
	}

	getPortList(Id,name);
	$("#newAdd .modal-title").html("编辑设备设施");
	$("input[name=indexId][type=hidden]").val("");
	$("#edit").val('1');
	$("#newAdd").modal();
}

// 查看设备
function showVideo(_this) {
	$(".oneKeyFilling").hide();		//查看时隐藏电气火灾的一键填充按钮
	showSystemList();
	unitID = _this.unitid;
	showUnitList(unitID);
	$("#EqClassIDView").attr("disabled", "disabled");
	$("#EqSystemCodeView").attr("disabled", "disabled");
	$("#SetPortView").hide();
	$("#smokePortView").hide();
	$("#otherPortView").hide();
	$("#threePortView").hide();
	$("#fourPortView").hide();
	$("#faultPortView").hide();
	$("#ElevenPortView").hide();
	$("#customPortView").hide();
	$("#twoPortView").hide();
	Id = _this.id;
	emptySearch();
	emptyPortMsgView();
	emptyOtherPortMsg();
	emptyFoutPortMsgView();
	emptyFirePortMsgView();
	emptyTwoPortMsgView();
	var code = _this.classcode;
	// console.log("daada:" + code);
	var name = _this.classname;

	if(_this.pointVideoId != "" || _this.pointVideoId != undefined){
		getPointVideoSelect('pointVideoView',_this.unitid,_this.pointVideoId);
	}else{
        $("#pointVideoView").html('<option value="">请选择</option>');
    }

	if (code == "27") {
		$("#SetPortView").show();
		SetPortValue = 1;
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		// switch (code) {
		// 	case "18":
		// 		$("#NameView0").html("端口号(手自动)");
		// 		$("#NameView1").html("端口号(启停)");
		// 		$("#NameView2").html("端口号(电源)");
		// 		break;
		// 	case "27":
		// 		$("#NameView0").html("端口号(电源)");
		// 		$("#NameView1").html("端口号(半降)");
		// 		$("#NameView2").html("端口号(全降)");
		// 		break;
		// 	default:
		// 		break;
		// }
	}else if(code == "18") {
		$("#SetPortView").hide();
		$("#smokePortView").show();
		smokePortValue = 1;
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		// switch (code) {
		// 	case "18":
		// 		$("#NameView0").html("端口号(手自动)");
		// 		$("#NameView1").html("端口号(启停)");
		// 		$("#NameView2").html("端口号(电源)");
		// 		break;
		// 	case "27":
		// 		$("#NameView0").html("端口号(电源)");
		// 		$("#NameView1").html("端口号(半降)");
		// 		$("#NameView2").html("端口号(全降)");
		// 		break;
		// 	default:
		// 		break;
		// }
	}else if (code == "4"){//泵控制柜
		$("#SetPortView").hide();
		$("#smokePortView").hide()
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#fourPortView").show();
		$("#twoPortView").hide();
		otherPortValue = 0;
		smokePortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue  = 1;
		SetPortValue = 0;
		twoPortValue = 0;
		deviceIndex = 3;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
	}else if (code == "19" || code == "0" ||  code == "5"
		|| code == "6" || code == "8" || code == "9"
		|| code == "10" || code == "11" || code == "12") {
		$("#otherPortView").show();
		$("#OtherPortNameView").html(_this.classname);
		otherPortValue = 1;
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#faultPortView").hide();
		$("#threePortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		switch (code) {
			case "5":
				$("#OtherPortNameView").html("端口号(温度)");
				break;
			case "6":
				$("#OtherPortNameView").html("端口号(湿度)");
				break;
			case "8":
				$("#OtherPortNameView").html("端口号(溢水值)");
				break;
			case "10":
				$("#OtherPortNameView").html("端口号(电压)");
				break;
			case "11":
				$("#OtherPortNameView").html("端口号(电弧)");
				break;
			case "12":
				$("#OtherPortNameView").html("端口号(电流)");
				break;
			default:
				$("#OtherPortNameView").html("端口号(压力)");
				break;
		}
	} else if(code == "2" || code == "3" || code == "7"){
		$("#otherPortView").hide();
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#faultPortView").hide();
		$("#threePortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").show();
		otherPortValue = 0;
		SetPortValue = 0;
		smokePortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 1;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		switch (code) {
			case "2":
				$(".twoPortNameView:eq(0)").html("端口号(水箱)");
				break;
			case "3":
				$(".twoPortNameView:eq(0)").html("端口号(水池)");
				break;
			case "7":
				$(".twoPortNameView:eq(0)").html("端口号(压力)");
				break;
		}
	} else if (code == "15") {
		$("#threePortView").show();
		threePortValue = 1;
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#ElevenPortView").hide();
		$("#faultPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		$("#otherPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		faultPortValue = 0;
		otherPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
		$("#portNameView0").html("端口号(IA)");
		$("#portNameView1").html("端口号(IB)");
		$("#portNameView2").html("端口号(IC)");
	} else if (code == "14") {// 三相电压
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").show();
		$("#portNameView0").html("端口号(UA)");
		$("#portNameView1").html("端口号(UB)");
		$("#portNameView2").html("端口号(UC)");
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 1;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		getDeviceList(3,_this.netdeviceid);
		$(".leadingInImg").show();
	} else if (code == "13" || code == "28") {
		$("#ElevenPortView").hide();
		$("#threePortView").hide();
		$("#customPortView").hide();
		threePortValue = 0;
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#faultPortView").show();
		$("#twoPortView").hide();
		faultPortValue = 1;
		SetPortValue = 0;
		smokePortValue = 0;
		$("#otherPortView").hide();
		otherPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").show();
		if (code == "13") {
			$("#FNameView").html("端口号(故障)");
		} else {
			$("#FNameView").html("端口号(状态)");
		}
		getDeviceList(3,_this.netdeviceid);
	} else if (code == "20") {
		$("#ElevenPortView").hide();
		$("#threePortView").hide();
		$("#customPortView").hide();
		threePortValue = 0;
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#faultPortView").show();
		$("#twoPortView").hide();
		faultPortValue = 1;
		SetPortValue = 0;
		smokePortValue = 0;
		$("#otherPortView").hide();
		otherPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$("#FNameView").html("端口号(称重)");
		getDeviceList(4,_this.netdeviceid);
		$(".leadingInImg").show();
	} else if (name == "报警主机") {
		getDeviceList(1,_this.netdeviceid);
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").hide();
	} else if (code == "16") {
		getDeviceList(2,_this.netdeviceid);
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#ElevenPortView").hide();
		$("#customPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		ElevenPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").hide();
	} else if (code == "17") {
		getDeviceList(3,_this.netdeviceid);
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#customPortView").hide();
		$("#fourPortView").hide();
		$("#ElevenPortView").show();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		customPortValue = 0;
		fourPortValue = 0;
		ElevenPortValue = 1;
		twoPortValue = 0;
		$(".leadingInImg").show();
	}else if (code == "100"|| code == "101" || code == "102" || code == "103" || code == "104") {
		$('#portTableView').bootstrapTable("destroy");
		portViewSearch(Id);
		getDeviceList(3,_this.netdeviceid);
		$("#SetPortView").hide();
		$("#smokePortView").hide();
		$("#otherPortView").hide();
		$("#threePortView").hide();
		$("#faultPortView").hide();
		$("#customPortView").show();
		$("#ElevenPortView").hide();
		$("#twoPortView").hide();
		SetPortValue = 0;
		smokePortValue = 0;
		otherPortValue = 0;
		threePortValue = 0;
		faultPortValue = 0;
		customPortValue = 1;
		ElevenPortValue = 0;
		fourPortValue = 0;
		twoPortValue = 0;
		$(".leadingInImg").show();
	}
	Id = _this.id;
	detailId = _this.detailid;
	showBuildList();
	showBuildAreaList();
	showSystemList();
	$("#EqSystemCodeView").val(_this.eqsystemid);
	$("#UnitIDView").val(_this.unitid);
	getBuildList();
	setTimeout(function() {
		$("#BuildIdView").val(_this.buildid);
		getBuildAreaList();
		setTimeout(function() {
			$("#BuildAreaIdView").val(_this.buildareaid);
		}, 200);
	}, 100);

	getClassList(_this.eqsystemid);
	$("#EqClassIDView").val(_this.eqclassid);
	setTimeout(function() {
		$("#NetDeviceIDView").val(_this.netdeviceid);
	}, 100);
	$("#PositionView").val(_this.installposition);
	$("#EqNameView").val(_this.eqname);
	$("#reserveView").val(_this.reserve);

	$("#picurls-photoView").attr("file_string",_this.buildImgbg);
	var photo = $("#picurls-photoView").attr("file_string");
	var img = new Image();
	img.src = photo;
	$("#imgDivView").html(img);
	if(_this.pointx && _this.pointy){
		$("#imgSmallView").remove();
		$("#imgDivView").append('<div id="imgSmallView" style="background: url(../../../image/dingwei.png);width: 24px;height: 32px;top: '+_this.pointy+';left: '+_this.pointx+';cursor:move;"></div>');
		tuozhuai();
	}

	getPortList(Id,name);
	$("#newAddView .modal-title").html("查看设备设施");
	$("input[name=indexId][type=hidden]").val("");
	$("#newAddView").modal();
}


// 根据设备id查询端口信息
function getPortList(id,name) {
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			"id" : id
		},
		url : baseUrl + '/eqDetail/getPortList',
		dataType : "json",
		success : function(d) {
			if (d.success) {
				portList = d.obj;
				// 这里做判断，新增的自定义数据加载其中一个方法
				if(name == '泵控制柜'){
					if($(".pumpPort").length <= 4){
						getAddPumpView();
					}
				}else if(name == '防排烟'){
					if($(".smokeControlPort").length <= 3){
						getAddSmokeView();
					}
				}
				showPortList();
				showOtherList();
				showOtherListView();
				showPortListView();
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}


// 根据设备id查询端口信息(查看窗口)

function showOtherListView() {// 查看模拟量
	for (var i = 0; i < portList.length; i++) {
		if (portList[i].iotype == 1) {
			if (portList[i].eqSystemID == '1') {
                if(portList[i].classCode == 2){
                    $(".twoPortView").eq(i).find(".twoPortOdetailIDView").val(portList[i].id);
                    $(".twoPortView").eq(i).find(".twoPortValueView").val(portList[i].ioport);
                    $(".twoPortView").eq(i).find(".twoPortKValueView").val(portList[i].analogk);
                    $(".twoPortView").eq(i).find(".twoPortBValueView").val(portList[i].analogb);
                    $(".twoPortView").eq(i).find(".twoPortTopValueView").val(portList[i].analogup);
                    $(".twoPortView").eq(i).find(".twoPortLowValueView").val(portList[i].analogdown);
                    $(".twoPortView").eq(i).find(".twoPortMNUnitView").val(portList[i].analogunit);
                    $(".twoPortView").eq(i).find(".twoPortMaxValueView").val(portList[i].maxValue);
                    $(".twoPortView").eq(i).find(".twoPortMinValueView").val(portList[i].minValue);
                    $(".twoPortView").eq(i).find(".twoPortreserveView").val(portList[i].reserve);
                }else if(portList[i].classCode == 3){
                    $(".twoPortView").eq(i).find(".twoPortOdetailIDView").val(portList[i].id);
                    $(".twoPortView").eq(i).find(".twoPortValueView").val(portList[i].ioport);
                    $(".twoPortView").eq(i).find(".twoPortKValueView").val(portList[i].analogk);
                    $(".twoPortView").eq(i).find(".twoPortBValueView").val(portList[i].analogb);
                    $(".twoPortView").eq(i).find(".twoPortTopValueView").val(portList[i].analogup);
                    $(".twoPortView").eq(i).find(".twoPortLowValueView").val(portList[i].analogdown);
                    $(".twoPortView").eq(i).find(".twoPortMNUnitView").val(portList[i].analogunit);
                    $(".twoPortView").eq(i).find(".twoPortMaxValueView").val(portList[i].maxValue);
                    $(".twoPortView").eq(i).find(".twoPortMinValueView").val(portList[i].minValue);
                    $(".twoPortView").eq(i).find(".twoPortreserveView").val(portList[i].reserve);
                }else if(portList[i].classCode == 7){
                    $(".twoPortView").eq(i).find(".twoPortOdetailIDView").val(portList[i].id);
                    $(".twoPortView").eq(i).find(".twoPortValueView").val(portList[i].ioport);
                    $(".twoPortView").eq(i).find(".twoPortKValueView").val(portList[i].analogk);
                    $(".twoPortView").eq(i).find(".twoPortBValueView").val(portList[i].analogb);
                    $(".twoPortView").eq(i).find(".twoPortTopValueView").val(portList[i].analogup);
                    $(".twoPortView").eq(i).find(".twoPortLowValueView").val(portList[i].analogdown);
                    $(".twoPortView").eq(i).find(".twoPortMNUnitView").val(portList[i].analogunit);
                    $(".twoPortView").eq(i).find(".twoPortMaxValueView").val(portList[i].maxValue);
                    $(".twoPortView").eq(i).find(".twoPortMinValueView").val(portList[i].minValue);
                    $(".twoPortView").eq(i).find(".twoPortreserveView").val(portList[i].reserve);
                }else{
                    $("#OdetailIDView").val(portList[i].id);
                    $("#PortView").val(portList[i].ioport);
                    $("#KValueView").val(portList[i].analogk);
                    $("#BValueView").val(portList[i].analogb);
                    $("#TopValueView").val(portList[i].analogup);
                    $("#LowValueView").val(portList[i].analogdown);
                    $("#MaxValueView").val(portList[i].maxValue);
                    $("#MinValueView").val(portList[i].minValue);
                    $("#MNUnitView").val(portList[i].analogunit);
                    $("#reserveView").val(portList[i].reserve);
                }
			} else if (portList.length == 3&&portList[i].eqSystemID != '2') {
				$("#portNameView" + i).val(portList[i].detialname);
				$("#TdetailIDView" + i).val(portList[i].id);
				$("#TPortView" + i).val(portList[i].ioport);
				$("#KValueView" + i).val(portList[i].analogk);
				$("#BValueView" + i).val(portList[i].analogb);
				$("#TopValueView" + i).val(portList[i].analogup);
				$("#LowValueView" + i).val(portList[i].analogdown);
				$("#MaxValueView"+i).val(portList[i].maxValue);
				$("#MinValueView"+i).val(portList[i].minValue);
				$("#MNUnitView" + i).val(portList[i].analogunit);
				$("#reserveView" + i).val(portList[i].reserve);
			} else if(portList[i].eqSystemID == 5) {
				$("#OdetailIDView").val(portList[i].id);
				$("#PortView").val(portList[i].ioport);
				$("#KValueView").val(portList[i].analogk);
				$("#BValueView").val(portList[i].analogb);
				$("#TopValueView").val(portList[i].analogup);
				$("#LowValueView").val(portList[i].analogdown);
				$("#MaxValueView").val(portList[i].maxValue);
				$("#MinValueView").val(portList[i].minValue);
				$("#MNUnitView").val(portList[i].analogunit);
				$("#reserveView").val(portList[i].reserve);
			} else if (portList[i].eqSystemID == '2') {
				if (portList[i].detialname == "IA") {
					$("#fireNameView0").val(portList[i].detialname);
					$("#firedetailIDView0").val(portList[i].id);
					$("#firePortView0").val(portList[i].ioport);
					$("#fireKValueView0").val(portList[i].analogk);
					$("#fireBValueView0").val(portList[i].analogb);
					$("#fireTopValueView0").val(portList[i].analogup);
					$("#fireLowValueView0").val(portList[i].analogdown);
					$("#fireMaxValueView0").val(portList[i].maxValue);
					$("#fireMinValueView0").val(portList[i].minValue);
					$("#fireMNUnitView0").val(portList[i].analogunit);
					$("#fireReserveView0").val(portList[i].reserve);
				} else if (portList[i].detialname == "IB") {
					$("#fireNameView1").val(portList[i].detialname);
					$("#firedetailIDView1").val(portList[i].id);
					$("#firePortView1").val(portList[i].ioport);
					$("#fireKValueView1").val(portList[i].analogk);
					$("#fireBValueView1").val(portList[i].analogb);
					$("#fireTopValueView1").val(portList[i].analogup);
					$("#fireLowValueView1").val(portList[i].analogdown);
					$("#fireMaxValueView1").val(portList[i].maxValue);
					$("#fireMinValueView1").val(portList[i].minValue);
					$("#fireMNUnitView1").val(portList[i].analogunit);
					$("#fireReserveView1").val(portList[i].reserve);
				} else if (portList[i].detialname == "IC") {
					$("#fireNameView2").val(portList[i].detialname);
					$("#firedetailIDView2").val(portList[i].id);
					$("#firePortView2").val(portList[i].ioport);
					$("#fireKValueView2").val(portList[i].analogk);
					$("#fireBValueView2").val(portList[i].analogb);
					$("#fireTopValueView2").val(portList[i].analogup);
					$("#fireLowValueView2").val(portList[i].analogdown);
					$("#fireMaxValueView2").val(portList[i].maxValue);
					$("#fireMinValueView2").val(portList[i].minValue);
					$("#fireMNUnitView2").val(portList[i].analogunit);
					$("#fireReserveView2").val(portList[i].reserve);
				} else if (portList[i].detialname == "UA") {
					$("#fireNameView3").val(portList[i].detialname);
					$("#firedetailIDView3").val(portList[i].id);
					$("#firePortView3").val(portList[i].ioport);
					$("#fireKValueView3").val(portList[i].analogk);
					$("#fireBValueView3").val(portList[i].analogb);
					$("#fireTopValueView3").val(portList[i].analogup);
					$("#fireLowValueView3").val(portList[i].analogdown);
					$("#fireMaxValueView3").val(portList[i].maxValue);
					$("#fireMinValueView3").val(portList[i].minValue);
					$("#fireMNUnitView3").val(portList[i].analogunit);
					$("#fireReserveView3").val(portList[i].reserve);
				} else if (portList[i].detialname == "UB") {
					$("#fireNameView4").val(portList[i].detialname);
					$("#firedetailIDView4").val(portList[i].id);
					$("#firePortView4").val(portList[i].ioport);
					$("#fireKValueView4").val(portList[i].analogk);
					$("#fireBValueView4").val(portList[i].analogb);
					$("#fireTopValueView4").val(portList[i].analogup);
					$("#fireLowValueView4").val(portList[i].analogdown);
					$("#fireMaxValueView4").val(portList[i].maxValue);
					$("#fireMinValueView4").val(portList[i].minValue);
					$("#fireMNUnitView4").val(portList[i].analogunit);
					$("#fireReserveView4").val(portList[i].reserve);
				} else if (portList[i].detialname == "UC") {
					$("#fireNameView5").val(portList[i].detialname);
					$("#firedetailIDView5").val(portList[i].id);
					$("#firePortView5").val(portList[i].ioport);
					$("#fireKValueView5").val(portList[i].analogk);
					$("#fireBValueView5").val(portList[i].analogb);
					$("#fireTopValueView5").val(portList[i].analogup);
					$("#fireLowValueView5").val(portList[i].analogdown);
					$("#fireMaxValueView5").val(portList[i].maxValue);
					$("#fireMinValueView5").val(portList[i].minValue);
					$("#fireMNUnitView5").val(portList[i].analogunit);
					$("#fireReserveView5").val(portList[i].reserve);
				} else if (portList[i].detialname == "WA") {
					$("#fireNameView6").val(portList[i].detialname);
					$("#firedetailIDView6").val(portList[i].id);
					$("#firePortView6").val(portList[i].ioport);
					$("#fireKValueView6").val(portList[i].analogk);
					$("#fireBValueView6").val(portList[i].analogb);
					$("#fireTopValueView6").val(portList[i].analogup);
					$("#fireLowValueView6").val(portList[i].analogdown);
					$("#fireMaxValueView6").val(portList[i].maxValue);
					$("#fireMinValueView6").val(portList[i].minValue);
					$("#fireMNUnitView6").val(portList[i].analogunit);
					$("#fireReserveView6").val(portList[i].reserve);
				} else if (portList[i].detialname == "WB") {
					$("#fireNameView7").val(portList[i].detialname);
					$("#firedetailIDView7").val(portList[i].id);
					$("#firePortView7").val(portList[i].ioport);
					$("#fireKValueView7").val(portList[i].analogk);
					$("#fireBValueView7").val(portList[i].analogb);
					$("#fireTopValueView7").val(portList[i].analogup);
					$("#fireLowValueView7").val(portList[i].analogdown);
					$("#fireMaxValueView7").val(portList[i].maxValue);
					$("#fireMinValueView7").val(portList[i].minValue);
					$("#fireMNUnitView7").val(portList[i].analogunit);
					$("#fireReserveView7").val(portList[i].reserve);
				} else if (portList[i].detialname == "WC") {
					$("#fireNameView8").val(portList[i].detialname);
					$("#firedetailIDView8").val(portList[i].id);
					$("#firePortView8").val(portList[i].ioport);
					$("#fireKValueView8").val(portList[i].analogk);
					$("#fireBValueView8").val(portList[i].analogb);
					$("#fireTopValueView8").val(portList[i].analogup);
					$("#fireLowValueView8").val(portList[i].analogdown);
					$("#fireMaxValueView8").val(portList[i].maxValue);
					$("#fireMinValueView8").val(portList[i].minValue);
					$("#fireMNUnitView8").val(portList[i].analogunit);
					$("#fireReserveView8").val(portList[i].reserve);
				} else if (portList[i].detialname == "WD") {
					//漏电流
					$("#fireNameView9").val(portList[i].detialname);
					$("#firedetailIDView9").val(portList[i].id);
					$("#firePortView9").val(portList[i].ioport);
					$("#fireKValueView9").val(portList[i].analogk);
					$("#fireBValueView9").val(portList[i].analogb);
					$("#fireTopValueView9").val(portList[i].analogup);
					$("#fireLowValueView9").val(portList[i].analogdown);
					$("#fireMaxValueView9").val(portList[i].maxValue);
					$("#fireMinValueView9").val(portList[i].minValue);
					$("#fireMNUnitView9").val(portList[i].analogunit);
					$("#fireReserveView9").val(portList[i].reserve);
				} else if (portList[i].detialname == "漏电流") {
					$("#fireNameView10").val(portList[i].detialname);
					$("#firedetailIDView10").val(portList[i].id);
					$("#firePortView10").val(portList[i].ioport);
					$("#fireKValueView10").val(portList[i].analogk);
					$("#fireBValueView10").val(portList[i].analogb);
					$("#fireTopValueView10").val(portList[i].analogup);
					$("#fireLowValueView10").val(portList[i].analogdown);
					$("#fireMaxValueView10").val(portList[i].maxValue);
					$("#fireMinValueView10").val(portList[i].minValue);
					$("#fireMNUnitView10").val(portList[i].analogunit);
					$("#fireReserveView10").val(portList[i].reserve);
				}
			}

		}
	}
}

function showOtherList() {// 模拟量
	for (var i = 0; i < portList.length; i++) {
		if (portList[i].iotype == 1) {
			if (portList[i].eqSystemID == '1') {
				if(portList[i].classCode == 2){
                    $(".twoPort").eq(i).find(".twoPortOdetailID").val(portList[i].id);
                    $(".twoPort").eq(i).find(".twoPortValue").val(portList[i].ioport);
                    $(".twoPort").eq(i).find(".twoPortKValue").val(portList[i].analogk);
                    $(".twoPort").eq(i).find(".twoPortBValue").val(portList[i].analogb);
                    $(".twoPort").eq(i).find(".twoPortTopValue").val(portList[i].analogup);
                    $(".twoPort").eq(i).find(".twoPortLowValue").val(portList[i].analogdown);
                    $(".twoPort").eq(i).find(".twoPortMNUnit").val(portList[i].analogunit);
                    $(".twoPort").eq(i).find(".twoPortMaxValue").val(portList[i].maxValue);
                    $(".twoPort").eq(i).find(".twoPortMinValue").val(portList[i].minValue);
                    $(".twoPort").eq(i).find(".twoPortreserve").val(portList[i].reserve);
                }else if(portList[i].classCode == 3){
                    $(".twoPort").eq(i).find(".twoPortOdetailID").val(portList[i].id);
                    $(".twoPort").eq(i).find(".twoPortValue").val(portList[i].ioport);
                    $(".twoPort").eq(i).find(".twoPortKValue").val(portList[i].analogk);
                    $(".twoPort").eq(i).find(".twoPortBValue").val(portList[i].analogb);
                    $(".twoPort").eq(i).find(".twoPortTopValue").val(portList[i].analogup);
                    $(".twoPort").eq(i).find(".twoPortLowValue").val(portList[i].analogdown);
                    $(".twoPort").eq(i).find(".twoPortMNUnit").val(portList[i].analogunit);
                    $(".twoPort").eq(i).find(".twoPortMaxValue").val(portList[i].maxValue);
                    $(".twoPort").eq(i).find(".twoPortMinValue").val(portList[i].minValue);
                    $(".twoPort").eq(i).find(".twoPortreserve").val(portList[i].reserve);
                }else if(portList[i].classCode == 7){
                    $(".twoPort").eq(i).find(".twoPortOdetailID").val(portList[i].id);
					$(".twoPort").eq(i).find(".twoPortValue").val(portList[i].ioport);
					$(".twoPort").eq(i).find(".twoPortKValue").val(portList[i].analogk);
					$(".twoPort").eq(i).find(".twoPortBValue").val(portList[i].analogb);
					$(".twoPort").eq(i).find(".twoPortTopValue").val(portList[i].analogup);
					$(".twoPort").eq(i).find(".twoPortLowValue").val(portList[i].analogdown);
					$(".twoPort").eq(i).find(".twoPortMNUnit").val(portList[i].analogunit);
					$(".twoPort").eq(i).find(".twoPortMaxValue").val(portList[i].maxValue);
					$(".twoPort").eq(i).find(".twoPortMinValue").val(portList[i].minValue);
					$(".twoPort").eq(i).find(".twoPortreserve").val(portList[i].reserve);
				}else{
					$("#OdetailID").val(portList[i].id);
					$("#Port").val(portList[i].ioport);
					$("#KValue").val(portList[i].analogk);
					$("#BValue").val(portList[i].analogb);
					$("#TopValue").val(portList[i].analogup);
					$("#LowValue").val(portList[i].analogdown);
					$("#MaxValue").val(portList[i].maxValue);
					$("#MinValue").val(portList[i].minValue);
					$("#MNUnit").val(portList[i].analogunit);
					$("#reserve").val(portList[i].reserve);
				}
			} else if (portList.length == 3&&portList[i].eqSystemID != 2) {
				$("#portName" + i).val(portList[i].detialname);
				$("#TdetailID" + i).val(portList[i].id);
				$("#TPort" + i).val(portList[i].ioport);
				$("#KValue" + i).val(portList[i].analogk);
				$("#BValue" + i).val(portList[i].analogb);
				$("#TopValue" + i).val(portList[i].analogup);
				$("#LowValue" + i).val(portList[i].analogdown);
				$("#MaxValue"+i).val(portList[i].maxValue);
				$("#MinValue"+i).val(portList[i].minValue);
				$("#MNUnit" + i).val(portList[i].analogunit);
				$("#reserve" + i).val(portList[i].reserve);
			} else if(portList[i].eqSystemID == 5){
				$("#OdetailID").val(portList[i].id);
				$("#Port").val(portList[i].ioport);
				$("#KValue").val(portList[i].analogk);
				$("#BValue").val(portList[i].analogb);
				$("#TopValue").val(portList[i].analogup);
				$("#LowValue").val(portList[i].analogdown);
				$("#MaxValue").val(portList[i].maxValue);
				$("#MinValue").val(portList[i].minValue);
				$("#MNUnit").val(portList[i].analogunit);
				$("#reserve").val(portList[i].reserve);
			}
			else if (portList[i].eqSystemID == 2) {
				if (portList[i].detialname == "IA") {
					$("#fireName0").val(portList[i].detialname);
					$("#firedetailID0").val(portList[i].id);
					$("#firePort0").val(portList[i].ioport);
					$("#fireKValue0").val(portList[i].analogk);
					$("#fireBValue0").val(portList[i].analogb);
					$("#fireTopValue0").val(portList[i].analogup);
					$("#fireLowValue0").val(portList[i].analogdown);
					$("#fireMaxValue0").val(portList[i].maxValue);
					$("#fireMinValue0").val(portList[i].minValue);
					$("#fireMNUnit0").val(portList[i].analogunit);
					$("#fireReserve0").val(portList[i].reserve);
				} else if (portList[i].detialname == "IB") {
					$("#fireName1").val(portList[i].detialname);
					$("#firedetailID1").val(portList[i].id);
					$("#firePort1").val(portList[i].ioport);
					$("#fireKValue1").val(portList[i].analogk);
					$("#fireBValue1").val(portList[i].analogb);
					$("#fireTopValue1").val(portList[i].analogup);
					$("#fireLowValue1").val(portList[i].analogdown);
					$("#fireMaxValue1").val(portList[i].maxValue);
					$("#fireMinValue1").val(portList[i].minValue);
					$("#fireMNUnit1").val(portList[i].analogunit);
					$("#fireReserve1").val(portList[i].reserve);
				} else if (portList[i].detialname == "IC") {
					$("#fireName2").val(portList[i].detialname);
					$("#firedetailID2").val(portList[i].id);
					$("#firePort2").val(portList[i].ioport);
					$("#fireKValue2").val(portList[i].analogk);
					$("#fireBValue2").val(portList[i].analogb);
					$("#fireTopValue2").val(portList[i].analogup);
					$("#fireLowValue2").val(portList[i].analogdown);
					$("#fireMaxValue2").val(portList[i].maxValue);
					$("#fireMinValue2").val(portList[i].minValue);
					$("#fireMNUnit2").val(portList[i].analogunit);
					$("#fireReserve2").val(portList[i].reserve);
				} else if (portList[i].detialname == "UA") {
					$("#fireName3").val(portList[i].detialname);
					$("#firedetailID3").val(portList[i].id);
					$("#firePort3").val(portList[i].ioport);
					$("#fireKValue3").val(portList[i].analogk);
					$("#fireBValue3").val(portList[i].analogb);
					$("#fireTopValue3").val(portList[i].analogup);
					$("#fireLowValue3").val(portList[i].analogdown);
					$("#fireMaxValue3").val(portList[i].maxValue);
					$("#fireMinValue3").val(portList[i].minValue);
					$("#fireMNUnit3").val(portList[i].analogunit);
					$("#fireReserve3").val(portList[i].reserve);
				} else if (portList[i].detialname == "UB") {
					$("#fireName4").val(portList[i].detialname);
					$("#firedetailID4").val(portList[i].id);
					$("#firePort4").val(portList[i].ioport);
					$("#fireKValue4").val(portList[i].analogk);
					$("#fireBValue4").val(portList[i].analogb);
					$("#fireTopValue4").val(portList[i].analogup);
					$("#fireLowValue4").val(portList[i].analogdown);
					$("#fireMaxValue4").val(portList[i].maxValue);
					$("#fireMinValue4").val(portList[i].minValue);
					$("#fireMNUnit4").val(portList[i].analogunit);
					$("#fireReserve4").val(portList[i].reserve);
				} else if (portList[i].detialname == "UC") {
					$("#fireName5").val(portList[i].detialname);
					$("#firedetailID5").val(portList[i].id);
					$("#firePort5").val(portList[i].ioport);
					$("#fireKValue5").val(portList[i].analogk);
					$("#fireBValue5").val(portList[i].analogb);
					$("#fireTopValue5").val(portList[i].analogup);
					$("#fireLowValue5").val(portList[i].analogdown);
					$("#fireMaxValue5").val(portList[i].maxValue);
					$("#fireMinValue5").val(portList[i].minValue);
					$("#fireMNUnit5").val(portList[i].analogunit);
					$("#fireReserve5").val(portList[i].reserve);
				} else if (portList[i].detialname == "WA") {
					$("#fireName6").val(portList[i].detialname);
					$("#firedetailID6").val(portList[i].id);
					$("#firePort6").val(portList[i].ioport);
					$("#fireKValue6").val(portList[i].analogk);
					$("#fireBValue6").val(portList[i].analogb);
					$("#fireTopValue6").val(portList[i].analogup);
					$("#fireLowValue6").val(portList[i].analogdown);
					$("#fireMaxValue6").val(portList[i].maxValue);
					$("#fireMinValue6").val(portList[i].minValue);
					$("#fireMNUnit6").val(portList[i].analogunit);
					$("#fireReserve6").val(portList[i].reserve);
				} else if (portList[i].detialname == "WB") {
					$("#fireName7").val(portList[i].detialname);
					$("#firedetailID7").val(portList[i].id);
					$("#firePort7").val(portList[i].ioport);
					$("#fireKValue7").val(portList[i].analogk);
					$("#fireBValue7").val(portList[i].analogb);
					$("#fireTopValue7").val(portList[i].analogup);
					$("#fireLowValue7").val(portList[i].analogdown);
					$("#fireMaxValue7").val(portList[i].maxValue);
					$("#fireMinValue7").val(portList[i].minValue);
					$("#fireMNUnit7").val(portList[i].analogunit);
					$("#fireReserve7").val(portList[i].reserve);
				} else if (portList[i].detialname == "WC") {
					$("#fireName8").val(portList[i].detialname);
					$("#firedetailID8").val(portList[i].id);
					$("#firePort8").val(portList[i].ioport);
					$("#fireKValue8").val(portList[i].analogk);
					$("#fireBValue8").val(portList[i].analogb);
					$("#fireTopValue8").val(portList[i].analogup);
					$("#fireLowValue8").val(portList[i].analogdown);
					$("#fireMaxValue8").val(portList[i].maxValue);
					$("#fireMinValue8").val(portList[i].minValue);
					$("#fireMNUnit8").val(portList[i].analogunit);
					$("#fireReserve8").val(portList[i].reserve);
				} else if (portList[i].detialname == "WD") {
					//漏电流
					$("#fireName9").val(portList[i].detialname);
					$("#firedetailID9").val(portList[i].id);
					$("#firePort9").val(portList[i].ioport);
					$("#fireKValue9").val(portList[i].analogk);
					$("#fireBValue9").val(portList[i].analogb);
					$("#fireTopValue9").val(portList[i].analogup);
					$("#fireLowValue9").val(portList[i].analogdown);
					$("#fireMaxValue9").val(portList[i].maxValue);
					$("#fireMinValue9").val(portList[i].minValue);
					$("#fireMNUnit9").val(portList[i].analogunit);
					$("#fireReserve9").val(portList[i].reserve);
				} else if (portList[i].detialname == "漏电流") {
					$("#fireName10").val(portList[i].detialname);
					$("#firedetailID10").val(portList[i].id);
					$("#firePort10").val(portList[i].ioport);
					$("#fireKValue10").val(portList[i].analogk);
					$("#fireBValue10").val(portList[i].analogb);
					$("#fireTopValue10").val(portList[i].analogup);
					$("#fireLowValue10").val(portList[i].analogdown);
					$("#fireMaxValue10").val(portList[i].maxValue);
					$("#fireMinValue10").val(portList[i].minValue);
					$("#fireMNUnit10").val(portList[i].analogunit);
					$("#fireReserve10").val(portList[i].reserve);
				}

			}

		}
	}
}

function showPortListView() {// 查看数字量
	var j = 0;
	var x = 4; //用来判断泵控制柜中是否是新增的字段
	// 为加载好的标签填充值
    var y = 3;  //用来判断防排烟系统中是否是新增的字段
	var flag1 = true;		//新增自定义的端口时，防止覆盖到原端口
	var flag2 = true;		//新增自定义的端口时，防止覆盖到原端口
	var flag3 = true;		//新增自定义的端口时，防止覆盖到原端口
	var flag4 = true;		//新增自定义的端口时，防止覆盖到原端口
	for (var i = 0; i < portList.length; i++) {
		if (portList[i].iotype == 2) {
//			if (portList.length == 1) {// 故障一个端口
//				$("#FDetailIDView").val(portList[i].id);
//				$("#FNameView").val(portList[i].detialname);
//				$("#FPortView").val(portList[i].ioport);
//				$("#FTypeView").val(portList[i].iotype);
//				$("#FValueView").val(portList[i].digitalnormal);
//				$("#FOneView").val(portList[i].goodname);
//				$("#FZeroView").val(portList[i].badname);
//				$("#ShReserveView").val(portList[i].reserve);
//			} else
			if(portList.length == 3&&portList[i].eqSystemID ==4){// 三个端口
				var detialname = portList[i].detialname;
				if(detialname == "电源"){
					j = 0;
				}else if(detialname == "半降"){
					j = 1;
				}else if(detialname == "全降"){
					j = 2;
				}else if(detialname == "状态"){
					$("#FPortView").val(portList[i].ioport);
					$("#FValueView").val(portList[i].digitalnormal);
					$("#FOneView").val(portList[i].goodname);
					$("#FZeroView").val(portList[i].badname);
					$("#ShReserveView").val(portList[i].reserve);
					$("#FDetailIDView").val(portList[i].id);
				}
				$("#BDetailIDView" + j).val(portList[i].id);
				$("#PortView" + j).val(portList[i].ioport);
				$("#TypeView" + j).val(portList[i].iotype);
				$("#ValueView" + j).val(portList[i].digitalnormal);
				$("#OneView" + j).val(portList[i].goodname);
				$("#ZeroView" + j).val(portList[i].badname);
				$("#ShReserveView"+j).val(portList[i].reserve);
			}else if(portList[i].eqSystemID ==5 && portList[i].detialname == "称重"){
				$("#FPortView").val(portList[i].ioport);
				$("#FValueView").val(portList[i].digitalnormal);
				$("#FOneView").val(portList[i].goodname);
				$("#FZeroView").val(portList[i].badname);
				$("#ShReserveView").val(portList[i].reserve);
				$("#FDetailIDView").val(portList[i].id);
			}else if(portList[i].eqSystemID ==9){
                var detialname = portList[i].detialname;
                if (flag1 && detialname == "手自动") {
                    j = 0;
                    flag1 = false;
                } else if (flag2 && detialname == "启停") {
                    j = 1;
                    flag2 = false;
                } else if (flag3 && detialname == "电源") {
                    j = 2;
                    flag3 = false;
                }else{
                    j = y;
                    y++;
                }
                if(j>2){
                    $(".smokeControlPortView").eq(j).find(".PortId").val(portList[i].detialname);
                }
                $(".smokeControlPortView").eq(j).attr('id',portList[i].id);
                $(".smokeControlPortView").eq(j).find(".PortIdDetailId").val(portList[i].id);
                $(".smokeControlPortView").eq(j).find(".PortIdValue").val(portList[i].ioport);
                $(".smokeControlPortView").eq(j).find(".PortIdType").val(portList[i].iotype);
                $(".smokeControlPortView").eq(j).find(".PortIdNormalNum").val(portList[i].digitalnormal);
                $(".smokeControlPortView").eq(j).find(".PortIdNormalName").val(portList[i].goodname);
                $(".smokeControlPortView").eq(j).find(".PortIdAbnormalName").val(portList[i].badname);
                $(".smokeControlPortView").eq(j).find(".PortIdSignal").val(portList[i].reserve);
            } else{
				var detialname = portList[i].detialname;
				if(flag1 && detialname == "启停") {
					j = 1;
					flag1 = false;
				}else if(flag2 && detialname == "电源") {
					j = 2;
					flag2 = false;
				}else if(flag3 && detialname == "手自动") {
					j = 0;
					flag3 = false;
				}else if(flag4 && detialname == "故障") {
					j = 3;
					flag4 = false;
				}else {
					j = x;
					x++;
				}

				if(j>3){
					$(".pumpPortView").eq(j).find(".PortId").val(portList[i].detialname);
				}
				$(".pumpPortView").eq(j).find(".PortIdDetailId").val(portList[i].id);
				$(".pumpPortView").eq(j).find(".PortIdValue").val(portList[i].ioport);
				$(".pumpPortView").eq(j).find(".PortIdType").val(portList[i].iotype);
				$(".pumpPortView").eq(j).find(".PortIdNormalNum").val(portList[i].digitalnormal);
				$(".pumpPortView").eq(j).find(".PortIdNormalName").val(portList[i].goodname);
				$(".pumpPortView").eq(j).find(".PortIdAbnormalName").val(portList[i].badname);
				$(".pumpPortView").eq(j).find(".PortIdSignal").val(portList[i].reserve);


				// $("#FBDetailIDView" + j).val(portList[i].id);
				// $("#FPortView" + j).val(portList[i].ioport);
				// $("#FTypeView" + j).val(portList[i].iotype);
				// $("#FValueView" + j).val(portList[i].digitalnormal);
				// $("#FOneView" + j).val(portList[i].goodname);
				// $("#FZeroView" + j).val(portList[i].badname);
				// $("#FShReserveView"+j).val(portList[i].reserve);
			}
		}
	}
}

function showPortList() {// 数字量

	// 为加载好的标签填充值
	var j=0;
	var x=4;   //用来判断泵控制柜中是否是新增的字段
	var y=3;   //用来判断防排烟系统中是否是新增的字段
	var flag1 = true;		//新增自定义的端口，防止覆盖到原端口
	var flag2 = true;		//新增自定义的端口，防止覆盖到原端口
	var flag3 = true;		//新增自定义的端口，防止覆盖到原端口
	var flag4 = true;		//新增自定义的端口，防止覆盖到原端口
	for (var i = 0; i < portList.length; i++) {
		if (portList[i].iotype == 2) {
			var detialname = portList[i].detialname;
//			if (portList.length == 1&&portList[i].eqSystemID ==4) {// 故障一个端口
//				$("#FDetailID").val(portList[i].id);
//				$("#FName").val(portList[i].detialname);
//				$("#FPort").val(portList[i].ioport);
//				$("#FType").val(portList[i].iotype);
//				$("#FValue").val(portList[i].digitalnormal);
//				$("#FOne").val(portList[i].goodname);
//				$("#FZero").val(portList[i].badname);
//				$("#ShReserve").val(portList[i].reserve);
//			}else
			if(portList[i].eqSystemID ==4){
				if(detialname == "电源"){
					j = 0;
				}else if(detialname == "半降"){
					j = 1;
				}else if(detialname == "全降"){
					j = 2;
				}else if(detialname == "状态"){
					$("#FPort").val(portList[i].ioport);
					$("#FValue").val(portList[i].digitalnormal);
					$("#FOne").val(portList[i].goodname);
					$("#FZero").val(portList[i].badname);
					$("#ShReserve").val(portList[i].reserve);
					$("#FDetailID").val(portList[i].id);
				}
				$("#BDetailID" + j).val(portList[i].id);
				$("#Port" + j).val(portList[i].ioport);
				$("#Type" + j).val(portList[i].iotype);
				$("#Value" + j).val(portList[i].digitalnormal);
				$("#One" + j).val(portList[i].goodname);
				$("#Zero" + j).val(portList[i].badname);
				$("#ShReserve"+j).val(portList[i].reserve);
			}else if(portList[i].eqSystemID ==5 && portList[i].detialname == "称重") {
				$("#FPort").val(portList[i].ioport);
				$("#FValue").val(portList[i].digitalnormal);
				$("#FOne").val(portList[i].goodname);
				$("#FZero").val(portList[i].badname);
				$("#ShReserve").val(portList[i].reserve);
				$("#FDetailID").val(portList[i].id);
			}else if(portList[i].eqSystemID == 9) {
                var detialname = portList[i].detialname;
                if (flag1 && detialname == "手自动") {
					j = 0;
					flag1 = false;
				} else if (flag2 && detialname == "启停") {
					j = 1;
					flag2 = false;
				} else if (flag3 && detialname == "电源") {
					j = 2;
					flag3 = false;
				}else{
					j = y;
					y++;
				}
				if(j>2){
					$(".smokeControlPort").eq(j).find(".PortId").val(portList[i].detialname);
				}
				$(".smokeControlPort").eq(j).attr('id',portList[i].id);
				$(".smokeControlPort").eq(j).find(".PortIdDetailId").val(portList[i].id);
				$(".smokeControlPort").eq(j).find(".PortIdValue").val(portList[i].ioport);
				$(".smokeControlPort").eq(j).find(".PortIdType").val(portList[i].iotype);
				$(".smokeControlPort").eq(j).find(".PortIdNormalNum").val(portList[i].digitalnormal);
				$(".smokeControlPort").eq(j).find(".PortIdNormalName").val(portList[i].goodname);
				$(".smokeControlPort").eq(j).find(".PortIdAbnormalName").val(portList[i].badname);
				$(".smokeControlPort").eq(j).find(".PortIdSignal").val(portList[i].reserve);
			} else {// 泵控制柜
				var detialname = portList[i].detialname;
				if(flag1 && detialname == "启停") {
					j = 1;
					flag1 = false;
				}else if(flag2 && detialname == "电源") {
					j = 2;
					flag2 = false;
				}else if(flag3 && detialname == "手自动") {
					j = 0;
					flag3 = false;
				}else if(flag4 && detialname == "故障") {
					j = 3;
					flag4 = false;
				}else {
					j = x;
					x++;
				}

				if(j>3){
					$(".pumpPort").eq(j).find(".PortId").val(portList[i].detialname);
				}
				$(".pumpPort").eq(j).attr('id',portList[i].id);
				$(".pumpPort").eq(j).find(".PortIdDetailId").val(portList[i].id);
				$(".pumpPort").eq(j).find(".PortIdValue").val(portList[i].ioport);
				$(".pumpPort").eq(j).find(".PortIdType").val(portList[i].iotype);
				$(".pumpPort").eq(j).find(".PortIdNormalNum").val(portList[i].digitalnormal);
				$(".pumpPort").eq(j).find(".PortIdNormalName").val(portList[i].goodname);
				$(".pumpPort").eq(j).find(".PortIdAbnormalName").val(portList[i].badname);
				$(".pumpPort").eq(j).find(".PortIdSignal").val(portList[i].reserve);
				// $("#FBDetailID" + j).val(portList[i].id);
				// $("#FPort" + j).val(portList[i].ioport);
				// $("#FType" + j).val(portList[i].iotype);
				// $("#FValue" + j).val(portList[i].digitalnormal);
				// $("#FOne" + j).val(portList[i].goodname);
				// $("#FZero" + j).val(portList[i].badname);
				// $("#FShReserve"+j).val(portList[i].reserve);

			}
		}
	}


	// for(var i=0;i<4;i++){
	// 	oldNameArray.push($('.pumpPort').eq(i).find('.PortIdValue').val());
	// }
	// console.log(oldNameArray,'oldNameArray');
}
// 删除设备
function deleteVideo(_this) {
	var indexId = _this.id;
	var url = baseUrl + '/equipmentFac/remove';
	layer.open({
		content : "是否确定删除？",
		btn : [ "确认", "取消" ],
		yes : function(index, layero) {
			$.ajax({
				type : "post",
				async : true,
				beforeSend: function(request){
					request.setRequestHeader("Authorization","Bearer "+login.token);
				},
				data : {
					"id" : indexId
				},
				url : url,
				dataType : "json",
				success : function(d) {
					if (d.success) {
						$('#equipmentTable').bootstrapTable("refresh");
						layer.msg("删除成功！");
					} else {
						layer.msg(d.msg);
					}
				},
				error : function(e) {

				}
			});
		},
		no : function(index, layero) {
		}
	});
}
// 将返回数据转换成数组
function convert(result) {
	var nodes = [];
	for ( var i in result) {
		nodes.push(result[i]);
	}
	doInitSecondSource(nodes);
}
// 给组织添加点击事件
function doInitSecondSource(nodes) {
	var setting = {
		view : {
			dblClickExpand : true
			// 屏蔽掉双击事件
		},
		check : {
			enable : true,
			chkStyle : "checkbox"
		},
		data : {
			key : {
				url : "",
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : 'pid',
				rootPId : null
			}
		},
		callback : {
			onCheck : onCheck
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
	$('#equipmentTable')
		.bootstrapTable(
			{
				url : baseUrl + '/equipmentFac/getList', // 请求后台的URL（*）
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
						eqname : $("#EQMName").val(),
						unitName : $("#unitName").val(),
						eqclassid : $("#EqClassIDs").val(),
						eqsystemid : $("#EqSystemCodes").val(),
						netDeviceName:$("#NdName").val(),
						ownercode:$("#ownercode").val()
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
						field : 'unitName',
						title : '单位名称'
					},
					{
						field : 'name',
						title : '联网设备名称'
					},
					{
						field : 'eqname',
						title : '设备设施名称'
					},
					{
						field : 'ownercode',
						title : '设备编号'
					},
					{
						field : 'buildingname',
						title : '安装建筑'
					},
					{
						field : 'classname',
						title : '监测类型',
					},
					{
						field : 'installposition',
						title : '安装位置'
					},

					{
						field : '',
						title : '操作',
						width:'350px',
						formatter : function(value, row, index) {
							var str = "";
							str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
							str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
							str += '<button type="button" class="btn btn-new btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
							if (row.deviceindex == "1") {
								str += '<button type="button" style="margin-right: 3px" class="btn btn-new btn-xs cBtn-main export"><i class="fa fa-plus"></i>&nbsp;关联地址</button>'
							}else{
								str += '<button type="button" style="margin-right: 3px" class="btn btn-xs btn-close btnClose" disabled><i class="fa fa-plus"></i>&nbsp;关联地址</button>'
							}
							if(row.deviceindex==3){
								str += '<button type="button" class="btn btn-primary btn-xs cBtn-main interfaceC"><i class="fa fa-pencil"></i>&nbsp;控制接口</button>'
							}else{
								str += '<button type="button" disabled="disabled" class="btn btn-primary btn-xs cBtn-main"><i class="fa fa-pencil"></i>&nbsp;控制接口</button>'
							}
							return str;
						},
						events : {
							'click .edit' : function(e, value, row,
													 index) {
								editVideo(row);
							},
							'click .del' : function(e, value, row,
													index) {
								deleteVideo(row);
							},
							'click .view' : function(e, value, row,
													 index) {
								showVideo(row);
							},
							'click .export' : function(e, value,
													   row, index) {
								exportAddress(row);
							},
							'click .interfaceC': function (e, value, row, index) {
								interfaceC(row);
							}
						}
					}

				]
			});
	getUnitList();
}

function interfaceC(row){
	$("#newInterface").modal("show");
	$("#indexId").val(row.id);
	if(isHasInterfaceTable == 0){
		showInterfaceList();
	}else{
		$('#interfaceList').bootstrapTable("refresh");
	}
}

// 为报警设备导入地址
function exportAddress(_this) {
	$("#exportAddress .modal-title").html("关联地址");
	$("#exportAddress").modal();
	// $("#newnetDeviceRel-unit").val(_this.unitid);
	showUnitList(_this.unitid);
	unitID = _this.unitid;
	buildSelect();
	$("#newnetDeviceRel-unit").comboSelect();
	eqID = _this.id;
	$("#deviceId").val(_this.id);
	DeviceRelSearch(_this.id);
	// BuildAreaSelect(_this.unitid);
	pointTypeSelect();
	$("#newnetDeviceRel-partcode").val("");
	$("#newnetDeviceRel-adress").val("");
	$("#newnetDeviceRel-build").val("");
	$("#newnetDeviceRel-buildAreaid").val("");
	$("#newnetDeviceRel-pointtype").val("");
	$("#newnetDeviceRel-eqsystemid").val("");
	$("#newnetDeviceRel-xaxis").val("");
	$("#newnetDeviceRel-yaxis").val("");
	$("#newnetDeviceRel-name").val("");
	$("#newnetDeviceRel-remark").val("");
	$("#newnetDeviceRel-pointVideo").val("");
}

$("#newnetDeviceRel-unit").on("change",function () {
	$("#newnetDeviceRel-build").html("<option value=''>--请选择--</option>");
	$("#newnetDeviceRel-buildAreaid").html("<option value=''>--请选择--</option>");
	unitID = $("#newnetDeviceRel-unit").val();
	buildSelect();
	getPointVideoSelect('newnetDeviceRel-pointVideo',unitID,'');
});

$("#newnetDeviceRelEdit-unit").bind("change",function () {
	$("#newnetDeviceRelEdit-build").html("<option value=''>--请选择--</option>");
	$("#newnetDeviceRelEdit-buildAreaid").html('<option value="">--请选择--</option>');
	unitID = $("#newnetDeviceRelEdit-unit").val();
	buildEditSelect(unitID);
	getPointVideoSelect('newnetDeviceRelEdit-pointVideo',unitID,'');
});

$("#newnetDeviceRel-pointVideo").on("mousedown",function () {
	unitID = $("#newnetDeviceRel-unit").val();
	getPointVideoSelect('newnetDeviceRel-pointVideo',unitID,'');
	$("#newnetDeviceRel-pointVideo").unbind("mousedown").bind("change");
})

$("#newnetDeviceRelEdit-pointVideo").on("mousedown",function () {
    unitID = $("#newnetDeviceRelEdit-unit").val();
    getPointVideoSelect('newnetDeviceRelEdit-pointVideo',unitID,'');
    $("#newnetDeviceRelEdit-pointVideo").unbind("mousedown").bind("change");
})

// 关联地址新增建筑下拉
function buildSelect() {
    if(unitID == ""){
    	return;
	}
	$("#newnetDeviceRel-build").empty();
	$("#newnetDeviceRel-build").html("<option value=''>--请选择--</option>");
	var data = {
		UnitID : unitID
	};
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
				var content = "<option value=''>--请选择--</option>";
				for (var i = 0; i < d.obj.length; i++) {
					content += '<option value="' + d.obj[i].id + '">'
						+ d.obj[i].buildingName + '</option>';
				}
				$("#newnetDeviceRel-build").html(content);
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

$("#newnetDeviceRel-build").on("change",function () {
	$("#newnetDeviceRel-buildAreaid").empty();
	$("#newnetDeviceRel-buildAreaid").html("<option value=''>--请选择--</option>");
	buildId = $("#newnetDeviceRel-build").val();
	buildAreaSelect();
});

// 关联地址新增区域下拉
function buildAreaSelect() {
	$("#newnetDeviceRel-buildAreaid").empty();
	$("#newnetDeviceRel-buildAreaid").html("<option value=''>--请选择--</option>");
	var buildId = $("#newnetDeviceRel-build").val();
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
				var content = "<option value=''>--请选择--</option>";
				for (var i = 0; i < d.obj.length; i++) {
					content += '<option value="' + d.obj[i].id + '">'
						+ d.obj[i].buildAreaName + '</option>';
				}
				$("#newnetDeviceRel-buildAreaid").html(content);
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 点位类型下拉
function pointTypeSelect() {
	$.ajax({
		type : "post",
		async : false,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			codeGroupKey : "PointType"
		},
		url : baseUrl + "/code/getListByGroupKey",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				var content = "<option value=''>--请选择--</option>";
				for (var i = 0; i < d.obj.length; i++) {
					content += '<option value="' + d.obj[i].codeid + '">'
						+ d.obj[i].codename + '</option>';
				}
				$("#newnetDeviceRel-pointtype").html(content);
				$("#newnetDeviceRelEdit-pointtype").html(content);
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 导入弹窗按钮
function addressExport() {
	$("#eqid").val(eqID);
	$("#importBuilding").modal();
	$('.fileName').html(""); // 赋值给自定义input框

}

function doChange(file) {
	var upload_file = $(file).val(); // 获取上传文件
	$('.fileName').html(upload_file); // 赋值给自定义input框
};
// 导入按钮
$(".btnImport").click(function() {
	var formData = new FormData($("#projectForm")[0]);
	var uploadExcel = $(".uploadExcel").val();
	if (uploadExcel == "") {
		layer.msg("请选择上传文件！");
		return;
	}
	$.ajax({
		url : baseUrl + "/netDeviceRel/importExcel",
		type : "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : formData,
		dataType : "json",
		contentType : false,
		processData : false,
		success : function(data) {
			if(data.success){
				layer.msg(data.msg);
				layer.closeAll("loading");
				$("#importBuilding").modal("hide");
				$("#exportPage").modal("hide");
				$('#userTable').bootstrapTable('refresh');
			}else{
				layer.msg(data.msg);
			}
		}
	})
})
// 保存地址
function Preservation() {
	var unit = $("#newnetDeviceRel-unit").val();
	var buildid = $("#newnetDeviceRel-build").val();
	var buildAreaid = $("#newnetDeviceRel-buildAreaid").val();
	var pointtype = $("#newnetDeviceRel-pointtype").val();
	var partcode = $("#newnetDeviceRel-partcode").val();
	var address = $("#newnetDeviceRel-adress").val();
	var systemID = $("#newnetDeviceRel-eqsystemid").find("option:selected")
		.val();
	var xaxis = $("#newnetDeviceRel-xaxis").val();
	var yaxis = $("#newnetDeviceRel-yaxis").val();
	var name = $("#newnetDeviceRel-name").val();
	var remark = $("#newnetDeviceRel-remark").val();
	var videoid = $("#newnetDeviceRel-pointVideo").val();

	if (unit == "" || unit == null) {
		layer.msg("请选择单位");
		return;
	}
	if (buildid == "" || buildid == null) {
		layer.msg("请选择建筑");
		return;
	}
	if (buildAreaid == "" || buildAreaid == null) {
		layer.msg("请选择区域");
		return;
	}
	// if(pointtype == "" || pointtype == null){
	// layer.msg("请选择点位类型");
	// return ;
	// }
	if (partcode == "") {
		layer.msg("请输入部位号");
		return;
	}
	if (address == "") {
		layer.msg("请输入真实地址");
		return;
	}

	var url = baseUrl + '/netDeviceRel/add';
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			unitid : unit,
			"eqid" : eqID,
			"partcode" : partcode,
			"adress" : address,
			name : name,
			remark : remark,
			xaxis : xaxis,
			yaxis : yaxis,
			eqSystemID : systemID,
			pointtype : pointtype,
			buildAreaid : buildAreaid,
			videoid:videoid
		},
		url : url,
		dataType : "json",
		success : function(d) {
			if (d.success) {
				// $("#exportAddress .modal-title").html("关联地址");
				// $("#exportAddress").modal();
				$("#exportAddress").modal("hide");
				$('#userTable').bootstrapTable('refresh');
				layer.msg("保存成功！");
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		}
	});
}

function updateEqAddressRel(){
	var unit = $("#newnetDeviceRelEdit-unit").val();
	var buildid = $("#newnetDeviceRelEdit-build").val();
	var buildAreaid = $("#newnetDeviceRelEdit-buildAreaid").val();
	var pointtype = $("#newnetDeviceRelEdit-pointtype").val();
	var partcode = $("#newnetDeviceRelEdit-partcode").val();
	var address = $("#newnetDeviceRelEdit-adress").val();
	var systemID = $("#newnetDeviceRelEdit-eqsystemid").find("option:selected").val();
	var xaxis = $("#newnetDeviceRelEdit-xaxis").val();
	var yaxis = $("#newnetDeviceRelEdit-yaxis").val();
	var name = $("#newnetDeviceRelEdit-name").val();
	var remark = $("#newnetDeviceRelEdit-remark").val();
	var videoid = $("#newnetDeviceRelEdit-pointVideo").val();

	var id = $("#addressRelId").val();

	if (unit == "" || unit == null) {
		layer.msg("请选择单位");
		return;
	}
	if (buildid == "" || buildid == null) {
		layer.msg("请选择建筑");
		return;
	}
	if (buildAreaid == "" || buildAreaid == null) {
		layer.msg("请选择区域");
		return;
	}
	if (partcode == "") {
		layer.msg("请输入部位号");
		return;
	}
	if (address == "") {
		layer.msg("请输入真实地址");
		return;
	}

	var url = baseUrl + '/netDeviceRel/update';
	$.ajax({
		type : "post",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : {
			id:id,
			"unitid" : unit,
			"eqid" : eqID,
			"partcode" : partcode,
			"adress" : address,
			name : name,
			remark : remark,
			xaxis : xaxis,
			yaxis : yaxis,
			eqSystemID : systemID,
			pointtype : pointtype,
			buildAreaid : buildAreaid,
			videoid:videoid
		},
		url : url,
		dataType : "json",
		success : function(d) {
			if (d.success) {
				// $("#exportAddress .modal-title").html("关联地址");
				// $("#exportAddress").modal();
				$("#editAddress").modal("hide");
				$('#userTable').bootstrapTable('refresh');
				layer.msg("保存成功！");
			} else {
				layer.msg(d.msg);
			}
		},
		error : function(e) {

		}
	});
}
// 主页面查询
function DeviceRelSearch(id) {
	$("#userTable").bootstrapTable('destroy');
	$('#userTable').bootstrapTable(
		{
			url : baseUrl + '/netDeviceRel/list', // 请求后台的URL（*）
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
					eqid : id,
					unitName:$("#unitName-relAddress").val()
				};
				$("#pageSize").val(this.pageSize);
				$("#pageNumber").val(this.pageNumber);
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
			showColumns : false, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			singleSelect : false,
			// height: 570, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "id", // 每一行的唯一标识，一般为主键列
			// showToggle: true, //是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			columns : [
                {
                    field : 'selectItemMain',
                    checkbox : 'true'
                },
				{
					field : 'unitname',
					title : '单位名称'
				},
				{
					field : 'netdevicename',
					title : '联网设施名称'
				},
				{
					field : 'partcode',
					title : '部位号'
				},
				{
					field : 'adress',
					title : '真实地址'
				},
				{
					field : '',
					title : '操作',
					formatter : function(value, row, index) {
						var str = "";
						str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
						str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
						return str;
					},
					events : {
						'click .edit' : function(e, value, row,
												 index) {
							editBaseInfo(row);
						},
						'click .del' : function(e, value, row,
												index) {
							deleteBaseInfo(row);
						}
					}
				} ]
		});
}
function editBaseInfo(_this){
	$("#editAddress .modal-title").html("关联地址");
	$("#editAddress").modal();
	$("#addressRelId").val(_this.id);
    showUnitList(_this.unitid);
    $("#newnetDeviceRelEdit-unit").val(_this.unitid);
    $("#newnetDeviceRelEdit-unit").comboSelect();
    unitID = _this.unitid;
	buildEditSelect(unitID);
	$("#newnetDeviceRelEdit-build").val(_this.buildId);

	buildAreaEditSelect();

	$("#newnetDeviceRelEdit-buildAreaid").val(_this.buildareaid);
	$("#newnetDeviceRelEdit-pointtype").val(_this.pointtype);
	$("#newnetDeviceRelEdit-eqsystemid").val(_this.eqsystemid);
	$("#newnetDeviceRelEdit-pointVideo").val(_this.videoid);
	$("#newnetDeviceRelEdit-partcode").val(_this.partcode);
	$("#newnetDeviceRelEdit-adress").val(_this.adress);
	$("#newnetDeviceRelEdit-name").val(_this.name);
	$("#newnetDeviceRelEdit-xaxis").val(_this.xaxis);
	$("#newnetDeviceRelEdit-yaxis").val(_this.yaxis);
	$("#newnetDeviceRelEdit-remark").val(_this.remark);
}

// 关联地址编辑建筑下拉
function buildEditSelect(unitID) {
	$("#newnetDeviceRelEdit-build").empty();
	$("#newnetDeviceRelEdit-build").html("<option value=''>--请选择--</option>");
	var data = {
		UnitID : unitID
	};
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
				var content = "<option value=''>--请选择--</option>";
				for (var i = 0; i < d.obj.length; i++) {
					content += '<option value="' + d.obj[i].id + '">'
						+ d.obj[i].buildingName + '</option>';
				}
				$("#newnetDeviceRelEdit-build").html(content);
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 关联地址编辑区域下拉
function buildAreaEditSelect() {
	$("#newnetDeviceRelEdit-buildAreaid").empty();
	$("#newnetDeviceRelEdit-buildAreaid").html("<option value=''>--请选择--</option>");
	var buildId = $("#newnetDeviceRelEdit-build").val();
	if (buildId == "") {
		return;
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
		async: false,
		success : function(d) {
			if (d.success) {
				var content = "<option value=''>--请选择--</option>";
				for (var i = 0; i < d.obj.length; i++) {
					content += '<option value="' + d.obj[i].id + '">'
						+ d.obj[i].buildAreaName + '</option>';
				}
				$("#newnetDeviceRelEdit-buildAreaid").html(content);
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

//关联地址中的批量删除
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
    var checked = $("#userTable").bootstrapTable('getSelections');
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
    var url = baseUrl + "/netDeviceRel/delete";
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
                $('#userTable').bootstrapTable("refresh");
                layer.msg("删除成功！");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });
});

// 删除设备中的关联地址
function deleteBaseInfo(_this) {
	var select = _this.id;
	layer.open({
		content : '确认删除选中编号信息？',
		btn : [ '确认', '取消' ],
		yes : function() {
			$.ajax({
				url : baseUrl + "/netDeviceRel/delete",
				type : "post",
				beforeSend: function(request){
					request.setRequestHeader("Authorization","Bearer "+login.token);
				},
				data : {
					id : select
				},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						layer.msg(data.msg);
						$("#userTable").bootstrapTable('refresh');
					} else {
						layer.msg(data.msg);
					}
				},
				error : function(data) {

				}
			});
		},
		cancel : function() {
			// 右上角关闭回调
		}
	});
}

// 获取单位列表
function getUnitList() {
	$.ajax({
		type : "get",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		url : baseUrl + "/baseInfo/getArrayList",
		dataType : "json",
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

// 填充单位下拉框
function showUnitList(unitID) {
	var a = '<option value="%unitID%" >%unitName%</option>';
	var option;
	var wrap = '<option value="">--搜索--</option>';
	for (var i = 0; i < unitList.length; i++) {
		if (unitID && unitID == unitList[i].id) {
			option = '<option selected value="' + unitList[i].id + '" >'
				+ unitList[i].unitname + '</option>';
		} else {
			option = a.replace("%unitID%", unitList[i].id);
			option = option.replace("%unitName%", unitList[i].unitname);
		}
		wrap += option;
	}
	$("#UnitID").html(wrap);
	$("#UnitID").comboSelect();
	$("#newnetDeviceRel-unit").html(wrap);
	$("#newnetDeviceRelEdit-unit").html(wrap);
	$("#UnitIDView").html(wrap);
	$("#UnitIDView").comboSelect();
	$("#UnitIDView").siblings("input").attr('disabled', 'disabled');
	$("#UnitIDView").siblings(".combo-arrow").hide();
}

// 切换单位
$("#UnitID").bind("change", function() {
	emptyBuildList();
	emptyBuildAreaList();
	unitID = $("#UnitID").val();
	getBuildList();
	getDeviceList(deviceIndex,"");
	getPointVideoSelect('pointVideo',unitID,'');
});

// 获取建筑物列表
function getBuildList() {
	var UnitID = $("#UnitID").find("option:selected").val();
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
function emptyBuildList() {
	$("#BuildId").empty();
	$("#BuildId").html('<option value="">--请选择--</option>');
	$("#BuildIdView").empty();
	$("#BuildIdView").html('<option value="">--请选择--</option>');
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
	};
	$.ajax({
		type : "get",
		async : true,
		data : data,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
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
// 清空分区下拉框
function emptyBuildAreaList() {
	$("#BuildAreaId").empty();
	$("#BuildAreaId").html('<option value="">--请选择--</option>');
	$("#BuildAreaIdView").empty();
	$("#BuildAreaIdView").html('<option value="">--请选择--</option>');
}

// 获取新增页系统列表
function getSystemList() {
	$.ajax({
		type : "get",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		url : baseUrl + "/eqClass/getSystemList",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				SystemList = d.obj;
				showSystemLists();
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 填充系统下拉框（主页面）
function showSystemLists() {
	var a = '<option value="%SystemIds%">%SystemNames%</option>';
	var option;
	var wrap = '<option value="">--请选择--</option>';
	var warpRel = '<option value="">--请选择--</option>';
	if (SystemList) {
		for (var i = 0; i < SystemList.length; i++) {
			option = a.replace("%SystemIds%", SystemList[i].id);
			option = option
				.replace("%SystemNames%", SystemList[i].eqsystemname);
			wrap += option;
			if (SystemList[i].eqsystemcode == 3
				|| SystemList[i].eqsystemcode == 5
				|| SystemList[i].eqsystemcode == 6
				|| SystemList[i].eqsystemcode == 7
				|| SystemList[i].eqsystemcode == 8) {
				warpRel += option;
			}
		}
		$("#EqSystemCodes").html(wrap);
		$("#newnetDeviceRel-eqsystemid").html(warpRel);
		$("#newnetDeviceRelEdit-eqsystemid").html(warpRel);
	}
}

// 填充系统下拉框（新增页面）
function showSystemList() {
	var a = '<option value="%SystemId%">%SystemName%</option>';
	var option;
	var wrap = '<option value="">--请选择--</option>';
	if (SystemList) {
		for (var i = 0; i < SystemList.length; i++) {
			option = a.replace("%SystemId%", SystemList[i].id);
			option = option.replace("%SystemName%", SystemList[i].eqsystemname);
			wrap += option;
		}
		$("#EqSystemCode").html(wrap);
		$("#EqSystemCodeView").html(wrap);
	}
}

// 主页系统类型改变
$("#EqSystemCodes").bind("change", function() {
	getClassLists();
	emptyClassLists();
});

// 新增编辑系统类型改变
$("#EqSystemCode").bind("change", function() {
	getClassList($("#EqSystemCode").val());
	emptyDeviceList();
});

// 新增编辑设备分类
function getClassList(EqSystemCode) {
	$.ajax({
		type : "get",
		data : {
			"systemID" : EqSystemCode
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		async : false,
		url : baseUrl + "/eqClass/getArrayList",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				classList = d.obj;
				showClassList();
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 填充设备类型下拉框（新增编辑页）
function showClassList() {
	var a = '<option value="%classId%" code="%code%">%className%</option>';
	var option;
	var wrap = '<option value="">--请选择--</option>';
	if (classList) {
		for (var i = 0; i < classList.length; i++) {
			option = a.replace("%classId%", classList[i].id);
			option = option.replace("%code%", classList[i].classcode);
			option = option.replace("%className%", classList[i].classname);
			wrap += option;
		}
		$("#EqClassID").html(wrap);
		$("#EqClassIDView").html(wrap);
	}
}
function emptyClassList() {
	$("#EqClassID").empty();
	$("#EqClassID").html('<option value="">--请选择--</option>');
	$("#EqClassIDView").empty();
	$("#EqClassIDView").html('<option value="">--请选择--</option>');
}
// 获取主页设备类型列表
function getClassLists() {
	var systemID = $("#EqSystemCodes").find("option:selected").val();
	if (systemID == "") {
		systemID = null;
	}
	var data = {
		systemID : systemID
	};
	$.ajax({
		type : "get",
		async : true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : data,
		url : baseUrl + "/eqClass/getArrayList",
		dataType : "json",
		success : function(d) {
			if (d.success) {
				classList = d.obj;
				showClassLists();
			} else {
				layer.msg(d.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 填充主页面设备类型下拉框
function showClassLists() {
	var a = '<option value="%classId%">%className%</option>';
	var option;
	var wrap = '<option value="">--请选择--</option>';
	if (classList) {
		for (var i = 0; i < classList.length; i++) {
			option = a.replace("%classId%", classList[i].id);
			option = option.replace("%className%", classList[i].classname);
			wrap += option;
		}
		$("#EqClassIDs").html(wrap);
	}
}
// 清空主页面设备类型下拉框
function emptyClassLists() {
	$("#EqClassIDs").empty();
	$("#EqClassIDs").html('<option value="">--请选择--</option>');
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
			$("#twoPort").hide();
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			SetPortValue = 1;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
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
			$("#twoPort").hide();
			smokePortValue = 1;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0
			SetPortValue = 0;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
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
			$("#twoPort").hide();
			otherPortValue = 0;
			smokePortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue  = 1;
			SetPortValue = 0;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
		}else if (eqClass == "19" || eqClass == "5" || eqClass == "6"
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			otherPortValue = 1;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
			switch (eqClass) {
				case "5":
					$("#OtherPortName").html("端口号(温度)");
					break;
				case "6":
					$("#OtherPortName").html("端口号(湿度)");
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
		}else if(eqClass == "7" || eqClass == "2" || eqClass == "3"){
			$("#smokePort").hide();
			$("#SetPort").hide();
			$("#threePort").hide();
			$("#fourPort").hide();
			$("#faultPort").hide();
			$("#threeUPort").hide();
			$("#otherPort").hide();
			$("#ElevenPort").hide();
			$("#customPort").hide();
			$("#twoPort").show();
			SetPortValue = 0;
			smokePortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			otherPortValue = 0;
			twoPortValue = 1;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
			switch (eqClass) {
				case "2":
					$(".twoPortName:eq(0)").html("端口号(水箱)");
					break;
				case "3":
					$(".twoPortName:eq(0)").html("端口号(水池)");
					break;
				case "7":
					$(".twoPortName:eq(0)").html("端口号(压力)");
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			threePortValue = 1;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 1;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
		} else if (eqClass == "13" || eqClass == "28") {// 故障
			$("#smokePort").hide();
			$("#SetPort").hide();
			$("#otherPort").hide();
			$("#threePort").hide();
			$("#faultPort").show();
			$("#fourPort").hide();
			$("#ElevenPort").hide();
			$("#customPort").hide();
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			faultPortValue = 1;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			$("#FName").html("端口号(称重)");
			faultPortValue = 1;
			twoPortValue = 0;
			deviceIndex = 3;
			$(".leadingInImg").show();//控制导入按钮
		} else if (eqClass == "--请选择--") {
			$("#SetPort").hide();
			$("#smokePort").hide();
			$("#otherPort").hide();
			$("#threePort").hide();
			$("#faultPort").hide();
			$("#fourPort").hide();
			$("#ElevenPort").hide();
			$("#customPort").hide();
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			twoPortValue = 0;
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			twoPortValue = 0;
			$(".leadingInImg").hide();//控制导入按钮
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			fourPortValue = 0;
			twoPortValue = 0;
			$(".leadingInImg").hide();//控制导入按钮
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 1;
			customPortValue = 0;
			fourPortValue = 0;
			twoPortValue = 0;
			$(".leadingInImg").show();//控制导入按钮
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
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 1;
			fourPortValue = 0;
			twoPortValue = 0;
			$(".leadingInImg").show();//控制导入按钮
		} else if (eqClass == undefined) {
			$("#SetPort").hide();
			$("#smokePort").hide();
			$("#otherPort").hide();
			$("#threePort").hide();
			$("#faultPort").hide();
			$("#fourPort").hide();
			$("#ElevenPort").hide();
			$("#customPort").hide();
			$("#twoPort").hide();
			SetPortValue = 0;
			smokePortValue = 0;
			otherPortValue = 0;
			threePortValue = 0;
			fourPortValue = 0;
			faultPortValue = 0;
			ElevenPortValue = 0;
			customPortValue = 0;
			twoPortValue = 0;
		}
		emptyDeviceList();
		if (unitID == "" || unitID == undefined) {
			return;
		}
		getDeviceList(deviceIndex,"");
	});

// 获取联网设备列表
function getDeviceList(deviceIndex,SelectNetDeviceId) {
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
				showDeviceList(deviceList,SelectNetDeviceId);
			} else {
				layer.msg(data.desc);
			}
		},
		error : function(e) {

		}
	});
}

// 填充联网设备下拉框
function showDeviceList(deviceList,SelectNetDeviceId) {
	var option = "";
	var wrap = '<option value="">--搜索--</option>'

		for (var i = 0; i < deviceList.length; i++) {
			if (SelectNetDeviceId && SelectNetDeviceId == deviceList[i].id) {
				option = '<option selected value="' + deviceList[i].id + '" >'+ deviceList[i].name +'</option>';
			} else {
				option = '<option value="' + deviceList[i].id + '" >'+ deviceList[i].name +'</option>';
			}
			wrap += option;
		}
		$("#NetDeviceID").html(wrap);
		$("#NetDeviceID").comboSelect();
		$("#NetDeviceIDView").html(wrap);
}

function emptyDeviceList() {
	$("#NetDeviceID").empty();
	$("#NetDeviceID").html('<option value="">--请选择--</option>');
	$("#NetDeviceIDView").empty();
	$("#NetDeviceIDView").html('<option value="">--请选择--</option>');
}

// 根据单位获取点位视频
function getPointVideoSelect(element,unitId,pointVideoId){
	$.ajax({
		type:"post",
		url:baseUrl + "/pointVideo/getPointVideoSelect?unitId="+unitId,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		dataType:"json",
		success:function(data){
			if(data.success){
				var content = '<option value="">--请选择--</option>';
				var wrap = "";
				for(var i=0;i<data.obj.length;i++){
					if(pointVideoId == data.obj[i].id){
						wrap = '<<option value="'+ data.obj[i].id +'" selected>'+data.obj[i].name+'</option>>'
					}else{
						content += '<option value="'+data.obj[i].id+'">'+data.obj[i].name+'</option>';
					}
					content += wrap;
				}
				$("#" + element).html(content);
			}
		},
		error: function (e) {

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
	// if (maxValue == "") {
	// 	layer.msg("最大值不能为空！");
	// 	return;
	// }
	// if (minValue == "") {
	// 	layer.msg("最小值不能为空！");
	// 	return;
	// }
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
	// if (maxValue == "") {
	// 	layer.msg("最大值不能为空！");
	// 	return;
	// }
	// if (minValue == "") {
	// 	layer.msg("最小值不能为空！");
	// 	return;
	// }
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

function portViewSearch(Id) {
	$('#portTableView')
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
						title : '上限'
					},
					{
						field : 'analogdown',
						title : '下限'
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
						title : '最大值'
					},
					{
						field : 'minValue',
						title : '最小值'
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
function removeConfigDataBase(row){
	newcount = newcount - 1;
	row.deleteFlag = "true";
	// 删除选定的行
	$('#portTable').bootstrapTable('remove',{field:"deleteFlag", values:["true"]});
}


// 填充地址中的系统下拉框（新增页面）
// function showAddressSystemList(){
// var a = '<option value="%SystemIdss%">%SystemNamess%</option>';
// var option;
// var wrap='<option value="">--请选择--</option>';
// if(SystemList){
// for(var i = 0;i<SystemList.length;i++){
// option = a.replace("%SystemIdss%",SystemList[i].id);
// option = option.replace("%SystemNamess%",SystemList[i].eqsystemname);
// wrap+=option;
// }
// $("#AddressEqSystemID").html(wrap);
// }
// }

function showInterfaceList(){
	isHasInterfaceTable = 1;
	$('#interfaceList').bootstrapTable({
		url: baseUrl + '/equipmentFac/getInterfaceOutList',         //请求后台的URL（*）
		method: 'get',                      //请求方式（*）
		contentType: 'application/x-www-form-urlencoded',
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: false,                   //是否显示分页（*）
		sortable: false,                     //是否启用排序
		sortOrder: "asc",                   //排序方式
		queryParamsType: "undefined",   //排序方式
		ajaxOptions:{
			headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
		},
		queryParams: function queryParams(params) {   //设置查询参数
			var param = {
				deviceId: $("#indexId").val()
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
				field: 'ioPort',
				title: '端口号'
			},
			{
				field: 'ioName',
				title: '信号名称'
			},
			{
				field: 'fotActionTime',
				title: '点动时间'
			},
			{
				field: 'digitalNormal',
				title: '正常电平',
				formatter:function(value, row, index){
					if(value==0){
						return "低电平0";
					}
					if(value==1){
						return "高电平1";
					}
					return "";
				}
			},
			{
				field: 'goodName',
				title: '正常名'
			},
			{
				field: 'badName',
				title: '异常名'
			},
			{
				field: 'fotActionUnit',
				title: '点动单位',
				formatter:function(value, row, index){
					if(value==2){
						return "100ms";
					}
					if(value==3){
						return "1s";
					}
					if(value==6){
						return "100s";
					}
					if(value==7){
						return "1min";
					}
					if(value==8){
						return "100min";
					}
					return "1ms";
				}
			},
			{
				field: '',
				title: '操作',
				formatter: function (value, row, index) {
					var str = "";
					str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
					str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
					return str;
				},
				events: {
					'click .edit': function (e, value, row, index) {
						editInterface(row);
					},
					'click .del': function (e, value, row, index) {
						deleteInterface(row);
					}
				}
			}
		]
	});
}
function editInterface(row){
	$("#interfaceId").val(row.id);
	$("#iOPort_add").val(row.ioPort);
	$("#detialName_add").val(row.ioName);
	$("#movementTime_add").val(row.fotActionTime);
	$("#digitalNormal_add").val(row.digitalNormal);
	$("#goodName_add").val(row.goodName);
	$("#badName_add").val(row.badName);
	$("#movement_add").val(row.fotActionUnit);
	$("#orderType_add").val(row.orderType);
	$("#sortno_add").val(row.showOrder);
	$("#reserve_add").val(row.reserve);
	var lock = row.locks;
	if(lock == "1"){
		$("#isLock_add1").prop("checked","checked");
	}else{
		$("#isLock_add0").prop("checked","checked");
	}
}
function deleteInterface(row){
	var url = baseUrl + "/equipmentFac/deleteInterfaceOut";
	$.ajax({
		url: url,
		type: "get",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {id:row.id},
		dataType: "json",
		success: function (d) {
			if (d.success) {
				layer.msg("删除成功");
				$("#interfaceList").bootstrapTable("refresh");
				emptyInterface();
			} else {
				layer.msg(d.msg||"删除失败。");
			}
		},
		error: function (data) {

		}
	});
}
$(".btnSureInterface").click(function(){
	var eqDetailId = $("#indexId").val();
	var id = $("#interfaceId").val();
	var ioport = $("#iOPort_add").val();
	var ioName = $("#detialName_add").val();
	var fotActionTime = $("#movementTime_add").val();
	var digitalNormal= $("#digitalNormal_add").val();
	var goodName = $("#goodName_add").val();
	var badName = $("#badName_add").val();
	var fotActionUnit = $("#movement_add").val();
	var showOrder = $("#sortno_add").val();
	var reserve = $("#reserve_add").val();
	var orderType = $("#orderType_add").val();
	var isLock = $("input[name='isLock_add']:checked").val();

	if (ioport == "" || ioport == null) {
		layer.msg("端口不能为空！");
		return;
	}
	if (ioName == "" || ioName == null) {
		layer.msg("信号名称不能为空！");
		return;
	}
	if (digitalNormal == "" || digitalNormal == null) {
		layer.msg("正常电平不能为空！");
		return;
	}

	var data = {
		id:id,
		ioPort:ioport,
		ioName:ioName,
		fotActionTime:fotActionTime,
		digitalNormal:digitalNormal,
		goodName:goodName,
		badName:badName,
		fotActionUnit:fotActionUnit,
		showOrder:showOrder,
		locks:isLock,
		reserve:reserve,
		orderType:orderType,
		netDeviceId:eqDetailId
	}
	var url = baseUrl + "/equipmentFac/updateInterfaceOut";
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
				layer.msg("保存成功");
				$("#interfaceList").bootstrapTable("refresh");
				emptyInterface();
			} else {
				layer.msg(d.msg||"保存失败。");
			}
		},
		error: function (data) {

		}
	});
});
$(".btnCloseInterface").click(function(){
	emptyInterface();
});
function emptyInterface(){
	$("#interfaceId").val("");
	$("#iOPort_add").val("");
	$("#detialName_add").val("");
	$("#movementTime_add").val("");
	$("#digitalNormal_add").val("");
	$("#goodName_add").val("");
	$("#badName_add").val("");
	$("#movement_add").val("");
	$("#orderType_add").val("2");
	$("#sortno_add").val("");
	$("#reserve_add").val("");
	$("#isLock_add1").removeAttr("checked");
	$("#isLock_add0").removeAttr("checked");
}

// 导入弹窗按钮
function interfaceImport() {
	$("#deviceId").val($("#indexId").val());
	$("#importInterfaces").modal("show");
	$('.fileName1').html("");
}

function doChange(file) {
	var upload_file = $(file).val(); // 获取上传文件
	$('.fileName1').html(upload_file); // 赋值给自定义input框
};

// 导入按钮
$(".btnImport").click(function() {
	var formData = new FormData($("#interfaceForm")[0]);
	var uploadExcel = $(".uploadExcel1").val();
	if (uploadExcel == "") {
		layer.msg("请选择上传文件！");
		return;
	}
	$.ajax({
		url : baseUrl + "/equipmentFac/importExcel",
		type : "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data : formData,
		dataType : "json",
		contentType : false,
		processData : false,
		success : function(data) {
			if(data.success){
				layer.msg(data.msg);
				layer.closeAll("loading");
				$("#importInterfaces").modal("hide");
				$('#interfaceList').bootstrapTable('refresh');
			}else{
				layer.msg(data.msg);
			}
		}
	})
});
function searchAddress(){
	var deviceId = $("#deviceId").val();
	DeviceRelSearch(deviceId);
}
