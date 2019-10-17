$(function () {
    var baseUrl = $("#baseUrl").val();
    $("#phoneNo").val('');

    $("#resetpwd").click(function () {
        getVerificationCode();
    });

    function getVerificationCode() {
        var phoneNo = $("#phoneNo").val();
        if (phoneNo == "" || phoneNo == null || phoneNo == undefined) {
            layer.msg("登录名不能为空！");
            return;
        } 
        $.ajax({
            url: baseUrl + "/console/resetPassWord",
            type: "post",
            data: {account:phoneNo},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    layer.msg(data.msg);
                    setTimeout("parent.location.href='"+baseUrl+"/console/index'", 1500);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {

            }
        });
    }
});