<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>上传单位A</title>
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
						<li class="is_active"><span class="title">单位上传</span></li>
						<li class="no_active"><span class="title">上传单位</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">单位名称<strong>
										:</strong></label> <input type="text" class="form-control" name="unitName-Search"
									id="unitName-Search">
								<label for="identityNo" class="control-label">新门海单位上传状态<strong>
										:</strong></label><select id="xmhStatus-Search" name="xmhStatus-Search" class="form-control">
											<option value="">--请选择--</option>
											<option value="0">未上传</option>
											<option value="1">已上传</option>
										</select>
								<label for="identityNo" class="control-label">安讯单位上传状态<strong>
										:</strong></label><select id="axStatus-Search" name="axStatus-Search" class="form-control">
											<option value="">--请选择--</option>
											<option value="0">未上传</option>
											<option value="1">已上传</option>
										</select>
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
									<option value="unitName-Search">单位名称</option>
									<option value="xmhStatus-Search">新门海单位上传状态</option>
									<option value="axStatus-Search">安讯单位上传状态</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button class="btn btn-new uploadAllXMHUnit">
								<i class="fa fa-upload"></i>上传所有未上传单位至新门海
							</button>
							<button class="btn btn-new uploadAllAXUnit">
								<i class="fa fa-upload"></i>上传所有未上传单位至安讯
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
	<div class="modal fade" tabindex="-1" role="dialog" id="netdevice">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<button class="btn btn-new uploadAllXMHDevice" style="margin-bottom: 10px;">
									<i class="fa fa-upload"></i>上传所有未上传设备至新门海
								</button>
								<button class="btn btn-new uploadAllAXDevice" style="margin-bottom: 10px;">
									<i class="fa fa-upload"></i>上传所有未上传设备至安讯
								</button>
								<table class="table table-bordered" id="netdeviceTable">
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	<!-- 员工查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="equipment">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<button class="btn btn-new uploadAllXMHEquipment" style="margin-bottom: 10px;">
									<i class="fa fa-upload"></i>上传所有未上传监控点至新门海
								</button>
								<div class="">
									<table class="table table-bordered" id="equipmentTable">
									</table>
								</div>
							</div>
						</div>
					</div>
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
<script src="/js/page_js/uploadUnit/uploadUnit.js"></script>
</html>