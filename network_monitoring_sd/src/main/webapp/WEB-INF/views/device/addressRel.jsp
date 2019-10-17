<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>联网设备地址管理</title>
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
						<li class="no_active"><span class="title">联网设备地址管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">部位号<strong>
										:</strong></label> <input type="text" class="form-control" name="partcode"
									id="partcode">
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
									<option value="partcode">部位号</option>
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
							<button type="button" class="btn btn-new importBuilding" data-toggle="modal"
                                  data-target="#importBuilding"><i
                                  class="fa fa-cloud-download"></i>&nbsp;导入设备地址
                          </button>
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
					<h4 class="modal-title">新增联网设备地址</h4>
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
											<td class="text-left"><select name="newnetDeviceRel-unitid" id="newnetDeviceRel-unitid" class="form-control inputNormal unitid" onchange="getNetDeviceList(this.options[this.options.selectedIndex].value)"></select>
											</td>
										</tr>
										<tr>
											<td class="text-right">设备<strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newnetDeviceRel-eqid" id="newnetDeviceRel-eqid" class="form-control inputNormal"></select>
											</td>
										</tr>
										<tr>
											<td class="text-right">部位号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newnetDeviceRel-partcode" >
											</td>
										</tr>
										<tr>
											<td class="text-right password">真实地址<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newnetDeviceRel-adress" >
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
					<button type="button" class="btn btn-primary" id="continue" >
						<i class="fa fa-floppy-o"></i>&nbsp;保存并继续
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
<div class="modal fade"  tabindex="-1"  role="dialog" id="importBuilding">
    <div class="modal-dialog"  role="document" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">导入</h4>
            </div>
            <div class="modal-body" style="height: auto;">
                    <div class="row">
                        <div class="col-md-12">
                            <form id="projectForm" class="importArea">
                                <div>
                                	<span class="col-md-4">选择单位</span>
                                	<select id="importunitid" name="importunitid" class="unitid" style="width: 38%">
                                	</select>
                                </div>
                                <div>
                                	<span class="col-md-4">选择联网设备</span>
                                	<select id="importeqid" name="importeqid" class="eqid" style="width: 38%">
                                	</select>
                                </div>
                                <div>
                                	<p class="col-md-offset-2"></p>
                                </div>
                                <div>
                                	<span class="col-md-2">第一步</span>
                                	 <a href='${ctx}/common/download/address' ><span
                                        class="btn btn-sm downTemp btn-new"><i
                                        class="fa fa-download"></i>下载模板</span></a>
                                </div>
                                <div >
                                	<p class="col-md-offset-2">请根据模板格式填写内容，然后导入</p>
                                </div>
                                <div >
                                	<span class="col-md-2">第二步</span>
                                	 <div class="wrapExport">
	                                    <input type="file" name="uploadExcel" onchange="doChange(this)" class="changeInput uploadExcel"/>
	                                     <button type="button" class="btn btn-new btn-sm btn-new chooseFile" ><i
                                        class="fa fa-arrow-up"></i>&nbsp;上传</button>
                              		</div> 
                              		 <span class="fileName"></span>
                                </div>
                                <div >
<!--                                 	<p class="col-md-offset-2">上传填写好的。</p> -->
                                </div>
                            </form>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-new btn-sm upTemp btnImport" >开始导入</button>
            </div>
        </div>
    </div>
</div>
	
	
	
	<!-- 员工查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
		<div class="modal-dialog" role="document" id="secondViewModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看人员</h4>
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
											<td class="text-left"><select name="newnetDeviceRelView-unitid" id="newnetDeviceRelView-unitid" class="form-control inputNormal unitid"></select>
											</td>
										</tr>
										<tr>
											<td class="text-right">设备<strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newnetDeviceRelView-eqid" id="newnetDeviceRelView-eqid" class="form-control inputNormal"></select>
											</td>
										</tr>
										<tr>
											<td class="text-right">部位号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newnetDeviceRelView-partcode" >
											</td>
										</tr>
										<tr>
											<td class="text-right password">真实地址<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newnetDeviceRelView-adress" >
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
	
	
	<!--关联项目-->
<div class="modal fade"  tabindex="-1"  role="dialog" id="relationProject">
    <div class="modal-dialog"  role="document"  id="third-modal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">关联项目</h4>
            </div>
            <div class="modal-body" style="height: auto;">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="userList scrollStyle">
                            	<p>已关联项目</p>
			                    <table class="table table-bordered" id="checkTable" data-click-to-select="true">
			
			                    </table>
			                </div>
                            <div class="userList scrollStyle" >
                            	<p>未关联项目</p>
			                    <table class="table table-bordered" id="noCheckTable" data-click-to-select="true">
									
			                    </table>
			                </div>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</div>

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/device/addressRel.js"></script>
</html>