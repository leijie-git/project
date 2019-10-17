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
<title>重点部位管理</title>
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
                        <li class="is_active"><span class="title">部位管理</span></li>
                        <li class="no_active"><span class="title">重点部位管理</span></li>
                    </ul>
                     <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                            <label for="identityNo" class="control-label">单位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="unitName">
                                <label for="identityNo" class="control-label">部位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="importName">
                                     <label for="identityNo" class="control-label">所在位置<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="importSite">
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
                                    <option value="importName">部位名称</option>
                                     <option value="importSite">所在位置</option>
                                </select><span id="commonInput"><input type="text"
                                   class="form-control inputWidth" name="searchContent"
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
                    <div class="keyPartsTable">
                        <table class="table table-bordered" id="keyPartsTable">

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
                    <h4 class="modal-title">新增重点部位</h4>
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
                                        <td class="text-right">所属单位<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select  class="form-control showInput" id="UnitID">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">所在建筑<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildId">
                                                <option value="">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">部位名称<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="ImportName">
                                        </td>
                                         <td class="text-right">所在位置<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="ImportSite">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">所在层数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Floor"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">所在高度<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Height"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防电梯数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="DTCount"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">安全出口数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="OutCount"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">火灾危险性<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="HZWXX">
                                        </td>
                                        <td class="text-right">建筑面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BuildArea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">耐火等级<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="NHLevel">
                                                <option value="0">--请选择--</option>
                                                <option value="1">一级</option>
                                                <option value="2">二级</option>
                                                <option value="3">三级</option>
                                            </select>
                                        </td>
                                         <td class="text-right">火种情况<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="FireInfo" name="FireInfo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">确立原因<strong> :</strong>
                                        
                                        </td>
                                        <td class="text-left">
                                        <input id="IsDXFYZDX" name="IsDXFYZDX" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        对消防有重大影响
                                        <input id="IsHZFSHRYSWD" name="IsHZFSHRYSWD" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        火灾后发生人员伤亡
                                        <input id="IsHZHSSD" name="IsHZHSSD" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        火灾后损失大
                                        <input id="IsYFSHZ" name="IsYFSHZ" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        已发生火灾
                                        <input id="IsOther" name="IsOther" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        其他
                                        </td>
                                        <td class="text-right">建筑结构<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="BuildingStructure">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">防火标志<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" class="form-control inputWidth" name="FHBZSLQK" id="FHBZSLQK">
                                        </td>
                                         <td class="text-right">使用功能<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Used">
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="text-right">危险源情况<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                         <input id="IsBZP" name="IsBZP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                                                                                                    爆炸品
                                         <input id="IsYHQHYJHHW" name="IsYHQHYJHHW" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                                                                                                   氧化剂和有机过氧化物
                                         <input id="IsFSP" name="IsFSP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                           腐蚀品
                                         <input id="IsYSQT" name="IsYSQT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                         压缩气体
                                         <input id="IsYDP" name="IsYDP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        有毒品
                                         <input id="IsZXWXW" name="IsZXWXW" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        杂项危险物
                                         <input id="IsYRYT" name="IsYRYT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                          易燃液体 
                                         <input id="IsFSX" name="IsFSX" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                         放射性
                                         <input id="IsYRGT" name="IsYRGT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        易燃固体
                                         <input id="IsQT" name="IsQT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                          其他
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
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增重点部位</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="10%">
                                        <col width="40%">
                                        <col width="10%">
                                        <col width="40%">
                                    </colgroup>
                                    <tr>
                                        <td class="text-right">部位名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputWidth" id="ImportNameView">
                                        </td>
                                         <td class="text-right">所在位置<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="ImportSiteView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">所在层数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="FloorView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">所在高度<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="HeightView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                    
                                        <td class="text-right">所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select  class="form-control showInput" id="UnitIDView" >
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                        
                                        
                                        
                                         <td class="text-right">所在建筑<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="BuildIdView"  disabled="disabled" >
                                                <option value="">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防电梯数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="DTCountView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">安全出口数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="OutCountView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">火灾危险性<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="HZWXXView">
                                        </td>
                                        <td class="text-right">建筑面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="BuildAreaView"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">耐火等级 <strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="NHLevelView"  disabled="disabled" >
                                                <option value="0">--请选择--</option>
                                                <option value="1">一级</option>
                                                <option value="2">二级</option>
                                                <option value="3">三级</option>
                                            </select>
                                        </td>
                                         <td class="text-right">火种情况<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="FireInfoView" name="FireInfo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">确立原因<strong> :</strong>
                                        
                                        </td>
                                        <td class="text-left">
                                        <input id="IsDXFYZDXView" name="IsDXFYZDXView" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        对消防有重大影响
                                        <input id="IsHZFSHRYSWDView" name="IsHZFSHRYSWDView" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        火灾后发生人员伤亡
                                        <input id="IsHZHSSDView" name="IsHZHSSDView" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        火灾后损失大
                                        <input id="IsYFSHZView" name="IsYFSHZView" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        已发生火灾
                                        <input id="IsOtherView" name="IsOtherView" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        其他
                                        </td>
                                        <td class="text-right">建筑结构<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="BuildingStructureView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">防火标志<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <input type="text" class="form-control inputWidth" readonly="readonly" name="FHBZSLQK" id="FHBZSLQKView">
                                        </td>
                                         <td class="text-right">使用功能<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="UsedView">
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="text-right">危险源情况<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                         <input id="IsBZPView" name="IsBZP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                                                                                                    爆炸品
                                         <input id="IsYHQHYJHHWView" name="IsYHQHYJHHW" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                                                                                                   氧化剂和有机过氧化物
                                         <input id="IsFSPView" name="IsFSP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                           腐蚀品
                                         <input id="IsYSQTView" name="IsYSQT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                         压缩气体
                                         <input id="IsYDPView" name="IsYDP" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        有毒品
                                         <input id="IsZXWXWView" name="IsZXWXW" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        杂项危险物
                                         <input id="IsYRYTView" name="IsYRYT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                          易燃液体 
                                         <input id="IsFSXView" name="IsFSX" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                         放射性
                                         <input id="IsYRGTView" name="IsYRGT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                        易燃固体
                                         <input id="IsQTView" name="IsQT" style="vertical-align:text-bottom; width:17px;height:17px;" type="checkbox" value="true">
                                          其他
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
    
    <input type="hidden" id="unitId" value="${userSession.unitId }">


</body>
<script src="/js/page_js/unit/keyParts.js"></script>
</html>