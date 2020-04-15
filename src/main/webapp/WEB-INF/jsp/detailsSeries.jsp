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
                                    <button type="button" class="btn btn-default btn-sm btn-primary" onClick="document.location='/getSeries/${serie.id}/updateEvent/${event.id}';">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
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
    </script>
    </body>
</html>
