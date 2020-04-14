<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>My TimesSeries</title>
  </head>
    <body>
    <style><%@include file="/WEB-INF/jsp/resources/css/bootstrap.min.css"%></style>
    <style><%@include file="/WEB-INF/jsp/resources/css/common.css"%></style>
            <div class="container">
                    <div class="form-group">
                      <h2 class="form-heading">Modify Event</h2>
                    <form action="/getSeries/${serie_id}/updateEvent/${event.id}" method="post" class="form-signin" id="eventForm">
                        <div class="form-group">
                            <label for="value">Enter your value: </label>
                            <input type="text" class="form-control" name="value" id="value" value="<c:out value='${event.value}'/>" required>
                        </div>
                        <div class="form-group">
                            <label for="comment">Enter your comment: </label>
                            <input type="text" class="form-control" name="comment" id="comment" value="<c:out value='${event.comments}'/>">
                        </div>
                        <c:set var="date" value="${event.event_date}" scope="page" />
                        <div class="form-group">
                            <label for="time">Choose your date: </label>
                            <input name="time" class="form-control" id="time" type="datetime-local" step="1" value="<fmt:formatDate value='${date.time}' pattern='yyyy-MM-dd\'T\'hh:mm:s' type='both' dateStyle='short' />" />
                        </div>
                        <div class="form-group"  id="TagArea">
                        <br /><label for="tags">Enter tags : </label>
                        <textarea class="form-control"  id="tags" name="tags" varStatus="status">
                        <c:forEach var="word" items="${event.tag}">
                            ${word}<c:if test="${!status.last}">, </c:if>
                          </c:forEach>
                          </textarea
                        <input class="form-control"  id="tags" name="tags" value="<c:out value='${event.tag}' />"/>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
                    </form>
                </div>
    </div>
    </body>
</html>