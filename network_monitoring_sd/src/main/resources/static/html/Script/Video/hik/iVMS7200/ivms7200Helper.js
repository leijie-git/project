//初始化控件
function InitOcx7200(config) {
    if (true)//window.ActiveXObject) 
    {
        try {
            //根据控件的PorgID来判断是否注册，未注册则抛出异常
            config.ActiveXObj = new ActiveXObject("PPVSGUARD.PpvsguardCtrl.1");
            ////设置窗口模式
            //config.PlayOCX = document.getElementById("ppvs");
            if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
                return "控件已注册但页面对象不存在！";
            }
            config.PlayOCX.SetActiveXShowMode(0, 0);//实时预览模式，需要修改模式或者窗口UI可参考SetActiveXShowMode接口说明

            return "控件已注册并初始化完成！";
        }
        catch (e) {
            return "控件未注册或未安装！";
        }
    }
}

function connDevice7200(config) {
    //PlayOCX = $("#ppvs"); //document.getElementById("ppvs");
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }
    if (typeof config.DeviceID == "undefined" || config.DeviceID == null || config.DeviceID == "") {
        return "设备ID不能为空！";
    }
    config.iConnHandle = config.PlayOCX.ConnectDeviceByACS(config.DeviceID, config.RegIP, config.RegPort, "admin", "12345");

    if (config.iConnHandle < 0) {
        return "连接设备失败！";
    }
    else {
        return "连接设备成功！";
    }
}

//实时预览
function Play7200(config) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    if (config.iConnHandle < 0) {
        return "请先连接设备，并确保连接成功！";
    }

    //有视频播放，则先停止
    if (config.isPlay > 0) {
        StopPlay7200(config);
    }
    //设置设备注册线路编号，以iVMS-7200添加设备选择注册线路匹配
    config.PlayOCX.SetDeviceNetLine(config.iRegisterNo);

    //客户端到流媒体采用TCP，流媒体到设备采用TCP取流
    config.isPlay = config.PlayOCX.StreamPlayStartByTCP(config.StreamIP, config.StreamPort, config.DeviceID, config.iChannel, config.iStreamType, 1);

    if (-1 == config.isPlay) {
        return "预览失败！";
    }

    //设置窗口的设备信息显示
    //var szMode = "TCP";
    //config.PlayOCX.SetDeviceInfoForShow(">>> 转发取流(" + szMode + ") <<<");
}

//停止预览
function StopPlay7200(config) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    if (config.isPlay >= 0) {
        if (config.PlayOCX.StreamPlayStopByTCP()) {
            config.isPlay = -1;
            return "预览已停止！";
        }
    }
    else {
        return "预览未开启！";
    }
}

//断开与设备连接
function DisConnect7200(config) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    if (config.iConnHandle > 0) {
        if (config.PlayOCX.DisConnectDevice()) {
            config.iConnHandle = -1;
            return "与设备已断开连接！";
        }
    }
    else {
        return "设备未连接！";
    }
}

//打开声音
function OpenSound7200(config) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    if (config.isPlay < 0) {
        return "请确保正在预览视频！";
    }
    else if (false == config.PlayOCX.OpenSound()) {
        return "打开声音失败！";
    }
    config.isVoice = 1;
}

//关闭声音
function CloseSound7200(config) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    if (false == config.PlayOCX.CloseSound()) {
        return "关闭声音失败！";
    }
    config.isVoice = 0;
}

//云台控制
function PTZ7200(config,iCommand, iDo) {
    if (typeof config.ActiveXObj == "undefined" || config.ActiveXObj == null) {
        return "控件未注册！";
    }
    if (typeof config.PlayOCX == "undefined" || config.PlayOCX == null) {
        return "控件已注册但页面对象不存在！";
    }

    config.PlayOCX.PTZControl(config.iChannel, iCommand, iDo, 3);
    if (1 == iDo) {
        return "云台操作已完成!";
    }
    else if (0 == iDo) {
        return "云台操作已启动...";
    }
}