<#assign base = context.contextPath>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Zookeeper Console</title>
    <link rel="shortcut icon" href="${base}/img/favicon.ico"/>
    <link href="${base}/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/css/bootstrap-table.css" rel="stylesheet">
    <link href="${base}/css/bootstrap-treeview.min.css" rel="stylesheet">
    <link href="${base}/css/toastr.css" rel="stylesheet">
    <link href="${base}/css/bootstrapValidator.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="${base}/js/jquery-3.3.1.js"></script>
    <script src="${base}/js/bootstrap.js"></script>
    <script src="${base}/js/bootstrap-table.js"></script>
    <script src="${base}/js/bootstrap-treeview.js"></script>
    <script src="${base}/js/toastr.min.js"></script>
    <script src="${base}/js/bootstrapValidator.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        #path-tree-div {
            border: 1px solid #dddddd;
            height: 480px;
            overflow-x: auto;
        }

        #node-data-div {
            border: 1px solid #dddddd;
            height: 480px;
        }

        .node-state-label {
            font-size: 20px;
            background-color: #337ab7;
            color: #fff
        }

        .node-state-input {
            text-align: center;
        }

        .node-state-row {
            margin-bottom: 15px;
        }
        #node-data-textarea{
            height: 330px;
            resize:none;
        }

        #add_node_data_textarea{
            height: 200px;
            resize:none;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12" id="zookeeper-table-div">
        <#include "zookeeper_table.ftl">
        </div>

        <div class="col-md-12" id="data-div">
            <input id="zookeeper-id" type="text" hidden>
            <div id="path_tree_btn_div" class="input-group glideDiv0" style="margin-top: 15px;display: none;">
                <button id="btn-add-node" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span></button>
                <button id="btn-del-node" style="margin-left: 15px" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span></button>
            </div>
            <div class="col-md-4" id="path-tree-div">
            <#include "path_tree.ftl">
            </div>
            <div class="col-md-8" id="node-data-div">
            <#include "node_data.ftl">
            </div>
        </div>
    </div>
<#include "footer.ftl">
</div>
<script src="${base}/js/console.js"></script>
<!-- 外部引入的模态框 -->
<#include "post_zookeeper_modal.ftl">
<#include "delete_confirm_modal.ftl">
<#include "add_node_modal.ftl">
</body>
</html>