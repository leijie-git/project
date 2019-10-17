<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>无线设备</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet"
	href="/css/page_css/Employee_management.css">
	<script type="text/javascript"
    src="http://api.map.baidu.com/getscript?v=2.0&ak=uAVQruFnlAevcIBVA89lt02GH5kLkUXd&services="></script>
	<%--<style>--%>
		<%--.Width14{--%>
			<%--width: 14%;--%>
		<%--}--%>
		<%--.Width36{--%>
			<%--width: 36%;--%>
		<%--}--%>
	<%--</style>--%>
</head>
<body>

	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">设备管理</span></li>
						<li class="no_active"><span class="title">无线设备管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">网关ID<strong>
										:</strong></label> <input type="text" class="form-control" name="partcode"
									id="partcode">
									<label for="identityNo" class="control-label">关键字<strong>
                                        :</strong></label> <input type="text" class="form-control" name="keyword"
                                    id="keyword">
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
								    <option value="partcode">网关ID</option>
									<option value="keyword">关键字</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
							<button type="button" class="btn btn-new importBuilding" data-toggle="modal"
                                  data-target="#importBuilding"><i
                                  class="fa fa-cloud-download"></i>&nbsp;导入设备
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
					<h4 class="modal-title">编辑无线设备信息</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
									   <tr>
										   <td class="text-right">绑定联网设备<strong>
											   :</strong>
										   </td>
										   <td>
											   <select id="importNetDevice" class="form-control inputNormal" >
											   </select>
										   </td>
                                        </tr>
                                        <tr>
										   <td class="text-right">网关ID<font style="color: red">*</font><strong>
											   :</strong>
										   </td>
										   <td class="text-left"><input type="text"
																		class="form-control inputNormal" id="gatewaycode" >
										   </td>
                                        </tr>
                                        <tr>
											<td class="text-right">二维码编号<strong>
												:</strong>
											</td>
											<td class="text-left"><input type="text"
																		 class="form-control inputNormal" id="devicecode" >
											</td>
                                        </tr>
                                        <tr>
											<td class="text-right">安装地点<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
																		 class="form-control inputNormal" id="installaddr">
                                        </tr>
										<tr>
										    <td class="text-right">经度<strong> :</strong>
                                            </td>
                                            <td class="text-left"><input type="text" 
                                                class="form-control inputNormal" id="newFirePower-pointX">
                                            </td>

										</tr>
										<tr>
											<td class="text-right">纬度<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
																		 class="form-control inputNormal" id="newFirePower-pointY"><a
													href="#" onclick="showMap()">标点</a></td>
										</tr>
										<tr>
											<td class="text-right password">通知电话<strong> :</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="notifyphone" >
											</td>
										</tr>
										<tr>
											<td class="text-right password">自动拨号<strong>
												:</strong>
											</td>
											<td class="text-left password">
												<span style="margin-right:5px">是</span><input id="AutomaticDialing1" name="AutomaticDialing" style="vertical-align:text-bottom; width:17px;height:17px; margin-right:20px" type="radio" value="1">
												<span style="margin-right:5px">否</span><input id="AutomaticDialing0" name="AutomaticDialing" style="vertical-align:text-bottom; width:17px;height:17px;" type="radio" value="0">

											</td>
										</tr>
										<!-- <tr>
										  <td class="text-right password">心跳时间<strong> :</strong>
											</td>
                                            <td class="text-left password"><input type="text"
                                                class="form-control inputNormal" id="heartbeat"
                                                onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
											</td>
										</tr> -->
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btnUpdate">
						<i class="fa fa-floppy-o"></i>&nbsp;更新
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
                                <div style="margin-bottom:50px;">
                                    <span class="col-md-4">设备类型：</span>
                                    <select id="DeviceType" style="width:30%;">
                                           <option value="">--请选择--</option>
                                           <option value="1">NB设备</option>
                                    </select>
                                    <input type="hidden" name="usingtype" id="usingtype">
                                </div>
                               <!--  <div style="margin-bottom:50px;">
                                    <span class="col-md-4 text-right" >心跳时间：</span>
                                    <input   name="heartbeats" style="width:30%;" 
                                    onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                    class=" inputNormal form-control text-left" id="heartbeats">
                                </div> -->
                                <%-- <div>
                                	<span class="col-md-4">第一步：</span>
                                	 <a href='${ctx}/common/download/wirelessDevice' ><span
                                        class="btn btn-sm downTemp btn-new"><i
                                        class="fa fa-download"></i>下载模板</span></a>
                                </div>
                                <div >
                                	<p class="col-md-offset-4" style="color:red;">请根据模板格式填写内容，然后导入</p>
                                </div> --%>
                                <div >
                                	<span class="col-md-4">上传模板：</span>
                                	 <div class="wrapExport">
	                                    <input type="file" name="uploadExcel" onchange="doChange(this)" class="changeInput uploadExcel"/>
	                                     <button type="button" class="btn btn-new btn-sm btn-new chooseFile" ><i
                                        class="fa fa-arrow-up"></i>&nbsp;上传</button>
                              		</div> 
                              		 <span class="fileName"></span>
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
	
	
	
	<!-- 网关查看模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
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
                                            <td class="text-right">绑定联网设备<strong>
                                                    :</strong>
                                            </td>
                                            <td>
                                               <select id="importNetDeviceView" readonly="readonly" class="form-control inputNormal" >
                                               </select>
                                            </td>
                                        </tr>
                                        <tr>
											<td class="text-right">网关ID<font style="color: red">*</font><strong>
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
                                       <!--  <tr>
                                            <td class="text-right password">心跳时间<font style="color: red">*</font><strong>
                                                    :</strong>
											</td>
                                            <td class="text-left password"><input type="text" readonly="readonly"
                                                class="form-control inputNormal" id="heartbeatView" >
											</td>
                                        </tr> -->
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
                    <button type="button" class="btn btn-primary map-search" >定位
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <!--                    <button type="button" class="btn btn-primary">确定</button> -->
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
    </div>
    <!-- /.modal -->


	<input type="hidden" name="indexId" id="indexId" value="">
	
	<input type="hidden" name="nsId" id="nsId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/device/wirelessDevice.js"></script>
</html>