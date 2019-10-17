<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>消防中队配置</title>
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
						<li class="is_active"><span class="title">消防力量</span></li>
						<li class="no_active"><span class="title">消防中队配置</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">消防中队<strong>
										:</strong></label> <input type="text" class="form-control" name="powerName"
									id="powerName">
								<label for="identityNo" class="control-label">设备名称<strong>
										:</strong></label> <input type="text" class="form-control" name="name"
									id="name">
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
									<option value="powerName">消防中队</option>
									<option value="name">设备名称</option>
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
	<!-- 新增消防力量模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmploy">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增消防中队配置信息</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">消防中队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<select name="newSquadron-powerId" id="newSquadron-powerId" class="form-control inputNormal" >
													<option value="">---请选择---</option>
												</select>
											</td>
											<td class="text-right">设备名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-name" >
											</td>
										</tr>
										<tr>
											<td class="text-right">高喷消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-highSpray" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
											<td class="text-right">水罐消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-waterTank" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
										</tr>
										<tr>
											<td class="text-right">泡沫消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-foam" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
											<td class="text-right">干粉消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-dryPowder" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
										</tr>
										<tr>
											<td class="text-right">云梯消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-ladderLadder" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
											<td class="text-right">A类泡沫消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-aFoam" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
										</tr>
										<tr>
											<td class="text-right">抢险救援车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-rescueVehicle" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
											<td class="text-right">登高平台消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-climbingPlatform" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
										</tr>
										<tr>
											<td class="text-right">正负压排烟消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-pressureSmoke" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
											<td class="text-right">作战人员<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadron-combatants" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量">
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong>
													:</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols="" class="form-control inputNormal" id="newSquadron-remark"></textarea>
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
	
	<!-- 员工查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看消防设施</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">消防中队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<select name="newSquadronView-powerId" id="newSquadronView-powerId" class="form-control inputNormal" disabled="disabled">
													<option value="">---请选择---</option>
												</select>
											</td>
											<td class="text-right">设备名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-name" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">高喷消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-highSpray" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
											<td class="text-right">水罐消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-waterTank" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">泡沫消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-foam" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
											<td class="text-right">干粉消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-dryPowder" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">云梯消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-ladderLadder" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
											<td class="text-right">A类泡沫消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-aFoam" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">抢险救援车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-rescueVehicle" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
											<td class="text-right">登高平台消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-climbingPlatform" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">正负压排烟消防车<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-pressureSmoke" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
											<td class="text-right">作战人员<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newSquadronView-combatants" onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong>
													:</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols="" class="form-control inputNormal" id="newSquadronView-remark" readonly="readonly"></textarea>
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
<script src="/js/page_js/firePower/squadron.js"></script>
</html>