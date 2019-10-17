var map;
var unitList;
var local;
$(function() {

	// 主页面查询
	mainSearch();
	// 初始化单位下拉框
	// initUnit();
	// 初始化地图
	loadMap();
	getUnitList();
	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$(".notLastModal").on("hidden.bs.modal", function() {
		// $("#newProject").addClass("modal-open");
		$("#newEmploy").css("overflow-y", "auto");
	});

	$(".btnReset").click(function() {
		emptySearch();
		$("#userTable").bootstrapTable("refresh");
		$("#searchContent").val('');
	});
	// 查询按钮查询
	$(".btnSearch").click(function() {
		$("#userTable").bootstrapTable('destroy');
		mainSearch();
	});
	// 批量删除
	$(".btnDeleteMany").click(function() {
		deleteEmploy();
	});

	$(".newAdd").click(function() {
		showUnitList();
		$(".belongdd").hide();
		$(".belongzd").hide();
		$("#newEmploy").modal("show");
		// $(".password").show();
		$("#newEmploy .modal-title").html("新增消防力量");
		$("#newEmploy .btnSure").show();

		$("#newFirePower-type").val("");
		$("#newFirePower-belongdd").val("");
		$("#newFirePower-belongzd").val("");
		$("#newFirePower-unitid").val("");
		$("#newFirePower-name").val("");
		$("#newFirePower-remark").val("");
		$("#newFirePower-pointX").val("");
		$("#newFirePower-pointY").val("");
		$("#newFirePower-address").val("");
		$("#newFirePower-contact").val("");
		$("#newFirePower-phone").val("");
		$("#picurls").attr("file_string", "");
		$("#loadImg").html('<li class="modalImg"><span>请选择上传图片</span></li>');

		$("#editId").val("0");
		$("#indexId").val("");

	});

	// 新增或编辑
	$(".btnSure")
			.click(
					function() {
						var editId = $("#editId").val();
						var id = $("#indexId").val();
						var type = $("#newFirePower-type").val();
						var unitid = $("#newFirePower-unitid").siblings(
								".combo-dropdown").find("li.option-selected")
								.attr("data-value");
						var name = $("#newFirePower-name").val();
						var remark = $("#newFirePower-remark").val();
						var pointX = $("#newFirePower-pointX").val();
						var pointY = $("#newFirePower-pointY").val();
						var address = $("#newFirePower-address").val();
						var contact = $("#newFirePower-contact").val();
						var phone = $("#newFirePower-phone").val();
						var belongdd = $("#newFirePower-belongdd").val();
						var belongzd = $("#newFirePower-belongzd").val();
						var image = "";
						$('#loadImg')
								.find('li')
								.each(
										function() {
											if ($(this).children("img").attr(
													"src") != ""
													&& $(this).children("img")
															.attr("src") != null) {
												image += $(this)
														.children("img").attr(
																"src")
														+ ",";
											}
										});
						image = image.substr(0, image.length - 1);

						var pid = "";

						if (type == null || type == '') {
							layer.msg("消防类型不能为空！");
							return;
						} else if (type == 0) {
							if (unitid == null || unitid == '') {
								layer.msg("单位不能为空！");
								return;
							}
						} else if (type == 1) {
							if (belongdd == null || belongdd == '') {
								layer.msg("所属大队不能为空！");
								return;
							} else {
								pid = belongdd;
							}
						} else if (type == 6) {
							if (belongzd == null || belongzd == '') {
								layer.msg("所属支队不能为空！");
								return;
							} else {
								pid = belongzd;
							}
						}
						if (name == "") {
							layer.msg("名称不能为空！");
							return;
						}
						if (contact == "") {
							layer.msg("联系人不能为空！");
							return;
						}
						if (phone == "") {
							layer.msg("联系电话不能为空！");
							return;
						}
						if (address == "") {
							layer.msg("详细地址不能为空！");
							return;
						}
						var editId = $("#editId").val();
						if (editId == 0) {
							var url = baseUrl + "/firePowerManage/addFirePower";
							var data = {
								type : type,
								unitId : unitid,
								name : name,
								remark : remark,
								pointX : pointX,
								pointY : pointY,
								address : address,
								contact : contact,
								phone : phone,
								image : image,
								pid : pid
							};
							$.ajax({
								url : url,
								type : "post",
								beforeSend: function(request){
									request.setRequestHeader("Authorization","Bearer "+login.token);
								},
								data : data,
								dataType : "json",
								success : function(d) {
									if (d.success) {
										layer.msg("添加成功");
										$("#userTable").bootstrapTable(
												"refresh");
										$("#newEmploy").modal("hide");
									} else {
										layer.msg("添加失败");
									}
								},
								error : function(data) {

								}
							});
						} else {
							var url = baseUrl
									+ "/firePowerManage/updateFirePower";
							var data = {
								id : id,
								type : type,
								unitId : unitid,
								name : name,
								remark : remark,
								pointX : pointX,
								pointY : pointY,
								address : address,
								contact : contact,
								phone : phone,
								image : image,
								pid : pid
							};
							$.ajax({
								url : url,
								type : "post",
								beforeSend: function(request){
									request.setRequestHeader("Authorization","Bearer "+login.token);
								},
								data : data,
								dataType : "json",
								success : function(d) {
									if (d.success) {
										layer.msg("修改成功");
										$("#userTable").bootstrapTable(
												"refresh");
										$("#newEmploy").modal("hide");
									} else {
										layer.msg("修改失败");
									}
								},
								error : function(data) {

								}
							});
						}
					});

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
$(".ordinarySearch").click(function() {
	var id = $("#menuType").val();
	var value = $("#searchContent").val();
	$("#" + id).val(value);
	$("#userTable").bootstrapTable('destroy');
	mainSearch();

});
$("#menuType")
		.change(
				function() {
					emptySearch();
					if ($(this).val() == 'type') {
						var html = '<select name="searchContent" id="searchContent" class="form-control">'
								+ '<option value="">全部</option>'
								+ '<option value="0">单位微型消防站</option>'
								+ '<option value="1">消防中队</option>'
								+ '<option value="6">消防大队</option>'
								+ '<option value="7">消防支队</option>'
								+ '<option value="8">职业消防队</option>'
								+ '<option value="2">公共消防站</option>'
								+ '<option value="3">室外消火栓</option>'
								+ '<option value="4">消防水鹤</option>'
								+ '<option value="5">湖泊/池塘/消防水源</option>'
								+ '</select>';
						$("#commonInput").html(html);
					} else {
						var id = $(this).val();
						if (id == "") {
							$("#userTable").bootstrapTable('refresh');
						}
						if (!$('#searchContent').hasClass('showInput')) {
							$("#commonInput")
									.html(
											'<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
						}
					}
				});

function emptySearch() {
	$("#equipmentname").val('');
    $("#firePowerName").val('');
    $("#type").val('');
    $("#unitName").val('');
}
// 编辑
function editBaseInfo(_this) {

	showUnitList(_this.unitId);
	$("#newEmploy").modal("show");
	$("#newEmploy .modal-title").html("编辑消防力量");
	$("#newEmploy .btnSure").show();
	var id = _this.id;
	$("#indexId").val(id);
	$("#editId").val("1");

	$("#newFirePower-type").val(_this.type);
	$("#newFirePower-name").val(_this.name);
	$("#newFirePower-remark").val(_this.remark);
	$("#newFirePower-pointX").val(_this.pointX);
	$("#newFirePower-pointY").val(_this.pointY);
	$("#newFirePower-address").val(_this.address);
	$("#newFirePower-contact").val(_this.contact);
	$("#newFirePower-phone").val(_this.phone);
	typefunction();
	if (_this.type == 1) {
		$("#newFirePower-belongdd").val(_this.pid);
	} else if (_this.type == 6) {
		$("#newFirePower-belongzd").val(_this.pid);
	}

	var images = _this.image;
	if (images) {
		var image = images.split(",");
		var props = "";
		var htm = "";
		for (var i = 0; i < image.length; i++) {
			props += image[i];
			htm += " <li style='position: relative;margin: 0px;border: none;'> "
					+ "  <img src='"
					+ image[i]
					+ "' "
					+ " style = 'width:100%;height:100%;'/>"
					+ '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">'
					+ " <img style='width: 15px;height: 15px;position: absolute;top: 3px;right: 3px;' src='"
					+ baseUrl
					+ "/image/mima.png' class='delete'/>"
					+ " </a>"
					+ " </li>";
		}
		$("#picurls").attr("file_string", props);
		$("#loadImg").html(
				"<li class='modalImg'><span>请选择上传图片</span></li>" + htm);

	} else {
		$("#picurls").attr("file_string", "");
		$("#loadImg").html("<li class='modalImg'><span>请选择上传图片</span></li>");

	}

}
// 查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newFirePowerView-type").val(_this.type);
    $("#newFirePower-unitid").val(_this.unitName);
    $("#newFirePowerView-unitid").empty();
    $("#newFirePowerView-unitid").append("<option value="+_this.unitName+">"+_this.unitName+"</option>");
	$("#newFirePowerView-name").val(_this.name);
	$("#newFirePowerView-remark").val(_this.remark);
	$("#newFirePowerView-pointX").val(_this.pointX);
	$("#newFirePowerView-pointY").val(_this.pointY);
	$("#newFirePowerView-address").val(_this.address);
	$("#newFirePowerView-contact").val(_this.contact);
	$("#newFirePowerView-phone").val(_this.phone);
	typefunctionView();
	if (_this.type == 1) {
		$("#newFirePowerView-belongdd").val(_this.pid);
	} else if (_this.type == 6) {
		$("#newFirePowerView-belongzd").val(_this.pid);
	}

	var images = _this.image;
	if (images) {
		var image = images.split(",");
		var props = "";
		var htm = "";
		for (var i = 0; i < image.length; i++) {
			props += image[i];
			htm += " <li style='position: relative;margin: 0px;border: none;'> "
					+ "  <img src='"
					+ image[i]
					+ "' "
					+ " style = 'width:100%;height:100%;'/>" + " </li>";
		}
		$("#picurlsView").prop(props);
		$("#loadImgView").html("<li class=''></li>" + htm);
	} else {
		$("#picurlsView").prop("file_string", "");
		$("#loadImgView").html("<li class=''></li>");

	}
}

function deleteBaseInfo(_this) {
	var select = _this.id;
	layer.open({
		content : '确认删除选中编号信息？',
		btn : [ '确认', '取消' ],
		yes : function() {
			$.ajax({
				url : baseUrl + "/firePowerManage/deleteFirePower",
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
						layer.msg("删除成功!");
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

// 主页面查询
function mainSearch() {
	$('#userTable')
			.bootstrapTable(
					{
						url : baseUrl + '/firePowerManage/getFirePowerList', // 请求后台的URL（*）
						method : 'get', // 请求方式（*）
						contentType : 'application/x-www-form-urlencoded',
						toolbar : '#toolbar', // 工具按钮用哪个容器
						striped : true, // 是否显示行间隔色
						cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
						pagination : true, // 是否显示分页（*）
						sortable : false, // 是否启用排序
						sortOrder : "asc", // 排序方式
						queryParamsType : "undefined", // 排序方式
						ajaxOptions: {
							headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
						},
						queryParams : function queryParams(params) { // 设置查询参数
							var param = {
								pageNumber : this.pageNumber,
								pageSize : this.pageSize,
								name : $("#firePowerName").val(),
								type : $("#type").val(),
								unitName : $("#unitName").val(),
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
									field : 'name',
									title : '名称'
								},
								{
									field : 'type',
									title : '消防类型',
									formatter : function(value, row, index) {
										if (row.type == 0) {
											return "单位消防站";
										}
										if (row.type == 1) {
											return "中队配置";
										}
										if (row.type == 2) {
											return "公共消防站";
										}
										if (row.type == 3) {
											return "室外消火栓";
										}
										if (row.type == 4) {
											return "消防水鹤";
										}
										if (row.type == 5) {
											return "湖泊/池塘/消防水源";
										}
										if (row.type == 6) {
											return "消防大队";
										}
										if (row.type == 7) {
											return "消防支队";
										}
										if (row.type == 8) {
											return "职业消防队";
										}
									}
								},
								{
									field : 'pointX',
									title : 'X坐标'
								},
								{
									field : 'pointY',
									title : 'Y坐标'
								},
								{
									field : 'unitName',
									title : '单位名称'
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
										'click .edit' : function(e, value, row,
												index) {
											editBaseInfo(row);
										},
										'click .del' : function(e, value, row,
												index) {
											deleteBaseInfo(row);
										},
										'click .view' : function(e, value, row,
												index) {
											viewBaseInfo(row);
										}
									}
								} ]
					});
}

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

function showUnitList(unitID) {
	var a = '<option value="%unitID%" >%unitName%</option>';
	var option;
	var wrap = '<option value="">--请选择--</option>';
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
	$("#newFirePower-unitid").html(wrap);
	$("#newFirePower-unitid").comboSelect();
	// $("#newFirePower-unitid").siblings("input").attr('disabled','disabled');
	// $("#newFirePower-unitid").siblings(".combo-arrow").hide();
}

// 初始化单位下拉选择框
/*
 * function initUnit() { $.ajax({ url : baseUrl + "/xfzUser/unitSelect", type :
 * "post", dataType : "json", success : function(data) { if (data.success) { for
 * (var i = 0; i < data.obj.length; i++) { $("#newFirePower-unitid").append( '<option
 * value="' + data.obj[i].id + '">' + data.obj[i].unitname + '</option>');
 * $("#newFirePowerView-unitid").append( '<option value="' + data.obj[i].id +
 * '">' + data.obj[i].unitname + '</option>'); } } } }) }
 */

function typefunction() {
	var type = $("#newFirePower-type").val();
	if (type != 0) {
		$("#newFirePower-unitid").siblings("input")
				.attr('disabled', 'disabled');
		$("#newFirePower-unitid").siblings(".combo-arrow").hide();
		$("#newFirePower-unitid").val("");
		$("#newFirePower-unitid").siblings(".text-input").val("");
		$("#newFirePowerView-unitid").val("");
	} else {
		$("#newFirePower-unitid").siblings("input").removeAttr('disabled',
				'disabled');
		$("#newFirePower-unitid").siblings(".combo-arrow").show();
		// initUnit();
	}
	if (type == 6) {
		$(".belongzd").show();
		$(".belongdd").hide();
		loadBelong(7);
	} else if (type == 1) {
		$(".belongdd").show();
		$(".belongzd").hide();
		loadBelong(6);
	} else {
		$(".belongdd").hide();
		$(".belongzd").hide();
	}
}
function typefunctionView() {
	var type = $("#newFirePowerView-type").val();
	if (type == 6) {
		$(".belongzdView").show();
		$(".belongddView").hide();
		loadBelong(7);
	} else if (type == 1) {
		$(".belongddView").show();
		$(".belongzdView").hide();
		loadBelong(6);
	} else {
		$(".belongddView").hide();
		$(".belongzdView").hide();
	}
}

function loadBelong(type) {
	$.ajax({
		url : baseUrl + "/firePowerManage/getFireStationName",
		data : {
			type : type
		},
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var content = "<option value=''>--请选择--</option>";
			for (var i = 0; i < data.obj.length; i++) {
				content += '<option value="' + data.obj[i].id + '">'
						+ data.obj[i].name + '</option>';
			}
			if (type == 6) {
				$("#newFirePower-belongdd").html(content);
				$("#newFirePowerView-belongdd").html(content);
			} else if (type == 7) {
				$("#newFirePower-belongzd").html(content);
				$("#newFirePowerView-belongzd").html(content);
			}
		}

	})
}

// 地图
function loadMap() {
	setTimeout(
			function() {// 添加延时加载。解决问题
				map = new window.BMap.Map("maps");
				map.enableScrollWheelZoom(); // 鼠标滚动放大缩小
				// map.centerAndZoom("上海",12);//默认中心点坐标
				var myCity = new window.BMap.LocalCity();
				myCity.get(function(res) {
					map.centerAndZoom(res.center, res.level);
				});
				local = new BMap.LocalSearch(
						map,
						{
							// renderOptions:{map: map},
							onSearchComplete : function(results) {
								map.clearOverlays();
								console.log(results)
								var len = results.getCurrentNumPois();
								for (var i = 0; i < len; i++) {
									if (i > 0) {
										break;
									}
									var msg = results.getPoi(i)
									var baidu_Point = new BMap.Point(
											msg.point.lng, msg.point.lat);
									$("#newFirePower-pointX")
											.val(msg.point.lng);
									$("#newFirePower-pointY")
											.val(msg.point.lat);
									var marker = new BMap.Marker(baidu_Point); // 标记
									map.addOverlay(marker);
									marker.enableDragging();// 可以拖拽标点
									marker.addEventListener("dragend",
											function(e) {// 拖拽获取marker位置
												var p = marker.getPosition();// 获取marker的位置
												$("#newFirePower-pointX").val(
														p.lng);
												$("#newFirePower-pointY").val(
														p.lat);
												// alert("marker的位置是" + p.lng +
												// "," + p.lat);
											});
									marker
											.addEventListener(
													"click",
													function(e) {
														var content = '<div class="BMap_bubble_title" style="overflow: hidden; height: auto; line-height: 24px; white-space: nowrap; width: 200px;"><p style="width:210px;font:bold 14px/16px arial,sans-serif;margin:0;color:#cc5522;white-space:nowrap;overflow:hidden" title="'
																+ msg.title
																+ '">'
																+ msg.title
																+ '<a target="_blank" href="'
																+ msg.url
																+ '" style="margin-left:5px;font-size:12px;color:#3d6dcc;font-weight:normal;text-decoration:none;">详情»</a></p></div>'
																+ '<div class="BMap_bubble_content" style="width: auto; height: auto;"><div style="font:12px arial,sans-serif;margin-top:10px">'
																+ '<table cellspacing="0" style="overflow:hidden;table-layout:fixed;width:100%;font:12px arial,sans-serif"><tbody>'
																+ '<tr><td style="vertical-align:top;width:38px;white-space:nowrap;word-break:keep-all">地址：&nbsp;</td><td style="line-height:16px">'
																+ (msg.address || "")
																+ '</td></tr>'
																+ '<tr><td style="vertical-align:top;">电话：</td><td>'
																+ (msg.phoneNumber || "")
																+ '</td></tr>'
																+ '<tr><td style="vertical-align:top;"></td></tr>'
																+
																// '<tr><td
																// style="vertical-align:top;"></td><td><div
																// style="float:right;background:#337ab7;cursor:pointer;color:#fff;width:45px;height:20px;line-height:20px;text-align:center;border-radius:5px;"
																// onclick="getThisPoint(\''+msg.point.lng+','+msg.point.lat+'\');">选中</div></td></tr>'+
																'</tbody></table></div></div>'
														var p = e.target;
														var point = new BMap.Point(
																p.getPosition().lng,
																p.getPosition().lat);
														var opts = {
															width : 200, // 信息窗口宽度
															height : 80, // 信息窗口高度
														};
														var infoWindow = new BMap.InfoWindow(
																content, opts); // 创建信息窗口对象
														map.openInfoWindow(
																infoWindow,
																point); // 开启信息窗口
													});
									map.centerAndZoom(marker.getPosition(), 14);
								}
							}
						});
			}, 500);
}

function showMap() {
	$("#mapModal").modal("show");
	var myCity = new window.BMap.LocalCity();
				myCity.get(function(res) {
					map.centerAndZoom(res.center, res.level);
				});
	setTimeout(function() {
		map.clearOverlays(); // 清除地图上点
		var pointX = $("#newFirePower-pointX").val();
		var pointY = $("#newFirePower-pointY").val();
		if (pointX != '' || pointX != '') {
			var baidu_Point = new window.BMap.Point($(
					"#newFirePower-pointX").val(),
					$("#newFirePower-pointY").val()); // 经纬度坐标
			map.panTo(baidu_Point);
			var marker = new window.BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			marker.enableDragging();// 可以拖拽标点
			marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
				var p = marker.getPosition();// 获取marker的位置
				$("#newFirePower-pointX").val(p.lng);
				$("#newFirePower-pointY").val(p.lat);
				// alert("marker的位置是" + p.lng + "," + p.lat);
			});
		}else{
			var myCity = new window.BMap.LocalCity();
			myCity.get(function(res) {
				map.centerAndZoom(res.center, res.level);
			});
		}
	}, 500);

	map.disableDoubleClickZoom(); // 禁用鼠标双击放大
	map.enableKeyboard(); // 启用键盘上下左右键移动地图
	map.addEventListener('dblclick', function(e) {// 双击事件
		map.clearOverlays(); // 清除地图上点
		// alert("marker的位置是" + e.point.lng + "," + e.point.lat);
		$("#newFirePower-pointX").val(e.point.lng);
		$("#newFirePower-pointY").val(e.point.lat);
		var baidu_Point = new window.BMap.Point(e.point.lng, e.point.lat); // 经纬度坐标
		var marker = new window.BMap.Marker(baidu_Point); // 标记
		map.addOverlay(marker);
		marker.enableDragging();// 可以拖拽标点
		marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
			var p = marker.getPosition();// 获取marker的位置
			$("#newFirePower-pointX").val(p.lng);
			$("#newFirePower-pointY").val(p.lat);
			// alert("marker的位置是" + p.lng + "," + p.lat);
		});

	});
	//    
	// var pos_info = "<h5>地点</h5>" + "<p style='font-size:12px;'>详细地址</p>"
	// var infoWindow = new BMap.InfoWindow(pos_info); //信息展示
}

function checkPhotoNum() {
	var image = "";
	var len = $('#loadImg').find('li').length;
	if (len >= 4) {
		$("#picurls").removeAttr("type");
		layer.msg("最多只能上上传三张图片！");
		return;
	} else {
		$("#picurls").attr("type", "file");
	}
}

function doChangeProject() {
	/*
	 * $("#loadImg-photo").html( '<li class="modalImg"><span>请选择上传图片</span></li>');
	 */
	var file = "";
	uploadOneMany("picurls", "loadImg", "photoCover", false);
}

$(".btn-search").click(function() {
	local.search($("#map_search").val());
});
