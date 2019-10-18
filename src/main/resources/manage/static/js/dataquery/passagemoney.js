//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
var layer = null;
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
            url: '/manage/query/passagemoney/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                partnerId: $("#chanelidquery").val(),//机构编号
                license: $("#licensequery").val(),//车牌号
                startTime: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(0, 10) + " 00:00:00",//查询创建时间起
                endTime: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(13, 11) + " 00:00:00",//查询创建时间止
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
                {field: 'partnerId',  title: '机构编号'},
                {field: 'authId', title: '用户编号'},
                {field: 'accountNo', title: '账户'},
                {field: 'authName', title: '用户名称'},
                {field: 'carNo', title: '车牌号'},
                {field: 'status', title: '交易结果'},
                {field: 'transeTime', title: '请求时间'},
                {field: 'amount',  title: '金额'},
                {field: 'passageway', title: '出入口'}
                // {field: 'insertTime',  title: '插入时间'}
            ]]
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


    //导出改为单独的事件，每次点击导出才会执行
    $("#export").click(function () {
        var ins1 = table.render({
            elem: '#data_export',
            url: '/manage/query/passagemoney/export', //数据接口
            method: 'get',
            title: '通行费记录表',
            //请求参数
            where: {
                partnerId: $("#chanelidquery").val(),//机构编号
                license: $("#licensequery").val(),//车牌号
                startTime: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(0, 10) + " 00:00:00",//查询创建时间起
                endTime: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(12, 11) + " 00:00:00",//查询创建时间止
            },
            cols: [[
                {field: 'partnerId', title: '机构编号'},
                {field: 'authId', title: '用户编号'},
                {field: 'accountNo', title: '账户'},
                {field: 'authName', title: '用户名称'},
                {field: 'carNo', title: '车牌号'},
                {field: 'status', title: '交易结果'},
                {field: 'transeTime', title: '请求时间'},
                {field: 'amount', title: '金额'},
                {field: 'passageway', title: '出入口'}
                // {field: 'insertTime',  title: '插入时间'}
            ]],
            done: function (res, curr, count) {

                // exportData = res.list;
                // table.exportFile(ins1.config.id, exportData, 'xls');
                var data = res.list;
                var excel = layui.excel;
                // console.log(res);
                // 重点！！！如果后端给的数据顺序和映射关系不对，请执行梳理函数后导出
                data = excel.filterExportData(data, [
                    'partnerId'
                    , 'authId'
                    , 'accountNo'
                    , 'authName'
                    , 'carNo'
                    , 'status'
                    , 'transeTime'
                    , 'amount'
                    , 'passageway'
                ]);
                // 重点2！！！一般都需要加一个表头，表头的键名顺序需要与最终导出的数据一致
                data.unshift({
                    partnerId: "机构编号",
                    authId: "用户编号",
                    accountNo: "账户",
                    authName: "用户名称",
                    carNo: "车牌号",
                    status: "交易结果",
                    transeTime: "请求时间",
                    amount: "金额",
                    passageway: "出入口"
                });

                var timestart = Date.now();
                excel.exportExcel(data, '通行费记录表.xlsx', 'xlsx');
                //	var timeend = Date.now();

                //	var spent = (timeend - timestart) / 1000;
                //	layer.alert('单纯导出耗时 '+spent+' s');
            }
        });
    })
});


