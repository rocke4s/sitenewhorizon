<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <div class="container">
        <div class="header">
            <div class="logo">
                <img src="logo.png" alt="Логотип">
                <h1>Мой профиль</h1>
            </div>
            <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
            </form>

            <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
            </h2>
        </div>
        <div class="wrapper">
            <div class="buttons">
                <form:form method="GET" action="/profile?${_csrf.parameterName}=${_csrf.token}">

                    <button class="show-user" type="submit">Показать данные пользователя</button>
                </form:form>
            </div>
            <div class="buttons">
                <form:form method="GET" action="/rating?${_csrf.parameterName}=${_csrf.token}">
                    <input type="hidden" name="Profile" value="${Profile}"/>
                    <button class="show-tasks" type="submit">Показать рейтинг списка задач</button>
                </form:form>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</c:if>
</body>
</html>