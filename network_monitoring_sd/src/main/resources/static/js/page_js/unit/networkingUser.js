var unitId = $("#unitId").val();
var unitList;
var netWorkUnitId;
$(function() {
	// 主页面查询
	getUnitID();
    //mainSearch();
	initUnit();
	getUnitList();initDataSource(1);
	// 日期选择控件
	$("#newNetworkingUser-birthday").jeDate({
		ishmsVal : false,
		minDate : '1900-01-01 00:00:00',
		maxDate : '2099-12-31 23:59:59',
		format : "YYYY-MM-DD",
		zIndex : 3000
	})
	// 日期选择控件
	$("#newNetworkingUser-certificatesdate").jeDate({
		ishmsVal : false,
		minDate : '1900-01-01 00:00:00',
		maxDate : '2099-12-31 23:59:59',
		format : "YYYY-MM-DD",
		zIndex : 3000
	})
	$("#newNetworkingUser-expirytime").jeDate({
		ishmsVal : false,
		minDate : '1900-01-01 00:00:00',
		maxDate : '2099-12-31 23:59:59',
		format : "YYYY-MM-DD",
		zIndex : 3000
	})

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

	// 新增按钮$(".newAdd").click(
			function() {$("#preIdWrapRole>span").html("暂无数据");
        $("#preIdRole").html("");
				showUnitList(netWorkUnitId);
        if(netWorkUnitId){
            getBuildList();
        }
				$("#newEmploy").modal("show");

				$("#newEmploy .modal-title").html("新增联网单位人员");
				$("#newEmploy .btnSure").show();


				$(".password").removeAttr("style");
				$("#newNetworkingUser-unitid").removeAttr("disabled");


				$("#newNetworkingUser-account").val("");
				$("#newNetworkingUser-password").val("");
				$("#newNetworkingUser-unitid").val("");
				$("#newNetworkingUser-userrole").val("");
				$("#newNetworkingUser-sex1").prop("checked", "checked");
				$("#newNetworkingUser-username").val("");
				$("#newNetworkingUser-birthday").val("");
				$("#newNetworkingUser-email").val("");
				$("#newNetworkingUser-mobilephone").val("");
				$("#newNetworkingUser-department").val("");
				$("#newNetworkingUser-post").val("");
				$("#newNetworkingUser-certificatestype").val("");
				$("#newNetworkingUser-certificatesno").val("");
				$("#newNetworkingUser-certificatesdate").val("");
				$("#newNetworkingUser-expirytime").val("");
				$("#newNetworkingUser-licenseno").val("");
				$("#picurls-photo").attr("file_string", "");
				$("#loadImg-photo").html(
						'<li class="modalImg"><span>请选择上传图片</span></li>');
				$("#picurls-certificatespic").attr("file_string", "");
				$("#loadImg-certificatespic").html(
						'<li class="modalImg"><span>请选择上传图片</span></li>');
				$("input[name='ReceiveAlarmType'][value!='1']").removeAttr("checked");
				$("#editId").val("0");
				$("#indexId").val("");

        if (unitId == null || unitId == "") {
            $(".role").hide();
            $("#newNetworkingUser-userrole").val(1);
        } else {
            $(".unit").hide();
            $("#newNetworkingUser-unitid").val(unitId);
        }

    });

	// 保存并继续
	// $("#continue").click(function(){
	// var soureaddress = $("#newBaseInfoRelation-soureaddress").val();
	// var unitid = $("#newBaseInfoRelation-unitid").val();
	//        
	// if (soureaddress == "") {
	// layer.msg("绑定编号不能为空！");
	// return;
	// }
	// if (unitid == "" || unitid == null) {
	// layer.msg("单位不能为空！");
	// return;
	// }
	//        
	// var url = baseUrl + "/baseInfoRelation/add";
	// var data = {
	// soureaddress:soureaddress,
	// unitid:unitid
	// };
	// $.ajax({
	// url: url,
	// type: "post",
	// data: data,
	// dataType: "json",
	// success: function (d) {
	// if (d.success) {
	// layer.msg(d.msg);
	// $("#userTable").bootstrapTable("refresh");
	// $("#newBaseInfoRelation-soureaddress").val("");
	// } else {
	// layer.msg(d.msg);
	// }
	// },
	// error: function (data) {
	//
	// }
	// });
	//        
	// });
	// 新增或编辑
	$(".btnSure").click(
			function() {
				var editId = $("#editId").val();
				var id = $("#indexId").val();
				//alert($("#indexId").val());var account = $("#newNetworkingUser-account").val();
				var password = $("#newNetworkingUser-password").val();
				//var architecture = $("#newNetworkingUser-architecture").val();
            varunitid = $("#newNetworkingUser-unitid").find("option:selected").val();
            var preId = $("#preId").val();
				var userrole = $("#newNetworkingUser-userrole").val();
				var sex = $("input[name='sex']:checked").val();
				var username = $("#newNetworkingUser-username").val();
				var birthday = $("#newNetworkingUser-birthday").val();
				var email = $("#newNetworkingUser-email").val();
				var mobilephone = $("#newNetworkingUser-mobilephone").val();
				var department = $("#newNetworkingUser-department").val();
				var post = $("#newNetworkingUser-post").val();
				var expirytime = $("#newNetworkingUser-expirytime").val();
				var certificatestype = $("#newNetworkingUser-certificatestype")
						.val();
				var certificatesno = $("#newNetworkingUser-certificatesno")
						.val();
				var certificatesdate = $("#newNetworkingUser-certificatesdate")
						.val();
				var licenseno = $("#newNetworkingUser-licenseno").val();
				var photo = $("#picurls-photo").attr("file_string");
				var certificatespic = $("#picurls-certificatespic").attr(
						"file_string");
				var certificatespic = "";
				$('#loadImg-certificatespic').find('li').each(function(){
					if($(this).children("img").attr("src") != "" && $(this).children("img").attr("src") != null){
						certificatespic += $(this).children("img").attr("src")+","; 
					}
				});
				certificatespic = certificatespic.substr(0, certificatespic.length - 1);  
				
				var receivealarmtype = "";
				$("input[name='ReceiveAlarmType']:checked").each(function(j) {
				    if (j >= 0) {
				        receivealarmtype += $(this).val() + ","
				    }
				});
				receivealarmtype = receivealarmtype.substring(0,receivealarmtype.length-1);
				//getImgSrc(certificatespic);
				if (account == "") {
					layer.msg("账号不能为空！");
					return;
				}
				
				if (unitId == null || unitId == "") {
					if (unitid == null || unitid == "") {
						layer.msg("单位不能为空！");
						return;
					}
				} else {
					if (userrole == null || userrole == "") {
						layer.msg("用户角色不能为空！");
						return;
					}
				}
				if (username == "") {
					layer.msg("姓名不能为空！");
					return;
				}
				if (mobilephone == ""){
					layer.msg("手机号码不能为空！");
					return;
				}

				var editId = $("#editId").val();
				var url = ""if (editId == 0) {
					if (password == "") {
						layer.msg("密码不能为空！");
						return;
					}
					 url = baseUrl + "/networkingUser/add";
					var data = {
						account : account,
						password : password,
						//architecture:architecture,userrole : userrole,
						sex : sex,
						username : username,
						birthday : birthday,
						email : email,
						mobilephone : mobilephone,
						department : department,
						post : post,
						certificatestype : certificatestype,
						certificatesno : certificatesno,
						certificatesdate : certificatesdate,
						licenseno : licenseno,
						photo : photo,
						certificatespic : certificatespic,
						unitid : unitid,relationID:preId,
						relationID:preId,receivealarmtype:receivealarmtype,
						expirytime:expirytime
					};
					$.ajax({
						url : url,
						type : "post",beforeSend: function(request){
                        request.setRequestHeader("Authorization","Bearer "+login.token);
                    },
						data : data,
						dataType : "json",
						success : function(d) {
							if (d.success) {
								layer.msg(d.msg);
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
					 url = baseUrl + "/networkingUser/update";
					var data = {
						id : id,
						account : account,
						password : password,
						//architecture:architecture,userrole : userrole,
						sex : sex,
						username : username,
						birthday : birthday,
						email : email,
						mobilephone : mobilephone,
						department : department,
						post : post,
						certificatestype : certificatestype,
						certificatesno : certificatesno,
						certificatesdate : certificatesdate,
						licenseno : licenseno,
						photo : photo,
						certificatespic : certificatespic,
						unitid : unitid,relationID:preId,
						receivealarmtype:receivealarmtype,
						expirytime:expirytime
					};
					$.ajax({
						url : url,
						type : "post",beforeSend: function(request){
                        request.setRequestHeader("Authorization","Bearer "+login.token);
                    },
						data : data,
						dataType : "json",
						success : function(d) {
							if (d.success) {
								layer.msg(d.msg);
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

    $("#remove").click(function() {
        $("#select1 option:selected").appendTo("#select2");
    });

    $("#remove_all").click(function() {
        $("#select1 option").appendTo("#select2");
    });

    $("#add").click(function() {
        $("#select2 option:selected").appendTo("#select1");
    });

    $("#add_all").click(function() {
        $("#select2 option").appendTo("#select1");
    });

    $("#select1").dblclick(function() {
        $("#select1 option:selected").appendTo("#select2");

    });

    $("#select2").dblclick(function() {
        $("#select2 option:selected").appendTo("#select1");
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
    $("#username").val('');
    $("#unitname").val('');
    $("#account").val('');
}

//获取当前用户所属单位
function getUnitID() {
    $.ajax({
        type : "get",
        async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url : baseUrl + "/inspectPlan/getUnitID",
        dataType : "json",
        success : function(d) {
            if (d.success) {
                netWorkUnitId = d.obj;
                mainSearch();
            } else {
                layer.msg(d.msg);
            }
        },
        error : function(e) {

        }
    });
}

// 编辑
function editBaseInfo(_this) {
	
	showUnitList(_this.unitid);
	getBuildList(0);$("#newEmploy").modal("show");
	$("#newEmploy .modal-title").html("编辑联网单位人员");
	$("#newEmploy .btnSure").show();

	var id = _this.id;
	$("#indexId").val(id);
	$("#editId").val("1");

    if (unitId == null || unitId == "") {
        $(".role").hide();
    } else {
        $(".unit").hide();
    }

    // 获取建筑和区域
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl + "/baseInfo/getUserBuildAndArea",
        data: {"userId": id},
        dataType: "json",
        success: function (d) {
            if (d.success) {

                // if (d.obj) {
                //     $("#preIdWrapRole > span").html('已选' + d.obj.length + '项');
                // } else {
                //     $("#preIdWrapRole > span").html('已选0项');
                // }
                //$("#preId").val(setPreId(d.obj.userId));
                //initDataSource(1);
                var arr = d.obj;
                $("#preIdWrapRole > span").html('已选' + arr.length + '项');
                var newArr = [];
                for (var i = 0; i < arr.length; i++) {
                    newArr.push(arr[i].id);
                }
                var preId = newArr.toString();
                $("#preId").val(preId);
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

            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });

    $("#newNetworkingUser-account").val(_this.account);
    $(".password").attr("style", "display:none");
    $("#newNetworkingUser-unitid").val(_this.unitid);
    //$("#preIdWrapRole").val(_this.preIdWrapRole);
    $("#newNetworkingUser-userrole").val(_this.userrole);
    $("#newNetworkingUser-username").val(_this.username);
    $("#newNetworkingUser-birthday").val(_this.birthday);
    $("#newNetworkingUser-email").val(_this.email);
    $("#newNetworkingUser-mobilephone").val(_this.mobilephone);
    $("#newNetworkingUser-department").val(_this.department);
    $("#newNetworkingUser-post").val(_this.post);
    $("#newNetworkingUser-certificatestype").val(_this.certificatestype);
    $("#newNetworkingUser-certificatesno").val(_this.certificatesno);
    $("#newNetworkingUser-certificatesdate").val(_this.certificatesdate);
    $("#newNetworkingUser-licenseno").val(_this.licenseno);
    $("#newNetworkingUser-expirytime").val(_this.expirytime);
    $("input[name='ReceiveAlarmType'][value!='1']").removeAttr("checked");

    var sex = _this.sex;
    if (sex == 1) {
        $("#newNetworkingUser-sex1").prop("checked", "checked");
        $("#newNetworkingUser-sex0").removeAttr("checked");
    } else {
        $("#newNetworkingUser-sex0").prop("checked", "checked");
        $("#newNetworkingUser-sex1").removeAttr("checked");
    }

    var certificatespics = _this.certificatespic;
    if (certificatespics) {
        var certificatespic = certificatespics.split(",");
        var props = "";
        var htm = "";
        for(var i=0;i<certificatespic.length;i++){
            props += certificatespic[i];
            htm += " <li style='position: relative;margin: 0px;border: none;'> "
                + "  <img src='" + certificatespic[i] + "' "
                + " style = 'width:100%;height:100%;'/>"
                + '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">'
                + " <img style='width: 15px;height: 15px;position: absolute;top: 3px;right: 3px;' src='"+baseUrl+"/image/mima.png' class='delete'/>"
                + " </a>" + " </li>";
        }
        $("#picurls-certificatespic").attr("file_string", props);
        $("#loadImg-certificatespic").html("<li class='modalImg'><span>请选择上传图片</span></li>" + htm);

        /*$("#loadImg-certificatespic")
                .html(
                        "<li class='modalImg'><span>请选择上传图片</span></li>"
                                + " <li style='position: relative;margin: 0px;border: none;'> "
                                + "  <img src='"
                                + certificatespic
                                + "' "
                                + " style = 'width:100%;height:100%;'/>"
                                + '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">'
                                + " </a>" + " </li>");*/
    } else {
        $("#picurls-certificatespic").attr("file_string", "");
        $("#loadImg-certificatespic").html(
            "<li class='modalImg'><span>请选择上传图片</span></li>");

    }
    var photo = _this.photo;
    if (photo) {
        $("#picurls-photo").attr("file_string", photo);
        $("#loadImg-photo")
            .html(
                "<li class='modalImg'><span>请选择上传图片</span></li>"
                + " <li style='position: relative;margin: 0px;border: none;'> "
                + "  <img src='"
                + photo
                + "' "
                + " style = 'width:100%;height:100%;'/>"
                + '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">'
                + " </a>" + " </li>");
    } else {
        $("#picurls-photo").attr("file_string", "");
        $("#loadImg-photo").html(
            "<li class='modalImg'><span>请选择上传图片</span></li>");
    }

    var receivealarmtype = _this.receivealarmtype;
    if(receivealarmtype != null && receivealarmtype != ''){
        var arr = receivealarmtype.split(",");
        for(var i=0;i<arr.length;i++){
            $("input[name='ReceiveAlarmType'][value="+arr[i]+"]").prop("checked","checked");
        }
    }
}
// 查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newEmploy .modal-title").html("查看单位绑定数据");
	$("#newEmploy .btnSure").show();
	// $("#continue").hide();
	var id = _this.id;
	$("#indexId").val(id);
	$("#editId").val("1");

    //单独获取选择的建筑个数
    $.ajax({
        type: "get",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        url: baseUrl + "/baseInfo/getUserBuildAndArea",
        data: {"userId": id},
        dataType: "json",
        success: function (d) {
            if (d.success) {
                var arr = d.obj;
                $("#preIdRole-look").val('已选' + arr.length + '项');
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });

    $("#newNetworkingUserView-account").val(_this.account);
    $("#newNetworkingUserView-password").val("");
    //$("#newNetworkingUserView-architecture").val("");
    $("#newNetworkingUserView-unitid").val(_this.unitid);//这里是单位列表回调的地方
    //在它的下面请求这个请求建筑列表的方法
    //getBuildList();
    //$("#preIdWrapRole").val(_this.preIdWrapRole);
    $("#newNetworkingUser-userrole").val(_this.userrole);
    $("#newNetworkingUserView-username").val(_this.username);
    $("#newNetworkingUserView-birthday").val(_this.birthday);
    $("#newNetworkingUserView-email").val(_this.email);
    $("#newNetworkingUserView-mobilephone").val(_this.mobilephone);
    $("#newNetworkingUserView-department").val(_this.department);
    $("#newNetworkingUserView-post").val(_this.post);
    $("#newNetworkingUserView-certificatestype").val(_this.certificatestype);
    $("#newNetworkingUserView-certificatesno").val(_this.certificatesno);
    $("#newNetworkingUserView-certificatesdate").val(_this.certificatesdate);
    $("#newNetworkingUserView-licenseno").val(_this.licenseno);
    $("#newNetworkingUserView-expirytime").val(_this.expirytime);
    $("input[name='ReceiveAlarmTypeView'][value!='1']").removeAttr("checked");
    // $("#picurls-photo").prop("file_string");
    // $("#picurls-certificatespic").prop("file_string");

    var sex = _this.sex;
    if (sex == 1) {
        $("#newNetworkingUserView-sex1").prop("checked", "checked");
        $("#newNetworkingUserView-sex0").removeAttr("checked");
    } else {
        $("#newNetworkingUserView-sex0").prop("checked", "checked");
        $("#newNetworkingUserView-sex1").removeAttr("checked");
    }

    var certificatespics = _this.certificatespic;
    if (certificatespics) {
        var certificatespic = certificatespics.split(",");
        var props = "";
        var htm = "";
        for(var i=0;i<certificatespic.length;i++){
            props += certificatespic[i];
            htm += " <li style='position: relative;margin: 0px;border: none;'> "
                + "  <img src='" + certificatespic[i] + "' "
                + " style = 'width:100%;height:100%;'/>"
                + " </li>";
            /*$("#picurlsView-certificatespic").prop("file_string", certificatespic[i]);
            $("#loadImgView-certificatespic")
            .html(
                    "<li class=''></li>"
                    + " <li style='position: relative;margin: 0px;border: none;'> "
                    + "  <img src='" + certificatespic[i] + "' "
                    + " style = 'width:100%;height:100%;'/>"
                    + " </li>");*/
        }
        $("#picurlsView-certificatespic").prop(props);
        $("#loadImgView-certificatespic").html("<li class=''></li>" + htm);
    } else {
        $("#picurlsView-certificatespic").prop("file_string", "");
        $("#loadImgView-certificatespic").html("<li class=''></li>");

    }
    var photo = _this.photo;
    if (photo) {
        $("#picurlsView-photo").prop("file_string", photo);
        $("#loadImgView-photo")
            .html(
                "<li class=''></li>"
                + " <li style='position: relative;margin: 0px;border: none;'> "
                + "  <img src='" + photo + "' "
                + " style = 'width:100%;height:100%;'/>"
                + " </li>");
    } else {
        $("#picurlsView-photo").prop("file_string", "");
        $("#loadImgView-photo").html("<li class=''></li>");
    }

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
    var receivealarmtype = _this.receivealarmtype;
    if(receivealarmtype != null && receivealarmtype != ''){
        var arr = receivealarmtype.split(",");
        for(var i=0;i<arr.length;i++){
            $("input[name='ReceiveAlarmTypeView'][value="+arr[i]+"]").prop("checked","checked");
        }
    }
}

function deleteBaseInfo(_this) {
    var select = _this.id;
    layer.open({
        content : '确认删除选中人员？',
        btn : [ '确认', '取消' ],
        yes : function() {
            $.ajax({
                url : baseUrl + "/networkingUser/delete",
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

function resetPas(_this) {
    var select = _this.id;
    layer.open({
        content : '确认重置选中用户密码？',
        btn : [ '确认', '取消' ],
        yes : function() {
            $.ajax({
                url : baseUrl + "/networkingUser/resetPas",
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

// 主页面查询
function mainSearch() {
	$('#userTable')
			.bootstrapTable(
					{
						url : baseUrl + '/networkingUser/list', // 请求后台的URL（*）
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
                },queryParams : function queryParams(params) { // 设置查询参数
							var param = {
								pageNumber : this.pageNumber,
								pageSize : this.pageSize,
								username : $("#username").val(),
								unitname : $("#unitname").val(),
								account : $("#account").val()
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
									field : 'unitname',
									title : '单位名称'
								},
								{
									field : 'account',
									title : '账号'
								},
								{
									field : 'username',
									title : '姓名'
								},
								{
									field : 'userrole',
									title : '用户角色',
									formatter : function(value, row, index) {
										if (row.userrole == "0") {
											return "单位负责人";
										}
										if (row.userrole == "1") {
											return "单位管理人";
										}
										if (row.userrole == "2") {
											return "巡查员";
										}
										if (row.userrole == "3") {
											return "工程人员";
										}
										if (row.userrole == "4") {
											return "监控人员";
										}
									if (row.userrole == "7") {
                                return "九小场所";
                            }}
								},
								{
									field : '',
									title : '操作',
									formatter : function(value, row, index) {
										var str = "";
										str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
										str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
										str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
										str += '<button type="button" class="btn btn-primary btn-xs cBtn-main resetPas"><i class="fa fa-eye"></i>&nbsp;重置密码</button>'
										str += '<button type="button" class="btn btn-new btn-xs cBtn-main setPeople"><i class="fa fa-pencil"></i>&nbsp;设置角色</button>'return str;
									},
									events : {
										'click .edit' : function(e, value, row,
												index) {
											editBaseInfo(row);
										// editRole(row);},
										'click .del' : function(e, value, row,
												index) {
											deleteBaseInfo(row);
										},
										'click .view' : function(e, value, row,
												index) {
											viewBaseInfo(row);
										},
										'click .resetPas' : function(e, value,
												row, index) {
											resetPas(row);
										},
                        'click .setPeople': function (e, value, row, index) {
                            addSet(row);
                        }
									}
                    } ]
            });
}



// 设置角色函数
function addSet(_this){
    $("#addSetModal").modal("show");
    $('#select1').empty();
    $('#select2').empty();
    var id = _this.id;
    $("#indexId").val(id);
    var url = baseUrl + '/user/getUserRole';
    $.ajax({
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            id:id,
            type:1
        },
        url: url,
        dataType: "json",
        success: function (d) {
            if(d.success){
                var GetUserRolesData = d.obj;
                var hasRoles = GetUserRolesData.hasRoles;
                console.log("hasRoles:"+hasRoles);
                if(hasRoles){
                    for(var i=0;i<hasRoles.length;i++){
                        var option = ' <option id="'+hasRoles[i].id+'">'+hasRoles[i].roleName+'</option>'
                        $('#select2').append(option);
                    }
                }
                var noRoles = GetUserRolesData.noRoles;
                if(noRoles){
                    for(var i=0;i<noRoles.length;i++){
                        var option = ' <option id="'+noRoles[i].id+'">'+noRoles[i].roleName+'</option>'
                        $('#select1').append(option);
                    }
                }
            }
        }
    });
}

// 保存设置角色
$(".btn-save_role").click(function(){
    var userId = $("#indexId").val();
    var ids = "";
    for(var i = 0;i < $("#select2 option").length;i++){
        var id = $($("#select2 option").eq(i)).attr("id");
        ids += id +",";
    }

    var url = baseUrl + '/user/setUserRole';
    $.ajax({
        type:"post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
            userIdOne:userId,
            manyRoleId:ids,
            type:1
        },
        url: url,
        dataType: "json",
        success: function (d) {
            $("#setRoleModal").modal("hide");
            if(d.success){
                layer.msg("设置成功。");
                $("#addSetModal").modal("hide");
                $("#userTable").bootstrapTable("refresh");
            }else{
                layer.msg("设置失败");
            }

        }
    });
});

function doChangeProjectcertificatespic() {
    /*$("#loadImg-photo").html(
                        '<li class="modalImg"><span>请选择上传图片</span></li>');*/
    var file = "";
    uploadOneMany("picurls-certificatespic", "loadImg-certificatespic",
        "photoCover-certificatespic", false);
};
function doChangeProjectphoto() {
    uploadOne("picurls-photo", "loadImg-photo", "photoCover-photo", false);
};

// 初始化单位下拉选择框
function initUnit() {
    $.ajax({
        url : baseUrl + "/networkingUser/typeSelect",
        type : "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType : "json",
        success : function(data) {
            if (data.success) {
                for (var i = 0; i < data.obj.length; i++) {
                    $("#newNetworkingUser-unitid").append(
                        '<option value="' + data.obj[i].id + '">'
                        + data.obj[i].unitname + '</option>');
                    $("#newNetworkingUserView-unitid").append(
                        '<option value="' + data.obj[i].id + '">'
                        + data.obj[i].unitname + '</option>');
                }
            }
        }
    })
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
//根据单位获取建筑列表
function getBuildList(){
    var unitID =$("#newNetworkingUser-unitid").find("option:selected").val();
    // console.log(unitID);
    var data = {
        unitID:unitID
    };
    $.ajax({
        type: "post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data:data,
        url: baseUrl+"/baseInfo/getUnitBuildAndArea",
        dataType: "json",
        success: function (d) {
            if (d.success) {
                if(d.obj.length > 0){
                    var arr = d.obj;
                   /* if(notBack){
                        $("#preIdWrapRole > span").html('已选' + arr.length + '项');
                        var newArr = [];
                        for (var i = 0; i < arr.length; i++) {
                            newArr.push(arr[i].id);
                        }
                        var preId = newArr.toString();
                        $("#preId").val(preId);
                    }*/
                    convert(arr);
                }else{
                    $("#preIdWrapRole>span").html("暂无数据");
                    // $("#preIdRole").html("");
                    $("#preIdRole").html("");

                }
            } else {
                layer.msg(d.desc);
            }
        },
        error: function (e) {

        }
    });
}

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
    $("#newNetworkingUser-unitid").html(wrap);
    $("#newNetworkingUser-unitid").comboSelect();
}
$("#newNetworkingUser-unitid").change(function () {
    getBuildList();
});



function getImgSrc(certificatespic){
    $('#loadImg-certificatespic').find('li').each(function(){
        certificatespic += $(this).children("img").src;
    });
}

function checkPhotoNum(){
    var len = $('#loadImg-certificatespic').find('li').length;
    if(len>=4){
        $("#picurls-certificatespic").removeAttr("type");
        layer.msg("最多只能上上传三张图片！");
        return;
    }else{
        $("#picurls-certificatespic").attr("type","file");
    }
}
/*建筑添加  以下是我写的*/

//编辑建筑
/*function editRole(_this) {
  //  $("#newAdd .modal-title").html("编辑角色");
    // initDataSource(1);
    var indexId = _this.id;
    $("#edit").val('1');
    // $("#indexId").val(indexId);
    $("#newAdd").modal();
    var url = baseUrl + '/role/editRole';

    $.ajax({
        type: "get",
        async: true,
        data: {"id": indexId},
        url: url,
        dataType: "json",
        success: function (d) {
            if (d.success) {
                $("#roleName").val(d.obj.roleName);
//                $("#roleType").val(d.obj.roleType);
                if (d.obj.resourceId) {
                    $(".preIdWrapRole > span").html('已选' + d.obj.resourceId.length + '项');
                } else {
                    $(".preIdWrapRole > span").html('已选0项');
                }
                $("#preId").val(setPreId(d.obj.resourceId));
                $("#indexId").val(d.obj.id);
                initDataSource(1);
                $("#sortIndex").val(d.obj.sortIndex);
                mainSearch();
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (e) {

        }
    });


}*/

//资源管理（上级菜单）
function initDataSource(index) {
    no = index;
    $("#preIdWrapRole").click(function (e) {
        e.stopPropagation();
        $("#preIdRole").show();
    });
    $(document).click(function () {
        $("#preIdRole").hide();
    });
    $("#preIdRole").click(function (e) {
        e.stopPropagation();
    })
}
//将返回数据转换成数组
function convert(result) {
    var nodes = [];
    for (var i in result) {
        nodes.push(result[i]);
    }
    doInitSecondSource(nodes);
}
//给组织添加点击事件
function doInitSecondSource(nodes) {
    var setting = {
        view: {
            dblClickExpand: true//屏蔽掉双击事件
        },
        check:{
            enable: true,
            chkStyle: "checkbox"
        },
        data: {
            key: {
                url: "",
                name: "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: null
            }
        },
        callback: {
            onCheck: onCheck
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
function setPreId(list) {
    var vals = '';
    $(list).each(function (i, item) {
        if (vals == '') {
            vals = vals + item;
        } else {
            vals = vals + ',' + item;
        }
    });
    return vals;
}


