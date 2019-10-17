<%--
  Created by IntelliJ IDEA.
  User: SD001
  Date: 2019/7/5
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
    <title>巡查点检查项配置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <%@include file="../inc.jsp"%>
    <style>
        .checkboxChange {
            display: inline-block;
            border: 1px solid #dcdfe6;
            width: 14px;
            height: 14px;
            background-color: #fff;
            z-index: 1;
            cursor: pointer;
            position: relative;
        }
        .checkboxChange:after{
            content: "";
            position: absolute;
            width: 10px;
            height: 6px;
            background: transparent;
            top: 2px;
            left: 1px;
            border: 2px solid #fff;
            border-top: none;
            border-right: none;
            -webkit-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -o-transform: rotate(-45deg);
            -ms-transform: rotate(-45deg);
            transform: rotate(-45deg);
        }
        .checkboxChange:hover {
            border: 1px solid #409eff;
        }
        .checkbox-reduce {
            display: inline-block;
            border: 1px solid #dcdfe6;
            width: 14px;
            height: 14px;
            background-color: #fff;
            z-index: 1;
            cursor: pointer;
            position: relative;
        }
        .checkbox-reduce:after{
            content: "";
            position: absolute;
            width: 10px;
            background: transparent;
            top: 5px;
            left: 1px;
            border: 2px solid #fff;
            border-top: none;
            border-right: none;
            border-left: none;
        }
        .active-true{
            background-color: #409eff;
            border-color: #409eff;
        }
        .active-reduce{
            background-color: #409eff;
            border-color: #409eff;
        }
        #Cascader li{
            height: 30px;
            line-height: 30px;
        }
        #Cascader-2 ul li{
            height: 30px;
            line-height: 30px;
        }
        #Cascader-3 ul li{
            height: 30px;
            line-height: 30px;
        }
        #Cascader li:hover{
            cursor: pointer;
        }
        #Cascader-2 ul li:hover{
            cursor: pointer;
        }
        #Cascader-3 ul li:hover{
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container jy_wrap">
    <div class="row">
        <div class="col-md-12">
            <div class="jy_wrapper">
                <ul class="jy_title">
                    <li class="is_active"><span class="title">巡查管理</span></li>
                    <li class="no_active"><span class="title">巡查点检查项配置</span></li>
                </ul>
                <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                    <div class="form-inline">
                        <div class="form-group">
                            <label for="identityNo" class="control-label">创建时间<strong>:</strong></label>
                            <input type="text" readonly="readonly" class="form-control" id="Search-lastUpdate">
                            <label for="identityNo" class="control-label">巡查周期<strong>:</strong></label>
                            <select class="form-control" id="Search-InspectCycle">
                                <option value="">--请选择--</option>
                                <option value="0">日</option>
                                <option value="1">周</option>
                                <option value="2">月</option>
                                <option value="3">年</option>
                            </select>
                            <label for="identityNo" class="control-label">频率查询<strong>:</strong></label>
                            <input type="text" class="form-control" id="Search-InspectCount">

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
                                <option value="lastUpdate">创建时间</option>
                                <option value="InspectCycle">巡查周期</option>
                                <option value="InspectCount">频率查询</option>
                            </select>
                            <span id="commonInput">
                                <input type="text" readonly="readonly" class="form-control inputWidth" id="lastUpdate">
                                <select class="form-control inputWidth" name="SearchInspectCycle" id="SearchInspectCycle" style="display: none;">
                                    <option value="">请选择</option>
                                    <option value="0">日</option>
                                    <option value="1">周</option>
                                    <option value="2">月</option>
                                    <option value="3">年</option>
                                </select>
                                <input type="text" class="form-control showInput" name="searchContent" id="SearchContent" style="display: none;">
                            </span>
                        </div>
                        <button type="button" class="btn btn-primary ordinarySearch">
                            <i class="fa fa-search"></i>&nbsp;查询
                        </button>
                        <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                        <%-- <c:if test="${userSession.account != 'admin' }"> --%>
                        <%-- <c:if test="${userSession.unitId != null and userSession.unitId != ''}"> --%>
                        <%--  </c:if> --%>
                        <%--  </c:if> --%>
                        <%--<button type="button" class="btn btn-del removeMove"--%>
                                <%--data-toggle="modal">--%>
                            <%--<i class="fa fa-trash"></i>&nbsp;批量删除--%>
                        <%--</button>--%>
                        <%--<button type="button" class="btn btn-primary" data-toggle="modal">--%>
                            <%--<i class="fa fa-eye"></i>&nbsp;巡查点配置--%>
                        <%--</button>--%>
                        <c:if test="${userSession.unitId != null and userSession.unitId != ''}">
                            <button type="button" class="btn btn-new leadingIn"
                                    data-toggle="modal">
                                <i class="fa fa-plus"></i>&nbsp;导入
                            </button>
                        </c:if>
                        <button type="button" class="btn btn-del removeMove" data-toggle="modal">
                            <i class="fa fa-trash"></i>&nbsp;批量删除
                        </button>
                        <button class="btn btn-new bind">
                            <i class="fa fa-eye"></i>巡查关系确认
                        </button>
                        <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                        <!--                             </button> -->
                    </div>
                </div>
                <div class="inspectCheckConfigureTable">
                    <table class="table table-bordered" id="inspectCheckConfigureTable">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 新增模态框 -->
<%--<div class="modal fade" tabindex="-1" role="dialog" id="newAdd">--%>
    <%--<div class="modal-dialog" role="document" id="wrapModal">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal"--%>
                        <%--aria-label="Close">--%>
                    <%--<span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
                <%--<h4 class="modal-title">新增巡查点检查项</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--<div class="container">--%>
                    <%--<div class="row">--%>
                        <%--<div class="col-md-10">--%>
                            <%--<table class="table tableNo">--%>
                                <%--<colgroup>--%>
                                    <%--<col width="15%">--%>
                                    <%--<col width="35%">--%>
                                    <%--<col width="15%">--%>
                                    <%--<col width="35%">--%>
                                <%--</colgroup>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="UnitID"></select>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>所属建筑<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="BuildID"></select>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>所属区域<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="BuildAreaID"></select>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>巡查点<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="SiteId"></select>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>巡查设施<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="SiteClassId"></select>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>检查项<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="SiteClassDetialId"></select>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>巡查周期<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<select class="form-control inputWidth" id="InspectCycle">--%>
                                            <%--<option value="">--请选择--</option>--%>
                                            <%--<option value="0">日</option>--%>
                                            <%--<option value="1">周</option>--%>
                                            <%--<option value="2">月</option>--%>
                                            <%--<option value="3">年</option>--%>
                                        <%--</select>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>巡查频率<strong>:</strong></td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<input type="text" class="form-control inputWidth" id="InspectCount">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>执行开始时间<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left"><input type="text" readonly="readonly"--%>
                                                                 <%--class="form-control inputWidth" id="TaskStart">--%>
                                    <%--</td>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>执行结束时间<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left"><input type="text" readonly="readonly"--%>
                                                                 <%--class="form-control inputWidth" id="TaskEnd">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="text-right"><font style="color: red">*</font>责任人<strong> :</strong>--%>
                                    <%--</td>--%>
                                    <%--<td class="text-left">--%>
                                        <%--<input type="text" class="form-control inputWidth" id="TaskUserName">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                            <%--</table>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-primary btnSure">--%>
                    <%--<i class="fa fa-floppy-o"></i>&nbsp;保存--%>
                <%--</button>--%>
                <%--<button type="button" class="btn btn-close btnClose"--%>
                        <%--data-dismiss="modal">--%>
                    <%--<i class="fa fa-times"></i>&nbsp;关闭--%>
                <%--</button>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<!-- /.modal-content -->--%>
    <%--</div>--%>
    <%--<!-- /.modal-dialog -->--%>
<%--</div>--%>
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
                                        <input class="form-control inputWidth" id="UnitIDView" readonly="readonly">
                                    </td>
                                    <td class="text-right">所属建筑<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input class="form-control inputWidth" id="BuildIDView" readonly="readonly">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">所属区域<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input class="form-control inputWidth" id="BuildAreaIDView" readonly="readonly">
                                    </td>
                                    <td class="text-right">巡查点<strong> :</strong>
                                    </td>
                                    <td class="text-left"><input type="text"  readonly="readonly"
                                                                 class="form-control inputWidth" id="SiteIdView">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">巡查设施<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input class="form-control inputWidth" readonly="readonly" id="SiteClassIdView">
                                    </td>
                                    <td class="text-right">检查项<strong> :</strong>
                                    </td>
                                    <td class="text-left"><input type="text"  readonly="readonly"
                                                                 class="form-control inputWidth" id="SiteClassDetialIdView">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">巡查周期<strong> :</strong>
                                    </td>

                                    <td class="text-left">
                                        <select disabled="disabled" class="form-control inputWidth" id="InspectCycleView">
                                            <option value="">--请选择--</option>
                                            <option value="0">日</option>
                                            <option value="1">周</option>
                                            <option value="2">月</option>
                                            <option value="3">年</option>
                                        </select>
                                    </td>
                                    <td class="text-right">巡查频率<strong>:</strong></td>
                                    <td class="text-left">
                                        <input type="text" class="form-control inputWidth" id="InspectCountView" readonly="readonly">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">执行开始时间<strong> :</strong>
                                    </td>
                                    <td class="text-left"><input type="text"  readonly="readonly"
                                                                 class="form-control inputWidth" id="TaskStartView">
                                    </td>
                                    <td class="text-right">执行结束时间<strong> :</strong>
                                    </td>
                                    <td class="text-left"><input type="text"  readonly="readonly"
                                                                 class="form-control inputWidth" id="TaskEndView">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">责任人<strong> :</strong>
                                    </td>
                                    <td class="text-left">
                                        <input type="text" class="form-control inputWidth" id="TaskUserNameView" readonly="readonly">
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

<!-- 查看巡查项模态框 -->
<%--<div class="modal fade" tabindex="-1" role="dialog" id="PatrolItem">--%>
    <%--<div class="modal-dialog" role="document">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal"--%>
                        <%--aria-label="Close">--%>
                    <%--<span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
                <%--<h4 class="modal-title">查看巡查项</h4>--%>
            <%--</div>--%>
            <%--<table class="table table-bordered" id="PatrolItemTable">--%>

            <%--</table>--%>
        <%--</div>--%>
        <%--<!-- /.modal-content -->--%>
    <%--</div>--%>
    <%--<!-- /.modal-dialog -->--%>
<%--</div>--%>

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

<%--巡查关系确认框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="patrolRelation">
    <div class="modal-dialog" role="document" id="wrapModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">巡查点和巡查对象关系确认</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="Cascader" style="display: flex;justify-content: flex-start">
                                <ul id="Cascader" style="max-height: 300px;overflow-y: auto;">

                                </ul>
                                <div id="Cascader-2" style="max-height: 300px;overflow-y: auto;display: none;">

                                </div>
                                <div id="Cascader-3" style="max-height: 300px;overflow-y: auto;display: none;">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnSure">
                    <i class="fa fa-floppy-o"></i>&nbsp;保存
                </button>
                <button type="button" class="btn btn-close btnClose" data-dismiss="modal">
                    <i class="fa fa-times"></i>&nbsp;关闭
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 区分新增和编辑 -->
<input type="hidden" id="edit" value="0">

<input type="hidden" name="indexId" id="indexId" value="">

<%--权限--%>
<input type="hidden" name="preId" id="preId" value="">

<script src="/js/page_js/inspect/inspectCheckConfigure.js"></script>
</body>
</html>
