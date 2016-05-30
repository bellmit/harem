/*
 * 模块配置
 */
seajs.config({
    alias: { /*alias配置各模块别名，页面引入直接引入别名*/
        'jquery': 'jquery-1.11.0',
        'md5': 'module/md5.min',
        "ajaxform" : "plugin/jquery.form",
        "ajaxforms" : "plugin/jquery.forms",
        "layer" : "plugin/layer-v2.1/layer",
        "datetimepicker" : "plugin/datetimepicker.min",
        "validform" : "plugin/jquery.validform.js",
        'json': 'plugin/jquery.json',
        'upload': 'plugin/ajaxfileupload',
        "cookie" : "plugin/jquery.cookie.js",
        "json" : "plugin/jquery.json.js",
        "tmpl" : "plugin/jquery.tmpl.js",
        "html5shiv" : "ieonly/html5shiv.js",
        "respond" : "ieonly/respond.js",
        "excanvas" : "ieonly/excanvas.js",
        "zui" : "plugin/zui.min.js",
        "imgupload" : "module/md.imgupload.js",


        "public" : "module/md.public",
        'imgboxpack': 'plugin/jquery.imgbox.pack',
        //'addlable': 'module/mod.addlable',
        'lablelist': 'module/mod.lablelist',
        'addgood': 'module/mod.addgood',
        'lookgood': 'module/mod.lookgood',
        "addscenicspot" : "module/md.addscenicspot.js",
        'businesscheck' : 'module/md.businesscheck.js'
        
    },
    preload: ["jquery"],/*配置提取预加载模块，全局模块，所以页面都需要使用的模块*/
    debug: true
});


