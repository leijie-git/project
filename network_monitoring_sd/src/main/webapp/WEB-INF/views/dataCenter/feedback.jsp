<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>反馈管理</title>
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
						<li class="is_active"><span class="title">用户管理</span></li>
						<li class="no_active"><span class="title">反馈管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">电话<strong>
										:</strong></label> <input type="text" class="form-control" name="phone"
									id="phone">
							</div>
							<div class="form-group">
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
									<option value="phone">电话</option>
									<option value="content">内容</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
<!-- 							<button class="btn btn-new newAdd"> -->
<!-- 								<i class="fa fa-plus"></i>新增 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-new import"> -->
<!-- 								<i class="fa fa-plus"></i>导入 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-danger btnDeleteMany"> -->
<!-- 								<i class="fa fa-trash"></i>批量删除 -->
<!-- 							</button> -->
						</div>
					</div>
					<div id="parentForm">
						<table class="table table-bordered" id="feedbackTable">

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script src="/js/page_js/dataCenter/feedback.js"></script>
</html>