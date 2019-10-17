<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>系统登录</title>
<!--     <link rel="shortcut icon" href="/image/home.png" type="image/x-icon"/> -->
    <base href="<%=basePath%>">
    <meta http-equiv="content-type" charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" href="/js/common/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/js/common/layer/skin/layer.css">
    <link rel="stylesheet" href="/js/common/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/page_css/login.css">


    <script src="/js/common/jquery.min.js"></script>
    <script src="/js/common/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="/js/common/layer/layer.js"></script>
    <script src="/js/common/ajaxJS.js"></script>

    <style type="text/css">
        #code {
            font-family: Arial;
            font-style: italic;
            font-weight: bold;
            border: 0;
            letter-spacing: 2px;
            color: blue;
        }

        input[type=number]::-webkit-outer-spin-button,
        input[type=number]::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
        .qrcode{
            position:absolute;
            bottom: 20px;
            right:20px;
        }
        .qrcode>div{
            width:150px;
            display:inline-block;
            margin-right:15px;
        }
        .qrcode p{
           color: #fff;
        }
        .qrcode>div img{
            width:120px!important;
            height:120px!important;
        }
    </style>
</head>

<body>
<div class="container-fluid">
    <div class="panel-body">
        <div class="wrapout"></div>
        <div class="wrappers">
            <p class="title">
               消防安全管理平台
            </p>
            <div class="wrapUser">
                <div class="form-group has-feedback">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    <input type="text" class="form-control" placeholder='请输入账号' name='userName' value="" id="userName">
                </div>
                <div class="form-group has-feedback">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    <input type="password" class="form-control" placeholder="请输入密码" name="passWord" value="">
                </div>
                <div style='display:none;padding-left: 60px;' id="codeShow">
                    <input type="text" id="input"/>
                    <input type="button" id="code" onclick="createCode(0)"/>
                </div>
                <div class="checkbox">
                    <%-- <a style="padding-right: 178px;" href="${ctx}/console/resetPwd"
                       target="" id="reset_pwd">忘记密码？</a>
                    <label> --%>
                        <input name="remember_pwd" type="checkbox" id="remember_pwd" value="">记住密码
                    </label>
                </div>
                <div class="login">
                    <button class="btn btn-lg btn-primary btn-block" onclick="login()" id="loginIn">登录</button>
                </div>
            </div>
        </div>
    </div>

</div>


<input type="hidden" id="baseUrl" value="${ctx}">
</body>
<script src="/js/page_js/login.js"></script>
<script src="/js/common/qrcode/jquery.qrcode.min.js"></script>
</html>
