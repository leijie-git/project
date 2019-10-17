/* eslint-disable */
$('.echartsMap').show();
var convertCityName = {
    '北京市': '北京',
    '天津市': '天津',
    '河北省': '河北',
    '山西省': '山西',
    '内蒙古自治区': '内蒙古',
    '辽宁省': '辽宁',
    '吉林省': '吉林',
    '黑龙江省': '黑龙江',
    '上海市': '上海',
    '江苏省': '江苏',
    '浙江省': '浙江',
    '安徽省': '安徽',
    '福建省': '福建',
    '江西省': '江西',
    '山东省': '山东',
    '河南省': '河南',
    '湖北省': '湖北',
    '湖南省': '湖南',
    '广东省': '广东',
    '广西壮族自治区': '广西',
    '海南省': '海南',
    '重庆市': '重庆',
    '四川省': '四川',
    '贵州省': '贵州',
    '云南省': '云南',
    '西藏自治区': '西藏',
    '陕西省': '陕西',
    '甘肃省': '甘肃',
    '青海省': '青海',
    '宁夏回族自治区': '宁夏',
    '新疆维吾尔自治区': '新疆',
}
var data =[{ lng: "116.61774", lat: "39.934598",city: '北京', name: "北京总公司", company: "北京安迪盛安全系统自动化有限公司", position: '北京市朝阳区五里桥一街非中心23号楼', region: '朝阳区' },
    {lng: "106.285109", lat: "38.480539", city: '宁夏', name: "宁夏分公司", company: "北京安迪盛安全系统自动化有限公司宁夏分公司", position: '银川市兴庆区北京东路339号宁夏中房集团办公楼415室', region: '兴庆区' },
    {lng: "117.236033", lat: "31.764571", city: '安徽', name: "安徽分公司", company: "北京安迪盛安全系统自动化有限公司安徽分公司", position: '安徽省合肥市经济技术开发区莲花路怡莲新城4幢403室', region: '经济开发区' },
    {lng: "117.006168", lat: "36.669996", city: '山东', name: "山东分公司", company: "北京安迪盛安全系统自动化有限公司山东分公司", position: '济南市市中区经一纬三路8号', region: '市中区' },
    {lng: "114.487565", lat: "38.027315", city: '河北', name: "河北分公司", company: "北京安迪盛安全系统自动化有限公司河北分公司", position: '河北省石家庄市桥西区海龙花园4-1-501', region: '桥西区' },
    {lng: "117.154159", lat: "39.140337", city: '天津', name: "天津分公司", company: "北京安迪盛安全系统自动化有限公司天津分公司", position: '天津市南开区渭水道4号3573室-25', region: '南开区' },
    {lng: "102.772116", lat: "25.050807", city: '云南', name: "云南分公司", company: "北京安迪盛安全系统自动化有限公司云南分公司", position: '云南省昆明市盘龙区金辰青云街道办事处云上社区伍家村清水佳湖雅居9幢108号', region: '盘龙区' },
    {lng: "121.599094", lat: "38.919797", city: '辽宁', name: "辽宁分公司", company: "北京安迪盛安全系统自动化有限公司辽宁分公司", position: '大连市沙河口区长兴街１３７－１３９号', region: '沙河口区' },
    {lng: "113.698248", lat: "34.727838", city: '河南', name: "河南分公司", company: "北京安迪盛安全系统自动化有限公司河南分公司", position: '郑州市管城区航海东路70号院2号楼1-2层商1号', region: '管城区' },
    {lng: "118.748179", lat: "32.039223", city: '江苏', name: "南京分公司", company: "北京安迪盛安全系统自动化有限公司南京分公司", position: '建邺区水西门大街58号', region: '建邺区' },
    {lng: "106.490303", lat: "29.527758", city: '重庆', name: "重庆分公司", company: "北京安迪盛安全系统自动化有限公司重庆分公司", position: '重庆市九龙坡区南华街711号附3号', region: '九龙坡区' },
    {lng: "115.896978", lat: "28.690176", city: '江西', name: "江西分公司", company: "北京安迪盛安全系统自动化有限公司江西分公司", position: '江西省南昌市东湖区象山北路１１５号二楼', region: '东湖区' },
    {lng: "87.573409", lat: "43.863708", city: '新疆', name: "新疆分公司", company: "北京安迪盛安全系统自动化有限公司新疆分公司", position: '乌鲁木齐市北京南路558号京华佳座413号', region: '北京南路' },
    {lng: "113.472793", lat: "23.173881", city: '广州', name: "广州分公司", company: "北京安迪盛安全系统自动化有限公司广州分公司", position: '广州市黄埔区万达广场', region: '黄埔区' },
    {lng: "112.568524", lat: "37.813108", city: '山西', name: "山西分公司", company: "北京安迪盛安全系统自动化有限公司山西分公司", position: '山西省太原市长治路新发展大厦４２３室', region: '太原市' },
];


var getParams = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return unescape(r[2]);
    return '';
}

//#region 中间地图 
var zhongguo = "../../Content/mapJson/map.json";



echarts.extendsMap = function (id, opt) {
    // 实例
    var chart = this.init(document.getElementById(id));

    var curGeoJson = {};
    var cityMap = {
        '中国': zhongguo
    };

    var defaultOpt = {
        mapName: '中国', // 地图展示
        text: '大屏展示',
        goDown: true, // 是否下钻
        activeArea: [], // 区域高亮,同echarts配置项
        data: [],
        // 下钻回调(点击的地图名、实例对象option、实例对象)
        callback: function (name, option, instance) {}
    };
    if (opt) opt = this.util.extend(defaultOpt, opt);

    // 层级索引
    var name = [opt.mapName];
    var idx = 0;
    var pos = {
        leftPlus: 115,
        leftCur: 150,
        left: 198,
        top: 50
    };

    var line = [
        [0, 0],
        [8, 11],
        [0, 22]
    ];
    // style
    var style = {
        font: '18px "Microsoft YaHei", sans-serif',
        textColor: '#eee',
        lineColor: 'rgba(147, 235, 248, .8)'
    };

    var handleEvents = {
        /**
         * i 实例对象
         * o option
         * n 地图名
         **/
        resetOption: function (i, o, n) {
            var breadcrumb = this.createBreadcrumb(n);
            var j = name.indexOf(n);
            var l = o.graphic.length;
            if (j < 0) {
                o.graphic.push(breadcrumb);
                o.graphic[0].children[0].shape.x2 = 280;
                o.graphic[0].children[1].shape.x2 = 280;
                if (o.graphic.length > 2) {
                    var cityData = [];
                    var cityJson;
                    for (var x = 0; x < opt.data.length; x++) {
                        if (n === opt.data[x].city) {
                            $([opt.data[x]]).each(function (index, data) {
                                cityJson = {
                                    city: data.city,
                                    name: data.name,
                                    lng: data.lng,
                                    lat: data.lat,
                                    company: data.company,
                                    position: data.position
                                };
                                cityData.push(cityJson)
                            })
                        }
                    }

                    if (cityData != null) {
                        o.series[0].data = handleEvents.initSeriesData(cityData);
                    } else {
                        o.series[0].data = [];
                    }
                }
                name.push(n);
                idx++;
            } else {
                o.graphic.splice(j + 2, l);
                if (o.graphic.length <= 2) {
                    o.graphic[0].children[0].shape.x2 = 60;
                    o.graphic[0].children[1].shape.x2 = 60;
                    o.series[0].data = handleEvents.initSeriesData(opt.data);
                };
                name.splice(j + 1, l);
                idx = j;
                pos.leftCur -= pos.leftPlus * (l - j - 1);
            };

            o.geo.map = n;
            o.geo.zoom = 0.4;
            i.clear();
            i.setOption(o);
            this.zoomAnimation();
            opt.callback(n, o, i);
        },

        getCityNum: function (data, name) {
            var count = 0;
            for (var i in data) {
                console.log(data[i])
                if(data[i].city==name){
                    count++;
                }
            }
            return count;
        },

        /**
         * name 地图名
         **/
        createBreadcrumb: function (name) {
            var cityToPinyin = {
                '中国': 'zhongguo',
                '上海': 'shanghai',
                '河北': 'hebei',
                '山西': 'shangxi',
                '内蒙古': 'neimenggu',
                '辽宁': 'liaoning',
                '吉林': 'jilin',
                '黑龙江': 'heilongjiang',
                '江苏': 'jiangsu',
                '浙江': 'zhejiang',
                '安徽': 'anhui',
                '福建': 'fujian',
                '江西': 'jiangxi',
                '山东': 'shangdong',
                '河南': 'henan',
                '湖北': 'hubei',
                '湖南': 'hunan',
                '广东': 'guangdong',
                '广西': 'guangxi',
                '海南': 'hainan',
                '四川': 'sichuan',
                '贵州': 'guizhou',
                '云南': 'yunnan',
                '西藏': 'xizang',
                '陕西': 'shanxi',
                '甘肃': 'gansu',
                '青海': 'qinghai',
                '宁夏': 'ningxia',
                '新疆': 'xinjiang',
                '北京': 'beijing',
                '天津': 'tianjin',
                '重庆': 'chongqing',
                '香港': 'xianggang',
                '澳门': 'aomen'
            };
            var breadcrumb = {
                type: 'group',
                id: name,
                left: pos.leftCur + pos.leftPlus,
                top: pos.top + 3,
                children: [{
                    type: 'polyline',
                    left: -90,
                    top: -5,
                    shape: {
                        points: line
                    },
                    style: {
                        stroke: '#fff',
                        key: name
                    },
                    onclick: function () {
                        var name = this.style.key;
                        handleEvents.resetOption(chart, option, name);
                    }
                }, {
                    type: 'text',
                    left: -68,
                    top: 'middle',
                    style: {
                        text: name + '   联网单位数：' + handleEvents.getCityNum(opt.data, name),
                        textAlign: 'center',
                        fill: style.textColor,
                        font: style.font
                    },
                    // onclick: function () {
                    //     var name = this.style.text;
                    //     handleEvents.resetOption(chart, option, name);
                    // }
                }, {
                    type: 'text',
                    left: -68,
                    top: 10,
                    style: {
                        name: name,
                        text: cityToPinyin[name] ? cityToPinyin[name].toUpperCase() : '',
                        textAlign: 'center',
                        fill: style.textColor,
                        font: '12px "Microsoft YaHei", sans-serif',
                    },
                    onclick: function () {
                        var name = this.style.name;
                        handleEvents.resetOption(chart, option, name);
                    }
                }]
            }

            pos.leftCur += pos.leftPlus;

            return breadcrumb;
        },

        // 设置effectscatter
        initSeriesData: function (data) {
            var temp = [];
            for (var i = 0; i < data.length; i++) {
                var lnglat = [data[i].lng];
                lnglat.push(data[i].lat);
                temp.push({
                    name: data[i].company,
                    value: lnglat,
                    position: data[i].position,
                });

            };
            return temp;
        },
        zoomAnimation: function () {
            var count = null;
            var zoom = function (per) {
                if (!count) count = per;
                count = count + per;
                chart.setOption({
                    geo: {
                        zoom: count
                    }
                });
                if (count < 1) window.requestAnimationFrame(function () {
                    zoom(0.2);
                });
            };
            window.requestAnimationFrame(function () {
                zoom(0.2);
            });
        }
    };

    var option = {
        backgroundColor: opt.bgColor,
        tooltip: {
            show: true,
            trigger: 'item',
            alwaysShowContent: false,
            backgroundColor: 'rgba(50,50,50,0.7)',
            hideDelay: 100,
            triggerOn: 'mousemove',
            enterable: true,
            //position: ['60%', '70%'],
            formatter: function (params, ticket, callback) {
                return '单位名称：' + params.data.name + '<br/>' + '所在位置：' + params.data.position
            }
        },
        geo: {
            map: opt.mapName,
            roam: true,
            zoom: 1,
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                },
                emphasis: {
                    textStyle: {
                        color: '#fff'
                    }
                }
            },
            itemStyle: {
                normal: {
                    borderColor: 'rgba(147, 235, 248, 1)',
                    borderWidth: 1,
                    areaColor: {
                        type: 'radial',
                        x: 0.5,
                        y: 0.5,
                        r: 0.8,
                        colorStops: [{
                            offset: 0,
                            color: 'rgba(147, 235, 248, 0)' // 0% 处的颜色
                        }, {
                            offset: 1,
                            color: 'rgba(147, 235, 248, .2)' // 100% 处的颜色
                        }],
                        globalCoord: false // 缺省为 false
                    },
                    shadowColor: 'rgba(128, 217, 248, 1)',
                    // shadowColor: 'rgba(255, 255, 255, 1)',
                    shadowOffsetX: -2,
                    shadowOffsetY: 2,
                    shadowBlur: 10
                },
                emphasis: {
                    areaColor: '#389BB7',
                    borderWidth: 0
                }
            },
            regions: opt.activeArea.map(function (item) {
                if (typeof item !== 'string') {
                    return {
                        name: item.name,
                        itemStyle: {
                            normal: {
                                areaColor: item.areaColor || '#389BB7'
                            }
                        },
                        label: {
                            normal: {
                                show: item.showLabel,
                                textStyle: {
                                    color: '#fff'
                                }
                            }
                        }
                    }
                } else {
                    return {
                        name: item,
                        itemStyle: {
                            normal: {
                                borderColor: '#91e6ff',
                                areaColor: '#389BB7'
                            }
                        }
                    }
                }
            })
        },
        series: [{
            type: 'effectScatter',
            coordinateSystem: 'geo',
            showEffectOn: 'render',
            rippleEffect: {
                period: 15,
                scale: 4,
                brushType: 'fill'
            },
            hoverAnimation: true,
            itemStyle: {
                normal: {
                    color: '#FFC848',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: handleEvents.initSeriesData(opt.data)
        }]
    };
    chart.setOption(option);
    // 添加事件
    chart.on('click', function (params) {
        map.centerAndZoom(params.name,10);
        $('.echartsMap').hide();
    });

    chart.setMap = function (mapName) {
        var _self = this;
        if (mapName.indexOf('市') < 0) mapName = mapName + '市';
        var citySource = cityMap[mapName];
        if (citySource) {
            var url = './map/' + citySource + '.json';
            $.get(url, function (response) {
                curGeoJson = response;
                echarts.registerMap(mapName, response);
                handleEvents.resetOption(_self, option, mapName);
            });
        }
    };

    return chart;
};

//#endregion

// 地图数据
$.getJSON(zhongguo, function (geoJson) {
    echarts.registerMap('中国', geoJson);
    /*************/
    var myChart = echarts.extendsMap('mapBox', {
        callback: function (name, option, instance) {},
        data: data
    });
    /*************/
});

$(function(){

    $('.btn_backTop').show();
    $('.btn_backTop').unbind('click').click(function(){
        $('.echartsMap').show();
    })
});