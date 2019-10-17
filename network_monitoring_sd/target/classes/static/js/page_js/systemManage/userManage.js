$(function () {
	//日期选择控件
	$("#newPeople-birthday").jeDate({
        ishmsVal: false,
        minDate: '1900-01-01 00:00:00',
        maxDate: '2099-12-31 23:59:59',
        format: "YYYY-MM-DD",
        zIndex: 3000
    })
    //主页面查询
    mainSearch();
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
        $(".password").show();
        $("#newEmploy .modal-title").html("新增员工");
        $("#newEmploy .btnSure").show();
        
        $("#newPeople-account").val("");
        $("#newPeople-password").val("");
        $("#newPeople-phone").val("");
        $("#newPeople-userName").val("");
        $("#newPeople-sex1").prop("checked", "checked");
        $("#newPeople-birthday").val("");
        $("#newPeople-ethnicGroup").val("");
        $("#newPeople-sign").val("");
        $("#newPeople-address").val("");
        $("#newPeople-remark").val("");
        
        $("#picurls-userHeader").attr("file_string","");
        $("#loadImg-userHeader").html('<li class="modalImg"><span>请选择上传图片</span></li>');
        
        $("#editId").val("0");
        $("#indexId").val("");
    });
    // 新增或编辑
    $(".btnSure").click(function () {
        var editId = $("#editId").val();
        var indexId = $("#indexId").val();
        
        var account = $("#newPeople-account").val();
        var phone = $("#newPeople-phone").val();
        var password = $("#newPeople-password").val();
        var userName = $("#newPeople-userName").val();
        var userHeader = $("#picurls-userHeader").attr("file_string");
        var sex = $("input[name='newPeople-sex']:checked").val();
        var birthday = $("#newPeople-birthday").val();
        var ethnicGroup = $("#newPeople-ethnicGroup").val();
        var sign = $("#newPeople-sign").val();
        var address = $("#newPeople-address").val();
        var remark = $("#newPeople-remark").val();
        
        if (account == "") {
        	layer.msg("账号不能为空！");
        	return;
        }
        if (editId == 0 && password == "") {
        	layer.msg("密码不能为空！");
        	return;
        }
        if (phone == ""){
        	layer.msg("电话不能为空！");
        	return;
        }

        if (editId == 0) {
            var url = baseUrl + "/user/addUser";
            var data = {
            	account: account,
            	phone: phone,
            	password: password,
            	userName: userName,
            	userHeader: userHeader,
            	sex:sex,
            	birthday:birthday,
            	ethnicGroup:ethnicGroup,
            	sign:sign,
            	address:address,
            	remark:remark
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
            var url = baseUrl + "/user/updateUser";
            var data = {
            		account: account,
	            	phone: phone,
	            	password: password,
	            	userName: userName,
	            	userHeader: userHeader,
	            	sex:sex,
	            	birthday:birthday,
	            	ethnicGroup:ethnicGroup,
	            	sign:sign,
	            	address:address,
	            	remark:remark,
                    id: indexId
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
                        layer.msg("保存成功！");
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
    $("#userName").val('');
}
// 编辑
function editEmploy(_this) {
    $("#newEmploy").modal("show");
    $("#newEmploy .modal-title").html("编辑人员");
    $("#newEmploy .btnSure").show();
    var id = _this.id;
    $("#indexId").val(id);
    $("#editId").val("1");
    $(".password").hide();
    $("#newPeople-account").val(_this.account);
    $("#newPeople-userName").val(_this.userName);
    $("#newPeople-passWord").val(_this.password);
    $("#newPeople-phone").val(_this.phone);
    $("#newPeople-birthday").val(_this.birthday);
    $("#newPeople-ethnicGroup").val(_this.ethnicGroup);
    $("#newPeople-sign").val(_this.sign);
    $("#newPeople-remark").val(_this.remark);
    $("#newPeople-address").val(_this.address);
    var sex = _this.sex;
	if (sex == "1") {
		$("#newPeople-sex0").removeAttr("checked");
		$("#newPeople-sex1").prop("checked", "checked");
	} else {
		$("#newPeople-sex1").removeAttr("checked");
		$("#newPeople-sex0").prop("checked", "checked");
	}
    
    var userHeader = _this.userHeader;
    if(userHeader){
    	$("#picurls-userHeader").attr("file_string",userHeader);
    	$("#loadImg-userHeader").html(
    			"<li class='modalImg'><span>请选择上传图片</span></li>"+	
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + userHeader + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#picurls-userHeader").attr("file_string","");
    	$("#loadImg-userHeader").html("<li class='modalImg'><span>请选择上传图片</span></li>");
    }
}
//查看
function viewEmploy(_this) {
    $("#newEmployView").modal("show");
    
    $("#newPeopleView-account").val(_this.account);
    $("#newPeopleView-userName").val(_this.userName);
    $("#newPeopleView-phone").val(_this.phone);
    $("#newPeopleView-passWord").val(_this.password);
    $("#newPeopleView-birthday").val(_this.birthday);
    $("#newPeopleView-ethnicGroup").val(_this.ethnicGroup);
    $("#newPeopleView-sign").val(_this.sign);
    $("#newPeopleView-remark").val(_this.remark);
    $("#newPeopleView-address").val(_this.address);
    
    var sex = _this.sex;
	if (sex == "1") {
		$("#newPeopleView-sex0").removeAttr("checked");
		$("#newPeopleView-sex1").prop("checked", "checked");
	} else {
		$("#newPeopleView-sex1").removeAttr("checked");
		$("#newPeopleView-sex0").prop("checked", "checked");
	}
    
    var userHeader = _this.userHeader;
    if(userHeader){
    	$("#loadImgView-userHeader").html(
    			" <li style='position: relative;margin: 0px;border: none;'> " +
    			"  <img src='" + userHeader + "' " +
    			" style = 'width:100%;height:100%;'/>" +
    			'<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
    			" </a>"+
    			" </li>"
    	);
    }else{
    	$("#loadImgView-userHeader").html("<li></li>");
    }
}
//批量删除
function deleteEmploy() {
    var select = $('#userTable').bootstrapTable('getSelections');
    var idArray = [];
    if (select.length > 0) {
        for (var i = 0; i < select.length; i++) {
            idArray.push(select[i].id);
        }
    }
    if (idArray.length == 0) {
        layer.msg("请至少选择一条记录!");
        return;
    }
    // var id = _this.id;
    layer.open({
        content: '<span style="color: red">请输入单词DELETE,点击确定后完成删除</span>' +
        '<input type="text" class="form-control" name="deleteMore" id="deleteMore">'
        , btn: ['确认', '取消']
        , yes: function () {
            var deleteMore = $("#deleteMore").val();
            if (deleteMore.toUpperCase() != 'DELETE') {
                layer.msg('请输入DELETE！');
                return;
            }
            $.ajax({
                url: baseUrl + "/user/deleteManyUser",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                	id: JSON.stringify(idArray)
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.msg("删除成功！");
                        $("#userTable").bootstrapTable('refresh');
                    } else {
                        layer.msg(data.desc);
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
//批量删除
function deleteMany() {
    var checked = $("#userTable").bootstrapTable('getSelections');
    if (checked.length <= 0) {
        layer.msg("请至少选择一条记录!");
        return;
    }
    var ids = '';
    $(checked).each(function (i, item) {
        var indexId = item.id;
        if (!ids) {
            ids = ids + indexId;
        } else {
            ids = ids + ',' + indexId;
        }
    });
    var url = baseUrl + '/user/deleteManyUser';
    layer.open({
        content: '确认删除?'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: url,
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {id: ids},
                dataType: "json",
                success: function (d) {
                    if (d.success) {
                        $("#userTable").bootstrapTable("refresh");
                        layer.msg("删除成功！");
                    } else {
                        layer.msg(d.desc);
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
function deleteOneEmploy(_this) {
    var select = _this.id;
    layer.open({
        content: '确认删除选中人员？'
        , btn: ['确认', '取消']
        , yes: function () {
            $.ajax({
                url: baseUrl + "/user/deleteUser",
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
                        layer.msg(data.desc);
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

//重置密码
function resetPass(_this) {
    var select = _this.id;
    layer.open({
        content : '确认重置选中用户密码？',
        btn : [ '确认', '取消' ],
        yes : function() {
            $.ajax({
                url : baseUrl + '/user/resetPas',
                type : "get",
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
        url: baseUrl + '/user/userList',         //请求后台的URL（*）
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
                userName: $("#userName").val()
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
                field: 'account',
                title: '账号'
            },
            {
            	field: 'userName',
            	title: '姓名'
            },
            {
            	field: 'phone',
            	title: '电话'
            },
            {
                field: 'createDate',
                title: '添加日期'
            },
            {
                field: 'options',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
                    if(row.account == "admin"){
                    	str += '<button class="cBtn-main view btn btn-primary btn-xs"><i class="fa fa-eye"></i>&nbsp;查看</button>'
                    }else{
                    	str += '<button class="cBtn-main edit btn btn-new btn-xs"><i class="fa fa-pencil"></i>&nbsp;编辑</button>'
                		str += '<button class="cBtn-main del btn btn-danger btn-xs"><i class="fa fa-trash"></i>&nbsp;删除</button>'
            			str += '<button class="cBtn-main view btn btn-primary btn-xs"><i class="fa fa-eye"></i>&nbsp;查看</button>'
        				str += '<button class="cBtn-main setRole btn btn-new btn-xs"><i class="fa fa-pencil"></i>&nbsp;设置角色</button>'
                        str += '<button class="cBtn-main resetPass btn btn-new btn-xs"><i class="fa fa-eye"></i>&nbsp;重置密码</button>'
                    }
                    return str;
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        editEmploy(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteOneEmploy(row);
                    },
                    'click .view': function (e, value, row, index) {
                        viewEmploy(row);
                    },
                    'click .setRole': function (e, value, row, index) {
                    	setRole(row);
                    },
                    'click .resetPass': function (e, value, row, index) {
                        resetPass(row);
                    }
                }
            }
        ]
    });
}

function relProject(_id){
	$("#indexId").val(_id);
	$('#checkTable').bootstrapTable("destroy");
    $('#noCheckTable').bootstrapTable("destroy");
	projectListSearch("checkTable","");
	projectListSearch("noCheckTable","2");
	$("#relationProject").modal("show");
}

//人员查询
function projectListSearch(tableId,isRelation) {
    $('#'+tableId).bootstrapTable({
        url: baseUrl + '/project/getRelationProject',         //请求后台的URL（*）
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
                userId:$("#indexId").val(),
                isRelation:isRelation
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
        columns: [
        	{
                title: '名称',
                field: 'projectname',
                sortable: true
//                width: '100px'
            },
            {
            	title: '类型',
            	field: 'projectlevel',
            	sortable: true,
            	formatter: function (value, row, index) {
        			if (value == 1) {
        				return '小区';
        			} else if (value == 2) {
        				return '楼幢';
        			} else if (value == 3) {
        				return '单元';
        			} else if (value == 4) {
        				return '楼层';
        			} else if (value == 5) {
        				return '房间';
        			} else{
        				return "-";
        			}
                }
//                width: '100px'
            },
            {
                field: 'options',
                title: '操作',
                formatter: function (value, row, index) {
                    	if (tableId == "checkTable") {
                    		return '<button class="cBtn-main cancel btn btn-primary btn-xs"><i class="fa fa-eye"></i>&nbsp;取消关联</button>'
                    	}else{
                    		return '<button class="cBtn-main rel btn btn-primary btn-xs"><i class="fa fa-eye"></i>&nbsp;关联</button>'
                    	}
                },
                events: {
                    'click .rel': function (e, value, row, index) {
                        relUser(row.id,1);
                    	},
	                'click .cancel': function (e, value, row, index) {
	                	relUser(row.id,0);
	                }
                }
            }
        ]
//        ,onLoadSuccess: function () {
//        	if (tableId == "checkTable") {
//                $('#checkTable').bootstrapTable('hideColumn', 'options');
//            }else{
//            	//showColumn
//            }
//        	
//        }
    });
}

function relUser(userId,isRel){
    $.ajax({
        async: true,
        data: {userId:userId,id:$("#indexId").val(),isRel:isRel},
        url: baseUrl+"/project/relationUser",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        dataType: "json",
        success: function (d) {
            if (d.success) {
                layer.msg("操作成功！")
                $('#checkTable').bootstrapTable("refresh");
                $('#noCheckTable').bootstrapTable("refresh");
            } else {
                layer.msg(d.msg||"操作失败")
            }
        },
        error: function (e) {

        }
    });
}

function setRole(_this){
	$("#setRoleModal").modal("show");
	$('#select1').empty();
	$('#select2').empty();
	var id = _this.id;
	$("#indexId").val(id);
	var url = baseUrl + '/user/getUserRole?type=0';
	$.ajax({
        async: true,
        data: {id:id},
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
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
$(".btn-save_role").click(function(){
	var userId = $("#indexId").val();
	var ids = "";
	for(var i = 0;i < $("#select2 option").length;i++){
        var id = $($("#select2 option").eq(i)).attr("id");
        ids += id +",";
    }
	
	var url = baseUrl + '/user/setUserRole?type=0';
	$.ajax({
		type:"post",
        async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {userIdOne:userId,manyRoleId:ids},
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
});


function doChangeProject() {
	uploadOne("picurls-userHeader", "loadImg-userHeader", "photoCover-userHeader", false);
};

