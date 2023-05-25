<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

  <title>Rit.Рейтинг задач</title>
  <style type="text/css">
  </style>
  <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div align="left">
  <c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="post" action="${contextPath}/logout">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <table class="table">
      <thead>
      <tr>
        <th>Название документа</th>
        <th>Оценка работы</th>
      </tr>
      </thead>
      <c:forEach var="rating" items="${rating}">
      <tbody>
      <tr>
        <td>${rating.getNameTask()}</td>
        <td>${rating.getRating()}</td>
      </tr>
      </tbody>
      </c:forEach>
    </table>
    </c:if>
</body>
</html>
