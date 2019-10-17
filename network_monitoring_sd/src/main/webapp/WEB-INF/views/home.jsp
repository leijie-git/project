<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="headUrl" value="${userSession.headUrl}"/>
<!doctype html>
<html lang="zh-CN">
<head>
<!--     <link rel="shortcut icon" href="/image/home.png" type="image/x-icon"/> -->
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>消防安全管理平台</title>
    <link rel="stylesheet" href="/css/common/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/css/common/zTreeMenu.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/js/common/bootstrap-3.3.7-dist/bootstrap-table.css">
    <link rel="stylesheet" href="/js/common/layer/skin/layer.css">
    <link rel="stylesheet" href="/js/common/zz-menu/zz-menu.css">
    <link rel="stylesheet" href="/js/common/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/common/mobile/metisMenu.min.css">
    <link rel="stylesheet" href="/css/common/mobile/sb-admin-2.min.css">
    <link rel="stylesheet" href="/css/common/mobile/jquery.treegrid.css">

    <link rel="stylesheet" href="/css/common/jquery.searchableSelect.css">

    <script src="/js/common/jquery.min.js"></script>

    <link rel="stylesheet" href="/js/common/bootstrapselect/bootstrap-select.css">
    <link rel="stylesheet" href="/js/common/bootstrap-3.3.7-dist/css/bootstrap.min.css">

    <link rel="stylesheet" href="/css/page_css/common.css">
    <link rel="stylesheet" href="/css/page_css/home.css">
    <script src="/js/common/bootstrapselect/bootstrap-select.js"></script>
    <script src="/js/common/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="/js/common/upload/ajaxfileupload.js"></script>

    <script src="/js/common/bootstrap-3.3.7-dist/bootstrap-table.min.js"></script>
    <script src="/js/common/bootstrap-3.3.7-dist/bootstrap-table-zh-CN.min.js"></script>
    <script src="/js/common/layer/layer.js"></script>
    <script src="/js/common/ajaxJS.js"></script>
    <script src="/js/common/zz-menu/zz-menu.js"></script>
    <script src="/js/common/slide/metisMenu.min.js"></script>
    <script src="/js/common/slide/sb-admin-2.js"></script>
    <script src="/js/common/slide/tree.table.js"></script>
    <script src="/js/common/slide/bootstrap-paginator.js"></script>
    <script src="/js/common/mobile/jquery.treegrid.extension.js"></script>

    <script type="text/javascript" src="/js/common/zTree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="/js/common/zTree/jquery.ztree.excheck.js"></script>
	<!--     二维码 -->
<!--     <script src="/js/common/qrcode/jquery.qrcode.min.js"></script> -->


    <link rel="stylesheet" href="/html/Script/jquery-scrollbar/jquery.scrollbar.css">
    <script type="text/javascript" src="/html/Script/jquery-scrollbar/jquery.scrollbar.js"></script>


    <link rel="stylesheet" href="/js/common/jqueryComboSelect/css/combo.select.css">
    <script type="text/javascript" src="/js/common/jqueryComboSelect/js/jquery.combo.select.js"></script>

</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" id="topHeader" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="${ctx}/console/index" id="logoTop"><img src="/file/upload/logo.png" alt=""></a>
        </div>
        <!-- /.navbar-header -->
        <ul class="nav navbar-top-links navbar-right">
            <!-- /.dropdown -->
            <li class="userImg"><img src="<c:choose>
<c:when test="${userSession.headUrl == '' || userSession.headUrl == null}">/image/user.jpg</c:when>
<c:otherwise>${userSession.headUrl}</c:otherwise></c:choose>" alt="" class="img-circle">
            </li>
<%--             <li class="rolName"><span>${userSession.roleName}</span></li> --%>
            <li class="dropdown user" id="userRight">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    ${userSession.userName}
                    <!--<i class="fa fa-angle-down"></i>-->
                    <img src="/image/down_blue.png" style="width: 20px;height: 20px;">
                </a>
                <ul class="dropdown-menu dropdown-user">
<!--                     <li><a href="#" id="changeRole"><i class="fa fa-user fa-fw"></i> 切换角色</a> -->
<!--                     </li> -->
                    <li><a href="#" data-toggle="modal" data-target="#modal_edit_password">
                        <i class="fa fa-gear fa-fw"></i> 修改密码</a>
                    </li>
                    <li><a href="#" id="jy_esc"><i class="fa fa-sign-out fa-fw"></i>退出</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>

            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->
    </nav>
    <div id="contentsBody">
        <!-- 左边部分 -->
        <div class="navbar-default sidebar " role="navigation" id="sideMenu">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">

                </ul>
            </div>
        </div>
        <!-- 右边部分 -->
        <div id="page-wrappers" class="firstMenu">
            <div class="container-fluid scrollStyle" id="container" style="height: 100%;background-color: #eee;">

            </div>
        </div>
    </div>
</div>
<%--修改密码模态框--%>
<div class="modal fade" id="modal_edit_password" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="password_new" class="col-sm-4 control-label">新密码<font
                                style="color:red">*</font><strong>:</strong></label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control " name="password_new" id="password_new"
                                   placeholder="新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password_new" class="col-sm-4 control-label">确认密码<font
                                style="color:red">*</font><strong>:</strong></label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" name="rep_password" id="rep_password"
                                   placeholder="确认新密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary save_password"><i class="fa fa-floppy-o"></i>&nbsp;保存
                </button>
                <button type="button" class="btn btn-close" data-dismiss="modal"><i class="fa fa-times"></i>&nbsp;关闭
                </button>
            </div>
        </div>
    </div>
</div>
<%--切换角色模态框--%>
<div class="modal fade bs-example-modal-md systemCheck" tabindex="-1" role="dialog" id="changeName">
    <div class="modal-dialog" role="document" id="firstModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">切换角色</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="changeTab">
                                <table class="table table-bordered" id="tabStatistics">

                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btnChangeRoleHome"><i class="fa fa-floppy-o"></i>&nbsp;确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--点击图片放大--%>
<div class="wrapBox"></div>
<div class="imgContent">
    <img src="" alt="">
</div>


</body>
<script>
	//滚动条
	//jQuery('.scrollbar-macosx').scrollbar({});
	
    var baseUrl = "${ctx}";
    var userSession_userId = '${userSession.id}';
    if (!userSession_userId) {
        window.location.href = baseUrl+'/console/index';
    }
    window.UEDITOR_HOME_URL=baseUrl+"/ueditor/";
    
    
</script>
<script src="/js/page_js/home.js"></script>
</html>