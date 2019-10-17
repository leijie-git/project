<%--
  Created by IntelliJ IDEA.
  User: Kip
  Date: 2017/9/21
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
	<title>设备管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@include file="../inc.jsp"%>
	<link rel="stylesheet" href="/css/page_css/role_management.css">
	<script type="text/javascript"
			src="/js/common/zTree/jquery.ztree.core.js"></script>
	<script type="text/javascript"
			src="/js/common/zTree/jquery.ztree.excheck.js"></script>
	<style>
		/*.pumpPort .displayInline{*/
			/*display: inline-block;*/
			/*margin: 1%;*/
		/*}*/
		/*.pumpPortView .displayInline{*/
			/*display: inline-block;*/
			/*margin: 1%;*/
		/*}*/
		/*.smokeControlPort .displayInline{*/
			/*display: inline-block;*/
			/*margin: 1%;*/
		/*}*/
		/*.smokeControlPortView .displayInline{*/
			/*display: inline-block;*/
			/*margin: 1%;*/
		/*}*/
		.displayInline{
			display: inline-block;
			margin: 1%;
		}
		.Width14{
			width: 14%;
		}
		.Width31{
			width: 31%;
		}
		.Width11{
			width: 11%;
		}
		.Width19{
			width: 19%;
		}
		.Width7{
			width: 7%;
		}
		.Width9{
			width: 9%;
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
        .flowauto{
			overflow: auto;
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
					<li class="no_active"><span class="title">设备设施管理</span></li>
				</ul>

				<div class="jy_mainTile panel-heading" style="display: none"
					 clickNo="0">
					<div class="form-inline">
						<div class="form-group">
							<label for="unitName" class="control-label">单位名称<strong>
								:</strong></label> <input type="text" class="form-control" id="unitName">
							<label for="NdName" class="control-label">联网设备<strong>
								:</strong></label> <input type="text" class="form-control" id="NdName">
							<label for="EQMName" class="control-label">设备设施<strong>
								:</strong></label> <input type="text" class="form-control" id="EQMName">
						</div>
						<div class="form-group">
							<label for="ownercode" class="control-label">设备编号<strong>
								:</strong></label> <input type="text" class="form-control" id="ownercode">
							<label for="EqSystemCodes" class="control-label">系统类型<strong>
								:</strong></label> <select style="width: 190px; height: 32px;"
														   id="EqSystemCodes">
							<option value="">--请选择--</option>
						</select> <label for="EqClassIDs" class="control-label">设备分类<strong>
							:</strong></label> <select style="width: 190px; height: 32px;" id="EqClassIDs">
							<option value="">--请选择--</option>
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
	<div class="row">
		<div class="col-md-12">
			<div class="dataTable">
				<div class="jy_new" style="top: 6px;">
					<div class="form-inline">
						<div class="form-group">
							<select name="menuType" id="menuType" class="form-control">
								<option value="unitName">单位名称</option>
								<option value="NdName">联网设备</option>
								<option value="EQMName">设备设施</option>
								<option value="ownercode">设备编号</option>
							</select> <span id="commonInput"><input type="text"
																	class="form-control showInput" name="searchContent"
																	id="searchContent"></span>


						</div>
						<button type="button" class="btn btn-primary ordinarySearch">
							<i class="fa fa-search"></i>&nbsp;查询
						</button>
						<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>

						<button type="button" class="btn btn-new btnNew"
								data-toggle="modal" data-target='#newAdd'>
							<i class="fa fa-plus"></i>&nbsp;新增
						</button>
					</div>
				</div>
				<div class="equipmentTable">
					<table class="table table-bordered" id="equipmentTable">

					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 新增模态框 -->
<div class="modal fade flowauto" tabindex="-1" role="dialog" id="newAdd" style="overflow-y: auto;">
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
									<td class="text-right"><font style="color: red">*</font>所属单位<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" id="UnitID">
										<option value="">--请选择--</option>
									</select></td>
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
								</tr>
								<tr>

									<td class="text-right"><font style="color: red">*</font>系统类型<strong>
										:</strong></td>
									<td class="text-left"><select id="EqSystemCode"
																  class="form-control inputWidth">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>设备类型<strong>
										:</strong></td>
									<td class="text-left"><select id="EqClassID"
																  class="form-control inputWidth">
										<option value="" code="">--请选择--</option>
									</select></td>

									<td class="text-right"><font style="color: red">*</font>联网设备<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" id="NetDeviceID">
										<option value="">--请选择--</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>设备设施名称<strong>:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="EqName"></td>
									<td class="text-right"><font style="color: red">*</font>监测位置<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" name="Position" id="Position">
									</td>
									<td class="text-right leadingInImg" style="display: none;">点位视频<strong>:</strong></td>
									<td class="text-left leadingInImg" style="display: none;"><select type="text" class="form-control inputWidth" id="pointVideo"></select></td>
								</tr>
								<tr>
									<td class="text-right leadingInImg" style="display: none;">导入图片<strong>:</strong></td>
									<td class="text-left leadingInImg" style="display: none;"><input type="button" class="btn btn-new" value="导入图片" id="leadingInImg"></td>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue0"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue1"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue2"></td>
									<td class="text-right">最小值<strong>
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

			<%--压力，水箱，水池，附带电量--%>
			<div class="modal-body" id="twoPort">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="twoPort">
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font><span class="twoPortName">端口号</span><strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																 class="form-control inputWidth twoPortValue"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>K值<strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth twoPortKValue"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>B值<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																 onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																 class="form-control inputWidth twoPortBValue"></div>
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font>上限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth twoPortTopValue"></div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>下限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
																 onafterpaste="if(isNaN(value))execCommand('undo')"
																 class="form-control inputWidth twoPortLowValue"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>模拟量单位<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" class="form-control inputWidth twoPortMNUnit"></div>
									<div class="text-right displayInline" style="display: none;">
										<font style="color: red">*</font>id<strong> :</strong>
									</div>
									<div class="text-left displayInline" style="display: none;">
										<input type="text" class="form-control inputWidth twoPortOdetailID">
									</div>
									<div class="text-right displayInline Width11">最大值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																 class="form-control inputWidth twoPortMaxValue"></div>
									<div class="text-right displayInline Width7">最小值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																 class="form-control inputWidth twoPortMinValue"></div>
									<div class="text-right displayInline Width9">
										信号名称<strong>
										:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																 class="form-control inputWidth twoPortreserve"></div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>

								<div class="twoPort">
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font><span class="twoPortName">端口号(电池)</span><strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortValue"
																						onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																						onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>K值<strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortKValue"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>B值<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortBValue"></div>
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font>上限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortTopValue"></div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>下限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortLowValue"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>模拟量单位<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" class="form-control inputWidth twoPortMNUnit"></div>
									<div class="text-right displayInline" style="display: none;">
										<font style="color: red">*</font>id<strong> :</strong>
									</div>
									<div class="text-left displayInline" style="display: none;">
										<input type="text" class="form-control inputWidth twoPortOdetailID">
									</div>
									<div class="text-right displayInline Width11">最大值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMaxValue"></div>
									<div class="text-right displayInline Width7">最小值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMinValue"></div>
									<div class="text-right displayInline Width9">
										信号名称<strong>
										:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortreserve"></div>
								</div>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="MaxValue"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue0"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue1"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue2"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue3"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue4"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue5"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue6"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue7"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue8"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue9"></td>
									<td class="text-right">最小值<strong>
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
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 class="form-control inputWidth" id="fireMaxValue10">
									</td>
									<td class="text-right">最小值<strong>
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
										<td class="text-right">最大值<strong>
											:</strong></td>
										<td class="text-left"><input type="text" labport="IA"
																	 class="form-control inputWidth" id="MNIOPortMaxValue">
										</td>
										<td class="text-right">最小值<strong>
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
				<button type="button" class="btn btn-primary btnSure">
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


<!-- 查看模态框 -->
<div class="modal fade flowauto" tabindex="-1" role="dialog" id="newAddView">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看设备设施</h4>
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
									<td class="text-right"><font style="color: red">*</font>所属单位<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" id="UnitIDView">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>所属建筑<strong>
										:</strong></td>
									<td class="text-left"><select id="BuildIdView"
																  disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>所属区域<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" disabled="disabled"
											id="BuildAreaIdView">
										<option value="">--请选择--</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>系统类型<strong>
										:</strong></td>
									<td class="text-left"><select id="EqSystemCodeView"
																  disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>设备类型<strong>
										:</strong></td>
									<td class="text-left"><select id="EqClassIDView"
																  disabled="disabled" class="form-control inputWidth">
										<option value="" code="">--请选择--</option>
									</select></td>

									<td class="text-right"><font style="color: red">*</font>联网设备<strong>
										:</strong></td>
									<td class="text-left"><select
											class="form-control inputWidth" disabled="disabled"
											id="NetDeviceIDView">
										<option value="">--请选择--</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>设备名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="EqNameView"></td>
									<td class="text-right"><font style="color: red">*</font>监测位置<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" readonly="readonly"
																 name="Position" id="PositionView"></td>
									<td class="text-right leadingInImg">点位视频<strong>:</strong></td>
									<td class="text-left leadingInImg"><select type="text" class="form-control inputWidth" id="pointVideoView" disabled="disabled"></select></td>
								</tr>
								<tr>
									<td class="text-right leadingInImg">查看图片<strong>:</strong></td>
									<td class="text-left leadingInImg"><input type="button" class="btn btn-new" value="查看图片" id="leadingInImgView"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="SetPortView">
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
											id="NameView0">端口号(电源)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="PortView0"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="TypeView0" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="ValueView0"
																  disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="OneView0"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ZeroView0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="BDetailIDView0">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ShReserveView0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="NameView1">端口号(半降)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="PortView1"
																 readonly="readonly"
																 onkeyup="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="TypeView1" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="ValueView1"
																  disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="OneView1"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ZeroView1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="BDetailIDView1">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ShReserveView1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="NameView2">端口号(全降)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="PortView2"
																 readonly="readonly"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>

									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="TypeView2" class="form-control inputWidth">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="ValueView2"
																  disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="OneView2"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ZeroView2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="BDetailIDView2">
									</td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ShReserveView2"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="smokePortView">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="smokeControlPortView">
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
								<div class="smokeControlPortView">
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
								<div class="smokeControlPortView">
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
							</div>


							<%--<table class="table tableNo portTable">--%>
							<%--<colgroup>--%>
							<%--<col width="10%">--%>
							<%--<col width="20%">--%>
							<%--<col width="10%">--%>
							<%--<col width="20%">--%>
							<%--<col width="10%">--%>
							<%--<col width="20%">--%>
							<%--</colgroup>--%>

							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font><span--%>
							<%--id="FNameView0">端口号(手自动)</span><strong> :</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FPortView0"--%>
							<%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
							<%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
							<%--</td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><select--%>
							<%--id="FTypeView0" class="form-control inputWidth">--%>
							<%--<option value=""></option>--%>
							<%--<option value="0" selected="selected">数字量</option>--%>
							<%--<option value="1">模拟量</option>--%>
							<%--</select></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><select id="FValueView0"--%>
							<%--class="form-control inputWidth">--%>
							<%--<option value="">--请选择--</option>--%>
							<%--<option value="0">低电平0</option>--%>
							<%--<option value="1">高电平1</option>--%>
							<%--</select></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FOneView0"></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FZeroView0"></td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font><strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><input--%>
							<%--type="text" class="form-control inputWidth" id="FBDetailIDView0">--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right">--%>
							<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
							<%--:</strong>--%>
							<%--</td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FShReserveView0"></td>--%>
							<%--</tr>--%>
							<%--<tr style="height: 10px;">--%>
							<%--<td colspan="6">--%>
							<%--<p class="borderbottom"></p>--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font><span--%>
							<%--id="FNameView1">端口号(启停)</span><strong> :</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FPortView1"--%>
							<%--onkeyup="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
							<%--onafterpaste="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
							<%--</td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><select--%>
							<%--id="FTypeView1" class="form-control inputWidth">--%>
							<%--<option value=""></option>--%>
							<%--<option value="0" selected="selected">数字量</option>--%>
							<%--<option value="View1">模拟量</option>--%>
							<%--</select></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><select id="FValueView1"--%>
							<%--class="form-control inputWidth">--%>
							<%--<option value="">--请选择--</option>--%>
							<%--<option value="0">低电平0</option>--%>
							<%--<option value="View1">高电平View1</option>--%>
							<%--</select></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FOneView1"></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FZeroView1"></td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font><strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><input--%>
							<%--type="text" class="form-control inputWidth" id="FBDetailIDView1">--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right">--%>
							<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
							<%--:</strong>--%>
							<%--</td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FShReserveView1"></td>--%>
							<%--</tr>--%>
							<%--<tr style="height: 10px;">--%>
							<%--<td colspan="6">--%>
							<%--<p class="borderbottom"></p>--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font><span--%>
							<%--id="FNameView2">端口号(电源)</span><strong> :</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FPortView2"--%>
							<%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
							<%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
							<%--</td>--%>

							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><select--%>
							<%--id="FTypeView2" class="form-control inputWidth">--%>
							<%--<option value=""></option>--%>
							<%--<option value="0" selected="selected">数字量</option>--%>
							<%--<option value="1">模拟量</option>--%>
							<%--</select></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><select id="FValueView2"--%>
							<%--class="form-control inputWidth">--%>
							<%--<option value="">--请选择--</option>--%>
							<%--<option value="0">低电平0</option>--%>
							<%--<option value="1">高电平1</option>--%>
							<%--</select></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FOneView2"></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FZeroView2"></td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font><strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><input--%>
							<%--type="text" class="form-control inputWidth" id="FBDetailIDView2">--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right">--%>
							<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
							<%--:</strong>--%>
							<%--</td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FShReserveView2"></td>--%>
							<%--</tr>--%>
							<%--<tr style="height: 10px;">--%>
							<%--<td colspan="6">--%>
							<%--<p class="borderbottom"></p>--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font><span--%>
							<%--id="FNameView3">端口号(故障)</span><strong> :</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FPortView3"--%>
							<%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
							<%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
							<%--</td>--%>

							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><select--%>
							<%--id="FTypeView3" class="form-control inputWidth">--%>
							<%--<option value=""></option>--%>
							<%--<option value="0" selected="selected">数字量</option>--%>
							<%--<option value="1">模拟量</option>--%>
							<%--</select></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><select id="FValueView3"--%>
							<%--class="form-control inputWidth">--%>
							<%--<option value="">--请选择--</option>--%>
							<%--<option value="0">低电平0</option>--%>
							<%--<option value="1">高电平1</option>--%>
							<%--</select></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FOneView3"></td>--%>
							<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
							<%--:</strong></td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FZeroView3"></td>--%>
							<%--<td class="text-right" style="display: none;"><font--%>
							<%--style="color: red">*</font><strong> :</strong></td>--%>
							<%--<td class="text-left" style="display: none;"><input--%>
							<%--type="text" class="form-control inputWidth" id="FBDetailIDView3">--%>
							<%--</td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
							<%--<td class="text-right">--%>
							<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
							<%--:</strong>--%>
							<%--</td>--%>
							<%--<td class="text-left"><input type="text"--%>
							<%--class="form-control inputWidth" id="FShReserveView3"></td>--%>
							<%--</tr>--%>
							<%--</table>--%>


						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="faultPortView">
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
											id="FNameView">手动端口</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="FPortView"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>数据类型<strong> :</strong></td>
									<td class="text-left" style="display: none;"><select
											id="FTypeView" class="form-control" style="width: 122px;">
										<option value=""></option>
										<option value="0" selected="selected">数字量</option>
										<option value="1">模拟量</option>
									</select></td>
									<td class="text-right"><font style="color: red">*</font>正常值<strong>
										:</strong></td>
									<td class="text-left"><select id="FValueView" disabled="disabled" class="form-control inputWidth">
										<option value="">--请选择--</option>
										<option value="0">低电平0</option>
										<option value="1">高电平1</option>
									</select></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>正常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="FOneView"></td>
									<td class="text-right"><font style="color: red">*</font>异常名<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="FZeroView"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font><strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" class="form-control inputWidth"
											id="FDetailIDView"></td>
								</tr>
								<tr>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="ShReserveView"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="fourPortView">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="pumpPortView">
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
								<div class="pumpPortView">
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
								<div class="pumpPortView">
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
								<div class="pumpPortView">
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
							</div>


							<%--<table class="table tableNo portTable">--%>
								<%--<colgroup>--%>
									<%--<col width="10%">--%>
									<%--<col width="20%">--%>
									<%--<col width="10%">--%>
									<%--<col width="20%">--%>
									<%--<col width="10%">--%>
									<%--<col width="20%">--%>
								<%--</colgroup>--%>

								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font><span--%>
											<%--id="FNameView0">端口号(手自动)</span><strong> :</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FPortView0"--%>
																 <%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
																 <%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
									<%--</td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><select--%>
											<%--id="FTypeView0" class="form-control inputWidth">--%>
										<%--<option value=""></option>--%>
										<%--<option value="0" selected="selected">数字量</option>--%>
										<%--<option value="1">模拟量</option>--%>
									<%--</select></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><select id="FValueView0"--%>
																  <%--class="form-control inputWidth">--%>
										<%--<option value="">--请选择--</option>--%>
										<%--<option value="0">低电平0</option>--%>
										<%--<option value="1">高电平1</option>--%>
									<%--</select></td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FOneView0"></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FZeroView0"></td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font><strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><input--%>
											<%--type="text" class="form-control inputWidth" id="FBDetailIDView0">--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right">--%>
										<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
										<%--:</strong>--%>
									<%--</td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FShReserveView0"></td>--%>
								<%--</tr>--%>
								<%--<tr style="height: 10px;">--%>
									<%--<td colspan="6">--%>
										<%--<p class="borderbottom"></p>--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font><span--%>
											<%--id="FNameView1">端口号(启停)</span><strong> :</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FPortView1"--%>
																 <%--onkeyup="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
																 <%--onafterpaste="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
									<%--</td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><select--%>
											<%--id="FTypeView1" class="form-control inputWidth">--%>
										<%--<option value=""></option>--%>
										<%--<option value="0" selected="selected">数字量</option>--%>
										<%--<option value="View1">模拟量</option>--%>
									<%--</select></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><select id="FValueView1"--%>
																  <%--class="form-control inputWidth">--%>
										<%--<option value="">--请选择--</option>--%>
										<%--<option value="0">低电平0</option>--%>
										<%--<option value="View1">高电平View1</option>--%>
									<%--</select></td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FOneView1"></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FZeroView1"></td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font><strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><input--%>
											<%--type="text" class="form-control inputWidth" id="FBDetailIDView1">--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right">--%>
										<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
										<%--:</strong>--%>
									<%--</td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FShReserveView1"></td>--%>
								<%--</tr>--%>
								<%--<tr style="height: 10px;">--%>
									<%--<td colspan="6">--%>
										<%--<p class="borderbottom"></p>--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font><span--%>
											<%--id="FNameView2">端口号(电源)</span><strong> :</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FPortView2"--%>
																 <%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
																 <%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
									<%--</td>--%>

									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><select--%>
											<%--id="FTypeView2" class="form-control inputWidth">--%>
										<%--<option value=""></option>--%>
										<%--<option value="0" selected="selected">数字量</option>--%>
										<%--<option value="1">模拟量</option>--%>
									<%--</select></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><select id="FValueView2"--%>
																  <%--class="form-control inputWidth">--%>
										<%--<option value="">--请选择--</option>--%>
										<%--<option value="0">低电平0</option>--%>
										<%--<option value="1">高电平1</option>--%>
									<%--</select></td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FOneView2"></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FZeroView2"></td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font><strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><input--%>
											<%--type="text" class="form-control inputWidth" id="FBDetailIDView2">--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right">--%>
										<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
										<%--:</strong>--%>
									<%--</td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FShReserveView2"></td>--%>
								<%--</tr>--%>
								<%--<tr style="height: 10px;">--%>
									<%--<td colspan="6">--%>
										<%--<p class="borderbottom"></p>--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font><span--%>
											<%--id="FNameView3">端口号(故障)</span><strong> :</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FPortView3"--%>
																 <%--onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"--%>
																 <%--onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">--%>
									<%--</td>--%>

									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font>数据类型<strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><select--%>
											<%--id="FTypeView3" class="form-control inputWidth">--%>
										<%--<option value=""></option>--%>
										<%--<option value="0" selected="selected">数字量</option>--%>
										<%--<option value="1">模拟量</option>--%>
									<%--</select></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常值<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><select id="FValueView3"--%>
																  <%--class="form-control inputWidth">--%>
										<%--<option value="">--请选择--</option>--%>
										<%--<option value="0">低电平0</option>--%>
										<%--<option value="1">高电平1</option>--%>
									<%--</select></td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right"><font style="color: red">*</font>正常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FOneView3"></td>--%>
									<%--<td class="text-right"><font style="color: red">*</font>异常名<strong>--%>
										<%--:</strong></td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FZeroView3"></td>--%>
									<%--<td class="text-right" style="display: none;"><font--%>
											<%--style="color: red">*</font><strong> :</strong></td>--%>
									<%--<td class="text-left" style="display: none;"><input--%>
											<%--type="text" class="form-control inputWidth" id="FBDetailIDView3">--%>
									<%--</td>--%>
								<%--</tr>--%>
								<%--<tr>--%>
									<%--<td class="text-right">--%>
										<%--<!-- <font style="color: red">*</font> -->信号名称<strong>--%>
										<%--:</strong>--%>
									<%--</td>--%>
									<%--<td class="text-left"><input type="text"--%>
																 <%--class="form-control inputWidth" id="FShReserveView3"></td>--%>
								<%--</tr>--%>
							<%--</table>--%>


						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="threePortView">
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
											id="portNameView0">端口号(IA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TPortView0"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="KValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="BValueView0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TopValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="LowValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="MNUnitView0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="TdetailIDView0">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MaxValueView0"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MinValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="reserveView0"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="portNameView1">端口号(IB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 class="form-control inputWidth" id="TPortView1"
																 readonly="readonly"
																 onkeyup="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="KValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="BValueView1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TopValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="LowValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="MNUnitView1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="TdetailIDView1">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MaxValueView1"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MinValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="reserveView1"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="portNameView2">端口号(IC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TPortView2"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="KValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="BValueView2"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TopValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="LowValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="MNUnitView2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="TdetailIDView2">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MaxValueView2"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MinValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>信号名称<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="reserveView2"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<%--压力，水箱，水池，附带电量--%>
			<div class="modal-body" id="twoPortView">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<div class="container">
					<div class="row">
						<div class="col-md-10">

							<div class="portTable">
								<div class="twoPortView">
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font><span class="twoPortNameView">端口号</span><strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortValueView"
																						onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																						onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>K值<strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortKValueView"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>B值<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortBValueView"></div>
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font>上限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortTopValueView"></div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>下限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortLowValueView"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>模拟量单位<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" class="form-control inputWidth twoPortMNUnitView"></div>
									<div class="text-right displayInline" style="display: none;">
										<font style="color: red">*</font>id<strong> :</strong>
									</div>
									<div class="text-left displayInline" style="display: none;">
										<input type="text" class="form-control inputWidth twoPortOdetailIDView">
									</div>
									<div class="text-right displayInline Width11">最大值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMaxValueView"></div>
									<div class="text-right displayInline Width7">最小值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMinValueView"></div>
									<div class="text-right displayInline Width9">
										信号名称<strong>
										:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortreserveView"></div>
									<div class="borderbottom111 displayInline" style="height: 10px;width: 100%;border-top: 1px solid #ccc;"></div>
								</div>

								<div class="twoPortView">
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font><span class="twoPortNameView">端口号(电池)</span><strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortValueView"
																						onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																						onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>K值<strong> :</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortKValueView"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>B值<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						onkeyup="this.value=this.value.replace(/[^\-?\d.]/g,'')"
																						class="form-control inputWidth twoPortBValueView"></div>
									<div class="text-right displayInline Width11">
										<font style="color: red">*</font>上限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortTopValueView"></div>
									<div class="text-right displayInline Width7">
										<font style="color: red">*</font>下限<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" onkeyup="if(isNaN(value))execCommand('undo')"
											   onafterpaste="if(isNaN(value))execCommand('undo')"
											   class="form-control inputWidth twoPortLowValueView"></div>
									<div class="text-right displayInline Width9">
										<font style="color: red">*</font>模拟量单位<strong>:</strong>
									</div>
									<div class="text-left displayInline Width19">
										<input type="text" class="form-control inputWidth twoPortMNUnitView"></div>
									<div class="text-right displayInline" style="display: none;">
										<font style="color: red">*</font>id<strong> :</strong>
									</div>
									<div class="text-left displayInline" style="display: none;">
										<input type="text" class="form-control inputWidth twoPortOdetailIDView">
									</div>
									<div class="text-right displayInline Width11">最大值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMaxValueView"></div>
									<div class="text-right displayInline Width7">最小值<strong>
										:</strong></div>
									<div class="text-left displayInline Width19"><input type="text" labport="IA"
																						class="form-control inputWidth twoPortMinValueView"></div>
									<div class="text-right displayInline Width9">
										信号名称<strong>
										:</strong>
									</div>
									<div class="text-left displayInline Width19"><input type="text"
																						class="form-control inputWidth twoPortreserveView"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="otherPortView">
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
											id="OtherPortNameView">端口号</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="PortView"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="KValueView"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="BValueView"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="TopValueView"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="LowValueView"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="MNUnitView"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="OdetailIDView"></td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MaxValueView"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="MinValueView"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="reserveView"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="ElevenPortView">
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
											id="fireNameView0">端口号(IA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView0"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView0"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" readonly="readonly"
																 class="form-control inputWidth" id="fireTopValueView0">
									</td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView0"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView0"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView0">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView0"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView0"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView0"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView1">端口号(IB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView1"
																 onkeyup="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==View1){this.value=this.value.replace(/[^View1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView1"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView1"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView1"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView1">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView1"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView1"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView1"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView2">端口号(IC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView2"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView2"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView2"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView2"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView2">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView2"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView2"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView2"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView3">端口号(UA)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView3"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView3"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView3"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView3"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView3"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView3"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView3">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView3"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView3"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView3"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView4">端口号(UB)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView4"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView4"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView4"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView4"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView4"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView4"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView4">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView4"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView4"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView4"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView5">端口号(UC)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView5"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView5"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView5"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView5"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView5"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView5"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView5">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView5"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView5"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView5"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView6">端口号(A)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView6"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView6"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView6"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView6"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView6"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView6"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView6">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView6"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView6"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView6"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView7">端口号(B)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView7"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView7"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView7"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView7"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView7"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView7"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView7">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView7"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView7"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView7"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView8">端口号(C)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView8"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView8"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView8"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView8"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView8"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView8"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView8">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView8"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView8"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView8"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>

								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView9">端口号(D)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView9"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValue9View"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView9"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView9"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView9"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView9"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView9">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView9"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView9"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView9"></td>
								</tr>
								<tr style="height: 10px;">
									<td colspan="6">
										<p class="borderbottom"></p>
									</td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font><span
											id="fireNameView10">端口号(漏电流)</span><strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="firePortView10"
																 onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
																 onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
									</td>
									<td class="text-right" style="display: block;"><font
											style="color: red">*</font>K值<strong> :</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireKValueView10"></td>
									<td class="text-right"><font style="color: red">*</font>B值<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireBValueView10"></td>
								</tr>
								<tr>
									<td class="text-right"><font style="color: red">*</font>上限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireTopValueView10"></td>
									<td class="text-right"><font style="color: red">*</font>下限<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireLowValueView10"></td>
									<td class="text-right"><font style="color: red">*</font>模拟量单位<strong>
										:</strong></td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMNUnitView10"></td>
									<td class="text-right" style="display: none;"><font
											style="color: red">*</font>id<strong> :</strong></td>
									<td class="text-left" style="display: none;"><input
											type="text" readonly="readonly"
											class="form-control inputWidth" id="firedetailIDView10">
									</td>
								</tr>
								<tr>
									<td class="text-right">最大值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMaxValueView10"></td>
									<td class="text-right">最小值<strong>
										:</strong></td>
									<td class="text-left"><input type="text" labport="IA"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireMinValueView10"></td>
									<td class="text-right">
										<!-- <font style="color: red">*</font> -->信号名称<strong>
										:</strong>
									</td>
									<td class="text-left"><input type="text"
																 readonly="readonly" class="form-control inputWidth"
																 id="fireReserveView10"></td>
								</tr>

							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-body" id="customPortView">
				<div style="text-align: center">
					<h3>端口信息</h3>
				</div>
				<table class="table table-bordered" id="portTableView">
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-close btnClose"
						data-dismiss="modal">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 新增单位编号绑定模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="exportAddress">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</div>
			<div id="parentForm">
				<div class="form-inline">
					<div class="form-group">
						<label for="unitName" class="control-label">单位名称<strong>
							:</strong></label> <input type="text" class="form-control inputNormal" id="unitName-relAddress">
					</div>
					<button type="button" class="btn btn-primary" onclick="searchAddress()">
						<i class="fa fa-search"></i>&nbsp;查询
					</button>
					<button type="button" class="btn btn-del removeMove"
							data-toggle="modal">
						<i class="fa fa-trash"></i>&nbsp;批量删除
					</button>
				</div>
				<div style="height: 10px;"></div>
				<table class="table table-bordered" id="userTable">

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
										<td class="text-right">单位<font style="color: red">*</font><strong>:</strong>
										</td>
										<td class="text-left">
											<select name="newnetDeviceRel-unit" id="newnetDeviceRel-unit" style="width: 197px;"></select>
										</td>
										<td class="text-right">建筑<font style="color: red">*</font><strong>:</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRel-build" class="form-control"
												style="width: 197px;" onchange="buildAreaSelect()">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">区域<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRel-buildAreaid" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>

										<td class="text-right">点位类型<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRel-pointtype" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">系统类型<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRel-eqsystemid" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>

										<td class="text-right">点位视频<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRel-pointVideo" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">部位号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal"
																	 id="newnetDeviceRel-partcode"></td>
										<td class="text-right password">真实地址<font
												style="color: red">*</font><strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal" id="newnetDeviceRel-adress">
										</td>
									</tr>
									<tr>
										<td class="text-right password">设备名称<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal" id="newnetDeviceRel-name">
										</td>

										<td class="text-right password">x坐标<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal" id="newnetDeviceRel-xaxis">
										</td>
									</tr>
									<tr>
										<td class="text-right password">y坐标<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal" id="newnetDeviceRel-yaxis">
										</td>
										<td class="text-right password">备注<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal" id="newnetDeviceRel-remark">
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group" align="center" style="margin-right: 60px">
				<button type="button" class="btn btn-primary"
						onclick="Preservation()">
					<i class="fa fa-floppy-o"></i>&nbsp;保存
				</button>
				<button type="button" class="btn btn-primary "
						onclick="addressExport()">
					<i class="fa fa-pencil"></i>&nbsp;导入
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 编辑关联地址 -->
<div class="modal fade notLastModal" tabindex="-1" role="dialog" id="editAddress">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">编辑联网设备地址</h4>
			</div>
			<div class="modal-body" id="EditMsg">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位<font style="color: red">*</font><strong>:</strong>
										</td>
										<td class="text-left">
											<select name="newnetDeviceRelEdit-unit" id="newnetDeviceRelEdit-unit" style="width: 197px;"></select>
										</td>
										<td class="text-right">建筑<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRelEdit-build" class="form-control"
												style="width: 197px;" onchange="buildAreaEditSelect()">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">区域<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRelEdit-buildAreaid" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>

										<td class="text-right">点位类型<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRelEdit-pointtype" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">系统类型<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRelEdit-eqsystemid" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>

										<td class="text-right">点位视频<strong> :</strong>
										</td>
										<td class="text-left"><select
												id="newnetDeviceRelEdit-pointVideo" class="form-control"
												style="width: 197px;">
											<option value="">--请选择--</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">部位号<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal"
																	 id="newnetDeviceRelEdit-partcode"></td>

										<td class="text-right password">真实地址<font
												style="color: red">*</font><strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal"
																			  id="newnetDeviceRelEdit-adress"></td>
									</tr>
									<tr>
										<td class="text-right password">设备设施名称<strong>
											:</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal"
																			  id="newnetDeviceRelEdit-name"></td>

										<td class="text-right password">x坐标<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal"
																			  id="newnetDeviceRelEdit-xaxis"></td>
									</tr>
									<tr>
										<td class="text-right password">y坐标<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal"
																			  id="newnetDeviceRelEdit-yaxis"></td>


										<td class="text-right password">备注<strong> :</strong>
										</td>
										<td class="text-left password"><input type="text"
																			  class="form-control inputNormal"
																			  id="newnetDeviceRelEdit-remark"></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group" align="center" style="margin-right: 60px">
				<button type="button" class="btn btn-primary"
						onclick="updateEqAddressRel()">
					<i class="fa fa-floppy-o"></i>&nbsp;保存
				</button>
			</div>
			<!-- <div id="parentForm">
					<table class="table table-bordered" id="userTable">

					</table>
				</div> -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!--导入数据-->
<div class="modal fade notLastModal" tabindex="-1" role="dialog"
	 id="importBuilding">
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
								<input type="hidden" name="eqid" id="eqid"> <span
									class="col-md-2">第一步</span> <a
									href='${ctx}/common/download/address'><span
									class="btn btn-sm downTemp btn-new"><i
									class="fa fa-download"></i>下载模板</span></a>
							</div>
							<div>
								<p class="col-md-offset-2">区域ID和点位视频ID请到相应的模块导出文档查看</p>
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


<!-- 新增控制接口模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="newInterface">
	<div class="modal-dialog" role="document" id="firstModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">增加控制接口</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div id="controller_table">
								<table class="table table-bordered" id="interfaceList">

								</table>
							</div>
							<div style="height: 3px;"></div>
							<div class="" style="border: 0.8px solid #dbdbdb;">
								<table class="table tableNo">
									<colgroup>
										<col width="12%">
										<col width="21%">
										<col width="12%">
										<col width="21%">
										<col width="12%">
										<col width="22%">
									</colgroup>
									<tr>
										<td class="text-right">控制端口<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select name="IOPort1"
																	  class="form-control inputwith" id="iOPort_add">
											<option value="1" selected="selected">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
										</select></td>
										<td class="text-right">正常电平<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><select id="digitalNormal_add"
																	  class="form-control inputwith">
											<option value="">--请选择--</option>
											<option value="0">低电平0</option>
											<option value="1">高电平1</option>
										</select></td>
										<td class="text-right">信号名称<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="detialName_add">
										</td>
									</tr>
									<tr>
										<td class="text-right">反馈端口<font style="color: red"></font><strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="reserve_add"></td>

										<td class="text-right">正常名<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="goodName_add"></td>
										<td class="text-right">异常名<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="badName_add"></td>
									</tr>
									<tr>

										<td class="text-right">点动单位<strong> :</strong>
										</td>
										<td><select id="movement_add"
													class="form-control inputwith">
											<option value="1" selected="selected">1ms</option>
											<option value="2">100ms</option>
											<option value="3">1s</option>
											<option value="6">100s</option>
											<option value="7">1min</option>
											<option value="8">100min</option>
										</select></td>
										<td class="text-right">
											<!-- <font style="color: red">*</font> -->点动时间<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="movementTime_add">
										</td>



										<td class="text-right">
											<!-- <font style="color: red">*</font> -->排序<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputwith" id="sortno_add"></td>
									</tr>
									<tr>

										<td class="text-right">控制类型<strong> :</strong>
										</td>
										<td><select id="orderType_add"
													class="form-control inputwith">
											<option value="2" selected="selected">点动</option>
											<option value="1">电平</option>
										</select></td>
										<td class="text-right">
											<!-- <font style="color: red">*</font> -->锁定<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="radio"
																	 name="isLock_add" value="1" id="isLock_add1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="isLock_add" value="0"
												   id="isLock_add0" checked="checked">否</td>
									</tr>

								</table>
								<div style="text-align: center; margin-bottom: 2px;">
									<button type="button" class="btn btn-primary btnSureInterface">
										<i class="fa fa-floppy-o"></i>&nbsp;保存
									</button>
									<button type="button" class="btn btn-close btnCloseInterface"
											aria-label="Close">
										<i class="fa fa-times"></i>&nbsp;取消
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary "
						onclick="interfaceImport()">
					<i class="fa fa-pencil"></i>&nbsp;导入
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

<!--导入数据-->
<div class="modal fade notLastModal" tabindex="-1" role="dialog"
	 id="importInterfaces">
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
						<form id="interfaceForm" class="importArea">
							<div>
								<input type="hidden" name="deviceId" id="deviceId"> <span
									class="col-md-2">第一步</span> <a
									href='${ctx}/common/download/interfaceOut'><span
									class="btn btn-sm downTemp btn-new"><i
									class="fa fa-download"></i>下载模板</span></a>
							</div>
							<div>
								<p class="col-md-offset-2">先下载模板，完善内容上传。</p>
							</div>
							<div>
								<span class="col-md-2">第二步</span>
								<div class="wrapExport">
									<input type="file" name="uploadExcel"
										   onchange="doChange(this)" class="changeInput uploadExcel1" />
									<button type="button"
											class="btn btn-new btn-sm btn-new chooseFile">
										<i class="fa fa-arrow-up"></i>&nbsp;上传
									</button>
								</div>
								<span class="fileName1"></span>
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

<!-- 导入图片并标点 -->
<div class="modal fade" tabindex="-1" role="dialog" id="imgMark">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">导入图片并标点</h4>
			</div>
			<div class="modal-body" id="addImg">
				<form name="imgLoad" id="imgLoad">
					<div class="container" style="width: 970px;">
						<div>
							<span class="col-md-2" style="width: 120px;margin-top: 4px;">导入图片</span>
							<div class="wrapExport">
								<button type="button" class="btn btn-new btn-sm btn-new chooseFile">
									<i class="fa fa-arrow-up"></i>&nbsp;上传
								</button>

								<input id="photoCover-photo" type="text" style="display: none;">
								<input id="picurls-photo" name="imageDataList" type="file"
										   class="file typeFileMore"
										   value="" onchange="doChangeProjectphoto()" style="width:60px;height: 30px;"/>
							</div>
							<span class="fileName"></span>
							<button type="button" class="btn btn-new new-mark" style="position: absolute;top: 15px;right: 150px;">
								<i class="fa fa-plus"></i>&nbsp;增加标注
							</button>
							<button type="button" class="btn btn-del del-mark" style="position: absolute;top: 15px;right: 35px;">
								<i class="fa fa-trash"></i>&nbsp;删除标注
							</button>
						</div>
					</div>
				</form>

				<div style="width: 100%;height: 500px;overflow: auto;position: relative;top: 0;">
					<div id="wrap_img" style="position: absolute;top: 20px;left: 0;width: auto;height: auto;">
						<div style="position: relative;" id="imgDiv"  onmousewheel="return bbimg(this)">

						</div>
					</div>
				</div>

				<%--<div id="imgshow" style="overflow: auto;max-height: 500px;"></div>--%>
			</div>
			<div class="form-group" align="center" style="margin-right: 60px">
				<button type="button" class="btn btn-close" data-dismiss="modal"
						aria-label="Close">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
			<!-- <div id="parentForm">
					<table class="table table-bordered" id="userTable">

					</table>
				</div> -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 查看导入图和标点 -->
<div class="modal fade" tabindex="-1" role="dialog" id="imgMarkView" style="overflow: auto;">
    <div class="modal-dialog" role="document" id="wrapModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">导入图片并标点</h4>
            </div>
            <div class="modal-body" id="addImgView">
                <form name="imgLoadView" id="imgLoadView">
                    <div class="container" style="width: 970px;">
                        <div>
                            <span class="col-md-2" style="width: 120px;margin-top: 4px;">导入图片</span>
                            <input id="picurls-photoView" type="text" style="display: none;">
                        </div>
                    </div>
                </form>

                <div style="width: 100%;max-height: 500px;overflow: auto;">
                    <div style="position: relative;" id="imgDivView"  onmousewheel="return bbimg(this)">

                    </div>
                </div>

                <%--<div id="imgshow" style="overflow: auto;max-height: 500px;"></div>--%>
            </div>
            <div class="form-group" align="center" style="margin-right: 60px">
                <button type="button" class="btn btn-close" data-dismiss="modal"
                        aria-label="Close">
                    <i class="fa fa-times"></i>&nbsp;关闭
                </button>
            </div>
            <!-- <div id="parentForm">
                    <table class="table table-bordered" id="userTable">

                    </table>
                </div> -->
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 区分新增和编辑 -->
<input type="hidden" id="edit" value="0">

<input type="hidden" name="indexId" id="indexId" value="">

<%--权限--%>
<input type="hidden" name="preId" id="preId" value="">

<input type="hidden" id="addressRelId" value="">

<input type="hidden" id="interfaceId">

<input type="hidden" id="deviceId" value="">

</body>
<script src="/js/common/udraggable/jquery.udraggable.js"></script>
<script src="/js/common/udraggable/jquery.event.ue.js"></script>
<script src="/js/page_js/device/equipments.js"></script>
</html>