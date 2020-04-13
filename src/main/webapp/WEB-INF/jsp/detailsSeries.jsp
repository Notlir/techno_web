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
              <tr> Event(s) : </tr>
              <button class="btn btn-lg btn-primary btn-block"  data-toggle="modal" data-target="#myModal">Add Event</button>
              <jsp:include page='newEvent.jsp'>
                  <jsp:param name="event" value="${event}"/>
              </jsp:include>
              <td>
              </td>
                <c:forEach items="${serie.eventList}" var="event"><br />
                <div class="container">
                <tr>
                          time : <fmt:formatDate value="${event.time.time}" type="both" dateStyle="short" /><br />
                          value : <c:out value="${event.value}" /><br />
                          comment : <c:out value="${event.comment}" /><br />

                          <c:if test="${not empty event.tags}">
                           Tags list :
                          <c:forEach items="${event.tags}" var="tag">
                          <tr>
                            <c:out value="${tag}" />
                          </tr>
                          </c:forEach>
                          </c:if>
                          <br />
                </tr>
                </div>
                </c:forEach>
              </div>
        </table>
        </div>
    </div>
    <script type="text/javascript">document.getElementById("eventForm").style.display="none";</script>
</body>
</html>
