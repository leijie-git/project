$(function() {
	// 主页面查询
	mainSearch();

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
		$("#newEmploy").modal("show");
		// $(".password").show();
		$("#newEmploy .modal-title").html("新增消防站信息");
		$("#newEmploy .btnSure").show();

		$("#newFireStation-type").val("");
		$("#newFireStation-powerid").val("");
		$("#newFireStation-name").val("");
		$("#newFireStation-waterGun").val("");
		$("#newFireStation-waterBelt").val("");
		$("#newFireStation-safetyRope").val("");
		$("#newFireStation-hydrantWrench").val("");
		$("#newFireStation-fireExtinguisher").val("");
		$("#newFireStation-brightLight").val("");
		$("#newFireStation-fireAxe").val("");
		$("#newFireStation-fireHelmet").val("");
		$("#newFireStation-clothing").val("");
		$("#newFireStation-boots").val("");
		$("#newFireStation-safetyBelt").val("");
		$("#newFireStation-fireGloves").val("");
		$("#newFireStation-integratedRespirator").val("");
		$("#newFireStation-loudspeaker").val("");
		$("#newFireStation-patrolCar").val("");
		$("#newFireStation-remark").val("");

		$("#editId").val("0");
		$("#indexId").val("");

	});

	// 新增或编辑
	$(".btnSure").click(
			function() {
				var editId = $("#editId").val();
				var id = $("#indexId").val();

				var type = $("#newFireStation-type").val();
				var powerId = $("#newFireStation-powerid").val();
				var name = $("#newFireStation-name").val();
				var waterGun = $("#newFireStation-waterGun").val();
				var waterBelt = $("#newFireStation-waterBelt").val();
				var safetyRope = $("#newFireStation-safetyRope").val();
				var hydrantWrench = $("#newFireStation-hydrantWrench").val();
				var fireExtinguisher = $("#newFireStation-fireExtinguisher")
						.val();
				var brightLight = $("#newFireStation-brightLight").val();
				var fireAxe = $("#newFireStation-fireAxe").val();
				var fireHelmet = $("#newFireStation-fireHelmet").val();
				var clothing = $("#newFireStation-clothing").val();
				var boots = $("#newFireStation-boots").val();
				var safetyBelt = $("#newFireStation-safetyBelt").val();
				var fireGloves = $("#newFireStation-fireGloves").val();
				var integratedRespirator = $(
						"#newFireStation-integratedRespirator").val();
				var loudspeaker = $("#newFireStation-loudspeaker").val();
				var patrolCar = $("#newFireStation-patrolCar").val();
				var remark = $("#newFireStation-remark").val();

				if (type == "" || type == null) {
					layer.msg("消防站类型不能为空！");
					return;
				}
				if (powerId == "" || powerId == null) {
					layer.msg("消防站不能为空！");
					return;
				}
				if (name == "") {
					layer.msg("名称不能为空！");
					return;
				}
				var editId = $("#editId").val();
				if (editId == 0) {
					var url = baseUrl + "/fireStationManage/addFireStation";
					var data = {
						powerId : powerId,
						name : name,
						waterGun : waterGun,
						waterBelt : waterBelt,
						safetyRope : safetyRope,
						hydrantWrench : hydrantWrench,
						fireExtinguisher : fireExtinguisher,
						brightLight : brightLight,
						fireAxe : fireAxe,
						fireHelmet : fireHelmet,
						clothing : clothing,
						boots : boots,
						safetyBelt : safetyBelt,
						fireGloves : fireGloves,
						integratedRespirator : integratedRespirator,
						loudspeaker : loudspeaker,
						patrolCar : patrolCar,
						remark : remark
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
								$("#userTable").bootstrapTable("refresh");
								$("#newEmploy").modal("hide");
							} else {
								layer.msg("添加失败");
							}
						},
						error : function(data) {

						}
					});
				} else {
					var url = baseUrl + "/fireStationManage/updateFireStation";
					var data = {
						id : id,
						powerId : powerId,
						name : name,
						waterGun : waterGun,
						waterBelt : waterBelt,
						safetyRope : safetyRope,
						hydrantWrench : hydrantWrench,
						fireExtinguisher : fireExtinguisher,
						brightLight : brightLight,
						fireAxe : fireAxe,
						fireHelmet : fireHelmet,
						clothing : clothing,
						boots : boots,
						safetyBelt : safetyBelt,
						fireGloves : fireGloves,
						integratedRespirator : integratedRespirator,
						loudspeaker : loudspeaker,
						patrolCar : patrolCar,
						remark : remark
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
								$("#userTable").bootstrapTable("refresh");
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
					var id = $(this).val();
					if (id == "") {
						$("#userTable").bootstrapTable('refresh');
					}
					if (!$('#searchContent').hasClass('showInput')) {
						$("#commonInput")
								.html(
										'<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
					}

				});

function emptySearch() {
	$("#equipmentname").val('');
    $("#fireStationName").val('');
    $("#name").val('');
}
// 编辑
function editBaseInfo(_this) {

	$("#newEmploy").modal("show");
	$("#newEmploy .modal-title").html("编辑消防站信息");
	$("#newEmploy .btnSure").show();
	var id = _this.id;
	$("#indexId").val(id);
	$("#editId").val("1");

	$("#newFireStation-type").val(_this.type);
	fireStationType();
	$("#newFireStation-name").val(_this.name);
	$("#newFireStation-powerid").val(_this.powerId);
	$("#newFireStation-waterGun").val(_this.waterGun);
	$("#newFireStation-waterBelt").val(_this.waterBelt);
	$("#newFireStation-safetyRope").val(_this.safetyRope);
	$("#newFireStation-hydrantWrench").val(_this.hydrantWrench);
	$("#newFireStation-fireExtinguisher").val(_this.fireExtinguisher);
	$("#newFireStation-brightLight").val(_this.brightLight);
	$("#newFireStation-fireAxe").val(_this.fireAxe);
	$("#newFireStation-fireHelmet").val(_this.fireHelmet);
	$("#newFireStation-clothing").val(_this.clothing);
	$("#newFireStation-boots").val(_this.boots);
	$("#newFireStation-safetyBelt").val(_this.safetyBelt);
	$("#newFireStation-fireGloves").val(_this.fireGloves);
	$("#newFireStation-integratedRespirator").val(_this.integratedRespirator);
	$("#newFireStation-loudspeaker").val(_this.loudspeaker);
	$("#newFireStation-patrolCar").val(_this.patrolCar);
	$("#newFireStation-remark").val(_this.remark);
}
// 查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");

	$("#newFireStationView-type").val(_this.type);
	fireStationViewType();
	$("#newFireStationView-name").val(_this.name);
	$("#newFireStationView-waterGun").val(_this.waterGun);
	$("#newFireStationView-powerid").val(_this.powerId);
	$("#newFireStationView-waterBelt").val(_this.waterBelt);
	$("#newFireStationView-safetyRope").val(_this.safetyRope);
	$("#newFireStationView-hydrantWrench").val(_this.hydrantWrench);
	$("#newFireStationView-fireExtinguisher").val(_this.fireExtinguisher);
	$("#newFireStationView-brightLight").val(_this.brightLight);
	$("#newFireStationView-fireAxe").val(_this.fireAxe);
	$("#newFireStationView-fireHelmet").val(_this.fireHelmet);
	$("#newFireStationView-clothing").val(_this.clothing);
	$("#newFireStationView-boots").val(_this.boots);
	$("#newFireStationView-safetyBelt").val(_this.safetyBelt);
	$("#newFireStationView-fireGloves").val(_this.fireGloves);
	$("#newFireStationView-integratedRespirator").val(_this.integratedRespirator);
	$("#newFireStationView-loudspeaker").val(_this.loudspeaker);
	$("#newFireStationView-patrolCar").val(_this.patrolCar);
	$("#newFireStationView-remark").val(_this.remark);

	// var userHeader = _this.userHeader;
	// if(userHeader){
	// $("#loadImgView").html(
	// " <li style='position: relative;margin: 0px;border: none;'> " +
	// " <img src='" + userHeader + "' " +
	// " style = 'width:100%;height:100%;'/>" +
	// '<a onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;"
	// class="reg_btn_yzm reg_btn_yzm_display settled_up input-file">' +
	// " </a>"+
	// " </li>"
	// );
	// }else{
	// $("#loadImgView").html("<li class='modalImg'><span>暂无图片</span></li>");
	// }
}

function deleteBaseInfo(_this) {
	var select = _this.id;
	layer.open({
		content : '确认删除选中编号信息？',
		btn : [ '确认', '取消' ],
		yes : function() {
			$.ajax({
				url : baseUrl + "/fireStationManage/deleteFireStation",
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
						layer.msg("删除成功");
						$("#userTable").bootstrapTable('refresh');
					} else {
						layer.msg("删除失败");
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
						url : baseUrl + '/fireStationManage/getFireStationList', // 请求后台的URL（*）
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
								fireStationName : $("#fireStationName").val(),
								name : $("#name").val()
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
									title : '设备名称'
								},
								{
									field : 'powerName',
									title : '消防站名称'
								},
								{
									field : 'type',
									title : '消防站类型',
									formatter : function(value, row, index) {
										if (row.type == 0) {
											return "单位消防站";
										}
										if (row.type == 2) {
											return "公共消防站";
										}
										if (row.type == 8) {
											return "职业消防队";
										}
									}
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

// 初始化下拉选择框
function initFireStation(type) {
	$.ajax({
		url : baseUrl + "/firePowerManage/getFireStationName",
		type : "post",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		async:false,
		data : {
			type : type
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#newFireStation-powerid").append(
							'<option value="' + data.obj[i].id + '">'
							+ data.obj[i].name + '</option>');
					$("#newFireStationView-powerid").append(
							'<option value="' + data.obj[i].id + '">'
							+ data.obj[i].name + '</option>');
				}
			}
		}
	})
}

function fireStationType() {
	var type = $("#newFireStation-type").val();
	if (type == 0) {
		$("#newFireStation-patrolCar").attr("disabled", "disabled");
		$("#newFireStation-powerid").empty();
		$("#newFireStation-powerid").append(
				"<option value='' selected='selected'>---请选择---</option>");
		initFireStation(type);
	} else {
		$("#newFireStation-patrolCar").removeAttr("disabled", "disabled");
		$("#newFireStation-powerid").empty();
		$("#newFireStation-powerid").append(
				"<option value='' selected='selected'>---请选择---</option>");
		initFireStation(type);
	}
}

function fireStationViewType() {
	var type = $("#newFireStationView-type").val();
	if (type == 0) {
		$("#newFireStationView-powerid").empty();
		$("#newFireStationView-powerid").append(
		"<option value='' selected='selected'>---请选择---</option>");
		initFireStation(type);
	} else {
		$("#newFireStationView-powerid").empty();
		$("#newFireStationView-powerid").append(
		"<option value='' selected='selected'>---请选择---</option>");
		initFireStation(type);
	}
}
