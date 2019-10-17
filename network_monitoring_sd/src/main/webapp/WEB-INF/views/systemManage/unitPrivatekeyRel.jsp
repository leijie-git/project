<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>私钥管理</title>
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
						<li class="no_active"><span class="title">私钥配置</span></li>
					</ul>
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
									<option value="Search-privateKey">私钥</option>
								</select> <span id="commonInput"><input type="text"
									class="form-control showInput" name="searchContent"
									id="searchContent"></span>
							</div>
							<button type="button" class="btn btn-primary ordinarySearch">
								<i class="fa fa-search"></i>查询
							</button>
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
		<div class="modal-dialog" role="document" id="secondModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增私钥</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-6">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">私钥<font style="color: red">*</font><strong>
													:</strong>
											</td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="privateKey" >
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
	
	<!-- 关联应用 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="linkApp">
		<div class="modal-dialog" role="document" id="wrapModalView">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">绑定单位</h4>
				</div>
					<div class="modal-body">
					<div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
                             <h3 style="line-height: 16px;text-align: center;font-size: 14px">已选择</h3>
                             <div class="form-inline">
					                 <div class="form-group" style="float:left;margin-bottom:5px;">
					                     <label for="appName1">单位名称<strong> :</strong></label>
					                     <input type="text" class="form-control" name="appName1" id="appName1">
					                 </div>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary btnSearchA1"><i class="fa fa-search"></i>&nbsp;查询
					                 </button>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary delAppBtn"><i class="fa fa-trash"></i>&nbsp;移除
					                 </button>
					             </div>
			                    <table class="table table-bordered" id="associatedTable" data-click-to-select="true">
									
			                    </table>
                     </div>
                     <div class="content" style="float:left; text-align:center; margin-right:10px;width:calc(50% - 10px)">
                         <h3 style="line-height: 16px;text-align: center;font-size: 14px">未选择</h3>
                         <div class="form-inline">
					            	<div class="form-group" style="float:left;margin-bottom:5px;">
					                     <label for="appName2">单位名称<strong> :</strong></label>
					                     <input type="text" class="form-control" name="appName2" id="appName2">
					                 </div>
					                 <button style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary btnSearchA2"><i class="fa fa-search"></i>&nbsp;查询
					                 </button>
					                 <button  style="float:left;margin-left:5px;margin-bottom:5px;" type="button" class="btn btn-primary addAppBtn"><i class="fa fa-plus"></i>&nbsp;关联
					                 </button>
					             </div>
			                    <table class="table table-bordered" id="unassociatedTable" data-click-to-select="true">
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
	

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">
	
	<input type="hidden" id="privateKey-hidden" value="">

</body>
<script src="/js/page_js/systemManage/unitPrivatekeyRel.js"></script>
</html>