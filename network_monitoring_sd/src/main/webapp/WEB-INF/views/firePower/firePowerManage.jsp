<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>消防力量管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/Employee_management.css">
<script type="text/javascript"
	src="http://api.map.baidu.com/getscript?v=2.0&ak=uAVQruFnlAevcIBVA89lt02GH5kLkUXd&services="></script>
</head>
<body>

	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">消防力量</span></li>
						<li class="no_active"><span class="title">消防力量管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none"
						clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">名称<strong>
										:</strong></label> <input type="text" class="form-control" name="firePowerName"
									id="firePowerName">
								<label for="identityNo" class="control-label">消防类型<strong>
										:</strong></label><select name="type"
												id="type" class="form-control">
													<option value="">全部</option>
													<option value="0">单位微型消防站</option>
													<option value="1">消防中队</option>
													<option value="6">消防大队</option>
													<option value="7">消防支队</option>
													<option value="8">职业消防队</option>
													<option value="2">公共消防站</option>
													<option value="3">室外消火栓</option>
													<option value="4">消防水鹤</option>
													<option value="5">湖泊/池塘/消防水源</option>
											</select>
								<label for="identityNo" class="control-label">单位名称<strong>
										:</strong></label> <input type="text" class="form-control" name="unitName"
									id="unitName">
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
									<option value="firePowerName">名称</option>
									<option value="type">消防类型</option>
									<option value="unitName">单位名称</option>
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
		<div class="modal-dialog" role="document" id="firstModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增消防力量</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">消防类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newFirePower-type"
												id="newFirePower-type" class="form-control inputNormal"
												onchange="typefunction()">
													<option value="0">单位微型消防站</option>
													<option value="1">消防中队</option>
													<option value="6">消防大队</option>
													<option value="7">消防支队</option>
													<option value="8">职业消防队</option>
													<option value="2">公共消防站</option>
													<option value="3">室外消火栓</option>
													<option value="4">消防水鹤</option>
													<option value="5">湖泊/池塘/消防水源</option>
											</select></td>
											<td class="text-right belongdd">所属大队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left belongdd"><select name="newFirePower-belongdd"
												id="newFirePower-belongdd" class="form-control inputNormal" >
											</select></td>
											<td class="text-right belongzd">所属支队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left belongzd"><select name="newFirePower-belongzd"
												id="newFirePower-belongzd" class="form-control inputNormal" >
											</select></td>
										</tr>
										<tr>
											<td class="text-right password">所属单位<font
												style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left password"><select
												name="newFirePower-unitid" id="newFirePower-unitid"
												class="form-control inputNormal">
													<!-- <option value=""></option> -->
											</select></td>
											<td class="text-right">名称<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-name">
											</td>
										</tr>
										<tr>
											<td class="text-right">联系人<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-contact">
											</td>
											<td class="text-right">联系电话<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-phone">
											</td>
										</tr>
										<tr>
											<td class="text-right">经度<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-pointX">
											</td>
											<td class="text-right">纬度<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-pointY"><a
												href="#" onclick="showMap()">标点</a></td>
										</tr>
										<tr>
											<td class="text-right">详细地址<font style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePower-address">
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													id="newFirePower-remark" class="form-control inputNormal"></textarea>
											</td>
										</tr>
										<tr>
											<td class="text-right">照片<strong> :</strong>
											<td class="text-left">
												<input id="photoCover" type="text" style="display: none;">
		                                        <div class="wrapTypeMore">
		                                            <input id="picurls" name="imageDataList" type="file"
		                                                   class="file typeFileMore" onclick="checkPhotoNum()"
		                                                   value="" onchange="doChangeProject()"/>
		                                            <ul id="loadImg" class="loadImg">
		                                                <li class="modalImg"><span>请选择上传图片</span></li>
		                                            </ul>
		                                        </div>
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
		<div class="modal-dialog" role="document" id="firstViewModal">
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
											<td class="text-right">消防类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select
												name="newFirePowerView-type" id="newFirePowerView-type"
												class="form-control inputNormal" disabled="disabled">
													<option value="0">单位微型消防站</option>
													<option value="1">消防中队</option>
													<option value="6">消防大队</option>
													<option value="7">消防支队</option>
													<option value="8">职业消防队</option>
													<option value="2">公共消防站</option>
													<option value="3">室外消火栓</option>
													<option value="4">消防水鹤</option>
													<option value="5">湖泊/池塘/消防水源</option>
											</select></td>
											<td class="text-right belongddView">所属大队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left belongddView"><select name="newFirePower-belongdd"
												id="newFirePowerView-belongdd" class="form-control inputNormal" disabled="disabled">
											</select></td>
											<td class="text-right belongzdView">所属支队<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left belongzdView"><select name="newFirePower-belongzd"
												id="newFirePowerView-belongzd" class="form-control inputNormal" disabled="disabled">
											</select></td>
										</tr>
										<tr>
											<td class="text-right password">所属单位<font
												style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left password"><select
												name="newFirePowerView-unitid" id="newFirePowerView-unitid"
												class="form-control inputNormal" disabled="disabled">
													<!-- <option value=""></option> -->
											</select></td>
											<td class="text-right">名称<font
												style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePowerView-name"
												readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">联系人<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePowerView-contact" readonly="readonly">
											</td>
											<td class="text-right">联系电话<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newFirePowerView-phone" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">详细地址<font
												style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFirePowerView-address" readonly="readonly"></td>
											<td class="text-right">经度<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFirePowerView-pointX" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">纬度<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newFirePowerView-pointY" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													id="newFirePowerView-remark"
													class="form-control inputNormal" readonly="readonly"></textarea></td>
										</tr>
										<tr>
											<td class="text-right">照片<strong> :</strong>
											<td class="text-left">
	                                            <ul id="loadImgView" class="loadImg">
	                                                <li class=""></li>
	                                            </ul>
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
	<!-- 地图模态框（Modal） -->
	<div class="modal fade notLastModal" id="mapModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">地图标点</h4>
				</div>
				<div class="modal-body" id="maps" style="height: 400px;">
					在这里添加一些文本</div>
				<div class="modal-footer">
					<input type="text" id="map_search" placeholder="请输入地点">
					<button type="button" class="btn btn-primary btn-search" >定位
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<!-- 					<button type="button" class="btn btn-primary">确定</button> -->
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/firePower/firePowerManage.js"></script>
</html>