//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['layer', 'form'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var btnId;

    $("#loginSubmit").on("click", function () {
        btnId = "loginSubmit";
    });
    $("#logoutSubmit").on("click", function () {
        // btnId = "logoutSubmit";
        window.location.href = "/logout";
    });
    //监听form表单提交事件 防止页面跳转
    form.on('submit(formFilter)', function (data) {
        var url;
        if (btnId == "loginSubmit") {
            url = "/etc/login";
        } else {
            url = "/etc/logout";
        }
        $.ajax({
            url: url,
            type: 'post',
            data: $("#loginForm").serialize(),
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    if (btnId == "loginSubmit") {
                        sessionStorage.setItem("allinpayAuthority", data.data.roleIdList);
                        console.log(data.data.roleIdList);
                        window.location.href = "/web/index";
                    } else {
                        window.location.href = "/web/login";
                    }

                } else {
                    layer.alert(data.msg);
                }
            },
            error: function () {
                layer.alert("登录失败，请重试！");
            }
        });
        return false;
    });

    //展示图片信息 图片路径+图片名称
    function showImg(myData) {
        $('#licenseImg').attr('src', myData.license);
        $('#legalFrontImg').attr('src', myData.idFront);
        $('#legalBackImg').attr('src', myData.idBack);
        $('#agreementImg').attr('src', myData.agreement);
    }
});