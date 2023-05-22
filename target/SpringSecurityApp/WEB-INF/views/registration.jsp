<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Регистрация</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Регистрация нового пользователя</h2>
        <spring:bind path="INN">
            <div class="form-group">
                <form:input type="text" path="INN" class="form-control" placeholder="ИНН"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="surname">
            <div class="form-group">
                <form:input type="text" path="surname" class="form-control" placeholder="Фамилия"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="name">
            <div class="form-group">
                <form:input type="text" path="name" class="form-control" placeholder="Имя"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="secondSurname">
            <div class="form-group">
                <form:input type="text" path="secondSurname" class="form-control" placeholder="Отчество"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="phone">
            <div class="form-group">
                <form:input type="text" path="phone" class="form-control" placeholder="Номер телефона"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="userMail">
            <div class="form-group">
                <form:input type="text" path="userMail" class="form-control" placeholder="Почта"
                            autofocus="true"></form:input>
            </div>
        </spring:bind>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Имя пользователя(логин)"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Пароль"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" class="form-control"
                            placeholder="Подтверждение пароля"></form:input>
                <form:errors path="confirmPassword"></form:errors>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрироваться</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>