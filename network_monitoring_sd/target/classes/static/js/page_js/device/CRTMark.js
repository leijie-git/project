 var _imgrtstr = "";
 var _pointPercentY = 0, _pointPercentX = 0;
 var _crtWidth = $('#imgDiv').width(); _crtHeight = $('#imgDiv').height(); //图片的宽高


$(function() {
	 $(document).bind("contextmenu", function () { return false; });//鼠标右击 阻止默认菜单
	 initDataSource();
	 domEvelement();
	 initPointType();
	 getSystemList();
	 getPointVideoSelect();
});

///////初始化设备型号下拉框
function initPointType(){
	$.ajax({
		type: "post",
		async: true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		url: baseUrl+"/code/getListByGroupKey",
		data:{
			codeGroupKey:"PointType"
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				var html = "";
				var content = "<option value=''>--请选择--</option>";
				for(var i=0;i<data.obj.length;i++){
					html += '<div class="" codeid="'+data.obj[i].codeid+'" logo="/image/crt/'+data.obj[i].codevalue+'.png" code="'+data.obj[i].codevalue+'"><img src="/image/crt/'+data.obj[i].codevalue+'.png"><span>'+data.obj[i].codename+'</span></div>'
					content += '<option value="' + data.obj[i].codeid + '">' + data.obj[i].codename + '</option>';
				}
				$('.rank_list').append(html);
				//$('.wrap_miancon').css('height','calc(100% - '+$('.rank_list').height()+'px)');//
				$("#pointType").html(content);
			} else {
				layer.msg(data.desc);
			}
		},
		error: function (e) {
			
		}
	});
}


// 初始化系统类型下拉列表
function getSystemList() {
	$.ajax({
		type : "get",
		async : true,
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		url : baseUrl + "/eqClass/getSystemList",
		dataType : "json",
		success : function(data) {
			if (data.success) {
				var content = "<option value=''>--请选择--</option>";
				for(var i=0;i<data.obj.length;i++){
					content += '<option value="' + data.obj[i].id + '">' + data.obj[i].eqsystemname + '</option>';
				}
				$("#eqSystemId").html(content);
			} else {
				layer.msg(data.desc);
			}
		},
		error : function(e) {

		}
	});
}

/////////左侧区域列表 - start
//资源管理（上级菜单）
function initDataSource() {
	initTreeDataSource();
	$("#areaTree").show();
}

//初始化资源
function initTreeDataSource(unitName) {
	$.ajax({
		url : baseUrl + "/crtMark/getUnitAssociatedArea",
		type : "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		data : {
					unitName:unitName,
					unitId:$("#unitId").val()
				},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				convert(data.obj);
			} else {
				layer.msg(data.msg);
			}
		}
	})
}
// 将返回数据转换成数组
function convert(result) {
	var nodes = [];
	for ( var i in result) {
		nodes.push(result[i]);
	}
	doInitSecondSource(nodes);
}
// 给组织添加点击事件
function doInitSecondSource(nodes) {
	var setting = {
		view : {
			dblClickExpand : true,// 屏蔽掉双击事件
			showIcon : false
		},
		data : {
			key : {
				name : "name",
				
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : 'pid',
				rootPId : null
			}
		},
		callback : {
			onClick : zTreeOnC
		}
	};
	$.fn.zTree.init($("#areaTree"), setting, nodes);
	
	function zTreeOnC(event, treeId, treeNode) {
		var name = treeNode.name;
		var buildImgbg = $.fn.zTree.getZTreeObj("areaTree").getSelectedNodes()[0].buildImgbg;
		console.log(treeNode)
		
		if(treeNode.isArea == 1){		
			$('#imgDiv').html('<img src="'+ treeNode.buildImgbg +'" id="img">').attr({'height':treeNode.bgHeight,'width':treeNode.bgWidth}).css({'height':treeNode.bgHeight,'width':treeNode.bgWidth})
			$('#wrap_img').css({'top':0,'left':0});
			$('#CRTTable').bootstrapTable("destroy");
			mainSearch(treeNode.id);//获取CRT点位 - 加载表格
			
			showAllMark(treeNode.id);
			 _crtWidth = $('#imgDiv').width();
			 _crtHeight = $('#imgDiv').height();
		}
		else{
			
		}
		//$("#buildImgbg").val(buildImgbg);
	}
}
/////////左侧区域列表 - end

////加载所有点位
function showAllMark(buildAreaId){
	$.ajax({
		url : baseUrl + "/crtMark/getAllAreaAssociatedEquipment",
		type : "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		data : {buildAreaId: buildAreaId},
		dataType : "json",
		success : function(data) {
			if(data.success){
				var data = data.obj;
				var html = "";
				for(var i = 0 ; i < data.length;i++){
					 var top = (data[i].yaxis||0) / $('#imgDiv').height() * 100;     //
				     var left = (data[i].xaxis||0) / $('#imgDiv').width() * 100;    //
				///////加载点位
				     var codeid = data[i].codeid;
				     if(data[i].xaxis=="" && data[i].yaxis==""){
				    	 continue;
				     }
				     if(codeid){
				    	 var pointtype = $('.rank_list>div[codeid='+codeid+']').attr('code')
		                 var dom = $('<div class="dot" codeid="'+data[i].codeid+'" id="' + data[i].id + '" top="'+(data[i].yaxis||0)+'" left="'+(data[i].xaxis||0)+'" style=" top:' + (data[i].yaxis||0) + ';left:' + (data[i].xaxis||0) + ';display:block;" classCode="' + pointtype + '" /></div>');                    
		                 var tip = getCRTTips(data[i].adress,data[i].eqsystemName,data[i].partcode)
		                 dom.html(tip);
		                 dom.appendTo($('#imgDiv'));
		                 //var _dotLogo = "/Content/equipmenticon/" + _data[i].ClassCode + ".png";
		                 var dotLogo = pointtype// data[i].pointtype;
		                 udraggableDot(dom, dotLogo);
				     }
				    
				}
			}
		}
	})
}

//点位tips
function getCRTTips(adress,eqsystemName,partcode){
	var html = ' <div class="wrap_dotInfo"><div class="div_info">'
        + '<table cellpadding="0" cellspacing="0" align="center" class="table-detail">'
        + ' <colgroup><col width="50%" /><col width="50%" /></colgroup>'
        + ' <tbody><tr><th>真实地址</th><td class="adress">' + (adress || "") + '</td></tr><tr><th>部件地址</th><td class="partcode">' + (partcode || "") + '</td></tr></tbody>'
        + '</table></div><div class="triangle"></div></div>'
        
     return html;
}

//主页面查询
function mainSearch(buildAreaId) {
    $('#CRTTable').bootstrapTable({
        url: baseUrl + '/crtMark/getAreaAssociatedEquipment',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        ajaxOptions:{
            headers: {"Authorization": "Bearer "+login.token}		//token写入请求头
        },
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
            	pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                buildAreaId: buildAreaId,
                keyword:$("#searchTxt").val()
            };
//            $("#pageSize").val(this.pageSize);
//            $("#pageNumber").val(this.pageNumber);
            return param;
        },
        dataField: 'list',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100, 200, 500],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        searchTimeOut: 1000,
        //  	: true,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field: 'partcode',
                title: '编号'
            },
            {
            	field: 'adress',
            	title: '设备地址',
            	formatter: function (value, row, index) {
    				if (value == null) {
    					return "";
    				}
    				return '<p title="'+value+'" style="width: 80px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
    			}
            },
            {
                field: '',
                title: '操作',
                width: 200,
                formatter: function (value, row, index) {
                	//console.log(row)
                	var btnCalss = "btn-warning"
                	if(row.xaxis&&row.yaxis){
                		btnCalss = "btn-success"
                	}
                    var str = "";
                    	str += '<button class="cBtn-main mark btn '+ ((row.xaxis&&row.yaxis)?"btn-xs":"btn-warning") +'">&nbsp;标点</button>'
//                    	str += '<button class="cBtn-main edit btn btn-new btn-xs">&nbsp;编辑</button>'
                		str += '<button class="del btn btn-danger btn-xs">&nbsp;取消</button>'
                    return str;
                },
                events: {
                	'click .mark': function (e, value, row, index) {
                        markEqument(row);
                    },
                    'click .edit': function (e, value, row, index) {
                        editEqument(row);
                    },
                    'click .del': function (e, value, row, index) {
                        deleteOneEqument(row);
                    }
                }
            }
        ]
       
    });
}


function markEqument(row){
	 if(row.xaxis&&row.yaxis&&row.codeid){
		 $("#imgDiv>#"+row.id).addClass('choosed').siblings('.dot').removeClass('choosed');
	 }
	 else{
		 $('#imgDiv .dot').removeClass('choosed')
		 
		 if(row.codeid != null && row.codeid != ''){
			 $('.rank_list>div[codeid="'+row.codeid+'"]').eq(0).click();
		 }else{
			 $('.rank_list>div').eq(0).click(); //默认选中第一个设备
		 }
		 var pointtype = $('.rank_list>div.active').attr('code'); //设备类型
		 var codeid = $('.rank_list>div.active').attr('codeid'); //设备类型
		 var top = 0 / $('#imgDiv').height() * 100;     //
	     var left = 0 / $('#imgDiv').width() * 100;    //
	     
	     var dom = $('<div class="dot choosed" codeid="'+codeid+'"  id="' + row.id + '" top="0" left="0" style=" top:' + top + '%;left:' + left + '%;display:block;" classCode="' + pointtype + '" /></div>');                    
	     var tip = getCRTTips(row.adress,row.eqsystemName,row.partcode);
	     console.log(dom)
	     dom.html(tip);
	     dom.appendTo($('#imgDiv'));
	     //var _dotLogo = "/Content/equipmenticon/" + _data[i].ClassCode + ".png";
	     var dotLogo = pointtype;
	     udraggableDot(dom, dotLogo);
	 }
	 
}

//编辑
function editEqument(row){
	 $("#editCRTInfo").modal("show");
	 $("#indexId").val(row.id);
	 $("#pointType").val(row.codeid);
	 $("#eqSystemId").val(row.eqsystemid);
	 $("#xAxis").val(row.xaxis);
	 $("#yAxis").val(row.yaxis);
	 $("#partcode").val(row.partcode);
	 $("#address").val(row.adress);
	 $("#name").val(row.name);
	 $("#remark").val(row.remark);
	 $("#pointVideo").val(row.videoid);
}

//保存
$(".btnSure").click(function(){
	var address = $("#address").val();
	var name = $("#name").val();
	var remark = $("#remark").val();
	var id = $("#indexId").val();
	$.ajax({
        url: baseUrl + "/crtMark/updateEqAddressRel",
        type: "post",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
        data: {
        	id:id,
        	adress:address,
        	name:name,
        	remark:remark
        },
        dataType: "json",
        success: function (d) {
            if (d.success) {
                layer.msg(d.msg);
                $("#CRTTable").bootstrapTable('refresh');
                $("#editCRTInfo").modal("hide");
            } else {
                layer.msg(d.msg);
            }
        },
        error: function (data) {

        }
    });
});

//取消标点
function deleteOneEqument(row){
	var id = row.id
    layer.open({
        content: '确认取消标点？'
        , btn: ['确认', '取消']
        , yes: function (index) {
        	layer.close(index);
            $.ajax({
                url: baseUrl + "/crtMark/emptyPoint",
                type: "post",
                beforeSend: function(request){
                    request.setRequestHeader("Authorization","Bearer "+login.token);
                },
                data: {
                    id: id
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {                      
                        $('#imgDiv>#'+id).remove();
                        $("#CRTTable").bootstrapTable('refresh');
                    } else {
                        layer.msg(data.msg);
                    }
                },
                error: function (data) {

                }
            });
        }
        , cancel: function () {
            //右上角关闭回调
        }
    });
	
}

//////////////////////////////////////
//图片缩放
function bbimg(o) {
    var zoom = parseInt(o.style.scale, 10) || 100;
    zoom += event.wheelDelta / 12;

    if (zoom > 30)
        o.style.scale = zoom + '%';
    $('#imgDiv').css({ 'height': zoom / 100 * $('#imgDiv').attr('height') + 'px', 'width': zoom / 100 * $('#imgDiv').attr('width') + 'px' });
    $('.imgDiv>.dot').css({ 'top': _pointPercentY + '%', 'left': _pointPercentX + '%' }).attr({'top':_pointPercentY,'left':_pointPercentX});

    _crtWidth = $('#imgDiv').width();
    _crtHeight = $('#imgDiv').height();
}

//图片拖拽
$('#wrap_img ').udraggable({
    drag: function (e, ui) {
        $('#imgDiv').css({ 'width': _crtWidth, 'height': _crtHeight })

    }
});



//点位拖拽
//var dragone = 1;
function udraggableDot(dom, dotLogo) {
    var domY = $(dom).css('top');
    var domX = $(dom).css('left');
    
    var id = dom.attr('id');
    var pos = "";

    var reg = /px|%/;
    dom.udraggable({
        containment: 'parent',
        drag: function (e, ui) {
//        	 console.log(ui)
            pos = ui.position;
            var wrap_h = $('#imgDiv').height();
            var wrap_w = $('#imgDiv').width();
            $('#xvalue').val(pos.left / wrap_w * 100); //坐标赋值
            $('#yvalue').val(pos.top / wrap_h * 100);

            pos.left = pos.left / wrap_w * 100 + "%";
            pos.top = pos.top / wrap_h * 100 + "%";
            
            domY = pos.top.replace(reg, " ")*$('#imgDiv').attr('height')/100;
            domX = pos.left.replace(reg, " ")*$('#imgDiv').attr('width')/100;
            
                                       
        },
        stop: function (ui) {       	
        	console.log(pos.top,pos.left);  
        	$(dom).css({ 'top': pos.top, 'left': pos.left}).attr({'top': pos.top, 'left': pos.left}) 
            deitorDotSitInfo(id, dom, pos.top,pos.left, dotLogo);//保存点位坐标，并将原点位x,y传过去
        }
    });

    //重新给点位赋值，因为拖拽的插件问题，具体点位想x,y会被设置成px，为了方便缩放，所以要重新设置成%单位
    setDotPointAgain(dom, $(dom).css('top'), $(dom).css('left'), dotLogo);

}

function setDotPosition(obj) {
    var _sing = $(obj).attr('class');
    var _topArr = [], _leftArr = [];
    var _pos;
    var _w = $('#imgDiv').width();
    var _h = $('#imgDiv').height();
    var reg = /px|%/;
    for (var i = 0; i < $('#imgDiv>.choosed').length; i++) {
        _topArr.push($('#imgDiv>.choosed').eq(i).attr('top').replace(reg, " "));
        _leftArr.push($('#imgDiv>.choosed').eq(i).attr('left').replace(reg, " "));
    }
    if (_sing == 'up') {
        _pos = Math.min.apply(null, _topArr);
        $('#imgDiv>.choosed').css({ 'top': _pos + '%' }).attr({ 'top': _pos + '%' });
        for (var i = 0; i < $('#imgDiv>.choosed').length; i++) {
            var _id = $('#imgDiv>.choosed').eq(i).attr('id');
            var codeid = $('#imgDiv>.choosed').eq(i).attr('codeid');
            var _percentX = $('#imgDiv>.choosed').eq(i).attr('left');
            var type = $('#imgDiv>.choosed').eq(i).attr('classcode');
            console.log(_percentX, _pos+'%')
            savePositionDot(_id, _percentX, _pos+'%',type,codeid);
        }
    }
    else if (_sing == 'down') {
        _pos = Math.max.apply(null, _topArr);
        $('#imgDiv>.choosed').css({ 'top': _pos + '%' }).attr({ 'top': _pos + '%' });
        for (var i = 0; i < $('#imgDiv>.choosed').length; i++) {
            var _id = $('#imgDiv>.choosed').eq(i).attr('id');
            var codeid = $('#imgDiv>.choosed').eq(i).attr('codeid');
            var _percentX = $('#imgDiv>.choosed').eq(i).attr('left');
            var type = $('#imgDiv>.choosed').eq(i).attr('classcode')
            savePositionDot(_id, _percentX, _pos+'%',type,codeid);
        }
    }
    else if (_sing == 'left') {
        _pos = Math.min.apply(null, _leftArr);
        $('#imgDiv>.choosed').css({ 'left': _pos+'%' }).attr({ 'left': _pos+'%' });
        for (var i = 0; i < $('#imgDiv>.choosed').length; i++) {
            var _id = $('#imgDiv>.choosed').eq(i).attr('id');
            var codeid = $('#imgDiv>.choosed').eq(i).attr('codeid');
            var _percentY = $('#imgDiv>.choosed').eq(i).attr('top');
            var type = $('#imgDiv>.choosed').eq(i).attr('classcode')
            savePositionDot(_id, _pos+'%', _percentY,type,codeid);
        }
    }
    else if (_sing == 'right') {
        _pos = Math.max.apply(null, _leftArr);
        $('#imgDiv>.choosed').css({ 'left': _pos + '%' }).attr({ 'left': _pos + '%' });
        for (var i = 0; i < $('#imgDiv>.choosed').length; i++) {
            var _id = $('#imgDiv>.choosed').eq(i).attr('id');
            var codeid = $('#imgDiv>.choosed').eq(i).attr('codeid');
            var _percentY = $('#imgDiv>.choosed').eq(i).attr('top');
            var type = $('#imgDiv>.choosed').eq(i).attr('classcode')
            savePositionDot(_id, _pos+'%', _percentY,type,codeid);
        }
    }
}


function savePositionDot(id, xvalue, yvalue,type,codeid) {
	var imgHeight = $('#imgDiv').attr('height');
	var imgWidth = $('#imgDiv').attr('width')
    var data = {
        id: id,
        xaxis: xvalue,
        yaxis: yvalue,
        pointtype:codeid
    }
	$.ajax({
		url : baseUrl + "/crtMark/setEqPoint",
		type : "get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.success){
				

			}
		}
	})
}

//拖拽、保存点位
function deitorDotSitInfo(id, dom, domY, domX, dotLogo) {
    var wrap_h = $('#imgDiv').height();
    var wrap_w = $('#imgDiv').width();
    var codeid = dom.attr('codeid')
    var px_t = dom.attr('top');
    var px_l = dom.attr('left');
    //$(dom).css({ 'top': domY, 'left': domX}).attr({'top': domY, 'left': domX})
//    console.log(px_t,px_l);
    console.log(domY, domX)
    
    var xvalue = px_l / wrap_w * 100;
    var yvalue = px_t / wrap_h * 100;
    
    var data = {
            id: id,
            xaxis: px_l,
            yaxis: px_t,
            pointtype:codeid
        }
	$.ajax({
		url : baseUrl + "/crtMark/setEqPoint",
		type : "get",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.success){
				
				$("#CRTTable").bootstrapTable('refresh');
			}
		}
	})
}


function setDotPointAgain(dom, domTop, domLeft, dotLogo) {
    var wrap_h = $('#imgDiv').height();
    var wrap_w = $('#imgDiv').width();
    var reg = /px|%/;//去除px或%单位
    //console.log($('#imgDiv').attr('height'),domTop.replace(reg, " "),wrap_h)
    var px_t = domTop.replace(reg, " ");
    var px_l = domLeft.replace(reg, " ");
    
    var top = domTop.replace(reg, " ")/wrap_h*$('#imgDiv').attr('height');
    var left = domLeft.replace(reg, " ")/wrap_h*$('#imgDiv').attr('width');
    
    dom.css({ 'top': px_t / wrap_h * 100 + "%", 'left': px_l / wrap_w * 100 + "%", 'background-image': 'url(/image/crt/' + dotLogo + '.png)' })
}

//////////////////////////////////////

//ifHasconds("right_pointlist", "left_arealist", this); 左
//ifHasconds("left_arealist", "right_pointlist", this);右
function ifHasconds(_condClss, _resultClass, obj) {
 if (!$('.' + _condClss).hasClass('totoggle')) {
     if (_condClss == "left_arealist")
         $('.center_areapage').toggleClass('left_partscreen');
     else if (_condClss == "right_pointlist")
         $('.center_areapage').toggleClass('right_partscreen');
 }
 else {
     if (_condClss == "right_pointlist") {
         $('.center_areapage').toggleClass('right_partscreen');
         $('.center_areapage').toggleClass('left_partscreen');
     }
     $('.center_areapage').toggleClass('fullscreen');
 }
 $('.' + _resultClass).toggleClass('totoggle');
 $(obj).toggleClass('roate')
}


function domEvelement(){
    //////设备
	$('.rank_list').delegate('div','click',function(){
        $(this).addClass('active').siblings().removeClass('active');
        var pointtype = $(this).attr('code');
        var dotDom = $('#imgDiv .dot.choosed');
        var codeid = $(this).attr('codeid');
        
//        var wrap_h = $('#imgDiv').attr('height');
//        var wrap_w = $('#imgDiv').width('width');
        
        if(dotDom.length>0){
        	for(var i = 0 ; i < dotDom.length;i++){
        		var id = dotDom.eq(i).attr('id');
        	
                var top = dotDom.eq(i).attr('top');
                var left = dotDom.eq(i).attr('left');
                
                var cssTop = dotDom.eq(i).css('top');
                var cssLeft = dotDom.eq(i).css('left')
                
                var address = dotDom.eq(i).find('td.address').text();
                var eqsystemName = dotDom.eq(i).find('td.eqsystemName').text();
                var partcode = dotDom.eq(i).find('td.partcode').text();
          
                dotDom.eq(i).remove();
                
                var dom = $('<div class="dot choosed" codeid="'+codeid+'" id="' + id + '" top="'+top+'" left="'+left+'" style=" top:' + cssTop + ';left:' + cssLeft + ';display:block;" classCode="' + pointtype + '" /></div>');                    
     		    var tip = getCRTTips(address,eqsystemName,partcode);
     		    dom.html(tip);
     		    dom.appendTo($('#imgDiv'));
     		    var dotLogo = pointtype;
     		    deitorDotSitInfo(id, dom, cssTop, cssLeft, dotLogo)
     		    
     		    udraggableDot(dom, dotLogo);
     		    
        	}	
        }                  
    })

    /////点位选中
    $('#imgDiv').delegate('.dot', 'click', function () {
        setDotPointAgain($(this), $(this).css('top'), $(this).css('left'), $(this).attr('classcode'));
        if (window.event.ctrlKey) {
            $(this).toggleClass('choosed');
        }
    })

    /////点位对齐方式
    $('.wrap_btnOpt>div').click(function () {
        setDotPosition(this);    //设置点位
    })
    
            //////左滑右滑按钮
        $('.btn_toleft').click(function (e) {
            ifHasconds("right_pointlist", "left_arealist", this);
        })
        $('.btn_toright').click(function (e) {
            ifHasconds("left_arealist", "right_pointlist", this);
        })
}

function getPointVideoSelect(){
	$.ajax({
		type:"post",
		url:baseUrl + "/pointVideo/getPointVideoSelect",
        beforeSend: function(request){
            request.setRequestHeader("Authorization","Bearer "+login.token);
        },
		dataType:"json",
		success:function(data){
			if(data.success){
				var content = '<option value="">--请选择--</option>';
				for(var i=0;i<data.obj.length;i++){
					content += '<option value="'+data.obj[i].id+'">'+data.obj[i].name+'</option>';
				}
				$("#pointVideo").html(content);
			}
		},
		error: function (e) {
			
		}
	});
}
$("#searchBtn").click(function(){
	$('#CRTTable').bootstrapTable("refresh");
});
$("#searchUnit").click(function(){
	initTreeDataSource($("#searchUnitTxt").val());
});