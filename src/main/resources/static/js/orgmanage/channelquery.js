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
                status: $("#chanelstatus").val(),//机构状态
                // createTime:new Date($("#creatdate").val().split(" ")[0].split('-')[0], $("#creatdate").val().split(" ")[0].split('-')[1]-1, $("#creatdate").val().split(" ")[0].split('-')[2]),
                // modifyTime:new Date($("#modifydate").val().split(" ")[0].split('-')[0], $("#modifydate").val().split(" ")[0].split('-')[1]-1, $("#modifydate").val().split(" ")[0].split('-')[2])

                // createTimeStart:"2019-07-03",
                createTimeStart: $("#creatdate").val()==""?"":$("#creatdate").val().substr(0,10),//查询创建时间起
                createTimeEnd:$("#creatdate").val()==""?"":$("#creatdate").val().substr(12,11),//查询创建时间止
                modifyTimeStart:$("#modifydate").val()==""?"":$("#modifydate").val().substr(0,10),//修改时间起
                modifyTimeEnd:$("#modifydate").val()==""?"":$("#modifydate").val().substr(12,11)
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
            limits: [2, 4, 6],
            //每页默认显示的数量
            limit: 2,
            //单元格设置
            cols: [[
                {field: 'partnerId', width: 100, title: '机构编号', sort: true},
                {field: 'PARTNER_NAME', width: 100, title: '机构名称'},
                {field: 'PARTNER_TYPE', width: 100, title: '机构类型', sort: true},
                {field: 'SALER', width: 100, title: '推广人', sort: true,hide:true},
                {field: 'LEGAL_NAME', width: 100, title: '法人姓名', sort: true,hide:true},
                {field: 'LEGAL_ID', width: 100, title: '法人身份证', sort: true,hide:true},
                {field: 'LEGAL_PHONE', width: 100, title: '法人联系方式', sort: true,hide:true},
                {field: 'CONTACTOR', width: 100, title: '机构联系人', sort: true,hide:true},
                {field: 'CONTACT_PHONE', width: 100, title: '联系人电话', sort: true,hide:true},
                {field: 'PARENT_ID', width: 100, title: '父机构编号'},
                {field: 'BUSINESS_LICENCE_NO',width: 120, title: '营业执照编号'},
                // {field: 'experience', title: '机构地址', sort: true},
                {field: 'STATUS', width: 120,title: '机构状态', sort: true},
                {field: 'createTime', width: 100,title: '创建时间'},
                {field: 'modifyTime', width: 100, title: '更新时间', sort: true},
                {field: 'SYS_USER', width: 100, title: '最后操作人', sort: true}
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

        $("#chanelidshow").val(data.PARTNER_ID);
        $("#chanelnameshow").val(data.PARTNER_NAME);
        $("#chaneltypeshow").val(data.PARTNER_TYPE);
        $("#partneridshow").val(data.PARENT_ID);

        $("#registidshow").val(data.BUSINESS_LICENCE_NO);
        // $("#chaneladdressshow").val(data.PARTNER_ID);
        $("#leglnameshow").val(data.LEGAL_NAME);
        $("#legldcardshow").val(data.LEGAL_ID);
        $("#leglphoneshow").val(data.LEGAL_PHONE);
        $("#chanelpersonshow").val(data.CONTACTOR);
        $("#chanelpersonphoneshow").val(data.CONTACT_PHONE);
        $("#chanelstatusshow").val(data.STATUS);
        $("#creattimeshow").val(data.CREATE_TIME);
        $("#modifytimeshow").val(data.MODIFY_TIME);
        // $("#approvalopinionshow").val(data.PARTNER_ID);//审批意见
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

//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {//获取当前时间
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
    var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
    var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
        + " "  + date.getHours()  + seperator2  + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}