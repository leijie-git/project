

var hostAddFile = 'http://'+window.location.host+'/';
var hostAdd = hostAddFile + 'wechat';

$(function() {})

//接收地址栏参数
function GetQuery(key) {
    var search = location.search.slice(1); //得到get方式提交的查询字符串
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

//关闭微信页面的方法
function closeWXPage() {
    setTimeout(function() {
        document.addEventListener('WeixinJSBridgeReady', function() { parent.WeixinJSBridge.call('closeWindow'); }, false);
        parent.WeixinJSBridge.call('closeWindow');
    }, 500);
}

//时间格式转换
function renderTime(data) {
    if (data == null || data == undefined || data == '') {
        return '未获取到时间';
    }
    var da = eval('new ' + data.replace('/', '', 'g').replace('/', '', 'g'));
    var yearT = da.getFullYear();
    var monthT = (da.getMonth() + 1);
    var dateT = da.getDate();
    var hourT = da.getHours();
    var minT = da.getMinutes();
    var secondT = da.getSeconds();
    if (monthT < 10) {
        monthT = '0' + monthT;
    }
    if (dateT < 10) {
        dateT = '0' + dateT;
    }
    if (hourT < 10) {
        hourT = '0' + hourT;
    }
    if (minT < 10) {
        minT = '0' + minT;
    }
    if (secondT < 10) {
        secondT = '0' + secondT;
    }
    youWant = yearT + '-' + monthT + '-' + dateT + ' ' + hourT + ':' + minT + ':' + secondT;
    return youWant; //da.getFullYear() + "年" + da.getMonth() + "月" + da.getDay() + "日" + da.getHours() + ":" + da.getSeconds() + ":" + da.getMinutes();
}