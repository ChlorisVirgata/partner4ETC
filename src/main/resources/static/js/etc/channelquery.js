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
            elem: '#test',
            //请求地址getList
            url: '/web/getList',
            //是否分页
            page: true,
            //请求参数
            where: {
                username: $("#username").val(),
                position: $("#position").val()
            },
            //分页信息
            request: {
                pageName: 'pageNum',
                limitName: 'pageSize'
            },
            //设置返回的属性值，依据此值进行解析
            response: {
                statusName: 'code',
                statusCode: 200,
                msgName: 'msg',
                dataName: 'data'
            },
            //每页展示的条数
            limits: [2, 4, 6],
            //每页默认显示的数量
            limit: 2,
            //单元格设置
            cols: [[
                {field: 'PARTNER_ID', width: 100, title: '机构编号', sort: true},
                {field: 'PARTNER_NAME', width: 100, title: '机构名称'},
                {field: 'PARTNER_TYPE', width: 100, title: '机构类型', sort: true},
                {field: 'PARENT_ID', width: 100, title: '父机构编号'},
                {field: 'BUSINESS_LICENCE_NO',width: 120, title: '营业执照编号'},
                // {field: 'experience', title: '机构地址', sort: true},
                {field: 'STATUS', width: 120,title: '机构状态', sort: true},
                {field: 'CREATE_TIME', width: 100,title: '创建时间'},
                {field: 'MODIFY_TIME', width: 100, title: '更新时间', sort: true}
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

    //重置参数
    $("#resetBtn").on("click", function () {
        $("#username").val("");
        $("#position").val("");
        layer.alert("参数清除");
    });

    function showinfo(data){
        $("#chanelidshow").val(data.PARTNER_ID);

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