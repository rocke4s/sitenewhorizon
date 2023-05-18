<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

    <title>Задачи!</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        body {
            background-color: #f8f8f8;
            font-family: Arial, sans-serif;
            font-size: 18px;
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
        summary {
            background: #145b04;
            color: #FFFFFF;
            border-radius: 7px;
            padding: 5px 10px;
        }

        /* Style the summary when details box is open */
        details[open] summary {
            background: #69c773;
            color: #000000;
        }
        /* Custom Markers */
        #custom-marker summary {
            font-size: 17px;
            vertical-align: top;
        }

        #custom-marker summary::-webkit-details-marker {
            display: none;
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
        .all-list-task {
            border: 4px solid black;
            padding: 13px;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <img src="${contextPath}/resources/image/logo.png" alt="Логотип">
            <h1>Список задач</h1>
        </div>
        <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
        </form>
        <a href="/welcome" class="btn">Назад</a>
        <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
    </div>
</div>
        <input type="checkbox" name="a" value="checker" onchange="hideEnding(this)" checked>Скрыть/Показать завершенные задачи</p>
<div class="all-list-task">
<c:forEach var="Task" items="${Tasks.getTasks()}">
    <details><div class="layer1" margin-top="5px"  margin-bottom="5px">
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
                    <c:if test="${ Tasks.getTaskStatus()!='Проверено'
                    && Tasks.getTaskStatus()!='В работе' && Tasks.getTaskStatus()!='На доработке'}">
                        <td>
                            <c:if test="${Tasks.getTaskStatus()=='На тестировании' || Tasks.getTaskStatus()=='Выполнена'}">
                            <div>
                                <form:form action="/change_status?${_csrf.parameterName}=${_csrf.token}" method="get" modelAttribute="changeStatus">
                                    <input type="hidden" value="${Tasks.getTaskNumber()}" name="TaskNumber">
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
                            <c:if test="${Tasks.getTaskStatus()=='Выполнена' || Tasks.getTaskStatus()=='На тестировании'}">
                                <div>
                                    <form class="myForm" method="GET" action="/change_status">
                                        <input id="TaskNumber" type="hidden" value="${Tasks.getTaskNumber()}" name="TaskNumber">
                                        <input id="uidDoc_8" type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_8">
                                        <input id="NameTasks" type="hidden" value="${Tasks.getNameTask()}" name="NameTasks">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Проверено</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${Tasks.getTaskStatus()=='Новая' || Tasks.getTaskStatus()=='На расмотрении' || Tasks.getTaskStatus()=='Запланировано'}">
                                <div>
                                    <form method="GET" action="/change_status">
                                        <input type="hidden" value="${Tasks.getTaskNumber()}" name="TaskNumber">
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
                                 <input type="text" id="messageForUser" placeholder="Введите сообщение...">
                            <button onclick="sendMessage('${Task.getTaskNumber()}')" type="submit">send</button>
        </div>
    </details>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Оцените работу сотрудника</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <!-- Modal Body -->
                <form class="rating" method="GET" action="/rating">
                <div class="modal-body">
                    <p>Оставьте свою оценку:</p>
                    <div class="btn-group" role="group" aria-label="Оценить">
                        <input id="uidDoc_88" type="hidden" name="uidDoc_8">
                        <input id="NameTasker" type="hidden" name="NameTasker">
                        <button type="submit" class="btn btn-success" name="ratingg" value="Хорошо">Хорошо</button>
                        <button type="submit" class="btn btn-warning" name="ratingg" value="Удовлетворительно">Удовлетворительно</button>
                        <button type="submit" class="btn btn-danger" name="ratingg" value="Плохо">Плохо</button>
                    </div>
                </div>
                </form>

                <!-- Modal Footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                </div>

            </div>
        </div>
    </div>
</c:forEach>
</div>
<%--<h1>Тест скачки файла</h1>--%>
<%--<div>--%>
<%--    <a href="http://localhost/data/test.txt" download>--%>
<%--        <button>Download File</button></a>--%>
<%--</div>--%>
<script>
    var tdElements = document.getElementsByTagName('td');

    // перебираем каждый элемент td
    for (var i = 0; i < tdElements.length; i++) {
        var tdElement = tdElements[i];

        // если значение элемента td равно 'Проверено'
        if (tdElement.innerHTML === 'Проверено' || tdElement.innerHTML === 'Отменено') {
            var detailsElement = tdElement.closest('details');
            // скрываем тег Details, если он есть внутри найденного элемента td
            if (detailsElement) {
                    detailsElement.style.display = 'none';

            }
        }
    }


    const forms = document.querySelectorAll('.myForm');

    // добавляем обработчик события submit для каждой формы
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            // Получение значений полей формы
            const uidDoc_8 = form.elements.uidDoc_8.value;
            const myInput = document.getElementById("uidDoc_88");
            myInput.value = uidDoc_8;
            const saveNameTask = form.elements.NameTasks.value;
            const myInput2 = document.getElementById("NameTasker");
            myInput2.value = saveNameTask;
            // Отправка данных на сервер
            fetch('/change_status?uidDoc_8='+uidDoc_8, {
                method: 'GET'
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                })
                .catch(error => {
                    console.error(error);
                    // Действия при ошибке отправки данных
                });
        });
    });


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


    var socket = new WebSocket("ws://localhost/chat");

    socket.onopen = function(event) {
        console.log("WebSocket opened");
    };

    socket.onmessage = function(event) {
        console.log("WebSocket message received: " + event.data);
    };

    socket.onclose = function(event) {
        console.log("WebSocket closed");
    };

    socket.onerror = function(event) {
        console.error("WebSocket error: " + event);
    };




    // const socket = new WebSocket("ws://localhost:8081/chat");
    // socket.onopen = function() {
    //     console.log("Connected to server");
    //     socket.send("Hello, server!");
    // };
    //
    function hideEnding(stat)
    {
        var tdElements = document.getElementsByTagName('td');

        // перебираем каждый элемент td
        for (var i = 0; i < tdElements.length; i++) {
            var tdElement = tdElements[i];

            // если значение элемента td равно 'Проверено'
            if (tdElement.innerHTML === 'Проверено' || tdElement.innerHTML === 'Отменено') {
                var detailsElement = tdElement.closest('details');
                // скрываем тег Details, если он есть внутри найденного элемента td
                if (detailsElement) {
                    if(stat.checked) {
                        detailsElement.style.display = 'none';
                    }
                    else
                    {
                        detailsElement.style.display = '';
                    }
                }
            }
        }
    }
    function sendMessage(ids) {
        // Отправка сообщения на сервер
        ul = document.getElementById('messageArea'+ids);
        socket.send("{'uidDoc':'"+ids+"','Name':'${pageContext.request.userPrincipal.name}','message':'"+document.querySelector('#id_'+ids+' input[type="text"]').value+"'}");
        document.querySelector('#id_'+ids+' input[type="text"]').value = "";
    }
    setInterval(function() {
        socket.onmessage = function(event) {
            console.log(event.data);
            var parsed= JSON.parse(event.data);
            console.log(parsed.Docid)
            var ull = document.getElementById("messageArea"+parsed.Docid);
            const li = document.createElement('li');
            li.innerHTML = parsed.Named+": "+parsed.messg;
            messageList = event.data;
            ull.appendChild(li);
        };
    }, 1000);
</script>
</body>
</html>
