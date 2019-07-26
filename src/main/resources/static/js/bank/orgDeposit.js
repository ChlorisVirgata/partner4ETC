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
            elem: '#depositTable',
            //请求地址
            url: '/org/deposit/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerId: $.trim($("#partnerId").val())
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
                {field: 'kid', hide: true},
                {field: 'partnerId', title: '机构编号'},
                {field: 'deposit', title: '签约保证金'},
                {field: 'minDeposit', title: '最低保证金'},
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

    $("#addBtn").on("click", function () {
        $("#addForm").find("input[name='partnerId']").val("");
        $("#addForm").find("input[name='deposit']").val("");
        $("#addForm").find("input[name='minDeposit']").val("");
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
                "kid": myData.kid,
                "partnerId": myData.partnerId,
                "deposit": myData.deposit,
                "minDeposit": myData.minDeposit
            });
            //打开模态框
            openModal("编辑", "editForm");
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(addFilter)', function (data) {
        if (parseInt($("#addForm").find("input[name=deposit]").val())
            < parseInt($("#addForm").find("input[name=minDeposit]").val())) {
            layer.alert("签约保证金金额不能小于最低保证金金额");
            return false;
        }
        $.ajax({
            url: '/org/deposit/add',
            type: 'post',
            data: $("#addForm").serialize(),
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
        if (parseInt($("#editForm").find("input[name=deposit]").val())
            < parseInt($("#editForm").find("input[name=minDeposit]").val())) {
            layer.alert("签约保证金金额不能小于最低保证金金额");
            return false;
        }
        $.ajax({
            url: '/org/deposit/edit',
            type: 'post',
            data: $("#editForm").serialize(),
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
            area: ['500px', '270px'],
            //点击遮罩关闭窗口
            shadeClose: true,
            //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type: 1
        });
    }

    form.verify({
        numberCheck: function (value, item) {
            if (value.substr(0, 1) == "0" ||
                value.indexOf(".") != -1) {
                return "金额有误,请输入正整数！";
            }
        }
    })
});