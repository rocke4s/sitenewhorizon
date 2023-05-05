<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

    <title>Задачи!</title>
    <style type="text/css">
        summary {
            background: #333;
            color: #FFF;
            border-radius: 3px;
            padding: 5px 10px;
        }

        /* Style the summary when details box is open */
        details[open] summary {
            background: #69c773;
            color: #333;
        }

        /* Custom Markers */
        #custom-marker summary {
            font-size: 17px;
            vertical-align: top;
        }

        #custom-marker summary::-webkit-details-marker {
            display: none;
        }
        .btn {
            display: inline-block; /* Строчно-блочный элемент */
            background: #8C959D; /* Серый цвет фона */
            color: #fff; /* Белый цвет текста */
            padding: 1rem 1.5rem; /* Поля вокруг текста */
            text-decoration: none; /* Убираем подчёркивание */
            border-radius: 3px; /* Скругляем уголки */
        }
        #custom-marker summary:before {
            display: inline-block;
            width: 18px;
            height: 18px;
            margin-right: 8px;
            content: "";
            background-image: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/4621/treehouse-icon-sprite.png);
            background-repeat: no-repeat;
            background-position: 0 0;
        }

        #custom-marker[open] summary:before {
            background-position: -18px 0;
        }


        table {
            border: 0;
            width: 100%;
        }

        th, td {
            vertical-align: top;
            text-align: left;
            padding: 0.5em;
            border-bottom: 1px solid #E6E6E6;
        }

        th {
            width: 200px;
        }
        .layer1 {
            color: white; /* Цвет текста */
            padding: 10px; /* Поля вокруг текста */
            margin-bottom: -7px; /* Отступ снизу */
        }
        #chat-page ul {
            list-style-type: none;
            background-color: #FFF;
            margin: 0;
            overflow: auto;
            overflow-y: scroll;
            padding: 0 20px 0px 20px;
            height: calc(100% - 150px);
        }
    </style>
</head>
<body>
<div align="left">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="post" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Список задач пользователя ${pageContext.request.userPrincipal.name}
        </h2>
        <sec:authorize access="hasRole('ROLE_USER')"> <!-- Проверяем, что пользователь авторизован -->
            <a href="/welcome" class="btn">Назад</a> <!-- Отображаем ссылку на профиль -->
        </sec:authorize>
    </c:if>
</div>
<c:forEach var="Task" items="${Tasks.getTasks()}">
    <details ><div class="layer1" margin-top="5px"  margin-bottom="5px">
    </div>
     <summary>Номер задачи: [${Task.getTaskNumber()}]; Название задачи - ${Task.getNameTask()}</summary>
        <button id="buttonShow${Task.getTaskNumber()}" onclick="ShowChat('${Task.getTaskNumber()}')">Показать чат</button>
        <button id="buttonHide${Task.getTaskNumber()}" onclick="HideChat('${Task.getTaskNumber()}')" hidden="true">Скрыть чат</button>
            <table>
                <thead>
                <tr>
                    <c:if test="${!Task.getTaskUrl().isEmpty()}"><th>Ссылка</th></c:if>
                    <c:if test="${!Task.getTaskPartner().isEmpty()}"><th>Контрагент</th></c:if>
                    <c:if test="${!Task.getTaskStatus().isEmpty()}"><th>Состояние заявки</th></c:if>
                    <c:if test="${Task.getTaskStatus()!='Отменено' && Task.getTaskStatus()!='Проверено'
                     && Task.getTaskStatus()!='В работе' && Task.getTaskStatus()!='На доработке'}">
                        <th>Изменить состояние</th>
                    </c:if>
                    <c:if test="${!Task.getTypeTask().isEmpty()}"><th>Тип задачи</th></c:if>
                    <c:if test="${!Task.getTaskImportance().isEmpty()}"><th>Важность</th></c:if>
                    <c:if test="${!Task.getTaskContent().isEmpty()}"><th>Содержание</th></c:if>
                    <c:if test="${!Task.getTaskDeadline().isEmpty()}"><th>СрокДо</th></c:if>
                    <c:if test="${!Task.getTaskIntensity().isEmpty()}"><th>Трудоемкость</th></c:if>
                    <c:if test="${!Task.getTaskId().isEmpty()}"><th>ID</th></c:if>
                    <c:if test="${!Task.getTaskDataDone().isEmpty()}"><th>Дата Выполнено</th></c:if>
                    <c:if test="${!Task.getTaskContentLVR().isEmpty()}"><th>Содержание ЛУВР</th></c:if>
                    <c:if test="${!Task.getTaskData().isEmpty()}"><th>Дата</th></c:if>
                    <c:if test="${!Task.getTaskEmployee().isEmpty()}"><th>Сотрудник</th></c:if>
                </tr>
                </thead>
            <c:forEach var="Tasks" items="${Tasks.getTasks()}">
                <c:if test="${Task.getTaskNumber()==Tasks.getTaskNumber()}">
                <tr>
                    <c:if test="${!Tasks.getTaskUrl().isEmpty()}"><td>${Tasks.getTaskUrl()}</td></c:if>
                    <c:if test="${!Tasks.getTaskPartner().isEmpty()}"><td>${Tasks.getTaskPartner()}</td></c:if>
                    <c:if test="${!Tasks.getTaskStatus().isEmpty()}"><td>${Tasks.getTaskStatus()}</td></c:if>
                    <c:if test="${Tasks.getTaskStatus()!='Отменено' && Tasks.getTaskStatus()!='Проверено'
                    && Tasks.getTaskStatus()!='В работе' && Tasks.getTaskStatus()!='На доработке'}">
                        <td>
                            <c:if test="${Tasks.getTaskStatus()=='На тестировании' || Tasks.getTaskStatus()=='Выполнена'}">
                            <div>
                                <form:form action="/change_status?${_csrf.parameterName}=${_csrf.token}" method="get" modelAttribute="changeStatus">
                                    <input type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_5">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <spring:bind path="causeChangeStatus">
                                    <form:input type="text" path="causeChangeStatus" class="form-control" placeholder="Причина доработки"
                                                autofocus="true"></form:input>
                                    </spring:bind>
                                    <button type="submit">Доработка</button>
                                </form:form>
                            </div>
                        </c:if>
                            <c:if test="${Tasks.getTaskStatus()=='На тестировании' || Tasks.getTaskStatus()=='Выполнена'}">
                                <div>
                                    <form method="GET" action="/change_status">
                                        <input type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_8">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit">Проверено</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${Tasks.getTaskStatus()=='Новая' || Tasks.getTaskStatus()=='На расмотрении' || Tasks.getTaskStatus()=='Запланировано'}">
                                <div>
                                    <form method="GET" action="/change_status">
                                        <input type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_0">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit">Отмена</button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${!Tasks.getTypeTask().isEmpty()}"><td>${Tasks.getTypeTask()}</td></c:if>
                    <c:if test="${!Tasks.getTaskImportance().isEmpty()}"><td>${Tasks.getTaskImportance()}</td></c:if>
                    <c:if test="${!Tasks.getTaskContent().isEmpty()}"><td>${Tasks.getTaskContent()}</td></c:if>
                    <c:if test="${!Tasks.getTaskDeadline().isEmpty()}"><td>${Tasks.getTaskDeadline()}</td></c:if>
                    <c:if test="${!Tasks.getTaskIntensity().isEmpty()}"><td>${Tasks.getTaskIntensity()}</td></c:if>
                    <c:if test="${!Tasks.getTaskId().isEmpty()}"><td>${Tasks.getTaskId()}</td></c:if>
                    <c:if test="${!Tasks.getTaskDataDone().isEmpty()}"><td>${Tasks.getTaskDataDone()}</td></c:if>
                    <c:if test="${!Tasks.getTaskContentLVR().isEmpty()}"><td>${Tasks.getTaskContentLVR()}</td></c:if>
                    <c:if test="${!Tasks.getTaskData().isEmpty()}"><td>${Tasks.getTaskData()}</td></c:if>
                    <c:if test="${!Tasks.getTaskEmployee().isEmpty()}"><td>${Tasks.getTaskEmployee()}</td></c:if>
                </tr>
            </c:if>
            </c:forEach>

            </tr>
        </table>
        <div id="id_${Task.getTaskNumber()}" hidden="true">
            <ul id="messageArea${Task.getTaskNumber()}">

            </ul>
<%--            <form id="chatMessage" name="messageForm" nameForm="messageForm">--%>
<%--                <div class="form-group">--%>
<%--                    <div class="input-group clearfix">--%>
<%--                        <form:form action="/change_status?${_csrf.parameterName}=${_csrf.token}" method="get" modelAttribute="chat">--%>
<%--                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--                            <spring:bind path="message">--%>
<%--                                <form:input type="text" path="message" class="form-control" placeholder="Введите сообщение..."--%>
<%--                                            autofocus="true"></form:input>--%>
<%--                            </spring:bind>--%>
                                 <input type="text" id="messageForUser">
                            <button onclick="sendMessage('${Task.getTaskNumber()}')" type="submit">send</button>
<%--                        </form:form>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </form>--%>
        </div>
    </details>
</c:forEach>
<script>
    let ul = null;
    let messageList = null;

    function ShowChat(ids)
    {
       document.getElementById('id_'+ids).hidden= false;
        document.getElementById('buttonShow'+ids).hidden = true;
        document.getElementById('buttonHide'+ids).hidden = false;
    }
    function HideChat(ids)
    {
       document.getElementById('id_'+ids).hidden= true;
        document.getElementById('buttonShow'+ids).hidden = false;
        document.getElementById('buttonHide'+ids).hidden = true;
    }

    const socket = new WebSocket("ws://194.67.111.29:8081/chat");
    socket.onopen = function() {
        console.log("Connected to server");
        socket.send("Hello, server!");
    };

    function sendMessage(ids) {
        // Отправка сообщения на сервер
        ul = document.getElementById('messageArea'+ids);
        socket.send('{message:'+document.querySelector('#id_'+ids+' input[type="text"]').value+',uidDoc:'+ids+'}');
        document.querySelector('#id_'+ids+' input[type="text"]').value = "";
    }
    setInterval(function() {
        socket.onmessage = function(event) {
            const li = document.createElement('li');
            li.textContent = event.data;
            messageList = event.data;
            ul.appendChild(li);
        };
    }, 1000);
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
