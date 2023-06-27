<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Rit.Добро пожаловать</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${contextPath}/resources/css/welcome-css.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.min.js"></script>
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
<div class="container">
    <div class="header">
        <div class="logo">
            <img src="${contextPath}/resources/image/logo.png" alt="Логотип">
            <h1>Мой профиль</h1>
        </div>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
    </div>
    <div class="wrapper">
        <div class="buttons">
            <form:form method="POST" action="/profile">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="show-user" type="submit">Показать данные пользователя</button>
            </form:form>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="buttons">
                <form:form method="POST" action="/ratings">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="show-tasks" type="submit">Показать рейтинг списка задач</button>
                </form:form>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="buttons">
                <form:form method="GET" action="/stats">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="show-tasks" type="submit">Показать статистику по клиентам</button>
                </form:form>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
        <div class="buttons">
            <form:form method="GET" action="/tasks">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="Profile" value="${Profile}"/>
                <button class="show-tasks" type="submit">Показать список задач</button>
            </form:form>
        </div>
        <div class="buttons">
            <form:form method="GET" action="/new_task">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="Profile" value="${User}"/>
                <button class="create-task" type="submit">Создать задачу</button>
            </form:form>
        </div>
        </sec:authorize>
    </div>
</div>
    <div id="online-users">
        <div>Пользователей онлайн:</div>
        <ul id="users-list"></ul>
    </div>
    <script>
        $(document).ready(function() {
            $.ajax({
                url: "/addUserToOnlineList",
                headers: {
                    "user": encodeURIComponent("${pageContext.request.userPrincipal.name}")
                },
                type: "GET",
                success: function() {
                    console.log("User added to online list");
                },
                error: function() {
                    console.error("Unable to add user to online list");
                }
            });
        });
        function updateUsersOnline() {
            $.get("/getUsersOnline", function(mess) {
                // обработчик успешного выполнения запроса
                $("#users-list").text(mess);
            }).fail(function() {
                // обработчик ошибок выполнения запроса
                alert("Ошибка при выполнении запроса");
            });
        }
        setInterval(updateUsersOnline, 1000);
        $(window).unload(function() {
            $.ajax({
                url: "/removeUserFromOnlineList",
                headers: {
                    "user": encodeURIComponent("${pageContext.request.userPrincipal.name}")
                },
                type: "GET",
                success: function() {
                    console.log("User removed from online list");
                },
                error: function() {
                    console.error("Unable to remove user from online list");
                }
            });
        });
    </script>
</c:if>
</body>
</html>