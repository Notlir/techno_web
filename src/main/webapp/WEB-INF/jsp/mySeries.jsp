<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>My TimesSeries</title>
  </head>
	<body>
	<style><%@include file="/WEB-INF/jsp/resources/css/bootstrap.min.css"%></style>
    <style><%@include file="/WEB-INF/jsp/resources/css/common.css"%></style>
    <div class="form-group">
    <div class="form-signin">
    <h2 class="form-heading">My TimesSeries</h2>
        <table>
          <c:forEach items="${list}" var="list">
                <tr
                onmouseover="ChangeColor(this, true);"
                onmouseout="ChangeColor(this, false);"
                onclick="document.location='/getSeries/${list.id}';"
                >
                  <td><h3><c:out value="${list.title}" /></td></h3><br />
                  <c:if test="${not empty list.description}">
                    <c:out value="${list.description}" />
                  </c:if>

                </tr>

          </c:forEach>
        </table>
        </div>
    </div>
</body>
<script type="text/javascript">
       function ChangeColor(tableRow, highLight)
       {
       if (highLight)
       {
         tableRow.style.backgroundColor = '#dcfac9';
       }
       else
       {
         tableRow.style.backgroundColor = '#eee';
       }
     }
     </script>
</html>