<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>Создание заявки</title>
</head>
<body>
<http>
  <csrf disabled="true" />
</http>
<c:if test="${pageContext.request.userPrincipal.name != null}">
  <form id="logoutForm" method="post" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
  <h2>Данные пользователя ${pageContext.request.userPrincipal.name}
  </h2>
  <sec:authorize access="hasRole('ROLE_USER')"> <!-- Проверяем, что пользователь авторизован -->
    <a href="/welcome.jsp" class="btn">Назад</a> <!-- Отображаем ссылку на профиль -->
  </sec:authorize>

</div>
<%--<form:form action="/create_task" modelAttribute="newTask" method="GET" enctype="multipart/form-data">--%>
<%--  <h1>Форма заявки</h1>--%>
<%--  <div><label>Наименования заявки</label>--%>
<%--      <form:input type="text" path="nameTask" class="form-control"></form:input>--%>
<%--  <div>--%>
<%--    <form:select path="TaskImportance" >--%>
<%--      <c:forEach var="listImportance" items="${listImportance}">--%>
<%--      <option>--%>
<%--              ${listImportance}</option>--%>
<%--      </c:forEach>--%>
<%--    </form:select>--%>
<%--  </div>--%>


<%--  <div>--%>
<%--    <label>Содержание</label>--%>
<%--    <form:textarea type="text" path="TaskContent" class="form-control"></form:textarea>--%>
<%--  </div>--%>
<%--    </form:form>--%>

<form:form action="/add_file" modelAttribute="file" method="POST" enctype="multipart/form-data">
  <div>
    <label>Прикрепить файл</label>
    <h1>dota</h1>
<%--    <input type="text" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <h2>dota2</h2>
    <input type="file" value="${file}" name="file" class="form-control">
  </div>
  <input value="Отправить" type="submit">

</form:form>
</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
