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
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/profile-css.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <img src="${contextPath}/resources/image/logo.png" alt="Логотип">
            <h1>Данные пользователя ${pageContext.request.userPrincipal.name}</h1>
        </div>
        <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
        </form>
        <a href="/welcome" class="btn">Назад</a>
        <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
    </div>
</div>
<div class="user-data">
    <div class="user-data__item">
        <p>ФИО:</p>
    </div>
    <div class="user-data__item">
        <p>${Profile.name}</p>
    </div>
    <div class="user-data__item">
        <p>Организация:</p>
    </div>
    <div class="user-data__item">
        <p>${Profile.orgName}</p>
    </div>
    <div class="user-data__item">
        <p>Задолженность:</p>
    </div>
    <div class="user-data__item">
        <p>${Profile.debt}</p>
    </div>
    <div class="user-data__item">
        <p>Телефон:</p>
    </div>
    <div class="user-data__item">
        <p>${Profile.telefon}</p>
    </div>
    <div class="user-data__item">
        <p>Почта:</p>
    </div>
    <div class="user-data__item">
        <p>${Profile.userMail}</p>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>