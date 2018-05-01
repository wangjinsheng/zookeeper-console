<div class="modal fade" id="delete_confirm_modal" tabindex="-1" role="dialog"
     aria-labelledby="delete_confirm_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="delete_confirm_modal_title">
                   Delete Warning
                </h4>
            </div>
            <div class="modal-body">
                <input hidden id="delete_confirm_id_text"/>
                <p id="delete_confirm_modal_message">
                    You are going to delete one row of zookeeper info table
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="btn_delete_confirm" class="btn btn-primary">Ok</button>
            </div>
        </div>
    </div>
</div>