$(function () {
	getStatic();
    //记住密码
    var remember_pwd_status=localStorage.getItem("remember_pwd_status");
    var remember_loginAccount=localStorage.getItem("remember_loginAccount");
    var remember_pwd=localStorage.getItem("remember_pwd");


    if(remember_pwd_status==1){
        $("input[name=remember_pwd]").prop("checked",true);
        $("input[name=userName]").val(remember_loginAccount);
        $("input[name=passWord]").val(remember_pwd);
    }else {
        $("input[name=remember_pwd]").prop("checked",false);
        $("input[name=passWord][type=text]").val("");
    }

    $("input[name=passWord]").focus(function () {
        if(document.addEventListener){
            document.addEventListener("keyup",dd,false)
        }else{
            document.attachEvent("onkeyup",dd)
        }
    });
    $("input[name=userName]").focus(function () {
        if(document.addEventListener){
            document.addEventListener("keyup",dd,false)
        }else{
            document.attachEvent("onkeyup",dd)
        }
    });


    function dd(e) {
        if(e.keyCode==13){
            login()
        }
    }

    enterlogin();
    // //回车登录
    function enterlogin() {
        if (event.keyCode == 13) {
            event.returnValue = false;
            event.cancel = true;
            login();
        }
    }
});
function getStatic(){
    $.ajax({
        type: "get",
        url: baseUrl + "/getStatic",
        success: function (data) {
            if (data.success) {
                var obj = data.obj
                $('.title').html(obj.title);
            }
        }
    });
}
//判断用户名只能输入数字
//$("#userName").keyup(function () {
//    $(this).val($(this).val().replace(/[^0-9.]/g, ''));
//}).css("ime-mode", "disabled"); //CSS设置输入法不可用

var code ; //在全局定义验证码
//产生验证码
function createCode(type){
    var inputCode = document.getElementById("input").value.toUpperCase();
    if (inputCode.length <= 0 || type == 0) {
        code = "";
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("code");
        var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
            'S','T','U','V','W','X','Y','Z');//随机数
        for(var i = 0; i < codeLength; i++) {//循环操作
            var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
            code += random[index];//根据索引取得随机数加到code上
        }
        checkCode.value = code;//把code值赋给验证码
    }
}

var count = 0;
var flag = false;
var baseUrl=$("#baseUrl").val();
function login() {
//    if (count >= 3) {
//        $("#codeShow").show();
//        createCode();
//        var inputCode = document.getElementById("input").value.toUpperCase(); //取得输入的验证码并转化为大写
//        if(inputCode.length <= 0 && !flag) { //若输入的验证码长度为0
//            layer.msg("请输入验证码！"); //则弹出请输入验证码
//            return;
//        }
//        if(inputCode != code ) { //若输入的验证码与产生的验证码不一致时
//            layer.msg("验证码输入错误！"); //则弹出验证码输入错误
//            document.getElementById("input").value = "";//清空文本框
//            createCode();//刷新验证码
//            return;
//        } else { //输入正确时
//            flag = true;
//        }
//    }
    var loginAccount = $('input[name=userName]').val();
    var password = $('input[name=passWord]').val();
    if(loginAccount==""|| password=="" ){
        layer.msg("账号或密码不能为空！");
        return;
    };

    var data = {password: password, loginAccount: loginAccount};
    var load = layer.load();
    $.ajax({
        type : "post",
        async : true,
        data : data,
        url : baseUrl + '/console/login',
        dataType : "json",
        success : function(result) {
            layer.close(load);
            $("form").submit(function () {
                return false;
            })
            if (!result.success) {
//                count++;
                layer.msg(result.msg);
//                document.getElementById("input").value = "";//清空文本框
//                createCode();//刷新验证码
                return;
            } else {
                // 记住密码状态
                var rem_status=$("#remember_pwd").prop("checked");
                if(rem_status){
                    localStorage.setItem("remember_loginAccount",encodeURIComponent(loginAccount));
                    localStorage.setItem("remember_pwd",encodeURIComponent(password));
                    localStorage.setItem("remember_pwd_status",1);
                }else{
                    localStorage.setItem("remember_loginAccount","");
                    localStorage.setItem("remember_pwd","");
                    localStorage.setItem("remember_pwd_status",0);
                }

                var date = new Date();
                var userinfo = result.obj;
                localStorage.setItem("Login",JSON.stringify(userinfo));
                var phone = userinfo.phone;
                //判断是否是当月1号
                if (date.getDate() == 1 && phone) {
	            	layer.confirm(
                        '请确认手机号是否正确：'+phone,
                        {btn: ['确定', '取消'] ,skin: 'layui-layer-confirm',area:['350px','170px'],title: '提示'},
                        function(index){
                        	count = 0;
                        	setTimeout(function () {
                        		window.location.href =  baseUrl + "/console/index";
                        	}, 1000);
                        }
	                );
                }else{
                	layer.msg('登录成功！');
                	count = 0;
                	setTimeout(function () {
                		window.location.href =  baseUrl + "/console/index";
                	}, 1000);
                }
            }
        },
        error : function(e) {

        }
    });
}

