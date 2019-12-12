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
            url: '/manage/query/activation/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                authId: $("#useridquery").val(),//用户标识
                authName: $("#usernamequery").val(),//用户名称
                partnerId: $("#chanelidquery").val(),//机构编号
                obuId: $("#obuid").val(),//obu序列号
                openIs: $("#isopen").val(),//激活状态
                queryTimeStart: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(0, 10),//查询创建时间起
                queryTimeEnd: $("#creatdate").val() == "" ? "" : $("#creatdate").val().substr(13, 11)//查询创建时间止
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
                // {field: 'partnerId', title: '机构编号'},
                {field: 'carNo',  title: '车牌号'},
                {field: 'licenseColor',  title: '车牌颜色'},
                // {field: 'authId',  title: '用户标识'},
                {field: 'phone',  title: '手机号'},
                {field: 'authName',  title: '车主姓名'},
                {field: 'finishTime',  title: '签约时间'},
                {field: 'obuId',  title: 'OBU序列号'},
                {field: 'openTime',  title: 'OBU激活时间'},
                {field: 'cpuId',  title: 'CPU序列号'},
                {field: 'cpuOpenTime',  title: 'CPU激活时间'},
                {field: 'openIs',  title: '是否激活'}
                // {field: 'deliveryMethod',  title: '邮寄方式'}

            ]],

            done: function (res, curr, count) {
                // $("[data-field='deliverymethod']").children().each(function () {
                //     if ($(this).text() == '1') {
                //         $(this).text("快递")
                //     } else if ($(this).text() == '2') {
                //         $(this).text("自提")
                //     }
                // });
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


    //导出改为单独的事件，每次点击导出才会执行
    $("#export").click(function () {
        var ins1 = table.render({
            elem: '#data_export',
            url: '/manage/query/activation/export', //数据接口
            method: 'get',
            title: 'OBU激活信息数据表',
            //请求参数
            where: {
                export: "1",
                authId: $("#useridquery").val(),//用户标识
                authName: $("#usernamequery").val(),//用户名称
                partnerId: $("#chanelidquery").val(),//机构编号
                obuId: $("#obuid").val(),//obu序列号
                openIs: $("#isopen").val(),//激活状态
                queryTimeStart:$("#creatdate").val() == "" ? "" :$("#creatdate").val().substr(0, 10),//查询创建时间起
                queryTimeEnd:$("#creatdate").val() == "" ? "" :$("#creatdate").val().substr(13, 11)//查询创建时间止
            },
            cols: [[
                // {field: 'partnerId', title: '机构编号'},
                {field: 'carNo',  title: '车牌号'},
                {field: 'licenesColor',  title: '车牌颜色'},
                // {field: 'authId',  title: '用户标识'},
                {field: 'phone',  title: '手机号'},
                {field: 'authName',  title: '车主姓名'},
                {field: 'finishTime',  title: '签约时间'},
                {field: 'obuId',  title: 'OBU序列号'},
                {field: 'openTime',  title: 'OBU激活时间'},
                {field: 'cpuId',  title: 'CPU序列号'},
                {field: 'cpuOpenTime',  title: 'CPU激活时间'},
                {field: 'openIs',  title: '是否激活'}
                // {field: 'deliveryMethod',  title: '邮寄方式'}

            ]],
            done: function (res, curr, count) {
                // exportData = res.list;
                // table.exportFile(ins1.config.id, exportData, 'xls');
                var data = res.list;
                var excel = layui.excel;
                // console.log(res);
                // 重点！！！如果后端给的数据顺序和映射关系不对，请执行梳理函数后导出
                data = excel.filterExportData(data, [
                    // 'partnerId'
                     'carNo'
                    , 'licenseColor'
                    // , 'authId'
                    , 'phone'
                    , 'authName'
                    , 'finishTime'
                    , 'obuId'
                    ,'openTime'
                    , 'cpuId'
                    , 'cpuOpenTime'
                    , 'openIs'
                    // ,'deliveryMethod'
                ]);
                // 重点2！！！一般都需要加一个表头，表头的键名顺序需要与最终导出的数据一致
                data.unshift({
                // partnerId: "机构编号",
                carNo: "车牌号",
                licenseColor: "车牌颜色",
                // authId: "用户标识",
                phone: "手机号",
                authName: "车主姓名",
                finishTime: "签约时间",
                obuId:"OBU序列号",
                openTime: "OBU激活时间",
                cpuId: "CPU序列号",
                 cpuOpenTime: "CPU激活时间",
                openIs:"是否激活"
                // deliveryMethod: "邮寄方式"
                });

                var timestart = Date.now();
                excel.exportExcel(data, 'OBU激活信息数据表.xlsx', 'xlsx');

            }
        });
    })

});





