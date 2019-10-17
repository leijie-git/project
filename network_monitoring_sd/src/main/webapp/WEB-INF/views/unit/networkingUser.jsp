<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>联网单位人员管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@include file="../inc.jsp"%>
	<link rel="stylesheet" href="/css/page_css/Employee_management.css">
	<script type="text/javascript"src="/js/common/zTree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="/js/common/zTree/jquery.ztree.excheck.js"></script>
	<style>
		#preIdWrapRole{position: relative;
			display: block;
			width: 70%;
			height: 34px;
			padding: 6px 12px;
			font-size: 14px;
			line-height: 1.42857143;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
	</style>
</head>
<body>

<div class="container jy_wrap">
	<div class="row">
		<div class="col-md-12">
			<div class="jy_wrapper">
				<ul class="jy_title">
					<li class="is_active"><span class="title">用户管理</span></li>
					<li class="no_active"><span class="title">联网单位人员管理</span></li>
				</ul>
				<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
					<div class="form-inline">
						<div class="form-group">
							<label for="identityNo" class="control-label">单位名称<strong>
								:</strong></label> <input type="text" class="form-control" name="unitname"
														  id="unitname">
							<label for="identityNo" class="control-label">账号<strong>
								:</strong></label> <input type="text" class="form-control" name="account"
														  id="account">
							<label for="identityNo" class="control-label">员工名<strong>
								:</strong></label> <input type="text" class="form-control" name="username"
														  id="username">
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
								<option value="username">员工名</option>
								<option value="unitname">单位名称</option>
								<option value="account">账号</option>
							</select> <span id="commonInput"><input type="text"
																	class="form-control showInput" name="searchContent"
																	id="searchContent"></span>
						</div>
						<button type="button" class="btn btn-primary ordinarySearch">
							<i class="fa fa-search"></i>查询
						</button>
						<button type="button" class="btn btn-primary seniorSearch">高级搜索</button>
						<%-- <c:if test="${userSession.account != 'admin' }"> --%>
						<button class="btn btn-new newAdd">
							<i class="fa fa-plus"></i>新增
						</button>
						<%-- </c:if> --%>
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
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增联网单位人员</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<colgroup>
											<col width="10%">
											<col width="40%">
											<col width="10%">
											<col width="40%">
										</colgroup>
										<tr>
											<td class="text-right unit">单位选择<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left unit"><select name="newNetworkingUser-unitid" id="newNetworkingUser-unitid" class="form-control inputNormal">
                                                </select>
											</td>
	<td class="text-right architecture "  >建筑<strong> :</strong>
										</td>
										<td class="text-left  architecture">
											<div id="preIdWrapRole" >
												<span></span>
												<ul id="preIdRole" class="ztree"
													style="margin-top: 0; width: 100%;"></ul>
											</div>
										</td>										<td class="text-right role">用户角色<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left role"><select name="newNetworkingUser-userrole" id="newNetworkingUser-userrole" class="form-control inputNormal">
												<option value="0" >单位负责人</option>
												<option value="1" hidden="hidden">单位管理人</option>
												<option value="2" >巡查员</option>
												<option value="3" >工程人员</option>
												<option value="4" >监控人员</option><option value="7" >九小场所</option>
                                                </select>
											</td></tr>
									<tr>
	</tr>
										<tr>										<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-account" >
											</td>

											<td class="text-right password">密码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-password" >
											</td>
											</tr>
									<tr><td class="text-right">手机号码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-mobilephone">
											</td>
										</tr>
										<tr>
											<td class="text-right">性别<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left" >
		                                    	<input type="radio" name="sex" value='1' id="newNetworkingUser-sex1" checked="checked">男
		                                    	<input type="radio" name="sex" value='0' id="newNetworkingUser-sex0">女
		                                    </td>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-username" >
											</td>
										</tr>
										<tr>
											<td class="text-right">生日<font style="color: red"></font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-birthday" readonly="readonly">
											</td>
											<td class="text-right">邮箱<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-email" >
											</td>
										</tr>
										<tr>
											<td class="text-right">职务<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-post" >
											</td>
											<td class="text-right">部门<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-department" >
											</td>
											
										</tr>
										<tr>
											<td class="text-right">证件类型<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-certificatestype" >
											</td>
											<td class="text-right">证件号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-certificatesno" >
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-certificatesdate" readonly="readonly" >
											</td>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-licenseno" >
											</td>
										</tr>
										<tr>
											<td class="text-right">过期时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUser-expirytime" readonly="readonly" >
											</td>
											<td class="text-right">证件照片<strong> :</strong>
											<td class="text-left">
												<input id="photoCover-certificatespic" type="text" style="display: none;">
		                                        <div class="wrapTypeMore">
		                                            <input id="picurls-certificatespic" name="imageDataList" type="file"
		                                                   class="file typeFileMore"
		                                                   value="" onchange="doChangeProjectcertificatespic()" onclick="checkPhotoNum()"/>
		                                            <ul id="loadImg-certificatespic" class="loadImg">
		                                                <li class="modalImg"><span>请选择上传图片</span></li>
		                                            </ul>
		                                        </div>
											</td>
										</tr>
										<tr>
											<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
                                            <td class="text-left" style="overflow: hidden;" colspan="3">
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype1" value="1" disabled="disabled" checked="checked">火警&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype2" value="2">故障&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype3" value="3">启动&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype4" value="4">反馈&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype5" value="5">监管&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype6" value="6">屏蔽&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype8" value="8">复位&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newNetworkingUser-receivealarmtype7" value="9">其他
                                            </td>
		                                </tr>
										<tr>
											<td class="text-right">照片<strong> :</strong>
											<td class="text-left">
												<input id="photoCover-photo" type="text" style="display: none;">
		                                        <div class="wrapTypeMore">
		                                            <input id="picurls-photo" name="imageDataList" type="file"
		                                                   class="file typeFileMore"
		                                                   value="" onchange="doChangeProjectphoto()"/>
		                                            <ul id="loadImg-photo" class="loadImg">
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
					<!-- <button type="button" class="btn btn-primary" id="continue" >
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

<!-- 员工查看模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="newEmployView">
	<div class="modal-dialog" role="document" id="wrapModal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看联网单位人员</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<colgroup>
											<col width="10%">
											<col width="40%">
											<col width="10%">
											<col width="40%">
										</colgroup>
										<tr>
											<td class="text-right">单位<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><select name="newNetworkingUserView-unitid" id="newNetworkingUserView-unitid" class="form-control inputNormal" disabled="disabled">
                                                </select>
											</td>
											<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-account" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">手机号码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-mobilephone" readonly="readonly"> 
											</td>
										<td class="text-right architecture ">建筑<strong> :</strong>
										</td>
										<td class="text-left  architecture">
											<input type="text" class="form-control inputNormal" id="preIdRole-look" readonly="readonly">
										</td>
									</tr>
										<tr>
											<td class="text-right">性别<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left" >
		                                    	<input type="radio" name="sex" value='1' id="newNetworkingUserView-sex1" checked="checked" disabled="disabled">男
		                                    	<input type="radio" name="sex" value='0' id="newNetworkingUserView-sex0" disabled="disabled">女
		                                    </td>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-username" readonly="readonly" >
											</td>
										</tr>
										<tr>
											<td class="text-right">生日<font style="color: red"></font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-birthday" readonly="readonly">
											</td>
											<td class="text-right">邮箱<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-email" readonly="readonly" >
											</td>
										</tr>
										<tr>
											<td class="text-right">职务<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-post" readonly="readonly" >
											</td>
											<td class="text-right">部门<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-department" readonly="readonly" >
											</td>
										</tr>
										<tr>
											<td class="text-right">证件类型<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-certificatestype" readonly="readonly" >
											</td>
											<td class="text-right">证件号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-certificatesno" readonly="readonly" >
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-certificatesdate" readonly="readonly" >
											</td>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-licenseno" readonly="readonly" >
											</td>
										</tr>
										<tr>
											<td class="text-right">过期时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newNetworkingUserView-expirytime" readonly="readonly" >
											</td>
											<td class="text-right">证件照片<strong> :</strong>
											<td class="text-left">
		                                            <ul id="loadImgView-certificatespic" class="loadImg">
		                                                <li class=""></li>
		                                            </ul>
											</td>
										</tr>
										<tr>
											<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
                                            <td class="text-left" style="overflow: hidden;" colspan="3">
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype1" value="1" disabled="disabled">火警&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype2" value="2" disabled="disabled">故障&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype3" value="3" disabled="disabled">启动&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype4" value="4" disabled="disabled">反馈&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype5" value="5" disabled="disabled">监管&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype6" value="6" disabled="disabled">屏蔽&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype8" value="8" disabled="disabled">复位&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newNetworkingUserView-receivealarmtype7" value="9" disabled="disabled">其他
                                            </td>
		                                </tr>
										<tr>
											<td class="text-right">照片<strong> :</strong>
											<td class="text-left">
		                                            <ul id="loadImgView-photo" class="loadImg">
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


<!-- 添加角色模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="addSetModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">设置角色</h4>
			</div>
			<div class="modal-body">
				<div class="content" style="float:left; text-align:center; margin-right:10px;">
					<h3 style="line-height: 16px;text-align: center;font-size: 14px">未选择</h3>
					<select multiple="multiple" id="select1"
							style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">


					</select>
					<span id="remove" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">选中右移>></span>
					<span id="remove_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">全部右移>></span>
				</div>

				<div class="content" style="float:left; text-align:center; margin-right:10px;">
					<h3 style="line-height: 16px;text-align: center;font-size: 14px">已选择</h3>
					<select multiple="multiple" id="select2"
							style="width: 250px;height: 167px;padding: 5px;overflow: auto;display: block">
					</select>
					<span id="add" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;选中左移</span>
					<span id="add_all" style="display:inline-block; width:100px; background:#eee; cursor:pointer; border:1px solid #ccc; padding:5px 0; margin:5px 0;">&lt;&lt;全部左移</span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary btn-save_role">
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
	
	<input type="hidden" id="unitId" value="${userSession.unitId}">
<%--权限--%>
<input type="hidden" name="preId" id="preId" value="">
</body>
<script src="/js/page_js/unit/networkingUser.js"></script>
</html>