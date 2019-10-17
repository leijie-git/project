﻿var version = '2.2.7';
var sp = location.pathname.lastIndexOf('/');
var ep = location.pathname.lastIndexOf('.html');
var curPage = sp < ep ? location.pathname.slice(sp + 1, ep) : 'index';
var enVersion = location.href.indexOf('-en.html') != -1
    // for example: http://ecomfe.github.io/echarts/doc/example/force2.html#-en
    || (location.pathname.indexOf('doc/example/') >= 0
        && location.hash
        && location.hash.indexOf('-en') != -1
    );

var activeClass = {};
var loc = {};
var forkWidth = 149;
curPage = curPage.replace('-en', '');
var isExample = false;
switch (curPage) {
    case 'index':
        activeClass[curPage] = 'active';
        loc[curPage] = '.';
        loc.feature = './doc';
        loc.example = './doc';
        loc.doc = './doc';
        loc.about = './doc';
        loc.changelog = './doc';
        loc.option = './doc';
        loc.spreadsheet = './doc';
        loc.start = './doc';
        loc.img = './doc';
        loc.schema = './docv';
        break;
    case 'feature':
    case 'example':
    case 'doc':
    case 'about':
    case 'changelog':
    case 'start':
        activeClass[curPage] = 'active';
        loc.index = '..';
        break;
    case 'option':
    case 'spreadsheet':
        forkWidth = 60;
        activeClass[curPage] = 'active';
        loc.index = '..';
        break;
    default:
        isExample = true;
        forkWidth = 60;
        activeClass['example'] = 'active';
        var extSub = location.href.indexOf('extension') != -1 ? '../' : '';
        loc.index = extSub + '../..';
        loc.feature = extSub + '../../doc';
        loc.example = extSub + '../../doc';
        loc.doc = extSub + '../../doc';
        loc.about = extSub + '../../doc';
        loc.changelog = extSub + '../../doc';
        loc.option = extSub + '../../doc';
        loc.spreadsheet = extSub + '../../doc';
        loc.start = extSub + '../../doc';
        loc.img = extSub + '../../doc';
        loc.schema = extSub + '../../docv';
        break;
}

// Those pages only exist in echarts-www project,
// but not in http://ecomfe.github.io/echarts/ and doc in echarts project.
if (location.href.indexOf('.baidu.com') < 0) {
    loc.option = 'http://echarts.baidu.com/doc';
    loc.spreadsheet = 'http://echarts.baidu.com/doc';
    loc.schema = 'http://echarts.baidu.com/docv';
}

$('#head')[0].innerHTML =
    '<div class="container">'
        + '<div class="navbar-header">'
          + '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">'
            + '<span class="sr-only">Toggle navigation</span>'
            + '<span class="icon-bar"></span>'
            + '<span class="icon-bar"></span>'
            + '<span class="icon-bar"></span>'
          + '</button>'
          + '<a class="navbar-brand" href="http://echarts.baidu.com/index'
          + (enVersion ? '-en' : '')
          + '.html">ECharts</a>'
        + '</div>'
        + '<a href="http://echarts.baidu.com" target="_blank">'
            + '<img id="fork-image" style="position:absolute;top:0;right:0;border:0;z-index:1000;width:' + forkWidth + 'px" src="' + (loc.img || '.') + '/asset/img/try3.png" alt="Fork me on GitHub">'
        + '</a>'
        + '<div class="navbar-collapse collapse" id="nav-wrap">'
          + '<ul class="nav navbar-nav navbar-right" id="nav" style="max-width:100%;">'
            + (enVersion
            ? ('<li class="' + (activeClass.index || '') + '"><a href="' + (loc.index || '.') + '/index-en.html">Index</a></li>'
                + '<li class="' + (activeClass.feature || '') + '"><a href="' + (loc.feature || '.') + '/feature-en.html">Feature</a></li>'
                + '<li class="' + (activeClass.example || '') + '"><a href="' + (loc.example || '.') + '/example-en.html">Example</a></li>'
                // + '<li class="' + (activeClass.doc || '') + '"><a href="' + (loc.doc || '.') + '/doc-en.html">API & Doc</a></li>'
            )
            : ('<li class="' + (activeClass.index || '') + '"><a href="' + (loc.index || '.') + '/index.html">棣栭〉</a></li>'
                + '<li class="' + (activeClass.feature || '') + '"><a href="' + (loc.feature || '.') + '/feature.html">鐗规€�</a></li>'
                + '<li class="' + (activeClass.example || '') + '"><a href="' + (loc.example || '.') + '/example.html">瀹炰緥</a></li>'
                // + '<li class="' + (activeClass.doc || '') + '"><a href="' + (loc.doc || '.') + '/doc.html">鏂囨。</a></li>'
            ))
            + '<li class="dropdown ' + (activeClass.doc || activeClass.option || '') + '">'
              + '<a href="#" class="dropdown-toggle" data-toggle="dropdown">' + (enVersion ? 'API & Doc' : '鏂囨。') + '<b class="caret"></b></a>'
              + '<ul class="dropdown-menu">'
                + '<li class=""><a href="' + (loc.doc || '.') + '/doc' + (enVersion ? '-en.html">Base Document' : '.html">鍙傝€冩墜鍐�') + '</a></li>'
                + '<li class=""><a href="' + (loc.option || '.') + '/option' + (enVersion ? '-en.html">Option Manual' : '.html">閰嶇疆椤规煡鎵惧伐鍏�') + '</a></li>'
              + '</ul>'
            + '</li>'
            + '<li class="dropdown ' + (activeClass.spreadsheet || '') + ' ec-secret-dropdown">'
              + '<a href="#" class="dropdown-toggle" data-toggle="dropdown">' + (enVersion ? 'Tool' : '宸ュ叿') + '<b class="caret"></b></a>'
              + '<ul class="dropdown-menu">'
                + '<li><a href="http://ecomfe.github.io/echarts-builder-web/">' + (enVersion ? 'Online Builder' : '鍦ㄧ嚎鏋勫缓宸ュ叿') + '</a></li>'
                + '<li><a href="' + (loc.example || '.') + '/example/theme.html">' + (enVersion ? 'Theme Preview' : '鐨偆涓婚棰勮') + '</a></li>'
                + '<li><a href="' + (loc.example || '.') + '/example/themeDesigner.html">' + (enVersion ? 'Theme Designer' : '鐨偆涓婚璁捐宸ュ叿') + '</a></li>'
                + '<li><a href="http://ecomfe.github.io/echarts-map-tool/">' + (enVersion ? 'Map Data Tool' : '鍦板浘鏁版嵁鐢熸垚宸ュ叿') + '</a></li>'
                + '<li><a href="' + (loc.spreadsheet || '.') + '/spreadsheet' + (enVersion ? '-en.html">Spreadsheet Data Tool' : '.html">琛ㄦ牸鏁版嵁杞崲宸ュ叿') + '</a></li>'
                + (enVersion ? '' : '<li><a target="_blank" href="http://study.163.com/course/courseMain.htm?courseId=1016007">瑙嗛鏁欑▼</a></li>')
                + (enVersion ? '' : '<li class="ec-secret-item"><a target="_blank" href="' + (loc.schema || '../docv') + '/entry/schema.html">鏂囨。缂栬緫宸ュ叿</a></li>')
              + '</ul>'
            + '</li>'
            /*
            + '<li class="dropdown">'
              + '<a href="#" class="dropdown-toggle" data-toggle="dropdown">鏁欏 <b class="caret"></b></a>'
              + '<ul class="dropdown-menu">'
                + '<li><a href="#">鍒濆鏁欑▼</a></li>'
                + '<li class="divider"></li>'
                + '<li class="dropdown-header">澶栭儴璧勬簮</li>'
                + '<li><a href="#"></a></li>'
                + '<li><a href="#"></a></li>'
              + '</ul>'
            + '</li>'
            */
            + '<li class="dropdown">'
              + '<a href="#" class="dropdown-toggle" data-toggle="dropdown">' + (enVersion ? 'Download' : '涓嬭浇') + '<b class="caret"></b></a>'
              + '<ul class="dropdown-menu">'
                + '<li><a href="http://echarts.baidu.com/build/echarts-' + version + '.zip"> echarts-' + version + ' (from Baidu)</a></li>'
                + '<li><a href="https://github.com/ecomfe/echarts/archive/' + version + '.zip"> echarts-' + version + ' (from GitHub)</a></li>'
                + '<li><a href="http://echarts.baidu.com/build/echarts-m-1.0.0.zip"> echarts-m-1.0.0 (beta)</a></li>'
                + '<li><a href="http://echarts.baidu.com/x/build/echarts-x-0.2.0.zip"> echarts-x-0.2.0 </a></li>'
                + '<li class="divider"></li>'
                + (enVersion
                    ? '<li><a href="http://ecomfe.github.io/echarts/doc/changelog-en.html">Changelog</a></li>'
                    : '<li><a href="http://echarts.baidu.com/doc/changelog.html">Changelog</a></li>'
                )
              + '</ul>'
            + '</li>'
            //+ '<li><a href="http://echarts.baidu.com/build/echarts-' + version + '.rar">涓嬭浇</a></li>'
            + (enVersion
               ? ('<li class="' + (activeClass.about || '') + '"><a href="' + (loc.about || '.') + '/about-en.html">About Us</a></li>')
               : ('<li class="' + (activeClass.about || '') + '"><a href="' + (loc.about || '.') + '/about.html">鍏充簬鎴戜滑</a></li>')
            )
            + '<li><a href="javascript:void()" onclick="changeVersion()">' + (enVersion ? '涓枃' : 'English') + '</a></li>'
          + '</ul>'
        + '</div><!--/.nav-collapse -->'
      + '</div>';

$('#head').on('click', '.ec-secret-dropdown', function (e) {
    $(e.currentTarget).find('.ec-secret-item')[e.altKey ? 'show' : 'hide']();
});

function back2Top() {
    $("body,html").animate({ scrollTop: 0 }, 1000);
    return false;
}

function changeVersion() {
    if (!isExample) {
        window.location = curPage + (enVersion ? '' : '-en') + '.html'
    }
    else {
        window.location = curPage + '.html' + (enVersion ? '' : '#-en');
        if (enVersion) {
            window.location.hash = window.location.hash.replace('-en', '');
        }
        window.location.reload();
    }
}

var $footer = $('#footer');
if ($footer.length) {
    $footer[0].style.marginTop = '50px';
    $footer[0].innerHTML =
         '<div class="container">'
            + '<div class="row" style="padding-bottom:20px;">'
                + '<div class="col-md-3">'
                    + '<p>' + (enVersion ? 'Link' : 'ECharts鍥㈤槦鍑哄搧') + '</p>'
                    + '<ul>'
                        + '<li><a href="http://tushuo.baidu.com/" target="_blank">Baidu 鍥捐</a></li>'
                        + '<li><a href="' + (enVersion ? "http://ecomfe.github.io/echarts-x" : 'http://echarts.baidu.com/x/doc') + '" target="_blank">ECharts-X</a></li>'
                        + '<li><a href="http://ecomfe.github.io/zrender/index.html" target="_blank">ZRender</a></li>'
                        + '<li><a href="https://github.com/pissang/qtek" target="_blank">QTEK</a></li>'
                    + '</ul>'
                + '</div>'
                + '<div class="col-md-3">'
                    + '<p>' + (enVersion ? 'More' : '鏇村') + '</p>'
                    + '<ul>'
                        + '<li><a href="https://github.com/ecomfe/echarts/blob/master/LICENSE.txt" target="_blank">License</a></li>'
                        + '<li><a href="http://echarts.baidu.com/doc/changelog' + (enVersion ? '-en' : '') + '.html" target="_blank">Changelog</a></li>'
                        + '<li><a href="http://efe.baidu.com" target="_blank">Baidu EFE</a></li>'
                        + '<li><a href="http://www.oschina.net/p/echarts" target="_blank">' + (enVersion ? 'Open Source China' : '寮€婧愪腑鍥�') + '</a></li>'
                    + '</ul>'
                + '</div>'
                + '<div class="col-md-3">'
                    + '<p>' + (enVersion ? 'Contact Us' : '鑱旂郴鎴戜滑') + '</p>'
                    + '<ul>'
                        + '<li><a href="mailto:echarts(a)baidu.com">echarts(a)baidu.com</a></li>'
                        + '<li><a href="https://github.com/ecomfe/echarts" target="_blank"> Github</a></li>'
                        + '<li><a href="http://weibo.com/echarts" target="_blank">Weibo</a></li>'
                    + '</ul>'
                + '</div>'
                + '<div class="col-md-3 flogo">'
                    + '<a href="javascript:void(0)" onclick="back2Top()" title="' + (enVersion ? 'Back to top' : '鍥炲埌椤堕儴') + '"><img src="' + (loc.img || '.') + '/asset/img/echarts-logo2.png" alt="ECharts"/></a>'
                + '</div>'
            + '</div>'
            + '<p class="pull-right"><a href="javascript:void(0)" onclick="back2Top()" >Back to top</a></p>'
            + '<p>&copy; 2015 <a href="http://www.baidu.com/" target="_blank">Baidu</a></p>'
        + '</div>';
}

if (document.location.href.indexOf('local') == -1) {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?4bad1df23f079e0d12bdbef5e65b072f";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
}

function fixFork() {
    var navMarginRight = 0;
    var bodyWidth = document.body.offsetWidth;
    var contnetWidth = $('#nav-wrap')[0].offsetWidth;
    if (bodyWidth < 1440) {
        navMarginRight = 150 - (bodyWidth - contnetWidth) / 2;
    }
    $('#nav')[0].style.marginRight = navMarginRight + 'px';
    $('#fork-image')[0].style.width = (document.body.offsetWidth < 768 ? 1 : forkWidth) + 'px';
};
fixFork();
$(window).on('resize', fixFork);