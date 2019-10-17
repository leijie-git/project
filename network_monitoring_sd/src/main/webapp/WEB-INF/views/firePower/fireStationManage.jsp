<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>消防站设备管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/Employee_management.css">
</head>
<body>

	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">消防力量</span></li>
						<li class="no_active"><span class="title">消防站设备管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none"
						clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">消防站名称<strong>
										:</strong></label> <input type="text" class="form-control"
									name="fireStationName" id="fireStationName">
								<label for="identityNo" class="control-label">设备名称<strong>
										:</strong></label> <input type="text" class="form-control"
									name="name" id="name">
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
									<option value="fireStationName">消防站名称</option>
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
					<h4 class="modal-title">新增消防站设备信息</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">消防站类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newFireStation-type"
												id="newFireStation-type" class="form-control inputNormal"
												onchange="fireStationType()">
													<option value="" selected="selected">---请选择---</option>
													<option value="0">单位微型消防站</option>
													<option value="2">公共消防站</option>
													<option value="8">职业消防队</option>
											</select></td>
											<td class="text-right">消防站<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select
												name="newFireStation-powerid" id="newFireStation-powerid"
												class="form-control inputNormal">
													<option value="" selected="selected">---请选择---</option>
											</select></td>
										</tr>
										<tr>
											<td class="text-right">设备名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFireStation-name">
											</td>
											<td class="text-right">水枪<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-waterGun"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">水带<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-waterBelt"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">安全绳<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-safetyRope"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">消火栓扳手<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-hydrantWrench"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">灭火器<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-fireExtinguisher"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">强光照明灯<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-brightLight"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">消防斧<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFireStation-fireAxe"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">消防头盔<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-fireHelmet"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">消防员灭火防护服<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-clothing"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">消防员灭火防护靴<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFireStation-boots"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">消防安全腰带<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-safetyBelt"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">消防手套<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-fireGloves"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">消防过滤式综合防毒面具<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-integratedRespirator"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">扩音器<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-loudspeaker"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
											<td class="text-right">消防巡逻车<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStation-patrolCar"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量"></td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													class="form-control inputNormal" id="newFireStation-remark"></textarea>
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
											<td class="text-right">消防站类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select
												name="newFireStationView-type" id="newFireStationView-type"
												class="form-control inputNormal"
												onchange="fireStationType()" disabled="disabled">
													<option value="" selected="selected">---请选择---</option>
													<option value="0">单位微型消防站</option>
													<option value="2">公共消防站</option>
											</select></td>
											<td class="text-right">消防站<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select
												name="newFireStationView-powerid"
												id="newFireStationView-powerid"
												class="form-control inputNormal" disabled="disabled">
													<option value="" selected="selected">---请选择---</option>
											</select></td>
										</tr>
										<tr>
											<td class="text-right">设备名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-name" readonly="readonly"></td>
											<td class="text-right">水枪<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-waterGun"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">水带<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-waterBelt"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">安全绳<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-safetyRope"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">消火栓扳手<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-hydrantWrench"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">灭火器<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-fireExtinguisher"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">强光照明灯<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-brightLight"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">消防斧<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-fireAxe"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">消防头盔<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-fireHelmet"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">消防员灭火防护服<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-clothing"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">消防员灭火防护靴<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-boots"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">消防安全腰带<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-safetyBelt"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">消防手套<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-fireGloves"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">消防过滤式综合防毒面具<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-integratedRespirator"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">扩音器<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-loudspeaker"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
											<td class="text-right">消防巡逻车<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFireStationView-patrolCar"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												placeholder="请输入数量" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													class="form-control inputNormal"
													id="newFireStationView-remark" readonly="readonly"></textarea>
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
<script src="/js/page_js/firePower/fireStationManage.js"></script>
</html>