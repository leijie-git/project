<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>设备类型管理</title>
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
						<li class="no_active"><span class="title">设备类型管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">类型名称<strong>
										:</strong></label> <input type="text" class="form-control" name="classname"
									id="classname">
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
									<option value="classname">类型名称</option>
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
					<h4 class="modal-title">新增设备类型</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
									   <tr>
                                            <td class="text-right">系统类型<font style="color: red">*</font><strong>
                                                    :</strong>
                                            </td>
                                            <td>
	                                            <select class="form-control inputNormal"   id="newEqClass-eqsystemid">
	                                                <option value="">--请选择--</option>
	                                            </select>
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">类型编号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-classcode" >
											</td>
										</tr>
										<tr>
											<td class="text-right">类型名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-classname" >
											</td>
										</tr>
										<!-- <tr>
											<td class="text-right" >类型分类<strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newEqClass-type" id="newEqClass-classtype" class="form-control inputNormal" >
												<option value="101">故障电弧</option>
												<option value="102">漏电</option>
												<option value="103">电缆温度异常</option>
												<option value="201">火</option>
												<option value="301">水位</option>
												<option value="302">水压</option>
												<option value="303">泵</option>
											</select>
											</td>
											<td class="text-right">部件编号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-areacode" >
											</td>
										</tr>
										<tr>
											<td class="text-right">监测量的个数<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-monitornum" >
											</td>
											<td class="text-right">部件状态<strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newEqClass-status" id="newEqClass-status" class="form-control inputNormal">
												<option value="1">正常</option>
												<option value="10">报警</option>
												<option value="20">敌障</option>
												<option value="99">其他</option>
											</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-remark" >
											</td>
											<td class="text-right">维保周期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-cycle" >
											</td>
										</tr>
										<tr>
											<td class="text-right">维保周期单位<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-cycleunit" >
											</td>
											<td class="text-right">维保频率<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-frequency" >
											</td>
										</tr>
										<tr>
											<td class="text-right">周期(设备)<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-cycleinfo" >
											</td>
											<td class="text-right">最小周期(设备)<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-mincycleinfo" >
											</td>
										</tr>
										<tr>
											<td class="text-right">巡检周期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-inspectcycle" >
											</td>
											<td class="text-right">巡检周期单位<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-inspectcycleunit" >
											</td>
										</tr>
										<tr>
											<td class="text-right">巡检频率<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClass-inspectfrequency" >
											</td>
											<td class="text-right password">系统名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><select name="newEqClass-eqsystemid" id="newEqClass-eqsystemid"  class="form-control inputNormal">
													<option value=""></option>
                                                </select>
											</td>
										</tr>
										<tr>
                                            <td class="text-right">图片（CRT）<strong> :</strong>
											<td class="text-left">
												<input id="photoCover" type="text" style="display: none;">
		                                        <div class="wrapTypeMore">
		                                            <input id="picurls" name="imageDataList" type="file"
		                                                   class="file typeFileMore"
		                                                   value="" onchange="doChangeProject()"/>
		                                            <ul id="loadImg" class="loadImg">
		                                                <li class="modalImg"><span>请选择上传图片</span></li>
		                                            </ul>
		                                        </div>
											</td>
                                        </tr> -->
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
		<div class="modal-dialog" role="document" id="secondModal">
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
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
									   <tr>
                                            <td class="text-right">系统类型<font style="color: red">*</font><strong>
                                                    :</strong>
                                            </td>
                                            <td>
	                                            <select class="form-control inputNormal" id="newEqClassView-eqsystemid" disabled="disabled">
	                                                <option value="">--请选择--</option>
	                                            </select>
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">类型编号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClassView-classcode" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">类型名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEqClassView-classname" readonly="readonly">
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
<script src="/js/page_js/device/eqClass.js"></script>
</html>