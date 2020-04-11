<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
  <c:forEach items="${time_series}" var="serie">
    <tr>
      <td><c:out value="${serie.title}" /></td>
      <td><c:out value="${serie.hasModificationRight}" /></td>
    </tr>
  </c:forEach>
</table>