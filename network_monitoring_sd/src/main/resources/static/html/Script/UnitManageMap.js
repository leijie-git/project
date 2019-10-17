/**
 * Created by 20170810001 on 2018/9/28.
 */
//地图对象
var mapData = {
    map: null,
    zoom: 15,
    j: 120.643098,
    w: 31.268893,
};
$(function(){
    //位置地图
    initMap();
})

//位置地图
function initMap() {
    mapData.map = new BMap.Map('map');
    var point = new BMap.Point(mapData.j, mapData.w);
    mapData.map.centerAndZoom(point, 15);
    mapData.map.setCurrentCity("江苏省");
    // mapData.map.enableScrollWheelZoom(true); //开启鼠标滚轮事件
    var marker = new BMap.Marker(new BMap.Point(mapData.j, mapData.w)); //创建点
    mapData.map.addOverlay(marker); //添加点


}