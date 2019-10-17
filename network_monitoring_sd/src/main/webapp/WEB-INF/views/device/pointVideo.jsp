<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>点位视频管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet"
	href="/css/page_css/Employee_management.css">
</head>
<body>

	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">设备管理</span></li>
						<li class="no_active"><span class="title">点位视频管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">视频名称<strong>
										:</strong></label> <input type="text" class="form-control" name="name"
									id="name">
								<label for="unitName" class="control-label">单位名称<strong>
										:</strong></label> <input type="text" class="form-control" name="unitName"
									id="unitName">
								<label for="identityNo" class="control-label">IP地址<strong>
										:</strong></label> <input type="text" class="form-control" name="ip"
									id="ip">
								<label for="identityNo" class="control-label">视频品牌<strong>
										:</strong></label> <input type="text" class="form-control" name="brand"
									id="brand">
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
		<div class="row" id="employss">
			<div class="col-md-12">
				<div class="dataTable">
					<div class="jy_new" style="top: 20px;">
						<div class="form-inline">
							<div class="form-group">
								<select name="menuType" id="menuType" class="form-control">
									<option value="unitName">单位名称</option>
									<option value="name">视频名称</option>
									<option value="ip">IP地址</option>
									<option value="brand">视频品牌</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button class="btn btn-new newAdd">
								<i class="fa fa-plus"></i>新增
							</button>
							<button class="btn btn-new export">
								<i class="fa fa-plus"></i>导出
							</button>
<!-- 							<button class="btn btn-danger btnDeleteMany"> -->
<!-- 								<i class="fa fa-trash"></i>批量删除 -->
<!-- 							</button> -->
						</div>
					</div>
					<div id="parentForm">
						<table class="table table-bordered" id="userTable">

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增单位编号绑定模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmploy">
		<div class="modal-dialog" role="document" id="firstModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增点位视频</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
									   <tr>
                                            <td class="text-right" id="unitTd">单位<font style="color: red">*</font><strong> :</strong>
	                                        </td>
	                                        <td class="text-left">
		                                            <select  class="form-control inputWidth" id="UnitID">
		                                                <option value="">--请选择--</option>
		                                            </select>
	                                        </td>
                                            <td class="text-right">视频名称<font style="color: red">*</font><strong>
                                                    :</strong>
                                            </td>
                                            <td>
	                                            <input type="text" class="form-control inputNormal" id="newPointVideo-name" >
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">IP<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-ip" >
											</td>
											<td class="text-right">视频品牌<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-brand" >
											</td>
										</tr>
										<tr>
											<td class="text-right">端口号<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-port" >
											</td>
											<td class="text-right">用户名<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-userName" >
											</td>
										</tr>
										<tr>
											<td class="text-right">密码<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-password" >
											</td>
											<td class="text-right">视频插件类型<strong>
													:</strong>
											</td>
											<td class="text-left">
												<select class="form-control inputNormal" id="newPointVideo-plugInType" >
	                                                <option value="">--请选择--</option>
	                                                <option value="0">海康Ocx控件</option>
	                                                <option value="1">海康Web组件</option>
	                                                <option value="2">萤石直播UIKit</option>
													<option value="9">乐橙云</option>
	                                            </select>
											</td>
										</tr>
										<tr>
											<td class="text-right">通道<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-poscode" >
											</td>
											<td class="text-right">序列号<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-serialnumber" >
											</td>
										</tr>
										<tr>
											<td class="text-right">安装位置<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-position" >
											</td>
											<td class="text-right">生产厂家<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideo-manufactor" >
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btnSure">
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
	
	<!-- 查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
		<div class="modal-dialog" role="document" id="firstViewModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看设备类型</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
									   <tr>
                                           <td class="text-right" id="unitTd">所属单位<font style="color: red">*</font><strong> :</strong>
	                                        </td>
	                                        <td class="text-left">
	                                                <select  class="form-control inputWidth"  disabled="disabled" id="UnitIDView">
	                                                    <option value="">--请选择--</option>
	                                                </select>
	                                        </td>
                                            <td class="text-right">视频名称<strong>
                                                    :</strong>
                                            </td>
                                            <td>
	                                            <input type="text" class="form-control inputNormal" id="newPointVideoView-name" readonly="readonly">
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">IP<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-ip" readonly="readonly">
											</td>
											<td class="text-right">视频品牌<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-brand" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">端口号<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-port" readonly="readonly">
											</td>
											<td class="text-right">用户名<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-userName" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">密码<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-password" readonly="readonly">
											</td>
											<td class="text-right">视频插件类型<strong>
													:</strong>
											</td>
											<td class="text-left">
												<select class="form-control inputNormal" id="newPointVideoView-plugInType" disabled="disabled">
	                                                <option value="">--请选择--</option>
	                                                <option value="0">海康Ocx控件</option>
	                                                <option value="1">海康Web组件</option>
	                                                <option value="2">萤石直播UIKit</option>
													<option value="9">乐橙云</option>

	                                            </select>
											</td>
										</tr>
										<tr>
											<td class="text-right">通道<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-poscode" readonly="readonly">
											</td>
											<td class="text-right">序列号<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-serialnumber" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">安装位置<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-position" readonly="readonly">
											</td>
											<td class="text-right">生产厂家<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="text" class="form-control inputNormal" id="newPointVideoView-manufactor" readonly="readonly">
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
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

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/device/pointVideo.js"></script>
</html>