//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#auditTable',
            //请求地址
            url: '/org/audit/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerName: $("#partnerName").val(),
                saler: $("#saler").val(),
                createTime: $("#createTime").val(),
                modifyTime: $("#modifyTime").val()
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
                statusCode: 00000,
                msgName: 'msg',
                dataName: 'data'
            },
            //每页展示的条数
            limits: [2, 4, 6],
            //每页默认显示的数量
            limit: 2,
            //单元格设置
            cols: [[
                {field: 'partnerId', width: 90, title: '机构编号'},
                {field: 'partnerName', width: 90, title: '机构名称'},
                {field: 'saler', width: 80, title: '推广员'},
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
                {field: 'modifyTime', title: '更新时间', width: 90},
                {field: 'center', title: '操作', toolbar: '#operator', width: 65}
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

    //进行编辑操作
    $("#editSubmit").on("click", function () {
        $.ajax({
            url: '/web/editById',
            type: 'post',
            data: {
                id: '001'
            },
            // async: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("编辑成功", function () {
                        //点击确认按钮执行回调函数
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert("编辑失败！");
                }
            },
            error: function () {
                layer.alert("编辑失败，请重试！");
            }
        });
    });

    //打开添加页面模态框
    $("#addBtn").on("click", function () {
        openModal("添加", "addForm");
    });

    //监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(tableFilter)', function (obj) {
        //获取所在行的数据
        var data = obj.data;
        //审核
        if (obj.event === 'audit') {
            //先通过后台查询数据，渲染页面后打开模态框
            $.ajax({
                url: '/web/getById',
                type: 'get',
                data: {
                    id: data.id
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == "00000") {
                        var myData = data.data[0];

                    } else {
                        layer.alert("查询失败！");
                    }
                },
                error: function () {
                    layer.alert("查询失败，请重试！");
                }
            });
            //打开模态框
            openModal("编辑", "editForm");
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(editFilter)', function (data) {
        return false;
    });

    //打开模态框
    function openModal(operateName, modalName) {
        layer.open({
            title: operateName,
            content: $('#' + modalName),
            area: ['700px', '450px'],
            //点击遮罩关闭窗口
            shadeClose: true,
            //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type: 1
        });
    }
});