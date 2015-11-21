jQuery(function($) {
    $.supersized({
        slide_interval: 5000,
        transition: 1,
        transition_speed: 3000,
        performance: 1,
        min_width: 0,
        min_height: 0,
        vertical_center: 1,
        horizontal_center: 1,
        fit_always: 0,
        fit_portrait: 1,
        fit_landscape: 0,
        slide_links: 'blank',
        slides: [{
            image: '/resources/img/bj01.jpg'
        }, {
            image: '/resources/img/bj02.jpg'
        }, {
            image: '/resources/img/bj03.jpg'
        }, {
            image: '/resources/img/bj04.jpg'
        }]
    });
});

function refresh(obj){
    obj.src="/chkcode/gen?time="+ Date.now();
}

$(function () {
    $("#loginUsername").focus();//输入焦点
    $("#loginUsername").keydown(function (event) {
        if (event.which == "13") {//回车键,移动光标到密码框
            $("#loginPassword").focus();
        }
    });

    $("#loginPassword").keydown(function (event) {    loginVerify
        if (event.which == "13") {//回车键，用.ajax提交表单
            $("#loginVerify").focus();
        }
    });

    $("#loginVerify").keydown(function (event) {
        if (event.which == "13") {//回车键，用.ajax提交表单
            $("#loginSubmit").trigger("click");
        }
    });
    $("#loginSubmit").click(function () { //“登录”按钮单击事件
        //获取用户名称
        var strTxtName = encodeURI($("#loginUsername").val());
        //获取输入密码
        var strTxtPass = encodeURI($("#loginPassword").val());
        //获取验证码
        var strTxtVerify = encodeURI($("#loginVerify").val());
        //开始发送数据
        $.ajax
        ({ //请求登录处理页
            url: '/login', //登录处理页
            dataType: "json",
            //传送请求数据
            data: { username: strTxtName, password: strTxtPass ,verifyCode: strTxtVerify},
            success: function (data) { //登录成功后返回的数据
                //根据返回值进行状态显示
                if (data && data.status == 0) {//注意是True,不是true
                    //$(".clsShow").html("操作提示，登录成功！" + strValue);
                    alert("登陆成功");
                    location.href = 'www.baidu.com';
                }
                else {
                    //$("#divError").show().html("用户名或密码错误！" + strValue);
                    alert(data.message);
                }
            }
        })
    })
})
