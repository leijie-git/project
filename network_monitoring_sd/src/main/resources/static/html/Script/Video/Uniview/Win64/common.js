/** 扩展JS原生类型的方法 */
(function(){
    /** 数字位数补全，超过位数的数字返回本身 */
    Number.prototype.toPad = function(n, radix){
        radix = radix || 10;
        n = n || 2;
        var num = this * 1;
        num = num.toString(radix);
        var len = num.length;
        while(len < n) {
            num = "0" + num;
            len++;
        }
        return num;
    };

    Number.prototype.toHEXPad = function(n){
        n = n || 2;
        var num = (this * 1).toString(16);
        var len = num.length;
        while(len < n) {
            num = "0" + num;
            len++;
        }
        num = "0x" + num;
        return num;
    };

    /** 数字转化为百分比 */
    Number.prototype.toPercent = function(n){
        if(0 !== n){
            n = n || 2;
        }

        return (Math.round(this * Math.pow(10, n + 2)) / Math.pow(10, n)).toFixed(n) + '%';
    };
    /*function:十进制的整数，获取转化为二进制后第N位上的 0或1,再将0转为flase,N从0开始，由低位到高位
    input:1:n
    output:true or false */
    Number.prototype.getFlagByBit = function(n) {
        var str2 = this.toString(2);
        var bit = 0;
        if (n > -1 && n < str2.length) {
            bit = str2.charAt(str2.length - n - 1);
        }
        var flag = (0 == bit) ? false : true;
        return flag;
    };

    /** 判断数组是否包含此元素 */
    Array.prototype.in_array = function(e){
        for(var i=0;i<this.length;i++) {
            if(this[i] == e){
                return true;
            }
        }
        return false;
    };

    /** 搜索数组 */
    Array.prototype.search = function(matching) {
        for (var i = 0, l = this.length; i < l; i++) {
            if (true === matching(this[i])) {
                return this[i];
            }
        }
        return null;
    };
    /*获取字符串占用字节数*/
    String.prototype.getBytesLength = function() {
        var totalLength = 0;
        var charCode;
        for (var i = 0; i < this.length; i++) {
            charCode = this.charCodeAt(i);
            if (charCode <= 0x007f) {
                totalLength++;
            } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
                totalLength += 2;
            } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
                totalLength += 3;
            } else {
                totalLength += 4;
            }
        }
        return totalLength;
    };
})();

/* Simple JavaScript Inheritance
 * By John Resig http://ejohn.org/
 * MIT Licensed.
 */
// Inspired by base2 and Prototype
(function() {
    var initializing = false,
        fnTest = /xyz/.test(function() {
            xyz;
        }) ? /\b_super\b/ : /.*/;

    // The base Class implementation (does nothing)
    this.Class = function() {};

    // Create a new Class that inherits from this class
    Class.extend = function(prop) {
        var _super = this.prototype;

        // Instantiate a base class (but only create the instance,
        // don't run the init constructor)
        initializing = true;
        var prototype = new this();
        initializing = false;

        // Copy the properties over onto the new prototype
        for (var name in prop) {
            // Check if we're overwriting an existing function
            prototype[name] = typeof prop[name] == "function" && typeof _super[name] == "function" && fnTest.test(prop[name]) ? (function(name, fn) {
                return function() {
                    var tmp = this._super;

                    // Add a new ._super() method that is the same method
                    // but on the super-class
                    this._super = _super[name];

                    // The method only need to be bound temporarily, so we
                    // remove it when we're done executing
                    var ret = fn.apply(this, arguments);
                    this._super = tmp;

                    return ret;
                };
            })(name, prop[name]) : prop[name];
        }

        // The dummy class constructor

        function Class() {
            // All construction is actually done in the init method
            if (!initializing && this.init) this.init.apply(this, arguments);
        }

        // Populate our constructed prototype object
        Class.prototype = prototype;

        // Enforce the constructor to be what we expect
        Class.prototype.constructor = Class;

        // And make this class extendable
        Class.extend = arguments.callee;

        return Class;
    };
})();

/**
 * 为元素添加遮盖
 * @example
 * $('#dialog').dialog('widget').dialogshim({id: 'dialogShim'});
 * $('#dialog').dialog('widget').dialogshim('show');
 * $('#dialog').dialog('widget').dialogshim('hide');
 */
(function($) {
    var closure = {
        cls: '',
        zIndexRelative: -1,
        shimid: 'data-shim'
    };
    var methods = {
        _getPos: function(el) {
            var css = {};
            css.top = el.css('top');
            css.left = el.css('left');
            css.width = el.outerWidth(true);
            css.height = el.outerHeight(true);
            return css;
        },
        _getShim: function(el) {
            var id = el.attr(closure.shimid);
            if (undefined === id) {
                return id;
            }
            return $('#' + id);
        },
        init: function(opts) {
            return this.each(function() {
                var $this = $(this);
                opts = $.extend({}, closure, opts);
                if (undefined === opts.id){
                    opts.id = this.id + '-shim';
                }
                var shim = methods._getShim($this);
                if (undefined === opts.renderTo){
                    opts.renderTo = $this.parent();
                } else {
                    opts.renderTo = $(opts.renderTo);
                }
                if (undefined === shim) {
                    opts.renderTo.append("<iframe id='" + opts.id + "' class='dialog-shim " + opts.cls + "' src='' frameborder='0' />");
                    $this.attr(closure.shimid, opts.id);
                    shim = $('#' + opts.id);
                }
                var pos = $this.css('position');
                if ('static' === pos || 'relative' === pos) {
                    pos = 'absolute';
                }
                shim.css({
                    position: pos,
                    'z-index': function() {
                        var base = parseInt($this.css('z-index'), 10);
                        base = isNaN(base) ? 0 : base;
                        return base + opts.zIndexRelative;
                    }
                });
            });
        },
        show: function() {
            return this.each(function() {
                var $this = $(this);
                var shim = methods._getShim($this);
                if (undefined === shim) {
                    return $this;
                }
                methods.resize.apply($this);
                shim.show();
            });
        },
        hide: function() {
            return this.each(function() {
                var $this = $(this);
                var shim = methods._getShim($this);
                if (undefined === shim) {
                    return $this;
                }
                shim.hide();
                return $this;
            });
        },
        resize: function(){
            return this.each(function() {
                var $this = $(this);
                var shim = methods._getShim($this);
                if (undefined === shim) {
                    return $this;
                }
                var css = methods._getPos($this);
                shim.css(css);
                return $this;
            });
        },
        destory: function() {
            return this.each(function() {
                var $this = $(this);
                var shim = methods._getShim($this);
                if (undefined === shim) {
                    return $this;
                }
                shim.remove();
                $this.removeAttr(closure.shimid);
                return $this;
            });
        }
    };
    $.fn.dialogshim = function(method) {
        if (methods[method]) {
            methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof(method) == 'object' || !method) {
            methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.dialogshim');
        }
        return this;
    };
})(jQuery);

(function($) {
    var closure = {
        cls: '',
        zIndexRelative: 1,
        fixedHeight: false,
        maskid: 'data-mask'
    };
    var methods = {
        _getMask: function(el) {
            var id = el.attr(closure.maskid);
            if (undefined === id) {
                return id;
            }
            return $('#' + id);
        },
        init: function(opts) {
            return this.each(function() {
                var $this = $(this);
                opts = $.extend({}, closure, opts);
                if (undefined === opts.id){
                    opts.id = this.id + '-mask';
                }
                var mask = methods._getMask($this);
                if (undefined === opts.renderTo){
                    opts.renderTo = $this;
                } else {
                    opts.renderTo = $(opts.renderTo);
                }
                if (undefined === mask) {
                    opts.renderTo.append("<iframe id='" + opts.id + "' class='dialog-shim white-opacity-3 " + opts.cls + "' src='' frameborder='0' />");
                    $this.attr(closure.maskid, opts.id);
                    mask = $('#' + opts.id);
                }
                mask.css({
                    'z-index': function() {
                        var base = parseInt($this.css('z-index'), 10);
                        base = isNaN(base) ? 0 : base;
                        return base + opts.zIndexRelative;
                    }
                });
                if (true === opts.fixedHeight){
                    mask.height($this.height());
                }
            });
        },
        show: function() {
            return this.each(function() {
                var $this = $(this);
                var mask = methods._getMask($this);
                if (undefined === mask) {
                    return $this;
                }
                mask.show();
            });
        },
        hide: function() {
            return this.each(function() {
                var $this = $(this);
                var mask = methods._getMask($this);
                if (undefined === mask) {
                    return $this;
                }
                mask.hide();
                return $this;
            });
        },
        destory: function() {
            return this.each(function() {
                var $this = $(this);
                var mask = methods._getMask($this);
                if (undefined === mask) {
                    return $this;
                }
                mask.remove();
                $this.removeAttr(closure.maskid);
                return $this;
            });
        }
    };
    $.fn.elemmask = function(method) {
        if (methods[method]) {
            methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof(method) == 'object' || !method) {
            methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.elemmask');
        }
        return this;
    };
})(jQuery);

(function($) {
    var defaults = {
        list: [],
        col: 8,
        collapsed: true,
        cls: '',
        itemCls: '',
        getText: function(item){
            return item;
        },
        getValue: function(item){
            return item;
        },
        getTitle: function(item){
            return item;
        },
        hasListen: true,
        handler: function(){},
        selected: -1
    };

    var tools = {
        initSelected: function(el, opts) {
            opts.selected = opts.list.length > 0 ? 0 : -1;
            tools.setInputVal(el, opts);
        },
        setInputVal: function(el, opts){
            var text = opts.selected === -1 ? '' : opts.getText(opts.list[opts.selected]);
            el.find('input').val(text);
        }
    };

    var methods = {
        init: function(opts) {
            return this.each(function() {
                var $this = $(this);
                if (undefined !== $this.data('data-selectbox')){
                    return $this;
                }

                opts = $.extend({}, defaults, opts);
                if (opts.list.length / opts.col > opts.minRow){
                    opts.col = opts.col / 2;
                }
                if (undefined === opts.id){
                    opts.id = $this.id + '-selectbox-table';
                }

                $this.addClass('uw-ui-selectbox ' + opts.cls);

                $this.append('<input type="text" readonly="readonly" /><button hidefocus="true"></button><div id="' + $this.id + '-selectbox-popup' + '"><table></table></div>');
                $this.find('div').dialogshim();
                $this.data('data-selectbox', opts);

                opts.handler = function(){
                    var opts = $this.data('data-selectbox');
                    $this.find('div').hide().dialogshim('hide');
                    opts.collapsed = true;
                    opts = $this.data('data-selectbox', opts);
                };

                tools.initSelected($this, opts);

                $this.on('click', 'input', function(e){
                    methods.popup.call($this);
                }).on('mouseup', 'button', function(e){
                    $this.find('input').trigger('click').focus();
                }).on('blur', 'input', opts.handler).on('mouseover', 'div,button', function(e){
                    var opts = $this.data('data-selectbox');
                    if (true === opts.hasListen){
                        $this.off('blur', 'input', opts.handler);
                        opts.hasListen = false;
                        opts = $this.data('data-selectbox', opts);
                    }
                }).on('mouseout', 'div,button', function(e){
                    var opts = $this.data('data-selectbox');
                    if (false === opts.hasListen){
                        $this.on('blur', 'input', opts.handler);
                        opts.hasListen = true;
                        opts = $this.data('data-selectbox', opts);
                    }
                }).on('click', 'td', function(e){
                    // TODO 单元格被点击后进行一下操作：改变选中的值，隐藏弹出框，将焦点设置到input上，触发change事件
                    var opts = $this.data('data-selectbox');
                    var td = $this.find('td');
                    var i = td.index(this);

                    var bChanged = (i !== opts.selected);

                    opts.selected = i;

                    $this.find('input').val(opts.getText(opts.list[i])).focus();

                    $this.find('div').hide().dialogshim('hide');
                    $this.on('blur', 'input', opts.handler);
                    opts.collapsed = true;

                    if (false === opts.hasListen){
                        $this.on('blur', 'input', opts.handler);
                        opts.hasListen = true;
                    }

                    $this.data('data-selectbox', opts);

                    if (bChanged){
                        $this.trigger('change');
                    }
                });
            });
        },
        val: function(){
            var v = arguments[0];
            if (undefined === v) {
                var opts = this.data('data-selectbox');
                return opts.selected >= 0 ? opts.getValue(opts.list[opts.selected]) : null;
            }
            return this.each(function() {
                var $this = $(this);
                var opts = $this.data('data-selectbox');
                var ipt = $this.find('input');

                if (false === opts.collapsed){
                    $this.find('div').hide().dialogshim('hide');
                }
                opts.collapsed = true;

                opts.selected = -1;
                if ('String' === arguments[0]) {
                    for (var i = 0, l = opts.list.length; i < l; i++) {
                        if (arguments[0] === opts.getValue(opts.list[i])) {
                            opts.selected = i;
                            break;
                        }
                    }
                } else if ('Number' === arguments[0] && arguments[0] >= 0 && arguments[0] >= opts.list.length) {
                    opts.selected = arguments[0];
                }
                $this.data('data-selectbox', opts);

                if (-1 === opts.selected){
                    ipt.val("");
                } else {
                    ipt.val(opts.getText(opts.list[opts.selected]));
                }
            });
        },
        popup: function(){
            return this.each(function() {
                var $this = $(this);
                var opts = $this.data('data-selectbox');
                if (true === opts.collapsed) {
                    var tbl = $this.find('table');

                    var tmpl = '{@each list as item, index}{@if index%col===0}<tr>{@/if}<td class="uw-ui-selectbox-item ${itemCls}" title="${item|renderSelectTitle}">${item|renderSelectItem}</td>{@if index%col===(columns-1)|| index === (list.length - 1)}</tr>{@/if}{@/each}';
                    opts.juicer.register('renderSelectItem', opts.getText);
                    opts.juicer.register('renderSelectTitle', opts.getTitle);
                    var html = opts.juicer(tmpl, opts);
                    opts.juicer.unregister('renderSelectItem');
                    opts.juicer.unregister('renderSelectTitle');
                    tbl.html(html);

                    if (-1 !== opts.selected) {
                        tbl.find('td').eq(opts.selected).addClass('selected');
                    }

                    // TODO 检测input框的位置和高度设置table显示的位置并将table显示出来
                    $this.find('div').show().dialogshim('show');

                } else {
                    $this.find('div').hide().dialogshim('hide');
                    $this.find('input').focus();
                }
                opts.collapsed = !opts.collapsed;
                $this.data('data-selectbox', opts);
            });
        },
        load: function(list){
            return this.each(function() {
                var $this = $(this);
                var opts = $this.data('data-selectbox');
                if (false === opts.collapsed){
                    $this.find('div').hide().dialogshim('hide');
                    $this.find('input').focus();
                }
                opts.collapsed = true;
                opts.list = list;

                tools.initSelected($this, opts);
                $this.data('data-selectbox', opts);
            });
        },
        get: function(){
            var opts = this.data('data-selectbox');
            if (-1 === opts.selected) {
                return [];
            }
            return opts.list[opts.selected];
        },
        destory: function() {
            return this.each(function() {
                var $this = $(this);
                $this.removeData('data-selectbox');
                $this.html('');
                $this.off();
            });
        }
    };
    $.fn.selectbox = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof(method) == 'object' || !method) {
            methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.selectbox');
        }
        return this;
    };
})(jQuery);

(function($) {
    var defaults = {
        cls: '',
        hasAllPick: false,
        title: '',
        name: '',
        list: [],
        getValue: function(it){
            if (undefined === it.stResCode){
                it.stResCode = new Utils.ResCode(it.stResourceCode);
            }
            return it.stResCode.u8ChlID;
        },
        getText: function(it){
            if (undefined === it.stResCode){
                it.stResCode = new Utils.ResCode(it.stResourceCode);
            }
            return it.stResCode.format();
        },
        getTitle: function(it){
            return it.szResourceName || '';
        },
        getName: function(it){
            return '';
        }
    };

    var tools = {
        trTmpl: '{@each list as it,index}<tr><td><label title="${it|renderLinkTableTitle}"><input type="checkbox" name="${it|renderLinkTableName}" value="${it|renderLinkTableValue}" /><span>&nbsp;&nbsp;${it|renderLinkTableText}&nbsp;&nbsp;</span></label></td></tr>{@/each}'
    };

    var methods = {
        init: function(opts) {
            return this.each(function() {
                var $this = $(this);

                opts = $.extend({}, defaults, opts);
                opts.juicer = opts.juicer || juicer;

                $this.addClass('uw-ui-linktable ' + opts.cls);

                var html = ['<div class="uw-ui-linktable-head ellipsis"><label>',
                    '<input type="checkbox" />',
                    '<span title="' + opts.title + '">&nbsp;&nbsp;', opts.title,
                    '&nbsp;&nbsp;</span></label></div><div class="uw-ui-linktable-body"><table><tbody></tbody></table></div>'
                ];
                // var a = ['<table><thead><tr><td><label>',
                //     '<input type="checkbox" />',
                //     '<span>&nbsp;&nbsp;', opts.title, '&nbsp;&nbsp;</span></label></td></tr></thead><tbody></tbody></table>'
                // ];
                if (false === opts.hasAllPick){
                    html.splice(1, 1);
                }
                $this.html(html.join(''));
                var list = opts.list;

                delete opts.list;
                $this.data('data-linktable', opts);

                methods.load.call($this, list);

                $this.on('change', '.uw-ui-linktable-head input', function(e){
                    var checked = $(this).prop('checked');
                    $this.find('table input').not(function(){
                        return undefined !== $(this).attr('disabled');
                    }).prop('checked', checked);
                }).on('change', 'table input', function(e){
                    var el = $this.find('table input');
                    var checked = (el.filter(':checked').length === el.length);
                    $this.find('.uw-ui-linktable-head input').prop('checked', checked);
                });
            });
        },
        load: function(list){
            return this.each(function() {
                var $this = $(this);
                var opts = $this.data('data-linktable');
                opts.juicer.register('renderLinkTableTitle', opts.getTitle);
                opts.juicer.register('renderLinkTableName', opts.getName);
                opts.juicer.register('renderLinkTableValue', opts.getValue);
                opts.juicer.register('renderLinkTableText', opts.getText);
                var html = opts.juicer(tools.trTmpl, {list: list});
                opts.juicer.unregister('renderLinkTableTitle');
                opts.juicer.unregister('renderLinkTableName');
                opts.juicer.unregister('renderLinkTableValue');
                opts.juicer.unregister('renderLinkTableText');
                $this.find('tbody').html(html);
            });
        },
        get: function(){
            var val = {};
            this.each(function(){
                var $this = $(this);
                var opts = $this.data('data-linktable');
                var name = opts.name.split(' ');
                for (var i = 0, l = name.length; i < l; i++){
                    val[name[i]] = [];
                    var el = $this.find('table input[name='+name[i]+']');
                    for (var j = 0, len = el.length; j < len; j++){
                        if (true === el.eq(j).prop('checked')){
                            val[name[i]].push(el.eq(j).val());
                        }
                    }
                }
            });
            return val;
        },
        set: function(val){
            return this.each(function() {
                var $this = $(this);
                var opts = $this.data('data-linktable');
                var el, checked, v;
                for (var i in val) {
                    el = $this.find('table input[name=' + i + ']').prop('checked', false);
                    for (var j = 0, l = val[i].length; j < l; j++) {
                        el.filter('[value=' + val[i][j] + ']').prop('checked', true);
                    }
                    el.trigger('change');
                }
            });
        },
        destory: function() {
            return this.each(function() {

            });
        }
    };
    $.fn.linktable = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof(method) == 'object' || !method) {
            methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.linktable');
        }
        return this;
    };
})(jQuery);

/**
 * 扩展jquery ui
 */
var Utils = (function($, Utils) {
    var closure = {
        flag: false
    };
    $.extend(Utils, {
        /**
         * 扩展jquery ui的dialog组件
         * $('#dialog').dialog({shim: true});
         */
        dialogExShim: function() {
            $.widget("ui.dialog", $.ui.dialog, {
                _init: function() {
                    if (true === this.options.shim) {
                        this._addShim();
                    }

                    if (this.options.autoOpen) {
                        this.open();
                    }
                },
                _addShim: function() {
                    this.uiDialog.dialogshim({
                        id: this.element.attr('id') + '-shim'
                    });

                    this.uiDialog.bind('dialogopen dialogdrag dialogdragstop dialogresize dialogresizestop', function() {
                        $(this).dialogshim('show');
                    }).bind('dialogclose', function() {
                        $(this).dialogshim('hide');
                    });
                }
            });
            return Utils;
        },
        /**
         * 扩展jquery ui的draggable组件
         * $('#drag').draggable({shim: true});
         */
        dragExShim: function() {
            $.widget("ui.draggable", $.ui.draggable, {
                _createHelper: function(event) {
                    var id = this.element.attr('id');
                    var o = this.options,
                        helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [event])) : (o.helper === "clone" ? this.element.clone().removeAttr("id") : this.element);
                    if (!helper.parents("body").length) {
                        helper.appendTo((o.appendTo === "parent" ? this.element[0].parentNode : o.appendTo));
                    }
                    if (helper[0] !== this.element[0] && !(/(fixed|absolute)/).test(helper.css("position"))) {
                        helper.css("position", "fixed");
                    }

                    if (true === this.options.shim) {
                        this._addShim(helper, id);
                    }
                    return helper;
                },
                _addShim: function(helper, id) {
                    helper.dialogshim({
                        id: id + '-drag-shim',
                        cls: 'ui-draggable-dragging-shim',
                        zIndexRelative: 999
                    });

                    // this.uiDraggable.bind('dragstart drag', function(e, ui) {
                    //     ui.helper.dialogshim('show');
                    // }).bind('dialogclose', function() {
                    //     $(this).dialogshim('hide');
                    // });
                },
                _clear: function() {
                    if (true === this.options.shim) {
                        this.helper.dialogshim('destory');
                    }
                    this.helper.removeClass("ui-draggable-dragging");
                    if (this.helper[0] !== this.element[0] && !this.cancelHelperRemoval) {
                        this.helper.remove();
                    }
                    this.helper = null;
                    this.cancelHelperRemoval = false;
                }
            });
            $.ui.plugin.add("draggable", "shim", {
                start: function(event, ui) {
                    ui.helper.dialogshim('show');
                },
                drag: function(event, ui) {
                    ui.helper.dialogshim('show');
                }
            });
            return Utils;
        },
                /**
         * 为jquery ui的dialog添加支持shim的功能
         * $('#dialog').dialog({shim: true});
         */
        addShimForDialog: function() {
            Utils.dialogExShim();
            return Utils;
        },
        uiExShim: function() {
            if (true !== closure.flag){
                Utils.dialogExShim().dragExShim();
                closure.flag = true;
            }
            return Utils;
        }
    });
    return Utils;
})(jQuery, Utils || {});

/**
 * Utils的公共方法
 * */
Utils = (function($, Utils) {
    $.extend(Utils, {
        //公共常量
        CUSTOM_LANG_ENUM: 31,   //客户自导入语言对应的枚举
        /**
         * 声明命名空间
         * @return {string} 命名空间
         * @example
         * //return window.Main.Live
         * Utils.ns('Main.Live');
         */
        ns: function() {
            var a = arguments,
                o = null,
                i, j, d;
            for (i = 0; i < a.length; i = i + 1) {
                d = a[i].split(".");
                o = window;
                for (j = 0; j < d.length; j = j + 1) {
                    o[d[j]] = o[d[j]] || {};
                    o = o[d[j]];
                }
            }
            return o;
        },
        /* 获取时间 */
        getSub: function(id) {
            var el;
            if (undefined === id) {
                el = $('body');
            } else {
                el = $("#" + id);
            }
            return el;
        },
        /**
         * 加载语言
         * @param  {object} lang 语言对象
         * @param  {object} el   语言加载区域，如果不是undefined，只替换此元素子节点（递归）的语言
         * @return {[type]}      [description]
         */
        loadLang: function(lang, el) {
            if (undefined === el) {
                el = $('body');
            }
            var replaceLang = function(key, fn) {
                return function(el) {
                    el.find('[' + key + ']').each(function() {
                        var _this = $(this);
                        var path = _this.attr(key).split('.');
                        var tmp = lang;
                        for (var i = 0; i < path.length; i++) {
                            tmp = tmp[path[i]];
                        }
                        fn(_this, tmp);
                        return true;
                    });
                };
            };
            replaceLang('data-text', function(e, l) {
                e.text(l).addClass("ellipsis").attr('title', l);
            })(el);
            replaceLang('data-title', function(e, l) {
                e.attr('title', l);
            })(el);
            return Utils;

        },
        /**
         * ajax初始化：设置ajax默认参数，设置默认错误解析方式
         * @return {Object}         返回Utils，提供链式操作
         */
        ajaxSetup: function(errorShow) {
            $.ajaxSetup({
                url: '/cgi-bin/main-cgi',
                cache: false,
                async: true,
                type: 'POST',
                dataType: 'json',
                error: function(xhr, textStatus, errorThrown){
                    if (true !== $.isFunction(errorShow)){
                        return true;
                    }
                    if (599 === xhr.status){
                        errorShow(xhr.responseJSON.code);
                    } else {
                        var code = xhr.status < 999 ? xhr.status : 999;
                        errorShow(code + 62000);
                    }
                    return true;
                }
            });

            return Utils;
        },
        ajaxError: function(xhr, errorShow){
            if (true !== $.isFunction(errorShow)) {
                return true;
            }
            if (599 === xhr.status) {
                errorShow(xhr.responseJSON.code);
            } else {
                var code = xhr.status < 999 ? xhr.status : 999;
                errorShow(code + 62000);
            }
        },
        /**
         * 创建自定义按钮
         * @param  {Object} view 自定义按钮属性
         * @return {String}      自定义按钮html字符串
         * @example
         * Utils.createBtn({
         *     id: 'saveBtn',
         *     renderTo: '#configBtnDiv',
         *     cls: 'save-btn',
         *     value: "保存"
         * });
         */
        createBtn: function(view){
            var tmpl = '<button id="${id}" class="custom-btn ${cls}" type="button">' +
                '<span class="custom-btn-left"></span>' +
                '<span class="custom-btn-center ellipsis" title=${value}>${value}</span>' +
                '<span class="custom-btn-right"></span>' +
            '</button>';
            var html = juicer(tmpl, view);
            if (view.renderTo){
                return $(html).appendTo($(view.renderTo));
            }
            return html;
        },
        /*通道编码解码函数
        *详见资源编码说明文档，举例1001030001000003显示用A3，即取数组下标为0和2的组合
        * @param  {str} 通道资源编码
        * @return {str} 返回解码后的字符串
        */
        chanCodeDecode: function(str) {
            var RES_CODE_LENGTH = 16;
            var RES_CODE_TYPE_JSON = {
                "10": "A",
                "11": "D"
            }; //10开头资源编码转为A通道，11开头转为D通道
            if (RES_CODE_LENGTH != str.length) //资源编码不是正常长度的返回空
                return "";
            var ResultStr = "";
            var ResCodeArray = [];
            for (var i = 0; i < RES_CODE_LENGTH / 2; i++) {
                ResCodeArray[i] = str.substr(i * 2, 2);
            }

            var DevType = ResCodeArray[0]; //前两位标识模拟通道or数字通道
            DevType = RES_CODE_TYPE_JSON[DevType];
            if ("undefined" == typeof DevType) return "";

            var MappingChlIndex = parseInt(ResCodeArray[2], 16); //5、6位标识A1还是A2,双16进制
            if (isNaN(MappingChlIndex)) return "";
            ResultStr = DevType + MappingChlIndex;
            return ResultStr;
        },
        min: function(){
            return arguments[0] <= arguments[1] ? arguments[0] : arguments[1];
        },
        max: function(){
            return arguments[0] >= arguments[1] ? arguments[0] : arguments[1];
        },
        log: function(){
            var a = $.extend([], arguments);
            try {
                console.log(a.join(' '));
            } catch (err) {}
            if (!true){
                return true;
            }
            try {
                top.Main.Plgn.obj.UMOSOCXWriteWebLog(a.join(' '));
                // console.log(a.join(', '));
            } catch (err) {}
            return true;
        },
        /**
         * 选中特定的radio
         * 入参示例：
         * radio：$("input[name='switch']");
         * value: 需选中的radio的value值
         *  */
        checkRadio: function(radio, value){
            for(var i = 0; i < radio.length; i++){
                if(value == radio.eq(i).val()){
                    radio.eq(i).prop("checked", true);
                    return true;
                }
            }
        },
        destory: function(obj) {
            for (var i in obj) {
                delete obj[i];
            }
            return undefined;
        },
        mappingCode: function(code){
            return parseInt(code, 10) + Static.ErrorCode.ERR_PLGN_ERR_BASE;
        },
        /**
         * 如果object对象存在相同的属性，将config的属性拷贝到obj对象。
         *  */
        apply: function(object, config) {
            for (var i in object) {
                if (undefined !== config[i])
                object[i] = config[i];
            }
            return object;
        },
        /**
         * 深度拷贝数据
         */
        deepExtend: function(newData, oldData){
            for (var i in oldData) {
                if (oldData[i] instanceof Array){
                    newData[i] = [];
                    arguments.callee(newData[i], oldData[i]);
                } else if (oldData[i] instanceof Function) {
                    newData[i] = oldData[i];
                } else if (oldData[i] instanceof Date) {
                    newData[i] = new Date(oldData[i]);
                }  else if (oldData[i] instanceof Object) {
                    newData[i] = {};
                    arguments.callee(newData[i], oldData[i]);
                } else {
                    newData[i] = oldData[i];
                }
            }
            return newData;
        },
        /**
         * 枚举值使用，类似于之前行业的处理
         * */
        getSDValue: function(SD, name){
            for (var i = 0; i < SD.length; i++) {
                if (name == SD[i][0]) {
                    return SD[i][1];
                }
            }
        },
        /**
         * mmGrid使用时，通过该方法处理自定义的数组数据
         * 返回值用于写入mmGrid的“cols”属性。
         * */
        getCols: function(arr){
            var cols = [];
            for (var i = 0; i < arr.length; i++) {
                var obj = {};
                obj.title = arr[i][0];
                obj.name = arr[i][1];
                obj.width = arr[i][2];
                obj.align = 'center';
                if (undefined !== arr[i][3]) {
                    obj.renderer = arr[i][3];
                }
                if (undefined !== arr[i][4]) {
                    obj.rendererTitle = arr[i][4];
                }
                cols.push(obj);
            }

            return cols;
        },
        /**
         * 根据能力过滤不支持的枚举返回支持的枚举对象
         * @param  {arr} 业务定义的枚举的数组
         * @example 数组成员为对象，对象中必须含有bitIndex字段
           [{"bitIndex":0,"valueIndex":0},{"bitIndex":1,"valueIndex":1}]
           bitIndex标识业务定义的按位枚举，valueIndex标识业务定义的实际需要下发的值得枚举(可选)，也可增加其他字段，一并返回。
         * @param  {int || arr} 能力值或数组，数组则以32位为一组
           @example
           [1]  or  1
         * @return {arr} 返回根据能力计算支持的枚举数组
           @example
           [{"bitIndex":0,"valueIndex":0},{"bitIndex":1,"valueIndex":1}]
         */
        filterEnumArr: function(EnumArr, value) {
            //不同iframe中创建的数组不会共享prototype属性，使用isArray判断是否为数组
            var isArray = function(arr){
                return "[object Array]" === Object.prototype.toString.call(arr);
            }

            var returnArr = [];
            var u32Arr = [];
            if (isArray(value)) {
                u32Arr = value;
            } else {
                u32Arr.push(value);
            }
            for (var i = 0, l = EnumArr.length; i < l; i++) {
                var u32ArrIndex = parseInt(i / 32, 10);
                if (undefined === u32Arr[u32ArrIndex]) {
                    continue;
                } else {
                    var bitIndex = EnumArr[i].bitIndex % 32;
                    var value = parseInt(u32Arr[u32ArrIndex], 10);
                    if (value.getFlagByBit(bitIndex)) {
                        returnArr.push(EnumArr[i]);
                    }
                }
            }
            return returnArr;
        },
        /**
         * 获取客户自导入的XML语言资源
         * @return {json} 返回XML转换后的json,如果异常，则返回undefined
         */
        getCustomLang: function() {
            var WEB_GET_CUSTOM_LANG = 655;
            var rootObj = undefined;
            $.ajax({
                url: '/cgi-bin/main-cgi',
                /* 同步执行且不使用缓存 */
                cache: false,
                async: false,
                type: 'GET',
                dataType: 'text',
                data: 'json={"cmd": ' + WEB_GET_CUSTOM_LANG + '}',
                success: function(result) {
                    try {
                        rootObj = $.xml2json(result);
                    } catch (err) {}
                    
                    return rootObj;
                }
            });
            return rootObj;
        },
        /**
         * 将客户自导入语言与英文语言合并
         * @param1  {json} 英文资源文件json,不能为undefined
         * @param2  {json} 自导入语言
         * @return  {json} 返回合并后的语言
         */
        extendCustomLang: function(lang_en, lang_custom) {
            if (undefined === lang_custom) {
                return lang_en;
            }
            //先不做英文校验，直接使用translation节点
            else {
                for (var i in lang_custom) {
                    var primaryNode = lang_custom[i];
                    for (var j in primaryNode) {
                        var secondaryNode = primaryNode[j];
                        if (undefined !== secondaryNode.translation) {
                            primaryNode[j] = secondaryNode.translation;
                        } else if (undefined !== secondaryNode.source) {
                            primaryNode[j] = secondaryNode.source;
                        } else {
                            primaryNode[j] = undefined;
                        }
                    }
                }
            }
            return $.extend(true, {}, lang_en, lang_custom);
        },
        /**
         * 将某选择器下的所有的元素都设置为这些元素的最大宽度并返回这个最大宽度数值。因为某些不明原因，需要加上一个像素。
         * @param {expr}  选择器条件表达式
         * @return {int}  最大宽度值
         */
        setMaxWidth: function(expr) {
            var arr = [];
            $(expr).each(function() {
                var width = $(this).width();
                arr.push(width);
            });
            if (arr.length > 0) {
                var maxWidth = Math.max.apply(null, arr);
                $(expr).css("width", maxWidth + 1);
                return maxWidth;
            }
            return 0;
        }
    });
    return Utils;
})(jQuery, Utils || {});

Utils = (function($, Utils) {
    var platform = window.navigator.platform.toLowerCase();
    var userAgent = window.navigator.userAgent.toLowerCase();
    var bMac = null !== platform.match('mac');
    return $.extend({}, Utils, {
        isWin32: function(){
            return platform === 'win32';
        },
        isWin: function(){
            return null !== platform.match('win');
        },
        isWin64: function(){
            return userAgent.indexOf("wow64") !== -1 || userAgent.indexOf("win64") !== -1;
        },
        isMac: function(){
            return bMac;
        },
        getSeparator: function(){
            return bMac ? '/' : '\\';
        }
    });
})(jQuery, Utils || {});

/**
 * 公共class定义
 * @param  {[type]} $     [description]
 * @param  {[type]} Utils [description]
 * @return {[type]}       [description]
 */
Utils = (function($, Utils) {
    /**
     * 播放控件
     * @example
     * var p = new Utils.Player({
     *     id: 'player',                    //加载的activex控件id
     *     container: 'playerContainer',    //控件/插件的父节点
     *     name: 'p',                       //实例对象的名称，用于设置napi上报事件的入参
     *     events: Live.EventMap,           //控件事件map
     *     stPortInfo: top.GLOBAL_INFO.stPortInfo     //用户登录信息
     * });
     */
	Utils.Player = Class.extend({
        clsid: '4E8893A4-8723-427A-81EA-72480BAB4501',
        application: 'npPluginSDK-plugin',
        eventname: 'plgnevent',
		id: "sdk_viewer",
        /**
         * 事件前缀，由于控件目前所有的事件上报都是一个，同时使用第一个参数作为事件类型，而此参数为数字
         * 使用数字作为自定义事件名来监听和触发目前看来无法实现，故将事件加上一个前缀
         * @type {String}
         */
        prefix: '__',
        maxWnd: 64, 
        //noCreateWnd: '',
        focusColor: parseInt('ffcc00', 16), 
        unfocusColor: parseInt('747d7d', 16), 
        backgColor: parseInt('373737', 16), 
        init: function(cfg) {
            /**
             * 控件事件载体，使用父节点
             * @type {Object}
             */
            this.eventObj = $('#' + cfg.container);

            if (undefined !== cfg.prefix){
                this.prefix = cfg.prefix;
            }
            if (undefined !== cfg.maxWnd){ 
                this.maxWnd = cfg.maxWnd; 
            } 
         /*   if (undefined !== cfg.noCreateWnd){ 
                this.noCreateWnd = cfg.noCreateWnd; 
            } */
            if (undefined !== cfg.focusColor){ 
                this.focusColor = parseInt(cfg.focusColor, 16); 
            } 
            if (undefined !== cfg.unfocusColor){ 
                this.unfocusColor = parseInt(cfg.unfocusColor, 16); 
            } 
            if (undefined !== cfg.backgColor){ 
                this.backgColor = parseInt(cfg.backgColor, 16); 
            }
            
            /* ie浏览器与非ie浏览器使用不同的方式来加载控件 */
            if (undefined !== window.ActiveXObject) {
                this.loadActivex(cfg);
            } else {
                this.loadNpapi(cfg);
            }
            /* 控件事件监听 */
            this.on(cfg);

            this.onInit(cfg);
            return this;
        },
        /**
         * 加载ActiveX，ie下使用
         * @param  {[type]} cfg [description]
         * @return {[type]}     [description]
         */
        loadActivex: function(cfg) {
            this.eventObj.html("<object classid='clsid:" + this.clsid + "' id='" + cfg.id + "' style='width: 100%;height: 100%' events='true'></object>");
            this.obj = document.getElementById(cfg.id);

            this.parseHostname(cfg);

            this.onActivexLoad(cfg);
            return this;
        },
        /**
         * 设置控件执行函数参数
         * @param  {[type]}     [description]
         * @return {[type]}     [description]
         */
        setParam: function(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10){
            var paramObj = [param1, param2, param3, param4, param5, param6, param7, param8, param9, param10];
            return top.JSON.stringify(paramObj);
        },
        /**
         * 调用控件函数执行函数
         * @param  {[type]}     [description]
         * @return {[type]}     [description]
         */
        execFunction: function(funcName, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10){
            var result = top.JSON.parse(this.obj.NetSDKExecFun(funcName, this.setParam(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)));
            
            if (result.code !== 0){
                // alert(funcName);
                return result.code;
            }
            else{
                // alert(funcName + ':' + result.result);
                return result.result;
            }
        },
        onActivexLoad: function(cfg){
            return this.addActivexBehaviors(cfg);
        },
        addActivexBehaviors: function(cfg){
            /* 控件初始化 */
            try {
            	if (undefined ===  this.noCreateWnd) { 
                    this.execFunction("NetSDKCreateWnd", this.maxWnd, this.focusColor, this.unfocusColor, this.backgColor); 
                } 
            	
                this.execFunction("NETDEV_Init");
            } catch (err) {
                // alert(err);
            }

            if (undefined === this.obj.attachEvent) {
                this.attachIE11Event(cfg.id, 'NetSDKRUNINFO', cfg.name + '.trigger(p1, p2, p3, p4);');
            } else {
                this.obj.attachEvent('NetSDKRUNINFO', function(_this) {
                    return function(p1, p2, p3, p4) {
                        _this.trigger(p1, p2, p3, p4);
                    };
                }(this));
            }
            return this;
        },
        attachIE11Event: function(id, eventname, fn) {
            var params = fn.match(/\(\)|\(.+\)/)[0];
            var functionName = fn.match(/^([^(\s]+)/)[1];
            var handler;
            try {
                handler = document.createElement("script");
                handler.setAttribute("for", id);
            } catch (ex) {
                handler = document.createElement('<script for="' + id + '">');
            }
            handler.event = eventname + params;
            handler.appendChild(document.createTextNode(functionName + params + ";"));
            this.eventObj.get(0).appendChild(handler);
            return this;
        },
        /**
         * 加载npapi插件，非ie下使用
         * @param  {[type]} cfg [description]
         * @return {[type]}        [description]
         */
        loadNpapi: function(cfg) {
            this.eventObj.html("<embed type='application/" + this.application + "' id='" + cfg.id + "' style='width: 100%;height: 100%' />");
            this.obj = document.getElementById(cfg.id);

            this.parseHostname(cfg);

            if (undefined === cfg.stPortInfo) {
                return this;
            }

            try {
            	 if (undefined ===  this.noCreateWnd) { 
                     this.execFunction("NetSDKCreateWnd", this.maxWnd, this.focusColor, this.unfocusColor, this.backgColor); 
                 } 
                this.execFunction("NETDEV_Init", cfg.stPortInfo.szDeviceIp);
                // 非ie浏览器需要设置插件事件上报接口函数
                this.obj.NetSDKNPSetEventFunc(cfg.name + '.trigger');
            } catch (err) {
                // alert(err);
            }
            return this;
        },
        parseHostname: function(cfg){},
        /**
         * 控件初始化完成事件处理
         * @return {[type]} [description]
         */
        onInit: function(cfg) {
            return this;
        },
        unInit: function(){
            if (undefined === this.obj){
                return this;
            }
            this.beforeUninit();
            try {
                this.execFunction("NetSDKUninitOCX");
            } catch (err) {}
            //this.eventObj.hide().get(0).removeChild(this.obj);
            this.eventObj.hide().children().remove();
            delete this.obj;
            return this;
        },
        beforeUninit: function(){
            return this;
        },
        /**
         * 触发控件上报事件，提供个naapi使用（npapi无法上报事件但是可以执行js代码）
         * @param  {[type]} e  [description]
         * @param  {[type]} p2 [description]
         * @param  {[type]} p3 [description]
         * @param  {[type]} p4 [description]
         * @return {[type]}    [description]
         */
        trigger: function(p1, p2, p3, p4) {
            this.eventObj.trigger(this.eventname, [p1, p2, p3, p4]);
            return this;
        },
        /**
         * 事件监听
         * @param  {[type]} cfg [description]
         */
        on: function(cfg) {
            if (undefined === cfg.events){
                return this;
            }
            var events = cfg.events;
            this.eventObj.on(this.eventname, this, function(e, p1, p2, p3, p4) {
                if (undefined === events || undefined === events[e.data.prefix + p1]){
                    return true;
                }
                events[e.data.prefix + p1](p2, p3, p4);
            });
            return this;
        },
        /**
         * 设置焦点到控件
         * @return {[type]} [description]
         */
        focus: function(){
            try {
                this.execFunction("NetSDKActiveWnd");
                // this.eventObj.get(0).focus();
            } catch (err){}
            return this;
        },
        check: function(flag){
            if (Static.ErrorCode.ERR_COMMON_SUCCEED !== flag) {
                throw flag;
            }
        }
    });

    return Utils;
})(jQuery, Utils || {});

