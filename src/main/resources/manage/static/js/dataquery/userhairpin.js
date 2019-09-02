//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
var layer=null;
layui.use(['table', 'element', 'laypage', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    layer = layui.layer;
    var form = layui.form;

    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#orginfotable',
            //请求地址getList
            url: '/manage/query/userhairpin/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                authId: $("#useridquery").val(),//用户标识
                authName: $("#usernamequery").val(),//用户名称
                partnerId: $("#chanelidquery").val(),//机构编号
                orderNo: $("#serialnumberquery").val(),//流水号
                signsStatus: $("#chanetype").val(),//签约结果
                queryTimeStart: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(0, 10),//查询创建时间起
                queryTimeEnd: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(12, 11)//查询创建时间止
            },
            //分页信息
            request: {
                pageName: 'pageNum',
                limitName: 'pageSize'
            },

            //处理返回参数
            parseData: function (res) {

                if(res.data.total == 0) {
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
                {field: 'authId',  title: '用户标识'},
                {field: 'authName',  title: '用户名称'},
                {field: 'phone',  title: '机构请求手机号'},
                {field: 'orderNo',  title: '请求流水号'},
                {field: 'reqtime',  title: '请求时间'},
                {field: 'issuestatus',  title: '发行状态'},
                {field: 'carno',  title: '车牌号'},
                {field: 'realphone',  title: '绑定手机号'},
                {field: 'id',  title: '车主身份证'},
                {field: 'realname',  title: '车主姓名'},
                {field: 'issuemsg',  title: '发行结果'},
                {field: 'finishtime', title: '请求完成时间'}

            ]],

            done: function (res, curr, count) {

                $("[data-field='issuestatus']").children().each(function () {
                    if ($(this).text() == '1') {
                        $(this).text("完成")
                    } else if ($(this).text() == '2') {
                        $(this).text("进行中")
                    } else if ($(this).text() == '3') {
                        $(this).text("失败")
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

    //监听form表单提交事件 防止页面跳转
    form.on('submit(backbtn)', function (data) {
        return false;
    });

    form.on('submit(addFilter)', function (data) {
        return false;
    });
});



