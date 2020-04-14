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
        <table>
              <div class="form-group">
              <tr> Event(s) :
                <button class="btn btn-lg btn-primary btn-block"  data-toggle="modal" data-target="#myModal">Add Event</button>
              </tr>
              <jsp:include page='newEvent.jsp'>
                  <jsp:param name="id" value="${serie.id}"/>
              </jsp:include>
              <td>
              </td>
                    <c:forEach items="${serie.eventList}" var="event"><br />
                        <div class="container">
                        <tr>
                        value : <c:out value="${event.value}" /><br />
                        comment : <c:out value="${event.comment}" /><br />
                        time : <fmt:formatDate value="${event.time.time}" type="both" dateStyle="short" /><br />
                        <c:if test="${not empty event.tags}">
                            Tags list :
                            <c:forEach items="${event.tags}" var="tag">
                            <tr>
                                <c:out value="${tag}" />
                            </tr>
                            </c:forEach>
                        </c:if>
                        <br />
                        <button type="button" class="btn-warning" onClick="document.location='/getSeries/${serie.id}/updateEvent/${event.id}';">Modify</button>
                        <button type="button" class="btn-danger" onClick="deleteEvent('${event.id}');">Delete</button>
                        <br />
                        </tr>
                        </div>
                    </c:forEach>
              </div>
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
