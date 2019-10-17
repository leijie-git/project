/**
 * Created by Administrator on 2016/12/23.
 */
var MyIframe = {
    param: {
        iframeID: "#iframe1",
        width: 800,
        height: 650,
        // animateClass: "fadeInDw"
    },
    other:{},
    getIframe: function (o) {
        var _this = this;
        var p = {
            oFormID: o.oFormID,
            url: o.url,
            headerInfo:o.headerInfo,
            getFather:o.getFather,
            zIndex:o.zIndex,
            iframeID: o.iframeID != undefined || o.iframeID != null || o.iframeID != "" ? o.iframeID : this.param.iframeID,
            width: o.width == undefined || o.width == null || o.width == "" ? this.param.width : o.width,
            height: o.height == undefined || o.height == null || o.height == "" ? this.param.height : o.height,
            closeB:o.closeB == true ? true: false ,
            clearIframe:o.clearIframe == true ? true: false ,
            clickOut:o.clickOut == true ? true: false ,
            succ:o.succ
        }
        _this.other=p;
        _this.other.alert=function(con){
        	layer.msg(con);
        };
        _this.other.cls=function () {
            $(p.iframeID).parent().parent().remove();
        }
        if (!p.url) {
            alert("请给出iframe的url")
            return false;
        }
        if ($(o.iframeID).length == 0) {
            _this.createIframe(o.iframeID, o.url,p);
            if(p.headerInfo){
                _this.createHeader(p.iframeID,p.headerInfo)
            }
        } else {
            _this.init(_this, p);
        }
        if (!p.oFormID) {
            alert("请选择子框里的元素id")
            return false;
        } else {
            layer.load(2);
            $(p.iframeID).load(p.url,function (d,txt,x) {
                layer.closeAll('loading');
                var ifm=$(p.iframeID),
                    form=$(p.oFormID),
                    par=ifm.parent(),
                    pPar=par.parent();
                    window.other=_this.other;
                    par.css({width: p.width, height: p.height})
                if(x.responseText.length<100){
                    var oo=JSON.parse(x.responseText);
                    if(oo.success==false){
                        ifm.html(o.msg)
                    }
                }
            	if(Object.prototype.toString.call(p.succ)=='[object Function]'){
            		p.succ();
            	}

                $(window).resize(function () {
                    par.css({"top":(pPar.height() - par.height()) / 2,"left":(pPar.width() - par.width()) / 2})
                });
                if(p.closeB){
                    var c=$('<span class="closeWrap">&times;</span>');
                    $(p.iframeID).parent().append(c);
                    c.click(function () {
                        if(p.clearIframe){
                            pPar.remove();
                        }
                        pPar.fadeOut(200);
                    })
                }
                if (o.hasOwnProperty("btn")) {
                    for (var i = 0; i < o.btn.length; i++) {
                        _this.createBtn(_this, p.iframeID, form, o.btn[i])
                    }
                }
                if(p.getFather){
                    var gf=p.getFather;
                    if(gf.eleID&&Object.prototype.toString.call(gf.fn)=='[object Function]'){
                        var fEle=ifCon.find(gf.eleID);
                        fEle.click(function () {
                            var w=window.parent;
                            var e=$(gf.currentEle,parent.document);
                            gf.fn(w,e)
                        })
                    }
                }
                var iHeight = par.height() - par.find("div.btn_mydiv").height()-par.find("div.headerInfo").height()-22;
                $(p.iframeID).height(iHeight);
                _this.init(_this, p);
            })
        }
    },
    createIframe: function (iframeID, url,p) {
        var i=iframeID.replace(/^#|\./g,"");
        var zIndex=100;
        if(p.zIndex){
            zIndex=p.zIndex;
        }
        var iframe='<div class="y_iWrap" style="z-index: ' +zIndex+ '"><div class="y_iCon animated">' +
            '<div id="'+i+'" style="overflow-y: auto;padding: 6px" ></div>' +
            '</div></div>';
        $("body").append(iframe);
    },
    createHeader:function (iframeID,headerInfo) {
        var par=$(iframeID).parent();
        par.prepend("<div class='headerInfo' ><h4>" +headerInfo+ "</h4></div>");
        $(".headerInfo").mousedown(function (ev) {
            $(this).css("cursor","-webkit-grabbing")
            var oldX=ev.pageX;
            var oldY=ev.pageY;
            var parX=parseInt(par.css("left"));
            var parY=parseInt(par.css("top"));
            var chaX=oldX-parX;
            var chaY=oldY-parY;
            $(document).mousemove(function (e) {
                var newX=e.pageX;
                var newY=e.pageY;
                par.css("left",newX-chaX)
                par.css("top",newY-chaY)
            })
        })
        $(document).mouseup(function (e) {
            $(this).css("cursor","auto")
            $(document).unbind("mousemove")
        })
        $(".headerInfo").mouseout(function () {
            $(".headerInfo").unbind("mousemove")
        })
    },
    createBtn: function (_this, iframeID, form, obtn) {
        var btn = $('<button>' + obtn.text + '</button>');
        var btnWrapDiv = $("<div class='btn_mydiv'></div>").append(btn)
        $(iframeID).parent().append(btnWrapDiv);
        if (Object.prototype.toString.call(obtn.fn) === '[object Function]') {
            $(btn).click(function () {
                obtn.fn(form)
            })
            $(btn).mouseleave(function () {
                $(this).removeClass("btn_down")
            })
            $(btn).mousedown(function () {
                $(this).addClass("btn_down")
            })
            $(btn).mouseup(function () {
                $(this).removeClass("btn_down")
            })
        }
    },

    init: function (_this, p) {
        var i = $(p.iframeID),
            // anClass = o.animateClass,
            parent = i.parent(),
            pParent = parent.parent();
        pParent.show();
        parent.fadeIn(400)
        parent.css({"top":(pParent.height() - parent.height()) / 2,
        "left":(pParent.width() - parent.width()) / 2});
        if(p.clickOut){
            pParent.click(function () {
                $(this).fadeOut(200);
                parent.hide()
                if(p.clearIframe){
                    pParent.remove()
                }
            });
            parent.click(function (e) {
                e.stopPropagation();
            })
        }
    }
};