//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'layer', 'form', 'laydate'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;

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

    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#auditTable',
            //请求地址
            url: '/manage/org/audit/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerName: $.trim($("#partnerName").val()),
                saler: $.trim($("#saler").val()),
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
            //单元格设置
            cols: [[
                {field: 'partnerId', width: 90, title: '机构编号'},
                {field: 'partnerName', width: 90, title: '机构名称'},
                {field: 'saler', width: 80, title: '推广人'},
                {field: 'partnerType', width: 90, title: '机构类型'},
                {field: 'parentId', width: 100, title: '父机构编号'},
                {field: 'businessLicenceNo', width: 90, title: '营业执照'},
                {field: 'partnerAddress', title: '机构地址', width: 90},
                {field: 'legalName', title: '法人姓名', width: 90},
                {field: 'legalId', title: '法人身份证', width: 100},
                {field: 'legalPhone', title: '法人联系电话', width: 120},
                {field: 'contactor', width: 100, title: '机构联系人'},
                {field: 'contactPhone', title: '机构联系电话', width: 120},
                {field: 'failReason', title: '审核意见', width: 90},
                {field: 'createTime', title: '创建时间', width: 90},
                {field: 'modifyTime', title: '审核时间', width: 90},
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

    $("#agreementBtn").on("click", function () {
        window.open($("#pdfUrl").val());
    });

    //审核失败
    $("#refuse").on("click", function () {
        layer.prompt({
            formType: 2,
            title: '请填写审核失败原因',
            area: ['300px', '150px']
        }, function (value, index, elem) {
            $.ajax({
                url: '/manage/org/audit/refuse',
                type: 'post',
                data: {
                    partnerId: $("#partnerId").val(),
                    failReason: value
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == "00000") {
                        layer.closeAll();
                        search();
                    } else {
                        layer.alert(data.msg);
                        layer.closeAll();
                    }
                },
                error: function () {
                    layer.alert("操作失败，请重试！");
                }
            })
        });
    });

    //审核成功
    $("#approveSubmit").on("click", function () {
        $.ajax({
            url: '/manage/org/audit/approve',
            type: 'post',
            data: $("#auditForm").serialize(),
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("审核成功", function () {
                        //点击确认按钮执行回调函数
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert(data.msg);
                    layer.closeAll();
                }
            },
            error: function () {
                layer.alert("操作失败，请重试！");
            }
        });
    });


    //监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(tableFilter)', function (obj) {
        //获取所在行的数据
        var myData = obj.data;
        //审核
        if (obj.event === 'audit') {
            var notifyConfig = "";
            //form表单初始化
            $.ajax({
                url: '/manage/sign/getOne',
                type: 'get',
                async: false,
                data: {
                    partnerId: myData.partnerId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == "00000") {
                        var config = data.data;
                        if (!$.trim(config)) {
                            return;
                        } else {
                            notifyConfig = "手机号-" + formatStatus(config.phone)
                                + " 车牌号-" + formatStatus(config.carNo)
                                + " 身份证号-" + formatStatus(config.userId)
                                + " 姓名-" + formatStatus(config.userName)
                                + " 银行卡-" + formatStatus(config.cardNo)
                        }
                    }
                },
                error: function () {

                }
            });
            form.val("formFilter", {
                "partnerId": myData.partnerId,
                "partnerName": myData.partnerName,
                "saler": myData.saler,
                "partnerType": myData.partnerType,
                "parentId": myData.parentId,
                "businessLicenceNo": myData.businessLicenceNo,
                "partnerAddress": myData.partnerAddress,
                "legalName": myData.legalName,
                "legalId": myData.legalId,
                "legalPhone": myData.legalPhone,
                "contactor": myData.contactor,
                "contactPhone": myData.contactPhone,
                "failReason": myData.failReason,
                "createTime": myData.createTime,
                "modifyTime": myData.modifyTime,
                "rank": myData.rank,
                "notifyConfig": notifyConfig
            });
            showImg(myData);
            //打开模态框
            openModal("审核", "auditForm");
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(formFilter)', function (data) {
        return false;
    });
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

    //展示图片信息 图片路径+图片名称
    function showImg(myData) {
        $.trim(myData.license) == "" ? $('#licenseImg').attr('src', "") :
            $('#licenseImg').attr('src', "/manage/etcimg/temp/" + myData.partnerId + "/license/" + myData.license);
        $.trim(myData.idFront) == "" ? $('#legalFrontImg').attr('src', "") :
            $('#legalFrontImg').attr('src', "/manage/etcimg/temp/" + myData.partnerId + "/front/" + myData.idFront);
        $.trim(myData.idBack) == "" ? $('#legalBackImg').attr('src', "") :
            $('#legalBackImg').attr('src', "/manage/etcimg/temp/" + myData.partnerId + "/back/" + myData.idBack);
        $.trim(myData.agreement) == "" ? $("#pdfUrl").val("")
            : $("#pdfUrl").val("/manage/etcimg/temp/" + myData.partnerId + "/agreement/" + myData.agreement);
    }

    function formatStatus(value) {
        if (value == "0") {
            return "不回传";
        }
        if (value == "1") {
            return "回传";
        }
        if (value == "2") {
            return "脱敏回传";
        } else {
            return "";
        }
    }
});