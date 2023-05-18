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
<style>
    body {
        background-color: #f8f8f8;
        font-family: Arial, sans-serif;
        color: #555;
        margin: 0;
        padding: 0;
    }

    .header {
        background-color: #fff;
        border-bottom: 1px solid #ccc;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
    }

    .logo {
        display: flex;
        align-items: center;
    }

    .logo img {
        width: 50px;
        height: 50px;
        margin-right: 10px;
    }

    h1 {
        margin: 0;
        font-size: 24px;
    }

    .logout {
        background-color: #449d44;
        color: #fff;
        border: none;
        border-radius: 4px;
        padding: 10px 15px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .logout:hover {
        background-color: #3c8c3c;
    }

    .wrapper {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
    }

    .buttons {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 40px;
        padding: 20px;
    }

    .buttons button {
        background-color: #e7e7e7;
        color: #555;
        border: none;
        border-radius: 10px;
        padding: 20px 15px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .buttons button:hover {
        background-color: #ccc;
    }
    .btn {
        display: inline-block; /* Строчно-блочный элемент */
        background: #8C959D; /* Серый цвет фона */
        color: #fff; /* Белый цвет текста */
        padding: 1rem 1.5rem; /* Поля вокруг текста */
        text-decoration: none; /* Убираем подчёркивание */
        border-radius: 3px; /* Скругляем уголки */
    }
    .user-data {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-gap: 20px;
        margin: 20px;
    }

    .user-data__item {
        border: 1px solid #ccc;
        padding: 10px;
        background-color: #449d44;
        color:white;
        font-size: 18px;
    }
</style>
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