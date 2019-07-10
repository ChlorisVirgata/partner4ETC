//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'laypage', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var statusTpl = function (d) { // 参数d是当前行数据
        if (d.status == 1) {
            return '<input type="checkbox" lay-skin="switch" lay-text="启用|禁用" checked> ';
        } else {
            return '<input type="checkbox" lay-skin="switch" lay-text="启用|禁用"> ';
        }
    };
    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#roleTable',
            //请求地址
            url: '/role/list',
            cellMinWidth: 80,  //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            toolbar: '#toolbarDemo',
            even: true,
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
                // {field: 'id', width: 80, title: 'ID', sort: true},
                {field: 'roleId', width: 110, title: '角色ID', sort: true},
                {field: 'roleName', width: 110, title: '角色名称'},
                {field: 'password', width: 110, title: '创建人', sort: true},
                {field: 'status', width: 110, title: '状态', width: 120, templet: statusTpl},
                // {field: 'deptId', width: 80, title: '部门', sort: true},
                {field: 'createTime', width: 180, title: '创建时间', sort: true},
                {field: 'updateTime', width: 180, title: '更新时间'},
                // {field: 'lastLoginTime', width: 180, title: '最后登录时间', sort: true},
                {fixed: 'right', title: '操作', toolbar: '#barRole', width: 120}
            ]]
        });
    };

    //条件查询
    $("#queryBtn").on("click", function () {
        table.reload('roleTable', {
            url: "/role/list"
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
        //alert("resetBtn")
        $("#rolename").val("");
        // layer.alert("参数清除");
    });

    //重置参数
    $("#addBtn").on("click", function () {
        layer.open({
            type: 2,
            title: '添加菜单',
            shadeClose: true,
            shade: 0.8,
            area: ['450px', '50%'],
            content: 'role/add', //iframe的url
            btn: ['关闭'],
            yes: function () {
                search();
                layer.closeAll();
            }

        });
    });


    //监听行工具事件
    table.on('tool(roleTable)', function (obj) {
        var data = obj.data;
        // var checkStatus = table.checkStatus(data.roleId);
        // var data = checkStatus.data;  //获取选中行数据
        // layer.alert(JSON.stringify(data));
        // var str = JSON.stringify(data.field);
        // alert(str);
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                table.reload("roleTable", {
                    url: '/role/del',
                    where: {
                        id: data.roleId
                    },
                    success: function () {
                        location.href = index.html;
                    }
                });
                layer.close(index);
                search();
            });
        } else if (obj.event === 'edit_role') {
            layer.open({
                type: 2,
                title: '编辑用户',
                shadeClose: true,
                shade: 0.8,
                area: ['450px', '50%'],
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
                    title: '添加菜单',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['450px', '50%'],
                    content: 'role/add', //iframe的url
                    btn: ['关闭'],
                    yes: function () {
                        search();
                        layer.closeAll();
                    }

                });
                break;
        }
        ;
    });


    //触发事件
    var active = {
        setTop: function () {
            var that = this;
            layer.open({
                type: 2,
                title: '添加用户',
                shadeClose: true,
                shade: 0.8,
                area: ['450px', '50%'],
                content: 'role/add', //iframe的url
                btn: ['关闭'],
                yes: function () {
                    search();
                    layer.closeAll();
                }

            });

            /*//多窗口模式，层叠置顶
            layer.open({
                type: 2 //此处以iframe举例
                , title: '添加用户'
                , area: ['500px', '260px']
                , shade: 0
                , maxmin: true
                , offset: [ //为了演示，随机坐标
                    ($(window).height() / 2),
                    ($(window).width() / 2)
                    // Math.random() * ($(window).height() - 300)
                    // , Math.random() * ($(window).width() - 390)
                ]
                , content: 'role/add'
                , btn: ['关闭'] //只是为了演示
                , yes: function () {
                    search();
                    layer.closeAll();
                }
                // , btn2: function () {
                //     layer.closeAll();
                // }

                , zIndex: layer.zIndex //重点1
                , success: function () {
                    // layer.setTop(layero); //重点2
                    //layer.closeAll();
                }
            });*/
        }
    };

    $('#layerDemo .layui-btn').on('click', function () {
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });


    //打开添加页面模态框
    // $("#addBtn").on("click", function () {
    //     layer.open({
    //         type: 2,
    //         title: '添加角色',
    //         shadeClose: true,
    //         shade: 0.8,
    //         area: ['500px', '80%'],
    //         content: 'addUser11.html', //iframe的url
    //         btn: ['确认', '取消'],
    //     });
    //     alert("add")
    //     // break;
    //     //openModal("添加", "addForm");
    // });


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

    /*//监听table行工具事件 如详情、编辑、删除操作
    table.on('tool(test)', function (obj) {
        //获取所在行的数据
        var data = obj.data;
        //删除
        if (obj.event === 'del') {
            var index = layer.confirm('确定删除？', function () {
                $.ajax({
                    url: '/web/delById',
                    type: 'post',
                    data: {
                        id: data.id
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "00000") {
                            layer.close(index);
                            search();
                        } else {
                            layer.alert("删除失败！");
                        }
                    },
                    error: function () {
                        layer.alert("删除失败，请重试！");
                    }
                });
            });
            //编辑
        } else if (obj.event === 'edit') {
            //先通过后台查询数据，渲染页面后打开模态框
            $.ajax({
                url: '/web/getById',
                type: 'get',
                data: {
                    id: data.id
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == "00000") {
                        var myData = data.data[0];
                        $("#editUsername").val(myData.username);
                        $("#editExperience").val(myData.experience);
                        $("#editScore").val(myData.score);
                        $("#editWealth").val(myData.wealth);
                    } else {
                        layer.alert("查询失败！");
                    }
                },
                error: function () {
                    layer.alert("查询失败，请重试！");
                }
            });
            //打开模态框
            openModal("编辑", "editForm");
        }
    });
*/
    //监听form表单提交事件 防止页面跳转
    form.on('submit(editFilter)', function (data) {
        return false;
    });

    form.on('submit(addFilter)', function (data) {
        return false;
    });

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


});