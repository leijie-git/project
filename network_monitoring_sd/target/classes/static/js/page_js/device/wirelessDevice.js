$(function () {
	//主页面查询
	mainSearch();
	//初始化单位下拉框
	getNetDeviceList()
	// 初始化地图
	loadMap();
	// 最上层模态框关闭以后 底层模特框无法滚动问题
	$(".notLastModal").on("hidden.bs.modal", function() {
		// $("#newProject").addClass("modal-open");
		$("#newEmploy").css("overflow-y", "auto");
	});
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
    // 编辑
    $(".btnUpdate").click(function () {
    	$("#newEmploy").modal("show");
        var importNetDevice = $("#importNetDevice").val();
        var installaddr = $("#installaddr").val();
        var notifyphone = $("#notifyphone").val();
        var lon = $("#newFirePower-pointX").val();
		var lat = $("#newFirePower-pointY").val();
		var devicecode = $("#devicecode").val();
        var id = $("#indexId").val();
//        var heartbeat = $("#heartbeat").val();
        var isphone = $("input:radio[name='AutomaticDialing']:checked").val();
        // var unitId = $("#unitId").val();
        // var buildId = $("#buildId").val();
        // var buildAreaId = $("#buildAreaId").val();
        var data = {
        		id:id,
        		installaddr:installaddr,
        		notifyphone:notifyphone,
        		lon:lon,
        		lat:lat,
        		netdeviceid:importNetDevice,
        		devicecode:devicecode,
        		isphone:isphone,
                // unitId:unitId,
                // buildId:buildId,
                // buildAreaId:buildAreaId
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
                    $("#userTable").bootstrapTable("refresh");
                    $("#newEmploy").modal("hide");
                } else {
                    layer.msg(d.msg);
                }
            },
            error: function (data) {

            }
        });
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
    $("#searchContent").val('');
    $("#partcode").val('');
    $("#keyword").val('');
}
// 编辑
function editBaseInfo(_this) {
	$("#newEmploy").modal("show");
	$("#gatewaycode").val(_this.code);
    $("#importNetDevice").val(_this.netdeviceid);
    $("#installaddr").val(_this.installaddr);
    $("#notifyphone").val(_this.notifyphone);
    $("#indexId").val(_this.id);
    $("#devicecode").val(_this.devicecode);
    $("#newFirePower-pointX").val(_this.lon);
	$("#newFirePower-pointY").val(_this.lat);

    // var id = _this.unitId;
    // getUnitList();
    // showUnitList(id);
    //
    // $("#unitId").val(_this.unitId);
    // getBuildList(id);
    // $("#buildId").val(_this.buildId);
    // getBuildAreaList(_this.buildId);
    // $("#buildAreaId").val(_this.buildAreaId);

//	$("#heartbeat").val(_this.heartbeat);
	if(_this.isphone == "0"){
		$("#AutomaticDialing0").prop("checked",true);
	}else{
		$("#AutomaticDialing1").prop("checked",true);
	}
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#gatewaycodeView").val(_this.code);
    $("#importNetDeviceView").val(_this.netdeviceid);
    $("#installaddrView").val(_this.installaddr);
    $("#notifyphoneView").val(_this.notifyphone);
    $("#indexId").val(_this.id);
    $("#devicecodeView").val(_this.devicecode);

    // var id = _this.unitId;
    // getUnitList();
    // showUnitList(id);
    //
    // $("#unitIdView").val(_this.unitId);
    // getBuildList(id);
    // $("#buildIdView").val(_this.buildId);
    // getBuildAreaList(_this.buildId);
    // $("#buildAreaIdView").val(_this.buildAreaId);

//  $("#heartbeatView").val(_this.heartbeat);
    $("#importNetDeviceView").attr("disabled","disabled");
    if(_this.isphone == "0"){
		$("#AutomaticDialingView0").prop("checked",true);
	}else{
		$("#AutomaticDialingView1").prop("checked",true);
	}
}

//删除某个设备
function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中无线设备？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/wirelessDevice/delete",
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
        url: baseUrl + '/wirelessDevice/getList',         //请求后台的URL（*）
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
                keyword: $("#keyword").val(),
                partcode:$("#partcode").val()
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
        columns: [
            {
                field: 'code',
                title: '设备ID'
            },
            {
            	field: 'devicecode',
            	title: '二维码编号'
            },
            {
            	field: 'installaddr',
            	title: '安装地点'
            },
            {
            	field: 'notifyphone',
            	title: '通知电话'
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





// 这段暂定
// 获取单位列表
// function getUnitList() {
//     $.ajax({
//         type : "get",
//         async : false,
//         url : baseUrl + "/baseInfo/getArrayList",
//         dataType : "json",
//         success : function(d) {
//             if (d.success) {
//                 unitList = d.obj;
//             }
//         },
//         error : function(e) {
//
//         }
//     });
// }
//
// // 填充单位下拉框
// function showUnitList(unitID) {
//     var a = '<option value="%unitID%" >%unitName%</option>';
//     var option;
//     var wrap = '<option value="">--搜索--</option>';
//     for (var i = 0; i < unitList.length; i++) {
//         if (unitID && unitID == unitList[i].id) {
//             option = '<option selected value="' + unitList[i].id + '" >'
//                 + unitList[i].unitname + '</option>';
//         } else {
//             option = a.replace("%unitID%", unitList[i].id);
//             option = option.replace("%unitName%", unitList[i].unitname);
//         }
//         wrap += option;
//     }
//     $("#unitId").html(wrap);
//     $("#unitId").comboSelect();
//     $("#unitIdView").html(wrap);
// }
//
// // 切换单位
// $("#unitId").bind("change", function() {
//     emptyBuildList();
//     emptyBuildAreaList();
//     getBuildList($("#unitId").val());
// });
//
// // 获取建筑物列表
// function getBuildList(UnitID) {
//     // var UnitID = $("#unitId").find("option:selected").val();
//     var data = {
//         UnitID : UnitID
//     };
//     $.ajax({
//         type : "get",
//         async : false,
//         data : data,
//         url : baseUrl + "/build/getArrayList",
//         dataType : "json",
//         success : function(d) {
//             if (d.success) {
//                 buildList = d.obj;
//                 if(UnitID != null && UnitID != ""){
//                     showBuildList();
//                 }
//             } else {
//                 layer.msg(d.desc);
//             }
//         },
//         error : function(e) {
//
//         }
//     });
// }
//
// // 填充建筑物下拉框
// function showBuildList() {
//     var a = '<option value="%buildId%">%buildName%</option>';
//     var option;
//     var wrap = '<option value="">--请选择--</option>';
//     if (buildList) {
//         for (var i = 0; i < buildList.length; i++) {
//             option = a.replace("%buildId%", buildList[i].id);
//             option = option.replace("%buildName%", buildList[i].buildingName);
//             wrap += option;
//         }
//         $("#buildId").html(wrap);
//         $("#buildIdView").html(wrap);
//     }
// }
// function emptyBuildList() {
//     $("#buildId").empty();
//     $("#buildId").html('<option value="">--请选择--</option>');
//     $("#buildIdView").empty();
//     $("#buildIdView").html('<option value="">--请选择--</option>');
// }
// // 切换建筑物
// $("#buildId").bind("change", function() {
//     emptyBuildAreaList();
//     getBuildAreaList($("#buildId").val());
// });
//
// // 获取区域列表
// function getBuildAreaList(buildId) {
//     // var buildId = $("#buildId").find("option:selected").val();
//     // if(buildId == ""){
//     //     buildId = $("#buildIdView").find("option:selected").val();
//     // }
//     var data = {
//         buildID : buildId
//     }
//     $.ajax({
//         type : "get",
//         async : false,
//         data : data,
//         url : baseUrl + "/buildArea/getArrayList",
//         dataType : "json",
//         success : function(d) {
//             if (d.success) {
//                 buildAreaList = d.obj
//                 if(buildId != null && buildId != ""){
//                     showBuildAreaList();
//                 }
//             } else {
//                 layer.msg(d.desc);
//             }
//         },
//         error : function(e) {
//
//         }
//     });
// }
// // 填充分区下拉框
// function showBuildAreaList() {
//     var a = '<option value="%BuildAreaId%">%BuildAreaName%</option>';
//     var option;
//     var wrap = '<option value="">--请选择--</option>';
//     if (buildAreaList) {
//         for (var i = 0; i < buildAreaList.length; i++) {
//             option = a.replace("%BuildAreaId%", buildAreaList[i].id);
//             option = option.replace("%BuildAreaName%",
//                 buildAreaList[i].buildAreaName);
//             wrap += option;
//         }
//         $("#buildAreaId").html(wrap);
//         $("#buildAreaIdView").html(wrap);
//     }
// }
// // 清空分区下拉框
// function emptyBuildAreaList() {
//     $("#buildAreaId").empty();
//     $("#buildAreaId").html('<option value="">--请选择--</option>');
//     $("#buildAreaIdView").empty();
//     $("#buildAreaIdView").html('<option value="">--请选择--</option>');
// }







//获取联网设备列表
function getNetDeviceList(){
	$.ajax({
		url: baseUrl + "/wirelessDevice/getNetDeviceList",
		type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		dataType: "json",
		success: function (data) {
			if(data.success){
				$("#importNetDevice").append('<option value="" >--请选择--</option>');
				for(var i=0;i<data.obj.length;i++){
					$("#importNetDevice").append('<option value="'+data.obj[i].id+'" >'+data.obj[i].name+'</option>');
					$("#importNetDeviceView").append('<option value="'+data.obj[i].id+'" >'+data.obj[i].name+'</option>');
				}
			}
		}
	})
}

//导入设备
$(".importBuilding").click(function(){
	/*$.ajax({
        url: baseUrl + "wirelessDevice/test",
        type: "post",
        dataType: "json",
        success: function (data) {
        	
        }
    })*/
//    var htm = baseUrl+"wirelessDevice/test";
//	window.location.href=htm;
	
	$('.fileName').html("");
	$("#DeviceType").val('');
	$("#heartbeats").val('');
	$(".uploadExcel").val("");
});

function doChange(file) {
    var upload_file = $(file).val();    //获取上传文件
    $('.fileName').html(upload_file);     //赋值给自定义input框
};

$("#DeviceType").bind("change",function(){
	$("#usingtype").val( $("#DeviceType").val());
	
})
//导入
$(".btnImport").click(function(){
	var formData = new FormData($("#projectForm")[0]);
	var pid = $("#DeviceType").val();
//	var heartbeat = $("#heartbeats").val();
	if(pid == null || pid == ''){
		layer.msg("请选择设备类型！");
		return;
	}
//	if(heartbeat == null || heartbeat == ''){
//		layer.msg("请填写心跳时间！");
//		return;
//	}
    var uploadExcel =$(".uploadExcel").val();
    if (uploadExcel == "") {
        layer.msg("请选择上传文件！");
        return;
    }
//    var data = {uploadExcel:uploadExcel,importProjectPid:pid};
    $.ajax({
        url: baseUrl + "/wirelessDevice/importData",
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
            $("#userTable").bootstrapTable("refresh");
        }
    })
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
					$("#newFirePower-pointX").val(msg.point.lng);
					$("#newFirePower-pointY").val(msg.point.lat);
					var marker = new BMap.Marker(baidu_Point); // 标记
					map.addOverlay(marker);
					marker.enableDragging();// 可以拖拽标点
					marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
						var p = marker.getPosition();// 获取marker的位置
						$("#newFirePower-pointX").val(p.lng);
						$("#newFirePower-pointY").val(p.lat);
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
	myCity.get(function(res) {
		map.centerAndZoom(res.center, res.level);
	});
	
	setTimeout(function() {
		map.clearOverlays(); // 清除地图上点

		var pointX = $("#newFirePower-pointX").val();
		var pointY = $("#newFirePower-pointY").val();
		if (pointX != "" && pointY != "") {
			var baidu_Point = new BMap.Point(pointX, pointY); // 经纬度坐标
			var marker = new BMap.Marker(baidu_Point); // 标记
			map.addOverlay(marker);
			map.panTo(baidu_Point);
			marker.enableDragging();// 可以拖拽标点
			marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
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
		var baidu_Point = new BMap.Point(e.point.lng, e.point.lat); // 经纬度坐标
		var marker = new BMap.Marker(baidu_Point); // 标记
		map.addOverlay(marker);
		marker.enableDragging();// 可以拖拽标点
		marker.addEventListener("dragend", function(e) {// 拖拽获取marker位置
			var p = marker.getPosition();// 获取marker的位置
			$("#newFirePower-pointX").val(p.lng);
			$("#newFirePower-pointY").val(p.lat);
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
$(".map-search").click(function(){
	local.search($("#map_search").val());
});  
