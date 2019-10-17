
var login = JSON.parse(localStorage.getItem("Login"));
//上传图片
function upload(picId, loadImgId, photoCoverId, isChange,count) {

    var file_string =$("#" + picId).attr("file_string");
    //检查上传文件是否是图片，如果是则进行上传操作，若不是则提示用户注意上传文件格式
    var imgurl = $("#" + picId).val();
    var extend = imgurl.substring(imgurl.lastIndexOf("."), imgurl.length);
    // var urlName = imgurl.substring(imgurl.lastIndexOf("/"), imgurl.lastIndexOf("."));
    // if (reg.test(urlName.trim())) {
    //     layer.msg("附件名不能含有特殊字符");
    //     return;
    // }
    extend = extend.toLowerCase();
    if (extend == ".jpg" || extend == ".jpeg" || extend == ".png"
        || extend == ".gif" || extend == ".bmp" || extend == ".mp4") {
        layer.msg("附件上传中，请稍等...",{time:1500})
        $.ajaxFileUpload({
            url : baseUrl + "/schoolManage/recipes/upLoadPic?imageDataList=imageDataList",
            fileElementId :picId,
            dataType : "json",
            success : function(data) {

                //追加显示上传后的图片
                if(!isChange){
                    for (var i = 0; i < data.obj.length; i++) {
                        $("#" + loadImgId).append(
                            " <li style='position: relative;'> " +
                            "  <img class='loadImg' src='" + data.obj[i] + "' " +
                            " style = 'width:100px;height:100px;'/>" +
                            '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                            " " + " <img style='width: 15px;height: 15px;position: absolute;top: 3px;right: 3px;' src='"+baseUrl+"/image/mima.png' class='delete'/>" +
                            " </a>"+
                            " </li>"
                        );
                        file_string = file_string + data.obj[i] + ",";
                    }
                }
                $("#" + picId).attr("file_string", file_string);
            },
            complete: function() {
                //当上传结束后，重新生成input file，不然会失效
                $("#" + picId).val("");
                $("#" + photoCoverId).val("");
            },
            error : function(data, status, e) {
                layer.msg("系统繁忙");
            }
        });
    } else {
        return layer.msg("请上传jpg,jpep,png,gif,bmp,mp4格式的文件");
    }
}

function deleteFile(_this) {
    $(_this).closest("li").remove();
}

//function doChange(picId, photoId){
//    var upload_file = $.trim($("#" + picId).val());    //获取上传文件
//    $("#" + photoId).val(upload_file);     //赋值给自定义input框
//};

//上传图片(多张)
function uploadMany(picId, loadImgId, photoCoverId, isChange,count) {
    var file_string =$("#" + picId).attr("file_string");
    //检查上传文件是否是图片，如果是则进行上传操作，若不是则提示用户注意上传文件格式
    var imgurl = $("#" + picId).val();
    var extend = imgurl.substring(imgurl.lastIndexOf("."), imgurl.length);
    extend = extend.toLowerCase();
    if (extend == ".jpg" || extend == ".jpeg" || extend == ".png"
        || extend == ".gif" || extend == ".bmp" || extend == ".mp4") {
        layer.msg("附件上传中，请稍等...",{time:1500})
        $.ajaxFileUpload({
            url : baseUrl + "/schoolManage/recipes/upLoadPic?imageDataList=imageDataList",
            fileElementId :picId,
            dataType : "json",
            success : function(data) {

                //追加显示上传后的图片
                if(!isChange){
                    for (var i = 0; i < data.obj.length; i++) {
                        $("#" + loadImgId).append(
                            " <li style='position: relative;'> " +
                            "  <img src='" + data.obj[i] + "' " +
                            " style = 'width:100px;height:100px;'class='loadImg' />" +
                            '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                            " " + " <img style='width: 15px;height: 15px;position: absolute;top: 3px;right: 3px;' src='"+baseUrl+"/image/mima.png' class='delete'/>" +
                            " </a>"+
                            " </li>"
                        );
                        file_string = file_string + data.obj[i] + ",";
                    }
                }
                $("#" + picId).attr("file_string", file_string);
            },
            complete: function() {
                //当上传结束后，重新生成input file，不然会失效
                $("#" + picId).val("");
                $("#" + photoCoverId).val("");
            },
            error : function(data, status, e) {
                layer.msg("系统繁忙");
            }
        });
    } else {
        return layer.msg("请上传jpg,jpep,png,gif,bmp,mp4格式的文件");
    }
}
//上传头像
function uploadOne(picId, loadImgId, photoCoverId, isChange) {
    var file_string = '';
    //检查上传文件是否是图片，如果是则进行上传操作，若不是则提示用户注意上传文件格式
    var imgurl = $("#" + picId).val();
    var extend = imgurl.substring(imgurl.lastIndexOf("."), imgurl.length);
    extend = extend.toLowerCase();
    if (extend == ".jpg" || extend == ".jpeg" || extend == ".png"
        || extend == ".gif" || extend == ".bmp" || extend == ".mp4") {
        layer.msg("附件上传中，请稍等...",{time:1500})
        $.ajaxFileUpload({
            url : baseUrl + "/upload/upLoadPic?imageDataList=imageDataList",
            fileElementId :picId,
            dataType : "json",
            success : function(data) {
                //追加显示上传后的图片
                if(!isChange){
                    $("#" + loadImgId).html(
                    	"<li class='modalImg'><span>请选择上传图片</span></li>"+	
                        " <li style='position: relative;margin: 0px;border: none;'> " +
                        "  <img src='" + data.obj + "' " +
                        " style = 'width:100%;height:100%;'/>" +
                        '<a  onclick="deleteFile(this)" style="margin-top: 5px;margin-left: 5px;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                        " </a>"+
                        " </li>"
                    );
                    file_string = file_string + data.obj;
                }
                $("#" + picId).attr("file_string", file_string);
            },
            complete: function() {
                //当上传结束后，重新生成input file，不然会失效
                $("#" + picId).val("");
                $("#" + photoCoverId).val("");
            },
            error : function(data, status, e) {
                layer.msg("系统繁忙");
            }
        });
    } else {
        return layer.msg("请上传jpg,jpep,png,gif,bmp");
    }
}

function uploadOneMany(picId, loadImgId, photoCoverId, isChange) {
    var file_string = '';
    //检查上传文件是否是图片，如果是则进行上传操作，若不是则提示用户注意上传文件格式
    var imgurl = $("#" + picId).val();
    var file_extend = $("#" + picId).attr("file_string");
    var extend = imgurl.substring(imgurl.lastIndexOf("."), imgurl.length);
    extend = extend.toLowerCase();
    if (extend == ".jpg" || extend == ".jpeg" || extend == ".png"
        || extend == ".gif" || extend == ".bmp" || extend == ".mp4") {
        layer.msg("附件上传中，请稍等...",{time:1500})
        $.ajaxFileUpload({
            url : baseUrl + "/upload/upLoadPic?imageDataList=imageDataList",
            fileElementId :picId,
            dataType : "json",
            success : function(data) {
                //追加显示上传后的图片
                if(!isChange){
                	 if($('#'+ loadImgId).is(':empty')){
                		  $("#" + loadImgId).html("<li class='modalImg'><span>请选择上传图片</span></li>");
                	 }
                	 $("#" + loadImgId).append(
                        " <li style='position: relative;margin: 0px;border: none;'> " +
                        "  <img src='" + data.obj + "' " +
                        " style = 'width:100%;height:100%;'/>" +
                        '<a  onclick="deleteFile(this)" style="position: absolute;bottom:0;right:0;width:20px;height:20px;background-size:100% 100%;" class="reg_btn_yzm reg_btn_yzm_display settled_up  input-file">' +
                        " <img style='width: 15px;height: 15px;position: absolute;top: 3px;right: 3px;' src='"+baseUrl+"/image/mima.png' class='delete'/>" +
                        " </a>"+
                        " </li>"
                    );
                    file_string = file_string + data.obj;
                }
                if(file_extend =="" || file_extend == null){
                	$("#" + picId).attr("file_string", file_string);
                }else{
                	$("#" + picId).attr("file_string", file_extend+","+file_string);
                }
            },
            complete: function() {
                //当上传结束后，重新生成input file，不然会失效
                $("#" + picId).val("");
                $("#" + photoCoverId).val("");
            },
            error : function(data, status, e) {
                layer.msg("系统繁忙");
            }
        });
    } else {
        return layer.msg("请上传jpg,jpep,png,gif,bmp");
    }
}

