layui.use(['layer', 'form', 'element', 'upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;
    var $ = layui.$;

    //form表单初始化
    form.val("formFilter", {
        "partnerName": "贤心",
        "partnerType": "0"
    });

    var license = upload.render({
        elem: '#licenseBtn'
        , url: '/web/orgEnter'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#licenseImg').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });

    var legal = upload.render({
        elem: '#legalBtn'
        , url: '/web/orgEnter'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#legalImg').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });

    var agreement = upload.render({
        elem: '#agreementBtn'
        , url: '/web/orgEnter'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                //图片链接（base64）
                $('#agreementImg').attr('src', result);
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    })


});