<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/22
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>员工管理</title>
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
						<li class="is_active"><span class="title">系统管理</span></li>
						<li class="no_active"><span class="title">人员管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">用户名<strong>
										:</strong></label> <input type="text" class="form-control" name="userName"
									id="userName">
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
									<option value="userName">用户名</option>
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
	<!-- 员工新增模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmploy">
		<div class="modal-dialog" role="document" id="firstModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增人员</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-account">
											</td>
											<td class="text-right password">密码<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left password"><input type="text"
												class="form-control inputNormal" id="newPeople-password">
											</td>
										</tr>
										<tr>
											<td class="text-right">姓名<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-userName">
											</td>
											<td class="text-right">性别<strong> :</strong>
											</td>
											<td class="text-left"><input type="radio" name="newPeople-sex" value="1"
												 id="newPeople-sex1" checked="checked">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newPeople-sex" value="0"
												 id="newPeople-sex0">女
											</td>
											
										</tr>
										<tr>
											<td class="text-right">电话<font style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-phone">
											</td>
											<td class="text-right">出生日期<strong> :</strong>
											</td>
											<td class="text-left"><input type="text" class="form-control inputNormal" id="newPeople-birthday" readonly>
											</td>
										</tr>
										<tr>
											<td class="text-right">地址<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-address">
											</td>
											<td class="text-right">民族<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-ethnicGroup">
											</td>
										</tr>
										<tr>
											<td class="text-right">签名<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeople-sign">
											</td>
										</tr>
										<tr>
											<td class="text-right">头像<strong> :</strong>
											<td class="text-left">
												<input id="photoCover-userHeader" type="text" style="display: none;">
		                                        <div class="wrapTypeMore">
		                                            <input id="picurls-userHeader" name="imageDataList" type="file"
		                                                   class="file typeFileMore"
		                                                   value="" onchange="doChangeProject()"/>
		                                            <ul id="loadImg-userHeader" class="loadImg">
		                                                <li class="modalImg"><span>请选择上传图片</span></li>
		                                            </ul>
		                                        </div>
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left">
												<textarea rows="3" cols=""
													id="newPeople-remark" class="form-control inputNormal"></textarea>
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
					<h4 class="modal-title">查看人员</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">账号<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-account" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">姓名<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-userName" readonly="readonly">
											</td>
											<td class="text-right">性别<strong> :</strong>
											</td>
											<td class="text-left"><input type="radio" name="newPeopleView-sex" value="1"
												 id="newPeopleView-sex1" checked="checked" disabled="disabled">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="newPeopleView-sex" value="0"
												 id="newPeopleView-sex0" disabled="disabled">女
											</td>
											
										</tr>
										<tr>
											<td class="text-right">电话<font style="color: red">*</font><strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-phone" readonly="readonly">
											</td>
											<td class="text-right">出生日期<strong> :</strong>
											</td>
											<td class="text-left"><input type="text" class="form-control inputNormal" id="newPeopleView-birthday" readonly>
											</td>
										</tr>
										<tr>
											<td class="text-right">地址<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-address" readonly="readonly">
											</td>
											<td class="text-right">民族<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-ethnicGroup" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">签名<strong> :</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="newPeopleView-sign" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td class="text-right">头像<strong> :</strong>
											<td class="text-left">
		                                            <ul id="loadImgView-userHeader" class="loadImg" >
		                                                <li></li>
		                                            </ul>
											</td>
										</tr>
										<tr>
											<td class="text-right">备注<strong> :</strong>
											</td>
											<td class="text-left">
												<textarea rows="3" cols=""
													id="newPeopleView-remark" class="form-control inputNormal" readonly="readonly"></textarea>
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
	<div class="modal fade" tabindex="-1" role="dialog" id="setRoleModal">
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
<script src="/js/page_js/systemManage/userManage.js"></script>
</html>