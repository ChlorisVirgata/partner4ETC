//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
var layer = null;
layui.use(['table', 'element', 'laypage', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    layer = layui.layer;
    var form = layui.form;

    //初始化开始查询时间
    // var inittime = getNowFormatDate();
    // $("#creatdate").val(inittime);
    // $("#modifydate").val(inittime);

    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#orginfotable',
            //请求地址getList
            url: '/query/org/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerId: $("#chanelid").val(),//机构编号
                partnerName: $("#chanelname").val(),//机构名称
                partnerType: $("#chanetype").val(),//机构类型
                status: $("#chanelstatus").val(),//机构状态
                sbstatus: "('1','2','3')",//默认查询状态
                // createTime:new Date($("#creatdate").val().split(" ")[0].split('-')[0], $("#creatdate").val().split(" ")[0].split('-')[1]-1, $("#creatdate").val().split(" ")[0].split('-')[2]),
                // modifyTime:new Date($("#modifydate").val().split(" ")[0].split('-')[0], $("#modifydate").val().split(" ")[0].split('-')[1]-1, $("#modifydate").val().split(" ")[0].split('-')[2])

                // createTimeStart:"2019-07-03",
                createTimeStart: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(0, 10),//查询创建时间起
                createTimeEnd: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(12, 11),//查询创建时间止
                modifyTimeStart: $("#modifydate").val() == "" ? "" : $("#modifydate").val().substr(0, 10),//修改时间起
                modifyTimeEnd: $("#modifydate").val() == "" ? "" : $("#modifydate").val().substr(12, 11)
            },
            //分页信息
            request: {
                pageName: 'pageNum',
                limitName: 'pageSize'
            },

            //处理返回参数
            parseData: function (res) {

                if (res.data.total == 0) {
                    var index = layer.alert("无数据，请修改查询条件", function () {
                        layer.close(index);
                    })
                    return {
                        "code": res.code,
                        "msg": "无数据",
                        "count": res.data.total,
                        "data": res.data.list
                    };
                }
                return {
                    "code": res.code,
                    "msg": res.msg,
                    "count": res.data.total,
                    "data": res.data.list
                };
            },

            //每页展示的条数
            limits: [5, 10,20],
            //每页默认显示的数量
            limit: 10,
            //单元格设置
            cols: [[
                {field: 'partnerId', title: '机构编号'},
                {field: 'partnerName', title: '机构名称'},
                {field: 'partnerType',  title: '机构类型'},
                {field: 'parentId',  title: '父机构编号'},
                {field: 'partnerAddress',  title: '机构地址'},
                {field: 'saler',  title: '推广人'},
                {field: 'parstatus',  title: '机构状态'},
                {field: 'createTimeX',  title: '创建时间'},
                {field: 'modifyTimeX', title: '更新时间'},
                {field: 'sysUser', title: '最后操作人'},
                {fixed: 'right',  title: '操作', toolbar: '#operators'}
            ]],
            done: function (res, curr, count) {

                $("[data-field='partnerType']").children().each(function () {
                    if ($(this).text() == '00') {
                        $(this).text("银行")
                    } else if ($(this).text() == '01') {
                        $(this).text("汽车服务")
                    } else if ($(this).text() == '02') {
                        $(this).text("互联网平台")
                    } else if ($(this).text() == '04') {
                        $(this).text("其他")
                    }
                });
            }
        });
    };

    //页面加载就查询列表
    search();

    //条件查询
    $("#queryBtn").on("click", function () {
        // var index = layer.alert("立即提交", function () {
        //     layer.close(index);
        search();
        // })

    });

//监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(orginfotable)', function (obj) {
        //获取所在行的数据
        var data = obj.data;
        //删除
        if (obj.event === 'logout') {
            var index = layer.confirm('确定注销？', function () {
                $.ajax({
                    url: '/query/org/blockOrg',
                    type: 'post',
                    data: {
                        partnerId: data.partnerId,
                        status: '2'
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "00000") {
                            layer.close(index);
                            search();
                            layer.alert("注销成功！");
                        } else {
                            layer.alert("注销失败！");
                        }
                    },
                    error: function () {
                        layer.alert("注销失败，请重试！");
                    }
                });
            });
            //编辑
        }
    });


    //监听form表单提交事件 防止页面跳转
    form.on('submit(backbtn)', function (data) {
        return false;
    });

    form.on('submit(addFilter)', function (data) {
        return false;
    });
});

