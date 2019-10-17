<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>维保单位人员管理</title>
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
						<li class="is_active"><span class="title">用户管理</span></li>
						<li class="no_active"><span class="title">维保单位人员管理</span></li>
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
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增维保单位人员</h4>
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
											<td class="text-right">维保单位<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<select class="form-control inputNormal" id="newMaintenanceUser-unitid">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-account" >
											</td>
											<td class="text-right">密码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-password" >
											</td>
										</tr>
										<tr>
											<td class="text-right">用户类别<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left" >
                                                <input type="radio" name="role" value='5' id="wbUserRole" checked="checked">维保人员
                                                <input type="radio" name="role" value='6' id="jgUserRole">监管人员
                                            </td>
											<td class="text-right">性别<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left" >
		                                    	<input type="radio" name="sex" value='1' id="newMaintenanceUser-sex1" checked="checked">男
		                                    	<input type="radio" name="sex" value='0' id="newMaintenanceUser-sex0">女
		                                    </td>
										</tr>
										<tr>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-username" >
											</td>
											<td class="text-right">手机号码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-mobilephone">
											</td>
										</tr>
										<tr>
                                            <td class="text-right">部门<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUser-department" >
                                            </td>
                                            <td class="text-right">职务<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUser-post" >
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-licenseno" >
											</td>
											<td class="text-right">生日<font style="color: red"></font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-birthday" readonly="readonly">
											</td>
										</tr>
										
										<tr>
											<td class="text-right">证件类型<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-certificatestype" >
											</td>
											<td class="text-right">证件号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-certificatesno" >
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-certificatesdate" readonly="readonly" >
											</td>
											<td class="text-right">邮箱<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUser-email" >
                                            </td>
										</tr>
										<tr>
											<td class="text-right">过期时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUser-expirytime" readonly="readonly" >
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
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype1" value="1" disabled="disabled" checked="checked">火警&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype2" value="2">故障&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype3" value="3">启动&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype4" value="4">反馈&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype5" value="5">监管&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype6" value="6">屏蔽&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype8" value="8">复位&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmType" id="newMaintenanceUser-receivealarmtype7" value="9">其他
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
					<h4 class="modal-title">查看维保单位人员</h4>
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
											<td class="text-right">维保单位<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left">
												<select class="form-control inputNormal" id="newMaintenanceUserView-unitid">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-account" readonly="readonly">
											</td>
											<td class="text-right">密码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-password" readonly="readonly">
											</td>
										</tr>
										<tr>
										<td class="text-right">用户类别<font style="color: red">*</font><strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left" >
                                                <input type="radio" name="role" value='5' id="wbUserRoleView" checked="checked">维保人员
                                                <input type="radio" name="role" value='6' id="jgUserRoleView">监管人员
                                            </td>
											
											<td class="text-right">性别<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left" >
		                                    	<input type="radio" name="sex" value='1' id="newMaintenanceUserView-sex1" checked="checked" disabled="disabled">男
		                                    	<input type="radio" name="sex" value='0' id="newMaintenanceUserView-sex0" disabled="disabled">女
		                                    </td>
										</tr>
										<tr>
											<td class="text-right">姓名<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-username" readonly="readonly" >
											</td>
											<td class="text-right">手机号码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-mobilephone" readonly="readonly">
											</td>
										</tr>
										<tr>
                                            <td class="text-right">部门<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUserView-department" readonly="readonly">
                                            </td>
                                            <td class="text-right">职务<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUserView-post" readonly="readonly">
                                            </td>
                                        </tr>
										<tr>
											<td class="text-right">上岗证号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-licenseno" readonly="readonly">
											</td>
											<td class="text-right">生日<font style="color: red"></font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-birthday" readonly="readonly">
											</td>
										</tr>
										
										<tr>
											<td class="text-right">证件类型<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-certificatestype" readonly="readonly">
											</td>
											<td class="text-right">证件号<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-certificatesno" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">发证日期<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-certificatesdate" readonly="readonly" >
											</td>
											<td class="text-right">邮箱<strong>
                                                    :</strong>
                                            </td>
                                            <td class="text-left"><input type="text"
                                                class="form-control inputNormal" id="newMaintenanceUserView-email" readonly="readonly" >
                                            </td>
										</tr>
										<tr>
											<td class="text-right">过期时间<strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newMaintenanceUserView-expirytime" readonly="readonly" >
											</td>
											<td class="text-right">证件照片<strong> :</strong>
											<td class="text-left">
		                                            <ul id="loadImgView-certificatespic" class="loadImg">
		                                            </ul>
											</td>
										</tr>
										<tr>
											<td class="text-right">接收报警类型<strong class="explain">:</strong></td>
                                            <td class="text-left" style="overflow: hidden;" colspan="3">
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype1" value="1" disabled="disabled">火警&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype2" value="2" disabled="disabled">故障&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype3" value="3" disabled="disabled">启动&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype4" value="4" disabled="disabled">反馈&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype5" value="5" disabled="disabled">监管&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype6" value="6" disabled="disabled">屏蔽&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype8" value="8" disabled="disabled">复位&nbsp;
                                                <input type="checkbox" name="ReceiveAlarmTypeView" id="newMaintenanceUserView-receivealarmtype7" value="9" disabled="disabled">其他
                                            </td>
		                                </tr>
										<tr>
											<td class="text-right">照片<strong> :</strong>
											<td class="text-left">
		                                            <ul id="loadImgView-photo" class="loadImg">
		                                            </ul>
											</td>
										</tr>
									</table>
									
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
	
	
	<!-- 设置角色模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="setRoleModal">
		<div class="modal-dialog" role="document" id="wrapModalView">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">关联单位</h4>
				</div>
				<div class="modal-body">
                     <div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
                         <h3 style="line-height: 16px;text-align: center;font-size: 14px">未选择</h3>
                         <div class="form-inline">
					            	<div class="form-group" style="float:left;margin-bottom:5px;">
					                     <label for="questionName1">单位名称<strong> :</strong></label>
					                     <input type="text" class="form-control" name="unitName1" id="unitName1">
					                 </div>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary btnSearchQ1"><i class="fa fa-search"></i>&nbsp;查询
					                 </button>
					                 <button  style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary addQuestionBtn"><i class="fa fa-plus"></i>&nbsp;关联
					                 </button>
					             </div>
			                    <table class="table table-bordered" id="noCheckTable" data-click-to-select="true">
		                    	</table>
                     </div>
                     <div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
                             <h3 style="line-height: 16px;text-align: center;font-size: 14px">已选择</h3>
                             <div class="form-inline">
					                 <div class="form-group" style="float:left;margin-bottom:5px;">
					                     <label for="questionName2">单位名称<strong> :</strong></label>
					                     <input type="text" class="form-control" name="unitName2" id="unitName2">
					                 </div>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary btnSearchQ2"><i class="fa fa-search"></i>&nbsp;查询
					                 </button>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary delQuestionBtn"><i class="fa fa-trash"></i>&nbsp;移除
					                 </button>
					             </div>
			                    <table class="table table-bordered" id="checkTable" data-click-to-select="true">
									
			                    </table>
                     </div>
				</div>
				<div class="modal-footer">
					
<!-- 					<button type="button" class="btn btn-close" data-dismiss="modal" -->
<!-- 						aria-label="Close"> -->
<!-- 						<i class="fa fa-times"></i>&nbsp;关闭 -->
<!-- 					</button> -->
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

	<!-- 添加角色模态框 -->
	<!-- <div class="modal fade" tabindex="-1" role="dialog" id="setRoleModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看维保人员</h4>
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
			/.modal-content
		</div>
		/.modal-dialog
	</div> -->
	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/unit/maintenanceUser.js"></script>
</html>