<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--  <meta charset="UTF-8">--%>
<%--  <title>Создание заявки</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<c:if test="${pageContext.request.userPrincipal.name != null}">--%>
<%--  <form id="logoutForm" method="post" action="${contextPath}/logout">--%>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--  </form>--%>
<%--  <h2>Данные пользователя ${pageContext.request.userPrincipal.name} --%>
<%--  </h2>--%>
<%--  <sec:authorize access="hasRole('ROLE_USER')"> <!-- Проверяем, что пользователь авторизован -->--%>
<%--    <a href="/welcome" class="btn">Назад</a> <!-- Отображаем ссылку на профиль -->--%>
<%--  </sec:authorize>--%>
<%--</c:if>--%>
<%--</div>--%>
<%--<form name="form" action="#" th:action="@{/createTask}" method="post" th:object="${newTask}" enctype="multipart/form-data">--%>
<%--  <h1>Форма заявки</h1>--%>
<%--  <div><label>Наименования заявки</label><input type="text" th:field="*{nameTask}"></div>--%>
<%--  <div>--%>
<%--    <select th:field="*{taskImportance}">--%>
<%--      <option th:each="listImportance : ${listImportance}"--%>
<%--              th:value="${listImportance}"--%>
<%--              th:text="${listImportance}"></option>--%>
<%--    </select>--%>
<%--  </div>--%>


<%--  <div>--%>
<%--    <label>Содержание</label>--%>
<%--    <textarea name="soderzhanie" th:field="*{taskContent}"></textarea>--%>
<%--  </div>--%>
<%--  <div>--%>
<%--    <label>Прикрепить файл</label>--%>
<%--    <input type="file" th:name="${fileUser.name}" th:field="${fileUser.file}">--%>
<%--  </div>--%>
<%--  <input value="Отправить" type="submit">--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
