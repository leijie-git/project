<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>灭火器管理</title>
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
						<li class="is_active"><span class="title">设备管理</span></li>
						<li class="no_active"><span class="title">灭火器管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none"
						clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">灭火器类型<strong>
										:</strong></label> <input type="text" class="form-control"
									name="extinguisherType" id="extinguisherType"> <label
									for="identityNo" class="control-label">灭火器位置<strong>
										:</strong></label> <input type="text" class="form-control"
									name="extinguisherPosition" id="extinguisherPosition">
								<label for="identityNo" class="control-label">生产单位名称<strong>
										:</strong></label> <input type="text" class="form-control"
									name="productUnitName" id="productUnitName">
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
		<div class="row" id="equipment">
			<div class="col-md-12">
				<div class="dataTable">
					<div class="jy_new" style="top: 20px;">
						<div class="form-inline">
							<div class="form-group">
								<select name="menuType" id="menuType" class="form-control">
									<option value="extinguisherType">灭火器类型</option>
									<option value="extinguisherPosition">灭火器位置</option>
									<option value="productUnitName">生产单位名称</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button type="button" class="btn btn-primary moreCode" data-toggle="modal">
								<i class="fa fa-eye"></i>&nbsp;批量生成二维码
							</button>

							<c:if test="${userSession.unitId != null and userSession.unitId != ''}">
								<button class="btn btn-new newAdd">
									<i class="fa fa-plus"></i>新增
								</button>
								<button class="btn btn-new import" onclick="addressExport()">
									<i class="fa fa-plus"></i>导入
								</button>
							</c:if>
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
	<!-- 新增灭火器模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEquipment">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增灭火器</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<!-- 										<tr> -->
										<!-- 											<td class="text-right">单位<font style="color: red">*</font><strong> -->
										<!-- 													:</strong> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-left"><select name="newEquipment-unitID" id="newEquipment-unitID" class="form-control inputNormal"> -->
										<!-- 											</select> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-right">建筑<font style="color: red">*</font><strong> -->
										<!-- 													:</strong> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-left password"><select name="newEquipment-buildID" id="newEquipment-buildID" class="form-control inputNormal"> -->
										<!-- 											</select> -->
										<!-- 											</td> -->
										<!-- 										</tr> -->
										<tr>
											<td class="text-right">灭火剂类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<select id="newEquipment-extinguisherType" name="newEquipment-extinguisherType"
												class="form-control inputNormal">
													<%--<option value=""></option>--%>
												</select>
											</td>
											<td class="text-right">灭火器编号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipment-extinguisherCode"
												class="form-control inputNormal"></td>
										</tr>
										<tr>
											<td class="text-right">灭火器位置<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipment-extinguisherPosition"
												class="form-control inputNormal"></td>
											<td class="text-right">出厂时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipment-productDate"
												class="form-control inputNormal" readonly="readonly">
											</td>

										</tr>
										<tr>
											<td class="text-right">填充时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEquipment-fillDate"
												readonly="readonly"></td>
											<td class="text-right">检测时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newEquipment-jcDate"
												readonly="readonly"></td>

										</tr>
										<tr>
											<td class="text-right">报废时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipment-validityDate" readonly="readonly">
											</td>
											<td class="text-right">药剂到期时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipment-expireDate" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">生产单位名称<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipment-productUnitname"></td>
											<td class="text-right">生产单位电话 <strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipment-productUnitphone"></td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													class="form-control inputNormal" id="newEquipment-remark"></textarea>
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
					<!-- 					<button type="button" class="btn btn-primary" id="continue" > -->
					<!-- 						<i class="fa fa-floppy-o"></i>&nbsp;保存并继续 -->
					<!-- 					</button> -->
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

	<!-- 灭火器查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog"
		id="newEquipmentView">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看灭火器</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<!-- 										<tr> -->
										<!-- 											<td class="text-right">单位<font style="color: red">*</font><strong> -->
										<!-- 													:</strong> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-left"><select name="newEquipmentView-unitID" id="newEquipmentView-unitID" class="form-control inputNormal"> -->
										<!-- 											</select> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-right">建筑<font style="color: red">*</font><strong> -->
										<!-- 													:</strong> -->
										<!-- 											</td> -->
										<!-- 											<td class="text-left password"><select name="newEquipmentView-buildID" id="newEquipmentView-buildID" class="form-control inputNormal"> -->
										<!-- 											</select> -->
										<!-- 											</td> -->
										<!-- 										</tr> -->
										<tr>
											<td class="text-right">灭火剂类型<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipmentView-extinguisherType"
												class="form-control inputNormal" readonly="readonly"></td>
											<td class="text-right">灭火器编号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipmentView-extinguisherCode"
												class="form-control inputNormal" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">灭火器位置<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipmentView-extinguisherPosition"
												class="form-control inputNormal" readonly="readonly"></td>
											<td class="text-right">出厂时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												id="newEquipmentView-productDate"
												class="form-control inputNormal" readonly="readonly">
											</td>

										</tr>
										<tr>
											<td class="text-right">填充时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-fillDate" readonly="readonly">
											</td>
											<td class="text-right">检测时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-jcDate" readonly="readonly"></td>

										</tr>
										<tr>
											<td class="text-right">报废时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-validityDate" readonly="readonly">
											</td>
											<td class="text-right">药剂到期时间<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-expireDate" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">生产单位名称<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-productUnitname" readonly="readonly"></td>
											<td class="text-right">生产单位电话 <strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal"
												id="newEquipmentView-productUnitphone" readonly="readonly"></td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left"><textarea rows="3" cols=""
													class="form-control inputNormal"
													id="newEquipmentView-remark" readonly="readonly"></textarea></td>
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

	<%--生成二维码页面--%>
	<div class="modal fade" tabindex="-1" role="dialog" id="lookModel">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header" style="width: 980px;margin-bottom: 30px;">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">二维码</h4>
					<div style="display: flex;margin-right: 10px;justify-content: flex-start;align-items: center;">
						<span>字体大小：</span>
						<select class="form-control" style="width: 100px" name="fontSizeChange" id="fontSizeChange" onchange="fontSize();">
							<option value="12">12</option>
							<option value="14">14</option>
							<option value="16">16</option>
							<option value="18">18</option>
							<option value="20">20</option>
						</select>
					</div>
				</div>
				<div id="pic" style="margin-left: 17%"></div>
				<div class="allCode" id="allCode" style="width: 980px;height: 900px;background: #fff;display: flex;flex-wrap: wrap;align-items: flex-start;align-content: flex-start;"></div>
			</div>
		</div>
	</div>

	<!--导入数据-->
	<div class="modal fade" tabindex="-1" role="dialog" id="importBuilding">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">导入</h4>
				</div>
				<div class="modal-body" style="height: auto;">
					<div class="row">
						<div class="col-md-12">
							<form id="projectForm" class="importArea">
								<div>
									<input type="hidden" name="eqid" id="eqid">
									<span class="col-md-2">第一步</span>
									<button type="button" class="btn btn-new downloadModel" onclick="downloadModel();" data-toggle="modal">
										<i class="fa fa-download"></i>&nbsp;下载导入模板
									</button>
								</div>
								<div>
									<p class="col-md-offset-2">请根据模板格式填写内容，然后导入</p>
								</div>
								<div>
									<span class="col-md-2">第二步</span>
									<div class="wrapExport">
										<input type="file" name="uploadExcel"
											onchange="doChange(this)" class="changeInput uploadExcel" />
										<button type="button"
											class="btn btn-new btn-sm btn-new chooseFile">
											<i class="fa fa-arrow-up"></i>&nbsp;上传
										</button>
									</div>
									<span class="fileName"></span>
								</div>
								<div>
									<!--                                    <p class="col-md-offset-2">上传填写好的。</p> -->
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-new btn-sm upTemp btnImport">开始导入</button>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

	<input type="hidden" id="unitId" value="${userSession.unitId }">

</body>

<script src="/js/page_js/device/equipment.js"></script>
<script src="/js/common/qrcode/jquery.qrcode.min.js"></script>
<script src="/js/common/select2.full.min.js"></script>      <%--下拉框搜索插件--%>
<script>
    $(function(){
        $("#newEquipment-extinguisherType").chosen();
        $("#newEquipment_extinguisherType_chosen").css("width","70%");
    })
</script>

</html>

