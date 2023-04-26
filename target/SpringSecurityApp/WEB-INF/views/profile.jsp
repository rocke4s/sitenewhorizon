<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Profile</title>

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
        <form id="logoutForm" method="post" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Данные пользователя ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
        <sec:authorize access="hasRole('ROLE_USER')"> <!-- Проверяем, что пользователь авторизован -->
            <a href="/welcome" class="btn">Назад</a> <!-- Отображаем ссылку на профиль -->
        </sec:authorize>
    </c:if>
</div>
<div align="center">
    <table>
        <colgroup>
            <col span="2" style="background:Khaki">
            <col span="2" style="background-color:LightCyan">
        </colgroup>
        <tr>
            <th>ФИО</th>
            <th>Организация</th>
            <th>Задолженность</th>
            <th>Номер телефона</th>
        </tr>
        <tr>
            <td>${Profile.name}</td>
            <td>${Profile.orgName}</td>
            <td>${Profile.debt}</td>
            <td>${Profile.telefon}</td>
        </tr>
    </table>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

</body>
</html>