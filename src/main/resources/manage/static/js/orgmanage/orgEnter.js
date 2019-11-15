layui.use('jquery', function () {
    var $ = layui.jquery;
    //查询父级机构
    $.ajax({
        url: '/manage/query/org/normalOrgList',
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (data) {
            if (data.code == "00000") {
                var myData = data.data;
                $("#orgSelector").find("select").html("<option value=''>请选择</option><option value='0'>暂无</option>");
                for (var i = 0; i < myData.length; i++) {
                    $("#orgSelector").find("select").append("<option value=" + myData[i].partnerId + ">" + myData[i].partnerName + "</option>");
                }
            } else {
                layer.alert(data.msg);
            }
        },
        error: function () {
            layer.alert("操作失败，请重试！");
        }
    });
});
layui.use(['layer', 'form', 'element', 'upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;
    var $ = layui.$;
    var btnId;

    upload.render({
        elem: '#licenseBtn',
        field: "licenseFile",
        accept: "images",
        auto: false,
        choose: function (obj) {
            //预读选择的文件，不支持ie8
            obj.preview(function (index, file, result) {
                //图片链接（base64）
                dealImg(file, "licenseImg", "licenseFile", result);
            });
        }
    });

    upload.render({
        elem: '#legalFrontBtn',
        field: "legalFront",
        accept: "images",
        auto: false,
        choose: function (obj) {
            //预读选择的文件，不支持ie8
            obj.preview(function (index, file, result) {
                dealImg(file, "legalFrontImg", "legalFront", result);
            });
        }
    });

    upload.render({
        elem: '#legalBackBtn',
        field: "legalBack",
        accept: "images",
        auto: false,
        choose: function (obj) {
            //预读选择的文件，不支持ie8
            obj.preview(function (index, file, result) {
                dealImg(file, "legalBackImg", "legalBack", result);
            });
        }
    });

    //添加
    $("#addSubmit").on("click", function () {
        checkParamsForAdd();
        btnId = "addSubmit";
    });

    //提交审核
    $("#sendAuditSubmit").on("click", function () {
        checkParamsForSendAudit();
        btnId = "sendAuditSubmit";
    });

    $("#agreementId").on("change", function () {
        var fileType = $("#agreementId").val().substr($("#agreementId").val().lastIndexOf(".") + 1);
        if (fileType != "pdf") {
            layer.alert("只支持pdf格式文件");
            $("#agreementId").val("");
        }
    });

    //表单参数验证
    function checkParamsForAdd() {
        //机构名称、机构类型、营业执照编号、机构地址 使用框架做非空校验做非空校验
        var $parentId = $("select[name='parentId']");
        var $legalName = $("input[name='legalName']");
        var $contactor = $("input[name='contactor']");
        var $saler = $("input[name='saler']");
        var $legalId = $("input[name='legalId']");
        var $legalPhone = $("input[name='legalPhone']");
        var $contactPhone = $("input[name='contactPhone']");
        var $license = $("input[name='licenseFile']");
        var $legalFront = $("input[name='legalFront']");
        var $legalBack = $("input[name='legalBack']");
        var $agreement = $("input[name='agreementFile']");
        //清除基础校验信息
        $parentId.attr("lay-verify", "");
        $legalName.attr("lay-verify", "");
        $contactor.attr("lay-verify", "");
        $saler.attr("lay-verify", "");
        $legalId.attr("lay-verify", "");
        $legalPhone.attr("lay-verify", "");
        $contactPhone.attr("lay-verify", "");
        //清除图片信息
        $license.attr("lay-verify", "");
        $legalFront.attr("lay-verify", "");
        $legalBack.attr("lay-verify", "");
        $agreement.attr("lay-verify", "");
        //非空项增加校验
        verify($legalId, "lay-verify", "identity");
        verify($legalPhone, "lay-verify", "phone");
        verify($contactPhone, "lay-verify", "phone");
    }

    function checkParamsForSendAudit() {
        //基础字段校验
        var $parentId = $("select[name='parentId']");
        var $legalName = $("input[name='legalName']");
        var $contactor = $("input[name='contactor']");
        var $saler = $("input[name='saler']");
        var $legalId = $("input[name='legalId']");
        var $legalPhone = $("input[name='legalPhone']");
        var $contactPhone = $("input[name='contactPhone']");
        var $license = $("input[name='licenseFile']");
        var $legalFront = $("input[name='legalFront']");
        var $legalBack = $("input[name='legalBack']");
        var $agreement = $("input[name='agreementFile']");
        $parentId.attr("lay-verify", "required");
        $legalName.attr("lay-verify", "required");
        $contactor.attr("lay-verify", "required");
        $saler.attr("lay-verify", "required");
        $legalId.attr("lay-verify", "identity");
        $legalPhone.attr("lay-verify", "phone");
        $contactPhone.attr("lay-verify", "phone");
        //图片信息
        $license.attr("lay-verify", "required").attr("lay-reqText", "请上传营业执照图片");
        $legalFront.attr("lay-verify", "required").attr("lay-reqText", "请上传法人身份证正面图片");
        $legalBack.attr("lay-verify", "required").attr("lay-reqText", "请上传法人身份证反面图片");
        $agreement.attr("lay-verify", "required").attr("lay-reqText", "请上传协议文件");
    }

    //给指定的$对象赋值属性
    function verify(dom, attr, val) {
        if ($.trim(dom.val())) {
            dom.attr(attr, val);
        } else {
            dom.attr(attr, "");
        }
    }

    //监听form表单提交事件 防止页面跳转
    form.on('submit(formFilter)', function (data) {
        var formData = new FormData(document.getElementById("addForm"));
        var url;
        if (btnId == "addSubmit") {
            url = "/manage/org/enter/add";
        } else if (btnId == "sendAuditSubmit") {
            url = "/manage/org/enter/sendAudit";
        } else {
            layer.alert("系统异常");
            return;
        }
        $.ajax({
            url: url,
            type: 'post',
            contentType: false,
            processData: false,
            data: formData,
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("操作成功", function () {
                        layer.close(index);
                        window.location.href = "/manage/orgEnter";
                    });
                } else {
                    layer.alert(data.msg);
                }
            },
            error: function () {
                layer.alert("操作失败，请重试！");
            }
        });
        return false;
    });

    function dealImg(file, imgId, fileName, result) {
        if (file.size > 0) {
            $('#' + imgId).attr('src', result);
        } else {
            $("input[name=" + fileName + "]").val("");
            $('#' + imgId).attr('src', "");
        }
    }
});