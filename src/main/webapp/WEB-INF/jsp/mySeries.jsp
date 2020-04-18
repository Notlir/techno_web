<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>My TimesSeries</title>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
              <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  </head>
	<body>
	<style><%@include file="/WEB-INF/jsp/resources/css/bootstrap.min.css"%></style>
    <style><%@include file="/WEB-INF/jsp/resources/css/common.css"%></style>
    <div class="form-group">
    <div class="form-signin">
    <h2 class="form-heading">My TimesSeries</h2>
      <button class="btn btn-lg btn-primary btn-block"  data-toggle="modal" data-target="#newSeriesModal">New Serie</button>
                    <div class="modal fade" id="newSeriesModal" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h2 class="form-heading">Add Event</h2>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="/getSeriesForMe/newSeries" method="post" class="form-signin" id="seriesForm">
                                                            <div class="form-group">
                                                                <label for="title">Enter title: </label>
                                                                <input type="text" class="form-control" name="title" id="title" required>
                                                            </div><div class="form-group">
                                                                <label for="description">Enter a description: </label>
                                                                <input type="text" class="form-control" name="description" id="description" required>
                                                            </div>
                                                        <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
        <table class="table">
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
          <c:forEach items="${list}" var="element">
                <tr>
                    <td onmouseover="this.style.background = '#dcfac9'; this.style.cursor='pointer'" onmouseout="this.style.background = '#eee'"  onclick="document.location='/getSeries/${element.id}';">
                      <c:out value="${element.title}" /><br />
                    </td>
                    <td>
                      <c:if test="${not empty element.description}">
                        <c:out value="${element.description}" />
                      </c:if>
                    </td>
                    <td>
                        <table class="table">
                            <tr>
                                <td class="actionTab">
                                    <button type="button" class="btn btn-default btn-sm btn-success" onclick="document.location='/getSeries/${element.id}';">
                                        <span class="glyphicon glyphicon-eye-open"></span>
                                    </button>
                                </td>
                                <td class="actionTab">
                                    <button type="button" class="btn btn-default btn-sm btn-primary" data-toggle="modal" data-target="#modal-toShare-${element.id}">
                                        <span class="glyphicon glyphicon-share"></span>
                                    </button>
                                </td>


                                 <div class="modal fade" id="modal-toShare-${element.id}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h2 class="form-heading">Share series</h2>
                                            </div>
                                            <div class="modal-body">
                                                <form action="/shareToUser/${element.id}" method="post" class="form-signin" id="updateSeriesForm">
                                                    <div class="form-group">
                                                        <label for="title">Give modification right ?</label>
                                                        <input type="radio" class="radio" name="writeRight" value="1" required/>Yes
                                                        <input type="radio" class="radio" name="writeRight" value="0" required/>No
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="description">Share to : </label>
                                                        <input type="text" class="form-control" name="to" id="to" placeholder="Enter Login.." required/>
                                                    </div>
                                                <button class="btn btn-lg btn-primary btn-block" type="submit" id="updateSeries">Share</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>



                                <td rowspan="2" class="actionTab">
                                    <button type="button" class="btn btn-default btn-sm btn-info" onClick="document.location='/getSeries/${element.id}/canvas';">
                                        <span class="glyphicon glyphicon-info-sign"></span>
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td class="actionTab">
                                    <button type="button" class="btn btn-default btn-sm btn-warning" data-toggle="modal" data-target="#modal-${element.id}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                </td class="actionTab">
                                <div class="modal fade" id="modal-${element.id}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h2 class="form-heading">Modify series</h2>
                                            </div>
                                            <div class="modal-body">
                                                <form class="form-signin" id="updateSeriesForm">
                                                    <div class="form-group">
                                                        <label for="title">Enter title: </label>
                                                        <input type="text" class="form-control" name="title" id="title" value="${element.title}" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="description">Enter a description: </label>
                                                        <input type="text" class="form-control" name="description" id="description" value="${element.description}" required>
                                                    </div>
                                                <button class="btn btn-lg btn-primary btn-block" id="updateSeries" type="button" onClick="updateSerie('${element.id}')">Add</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <td class="actionTab">
                                    <button type="button" class="btn btn-default btn-sm btn-danger" onClick="deleteEvent('${element.id}');">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
          </c:forEach>
        </table>
        </div>
    </div>
</body>
<script type = "text/javascript">
function deleteEvent(serieId){
    $.ajax({
         url: '/getSeriesForMe/'+serieId+'/deleteSeries',
         method: 'DELETE',
         contentType:'application/x-www-form-urlencoded; charset=UTF-8',
         success: function () {
             alert('record has been deleted');
             location.reload();
         },
         error: function () {
             alert('/getSeriesForMe/'+serieId+'/deleteSerie');
             location.reload();
         }
    })
     }
function updateSerie(serieId){
    $.ajax({
         url: '/getSeriesForMe/'+serieId+'/updateSeries',
         method: 'PUT',
         data:$("#updateSeriesForm").serialize(),
         contentType:'application/x-www-form-urlencoded; charset=UTF-8',
         success: function () {
             alert('record has been updated');
             location.reload();
         },
         error: function () {
             alert('/getSeriesForMe/'+serieId+'/updateSeries');
             location.reload();
         }
    })
     }
     
 </script>

</html>