//element 展示左边菜单栏; 预加载需要使用的模块
//由于layer弹层依赖jQuery，所以可以直接得到
layui.use(['table', 'element', 'laypage', 'layer', 'form'], function () {
    var table = layui.table;
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    //抽取查询方法
    var search = function () {
        table.render({
            //表格生成的位置：#ID
            elem: '#menuTable',
            //请求地址
            url: '/menu/list',
            toolbar: '#toolbarDemo'
            , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            //是否分页
            , page: true,
            //请求参数
            where: {
                name: $("#name").val()
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
                {type: 'radio'},
                {field: 'menuId', width: 80, title: '菜单ID', sort: true},
                {field: 'parentId', width: 80, title: '上级菜单ID', sort: true},
                {field: 'name', width: 80, title: '菜单名称'},
                {field: 'url', width: 80, title: '菜单地址', sort: true},
                {field: 'typeName', width: 80, title: '菜单类型', sort: true},
                {field: 'icon', width: 80, title: '图标'},
                {field: 'perms', width: 80, title: '权限'},
                {field: 'orderNum', width: 80, title: '菜单排序'},
                {field: 'createTime', width: 150, title: '创建时间', sort: true},
                {field: 'updateTime', width: 150, title: '更新时间'},
                {fixed: 'right', title: '操作', toolbar: '#barmenu', width: 120}
            ]]
        });
    };

    //头工具栏事件
    table.on('toolbar(menuTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;  //获取选中行数据
                layer.alert(JSON.stringify(data));
                break;
            case 'menuQueryBtn':
                search();
                break;
            case 'resetBtn':
                $("#name").val("");
                break;
            case 'addMenuBtn':
                layer.open({
                    type: 2,
                    title: '添加菜单',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['500px', '80%'],
                    content: 'menu/add', //iframe的url
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


    //页面加载就查询列表
    search();

    // $("#menuQueryBtn").on("click", function () {
    //     search();
    //     break;
    // });

    //重置参数
    $("#resetBtn").on("click", function () {
        //alert("resetBtn")
        $("#name").val("");

        // layer.alert("参数清除");
    });

    //重置参数
    $("#addBtn").on("click", function () {
        layer.open({
            type: 2,
            title: '添加菜单',
            shadeClose: true,
            shade: 0.8,
            area: ['500px', '80%'],
            content: 'menu/add', //iframe的url
            btn: ['关闭'],
            yes: function () {
                search();
                layer.closeAll();
            }

        });
    });


    //监听行工具事件
    table.on('tool(menuTable)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                table.reload("menuTable", {
                    url: '/menu/del',
                    where: {
                        id: data.menuId
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
        } else if (obj.event === 'edit_menu') {
            // var data = checkStatus.data;
            layer.open({
                type: 2,
                title: '添加菜单',
                shadeClose: true,
                shade: 0.8,
                area: ['500px', '80%'],
                content: 'menu/edit?menuId=' + data.menuId, //iframe的url
                btn: ['关闭'],
                yes: function () {
                    search();
                    layer.closeAll();
                }

            });
        }
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
                area: ['500px', '80%'],
                content: 'menu/add', //iframe的url
                btn: ['关闭'],
                yes: function () {
                    search();
                    layer.closeAll();
                }

            });

        }
    };

    $('#toolbarDemo .layui-btn').on('click', function () {
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