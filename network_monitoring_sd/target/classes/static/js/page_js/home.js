/**
 * Created by Kip Sheng 2017-9-22 14:36:38
 */
var login = JSON.parse(localStorage.getItem("Login"));
$(function () {
    $.ajaxSetup({
        beforeSend: function(request){
            request.setRequestHeader("Authorization", "Bearer "+login.token);
        },
        statusCode: {
//            499: function (data) {
//                window.location.href = baseUrl + "/static/error/otherLogin.jsp";
//            },
            4999:function (data) {
            	layer.msg("账号超时，请重新登录！");
            	setTimeout('window.location.href = baseUrl + "/console/index"',1500);
            },
            5000:function (data) {
                layer.msg("token已过期，请重新登录！");
                setTimeout(function () {
                    logout();
                },1500);
            }
        },
        complete: function (data) {
            if(data.getResponseHeader('tokenString') != null){
                login.token = data.getResponseHeader('tokenString');
                localStorage.setItem("Login",JSON.stringify(login));
                login = JSON.parse(localStorage.getItem("Login"));
            }
        }
    });
    getStatic();
   //  修改密码弹出框
    $(".edit_password").click(function () {
        $("#modal_edit_password").modal();
        $("#modal_edit_password .modal-title").html('修改密码');
    });
   // 保存密码
    $(".save_password").click(function () {
        updatePwd();
    });
    function updatePwd() {
        var passwordNew = $('input[name=password_new]').val();
        var passwordRep = $('input[name=rep_password]').val();
        if (passwordRep != passwordNew) {
            layer.msg("新密码与确认密码不一致,请重新输入!");
            return;
        }
        if (passwordRep.length < 6) {
            layer.msg("密码至少6位,请修改!");
            return;
        }
        var data = {passwordNew: passwordNew};
        var load = layer.load();
        $.ajax({
            type: "post",
            async: true,
            data: data,
            url: baseUrl + '/console/updatePassword',
            dataType: "json",
            success: function (result) {
                layer.close(load);
                if (!result.success) {
                    layer.msg("修改失败！");
                } else {
                    layer.msg("修改成功！");
                    $('#modal_edit_password').modal('hide')
                }
            },
            error: function (e) {

            }
        });
    }
    $('#modal_edit_password').on('hide.bs.modal', function () {
        $('input[name=password_old]').val("");
        $('input[name=password_new]').val("");
        $('input[name=rep_password]').val("");
    });
    $("#jy_esc").click(function () {
        layer.open({
            content: "是否确认退出？",
            btn: ["确定", "取消"],
            yes: function () {
                logout();
            },
            no: function () {

            }
        })
    });
    function logout() {
        var load = layer.load();
        $.ajax({
            type: "get",
            async: true,
            data: null,
            url: baseUrl + '/console/logout',
            dataType: "json",
            cache:false,
            success: function (result) {
                layer.close(load);
                if (!result.success) {
                    layer.msg(result.msg);
                    return;
                } else {
                    window.location.href = baseUrl + "/console/index";
                }
            },
            error: function (e) {

            }
        });
    }

//切换角色

    $("#changeRole").click(function () {
        $("#tabStatistics").empty();
        $("#changeName").modal('show');
        $("#changeName .modal-title").html('切换角色');
        changeRole();

    });
    function changeRole() {
        $('#tabStatistics').bootstrapTable('destroy');
        $('#tabStatistics').bootstrapTable({
            url: baseUrl + '/console/findRole',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            contentType: 'application/x-www-form-urlencoded',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParamsType: "undefined",   //排序方式
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    pageNumber: this.pageNumber,
                    pageSize: this.pageSize
                };
                return param;
            },
            dataField: 'rows',
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
            singleSelect: true,
            // height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
            //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [
                {
                    field: 'selectItem',
                    checkbox: true
                },
                {
                    field: 'userNo',
                    title: '工号'
                },
                {
                    field: 'userName',
                    title: '姓名'
                },
                {
                    field: 'schoolName',
                    title: '学校'
                },
                {
                    field: 'className',
                    title: '班级'
                },
                {
                    field: 'roleName',
                    title: '角色名称',
                    formatter: function (value, row, index) {
                        if (value) {
                            return value
                        } else {
                            return '无'
                        }
                    }
                }
            ]
        });
    }
    $(".btnChangeRoleHome").click(function () {
        var list = $('#tabStatistics').bootstrapTable('getSelections');
        if (list.length <= 0) {
            layer.msg("请至少选中一个!");
            return;
        } else {
            var roleId = list[0].roleId;
            var schoolId = list[0].schoolId;
            var userRoleId = list[0].userRoleId;
            var url = baseUrl + "/console/changeRole";
            layer.open({
                content: "是否确定切换角色？",
                btn: ["确定", "取消"],
                yes: function () {
                    $.get(url, {"roleId": roleId, "schoolId": schoolId,"userRoleId":userRoleId}, function (result) {
                        result = JSON.parse(result);
                        if (result.success) {
                            layer.msg("切换成功!");
                            $("#changeName").modal("hide");
                            parent.location.reload();
                        } else {
                            layer.msg(result.desc)
                        }
                    })
                },
                no: function (index, layero) {
                }
            })
        }
    })
    setHtmlStyle();
    doInitResource();
});


function getStatic(){
    $.ajax({
        type: "get",
        url: baseUrl + "/getStatic",
        success: function (data) {
            if (data.success) {
                var obj = data.obj
                document.title = obj.title;
            }
        }
    });
}
$(window).resize(function () {
    setHtmlStyle();
});
function setHtmlStyle() {
//   内容部分
//     var Height = $(window).height();
//     console.log(Height)
    // $('body').css("height", Height);
    // $("#container").css("height", Height - 51);
    // $("#container").css("overflow-y", "auto");
    // $("#side-menu").css("height", Height - 51);
    // $("#contentsBody").css("height", Height - 51);
    // $("#page-wrappers").addClass("firstMenu");
    // $("#contentsBody").height(Height - 51)
}

var flag = true;
$("#tabMenu").click(function () {
    $("#page-wrappers").toggleClass("bothMenu");
    var hasChildLi = $("#rightMenu>li").size() > 0 ? true : false;
    if (hasChildLi) {
        $("#tabMenu").hide();
        $("#rightMenu").fadeOut();
        $("#page-wrappers").removeClass("secondMenu").addClass("firstMenu");
    } else {
        $("#tabMenu").show();
        $("#rightMenu").fadeIn();
        $("#page-wrappers").removeClass("firstMenu").addClass("secondMenu");
    }
})
function doInitResource() {
    $('#side-menu').empty();
    $("#container").empty();
    $.ajax({
        type: "get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", "Bearer "+login.token);
        },
        url: baseUrl + "/resource/listResource?type=0",
        success: function (result) {
            if (result.success == true) {
                original(result.obj);
            }
        },
        error: function (result) {
            layer.msg(result.msg||"查询异常！");
        }
    });

    // $.get(baseUrl + "/resource/listResource?type=0", function (result) {
    //     if (result.success == true) {
    //         original(result.obj);
    //     } else {
    //     	layer.msg(result.msg||"查询异常！");
    //     }
    // })
}
function original(nodes) {
    var navHtml = '';
    if(!nodes){
    	return;
    }
    for(var i=0;i<nodes.length;i++){
    	var pNode = nodes[i];
    	if (!pNode.pid || pNode.pid == 0 || pNode.pid == '0') {
    		navHtml += '<li idx="li' + pNode.seq + '" ';
    		navHtml += ' >';
    		navHtml += '   <a><img src="" class="icons" style="width: 22px;margin-top: -2px;"> <span class="nav-label praName" idx="">' + pNode.name + '</span>' +
    		' <span class="fa fa-angle-right home" id="home"></span></a>';
    		navHtml += '   <ul class="nav nav-second-level">';
    	}
    	for(var j=0;j<nodes.length;j++){
    		var child = nodes[j];
    		if (child.pid == pNode.id)
    			navHtml += '<li><span class="icon"></span><span class="circle"></span>' +
    			'<a href="javascript:;" idx="' + child.url + '">' + child.name + '</a></li>';
    	}
    	navHtml += '   </ul>';
    	navHtml += '</li>';
    }
    $('#side-menu').append(navHtml);
    $("#side-menu>li:nth-child(2)>ul>li:gt(0)").each(function () {
        $(this).attr("hasSecMenu", 1);
    })
    
    //去掉图标
    $("#home").removeClass("fa-angle-right");
    $('#side-menu').metisMenu();
    $("#side-menu>li:nth-child(1)").click(function () {
        goIndex();
        $("#rightMenu").hide().empty();
        $("#tabMenu").hide();
        $("#page-wrappers").removeClass("bothMenu secondMenu").addClass("firstMenu");
    });
    
    
    

	var url1='/image/navicon1.png';
	var url2='/image/navicon2.png';
	var url3='/image/navicon3.png';
	var url4='/image/navicon4.png';
	var url5='/image/navicon5.png';
	var url6='/image/navicon6.png';
	var url7='/image/navicon7.png';
	var url8='/image/navicon8.png';
    var url9='/image/navicon8.png';
	
	var url11='/image/navicon1f.png';
	var url22='/image/navicon2f.png';
	var url33='/image/navicon3f.png';
	var url44='/image/navicon4f.png';
	var url55='/image/navicon5f.png';
	var url66='/image/navicon6f.png';
	var url77='/image/navicon7f.png';
	var url88='/image/navicon8f.png';
    var url99='/image/navicon8f.png';
//    添加图标
    $("#side-menu>li").each(function () {
    	
    	var praName = $(this).find(".praName").html();
    	if (praName == "首页管理") {
            $(this).find(".icons").attr("src",url1);
        } else if (praName == "用户管理") {
            $(this).find(".icons").attr("src",url2);
        } else if (praName == "系统管理") {
            $(this).find(".icons").attr("src",url3);
        } else if (praName == "单位管理") {
            $(this).find(".icons").attr("src",url4);
        } else if (praName == "设备管理") {
            $(this).find(".icons").attr("src",url5);
        } else if (praName == "消防力量" || praName == "消防站管理") {
            $(this).find(".icons").attr("src",url6);
        } else if (praName == "一次配置") {
            $(this).find(".icons").attr("src",url7);
        } else if (praName == "巡查管理") {
            $(this).find(".icons").attr("src",url8);
        } else if (praName == "维保管理") {
            $(this).find(".icons").attr("src",url9);
        }
        //else if (praName == "审批管理") {
           // $(this).find(".icons").addClass("fa-tasks");
        //} else if (praName == "考勤管理") {
        //    $(this).find(".icons").addClass("fa-tasks");
        //} else if (praName == "系统管理") {
        //    $(this).find(".icons").addClass("fa-cogs");
        //} else {
         //   $(this).find(".icons").addClass("fa-sitemap");
        //}
    });

  //点击选中事件
    $("#side-menu li ul li").each(function () {
        $(this).click(function () {
            $(this).addClass("curFocus").siblings().removeClass("curFocus");
        })
    });
    
    //
    $("#side-menu>li>a").click(function () {
    	//var newimg = $(this).find("img").attr("src").split(".")[0];
    	$(this).parent().addClass('activeimg');
    	$(this).parent().siblings().removeClass('activeimg');
    	//$(this).find("img").attr("src",newimg + "f.png");
    	
    	var praName = $(this).find(".praName").html();
    	if (praName == "首页管理") {
            $(this).find(".icons").attr("src",url11);
        } else if (praName == "用户管理") {
            $(this).find(".icons").attr("src",url22);
        } else if (praName == "系统管理") {
            $(this).find(".icons").attr("src",url33);
        } else if (praName == "单位管理") {
            $(this).find(".icons").attr("src",url44);
        } else if (praName == "设备管理") {
            $(this).find(".icons").attr("src",url55);
        } else if (praName == "消防力量" || praName == "消防站管理") {
            $(this).find(".icons").attr("src",url66);
        } else if (praName == "一次配置") {
            $(this).find(".icons").attr("src",url77);
        } else if (praName == "巡查管理") {
            $(this).find(".icons").attr("src",url88);
        } else if (praName == "维保管理") {
            $(this).find(".icons").attr("src",url99);
        }

        for(var i=0;i<$(this).parent().siblings().find('.praName').length;i++){
    		var _nopra = $(this).parent().siblings().eq(i).find('.praName').html();
    		
    		if (_nopra == "首页管理") {
        		$(this).parent().siblings().eq(i).find(".icons").attr("src",url1);
            } else if (_nopra == "用户管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url2);
            } else if (_nopra == "系统管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url3);
            } else if (_nopra == "单位管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url4);
            } else if (_nopra == "设备管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url5);
            } else if (_nopra == "消防力量" || _nopra == "消防站管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url6);
            } else if (_nopra == "一次配置") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url7);
            } else if (_nopra == "巡查管理") {
            	$(this).parent().siblings().eq(i).find(".icons").attr("src",url8);
            } else if (_nopra == "维保管理") {
                $(this).parent().siblings().eq(i).find(".icons").attr("src",url8);
            }
        }
    	
    	
    });
    
    //图标移入
    $("#side-menu>li>a").mouseenter(function(){
    
    	if(!$(this).parent().hasClass('activeimg')){
    		var praName = $(this).find(".praName").html();
        	if (praName == "首页管理") {
                $(this).find(".icons").attr("src",url11);
            } else if (praName == "用户管理") {
                $(this).find(".icons").attr("src",url22);
            } else if (praName == "系统管理") {
                $(this).find(".icons").attr("src",url33);
            } else if (praName == "单位管理") {
                $(this).find(".icons").attr("src",url44);
            } else if (praName == "设备管理") {
                $(this).find(".icons").attr("src",url55);
            } else if (praName == "消防力量" || praName == "消防站管理") {
                $(this).find(".icons").attr("src",url66);
            } else if (praName == "一次配置") {
                $(this).find(".icons").attr("src",url77);
            } else if (praName == "巡查管理") {
                $(this).find(".icons").attr("src",url88);
            }else if (praName == "维保管理") {
                $(this).find(".icons").attr("src",url99);
            }
        }
    });
    
    //图标移出
    $("#side-menu>li>a").mouseleave(function(){
    	if(!$(this).parent().hasClass('activeimg')){
    		var praName = $(this).find(".praName").html();
	    	if (praName == "首页管理") {
	            $(this).find(".icons").attr("src",url1);
	        } else if (praName == "用户管理") {
	            $(this).find(".icons").attr("src",url2);
	        } else if (praName == "系统管理") {
	            $(this).find(".icons").attr("src",url3);
	        } else if (praName == "单位管理") {
	            $(this).find(".icons").attr("src",url4);
	        } else if (praName == "设备管理") {
	            $(this).find(".icons").attr("src",url5);
	        } else if (praName == "消防力量" || praName == "消防站管理") {
	            $(this).find(".icons").attr("src",url6);
	        } else if (praName == "一次配置") {
	            $(this).find(".icons").attr("src",url7);
	        } else if (praName == "巡查管理") {
	            $(this).find(".icons").attr("src",url8);
	        } 
    	}    
    });  
}

$("#side-menu").click(function (e) {
    var url = $(e.target).attr("idx");
    if (url == undefined || url == "") {
        return;
    }
    goPage(url);
});
function goPage(url) {
    var destUrl = baseUrl + url + "?t=" + Math.random();
    $("#container").empty();
    var layerLoad = layer.load();//开启加载中
    $("#container").load(destUrl, function () {
        layer.close(layerLoad);//关闭加载中
    });
}
goIndex();
function goIndex() {
    var url = baseUrl + "/systemManage/indexPage";
    $("#container").empty();
    $("#container").load(url);
}
//图片放大
$(document).click(function(e){
    var loadImg=$(e.target).attr("class");
    if(loadImg=="loadImg"){
        $(".wrapBox").fadeIn();
        $(".imgContent").fadeIn();
        var src=$(e.target).attr("src");
        $(".imgContent img").attr("src",src);
    }
})
$(".wrapBox").click(function(event){
    $(".wrapBox").fadeOut();
    $(".imgContent").fadeOut();
    event.stopPropagation();
})
$(".imgContent").click(function(event){
    $(".wrapBox").fadeOut();
    $(".imgContent").fadeOut();
    event.stopPropagation();
})


/**Add global function*/
Date.prototype.format = function (f) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(f))f = f.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(f))f = f.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return f
};
function long2Date(long) {
    var d = new Date();
    d.setTime(long);
    return d.format('yyyy-MM-dd');
}

function long2TimeStamp(long) {
    var d = new Date();
    d.setTime(long);
    return d.format('yyyy-MM-dd hh:mm:ss');
}

$("img").load(function () {
    var showImgWidth = $(".imgContent").width();
    var showImgheight = $(".imgContent").height();
    if(showImgheight > 600){
        showImgheight == 600
    }
    $('.imgContent').css({
        marginLeft:-showImgWidth /2 +"px",
        marginTop:-showImgheight /2 +"px",
    })

})
