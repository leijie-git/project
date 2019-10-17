<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>

<head>
	<meta charset="UTF-8">
	<title>单位管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@include file="../inc.jsp"%>
	<link rel="stylesheet" href="/css/page_css/Employee_management.css">
	<script type="text/javascript"
			src="http://api.map.baidu.com/getscript?v=2.0&ak=uAVQruFnlAevcIBVA89lt02GH5kLkUXd&services="></script>
	<script src="/js/page_js/unit/baseInfo.js"></script>
	<style>
		.modalLW .table .inputNormal{
			width: 70%;
		}
		#firstViewModal .table .inputNormal{
			width: 70%;
		}
	</style>
</head>

<body>

<div class="container jy_wrap">
	<div class="row">
		<div class="col-md-12">
			<div class="jy_wrapper">
				<ul class="jy_title">
					<li class="is_active"><span class="title">单位管理</span></li>
					<li class="no_active"><span class="title">单位管理</span></li>
				</ul>
				<div class="unitManageLWseniorSearch">
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">单位名称
									<strong>:</strong>
								</label>
								<input type="text" class="form-control" name="unitName_top" id="unitName_top">

								<label for="identityNo" class="control-label">单位编号
									<strong>:</strong>
								</label>
								<input type="text" class="form-control" name="unitCode_top" id="unitCode_top">

								<label for="identityNo" class="control-label">单位类别
									<strong>:</strong>
								</label>
								<select id="unitType" name="unitType" class="form-control">
									<option value="">全部</option>
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

				<div class="unitManageWBseniorSearch" hidden>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">单位名称
									<strong>:</strong>
								</label>
								<input type="text" class="form-control" name="Search-unitName" id="Search-unitName">

								<label for="identityNo" class="control-label">单位编号
									<strong> :</strong>
								</label>
								<input type="text" class="form-control" name="Search-unitCode" id="Search-unitCode">
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

				<div class="unitManageJGseniorSearch" hidden>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">单位名称
									<strong>:</strong>
								</label>
								<input type="text" class="form-control" name="JGunitName" id="JGunitName">

								<label for="identityNo" class="control-label">单位编号
									<strong> :</strong>
								</label>
								<input type="text" class="form-control" name="JGunitCode" id="JGunitCode">
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
	</div>
	<div class="row" id="employss">
		<div class="col-md-12">
			<div class="dataTable">
				<div class="jy_new" style="top: 20px;">
					<div class="form-inline">
						<select id="unitManageSelect" class="form-control" style="width: 120px;float: left;margin-top: 6px;margin-right: 10px;">
							<option value="0">联网单位</option>
                            <c:if test="${userSession.unitId == null }">
							    <option value="1">维保单位</option>
							    <option value="2">监管单位</option>
                            </c:if>
						</select>
						<div class="unitManageLW">
							<div class="form-group">
								<select name="menuType" id="menuTypeLW" class="form-control">
									<option value="unitName_top">单位名称</option>
									<option value="unitCode_top">单位编号</option>
									<option value="unitType">单位类别</option>
								</select>
								<span id="commonInputLW">
									<input type="text" class="form-control showInput" name="searchContentLW" id="searchContentLW">
								</span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
                            <c:if test="${userSession.unitId == null }">
							    <button class="btn btn-new newAdd0">
							    	<i class="fa fa-plus"></i>新增
							    </button>
                            </c:if>
						</div>

						<div class="unitManageWB" style="display: none;">
							<div class="form-group">
								<select name="menuType" id="menuTypeWB" class="form-control">
									<option value="Search-unitName">单位名称</option>
									<option value="Search-unitCode">单位编号</option>
								</select>
								<span id="commonInputWB">
									<input type="text" class="form-control showInput" name="searchContentWB" id="searchContentWB">
								</span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button class="btn btn-new newAdd1">
								<i class="fa fa-plus"></i>新增
							</button>
						</div>

						<div class="unitManageJG" style="display: none;">
							<div class="form-group">
								<select name="menuType" id="menuTypeJG" class="form-control">
									<option value="JGunitName">单位名称</option>
									<option value="JGunitCode">单位编号</option>
								</select>
								<span id="commonInputJG">
									<input type="text" class="form-control showInput" name="searchContentJG" id="searchContentJG">
								</span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button class="btn btn-new newAdd2">
								<i class="fa fa-plus"></i>新增
							</button>
						</div>

						<!-- 							<button class="btn btn-danger btnDeleteMany"> -->
						<!-- 								<i class="fa fa-trash"></i>批量删除 -->
						<!-- 							</button> -->
					</div>
				</div>
				<div id="parentForm">

					<table class="table table-bordered table-hover table-striped" id="userTable">
						<!--
                                <thead>
                                    <tr>
                                        <th class="bs-checkbox " style="width: 36px; " data-field="selectItem"
                                            tabindex="0">
                                            <div class="th-inner "><input name="btSelectAll" type="checkbox"></div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="unitcode" tabindex="0">
                                            <div class="th-inner ">单位编号</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="unitname" tabindex="0">
                                            <div class="th-inner ">单位名称</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="phone" tabindex="0">
                                            <div class="th-inner ">联系电话</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="establishmenttime" tabindex="0">
                                            <div class="th-inner ">成立时间</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="unitmanagementname" tabindex="0">
                                            <div class="th-inner ">单位管理名称</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="" data-field="" tabindex="0">
                                            <div class="th-inner ">操作</div>
                                            <div class="fht-cell"></div>
                                        </th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr data-index="0" data-uniqueid="558304619877367808">
                                        <td class="bs-checkbox "><input data-index="0" name="btSelectItem"
                                                type="checkbox"></td>
                                        <td style="">001</td>
                                        <td style="">新国际博览中心</td>
                                        <td style="">1234567890</td>
                                        <td style="">2019-03-21</td>
                                        <td style="">联网单位</td>
                                        <td style=""><button type="button"
                                                class="btn btn-new btn-xs cBtn-main edit"><i
                                                    class="fa fa-pencil"></i>&nbsp;编辑</button><button type="button"
                                                class="btn btn-primary btn-xs cBtn-main view"><i
                                                    class="fa fa-eye"></i>&nbsp;查看</button><button type="button"
                                                class="btn btn-danger btn-xs cBtn-main del"><i
                                                    class="fa fa-trash"></i>&nbsp;删除</button><button type="button"
                                                class="btn btn-new btn-xs cBtn-main setRole"
                                                style="margin: 0px 10px 0px 0px;"><i
                                                    class="fa fa-pencil"></i>&nbsp;绑定人员</button><button
                                                type="button" class="btn btn-new btn-xs cBtn-main bindNumber"><i
                                                    class="fa fa-pencil"></i>&nbsp;绑定编号</button></td>
                                    </tr>
                                </tbody> -->
					</table>

				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>

<!-- 新增模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="newEmploy">
	<div class="modal-dialog" role="document" id="wrapModal" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">

				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">新增单位</h4>
			</div>
			<!--新增联网-->
			<div class="modal-body modalLW">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<input type="hidden" id="id">
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="35%">
										<col width="15%">
										<col width="35%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">单位基础信息</strong></td>
									</tr>
									<tr>
										<td class="text-right">维保和监管单位<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left">
											<select class="form-control inputNormal selectpicker" id="newUnit-maintenanceUnit" multiple data-live-search="true" data-size="10">
											</select>
										</td>
										<td class="text-right">系统编号<strong>:</strong></td>
										<td class="text-left">
											<input type="text" class="form-control inputNormal" id="newUnit-systemNumber" disabled="disabled">
										</td>
									</tr>
									<tr>
										<td class="text-right">单位名称<font style="color: red">*</font><strong
												class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-unitname"></td>
										<td class="text-right">单位编号<font style="color: red">*</font><strong
												class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="newUnit-unitcode"></td>
									</tr>
									<tr>
										<td class="text-right">单位地址<font style="color: red">*</font><strong
												class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="newUnit-unitaddress"></td>
										<td class="text-right">联系电话<font style="color: red">*</font><strong
												class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-phone"></td>
									</tr>
									<tr>
										<td class="text-right">子站号 <strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="newUnit-childstationnum"
																							   placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')">
										</td>
										<td class="text-right">经纬度 <strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" name="newUnit-unitpoint"
																							   id="newUnit-unitpoint"><a href="#" onclick="showMap()">位置标注</a>
										</td>
									</tr>
									<tr>
										<td class="text-right">组织机构代码<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="newUnit-OrgCode"></td>
										<td class="text-right">单位类别<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="radio" name="newUnit-important" value="1"
																	 id="newUnit-important1">重点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newUnit-important" value="0"
												   id="newUnit-important0" checked="checked">非重点</td>
									</tr>
									<tr>
										<td class="text-right">联网状态<strong class="explain">:</strong></td>
										<td class="text-left"><input type="radio" name="newUnit-networkstatus"
																	 value="1" id="newUnit-networkstatus1"
																	 checked="checked">联网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newUnit-networkstatus" value="0"
												   id="newUnit-networkstatus0">未联网</td>
										<td class="text-right">联网时间 <strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-onlinedate" readonly></td>
									</tr>
									<tr>
										<td class="text-right">单位类型<strong class="explain">:</strong></td>
										<td class="text-left"><select id="newUnit-unittype" name="newUnit-unittype"
																	  class="form-control inputNormal">
											<option value="" selected="selected">---请选择---</option>
										</select>
										</td>
										<td class="text-right">成立时间<strong> :</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-establishmenttime" readonly></td>
									</tr>
									<tr>
										<td class="text-right">监管等级<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<select id="newUnit-superviselevel" class="form-control inputNormal">
												<option value="重要">重要</option>
												<option value="一般">一般</option>
												<option value="次要">次要</option>
											</select>
										</td>
										<td class="text-right">监管类型<strong class="explain">
											:</strong></td>
										<td class="text-left"><select id="newUnit-supervisetype"
																	  name="newUnit-supervisetype" class="form-control inputNormal">
											<option value="">---请选择---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<!-- <input type="text"
                                                class="form-control inputNormal" id="newUnit-proviceID"> -->
											<select name="newUnit-proviceid" id="newUnit-proviceid"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
										<td class="text-right">市<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<!-- <input
                                                type="text" class="form-control inputNormal"
                                                id="newUnit-dataFrom"> --> <select name="newUnit-cityid"
																				   id="newUnit-cityid" class="form-control inputNormal">
											<option value="">--请选择---</option>
										</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="newUnit-regionid" id="newUnit-regionid"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="newUnit-townid" id="newUnit-townid"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
									</tr>
									<tr>

										<td class="text-right">危险等级1<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="newUnit-unitdangerlevel" class="form-control inputNormal">
												<option value="一级">一级</option>
												<option value="二级">二级</option>
												<option value="三级">三级</option>
											</select>
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="text-right">火灾等级<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="newUnit-firerating" class="form-control inputNormal">
												<option value="很严重">很严重</option>
												<option value="严重">严重</option>
												<option value="不严重">不严重</option>
											</select>
										</td>
										<td class="text-right">单位毗邻情况<strong class="explain">:</strong></td>
										<td class="text-left"><input id="photoCover-unitproximity" type="text"
																	 style="display: none;">
											<div class="wrapTypeMore">
												<input id="picurls-unitproximity" name="imageDataList" type="file"
													   class="file typeFileMore" value=""
													   onchange="doChangeProjectunitproximity()" />
												<ul id="loadImg-unitproximity" class="loadImg">
													<li class="modalImg"><span>请选择上传图片</span></li>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td class="text-right">合同文书<strong> :</strong>
										</td>
										<td class="text-left">
											<div class="wrapExport">
												<input type="file" name="uploadFile" id="uploadFileId"
													   onchange="doChange(this)" class="changeInput uploadFile" />
												<button type="button" class="btn btn-new btn-sm btn-new chooseFile">
													<i class="fa fa-arrow-up"></i>&nbsp;选择文件
												</button>
											</div> <span class="fileName" style="margin-left: 12px;"></span>
											<input type="hidden" id="attachment"> <input type="hidden"
																						 id="attachmentName">
										</td>
										<td class="text-right">单位logo<strong class="explain">:</strong></td>
										<td class="text-left"><input id="photoCover-unitLogo" type="text"
																	 style="display: none;">
											<div class="wrapTypeMore">
												<input id="picurls-unitLogo" name="imageDataList" type="file"
													   class="file typeFileMore" value=""
													   onchange="doChangeProjectunitLogo0()" />
												<ul id="loadImg-unitLogo" class="loadImg">
													<li class="modalImg"><span>请选择上传图片</span></li>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td class="text-right">单位链接<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="newUnit-link"></td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="17%">
										<col width="28%">
										<col width="28%">
										<col width="28%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">单位人员</strong></td>
									</tr>
									<tr>
										<td class="text-right">人员类别</td>
										<td class="text-center" style="overflow: hidden;">姓名</td>
										<td class="text-center">身份证号</td>
										<td class="text-center" style="overflow: hidden;">联系电话</td>
									</tr>
									<tr>
										<td class="text-right">法人代表姓名</td>
										<td class="text-center" style="overflow: hidden;"><input type="text"
																								 class="form-control " id="newUnit-legalpersonname"></td>
										<td class="text-center"><input type="text" class="form-control "
																	   id="newUnit-legalppersoncard"></td>
										<td class="text-center" style="overflow: hidden;"><input type="text"
																								 class="form-control " id="newUnit-legalpersonphone"></td>
									</tr>
									<tr>
										<td class="text-right">消防安全责任人姓名</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-saferesponsiblepersonname"></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-saferesponsiblepersoncard">
										</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-saferesponsiblepersonphone"></td>
									</tr>
									<tr>
										<td class="text-right">消防安全管理人姓名</td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-safemanagername"></td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-safemanagercard"></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-safemanagerphone"></td>
									</tr>
									<tr>
										<td class="text-right">专兼职消防管理人姓名</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-assistmanagername"></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-assistmanagercard"></td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-assistmanagerphone"></td>
									</tr>
									<tr>
										<td class="text-right">用户代表</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-userrepresentative"></td>
									</tr>
									<tr>
										<td class="text-right">技术代表 </td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-technicalrepresentative"></td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="21%">
										<col width="21%">
										<col width="21%">
										<col width="22%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">相关单位</strong></td>
									</tr>
									<tr>
										<td class="text-right">责任单位</td>
										<td class="text-center" style="overflow: hidden;">单位名称</td>
										<td class="text-center">联系人</td>
										<td class="text-center" style="overflow: hidden;">联系电话</td>
										<td class="text-center">时间</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">维保单位
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">报警主机厂商
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">联网运维单位
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="">
										</td>
									</tr>
									<tr>
										<td class="text-right">消防管辖单位
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control " id="newUnit-administunit"></td>
										<td class="text-left"><input type="text" class="form-control " id="">
										</td>
										<td class="text-left"><input type="text" class="form-control " id="">
										</td>
										<td class="text-left"><input type="text" class="form-control " id="">
										</td>
									</tr>
									<tr>
										<td class="text-right">监测单位
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-monitorunitname"></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-monitorunituser"></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-monitorunitphone"></td>
										<td class="text-left"><input type="text" class="form-control" id="">
										</td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="35%">
										<col width="15%">
										<col width="35%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">消控室信息</strong></td>
									</tr>
									<tr>
										<td class="text-right">消防控制室电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="newUnit-fireroomphone">
										</td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
									<tr>
										<td class="text-right"><strong class="explain">其他配置</strong></td>
									</tr>
									<tr>
										<td class="text-right">短信接收手机<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
                                                <textarea rows="3" cols="" id="newUnit-messagephone"
														  class="form-control inputNormal"
														  placeholder="输入多个号码时请用英文逗号隔开"
														  onkeyup="this.value=this.value.replace(/[^\d\,]/g,'')">
                                                </textarea></td>
										<td class="text-right">灭火器过期提前提醒天数<strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" placeholder="请输入数字"
																							   onkeyup="value=value.replace(/[^\d]/g,'')"
																							   id="newUnit-advancereminderday"></td>
									</tr>
									<c:if test="${userSession.unitId == null }">
										<tr>
											<td class="text-right">短信每月发送总数<strong class="explain">:</strong>
											</td>
											<td class="text-left" style="overflow: hidden;"><input type="text"
																								   class="form-control inputNormal" id="newUnit-messagenumber"
																								   placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')">
											</td>
										</tr>
									</c:if>
									<tr>
										<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;" colspan="3">
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype1" value="1">火警&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype2" value="2">故障&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype3" value="3">启动&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype4" value="4">反馈&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype5" value="5">监管&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype6" value="6">屏蔽&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype8" value="8">复位&nbsp;
											<input type="checkbox" name="ReceiveAlarmType"
												   id="newUnit-receivealarmtype9" value="9">其他
										</td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>


									<tr>
										<td class="text-right">是否自动拨打电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<input type="radio" name="isAutoCall" id="newUnit-isautocall1"
												   value="1">是&nbsp;
											<input type="radio" name="isAutoCall" id="newUnit-isautocall0"
												   value="0">否
										</td>
										<%--	<td class="text-right">自动拨打延时(min)<strong class="explain">:</strong>
                                            </td>-
                                            <td class="text-left" style="overflow: hidden;"><input type="text"
                                                                                                   class="form-control inputNormal" id="newUnit-autocalldelay"
                                                                                                   placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')">
                                            </td>--%>
										<td class="text-right">电话每月拨打总数<strong>:</strong></td>
										<td class="text-right"><input type="text" class="form-control inputNormal " id="newUnit-callTotal" placeholder=" 请输入数字 " onkeyup="value=value.replace(/[^\d]/g,'')"></td>
									</tr>
									<tr>
										<td class="text-right">自动拨打号码:<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
                                                <textarea rows="3" cols="" id="newUnit-autoCallNum"
														  class="form-control inputNormal"
														  placeholder="输入多个号码时请用英文逗号隔开"
														  onkeyup="this.value=this.value.replace(/[^\d\,]/g,'')">
                                                </textarea></td>
									</tr>



									<tr>
										<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;" colspan="3">
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType1" value="1">火警&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType2" value="2">故障&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType3" value="3">启动&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType4" value="4">反馈&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType5" value="5">监管&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType6" value="6">屏蔽&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType8" value="8">复位&nbsp;
											<input type="checkbox" name="callAlarmType"
												   id="newUnit-callAlarmType9" value="9">其他
										</td>
									</tr>

									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>

									<tr>
										<td class="text-right"><strong class="explain">萤石云配置</strong></td>
									</tr>
									<tr>
										<td class="text-right">萤石云appKey<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal" id="newUnit-videoAppkey" >
										</td>
										<td class="text-right">萤石云appSecret<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text"
																	 class="form-control inputNormal" id="newUnit-videoAppsecret" >
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!--新增维保-->
			<div class="modal-body modalWB" hidden>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位编号<font style="color: red">*</font><strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;">
											<input type="text" class="form-control inputNormal" id="unitCode" oninput="value=value.replace(/[^0-9A-Z]/g,'');"/>
										</td>
										<td class="text-right">单位名称<font style="color: red">*</font><strong class="explain">:</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="unitName">
										</td>
									</tr>
									<tr>
										<td class="text-right">系统编号<strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="systemNumber" disabled="disabled">
										</td>
										<td class="text-right">单位地址<strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="address">
										</td>
									</tr>
									<tr>
										<td class="text-right">联系人<strong class="explain">:</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="contacts">
										</td>
										<td class="text-right">联系电话<strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="telephone">
										</td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">:</strong>
										</td>
										<td class="text-left">
											<select id="proviceid" name="proviceid" class="form-control inputNormal">
											</select>
										</td>
										<td class="text-right">市<strong class="explain">:</strong>
										</td>
										<td class="text-left">
											<select id="cityid" name="cityid" class="form-control inputNormal">
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">:</strong>
										</td>
										<td class="text-left">
											<select id="regionid" name="regionid" class="form-control inputNormal">
											</select>
										</td>

										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="townid" id="townid"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">邮箱<strong class="explain">:</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="email">
										</td>
										<td></td>
										<td></td>

									</tr>


									<tr>
										<td class="text-right">坐标X<strong class="explain">:</strong>
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="pointX">
										</td>
										<td class="text-right">坐标Y<strong class="explain">:</strong>
										</td>
										<!-- <td class="text-left">
                            <input type="text" class="form-control inputNormal" id="pointY" >
                        </td> -->
										<td class="text-left"><input type="text" class="form-control inputNormal" name="pointY" id="pointY">
											<a href="#" onclick="showMap();">位置标注</a>
										</td>
									</tr>
									<tr>
										<td class="text-right">备注<strong class="explain">:</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="remark">
										</td>
										<td class="text-right">单位logo<strong class="explain">:</strong>
										</td>
										<td class="text-left"><input id="networking-photoCover-unitLogo" type="text" style="display: none;">
											<div class="wrapTypeMore">
												<input id="networking-picurls-unitLogo" name="imageDataList" type="file"
													   class="file typeFileMore" value=""
													   onchange="doChangeProjectunitLogo1()" />
												<ul id="networking-loadImg-unitLogo" class="loadImg">
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

			<!--新增监管-->
			<div class="modal-body modalJG" hidden>
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位编号<font style="color: red">*</font><strong
												class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="supervise-unitCode">
										</td>
										<td class="text-right">单位名称<font style="color: red">*</font><strong
												class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-unitName"></td>
									</tr>
									<tr>
										<td class="text-right">系统编号<strong>:</strong></td>
										<td class="text-left">
											<input type="text" class="form-control inputNormal" id="supervise-systemNumber" disabled="disabled">
										</td>
										<td class="text-right">单位地址<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-address">
										</td>
									</tr>
									<tr>
										<td class="text-right">联系人<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="supervise-contacts"></td>
										<td class="text-right">联系电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="supervise-telephone">
										</td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-proviceid" name="supervise-proviceid"
													class="form-control inputNormal">
											</select>
										</td>
										<td class="text-right">市<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-cityid" name="supervise-cityid" class="form-control inputNormal">
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-regionid" name="supervise-regionid" class="form-control inputNormal">
											</select>
										</td>

										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="supervise-townid" id="supervise-townid"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>

									</tr>
									<tr>
										<td class="text-right">邮箱<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-email"></td>
										<td></td>
									</tr>


									<tr>
										<td class="text-right">坐标X<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="supervise-pointX">
										</td>
										<td class="text-right">坐标Y<strong class="explain">
											:</strong></td>
										<!-- <td class="text-left">
                            <input type="text" class="form-control inputNormal" id="pointY" >
                        </td> -->
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 name="pointY" id="supervise-pointY">
											<a href="#" onclick="showMap();">位置标注</a></td>
									</tr>
									<tr>
										<td class="text-right">备注<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-remark"></td>
										<td class="text-right">单位logo<strong class="explain">:</strong></td>
										<td class="text-left"><input id="supervise-photoCover-unitLogo" type="text"
																	 style="display: none;">
											<div class="wrapTypeMore">
												<input id="supervise-picurls-unitLogo" name="imageDataList" type="file"
													   class="file typeFileMore" value=""
													   onchange="doChangeProjectunitLogo2()" />
												<ul id="supervise-loadImg-unitLogo" class="loadImg">
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

<!-- 查看模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">

	<!-- 查看联网单位 -->
	<div class="modal-dialog" role="document" id="firstViewModal" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看联网单位</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<input type="hidden" id="id">
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="35%">
										<col width="15%">
										<col width="35%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">单位基础信息</strong></td>
									</tr>
									<tr>
										<td class="text-right">维保和监管单位<font style="color: red">*</font><strong>
											:</strong>
										</td>
										<td class="text-left">
											<select class="form-control inputNormal selectpicker" id="newUnit-view-maintenanceUnit" multiple data-live-search="true" data-size="10">
											</select>
										</td>
										<td class="text-right">单位名称<strong class="explain">:</strong></td>
										<td class="text-left"><input type="text" disabled="disabled" class="form-control inputNormal" id="newUnit-view-systemNumber"></td>
									</tr>
									<tr>
										<td class="text-right">单位名称<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="newUnit-view-unitname"></td>
										<td class="text-right">单位编号<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-unitcode"></td>
									</tr>
									<tr>
										<td class="text-right">单位地址<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-unitaddress"></td>
										<td class="text-right">联系电话<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="newUnit-view-phone"></td>
									</tr>
									<tr>
										<td class="text-right">子站号 <strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-childstationnum" placeholder="请输入数字"
																							   onkeyup="value=value.replace(/[^\d]/g,'')"></td>
										<td class="text-right">经纬度 <strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   name="newUnit-unitpoint" id="newUnit-view-unitpoint"></td>
									</tr>
									<tr>
										<td class="text-right">组织机构代码<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-OrgCode"></td>
										<td class="text-right">单位类别<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="radio" disabled="disabled"
																	 name="newUnit-view-important" value="1"
																	 id="newUnit-view-important1">重点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newUnit-view-important" value="0"
												   disabled="disabled" id="newUnit-view-important0"
												   checked="checked">非重点</td>
									</tr>
									<tr>
										<td class="text-right">联网状态<strong class="explain">:</strong></td>
										<td class="text-left"><input type="radio" disabled="disabled"
																	 name="newUnit-view-networkstatus" value="1"
																	 id="newUnit-view-networkstatus1"
																	 checked="checked">联网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newUnit-view-networkstatus" value="0"
												   disabled="disabled" id="newUnit-view-networkstatus0">未联网</td>
										<td class="text-right">联网时间 <strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-view-onlinedate" disabled="disabled" readonly></td>
									</tr>
									<tr>
										<td class="text-right">单位类型<strong class="explain">:</strong></td>
										<td class="text-left"><select id="newUnit-view-unittype" disabled="disabled"
																	  name="newUnit-view-unittype" class="form-control inputNormal">
											<option value="" selected="selected">---请选择---</option>
										</select>
										</td>
										<td class="text-right">成立时间<strong> :</strong>
										</td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="newUnit-view-establishmenttime" readonly></td>
									</tr>
									<tr>
										<td class="text-right">监管等级<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<select id="newUnit-view-superviselevel"
													class="form-control inputNormal" disabled="disabled">
												<option value="重要">重要</option>
												<option value="一般">一般</option>
												<option value="次要">次要</option>
											</select>
										</td>
										<td class="text-right">监管类型<strong class="explain">
											:</strong></td>
										<td class="text-left"><select id="newUnit-view-supervisetype"
																	  name="newUnit-view-supervisetype" disabled="disabled"
																	  class="form-control inputNormal">
											<option value="">---请选择---</option>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<!-- <input type="text"
                                            class="form-control inputNormal" id="newUnit-view-proviceID"> -->
											<select name="newUnit-view-proviceid" id="newUnit-view-proviceid"
													disabled="disabled" class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
										<td class="text-right">市<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<!-- <input
                                            type="text" class="form-control inputNormal"
                                            id="newUnit-view-dataFrom"> --> <select name="newUnit-view-cityid" disabled="disabled"
																					id="newUnit-view-cityid" class="form-control inputNormal">
											<option value="">--请选择---</option>
										</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="newUnit-view-regionid" id="newUnit-view-regionid"
													class="form-control inputNormal" disabled="disabled">
												<option value="">--请选择---</option>
											</select>
										</td>
										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select name="newUnit-view-townid" id="newUnit-view-townid"
													class="form-control inputNormal" disabled="disabled">
												<option value="">--请选择---</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">危险等级2<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="newUnit-view-unitdangerlevel"
													class="form-control inputNormal" disabled="disabled">
												<option value="一级">一级</option>
												<option value="二级">二级</option>
												<option value="三级">三级</option>
											</select>
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="text-right">火灾等级<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="newUnit-view-firerating" class="form-control inputNormal"
													disabled="disabled">
												<option value="很严重">很严重</option>
												<option value="严重">严重</option>
												<option value="不严重">不严重</option>
											</select>
										</td>
										<td class="text-right">单位毗邻情况<strong class="explain">:</strong></td>
										<td class="text-left">
											<div class="wrapTypeMore">
												<ul id="loadImg-view-unitproximity" class="loadImg">
													<!--                                                         <li class="modalImg"><span>请选择上传图片</span></li> -->
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td class="text-right">合同文书<strong> :</strong>
										</td>
										<td class="text-left">
											<div class="wrapExport">
											</div> <span class="fileNameView" style="margin-left: 12px;"></span>
											<input type="hidden" id="attachmentView"> <input type="hidden"
																							 disabled="disabled" id="attachmentNameView">
										</td>
										<td class="text-right">单位logo<strong class="explain">:</strong></td>
										<td class="text-left">
											<div class="wrapTypeMore">
												<ul id="loadImg-view-unitLogo" class="loadImg">
													<!--                                                         <li class="modalImg"><span>请选择上传图片</span></li> -->
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td class="text-right">单位链接<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" disabled="disabled" class="form-control inputNormal" id="newUnit-view-link"></td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>

								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="17%">
										<col width="28%">
										<col width="28%">
										<col width="28%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">单位人员</strong></td>
									</tr>
									<tr>
										<td class="text-right">人员类别</td>
										<td class="text-center" style="overflow: hidden;">姓名</td>
										<td class="text-center">身份证号</td>
										<td class="text-center" style="overflow: hidden;">联系电话</td>
									</tr>
									<tr>
										<td class="text-right">法人代表姓名</td>
										<td class="text-center" style="overflow: hidden;"><input disabled="disabled"
																								 type="text" class="form-control " id="newUnit-view-legalpersonname">
										</td>
										<td class="text-center"><input type="text" disabled="disabled"
																	   class="form-control " id="newUnit-view-legalppersoncard"></td>
										<td class="text-center" style="overflow: hidden;"><input type="text"
																								 class="form-control " disabled="disabled"
																								 id="newUnit-view-legalpersonphone"></td>
									</tr>
									<tr>
										<td class="text-right">消防安全责任人姓名</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control" id="newUnit-view-saferesponsiblepersonname">
										</td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control" id="newUnit-view-saferesponsiblepersoncard">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control" id="newUnit-view-saferesponsiblepersonphone">
										</td>
									</tr>
									<tr>
										<td class="text-right">消防安全管理人姓名</td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control" id="newUnit-view-safemanagername">
										</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-view-safemanagercard"></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control" id="newUnit-view-safemanagerphone">
										</td>
									</tr>
									<tr>
										<td class="text-right">专兼职消防管理人姓名</td>
										<td class="text-left"><input type="text" class="form-control"
																	 id="newUnit-view-assistmanagername"></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control"
																							   id="newUnit-view-assistmanagercard"></td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control" id="newUnit-view-assistmanagerphone"></td>
									</tr>
									<tr>
										<td class="text-right">用户代表</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control" id="newUnit-view-userrepresentative"></td>
									</tr>
									<tr>
										<td class="text-right">技术代表 </td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control"
																							   id="newUnit-view-technicalrepresentative"></td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="21%">
										<col width="21%">
										<col width="21%">
										<col width="22%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">相关单位</strong></td>
									</tr>
									<tr>
										<td class="text-right">责任单位</td>
										<td class="text-center" style="overflow: hidden;">单位名称</td>
										<td class="text-center">联系人</td>
										<td class="text-center" style="overflow: hidden;">联系电话</td>
										<td class="text-center">时间</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">维保单位
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">报警主机厂商
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
									</tr>
									<tr style="display: none">
										<td class="text-right">联网运维单位
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="">
										</td>
									</tr>
									<tr>
										<td class="text-right">消防管辖单位
										</td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control " id="newUnit-view-administunit">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control " id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control " id="">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control " id="">
										</td>
									</tr>
									<tr>
										<td class="text-right">监测单位
										</td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control" id="newUnit-view-monitorunitname">
										</td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control" id="newUnit-view-monitorunituser">
										</td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control" id="newUnit-view-monitorunitphone">
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control" id="">
										</td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
								</table>
								<table class="table tableNo">
									<colgroup>
										<col width="15%">
										<col width="35%">
										<col width="15%">
										<col width="35%">
									</colgroup>
									<tr>
										<td class="text-right"><strong class="explain">消控室信息</strong></td>
									</tr>
									<tr>
										<td class="text-right">消防控制室电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-fireroomphone"></td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
									<tr>
										<td class="text-right"><strong class="explain">其他配置</strong></td>
									</tr>
									<tr>
										<td class="text-right">短信接收手机<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
                                                <textarea rows="3" disabled="disabled" cols=""
														  id="newUnit-view-messagephone" class="form-control inputNormal"
														  placeholder="输入多个号码时请用英文逗号隔开"
														  onkeyup="this.value=this.value.replace(/[^\d\,]/g,'')">
											</textarea></td>
										<td class="text-right">灭火器过期提前提醒天数<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal" placeholder="请输入数字"
																							   onkeyup="value=value.replace(/[^\d]/g,'')"
																							   id="newUnit-view-advancereminderday"></td>
									</tr>
									<tr>
										<td class="text-right">短信每月发送总数<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input disabled="disabled"
																							   type="text" class="form-control inputNormal"
																							   id="newUnit-view-messagenumber" placeholder="请输入数字"
																							   onkeyup="value=value.replace(/[^\d]/g,'')"></td>
									</tr>
									<tr>
										<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;" colspan="3">
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype1" value="1"
												   disabled="disabled">火警&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype2" value="2"
												   disabled="disabled">故障&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype3" value="3"
												   disabled="disabled">启动&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype4" value="4"
												   disabled="disabled">反馈&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype5" value="5"
												   disabled="disabled">监管&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype6" value="6"
												   disabled="disabled">屏蔽&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype8" value="8"
												   disabled="disabled">复位&nbsp;
											<input type="checkbox" name="ReceiveAlarmTypeView"
												   id="newUnit-view-receivealarmtype9" value="9" disabled="disabled">其他
										</td>
									</tr>
									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>
									<tr>
										<td class="text-right">是否自动拨打电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
											<input type="radio" name="isAutoCall" id="newUnit-view-isautocall1"
												   value="1" disabled="disabled">是&nbsp;
											<input type="radio" name="isAutoCall" id="newUnit-view-isautocall0"
												   value="0" disabled="disabled">否
										</td>
										<%--	<td class="text-right">自动拨打延时(min)<strong class="explain">:</strong></td>
                                            <td class="text-left" style="overflow: hidden;"><input type="text"
                                                                                                   class="form-control inputNormal" id="newUnit-view-autocalldelay"
                                                                                                   placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')"
                                                                                                   readonly="readonly"></td>
    --%>
										<td class="text-right">电话每月拨打总数<strong>:</strong></td>
										<td class="text-right"><input type="text" disabled="disabled" class="form-control inputNormal" id="newUnit-view-callTotal" placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')"></td>
									</tr>
									<tr>
										<td class="text-right">自动拨打号码<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;">
                                                <textarea rows="3" disabled="disabled" cols=""
														  id="newUnit-view-autoCallNum" class="form-control inputNormal"
														  placeholder="输入多个号码时请用英文逗号隔开"
														  onkeyup="this.value=this.value.replace(/[^\d\,]/g,'')">
											</textarea></td>
									</tr>


									<tr>
										<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;" colspan="3">
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType1" value="1"
												   disabled="disabled">火警&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType2" value="2"
												   disabled="disabled">故障&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType3" value="3"
												   disabled="disabled">启动&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType4" value="4"
												   disabled="disabled">反馈&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType5" value="5"
												   disabled="disabled">监管&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType6" value="6"
												   disabled="disabled">屏蔽&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType8" value="8"
												   disabled="disabled">复位&nbsp;
											<input type="checkbox" name="callAlarmTypeView"
												   id="newUnit-view-callAlarmType9" value="9" disabled="disabled">其他
										</td>
									</tr>

									<tr style="height: 10px;">
										<td colspan="6">
											<p class="borderTop"></p>
										</td>
									</tr>

									<tr>
										<td class="text-right"><strong class="explain">萤石云配置</strong></td>
									</tr>
									<tr>
										<td class="text-right">萤石云appKey<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="newUnit-view-videoAppkey" >
										</td>
										<td class="text-right">萤石云appSecret<strong>
											:</strong>
										</td>
										<td class="text-left"><input type="text" disabled="disabled"
																	 class="form-control inputNormal" id="newUnit-view-videoAppsecret" >
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
				<button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>

	<!-- 查看维保单位 -->
	<div class="modal-dialog" role="document" id="wrapModalCheck" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位编号<font style="color: red">*</font><strong
												class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="unitCode-View">
										</td>
										<td class="text-right">单位名称<font style="color: red">*</font><strong
												class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="unitName-View"></td>
									</tr>
									<tr>
										<td class="text-right">单位地址<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="systemNumber-View">
										</td>
										<td class="text-right">单位地址<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="address-View">
										</td>
									</tr>
									<tr>
										<td class="text-right">联系人<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="contacts-View"></td>
										<td class="text-right">联系电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="telephone-View">
										</td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="proviceid-View" name="proviceid-View"
													class="form-control inputNormal">
											</select>
										</td>
										<td class="text-right">市<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="cityid-View" name="cityid-View"
													class="form-control inputNormal">
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="regionid-View" name="regionid-View"
													class="form-control inputNormal">
											</select>
										</td>

										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="townid-View" name="townid-View"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">邮箱<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="email-View"></td>
										<td></td>
										<td></td>
									</tr>


									<tr>
										<td class="text-right">坐标X<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text"
																							   class="form-control inputNormal" id="pointX-View">
										</td>
										<td class="text-right">坐标Y<strong class="explain">
											:</strong></td>
										<!-- <td class="text-left">
                            <input type="text" class="form-control inputNormal" id="pointY" >
                        </td> -->
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 name="pointY" id="pointY-View">
									</tr>
									<tr>
										<td class="text-right">备注<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="remark-View"></td>
										<td class="text-right">单位logo<strong class="explain">:</strong></td>
										<td class="text-left">
											<div class="wrapTypeMore">
												<ul id="networking-loadImg-view-unitLogo" class="loadImg">
													<!--                                                         <li class="modalImg"><span>请选择上传图片</span></li> -->
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
				<button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>

	<!-- 查看监管单位 -->
	<div class="modal-dialog" role="document" id="superviseView" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看</h4>
			</div>
			<div class="modal-body">
				<div class="container">
					<div class="row">
						<div class="col-md-10">
							<div class="">
								<table class="table tableNo">
									<tr>
										<td class="text-right">单位编号<font style="color: red">*</font><strong
												class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-unitCode-View">
										</td>
										<td class="text-right">单位名称<font style="color: red">*</font><strong
												class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-unitName-View"></td>
									</tr>
									<tr>
										<td class="text-right">系统编号<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-systemNumber-View">
										</td>
										<td class="text-right">单位地址<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-address-View">
										</td>
									</tr>
									<tr>
										<td class="text-right">联系人<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-contacts-View"></td>
										<td class="text-right">联系电话<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-telephone-View">
										</td>
									</tr>
									<tr>
										<td class="text-right">省<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-proviceid-View" name="supervise-proviceid-View"
													class="form-control inputNormal">
											</select>
										</td>
										<td class="text-right">市<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-cityid-View" name="supervise-cityid-View"
													class="form-control inputNormal">
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">区<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-regionid-View" name="supervise-regionid-View"
													class="form-control inputNormal">
											</select>
										</td>

										<td class="text-right">街道<strong class="explain">
											:</strong></td>
										<td class="text-left">
											<select id="supervise-townid-View" name="supervise-townid-View"
													class="form-control inputNormal">
												<option value="">--请选择---</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="text-right">邮箱<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal" id="supervise-email-View"></td>
										<td></td>
										<td></td>
									</tr>


									<tr>
										<td class="text-right">坐标X<strong class="explain">:</strong></td>
										<td class="text-left" style="overflow: hidden;"><input type="text" class="form-control inputNormal" id="supervise-pointX-View">
										</td>
										<td class="text-right">坐标Y<strong class="explain">
											:</strong></td>
										<!-- <td class="text-left">
                            <input type="text" class="form-control inputNormal" id="pointY" >
                        </td> -->
										<td class="text-left"><input type="text" class="form-control inputNormal" name="pointY" id="supervise-pointY-View">
									</tr>
									<tr>
										<td class="text-right">备注<strong class="explain">
											:</strong></td>
										<td class="text-left"><input type="text" class="form-control inputNormal"
																	 id="supervise-remark-View"></td>
										<td class="text-right">单位logo<strong class="explain">:</strong></td>
										<td class="text-left">
											<div class="wrapTypeMore">
												<ul id="supervise-loadImg-view-unitLogo" class="loadImg">
													<!--                                                         <li class="modalImg"><span>请选择上传图片</span></li> -->
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
				<button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>


<!-- 添加角色模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="setRoleModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">查看维保人员</h4>
			</div>
			<div class="modal-body">
				<div class="content" style="float: left; text-align: center; margin-right: 10px;">
					<h3 style="line-height: 16px; text-align: center; font-size: 14px">未选择</h3>
					<select multiple="multiple" id="select1"
							style="width: 250px; height: 167px; padding: 5px; overflow: auto; display: block">


					</select> <span id="remove"
									style="display: inline-block; width: 100px; background: #eee; cursor: pointer; border: 1px solid #ccc; padding: 5px 0; margin: 5px 0;">选中右移>></span>
					<span id="remove_all"
						  style="display: inline-block; width: 100px; background: #eee; cursor: pointer; border: 1px solid #ccc; padding: 5px 0; margin: 5px 0;">全部右移>></span>
				</div>

				<div class="content" style="float: left; text-align: center; margin-right: 10px;">
					<h3 style="line-height: 16px; text-align: center; font-size: 14px">已选择</h3>
					<select multiple="multiple" id="select2"
							style="width: 250px; height: 167px; padding: 5px; overflow: auto; display: block">
					</select> <span id="add"
									style="display: inline-block; width: 100px; background: #eee; cursor: pointer; border: 1px solid #ccc; padding: 5px 0; margin: 5px 0;">&lt;&lt;选中左移</span>
					<span id="add_all"
						  style="display: inline-block; width: 100px; background: #eee; cursor: pointer; border: 1px solid #ccc; padding: 5px 0; margin: 5px 0;">&lt;&lt;全部左移</span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary btn-save_role">
					<i class="fa fa-floppy-o"></i>&nbsp;保存
				</button>

				<button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close">
					<i class="fa fa-times"></i>&nbsp;关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<!-- 地图模态框（Modal） -->
<div class="modal fade notLastModal" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-dialog" id="secondModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">地图标点</h4>
			</div>
			<div class="modal-body" id="maps" style="height: 400px;">
				在这里添加一些文本</div>
			<div class="modal-footer">
				<input type="text" id="map_search" placeholder="请输入地点">
				<button type="button" class="btn btn-primary btn-search">定位
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

<input type="hidden" name="supervise-indexId" id="supervise-indexId" value="">

<!--区分编辑和更新-->
<input type="hidden" id="editId" value="0">

<!-- 保存单位编号 -->
<input type="hidden" id="unitId">

<!-- 判断是否联网单位管理员，如果是，则保存联网单位ID -->
<input type="hidden" id="networkUnitId" value="${userSession.unitId }">



</body>

<%--<script>
    $(function(){
        if($("#networkUnitId").val() != null){
            $(".modalLW").find("tr:eq(1)").hide();
            $(".firstViewModal").find("tr:eq(1)").hide();
        }
    })
</script>--%>

</html>
