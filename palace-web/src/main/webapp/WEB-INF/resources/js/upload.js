//不是插件
var fileUpload = function(id, type, callBack, fileSize) { //id：上传控件筛选器（‘#id’或‘。class’），type：1单文件；2多文件，callBack：回调函数接收data，fileSize：限制上传大小
    var oFiles = document.querySelector(id).files;
    uploadSenedFile(oFiles, type, callBack, fileSize);
}

var uploadSenedFile = function(files, type, callBack, fileSize) { //id：上传控件筛选器（‘#id’或‘。class’），type：1单文件；2多文件，callBack：回调函数接收data，fileSize：限制上传大小
    var oFiles = files;

    //默认大小为4M
    var maxSize = fileSize || 4 * 1024;
    // 实例化一个表单数据对象
    var formData = new FormData();
    // 遍历文件列表，插入到表单数据中
    for (var i = 0, file; file = oFiles[i]; i++) {
        if (file.size > maxSize * 1024) {
            layer.msg('图片不能大于' + formatSize(maxSize * 1024), { icon: 2 });
            return;
        }
        /*else if (file.size > 4 * 1024 * 1024) {
            layer.msg('图片不能大于4M', { icon: 2 });
            return;
        }*/

        var fileName = file.name;
        var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();
        if (fileType != "jpg" && fileType != "jpeg" && fileType != "png") {
            layer.msg('图片格式为JPG,JPEG,PNG', { icon: 2 });
            return;
        }
        // 文件名称，文件对象
        formData.append(fileName, file);
    }
    var xhr = new XMLHttpRequest();
    xhr.onload = function(data) {
        var data = JSON.parse(data.target.response);
        if (data && data.status == 200) {
            var errMessage = new Array();
            for (key in data.data) {
                if (!data.data[key] || data.data[key] === "null") {
                    delete data.data[key];
                    errMessage.push(key);
                }
            }
            if (errMessage.length > 0) {
                layer.msg("以下图片上传失败：" + errMessage.join(','), { icon: 2 });
            }
        }
        callBack(data);

    };
    //xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", function(a, b, data) {
        layer.msg('提交出错', { icon: 2 });
    }, false);
    xhr.addEventListener("abort", function(a, b, data) {
        layer.msg('提交出错', { icon: 2 });
    }, false);
    if (type == 1) {
        xhr.open("POST", uploadFile, true);
    } else {
        xhr.open("POST", uploadFiles, true);
    }
    // 发送表单数据
    xhr.send(formData);
}

//格式化文件大小，输出成带单位的字符串
function formatSize(size, units) {
    var unit;

    units = units || ['B', 'K', 'M', 'G', 'TB'];

    while ((unit = units.shift()) && size > 1024) {
        size = size / 1024;
    }

    return (unit === 'B' ? size : size.toFixed(0)) + unit;
}
