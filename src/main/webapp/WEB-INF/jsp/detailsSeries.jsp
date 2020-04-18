<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <h2 class="form-heading"><c:out value="${serie.title}" /></h2>
            <p>
                <c:if test="${not empty serie.description}">
                    <c:out value="${serie.description}" />
                </c:if>
            </p>
            <h3 class="form-heading">Event(s)</h3>
            <table class="table">
              <tr>
                <th>Value</th>
                <th>Comment</th>
                <th>Time</th>
                <th>Tags</th>
                <th>
                    <button class="btn btn-default btn-sm btn-primary"  data-toggle="modal" data-target="#myModal">Add Event</button>
                    <jsp:include page='newEvent.jsp'>
                                      <jsp:param name="id" value="${serie.id}"/>
                    </jsp:include>
                </th>
                <th>
                    <button type="button" class="btn btn-default btn-sm btn-info" onClick="document.location='/getSeries/${serie.id}/canvas';">
                        <span class="glyphicon glyphicon-info-sign"></span>
                    </button>
                </th>
              </tr>
                <c:forEach items="${serie.eventList}" var="event">
                        <tr>
                           <td><c:out value="${event.value}" /></td>
                           <td><c:out value="${event.comment}" /></td>
                           <td> <fmt:formatDate value="${event.time.time}" type="both" dateStyle="short" /></td>
                           <td>
                               <c:if test="${not empty event.tags}">
                                    <c:forEach items="${event.tags}" var="tag">
                                        <c:out value="${tag}" />
                                    </c:forEach>
                                </c:if>
                           </td>
                            <td>
                            <table class="table">
                                <td>
                                    <button type="button" class="btn btn-default btn-sm btn-warning" data-toggle="modal" data-target="#myUpdateModal-${event.id}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                    <div class="modal fade" id="myUpdateModal-${event.id}" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h2 class="form-heading">Modify Event</h2>
                                                </div>
                                                <div class="modal-body">
                                                    <form class="form-signin" id="UpdateEventForm" name="eventForm">
                                                        <div class="form-group">
                                                            <label for="value">Enter your value: </label>
                                                            <input type="number" class="form-control" name="value" id="value" value="<c:out value='${event.value}'/>" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="comment">Enter your comment: </label>
                                                            <input type="text" class="form-control" name="comment" id="comment" value="<c:out value='${event.comment}'/>">
                                                        </div>
                                                        <c:set var="date" value="${event.time}" scope="page" />
                                                        <div class="form-group">
                                                            <label for="time">Choose your date: </label>
                                                            <input name="time" class="form-control" id="time" type="datetime-local" step="1" value="<fmt:formatDate value='${date.time}' pattern='yyyy-MM-dd\'T\'hh:mm:s' type='both' dateStyle='short' />" />
                                                        </div>
                                                        <div class="form-group"  id="TagArea">
                                                        <label for="tags">Enter tags : </label>
                                                        <c:set var="xv"></c:set>
                                                        <c:forEach items="${event.tags}" var="x" varStatus="status">
                                                            <c:if test="${not empty x}">
                                                            <c:choose>
                                                                <c:when test="${status.first}"><c:set var="xv" value="${x}"></c:set></c:when>
                                                                <c:otherwise><c:set var="xv" value="${xv}, ${x}"></c:set></c:otherwise>
                                                            </c:choose>
                                                            </c:if>
                                                        </c:forEach><textarea cols="45" rows="5" id="tags" name="tags">${xv}</textarea>
                                                        </div>
                                                        <button class="btn btn-lg btn-primary btn-block" type="button" onClick="updateEvent('${event.id}')">Add</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-default btn-sm btn-danger" onClick="deleteEvent('${event.id}');">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </td>
                            </table>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <script type = "text/javascript">
        function deleteEvent(eventId){
            $.ajax({
                url: '/getSeries/${serie.id}/deleteEvent/'+eventId,
                method: 'DELETE',
                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
                success: function () {
                    alert('record has been deleted');
                    location.reload();
                },
                error: function () {
                    alert('/getSeries/${serie.id}/deleteEvent/'+eventId);
                }
            })
        }
        
        function updateEvent(eventId){
            $.ajax({
                url: "/getSeries/${serie.id}/updateEvent/"+eventId,
                method: 'PUT',
                data:$("#UpdateEventForm").serialize(),
                contentType:'application/x-www-form-urlencoded; charset=UTF-8',
                success: function () {
                    alert('record has been updated');
                    location.reload();
                },
                error: function () {
                    alert('/getSeries/${serie.id}/deleteEvent/'+eventId);
                }
            })
        }
    </script>
    </body>
</html>
