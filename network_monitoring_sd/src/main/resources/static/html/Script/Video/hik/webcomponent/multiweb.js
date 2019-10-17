// 初始化插件

// 全局保存当前选中窗口
function initHikWeb(cc,div) {
	// 检查插件是否已经安装过
    if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
	    alert("您还未安装过海康威视预览插件，请点击监控视频标签，点击链接下载WebComponents.exe，并关闭浏览器后安装！");
	    downloadWebComponent(div);
		return;
	}
	
	// 初始化插件参数及插入插件
	WebVideoCtrl.I_InitPlugin('100%', '100%', {
	    iWndowType: 1
	});
	WebVideoCtrl.I_InsertOBJECTPlugin(div);

	// 检查插件是否最新
	if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
	    alert("检测到新的插件版本！");
	    downloadWebComponent(div);
		return;
	}
}

function downloadWebComponent(div) {
    var div=$("#"+div).parent().children("div").first();
    div.append("<div style='width:100%;height:20px;background-color:lightblue;padding-top:10px;padding-left:10px;text-decoration:underline;'><a href='/Plugins/Hik/WebComponents.exe'>点击下载海康威视视频预览组件(WebComponent.exe)</a></div>");
}

// 显示操作信息
function showOPInfo(szInfo) {
}

// 显示回调信息
function showCBInfo(szInfo) {
}

// 登录
function clickLogin(cc,btn,callback) {
    var szIP = cc.CameraIP,
		szPort = cc.CameraPort,
		szUsername = cc.CameraUser,
		szPassword = cc.CameraPwd;

    //alert(szIP + ' ' + szPort + ' ' + szUsername + ' ' + szPassword);

	if ("" == szIP || "" == szPort) {
		return;
	}

	var iRet = WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
		success: function (xmlDoc) {
		    //alert(szIP + " 登录成功！");
		    //$(btn).css("background-color", "gray");
		    //$(btn).next().css("background-color", "gold");
		    cc.LoginUserId = 1;
		    callback();
		    setTimeout(function () {
		        getChannelInfo(cc);
		    }, 10);
		},
		error: function () {
		    alert(szIP + " 登录失败！");
		    cc.LoginUserId = 0;
		    callback();
		}
	});

	if (-1 == iRet) {
		alert(szIP + " 已登录过！");
	}
}

// 退出
function clickLogout(cc,btn) {
	var szIP = cc.CameraIP,
		szInfo = "";

	if (szIP == "") {
		return;
	}

	var iRet = WebVideoCtrl.I_Logout(szIP);
	if (0 == iRet) {
	    szInfo = "退出成功！";
	    //$(btn).css("background-color", "gray");
	    //$(btn).prev().css("background-color", "gold");
	    cc.LoginUserId = 0;
	} else {
		szInfo = "退出失败！";
	}
	showOPInfo(szIP + " " + szInfo);
}

// 获取设备信息
function clickGetDeviceInfo(cc) {
	var szIP = cc.CameraIP;

	if ("" == szIP) {
		return;
	}

	WebVideoCtrl.I_GetDeviceInfo(szIP, {
		success: function (xmlDoc) {
			var arrStr = [];
			arrStr.push("设备名称：" + $(xmlDoc).find("deviceName").eq(0).text() + "\r\n");
			arrStr.push("设备ID：" + $(xmlDoc).find("deviceID").eq(0).text() + "\r\n");
			arrStr.push("型号：" + $(xmlDoc).find("model").eq(0).text() + "\r\n");
			arrStr.push("设备序列号：" + $(xmlDoc).find("serialNumber").eq(0).text() + "\r\n");
			arrStr.push("MAC地址：" + $(xmlDoc).find("macAddress").eq(0).text() + "\r\n");
			arrStr.push("主控版本：" + $(xmlDoc).find("firmwareVersion").eq(0).text() + " " + $(xmlDoc).find("firmwareReleasedDate").eq(0).text() + "\r\n");
			arrStr.push("编码版本：" + $(xmlDoc).find("encoderVersion").eq(0).text() + " " + $(xmlDoc).find("encoderReleasedDate").eq(0).text() + "\r\n");
			
			showOPInfo(szIP + " 获取设备信息成功！");
			alert(arrStr.join(""));
		},
		error: function () {
			showOPInfo(szIP + " 获取设备信息失败！");
		}
	});
}

// 获取通道
function getChannelInfo(cc) {
	// 模拟通道
    WebVideoCtrl.I_GetAnalogChannelInfo(cc.CameraIP, {
		async: false,
		success: function (xmlDoc) {
			//var oChannels = $(xmlDoc).find("VideoInputChannel");
			//nAnalogChannel = oChannels.length;

			//$.each(oChannels, function (i) {
			//	var id = parseInt($(this).find("id").eq(0).text(), 10),
			//		name = $(this).find("name").eq(0).text();
			//	if ("" == name) {
			//		name = "Camera " + (id < 9 ? "0" + id : id);
			//	}
			//	oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
			//});
			//showOPInfo(szIP + " 获取模拟通道成功！");
		},
		error: function () {
		    showOPInfo(cc.CameraIP + " 获取模拟通道失败！");
		}
	});
	// 数字通道
    WebVideoCtrl.I_GetDigitalChannelInfo(cc.CameraIP, {
		async: false,
		success: function (xmlDoc) {
			//var oChannels = $(xmlDoc).find("InputProxyChannelStatus");

			//$.each(oChannels, function (i) {
			//	var id = parseInt($(this).find("id").eq(0).text(), 10),
			//		name = $(this).find("name").eq(0).text(),
			//		online = $(this).find("online").eq(0).text();
			//	if ("false" == online) {// 过滤禁用的数字通道
			//		return true;
			//	}
			//	if ("" == name) {
			//		name = "IPCamera " + ((id - nAnalogChannel) < 9 ? "0" + (id - nAnalogChannel) : (id - nAnalogChannel));
			//	}
			//	oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
			//});
			//showOPInfo(szIP + " 获取数字通道成功！");
		},
		error: function () {
		    showOPInfo(cc.CameraIP + " 获取数字通道失败！");
		}
	});
	// 零通道
    WebVideoCtrl.I_GetZeroChannelInfo(cc.CameraIP, {
		async: false,
		success: function (xmlDoc) {
			//var oChannels = $(xmlDoc).find("ZeroVideoChannel");
			
			//$.each(oChannels, function (i) {
			//	var id = parseInt($(this).find("id").eq(0).text(), 10),
			//		name = $(this).find("name").eq(0).text();
			//	if ("" == name) {
			//		name = "Zero Channel " + (id < 9 ? "0" + id : id);
			//	}
			//	if ("true" == $(this).find("enabled").eq(0).text()) {// 过滤禁用的零通道
			//		oSel.append("<option value='" + id + "' bZero='true'>" + name + "</option>");
			//	}
			//});
			//showOPInfo(szIP + " 获取零通道成功！");
		},
		error: function () {
		    showOPInfo(cc.CameraIP + " 获取零通道失败！");
		}
	});
}

// 开始预览
function clickStartRealPlay(cc,btn) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(cc.WndIndex),
		szIP = cc.CameraIP,
		iStreamType = cc.StreamType,
		iChannelID = cc.ChannelNum,// parseInt($("#channels").val(), 10),
		bZeroChannel =false,// $("#channels option").eq($("#channels").get(0).selectedIndex).attr("bZero") == "true" ? true : false,
		szInfo = "";

	if ("" == szIP) {
		return;
	}

	if (oWndInfo != null) {// 已经在播放了，先停止
		WebVideoCtrl.I_Stop();
	}
	//alert("IP:" + szIP + " 流码:" + iStreamType + "  通道:" + iChannelID + "Zero:" + bZeroChannel);
	//debugger;
	var iRet = WebVideoCtrl.I_StartRealPlay(szIP, {
		iStreamType: iStreamType,
		iChannelID: iChannelID,
		bZeroChannel: bZeroChannel
	});

	if (0 == iRet) {
	    szInfo = "开始预览成功！";
	    //$(btn).css("background-color", "gray");
	    //$(btn).next().css("background-color", "darkgreen");
	    cc.IsPlay = 1;
	} else {
	    szInfo = "开始预览失败！";
	    cc.IsPlay = 0;
	}

	showOPInfo(szIP + " " + szInfo);
}

// 停止预览
function clickStopRealPlay(cc,btn) {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(cc.WndIndex),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_Stop();
		if (0 == iRet) {
		    szInfo = "停止预览成功！";
		    //$(btn).css("background-color", "gray");
		    //$(btn).prev().css("background-color", "darkgreen");
		    cc.IsPlay = 0;
		} else {
			szInfo = "停止预览失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 打开声音
function clickOpenSound(cc,btn) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(cc.WndIndex),
		szInfo = "";

	if (oWndInfo != null) {
		var allWndInfo = WebVideoCtrl.I_GetWindowStatus();
		// 循环遍历所有窗口，如果有窗口打开了声音，先关闭
		for (var i = 0, iLen = allWndInfo.length; i < iLen; i++) {
			oWndInfo = allWndInfo[i];
			if (oWndInfo.bSound) {
				WebVideoCtrl.I_CloseSound(oWndInfo.iIndex);
				break;
			}
		}

		var iRet = WebVideoCtrl.I_OpenSound();

		if (0 == iRet) {
		    szInfo = "打开声音成功！";
		    //$(btn).css("background-color", "gray");
		    //$(btn).next().css("background-color", "blue");
		    cc.IsVoice = 1;
		} else {
		    szInfo = "打开声音失败！";
		    cc.IsVoice = 0;
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 关闭声音
function clickCloseSound(cc,btn) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(cc.WndIndex),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_CloseSound();
		if (0 == iRet) {
		    szInfo = "关闭声音成功！";
		    //$(btn).css("background-color", "gray");
		    //$(btn).prev().css("background-color", "blue");
		    cc.IsVoice = 0;
		} else {
			szInfo = "关闭声音失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 全屏
function clickFullScreen() {
	WebVideoCtrl.I_FullScreen(true);
}

// PTZ控制 9为自动，1,2,3,4,5,6,7,8为方向PTZ
var g_bPTZAuto = false;
function mouseDownPTZControl(iPTZIndex) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		bZeroChannel = false;// $("#channels option").eq($("#channels").get(0).selectedIndex).attr("bZero") == "true" ? true : false,
        iPTZSpeed = 4, //$("#ptzspeed").val(),
		bStop = false;

	if (bZeroChannel) {// 零通道不支持云台
		return;
	}
	
	if (oWndInfo != null) {
		if (9 == iPTZIndex && g_bPTZAuto) {
			iPTZSpeed = 0;// 自动开启后，速度置为0可以关闭自动
			bStop = true;
		} else {
			g_bPTZAuto = false;// 点击其他方向，自动肯定会被关闭
			bStop = false;
		}

		WebVideoCtrl.I_PTZControl(iPTZIndex, bStop, {
			iPTZSpeed: iPTZSpeed,
			success: function (xmlDoc) {
				if (9 == iPTZIndex) {
					g_bPTZAuto = !g_bPTZAuto;
				}
				showOPInfo(oWndInfo.szIP + " 开启云台成功！");
			},
			error: function () {
				showOPInfo(oWndInfo.szIP + " 开启云台失败！");
			}
		});
	}
}

// 方向PTZ停止
function mouseUpPTZControl() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

	if (oWndInfo != null) {
		WebVideoCtrl.I_PTZControl(1, true, {
			success: function (xmlDoc) {
				showOPInfo(oWndInfo.szIP + " 停止云台成功！");
			},
			error: function () {
				showOPInfo(oWndInfo.szIP + " 停止云台失败！");
			}
		});
	}
}

// 设置预置点
function clickSetPreset() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		iPresetID = parseInt($("#preset").val(), 10);

	if (oWndInfo != null) {
		WebVideoCtrl.I_SetPreset(iPresetID, {
			success: function (xmlDoc) {
				showOPInfo(oWndInfo.szIP + " 设置预置点成功！");
			},
			error: function () {
				showOPInfo(oWndInfo.szIP + " 设置预置点失败！");
			}
		});
	}
}

// 调用预置点
function clickGoPreset(preset) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		iPresetID = preset;// parseInt($("#preset").val(), 10);

	if (oWndInfo != null) {
		WebVideoCtrl.I_GoPreset(iPresetID, {
			success: function (xmlDoc) {
				showOPInfo(oWndInfo.szIP + " 调用预置点成功！");
			},
			error: function () {
				showOPInfo(oWndInfo.szIP + " 调用预置点失败！");
			}
		});
	}
}

// 暂停
function clickPause() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_Pause();
		if (0 == iRet) {
			szInfo = "暂停成功！";
		} else {
			szInfo = "暂停失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 恢复
function clickResume() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_Resume();
		if (0 == iRet) {
			szInfo = "恢复成功！";
		} else {
			szInfo = "恢复失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 慢放
function clickPlaySlow() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_PlaySlow();
		if (0 == iRet) {
			szInfo = "慢放成功！";
		} else {
			szInfo = "慢放失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// 快放
function clickPlayFast() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0),
		szInfo = "";

	if (oWndInfo != null) {
		var iRet = WebVideoCtrl.I_PlayFast();
		if (0 == iRet) {
			szInfo = "快放成功！";
		} else {
			szInfo = "快放失败！";
		}
		showOPInfo(oWndInfo.szIP + " " + szInfo);
	}
}

// OSD时间
function clickGetOSDTime() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);
	
	if (oWndInfo != null) {
		var szTime = WebVideoCtrl.I_GetOSDTime();
		if (szTime != -1) {
			$("#osdtime").val(szTime);
			showOPInfo(oWndInfo.szIP + " 获取OSD时间成功！");
		} else {
			showOPInfo(oWndInfo.szIP + " 获取OSD时间失败！");
		}
	}
}

// 检查插件版本
function clickCheckPluginVersion() {
	var iRet = WebVideoCtrl.I_CheckPluginVersion();
	if (0 == iRet) {
		alert("您的插件版本已经是最新的！");
	} else {
		alert("检测到新的插件版本！");
	}
}

function PTZZoomIn() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(10, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 调焦+成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  调焦+失败！");
            }
        });
    }
}

function PTZZoomout() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(11, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 调焦-成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  调焦-失败！");
            }
        });
    }
}

function PTZZoomStop() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(11, true, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 调焦停止成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  调焦停止失败！");
            }
        });
    }
}

function PTZFocusIn() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(12, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 聚焦+成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  聚焦+失败！");
            }
        });
    }
}

function PTZFoucusOut() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(13, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 聚焦-成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  聚焦-失败！");
            }
        });
    }
}

function PTZFoucusStop() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(12, true, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 聚焦停止成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  聚焦停止失败！");
            }
        });
    }
}

function PTZIrisIn() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(14, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 光圈+成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  光圈+失败！");
            }
        });
    }
}

function PTZIrisOut() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(15, false, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 光圈-成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  光圈-失败！");
            }
        });
    }
}

function PTZIrisStop() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(0);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(14, true, {
            iWndIndex: 0,
            success: function (xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 光圈停止成功！");
            },
            error: function () {
                showOPInfo(oWndInfo.szIP + "  光圈停止失败！");
            }
        });
    }
}

// 切换模式
function changeIPMode(iType) {
	var arrPort = [0, 7071, 80];

	$("#serverport").val(arrPort[iType]);
}

// 获取设备IP
function clickGetDeviceIP() {
	var iDeviceMode = parseInt($("#devicemode").val(), 10),
		szAddress = $("#serveraddress").val(),
		iPort = parseInt($("#serverport").val(), 10) || 0,
		szDeviceID = $("#deviceid").val(),
		szDeviceInfo = "";

	szDeviceInfo = WebVideoCtrl.I_GetIPInfoByMode(iDeviceMode, szAddress, iPort, szDeviceID);

	if ("" == szDeviceInfo) {
		showOPInfo("设备IP和端口解析失败！");
	} else {
		showOPInfo("设备IP和端口解析成功！");

		var arrTemp = szDeviceInfo.split("-");
		$("#loginip").val(arrTemp[0]);
		$("#deviceport").val(arrTemp[1]);
	}
}