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
<title>角色管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/role_management.css">
<script type="text/javascript"
	src="/js/common/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="/js/common/zTree/jquery.ztree.excheck.js"></script>
</head>
<body>
	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">系统管理</span></li>
						<li class="no_active"><span class="title">后台角色管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="roleNames" class="control-label">角色名称<strong>
										:</strong></label> <input type="text" class="form-control " name="roleNames"
									id="roleNames">
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
<!-- 									<option value="0">全部</option> -->
<!-- 									<option value="numberId">编号</option> -->
									<option value="roleNames">名称</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>&nbsp;查询
							</button>
<!-- 							<button type="button" class="btn btn-primary seniorSearch">高级搜索</button> -->

							<button type="button" class="btn btn-new btnNew"
								data-toggle="modal" data-target='#newAdd'>
								<i class="fa fa-plus"></i>&nbsp;新增
							</button>
							<!--                             <button type="button" class="btn btn-danger deleteMore"><i class="fa fa-trash"></i>&nbsp;批量删除 -->
							<!--                             </button> -->
						</div>
					</div>
					<div class="roleTable">
						<table class="table table-bordered" id="roleTable">

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newAdd">
		<div class="modal-dialog" role="document" id="secondModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增角色</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<table class="table tableNo">
									<tr>
										<td class="text-right">名称<font style="color: red">*</font><strong>
												:</strong>
										</td>
										<td class="text-left"><input type="text"
											class="form-control inputWidth" id="roleName"></td>
									</tr>
									<tr>
                                        <td class="text-right">权限<strong> :</strong>
                                        </td>
                                        <td class="text-left">
                                            <div id="preIdWrapRole">
                                                <span></span>
                                                <ul id="preIdRole" class="ztree"
                                                    style="margin-top: 0; width: 100%;"></ul>
                                            </div>
                                        </td>
                                    </tr>
<!-- 									<tr> -->
<!--                                         <td class="text-right">角色类别<strong> :</strong> -->
<!--                                         </td> -->
<!--                                         <td class="text-left"> -->
<!--                                             <div class="needCheck inputWidth"> -->
<!--                                                 <input type="text" class="form-control" name="roleType" id="roleType"> -->
<!--                                             </div> -->
<!--                                         </td> -->
<!--                                     </tr> -->
									<tr>
										<td class="text-right">排序<strong> :</strong>
										</td>
										<td class="text-left">
											<div class="needCheck inputWidth">
												<input type="text" class="form-control" name="sortIndex" id="sortIndex">
											</div>
										</td>
									</tr>
<!-- 									<tr> -->
<!-- 										<td class="text-right">是否系统管理员<strong> :</strong> -->
<!-- 										</td> -->
<!-- 										<td class="text-left"> -->
<!-- 											<div class="needCheck inputWidth"> -->
<!-- 												<input type="radio" name="isadmin" value="1" -->
<!-- 												 id="isadmin1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 											<input type="radio" name="isadmin" value="0" -->
<!-- 												 id="isadmin0" checked="checked">否 -->
<!-- 											</div> -->
<!-- 										</td> -->
<!-- 									</tr> -->
								   
									
								</table>
							</div>
						</div>
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
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 查看角色模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newAddView">
		<div class="modal-dialog" role="document" id="secondViewModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查看角色</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<table class="table tableNo">
									<tr>
										<td class="text-right">角色名称<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
											class="form-control inputWidth" id="roleNameView" readonly>
										</td>
									</tr>
<!-- 									<tr> -->
<!--                                         <td class="text-right">角色类型<strong> :</strong> -->
<!--                                         </td> -->
<!--                                         <td class="text-left"><input type="text" -->
<!--                                             class="form-control inputWidth" id="roleTypeView" readonly> -->
<!--                                         </td> -->
<!--                                     </tr> -->
									<tr style="height: 5px;">
										<td colspan="2">
											<p class="borderTop"></p>
										</td>
									</tr>
									<tr>
										<td class="text-right">角色权限<strong> :</strong>
										</td>
										<td class="text-left"><input type="text"
											class="form-control inputWidth" name="seq" id="preView"
											readonly></td>
									</tr>
									<tr>
										<td class="text-right">排序<strong> :</strong>
										</td>
										<td class="text-left">
											<div class="needCheck inputWidth">
												<input type="text" class="form-control" name="sortIndex"
													id="sortIndexView" readonly>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
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
	<!-- 授权模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="grant">
		<div class="modal-dialog" role="document" id="second-modal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">授权</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-9">
								<div class="mainList">
									<table class="table" id="grantTable"
										data-click-to-select="true">

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btnGrant">
						<i class="fa fa-floppy-o"></i>&nbsp;确定
					</button>
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

	<!-- 区分新增和编辑 -->
	<input type="hidden" id="edit" value="0">
	
	<input type="hidden" name="indexId" id="indexId" value="">

	<%--权限--%>
	<input type="hidden" name="preId" id="preId" value="">


</body>
<script src="/js/page_js/systemManage/roleManage.js"></script>
</html>