<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>通知管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/Employee_management.css">
<!-- <script type="text/javascript"
	src="/js/common/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="/js/common/zTree/jquery.ztree.excheck.js"></script> -->
</head>
<body>

	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">系统管理</span></li>
						<li class="no_active"><span class="title">通知管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none"
						clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">发送人<strong>
										:</strong></label> <input type="text" class="form-control" name="sender"
									id="sender"> <label for="identityNo"
									class="control-label">标题<strong> :</strong></label> <input
									type="text" class="form-control" name="title" id="title">
								<label for="identityNo" class="control-label">内容<strong>
										:</strong></label> <input type="text" class="form-control" name="content"
									id="content">
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
									<option value="sender">发送人</option>
									<option value="title">标题</option>
									<option value="content">内容</option>
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
		<div class="modal-dialog" role="document" id="secondModal">
			<div class="modal-content" style="width: 110%;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增通知</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">主题<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNotify-title">
											</td>
										</tr>
										<tr>
											<td class="text-right">内容<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<textarea rows="3" cols="" class="form-control inputNormal" id="newNotify-content"></textarea>
											</td>
										</tr>
										<tr style="display: none">
											<td class="text-right password">发送时间<strong> :</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newNotify-sendDate"
												readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-left"><button type="button"
													class="btn btn-primary" onclick="Import()">
													</i>&nbsp;选择接收人
												</button></td>
										</tr>
										<!-- 通知接收人 -->
										<table class="table table-bordered receiverTable"
											id="receiverTable">

										</table>
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

	<!-- 员工查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
		<div class="modal-dialog" role="document" id="secondViewModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">主题<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNotifyView-title"
												readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">内容<strong> :</strong>
											</td>
											<td class="text-left">
												<textarea rows="3" cols="" class="form-control inputNormal" id="newNotifyView-content" readonly="readonly"></textarea>
											</td>
										</tr>
										<tr style="display: none">
											<td class="text-right password">发送时间<font
												style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newNotifyView-sendDate"
												readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-left">接收人<strong> :</strong>
											</td>
										</tr>
										<!-- 通知接收人 -->
										<table class="table table-bordered receiverTable"
											style="width: 95%;">
										</table>
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

	<!--  选择接收人模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="addSite">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">选择接收人</h4>
				</div>
				<div class="form-inline"
					style="margin-bottom: 10px; margin-top: 10px;">
					<div class="form-group">
						<button type="button" class="btn btn-primary SiteInto"
							data-dismiss="modal">
							<i class="fa fa-floppy-o"></i>&nbsp;确认选择
						</button>
					</div>
				</div>
				<table class="table table-bordered" id="receiverTableImport">
				</table>
			</div>
		</div>
	</div>

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

	<%--权限--%>
	<input type="hidden" name="preId" id="preId" value="">

</body>
<script src="/js/page_js/systemManage/notify.js"></script>
</html>