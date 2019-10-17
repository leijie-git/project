<%--
  Created by IntelliJ IDEA.
  User: Kip Sheng
  Date: 2017/9/21
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="zh-CN">
<head>
    <title>城市管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <%@include file="../inc.jsp" %>
    <link rel="stylesheet" href="/css/page_css/resource_management.css">
<!--     <script type="text/javascript" src="/js/common/zTree/jquery.ztree.core.js"></script> -->
<!--     <script type="text/javascript" src="/js/common/zTree/jquery.ztree.excheck.js"></script> -->
</head>
<body>
<div class="container jy_wrap">
    <div class="row">
        <div class="col-md-12">
            <div class="jy_wrapper">
                <ul class="jy_title">
                    <li class="no_active">
                        <span class="title">城市管理</span>
                    </li>
                </ul>
                <div class="jy_mainTile panel-heading" clickNo="0">
                    <div class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputName2" class="control-label">城市名称<strong> :</strong></label>
                            <input type="text" class="form-control" name="cityName" id="cityName">
                        </div>
                        <button type="button" class="btn btn-primary btnSearch"><i class="fa fa-search"></i>&nbsp;查询
                        </button>
                        <button type="button" class="btn btn-warning btnReset"><i class="fa fa-undo"></i>&nbsp;重置
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
       <div class="row">
       <div class="col-md-10">
           <div class="dataTable">
               <div class="jy_new" style="top: 6px;">
                   <div class="form-inline">
                       <div class="form-group">
                       <select name="menuType" id="menuType" class="form-control">
                                    <option value="cityName">城市名称</option>
                                </select> 
                           <span id="commonInput">
                           <input type="text" class="form-control showInput" name="searchContent"
                                                         id="searchContent"></span>
                       </div>
                       <button type="button" class="btn btn-primary ordinarySearch"><i class="fa fa-search"></i>&nbsp;查询
                       </button>
                         <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                         <button type="button" class="btn btn-new btnNew" data-toggle="modal"
                                 data-target="#newResouce"><i
                                 class="fa fa-plus"></i>&nbsp;新增
                         </button>
                   </div>
               </div>
               <div class="mainList scrollStyle">
                   <table class="table table-bordered" id="resourceTable" data-click-to-select="true">
                   </table>
               </div>
           </div>
       </div>
   </div>
</div>
<!-- 新增模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="newResouce">
    <div class="modal-dialog" role="document" id="secondModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增地区</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <table class="table tableNo">

                                <tr>
                                    <td class="text-right">
                                        <font style="color:red">*</font>地区名称<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input type="text" class="form-control inputWidth" name="name" id="name">
                                    </td>
                                </tr>
                               
                                <tr>
                                    <td class="text-right">
                                        地区编号<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input type="text" class="form-control inputWidth" name="code" id="code">
                                    </td>
                                </tr>
                                 <tr>
                                    <td class="text-right">
                                        地区类型<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                         <div class="needCheck inputWidth">
                                            <select name="type" id="type" class="form-control">
                                                <option value="1">省</option>
                                                <option value="2">市</option>
                                                <option value="3">区</option>
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">
                                        上级地区<strong> :</strong>
                                    </td>
                                    <td class="text-left preMenuWrap">
                                        <div id="preIdWrapSource" class="inputWidth">
                                            <span></span>
                                            <ul id="preIdSource" class="ztree" style="margin-top:0; width:100%;"></ul>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnAdd"><i class="fa fa-floppy-o"></i>&nbsp;确定</button>
                <button type="button" class="btn btn-close btnClose" data-dismiss="modal"><i class="fa fa-times"></i>&nbsp;关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<input type="hidden" name="indexId" value="" id="indexId">

<input type="hidden" id="pid" name="pid">
<%--上级菜单--%>
<input type="hidden" name="preIds" id="preIds" value="">
<!-- 区分新增和编辑 -->
<input type="hidden" name="edit" value="0" id="edit">

</body>
<script src="/js/page_js/dataCenter/proviceManage.js"></script>
</html>
