$(function () {
	//主页面查询
	mainSearch();
	maintenanceUnitSelect();
	
	//日期选择控件
	$("#newMaintenanceUser-birthday").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
    //日期选择控件
    $("#newMaintenanceUser-certificatesdate").jeDate({
    	ishmsVal: false,
    	minDate: '1900-01-01 00:00:00',
    	maxDate: '2099-12-31 23:59:59',
    	format: "YYYY-MM-DD",
    	zIndex: 3000
    })
    //日期选择控件
    $("#newMaintenanceUser-expirytime").jeDate({
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
    //查询按钮查询
    $(".btnSearch").click(function () {
        $("#userTable").bootstrapTable('destroy');
        mainSearch();
    });
    //批量删除
    $(".btnDeleteMany").click(function () {
        deleteEmploy();
    });


    $(".newAdd").click(function () {
    	$("#newEmploy").modal("show");
        //$(".password").show();
        $("#newEmploy .modal-title").html("新增维保单位人员");
        $("#newEmploy .btnSure").show();
        //$("#continue").show();
        
        $("#newMaintenanceUser-unitid").val("");
        $("#newMaintenanceUser-account").val("");
        $("#newMaintenanceUser-password").val("");
        $("#newMaintenanceUser-password").removeAttr("disabled");
        $("#jgUserRole").removeAttr("checked");
        $("#wbUserRole").removeAttr("checked");
        $("#newMaintenanceUser-sex1").prop("checked","checked");
        $("#wbUserRole").prop("checked","checked");
        $("#newMaintenanceUser-username").val("");
        $("#newMaintenanceUser-birthday").val("");
        $("#newMaintenanceUser-email").val("");
        $("#newMaintenanceUser-mobilephone").val("");
        $("#newMaintenanceUser-department").val("");
        $("#newMaintenanceUser-post").val("");
        $("#newMaintenanceUser-certificatestype").val("");
        $("#newMaintenanceUser-certificatesno").val("");
        $("#newMaintenanceUser-certificatesdate").val("");
        $("#newMaintenanceUser-licenseno").val("");
        $("#newMaintenanceUser-expirytime").val("");
        $("#picurls-photo").attr("file_string","");
        $("#loadImg-photo").html('<li class="modalImg"><span>请选择上传图片</span></li>');
        $("#picurls-certificatespic").attr("file_string","");
        $("#loadImg-certificatespic").html('<li class="modalImg"><span>请选择上传图片</span></li>');
        $("input[name='ReceiveAlarmType'][value!='1']").removeAttr("checked");
        $("#editId").val("0");
        $("#indexId").val("");
        
    });
    
    
    //保存并继续
//    $("#continue").click(function(){
//        var soureaddress = $("#newBaseInfoRelation-soureaddress").val();
//        var unitid = $("#newBaseInfoRelation-unitid").val();
//        
//        if (soureaddress == "") {
//        	layer.msg("绑定编号不能为空！");
//        	return;
//        }
//        if (unitid == "" || unitid == null) {
//        	layer.msg("单位不能为空！");
//        	return;
//        }
//        
//        var url = baseUrl + "/baseInfoRelation/add";
//        var data = {
//        		soureaddress:soureaddress,
//        		unitid:unitid
//        };
//        $.ajax({
//            url: url,
//            type: "post",
//            data: data,
//            dataType: "json",
//            success: function (d) {
//                if (d.success) {
//                    layer.msg(d.msg);
//                    $("#userTable").bootstrapTable("refresh");
//                    $("#newBaseInfoRelation-soureaddress").val("");
//                } else {
//                    layer.msg(d.msg);
//                }
//            },
//            error: function (data) {
//
//            }
//        });
//        
//    });
    // 新增或编辑
    $(".btnSure").click(function () {
    	var editId = $("#editId").val();
        var id = $("#indexId").val();
        var account = $("#newMaintenanceUser-account").val();
        var unitid = $("#newMaintenanceUser-unitid").val();
        var password = $("#newMaintenanceUser-password").val();
        var sex = $("input[name='sex']:checked").val();
        var userRole = $("input[name='role']:checked").val();
        var username = $("#newMaintenanceUser-username").val();
        var birthday = $("#newMaintenanceUser-birthday").val();
        var email = $("#newMaintenanceUser-email").val();
        var mobilephone = $("#newMaintenanceUser-mobilephone").val();
        var department = $("#newMaintenanceUser-department").val();
        var post = $("#newMaintenanceUser-post").val();
        var expirytime = $("#newMaintenanceUser-expirytime").val();
        var certificatestype = $("#newMaintenanceUser-certificatestype").val();
        var certificatesno = $("#newMaintenanceUser-certificatesno").val();
        var certificatesdate = $("#newMaintenanceUser-certificatesdate").val();
        var licenseno = $("#newMaintenanceUser-licenseno").val();
        var photo = $("#picurls-photo").attr("file_string");
        
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

		if(unitid == "" || unitid == null){
			layer.msg("请选择单位！");
			return;
		}
		
        if (account == "") {
        	layer.msg("账号不能为空！");
        	return;
        }
        
        if (username == "") {
        	layer.msg("姓名不能为空！");
        	return;
        }
        var editId = $("#editId").val();
        if (editId == 0) {
        	if (password == "") {
        	layer.msg("密码不能为空！");
        	return;
    	}
        	
    	if (mobilephone == ""){
    		layer.msg("手机号码不能为空！");
        	return;
    	}
            var url = baseUrl + "/maintenanceUser/add";
            var data = {
            		unitid:unitid,
            		account:account,
            		password:password,
            		sex:sex,
            		username:username,
            		birthday:birthday,
            		email:email,
            		mobilephone:mobilephone,
            		department:department,
            		post:post,
            		certificatestype:certificatestype,
            		certificatesno:certificatesno,
            		certificatesdate:certificatesdate,
            		licenseno:licenseno,
            		photo:photo,
            		userrole:userRole,
            		certificatespic:certificatespic,
            		receivealarmtype:receivealarmtype,
            		expirytime:expirytime
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
            var url = baseUrl + "/maintenanceUser/update";
            var data = {
            		id:id,
            		unitid:unitid,
            		account:account,
            		sex:sex,
            		username:username,
            		birthday:birthday,
            		email:email,
            		mobilephone:mobilephone,
            		department:department,
            		post:post,
            		certificatestype:certificatestype,
            		certificatesno:certificatesno,
            		certificatesdate:certificatesdate,
            		licenseno:licenseno,
            		photo:photo,
            		userrole:userRole,
            		certificatespic:certificatespic,
            		receivealarmtype:receivealarmtype,
            		expirytime:expirytime
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
    $("#username").val('');
    $("#unitname").val('');
    $("#account").val('');
}
// 编辑
function editBaseInfo(_this) {
	
    
	$("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑维保单位人员");
    $("#newEmploy .btnSure").show();
    //$("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    $("#newMaintenanceUser-unitid").val(_this.unitid);
    $("#newMaintenanceUser-account").val(_this.account);
    $("#newMaintenanceUser-password").val("");
    $("#newMaintenanceUser-password").attr("disabled","disabled");
    $("#newMaintenanceUser-username").val(_this.username);
    $("#newMaintenanceUser-birthday").val(_this.birthday);
    $("#newMaintenanceUser-email").val(_this.email);
    $("#newMaintenanceUser-mobilephone").val(_this.mobilephone);
    $("#newMaintenanceUser-department").val(_this.department);
    $("#newMaintenanceUser-post").val(_this.post);
    $("#newMaintenanceUser-certificatestype").val(_this.certificatestype);
    $("#newMaintenanceUser-certificatesno").val(_this.certificatesno);
    $("#newMaintenanceUser-certificatesdate").val(_this.certificatesdate);
    $("#newMaintenanceUser-licenseno").val(_this.licenseno);
    $("#newMaintenanceUser-expirytime").val(_this.expirytime);
    $("input[name='ReceiveAlarmType'][value!='1']").removeAttr("checked");
    //$("#picurls-photo").prop("file_string");
    //$("#picurls-certificatespic").prop("file_string");  
    
    var sex = _this.sex;
    if(sex == 1){
    	$("#newMaintenanceUser-sex1").prop("checked","checked");
    	$("#newMaintenanceUser-sex0").removeAttr("checked");
    }else{
    	$("#newMaintenanceUser-sex0").prop("checked","checked");
    	$("#newMaintenanceUser-sex1").removeAttr("checked");
    }
    
    var role = _this.userrole;
    if(role == 5){
    	$("#wbUserRole").prop("checked","checked");
    	$("#jgUserRole").removeAttr("checked");
    }else if(role == 6){
    	$("#jgUserRole").prop("checked","checked");
    	$("#wbUserRole").removeAttr("checked");
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
		
	} else {
		$("#picurls-certificatespic").attr("file_string", "");
		$("#loadImg-certificatespic").html(
				"<li class='modalImg'><span>请选择上传图片</span></li>");

	}
	
    var photo = _this.photo;
    if(photo){
    	$("#picurls-photo").attr("file_string",photo);
    	$("#loadImg-photo").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photo + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-photo").attr("file_string","");
    	$("#loadImg-photo").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
    
    var receivealarmtype = _this.receivealarmtype;
	if(receivealarmtype != null && receivealarmtype != ''){
		var arr = receivealarmtype.split(",");
		for(var i=0;i<arr.length;i++){
			$("input[name='ReceiveAlarmType'][value="+arr[i]+"]").prop("checked","checked");
		}
	}
}
//查看
function viewBaseInfo(_this) {
	$("#newEmployView").modal("show");
	$("#newEmploy .modal-title").html("查看单位绑定数据");
    $("#newEmploy .btnSure").show();
    //$("#continue").hide();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    
    $("#newMaintenanceUserView-unitid").val(_this.unitid);
    $("#newMaintenanceUserView-account").val(_this.account);
    $("#newMaintenanceUserView-password").val("");
    $("#newMaintenanceUserView-username").val(_this.username);
    $("#newMaintenanceUserView-birthday").val(_this.birthday);
    $("#newMaintenanceUserView-email").val(_this.email);
    $("#newMaintenanceUserView-mobilephone").val(_this.mobilephone);
    $("#newMaintenanceUserView-department").val(_this.department);
    $("#newMaintenanceUserView-post").val(_this.post);
    $("#newMaintenanceUserView-certificatestype").val(_this.certificatestype);
    $("#newMaintenanceUserView-certificatesno").val(_this.certificatesno);
    $("#newMaintenanceUserView-certificatesdate").val(_this.certificatesdate);
    $("#newMaintenanceUserView-licenseno").val(_this.licenseno);
    $("#newMaintenanceUserView-expirytime").val(_this.expirytime);
    $("input[name='ReceiveAlarmTypeView'][value!='1']").removeAttr("checked");
    //$("#picurls-photo").prop("file_string");
    //$("#picurls-certificatespic").prop("file_string");
    $("#newMaintenanceUserView-unitid").attr("disabled","disabled");
    var sex = _this.sex;
    if(sex == 1){
    	$("#newMaintenanceUserView-sex1").prop("checked","checked");
    	$("#newMaintenanceUserView-sex0").removeAttr("checked");
    }else{
    	$("#newMaintenanceUserView-sex0").prop("checked","checked");
    	$("#newMaintenanceUserView-sex1").removeAttr("checked");
    }
    
    var role = _this.userrole;
    if(role == 5){
    	$("#wbUserRoleView").prop("checked","checked");
    	$("#jgUserRoleView").removeAttr("checked");
    }else if(role == 6){
    	$("#jgUserRoleView").prop("checked","checked");
    	$("#wbUserRoleView").removeAttr("checked");
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
		}
		$("#picurlsView-certificatespic").prop(props);
		$("#loadImgView-certificatespic").html("<li class=''></li>" + htm);
	} else {
		$("#picurlsView-certificatespic").prop("file_string", "");
		$("#loadImgView-certificatespic").html("<li class=''></li>");

	}
    var photo = _this.photo;
    if(photo){
    	$("#picurlsView-photo").prop("file_string",photo);
    	$("#loadImgView-photo").html(
    			"<li class=''></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + photo + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			" </li>"
    	);
    }else{
    	$("#picurlsView-photo").prop("file_string","");
    	$("#loadImgView-photo").html("<li class=''></li>");
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
        content: '确认删除选中编号信息？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/maintenanceUser/delete",
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

//绑定联网单位
/*function setRole(_this){
	$("#setRoleModal").modal("show");
	$('#select1').empty();
	$('#select2').empty();
	var id = _this.id;
	$("#indexId").val(id);
	var url = baseUrl + '/maintenanceUser/getUserReUnit';
	$.ajax({
        async: true,
        data: {id:id},
        url: url,
        dataType: "json",
        success: function (d) {
        	if(d.success){
        		var GetUserRolesData = d.obj;
        		var hasRoles = GetUserRolesData.hasUnits;
        		console.log("hasRoles:"+hasRoles);
        		if(hasRoles){
        			for(var i=0;i<hasRoles.length;i++){
        				var option = ' <option id="'+hasRoles[i].id+'">'+hasRoles[i].unitName+'</option>'
        				$('#select2').append(option);
        			}
        		}
        		var noRoles = GetUserRolesData.noUnits;
        		if(noRoles){
        			for(var i=0;i<noRoles.length;i++){
        				var option = ' <option id="'+noRoles[i].id+'">'+noRoles[i].unitName+'</option>'
        				$('#select1').append(option);
        			}
        		}
        	}
        }
	});
}*/

/*$(".btn-save_role").click(function(){
	var userId = $("#indexId").val();
	var ids = "";
	for(var i = 0;i < $("#select2 option").length;i++){
        var id = $($("#select2 option").eq(i)).attr("id");
        ids += id +",";
    }
	
	var url = baseUrl + '/maintenanceUser/setUserReUnit';
	$.ajax({
		type:"post",
        async: true,
        data: {userId:userId,manyUnitId:ids},
        url: url,
        dataType: "json",
        success: function (d) {
        	$("#setRoleModal").modal("hide");
        	if(d.success){
        		layer.msg("设置成功。");
        	}else{
        		layer.msg("设置失败");
        	}
        	
        }
	});
});*/

function resetPas(_this) {
	var select = _this.id;
	layer.open({
		content : '确认重置选中用户密码？',
		btn : [ '确认', '取消' ],
		yes : function() {
			$.ajax({
				url : baseUrl + "/maintenanceUser/resetPas",
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

//主页面查询
function mainSearch() {
    $('#userTable').bootstrapTable({
        url: baseUrl + '/maintenanceUser/list',         //请求后台的URL（*）
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
                username: $("#username").val(),
                unitname: $("#unitname").val(),
                account:$("#account").val()
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
            	field: 'account',
            	title: '账号'
            },
            {
            	field: 'username',
            	title: '姓名'
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-new btn-xs cBtn-main edit"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
            		str += '<button type="button" class="btn btn-danger btn-xs cBtn-main del"><i class="fa fa-trash"></i>&nbsp;删除</button>'
        			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main view"><i class="fa fa-eye"></i>&nbsp;查看</button>'
        			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main resetPas"><i class="fa fa-eye"></i>&nbsp;重置密码</button>'
        			str += '<button type="button" class="btn btn-new btn-xs cBtn-main setRole" style="margin-left: 10px;"><i class="fa fa-pencil"></i>&nbsp;绑定联网单位</button>'
                    str += '<button type="button" class="btn btn-new btn-xs cBtn-main setPeople"><i class="fa fa-pencil"></i>&nbsp;设置角色</button>'
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
                    'click .resetPas' : function(e, value,row, index) {
						resetPas(row);
					},
                    'click .setRole': function (e, value, row, index) {
                    	addQuestion(row.id);
                },
                'click .setPeople': function (e, value, row, index) {
                    addSet(row);
                }
            }
            }
        ]
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
	var file = "";
	uploadOneMany("picurls-certificatespic", "loadImg-certificatespic", "photoCover-certificatespic", false);
};
function doChangeProjectphoto() {
	uploadOne("picurls-photo", "loadImg-photo", "photoCover-photo", false);
};

///////////////////////////////////关联单位

function addQuestion(_id){
	$("#setRoleModal").modal("show");
	$("#indexId").val(_id);
	$('#noCheckTable').bootstrapTable("destroy");
	$('#checkTable').bootstrapTable("destroy");
	questionListSearch1();
	questionListSearch2();
}

function questionListSearch1() {
    $('#noCheckTable').bootstrapTable({
        url: baseUrl + '/maintenanceUser/getUserReUnit',         //请求后台的URL（*）
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
                unitName:$("#unitName1").val(),
                userId:$("#indexId").val(),
                isRelation:0
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
            	field: 'unitName',
            	title: '单位名称'
            }
        ]
    });
}
function questionListSearch2() {
	$('#checkTable').bootstrapTable({
		url: baseUrl + '/maintenanceUser/getUserReUnit',         //请求后台的URL（*）
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
					unitName:$("#unitName2").val(),
					userId:$("#indexId").val(),
					isRelation:1
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
            	field: 'unitName',
            	title: '单位名称'
            }
		]
	});
}

//人员新增单位
$(".addQuestionBtn").click(function(){
	var rows = $('#noCheckTable').bootstrapTable('getSelections');
	if(rows.length == 0){
		layer.msg("请勾选要关联的问题。")
		return;
	}
	var questionIds = "";
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		questionIds += row.id + ",";
	}
	 $.ajax({
         async: true,
         data: {userId:$("#indexId").val(),unitIds:questionIds},
         beforeSend: function(request){
             request.setRequestHeader("Authorization","Bearer "+login.token);
         },
         url: baseUrl+"/maintenanceUser/setUserReUnit",
         dataType: "json",
         success: function (d) {
             if (d.success) {
                 layer.msg("操作成功！")
                 $('#checkTable').bootstrapTable("refresh");
                 $('#noCheckTable').bootstrapTable("refresh");
             } else {
                 layer.msg(d.msg||"操作失败")
             }},
         error: function (e) {

         }
	 });
});
//人员移除单位
$(".delQuestionBtn").click(function(){
	var rows = $('#checkTable').bootstrapTable('getSelections');
	if(rows.length == 0){
		layer.msg("请勾选要移除的问题。")
		return;
	}
	var questionIds = "";
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		questionIds += row.id + ",";
	}
	 $.ajax({
         async: true,
         data: {userId:$("#indexId").val(),unitIds:questionIds},
         beforeSend: function(request){
             request.setRequestHeader("Authorization","Bearer "+login.token);
         },
         url: baseUrl+"/maintenanceUser/delUserReUnit",
         dataType: "json",
         success: function (d) {
             if (d.success) {
                 layer.msg("操作成功！")
                 $('#checkTable').bootstrapTable("refresh");
                 $('#noCheckTable').bootstrapTable("refresh");
             } else {
                 layer.msg(d.msg||"操作失败")
             }},
         error: function (e) {

         }
	 });
});
$(".btnSearchQ1").click(function(){
	$('#noCheckTable').bootstrapTable("destroy");
	questionListSearch1();
});
$(".btnSearchQ2").click(function(){
	$('#checkTable').bootstrapTable("destroy");
	questionListSearch2();
});

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

function maintenanceUnitSelect(){
	$.ajax({
		url:baseUrl+"/maintenanceunit/getMaintenanceUnitSelectList",
		type:"get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		dataType:"json",
		success:function(data){
			if(data.success){
				var htm="<option selected='selected' value=''>--请选择--</option>";
				for(var i=0;i<data.obj.length;i++){
					htm += "<option value='"+data.obj[i].id+"'>"+data.obj[i].unitname+"</option>";
				}
				$("#newMaintenanceUser-unitid").html(htm);
				$("#newMaintenanceUserView-unitid").html(htm);
			}
		}
	});
}