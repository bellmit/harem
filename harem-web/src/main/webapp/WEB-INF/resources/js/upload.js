//不是插件
var fileUpload = function(id,type,callBack){//id：上传控件筛选器（‘#id’或‘。class’），type：1单文件；2多文件，callBack：回调函数接收data
    var oFiles = document.querySelector(id).files;
    // 实例化一个表单数据对象
    var formData = new FormData();
    // 遍历文件列表，插入到表单数据中
    for (var i = 0, file; file = oFiles[i]; i++) {
        // 文件名称，文件对象
        formData.append(file.name, file);
    }
    var xhr = new XMLHttpRequest();
    xhr.onload = function(data) {
        //执行回调
        callBack(data);

    };
    //xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", function(a,b,data){
        layer.message('提交出错',{icon:2});
    }, false);
    xhr.addEventListener("abort", function(a,b,data){
        layer.message('提交出错',{icon:2});
    }, false);
    if(type==1){
        xhr.open("POST", actionDefaultPath + "/upload/file", true);
    }else{
        xhr.open("POST",  actionDefaultPath + "/upload/files", true);
    }
    // 发送表单数据
    xhr.send(formData);
}