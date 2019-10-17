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
<title>建筑物管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/role_management.css">
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript"
    src="/js/common/zTree/jquery.ztree.excheck.js"></script>
    
    <link rel="stylesheet" href="/js/common/layui/css/layui.css">
    <script type="text/javascript"
    src="/js/common/layui/layui.js"></script>
    <style>
    .buildTable tr td:last-child{
    	-webkit-display: flex;
    	-moz-display: flex;
    	display: flex;
    	 justify-content:center;
    }
    </style>
</head>
<body>
    <div class="container jy_wrap">
        <div class="row">
            <div class="col-md-12">
                <div class="jy_wrapper">
                    <ul class="jy_title">
                        <li class="is_active"><span class="title">单位管理</span></li>
                        <li class="no_active"><span class="title">建筑物管理</span></li>
                    </ul>
                    <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <table>
                            <tr>
                            <td>
                             <label for="identityNo" class="control-label">建筑物名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="buildName">
                            </td>
                            <td>
                            <label for="identityNo" class="control-label">单位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="UnitIDs">

                            </td>
                            <td>
            &nbsp;&nbsp; 类别：  <select style="width:150px" class="form-control inputNormal" id="BuildingTypes">
                                        <option value="">--请选择--</option>
                                  </select>
                            </td>
                            <!-- <td>
             &nbsp;&nbsp;使用性质： <select style="width:150px" class="form-control inputNormal" id="UserTypes">
                                    <option value="">--请选择--</option>
                                  </select>
                            </td>
                            <td>
            &nbsp;&nbsp;火灾危险性：<select style="width:150px" class="form-control inputNormal" id="FireRiskGrades">
                                        <option value="">--请选择--</option>
                                    </select>
                            </td> -->
                            <td style="display:block;margin-left:20px;">
                            <button type="button" class="btn btn-primary btnSearch">
                                <i class="fa fa-search" ></i>&nbsp;查询
                            </button>
                            
                            </td>
                            <td>
                            <button type="button" class="btn btn-new btnClear"
                                data-toggle="modal" >
                                <i class="fa fa-undo"></i>&nbsp;重置
                            </button>
                            </td>
                            </table>
                            
                           
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
                        <table>
                           <tr>
                           <td>
                               <select name="menuType" id="menuType" class="form-control">
                                    <option value="buildName">建筑物名称</option>
                                     <option value="UnitIDs">单位名称</option>
                                    
                                </select> <span id="commonInput"><input type="text"
                                    class="form-control inputNormal" name="searchContent"
                                    id="searchContent"></span>
                           </td>
                            <td>
                            <button type="button" class="btn btn-primary ordinarySearch">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                           <button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                            <button type="button" class="btn btn-new btnNew"
                                data-toggle="modal" data-target='#newAdd'>
                                <i class="fa fa-plus"></i>&nbsp;新增
                            </button>
                           
                            </td>
                           </tr>
                        </table>
                        </div>
                    </div>
                    <div class="buildTable">
                        <table class="table table-bordered" id="buildTable">

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
                    <h4 class="modal-title">新增建筑物信息</h4>
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
                                        <td class="text-right"><font style="color: red">*</font>建筑物名称<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                        <input type="text" class="form-control inputNormal" id="BuildingName">
                                        </td>
                                        <td class="text-right">建筑物类别<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                        	<!-- <select name="city" lay-verify="" lay-search id="BuildingType" style="width:209px" class="form-control inputNormal">
											  <option value="0">--请选择--</option>
											  <option value="1">form</option>
											  <option value="2" selected>layim</option>
											</select> -->
											
                                             <select class="form-control inputNormal" id="BuildingType">
                                                <option value="0">--请选择--</option>
                                            </select> 
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right"><font style="color: red">*</font>所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select  class="form-control inputNormal" id="UnitID">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">火灾危险性<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="FireRiskGrade">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">耐火等级<strong> :</strong>
                                        </td>
                                       <td class="text-left">
                                            <select class="form-control inputNormal" id="RefractoryGrade">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">结构类型 <strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="StructureType">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">使用性质<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="UserType">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">建筑面积/m²<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingArea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">占地面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingOccupyarea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right">标准层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="StdFloorArea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">建造日期<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildDate" readonly="readonly">
                                        </td>
                                         <td class="text-right">建筑高度/m<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingHeight"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">地上层数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="OverGroundFloors"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">地上层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="OverGroundArea" name="OverGroundArea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">地下层数<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="UnderGroundFloors"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right">地下层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" name="UnderGroundArea" id="UnderGroundArea"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">隧道高度/m<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal " id="TunnelHeight"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" >
                                        </td>
                                        <td class="text-right">隧道长度/m<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" id="TunnelLength"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防控制室位置 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="FireRoomPosition">
                                        </td>
                                        <td class="text-right"> 避难层数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" id="RefugeNum"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">避难层总面积/m²<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="RefugeArea"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right">避难层位置 <strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" id="RefugePosition">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">安全出口数量<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="SafeExitNum"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 安全出口位置<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="SafeExitPosition">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">安全出口形势 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="SafeExitType">
                                        </td>
                                        <td class="text-right"> 消防电梯数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="FireElevatorNum"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防电梯容纳总重量/kg <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="FireElevatorWeight"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right"> 日常工作时间人数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="DailyPersonNum"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">最大容纳人数 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="AccommodatePersonMaxnum"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 储存物名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="StoreMaterialName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">储存物数量 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="StoreMaterialNum"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 储存物性质<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="StoreMaterialNature">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">储存物形态<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="StoreMaterialForm">
                                        </td>
                                        <td class="text-right">储存容积/m³<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="StoreVolume"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">主要原料<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="MainMaterial">
                                        </td>
                                        <td class="text-right"> 主要产品<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal"id="MainProduct">
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="text-right">毗邻建筑物情况<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <div class="needCheck ">
                                                <input type="text" class="form-control inputNormal" id="NearBuildingSituation">
                                            </div>
                                        </td>
                                        
                                        <td class="text-right">建筑物地址<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <div class="address ">
                                                <input type="text" class="form-control inputNormal" id="address">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                    
                                    <td class="text-right">建筑物平面图<strong> :</strong></td>
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
                                        <td class="text-right">消防设施平面布置图<strong> :</strong></td>
                                        <td class="text-left">
                                            <input id="photoCoverEQ-photo" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
                                                <input id="picurlsEQ-photo" name="imageDataList" type="file"
                                                       class="file typeFileMore"
                                                       value="" onchange="doChangeProjectphotoEQ()"/>
                                                <ul id="loadImgEQ-photo" class="loadImg">
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
                    <h4 class="modal-title">查看建筑物信息</h4>
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
                                        <td class="text-right"><font style="color: red">*</font>建筑物名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" id="BuildingNameView"  readonly="readonly">										
                                        </td>
                                        <td class="text-right">建筑物类别<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="BuildingTypeView"   disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select> 
                                            
                                            <!-- <select name="city" lay-verify="" lay-search id="BuildingType" style="width:209px" class="form-control inputNormal">
											  <option value="0">--请选择--</option>
											  <option value="1">form</option>
											  <option value="2" selected>layim</option>
											</select> -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">所属单位<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select  class="form-control inputNormal" id="UnitIDView">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">火灾危险性<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="FireRiskGradeView"  disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">耐火等级<strong> :</strong>
                                        </td>
                                       <td class="text-left">
                                            <select class="form-control inputNormal" id="RefractoryGradeView"  disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">结构类型 <strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="StructureTypeView"  disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">使用性质<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputNormal" id="UserTypeView"  disabled="disabled">
                                                <option value="0">--请选择--</option>
                                            </select>
                                        </td>
                                         <td class="text-right">建筑面积/m²<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingAreaView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">占地面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingOccupyareaView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right">标准层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="StdFloorAreaView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">建造日期<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildDateView" readonly="readonly">
                                        </td>
                                         <td class="text-right">建筑高度/m<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="BuildingHeightView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">地上层数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="OverGroundFloorsView"  readonly="readonly"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                         <td class="text-right">地上层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="OverGroundAreaView" name="OverGroundArea"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">地下层数<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="UnderGroundFloorsView"  readonly="readonly"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right">地下层面积/m²<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" name="UnderGroundArea" id="UnderGroundAreaView"  readonly="readonly"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">隧道高度/m<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal " id="TunnelHeightView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" >
                                        </td>
                                        <td class="text-right">隧道长度/m<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" 
                                        class="form-control inputNormal" id="TunnelLengthView"  readonly="readonly"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防控制室位置 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="FireRoomPositionView">
                                        </td>
                                        <td class="text-right"> 避难层数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal" id="RefugeNumView"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">避难层总面积/m²<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"
                                            class="form-control inputNormal" id="RefugeAreaView"  readonly="readonly"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right">避难层位置 <strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal" id="RefugePositionView">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">安全出口数量<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="SafeExitNumView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 安全出口位置<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal"id="SafeExitPositionView">
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="text-right">安全出口形势 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="SafeExitTypeView">
                                        </td>
                                        <td class="text-right"> 消防电梯数量<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                        class="form-control inputNormal"id="FireElevatorNumView"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">消防电梯容纳总重量/kg <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="FireElevatorWeightView"
                                            onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                        <td class="text-right"> 日常工作时间人数<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                        class="form-control inputNormal"id="DailyPersonNumView"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">最大容纳人数 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="AccommodatePersonMaxnumView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 储存物名称<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal"id="StoreMaterialNameView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">储存物数量 <strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="StoreMaterialNumView"
                                            onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                        </td>
                                        <td class="text-right"> 储存物性质<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal"id="StoreMaterialNatureView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">储存物形态<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="StoreMaterialFormView">
                                        </td>
                                        <td class="text-right">储存容积/m³<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"   readonly="readonly"
                                        class="form-control inputNormal"id="StoreVolumeView"
                                        onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">主要原料<strong> :</strong>
                                        </td>
                                       <td class="text-left"><input type="text"  readonly="readonly"
                                            class="form-control inputNormal" id="MainMaterialView">
                                        </td>
                                        <td class="text-right"> 主要产品<strong> :</strong>
                                        </td> 
                                        <td class="text-left"><input type="text"  readonly="readonly"
                                        class="form-control inputNormal"id="MainProductView">
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="text-right">毗邻建筑物情况<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <div class="needCheck ">
                                                <input type="text" class="form-control inputNormal" id="NearBuildingSituationView"  readonly="readonly">
                                            </div>
                                        </td>
                                        <td class="text-right">建筑物地址<strong> :</strong>
                                        </td>
                                         <td class="text-left">
                                            <div class="address ">
                                                <input type="text" class="form-control inputNormal" id="addressView"  readonly="readonly">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                    
                                    <td class="text-right">建筑物平面图<strong> :</strong></td>
                                        <td class="text-left">
                                            <input id="picurls-photoView" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
<!--                                                 <input id="picurls-photoView" name="imageDataList" type="file" -->
<!--                                                        class="file typeFileMore" -->
<!--                                                        value="" onchange="doChangeProjectphoto()"/> -->
                                                <ul id="loadImg-photoView" class="loadImg">
<!--                                                     <li class="modalImg"><span>请选择上传图片</span></li> -->
                                                </ul>
                                            </div>
                                        </td>
                                        <td class="text-right">消防设施平面布置图<strong> :</strong></td>
                                        <td class="text-left">
                                            <input id="photoCoverEQ-photoView" type="text" style="display: none;">
                                            <div class="wrapTypeMore">
<!--                                                 <input id="picurlsEQ-photoView" name="imageDataList" type="file" -->
<!--                                                        class="file typeFileMore" -->
<!--                                                        value="" onchange="doChangeProjectphotoEQ()"/> -->
                                                <ul id="loadImgEQ-photoView" class="loadImg">
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
<script src="/js/page_js/unit/build.js"></script>
</html>