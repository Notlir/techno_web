<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="form-heading">Add Event</h2>
            </div>
            <div class="modal-body">
                <form action="/getSeries/${id}/newEvent" method="post" class="form-signin" id="eventForm">
                    <div class="form-group">
                        <label for="value">Enter your value: </label>
                        <input type="text" class="form-control" name="value" id="value" required>
                    </div>
                    <div class="form-group">
                        <label for="comment">Enter your comment: </label>
                        <input type="text" class="form-control" name="comment" id="comment">
                    </div>
                    <div class="form-group">
                        <label for="time">Choose your date: </label>
                        <input name="time" class="form-control" id="time" type="datetime-local" step="1"/>
                    </div>
                    <input class="btn btn-lg btn-primary btn-block" type="button" value="Add tag" id="addTag" />
                    <div class="form-group"  id="TagArea" style="display:none">
                        <br /><label for="tags">Enter tags : </label>
                        <textarea class="form-control"  id="tags" name="tags"></textarea>
                    </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
    $('#addTag').click(function(){
        $('#TagArea').show();
    });
});
</script>

