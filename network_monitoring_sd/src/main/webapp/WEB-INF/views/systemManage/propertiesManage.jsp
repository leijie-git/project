<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>配置管理</title>
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
						<li class="no_active"><span class="title">配置管理</span></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row" id="employss">
			<div class="col-md-12">
				<div class="dataTable">
					<div id="parentForm">
						<table class="table tableNo">
							<tr>
								<td class="text-right"><strong class="explain">微信配置</strong></td>
							</tr>
							<tr>
								<td class="text-right">微信appid<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxAppid" >
								</td>
								<td class="text-right">微信secret<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxSecret" >
								</td>
							</tr>
							<tr>
								<td class="text-right">微信跳转路径<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxRedirectUri" >
								</td>
								<td class="text-right">微信模板id<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxTempId" >
								</td>
							</tr>
							<tr>
								<td class="text-right">微信模板RTUid<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxTempRtuId" >
								</td>
								<td class="text-right">微信跳转clickurl<strong>
										:</strong>
								</td>
								<td class="text-left"><input type="text"
									class="form-control inputNormal" id="wxClickUrl">
								</td>
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
                            <tr>
								<td class="text-right"><strong class="explain">启瑞短信配置</strong></td>
							</tr>
							<tr>
								<td class="text-right password">启瑞短信信息apiKey<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="shortMessageApiKey" >
								</td>
								<td class="text-right password">启瑞短信信息apiSecret<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="shortMessageApiSecret" >
								</td>
							</tr>
							<tr>
								<td class="text-right password">启瑞短信信息 签名<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="signName" >
								</td>
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
							<tr>
								<td class="text-right"><strong class="explain">老版信息配置</strong></td>
							</tr>
							<tr>
								<td class="text-right password">原始数据同步服务器路径<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="redServerPort" >
								</td>
								<td class="text-right password">原始数据同步令牌<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="accessToken" >
								</td>
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
                            <tr>
								<td class="text-right"><strong class="explain">App端配置</strong></td>
							</tr>
							<tr>
								<td class="text-right password">App端apiKey<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="androidApiKey" >
								</td>
								<td class="text-right password">App端apiSecret<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="androidApiSecret" >
								</td>
								<!-- <td class="text-right password">项目域名<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="projectDomainName" >
								</td> -->
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
                            <tr>
								<td class="text-right"><strong class="explain">地图配置</strong></td>
                            </tr>
							<tr>
								<td class="text-right password">首页地图中心点<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="mapCenter" >
								</td>
								<td class="text-right password">地图级别<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="mapLevel" >
								</td>
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
                            <tr>
								<td class="text-right"><strong class="explain">项目信息配置</strong></td>
                            </tr>
							<tr>
								<td class="text-right password">服务到期提醒人电话<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="serverTimeOutPhone" >
								</td>
								<td class="text-right password">项目标题<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="wzTitle" >
								</td>
							</tr>
							<tr>
								<td class="text-right password">公司名称<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="jsProviders" >
								</td>
								<td class="text-right password">公司官网链接<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="gwUrl" >
								</td>
							</tr>
							<tr>
								<td class="text-right password">备案号<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="wzRecordaddress" >
								</td>
								<td class="text-right password">备案号链接<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="wzRecord" >
								</td>
							</tr>
							<tr style="height: 10px;">
                                <td colspan="6">
                                    <p class="borderTop"></p>
                                </td>
                            </tr>
                            <tr>
								<td class="text-right"><strong class="explain">邮箱配置</strong></td>
                            </tr>
							<tr>
								<td class="text-right password">邮箱账号<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="yxAccount" >
								</td>
								<td class="text-right password">邮箱密码<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="yxPwd" >
								</td>
							</tr>
							<tr>
								<td class="text-right password">邮箱SMTP服务器<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="yxSmtpserver" >
								</td>
								<td class="text-right password">邮箱SMTP端口<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="yxSmtpport" >
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
								<td class="text-right password">萤石云appKey<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="videoAppkey" >
								</td>
								<td class="text-right password">萤石云appSecret<strong>
										:</strong>
								</td>
								<td class="text-left password"><input type="text"
									class="form-control inputNormal" id="videoAppsecret" >
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary btnSure">
							<i class="fa fa-floppy-o"></i>&nbsp;保存
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" name="indexId" id="indexId" value="">

	<%--区分编辑和更新--%>
	<input type="hidden" id="editId" value="0">

</body>
<script src="/js/page_js/systemManage/propertiesManage.js"></script>
</html>