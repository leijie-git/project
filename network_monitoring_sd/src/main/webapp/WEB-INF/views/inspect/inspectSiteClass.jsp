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
<title>新增巡查设施管理</title>
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
                        <li class="is_active"><span class="title">巡查管理管理</span></li>
                        <li class="no_active"><span class="title">新增巡查设施管理</span></li>
                    </ul>
                    <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="identityNo" class="control-label">巡查设施名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="siteNames">
                                    <label for="identityNo" class="control-label">巡查设施描述<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="siteClassDesc">
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
                                     <option value="siteNames">巡查设施名称</option>
                                     <option value="siteClassDesc">巡查设施描述</option>
                                </select>
                                <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="searchContent"
                                    id="searchContent"></span>
                            </div>
                            <button type="button" class="btn btn-primary ordinarySearch">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                            <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                            <c:if test="${userSession.unitId != null and userSession.unitId != ''}">
                            <button type="button" class="btn btn-new btnNew"
                                data-toggle="modal" data-target='#newAdd'>
                                <i class="fa fa-plus"></i>&nbsp;新增
                            </button>
                            </c:if>
                            <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                            <!--                             </button> -->
                        </div>
                    </div>
                    <div class="SiteClassTable">
                        <table class="table table-bordered" id="SiteClassTable">

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
                    <h4 class="modal-title">新增巡查点分类</h4>
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
                                        <td class="text-right"><font style="color: red">*</font>巡查设施名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="SiteClassName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">巡查设施描述<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="SiteClassDesc">
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
    
      <!-- 新增模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAddView">
        <div class="modal-dialog" role="document" id="secondModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增巡查点分类</h4>
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
                                        <td class="text-right"><font style="color: red">*</font>巡查设施名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="SiteClassNameView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">巡查设施描述<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="SiteClassDescView">
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
<script src="/js/page_js/inspect/inspectSiteClass.js"></script>
</html>