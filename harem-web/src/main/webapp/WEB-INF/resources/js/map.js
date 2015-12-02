var local;
var getPoint= function(){
    var city = document.getElementById("txtCity").value;
    local.search(city);                 // 初始化地图,设置城市和地图级别。
    //启用滚轮放大缩小
}
function openBaiduMap(pointX,pointY,callback){
    var map = new BMap.Map("l-map");            // 创建Map实例
    map.centerAndZoom(new BMap.Point(pointX, pointY), 4);
    map.enableScrollWheelZoom();
    map.addEventListener("click",function(e){

        document.getElementById("txtPointX").value=e.point.lng;
        document.getElementById("txtPointY").value=e.point.lat;
    });
    local = new BMap.LocalSearch("全国", {
        renderOptions: {
            map: map,
            panel : "r-result",
            autoViewport: true,
            selectFirstResult: false
        }
    });

    layer.open({
        type : 1, title : '地图', closeBtn : false,
        area : [ '900px', '600px' ], shadeClose : false,
        content : $('#baiduMap'),
        btn : [ '确认', '取消' ],
        yes : function(index) {
            var data = {"pointX":$("#txtPointX").val(),"pointY":$("#txtPointY").val()};
            callback(data);
            layer.close(index);
        },
        cancel : function(index) {
            layer.close(index);
        }
    });
}