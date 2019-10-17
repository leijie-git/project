/////////////////////////////////////////
/////////////////////////////////////////
// 关键属性，用来记录设备登陆信息
var loginParams = {
    ip: '192.168.1.108',
    port: 80,
    uname: 'admin',
    pwd: '12345',
    rtspport: 554
};
/////////////////////////////////////////
/////////////////////////////////////////

var browse = {};
//免费赠送一种检测IE浏览器的方法。A method to check weather the browser id IE for FREE.
browse.ie = document.getElementsByTagName('html')[0].className == 'is_ie' ? true : false;
var reg = /(msie\s|trident.*rv:)([\w.]+)/;
browse.ieTest = reg.exec(navigator.userAgent.toLowerCase());
browse.ie = browse.ieTest != null ? true : false;
/**
 * 以下内容用来设置编码配置，暂时只考虑2路码流，若多码流需要重新配置
 * @type {Array}
 */
var bitRateAll = [32, 48, 64, 80, 96, 128, 160, 192, 224, 256, 320, 384, 448, 512, 640, 768, 896, 1024, 1280, 1536, 1792, 2048, 4096, 6144, 8192, 10240, 12288, 14336, 16384, 18432, 20480, 22528, 'Customize'];
var encodeConfig = {
    jsonEncode: null, // encode配置
    tmpjsonEncode0: null,
    jsonEncodeCap: null, //  编码能力集
    jsonEncodeCapTmp: null,

    modeMain: 0, //当前主码流码流类型: 0-普通, 1-动检, 2-报警 
    modeExtr: 0, //当前辅码流  0~2       
    mainAry: [], //主码流分辨率能力
    extrAry: [], //辅码流分辨率能力
    ischangebitrate: false, //是否编码特殊处理
    resolutionDialogflag: false, // 65A（智能球）切换分片率是不是给出提示的标志
    extraTip: '', //辅码流0帧时 提示

    cpu: false,
    RotateResolutionArr: [],
    ExtraStreamNum: 1,
    H264Type: {
        "Main": "H.264",
        "Baseline": "H.264B",
        "High": "H.264H"
    },
    render: function () {
        encodeConfig.bind();
        encodeConfig.ischangebitrate = false;
        encodeConfig.modeMain = 0; //重新初始化
        encodeConfig.modeExtr = 0 //重新初始化   

        // 对多码流设备处理             
        $('video_extraAudioList').empty();
        if (encodeConfig.ExtraStreamNum < 2) {
            $('video_extraAudioList').style.display = 'none';
        } else {
            $('video_extraAudioList').style.display = '';
            for (var i = 0; i < encodeConfig.ExtraStreamNum; i++) {
                $('video_extraAudioList').options.add(new Option(tl('Second Stream' + (i + 1)), i));
            }
        }

        $('video_streamType0').empty();
        // 当且仅当报警输入数小于1且无SD卡时，无报警码流
        // if (ALARM_IN_NUMBER < 1 && !hasSDCard) {
        $('video_streamType0').options.add(new Option(tl('General'), 'general'));
        //      $('video_streamType0').options.add(new Option(tl('Motion'), 'motion'));
        //  } else {
        //      $('video_streamType0').options.add(new Option(tl('General'), 'general'));
        //      $('video_streamType0').options.add(new Option(tl('Motion'), 'motion'));
        //      $('video_streamType0').options.add(new Option(tl('Alarm'), 'alarm'));
        //  }

        var strResult = plugin.ocx.GetDeviceConfig("Encode");
        if (strResult) {
            encodeConfig.jsonEncode = JSON.decode(strResult);
            encodeConfig.tmpjsonEncode0 = $unlink(encodeConfig.jsonEncode);
            var strsResult = plugin.ocx.GetConfigCaps("Encode", strResult);
            if (strsResult) {
                encodeConfig.jsonEncodeCap = JSON.decode(strsResult);
                encodeConfig.jsonEncodeCapTmp = $unlink(encodeConfig.jsonEncodeCap);
                encodeConfig._initPage(0);
                encodeConfig._putvalue(0);
                encodeConfig.ischangebitrate = true;
            }
        }
    },

    // 根据能力渲染界面
    _initPage: function () {
        encodeConfig._putCapMain(encodeConfig.jsonEncodeCap, 0, 0);
        encodeConfig._putCapExtr(encodeConfig.jsonEncodeCap, 0, 0);
    },

    // 界面赋值
    _putvalue: function () {
        encodeConfig._putvalueExtr(encodeConfig.tmpjsonEncode0, 0, 0);
        encodeConfig._putvalueMain(encodeConfig.tmpjsonEncode0, 0, 0);
    },

    //参数n: 通道; mode: 码流类型(General, Motion, Alarm)
    _putCapMain: function (json, n, mode, jsonTmp) {
        var mainCap = json[n].MainFormat[mode];
        $('video_streamType0').selectedIndex = mode;
        $('video_compression0').innerHTML = '';

        if (mainCap.Video.H264Profile) {
            mainCap.Video.H264Profile.each(function (item) {
                $('video_compression0').options.add(new Option(tl(item), encodeConfig.H264Type[item]));
            });
        }
        mainCap.Video.CompressionTypes.each(function (item) {
            if (item == 'H.264') {
                if (!mainCap.Video.H264Profile) {
                    $('video_compression0').options.add(new Option(tl(item), item));
                    $('video_compression0').options.add(new Option(tl('H.264B'), 'H.264B'));
                    $('video_compression0').options.add(new Option(tl('H.264H'), 'H.264H'));
                }
            } else {
                $('video_compression0').options.add(new Option(tl(item), item));
            }
        });

        encodeConfig.mainAry = mainCap.Video.ResolutionTypes;
        // 分辨率加载
        if (mode == 0) {
            encodeConfig._insertResolution(mainCap.Video.ResolutionTypes, $('video_resolution0'));
        }

        // 帧率选择框加载
        $('video_fps0').innerHTML = '';
        for (var i = 0; i < mainCap.Video.FPSMax; i++) {
            $('video_fps0').options.add(new Option((i + 1), (i + 1)));
        }

    },

    // 同主码流
    _putCapExtr: function (json, n, mode) {
        var extrCap;
        if (encodeConfig.jsonEncodeCap[n].ExtraFormat == null) {
            $('video_subStream_con').style.display = 'none';
            $('video_showSnapExtra').style.display = 'none';
            return;
        } else {
            extrCap = json[n].ExtraFormat[mode];
        }

        $('video_compression1').innerHTML = '';

        if (extrCap.Video.H264Profile) {
            extrCap.Video.H264Profile.each(function (item) {
                $('video_compression1').options.add(new Option(tl(item), encodeConfig.H264Type[item]));
            });
        }

        extrCap.Video.CompressionTypes.each(function (item) {
            if (item == 'H.264') {
                if (!extrCap.Video.H264Profile) {
                    $('video_compression1').options.add(new Option(tl(item), item));
                    $('video_compression1').options.add(new Option(tl('H.264B'), 'H.264B'));
                    $('video_compression1').options.add(new Option(tl('H.264H'), 'H.264H'));
                }
            } else {
                $('video_compression1').options.add(new Option(tl(item), item));
            }
        });

        //alert($('video_compression1').value);
        encodeConfig.extrAry = extrCap.Video.ResolutionTypes;

        encodeConfig._insertResolution(extrCap.Video.ResolutionTypes, $('video_resolution1'));
        $('video_fps1').innerHTML = '';
        var mainFPSMax = encodeConfig.jsonEncodeCapTmp[0].MainFormat[0].Video.FPSMax;
        //辅码流帧率能力为0时 下拉框用配置渲染
        if (extrCap.Video.FPSMax == 0) {
            var extraPFS = encodeConfig.tmpjsonEncode0[0].ExtraFormat[encodeConfig.modeExtr].Video.FPS;
            $('video_fps1').options.add(new Option(extraPFS, extraPFS));

        }
        for (var i = 0; i < extrCap.Video.FPSMax; i++) {
            $('video_fps1').options.add(new Option((i + 1), (i + 1)));
        }

    },

    _insertResolution: function (ary, o) {
        o.innerHTML = '';
        ary.each(function (item, index) {
            if (item == '720') item = '720P';
            if (item == '1080') item = '1080P';
            if (item == '1_3M') item = '1.3M';
            if (item == '2_5M') item = '2.5M';

            var resName = encodeConfig._getResolutionName(item); //获得当前这种分辨率要显示在界面下拉框括号前面的形式。
            o.options.add(new Option(resName + ' ' + '(' + getWidthH(item) + ')', item));
            encodeConfig.resolutionDialogflag && (o.options[index].title = tl('Resolution Change'))
        });
        //alert(o.innerHTML)
    },
    //根据内部交互的能力中的分辨率的形式，输出要显示在界面结果。
    //部分分辨率没有固定的名称。
    _getResolutionName: function (res) {
        var name = '';
        switch (res) {
        case 'BCIF':
            name = '2CIF';
            break;
        case '5_1M':
            name = '5M';
            break;
        case '2592x2592':
            name = '6M';
            break;
        case '2592X1520':
            name = '4M';
            break;
        default:
            name = res;
            break;
        }
        return name;
    },

    // 界面赋值
    _putvalueMain: function (json, n, mode) {
        var jsonMain = json[n].MainFormat[mode];
        //console.log(JSON.encode(jsonMain));
        $('video_streamType0').selectedIndex = mode;

        // 报警码流，动检码流与普通码流分辨率、编码模式保持一致
        if (mode == 0) {
            $('video_resolution0').disabled = false;
            $('video_resolution0').value = getFrame(jsonMain.Video.Width, jsonMain.Video.Height);
            $('video_compression0').disabled = false;
        } else {
            $('video_resolution0').disabled = true;
            $('video_resolution0').value = getFrame(jsonMain.Video.Width, jsonMain.Video.Height);
            $('video_compression0').disabled = true;
        }

        $('video_quality0').value = jsonMain.Video.Quality;
        //码流已经在initBitSelt()中赋值
        $('video_gop0').value = jsonMain.Video.GOP;

        //$('c_audio0').checked = jsonMain.AudioEnable; 
        if (json[n].MainFormat[0].Video.Compression == 'H.264' && json[n].MainFormat[0].Video.Profile == 'Baseline') {
            $('video_compression0').value = 'H.264B';
        } else if (json[n].MainFormat[0].Video.Compression == 'H.264' && json[n].MainFormat[0].Video.Profile == 'High') {
            $('video_compression0').value = 'H.264H';
        } else {
            $('video_compression0').value = json[n].MainFormat[0].Video.Compression;
        }


        if ($('video_compression0').selectedIndex <= 0) {
            $('video_compression0').selectedIndex = 0;
        }
        if ($('video_resolution0').selectedIndex <= 0) {
            $('video_resolution0').selectedIndex = 0;
        }

        //MJPEG:只考虑CBR(限定码流)，不考虑VBR(可变码流)
        //当分辨率为3M时，码流为8M，最大帧率可设为12; 1080P以及SXGA:最大帧率可设为15
        if (jsonMain.Video.Compression == 'MJPG') {
            $('video_bitctrl0').value = 'CBR';
            $('video_bitctrl0').disabled = true;
            $('video_showQuality0').style.display = 'none';
            $('video_showGop0').style.display = 'none';
        } else {
            $('video_showGop0').style.display = 'block';
            $('video_bitctrl0').disabled = false;
            $('video_bitctrl0').value = jsonMain.Video.BitRateControl;
            if ($('video_bitctrl0').selectedIndex == 0) $('video_showQuality0').style.display = 'none';
            else $('video_showQuality0').style.display = '';
        }
        $('video_fps0').selectedIndex = jsonMain.Video.FPS - 1;

        // 对728wp设备做帧间间隔特殊情况处理
        var num = $('video_fps0').getElements('option').length;
        if (jsonMain.Video.FPS > num) {
            $('video_fps0').selectedIndex = num - 1;
            $('video_gop0').value = num * 2;
        }
        $('video_gop0_range').setProperty('text', '(' + ($('video_fps0').selectedIndex + 1) + '~150)');


        //计算码流参考值和Media -> Encode.cpp -> getImageBitRate保持一致
        //  var jsonVM  = tempCap[n].MainFormat[mode].Video;
        var min_max = encodeConfig.jsonEncodeCapTmp[n].MainFormat[mode].Video.BitRateOptions;
        var bit = jsonMain.Video.BitRate;
        encodeConfig._initBitSelt(min_max, bit, 0);

        $('video_bitrefer0').setProperty('text', min_max[0] + '-' + min_max[1] + 'Kb/S');
    },
    /**
     * 安霸平台下特殊处理  主码流为MJPEG 1080P 限制16M上限
     * @ignore
     */
    _A5SBitMinMax: function (min_max) {
        if ($('video_resolution0').value == '1080P' && $('video_compression0').value == 'MJPG' && encodeConfig.encodeConfig.cpu) {
            if (min_max[0] > 16384) {
                min_max[0] = 16384;
            }
            if (min_max[1] > 16384) {
                min_max[1] = 16384;
            }
            return min_max;
        }
        return false;
    },
    _putvalueExtr: function (json, n, mode) {

        if (json[n].ExtraFormat[mode] == null || $('video_subStream_con').style.display == 'none') return;
        var jsonExtr = json[n].ExtraFormat[mode];

        $('video_resolution1').value = getFrame(jsonExtr.Video.Width, jsonExtr.Video.Height);
        $('video_quality1').value = jsonExtr.Video.Quality;
        //码流已经在initBitSelt()中赋值
        $('video_gop1').value = jsonExtr.Video.GOP;

        $('video_subStream_enable').checked = jsonExtr.VideoEnable;

        if (jsonExtr.Video.Compression == 'H.264' && jsonExtr.Video.Profile == 'Baseline') {
            $('video_compression1').value = 'H.264B';
        } else if (jsonExtr.Video.Compression == 'H.264' && jsonExtr.Video.Profile == 'High') {
            $('video_compression1').value = 'H.264H';
        } else {
            $('video_compression1').value = jsonExtr.Video.Compression;
        }

        if ($('video_compression1').selectedIndex <= 0) $('video_compression1').selectedIndex = 0;
        // 保证辅码流有值可选。
        // 具体的辅码流的分辨率必须要小于主码流，是应用保证的，切换分辨率，会去请求此时的能力。
        // 应用会返回的辅码流能力中，保证比用户选择的主码流分辨率小即可。
        // 因为当前配置是大分辨率，但是下拉框是能力渲染，没有那么大，所以selectindex会是-1。
        // 火狐浏览器下是0。因为渲染完下拉框，默认是0，再赋一个不存在的值，火狐浏览器下拉框不变。
        if ($('video_resolution1').selectedIndex <= 0) {
            $('video_resolution1').selectedIndex = 0;
            var width_hExtra = getWidthH($('video_resolution1').value).split('*');
            jsonExtr.Video.Width = width_hExtra[0] - 0;
            jsonExtr.Video.Height = width_hExtra[1] - 0;
        }
        if (jsonExtr.Video.Compression == 'MJPG') {
            $('video_bitctrl1').value = 'CBR';
            $('video_bitctrl1').disabled = true;
            $('video_showQuality1').style.display = 'none';
            $('video_showGop1').style.display = 'none';
        } else {
            $('video_showGop1').style.display = '';
            $('video_bitctrl1').disabled = false;
            $('video_bitctrl1').value = jsonExtr.Video.BitRateControl;
            if ($('video_bitctrl1').selectedIndex == 0) $('video_showQuality1').style.display = 'none';
            else $('video_showQuality1').style.display = '';
        }
        var extraFPSMax = $('video_fps1').length;
        if (extraFPSMax < jsonExtr.Video.FPS) {
            $('video_fps1').selectedIndex = extraFPSMax - 1;
        } else {
            $('video_fps1').value = jsonExtr.Video.FPS;
        }
        // 对728WP
        var num = $('video_fps1').getElements('option').length;
        if (jsonExtr.Video.FPS > num) {
            $('video_fps1').selectedIndex = num - 1;
            $('video_gop1').value = $('video_fps1').value * 2;
        }
        $('video_gop1_range').setProperty('text', '(' + ($('video_fps1').value - 0) + '~150)');

        var min_max = encodeConfig.jsonEncodeCapTmp[n].ExtraFormat[mode].Video.BitRateOptions;
        var bit = jsonExtr.Video.BitRate;

        encodeConfig._initBitSelt(min_max, bit, 1);
        $('video_bitrefer1').setProperty('text', min_max[0] + '-' + min_max[1] + 'Kb/S');
    },

    //range: 码流范围, bit: 码流值, mod: 主码流(0)or辅码流(1) 
    _initBitSelt: function (range, bit, mod) {

        var flag = false;
        $('video_bitrate' + mod).set('text', '');

        var newArray = [];
        for (var i = 0; i < bitRateAll.length - 1; i++) {
            if (bitRateAll[i] >= range[0] && bitRateAll[i] <= range[1]) {
                $('video_bitrate' + mod).options.add(new Option(bitRateAll[i], bitRateAll[i]));
                if (bitRateAll[i] == bit) flag = true;
                newArray.push(bitRateAll[i]);
            }
        }
        var middleRange = ((range[0] + range[1]) / 2).toInt();

        $('video_bitrate' + mod).options.add(new Option(tl('Customize'), 'customize'));
        if (flag) {
            $('video_bitrate' + mod).value = bit;
            $('video_customwrap' + mod).style.display = 'none';
            $('video_custom' + mod).value = bit;
        } else {
            if (encodeConfig.ischangebitrate) {
                var k = 0;
                for (var i = 0; i < newArray.length; i++) {
                    if (newArray[i] >= middleRange) {
                        $('video_customwrap' + mod).style.display = 'none';
                        $('video_bitrate' + mod).value = newArray[i];
                        k = 1;
                        break;
                    }
                }
                if (k == 0) {
                    $('video_bitrate' + mod).value = 'customize';
                    $('video_customwrap' + mod).style.display = '';
                    $('video_custom' + mod).value = middleRange;
                }
            } else {
                if (range[0] > range[1]) range[1] = range[0];
                if (bit >= range[0] && bit <= range[1]) {
                    $('video_bitrate' + mod).value = 'customize';
                    $('video_customwrap' + mod).style.display = '';
                    $('video_custom' + mod).value = bit;
                } else {
                    var bitrate0 = encodeConfig._getSuggestBitrate(range[0], range[1]);
                    if (bitrate0 < 0) $('video_bitrate' + mod).selectedIndex = 0;
                    else $('video_bitrate' + mod).value = bitrate0;
                    $('video_customwrap' + mod).style.display = 'none';
                    if ($('video_bitrate' + mod).selectedIndex < 0 || isNaN(getSelect('video_bitrate' + mod))) {
                        $('video_bitrate' + mod).value = 'customize';
                        $('video_customwrap' + mod).style.display = '';
                        $('video_custom' + mod).value = bitrate0;
                    }
                }
            }
        }
    },

    _getSuggestBitrate: function (minbitrate, maxbitrate) {
        var sBitrate = [32, 48, 64, 80, 96, 128, 160, 192, 224, 256, 320, 384, 448, 512, 640, 768, 896, 1024, 1280, 1536, 1792, 2048, 4096, 6144, 8192, 10240, 12288, 14336, 16384, 18432, 20480, 22528];
        var middlevalue = (minbitrate + maxbitrate) / 2;
        var i = 0;
        for (i = 0; i < sBitrate.length; i++) {
            if (sBitrate[i] >= middlevalue) {
                if (sBitrate[i] > maxbitrate) return maxbitrate;
                else return sBitrate[i];
            }
        }
        return maxbitrate;
    },

    _saveJsonEncode: function (n) {
        var jsonTemp = $unlink(encodeConfig.tmpjsonEncode0);
        var jsonMain = jsonTemp[n].MainFormat[encodeConfig.modeMain];

        if (encodeConfig.modeMain == 0) {
            if ($('video_compression0').value == 'H.264') {
                jsonMain.Video.Profile = 'Main';
                jsonMain.Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[1].Video.Profile = 'Main';
                jsonTemp[n].MainFormat[1].Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[2].Video.Profile = 'Main';
                jsonTemp[n].MainFormat[2].Video.Compression = 'H.264';
            } else if ($('video_compression0').value == 'H.264B') {
                jsonMain.Video.Profile = 'Baseline';
                jsonMain.Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[1].Video.Profile = 'Baseline';
                jsonTemp[n].MainFormat[1].Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[2].Video.Profile = 'Baseline';
                jsonTemp[n].MainFormat[2].Video.Compression = 'H.264';
            } else if ($('video_compression0').value == 'H.264H') {
                jsonMain.Video.Profile = 'High';
                jsonMain.Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[1].Video.Profile = 'High';
                jsonTemp[n].MainFormat[1].Video.Compression = 'H.264';
                jsonTemp[n].MainFormat[2].Video.Profile = 'High';
                jsonTemp[n].MainFormat[2].Video.Compression = 'H.264';
            } else {
                jsonMain.Video.Compression = $('video_compression0').value;
                jsonTemp[n].MainFormat[1].Video.Compression = $('video_compression0').value;
                jsonTemp[n].MainFormat[2].Video.Compression = $('video_compression0').value;
            }
        }

        //动检和报警的分辨率和普通的一致
        var width_height = getWidthH($('video_resolution0').value).split('*');
        jsonTemp[n].MainFormat[0].Video.Width = width_height[0] - 0;
        jsonTemp[n].MainFormat[0].Video.Height = width_height[1] - 0;
        jsonTemp[n].MainFormat[1].Video.Width = width_height[0] - 0;
        jsonTemp[n].MainFormat[1].Video.Height = width_height[1] - 0;
        jsonTemp[n].MainFormat[2].Video.Width = width_height[0] - 0;
        jsonTemp[n].MainFormat[2].Video.Height = width_height[1] - 0;



        jsonMain.Video.FPS = $('video_fps0').value - 0;
        if ($('video_compression0').value == 'MJPG') {
            for (var i = 0; i < 3; i++) {
                jsonTemp[n].MainFormat[i].Video.BitRateControl = 'CBR'
            }
        } else {
            jsonMain.Video.BitRateControl = $('video_bitctrl0').value;
        }
        jsonMain.Video.Quality = $('video_quality0').value - 0;

        if ($('video_bitrate0').value == 'customize') {
            jsonMain.Video.BitRate = $('video_custom0').value - 0;
        } else {
            jsonMain.Video.BitRate = $('video_bitrate0').value - 0;
        }

        jsonMain.Video.GOP = $('video_gop0').value - 0;

        if ($('video_subStream_con').style.display != 'none' && jsonTemp[n].ExtraFormat[encodeConfig.modeExtr] != null) {
            var jsonExtr = jsonTemp[n].ExtraFormat[encodeConfig.modeExtr];

            if ($('video_compression1').value == 'H.264') {
                jsonExtr.Video.Profile = 'Main';
                jsonExtr.Video.Compression = 'H.264';
            } else if ($('video_compression1').value == 'H.264B') {
                jsonExtr.Video.Profile = 'Baseline';
                jsonExtr.Video.Compression = 'H.264';
            } else if ($('video_compression1').value == 'H.264H') {
                jsonExtr.Video.Profile = 'High';
                jsonExtr.Video.Compression = 'H.264';
            } else {
                jsonExtr.Video.Compression = $('video_compression1').value;
            }

            var width_height = getWidthH($('video_resolution1').value).split('*');
            jsonTemp[n].ExtraFormat[encodeConfig.modeExtr].Video.Width = width_height[0] - 0;
            jsonTemp[n].ExtraFormat[encodeConfig.modeExtr].Video.Height = width_height[1] - 0;

            jsonExtr.Video.FPS = $('video_fps1').value - 0;
            if ($('video_compression1').value == 'MJPG') {
                for (var i = 0; i < 3; i++) {
                    jsonTemp[n].ExtraFormat[i].Video.BitRateControl = 'CBR'
                }
            } else {
                jsonExtr.Video.BitRateControl = $('video_bitctrl1').value;
            }
            jsonExtr.Video.Quality = $('video_quality1').value - 0;
            if ($('video_subStream_enable').checked) {
                jsonExtr.VideoEnable = true;
            } else {
                jsonExtr.VideoEnable = false;
                //辅码流视频不使能,音频也不使能.
                jsonExtr.AudioEnable = false;
            }
            if ($('video_bitrate1').value == 'customize') {
                jsonExtr.Video.BitRate = $('video_custom1').value - 0;
            } else {
                jsonExtr.Video.BitRate = $('video_bitrate1').value - 0;
            }
            jsonExtr.Video.GOP = $('video_gop1').value - 0;

        }

        jsonTemp[n].SnapFormat[0].Video.Width = jsonTemp[n].MainFormat[0].Video.Width;
        jsonTemp[n].SnapFormat[0].Video.Height = jsonTemp[n].MainFormat[0].Video.Height;
        jsonTemp[n].SnapFormat[1].Video.Width = jsonTemp[n].MainFormat[0].Video.Width;
        jsonTemp[n].SnapFormat[1].Video.Height = jsonTemp[n].MainFormat[0].Video.Height;

        return jsonTemp;
    },

    /**
     * 由于主码流的普通，动检，报警使用了同个分辨率，保存编码配置前验证码流值是否在码流范围之内，防止某个编码模式下分辨率修改导致其他模式下的码流没有同步计算。而辅码流1和2只有普通模式，不做校验。
     * @ignore
     */
    _checkBitRate: function () {
        var item = encodeConfig.tmpjsonEncode0[0];
        var min_max, bitrate;
        for (var i = 1; i < 1; i++) {
            bitrate = item.MainFormat[i].Video.BitRate;
            min_max = encodeConfig.jsonEncodeCapTmp[0].MainFormat[i].Video.BitRateOptions;
            if (min_max[1] < bitrate || min_max[0] > bitrate) {
                item.MainFormat[i].Video.BitRate = min_max[1];
            }
        }
    },

    _selectBitRate: function (o, n) {
        if (o.value == 'customize') {
            $('video_customwrap' + n).style.display = '';
            var val = $('video_bitrefer' + n).getProperty('text').split('-');
            var max_min = [parseInt(val[0]), parseInt(val[1])];
            var cur_val = $('video_custom' + n).value - 0;
            if (cur_val > max_min[1] || cur_val < max_min[0]) {
                $('video_custom' + n).value = ((max_min[0] + max_min[1]) / 2).toInt();
            } else {
                return;
            }
        } else {
            $('video_customwrap' + n).style.display = 'none';
        }
    },

    _postConfig: function () {
        var ind = 0;
        encodeConfig.tmpjsonEncode0 = encodeConfig._saveJsonEncode(ind);
        var mainFPSMax = $('video_fps0').options.length;
        if (encodeConfig.tmpjsonEncode0[0].MainFormat[1].Video.FPS > mainFPSMax || encodeConfig.tmpjsonEncode0[0].MainFormat[2].Video.FPS > mainFPSMax) {
            encodeConfig.tmpjsonEncode0[0].MainFormat[1].Video.FPS = mainFPSMax;
            encodeConfig.tmpjsonEncode0[0].MainFormat[2].Video.FPS = mainFPSMax;
            encodeConfig.tmpjsonEncode0[0].MainFormat[1].Video.GOP = mainFPSMax * 2;
            encodeConfig.tmpjsonEncode0[0].MainFormat[2].Video.GOP = mainFPSMax * 2;
        }

        encodeConfig._checkBitRate();
        // console.log(JSON.encode(encodeConfig.tmpjsonEncode0))
        var s = plugin.ocx.SetDeviceConfig("Encode", JSON.encode(encodeConfig.tmpjsonEncode0));
        if (s) {
            remarkDisplay('video_remark0', tl('Succeed in saving configure'), 3000, 0);
        } else {
            remarkDisplay('video_remark0', tl('Saving failure'), 3000, 1);
        }
    },

    /**
     * 刷新能力获取
     * @param  {Number} mode 0：切换主码流部分；1：切换辅码流部分
     * @param  {Number} e 事件类型 1：blur事件
     * @ignore
     */
    _refreshEncodeCap: function (mode, e) {
        var ind = 0;
        var jsonTmp;
        var jsonCapTmp;
        jsonTmp = encodeConfig._saveJsonEncode(ind);

        var strsResult = plugin.ocx.GetConfigCaps("Encode", JSON.encode(jsonTmp));
        if (strsResult) {
            jsonCapTmp = JSON.decode(strsResult);
            encodeConfig.jsonEncodeCapTmp = jsonCapTmp;
            encodeConfig.tmpjsonEncode0 = jsonTmp;
            encodeConfig.extraTip = '';
            //辅码流帧率为0并且使能开启 给出提示并自动关掉使能
            for (var i = 0; i < encodeConfig.ExtraStreamNum; i++) {
                if ((jsonCapTmp[ind].ExtraFormat[i].Video.FPSMax == 0) && encodeConfig.tmpjsonEncode0[ind].ExtraFormat[i].VideoEnable) {
                    if (i == encodeConfig.modeExtr) {
                        $('video_subStream_enable').checked = false;
                    }
                    encodeConfig.tmpjsonEncode0[ind].ExtraFormat[i].VideoEnable = false;
                    encodeConfig.extraTip += tl('Second Stream') + (i + 1);

                }
            }
            encodeConfig._refreshEncodeCapDate(mode, ind, jsonCapTmp, jsonTmp);
            if (encodeConfig.extraTip != '') {
                remarkDisplay('video_remark0', tl('ExtraTips') + encodeConfig.extraTip, 3000, 2);
            }
        }

    },
    /**
     * 重新计算渲染页面
     * @param  {Number} mode 0：切换主码流部分；1：切换辅码流部分
     * @param  {Number} ind 通道
     * @param  {Object} jsonCapTmp 能力配置
     * @param  {Object} jsonTmp 页面配置
     * @ignore
     */
    _refreshEncodeCapDate: function (mode, ind, jsonCapTmp, jsonTmp) {
        if (mode == 0) {
            encodeConfig._putCapMain(jsonCapTmp, ind, encodeConfig.modeMain);
            encodeConfig._putvalueMain(jsonTmp, ind, encodeConfig.modeMain);
            encodeConfig.ischangebitrate = false;
            encodeConfig._putCapExtr(jsonCapTmp, ind, encodeConfig.modeExtr);
            encodeConfig._putvalueExtr(jsonTmp, ind, encodeConfig.modeExtr);
            encodeConfig.ischangebitrate = true;
        } else {
            encodeConfig.ischangebitrate = false;
            encodeConfig._putCapMain(jsonCapTmp, ind, encodeConfig.modeMain);
            encodeConfig._putvalueMain(jsonTmp, ind, encodeConfig.modeMain);
            encodeConfig.ischangebitrate = true;
            encodeConfig._putCapExtr(jsonCapTmp, ind, encodeConfig.modeExtr);
            encodeConfig._putvalueExtr(jsonTmp, ind, encodeConfig.modeExtr);
        }
    },

    _putJsonEncode: function (json, asyn) {
        var ind = 0;
        var strsResult = plugin.ocx.GetConfigCaps("Encode", JSON.encode(json));
        if (strsResult) {
            var tmpCap = JSON.decode(strsResult);
            encodeConfig.jsonEncodeCap = $unlink(tmpCap);
            encodeConfig.jsonEncodeCapTmp = $unlink(encodeConfig.jsonEncodeCap);
            encodeConfig._putCapMain(tmpCap, ind, encodeConfig.modeMain, json);
            encodeConfig._putvalueMain(json, ind, encodeConfig.modeMain);
            encodeConfig._putCapExtr(tmpCap, ind, encodeConfig.modeExtr);
            encodeConfig._putvalueExtr(json, ind, encodeConfig.modeExtr);
        }
    },

    bind: function () {
        $('video_encode_confirm').addEvent('click', encodeConfig._postConfig);
        $('video_extraAudioList').addEvent('change', function () {
            encodeConfig.ischangebitrate = false;
            encodeConfig.tmpjsonEncode0 = encodeConfig._saveJsonEncode(0);
            encodeConfig.modeExtr = this.selectedIndex;

            encodeConfig._putCapExtr(encodeConfig.jsonEncodeCapTmp, 0, encodeConfig.modeExtr);
            encodeConfig._putvalueExtr(encodeConfig.tmpjsonEncode0, 0, encodeConfig.modeExtr);
            encodeConfig.ischangebitrate = true;
        });
        $('video_compression0').addEvent('change', function () {
            encodeConfig._refreshEncodeCap(0);

        });
        $('video_resolution0').addEvent('change', function () {
            encodeConfig.resolutionDialogflag && remarkDisplay('video_remark0', tl('Resolution Change'), 3000, 2);
            encodeConfig._refreshEncodeCap(0);
        });
        //$('video_fps0').addEvent('change', refreshEncodeCap);
        $('video_gop0').addEvent('blur', function () {
            limit(this, 150);

            limitMin(this, $('video_fps0').value - 0);

            encodeConfig._refreshEncodeCap(0);
        });
        $('video_gop0').addEvent('keyup', function () {
            limitEx(this, 150)
        });
        $('video_bitrate0').addEvent('change', function () {
            encodeConfig._selectBitRate(this, 0)
        });
        $('video_compression1').addEvent('change', function () {
            encodeConfig._refreshEncodeCap(1);

        });
        $('video_resolution1').addEvent('change', function () {
            encodeConfig.resolutionDialogflag && remarkDisplay('video_remark0', tl('Resolution Change'), 3000, 2);
            encodeConfig._refreshEncodeCap(1);
        });
        //$('video_fps1').addEvent('change', refreshEncodeCap);
        $('video_gop1').addEvent('blur', function () {
            limit(this, 150);

            limitMin(this, $('video_fps1').value - 0);

            //同主码流I帧间隔策略
            encodeConfig._refreshEncodeCap(1);
        });

        $('video_gop1').addEvent('keyup', function () {
            limitEx(this, 150)
        });
        $('video_bitrate1').addEvent('change', function () {
            encodeConfig._selectBitRate(this, 1)
        });

        $('video_custom0').addEvents({
            'keyup': function () {
                var min_max = $('video_bitrefer0').getProperty('text').split('-');
                limitEx(this, parseInt(min_max[1]));
            },
            'blur': function () {
                var min_max = $('video_bitrefer0').getProperty('text').split('-');
                limit(this, parseInt(min_max[1]));
                limitMin(this, parseInt(min_max[0]));
            }
        });
        $('video_custom1').addEvents({
            'keyup': function () {
                var min_max = $('video_bitrefer1').getProperty('text').split('-');
                limitEx(this, parseInt(min_max[1]));
            },
            'blur': function () {
                var min_max = $('video_bitrefer1').getProperty('text').split('-');
                limit(this, parseInt(min_max[1]));
                limitMin(this, parseInt(min_max[0]));
            }
        });

        $('video_streamType0').addEvent('change', function () {
            encodeConfig.ischangebitrate = false;
            encodeConfig.tmpjsonEncode0 = encodeConfig._saveJsonEncode(0);
            //alert(JSON.encode(jsonEncodeTmp[0].MainFormat))
            encodeConfig.modeMain = this.selectedIndex;
            encodeConfig._putCapMain(encodeConfig.jsonEncodeCapTmp, 0, encodeConfig.modeMain);
            encodeConfig._putvalueMain(encodeConfig.tmpjsonEncode0, 0, encodeConfig.modeMain);
            encodeConfig.ischangebitrate = true;
        });

        $('video_bitctrl0').addEvent('change', function () {
            if (this.selectedIndex == 0) {
                $('video_showQuality0').style.display = 'none';
            } else $('video_showQuality0').style.display = '';
        });
        $('video_bitctrl1').addEvent('change', function () {
            if (this.selectedIndex == 0) $('video_showQuality1').style.display = 'none';
            else $('video_showQuality1').style.display = '';
        });

        $('video_fps0').addEvent('change', function () {
            $('video_gop0').value = (this.value - 0) * 2;
            $('video_gop0_range').setProperty('text', '(' + this.value + '~150)');
            encodeConfig._refreshEncodeCap(0);
        });
        $('video_fps1').addEvent('change', function () {
            $('video_gop1').value = (this.value - 0) * 2;
            $('video_gop1_range').setProperty('text', '(' + this.value + '~150)');
            encodeConfig._refreshEncodeCap(1);
        });

        $('video_subStream_enable').addEvent('click', function () {
            if ((this.checked == true) && (encodeConfig.jsonEncodeCapTmp[0].ExtraFormat[encodeConfig.modeExtr].Video.FPSMax == 0)) {
                remarkDisplay('video_remark0', tl('Extra Alarm') + tl('Second Stream') + (encodeConfig.modeExtr + 1), 3000, 1);
                this.checked = false;

            }
        })
    }
}
/**
 * 插件相关
 * @type {Object}
 */
var plugin = {
    ocx: null, // 插件对象
    domOperations: null,
    recordTimeOut: null,
    ShowFileBrowseFlag: 0,
    localRecordPlayTime: 0,

    init: function () {
        this.ocx = document.getElementById('ocx');
        this.domOperations = document.getElementById('operations');
        this.bind();
        if (!$('ocx')) {
            $('downOCX').click();
        }
        ptz.init();
        
    },
    bind: function () {
        //AddEventListener -->a way to handle some async datas whick ocx developer thought out.
        this.ocx.AddEventListener('FileDialogInfo', this._FileDialogInfo);
        this.ocx.AddEventListener('OutPutStringInfo', this._OutPutStringInfo);
        $('ocx').AddEventListener('RegionChanged', function (index, left, top, right, bottom) {
            var screenx = ((left + right) / (2 * 8192)).limit(-1, 1);
            var screeny = ((top + bottom) / (2 * 8192)).limit(-1, 1);
            var maxlength = Math.abs(bottom - top) > Math.abs(right - left) ? (bottom - top) : (right - left);
            // x坐标表示变倍方向
            var zoomtemp = (maxlength / 8192).limit(-1, 1);
            var zoom = (right > left) ? zoomtemp * zoomtemp : (-1) * zoomtemp * zoomtemp;

            $('ocx').SetConfig("PtzLocate", '{"method":"ptz.moveDirectly","params":{"screen":['+screenx+','+screeny+','+zoom+'],"speed":null},"id":169}');
        });

    },

    _syncConfigPath: function(){
        if(!$('ocx')){
            return;
        }

        var names = ['LiveSnapshot', 'LiveRecord', 'PlaybackSnapshot', 'PlaybackRecord', 'VideoClips'];

        var getDefaultConfigPath = function(name){
            return  this.ocx.GetUserDirectory() + '\\WebDownload\\' + name;
        }

        names.each(function(name, index){
            var num = index + 1;
            var path = this.ocx.GetConfigPath(num);
            if(!path){
                path = Cookie.read('DhWebVideoPath' + num) || getDefaultConfigPath(name);
                this.ocx.SetConfigPath(num, path);
            }
            Cookie.write('DhWebVideoPath' + num, path, { duration: 30 });
        });
    },
    /**
     * 判断大华插件安装
     * @param  {[type]} bool [description]
     * @return {[type]}      [description]
     */
    checkPlugin: function (bool) {
        if (browse.ie) { //if brower is IE 
            var ocxdomWrap = document.getElementById('ocx_wrap');
            var htmlStr = '<object id="ocx" width="400" height="300" classid="CLSID:ED1EDBF5-CCC7-4171-A559-6578BD6D3003">' + '<param name="lVideoWindNum" value="1">' + '<param name="VideoWindBarColor" value="414141">' + '<param name="VideoWindTextColor" value="9c9c9c">' + '</object>';
            ocxdomWrap.innerHTML = htmlStr;
        } else {
            var mimetype = navigator.mimeTypes["application/media-plugin-version-3.0.0.3"];
            if (mimetype) {
                if (mimetype.enabledPlugin) {
                    bool && alert('plugin has been installed');
                }
            } else {
                alert("there isn't a plugin fit for this program");
            }
        }
    },
    /**
     * 判断是否有VLC
     * @return {[type]} [description]
     */
    checkVlc: function () {
        var hasVlc = false
        if (browse.ie) {
            /*                try {
                new ActiveXObject('VideoLAN.Vlcplugin.1');
                hasVlc = true;
            } catch (e) {}*/
        } else {
            navigator.plugins.refresh(false);
            for (var i = 0; i < navigator.plugins.length; i++) {
                if (navigator.plugins[i].name.indexOf('VLC') >= 0) hasVlc = true;
            }
        }
        return hasVlc;
    },
    /**
     * 判断是否有QT插件
     * @return {[type]} [description]
     */
    checkQt: function () {
        var hasQt = false;
        if (browse.ie) {
            try {
                new ActiveXObject('QuickTime.QuickTime');
                hasQt = true;
            } catch (e) {}
        } else {
            navigator.plugins.refresh(false);
            for (var i = 0; i < navigator.plugins.length; i++) {
                if (navigator.plugins[i].name.indexOf('QuickTime') >= 0) hasQt = true;
            }
        }
        return hasQt;
    },
    /**
     * 登陆控件，打开QT VLC
     * @param  {[type]} num [description]
     * @return {[type]}     [description]
     */
    login: function (num) {
        // if (loginParams.ip == '172.29.2.201') {
        //     alert('Edit device ip first before use this demo(line 193 in index.html).');
        //     return;
        // }
        // params 1   --> ip
        // params 2   --> port
        // params 3   --> username
        // params 4   --> password
        // params 5   --> a switch -->  0:login; 1:logout
        this.ocx.LoginDeviceEx(loginParams.ip, loginParams.port, loginParams.uname, loginParams.pwd, num);
        this._uichangeLogin(num);
        clearInterval(plugin.recordTimeOut);

        if (this.checkVlc()) {
            var $vlc = document.getElementById('VLC');

            rtsp = 'rtsp://' + loginParams.uname + ':' + loginParams.pwd + '@' + loginParams.ip + ':' + loginParams.rtspport + '/cam/realmonitor?channel=1&subtype=1'; //subtype=0表示主码流，123表示辅码流123，推荐使用辅码流

            $vlc.innerHTML = '<div style=" width:400px; height:20px;">第三方标准控件-VLC插件播放</div>\
                              <div style=" width:400px; height:300px;">\
                                <object id="vlc" classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" style="width:100%;height:100%;">\
                                    <embed src="' + rtsp + '" width="100%" height="100%" type="application/x-vlc-plugin">\
                                    </embed>\
                                </object>\
                            </div>'
        } else {
            var $vlc = document.getElementById('VLC');
            $vlc.innerHTML = '<div style=" width:400px; height:100px;">第三方标准控件-VLC插件未安装</div>'
        }

        if (this.checkQt()) {
            $qt = document.getElementById('quicktime');
            var rtsp = 'rtsp://' + loginParams.ip + ':' + loginParams.rtspport + '/cam/realmonitor?channel=1&subtype=1'; //subtype=0表示主码流，123表示辅码流123，推荐使用辅码流

            $qt.innerHTML = '<div style=" width:400px; height:20px;">第三方标准控件-QuickTime插件播放</div>\
                             <div style=" width:400px; height:300px;">\
                             <div id="qt" style="width:100%;height:100%;">\
                                <embed src="sample.mov" qtsrc="' + rtsp + '" width="100%" height="100%" SCALE="ToFit" autoplay="true" loop="false" controller="false" wmode="transparent" type="video/quicktime">\
                                </embed>\
                             </div>\
                         </div>'
        } else {
            var $qt = document.getElementById('quicktime');
            $qt.innerHTML = '<div style=" width:400px; height:100px;">第三方标准控件-QuickTime插件未安装</div>'
        }
    },

    connect: function (bool) {
        this.ocx.SetModuleMode(0);
        var num = bool ? 0 : 1;
        // params 1 --> a switch -->  0:open video;  1:close video.
        // params 2 --> means what？i don't konw
        this.ocx.ConnectRealVideo(num, 0);
    },

    setMode: function (num) {
        //params1 --> a switch --> 0:preview; 1: setting
        this.ocx.SetModuleMode(num);
    },

    setDevideMode: function () {
        // params 1 --> 0   never change.
        // params 2 --> 1   something about cookie
        // plugin developer say: SetDeviceMode is invalid.
        this.ocx.SetDeviceMode(0, 1);
    },

    //choose right params to use the function correctly~~~
    control: function (arg1, arg2) {
        return this.ocx.SubVideoWndCtrl(arg1, arg2);
        //function --> Digital Zoom (translate into Chinese:局部放大功能)
        //arg1 ---> 5  never change
        //arg2 ---> a switch  1:start;0:stop.
        //====================================================
        //function --> Audio(in preview) (translate into Chinese:预览界面声音功能)
        //arg1 ---> 1  never change
        //arg2 ---> a switch  1:start;0:stop.
        //====================================================
        //function --> local recording( translate into Chinese:本地录像？)
        //arg1 ---> 3  never change
        //arg2 ---> a switch  1:start dav;2:start avi;3:start asf;0:stop;
        // open the floder which function GetConfigPath(2) returns.
        //====================================================
        //show more info need your requirement
    },

    pathDialog: function () {
        //async datas will come in function "addEventListener 'FileDialogInfo'"
        ShowFileBrowseFlag = 0;
        this.ocx.ShowFileBrowse();
    },

    path: function (route) {
        if (route == '') {
            // params 1 --> 2 never change.
            // returns  --> routes you have beem set before
            var result = this.ocx.GetConfigPath(2);
            if (result == '') {
                alert('Set path brfore get path every time you load the demo~');
            }
            var dom = document.getElementById('pathGet');
            dom.value = result;
        } else {
            // params 1 --> 2 never change.
            // params 2 --> routes you want
            this.ocx.SetConfigPath(2, route);
        }
    },

    talk: function (num) {
        if (num == 1) { //before start talk, you should set some config into plugin.
            //config 1 is the audio congfig of the device
            this.ocx.SetConfig("audio", '{"Bitrate":64,"Channels":[0],"Compression":"AAC","Depth":16,"Frequency":48000,"Mode":0,"Pack":"DHAV"}');
            //config 2 seams never change.
            this.ocx.SetConfig("audio", '{"Enable":true,"Port":80}');
        }

        // params 1 --> a switch  0:stop talk; 1-->start talk
        this.ocx.ControlTalking(num);
    },

    getPicSize: function () {
        var size = this.ocx.GetPicSize();
        var dom = document.getElementById('GetPicSizeSpan');
        dom.innerHTML = size;
    },

    record: function (bool) {
        var result = this.ocx.GetConfigPath(2);
        if (result == '') {
            this.ocx.SetConfigPath(2, "c:\\video"); //set a path to storage the video
        }
        if (bool) {
            var num = document.getElementById('videoFormat').value - 0;
            var flag = this.control(3, num);
            if (flag) {
                var dom = document.getElementById('record_time');
                dom.innerHTML = '00:00:00';
                clearInterval(plugin.recordTimeOut);
                plugin.recordTimeOut = setInterval(plugin.addRecodeTime, 1000);
            }

        } else {
            clearInterval(plugin.recordTimeOut);
            this.control(3, 0);
        }
    },

    addRecodeTime: function () {
        if (!plugin.recordTimeOut) {
            return;
        }
        var dom = document.getElementById('record_time');
        var str = dom.innerHTML;
        var arr = str.split(':');
        arr[2] = arr[2] - 0 + 1;
        if (arr[2] >= 60) {
            arr[2] = 0;
            arr[1] = arr[1] - 0 + 1;
            if (arr[1] >= 60) {
                arr[1] = 0;
                arr[0] = arr[0] - 0 + 1
            }
        }
        if (arr[0] - 0 < 10) {
            arr[0] = '0' + (arr[0] - 0);
        }
        if (arr[1] - 0 < 10) {
            arr[1] = '0' + (arr[1] - 0);
        }
        if (arr[2] - 0 < 10) {
            arr[2] = '0' + (arr[2] - 0);
        }

        dom.innerHTML = arr.join(':');
    },

    getPlayTime: function () {
        // result s    
        // playing    -->  '2012-10-12 03:02:00'
        // stop 	  -->  '2012-10-12 03:02:00stop'
        // can't play --> you can use addEventlistener('PlaybackRecordfileFailed',yourfunction)  to know when play failed
        var s = this.ocx.GetPlayTime(); //use this method when the video is play state
        var dom = document.getElementById('getPlayTime');
        dom.innerText = s;
        setTimeout(plugin.getPlayTime, 900);
    },

    // get local path
    getloaclPath: function () {
        ShowFileBrowseFlag = 1;
        this.ocx.ShowOpen();
    },

    localPlayback: function (flag) {
        if (!flag) {
            this.ocx.SetModuleMode(4);
            var dom = document.getElementById('localpathGet');
            var path = dom.value;
            this.ocx.OpenPlayLocalFile(path);
            clearInterval(plugin.localRecordPlayTime);
            plugin.localRecordPlayTime = setInterval(plugin.getLocalRecodeTime, 1000);
        } else {
            this.ocx.StopPlayLocalFile();
        }
    },

    // 注意，该接口调用图片数据比较大，时间会有点长，需要等待，
    getPicBase64: function () {
        this._syncConfigPath();
        this.ocx.CatchPicBase64Out(2);
    },

    snap: function () {
        this._syncConfigPath();
        this.ocx.CatchPicEx(2, 6, false);
        this.ocx.SubVideoWndCtrl(2, 6);
    },

    snapNotOpenPic: function () {
        this._syncConfigPath();
        this.ocx.CatchPicEx(2, 6, true);
        // this.ocx.CatchPicEx(2, 6, false);
    },

    getLocalRecodeTime: function () {
        var nPos = this.ocx.GetPlayPos();

        var dom = document.getElementById('localPlayTime');
        dom.style.width = (400 * nPos / 100) + 'px';
        if (nPos == 100) {
            clearInterval(plugin.localRecordPlayTime);
        }
    },

    stopPlayBack: function () {
        this.ocx.StopPlayBack();
    },

    playSetVolume: function () {
        this.ocx.PlaySetVolume(-50);
    },

    playBackByRecordFileEx: function () {
        var fileinfo = {
            // here you should reference '<<大华信息交换格式-存储分册>>16.1.2'  media file info format
            // StartTime:'...',
            // EndTime:'...',
            // Duration:'...',
            // Type:'...',
            // FilePath:'...'
            // ,'...':'...'
        };
        var json = JSON.stringify(fileinfo);
        var time = '2012-10-12 03:02:00';
        this.ocx.PlayBackByRecordFileEx(json, time);
    },

    playOpenSound: function () {
        this.ocx.PlayOpenSound(); // reset voice control in plugin???
    },

    _FileDialogInfo: function (fileName, path, filter) {
        // console.log('program comes here after you choose a path or a file');
        if (ShowFileBrowseFlag == 0) {
            var dom = document.getElementById('pathChoose');
            dom.value = path;
        }

        if (ShowFileBrowseFlag == 1) {
            var dom = document.getElementById('localpathGet');
            dom.value = path;
        }

    },

    _OutPutStringInfo: function(str1, str2) {
        //console.log(str2)
    },

    _uichangeLogin: function (num) {
        if (!num) {
            this.domOperations.className = '';
            $('encodeContent').style.display = '';
            $('ptzContent').style.display = '';
            encodeConfig.render();
        } else {
            this.domOperations.className = 'fn-hide';
            $('encodeContent').style.display = 'none';
            $('ptzContent').style.display = 'none';

        }
    }
};

var ptz = {
    init: function () {
        this.bind();
    },

    bind: function () {
        //云台按钮
        $('yt1').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt1').addClass('yt1_virtual');
            // cptzd(true, 'LeftUp', $('ps').value - 0, $('ps').value - 0, false);
            leftupClick();
        });
        $('yt1').addEvent('mouseup', function () {
            $('yt1').removeClass('yt1_virtual');
            cptzd(false, 'LeftUp', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt1').addEvent('mouseout', function () {
            $('yt1').removeClass('yt1_virtual');
            cptzd(false, 'LeftUp', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt2').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt2').addClass('yt2_virtual');
            upClick();
        });
        $('yt2').addEvent('mouseup', function () {
            $('yt2').removeClass('yt2_virtual');
            cptzd(false, 'Up', $('ps').value - 0, 0, false);
        });
        $('yt2').addEvent('mouseout', function () {
            $('yt2').removeClass('yt2_virtual');
            cptzd(false, 'Up', $('ps').value - 0, 0, false);
        });
        $('yt3').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt3').addClass('yt3_virtual');
            rightupClick();
        });
        $('yt3').addEvent('mouseup', function () {
            $('yt3').removeClass('yt3_virtual');
            cptzd(false, 'RightUp', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt3').addEvent('mouseout', function () {
            $('yt3').removeClass('yt3_virtual');
            cptzd(false, 'RightUp', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt4').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt4').addClass('yt4_virtual');
            letfClick();
            //cptzd(true, 'Left', $('ps').value - 0, 0, false);
        });
        $('yt4').addEvent('mouseup', function () {
            $('yt4').removeClass('yt4_virtual');
            cptzd(false, 'Left', $('ps').value - 0, 0, false);
        });
        $('yt4').addEvent('mouseout', function () {
            $('yt4').removeClass('yt4_virtual');
            cptzd(false, 'Left', $('ps').value - 0, 0, false);
        });
        $('yt5').addEvent('click', cptz);
        $('yt6').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt6').addClass('yt6_virtual');
            rightClick();
            //cptzd(true, 'Right', $('ps').value - 0, 0, false);
        });
        $('yt6').addEvent('mouseup', function () {
            $('yt6').removeClass('yt6_virtual');
            cptzd(false, 'Right', $('ps').value - 0, 0, false);
        });
        $('yt6').addEvent('mouseout', function () {
            $('yt6').removeClass('yt6_virtual');
            cptzd(false, 'Right', $('ps').value - 0, 0, false);
        });
        $('yt7').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt7').addClass('yt7_virtual');
            leftdownClick();
            //cptzd(true, 'LeftDown', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt7').addEvent('mouseup', function () {
            $('yt7').removeClass('yt7_virtual');
            cptzd(false, 'LeftDown', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt7').addEvent('mouseout', function () {
            $('yt7').removeClass('yt7_virtual');
            cptzd(false, 'LeftDown', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt8').addEvent('mousedown', function (e) {
            preventM(e);
            $('yt8').addClass('yt8_virtual');
            downClick();
            // cptzd(true, 'Down', $('ps').value - 0, 0, false);
        });
        $('yt8').addEvent('mouseup', function () {
            $('yt8').removeClass('yt8_virtual');
            cptzd(false, 'Down', $('ps').value - 0, 0, false);
        });
        $('yt8').addEvent('mouseout', function () {
            $('yt8').removeClass('yt8_virtual');
            cptzd(false, 'Down', $('ps').value - 0, 0, false);
        });
        $('yt9').addEvent('mousedown', function (e) {
            // console.log(e)
            preventM(e);
            $('yt9').addClass('yt9_virtual');
            rightdownClick();
            //cptzd(true, 'RightDown', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt9').addEvent('mouseup', function () {
            $('yt9').removeClass('yt9_virtual');
            cptzd(false, 'RightDown', $('ps').value - 0, $('ps').value - 0, false);
        });
        $('yt9').addEvent('mouseout', function () {
            $('yt9').removeClass('yt9_virtual');
            cptzd(false, 'RightDown', $('ps').value - 0, $('ps').value - 0, false);
        });

        $('b_bb1').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'ZoomTele', $('ps').value - 0, 0, false)
        });
        $('b_bb1').addEvent('mouseup', function () {
            cptzd(false, 'ZoomTele', $('ps').value - 0, 0, false);
        });
        $('b_bb1').addEvent('mouseout', function () {
            cptzd(false, 'ZoomTele', $('ps').value - 0, 0, false)
        });
        $('b_bb2').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'ZoomWide', $('ps').value - 0, 0, false)
        });
        $('b_bb2').addEvent('mouseup', function () {
            cptzd(false, 'ZoomWide', $('ps').value - 0, 0, false);
        });
        $('b_bb2').addEvent('mouseout', function () {
            cptzd(false, 'ZoomWide', $('ps').value - 0, 0, false)
        });
        $('b_bj2').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'FocusFar', $('ps').value - 0, 0, false)
        });
        $('b_bj2').addEvent('mouseup', function () {
            cptzd(false, 'FocusFar', $('ps').value - 0, 0, false);
        });
        $('b_bj2').addEvent('mouseout', function () {
            cptzd(false, 'FocusFar', $('ps').value - 0, 0, false)
        });
        $('b_bj1').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'FocusNear', $('ps').value - 0, 0, false)
        });
        $('b_bj1').addEvent('mouseup', function () {
            cptzd(false, 'FocusNear', $('ps').value - 0, 0, false);
        });
        $('b_bj1').addEvent('mouseout', function () {
            cptzd(false, 'FocusNear', $('ps').value - 0, 0, false)
        });
        $('b_gq2').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'IrisSmall', $('ps').value - 0, 0, false)
        });
        $('b_gq2').addEvent('mouseup', function () {
            cptzd(false, 'IrisSmall', $('ps').value - 0, 0, false);
        });
        $('b_gq2').addEvent('mouseout', function () {
            cptzd(false, 'IrisSmall', $('ps').value - 0, 0, false)
        });
        $('b_gq1').addEvent('mousedown', function (e) {
            preventM(e);
            cptzd(true, 'IrisLarge', $('ps').value - 0, 0, false)
        });
        $('b_gq1').addEvent('mouseup', function () {
            cptzd(false, 'IrisLarge', $('ps').value - 0, 0, false);
        });
        $('b_gq1').addEvent('mouseout', function () {
            cptzd(false, 'IrisLarge', $('ps').value - 0, 0, false)
        });
    }
}

plugin.checkPlugin(false);
plugin.init();

function leftupClick() {
    if ($('yt1') && $('yt1').hasClass('yt1_virtual')) {
        cptzd(true, 'LeftUp', $('ps').value - 0, $('ps').value - 0, false);
        leftupClick.delay(500);
    }
}

function upClick() {
    if ($('yt2') && $('yt2').hasClass('yt2_virtual')) {
        cptzd(true, 'Up', $('ps').value - 0, 0, false);
        upClick.delay(500);
    }

}

function rightupClick() {
    if ($('yt3') && $('yt3').hasClass('yt3_virtual')) {
        cptzd(true, 'RightUp', $('ps').value - 0, $('ps').value - 0, false);
        rightupClick.delay(500);
    }
}

function letfClick() {
    if ($('yt4') && $('yt4').hasClass('yt4_virtual')) {
        cptzd(true, 'Left', $('ps').value - 0, 0, false);
        letfClick.delay(500);
    }
}

function rightClick() {
    if ($('yt6') && $('yt6').hasClass('yt6_virtual')) {
        cptzd(true, 'Right', $('ps').value - 0, 0, false);
        rightClick.delay(500);
    }
}

function leftdownClick() {
    if ($('yt7') && $('yt7').hasClass('yt7_virtual')) {
        cptzd(true, 'LeftDown', $('ps').value - 0, $('ps').value - 0, false);
        leftdownClick.delay(500);
    }
}

function downClick() {
    if ($('yt8') && $('yt8').hasClass('yt8_virtual')) {
        cptzd(true, 'Down', $('ps').value - 0, 0, false);
        downClick.delay(500);
    }
}

function rightdownClick() {
    if ($('yt9') && $('yt9').hasClass('yt9_virtual')) {
        cptzd(true, 'RightDown', $('ps').value - 0, $('ps').value - 0, false);
        rightdownClick.delay(500);
    }
}

function cptzd(bFlag, sDir, iStep, iarg2, iarg3) {
    if (!$('ocx')) {
        alert('请先安装控件')
        return;
    }
    var flag = bFlag ? 1 : 0;
    var iarg = iarg3 ? 1 : 0;
    // console.log(123)
    $('ocx').ControlPtz(sDir, iStep, iarg2, iarg, flag);
}

function preventM(e) {
    if (browse.ie) {
        //document.documentElement.setCapture();
        document.documentElement.releaseCapture(); //阻止默认拖曳该元素
    } else {
        var e = e ? e : event;
        //e.stopPropagation();
        e.preventDefault();
    }
}



// 用于翻译！
function tl(str) {
    return str;
}
var isIPC = true; //为了区分非IPC产品与IPC产品对5M的表达不同作处理,现在IPC将原来5_1M改为5M
var PAL_NTSC = 'PAL';

function getFrame(width, height) {
    //var fra=(width-0)*(height-0); //HD1 = BCIF?
    var fra = width + '*' + height;
    if (fra == '704*576' || fra == '704*480') return 'D1';
    else if (fra == '352*576' || fra == '352*480') return 'HD1';
    else if (fra == '704*288' || fra == '704*240') return 'BCIF';
    else if (fra == '352*288' || fra == '352*240') return 'CIF';
    else if (fra == '176*144' || fra == '176*120') return 'QCIF';
    else if (fra == '640*480') return 'VGA';
    else if (fra == '320*240') return 'QVGA';
    else if (fra == '480*480') return 'SVCD';
    else if (fra == '160*128') return 'QQVGA';
    else if (fra == '800*592') return 'SVGA';
    else if (fra == '1024*768') return 'XVGA';
    else if (fra == '1280*800') return "WXGA";
    else if (fra == '1280*1024') return 'SXGA';
    else if (fra == '1600*1024') return 'WSXGA';
    else if (fra == '1600*1200') return 'UXGA';
    else if (fra == '1920*1200') return 'WUXGA';
    else if (fra == '240*192') return 'ND1';
    else if (fra == '1280*720') return '720P';
    else if (fra == '1920*1080') return "1080P";
    else if (fra == '1280*960') return "1.3M";
    else if (fra == '1872*1408') return "2.5M";
    else if (fra == '3744*1408') return "5M";
    else if (fra == '2560*1920') return "5_1M";
    else if (fra == '2048*1536') return "3M";
    else if (fra == '2560*1600') return "4M";
    //else if(fra=='1216*1024') return "1_2M";
    //else if(fra=='1408*1024') return "1_5M";
    //else if(fra=='3296*2472') return "8M";
    else return width + 'x' + height;
};

function getRorateFrame(width, height) {
    //var fra=(width-0)*(height-0); //HD1 = BCIF?
    var fra = width + '*' + height;
    if (fra == '704*576' || fra == '704*480') return 'D1';
    else if (fra == '352*576' || fra == '352*480') return 'HD1';
    else if (fra == '704*288' || fra == '704*240') return 'BCIF';
    else if (fra == '352*288' || fra == '352*240') return 'CIF';
    else if (fra == '176*144' || fra == '176*120') return 'QCIF';
    else if (fra == '640*480') return 'VGA';
    else if (fra == '320*240') return 'QVGA';
    else if (fra == '480*480') return 'SVCD';
    else if (fra == '160*128') return 'QQVGA';
    else if (fra == '800*592') return 'SVGA';
    else if (fra == '1024*768') return 'XVGA';
    else if (fra == '1280*800') return "WXGA";
    else if (fra == '1280*1024') return 'SXGA';
    else if (fra == '1600*1024') return 'WSXGA';
    else if (fra == '1600*1200') return 'UXGA';
    else if (fra == '1920*1200') return 'WUXGA';
    else if (fra == '240*192') return 'ND1';
    else if (fra == '1280*720') return '720P';
    else if (fra == '1920*1080') return "1080P";
    else if (fra == '1280*960') return "1_3M";
    else if (fra == '1872*1408') return "2_5M";
    else if (fra == '3744*1408') return "5M";
    else if (fra == '2560*1920') return "5_1M";
    else if (fra == '2048*1536') return "3M";
    else if (fra == '2560*1600') return "4M";
    //else if(fra=='1216*1024') return "1_2M";
    //else if(fra=='1408*1024') return "1_5M";
    //else if(fra=='3296*2472') return "8M";
    else return width + 'x' + height;
};
/**
 * 函数功能: 根据传入分辨率确定输出确定制式下(pal== PAL; pal== NTSC)宽高乘积.
 * 参数说明: fra:  输入分辨率.
 PAL_NTSC:不需要传入,全局变量确定.
 * 修改记录: 添加注释 By ma_houjian At 2012.4.1.
 */
function getWidthH(fra) {
    var pal = PAL_NTSC;
    //alert(PAL_NTSC)
    var fras = ['D1', 'HD1', 'BCIF', 'CIF', 'QCIF', 'VGA', 'QVGA', 'SVCD', 'QQVGA', 'SVGA', 'XVGA', 'WXGA', 'SXGA', 'WSXGA', 'UXGA', 'WUXGA', 'ND1', '720', '1080', '1.3M', '2.5M', '5_1M', '3M', '1_2M', '1_5M', '8M', '5M', '4M'];

    var whp = ['704*576', '352*576', '704*288', '352*288', '176*144', '640*480', '320*240', '480*480', '160*128', '800*592', '1024*768', '1280*800', '1280*1024', '1600*1024', '1600*1200', '1920*1200', '240*192', '1280*720', '1920*1080', '1280*960', '1872*1408', '2560*1920', '2048*1536', '1216*1024', '1408*1024', '3296*2472', '3744*1408', '2560*1600'];
    var whn = ['704*480', '352*480', '704*240', '352*240', '176*120', '640*480', '320*240', '480*480', '160*128', '800*592', '1024*768', '1280*800', '1280*1024', '1600*1024', '1600*1200', '1920*1200', '240*192', '1280*720', '1920*1080', '1280*960', '1872*1408', '2560*1920', '2048*1536', '1216*1024', '1408*1024', '3296*2472', '3744*1408', '2560*1600'];
    //alert(fras.length+';'+whp.length);
    //  alert(pal);
    if (fra == '720P') fra = '720';
    if (fra == '1080P') fra = '1080';
    if (fra == '1_3M') fra = '1.3M';
    if (fra == '2_5M') fra = '2.5M';
    var ind = fras.indexOf(fra);
    if (ind < 0) {
        var tp = fra.split('x');
        return tp[0] + '*' + tp[1];
    } else {
        if (pal == 'PAL') {
            return whp[ind];
        } else {
            return whn[ind];
        }
    }
};
//onkeyup="limit(this, 24)"
function limit(o, m) {
    var inpt = o.value;
    if (inpt == '') {
        return;
    }

    if (/[^\d]/g.test(inpt)) {
        o.value = inpt.replace(/[^\d]/g, '') - 0;
    } else {
        o.value = o.value - 0;
    }
    inpt = o.value;
    if (inpt > m) {
        o.value = m;
    }
}

function limitEx(o, m) {
    var inpt = o.value;
    if (/[^\d]/g.test(inpt)) {
        inpt = inpt.replace(/[^\d]/g, '') - 0;
        o.value = inpt;
    } else {
        inpt = o.value - 0;
    }
    if (inpt > m) {
        o.value = m;
    }
}

//onblur="limitMin(this, 5)"
function limitMin(o, m) {
    var inpt = o.value;
    if (/[^\d]/g.test(inpt)) {
        o.value = inpt.replace(/[^\d]/g, '') - 0;
    }
    inpt = o.value;
    if (inpt == '') {
        o.value = m;
    } else if (inpt < m) {
        o.value = m;
    }
}

//专门为时间输入框定制的限制函数, onkeyup="limit(this, 24)
function limitTime(o, m) {
    var inpt = o.value;
    inpt = inpt.replace(/[^\d]/g, '');
    if (inpt - 0 > m) o.value = m;
    inpt = o.value;
    if (inpt.length >= 2) {
        if (o.id != '') {
            var el = $(o.id).getNext('input');
            if (el) el.select();
        }
    }
}

function cptz() {
    if (!$('yt5').hasClass('ui-ptz-specail-icon-current')) {
        $('yt5').addClass('ui-ptz-specail-icon-current');
        $('ocx').SubVideoWndCtrl(11, 1);
    }else{
        $('yt5').removeClass('ui-ptz-specail-icon-current');
        $('ocx').SubVideoWndCtrl(11, 0);
    }
}

/**
 * 函数功能: 根据请求返回，不同返回以不同提示显示.
 *
 * 参数说明: id : 显示提示信息的Dom节点.
 *           info ：提示信息.
 *           interval : 目前统一 interval=3000,3s钟后提示消失.
 *           notifyType ：0  :  成功信息, 1  ： 失败信息, 2  ： 一般提示.
 * 修改记录: 添加注释 By ma_houjian At 2012.4.5.
 * 修改记录: 修复Bug  By ni_zhuguo At 2012.8.28
 */
var remarkDisplay = (function () {
    var timer = [];

    return function (id, info, interval, notifyType) {
        var ele;
        if ($type(id) === "string") {
            ele = $(id);
        } else {
            ele = id;
            id = ele.id;
        }
        if (timer[id]) {
            $clear(timer[id]);
        }
        var tipState;
        switch (notifyType) {
        case 0:
            tipState = 'success';
            break;
        case 1:
            tipState = 'error';
            break;
        case 2:
            tipState = 'warning';
            break;
        }
        ele.removeClass('ui-tip-success').removeClass('ui-tip-warning').removeClass('ui-tip-error').addClass('ui-tip-' + tipState).setStyle('display', 'block').fade('in');
        $(id + '_label').setProperty('text', info);

        if (interval) {
            timer[id] = (function () {
                if (ele) {
                    ele.fade('out');
                }
            }).delay(interval);
        }
    }
})();