$(function () {
    loadCopyRight();
    deleteConfirmZookeeper();
    saveZookeeper();
    postConfirmZookeeper();
    initPathTree();
    initZookeeperTable();
    registerUpdateDataButton();
    registerAddNodeButton();
    registerConfirmAddNodeButton();
    registerDeleteNodeButton();
    registerAddNodeFormValidator();
    registerPostZookeeperInfoValidator();
});

function getCurrentYear() {
    return new Date().getFullYear();
}

function loadCopyRight() {
    var copyRight = '&copy; 2016-' + getCurrentYear() + ' Throwable. All Rights Reserved.Contact me:739805340@qq.com';
    $('#copy-right').html(copyRight);
}

function initZookeeperTable() {
    var events = zookeeperOperateEvents();
    var queryUrl = '/zookeeper?rnd=' + Math.random();
    return $('#zookeeper-table').bootstrapTable({
        url: queryUrl,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: '#zookeeper_table_toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                      //初始化加载第一页，默认第一页,并记录
        pageList: [5],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: false,
        singleSelect: true,                  //禁用多行选定
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        queryParams: function (params) {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1   //页码
            };
            return temp;
        }, columns: [{
            checkbox: true,
            visible: true
        }, {
            field: 'description',
            title: 'DESCRIPTION',
            align: 'center'
        }, {
            field: 'connectionString',
            title: 'CONNECTION_STRING',
            align: 'center'
        }, {
            field: 'sessionTimeout',
            title: 'SESSION_TIMEOUT_MILLISECOND',
            align: 'center'
        }, {
            field: 'operate',
            title: 'OPERATION',
            align: 'center',
            events: events,
            formatter: zookeeperOperateFormatter
        }]
    });
}

function zookeeperOperateFormatter(value, row, index) {
    var element =
        "<button style='margin-right: 15px;' class='btn_update_zookeeper'><span class='glyphicon glyphicon-edit'>UPDATE</span></button>" +
        "<button class='btn_delete_zookeeper'><span class='glyphicon glyphicon-remove'>DELETE</span></button>";
    return element;
}

function zookeeperOperateEvents() {
    var events = window.operateEvents = {
        'click .btn_update_zookeeper': function (e, value, row, index) {
            clearPostZookeeperModal();
            $('#post_zookeeper_modal_title').html('UPDATE');
            $('#post_id_text').val(row.id);
            $('#post_description_text').val(row.description);
            $('#post_connection_string_text').val(row.connectionString);
            $('#post_session_timeout_millisecond_text').val(row.sessionTimeout);
            $('#post_zookeeper_modal').modal('show');
        },
        'click .btn_delete_zookeeper': function (e, value, row, index) {
            $('#delete_confirm_id_text').val(row.id);
            $('#delete_confirm_modal').modal('show');
        }
    };
    return events;
}

function saveZookeeper() {
    $('#btn_add_zookeeper').click(function () {
        clearPostZookeeperModal();
        $('#post_zookeeper_modal_title').html('CREATE');
        $('#post_zookeeper_modal').modal('show');
    });
}

function postConfirmZookeeper() {
    $('#btn_post_zookeeper').click(function () {
        var bootstrapValidator = $('#post_zookeeper_form').data('bootstrapValidator');
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            $('#post_zookeeper_modal').modal('hide');
            $.ajax({
                url: '/zookeeper',
                type: 'POST',
                dataType: 'json',
                data: {
                    id: $('#post_id_text').val(),
                    connectionString: $('#post_connection_string_text').val(),
                    description: $('#post_description_text').val(),
                    sessionTimeout: $('#post_session_timeout_millisecond_text').val()
                },
                success: function (response) {
                    if (response && response.code && 2000 === response.code) {
                        $('#zookeeper-table').bootstrapTable('refresh');
                        toastr.success('Operate successfully！', 'Success');
                    } else {
                        toastr.error('Operate abortively！Message:' + response.message, 'Error');
                    }
                }
            });
        }
    });
}

function deleteConfirmZookeeper() {
    $('#btn_delete_confirm').click(function () {
        $('#delete_confirm_modal').modal('hide');
        var id = $('#delete_confirm_id_text').val();
        if (id) {
            $.ajax({
                url: '/zookeeper/' + id,
                type: 'DELETE',
                dataType: 'json',
                success: function (response) {
                    if (response && response.code && 2000 === response.code) {
                        $('#zookeeper-table').bootstrapTable('refresh');
                        toastr.success('Operate successfully！', 'Success');
                    } else {
                        toastr.error('Operate abortively！', 'Error');
                    }
                }
            });
        } else {
            toastr.error('Operate abortively！You should choose a row to delete!', 'Error');
        }
    });
}

function initPathTree() {
    $('#zookeeper-table').on('click-row.bs.table', function (e, row, element, field) {
        var id = row.id;
        if (!id) {
            toastr.error('Selected id of zookeeper table could not be found！You must choose one zookeeper row to operate!', 'Error');
            return false;
        }
        $('#zookeeper-id').val(id);
        $('#path_tree_btn_div').css('display', 'block');
        $.getJSON('/tree', {id: id, path: '/', rnd: Math.random()}, function (data) {
            $('#path_tree').treeview({
                data: data,
                onNodeSelected: function (event, node) {
                    $.getJSON('/node', {id: id, path: node.fullPath, rnd: Math.random()}, function (response) {
                        if (response && 2000 === response.code && response.data) {
                            $('#node-fullPath').val(response.data.path);
                            if (response.data.statMetadata) {
                                $('#czxid').val(response.data.statMetadata.czxid);
                                $('#ctime').val(response.data.statMetadata.ctime);
                                $('#cversion').val(response.data.statMetadata.cversion);
                                $('#aversion').val(response.data.statMetadata.aversion);
                                $('#dataLength').val(response.data.statMetadata.dataLength);
                                $('#ephemeralOwner').val(response.data.statMetadata.ephemeralOwner);
                                $('#mtime').val(response.data.statMetadata.mtime);
                                $('#mzxid').val(response.data.statMetadata.mzxid);
                                $('#numChildren').val(response.data.statMetadata.numChildren);
                                $('#pzxid').val(response.data.statMetadata.pzxid);
                                $('#version').val(response.data.statMetadata.version);
                            }
                            if (response.data.aclMetadata) {
                                $('#id').val(response.data.aclMetadata.id);
                                $('#perms').val(response.data.aclMetadata.perms);
                                $('#scheme').val(response.data.aclMetadata.scheme);
                            }
                            if (response.data.data) {
                                $('#node-data-textarea').html(response.data.data);
                            }
                        } else {
                            toastr.error('Get data info failed for path [' + node.fullPath + ']！', 'Error');
                        }
                    });
                }
            });
        });
    });
}


function loadSelectedNodeInfo(id, fullPath) {
    $.getJSON('/node', {id: id, path: fullPath, rnd: Math.random()}, function (response) {
        if (response && 2000 === response.code && response.data) {
            if (response.data.statMetadata) {
                $('#czxid').val(response.data.statMetadata.czxid);
                $('#ctime').val(response.data.statMetadata.ctime);
                $('#cversion').val(response.data.statMetadata.cversion);
                $('#aversion').val(response.data.statMetadata.aversion);
                $('#dataLength').val(response.data.statMetadata.dataLength);
                $('#ephemeralOwner').val(response.data.statMetadata.ephemeralOwner);
                $('#mtime').val(response.data.statMetadata.mtime);
                $('#mzxid').val(response.data.statMetadata.mzxid);
                $('#numChildren').val(response.data.statMetadata.numChildren);
                $('#pzxid').val(response.data.statMetadata.pzxid);
                $('#version').val(response.data.statMetadata.version);
            }
            if (response.data.aclMetadata) {
                $('#id').val(response.data.aclMetadata.id);
                $('#perms').val(response.data.aclMetadata.perms);
                $('#scheme').val(response.data.aclMetadata.scheme);
            }
            if (response.data.data) {
                $('#node-data-textarea').val(response.data.data);
            }
        } else {
            toastr.error('Get data info failed for path [' + fullPath + ']！', 'Error');
        }
    });
}

function registerUpdateDataButton() {
    $('#btn-update-data').click(function () {
        updateNodeData();
    });
}

function updateNodeData() {
    var id = $('#zookeeper-id').val();
    var nodePath = $('#node-fullPath').val();
    if (id && nodePath) {
        $.ajax({
            url: '/node',
            type: 'POST',
            dataType: 'json',
            data: {
                id: id,
                path: nodePath,
                data: $('#node-data-textarea').val()
            },
            success: function (response) {
                if (response && 2000 === response.code) {
                    toastr.success('Update node data success!', 'Success');
                    loadSelectedNodeInfo(id, nodePath);
                } else {
                    toastr.error('Update node data failed!', 'Error');
                }
            }
        });
    } else {
        toastr.error('Update node data failed for path id or nodePath is not found!', 'Error');
    }
}

function registerAddNodeButton() {
    $('#btn-add-node').click(function () {
        var selectedNode = $('#path_tree').treeview('getSelected');
        if (typeof(selectedNode) === "undefined" || null === selectedNode || isEmpty(selectedNode)) {
            toastr.error('You should choose one node to operate!', 'Error');
            return false;
        }
        var firstNode = selectedNode.shift();
        var parentPath = firstNode.fullPath;
        var parentNodeId = firstNode.nodeId;
        var title = 'Add node for parent path [' + parentPath + ']';
        clearAddNodeModal();
        $('#add_node_modal_title').html(title);
        $('#parentPath').val(parentPath);
        $('#parentNodeId').val(parentNodeId);
        $('#add_node_modal').modal('show');
    });
}

function registerConfirmAddNodeButton() {
    $('#btn_add_node_confirm').click(function () {
        var id = $('#zookeeper-id').val();
        var path = $('#add_node_path_text').val();
        var parentPath = $('#parentPath').val();
        var bootstrapValidator = $('#add_node_form').data('bootstrapValidator');
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            $('#add_node_modal').modal('hide');
            if (id && path && parentPath) {
                var nodePath;
                if ('/' === parentPath) {
                    if (0 === path.indexOf('/')) {
                        nodePath = path;
                    } else {
                        nodePath = parentPath + path;
                    }
                } else {
                    if (0 === path.indexOf('/')) {
                        nodePath = parentPath + path;
                    } else {
                        nodePath = parentPath + '/' + path;
                    }
                }
                $.ajax({
                    url: '/node',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        id: id,
                        path: nodePath,
                        data: $('#add_node_data_textarea').val()
                    },
                    success: function (response) {
                        if (response && 2000 === response.code && response.data) {
                            toastr.success('Create node data success!', 'Success');
                            var parentNodeId = $('#parentNodeId').val();
                            var pathToUse;
                            if (0 === path.indexOf('/')) {
                                pathToUse = path.substring(1, path.length);
                            } else {
                                pathToUse = path;
                            }
                            var nodeData = {
                                fullPath: nodePath,
                                href: '#' + pathToUse,
                                parentPath: parentPath,
                                path: pathToUse,
                                text: pathToUse
                            };
                            $('#path_tree').treeview('addNode', [parseInt(parentNodeId), {node: nodeData}]);
                        } else {
                            toastr.error('Create node data failed!', 'Error');
                        }
                    }
                });
            } else {
                toastr.error('Create node data failed for path id、path or parentPath is not found!', 'Error');
            }
        }
    });
}

function registerDeleteNodeButton() {
    $('#btn-del-node').click(function () {
        var selectedNode = $('#path_tree').treeview('getSelected');
        if (typeof(selectedNode) === "undefined" || null === selectedNode || isEmpty(selectedNode)) {
            toastr.error('You should choose one node to operate!', 'Error');
            return false;
        }
        var firstNode = selectedNode.shift();
        var nodeId = firstNode.nodeId;
        var path = firstNode.fullPath;
        var id = $('#zookeeper-id').val();
        if (id && path && nodeId) {
            $.ajax({
                url: '/node/delete',
                type: 'POST',
                dataType: 'json',
                data: {
                    id: id,
                    path: path
                },
                success: function (response) {
                    if (response && response.code && 2000 === response.code && response.data) {
                        toastr.success('Delete node successfully！', 'Success');
                        var intNodeId = parseInt(nodeId);
                        var tree = $('#path_tree');
                        tree.treeview('deleteNode', [intNodeId, {silent: true}]);
                        tree.treeview('unselectNode', [intNodeId, {silent: true}]);
                        var nodeFullPath = $('#node-fullPath').val();
                        if (nodeFullPath === path) {
                            clearNodeStateAndNodeData();
                        }
                    } else {
                        toastr.error('Delete node abortively！', 'Error');
                    }
                }
            });
        } else {
            toastr.error('Delete node data failed for path id、path or nodeId is not found!', 'Error');
        }
    });
}

function clearNodeStateAndNodeData() {
    $('#czxid').val('');
    $('#ctime').val('');
    $('#cversion').val('');
    $('#aversion').val('');
    $('#dataLength').val('');
    $('#ephemeralOwner').val('');
    $('#mtime').val('');
    $('#mzxid').val('');
    $('#numChildren').val('');
    $('#pzxid').val('');
    $('#version').val('');
    $('#id').val('');
    $('#perms').val('');
    $('#scheme').val('');
    $('#node-data-textarea').val('');
}

function clearAddNodeModal() {
    $('#parentPath').val('');
    $('#parentNodeId').val('');
    $('#add_node_path_text').val('');
    $('#add_node_data_textarea').val('');
}

function clearPostZookeeperModal() {
    $('#post_id_text').val('');
    $('#post_description_text').val('');
    $('#post_connection_string_text').val('');
    $('#post_session_timeout_millisecond_text').val('');
}

function isEmpty(value) {
    return (Array.isArray(value) && value.length === 0)
        || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
}


function registerAddNodeFormValidator() {
    $('#add_node_form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            add_node_path_text: {
                message: 'Path must not be empty',
                validators: {
                    notEmpty: {
                        message: 'Path must not be empty'
                    }
                }
            }
        }
    });
}

function registerPostZookeeperInfoValidator() {
    $('#post_zookeeper_form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            post_description_text: {
                message: 'Description must not be empty',
                validators: {
                    notEmpty: {
                        message: 'Description must not be empty'
                    }
                }
            },
            post_connection_string_text: {
                message: 'ConnectionString must not be empty',
                validators: {
                    notEmpty: {
                        message: 'ConnectionString must not be empty'
                    }
                }
            },
            post_session_timeout_millisecond_text: {
                message: 'Session timeout millisecond must not be empty',
                validators: {
                    notEmpty: {
                        message: 'Session timeout millisecond must not be empty'
                    },
                    regexp: {
                        regexp: /^([0-9][0-9]*)$/,
                        message: 'Session timeout millisecond must be number'
                    }
                }
            }
        }
    });
}


