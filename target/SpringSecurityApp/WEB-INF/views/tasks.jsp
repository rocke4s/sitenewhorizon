<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Rit.Задачи</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${contextPath}/resources/css/tasks-css.css" rel="stylesheet">
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
<div class="toggle-switch">
    <input type="checkbox" id="toggle" class="toggle-checkbox" onchange="hideEnding(this)" checked>
    <label for="toggle" class="toggle-label"></label>
</div>
<label id="togLabel" >Показать задачи</label>
<div class="all-list-task" id="all-list-task">
<c:forEach var="Task" items="${Tasks.getTasks()}">
    <details><div class="layer1" margin-top="5px"  margin-bottom="5px">
    </div>
     <summary>Номер задачи: [${Task.getTaskNumber()}]; Название задачи - ${Task.getNameTask()}</summary>
        <button id="buttonShow${Task.getTaskNumber()}" class="btn btn-primary" onclick="ShowChat('${Task.getTaskNumber()}')">Показать чат</button>
        <button id="buttonHide${Task.getTaskNumber()}" class="btn btn-primary" onclick="HideChat('${Task.getTaskNumber()}')" hidden="true">Скрыть чат</button>
        <button class="btn btn-primary buttonShowM" onclick="ShowModal('${Task.getTaskNumber()}')">Жизненный цикл заявки</button>
        <button class="btn btn-primary buttonHideM" onclick="HideModal()" hidden="true">Скрыть жизненный цикл</button>
            <table>
                <thead>
                <tr>
                    <c:if test="${!Task.getTaskUrl().isEmpty()}"><th>Ссылка</th></c:if>
                    <c:if test="${!Task.getTaskPartner().isEmpty()}"><th>Контрагент</th></c:if>
                    <c:if test="${!Task.getTaskStatus().isEmpty()}"><th>Состояние заявки</th></c:if>
                    <c:if test="${Task.getTaskStatus()=='Новая' || Task.getTaskStatus()=='Выполнена'
                     || Task.getTaskStatus()=='На тестировании'}">
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
                    <c:if test="${!Task.getTaskDepartment().isEmpty()}"><th>Отдел</th></c:if>
                </tr>
                </thead>
            <c:forEach var="Tasks" items="${Tasks.getTasks()}">
                <c:if test="${Task.getTaskNumber()==Tasks.getTaskNumber()}">

                <tr>
                    <c:if test="${!Tasks.getTaskUrl().isEmpty()}"><td>${Tasks.getTaskUrl()}</td></c:if>
                    <c:if test="${!Tasks.getTaskPartner().isEmpty()}"><td>${Tasks.getTaskPartner()}</td></c:if>
                    <c:if test="${!Tasks.getTaskStatus().isEmpty()}"><td id="statusid${Tasks.getTaskNumber()}">${Tasks.getTaskStatus()}</td></c:if>
                    <c:if test="${Tasks.getTaskStatus()=='Новая' || Tasks.getTaskStatus()=='Выполнена'
                     || Tasks.getTaskStatus()=='На тестировании'}">
                        <td>
                            <c:if test="${Tasks.getTaskStatus()=='На тестировании' || Tasks.getTaskStatus()=='Выполнена'}">
                            <div>
                                <form:form action="/change_status?${_csrf.parameterName}=${_csrf.token}" method="GET" modelAttribute="changeStatus">
                                    <input type="hidden" value="${Tasks.getTaskNumber()}" name="TaskNumber">
                                    <input type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_5">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <spring:bind path="causeChangeStatus">
                                    <form:input type="text" path="causeChangeStatus" class="form-control" placeholder="Причина доработки"
                                                autofocus="true"></form:input>
                                    </spring:bind>
                                    <button type="submit" class="btn btn-primary">Доработка</button>
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
                                        <button id="modalbutton" type="submit" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Проверено</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${Tasks.getTaskStatus()=='Новая'}">
                                <div>
                                    <form method="GET" action="/change_status">
                                        <input type="hidden" value="${Tasks.getTaskNumber()}" name="TaskNumber">
                                        <input type="hidden" value="${Tasks.getUidDoc()}" name="uidDoc_0">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-primary">Отмена</button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${!Tasks.getTypeTask().isEmpty()}"><td>${Tasks.getTypeTask()}</td></c:if>
                    <c:if test="${!Tasks.getTaskImportance().isEmpty()}"><td>${Tasks.getTaskImportance()}</td></c:if>
                    <c:if test="${!Tasks.getTaskContent().isEmpty()}"><td style="word-wrap: break-word; word-break: break-word; max-width: 200px;">${Tasks.getTaskContent()}</td></c:if>
                    <c:if test="${!Tasks.getTaskDeadline().isEmpty()}"><td id="deadline${Tasks.getTaskNumber()}">${Tasks.getTaskDeadline()}</td></c:if>
                    <c:if test="${!Tasks.getTaskIntensity().isEmpty()}"><td>${Tasks.getTaskIntensity()}</td></c:if>
                    <c:if test="${!Tasks.getTaskId().isEmpty()}"><td>${Tasks.getTaskId()}</td></c:if>
                    <c:if test="${!Tasks.getTaskDataDone().isEmpty()}"><td>${Tasks.getTaskDataDone()}</td></c:if>
                    <c:if test="${!Tasks.getTaskContentLVR().isEmpty()}"><td>${Tasks.getTaskContentLVR()}</td></c:if>
                    <c:if test="${!Tasks.getTaskData().isEmpty()}"><td>${Tasks.getTaskData()}</td></c:if>
                    <c:if test="${!Tasks.getTaskDepartment().isEmpty()}"><td>${Tasks.getTaskDepartment()}</td></c:if>
                </tr>
            </c:if>
            </c:forEach>

            </tr>
        </table>
        <div id="id_${Task.getTaskNumber()}" hidden="true">
            <ul id="messageArea${Task.getTaskNumber()}">
            </ul>
                                 <input type="text" id="messageForUser" placeholder="Введите сообщение...">
                            <button onclick="sendMessage1('${Task.getTaskNumber()}','${Task.getUidDoc()}')" type="submit">send</button>
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
                <form class="rating" method="POST" action="/rating">
                <div class="modal-body">
                    <p>Оставьте свою оценку:</p>
                    <div class="btn-group" role="group" aria-label="Оценить">
                        <input id="uidDoc_88" type="hidden" name="uidDoc_8">
                        <input id="NameTasker" type="hidden" name="NameTasker">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
<div class="sidebar">
    <h2>Журнал изменений</h2>
    <ul id="sidebar-ul">
<c:forEach var="changeLogTask" items="${changeLogTask}">
        <li>
            <span class="change-type"><u>${changeLogTask.getChangetype()}</u></span><br>
            <span class="task-title">${changeLogTask.getNameTask()}</span><br>
            <span class="change">${changeLogTask.getChange()}</span>
            <hr><span class="time">${changeLogTask.getTime()}</span><br>
        </li>
</c:forEach>
    </ul>
</div>
<script src="${contextPath}/resources/js/tasks-js.js">
</script>
<script>
    function sendMessage1(numberDoc,uidDoc) {
        var date = new Date();
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();
        const time = date.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
        var newDate = day+"."+month+"."+year+" "+time;
        $.ajax({
            method: 'GET',
            url: 'http://194.67.111.29/worker',
            headers: {
                "NumberTask": encodeURIComponent(numberDoc),
                "uidDoc": encodeURIComponent(uidDoc),
            "userSender": encodeURIComponent("${Profile.getName()}"),
            "message": encodeURIComponent(""+document.querySelector('#id_' + numberDoc + ' input[type="text"]').value),
            "dataSend": encodeURIComponent(""+newDate),
                "userName": encodeURIComponent("${pageContext.request.userPrincipal.name}")
        }})
            .then(function(response) {
                console.log(response);})
            .catch(function(error) {
                console.error(error);});
    }
</script>
</body>
</html>
