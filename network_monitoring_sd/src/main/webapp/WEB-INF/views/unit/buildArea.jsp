<%--
  Created by IntelliJ IDEA.
  User: Kip
  Date: 2017/9/21
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<title>建筑物区域管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/role_management.css">
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.excheck.js"></script>
</head>
<body>
    <div class="container jy_wrap">
        <div class="row">
            <div class="col-md-12">
                <div class="jy_wrapper">
                    <ul class="jy_title">
                        <li class="is_active"><span class="title">单位管理</span></li>
                        <li class="no_active"><span class="title">建筑物区域管理</span></li>
                    </ul>
                    <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="identityNo" class="control-label">区域名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="areaName">
                                    <label for="identityNo" class="control-label">单位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="unitName">
                                    <label for="identityNo" class="control-label">区域地址<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="areaAddress">
                            </div>
                            <button type="button" class="btn btn-primary btnSearch">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                            <button type="button" class="btn btn-warning btnReset">
                                <i class="fa fa-undo"></i>&nbsp;重置
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="dataTable">
                    <div class="jy_new" style="top: 6px;">
                        <div class="form-inline">
                            <div class="form-group">
                            <select name="menuType" id="menuType" class="form-control">
                                    <option value="areaName">区域名称</option>
                                     <option value="areaAddress">区域地址</option>
                                     <option value="unitName">单位名称</option>
                                </select>
                                    <span id="commonInput"><input type="text"
                                    class="form-control inputNormal" name="searchContent"
                                    id="searchContent"></span>
                            </div>
                            <button type="button" class="btn btn-primary ordinarySearch">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                            <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                            <button type="button" class="btn btn-new btnNew"
                                data-toggle="modal" data-target='#newAdd'>
                                <i class="fa fa-plus"></i>&nbsp;新增
                            </button>
                            <button type="button" class="btn btn-new export" 
                                data-toggle="modal">
                                <i class="fa fa-plus"></i>&nbsp;导出
                            </button>
                             <button type="button" class="btn btn-del removeMove"
                                data-toggle="modal">
                                <i class="fa fa-trash"></i>&nbsp;批量删除
                            </button>
                            <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                            <!--                             </button> -->
                        </div>
                    </div>
                    <div class="buildAreaTable">
                        <table class="table table-bordered" id="buildAreaTable">

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 新增模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
        <div class="modal-dialog" role="document" id="secondModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增建筑物区域信息</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-6">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="30%">
                                        <col width="70%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>区域名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BuildAreaName">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="UnitID">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属建筑<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildId">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">区域地址<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BuildAreaSite">
                                        </td>
                                       
                                    </tr>
<!--                                     <tr> -->
<!--                                         <td class="text-right">缩略图宽度<strong> :</strong> -->
<!--                                         </td> -->
<!--                                         <td class="text-left"><input type="text" -->
<!--                                             class="form-control inputWidth" id="BgWidth" -->
<!--                                             onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"> -->
<!--                                         </td> -->
<!--                                     </tr> -->
<!--                                     <tr> -->
<!--                                         <td class="text-right">缩略图高度<strong> :</strong> -->
<!--                                         </td> -->
<!--                                         <td class="text-left"><input type="text" -->
<!--                                             class="form-control inputWidth" id="BgHeight" -->
<!--                                             onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"> -->
<!--                                         </td> -->
<!--                                     </tr> -->
                                    <tr>
                                        <td class="text-right">区域报警主机CRT图纸<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input id="photoCover-photo" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
                                                <input id="picurls-photo" name="imageDataList" type="file"
                                                       class="file typeFileMore"
                                                       value="" onchange="doChangeProjectphoto()"/>
                                                <ul id="loadImg-photo" class="loadImg">
                                                    <li class="modalImg"><span>请选择上传图片</span></li>
                                                </ul>
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">区域平面图<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input id="photoCover-photos" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
                                                <input id="picurls-photos" name="imageDataList" type="file"
                                                       class="file typeFileMore"
                                                       value="" onchange="doChangeProjectphotos()"/>
                                                <ul id="loadImg-photos" class="loadImg">
                                                    <li class="modalImg"><span>请选择上传图片</span></li>
                                                </ul>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary btnSure">
                        <i class="fa fa-floppy-o"></i>&nbsp;保存
                    </button>
                    <button type="button" class="btn btn-close btnClose"
                        data-dismiss="modal">
                        <i class="fa fa-times"></i>&nbsp;关闭
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    
    <!-- 查看模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAddView">
        <div class="modal-dialog" role="document" id="secondModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看建筑物区域信息</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-6">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="30%">
                                        <col width="70%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right">区域id<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                                                     class="form-control inputWidth" id="IdView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">区域名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="BuildAreaNameView">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="UnitIDView" >
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">所属建筑<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildIdView"  disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">区域地址<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BuildAreaSiteView" readonly="readonly">
                                        </td>
                                       
                                    </tr>
                                    <tr>
                                        <td class="text-right">缩略图宽度<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BgWidthView" readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">缩略图高度<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BgHeightView" readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">区域报警主机CRT图纸<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input id="photoCover-photoView" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
<!--                                                 <input id="picurls-photoView" name="imageDataList" type="file" -->
<!--                                                        class="file typeFileMore" -->
<!--                                                        value="" onchange="doChangeProjectphoto()"/> -->
                                                <ul id="loadImg-photoView" class="loadImg">
<!--                                                     <li class="modalImg"><span>请选择上传图片</span></li> -->
                                                </ul>
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">区域平面图<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input id="photoCover-photosView" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
<!--                                                 <input id="picurls-photosView" name="imageDataList" type="file" -->
<!--                                                        class="file typeFileMore" -->
<!--                                                        value="" onchange="doChangeProjectphotos()"/> -->
                                                <ul id="loadImg-photosView" class="loadImg">
<!--                                                     <li class="modalImg"><span>请选择上传图片</span></li> -->
                                                </ul>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-close btnClose"
                        data-dismiss="modal">
                        <i class="fa fa-times"></i>&nbsp;关闭
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    
    <!-- 区分新增和编辑 -->
    <input type="hidden" id="edit" value="0">
    
    <input type="hidden" name="indexId" id="indexId" value="">

    <%--权限--%>
    <input type="hidden" name="preId" id="preId" value="">


</body>
<script src="/js/page_js/unit/buildArea.js"></script>
</html>