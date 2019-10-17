var map;
var local;
$(function() {
	loadMap();
	// 主页面查询
	initProvince();
	mainSearch();
	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$(".notLastModal").on("hidden.bs.modal", function() {
		// $("#newProject").addClass("modal-open");
		$("#newEmploy").css("overflow-y", "auto");
	});
	// 初始化下拉框
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
		$("#newEmploy .modal-title").html("新增维保单位");

		$("#unitCode").val("");
		$("#unitName").val("");
		$("#address").val("");
		$("#contacts").val("");
		$("#telephone").val("");
		$("#remark").val("");
		$("#pointX").val("");
		$("#pointY").val("");
		$("#proviceid").val("");
		$("#cityid").val("");
		$("#regionid").val("");
		$("#email").val("");
		$("#loadImg-unitLogo").html('<li class="modalImg"><span>请选择上传图片</span></li>');

		$("#editId").val("0");
		$("#indexId").val("");

	});

	// 新增或编辑
	$(".btnSure").click(function() {
		var editId = $("#editId").val();
		var id = $("#indexId").val();

		var unitCode = $("#unitCode").val();
		var unitName = $("#unitName").val();
		var address = $("#address").val();
		var contacts = $("#contacts").val();
		var telephone = $("#telephone").val();
		var remark = $("#remark").val();
		var pointX = $("#pointX").val();
		var pointY = $("#pointY").val();
		var proviceid = $("#proviceid").val();
		var cityid = $("#cityid").val();
		var regionid = $("#regionid").val();
		var email = $("#email").val();
		var unitLogo = $("#picurls-unitLogo").attr("file_string");

		if (unitCode == "") {
			layer.msg("请输入单位编号");
			return;
		}
		if (unitName == "" ) {
			layer.msg("请输入单位名称");
			return;
		}
		var editId = $("#editId").val();
		if (editId == 0) {
			var url = baseUrl + "/maintenanceunit/addMaintenanceUnit";
			var data = {
				unitcode : unitCode,
				unitname : unitName,
				address : address,
				contacts : contacts,
				telephone : telephone,
				remark : remark,
				pointx : pointX,
				pointy : pointY,
				proviceid : proviceid,
				cityid : cityid,
				regionid : regionid,
				logourl:unitLogo,
				email : email
			};
			$.ajax({
				url : url,
				type : "post",
				data : data,
				dataType : "json",
				success : function(d) {
					if (d.success) {
						layer.msg("添加成功");
						$("#userTable").bootstrapTable("refresh");
						$("#newEmploy").modal("hide");
					} else {
						layer.msg(d.msg);
					}
				},
				error : function(data) {

				}
			});
		} else {
			var url = baseUrl + "/maintenanceunit/updateMaintenanceUnit";
			var data = {
				id : id,
				unitcode : unitCode,
				unitname : unitName,
				address : address,
				contacts : contacts,
				telephone : telephone,
				remark : remark,
				pointx : pointX,
				pointy : pointY,
				proviceid : proviceid,
				cityid : cityid,
				regionid : regionid,
				logourl:unitLogo,
				email : email
			};
			$.ajax({
				url : url,
				type : "post",
				data : data,
				dataType : "json",
				success : function(d) {
					if (d.success) {
						layer.msg("修改成功");
						$("#userTable").bootstrapTable("refresh");
						$("#newEmploy").modal("hide");
					} else {
						layer.msg(d.msg);
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
	$("#powerName").val('');
	$("#name").val('');
}
// 编辑
function editBaseInfo(_this) {

	$("#newEmploy").modal("show");
	$("#newEmploy .modal-title").html("编辑维保单位");
	$("#newEmploy .btnSure").show();
	var id = _this.id;
	$("#indexId").val(id);
	$("#editId").val("1");

	$("#unitCode").val(_this.unitcode);
	$("#unitName").val(_this.unitname);
	$("#address").val(_this.address);
	$("#contacts").val(_this.contacts);
	$("#telephone").val(_this.telephone);
	$("#proviceid").val(_this.proviceid);
	citySelect(_this);
	$("#cityid").val(_this.cityid);
	regionSelect(_this)
	$("#regionid").val(_this.regionid);
	$("#email").val(_this.email);
	$("#remark").val(_this.remark);
	$("#pointX").val(_this.pointx);
	$("#pointY").val(_this.pointy);
	
	var logourl = _this.logourl;
	if (logourl) {
		$("#picurls-unitLogo").attr("file_string", logourl);
		$("#loadImg-unitLogo")
		.html(
				"<li class='modalImg'><span>请选择上传图片</span></li>"
				+ " <li style='position: relative;margin: 0px;border: none;'> "
				+ "  <img src='"
				+ logourl
				+ "' "
				+ " style = 'width:100%;height:100%;'/>"
				+ '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">'
				+ " </a>" + " </li>");
	} else {
		$("#picurls-unitLogo").attr("file_string", "");
		$("#loadImg-unitLogo").html(
		"<li class='modalImg'><span>请选择上传图片</span></li>");
	}

}
// 查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");

	$("#unitCode-View").val(_this.unitcode);
	$("#unitName-View").val(_this.unitname);
	$("#address-View").val(_this.address);
	$("#contacts-View").val(_this.contacts);
	$("#telephone-View").val(_this.telephone);
	$("#proviceid-View").val(_this.proviceid);
	citySelect(_this);
	$("#cityid-View").val(_this.cityid);
	regionSelect(_this)
	$("#regionid-View").val(_this.regionid);
	$("#email-View").val(_this.email);
	$("#remark-View").val(_this.remark);
	$("#pointX-View").val(_this.pointx);
	$("#pointY-View").val(_this.pointy);
	
	var logourl = _this.logourl;
	if (logourl) {
		$("#loadImg-view-unitLogo")
		.html(
				"<li ></li>"
				+ " <li style='position: relative;margin: 0px;border: none;'> "
				+ "  <img src='" + logourl + "' "
				+ " style = 'width:100%;height:100%;'/>"
				+ " </li>");
	} else {
		$("#loadImg-view-unitLogo").html("<li></li>");
	}

}

function deleteBaseInfo(_this) {
	var select = _this.id;
	layer.open({
		content : '确认删除选中编号信息？',
		btn : [ '确认', '取消' ],
		yes : function() {
			$.ajax({
				url : baseUrl + "/maintenanceunit/deleteMaintenanceUnit",
				type : "post",
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



function refreshData() {
	$.post(baseUrl + "/maintenanceunit/edit", function(data) {
		if (data.success) {
			$("#id").val(data.obj.id);
			$("#unitCode").val(data.obj.unitCode);
			$("#unitName").val(data.obj.unitName);
			$("#address").val(data.obj.address);
			$("#contacts").val(data.obj.contacts);
			$("#telephone").val(data.obj.telephone);
			$("#remark").val(data.obj.remark);
			$("#pointX").val(data.obj.pointX);
			$("#pointY").val(data.obj.pointY);
		}
	});
}

function loadMap() {
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

function showMap() {
	$("#mapModal").modal("show");
	var myCity = new window.BMap.LocalCity();
	myCity.get(function(res) {
		map.centerAndZoom(res.center, res.level);
	});
	
	setTimeout(function() {
		map.clearOverlays(); // 清除地图上点

		var pointX = $("#pointX").val();
		var pointY = $("#pointY").val();
		if (pointX != "" && pointY != "") {
			var baidu_Point = new BMap.Point(pointX, pointY); // 经纬度坐标
			var marker = new BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			map.panTo(baidu_Point);
			marker.enableDragging();// 可以拖拽标点
			marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
				var p = marker.getPosition();// 获取marker的位置
				$("#pointX").val(p.lng);
				$("#pointY").val(p.lat);
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
		$("#pointX").val(e.point.lng);
		$("#pointY").val(e.point.lat);
		var baidu_Point = new BMap.Point(e.point.lng, e.point.lat); // 经纬度坐标
		var marker = new BMap.Marker(baidu_Point); // 标记
		map.addOverlay(marker);
		marker.enableDragging();// 可以拖拽标点
		marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
			var p = marker.getPosition();// 获取marker的位置
			$("#pointX").val(p.lng);
			$("#pointY").val(p.lat);
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
$(".btn-search").click(function() {
	local.search($("#map_search").val());
});
// 主页面查询
function mainSearch() {
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
						queryParams : function queryParams(params) { // 设置查询参数
							var param = {
								pageNumber : this.pageNumber,
								pageSize : this.pageSize,
								unitname : $("#Search-unitName").val(),
								unitcode : $("#Search-unitCode").val()
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

/////////////////////////省市区下拉
function initProvince() {
	$.ajax({
		url : baseUrl + "/baseInfo/locateList",
		type : "post",
		data : {
			type : 1
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				for (var i = 0; i < data.obj.length; i++) {
					$("#proviceid").append(
							'<option value="' + data.obj[i].id + '">'
									+ data.obj[i].regionName + '</option>');
					$("#proviceid-View").append(
							'<option value="' + data.obj[i].id + '">'
									+ data.obj[i].regionName + '</option>');
				}
			}
		}
	});

	$("#proviceid").change(
			function() {
				$("#cityid").empty();
				$("#cityid").append('<option value="">---请选择---</option>');
				$("#regionid").empty();
				$("#regionid").append('<option value="">---请选择---</option>');
				var parentId = $("#proviceid").val();
				$.post(baseUrl + "/baseInfo/locateList", {
					type : 2,
					parentId : parentId
				}, function(data) {
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
				});
			});
	
	$("#cityid").change(
			function() {
				$("#regionid").empty();
				$("#regionid").append('<option value="">--请选择---</option>');
				$("#regionid").empty();
				$("#regionid").append('<option value="">--请选择---</option>');
				var parentId = $("#cityid").val();
				$.post(baseUrl + "/baseInfo/locateList", {
					type : 3,
					parentId : parentId
				}, function(data) {
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
				});
			});

}
function citySelect(_this) {
	$("#cityid").empty();
	$("#cityid-View").empty();
	$("#cityid").append('<option value="">--请选择---</option>');
	$("#cityid-View").append('<option value="">--请选择---</option>');
	var parentId = _this.proviceid;
	$.ajax({
		url : baseUrl + "/baseInfo/locateList",
		type : "post",
		data : {
			type : 2,
			parentId : parentId
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if (data.success) {
			for (var i = 0; i < data.obj.length; i++) {
				$("#cityid").append(
						'<option value="' + data.obj[i].id + '">'
								+ data.obj[i].regionName + '</option>');
				$("#cityid-View").append(
						'<option value="' + data.obj[i].id + '">'
								+ data.obj[i].regionName + '</option>');
			}
		}
		}
	});
}
function regionSelect(_this) {		
	$("#regionid").empty();
	$("#regionid-View").empty();
	$("#regionid").append('<option value="">--请选择---</option>');
	$("#regionid-View").append('<option value="">--请选择---</option>');
	var parentId = _this.cityid;
	$.ajax({
		url : baseUrl + "/baseInfo/locateList",
		type : "post",
		data : {
			type : 3,
			parentId : parentId
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if (data.success) {
			for (var i = 0; i < data.obj.length; i++) {
				$("#regionid").append(
						'<option value="' + data.obj[i].id + '">'
								+ data.obj[i].regionName + '</option>');
				$("#regionid-View").append(
						'<option value="' + data.obj[i].id + '">'
								+ data.obj[i].regionName + '</option>');
			}
		}
		}
	});

}

function doChangeProjectunitLogo() {
	uploadOne("picurls-unitLogo", "loadImg-unitLogo", "photoCover-unitLogo", false);
};
