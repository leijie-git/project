$(function () {
	//主页面查询
	mainSearch();
	
    // 新增或编辑
    $(".btnSure").click(function () {
    	
    	var id = $("#indexId").val();
        var wxAppid = $("#wxAppid").val();
		var wxSecret = $("#wxSecret").val();
		var wxRedirectUri = $("#wxRedirectUri").val();
		var wxTempId = $("#wxTempId").val();
		var wxTempRtuId = $("#wxTempRtuId").val();
		var wxClickUrl = $("#wxClickUrl").val();
		var shortMessageApiKey = $("#shortMessageApiKey").val();
		var shortMessageApiSecret = $("#shortMessageApiSecret").val();
		var signName = $("#signName").val();
		var redServerPort = $("#redServerPort").val();
		var accessToken = $("#accessToken").val();
		var androidApiKey = $("#androidApiKey").val();
		var androidApiSecret = $("#androidApiSecret").val();
		var projectDomainName = $("#projectDomainName").val();
		var mapCenter = $("#mapCenter").val();
		var mapLevel = $("#mapLevel").val();
		var serverTimeOutPhone = $("#serverTimeOutPhone").val();
		var wzTitle = $("#wzTitle").val();
		var jsProviders = $("#jsProviders").val();
		var gwUrl = $("#gwUrl").val();
		var wzRecordaddress = $("#wzRecordaddress").val();
		var wzRecord = $("#wzRecord").val();
		var yxAccount = $("#yxAccount").val();
		var yxSmtpserver = $("#yxSmtpserver").val();
		var yxSmtpport = $("#yxSmtpport").val();
		var videoAppkey = $("#videoAppkey").val();
		var videoAppsecret = $("#videoAppsecret").val();
		var yxPwd = $("#yxPwd").val();

        var data = {
        		id:1,
        		wxAppid:wxAppid,
        		wxSecret:wxSecret,
        		wxRedirectUri:wxRedirectUri,
        		wxTempId:wxTempId,
        		wxTempRtuId:wxTempRtuId,
        		wxClickUrl:wxClickUrl,
        		shortMessageApiKey:shortMessageApiKey,
        		shortMessageApiSecret:shortMessageApiSecret,
        		signName:signName,
        		redServerPort:redServerPort,
        		accessToken:accessToken,
        		androidApiKey:androidApiKey,
        		androidApiSecret:androidApiSecret,
        		projectDomainName:projectDomainName,
        		mapCenter:mapCenter,
        		mapLevel:mapLevel,
        		serverTimeOutPhone:serverTimeOutPhone,
        		wzTitle:wzTitle,
        		jsProviders:jsProviders,
        		gwUrl:gwUrl,
        		wzRecordaddress:wzRecordaddress,
        		wzRecord:wzRecord,
        		yxAccount:yxAccount,
        		yxSmtpserver:yxSmtpserver,
        		yxSmtpport:yxSmtpport,
        		videoAppkey:videoAppkey,
        		videoAppsecret:videoAppsecret,
        		yxPwd:yxPwd
        };
        $.ajax({
            url: baseUrl + "/propertiesManage/updateProperties",
            type: "post",
			beforeSend: function(request){
				request.setRequestHeader("Authorization","Bearer "+login.token);
			},
            data: data,
            dataType: "json",
            success: function (d) {
                if (d.success) {
                    layer.msg("保存成功！");
                } else {
                    layer.msg("保存失败！");
                }
            },
            error: function (data) {

            }
        });
    });
    
    
});


//主页面查询
function mainSearch() {
	$.ajax({
		url:baseUrl+"/propertiesManage/getProperties",
		type:"get",
		beforeSend: function(request){
			request.setRequestHeader("Authorization","Bearer "+login.token);
		},
		dateType:"json",
		success:function(d){
			$("#indexId").val(d.obj.id);
			$("#wxAppid").val(d.obj.wxAppid);
			$("#wxSecret").val(d.obj.wxSecret);
			$("#wxRedirectUri").val(d.obj.wxRedirectUri);
			$("#wxTempId").val(d.obj.wxTempId);
			$("#wxTempRtuId").val(d.obj.wxTempRtuId);
			$("#wxClickUrl").val(d.obj.wxClickUrl);
			$("#shortMessageApiKey").val(d.obj.shortMessageApiKey);
			$("#shortMessageApiSecret").val(d.obj.shortMessageApiSecret);
			$("#signName").val(d.obj.signName);
			$("#redServerPort").val(d.obj.redServerPort);
			$("#accessToken").val(d.obj.accessToken);
			$("#androidApiKey").val(d.obj.androidApiKey);
			$("#androidApiSecret").val(d.obj.androidApiSecret);
			$("#projectDomainName").val(d.obj.projectDomainName);
			$("#mapCenter").val(d.obj.mapCenter);
			$("#mapLevel").val(d.obj.mapLevel);
			$("#serverTimeOutPhone").val(d.obj.serverTimeOutPhone);
			$("#wzTitle").val(d.obj.wzTitle);
			$("#jsProviders").val(d.obj.jsProviders);
			$("#gwUrl").val(d.obj.gwUrl);
			$("#wzRecordaddress").val(d.obj.wzRecordaddress);
			$("#wzRecord").val(d.obj.wzRecord);
			$("#yxAccount").val(d.obj.yxAccount);
			$("#yxSmtpserver").val(d.obj.yxSmtpserver);
			$("#yxSmtpport").val(d.obj.yxSmtpport);
			$("#videoAppkey").val(d.obj.videoAppkey);
			$("#videoAppsecret").val(d.obj.videoAppsecret);
			$("#yxPwd").val(d.obj.yxPwd);
		}
	});
}




