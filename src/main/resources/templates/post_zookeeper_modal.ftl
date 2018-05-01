<div class="modal fade" id="post_zookeeper_modal" tabindex="-1" role="dialog"
     aria-labelledby="post_zookeeper_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="post_zookeeper_modal_title">

                </h4>
            </div>
            <div class="modal-body">
                <input hidden id="post_id_text"/>
                <form role="form" class="form-horizontal" action="#" id="post_zookeeper_form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">DESCRIPTION</label>
                        <div class="col-sm-9">
                            <input id="post_description_text" name="post_description_text" type="text"
                                   class="form-control" placeholder="Description">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">CONNECTION_STRING</label>
                        <div class="col-sm-8">
                            <input id="post_connection_string_text" name="post_connection_string_text" type="text"
                                   class="form-control"
                                   placeholder="Connection String">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-5 control-label">SESSION_TIMEOUT_MILLISECOND</label>
                        <div class="col-sm-7">
                            <input id="post_session_timeout_millisecond_text"
                                   name="post_session_timeout_millisecond_text" type="text" class="form-control"
                                   placeholder="Session Timeout Millisecond">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="btn_post_zookeeper" class="btn btn-primary" onclick="">Ok</button>
            </div>
        </div>
    </div>
</div>