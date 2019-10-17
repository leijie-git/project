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
<title>视频管理</title>
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
						<li class="no_active"><span class="title">视频管理</span></li>
					</ul>
					 <div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
                        <div class="form-inline">
                            <div class="form-group">
                               <label for="unitName" class="control-label">单位名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="unitName">
                               <label for="identityNo" class="control-label">视频名称<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="name">
                                      <label for="identityNo" class="control-label">用户名<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="ip">
                             </div>
                             <div class="form-group">
                                     <label for="identityNo" class="control-label">端口号<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="port">
                                     <label for="identityNo" class="control-label">用户名<strong>
                                        :</strong></label> <input type="text" class="form-control"
                                    id="userName">
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
                                     <option value="name">视频名称</option>
                                     <option value="ip">IP</option>
                                     <option value="port">端口号</option>
                                     <option value="userName">用户名</option>
                                </select>
								  <span id="commonInput"><input type="text"
                                    class="form-control showInput" name="searchContent"
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
					<div class="videoTable">
						<table class="table table-bordered" id="videoTable">

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
					<h4 class="modal-title">新增视频</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<table class="table tableNo">
								<colgroup>
                                        <col width="13%">
                                        <col width="35%">
                                        <col width="17%">
                                        <col width="35%">
                                    </colgroup>
									<tr>
									   <td class="text-right">视频类型<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth" id="VideoIDType">
                                                <option value="">--请选择--</option>
                                                <option value="1">直属单位</option>
                                               <!--  <option value="2">直属建筑</option> -->
                                                <option value="3">直属分区</option>
                                                <option value="4">点位视频</option>
                                            </select>
                                        </td>
									
									   <td class="text-right" id="unitTd">所属单位<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
	                                            <select  class="form-control inputWidth" id="UnitID">
	                                                <option value="">--请选择--</option>
	                                            </select>
                                        </td>
										
									</tr>
									<tr>
									   <td class="text-right" id="buildTd" >所属建筑<font style="color: red">*</font><strong>
                                                :</strong>
                                        </td>
                                        <td class="text-left" >
	                                            <select class="inputWidth form-control" id="BuildId" disabled="disabled">
	                                               <option value="">--请选择--</option>
	                                            </select>
                                        </td>
                                        
									   <td class="text-right" id="buildAreaTd" >所属分区<font style="color: red">*</font><strong>
                                                :</strong>
                                        </td>
                                        <td class="text-left">
	                                            <select class="inputWidth form-control" id="BuildAreaId" disabled="disabled">
	                                               <option value="">--请选择--</option>
	                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">视频名称<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control showInput" name="Brand" id="VideoName">
                                            </div>
                                        </td>
                                        <td class="text-right">视频品牌<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control showInput" name="Brand" id="Brand">
                                            </div>
                                        </td>
                                    </tr>
									<tr>
                                        <td class="text-right">IP<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="IP">
                                        </td>
                                         <td class="text-right">端口号<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Port"
                                            onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
                                            onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">用户名<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="UserName">
                                        </td>
                                        <td class="text-right">密码<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Password">
                                        </td>
                                    </tr>
									<tr>
                                        <td class="text-right">视频插件类型<strong> :</strong>
                                        </td>
                                        <td class="text-left" >
	                                        <select class="form-control inputWidth" id="VideoType">
	                                               <option value="">--请选择--</option>
	                                            <option value="0">海康Ocx控件</option>
	                                            <option value="1">海康Web组件</option>
	                                            <option value="2">萤石直播UIKit</option>
                                                <option value="9">乐橙云</option>
<!-- 	                                           <option value="3">iVMS7200</option> -->
<!-- 	                                            <option value="4">萤石直播UIKit</option> -->
<!-- 	                                            <option value="5">阿百讯视屏服务器</option> -->
<!-- 	                                            <option value="6">阿百讯IPC</option> -->
<!-- 	                                            <option value="7">宇视摄像头</option> -->
	                                        </select>
                                        </td>
                                         <td class="text-right">通道<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="number"
                                            class="form-control inputWidth" id="PosCode" name="PosCode">
                                        </td>
                                    </tr>
                                    
									
									<tr>
                                        <td class="text-right">序列号<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control" name="serialnumber" id="serialnumber">
                                            </div>
                                        </td>
                                         <td class="text-right">生产厂家<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text"
                                            class="form-control inputWidth" id="Manufactor" name="Manufactor">
                                        </td>
                                    </tr>
									<tr>
                                        <td class="text-right">安装位置<strong> :</strong>
                                        </td>
                                        <td class="text-left" colspan="3">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control" name="Position" id="Position">
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
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增视频</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <table class="table tableNo">
                                <colgroup>
                                        <col width="13%">
                                        <col width="35%">
                                        <col width="17%">
                                        <col width="35%">
                                    </colgroup>
                                    <tr>
                                       <td class="text-right">视频类型<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <select class="form-control inputWidth"  disabled="disabled" id="VideoIDTypeView">
                                                <option value="">--请选择--</option>
                                                <option value="1">消控室</option>
                                                <option value="2">直属建筑</option>
                                                <option value="3">直属分区</option>
                                                <option value="4">点位视频</option>
                                            </select>
                                        </td>
                                    
                                       <td class="text-right" id="unitTd">所属单位<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                                <select  class="form-control inputWidth"  disabled="disabled" id="UnitIDView">
                                                    <option value="">--请选择--</option>
                                                </select>
                                        </td>
                                        
                                    </tr>
                                    <tr>
                                       <td class="text-right" id="buildTd" >所属建筑<font style="color: red">*</font><strong>
                                                :</strong>
                                        </td>
                                        <td class="text-left" >
                                                <select class="inputWidth form-control"  disabled="disabled" id="BuildIdView" disabled="disabled">
                                                   <option value="">--请选择--</option>
                                                </select>
                                        </td>
                                        
                                       <td class="text-right" id="buildAreaTd" >所属分区<font style="color: red">*</font><strong>
                                                :</strong>
                                        </td>
                                        <td class="text-left">
                                                <select class="inputWidth form-control"  disabled="disabled" id="BuildAreaIdView" disabled="disabled">
                                                   <option value="">--请选择--</option>
                                                </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">视频名称<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control showInput" readonly="readonly" name="Brand" id="VideoNameView">
                                            </div>
                                        </td>
                                        <td class="text-right">视频品牌<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control showInput" readonly="readonly" name="Brand" id="BrandView">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">IP<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="IPView">
                                        </td>
                                         <td class="text-right">端口号<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="PortView"
                                            onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
                                            onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">用户名<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="UserNameView">
                                        </td>
                                        <td class="text-right">密码<font style="color: red">*</font><strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="PasswordView">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">视频插件类型<strong> :</strong>
                                        </td>
                                        <td class="text-left" >
                                            <select class="form-control inputWidth" id="PlugInTypeView"  disabled="disabled">
                                                <option value="">--请选择--</option>
                                                <option value="0">海康Ocx控件</option>
                                                <option value="1">海康Web组件</option>
                                                <option value="2">萤石直播UIKit</option>
                                                <option value="9">乐橙云</option>
<!--                                                 <option value="3">iVMS7200</option> -->
<!--                                                 <option value="4">萤石直播UIKit</option> -->
<!--                                                 <option value="4">阿百讯视屏服务器</option> -->
<!--                                                 <option value="4">阿百讯IPC</option> -->
<!--                                                 <option value="4">宇视摄像头</option> -->
                                            </select>
                                        </td>
                                         <td class="text-right">生产厂家<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="PosCodeView">
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td class="text-right">序列号<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control" readonly="readonly" name="serialnumberView" id="serialnumberView">
                                            </div>
                                        </td>
                                         <td class="text-right">生产厂家<strong> :</strong>
                                        </td>
                                        <td class="text-left"><input type="text" readonly="readonly"
                                            class="form-control inputWidth" id="ManufactorView" name="Manufactor">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">安装位置<strong> :</strong>
                                        </td>
                                        <td class="text-left" colspan="3">
                                            <div class="needCheck inputWidth">
                                                <input type="text" class="form-control" readonly="readonly" name="Position" id="PositionView">
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
	
	<!-- 授权模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="grant">
		<div class="modal-dialog" role="document" id="second-modal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">授权</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-9">
								<div class="mainList">
									<table class="table" id="grantTable"
										data-click-to-select="true">

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btnGrant">
						<i class="fa fa-floppy-o"></i>&nbsp;确定
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

	<!-- 区分新增和编辑 -->
	<input type="hidden" id="edit" value="0">
	
	<input type="hidden" name="indexId" id="indexId" value="">

	<%--权限--%>
	<input type="hidden" name="preId" id="preId" value="">


</body>
<script src="/js/page_js/unit/video.js"></script>
</html>