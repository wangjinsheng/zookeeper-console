<div class="modal fade" id="add_node_modal" tabindex="-1" role="dialog"
     aria-labelledby="add_node_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="add_node_modal_title"></h4>
            </div>
            <div class="modal-body">
                <input hidden id="parentPath" type="text">
                <input hidden id="parentNodeId" type="text">
                <form id="add_node_form" class="form-horizontal" role="form" action="#">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">Path</label>
                        <div class="col-sm-11">
                            <input id="add_node_path_text" name="add_node_path_text" type="text" class="form-control"
                                   placeholder="Path">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <span class="label label-primary">Data</span>
                            <textarea id="add_node_data_textarea" name="add_node_data_textarea" class="form-control"
                                      rows="12"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="btn_add_node_confirm" class="btn btn-primary">Ok</button>
            </div>
        </div>
    </div>
</div>