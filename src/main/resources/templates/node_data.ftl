<div id="node_data" style="margin-top: 15px">
    <div class="col-md-6" id="node_state" style="height: 450px;border: 1px solid #dddddd">
        <h2>Node State</h2>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">czxid</span>
                    <input id="czxid" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">mzxid</span>
                    <input id="mzxid" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">ctime</span>
                    <input id="ctime" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">mtime</span>
                    <input id="mtime" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">version</span>
                    <input id="version" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">cversion</span>
                    <input id="cversion" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">aversion</span>
                    <input id="aversion" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">dataLength</span>
                    <input id="dataLength" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">numChildren</span>
                    <input id="numChildren" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">pzxid</span>
                    <input id="pzxid" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">scheme</span>
                    <input id="scheme" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">id</span>
                    <input id="id" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-12">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">perms</span>
                    <input id="perms" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
        <div class="row node-state-row">
            <div class="col-sm-12">
                <div class="input-group">
                    <span class="input-group-addon node-state-label">ephemeralOwner</span>
                    <input id="ephemeralOwner" type="text" class="form-control node-state-input" disabled>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6" id="node_data_content" style="height: 450px;border: 1px solid #dddddd">
        <h2>Node Data</h2>
        <input id="node-fullPath" type="text" hidden>
        <form role="form" action="#">
            <div class="form-group">
                <textarea id="node-data-textarea" class="form-control" rows="12"></textarea>
            </div>
            <div class="input-group">
                <button id="btn-update-data" type="button" class="btn btn-primary">UPDATE</button>
            </div>
        </form>
    </div>
</div>