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
            elem: '#bankTable',
            //请求地址
            url: '/manage/bank/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                bankId: $.trim($("#bankId").val()),
                bankName: $.trim($("#bankName").val()),
                status: $.trim($("#status").val()),
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
            done: function () {
                $("tbody td[data-field='status']").children().each(function (index, val) {
                    if ($(this).text() == "0") {
                        $(this).text("禁用");
                    } else if ($(this).text() == "1") {
                        $(this).text("有效");
                    }
                })
            },
            //每页展示的条数
            limits: [5, 10, 20],
            //每页默认显示的数量
            limit: 10,
            //单元格设置
            cols: [[
                {field: 'bankId', title: '银行编号'},
                {field: 'bankName', title: '银行名称'},
                {field: 'status', title: '状态'},
                {field: 'insertTime', title: '创建日期'},
                {field: 'modifyTime', title: '修改日期'},
                {fixed: 'right', title: '操作', toolbar: '#operator', width: 130}
            ]]
        });
    };
    //页面加载就查询列表
    search();
    //条件查询
    $("#queryBtn").on("click", function () {
        search();
    });

    $("#addBtn").on("click", function () {
        $("#addForm").find("input[name='bankId']").val("");
        $("#addForm").find("input[name='bankName']").val("");
        openModal("新增", "addForm");
    });

    //监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(tableFilter)', function (obj) {
        //获取所在行的数据
        var myData = obj.data;
        //审核
        if (obj.event === 'edit') {
            //form表单初始化
            form.val("editFilter", {
                "bankId": myData.bankId,
                "bankName": myData.bankName
            });
            //打开模态框
            openModal("编辑", "editForm");
        } else if (obj.event === 'forbidden') {
            var btnName = $(this).html();
            var status;
            if (btnName == "禁用") {
                status = "0";
            } else {
                status = "1";
            }
            var index = layer.confirm("确定置为" + btnName + "吗？", function () {
                $.ajax({
                    url: '/manage/bank/status',
                    type: 'post',
                    data: {
                        bankId: myData.bankId,
                        status: status
                    },
                    dataType: 'json',
                    success: function (data) {
                        layer.close(index);
                        if (data.code == "00000") {
                            search();
                        } else {
                            layer.alert(data.msg);
                        }
                    },
                    error: function () {
                        layer.alert("操作失败，请重试！");
                    }
                });
            })
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(addFilter)', function (data) {
        $.ajax({
            url: '/manage/bank/add',
            type: 'post',
            data: {
                bankId: $.trim($("#addForm").find("input[name='bankId']").val()),
                bankName: $.trim($("#addForm").find("input[name='bankName']").val())
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("保存成功", function () {
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert(data.msg);
                }
            },
            error: function () {
                layer.alert("新增失败，请重试！");
            }
        });
        return false;
    });
    form.on('submit(editFilter)', function (data) {
        $.ajax({
            url: '/manage/bank/edit',
            type: 'post',
            data: {
                bankId: $.trim($("#editForm").find("input[name='bankId']").val()),
                bankName: $.trim($("#editForm").find("input[name='bankName']").val())
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("编辑成功", function () {
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert(data.msg);
                }
            },
            error: function () {
                layer.alert("编辑失败，请重试！");
            }
        });
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
            area: ['500px', '250px'],
            //点击遮罩关闭窗口
            shadeClose: true,
            //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type: 1
        });
    }
});