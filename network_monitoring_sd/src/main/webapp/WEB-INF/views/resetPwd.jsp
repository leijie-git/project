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
    <title>重置密码</title>
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

    <script src="/js/page_js/login.js"></script>
</head>

<body>
<div class="container-fluid">
    <!-- <p class="bigTitle">家校通后台管理系统</p> -->
    <div class="panel-body">
        <div class="wrapout"></div>
        <div class="wrappers">
            <p class="title">
                静安消防后台管理系统
            </p>
            <div class="wrapUser">
                <div class="form-group has-feedback">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    <input type="text" class="form-control" placeholder='请输入登录名' name='userName' id='phoneNo'>
                </div>
                <div class="login">
                    <button class="btn btn-lg btn-primary btn-block" id="resetpwd">重置密码</button>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="baseUrl" value="${ctx}">
</body>
<script src="/js/page_js/resetPwd.js"></script>
</html>
