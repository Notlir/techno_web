<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="form-heading">Add Event</h2>
            </div>
            <div class="modal-body">
                <form action="/getSeries/${event.id}/newEvent" method="post" class="form-signin" id="eventForm">
                    <div class="form-group">
                        <label for="value">Enter your value: </label>
                        <input type="text" class="form-control" name="value" id="value" required>
                    </div>
                    <div class="form-group">
                        <label for="comment">Enter your comment: </label>
                        <input type="text" class="form-control" name="comment" id="comment">
                    </div>
                    <div class="form-group">
                        <input type="button" class="form-control" id="addTag" value="Add Tag"/>
                    </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $('#addtag').click(function(){
        document.write("<jsp:include page='newTag.jsp'/>");
    }
</script>