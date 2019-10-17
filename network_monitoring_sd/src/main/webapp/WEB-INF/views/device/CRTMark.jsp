<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>CRT标注</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<%@include file="../inc.jsp"%>
<link rel="stylesheet"
	href="/css/page_css/Employee_management.css">
	<link rel="stylesheet" href="/js/common/layui/css/layui.css">
	<link rel="stylesheet" href="/css/page_css/CRTMark.css">
</head>
<body>
<div class="container jy_wrap">
	<!-- 设备 -->
	<div class="rank_list">
<!-- 	    <div class="05f90cef-99c4-47bd-bdba-fe838cd33dcb" labid="81939f07-5a57-43a6-928c-a8f100d0c56f" logo="/image/crt/1.png" code="1"><img src="/image/crt/1.png"><span>烟感</span></div> -->
<!-- 	    <div class="05f90cef-99c4-47bd-bdba-fe838cd33dcb" labid="34a38c5b-63a9-471b-9121-a8f100d0db69" logo="/image/crt/2.png" code="2"><img src="/image/crt/2.png"><span>温感</span></div> -->
<!-- 	    <div class="" codeid="07985038-43f9-4113-b31f-a8f100d0ed35" logo="/image/crt/3.png" code="3"><img src="/image/crt/3.png"><span>手报</span></div> -->
<!-- 	    <div class="05f90cef-99c4-47bd-bdba-fe838cd33dcb" labid="27d37dc4-c891-4ace-bca6-a8f100d0fd4d" logo="/image/crt/4.png" code="4"><img src="/image/crt/4.png"><span>消报</span></div> -->
	</div>
	
	<div class="wrap_miancon">
		<!--左 - 区域 -->
		<div class="left_arealist">
 			<div class="col-md-2" >
				<div class="wrap_search">
                    <input type="text" class="searchTxt" id="searchUnitTxt" placeholder="请输入单位名称" />
                    <img src="/image/searchBtn.png" class="searchBtn" id="searchUnit" />
                </div>		
				<div class="jy_new" style="top: 6px;">
						<ul id="areaTree" class="ztree" style="height: 600px; width: 260px">
						</ul>
				</div>			
			</div>
		</div>
		<!--中间crt图  -->
		<div class="center_areapage">
			<div class="wrap_crtOpt">
                <img src="/image/pointpng.png" class="btn_toleft">
                <img src="/image/pointpng.png" class="btn_toright">

                <div class="wrap_btnOpt">
                    <div class="up">上</div><div class="down">下</div><div class="left">左</div><div class="right">右</div>
                </div>
            </div>
            
            <div class="wrap_wrap_bg">
            	<div id="wrap_img">
            		<div id="imgDiv" onmousewheel="return bbimg(this)" height="" width="" style="width:100%;height:100%">
            			
            		</div>           	
            	</div>            	
            </div>                       
		</div>
		<!--右侧-点位列表-->
		<div class="div_con right_pointlist">
			  <div class="list_opts">
                <div class="wrap_search">
                    <input type="text" class="searchTxt" id="searchTxt" placeholder="请输入编码或地址" />
                    <img src="/image/searchBtn.png" class="searchBtn" id="searchBtn" />
                </div>
<!--                 <button class="btn-primary hiddenAllPoints" id="btnShowOrHiddenAllDots"></button> -->
            </div>
            <div class="CRTTable">
               <table class="table table-bordered" id="CRTTable">
                </table>
            </div>
            <div id="mypage"></div>
		
		</div>
		
	
	</div>

</div>

	<!-- 新增模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="editCRTInfo">
		<div class="modal-dialog" role="document" id="wrapModal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">编辑信息</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-md-10">
								<table class="table tableNo">
									<tr>
                                       <td class="text-right">点位类型<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><select class="form-control inputWidth" id="pointType" disabled="disabled"></select></td>
										<td class="text-right">系统类型<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><select class="form-control inputWidth" id="eqSystemId" disabled="disabled"></select></td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">视频点位<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><select class="form-control inputWidth" id="pointVideo" disabled="disabled"></select></td>
										<td class="text-right">部位号<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="partcode" readonly="readonly"></td>
                                    </tr>
                                    <tr>
										<td class="text-right">x坐标<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="xAxis" readonly="readonly"></td>
										<td class="text-right">y坐标<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="yAxis" readonly="readonly"></td>
									</tr>
									<tr>
                                        <td class="text-right">设备名称<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="name"></td>
										<td class="text-right">真实地址<font style="color: red">*</font><strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="address"></td>
                                    </tr>
	 								<tr>
										<td class="text-right">备注<strong>:</strong></td>
										<td class="text-left"><input type="text"class="form-control inputWidth" id="remark"></td>
									</tr>
								   
									
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
<input type="hidden" id="indexId" value="">
<input type="hidden" id="unitId" value="${userSession.unitId }">
</body>
<script src="/js/common/udraggable/jquery.udraggable.js"></script>
<script src="/js/common/udraggable/jquery.event.ue.js"></script>
<script src="/js/common/layui/layui.js"></script>
<script src="/js/page_js/device/CRTMark.js"></script>
</html>