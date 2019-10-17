var map;
var local;
var html = '<select name="searchContentLW" id="searchContentLW" class="form-control"><option value="">全部</option>';
var mainType = 0; //单位管理类型。联网0、维保1、监管2

$(function () {
	//选择显示联网单位，维保单位及监管单位的对应的内容
	$("#unitManageSelect").change(function(){
		mainType = $(this).find('option:selected').val();
		emptySearch();
		if (mainType == 0) {
			$(".unitManageLWseniorSearch").show().siblings(".unitManageWBseniorSearch").hide().siblings(".unitManageJGseniorSearch").hide();
			$(".unitManageLW").show().siblings(".unitManageWB").hide().siblings(".unitManageJG").hide();
			initProvince0();
			loadMap0();
		} else if (mainType == 1) {
			$(".unitManageWBseniorSearch").show().siblings(".unitManageLWseniorSearch").hide().siblings(".unitManageJGseniorSearch").hide();
			$(".unitManageWB").show().siblings(".unitManageLW").hide().siblings(".unitManageJG").hide();
			loadMap1();
		}else if(mainType == 2){
			$(".unitManageJGseniorSearch").show().siblings(".unitManageLWseniorSearch").hide().siblings(".unitManageWBseniorSearch").hide();
			$(".unitManageJG").show().siblings(".unitManageWB").hide().siblings(".unitManageLW").hide();
			loadMap2();
		}
		mainSearch(); //联网
	});


	// 主页面查询
	mainSearch(); //联网
	// 初始化资源
	initProvince0();
	loadMap0();


	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$(".notLastModal").on("hidden.bs.modal", function () {
		// $("#newProject").addClass("modal-open");
		$("#newEmploy").css("overflow-y", "auto");
	});
	// 日期选择控件
	$("#newUnit-establishmenttime").jeDate({
		ishmsVal: false,
		minDate: '1900-01-01 00:00:00',
		maxDate: '2099-12-31 23:59:59',
		format: "YYYY-MM-DD",
		zIndex: 3000
	})
	// 日期选择控件
	$("#newUnit-onlinedate").jeDate({
		ishmsVal: false,
		minDate: '1900-01-01 00:00:00',
		maxDate: '2099-12-31 23:59:59',
		format: "YYYY-MM-DD",
		zIndex: 3000
	})
	$(".btnReset").click(function () {
		emptySearch();
		$("#userTable").bootstrapTable("refresh");
		$("#searchContent").val('');
	});
	// 查询按钮查询
	$(".btnSearch").click(function () {
		// $("#userTable").bootstrapTable('destroy');
		mainSearch();
	});
	// 批量删除
	$(".btnDeleteMany").click(function () {
		deleteEmploy();
	});



	//新增联网单位
	$(".newAdd0").click(function () {
		$("#newEmploy").modal("show");
		$('.modalLW').show().siblings('.modalWB').hide().siblings('.modalJG').hide(); //显示联网单位新增隐藏维保新增

		$("#editId").val("0");
		$(".password").show();
		// $("#newEmploy .modal-title").html("新增联网单位");
		$("#newEmploy .btnSure").show();

		$("#newUnit-maintenanceUnit").next("div").unbind("click");
		$("#newUnit-maintenanceUnit").val("");
		$("#newUnit-maintenanceUnit").selectpicker("val","");
		// $("#newUnit-maintenanceUnit").empty();
		$("#newUnit-maintenanceUnit").siblings(".btn-group").find(".btn .filter-option").html("");
		$("#newUnit-unitcode").val("");
		$("#newUnit-unitname").val("");
		$("#newUnit-systemNumber").val("");
		$("#newUnit-email").val("");
		$("#newUnit-unittype").val("");
		$("#newUnit-unitaddress").val("");
		$("#newUnit-phone").val("");
		$("#newUnit-unitsummary").val("");
		$("#newUnit-technicalrepresentative").val("");

		$("#newUnit-postcode").val("");
		$("#newUnit-fireroomphone").val("");
		$("#newUnit-saferesponsiblepersonname").val("");
		$("#newUnit-saferesponsiblepersoncard").val("");
		$("#newUnit-saferesponsiblepersonphone").val("");
		$("#newUnit-safemanagername").val("");
		$("#newUnit-safemanagercard").val("");
		$("#newUnit-safemanagerphone").val("");
		$("#newUnit-assistmanagername").val("");
		$("#newUnit-assistmanagercard").val("");
		$("#newUnit-assistmanagerphone").val("");
		$("#newUnit-legalpersonname").val("");
		$("#newUnit-legalppersoncard").val("");
		$("#newUnit-legalpersonphone").val("");
		$("#newUnit-staffcount").val("");
		$("#newUnit-establishmenttime").val("");
		$("#newUnit-superiorunit").val("");
		$("#newUnit-administunit").val("");
		$("#newUnit-economictype").val("");
		$("#newUnit-fixedassets").val("");
		$("#newUnit-area").val("");
		$("#newUnit-buildingarea").val("");
		$("#newUnit-onlinedate").val("");
		$("#newUnit-superviselevel").val("");
		$("#newUnit-supervisetype").val("");
		$("#newUnit-belongcentername").val("");
		$("#newUnit-userrepresentative").val("");
		$("#newUnit-unitpoint").val("");
		$("#newUnit-addressdetial").val("");
		$("#newUnit-datafrom").val("");
		$("#newUnit-datafromid").val("");
		$("#newUnit-networkstatus1").prop("checked", "checked");
		$("#newUnit-important0").prop("checked", "checked");

		$("#newUnit-importantid").val("");
		$("#newUnit-socialcreditcode").val("");
		$("#newUnit-propertyunitname").val("");
		$("#newUnit-lzscore").val("");
		$("#newUnit-lzscorelevel").val("");
		$("#newUnit-firerating").val("");
		$("#newUnit-firecount").val("");
		$("#newUnit-link").val("");
		$("#newUnit-watercount").val("");
		$("#newUnit-videocount").val("");
		$("#newUnit-electriccount").val("");
		$("#newUnit-unitdangerlevel").val("");
		emptyCityRegionTown();
		provinceSelect('newUnit-proviceid','');
		// $("#newUnit-cityid").val("");
		// $("#newUnit-regionid").val("");
		// $("#newUnit-townid").val("");
		$("#newUnit-childstationnum").val("");
		$("#newUnit-inductionpointcount").val("");
		$("#newUnit-monitorunitname").val("");
		$("#newUnit-monitorunituser").val("");
		$("#newUnit-monitorunitphone").val("");
		$("#newUnit-advancereminderday").val("");
		$("#newUnit-messagephone").val("");
		$("#newUnit-messagenumber").val("");
		$("#newUnit-OrgCode").val("");
		/*$("#newUnit-autocalldelay").val("");*/
		$("#newUnit-callTotal").val("");
		$("#newUnit-videoAppkey").val("");
		$("#newUnit-videoAppsecret").val("");
		$("#newUnit-isautocall1").prop("checked", "checked");
		$("#newUnit-autoCallNum").val("");
		$("input[name='ReceiveAlarmType']").removeAttr("checked");
		$("input[name='callAlarmType']").removeAttr("checked");

		$("#picurls-xfzimg").attr("file_string", "");
		$("#picurls-unitbg").attr("file_string", "");
		$("#picurls-svgfile").attr("file_string", "");
		$("#picurls-unitproximity").attr("file_string", "");
		$("#loadImg-xfzimg").html(
			'<li class="modalImg"><span>请选择上传图片</span></li>');
		$("#loadImg-unitbg").html(
			'<li class="modalImg"><span>请选择上传图片</span></li>');
		$("#loadImg-svgfile").html(
			'<li class="modalImg"><span>请选择上传图片</span></li>');
		$("#loadImg-unitproximity").html('<li class="modalImg"><span>请选择上传图片</span></li>');
		$("#loadImg-unitLogo").html('<li class="modalImg"><span>请选择上传图片</span></li>');

		$(".fileName").html("");
		$(".uploadFile").val("");
		$("#attachment").val("");
	});

	//新增维保单位
	$(".newAdd1").click(function () {
		$("#newEmploy").modal("show");
		$('.modalWB').show().siblings('.modalLW').hide().siblings('.modalJG').hide(); //显示维保新增隐藏联网新增窗口
		// $(".password").show();
		// $("#newEmploy .modal-title").html("新增维保单位");
		$("#unitCode").val("");
		$("#unitName").val("");
		$("#address").val("");
		$("#systemNumber").val("");
		$("#contacts").val("");
		$("#telephone").val("");
		$("#remark").val("");
		$("#pointX").val("");
		$("#pointY").val("");
		emptyCityRegionTown();
		provinceSelect('proviceid','');
		// $("#cityid").val("");
		// $("#regionid").val("");
		// $("#townid").val("");
		$("#email").val("");
		$("#networking-loadImg-unitLogo").html('<li class="modalImg"><span>请选择上传图片</span></li>');
		$("#editId").val("0");
		$("#indexId").val("");
	});

	// 新增监管单位
	$(".newAdd2").click(function () {
		$("#newEmploy").modal("show");
		$('.modalJG').show().siblings('.modalLW').hide().siblings('.modalWB').hide(); //显示维保新增隐藏联网新增窗口

		// $(".password").show();
		// $("#newEmploy .modal-title").html("新增维保单位");

		$("#supervise-unitCode").val("");
		$("#supervise-unitName").val("");
		$("#supervise-systemNumber").val("");
		$("#supervise-address").val("");
		$("#supervise-contacts").val("");
		$("#supervise-telephone").val("");
		$("#supervise-remark").val("");
		$("#supervise-pointX").val("");
		$("#supervise-pointY").val("");
		// $("#supervise-proviceid").val("");
		emptyCityRegionTown();
		provinceSelect('$("#supervise-proviceid")', '');

		// $("#supervise-cityid").val("");
		// $("#supervise-regionid").val("");
		// $("#supervise-townid").val("");
		$("#supervise-email").val("");
		$("#supervise-loadImg-unitLogo").html('<li class="modalImg"><span>请选择上传图片</span></li>');

		$("#editId").val("0");
		// $("#indexId").val("");    不确定功能
	});


	// 新增或编辑的保存按钮
	$(".btnSure")
		.click(
			function () {
				if (mainType == 0) { //联网
					var id = $("#id").val();
					var maintenanceunit = $("#newUnit-maintenanceUnit").val();
					var unitcode = $("#newUnit-unitcode").val();
					var unitname = $("#newUnit-unitname").val();
					var systemNumber = $("#newUnit-systemNumber").val();
					var email = $("#newUnit-email").val();
					var unittype = $("#newUnit-unittype").val();
					var unitaddress = $("#newUnit-unitaddress").val();
					var phone = $("#newUnit-phone").val();
					var unitsummary = $("#newUnit-unitsummary").val();
					var technicalrepresentative = $(
						"#newUnit-technicalrepresentative").val();
					var networkstatus = $(
						"input[name='newUnit-networkstatus']:checked")
						.val();

					var postcode = $("#newUnit-postcode").val();
					var fireroomphone = $("#newUnit-fireroomphone").val();
					var saferesponsiblepersonname = $(
						"#newUnit-saferesponsiblepersonname").val();
					var saferesponsiblepersoncard = $(
						"#newUnit-saferesponsiblepersoncard").val();
					var saferesponsiblepersonphone = $(
						"#newUnit-saferesponsiblepersonphone").val();
					var safemanagername = $("#newUnit-safemanagername")
						.val();
					var safemanagercard = $("#newUnit-safemanagercard")
						.val();
					var safemanagerphone = $("#newUnit-safemanagerphone")
						.val();
					var assistmanagername = $("#newUnit-assistmanagername")
						.val();
					var assistmanagercard = $("#newUnit-assistmanagercard")
						.val();
					var assistmanagerphone = $(
						"#newUnit-assistmanagerphone").val();
					var legalpersonname = $("#newUnit-legalpersonname")
						.val();
					var legalppersoncard = $("#newUnit-legalppersoncard")
						.val();
					var legalpersonphone = $("#newUnit-legalpersonphone")
						.val();
					var staffcount = $("#newUnit-staffcount").val();
					var establishmenttime = $("#newUnit-establishmenttime")
						.val();
					var superiorunit = $("#newUnit-superiorunit").val();
					var administunit = $("#newUnit-administunit").val();
					var economictype = $("#newUnit-economictype").val();
					var fixedassets = $("#newUnit-fixedassets").val();
					var area = $("#newUnit-area").val();
					var buildingarea = $("#newUnit-buildingarea").val();
					var onlinedate = $("#newUnit-onlinedate").val();
					var superviselevel = $("#newUnit-superviselevel").val();
					var supervisetype = $("#newUnit-supervisetype").val();
					var belongcentername = $("#newUnit-belongcentername")
						.val();
					var userrepresentative = $(
						"#newUnit-userrepresentative").val();
					var unitpoint = $("#newUnit-unitpoint").val();
					var addressdetial = $("#newUnit-addressdetial").val();
					var datafrom = $("#newUnit-datafrom").val();
					var datafromid = $("#newUnit-datafromid").val();
					var important = $(
						"input[name='newUnit-important']:checked")
						.val();

					var importantid = $("#newUnit-importantid").val();
					var socialcreditcode = $("#newUnit-socialcreditcode")
						.val();
					var propertyunitname = $("#newUnit-propertyunitname")
						.val();
					var lzscore = $("#newUnit-lzscore").val();
					var lzscorelevel = $("#newUnit-lzscorelevel").val();
					var firerating = $("#newUnit-firerating").val();
					var firecount = $("#newUnit-firecount").val();
					var unitlink = $("#newUnit-link").val();
					var watercount = $("#newUnit-watercount").val();
					var videocount = $("#newUnit-videocount").val();
					var electriccount = $("#newUnit-electriccount").val();
					var unitdangerlevel = $("#newUnit-unitdangerlevel")
						.val();
					var proviceid = $("#newUnit-proviceid").val();
					var cityid = $("#newUnit-cityid").val();
                    var regionid = $("#newUnit-regionid").val();
                    var townid = $("#newUnit-townid").val();
					var childstationnum = $("#newUnit-childstationnum").val();
					var inductionpointcount = $(
						"#newUnit-inductionpointcount").val();
					var monitorunitname = $("#newUnit-monitorunitname").val();
					var monitorunituser = $("#newUnit-monitorunituser").val();
					var monitorunitphone = $("#newUnit-monitorunitphone").val();
					var advancereminderday = $("#newUnit-advancereminderday").val();
					var messagephone = $("#newUnit-messagephone").val();
					var autoCallNum = $("#newUnit-autoCallNum").val();
					var messagenumber = $("#newUnit-messagenumber").val();
					var receivealarmtype = "";
					$("input[name='ReceiveAlarmType']:checked").each(function (j) {
						if (j >= 0) {
							receivealarmtype += $(this).val() + ","
						}
					});
					receivealarmtype = receivealarmtype.substring(0, receivealarmtype.length - 1);

                    var callAlarmType = "";
                    $("input[name='callAlarmType']:checked").each(function (j) {
                        if (j >= 0) {
                            callAlarmType += $(this).val() + ","
                        }
                    });
                    callAlarmType = callAlarmType.substring(0, callAlarmType.length - 1);
					var xfzimg = $("#picurls-xfzimg").attr("file_string");
					var unitbg = $("#picurls-unitbg").attr("file_string");
					var svgfile = $("#picurls-svgfile").attr("file_string");
					var unitproximity = $("#picurls-unitproximity").attr("file_string");
					var unitLogo = $("#picurls-unitLogo").attr("file_string");
					var attachment = $("#attachment").val();
					var attachmentName = $("#attachmentName").val();
					var orgCode = $("#newUnit-OrgCode").val();
                    /*var autocalldelay = $("#newUnit-autocalldelay").val();*/
                    var callTotal = $("#newUnit-callTotal").val();
                    var videoAppkey = $("#newUnit-videoAppkey").val();
                    var videoAppsecret = $("#newUnit-videoAppsecret").val();
					var isautocall = $("input[name='isAutoCall']:checked").val();

					if(maintenanceunit == null){
						layer.msg("维保和监管单位不能为空！");
						return;
					}
					if (unitcode == "") {
						layer.msg("单位编号不能为空！");
						return;
					}
					if (unitname == "") {
						layer.msg("单位名称不能为空！");
						return;
					}
					if (phone == "") {
						layer.msg("联系电话不能为空！");
						return;
					}
					if (unitaddress == "") {
						layer.msg("单位地址不能为空！");
						return;
					}

					// 获取维保和监管单位的选项
					var maintenanceunitid = "";
					for(var i = 0;i<maintenanceunit.length;i++){
						maintenanceunitid += maintenanceunit[i] + ","
					}
					maintenanceunitid = maintenanceunitid.substring(0, maintenanceunitid.length - 1);

					// if (editId == 0 && password == "") {
					// layer.msg("密码不能为空！");
					// return;
					// }
					var editId = $("#editId").val();
					if (editId == 0) {
						var url = baseUrl + "/baseInfo/addBaseInfo"; //新增
						var data = {
							maintenanceunitid: maintenanceunitid,
							unitcode: unitcode,
							unitname: unitname,
							email: email,
							unittype: unittype,
							unitaddress: unitaddress,
							phone: phone,
							unitsummary: unitsummary,
							technicalrepresentative: technicalrepresentative,
							networkstatus: networkstatus,
							postcode: postcode,
							fireroomphone: fireroomphone,
							saferesponsiblepersonname: saferesponsiblepersonname,
							saferesponsiblepersoncard: saferesponsiblepersoncard,
							saferesponsiblepersonphone: saferesponsiblepersonphone,
							safemanagername: safemanagername,
							safemanagercard: safemanagercard,
							safemanagerphone: safemanagerphone,
							assistmanagername: assistmanagername,
							assistmanagercard: assistmanagercard,
							assistmanagerphone: assistmanagerphone,
							legalpersonname: legalpersonname,
							legalppersoncard: legalppersoncard,
							legalpersonphone: legalpersonphone,
							staffcount: staffcount,
							establishmenttime: establishmenttime,
							superiorunit: superiorunit,
							administunit: administunit,
							economictype: economictype,
							fixedassets: fixedassets,
							area: area,
							buildingarea: buildingarea,
							onlinedate: onlinedate,
							superviselevel: superviselevel,
							supervisetype: supervisetype,
							belongcentername: belongcentername,
							userrepresentative: userrepresentative,
							unitpoint: unitpoint,
							addressdetial: addressdetial,
							datafrom: datafrom,
							datafromid: datafromid,
							important: important,
							importantid: importantid,
							socialcreditcode: socialcreditcode,
							propertyunitname: propertyunitname,
							lzscore: lzscore,
							lzscorelevel: lzscorelevel,
							firerating: firerating,
							firecount: firecount,
							unitlink: unitlink,
							watercount: watercount,
							videocount: videocount,
							electriccount: electriccount,
							unitdangerlevel: unitdangerlevel,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							childstationnum: childstationnum,
							inductionpointcount: inductionpointcount,
							unitproximity: unitproximity,
							logourl: unitLogo,
							xfzimg: xfzimg,
							unitbg: unitbg,
							svgfile: svgfile,
							xfcontractbook: attachment,
							xfcontractbookname: attachmentName,
							monitorunitname: monitorunitname,
							monitorunituser: monitorunituser,
							monitorunitphone: monitorunitphone,
							advancereminderday: advancereminderday,
							messagephone: messagephone,
							autoCallNum: autoCallNum,
							receivealarmtype: receivealarmtype,
							messagenumber: messagenumber,
							orgCode: orgCode,
							isautocall: isautocall,
							/*autocalldelay: autocalldelay,*/
                            videoAppkey: videoAppkey,
                            videoAppsecret: videoAppsecret,
                            callTotal:callTotal,
                            callAlarmType:callAlarmType
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
									$("#userTable").bootstrapTable(
										"refresh");
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					} else {
						var url = baseUrl + "/baseInfo/updateBaseInfo";   //编辑
						var data = {
							id: id,
							maintenanceunitid: maintenanceunitid,
							unitcode: unitcode,
							unitname: unitname,
							systemNumber: systemNumber,
							email: email,
							unittype: unittype,
							unitaddress: unitaddress,
							phone: phone,
							unitsummary: unitsummary,
							technicalrepresentative: technicalrepresentative,
							networkstatus: networkstatus,
							postcode: postcode,
							fireroomphone: fireroomphone,
							saferesponsiblepersonname: saferesponsiblepersonname,
							saferesponsiblepersoncard: saferesponsiblepersoncard,
							saferesponsiblepersonphone: saferesponsiblepersonphone,
							safemanagername: safemanagername,
							safemanagercard: safemanagercard,
							safemanagerphone: safemanagerphone,
							assistmanagername: assistmanagername,
							assistmanagercard: assistmanagercard,
							assistmanagerphone: assistmanagerphone,
							legalpersonname: legalpersonname,
							legalppersoncard: legalppersoncard,
							legalpersonphone: legalpersonphone,
							staffcount: staffcount,
							establishmenttime: establishmenttime,
							superiorunit: superiorunit,
							administunit: administunit,
							economictype: economictype,
							fixedassets: fixedassets,
							area: area,
							buildingarea: buildingarea,
							onlinedate: onlinedate,
							superviselevel: superviselevel,
							supervisetype: supervisetype,
							belongcentername: belongcentername,
							userrepresentative: userrepresentative,
							unitpoint: unitpoint,
							addressdetial: addressdetial,
							datafrom: datafrom,
							datafromid: datafromid,
							important: important,
							importantid: importantid,
							socialcreditcode: socialcreditcode,
							propertyunitname: propertyunitname,
							lzscore: lzscore,
							lzscorelevel: lzscorelevel,
							firerating: firerating,
							firecount: firecount,
							unitlink: unitlink,
							watercount: watercount,
							videocount: videocount,
							electriccount: electriccount,
							unitdangerlevel: unitdangerlevel,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							childstationnum: childstationnum,
							inductionpointcount: inductionpointcount,
							unitproximity: unitproximity,
							logourl: unitLogo,
							xfzimg: xfzimg,
							unitbg: unitbg,
							svgfile: svgfile,
							xfcontractbook: attachment,
							xfcontractbookname: attachmentName,
							monitorunitname: monitorunitname,
							monitorunituser: monitorunituser,
							monitorunitphone: monitorunitphone,
							advancereminderday: advancereminderday,
							messagephone: messagephone,
							autoCallNum: autoCallNum,
							receivealarmtype: receivealarmtype,
							messagenumber: messagenumber,
							orgCode: orgCode,
							isautocall: isautocall,
                         /*   autocalldelay: autocalldelay,*/
                            videoAppkey: videoAppkey,
                            videoAppsecret: videoAppsecret,
                            callTotal:callTotal,
                            callAlarmType:callAlarmType
						};
						//console.log("data:"+data);
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
									layer.msg("保存成功！");
									$("#userTable").bootstrapTable(
										"refresh");
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					}
				}
				else if(mainType == 1){ //维保

					var editId = $("#editId").val();
					var id = $("#indexId").val();

					var unitCode = $("#unitCode").val();
					var unitName = $("#unitName").val();
					var systemNumber = $("#systemNumber").val();
					var address = $("#address").val();
					var contacts = $("#contacts").val();
					var telephone = $("#telephone").val();
					var remark = $("#remark").val();
					var pointX = $("#pointX").val();
					var pointY = $("#pointY").val();
					var proviceid = $("#proviceid").val();
					var cityid = $("#cityid").val();
                    var regionid = $("#regionid").val();
                    var townid = $("#townid").val();
					var email = $("#email").val();
					var unitLogo = $("#networking-picurls-unitLogo").attr("file_string");

					if (unitCode == "") {
						layer.msg("请输入单位编号");
						return;
					}
					if (unitName == "") {
						layer.msg("请输入单位名称");
						return;
					}
					var editId = $("#editId").val();
					if (editId == 0) {
						var url = baseUrl + "/maintenanceunit/addMaintenanceUnit"; //保存新增
						var data = {
							mainType: 1,
							unitcode: unitCode,
							unitname: unitName,
							address: address,
							contacts: contacts,
							telephone: telephone,
							remark: remark,
							pointx: pointX,
							pointy: pointY,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							logourl: unitLogo,
							email: email
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
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					} else {
						var url = baseUrl + "/maintenanceunit/updateMaintenanceUnit"; //编辑
						var data = {
							mainType: 1,
							id: id,
							unitcode: unitCode,
							unitname: unitName,
							systemNumber: systemNumber,
							address: address,
							contacts: contacts,
							telephone: telephone,
							remark: remark,
							pointx: pointX,
							pointy: pointY,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							logourl: unitLogo,
							email: email
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
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					}
				}
				else if(mainType == 2){ //监管

					var editId = $("#editId").val();
					var id = $("#supervise-indexId").val();

					var unitCode = $("#supervise-unitCode").val();
					var unitName = $("#supervise-unitName").val();
					var systemNumber = $("#supervise-systemNumber").val();
					var address = $("#supervise-address").val();
					var contacts = $("#supervise-contacts").val();
					var telephone = $("#supervise-telephone").val();
					var remark = $("#supervise-remark").val();
					var pointX = $("#supervise-pointX").val();
					var pointY = $("#supervise-pointY").val();
					var proviceid = $("#supervise-proviceid").val();
					var cityid = $("#supervise-cityid").val();
                    var regionid = $("#supervise-regionid").val();
                    var townid = $("#supervise-townid").val();
					var email = $("#supervise-email").val();
					var unitLogo = $("#supervise-picurls-unitLogo").attr("file_string");

					if (unitCode == "") {
						layer.msg("请输入单位编号");
						return;
					}
					if (unitName == "") {
						layer.msg("请输入单位名称");
						return;
					}
					var editId = $("#editId").val();
					if (editId == 0) {
						var url = baseUrl + "/maintenanceunit/addMaintenanceUnit"; //保存监管
						var data = {
							mainType: 2,
							unitcode: unitCode,
							unitname: unitName,
							address: address,
							contacts: contacts,
							telephone: telephone,
							remark: remark,
							pointx: pointX,
							pointy: pointY,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							logourl: unitLogo,
							email: email
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
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					} else {
						var url = baseUrl + "/maintenanceunit/updateMaintenanceUnit"; //编辑
						var data = {
							mainType: 2,
							id: id,
							unitcode: unitCode,
							unitname: unitName,
							systemNumber: systemNumber,
							address: address,
							contacts: contacts,
							telephone: telephone,
							remark: remark,
							pointx: pointX,
							pointy: pointY,
							proviceid: proviceid,
							cityid: cityid,
                            regionid: regionid,
                            townid: townid,
							logourl: unitLogo,
							email: email
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
									$("#newEmploy").modal("hide");
								} else {
									layer.msg(d.msg);
								}
							},
							error: function (data) {

							}
						});
					}
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

	$("#menuTypeLW").change(function () {
		emptySearch();
		if ($(this).val() == 'unitType') {
			$("#commonInputLW").html(html);
		} else {
			var id = $(this).val();
			if (id == "") {
				$("#userTable").bootstrapTable('refresh');
			}
			if (!$('#searchContentLW').hasClass('showInput')) {
				$("#commonInputLW")
					.html(
						'<input type="text" class="form-control showInput" name="searchContentLW" id="searchContentLW">');
			}
		}
	});

	$("#menuTypeWB").change(function () {
		emptySearch();
		var id = $(this).val();
		if (id == "") {
			$("#userTable").bootstrapTable('refresh');
		}
		if (!$('#searchContentWB').hasClass('showInput')) {
			$("#commonInputWB")
				.html(
					'<input type="text" class="form-control showInput" name="searchContentWB" id="searchContentWB">');
		}
	});

	$("#menuTypeJG").change(function () {
		emptySearch();
		var id = $(this).val();
		if (id == "") {
			$("#userTable").bootstrapTable('refresh');
		}
		if (!$('#searchContentJG').hasClass('showInput')) {
			$("#commonInputJG")
				.html(
					'<input type="text" class="form-control showInput" name="searchContentJG" id="searchContentJG">');
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
	// emptySearch();
});

$(".ordinarySearch").click(function () {
	if(mainType == 0){
		var id = $("#menuTypeLW").val();
		var value = $("#searchContentLW").val();
		$("#" + id).val(value);
	}else if(mainType == 1){
		var id = $("#menuTypeWB").val();
		var value = $("#searchContentWB").val();
		$("#" + id).val(value);
	}else if(mainType == 2){
		var id = $("#menuTypeJG").val();
		var value = $("#searchContentJG").val();
		$("#" + id).val(value);
	}
	// $("#userTable").bootstrapTable('destroy');
	mainSearch();
});


function emptySearch() {
	$("#JGunitName").val('');
	$("#JGunitCode").val('');
	$("#unitName_top").val('');
	$("#unitCode_top").val('');
	$("#unitType").val('');
	$("#Search-unitName").val('');
	$("#Search-unitCode").val('');
	$("#searchContentLW").val('');
	$("#searchContentWB").val('');
	$("#searchContentJG").val('');
}

// 清空新增和编辑的省市区街道
function emptyCityRegionTown(){
	$("#proviceid").html('<option value="" selected>请选择</option>');
	$("#cityid").html('<option value="" selected>请选择</option>');
	$("#regionid").html('<option value="" selected>请选择</option>');
	$("#townid").html('<option value="" selected>请选择</option>');
	$("#newUnit-proviceid").html('<option value="" selected>请选择</option>');
	$("#newUnit-cityid").html('<option value="" selected>请选择</option>');
	$("#newUnit-regionid").html('<option value="" selected>请选择</option>');
	$("#newUnit-townid").html('<option value="" selected>请选择</option>');
	$("#supervise-proviceid").html('<option value="" selected>请选择</option>');
	$("#supervise-cityid").html('<option value="" selected>请选择</option>');
	$("#supervise-regionid").html('<option value="" selected>请选择</option>');
	$("#supervise-townid").html('<option value="" selected>请选择</option>');
}

// 清空查看的省市区街道
function emptyCityRegionTownView(){
	$("#proviceid-View").html('<option value="" selected>请选择</option>');
	$("#cityid-View").html('<option value="" selected>请选择</option>');
	$("#regionid-View").html('<option value="" selected>请选择</option>');
	$("#townid-View").html('<option value="" selected>请选择</option>');
	$("#newUnit-view-proviceid").html('<option value="" selected>请选择</option>');
	$("#newUnit-view-cityid").html('<option value="" selected>请选择</option>');
	$("#newUnit-view-regionid").html('<option value="" selected>请选择</option>');
	$("#newUnit-view-townid").html('<option value="" selected>请选择</option>');
	$("#supervise-proviceid-View").html('<option value="" selected>请选择</option>');
	$("#supervise-cityid-View").html('<option value="" selected>请选择</option>');
	$("#supervise-regionid-View").html('<option value="" selected>请选择</option>');
	$("#supervise-townid-View").html('<option value="" selected>请选择</option>');
}

// 获取联网单位的全部列表数据
function getInfoUnitList(row) {
    var id = row.id;
    $.ajax({
        url: baseUrl + '/baseInfo/baseInfoListById',
        type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		dataType: "json",
		async: false,
        data: {
            id: id
        },
        success: function(data){
            if(data.success){
            	var _that = data.obj;
                editBaseInfo0(_that[0]);
            }
        }

    })
}
//联网单位的新的编辑方法
function editBaseInfo0(_this) {
	emptyCityRegionTown();
    if (mainType == 0) { //联网单位
        $('.modalLW').show().siblings('.modalWB').hide().siblings('.modalJG').hide(); //显示维保新增隐藏联网和监管编辑窗口
        $("#newEmploy").modal("show");
        $("#newEmploy .modal-title").html("编辑");
        $("#newEmploy .btnSure").show();
        var id = _this.id;
        $("#id").val(id);
        $("#editId").val("1");
        $(".password").hide();
        // citySelect(_this);
        var arr = _this.maintenanceunitid.split(',');
        $("#newUnit-maintenanceUnit").val(arr);
        $("#newUnit-maintenanceUnit").selectpicker('val', arr);
        $("#newUnit-maintenanceUnit").siblings(".btn-group").find(".btn .filter-option").html(_this.maintenanceunitName);

		$("#newUnit-maintenanceUnit").next("div").on("click",function () {
			$.ajax({
				url: baseUrl + "/baseInfo/selectAllUnitById",
				type: "post",
				beforeSend: function(request){
					request.setRequestHeader("Authorization","Bearer "+login.token);
				},
				data: {
					id: id
				},
				dataType: "json",
				success: function (data) {
					if(data.success){
						var arr1 = [];
						var arr2 = [];
						var objList = data.obj;
						for(var i=0;i<objList.length;i++){
							arr1.push(objList[i].id);
							arr2.push(objList[i].maintenanceunitName);
						}
						arr2 = arr2.join(',');
						$("#newUnit-maintenanceUnit").val(arr1);
						$("#newUnit-maintenanceUnit").selectpicker('val', arr1);
						$("#newUnit-maintenanceUnit").siblings(".btn-group").find(".btn .filter-option").html(arr2);

					}
				}

			})
		});

        $("#newUnit-unitcode").val(_this.unitcode);
        $("#newUnit-unitname").val(_this.unitname);
        $("#newUnit-systemNumber").val(_this.systemNumber);
        $("#newUnit-email").val(_this.email);
        $("#newUnit-unittype").val(_this.unittype);
        $("#newUnit-unitaddress").val(_this.unitaddress);
        $("#newUnit-phone").val(_this.phone);
        $("#newUnit-unitsummary").val(_this.unitsummary);
        $("#newUnit-technicalrepresentative").val(_this.technicalrepresentative);

        $("#newUnit-postcode").val(_this.postcode);
        $("#newUnit-fireroomphone").val(_this.fireroomphone);
        $("#newUnit-saferesponsiblepersonname")
            .val(_this.saferesponsiblepersonname);
        $("#newUnit-saferesponsiblepersoncard")
            .val(_this.saferesponsiblepersoncard);
        $("#newUnit-saferesponsiblepersonphone").val(
            _this.saferesponsiblepersonphone);
        $("#newUnit-safemanagername").val(_this.safemanagername);
        $("#newUnit-safemanagercard").val(_this.safemanagercard);
        $("#newUnit-safemanagerphone").val(_this.safemanagerphone);
        $("#newUnit-assistmanagername").val(_this.assistmanagername);
        $("#newUnit-assistmanagercard").val(_this.assistmanagercard);
        $("#newUnit-assistmanagerphone").val(_this.assistmanagerphone);
        $("#newUnit-legalpersonname").val(_this.legalpersonname);
        $("#newUnit-legalppersoncard").val(_this.legalppersoncard);
        $("#newUnit-legalpersonphone").val(_this.legalpersonphone);
        $("#newUnit-staffcount").val(_this.staffcount);
        $("#newUnit-establishmenttime").val(_this.establishmenttime);
        $("#newUnit-superiorunit").val(_this.superiorunit);
        $("#newUnit-administunit").val(_this.administunit);
        $("#newUnit-economictype").val(_this.economictype);
        $("#newUnit-fixedassets").val(_this.fixedassets);
        $("#newUnit-area").val(_this.area);
        $("#newUnit-buildingarea").val(_this.buildingarea);
        $("#newUnit-onlinedate").val(_this.onlinedate);
        $("#newUnit-superviselevel").val(_this.superviselevel);
        $("#newUnit-supervisetype").val(_this.supervisetype);
        $("#newUnit-belongcentername").val(_this.belongcentername);
        $("#newUnit-userrepresentative").val(_this.userrepresentative);
        $("#newUnit-unitpoint").val(_this.unitpoint);
        $("#newUnit-addressdetial").val(_this.addressdetial);
        $("#newUnit-datafrom").val(_this.datafrom);
        $("#newUnit-datafromid").val(_this.datafromid);
    /*    $("#newUnit-autocalldelay").val(_this.autocalldelay);*/
        $("#newUnit-callTotal").val(_this.callTotal);
        $("#newUnit-videoAppkey").val(_this.videoAppkey);
        $("#newUnit-videoAppsecret").val(_this.videoAppsecret);
        if (_this.isautocall == 1) {
            $("#newUnit-isautocall1").prop("checked", "checked");
            $("#newUnit-isautocall0").removeAttr("checked");
        } else {
            $("#newUnit-isautocall0").prop("checked", "checked");
            $("#newUnit-isautocall1").removeAttr("checked");
        }
        // radiobox
        var isNetwork = _this.networkstatus;
        if (isNetwork == 1) {
            $("#newUnit-networkstatus1").prop("checked", "checked");
            $("#newUnit-networkstatus0").removeAttr("checked");
        } else {
            $("#newUnit-networkstatus0").prop("checked", "checked");
            $("#newUnit-networkstatus1").removeAttr("checked");
        }
        var isImportant = _this.important;
        if (isImportant == 1) {
            $("#newUnit-important1").prop("checked", "checked");
            $("#newUnit-important0").removeAttr("checked");
        } else {
            $("#newUnit-important0").prop("checked", "checked");
            $("#newUnit-important1").removeAttr("checked");
        }

        $("#newUnit-importantid").val(_this.importantid);
        $("#newUnit-socialcreditcode").val(_this.socialcreditcode);
        $("#newUnit-propertyunitname").val(_this.propertyunitname);
        $("#newUnit-lzscore").val(_this.lzscore);
        $("#newUnit-lzscorelevel").val(_this.lzscorelevel);
        $("#newUnit-firerating").val(_this.firerating);
        $("#newUnit-firecount").val(_this.firecount);
        $("#newUnit-link").val(_this.unitlink);
        $("#newUnit-watercount").val(_this.watercount);
        $("#newUnit-videocount").val(_this.videocount);
        $("#newUnit-electriccount").val(_this.electriccount);
        $("#newUnit-unitdangerlevel").val(_this.unitdangerlevel);

		if(_this.proviceid != ""){
			provinceSelect('newUnit-proviceid',_this.proviceid);
		}else{
			provinceSelect('newUnit-proviceid','');
		}

		if(_this.cityid != ""){
			citySelect('newUnit-cityid','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('newUnit-cityid','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('newUnit-regionid','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('newUnit-regionid','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('newUnit-townid','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('newUnit-townid','4',_this.regionid,"");
		}

        // $("#newUnit-proviceid").val(_this.proviceid);
        // $("#newUnit-cityid").val(_this.cityid);
        // regionSelect(_this);
        // $("#newUnit-regionid").val(_this.regionid);
        // $("#newUnit-townid").val(_this.townid);

        $("#newUnit-childstationnum").val(_this.childstationnum);
        $("#newUnit-inductionpointcount").val(_this.inductionpointcount);
        $("#newUnit-monitorunitname").val(_this.monitorunitname);
        $("#newUnit-monitorunituser").val(_this.monitorunituser);
        $("#newUnit-monitorunitphone").val(_this.monitorunitphone);
        $("#newUnit-advancereminderday").val(_this.advancereminderday);
        $("#newUnit-messagephone").val(_this.messagephone);
        $("#newUnit-messagenumber").val(_this.messagenumber);
        $("#newUnit-autoCallNum").val(_this.autoCallNum);
        $("#newUnit-callTotal").val(_this.callTotal);
        $("#newUnit-OrgCode").val(_this.orgCode);
        $("input[name='ReceiveAlarmType']").removeAttr("checked");
        var receivealarmtype = _this.receivealarmtype;
        if (receivealarmtype != null && receivealarmtype != '') {
            var arr = receivealarmtype.split(",");
            for (var i = 0; i < arr.length; i++) {
                $("input[name='ReceiveAlarmType'][value=" + arr[i] + "]").prop("checked", "checked");
            }
        }

        $("input[name='callAlarmType']").removeAttr("checked");
        var callAlarmType = _this.callAlarmType;
        if (callAlarmType != null && callAlarmType != '') {
            var arr = callAlarmType.split(",");
            for (var i = 0; i < arr.length; i++) {
                $("input[name='callAlarmType'][value=" + arr[i] + "]").prop("checked", "checked");
            }
        }


        var xfzimg = _this.xfzimg;
        if (xfzimg) {
            $("#picurls-xfzimg").attr("file_string", xfzimg);
            $("#loadImg-xfzimg")
                .html(
                    "<li class='modalImg'><span>请选择上传图片</span></li>" +
                    " <li style='position: relative;margin: 0px;border: none;'> " +
                    "  <img src='" +
                    xfzimg +
                    "' " +
                    " style = 'width:100%;height:100%;'/>" +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    " </a>" + " </li>");
        } else {
            $("#picurls-xfzimg").attr("file_string", "");
            $("#loadImg-xfzimg").html(
                "<li class='modalImg'><span>请选择上传图片</span></li>");
        }

        var unitbg = _this.unitbg;
        if (unitbg) {
            $("#picurls-unitbg").attr("file_string", unitbg);
            $("#loadImg-unitbg")
                .html(
                    "<li class='modalImg'><span>请选择上传图片</span></li>" +
                    " <li style='position: relative;margin: 0px;border: none;'> " +
                    "  <img src='" +
                    unitbg +
                    "' " +
                    " style = 'width:100%;height:100%;'/>" +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    " </a>" + " </li>");
        } else {
            $("#picurls-unitbg").attr("file_string", "");
            $("#loadImg-unitbg").html(
                "<li class='modalImg'><span>请选择上传图片</span></li>");
        }

        var svgfile = _this.svgfile;
        if (svgfile) {
            $("#picurls-svgfile").attr("file_string", svgfile);
            $("#loadImg-svgfile")
                .html(
                    "<li class='modalImg'><span>请选择上传图片</span></li>" +
                    " <li style='position: relative;margin: 0px;border: none;'> " +
                    "  <img src='" +
                    svgfile +
                    "' " +
                    " style = 'width:100%;height:100%;'/>" +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    " </a>" + " </li>");
        } else {
            $("#picurls-svgfile").attr("file_string", "");
            $("#loadImg-svgfile").html(
                "<li class='modalImg'><span>请选择上传图片</span></li>");
        }

        var unitproximity = _this.unitproximity;
        if (unitproximity) {
            $("#picurls-unitproximity").attr("file_string", unitproximity);
            $("#loadImg-unitproximity")
                .html(
                    "<li class='modalImg'><span>请选择上传图片</span></li>" +
                    " <li style='position: relative;margin: 0px;border: none;'> " +
                    "  <img src='" +
                    unitproximity +
                    "' " +
                    " style = 'width:100%;height:100%;'/>" +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    " </a>" + " </li>");
        } else {
            $("#picurls-unitproximity").attr("file_string", "");
            $("#loadImg-unitproximity").html(
                "<li class='modalImg'><span>请选择上传图片</span></li>");
        }

        var logourl = _this.logourl;
        if (logourl) {
            $("#picurls-unitLogo").attr("file_string", logourl);
            $("#loadImg-unitLogo")
                .html(
                    "<li class='modalImg'><span>请选择上传图片</span></li>" +
                    " <li style='position: relative;margin: 0px;border: none;'> " +
                    "  <img src='" +
                    logourl +
                    "' " +
                    " style = 'width:100%;height:100%;'/>" +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    " </a>" + " </li>");
        } else {
            $("#picurls-unitLogo").attr("file_string", "");
            $("#loadImg-unitLogo").html(
                "<li class='modalImg'><span>请选择上传图片</span></li>");
        }

        var attachment = _this.xfcontractbook;
        var attachmentname = _this.xfcontractbookname;
        if (attachment) {
            $(".fileName")
                .html(
                    attachmentname +
                    '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                    "<img style='width: 15px;height: 15px;' src='/static/image/mima.png' title='删除' class='delete'/>" +
                    " </a>" + '<a target="_blank" href="' +
                    baseUrl + attachment + '">点击下载</a>');
            $("#attachment").val(attachment);
            $("#attachmentName").val(attachmentname);
        } else {
            $(".fileName").html("");
            $("#attachment").val("");
            $("#attachmentName").val("");
        }
        /*
         * if(userHeader){ $("#picurls").attr("file_string",userHeader);
         * $("#loadImg").html( "<li class='modalImg'><span>请选择上传图片</span></li>"+ "
         * <li style='position: relative;margin: 0px;border: none;'> " + " <img
         * src='" + userHeader + "' " + " style = 'width:100%;height:100%;'/>" + '<a
         * onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;"
         * class="reg_btn_yzm reg_btn_yzm_display settled_up input-file">' + "
         * </a>"+ " </li>" ); }else{ $("#picurls").attr("file_string","");
         * $("#loadImg").html("<li class='modalImg'><span>请选择上传图片</span></li>"); }
         */
    }
}

// 编辑
function editBaseInfo(_this) {
	emptyCityRegionTown();
	if(mainType == 1){ //维保单位
		$('.modalWB').show().siblings('.modalLW').hide().siblings('.modalJG').hide(); //显示维保新增隐藏联网和监管编辑窗口
		$("#newEmploy").modal("show");
		$("#newEmploy .modal-title").html("编辑单位");
		$("#newEmploy .btnSure").show();
		var id = _this.id;
		$("#indexId").val(id);
		$("#editId").val("1");
		$("#unitCode").val(_this.unitcode);
		$("#unitName").val(_this.unitname);
		$("#systemNumber").val(_this.systemNumber);
		$("#address").val(_this.address);
		$("#contacts").val(_this.contacts);
		$("#telephone").val(_this.telephone);

		if(_this.proviceid != ""){
			provinceSelect('proviceid',_this.proviceid);
		}else{
			provinceSelect('proviceid','');
		}

		if(_this.cityid != ""){
			citySelect('cityid','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('cityid','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('regionid','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('regionid','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('townid','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('townid','4',_this.regionid,"");
		}

		$("#email").val(_this.email);
		$("#remark").val(_this.remark);
		$("#pointX").val(_this.pointx);
		$("#pointY").val(_this.pointy);

		var logourl = _this.logourl;
		if (logourl) {
			$("#networking-picurls-unitLogo").attr("file_string", logourl);
			$("#networking-loadImg-unitLogo")
				.html(
					"<li class='modalImg'><span>请选择上传图片</span></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" +
					logourl +
					"' " +
					" style = 'width:100%;height:100%;'/>" +
					'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
					" </a>" + " </li>");
		} else {
			$("#networking-picurls-unitLogo").attr("file_string", "");
			$("#networking-loadImg-unitLogo").html(
				"<li class='modalImg'><span>请选择上传图片</span></li>");
		}
	} else if(mainType == 2){ //监管单位
		$('.modalJG').show().siblings('.modalLW').hide().siblings('.modalWB').hide(); //显示维保新增隐藏联网和监管编辑窗口
		$("#newEmploy").modal("show");
		$("#newEmploy .modal-title").html("编辑单位");
		$("#newEmploy .btnSure").show();
		var id = _this.id;
		$("#supervise-indexId").val(id);
		$("#editId").val("1");
		// citySelect3(_this);
		$("#supervise-unitCode").val(_this.unitcode);
		$("#supervise-unitName").val(_this.unitname);
		$("#supervise-systemNumber").val(_this.systemNumber);
		$("#supervise-address").val(_this.address);
		$("#supervise-contacts").val(_this.contacts);
		$("#supervise-telephone").val(_this.telephone);

		if(_this.proviceid != ""){
			provinceSelect('supervise-proviceid',_this.proviceid);
		}else{
			provinceSelect('supervise-proviceid','');
		}

		if(_this.cityid != ""){
			citySelect('supervise-cityid','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('supervise-cityid','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('supervise-regionid','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('supervise-regionid','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('supervise-townid','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('supervise-townid','4',_this.regionid,"");
		}

		$("#supervise-email").val(_this.email);
		$("#supervise-remark").val(_this.remark);
		$("#supervise-pointX").val(_this.pointx);
		$("#supervise-pointY").val(_this.pointy);

		var logourl = _this.logourl;
		if (logourl) {
			$("#supervise-picurls-unitLogo").attr("file_string", logourl);
			$("#supervise-loadImg-unitLogo")
				.html(
					"<li class='modalImg'><span>请选择上传图片</span></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" +
					logourl +
					"' " +
					" style = 'width:100%;height:100%;'/>" +
					'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
					" </a>" + " </li>");
		} else {
			$("#supervise-picurls-unitLogo").attr("file_string", "");
			$("#supervise-loadImg-unitLogo").html(
				"<li class='modalImg'><span>请选择上传图片</span></li>");
		}
	}

}
// 查看时获取练完单位的所有数据
function viewInfoUnitList(row) {
	var id = row.id;
	$.ajax({
		url: baseUrl + '/baseInfo/baseInfoListById',
		type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
        dataType: "json",
		data: {
			id: id
		},
		success: function(data){
			if(data.success){
			    var _that = data.obj;
				viewBaseInfo0(_that[0]);
			}
		}
	})
}
function viewBaseInfo0(_this) {
	emptyCityRegionTown();
	if (mainType == 0) { //联网单位
		$("#newEmployView").modal("show");
		$("#firstViewModal").show().siblings("#wrapModalCheck").hide().siblings("#superviseView").hide();
		var id = _this.id;
		$("#id").val(id);
		$("#editId").val("1");
		$(".password").hide();
		$(".btnSure").hide();
		// citySelect(_this);
        var arr = _this.maintenanceunitid.split(',');
		$("#newUnit-view-maintenanceUnit").val(arr);
        $("#newUnit-view-maintenanceUnit").selectpicker('val', arr);
        $("#newUnit-view-maintenanceUnit").siblings(".btn-group").find(".btn .filter-option").html(_this.maintenanceunitName);

		$("#newUnit-view-maintenanceUnit").next("div").on("click",function () {
			$.ajax({
				url: baseUrl + "/baseInfo/selectAllUnitById",
				type: "post",
				beforeSend: function(request){
					request.setRequestHeader("Authorization","Bearer "+login.token);
				},
				data: {
					id: id
				},
				dataType: "json",
				success: function (data) {
					if(data.success){
						var arr1 = [];
						var arr2 = [];
						var objList = data.obj;
						for(var i=0;i<objList.length;i++){
							arr1.push(objList[i].id);
							arr2.push(objList[i].maintenanceunitName);
						}
						arr2 = arr2.join(',');
						$("#newUnit-view-maintenanceUnit").val(arr1);
						$("#newUnit-view-maintenanceUnit").selectpicker('val', arr1);
						$("#newUnit-view-maintenanceUnit").siblings(".btn-group").find(".btn .filter-option").html(arr2);

					}
				}

			})
		});

		$("#newUnit-view-unitcode").val(_this.unitcode);
		$("#newUnit-view-unitname").val(_this.unitname);
		$("#newUnit-view-systemNumber").val(_this.systemNumber);
		$("#newUnit-view-email").val(_this.email);
		$("#newUnit-view-unittype").val(_this.unittype);
		$("#newUnit-view-unitaddress").val(_this.unitaddress);
		$("#newUnit-view-phone").val(_this.phone);
		$("#newUnit-view-unitsummary").val(_this.unitsummary);
		$("#newUnit-view-technicalrepresentative").val(
			_this.technicalrepresentative);

		$("#newUnit-view-postcode").val(_this.postcode);
		$("#newUnit-view-fireroomphone").val(_this.fireroomphone);
		$("#newUnit-view-saferesponsiblepersonname").val(
			_this.saferesponsiblepersonname);
		$("#newUnit-view-saferesponsiblepersoncard").val(
			_this.saferesponsiblepersoncard);
		$("#newUnit-view-saferesponsiblepersonphone").val(
			_this.saferesponsiblepersonphone);
		$("#newUnit-view-safemanagername").val(_this.safemanagername);
		$("#newUnit-view-safemanagercard").val(_this.safemanagercard);
		$("#newUnit-view-safemanagerphone").val(_this.safemanagerphone);
		$("#newUnit-view-assistmanagername").val(_this.assistmanagername);
		$("#newUnit-view-assistmanagercard").val(_this.assistmanagercard);
		$("#newUnit-view-assistmanagerphone").val(_this.assistmanagerphone);
		$("#newUnit-view-legalpersonname").val(_this.legalpersonname);
		$("#newUnit-view-legalppersoncard").val(_this.legalppersoncard);
		$("#newUnit-view-legalpersonphone").val(_this.legalpersonphone);
		$("#newUnit-view-staffcount").val(_this.staffcount);
		$("#newUnit-view-establishmenttime").val(_this.establishmenttime);
		$("#newUnit-view-superiorunit").val(_this.superiorunit);
		$("#newUnit-view-administunit").val(_this.administunit);
		$("#newUnit-view-economictype").val(_this.economictype);
		$("#newUnit-view-fixedassets").val(_this.fixedassets);
		$("#newUnit-view-area").val(_this.area);
		$("#newUnit-view-buildingarea").val(_this.buildingarea);
		$("#newUnit-view-onlinedate").val(_this.onlinedate);
		$("#newUnit-view-superviselevel").val(_this.superviselevel);
		$("#newUnit-view-supervisetype").val(_this.supervisetype);
		$("#newUnit-view-belongcentername").val(_this.belongcentername);
		$("#newUnit-view-userrepresentative").val(_this.userrepresentative);
		$("#newUnit-view-unitpoint").val(_this.unitpoint);
		$("#newUnit-view-addressdetial").val(_this.addressdetial);
		$("#newUnit-view-datafrom").val(_this.datafrom);
		$("#newUnit-view-datafromid").val(_this.datafromid);
		$("#newUnit-view-autocalldelay").val(_this.autocalldelay);
        $("#newUnit-view-callTotal").val(_this.callTotal);
        $("#newUnit-view-videoAppkey").val(_this.videoAppkey);
        $("#newUnit-view-videoAppsecret").val(_this.videoAppsecret);
		if (_this.isautocall == 1) {
			$("#newUnit-view-isautocall1").prop("checked", "checked");
			$("#newUnit-view-isautocall0").removeAttr("checked");
		} else {
			$("#newUnit-view-isautocall0").prop("checked", "checked");
			$("#newUnit-view-isautocall1").removeAttr("checked");
		}

		// radiobox
		var isNetwork = _this.networkstatus;
		if (isNetwork == 1) {
			$("#newUnit-view-networkstatus1").prop("checked", "checked");
			$("#newUnit-view-networkstatus0").removeAttr("checked");
		} else {
			$("#newUnit-view-networkstatus0").prop("checked", "checked");
			$("#newUnit-view-networkstatus1").removeAttr("checked");
		}
		var isImportant = _this.important;
		if (isImportant == 1) {
			$("#newUnit-view-important1").prop("checked", "checked");
			$("#newUnit-view-important0").removeAttr("checked");
		} else {
			$("#newUnit-view-important0").prop("checked", "checked");
			$("#newUnit-view-important1").removeAttr("checked");
		}

		$("#newUnit-view-importantid").val(_this.importantid);
		$("#newUnit-view-socialcreditcode").val(_this.socialcreditcode);
		$("#newUnit-view-propertyunitname").val(_this.propertyunitname);
		$("#newUnit-view-lzscore").val(_this.lzscore);
		$("#newUnit-view-lzscorelevel").val(_this.lzscorelevel);
		$("#newUnit-view-firerating").val(_this.firerating);
		$("#newUnit-view-firecount").val(_this.firecount);
		$("#newUnit-view-link").val(_this.unitlink);
		$("#newUnit-view-watercount").val(_this.watercount);
		$("#newUnit-view-videocount").val(_this.videocount);
		$("#newUnit-view-electriccount").val(_this.electriccount);
		$("#newUnit-view-unitdangerlevel").val(_this.unitdangerlevel);

		if(_this.proviceid != ""){
			provinceSelect('newUnit-view-proviceid',_this.proviceid);
		}else{
			provinceSelect('newUnit-view-proviceid','');
		}

		if(_this.cityid != ""){
			citySelect('newUnit-view-cityid','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('newUnit-view-cityid','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('newUnit-view-regionid','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('newUnit-view-regionid','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('newUnit-view-townid','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('newUnit-view-townid','4',_this.regionid,"");
		}

		// $("#newUnit-view-proviceid").val(_this.proviceid);
		// $("#newUnit-view-cityid").val(_this.cityid);
		// regionSelect(_this);
        // $("#newUnit-view-regionid").val(_this.regionid);
        // $("#newUnit-view-townid").val(_this.townid);
		$("#newUnit-view-childstationnum").val(_this.childstationnum);
		$("#newUnit-view-inductionpointcount").val(_this.inductionpointcount);
		$("#newUnit-view-monitorunitname").val(_this.monitorunitname);
		$("#newUnit-view-monitorunituser").val(_this.monitorunituser);
		$("#newUnit-view-monitorunitphone").val(_this.monitorunitphone);
		$("#newUnit-view-advancereminderday").val(_this.advancereminderday);
		$("#newUnit-view-messagephone").val(_this.messagephone);
		$("#newUnit-view-messagenumber").val(_this.messagenumber);
		$("#newUnit-view-autoCallNum").val(_this.autoCallNum);
        $("#newUnit-view-callTotal").val(_this.callTotal);
        $("#newUnit-view-OrgCode").val(_this.orgCode);
        $("input[name='ReceiveAlarmType']").removeAttr("checked");
		var receivealarmtype = _this.receivealarmtype;
		if (receivealarmtype != null && receivealarmtype != '') {
			var arr = receivealarmtype.split(",");
			for (var i = 0; i < arr.length; i++) {
				$("input[name='ReceiveAlarmTypeView'][value=" + arr[i] + "]").prop("checked", "checked");
			}
		}


        $("input[name='callAlarmType']").removeAttr("checked");
        var callAlarmType = _this.callAlarmType;
        if (callAlarmType != null && callAlarmType != '') {
            var arr = callAlarmType.split(",");
            for (var i = 0; i < arr.length; i++) {
                $("input[name='callAlarmTypeView'][value=" + arr[i] + "]").prop("checked", "checked");
            }
        }




		var xfzimg = _this.xfzimg;
		if (xfzimg) {
			$("#loadImg-view-xfzimg")
				.html(
					"<li></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + xfzimg + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#loadImg-view-xfzimg").html("<li class=''></li>");
		}

		var unitbg = _this.unitbg;
		if (unitbg) {
			$("#loadImg-view-UnitBg")
				.html(
					"<li></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + unitbg + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#loadImg-view-UnitBg").html("<li></li>");
		}

		var svgfile = _this.svgfile;
		if (svgfile) {
			$("#loadImg-view-SvgFile")
				.html(
					"<li ></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + svgfile + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#loadImg-view-SvgFile").html("<li></li>");
		}

		var unitproximity = _this.unitproximity;
		if (unitproximity) {
			$("#loadImg-view-unitproximity")
				.html(
					"<li ></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + unitproximity + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#loadImg-view-unitproximity").html("<li></li>");
		}

		var logourl = _this.logourl;
		if (logourl) {
			$("#loadImg-view-unitLogo")
				.html(
					"<li ></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + logourl + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#loadImg-view-unitLogo").html("<li></li>");
		}

		var attachment = _this.xfcontractbook;
		var attachmentname = _this.xfcontractbookname;
		if (attachment) {
			$(".fileNameView")
				.html(
					attachmentname +
					'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
					" </a>" + '<a target="_blank" href="' +
					baseUrl + attachment + '">点击下载</a>');
			$("#attachmentView").val(attachment);
			$("#attachmentNameView").val(attachmentname);
		} else {
			$(".fileName").html("");
			$("#attachment").val("");
			$("#attachmentName").val("");
		}

		var attachment = _this.xfcontractbook;
		var attachmentname = _this.xfcontractbookname;
		if (attachment) {
			$(".fileName")
				.html(
					attachmentname +
					'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
					" </a>" + '<a target="_blank" href="' +
					baseUrl + attachment + '">点击下载</a>');
		} else {
			$(".fileName").html("");
		}
	}
}
// 查看
function viewBaseInfo(_this) {
	emptyCityRegionTownView();
	if(mainType == 1){ //维保单位
		$("#newEmployView").modal("show");
		$("#wrapModalCheck").show().siblings("#firstViewModal").hide().siblings("#superviseView").hide();
		$("#unitCode-View").val(_this.unitcode);
		$("#unitName-View").val(_this.unitname);
		$("#systemNumber-View").val(_this.systemNumber);
		$("#address-View").val(_this.address);
		$("#contacts-View").val(_this.contacts);
		$("#telephone-View").val(_this.telephone);

		if(_this.proviceid != ""){
			provinceSelect('proviceid-View',_this.proviceid);
		}else{
			provinceSelect('proviceid-View','');
		}

		if(_this.cityid != ""){
			citySelect('cityid-View','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('cityid-View','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('regionid-View','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('regionid-View','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('townid-View','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('townid-View','4',_this.regionid,"");
		}

		$("#proviceid-View").val(_this.proviceid);
		//$("#cityid-View").val(_this.cityid);
        //$("#regionid-View").val(_this.regionid);
        //$("#townid-View").val(_this.townid);
       /* citySelect2(_this);
        regionSelect2(_this);
        townidSelect2(_this);*/
        //console.log(_this)

		$("#email-View").val(_this.email);
		$("#remark-View").val(_this.remark);
		$("#pointX-View").val(_this.pointx);
		$("#pointY-View").val(_this.pointy);

		var logourl = _this.logourl;
		if (logourl) {
			$("#networking-loadImg-view-unitLogo")
				.html(
					"<li ></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + logourl + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#metworking-loadImg-view-unitLogo").html("<li></li>");
		}

	} else if(mainType == 2){ // 监管单位
		$("#newEmployView").modal("show");
		$("#superviseView").show().siblings("#firstViewModal").hide().siblings("#wrapModalCheck").hide();
		$("#supervise-unitCode-View").val(_this.unitcode);
		$("#supervise-unitName-View").val(_this.unitname);
		$("#supervise-systemNumber-View").val(_this.systemNumber);
		$("#supervise-address-View").val(_this.address);
		$("#supervise-contacts-View").val(_this.contacts);
		$("#supervise-telephone-View").val(_this.telephone);

		if(_this.proviceid != ""){
			provinceSelect('supervise-proviceid-View',_this.proviceid);
		}else{
			provinceSelect('supervise-proviceid-View','');
		}

		if(_this.cityid != ""){
			citySelect('supervise-cityid-View','2',_this.proviceid,_this.cityid);
		}else if(_this.cityid == "" && _this.proviceid != ""){
			citySelect('supervise-cityid-View','2',_this.proviceid,"");
		}

		if(_this.regionid != ""){
			citySelect('supervise-regionid-View','3',_this.cityid,_this.regionid);
		}else if(_this.regionid == "" && _this.cityid != ""){
			citySelect('supervise-regionid-View','3',_this.cityid,"");
		}

		if(_this.townid != ""){
			citySelect('supervise-townid-View','4',_this.regionid,_this.townid);
		}else if(_this.townid == "" && _this.regionid != ""){
			citySelect('supervise-townid-View','4',_this.regionid,"");
		}

		// $("#supervise-proviceid-View").val(_this.proviceid);
		// citySelect3(_this);
		// $("#supervise-cityid-View").val(_this.cityid);
		// regionSelect3(_this);
        // $("#supervise-regionid-View").val(_this.regionid);
        // $("#supervise-townid-View").val(_this.townid);
		$("#supervise-email-View").val(_this.email);
		$("#supervise-remark-View").val(_this.remark);
		$("#supervise-pointX-View").val(_this.pointx);
		$("#supervise-pointY-View").val(_this.pointy);

		var logourl = _this.logourl;
		if (logourl) {
			$("#supervise-loadImg-view-unitLogo")
				.html(
					"<li ></li>" +
					" <li style='position: relative;margin: 0px;border: none;'> " +
					"  <img src='" + logourl + "' " +
					" style = 'width:100%;height:100%;'/>" +
					" </li>");
		} else {
			$("#supervise-loadImg-view-unitLogo").html("<li></li>");
		}
	}

}

//删除
function deleteBaseInfo(_this) {
	var select = _this.id;
	if(mainType == 0){ //删除联网
		layer.open({
			content: '确认删除？',
			btn: ['确认', '取消'],
			yes: function () {
				$.ajax({
					url: baseUrl + "/baseInfo/deleteBaseInfo",
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
							layer.msg("删除成功！");
							$("#userTable").bootstrapTable('refresh');
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
	}else if(mainType == 1){ //删除维保
		layer.open({
			content : '确认删除？',
			btn : [ '确认', '取消' ],
			yes : function() {
				$.ajax({
					url : baseUrl + "/maintenanceunit/deleteMaintenanceUnit",
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
							layer.msg("删除成功！");
							$("#userTable").bootstrapTable('refresh');
						} else {
							layer.msg("删除失败！");
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
	}else if(mainType == 2){ //删除监管
		layer.open({
			content : '确认删除？',
			btn : [ '确认', '取消' ],
			yes : function() {
				$.ajax({
					url : baseUrl + "/maintenanceunit/deleteMaintenanceUnit",
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
							layer.msg("删除成功！");
							$("#userTable").bootstrapTable('refresh');
						} else {
							layer.msg("删除失败！");
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

}

// 绑定人员
function setRole(_this) {
	$("#setRoleModal").modal("show");
	$('#select1').empty();
	$('#select2').empty();
	var id = _this.id;
	$("#indexId").val(id);
	var url = baseUrl + '/baseInfo/getUserReUnit';
	$.ajax({
		async: true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			id: id
		},
		url: url,
		dataType: "json",
		success: function (d) {
			if (d.success) {
				var GetUserRolesData = d.obj;
				var hasRoles = GetUserRolesData.hasUsers;
				//console.log("hasRoles:" + hasRoles);
				if (hasRoles) {
					for (var i = 0; i < hasRoles.length; i++) {
						var option = ' <option id="' + hasRoles[i].id + '">' +
							hasRoles[i].userName + '</option>'
						$('#select2').append(option);
					}
				}
				var noRoles = GetUserRolesData.noUsers;
				if (noRoles) {
					for (var i = 0; i < noRoles.length; i++) {
						var option = ' <option id="' + noRoles[i].id + '">' +
							noRoles[i].userName + '</option>'
						$('#select1').append(option);
					}
				}
			}
		}
	});
}

$(".btn-save_role").click(function () {
	var unitId = $("#indexId").val();
	var ids = "";
	for (var i = 0; i < $("#select2 option").length; i++) {
		var id = $($("#select2 option").eq(i)).attr("id");
		ids += id + ",";
	}

	var url = baseUrl + '/baseInfo/setUserReUnit';
	$.ajax({
		type: "post",
		async: true,
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			unitId: unitId,
			manyUserId: ids
		},
		url: url,
		dataType: "json",
		success: function (d) {
			$("#setRoleModal").modal("hide");
			if (d.success) {
				layer.msg("设置成功。");
			} else {
				layer.msg("设置失败");
			}

		}
	});
});

// 绑定编号模态框
function bindNumber(_this) {
	$("#unitId").val(_this.id);
	$("#editId").val("");
	bindingNumberSearch(_this.id);
	$("#bindNumber").modal("show");
}

// 绑定编号
function bind() {
	var unitId = $("#unitId").val();
	var soureaddress = $("#soureaddress").val();
	// var editId = $("#editId").val();

	if (soureaddress.length != 12) {
		layer.msg("请输入12位编号");
		return;
	}

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
				$("#soureaddress").val("");
			} else {
				layer.msg(d.msg);
			}

		}
	});
}

// 编辑绑定编号
/*
 * function editBinding(_this){ var select = _this.id;
 * $("#indexId").val(select); $("#editId").val("1");
 * $("#soureaddress").val(_this.soureaddress); }
 */

// 删除绑定编号
function deleteBinding(_this) {
	var unitId = $("#unitId").val();
	var select = _this.id;
	layer.open({
		content: '确认删除选中编号信息？',
		btn: ['确认', '取消'],
		yes: function () {
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

// 主页面查询
function mainSearch() {
	$('#userTable').bootstrapTable('destroy');
	if (mainType == 0) {   //联网单位列表
		maintenanceUnitSelect();
		$('#userTable')
			.bootstrapTable({
				url : baseUrl + '/baseInfo/baseInfoList', // 请求后台的URL（*）
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
						unitname: $("#unitName_top").val(),
						unitcode: $("#unitCode_top").val(),
						unittype : $("#unitType").val(),
						id: $("#networkUnitId").val()
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
				showColumns: true, // 是否显示所有的列
				showRefresh: true, // 是否显示刷新按钮
				minimumCountColumns: 2, // 最少允许的列数
				clickToSelect: true, // 是否启用点击选中行
				singleSelect: false,
				// height: 570, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId: "id", // 每一行的唯一标识，一般为主键列
				// showToggle: true, //是否显示详细视图和列表视图的切换按钮
				cardView: false, // 是否显示详细视图
				detailView: false, // 是否显示父子表
				columns: [{
					field: 'selectItem',
					checkbox: true
				},
					{
						field: 'unitcode',
						title: '单位编号'
					},
					{
						field: 'unitname',
						title: '单位名称'
					},
					{
						field: 'phone',
						title: '联系电话'
					},
					{
						field: 'establishmenttime',
						title: '成立时间'
					},
					{
						field: '',
						title: '操作',
						formatter: function (value, row, index) {
							var str = "";
							// mainType = row.mainType; //字段待确认 0联网 1维保
							var unitId = $("#networkUnitId").val();

							str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>';
							str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>';

							if (unitId == null || unitId == "") {
								str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>';
								str += '<button type="button" class="btn btn-new btn-xs cBtn-main setRole" style="margin: 0px 10px 0px 0px;"><i class="fa fa-pencil"></i>&nbsp;绑定人员</button>';
								str += '<button type="button" class="btn btn-new btn-xs cBtn-main bindNumber"><i class="fa fa-pencil"></i>&nbsp;绑定编号</button>';
							}

							return str;

						},
						events: {
							'click .edit': function (e, value, row,
													 index) {
                                getInfoUnitList(row);
							},
							'click .del': function (e, value, row,
													index) {
								deleteBaseInfo(row);
							},
							'click .view': function (e, value, row,
													 index) {
								viewInfoUnitList(row);
							},
							'click .setRole': function (e, value,
														row, index) {
								setRole(row);
							},
							'click .bindNumber': function (e,
														   value, row, index) {
								bindNumber(row);
							}

						}
					}
				]
			});
	}

	else if (mainType == 1) {  //维保单位列表
		$('#userTable')
			.bootstrapTable(
				{
					url : baseUrl + '/maintenanceunit/getMaintenanceUnitList', // 请求后台的URL（*）
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
							mainType : 1,
							unitname : $("#Search-unitName").val(),
							unitcode : $("#Search-unitCode").val(),
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
					showColumns : true, // 是否显示所有的列
					showRefresh : true, // 是否显示刷新按钮
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
							field : 'selectItem',
							checkbox : true
						},
						{
							field : 'unitcode',
							title : '单位编号'
						},
						{
							field : 'unitname',
							title : '单位名称'
						},
						{
							field : 'contacts',
							title : '联系人'
						},
						{
							field : 'telephone',
							title : '联系电话'
						},
						{
							field : 'address',
							title : '单位地址'
						},
						{
							field : '',
							title : '操作',
							formatter : function(value, row, index) {
								var str = "";
								str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
								str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
								str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
								return str;
							},
							events : {
								'click .edit' : function(e, value, row, index) {
									editBaseInfo(row);
								},
								'click .del' : function(e, value, row, index) {
									deleteBaseInfo(row);
								},
								'click .view' : function(e, value, row, index) {
									viewBaseInfo(row);
								}
							}
						} ]
				});
	}

	else if(mainType == 2){  //监管单位列表
		$('#userTable').bootstrapTable({
			url : baseUrl + '/maintenanceunit/getMaintenanceUnitList', // 请求后台的URL（*）
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
					mainType : 2,
					unitname : $("#JGunitName").val(),
					unitcode : $("#JGunitCode").val(),
				};
				$("#pageSize").val(this.pageSize);
				$("#pageNumber").val(this.pageNumber);
				return param;},
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
			// height: 570, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "id", // 每一行的唯一标识，一般为主键列
			// showToggle: true, //是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			columns : [
				{
					field : 'selectItem',
					checkbox : true
				},
				{
					field : 'unitcode',
					title : '单位编号'
				},
				{
					field : 'unitname',
					title : '单位名称'
				},
				{
					field : 'contacts',
					title : '联系人'
				},
				{
					field : 'telephone',
					title : '联系电话'
				},
				{
					field : 'address',
					title : '单位地址'
				},
				{
					field : '',
					title : '操作',
					formatter : function(value, row, index) {
						var str = "";
						str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
						str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
						str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
						return str;
					},
					events : {
						'click .edit' : function(e, value, row, index) {
							editBaseInfo(row);
						},
						'click .del' : function(e, value, row, index) {
							deleteBaseInfo(row);
						},
						'click .view' : function(e, value, row, index) {
							viewBaseInfo(row);
						}
					}
				}
			]
		});
	}
}

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



// 初始化联网资源
function initProvince0() {

	$.ajax({
		url: baseUrl + "/code/getListByGroupKey",
		type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			codeGroupKey: "UnitType"
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newUnit-unittype").append(
						'<option value="' + data.obj[i].codeid + '">' +
						data.obj[i].codename + '</option>');
					$("#unitType").append(
						'<option value="' + data.obj[i].codeid + '">' +
						data.obj[i].codename + '</option>');
					$("#newUnit-view-unittype").append(
						'<option value="' + data.obj[i].codeid + '">' +
						data.obj[i].codename + '</option>');
					if(html.indexOf('<option value="' + data.obj[i].codeid + '">' + data.obj[i].codename + '</option>') == -1) {
                        html += '<option value="' + data.obj[i].codeid + '">' + data.obj[i].codename + '</option>';
					}
				}
			}
			html += '</select>';
		}
	});

	$.ajax({
		url: baseUrl + "/code/getListByGroupKey",
		type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			codeGroupKey: "SuperviseType"
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newUnit-supervisetype").append(
						'<option value="' + data.obj[i].codeid + '">' +
						data.obj[i].codename + '</option>');
					$("#newUnit-view-supervisetype").append(
						'<option value="' + data.obj[i].codeid + '">' +
						data.obj[i].codename + '</option>');
				}
			}
		}
	});

}

$("#newUnit-proviceid").on("change", function () {
	$("#newUnit-cityid").empty();
	$("#newUnit-cityid").append('<option value="">请选择</option>');
    $("#newUnit-regionid").empty();
    $("#newUnit-regionid").append('<option value="">请选择</option>');
    $("#newUnit-townid").empty();
    $("#newUnit-townid").append('<option value="">请选择</option>');
	var parentId = $("#newUnit-proviceid").val();
	//console.log('省份change');
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 2,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newUnit-cityid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#newUnit-view-cityid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
	// $.post(baseUrl + "/baseInfo/locateList", {
	// 	type: 2,
	// 	parentId: parentId
	// }, function (data) {
	// 	if (data.success) {
	// 		for (var i = 0; i < data.obj.length; i++) {
	// 			$("#newUnit-cityid").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 			$("#newUnit-view-cityid").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 		}
	// 	}
	// });
});

$("#newUnit-cityid").on("change",function () {
    $("#newUnit-regionid").empty();
    $("#newUnit-regionid").append('<option value="">请选择</option>');
    $("#newUnit-townid").empty();
    $("#newUnit-townid").append('<option value="">请选择</option>');
    var parentId = $("#newUnit-cityid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 3,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newUnit-regionid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#newUnit-view-regionid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
    // $.post(baseUrl + "/baseInfo/locateList", {
    //     type: 3,
    //     parentId: parentId
    // }, function (data) {
    //     if (data.success) {
    //         for (var i = 0; i < data.obj.length; i++) {
    //             $("#newUnit-regionid").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //             $("#newUnit-view-regionid").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //         }
    //     }
    // });
});
$("#newUnit-regionid").on("change",function () {
    $("#newUnit-townid").empty();
    $("#newUnit-townid").append('<option value="">请选择</option>');
    var parentId = $("#newUnit-regionid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 4,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newUnit-townid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#newUnit-view-townid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
    // $.post(baseUrl + "/baseInfo/locateList", {
    //     type: 4,
    //     parentId: parentId
    // }, function (data) {
    //     if (data.success) {
    //         for (var i = 0; i < data.obj.length; i++) {
    //             $("#newUnit-townid").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //             $("#newUnit-view-townid").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //         }
    //     }
    // });
});

$("#proviceid").on("change",function() {
	$("#cityid").empty();
	$("#cityid").append('<option value="">请选择</option>');
    $("#regionid").empty();
    $("#regionid").append('<option value="">请选择</option>');
    $("#townid").empty();
   $("#townid").append('<option value="">请选择</option>');
	var parentId = $("#proviceid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 2,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#cityid").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
					$("#cityid-View").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
				}
			}
		}
	});
	// $.post(baseUrl + "/baseInfo/locateList", {
	// 	type : 2,
	// 	parentId : parentId
	// }, function(data) {
	// 	if (data.success) {
	// 		for (var i = 0; i < data.obj.length; i++) {
	// 			$("#cityid").append(
	// 				'<option value="' + data.obj[i].id + '">'
	// 				+ data.obj[i].regionName
	// 				+ '</option>');
	// 			$("#cityid-View").append(
	// 				'<option value="' + data.obj[i].id + '">'
	// 				+ data.obj[i].regionName
	// 				+ '</option>');
	// 		}
	// 	}
	// });
});

$("#cityid").on("change",function() {
	$("#regionid").empty();
	$("#regionid").append('<option value="">请选择</option>');
    $("#townid").empty();
    $("#townid").append('<option value="">请选择</option>');
	var parentId = $("#cityid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 3,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#regionid").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
					$("#view-regionid").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
				}
			}
		}
	});
	// $.post(baseUrl + "/baseInfo/locateList", {
	// 	type : 3,
	// 	parentId : parentId
	// }, function(data) {
	// 	if (data.success) {
	// 		for (var i = 0; i < data.obj.length; i++) {
	// 			$("#regionid").append(
	// 				'<option value="' + data.obj[i].id + '">'
	// 				+ data.obj[i].regionName
	// 				+ '</option>');
	// 			$("#view-regionid").append(
	// 				'<option value="' + data.obj[i].id + '">'
	// 				+ data.obj[i].regionName
	// 				+ '</option>');
	// 		}
	// 	}
	// });
});


$("#regionid").on("change",function() {
    $("#townid").empty();
    $("#townid").append('<option value="">请选择</option>');
    var parentId = $("#regionid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 4,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#townid").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
					$("#view-townid").append(
						'<option value="' + data.obj[i].id + '">'
						+ data.obj[i].regionName
						+ '</option>');
				}
			}
		}
	});
    // $.post(baseUrl + "/baseInfo/locateList", {
    //     type : 4,
    //     parentId : parentId
    // }, function(data) {
    //     if (data.success) {
    //         for (var i = 0; i < data.obj.length; i++) {
    //             $("#townid").append(
    //                 '<option value="' + data.obj[i].id + '">'
    //                 + data.obj[i].regionName
    //                 + '</option>');
    //             $("#view-townid").append(
    //                 '<option value="' + data.obj[i].id + '">'
    //                 + data.obj[i].regionName
    //                 + '</option>');
    //         }
    //     }
    // });
});

$("#supervise-proviceid").on("change", function () {
	$("#supervise-cityid").empty();
	$("#supervise-cityid").append('<option value="">请选择</option>');
	$("#supervise-regionid").empty();
	$("#supervise-regionid").append('<option value="">请选择</option>');
    $("#supervise-townid").empty();
    $("#supervise-townid").append('<option value="">请选择</option>');
    var parentId = $("#supervise-proviceid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 2,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#supervise-cityid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#supervise-cityid-View").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
	// $.post(baseUrl + "/baseInfo/locateList", {
	// 	type: 2,
	// 	parentId: parentId
	// }, function (data) {
	// 	if (data.success) {
	// 		for (var i = 0; i < data.obj.length; i++) {
	// 			$("#supervise-cityid").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 			$("#supervise-cityid-View").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 		}
	// 	}
	// });
});

$("#supervise-cityid").on("change",function () {
	$("#supervise-regionid").empty();
	$("#supervise-regionid").append('<option value="">请选择</option>');
	$("#supervise-townid").empty();
	$("#supervise-townid").append('<option value="">请选择</option>');
	var parentId = $("#supervise-cityid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 3,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#supervise-regionid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#supervise-regionid-View").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
	// $.post(baseUrl + "/baseInfo/locateList", {
	// 	type: 3,
	// 	parentId: parentId
	// }, function (data) {
	// 	if (data.success) {
	// 		for (var i = 0; i < data.obj.length; i++) {
	// 			$("#supervise-regionid").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 			$("#supervise-regionid-View").append(
	// 				'<option value="' + data.obj[i].id + '">' +
	// 				data.obj[i].regionName +
	// 				'</option>');
	// 		}
	// 	}
	// });
 });

$("#supervise-regionid").on("change",function () {
    $("#supervise-townid").empty();
    $("#supervise-townid").append('<option value="">请选择</option>');
    var parentId = $("#supervise-regionid").val();
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		data:{
			type: 4,
			parentId: parentId
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		type: "post",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#supervise-townid").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
					$("#supervise-townid-View").append(
						'<option value="' + data.obj[i].id + '">' +
						data.obj[i].regionName +
						'</option>');
				}
			}
		}
	});
    // $.post(baseUrl + "/baseInfo/locateList", {
    //     type: 4,
    //     parentId: parentId
    // }, function (data) {
    //     if (data.success) {
    //         for (var i = 0; i < data.obj.length; i++) {
    //             $("#supervise-townid").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //             $("#supervise-townid-View").append(
    //                 '<option value="' + data.obj[i].id + '">' +
    //                 data.obj[i].regionName +
    //                 '</option>');
    //         }
    //     }
    // });
});


// 省加载
function provinceSelect(element,proviceid) {
	$("#" + element).empty();
	$("#" + element).html('<option value="">请选择</option>');
	var html = '<option value="">请选择</option>';
	var	str = "";
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			type: 1
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					if(data.obj[i].id == proviceid){
						str = '<option value="' + data.obj[i].id + '" selected>'+data.obj[i].regionName + '</option>';
						html += str;
					}else{
						html += '<option value="' + data.obj[i].id + '">'+data.obj[i].regionName + '</option>';
					}
				}
				$("#" + element).html(html);
			}
		}
	});
}

// 省市区街道联动
function citySelect(element,type,id1,id2) {
	$("#" + element).empty();
	$("#" + element).append('<option value="">请选择</option>');
	var html = '<option value="">请选择</option>';
	var	str = "";
	$.ajax({
		url: baseUrl + "/baseInfo/locateList",
		type: "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		data: {
			type: type,
			parentId: id1
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					if(data.obj[i].id == id2){
						str = '<option value="' + data.obj[i].id + '" selected>'+data.obj[i].regionName + '</option>';
						html += str;
					}else{
						html += '<option value="' + data.obj[i].id + '">'+data.obj[i].regionName + '</option>';
					}
				}
				$("#" + element).html(html);
			}
		}
	});
}



//图片上传
function doChangeProjectxfzimg() {
	uploadOne("picurls-xfzimg", "loadImg-xfzimg", "photoCover-xfzimg", false);
};

function doChangeProjectunitbg() {
	uploadOne("picurls-unitbg", "loadImg-unitbg", "photoCover-unitbg", false);
};

function doChangeProjectsvgfile() {
	uploadOne("picurls-svgfile", "loadImg-svgfile", "photoCover-svgfile", false);
};

function doChangeProjectunitproximity() {
	uploadOne("picurls-unitproximity", "loadImg-unitproximity", "photoCover-unitproximity", false);
};

function doChangeProjectunitLogo0() {
	uploadOne("picurls-unitLogo", "loadImg-unitLogo", "photoCover-unitLogo", false);
};

function doChangeProjectunitLogo1() {
	uploadOne("networking-picurls-unitLogo", "networking-loadImg-unitLogo", "photoCover-unitLogo", false);
};

function doChangeProjectunitLogo2() {
	uploadOne("supervise-picurls-unitLogo", "supervise-loadImg-unitLogo", "photoCover-unitLogo", false);
};


// 联网单位地图
function loadMap0() {
	setTimeout(function () { // 添加延时加载。解决问题
		map = new BMap.Map("maps");
		map.enableScrollWheelZoom(); // 鼠标滚动放大缩小
		// map.centerAndZoom("上海",12);//默认中心点坐标
		var myCity = new BMap.LocalCity();
		myCity.get(function (res) {
			map.centerAndZoom(res.center, res.level);
		});
		local = new BMap.LocalSearch(map, {
			//renderOptions:{map: map},
			onSearchComplete: function (results) {
				map.clearOverlays();
				console.log(results)
				var len = results.getCurrentNumPois();
				for (var i = 0; i < len; i++) {
					if (i > 0) {
						break;
					}
					var msg = results.getPoi(i)
					var baidu_Point = new BMap.Point(msg.point.lng, msg.point.lat);
					var poision = msg.point.lng + "," + msg.point.lat;
					$("#newUnit-unitpoint").val(poision);
					var marker = new BMap.Marker(baidu_Point); // 标记
					map.addOverlay(marker);
					marker.enableDragging(); // 可以拖拽标点
					marker.addEventListener("dragend", function (e) { // 拖拽获取marker位置
						var p = marker.getPosition(); // 获取marker的位置
						var poision3 = p.lng + "," + p.lat;
						$("#newUnit-unitpoint").val(poision3);
						// alert("marker的位置是" + p.lng + "," + p.lat);
					});
					marker.addEventListener("click", function (e) {
						var content = '<div class="BMap_bubble_title" style="overflow: hidden; height: auto; line-height: 24px; white-space: nowrap; width: 200px;"><p style="width:210px;font:bold 14px/16px arial,sans-serif;margin:0;color:#cc5522;white-space:nowrap;overflow:hidden" title="' + msg.title + '">' + msg.title + '<a target="_blank" href="' + msg.url + '" style="margin-left:5px;font-size:12px;color:#3d6dcc;font-weight:normal;text-decoration:none;">详情»</a></p></div>' +
							'<div class="BMap_bubble_content" style="width: auto; height: auto;"><div style="font:12px arial,sans-serif;margin-top:10px">' +
							'<table cellspacing="0" style="overflow:hidden;table-layout:fixed;width:100%;font:12px arial,sans-serif"><tbody>' +
							'<tr><td style="vertical-align:top;width:38px;white-space:nowrap;word-break:keep-all">地址：&nbsp;</td><td style="line-height:16px">' + (msg.address || "") + '</td></tr>' +
							'<tr><td style="vertical-align:top;">电话：</td><td>' + (msg.phoneNumber || "") + '</td></tr>' +
							'<tr><td style="vertical-align:top;"></td></tr>' +
							//						'<tr><td style="vertical-align:top;"></td><td><div style="float:right;background:#337ab7;cursor:pointer;color:#fff;width:45px;height:20px;line-height:20px;text-align:center;border-radius:5px;" onclick="getThisPoint(\''+msg.point.lng+','+msg.point.lat+'\');">选中</div></td></tr>'+
							'</tbody></table></div></div>'
						var p = e.target;
						var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
						var opts = {
							width: 200, // 信息窗口宽度
							height: 80, // 信息窗口高度
						};
						var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
						map.openInfoWindow(infoWindow, point); //开启信息窗口
					});
					map.centerAndZoom(marker.getPosition(), 14);
				}
			}
		});
	}, 500);
}

// 维保单位地图
function loadMap1() {
	setTimeout(function() {// 添加延时加载。解决问题
		map = new BMap.Map("maps");
		map.enableScrollWheelZoom(); // 鼠标滚动放大缩小
		// map.centerAndZoom("上海",12);//默认中心点坐标
		var myCity = new BMap.LocalCity();
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
					$("#pointX").val(msg.point.lng);
					$("#pointY").val(msg.point.lat);
					var marker = new BMap.Marker(baidu_Point); // 标记
					map.addOverlay(marker);
					marker.enableDragging();// 可以拖拽标点
					marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
						var p = marker.getPosition();// 获取marker的位置
						$("#pointX").val(p.lng);
						$("#pointY").val(p.lat);
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
	}, 500);
}

// 监管单位地图
function loadMap2() {
	setTimeout(function() {// 添加延时加载。解决问题
		map = new BMap.Map("maps");
		map.enableScrollWheelZoom(); // 鼠标滚动放大缩小
		// map.centerAndZoom("上海",12);//默认中心点坐标
		var myCity = new BMap.LocalCity();
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
					$("#supervise-pointX").val(msg.point.lng);
					$("#supervise-pointY").val(msg.point.lat);
					var marker = new BMap.Marker(baidu_Point); // 标记
					map.addOverlay(marker);
					marker.enableDragging();// 可以拖拽标点
					marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
						var p = marker.getPosition();// 获取marker的位置
						$("#supervise-pointX").val(p.lng);
						$("#supervise-pointY").val(p.lat);
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
	}, 500);
}

function getThisPoint(poision) {
	$("#newUnit-unitpoint").val(poision);
}

function showMap() {
	$("#mapModal").modal("show");
	var myCity = new window.BMap.LocalCity();
	myCity.get(function (res) {
		map.centerAndZoom(res.center, res.level);
	});

	setTimeout(function () {
		map.clearOverlays(); // 清除地图上点

		var unitpoint = $("#newUnit-unitpoint").val();
		var pointX = $("#pointX").val();
		var pointY = $("#pointY").val();
		var supervisePointX = $("#supervise-pointX").val();
		var supervisePointY = $("#supervise-pointY").val();
		if (unitpoint != "") {
			var baidu_Point = new BMap.Point(unitpoint.split(",")[0],
				unitpoint.split(",")[1]); // 经纬度坐标
			var marker = new BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			map.panTo(baidu_Point);
			marker.enableDragging(); // 可以拖拽标点
			marker.addEventListener("dragend", function (e) { // 拖拽获取marker位置
				var p = marker.getPosition(); // 获取marker的位置
				var poision = p.lng + "," + p.lat;
				$("#newUnit-unitpoint").val(poision);
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
		}else if(pointX != "" && pointY != ""){
            var baidu_Point = new BMap.Point(pointX, pointY); // 经纬度坐标
			var marker = new BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			map.panTo(baidu_Point);
			marker.enableDragging(); // 可以拖拽标点
			marker.addEventListener("dragend", function (e) { // 拖拽获取marker位置
				var p = marker.getPosition(); // 获取marker的位置
				$("#pointX").val(p.lng);
				$("#pointY").val(p.lat);
			});
		} else if(supervisePointX != "" && supervisePointY != ""){
			var baidu_Point = new BMap.Point(unitpoint.split(",")[0],
				unitpoint.split(",")[1]); // 经纬度坐标
			var marker = new BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			map.panTo(baidu_Point);
			marker.enableDragging(); // 可以拖拽标点
			marker.addEventListener("dragend", function (e) { // 拖拽获取marker位置
				var p = marker.getPosition(); // 获取marker的位置
				$("#supervise-pointX").val(p.lng);
				$("#supervise-pointY").val(p.lat);
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
	map.addEventListener('dblclick', function (e) { // 双击事件
		map.clearOverlays(); // 清除地图上点
		// alert("marker的位置是" + e.point.lng + "," + e.point.lat);
		var poision2 = e.point.lng + "," + e.point.lat;
		$("#newUnit-unitpoint").val(poision2);
		$("#pointX").val(e.point.lng);
		$("#pointY").val(e.point.lat);
		$("#supervise-pointX").val(e.point.lng);
		$("#supervise-pointY").val(e.point.lat);
		var baidu_Point = new BMap.Point(e.point.lng, e.point.lat); // 经纬度坐标
		var marker = new BMap.Marker(baidu_Point); // 标记
		map.addOverlay(marker);
		marker.enableDragging(); // 可以拖拽标点
		marker.addEventListener("dragend", function (e) { // 拖拽获取marker位置
			var p = marker.getPosition(); // 获取marker的位置
			var poision3 = p.lng + "," + p.lat;
			$("#newUnit-unitpoint").val(poision3);
			$("#pointX").val(p.lng);
			$("#pointY").val(p.lat);
			$("#supervise-pointX").val(p.lng);
			$("#supervise-pointY").val(p.lat);
			// alert("marker的位置是" + p.lng + "," + p.lat);
		});

	});
	// $("#editId").val("0");
	// $("#newEmploy").modal("show");
	// $(".password").show();
	// $("#newEmploy .modal-title").html("新增联网单位");
	// $("#newEmploy .btnSure").show();
	//
	// var pos_info = "<h5>地点</h5>" + "<p style='font-size:12px;'>详细地址</p>"
	// var infoWindow = new BMap.InfoWindow(pos_info); //信息展示
}

// 上传文件
function doChange(_this) {

	var file_string = '';
	// 检查上传文件是否是图片，如果是则进行上传操作，若不是则提示用户注意上传文件格式
	var fileurl = $(_this).val();
	if (!fileurl) {
		$("#attachment").val("");
		$(".fileName").html("");
		$(".uploadFile").val("");
		return;
	}
	var name = fileurl.substring(fileurl.lastIndexOf("\\") + 1);
	layer.msg("附件上传中，请稍等...", {
		time: 1500
	})
	$
		.ajaxFileUpload({
			url: baseUrl + "/upload/upLoadFile",
			fileElementId: "uploadFileId",
			beforeSend: function(request){
				request.setRequestHeader("Authorization","Bearer "+login.token);
			},
			type: "post",
			dataType: "json",
			success: function (data) {
				$(".fileName")
					.html(
						name +
						'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
						"<img style='width: 15px;height: 15px;' src='/static/image/mima.png' title='删除' class='delete'/>" +
						" </a>" +
						'<a target="_blank" href="' +
						baseUrl + data.obj + '">点击下载</a>');
				$("#attachment").val(data.obj);
				$("#attachmentName").val(name);
				// $(".fileName").html(name);
			},
			complete: function () {
				// 当上传结束后，重新生成input file，不然会失效
				// $("#" + picId).val("");
				// $("#" + photoCoverId).val("");
			},
			error: function (data, status, e) {
				layer.msg("系统繁忙");
			}
		});
}

function deleteFile(_this) {
	$(".fileName").html("");
	$("#attachment").val("");
	$("#attachmentName").val("");
	$(".uploadFile").val("");
}

function clearNoNum(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
}
$(".btn-search").click(function () {
	local.search($("#map_search").val());
});

function maintenanceUnitSelect() {
	$.ajax({
		url: baseUrl + "/maintenanceunit/getMaintenanceUnitSelectList",
		type: "get",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		dataType: "json",
		anysc: false,
		success: function (data) {
			if (data.success) {
				var htm = "";
				for (var i = 0; i < data.obj.length; i++) {
					htm += "<option value='" + data.obj[i].id + "'>" + data.obj[i].unitname + "</option>";
				}
				$("#newUnit-maintenanceUnit").html(htm);
				$("#newUnit-view-maintenanceUnit").html(htm);
				$("#newUnit-maintenanceUnit").selectpicker("refresh");
				$("#newUnit-maintenanceUnit").selectpicker("show");
                $("#newUnit-view-maintenanceUnit").selectpicker("refresh");
                $("#newUnit-view-maintenanceUnit").selectpicker("show");
			}
		}
	});

}