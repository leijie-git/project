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
<title>维保单位管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet" href="/css/page_css/Employee_management.css">
<script type="text/javascript"
	src="http://api.map.baidu.com/getscript?v=2.0&ak=uAVQruFnlAevcIBVA89lt02GH5kLkUXd&services="></script>
</head>
<body>
	<div class="container jy_wrap">
		<div class="row">
			<div class="col-md-12">
				<div class="jy_wrapper">
					<ul class="jy_title">
						<li class="is_active"><span class="title">单位管理</span></li>
						<li class="no_active"><span class="title">维保单位管理</span></li>
					</ul>
					<div class="jy_mainTile panel-heading" style="display: none"
						clickNo="0">
						<div class="form-inline">
							<div class="form-group">
								<label for="identityNo" class="control-label">单位名称<strong>
										:</strong></label> <input type="text" class="form-control" name="Search-unitName"
									id="Search-unitName"> <label for="identityNo"
									class="control-label">单位编号<strong> :</strong></label> <input
									type="text" class="form-control" name="Search-unitCode" id="Search-unitCode">
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
									<option value="Search-unitName">单位名称</option>
									<option value="Search-unitCode">单位编号</option>
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
	<!-- 新增消防力量模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="newEmploy">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增维保单位</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">单位编号<font style="color: red">*</font><strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="unitCode">
											</td>
											<td class="text-right">单位名称<font style="color: red">*</font><strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="unitName"></td>
										</tr>
										<tr>
											<td class="text-right">单位地址<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="address">
											</td>
											<td class="text-right">联系人<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="contacts"></td>
										</tr>
										<tr>
											<td class="text-right">联系电话<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="telephone">
											</td>
											<td class="text-right">省<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="proviceid" name="proviceid" class="form-control inputNormal">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">市<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="cityid" name="cityid" class="form-control inputNormal">
												</select>
											</td>
											<td class="text-right">区<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="regionid" name="regionid" class="form-control inputNormal">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">邮箱<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="email"></td>
											<td class="text-right">备注<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="remark"></td>
										</tr>
										<tr>
											<td class="text-right">坐标X<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="pointX">
											</td>
											<td class="text-right">坐标Y<strong class="explain">
													:</strong></td>
											<!-- <td class="text-left">
	                            <input type="text" class="form-control inputNormal" id="pointY" >
	                        </td> -->
											<td class="text-left"><input type="text"
												class="form-control inputNormal" name="pointY" id="pointY">
												<a href="#" onclick="showMap();">位置标注</a></td>
										</tr>
										<tr>
											<td class="text-right">单位logo<strong class="explain">:</strong></td>
                                            <td class="text-left"><input id="photoCover-unitLogo"
                                                type="text" style="display: none;">
                                                <div class="wrapTypeMore">
                                                    <input id="picurls-unitLogo" name="imageDataList" type="file"
                                                        class="file typeFileMore" value=""
                                                        onchange="doChangeProjectunitLogo()" />
                                                    <ul id="loadImg-unitLogo" class="loadImg">
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
					<h4 class="modal-title">查看</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<div class="">
									<table class="table tableNo">
										<tr>
											<td class="text-right">单位编号<font style="color: red">*</font><strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="unitCode-View">
											</td>
											<td class="text-right">单位名称<font style="color: red">*</font><strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="unitName-View"></td>
										</tr>
										<tr>
											<td class="text-right">单位地址<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="address-View">
											</td>
											<td class="text-right">联系人<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="contacts-View"></td>
										</tr>
										<tr>
											<td class="text-right">联系电话<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="telephone-View">
											</td>
											<td class="text-right">省<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="proviceid-View" name="proviceid-View" class="form-control inputNormal">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">市<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="cityid-View" name="cityid-View" class="form-control inputNormal">
												</select>
											</td>
											<td class="text-right">区<strong class="explain">
													:</strong></td>
											<td class="text-left">
												<select id="regionid-View" name="regionid-View" class="form-control inputNormal">
												</select>
											</td>
										</tr>
										<tr>
											<td class="text-right">邮箱<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="email-View"></td>
											<td class="text-right">备注<strong class="explain">
													:</strong></td>
											<td class="text-left"><input type="text"
												class="form-control inputNormal" id="remark-View"></td>
										</tr>
										<tr>
											<td class="text-right">坐标X<strong class="explain">:</strong></td>
											<td class="text-left" style="overflow: hidden;"><input
												type="text" class="form-control inputNormal" id="pointX-View">
											</td>
											<td class="text-right">坐标Y<strong class="explain">
													:</strong></td>
											<!-- <td class="text-left">
	                            <input type="text" class="form-control inputNormal" id="pointY" >
	                        </td> -->
											<td class="text-left"><input type="text"
												class="form-control inputNormal" name="pointY" id="pointY-View">
										</tr>
										<tr>
											<td class="text-right">单位logo<strong class="explain">:</strong></td>
                                            <td class="text-left">
                                                <div class="wrapTypeMore">
                                                    <ul id="loadImg-view-unitLogo" class="loadImg">
<!--                                                         <li class="modalImg"><span>请选择上传图片</span></li> -->
                                                    </ul>
                                                </div></td>
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
	
	<input type="hidden" name="indexId" id="indexId" value="">
	<input type="hidden" id="editId" value="0">
</body>
<script src="/js/page_js/unit/maintenanceUnit.js"></script>
</html>