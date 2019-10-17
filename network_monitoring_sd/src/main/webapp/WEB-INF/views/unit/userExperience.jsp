<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>行业人员管理</title>
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
						<li class="no_active"><span class="title">行业人员管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="name" class="control-label">姓名<strong>
										:</strong></label> <input type="text" class="form-control" name="name"
									id="name">
								<label for="phone" class="control-label">电话<strong>
										:</strong></label> <input type="text" class="form-control" name="phone"
									id="phone">
								<label for="idCard" class="control-label">身份证号<strong>
										:</strong></label> <input type="text" class="form-control" name="idCard"
									id="idCard">
								<label for="cardNumber" class="control-label">证件号<strong>
										:</strong></label> <input type="text" class="form-control" name="cardNumber"
									id="cardNumber">
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
									<option value="name">姓名</option>
									<option value="phone">电话</option>
									<option value="idCard">身份证号</option>
									<option value="cardNumber">证件号</option>
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
	<!-- 新增模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newUserInfo">
		<div class="modal-dialog" role="document" id="firstModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="nameNew">
											</td>
											<td class="text-right">身份证号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="idCardNew">
											</td>
										</tr>
										<tr>
											<td class="text-right">证书类别<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="cardTypeNew">
											</td>
											<td class="text-right">证书编号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="cardNumberNew">
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="startTimeNew" readonly="readonly">
											</td>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="certificateNew">
											</td>
										</tr>
										<tr>
											<td class="text-right">电话<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="phoneNew">
											</td>
											<td class="text-right">性别<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="radio" name="sex" value='1' id="sexNew1" checked="checked">男
		                                    	<input type="radio" name="sex" value='0' id="sexNew0">女
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
	<!-- 查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newUserInfoView">
		<div class="modal-dialog" role="document" id="firstViewModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看行业人员</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="nameView" disabled>
											</td>
											<td class="text-right">身份证号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="idCardView" disabled>
											</td>
										</tr>
										<tr>
											<td class="text-right">证书类别<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="cardTypeView" disabled>
											</td>
											<td class="text-right">证书编号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="cardNumberView" disabled>
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="startTimeView" readonly="readonly">
											</td>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="certificateView" disabled>
											</td>
										</tr>
										<tr>
											<td class="text-right">电话<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="phoneView" disabled>
											</td>
											<td class="text-right">性别<strong>
													:</strong>
											</td>
											<td class="text-left">
												<input type="radio" name="sex" value='1' id="sexView1" checked="checked" disabled>男
		                                    	<input type="radio" name="sex" value='0' id="sexView0" disabled>女
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
	
	<!-- 人员工作经历 -->
    <div class="modal fade" tabindex="-1" role="dialog" id="userExperiece">
        <div class="modal-dialog" role="document" id="wrapModal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">人员工作经历</h4>
                </div>
                 <div id="parentForm1">
                        <table class="table table-bordered" id="experieceTable">

                        </table>
                    </div>
                    <hr>
                <div class="modal-body" id="AddMsg">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-10">
                                <div class="">
                                    <table class="table tableNo">
										<tr>
											<td class="text-right">公司名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-companyName">
											</td>
											<td class="text-right">公司地址<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-companyAddr">
											</td>
										</tr>
										<tr>
											<td class="text-right">职务<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-job">
											</td>
											<td class="text-right">入职时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-entryDate" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">离职时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-quitDate" readonly="readonly">
											</td>
											<td class="text-right">备注<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newUserExperience-remark">
											</td>
										</tr>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group" align="center" style="margin-right: 60px">
                    <button type="button" class="btn btn-primary" onclick="saveExperiece()">
                        <i class="fa fa-floppy-o" ></i>&nbsp;保存
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    
	<input type="hidden" name="indexId" id="indexId" value="">
	
	<input type="hidden" name="indexId" id="indexId1" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/unit/userExperience.js"></script>
</html>