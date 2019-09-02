//element 展示左边角色栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'laypage', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var statusTpl = function (d) { // 参数d是当前行数据
        if (d.status == 1) {
            return '<input type="checkbox" lay-skin="switch" name="status" id="status" lay-text="启用|禁用" checked> ';
        } else {
            return '<input type="checkbox" lay-skin="switch" name="status" id="status" lay-text="启用|禁用"> ';
        }
    };
    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#roleTable',
            //请求地址
            url: '/manage/role/list',
            cellMinWidth: 80,  //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            toolbar: '#toolbarDemo',
            //是否分页
            page: true,
            //请求参数
            where: {
                rolename: $("#rolename").val()
                // roletype: $("#roletype").val()
            },
            //分页信息
            request: {
                pageName: 'pageNo',
                limitName: 'pageSize'
            },
            //设置返回的属性值，依据此值进行解析
            response: {
                statusName: 'code',
                statusCode: 0,
                msgName: 'msg',
                dataName: 'data'
            },
            //每页展示的条数
            limits: [10, 20, 30],
            //每页默认显示的数量
            limit: 10,
            //单元格设置
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'roleId', width: 140, title: '角色ID', sort: true},
                {field: 'roleName', width: 160, title: '角色名称'},
                {field: 'status', width: 160, title: '状态', templet: statusTpl},
                {field: 'createTime', width: 210, title: '创建时间', sort: true},
                {field: 'updateTime', width: 210, title: '更新时间'},
                {fixed: 'right', title: '操作', toolbar: '#barRole', width: 140}
            ]]
        });
    };

    //条件查询
    $("#queryBtn").on("click", function () {
        table.reload('roleTable', {
            url: "/manage/role/list"
            , where: {
                rolename: $("#rolename").val()
            }
            , page: {
                curr: 1
            }

        });

    });

    //页面加载就查询列表
    search();

    //重置参数
    $("#resetBtn").on("click", function () {
        $("#rolename").val("");
    });

    //监听单元格编辑
    table.on('switch(status)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段

        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });

    //监听行工具事件
    table.on('tool(roleTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                table.reload("roleTable", {
                    url: '/manage/role/del',
                    where: {
                        id: data.roleId
                    },
                    success: function () {
                        search();
                        layer.closeAll();
                        // location.href = index.html;
                    }
                });
                layer.close(index);
                search();
            });
        } else if (obj.event === 'edit_role') {
            layer.open({
                type: 2,
                title: '编辑角色',
                shadeClose: true,
                shade: 0.8,
                area: ['450px', '60%'],
                content: 'role/edit?roleId=' + data.roleId, //iframe的url
                btn: ['关闭'],
                yes: function () {
                    search();
                    layer.closeAll();
                }

            });
        }
    });


    //头工具栏事件
    table.on('toolbar(roleTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;  //获取选中行数据
                layer.alert(JSON.stringify(data));
                break;
            case 'roleQueryBtn':
                search();
                break;
            case 'resetBtn':
                $("#rolename").val("");
                break;
            case 'addRoleBtn':
                layer.open({
                    type: 2,
                    title: '添加角色',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['450px', '80%'],
                    content: '/manage/role/add', //iframe的url
                    btn: ['关闭'],
                    yes: function () {
                        search();
                        layer.closeAll();
                    }

                });
                break;
        }
    });


    $('#layerDemo .layui-btn').on('click', function () {
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });


    //进行编辑操作
    $("#editSubmit").on("click", function () {
        $.ajax({
            url: '/web/editById',
            type: 'post',
            data: {
                id: '001'
            },
            // async: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("编辑成功", function () {
                        //点击确认按钮执行回调函数
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert("编辑失败！");
                }
            },
            error: function () {
                layer.alert("编辑失败，请重试！");
            }
        });
    });


    $("#addSubmit").on("click", function () {
        $.ajax({
            url: '/web/add',
            type: 'post',
            data: {
                id: '001'
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == "00000") {
                    var index = layer.alert("添加成功", function () {
                        //点击确认按钮执行回调函数
                        layer.closeAll();
                        search();
                    });
                } else {
                    layer.alert("添加失败！");
                }
            },
            error: function () {
                layer.alert("添加失败，请重试！");
            }
        })
    });


});