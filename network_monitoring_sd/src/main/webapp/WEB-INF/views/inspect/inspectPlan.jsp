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
<title>巡查计划</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/role_management.css">
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.excheck.js"></script>
<style>
    .fixed-table-body thead th .th-inner{text-align: center;}

</style>
</head>
<body>
    <div class="container jy_wrap">
        <div class="row">
            <div class="col-md-12">
                <div class="jy_wrapper">
                    <ul class="jy_title">
                        <li class="is_active"><span class="title">巡查计划</span></li>
                        <li class="no_active"><span class="title">新增巡查计划</span></li>
                    </ul>
                </div>
                 <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="identityNo" class="control-label">计划名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="planNames">
                                    <label for="identityNo" class="control-label">巡检人<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="defaultUserName">
                                     计划时间： <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="searchContent"
                                    id="planStart" placeholder="开始时间" readonly="readonly"></span>
                                    - <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="searchContent"
                                    id="planEnd"  placeholder="结束时间" readonly="readonly"></span>
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
        <div class="row">
            <div class="col-md-12">
                <div class="dataTable">
                    <div class="jy_new" style="top: 6px;">
                        <div class="form-inline">
                            <div class="form-group">
                            <select name="menuType" id="menuType" class="form-control">
                                     <option value="planNames">计划名称</option>
                                     <option value="defaultUserName">计划责任人</option>
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
                          <c:if test="${userSession.unitId != null and userSession.unitId != ''}">
                              <button type="button" class="btn btn-new btnNew"
                                data-toggle="modal" data-target='#newAdd'>
                                <i class="fa fa-plus"></i>&nbsp;新增
                            </button>
                           </c:if>
                            <%-- </c:if> --%>
                          
                            <!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
                            <!--                             </button> -->
                        </div>
                    </div>
                    <div class="inspectPlanTable">
                        <table class="table table-bordered" id="inspectPlanTable">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 新增/编辑计划模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
        <div class="modal-dialog" role="document" id="firstModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增巡查计划</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="10%">
                                        <col width="40%">
                                        <col width="12%">
                                        <col width="38%">
                                </colgroup>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="PlanUnit" disabled="disabled">
                                           <%-- <option value="0">--请选择--</option>--%>
                                            </select>

                                        </td>
                                        <td class="text-right" style="width:100px;"><font style="color: red;">*</font>计划责任人<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="Personliable">
                                            <option value="0">--请选择--</option>
                                        </select>
                                        </td>

                                    </tr>

                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>计划名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="PlanName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>开始时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="StartTime" readonly="readonly">
                                        </td>
                                        <td class="text-right"><font style="color: red ;width: 100px;">*</font>结束时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="EndTime" readonly="readonly">
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
    
     <!-- 查看计划模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="newAddView">
        <div class="modal-dialog" role="document" id="fourModalView">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看巡查计划</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="10%">
                                        <col width="40%">
                                        <col width="12%">
                                        <col width="38%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select type="text" class="form-control inputWidth" id="unitname" disabled="disabled"></select>
                                        </td>
                                        <td class="text-right" style="width: 100px;"><font style="color: red;">*</font>计划责任人<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" class="form-control inputWidth" id="duty" readonly="readonly">
                                        </td>

                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>计划名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="PlanNameView" readonly="readonly"> 
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>开始时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="StartTimeView" readonly="readonly">
                                        </td>
                                        <td class="text-right"><font style="color: red">*</font>结束时间<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="EndTimeView" readonly="readonly">
                                        </td>
                                    </tr>
                                </table>
                                <table class="table table-bordered" id="inspectSiteTableView" style="width: 90%;">
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
    
       <!-- 编辑巡查点模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="editSite"style="z-index:1200;">
        <div class="modal-dialog" role="document" id="secondModal" >
            <div class="modal-content" >
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">编辑巡查点</h4>
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
                                        <td class="text-right"><font style="color: red">*</font>位置名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="SiteNames" readonly="readonly">
                                        </td>
                                       
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>位置编号<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="SiteCodes" readonly="readonly">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">巡查周期<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="inspectCycleType" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">巡查次数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="InspectCycles" readonly="readonly">
                                        </td>
                                    </tr>
                                  <%--  <tr>
                                         <td class="text-right"><font style="color: red">*</font>巡查人员<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                        <input type="text" style="display:none"
                                            class="form-control inputWidth" id="DefaultUserIDs">
                                            <input type="text"
                                            class="form-control inputWidth" id="DefaultUserNames" readonly="readonly">
                                            <button type="button" class="btn btn-primary " onclick="setRoles()">
						                        <i class="fa"></i>&nbsp;选择
						                    </button>
                                        </td>
                                    </tr>--%>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary " onclick="siteEdit()">
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
    
      <!-- 导入巡查点模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="addSite">
        <div class="modal-dialog" role="document" id="twoModalView">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查询巡查点</h4>
                </div>
                <div class="form-inline" style="margin-bottom:10px;margin-top:10px;">
                <div class="form-group">
                                                                                         位置名称： <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="SiteNameSite"
                                    id="SiteNameSite" ></span>
                                      位置编号： <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="SiteCodeSite"
                                    id="SiteCodeSite" ></span>
                            <button type="button" class="btn btn-primary " onclick="siteSearch()">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                            <button type="button" class="btn btn-primary " onclick="siteClear()">
                                <i class="fa fa-times"></i>&nbsp;清除
                            </button>
                </div>
                <div style="margin-bottom:10px;margin-top:10px;margin-right:10px;">
                    <button type="button" class="btn btn-primary " id="createAllTask" onclick="createAllTask()">
                        <i class="fa fa-floppy-o"></i>&nbsp;已关联批量生成任务
                    </button>
                    <button type="button" class="btn btn-primary SiteInto">
                        <i class="fa fa-floppy-o"></i>&nbsp;确认关联
                    </button>

                </div>
                </div>
                <div style="margin:5px auto;" id="taskImport">
                    <table class="table table-bordered" id="inspectSiteTable">
                    </table>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    
     <!-- 查看任务模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="LookTask">
        <div class="modal-dialog" role="document" id="firstViewModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">任务列表</h4>
                </div>
                 <div class="form-inline" style="margin-bottom:10px;margin-top:10px;">
	                <div class="form-group">
                                位置编号： <input type="text"
                                        class="form-control showInput" name="taskSearchVal"
                                        id="SiteCodeTask" >
                                        巡查人员： <input type="text"
                                        class="form-control showInput"  name="taskSearchVal"
                                        id="inspectusername" >
                                          位置名称： <input type="text"
                                        class="form-control showInput" name="taskSearchVal"
                                        id="SiteName" >
                                        <br>
                                        <br>
	                                         巡查时间： <span id="commonInput"><input type="text"
	                                    class="form-control showInput" placeholder="开始时间" name="SiteC  odeSite"
	                                    id="TaskStart" ></span>
	                                         <input type="text"
	                                    class="form-control showInput" style="margin-left:70px;" placeholder="结束时间" name="SiteC  odeSite"
	                                    id="TaskEnd" >
	                            <button type="button" class="btn btn-primary " style="margin-left:50px;"onclick="taskSearch()">
	                                <i class="fa fa-search"></i>&nbsp;查询
	                            </button>
	                            <button type="button" class="btn btn-primary " onclick="taskClear()">
	                                <i class="fa fa-times"></i>&nbsp;清除
	                            </button>
                                <button type="button" class="btn btn-del removeMove"
                                        data-toggle="modal">
                                    <i class="fa fa-trash"></i>&nbsp;批量删除
                                </button>
	                </div>
                </div>
                <table class="table table-bordered" id="mainTaskTable" style="text-align: center;">
                                  
                </table>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    
    <!-- 添加任务巡查人员模态框 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="setTaskRoleModal">
        <div class="modal-dialog" role="document" id="secondViewModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">设置人员</h4>
                </div>
                <div class="modal-body">
                    <div class="content" style="float:left; text-align:center; margin-right:10px;">
                             <h3 style="line-height: 16px;text-align: center;font-size: 14px">未选择</h3>
                             <select multiple="multiple" id="select1"
                                     style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">


                             </select>
                             <span id="Taskremove" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">选中右移>></span>
                             <span id="Taskremove_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">全部右移>></span>
                     </div>

                     <div class="content" style="float:left; text-align:center; margin-right:10px;">
                         <h3 style="line-height: 16px;text-align: center;font-size: 14px">已选择</h3>
                         <select multiple="multiple" id="select2"
                                 style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">
                         </select>
                         <span id="Taskadd" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;选中左移</span>
                         <span id="Taskadd_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;全部左移</span>
                     </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary btn-save_role">
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
<script src="/js/page_js/inspect/inspectPlan.js"></script>
</html>