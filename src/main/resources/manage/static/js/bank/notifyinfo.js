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
            url: '/manage/sign/getList',
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
            done: function () {
                $("tbody td[data-field='phone']").children().each(function (index, val) {
                    $(this).text(formatStatus($(this).text()));
                });
                $("tbody td[data-field='carNo']").children().each(function (index, val) {
                    $(this).text(formatStatus($(this).text()));
                });
                $("tbody td[data-field='userId']").children().each(function (index, val) {
                    $(this).text(formatStatus($(this).text()));
                });
                $("tbody td[data-field='userName']").children().each(function (index, val) {
                    $(this).text(formatStatus($(this).text()));
                });
                $("tbody td[data-field='cardNo']").children().each(function (index, val) {
                    $(this).text(formatStatus($(this).text()));
                });
            },
            //每页展示的条数
            limits: [5, 10, 20],
            //每页默认显示的数量
            limit: 10,
            //单元格设置
            cols: [[
                {field: 'partnerId', title: '机构编号'},
                {field: 'phone', title: '手机号'},
                {field: 'carNo', title: '车牌号'},
                {field: 'userId', title: '身份证号'},
                {field: 'userName', title: '姓名'},
                {field: 'cardNo', title: '银行卡'},
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
        $("#addForm").find("input[name='partnerId']").val("");
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
                "partnerId": myData.partnerId,
                "phone": myData.phone,
                "carNo": myData.carNo,
                "userId": myData.userId,
                "userName": myData.userName,
                "cardNo": myData.cardNo
            });
            //打开模态框
            openModal("编辑", "editForm");
        }
    });

    //监听form表单提交事件 防止页面跳转
    form.on('submit(addFilter)', function (data) {
        $.ajax({
            url: '/manage/sign/add',
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
        $.ajax({
            url: '/manage/sign/edit',
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
            area: ['500px', '450px'],
            //点击遮罩关闭窗口
            shadeClose: true,
            //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type: 1
        });
    }

    //状态格式化
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