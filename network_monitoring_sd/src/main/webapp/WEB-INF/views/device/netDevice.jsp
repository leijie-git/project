<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>联网设备管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@include file="../inc.jsp"%>
	<link rel="stylesheet"
		  href="/css/page_css/Employee_management.css">
	<script type="text/javascript"
			src="http://api.map.baidu.com/getscript?v=2.0&ak=uAVQruFnlAevcIBVA89lt02GH5kLkUXd&services="></script>
	<style>
		.pumpPort .displayInline{
			display: inline-block;
			margin: 1%;
		}
		.pumpPortView .displayInline{
			display: inline-block;
			margin: 1%;
		}
		.smokeControlPort .displayInline{
			display: inline-block;
			margin: 1%;
		}
		.smokeControlPortView .displayInline{
			display: inline-block;
			margin: 1%;
		}
		.Width14{
			width: 14%;
		}
		.Width31{
			width: 31%;
		}
		.closePort{
			margin-left: 97%;
			cursor: pointer;
			color: #ccc;
		}
		.oneKeyFilling{
			position: absolute;
			top: 30px;
			right: 73px;
		}
		.hide{
			display: none;
		}
		.modal{overflow:auto!important;}
		.fixed-table-body th{
			text-align: center;
		}
		.fixed-table-body{
			overflow: scroll;
			max-height: 550px;
			margin-top: 10px;
		}

		#div123 ul{
			position: absolute;
			top: 32px;
			background: #fff;
			border: 1px solid #ccc;
			min-width: 127px;
			padding: 0 12px;
			left: -1px;
		}
		#div123 ul li:hover{
			color: #fff;
			background: blue;
		}
	</style>
</head>
<body>

<div class="container jy_wrap">
	<div class="row">
		<div class="col-md-12">
			<div class="jy_wrapper">
				<ul class="jy_title">
					<li class="is_active"><span class="title">设备管理</span></li>
					<li class="no_active"><span class="title">联网设备管理</span></li>
				</ul>
				<div class="jy_mainTile panel-heading" style="display: none"
					 clickNo="0">
					<div class="form-inline">
						<div class="form-group">
							<label for="identityNo" class="control-label">设备编号<strong>
								:</strong></label> <input type="text" class="form-control" name="ownercode"
														  id="ownercode">
							<label for="identityNo" class="control-label">设备类型<strong>
								:</strong></label><select id="deviceIndex" name="deviceIndex" class="form-control">
							<option value="">全部</option>
							<option value="1">主机</option>
							<option value="2">用户传输装置</option>
							<option value="3">数据传输模块(数字量、模拟量)</option>
						</select>
							<label for="identityNo" class="control-label">设备子号<strong>
								:</strong></label> <input type="text" class="form-control" name="deviceNo"
														  id="deviceNo">
							<label for="identityNo" class="control-label">设备名称<strong>
								:</strong></label> <input type="text" class="form-control" name="deviceName"
														  id="deviceName">
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
								<option value="ownercode">设备编号</option>
								<option value="deviceIndex">设备类型</option>
								<option value="deviceNo">设备子号</option>
								<option value="deviceName">设备名称</option>
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
						<button class="btn btn-new import">
							<i class="fa fa-plus"></i>导入
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
				<h4 class="modal-title">新增联网设备</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<div class="">
								<button type="button" class="btn btn-new btn-xs cBtn-main bindNumber" style="position: absolute;top: 80px;right: 45px;">
									<i class="fa fa-pencil"></i>&nbsp;绑定编号
								</button>
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select name="newnetDevice-unitid"
																	  id="newnetDevice-unitid"
																	  class="form-control inputNormal unitid">
											<option selected="selected" value="">---搜索单位名称---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">设备编号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left">
											<select class="form-control inputNormal" id="newnetDevice-ownercode" name="newnetDevice-ownercode">

											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">设备类型<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												name="newnetDevice-deviceindex"
												id="newnetDevice-deviceindex"
												class="form-control inputNormal" >
											<option selected="selected" value="">---请选择---</option>
											<option value="1">主机</option>
											<option value="2">用户传输装置</option>
											<option value="3">数据传输模块(数字量、模拟量)</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">系统类型<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												class="form-control inputNormal" id="newnetDevice-eqsystemid"
												name="newnetDevice-eqsystemid">
											<option selected="selected" value="">---请选择---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">设备型号<strong>
											:</strong>
										</td>
										<td class="text-left"><select
												class="form-control inputNormal equipmentmodel" id="newnetDevice-equipmentmodel"
												name="newnetDevice-equipmentmodel">
											<option selected="selected" value="">---搜索设备型号---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">设备子号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal" id="newnetDevice-deviceno" placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')">
										</td>
									</tr>
									<tr>
										<td class="text-right">设备名称<font style="color: red">*</font><strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal" id="newnetDevice-name">
										</td>
									</tr>
									<tr>
										<td class="text-right">独立上线<font style="color: red">*</font><strong> :</strong>
										</td>
										<td class="text-left">
											<div class="needCheck inputNormal">
												<input type="radio" name="newnetDevice-isIndependent" value="1"
													   id="newnetDevice-isIndependent1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="newnetDevice-isIndependent" value="0"
													   id="newnetDevice-isIndependent0" checked="checked">否
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
					<i class="fa fa-floppy-o"></i>&nbsp;保存并下一步
				</button>
				<!-- <button type="button" class="btn btn-primary" id="continue">
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

<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
	<div class="modal-dialog" role="document" id="secondViewModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看联网设备</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												name="newnetDeviceView-unitid" id="newnetDeviceView-unitid"
												class="form-control inputNormal unitid"></select></td>
									</tr>
									<tr>
										<td class="text-right">设备编号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input
												class="form-control inputNormal"
												onKeyUp="value=value.replace(/[\W]/g,'')"
												id="newnetDeviceView-ownercode"
												name="newnetDeviceView-ownercode" disabled="disabled"></td>
									</tr>
									<tr>
										<td class="text-right">设备类型<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												name="newnetDeviceView-deviceindex"
												id="newnetDeviceView-deviceindex"
												class="form-control inputNormal" disabled="disabled">
											<option value="1">主机</option>
											<option value="2">用户传输装置</option>
											<option value="3">数据传输模块(数字量、模拟量)</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">系统类型<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												class="form-control inputNormal" id="newnetDeviceView-eqsystemid"
												name="newnetDeviceView-eqsystemid" disabled="disabled">
											<option selected="selected" value="">---请选择---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">设备型号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												class="form-control inputNormal " id="newnetDeviceView-equipmentmodel"
												name="newnetDevice-equipmentmodel" disabled="disabled">
											<option selected="selected" value="">---请选择---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">设备子号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal"
																	 id="newnetDeviceView-deviceno" readonly="readonly"></td>
									</tr>
									<tr>
										<td class="text-right">设备名称<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal" id="newnetDeviceView-name" readonly="readonly">
										</td>
									</tr>
									<tr>
										<td class="text-right">独立上线<font style="color: red">*</font><strong> :</strong>
										</td>
										<td class="text-left">
											<div class="needCheck inputNormal">
												<input type="radio" name="newnetDeviceView-isIndependent" value="1"
													   id="newnetDeviceView-isIndependent1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="newnetDeviceView-isIndependent" value="0"
													   id="newnetDeviceView-isIndependent0" checked="checked">否
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

<!-- 导入模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="addSite">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">导入联网设备</h4>
			</div>
			<div class="form-inline" style="margin-bottom:10px;margin-top:10px;">
				<div align="center" style="margin-bottom:10px;margin-top:10px;margin-right:10px;" >
					单位名称： <span id="commonInput"><select name="unitid" id="unitid" class="form-control inputNormal unitid" ></select></span>
				</div>
				<!-- <div style="margin-bottom:10px;margin-top:10px;margin-right:10px;">
                    <button type="button" class="btn btn-close SiteInto"
                        data-dismiss="modal" >
                        <i class="fa fa-floppy-o"></i>&nbsp;确认导入
                    </button>
                </div> -->
			</div>
			<table class="table table-bordered" id="mainSiteTable">

			</table>
			<div align="center" style="margin-bottom:10px;margin-top:10px;margin-right:10px;" ><button type="button" class="btn btn-primary SiteInto"
			>
				<i class="fa fa-floppy-o"></i>&nbsp;确认导入
			</button></div>
		</div>
	</div>
</div>

<!-- 关联网关模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="gateway">
	<div class="modal-dialog" role="document" id="wrapModalView">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">关联网关</h4>
			</div>
			<div class="modal-body">
				<div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
					<h3 style="line-height: 16px;text-align: center;font-size: 14px">未关联网关</h3>
					<div class="form-inline">
						<div class="form-group" style="float:left;margin-bottom:5px;">
							<label for="questionName1">网关ID<strong> :</strong></label>
							<input type="text" class="form-control" name="partcode1" id="partcode1">
						</div>
						<button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary searchGateWay1"><i class="fa fa-search"></i>&nbsp;查询
						</button>
						<button  style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary addGateWay"><i class="fa fa-plus"></i>&nbsp;关联
						</button>
					</div>
					<table class="table table-bordered" id="UnrelatedList" data-click-to-select="true">
					</table>
				</div>
				<div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
					<h3 style="line-height: 16px;text-align: center;font-size: 14px">已关联网关</h3>
					<div class="form-inline">
						<div class="form-group" style="float:left;margin-bottom:5px;">
							<label for="questionName2">网关ID<strong> :</strong></label>
							<input type="text" class="form-control" name="partcode2" id="partcode2">
						</div>
						<button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary searchGateWay2"><i class="fa fa-search"></i>&nbsp;查询
						</button>
						<button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary delGateWay"><i class="fa fa-trash"></i>&nbsp;移除
						</button>
					</div>
					<table class="table table-bordered" id="AssociatedList" data-click-to-select="true">
					</table>
				</div>
			</div>
			<div class="modal-footer">
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 添加接口模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="addAllPort">
	<div class="modal-dialog" role="document" style="width: 1280px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"></h4>
				<select type="text" class="form-control allPortUnit" style="display: none;"></select>
				<input type="text" class="form-control allPortNetDeviceId" style="display: none;">
			</div>
			<div class="modal-body">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<button type="button" class="btn btn-new AddPort">
					<i class="fa fa-plus"></i>&nbsp;新增接口
				</button>
				<div class="fixed-table-body">
					<table class="table table-bordered" style="min-width: 2050px;max-height: 700px;">
						<thead>
						<tr>
							<th style="width:3%"></th>
							<th style="width:7%"><font style="color: red">*</font>所属建筑</th>
							<th style="width:7%"><font style="color: red">*</font>所属区域</th>
							<th style="width:5%"><font style="color: red">*</font>监测位置</th>
							<th style="width:6%"><font style="color: red">*</font>设备设施名称</th>
							<th style="width:6%"><font style="color: red">*</font>系统类型</th>
							<th style="width:6%"><font style="color: red">*</font>设备类型</th>
							<th style="width:7%"><font style="color: red">*</font>端口信号类型</th>
							<th style="width:4%"><font style="color: red">*</font>端口号</th>
							<th style="width:6%">信号名称</th>
							<th style="width:5%"><font style="color: red">*</font>模拟量K值</th>
							<th style="width:5%"><font style="color: red">*</font>模拟量B值</th>
							<th style="width:5%"><font style="color: red">*</font>模拟量上限</th>
							<th style="width:5%"><font style="color: red">*</font>模拟量下限</th>
							<th style="width:5%"><font style="color: red">*</font>模拟量单位</th>
							<th style="width:6%"><font style="color: red">*</font>数字量正常电平</th>
							<th style="width:6%"><font style="color: red">*</font>数字量正常名</th>
							<th style="width:6%"><font style="color: red">*</font>数字量异常名</th>
						</tr>
						</thead>
						<tbody id="portTable233">

						</tbody>
					</table>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary btnAllPort">
						<i class="fa fa-floppy-o"></i>&nbsp;保存
					</button>
					<button type="button" class="btn btn-close btnClose"
							data-dismiss="modal">
						<i class="fa fa-times"></i>&nbsp;关闭
					</button>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
</div>

<!-- 网关查看模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="gateWayView">
	<div class="modal-dialog" role="document" id="secondViewModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看无线设备</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">设备ID<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text" readonly="readonly"
																	 class="form-control inputNormal" id="gatewaycodeView" >
										</td>
									</tr>
									<tr>
										<td class="text-right">二维码编号<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text" readonly="readonly"
																	 class="form-control inputNormal" id="devicecodeView" >
										</td>
									</tr>
									<tr>
										<td class="text-right">安装地点<strong> :</strong>
										</td>
										<td class="text-left"><input type="text" readonly="readonly"
																	 class="form-control inputNormal" id="installaddrView">
										</td>
										<td class="text-left"><input type="text" style="display:none" readonly="readonly"
																	 class="form-control inputNormal" id="newFirePower-pointXView">
										</td>
										<td class="text-left"><input type="text" style="display:none" readonly="readonly"
																	 class="form-control inputNormal" id="newFirePower-pointYView">
									</tr>
									<tr>
										<td class="text-right password">通知电话<strong>
											:</strong>
										</td>
										<td class="text-left password"><input type="text" readonly="readonly"
																			  class="form-control inputNormal" id="notifyphoneView" >
										</td>
									</tr>
									<tr>
										<td class="text-right password">自动拨号<strong>
											:</strong>
										</td>
										<td class="text-left password">
											<span>是</span><input id="AutomaticDialingView1" name="AutomaticDialing" style="disabled=true; vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="1">
											<span>否</span><input id="AutomaticDialingView0" name="AutomaticDialing" style="disabled=true; vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="0">

										</td>
									</tr>
									<tr>
										<td class="text-right password">心跳时间<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left password"><input type="text" readonly="readonly"
																			  class="form-control inputNormal" id="heartbeatView" >
										</td>
									</tr>
								</table>
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
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">新增设备设施</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo">
								<colgroup>
									<col width="13%">
									<col width="23%">
									<col width="10%">
									<col width="22%">
									<col width="10%">
									<col width="22%">
								</colgroup>
								<tr>

									<td class="text-right"><font style="color: red">*</font>所属建筑<strong>
										:</strong></td>
									<td class="text-left"><select id="BuildId"
																  class="form-control inputWidth">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>所属区域<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" id="BuildAreaId">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>监测位置<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" name="Position" id="Position">
									</td>
								</tr>
								<tr class="hide">

									<td class="text-right "><font style="color: red">*</font>系统类型<strong>
										:</strong></td>
									<td class="text-left "><select id="EqSystemCode"
																	   class="form-control inputWidth">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right "><font style="color: red">*</font>设备类型<strong>
										:</strong></td>
									<td class="text-left "><select id="EqClassID"
																	   class="form-control inputWidth">
										<option value="" code="">--请选择--</option>
									</select></td>

									<td class="text-right "><font style="color: red">*</font>联网设备<strong>
										:</strong></td>
									<td class="text-left "><select
											class="form-control inputWidth" id="NetDeviceID">
										<option value="">--请选择--</option>
									</select></td>
								</tr>
								<tr class="hide">
									<td class="text-right "><font style="color: red">*</font>设备设施名称<strong>
										:</strong></td>
									<td class="text-left "><input type="text"
																	  class="form-control inputWidth" id="EqName"></td>
									<td class="text-right "><font style="color: red">*</font>所属单位<strong>
										:</strong></td>
									<td class="text-left "><select
											class="form-control inputWidth" id="UnitIDPort">
										<option value="">--请选择--</option>
									</select></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="SetPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo portTable">
								<colgroup>
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
								</colgroup>

								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="Name0">端口号(电源)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Port0"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="Type0" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="Value0"
																  class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="One0"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Zero0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="BDetailID0">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="ShReserve0"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="Name1">端口号(半降)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Port1"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="Type1" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="Value1"
																  class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="One1"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Zero1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="BDetailID1">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="ShReserve1"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="Name2">端口号(全降)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Port2"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>

									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="Type2" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="Value2"
																  class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="One2"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Zero2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="BDetailID2">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="ShReserve2"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="smokePort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="smokeControlPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(手自动)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<div class="smokeControlPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(启停)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<div class="smokeControlPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(电源)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<button type="button" class="btn btn-new btnAddSmokePort" style="position: absolute;bottom: -13px;right: 48%;">
									<i class="fa fa-plus"></i>&nbsp;新增
								</button>
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="faultPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>

				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo portTable">
								<colgroup>
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
								</colgroup>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="FName">手动端口</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="FPort"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="FType" class="form-control" style="width: 122px;">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="FValue"
																  class="form-control" style="width: 122px;">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="FOne"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="FZero"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="FDetailID">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="ShReserve"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="threePort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo">
								<colgroup>
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
								</colgroup>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="portName0">端口号(IA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TPort0" labport="IA"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="KValue0"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="BValue0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="TopValue0"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="LowValue0"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MNUnit0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" labport="IA" class="form-control inputWidth"
											id="TdetailID0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue0"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MinValue0"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td style="border-bottom: 1px solid red" class="text-left"><input
											type="text" labport="IA" class="form-control inputWidth"
											id="reserve0"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="portName1">端口号(IB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TPort1"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="KValue1"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="BValue1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TopValue1"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="LowValue1"></td>

									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="MNUnit1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="TdetailID1">
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue1"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MinValue1"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="reserve1"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="portName2">端口号(IC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TPort2"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="KValue2"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="BValue2"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TopValue2"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="LowValue2"></td>

									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="MNUnit2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="TdetailID2">
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue2"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MinValue2"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="reserve2"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="fourPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="pumpPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(手自动)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<div class="pumpPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(启停)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<div class="pumpPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(电源)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<div class="pumpPort">
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span class="PortId">端口号(故障)</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdValue"
											   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
											   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<span>数据类型</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<select class="form-control inputWidth PortIdType">
											<option value=""></option>
											<option value="0" selected="selected">数字量</option>
											<option value="1">模拟量</option>
										</select>
									</div>

									<div class="text-right displayInline Width14">
										<span style="color: red;">*</span>
										<span>正常值</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<select class="form-control inputWidth PortIdNormalNum">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select>
									</div>
									<div class="text-right displayInline Width14">
										<span style="color:red">*</span>
										<span>正常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdNormalName">
									</div>
									<div class="text-right displayInline Width14">
										<span style="color: red">*</span>
										<span>异常名</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdAbnormalName">
									</div>

									<div class="text-right displayInline Width14" style="display: none;">
										<span style="color: red">*</span>
										<strong> :</strong>
									</div>
									<div class="text-left displayInline Width31" style="display: none;">
										<input type="text" class="form-control inputWidth PortIdDetailId">
									</div>

									<div class="text-right displayInline Width14">
										<span>信号名称</span>
										<strong>:</strong>
									</div>
									<div class="text-left displayInline Width31">
										<input type="text" class="form-control inputWidth PortIdSignal">
									</div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>
								<button type="button" class="btn btn-new btnAddPort" style="position: absolute;bottom: -13px;   right: 48%;">
									<i class="fa fa-plus"></i>&nbsp;新增
								</button>
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="otherPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo">
								<colgroup>
									<col width="15%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
									<col width="15%">
									<col width="20%">
								</colgroup>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="OtherPortName">端口号</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="Port"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="KValue"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="BValue"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="TopValue"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="LowValue"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="MNUnit"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth" id="OdetailID">
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MinValue"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="reserve"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="ElevenPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
					<button type="button" class="btn btn-new oneKeyFilling" onclick="oneKey();">
						<i class="fa fa-plus"></i>&nbsp;一键填充
					</button>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<table class="table tableNo">
								<colgroup>
									<col width="15%">
									<col width="20%">
									<col width="10%">
									<col width="20%">
									<col width="15%">
									<col width="20%">
								</colgroup>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName0">端口号(IA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort0"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue0"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue0"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue0"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue0"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue0"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve0"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName1">端口号(IB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort1"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue1"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue1"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue1"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue1"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue1"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve1"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName2">端口号(IC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort2"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue2"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue2"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue2"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue2"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID2"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue2"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue2"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve2"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>

								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName3">端口号(UA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort3"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue3"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue3"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue3"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue3"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit3"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID3"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue3"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue3"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve3"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName4">端口号(UB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort4"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue4"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue4"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue4"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue4"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit4"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID4"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue4"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue4"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve4"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName5">端口号(UC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort5"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue5"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue5"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue5"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue5"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit5"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID5"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue5"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue5"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve5"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>

								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName6">端口号(WA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort6"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue6"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue6"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue6"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue6"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit6"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID6"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue6"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue6"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve6"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName7">端口号(WB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort7"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue7"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue7"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue7"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue7"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit7"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID7"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue7"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue7"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve7"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName8">端口号(WC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort8"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue8"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue8"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue8"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue8"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit8"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID8"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue8"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue8"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve8"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName9">端口号(WD)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort9"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue9"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue9"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue9"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue9"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit9"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID9"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue9"></td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue9"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve9"></td>

								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireName10">端口号(漏电流)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="firePort10"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireKValue10"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth" id="fireBValue10"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireTopValue10">
									</td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth" id="fireLowValue10">
									</td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireMNUnit10"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="firedetailID10"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue10">
									</td>
									<td class="text-right"><font style="color: red">*</font>最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMinValue10">
									</td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="fireReserve10"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="customPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<button type="button" class="btn btn-new btAddMNPort">
					<i class="fa fa-plus"></i>&nbsp;新增模拟量端口
				</button>
				<button type="button" class="btn btn-new btAddSZPort">
					<i class="fa fa-plus"></i>&nbsp;新增数字量端口
				</button>
				<table class="table table-bordered" id="portTable">
				</table>
				<div class="container" id="MNPort">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="20%">
										<col width="10%">
										<col width="20%">
										<col width="15%">
										<col width="20%">
									</colgroup>
									<tr>
										<td class="text-left" style="display: none;"><input
												type="text" class="form-control inputWidth"
												id="MNIOPortDetailID"></td>
										<td class="text-right"><font style="color: red">*</font>端口名<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortName"></td>
										<td class="text-right"><font style="color: red">*</font><span
												id="MNIOPort">端口号</span><strong> :</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortValue"
																	 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																	 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
										</td>

										<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortUnit"></td>
									</tr>
									<tr>
										<td class="text-right"><font style="color: red">*</font>上限<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortTopValue">
										</td>
										<td class="text-right"><font style="color: red">*</font>下限<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortLowValue">
										</td>
										<td class="text-right" style="display: block;"><font
												style="color: red">*</font>K值<strong> :</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortKValue">
										</td>
									</tr>
									<tr>
										<td class="text-right"><font style="color: red">*</font>最大值<strong>
											:</strong></td>
										<td class="text-left"><input type="text" labport="IA"
																	 class="form-control inputWidth" id="MNIOPortMaxValue">
										</td>
										<td class="text-right"><font style="color: red">*</font>最小值<strong>
											:</strong></td>
										<td class="text-left"><input type="text" labport="IA"
																	 class="form-control inputWidth" id="MNIOPortMinValue">
										</td>

										<td class="text-right"><font style="color: red">*</font>B值<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortBValue">
										</td>
									</tr>
									<tr>
										<td class="text-right">信号名称<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="MNIOPortReserve">
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<button type="button" style="float: right; margin-right: 20%;"
							class="btn btn-close btnMNPortClose">
						<i class="fa fa-times"></i>&nbsp;关闭
					</button>
					<button type="button" style="float: right; margin-right: 30px;"
							class="btn btn-primary btnAddMNPortSure">
						<i class="fa fa-floppy-o"></i>&nbsp;保存端口
					</button>
					<button type="button" style="float: right; margin-right: 30px;"
							class="btn btn-primary btnUpdateMNPortSure">
						<i class="fa fa-floppy-o"></i>&nbsp;更新端口
					</button>

				</div>
				<div class="container" id="SZPort">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="20%">
										<col width="10%">
										<col width="20%">
										<col width="15%">
										<col width="20%">
									</colgroup>
									<tr>
										<td class="text-left" style="display: none;"><input
												type="text" class="form-control inputWidth"
												id="SZIOPortDetailID"></td>
										<td class="text-right"><font style="color: red">*</font>端口名<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="SZIOPortName"></td>
										<td class="text-right"><font style="color: red">*</font><span
												id="SZIOPorts">端口号</span><strong> :</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="SZIOPort"
																	 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																	 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
										</td>

										<td class="text-right"><font style="color: red">*</font>正常值<strong>
											:</strong></td>
										<td class="text-left"><select id="SZIOPortNormalValue"
																	  class="form-control inputWidth">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right"><font style="color: red">*</font>正常名<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="SZIOPortGoodName">
										</td>
										<td class="text-right"><font style="color: red">*</font>异常名<strong>
											:</strong></td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="SZIOPortBadName">
										</td>
										<td class="text-right">信号名称<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputWidth" id="SZIOPortReserve">
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<button type="button" style="float: right; margin-right: 20%;"
							class="btn btn-close btnSZPortClose">
						<i class="fa fa-times"></i>&nbsp;关闭
					</button>
					<button type="button" style="float: right; margin-right: 30px;"
							class="btn btn-primary btnAddSZPortSure">
						<i class="fa fa-floppy-o"></i>&nbsp;保存端口
					</button>
					<button type="button" style="float: right; margin-right: 30px;"
							class="btn btn-primary btnUpdateSZPortSure">
						<i class="fa fa-floppy-o"></i>&nbsp;更新端口
					</button>

				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary btnSurePort">
					<i class="fa fa-floppy-o"></i>&nbsp;保存
				</button>
				<button type="button" class="btn btn-close btnClose"
						data-dismiss="modal">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 单位编号绑定模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="bindNumber">
	<div class="modal-dialog" role="document" id="secondModal">
		<div class="modal-content" style="width: 600px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">绑定编号</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-left">绑定编号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="soureaddress" maxlength="12"></td>
										<td class="text-left"><button type="button" class="btn btn-primary"
																	  onclick="bind()">
											</i>&nbsp;保存
										</button></td>
									</tr>
									<!-- 通知接收人 -->
									<table class="table table-bordered baseInfoRelTable" id="baseInfoRelTable"
										   style="width: 95%;">

									</table>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close">
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

<!-- 保存单位编号 -->
<input type="hidden" id="unitId">

</body>
<script src="/js/page_js/device/netDevice.js"></script>
</html>