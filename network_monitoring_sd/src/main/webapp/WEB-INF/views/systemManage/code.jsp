<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>代码管理</title>
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
						<li class="is_active"><span class="title">系统管理</span></li>
						<li class="no_active"><span class="title">代码管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">名称<strong>
										:</strong></label> <input type="text" class="form-control" name="codeName"
									id="codeName">
								<label for="identityNo" class="control-label">值<strong>
										:</strong></label> <input type="text" class="form-control" name="codeValue"
									id="codeValue">
								<label for="identityNo" class="control-label">代码组编码<strong>
										:</strong></label> <input type="text" class="form-control" name="codeGroupKey"
									id="codeGroupKey">
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
									<option value="codeName">名称</option>
									<option value="codeValue">值</option>
									<option value="codeGroupKey">代码组编码</option>
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
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增代码</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCode-codename" >
											</td>
										</tr>
										<tr>
											<td class="text-right">值<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCode-codevalue" >
											</td>
										</tr>
										<tr>
											<td class="text-right">排序<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCode-sortorder" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入数字" >
											</td>
										</tr>
										<tr>
											<td class="text-right password">说明<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newCode-memo" >
											</td>
										</tr>
										<tr>
											<td class="text-right password">代码组ID<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><select name="newCode-codegroupid" id="newCode-codegroupid" class="form-control inputNormal"></select>
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
					<!-- <button type="button" class="btn btn-primary" id="continue" >
						<i class="fa fa-floppy-o"></i>&nbsp;保存并继续
					</button> -->
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
											<td class="text-right">名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCodeView-codename" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">值<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCodeView-codevalue" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">排序<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newCodeView-sortorder" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入数字" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right password">说明<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newCodeView-memo" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right password">代码组ID<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><select name="newCodeView-codegroupid" id="newCodeView-codegroupid" class="form-control inputNormal" disabled="disabled"></select>
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
<script src="/js/page_js/systemManage/code.js"></script>
</html>