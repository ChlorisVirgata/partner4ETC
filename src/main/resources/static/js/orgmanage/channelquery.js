//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
var layer=null;
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
                sbstatus: "('1','2','3','4','5','6')",//默认查询状态
                status: $("#chanelstatus").val(),//机构状态
                // createTime:new Date($("#creatdate").val().split(" ")[0].split('-')[0], $("#creatdate").val().split(" ")[0].split('-')[1]-1, $("#creatdate").val().split(" ")[0].split('-')[2]),
                // modifyTime:new Date($("#modifydate").val().split(" ")[0].split('-')[0], $("#modifydate").val().split(" ")[0].split('-')[1]-1, $("#modifydate").val().split(" ")[0].split('-')[2])

                // createTimeStart:"2019-07-03",
                createTimeStart: $("#creatdate").val()==""?"":$("#creatdate").val().substr(0,10),//查询创建时间起
                createTimeEnd:$("#creatdate").val()==""?"":$("#creatdate").val().substr(12,11),//查询创建时间止
                modifyTimeStart: $("#modifydate").val() == "" ? "" : $("#modifydate").val().substr(0, 10),//更新时间起
                modifyTimeEnd: $("#modifydate").val() == "" ? "" : $("#modifydate").val().substr(12, 11)//更新时间止
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

            //设置返回的属性值，依据此值进行解析
            // response: {
            //     statusName: 'code',
            //     statusCode: '0000',
            //     msgName: 'msg',
            //     dataName: 'data'
            // },

            //每页展示的条数
            limits: [5, 10],
            //每页默认显示的数量
            limit: 5,
            //单元格设置
            cols: [[
                {field: 'partnerId', width: 100, title: '机构编号'},
                {field: 'partnerName', width: 100, title: '机构名称'},
                {field: 'partnerType', width: 80, title: '机构类型'},
                {field: 'partner_address', width: 100, title: '机构地址', hide: true},
                {field: 'saler', width: 100, title: '推广人', hide: true},
                {field: 'legalName', width: 100, title: '法人姓名', hide: true},
                {field: 'legalId', width: 100, title: '法人身份证', hide: true},
                {field: 'legalPhone', width: 100, title: '法人联系方式', hide: true},
                {field: 'contactor', width: 100, title: '机构联系人', hide: true},
                {field: 'contactPhone', width: 100, title: '联系人电话', hide: true},
                {field: 'parentId', width: 100, title: '父机构编号'},
                {field: 'businessLicenceNo', width: 120, title: '营业执照编号'},
                {field: 'partner_address', width: 100, title: '机构地址'},
                {field: 'parstatus', width: 120, title: '机构状态'},
                {field: 'failReason', width: 120, title: '审核意见'},
                {field: 'createTimeX', width: 100, title: '创建时间'},
                {field: 'modifyTimeX', width: 100, title: '更新时间'},
                {field: 'sysUser', width: 100, title: '最后操作人'}
            ]]
        });

        //监听行单击事件（单击事件为：rowDouble）
        table.on('row(test)', function(obj){
            var data = obj.data;

            // layer.alert(JSON.stringify(), {
            //     title: '当前行数据：'
            // });
            showinfo(data);
            //标注选中样式
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
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

    function showinfo(data){

        $("#chanelidshow").val(data.partnerId);//机构编号
        $("#chanelnameshow").val(data.partnerName);//机构名称
        $("#chaneltypeshow").val(data.partnerType);//机构类型
        $("#partneridshow").val(data.parentId);//父机构编号
        $("#registidshow").val(data.businessLicenceNo);//营业执照编号
        $("#chaneladdressshow").val(data.partner_address);//机构地址
        $("#leglnameshow").val(data.legalName);//法人姓名
        $("#legldcardshow").val(data.legalId);//法人证件号
        $("#leglphoneshow").val(data.legalPhone);//法人身份证号
        $("#chanelpersonshow").val(data.contactor);//机构联系人
        $("#chanelpersonphoneshow").val(data.contactPhone);//联系人电话
        $("#chanelstatusshow").val(data.parstatus);//机构状态
        $("#creattimeshow").val(data.createTimeX);//创建时间
        $("#modifytimeshow").val(data.modifyTimeX);//更新时间
        $("#approvalopinionshow").val(data.failReason);//审批意见
        // $("#secretkeyshow").val(data.PARTNER_ID);//秘钥

        //打开模态框
        openModal("详细信息", "editForm");
    }


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

    //监听form表单提交事件 防止页面跳转
    form.on('submit(backbtn)', function (data) {
        return false;
    });

    form.on('submit(addFilter)', function (data) {
        return false;
    });
});

function query(){
    var index = layer.alert("立即提交", function () {
        layer.close(index);
        search();
    })
}

function back(){
    //点击确认按钮执行回调函数
    layer.closeAll();
}