/**
 * Created by Administrator on 2016/10/10.
 */
var ajaxJS = function(url, data, async, type, successfn) {
    // showLoading();
    async = (async == null || async == "" || typeof (async) == "undefined") ? "true"
        : async;
    type = (type == null || type == "" || typeof (type) == "undefined") ? "post"
        : type;
    data = (data == null || data == "" || typeof (data) == "undefined") ?
        // {
        // "date" : new Date().getTime()
        // }
        ""
        : data;
    $.ajax({
        type : type,
        async : async,
        data : data,
        url : "http://172.16.0.242:10019/"+url,
        // url : "http://localhost:8080/tc-console/"+url,
        dataType : "json",
        // beforeSend : function(request) {
        //     $(".lt").show()
        //     request.setRequestHeader("X-Token", "asdasdasdasdasdasdadsa");
        // },
        success : function(d) {
            successfn(d);
        },
        error : function(e) {

        }
    });
};