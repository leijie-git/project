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
<title>巡查点管理</title>
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
                        <li class="is_active"><span class="title">巡查管理</span></li>
                        <li class="no_active"><span class="title">巡查点管理</span></li>
                    </ul>
                    <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="identityNo" class="control-label">位置名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="siteName-Search">
                                    <label for="identityNo" class="control-label">位置描述<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="siteDesc-Search">
                                    
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
                                     <option value="siteName-Search">位置名称</option>
                                     <option value="siteDesc-Search">位置描述</option>
                                </select>
                                <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="searchContent"
                                    id="searchContent" ></span>
                            </div>
                            <button type="button" class="btn btn-primary ordinarySearch">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                            <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                            <%-- <c:if test="${userSession.account != 'admin' }"> --%>
                            <%-- <c:if test="${userSession.unitId != null and userSession.unitId != ''}"> --%>
                            <button type="button" class="btn btn-new btnNew"
                                data-toggle="modal" data-target='#newAdd'>
                                <i class="fa fa-plus"></i>&nbsp;新增
                            </button>
                            <%--  </c:if> --%>
                           <%--  </c:if> --%>
                             <button type="button" class="btn btn-del removeMove"
                                data-toggle="modal">
                                <i class="fa fa-trash"></i>&nbsp;批量删除
                            </button>
                             <button type="button" class="btn btn-primary moreCode"
                                data-toggle="modal">
                                <i class="fa fa-eye"></i>&nbsp;批量生成二维码
                            </button>
                            <c:if test="${userSession.unitId != null and userSession.unitId != ''}">
                            <button type="button" class="btn btn-new leadingIn"
                                    data-toggle="modal">
                                <i class="fa fa-plus"></i>&nbsp;导入
                            </button>
                            </c:if>
                            <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                            <!--                             </button> -->
                        </div>
                    </div>
                    <div class="inspectSiteTable">
                        <table class="table table-bordered" id="inspectSiteTable">

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 新增模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增巡查点</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="15%">
                                        <col width="35%">
                                        <col width="15%">
                                        <col width="35%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="UnitID"></select>
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>所属建筑<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildID"></select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属区域<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildAreaID"></select>
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>位置名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                                                     class="form-control inputWidth" id="SiteName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>位置编号<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                                                     class="form-control inputWidth" id="SiteCode">
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>位置描述<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                                                     class="form-control inputWidth" id="SiteDesc">
                                        </td>
                                    </tr>
                                    <%--<tr>--%>
                                        <%--<td class="text-right"><font style="color: red">*</font>位置描述<strong> :</strong>--%>
                                        <%--</td>--%>
                                        <%--<td class="text-left"><input type="text"--%>
                                            <%--class="form-control inputWidth" id="SiteDesc">--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <tr>
                                       <!--  <td class="text-right"><font style="color: red">*</font>巡查周期<strong> :</strong>
                                        </td>
                                        
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="InspectCycle">
                                        </td> -->
                                        <td class="text-right"><font style="color: red">*</font>执行人<strong> :</strong>
                                        </td>
                                        <td class="text-left" style="position: relative;">
                                            <input type="text" style="display:none"
                                                   class="form-control inputWidth" id="executorID">
                                            <input type="text"
                                                   class="form-control inputWidth" id="executorName" readonly="readonly">
                                            <button type="button" class="btn btn-primary" style="position: absolute;top: 16%;right: 10%;" onclick="setRole()">
                                                <i class="fa fa-add"></i>&nbsp;选择
                                            </button>
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>周期类型<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="InspectCycleType">
                                                <option value="">--请选择--</option>
                                                <option value="0">日</option>
                                                <option value="1">周</option>
                                                <option value="2">月</option>
                                                <option value="3">年</option>

                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>执行开始时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="TaskStart">
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>执行结束时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="TaskEnd">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>巡查次数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                                                     class="form-control inputWidth" id="InspectCount">
                                        </td>
                                        <td class="text-right">NFC编号<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="NFCCode">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">QR码<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                                                     class="form-control inputWidth" id="QRCode">
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
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增巡查点</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="15%">
                                        <col width="35%">
                                        <col width="15%">
                                        <col width="35%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right">所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="UnitIDView" disabled="disabled"></select>
                                        </td>
                                        <td class="text-right">所属建筑<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildIDView" disabled="disabled"></select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">所属区域<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildAreaIDView" disabled="disabled"></select>
                                        </td>
                                        <td class="text-right">位置名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                                                     class="form-control inputWidth" id="SiteNameView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">位置编号<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="SiteCodeView">
                                        </td>
                                        <td class="text-right">位置描述<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                                                     class="form-control inputWidth" id="SiteDescView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">执行人<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" class="form-control inputWidth" id="executorNameView" readonly="readonly">
                                        </td>
                                        <td class="text-right">周期类型<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth"  disabled="disabled" id="InspectCycleTypeView">
                                                <option value="">--请选择--</option>
                                                <option value="0">日</option>
                                                <option value="1">周</option>
                                                <option value="2">月</option>
                                                <option value="3">年</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">开始时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="TaskStartView">
                                        </td>
                                        <td class="text-right">结束时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="TaskEndView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>巡查次数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                     onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                                                     class="form-control inputWidth" id="InspectCountView">
                                        </td>
                                        <td class="text-right">NFC编号<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" readonly="readonly" class="form-control inputWidth" id="NFCCodeView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">QR码<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" class="form-control inputWidth" readonly="readonly" id="QRCodeView">
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
    
     <!-- 新增模态框 -->
     <div class="modal fade" tabindex="-1" role="dialog" id="lookModel">
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header" style="width:980px;margin-bottom:30px">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">二维码</h4>
                     <button type="button" class="btn btn-primary"
                        data-dismiss="modal" onclick="printCode()">
                        <i class="fa fa-plus"></i>&nbsp;打印
                    </button>
                    <div style="display: flex;margin-right: 10px;justify-content: flex-start;align-items:center;">
                        <span>字体大小：</span>
                        <select class="form-control" style="width: 100px;" name="fontSizeChange" id="fontSizeChange" onchange="fontSize();">
                            <option value="12">12</option>
                            <option value="14" selected>14</option>
                            <option value="16">16</option>
                            <option value="18">18</option>
                            <option value="20">20</option>
                        </select>
                    </div>
                </div>
                <div id="pic" style="margin-left:17%;">
                </div>
                <div class="allCode" id="allCode" style="width:980px;height:900px;background:white;display: flex;flex-wrap: wrap;align-items: flex-start;align-content: flex-start;">
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <!-- 查看巡查项模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="PatrolItem">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看巡查项</h4>
                </div>
                <table class="table table-bordered" id="PatrolItemTable">

                </table>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <!-- 导入模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="leadingInModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">导入</h4>
                </div>
                <div class="modal-body" style="height: auto;">
                    <div class="row">
                        <div class="col-md-12">
                            <form id="projectForm" class="importArea">
                                <div>
                                    <input type="hidden" name="eqid" id="eqid"> <span
                                        class="col-md-2">第一步</span>
                                    <button type="button" class="btn btn-new downloadModel" onclick="downloadModel();"
                                            data-toggle="modal">
                                        <i class="fa fa-download"></i>&nbsp;下载导入模板
                                    </button>
                                </div>

                                <div>
                                    <p class="col-md-offset-2">请根据模板格式填写内容，然后导入</p>
                                </div>
                                <div>
                                    <span class="col-md-2">第二步</span>
                                    <div class="wrapExport">
                                        <input type="file" onchange="doChange(this)" name="uploadExcel" class="changeInput uploadExcel" />
                                        <button type="button"
                                                class="btn btn-new btn-sm btn-new chooseFile">
                                            <i class="fa fa-arrow-up"></i>&nbsp;上传
                                        </button>
                                    </div>
                                    <span class="fileName"></span>
                                </div>
                                <div>
                                    <!--                                    <p class="col-md-offset-2">上传填写好的。</p> -->
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-new btn-sm upTemp btnImport">开始导入</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 添加计划巡查人员模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="setPlanRoleModal">
        <div class="modal-dialog" role="document" id="secondModals">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">设置巡查人员</h4>
                </div>
                <div class="modal-body">
                    <div class="content" style="float:left; text-align:center; margin-right:10px;">
                        <h3 style="line-height: 16px;text-align: center;font-size: 14px">未选择</h3>
                        <select multiple="multiple" id="select3"
                                style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">


                        </select>
                        <span id="Planremove" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">选中右移>></span>
                        <span id="Planremove_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">全部右移>></span>
                    </div>

                    <div class="content" style="float:left; text-align:center; margin-right:10px;">
                        <h3 style="line-height: 16px;text-align: center;font-size: 14px">已选择</h3>
                        <select multiple="multiple" id="select4"
                                style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">
                        </select>
                        <span id="Planadd" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;选中左移</span>
                        <span id="Planadd_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;全部左移</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary btn-save_rolePlan">
                        <i class="fa fa-floppy-o"></i>&nbsp;保存
                    </button>

                    <button type="button" class="btn btn-close" data-dismiss="modal"
                            aria-label="Close">
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
<script src="/js/page_js/inspect/inspectSite.js"></script>
<script src="/js/common/qrcode/jquery.qrcode.min.js"></script>
</html>