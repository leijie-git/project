/**
 * Created by 20170810001 on 2018/8/18.
 */

// var _serverIp = "http://192.168.0.151:8085";
// var _webSocketserverIp = "ws://192.168.0.151:8085";

var _serverIp = "http://"+location.host;
var _webSocketserverIp = "ws://"+location.host;
var _title;
var _record;
var _recordAddress;
var _gwUrl;
var _providers;
//var _serverIp = "http://www.sd-xfy.com:8085";
//var _webSocketserverIp = "ws://www.sd-xfy.com:8085";
var _robotUrl = "http://ask.x-niu.com.cn:8081/static/html/index.html?systemid=SD121987654321";


var _loginInfo = JSON.parse(localStorage.getItem("LoginInfo"));//登录信息

var _topAlarmVideoLayerIndex;//报警时弹出的layer- 有报警时关闭上一个

//获取当月日期 年-月-01
function getNowYeartDateStart() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + '01' + seperator1 + '01';
    return currentdate;
}

//获取当月日期 年-月-01
function getNowFormatDateStart() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + '01';
    return currentdate;
}
//获取当前日期 年-月-日
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

//获取当前日期 年-月-日 时-分-秒
function getNowMinute(){
    var date = new Date();
    var seperator2 = ":";
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();

    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }

    var currentdate =  hour + seperator2 + minute + seperator2 + second;
    return currentdate;
}
//获取当签日期 年-月-日
function getNowDay(){
    var date = new Date();
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month >= 0 && month <= 9) {
        month = "0" + month;
    }
    if (day >= 0 && day <= 9) {
        day = "0" + day;
    }
    var currentdate = year +"."+month +"."+ day;
    return currentdate;
}
//获取当签日期 周
function getNowWeek() {
    var val = new Date().getDay();
    if (val == 0) return "SUN";
    if (val == 1) return "MON";
    if (val == 2) return "TUS";
    if (val == 3) return "WED";
    if (val == 4) return "THU";
    if (val == 5) return "FRI";
    if (val == 6) return "SAT";
}

//将秒转为小时分钟
function formatSeconds(value) {
    var theTime = parseInt(value);// 秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时
    if(theTime > 60) {
        theTime1 = parseInt(theTime/60);
        theTime = parseInt(theTime%60);
        if(theTime1 > 60) {
            theTime2 = parseInt(theTime1/60);
            theTime1 = parseInt(theTime1%60);
        }
    }
    var result = ""+parseInt(theTime)+"秒";
    if(theTime1 > 0) {
        result = ""+parseInt(theTime1)+"分"+result;
    }
    if(theTime2 > 0) {
        result = ""+parseInt(theTime2)+"小时"+result;
    }
    return result;
}

function TimeDown(maxtime) {
    //var maxtime = 10 * 60; //时长,即10分钟
    var msg = "";
    if (maxtime >= 0) {
        minutes = Math.floor(maxtime / 60);
        seconds = Math.floor(maxtime % 60);
        if(minutes<10){
            minutes = "0"+minutes
        }
        if(seconds<10){
            seconds = "0"+seconds
        }
        msg =  minutes + ":" + seconds;
    } else{
        return ""
    }
    return msg;
}

//////////////
var now = new Date(); //当前日期
var nowMonth = now.getMonth(); //当前月
var nowYear = now.getYear(); //当前年
nowYear += (nowYear < 2000) ? 1900 : 0; //
//获得本季度的开端月份
function getQuarterStartMonth(){
    var quarterStartMonth = 0;
    if(nowMonth<3){
        quarterStartMonth = 0;
    }
    if(2<nowMonth && nowMonth<6){
        quarterStartMonth = 3;
    }
    if(5<nowMonth && nowMonth<9){
        quarterStartMonth = 6;
    }
    if(nowMonth>8){
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}
//获得本季度的开端日期
function getQuarterStartDate(){

    var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
    return formatDate(quarterStartDate);
}

//或的本季度的停止日期
function getQuarterEndDate(){
    var quarterEndMonth = getQuarterStartMonth() + 2;
    var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
    return formatDate(quarterStartDate);
}
//获得本月的开端日期
function getMonthStartDate(){
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}
//获得某月的天数
function getMonthDays(myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
    return days;
}

//格局化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}
///////////////////////////////


function GetQuery(key) {
    var search = location.search.slice(1);
    var arr = search.split("&");
    for (var i = 0; i < arr.length; i++) {
        var ar = arr[i].split("=");
        if (ar[0] == key) {
            if (unescape(ar[1]) == 'undefined') {
                return "";
            } else {
                return unescape(ar[1]);
            }
        }
    }
    return "";
}

//实时报警条数 - 联网单位首页、实时报警页
function getAlarmCount(num){
    var html="";
    for(var i = 0 ; i < num.toString().length;i++){
        html +="<span>"+ num.toString().substring(i,i+1)+"</span>"
    }
    return html
}


//物联 - 系统 - 判断当前值是否异常
function getSysValueType(obj) {
    var type = false;
    var curvalue = obj.currentValue || 0;
    if(curvalue){
        if (parseFloat(curvalue) <= parseFloat((obj.analogup||0)) && parseFloat(curvalue) >= parseFloat((obj.analogdown||0))) {
            type = true;
        }
    }
    return type;
}
function getCurrentStatus(data){
    var isValue = (parseFloat(data.currentValue)).toFixed(2) == (parseFloat(data.digitalNormal)).toFixed(2);
    return isValue;
}

//导出按钮
function postExport(url){
    var form = $("<form>");//
    form.attr("style", "display:none");
    form.attr("target", "");
    form.attr("method", "post");//
    form.attr("action", url);//
    $("body").append(form);//
    form.submit();//
}

//查看大图
function getBigPicInfo(obj){
    var imgWidth = $(obj).width(),imgHeight = $(obj).height(),scale = 0.6;
    var windowWidth = $(window.top).width(),windowHeight = $(window.top).height();
    var layerWidth=0,layerHeigth=0;
    var aa = imgHeight/imgWidth; //图片高和宽的比例 -- 注，图片的缩放比例应与原始比例相同
    var src= $(obj).attr('src');
    if(imgHeight>=imgWidth){ //高比宽大
        layerHeigth = scale*windowHeight;
        layerWidth = layerHeigth/aa;
    }
    if(imgWidth>=imgHeight){ //宽比高大
        layerWidth = scale*windowWidth;
        layerHeigth = layerWidth*aa;
    }
    //console.log(windowWidth, windowHeight);
    top.layer.open({
        type: 2,
        title: false,
        area: [layerWidth+"px", layerHeigth+"px"],
        fixed: false,
        closeBtn: 0,
        shade: 0.5,
        skin: 'layui-layer-bigpic',
        shadeClose: true,
        content:(_loginInfo.usertype == 0?"":"../")+ 'getBigPic.html?src='+escape(src)
    });
}

//获取报警类型 返回拼音
function alarmType(type){
    var key ="";
    if(type == "故障"){
        key = "guzhang"
    }else if(type == "火警"){
        key = "huojing"
    }else if(type == "屏蔽"){
        key = "pingbi"
    }else if(type == "复位"){
        key = "fuwei"
    }else if(type == "监管"){
        key = "jianguan"
    }else if(type == "启动"){
        key = "start"
    }else if(type == "反馈"){
        key = "fankui"
    }
    return key;
}

//防止页面后退
history.pushState(null, null, document.URL);
window.addEventListener('popstate', function () {
    history.pushState(null, null, document.URL);
});

function loadLayuiForm(){
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;
    });
}

$(function(){

    jQuery('.scrollbar-inner').scrollbar({});

    $.ajaxSetup({
        statusCode: {
            4999:function (data) {
                window.top.location.href = _serverIp + "/html/View/Login.html";
            },
		    499:function (data) {
		    	top.layer.confirm(
                    '您购买的服务已到期，请重新续费后使用。',
                    {btn: ['确定'] ,skin: 'layui-layer-confirm',area:['350px','170px'],title: '<img src="../Content/images/Home/logout_tip.png" class="icon_signOut">提示'},
                    function(index){
                    	window.top.location.href = _serverIp + "/html/View/Login.html";
                    }
	            );
		    }
        }
    });

    //var _loginInfo = JSON.parse(localStorage.getItem("LoginInfo"));
    if((_loginInfo == null || _loginInfo == "")&& window.top.location.href.indexOf("Login.html")==-1 && window.location.href.indexOf("Middle_GetCrt_Phone.html")==-1 && window.location.href.indexOf("video_a.html")==-1){
    	window.top.location.href = _serverIp + "/html/View/Login.html";
    }

    //layui.use('element', function(){var element = layui.element;});

});

//////////////////////////////个别推送页面弹出弹窗所用的方法
//用于火警推送或者RTU异常时弹出的视频弹窗
function getAlarmPushVideoPage(videoId,unitId){

    // getAlarmPushVideoPage(videoId,unitId)
    //var loginInfo = JSON.parse(localStorage.getItem("LoginInfo"));
    //if(loginInfo.isVidoePush != 1){
    //    return;
    //}

    $.ajax({
        type: "get",
        url: _serverIp + "/front/homeIndex/getVideoDetail",
        data:"videoID="+videoId,
        success: function (data) {
            //console.log(data);
            if (data.success) {
                var msg = data.obj;
                var token = msg.token,appKey = msg.appKey,serialnumber = msg.serialnumber,ip = msg.ip,port = msg.port,userName = msg.userName,password = msg.password;
                var deviceType = msg.plugInType;
                var videoIframeSrc="";
                // if(deviceType == 2){
                // 	if (!!window.ActiveXObject || "ActiveXObject" in window||window.navigator.userAgent.indexOf("MSIE 9.0")>=0){//如果浏览器为IE9
                //         videoIframeSrc = '/html/View/iframeUnitVideo_ie.html?token='+escape(token)+'&appKey='+escape(appKey)+'&serialnumber='+escape(serialnumber)
                //     }else{
                //     	videoIframeSrc = '/html/View/video_a.html?ip='+msg.ip
                //     }
                // }else if(deviceType == 0 || deviceType== 1 ){
                //     videoIframeSrc = '/html/View/camera.html?ip='+escape(msg.ip)+'&port='+escape(msg.port)+'&userName='+escape(msg.userName)+'&password='+escape(msg.password)+'&deviceType='+escape(msg.plugInType)
                // }
                if(_topAlarmVideoLayerIndex){
                	top.layer.close(_topAlarmVideoLayerIndex)
                }
                
                _topAlarmVideoLayerIndex = top.layer.open({
                    type: 2,
                    title: '视频',
                    closeBtn: 1,
                    area:['65%','70%'],
                    shade: 0.1,
                    skin: 'layui-layer-loginRecord',
                    // content:videoIframeSrc
                    content:"/html/View/newVideo.html?unitId="+unitId+"&token="+token+"&appKey="+appKey+"&serialnumber="+serialnumber+"&deviceType="+deviceType+"&ip="+ip+"&port="+port+"&userName="+userName+"&password="+password
                });

            }
        }
    });
}


//用于报警推送时的地图显示相应点位  ---  只用于维保首页的地图点位
function getAlarmPushPointInMap(unitid){
    if(!map){
        return;
    }
    var allOverlay = map.getOverlays();
    for (var i = 0; i < allOverlay.length; i++) {
        //if(allOverlay[i].lab=="point"){
        //    allOverlay[i].setAnimation(); //清除其他点的动画
        //}
        if(allOverlay[i].unitid == unitid){
            var point = new BMap.Point(allOverlay[i].point.lng,allOverlay[i].point.lat);
            map.centerAndZoom(point,_loginInfo.mapLevel); //地图中心点
            allOverlay[i].setAnimation(BMAP_ANIMATION_BOUNCE); //添加点位动画
        }
    }
}



