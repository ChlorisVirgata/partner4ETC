//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'layer', 'form', 'laydate', 'upload'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var upload = layui.upload;
    var btnId;

    //日期控件
    laydate.render({
        elem: '#createTime',
        range: "~",
        trigger: 'click'
    });
    laydate.render({
        elem: '#modifyTime',
        range: "~",
        trigger: 'click'
    });

    upload.render({
        elem: '#licenseBtn',
        field: "licenseFile",
        accept: "images",
        auto: false,
        choose: function (obj) {
            //预读选择的文件，不支持ie8
            obj.preview(function (index, file, result) {
                //图片链接（base64）
                $('#licenseImg').attr('src', result);
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
                $('#legalFrontImg').attr('src', result);
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
                $('#legalBackImg').attr('src', result);
            });
        }
    });

    upload.render({
        elem: '#agreementBtn',
        field: "agreementFile",
        accept: "images",
        auto: false,
        choose: function (obj) {
            //预读选择的文件，不支持ie8
            obj.preview(function (index, file, result) {
                //图片链接（base64）
                $('#agreementImg').attr('src', result);
            });
        }
    });

    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#modifyTable',
            //请求地址
            url: '/org/modify/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerId: $.trim($("#partnerId").val()),
                partnerName: $.trim($("#partnerName").val()),
                partnerType: $.trim($("#partnerType").val()),
                parentId: $.trim($("#parentId").val()),
                createStartTime: $("#createTime").val() != "" ? $("#createTime").val().split(" ~ ")[0] : "",
                createEndTime: $("#createTime").val() != "" ? $("#createTime").val().split(" ~ ")[1] : "",
                modifyStartTime: $("#modifyTime").val() != "" ? $("#modifyTime").val().split(" ~ ")[0] : "",
                modifyEndTime: $("#modifyTime").val() != "" ? $("#modifyTime").val().split(" ~ ")[1] : ""
            },
            //分页信息
            request: {
                pageName: 'pageNum',
                limitName: 'pageSize'
            },
            //处理返回参数
            parseData: function (res) {
                return {
                    "code": res.code,
                    "msg": res.msg,
                    "count": res.data.total,
                    "data": res.data.list
                };
            },
            //设置返回的属性值，依据此值进行解析
            response: {
                statusName: 'code',
                statusCode: "00000",
                msgName: 'msg',
                dataName: 'data'
            },
            //每页展示的条数
            limits: [5, 10, 20],
            //每页默认显示的数量
            limit: 10,
            loading: true,
            // done: function() {
            //     var str = $("table").find("td[data-fild='partnerType']").find("div").html();
            // },
            //单元格设置
            cols: [[
                {field: 'partnerId', width: 90, title: '机构编号'},
                {field: 'partnerName', width: 90, title: '机构名称'},
                {field: 'saler', width: 80, title: '推广人'},
                {field: 'partnerType', width: 90, title: '机构类型'},
                {field: 'parentId', width: 100, title: '父机构编号'},
                {field: 'businessLicenceNo', width: 90, title: '营业执照'},
                {field: 'partnerAddress', title: '机构地址', width: 90},
                {field: 'legalName', title: '法人姓名', width: 90, hide: true},
                {field: 'legalId', title: '法人身份证', width: 100, hide: true},
                {field: 'legalPhone', title: '法人联系电话', width: 120, hide: true},
                {field: 'contactor', width: 100, title: '机构联系人'},
                {field: 'contactPhone', title: '机构联系电话', width: 120},
                {field: 'failReason', title: '审核意见', width: 90},
                {field: 'createTime', title: '创建时间', width: 90},
                {field: 'modifyTime', title: '审核时间', width: 90},
                {field: 'status', title: '状态',hide: true},
                {fixed: 'right', title: '操作', toolbar: '#operator', width: 65}
            ]]
        });
    };
    //页面加载就查询列表
    search();
    //条件查询
    $("#queryBtn").on("click", function () {
        search();
    });

    //重置参数
    $("#resetBtn").on("click", function () {
        $("#partnerName").val("");
        $("#saler").val("");
        $("#createTime").val("");
        $("#modifyTime").val("");
    });

    //编辑保存
    $("#editSubmit").on("click", function () {
        checkParamsForEdit();
        btnId = "editSubmit";
    });

    //提交审核
    $("#sendAuditSubmit").on("click", function () {
        checkParamsForSendAudit();
        btnId = "sendAuditSubmit";
    });

    //取消
    $("#cancel").on("click", function () {
        layer.closeAll();
    });


    //监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(tableFilter)', function (obj) {
        //获取所在行的数据
        var myData = obj.data;
        //审核
        if (obj.event === 'edit') {
            //form表单初始化
            form.val("formFilter", {
                "partnerId": myData.partnerId,
                "partnerName": myData.partnerName,
                "saler": myData.saler,
                "partnerType": convertPartnerType(myData.partnerType),
                "parentId": myData.parentId,
                "businessLicenceNo": myData.businessLicenceNo,
                "partnerAddress": myData.partnerAddress,
                "legalName": myData.legalName,
                "legalId": myData.legalId,
                "legalPhone": myData.legalPhone,
                "contactor": myData.contactor,
                "contactPhone": myData.contactPhone,
                "status": myData.status
            });
            showImg(myData);
            //打开模态框
            openModal("编辑", "editForm");
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(formFilter)', function (data) {
        var formData = new FormData(document.getElementById("editForm"));
        var url;
        if (btnId == "editSubmit") {
            url = "/org/modify/edit";
        } else if (btnId == "sendAuditSubmit") {
            url = "/org/modify/sendAudit";
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
                        layer.closeAll();
                        search();
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
    //查询条件的form
    form.on('submit(queryFilter)', function (data) {
        return false;
    });


    //打开模态框
    function openModal(operateName, modalName) {
        layer.open({
            title: operateName,
            content: $('#' + modalName),
            area: ['850px', '450px'],
            //点击遮罩关闭窗口
            shadeClose: true,
            //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type: 1
        });
    }

    //机构类型转换
    function convertPartnerType(partnerType) {
        if (partnerType == "银行") {
            return "00";
        } else if (partnerType == "汽车服务") {
            return "01";
        } else if (partnerType == "互联网平台") {
            return "02";
        } else if (partnerType == "其他") {
            return "03";
        } else {
            return "";
        }
    }

    //表单参数验证
    function checkParamsForEdit() {
        //机构名称、机构类型、营业执照编号、机构地址 使用框架做非空校验做非空校验
        var $parentId = $("input[name='parentId']");
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
        var $parentId = $("input[name='parentId']");
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
        if (!$('#licenseImg').attr('src')) {
            $license.attr("lay-verify", "required").attr("lay-reqText", "请上传营业执照图片");
        }
        if (!$('#legalFrontImg').attr('src')) {
            $legalFront.attr("lay-verify", "required").attr("lay-reqText", "请上传法人身份证正面图片");
        }
        if (!$('#legalBackImg').attr('src')) {
            $legalBack.attr("lay-verify", "required").attr("lay-reqText", "请上传法人身份证反面图片");
        }
        if (!$('#agreementImg').attr('src')) {
            $agreement.attr("lay-verify", "required").attr("lay-reqText", "请上传协议图片");
        }
    }

    //给指定的$对象赋值属性
    function verify(dom, attr, val) {
        if ($.trim(dom.val())) {
            dom.attr(attr, val);
        } else {
            dom.attr(attr, "");
        }
    }

    //展示图片信息 图片路径+图片名称
    function showImg(myData) {
        $('#licenseImg').attr('src', myData.license);
        $('#legalFrontImg').attr('src', myData.idFront);
        $('#legalBackImg').attr('src', myData.idBack);
        $('#agreementImg').attr('src', myData.agreement);
    }
});