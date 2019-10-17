/**
 * Created by 20170810001 on 2018/9/6.
 */
$(function(){
    domEvent();
});

function domEvent(){
    setInterval(function(){
        $('.time_minute').text(getNowMinute());
        $('.week').text(getNowWeek());
        $('.day').text(getNowDay());
    },1000);
}