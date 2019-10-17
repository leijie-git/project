$(function () {
	//主页面查询
	mainSearch();
    $(".btnReset").click(function () {
        emptySearch();
        $("#userTable").bootstrapTable("refresh");
        $("#searchContent").val('');
    });
    //查询按钮查询
    $(".btnSearch").click(function () {
        $("#userTable").bootstrapTable('destroy');
        mainSearch();
    });
    
    /////////////////上传单位
    $(".uploadAllXMHUnit").click(function(){
    	layer.open({
	        content: '确认所有未上传单位信息？',
	        btn: ['确认', '取消'],
	        yes: function () {
	            $.ajax({
	                url: baseUrl + "/upload/setOrg",
	                type: "get",
	                data:{
	                	company:"A"
	                },
	                dataType: "json",
	                beforeSend: function () {
	                	$(".uploadAllXMHUnit").attr("disabled","disabled");
	                	layer.msg("上传中...");
				    },
	                success: function (data) {
	                    if (data.success) {
	                        layer.msg("上传成功！");
	                        $("#userTable").bootstrapTable('refresh');
	                    } else {
	                        layer.msg("上传失败！");
	                    }
	                },
	                complete: function () {
				        $(".uploadAllXMHUnit").removeAttr("disabled");
				    },
	                error: function (data) {
	
	                }
	            });
	        },
	        cancel: function () {
	        }
    	});
    });
    $(".uploadAllAXUnit").click(function(){
    	layer.open({
    		content: '确认所有未上传单位信息？',
    		btn: ['确认', '取消'],
    		yes: function () {
    			$.ajax({
    				url: baseUrl + "/upload/setOrg",
    				type: "get",
    				data:{
    					company:"B"
    				},
	    			dataType: "json",
	    			beforeSend: function () {
	    				$(".uploadAllAXUnit").attr("disabled","disabled");
	    				layer.msg("上传中...");
	    			},
	    			success: function (data) {
	    				if (data.success) {
	    					layer.msg("上传成功！");
	    					$("#userTable").bootstrapTable('refresh');
	    				} else {
	    					layer.msg("上传失败！");
	    				}
	    			},
	    			complete: function () {
				        $(".uploadAllAXUnit").removeAttr("disabled");
				    },
	    			error: function (data) {
	    				
	    			}
    			});
    		},
    		cancel: function () {
    		}
    	});
    });
    
    /////////////////上传设备
    $(".uploadAllXMHDevice").click(function(){
    	layer.open({
    		content: '确认所有未上传设备信息？',
    		btn: ['确认', '取消'],
    		yes: function () {
    			$.ajax({
    				url: baseUrl + "/upload/setDevice",
    				type: "get",
    				data:{
    					unitID:$("#indexId").val()
    				},
    				dataType: "json",
    				beforeSend: function () {
    					layer.msg("上传中...");
    				},
    				success: function (data) {
    					if (data.success) {
    						layer.msg("上传成功！");
    						$("#netdeviceTable").bootstrapTable('refresh');
    					} else {
    						layer.msg("上传失败！");
    					}
    				},
    				error: function (data) {
    					
    				}
    			});
    		},
    		cancel: function () {
    		}
    	});
    });
    $(".uploadAllAXDevice").click(function(){
    	layer.open({
    		content: '确认所有未上传设备信息？',
    		btn: ['确认', '取消'],
    		yes: function () {
    			$.ajax({
    				url: baseUrl + "/upload/setDevice",
    				type: "get",
    				data:{
    					unitID:$("#indexId").val()
    				},
    				dataType: "json",
    				beforeSend: function () {
    					layer.msg("上传中...");
    				},
    				success: function (data) {
    					if (data.success) {
    						layer.msg("上传成功！");
    						$("#netdeviceTable").bootstrapTable('refresh');
    					} else {
    						layer.msg("上传失败！");
    					}
    				},
    				error: function (data) {
    					
    				}
    			});
    		},
    		cancel: function () {
    		}
    	});
    });
    
    /////////////////上传监控点
    $(".uploadAllXMHEquipment").click(function(){
    	layer.open({
    		content: '确认所有未上传监控点信息？',
    		btn: ['确认', '取消'],
    		yes: function () {
    			$.ajax({
    				url: baseUrl + "/upload/setPoint",
    				type: "get",
    				data:{
    					unitID:$("#indexId").val()
    				},
    				dataType: "json",
    				beforeSend: function () {
    					layer.msg("上传中...");
    				},
    				success: function (data) {
    					if (data.success) {
    						layer.msg("上传成功！");
    						$("#equipmentTable").bootstrapTable('refresh');
    					} else {
    						layer.msg("上传失败！");
    					}
    				},
    				error: function (data) {
    					
    				}
    			});
    		},
    		cancel: function () {
    		}
    	});
    });
    $(".uploadAllAXEquipment").click(function(){
    	layer.open({
    		content: '确认所有未上传监控点信息？',
    		btn: ['确认', '取消'],
    		yes: function () {
    			$.ajax({
    				url: baseUrl + "/upload/setPoint",
    				type: "get",
    				data:{
    					unitID:$("#indexId").val()
    				},
    				dataType: "json",
    				beforeSend: function () {
    					layer.msg("上传中...");
    				},
    				success: function (data) {
    					if (data.success) {
    						layer.msg("上传成功！");
    						$("#equipmentTable").bootstrapTable('refresh');
    					} else {
    						layer.msg("上传失败！");
    					}
    				},
    				error: function (data) {
    					
    				}
    			});
    		},
    		cancel: function () {
    		}
    	});
    });
    
    
});


$(".seniorSearch").click(function () {
	var clickNo = $(".jy_mainTile").attr("clickNo");
    if (clickNo == 0) {
        $(".jy_mainTile").attr("clickNo", "1");
        $(this).html("普通搜索");
        $(".jy_mainTile").show();
    } else {
        $(".jy_mainTile").attr("clickNo", "0");
        $(this).html("高级搜索");
        $(".jy_mainTile").hide();
        dataHeight = 658;
    }
    // emptySearch();
});
$(".ordinarySearch").click(function () {
    var id = $("#menuType").val();
    var value = $("#searchContent").val();
    $("#" + id).val(value);
    $("#userTable").bootstrapTable('destroy');
    mainSearch();
});
$("#menuType").change(function () {
    emptySearch();
    var id = $(this).val();
    if ($(this).val() == 'xmhStatus-Search') {
    	var html='<select id="searchContent" name="searchContent" class="form-control">'
    				+'<option value="">--请选择--</option>'
					+'<option value="0">未上传</option>'
					+'<option value="1">已上传</option>'
				+'</select>';
        $("#commonInput").html(html);
    }else if ($(this).val() == 'axStatus-Search') {
    	var html='<select id="searchContent" name="searchContent" class="form-control">'
		    		+'<option value="">--请选择--</option>'
					+'<option value="0">未上传</option>'
					+'<option value="1">已上传</option>'
				+'</select>';
        $("#commonInput").html(html);
    }else {
    	if (id == "") {
    		$("#userTable").bootstrapTable('refresh');
    	}
    	if(!$('#searchContent').hasClass('showInput')){
    		$("#commonInput").html('<input type="text" class="form-control showInput" name="searchContent" id="searchContent">');
    	}
    }
});


function emptySearch() {
    $("#unitName-Search").val('');
    $("#xmhStatus-Search").val('');
    $("#axStatus-Search").val('');
}


//主页面查询
function mainSearch() {
    $('#userTable').bootstrapTable({
        url: baseUrl + '/uploadUnitInfo/getAllUnitInfo',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
            	pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                unitname: $("#unitName-Search").val(),
                isUploadA:$("#xmhStatus-Search").val(),
                isUploadB:$("#axStatus-Search").val()
            };
            $("#pageSize").val(this.pageSize);
            $("#pageNumber").val(this.pageNumber);
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
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "codeid",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [{
            field: 'selectItem',
            checkbox: true
        },
            {
                field: 'unitname',
                title: '名称'
            },
            {
            	field: 'unitcode',
            	title: '单位编码'
            },
            {
            	field: 'unitaddress',
            	title: '单位地址',
            	formatter: function (value, row, index) {
    				if (value == null) {
    					return "";
    				}
    				return '<p title="'+value+'" style="width: 110px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" >' + value + '</p>';
    			}
            },
            {
            	field: 'phone',
            	title: '电话'
            },
            {
            	field: 'isuploada',
            	title: '新门海',
            	formatter: function (value, row, index) {
            		if(row.isuploada==1){
            			return "<font color='green'>已上传</font>";
					}else{
						return "<font color='red'>未上传</font>";
					}
                }
            },
            {
            	field: 'isuploadb',
            	title: '安讯',
            	formatter: function (value, row, index) {
            		if(row.isuploadb==1){
            			return "<font color='green'>已上传</font>";
					}else{
						return "<font color='red'>未上传</font>";
					}
                }
            },
            {
                field: '',
                title: '操作',
                width:'430px',
                formatter: function (value, row, index) {
                    var str = "";
                	str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadUnitXMH"><i class="fa fa-upload"></i>&nbsp;上传单位至新门海</button>&nbsp;';
            		str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadUnitAX"><i class="fa fa-upload"></i>&nbsp;上传单位至安讯</button>&nbsp;';
            		if(row.isuploada==1 || row.isuploadb==1){
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadDevice"><i class="fa fa-upload"></i>&nbsp;上传监控点</button>&nbsp;';
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadEquipment"><i class="fa fa-upload"></i>&nbsp;上传设备</button>&nbsp;';
            		}else{
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadDevice" disabled="disbaled"><i class="fa fa-upload"></i>&nbsp;上传监控点</button>&nbsp;';
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadEquipment" disabled="disbaled"><i class="fa fa-upload"></i>&nbsp;上传设备</button>&nbsp;';
            		}
                    return str;
                },
                events: {
                    'click .uploadUnitXMH': function (e, value, row, index) {
                    	uploadUnitXMH(row);
                    },
                    'click .uploadUnitAX': function (e, value, row, index) {
                        uploadUnitAX(row);
                    },
                    'click .uploadDevice': function (e, value, row, index) {
                        uploadDevice(row);
                    },
                    'click .uploadEquipment': function (e, value, row, index) {
                    	uploadEquipment(row);
                    }
                }
            }
        ]
    });
}

function uploadUnitXMH(_this){
	 $.ajax({
        url: baseUrl + "/upload/setOrg",
        type: "get",
        data:{
        	unitID:_this.id,
        	company:"A"
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                layer.msg("上传成功！");
                $("#userTable").bootstrapTable('refresh');
            } else {
                layer.msg(data.msg);
            }
        },
        error: function (data) {

        }
    });	
}
function uploadUnitAX(_this){
	$.ajax({
		url: baseUrl + "/upload/setOrg",
		type: "get",
		data:{
			unitID:_this.id,
			company:"B"
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				layer.msg("上传成功！");
				$("#userTable").bootstrapTable('refresh');
			} else {
				layer.msg(data.msg);
			}
		},
		error: function (data) {
			
		}
	});	
}

function uploadDevice(row){
	$("#indexId").val(row.id);
	$("#netdevice").modal("show");
	$("#netdevice .modal-title").html(row.unitname);
	$('#netdeviceTable').bootstrapTable("destroy");
	deviceSearch(row);
}

function uploadEquipment(row){
	$("#indexId").val(row.id);
	$("#equipment").modal("show");
	$("#equipment .modal-title").html(row.unitname);
	$('#equipmentTable').bootstrapTable("destroy");
	equipmentSearch(row);
}
function deviceSearch(_this){
	$('#netdeviceTable').bootstrapTable({
        url: baseUrl + '/uploadUnitInfo/getUploadDevice',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParamsType: "undefined",   //排序方式
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
            	pageNumber: this.pageNumber,
                pageSize: this.pageSize,
                unitid : _this.id
            };
            $("#pageSize").val(this.pageSize);
            $("#pageNumber").val(this.pageNumber);
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
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,
        // height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "codeid",                     //每一行的唯一标识，一般为主键列
        //    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [{
            field: 'selectItem',
            checkbox: true
        },
            {
                field: 'name',
                title: '设备名称'
            },
            {
                field: 'ownercode',
                title: '编码'
            },
            {
                field: 'deviceindex',
                title: '设备索引'
            },
            {
                field: 'deviceno',
                title: '设备编号'
            },
            {
            	field: 'isuploada',
            	title: '新门海',
            	formatter: function (value, row, index) {
            		if(row.isuploada==1){
            			return "<font color='green'>已上传</font>";
					}else{
						return "<font color='red'>未上传</font>";
					}
                }
            },
            {
            	field: 'isuploadb',
            	title: '安讯',
            	formatter: function (value, row, index) {
            		if(row.isuploadb==1){
            			return "<font color='green'>已上传</font>";
					}else{
						return "<font color='red'>未上传</font>";
					}
                }
            },
            {
                field: '',
                title: '操作',
                formatter: function (value, row, index) {
                    var str = "";
            		if(_this.isuploada==1){
        				str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadXMHDevice"><i class="fa fa-upload"></i>&nbsp;上传监控点至新门海</button>&nbsp;';
            		}else{
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadXMHDevice" disabled="disabled"><i class="fa fa-upload"></i>&nbsp;上传监控点至新门海</button>&nbsp;';
            		}
            		if(_this.isuploadb==1){
        				str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadAXDevice"><i class="fa fa-upload"></i>&nbsp;上传设备至安讯</button>&nbsp;';
            		}else{
            			str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadAXDevice" disabled="disabled"><i class="fa fa-upload"></i>&nbsp;上传设备至安讯</button>&nbsp;';
            		}
                    return str;
                },
                events: {
                    'click .uploadXMHDevice': function (e, value, row, index) {
                    	uploadXMHDevice(row);
                    },
                    'click .uploadAXDevice': function (e, value, row, index) {
                        uploadAXDevice(row);
                    }
                }
            }
        ]
    });
}

function uploadXMHDevice(_this){
	$.ajax({
		url: baseUrl + "/upload/setDevice",
		type: "get",
		data:{
			companyName:"A",
			netDeviceID:_this.id
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				layer.msg("上传成功！");
				$("#netdeviceTable").bootstrapTable('refresh');
			} else {
				layer.msg(data.msg);
			}
		},
		error: function (data) {
			
		}
	});
}
function uploadAXDevice(_this){
	$.ajax({
		url: baseUrl + "/upload/setDevice",
		type: "get",
		data:{
			companyName:"B",
			netDeviceID:_this.id
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				layer.msg("上传成功！");
				$("#netdeviceTable").bootstrapTable('refresh');
			} else {
				layer.msg(data.msg);
			}
		},
		error: function (data) {
			
		}
	});
}

function equipmentSearch(_this){
	$('#equipmentTable').bootstrapTable({
		url: baseUrl + '/uploadUnitInfo/getUploadEquipment',         //请求后台的URL（*）
		method: 'get',                      //请求方式（*）
		contentType: 'application/x-www-form-urlencoded',
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true,                   //是否显示分页（*）
		sortable: false,                     //是否启用排序
		sortOrder: "asc",                   //排序方式
		queryParamsType: "undefined",   //排序方式
		queryParams: function queryParams(params) {   //设置查询参数
			var param = {
					pageNumber: this.pageNumber,
					pageSize: this.pageSize,
					unitid : _this.id
			};
			$("#pageSize").val(this.pageSize);
			$("#pageNumber").val(this.pageNumber);
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
		minimumCountColumns: 2,             //最少允许的列数
		clickToSelect: true,                //是否启用点击选中行
		singleSelect: false,
		// height: 570,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId: "codeid",                     //每一行的唯一标识，一般为主键列
		//    showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
		cardView: false,                    //是否显示详细视图
		detailView: false,                   //是否显示父子表
		columns: [{
			field: 'selectItem',
			checkbox: true
		},
		{
			field: 'name',
			title: '名称'
		},
		{
			field: 'iotype',
			title: '信号类型',
			formatter: function (value, row, index) {
				if(row.iotype==1){
					return "模拟量";
				}
				if(row.iotype==2){
					return "数字量";
				}
				if(row.iotype==3){
					return "输出";
				}else{
					return '';
				}
			}
		},
		{
			field: 'ioport',
			title: '端口值'
		},
		{
			field: 'partcode',
			title: '部位号'
		},
		{
			field: 'adress',
			title: '真实地址'
		},
		{
			field: 'isuploada',
			title: '新门海',
			formatter: function (value, row, index) {
				if(row.isuploada==1){
					return "<font color='green'>已上传</font>";
				}else{
					return "<font color='red'>未上传</font>";
				}
			}
		},
		/*{
			field: 'isuploadb',
			title: '安讯',
			formatter: function (value, row, index) {
				if(row.isuploadb==1){
					return "<font color='green'>已上传</font>";
				}else{
					return "<font color='red'>未上传</font>";
				}
			}
		},*/
		{
			field: '',
			title: '操作',
			formatter: function (value, row, index) {
				var str = "";
				if(_this.isuploada==1){
					str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadXMHEquipment"><i class="fa fa-upload"></i>&nbsp;上传设备至新门海</button>&nbsp;';
				}else{
					str += '<button type="button" class="btn btn-primary btn-xs cBtn-main uploadXMHEquipment" disabled="disabled"><i class="fa fa-upload"></i>&nbsp;上传设备至新门海</button>&nbsp;';
				}
				return str;
			},
			events: {
				'click .uploadXMHEquipment': function (e, value, row, index) {
					uploadXMHEquipment(row);
				}
			}
		}
		]
	});
}

function uploadXMHEquipment(_this){
    $.ajax({
        url: baseUrl + "/upload/setPoint",
        type: "get",
        data:{
        	companyName:"A",
        	pointID:_this.id,
        	isFire:_this.isRTU==1?0:1
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                layer.msg("上传成功！");
                $("#equipmentTable").bootstrapTable('refresh');
            } else {
                layer.msg(data.msg);
            }
        },
        error: function (data) {

        }
    });
}
function uploadAXEquipment(_this){
	$.ajax({
		url: baseUrl + "/upload/setPoint",
		type: "get",
		data:{
			companyName:"B",
			pointID:_this.id,
			isFire:_this.isRTU==1?0:1
		},
		dataType: "json",
		success: function (data) {
			if (data.success) {
				layer.msg("上传成功！");
				$("#equipmentTable").bootstrapTable('refresh');
			} else {
				layer.msg(data.msg);
			}
		},
		error: function (data) {
			
		}
	});
}

