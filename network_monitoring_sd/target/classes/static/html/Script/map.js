/**
 * Created by 20170810001 on 2018/9/6.
 */
    //单位
var _unitPoint = [
    //{id:'1',unitName:"单位1",point:[120.641499,31.286422],type:1},
    //{id:'2',unitName:"单位2",point:[120.635894,31.278769],type:1},
    //{id:'3',unitName:"单位3",point:[120.64114,31.273213],type:1},
    //{id:'4',unitName:"单位4",point:[120.643224,31.261916],type:1},
    //{id:'5',unitName:"单位5",point:[120.625258,31.267164],type:1},
    //{id:'6',unitName:"单位6",point:[120.632013,31.271423],type:1}
];

var _firePowerPoint = [];
var _mapCenter = _loginInfo.mapCenter; //中心城市
var _markerClusterer;//点聚合
var _markers=[];//用于点聚合
$(function () {

    loadMap(_mapCenter);
    mapFunction(); //跟地图有关的操作
    //showUnitMarkerInMap();


});

var map = "";
var _centerMarker;//定位中心点，单位点
var _centerPointer =[103.136943, 38.066285] ;//中心点，也用于全景地图
function loadMap(mapCenter) {
    map = new BMap.Map("bmap");
    var point = new BMap.Point(_centerPointer[0],_centerPointer[1]);
    //map.centerAndZoom(point, 5);
    if(_mapCenter != ""){
        map.centerAndZoom(mapCenter,_loginInfo.mapLevel);
    }
    else{
        map.centerAndZoom(point, 5);
    }

    map.enableScrollWheelZoom(true); //开启滚轮缩放

    var type = $('.wrap_sysColors>div.active', top.document).attr('type');
    setMapStyle(type); //自定义背景样式
    var centerIcon = new BMap.Icon("../../Content/images/mapMarker/mapCenter.png", new BMap.Size(32,32));
    //_centerMarker = new BMap.Marker(point,{icon:centerIcon});  // 创建标注
    //map.addOverlay(_centerMarker);
    //_centerMarker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    //_centerMarker.hide();
    //addMarker();
    
//    map.addEventListener("moveend",function(p){
//    	 if(_markerClusterer){
//             _markerClusterer.clearMarkers(_markers);
//         }
//         _markerClusterer = new BMapLib.MarkerClusterer(map, {markers:_markers});
//    })
    
   
   
}

function addMarkers(unitMarker, content){	// 创建标注对象并添加到地图
    unitMarker.addEventListener('click',function(p){
        var point = new BMap.Point(p.point.lng, p.point.lat);
        var infoWindow = new BMap.InfoWindow(content);  // 创建信息窗口对象  //获取提示信息内容 - 方法在home.js
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    });
}
//////显示单位点
function showUnitMarkerInMap(){

}

//显示报警点位
function showPointType(data){
   // if(type == "load"){
        clearLineInMap("firePower,circle");//清除报警点位，保存消防力量点位
    //}
    _unitPoint = data;
    _markers = [];
    for (var i = 0; i < _unitPoint.length; i++) {
        var unitPoint = _unitPoint[i].unitPoint.split(',');
        if(!unitPoint[0]||!unitPoint[1]){
        	continue;
        }
        //console.log(unitPoint[0], unitPoint[1]);
        var point = new BMap.Point(unitPoint[0], unitPoint[1]);

        //设置覆盖物点位的属性
        var unitMarker =  setMarkerPoint(_unitPoint[i],point);

        /////////////////////////////////////////////
        var result = ifViewMap(point);//用于判断点位是否在可视区域 返回 true/false

        if(result){
            if(_unitPoint[i].unittype != 0){
                _markers.push(unitMarker);
            }
            map.addOverlay(unitMarker);
        }
        mapPointEvemt(unitMarker,_unitPoint[i]); //添加覆盖物事件
        // _unitMarker.hide()
    }


    //添加点聚合
    //if(_markerClusterer){
    //    _markerClusterer.clearMarkers(_markers);
    //}
    //_markerClusterer = new BMapLib.MarkerClusterer(map, {markers:_markers});

    map.addEventListener("zoomend",function(){if(!_tilesloaded) updataPointType();});//地图更改缩放级别结束时触发触发此事件 - 方法写于map.js
    map.addEventListener("dragend",function(){if(!_tilesloaded) updataPointType();});//停止拖拽地图时触发

}

//设置覆盖物点位的属性
function setMarkerPoint(data,point){
    var icon = "../../Content/images/mapMarker/icon_auto.png";
    if(data.alarmStatus == 1){ //火警
        icon = "../../Content/images/mapMarker/icon_fire.png";
    } else if(data.alarmStatus == 2){//故障
        icon = "../../Content/images/mapMarker/icon_fault.png";
    }else if(data.alarmStatus == 9){//RTU
        icon = "../../Content/images/mapMarker/icon_RTU1.png";
    }

    else if( data.unittype == 0){
        icon = "../../Content/images/mapMarker/unitPoint.png";
        _loginInfo.weibaoUnitPoint = data.unitPoint;
    }
    var mapIcon = new BMap.Icon(icon, new BMap.Size(20,20));
    var unitMarker = new BMap.Marker(point,{icon:mapIcon});  // 创建标注
    unitMarker.lab="point";
    unitMarker.labUnitid=data.unitId;
    if(data.alarmStatus == 1){ //火警
        unitMarker.alarmType = "fire"
    }
    unitMarker.unitid = data.unitId;

    return unitMarker;
}

//更新点图点位 - 用于缩放或拖拽地图是 对 可视区域的点进行重加载 - 即 只显示可视区域的点为
function updataPointType(){
   //clearLineInMap("point");
    var unitPointMap = new Map();
    var allOverlay = map.getOverlays();

    for (var i = 0; i < allOverlay.length; i++) {
        var unitid = allOverlay[i].labUnitid;
        if(unitid){ //先判断是否是单位点 - 是
            unitPointMap.set(unitid,allOverlay[i]);
        }
    }

    for(var i = 0;i<_unitPoint.length;i++){
        var point = new BMap.Point(_unitPoint[i].unitPoint.split(',')[0], _unitPoint[i].unitPoint.split(',')[1]);
        var unitid = _unitPoint[i].unitId;
        if(ifViewMap(point)&&!unitPointMap.has(unitid)){//单位点在可视区域范围内 && 原单位点的覆盖物内没有该点 - 添加
            var unitMarker = setMarkerPoint(_unitPoint[i],point);
            map.addOverlay(unitMarker);
            mapPointEvemt(unitMarker,_unitPoint[i]); //添加覆盖物事件
            //var content = 'updataPointType()';
            //mapPointEvemt(unitMarker,_unitPoint[i], content); //添加覆盖物事件
           // console.log("yes:"+1)

        }
        else if(!ifViewMap(point)&&unitPointMap.has(unitid)){//单位点不在可视区域范围内 && 原单位点的覆盖物内有该点 - 清除
            map.removeOverlay(unitPointMap.get(unitid));
           // console.log("no:"+1)
        }
    }

}


function mapPointEvemt(unitMarker,data){
    var unitId = data.unitId;
    var alarmStatus = data.alarmStatus;
    //unitMarker.setTitle(data.alarmStatus);
    unitMarker.addEventListener("click",function(p){
        //var icon = new BMap.Icon("../../Content/images/mapMarker/unitPoint_hover.png", new BMap.Size(20,20));
        //p.target.setIcon(icon);
        var allOverlay = map.getOverlays();
        /*console.log(p);*/
        for (var i = 0; i < allOverlay.length; i++){
            if(allOverlay[i].lab=="point"){
                allOverlay[i].setAnimation(); //清除其他点的动画
            }
        }
        p.target.setAnimation(BMAP_ANIMATION_BOUNCE);
        map.addOverlay(unitMarker);
        clearLineInMap("point,firePower");//清除报警点位，保存消防力量点位
        //if(data.unittype != 0){
            // console.log(p.target.getIcon())
            //$(this).setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            var content = '';//弹窗html 字符串
            var isAdd = true;//为空不设置弹窗
            var temp = '';//火警 故障class样式
            if(data.alarmStatus == 1){
                temp = 'fire';
            }else if(data.alarmStatus == 2){
                temp = 'fault';
            }

            $.ajaxSettings.async = false;
            $.post('/front/homeIndex/getUnitByUnitpoint ',{'unitId':unitId}, function(data){
                if(data.success == true){//判断success的值为真  否则表示单位编号不存在
                    for(var i=0;i<data.obj.length;i++){
                        _unitId = data.obj[i].id;
                        tclass = unitId == _unitId ? temp : '';
                        if(unitId == _unitId){
                            content += '<h3 class="div_unitname '+tclass+' fontSize16" onclick="goUnitFunc(\''+_unitId+'\','+alarmStatus+');">'+(data.obj[i].unitName || '暂无')+'</h3>';
                        }else{
                            content += '<h3 class="div_unitname '+tclass+' fontSize16" onclick="goUnitFunc(\''+_unitId+'\');">'+(data.obj[i].unitName || '暂无')+'</h3>';

                        }
                    }
                    content += '<div class="div_address fontSize14">地址：'+(data.obj[0].address || '暂未收录')+'</div>';
                    _data = data.obj[0];
                    if(_data.unitType == 1){
                        $("#unitMapLine").show();
                    }else{
                        $("#unitMapLine").hide();
                    }
                }else{//data.success == flase 表示单位编号不存在
                    isAdd = false;
                }
            });
            $.ajaxSettings.async = true;

            $('#unitBaseInfoContent span[labname]').each(function(){
                var labname = $(this).attr('labname');
                $(this).text(_data[labname]||"-");
            });
            $('#unitMapLine').attr('labPoint',_data.unitPoint).removeClass('active');
            $('#btnUnitMore').attr('data-unitid',unitId);
            if(_data.phone){
                $('#unitPhone').html("<a href='skype://+86"+_data.phone+"'  onclick=\"saveCallRecord('"+unitId+"','"+_data.phone+"');\"><span labname='phone'>"+_data.phone+"</span><img src='../../Content/images/Home/icon_tell.png' class='ico_tel'></a>")
            }
            //getUnitBaseInfo(unitId); //首页左2显示单位信息 - 方法在首页Index.html
            getUnitVideo(unitId);
            getUnitSystem(unitId);
            $('.unitNameBox>img').remove();
            if(p.target.alarmType == "fire"){//我添加的
                $('.unitNameBox').append('<img src="../../Content/images/mapMarker/icon_fire.png" class="btn_alarmTypeFire">')
            }else if(data.alarmStatus == 2){
                $('.unitNameBox').append('<img src="../../Content/images/mapMarker/icon_fault.png" class="btn_alarmTypefault" style="width: 16px">')
            }else if(data.alarmStatus == 9){
                $('.unitNameBox').append('<img src="../../Content/images/mapMarker/icon_RTU1.png" class="btn_alarmTypeRTU" style="width: 16px">')
            }


            $('.firePower').attr('unitid',unitId);
            $('#unitMapLine').removeClass('active');//路线
            //$('.firePower>span').removeClass('active'); //消防力量
            $('#firePowerTxt').val("");
            layer.close(_firePowerIndex);
            //清除消防力量点位和圈


            // addMarkers(unitMarker,content);
            if(isAdd) {
                var point = new BMap.Point(p.point.lng, p.point.lat);
                var infoWindow = new BMap.InfoWindow(content);  // 创建信息窗口对象  //获取提示信息内容 - 方法在home.js
                map.openInfoWindow(infoWindow, point); //开启信息窗口
            }
        //}
    });

    //unitMarker.addEventListener("mouseout",function(p){
    //    var icon = new BMap.Icon("../../Content/images/mapMarker/unitPoint.png", new BMap.Size(32,32));
    //    p.target.setIcon(icon)
    //    // p.target.setIcon("../../Content/images/mapMarker/unitPoint.png", new BMap.Size(32,32))
    //    p.target.setAnimation(null)
    //    //console.log(p.target.getIcon())
    //    //$(this).setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    //});
}

//搜索单位选中触发点位事件
function getPointInMap(unitid){
    //console.log(_unitPoint);
    //showPointType(_unitPoint);
    for(var i = 0 ; i < _unitPoint.length;i++){
        if(_unitPoint[i].unitId == unitid){
        	var pointX = _unitPoint[i].unitPoint.split(',')[0];
        	var pointY = _unitPoint[i].unitPoint.split(',')[1];
        	if(pointX && pointY){
        		var point = new BMap.Point(pointX,pointY);
        		map.centerAndZoom(point,15); //地图中心点
        	}
        	break;
        }
    }
    showPointType(_unitPoint);
    var allOverlay = map.getOverlays();
    for (var i = 0; i < allOverlay.length; i++) {
        if(allOverlay[i].lab=="point"){
            allOverlay[i].setAnimation(); //清除其他点的动画
        }
        if(allOverlay[i].unitid == unitid){
            allOverlay[i].setAnimation(BMAP_ANIMATION_BOUNCE);
            getUnitBaseInfo(unitid); //首页左2显示单位信息 - 方法在首页Index.html
            getUnitVideo(unitid);
            getUnitSystem(unitid);
            $('.firePower').attr('unitid',unitid);
            //$('.firePower>span').removeClass('active'); //消防力量
            $('#firePowerTxt').val("");
            layer.close(_firePowerIndex);
            $('#unitMapLine').removeClass('active');//路线
            clearLineInMap("point,firePower");//清除报警点位，保存消防力量点位
        }
    }
}

//根据类型显示消防力量点位
function showFirePowerPoint(key,data){
    var lab = 'firePower';
    if(key == 1){ //1表示从地图分类进来的接口，给_firePowerPoint赋值，0表示根据距离选择的消防力量，不需要赋值
        _firePowerPoint = data;
        //lab = "firePower";
        clearLineInMap("point");//清除消防力量点位，保存报警点位
    }
    else{
        clearLineInMap("point");//清除消防力量点位，保存报警点位
    }
    var markers = [];
    for (var i = 0; i < data.length; i++) {
        //var unitPoint = _unitPoint[i].unitPoint.split(',');
        var point = new BMap.Point(data[i].pointX, data[i].pointY);
        var icon = "";
        if(data[i].type == 4){
            data[i].type = 3
        }
        if(data[i].type == 3 || data[i].type == 5 || data[i].type == 0 || data[i].type == 2 || data[i].type == 1 || data[i].type == 6 || data[i].type == 7 || data[i].type == 8){
            icon = "../../Content/images/mapMarker/icon_firePower_"+data[i].type+".png";
        }
        else{
            return;
        }

        var mapIcon = new BMap.Icon(icon, new BMap.Size(20,20));
        var powerMarker = new BMap.Marker(point,{icon:mapIcon});  // 创建标注
        powerMarker.unitid = data[i].unitId;
        powerMarker.lab=lab;
        if(data[i].type == 1){
        	powerMarker.labtype = "zhongdui";
        }
        //markers.push(powerMarker);
        mapFirePowerPointEvemt(powerMarker,data[i]); //点击图标事件
        map.addOverlay(powerMarker);
        // _unitMarker.hide()
    }
}

function mapFirePowerPointEvemt(powerMarker,data){
    powerMarker.addEventListener("click",function(p) {
        //console.log(p);
        //var opts = {
        //    width : 250,     // 信息窗口宽度
        //    height: 80,     // 信息窗口高度
        //    title : "信息窗口" , // 信息窗口标题
        //    enableMessage:true//设置允许信息窗发送短息
        //};
        var point = new BMap.Point(p.point.lng, p.point.lat);
        var more = "",pname="";
        if(data.type == 0|| data.type == 1||data.type == 2 || data.type == 8){
            more = '<div class="div_more" onclick="getFirePowerContent(\''+data.id+'\',\''+p.target.labtype+'\')">详情</div>';
        }
        if(data.type == 6|| data.type == 1){
            pname = '<div class="span_pname fontSize14">所属：'+data.pname+'</div>'
        }
        var html = '<h3 class="div_unitname fontSize16">'+data.name+'</h3>'+
                "<div class='div_address fontSize14'>地址："+(data.address||"暂未录入")+"</div>"+pname+more;



        var infoWindow = new BMap.InfoWindow(html); // 创建信息窗口对象  //获取提示信息内容 - 方法在home.js

        map.openInfoWindow(infoWindow, point); //开启信息窗口
    })
};

//根据距离显示消防力量点位
function showFirePowerInCircle(data,circle,point){
    //console.log(_firePowerPoint)
    clearLineInMap("point");//清除圆，保存报警和力量点位
    //if(_firePowerPoint.length>0){
    //    for(var i= 0 ; i < data.length;i++){
    //        var flag = true;
    //        for(var k= 0 ; k < _firePowerPoint.length;k++){
    //            if(data[i].id == _firePowerPoint[i].id){
    //                flag = false;
    //            }
    //        }
    //        if(flag){
    //            showFirePowerPoint(0,data);
    //           // map.removeOverlay(allOverlay[i]);
    //        }
    //    }
    //}
    //else{
        showFirePowerPoint(0,data)
    //}
////添加圆覆盖物

    var unitPoint = new BMap.Point(point.split(',')[0], point.split(',')[1]);
        var opts = {
        strokeColor:"",
        fillColor:'#0E4B81',
        strokeWeight:2
    };
    var powerCircle = new BMap.Circle(unitPoint,circle,opts);
    powerCircle.lab = "circle";
    map.addOverlay(powerCircle);

}

//////隐藏单位点
function clearLineInMap(key,lab){ //“lab”是后加的属性，用于清除不同属性的点位
    var keys = key.split(',');
    lab = lab || "lab";
    var allOverlay = map.getOverlays();
    //console.log(allOverlay);
    for (var i = 0; i < allOverlay.length; i++) {
        var flag = false;
        for (var k = 0; k < keys.length; k++) {
            //console.log(allOverlay[i][lab], keys[k]);
            if (allOverlay[i][lab] && (allOverlay[i][lab] == keys[k])) {
                flag = true;
            }
            if(keys[k] == "point"){
                if(allOverlay[i]._text){
                    flag = true;
                }
            }
        }
        if(!flag){
            map.removeOverlay(allOverlay[i]);
        }

    }
}

function mapFunction(){
    $('.wrap_mapType>div').click(function(){
        var mapType = $(this).attr('map');
        $('.BMap_noprint[title=进入全景]').hide();
        if(mapType == "normal"){
            map.setMapType(BMAP_NORMAL_MAP)
        }
        if(mapType == "hybrid"){
            map.setMapType(BMAP_HYBRID_MAP)
        }
        if(mapType == "panorama"){
            var swf = navigator.plugins["Shockwave Flash"];
            if (!swf) {
                top.layer.confirm(
                    '您未安装Flash Player播放器或者版本过低<br/>按“确定”键进行安装或更新！',
                    {btn: ['确定', '取消'],skin: 'layui-layer-confirm',area:['350px','200px'],title: '<img src="../Content/images/Home/logout_tip.png" class="icon_signOut">提示' },
                    function (index) {
                        window.open('http://www.macromedia.com/go/getflashplayer');
                    }
                );
                return;
            }
            $('.BMap_noprint[title=进入全景]').show();
            var stCtrl = new BMap.PanoramaControl(); //构造全景控件
            stCtrl.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
            stCtrl.setOffset(new BMap.Size(20, 20));
            map.addControl(stCtrl);//添加全景控件
        }
    });
    //
    $('.map_control>img').click(function(){
        var mapCol = $(this).attr('control');
        if(mapCol == "normal"){
            map.setMapType(BMAP_NORMAL_MAP)
        }
        if(mapCol == "position"){
            if(!$(this).hasClass('pos')){
                _centerMarker.show();
                $(this).addClass('pos');
            }
            else{
                _centerMarker.hide();
                $(this).removeClass('pos');
            }
        }
        if(mapCol == "reduce"){
            map.setZoom(map.getZoom() - 2);
        }
        if(mapCol == "add"){
            map.setZoom(map.getZoom() + 2);
        }
    })
}


function setMapStyle(type){
    if(type == 1||type == 2){ //深
        map.setMapStyle({
            styleJson:[{
                "featureType": "green",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#04326dff"
                }
            }, {
                "featureType": "background",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#052757"
                }
            }, {
                "featureType": "highway",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#031b3dff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.stroke",
                "stylers": {
                    "color": "#195e97ff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#031c3bff"
                }
            }, {
                "featureType": "all",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#2d7ebdff"
                }
            }, {
                "featureType": "all",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#011f53ff"
                }
            }, {
                "featureType": "land",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#04316cff"
                }
            }, {
                "featureType": "railway",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subway",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subwaylabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subwaylabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "poilabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "poilabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "background",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#838aacff"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#ffffff00"
                }
            }, {
                "featureType": "scenicspotslabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "scenicspotslabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "scenicspotslabel",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#ffffff00"
                }
            }, {
                "featureType": "scenicspotslabel",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#9b9595ff"
                }
            }, {
                "featureType": "road",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "road",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#d4d4d8ff"
                }
            }, {
                "featureType": "road",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#ffffff1f"
                }
            },{
                "featureType": "road",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "road",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }
            ]
        });
    }
    if(type == 999){ //黑
        map.setMapStyle({
            styleJson:[{
                "featureType": "water",
                "elementType": "geometry",
                "stylers": {
                    "color": "#181818ff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#736d61ff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.stroke",
                "stylers": {
                    "color": "#736d61ff"
                }
            }, {
                "featureType": "highway",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#565656ff"
                }
            }, {
                "featureType": "poilabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "poilabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "railway",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#565656ff"
                }
            }, {
                "featureType": "railway",
                "elementType": "geometry.stroke",
                "stylers": {
                    "color": "#56565600"
                }
            }, {
                "featureType": "railway",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "land",
                "elementType": "geometry",
                "stylers": {
                    "color": "#212121ff"
                }
            }, {
                "featureType": "background",
                "elementType": "geometry",
                "stylers": {
                    "color": "#212121ff"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#444444ff"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#ccccccff"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "districtlabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subwaylabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subwaylabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }]
        });
    }
    if(type == 3){ //浅
        map.setMapStyle({
            styleJson:[{
                "featureType": "green",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#04326dff"
                }
            }, {
                "featureType": "background",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#0A3C78"
                }
            }, {
                "featureType": "highway",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#031b3dff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.stroke",
                "stylers": {
                    "color": "#195e97ff"
                }
            }, {
                "featureType": "road",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#053161ff"
                }
            }, {
                "featureType": "all",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#2d7ebdff"
                }
            }, {
                "featureType": "all",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#011f53ff"
                }
            }, {
                "featureType": "land",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#06428Dff"
                }
            }, {
                "featureType": "poilabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "otherlabel",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#2dc4bbff"
                }
            }, {
                "featureType": "otherlabel",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#ffffff0"
                }
            }, {
                "featureType": "otherlabel",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "subwaylabel",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "nationalwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "railway",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "subway",
                "elementType": "geometry",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "provincialwaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "tertiarywaysign",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            },{
                "featureType": "road",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "road",
                "elementType": "labels.icon",
                "stylers": {
                    "visibility": "off"
                }
            }
            ]
        });
    }
}

//

function ifViewMap(point){
    var bs = map.getBounds();   //获取可视区域
    var pStart = bs.getSouthWest();   //可视区域左下角
    var pEnd = bs.getNorthEast();

    //console.log(pStart)
    var polygon = new BMap.Polygon([
        new BMap.Point(pStart.lng,pStart.lat),
        new BMap.Point(pEnd.lng,pStart.lat),
        new BMap.Point(pEnd.lng,pEnd.lat),
        new BMap.Point(pStart.lng,pEnd.lat)
    ], {strokeColor:"rgba(0,0,0,0)", strokeWeight:1, strokeOpacity:0,fillOpacity: 0,fillColor:"rgba(0,0,0,1)"});

    var result = BMapLib.GeoUtils.isPointInPolygon(point, polygon);

    return result;
}

//用于报警推送时的地图显示相应点位
//搜索单位选中触发点位事件
function getAlarmPushPointInMap(unitid){
    var allOverlay = map.getOverlays();
    for (var i = 0; i < allOverlay.length; i++) {
        //if(allOverlay[i].lab=="point"){
        //    allOverlay[i].setAnimation(); //清除其他点的动画
        //}
        if(allOverlay[i].unitid == unitid){
            var point = new BMap.Point(allOverlay[i].point.lng,allOverlay[i].point.lat);
            allOverlay[i].setAnimation(BMAP_ANIMATION_BOUNCE); //添加点位动画
        }
    }
}