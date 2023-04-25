<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Добро пожаловать!</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>.btn {
    display: inline-block; /* Строчно-блочный элемент */
    background: #8C959D; /* Серый цвет фона */
    color: #fff; /* Белый цвет текста */
    padding: 1rem 1.5rem; /* Поля вокруг текста */
    text-decoration: none; /* Убираем подчёркивание */
    border-radius: 3px; /* Скругляем уголки */
}</style>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
<%--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
        </form>

        <h2>Добро пожаловать ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
<%--        <sec:authorize access="hasRole('ROLE_USER')">--%>
<%--            <a href="/profile" class="btn">${pageContext.request.userPrincipal.name} data</a>--%>
<%--        </sec:authorize>--%>
        <div>
            <form:form method="GET" action="/profile?${_csrf.parameterName}=${_csrf.token}">

            <button type="submit">Показать данные пользователя</button>
            </form:form>
        </div>
        <div>
            <form:form method="GET" action="/tasks?${_csrf.parameterName}=${_csrf.token}">
                <input type="hidden" name="Profile" value="${Profile}"/>
                <button type="submit">Показать список задач</button>
            </form:form>
        </div>
        <div>
            <form:form method="POST" action="/new_task?${_csrf.parameterName}=${_csrf.token}">
                <input type="hidden" name="Profile" value="${User}"/>
                <button type="submit">Создать задачу</button>
            </form:form>
        </div>

    </c:if>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>