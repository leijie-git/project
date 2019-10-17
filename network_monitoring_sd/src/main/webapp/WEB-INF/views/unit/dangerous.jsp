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
<title>危险品管理</title>
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
                        <li class="no_active"><span class="title">危险品管理</span></li>
                    </ul>
                     <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                            <label for="identityNo" class="control-label">单位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="unitName">
                                <label for="identityNo" class="control-label">危险品名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="dangerousName">
                                     <label for="identityNo" class="control-label">存储位置<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="site">
                                     <label for="identityNo" class="control-label">技术负责人<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="jsfzr">
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
                                     <option value="unitName">单位名称</option>
                                     <option value="dangerousName">危险品名称</option>
                                     <option value="site">存储位置</option>
                                     <option value="jsfzr">技术负责人</option>
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
                            <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                            <!--                             </button> -->
                        </div>
                    </div>
                    <div class="dangerousTable">
                        <table class="table table-bordered" id="dangerousTable">

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 新增模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
        <div class="modal-dialog" role="document" id="firstModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增危险品</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                    <tr>
                                        <td class="text-right password">所属单位<font
												style="color: red">*</font><strong> :</strong>
											</td>
										<td class="text-left password"><select
												name="UnitID" id="UnitID"
												class="form-control inputNormal">
													<!-- <option value=""></option> -->
											</select></td>
                                        <td class="text-right">危险品名称<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品类型<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousType">
                                        </td>
                                    	<td class="text-right">存储位置<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="Site">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousCount"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">数量单位<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="CountUnit">
                                        </td>
                                    </tr>
                                    <tr>
                                         <td class="text-right">危险品状态 <strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="DangerousState">
                                                <option value="0">--请选择--</option>
                                                <option value="1">气体</option>
                                                <option value="2">液体</option>
                                                <option value="3">固体</option>
                                               
                                                
                                            </select>
                                        </td>
                                         <td class="text-right">是否有消防标识<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                        <input id="IsXFBS1" name="IsXFBS" style="vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="1">
                                            是
                                        <input id="IsXFBS0" name="IsXFBS" style="vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="0">
                                        否
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">技术负责人<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="JSFZR">
                                        </td>
                                        <td class="text-right">技术负责人电话<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="JSFZRDH">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">安全管理人<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="AQGLR">
                                        </td>
                                         <td class="text-right">安全管理人电话<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="AQGLRDH" name="AQGLRDH">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品用途<strong> :</strong>
                                        
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousUsed" name="DangerousUsed">
                                        </td>
                                         <td class="text-right">危险品等级<strong> :</strong>
                                        </td>
                                        <td class="text-left" >
                                            <select class="form-control inputNormal"  id="DangerousLevel">
                                                <option value="0">--请选择--</option>
                                                <option value="1">一级</option>
                                                <option value="2">二级</option>
                                                <option value="3">三级</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">处置措施<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                           <input type="text" class="form-control inputNormal" name="DangerousCZCS" id="DangerousCZCS">
                                        </td>
                                         
                                    </tr>
                                   <tr>
                                        <td class="text-right">操作使用说明<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <input type="text" class="form-control inputNormal" name="OperateDesc" id="OperateDesc">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品图片<strong> :</strong>
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
        <div class="modal-dialog" role="document" id="firstViewModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看危险品</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                 <colgroup>
                                        <col width="20%">
                                        <col width="30%">
                                        <col width="20%">
                                        <col width="30%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right">所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select  class="form-control inputNormal" id="UnitIDView" disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                        <td class="text-right">危险品名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousNameView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品类型<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousTypeView" readonly="readonly">
                                        </td>
                                    	<td class="text-right">存储位置<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="SiteView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousCountView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" readonly="readonly">
                                        </td>
                                         <td class="text-right">数量单位<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="CountUnitView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                         <td class="text-right">危险品状态 <strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="DangerousStateView" disabled="disabled">
                                                <option value="0">--请选择--</option>
                                                <option value="1">气体</option>
                                                <option value="2">液体</option>
                                                <option value="3">固体</option>
                                               
                                                
                                            </select>
                                        </td>
                                         <td class="text-right">是否有消防标识<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                        <input id="IsXFBS1View" name="IsXFBS" style="vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="1" readonly="readonly">
                                            是
                                        <input id="IsXFBS0View" name="IsXFBS" style="vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="0" readonly="readonly">
                                        否
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">技术负责人<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="JSFZRView" readonly="readonly">
                                        </td>
                                        <td class="text-right">技术负责人电话<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="JSFZRDHView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">安全管理人<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="AQGLRView" readonly="readonly">
                                        </td>
                                         <td class="text-right">安全管理人电话<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="AQGLRDHView" name="AQGLRDHView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品用途<strong> :</strong>
                                        
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="DangerousUsedView" name="DangerousUsedView" readonly="readonly">
                                        </td>
                                         <td class="text-right">危险品等级<strong> :</strong>
                                        </td>
                                        <td class="text-left" >
                                            <select class="form-control inputNormal"  id="DangerousLevelView" disabled="disabled">
                                                <option value="0">--请选择--</option>
                                                <option value="1">一级</option>
                                                <option value="2">二级</option>
                                                <option value="3">三级</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">处置措施<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                           <input type="text" class="form-control inputNormal" name="DangerousCZCSView" id="DangerousCZCSView" readonly="readonly">
                                        </td>
                                         
                                    </tr>
                                   <tr>
                                        <td class="text-right">操作使用说明<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <input type="text" class="form-control inputNormal" name="OperateDesc" id="OperateDescView" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">危险品图片<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input id="photoCover-photoView" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
                                                <input id="picurls-photoView" name="imageDataList" type="file"
                                                       class="file typeFileMore"
                                                       value="" onchange="doChangeProjectphoto()"/>
                                                <ul id="loadImg-photoView" class="loadImg">
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
    
    
    <!-- 区分新增和编辑 -->
    <input type="hidden" id="edit" value="0">
    
    <input type="hidden" name="indexId" id="indexId" value="">

    <%--权限--%>
    <input type="hidden" name="preId" id="preId" value="">
    
    <input type="hidden" id="unitId" value="${userSession.unitId }">


</body>
<script src="/js/page_js/unit/dangerous.js"></script>
</html>