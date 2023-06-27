<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rit.Админ.Статистика</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <link href="${contextPath}/resources/css/stats.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <a href="/welcome">
                <img src="${contextPath}/resources/image/logo.png" onclick="" alt="Логотип">
            </a>
            <h1>Админ Статистика</h1>
        </div>
        <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
        </form>
        <input type="text" id="searchbox" onchange="handleSearch()" placeholder="Поиск по задачам">
        <a href="/welcome" class="btn">Назад</a>
        <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
    </div>
</div>
<div style="text-align: center;">
    <div style="display: inline-block; width: 800px; height: 100px; border: 1px solid black; margin-top: 20px;">
        <div style="display: inline-block; margin-right: 140px;">
            <label>Фильтры</label><br>
            <button onclick="filtrOrg()">По организации</button>
            <br>
            <button onclick="filtrDepart()">По отделу</button>
            <br>
            <button onclick="filtrWorker()">По сотруднику</button><br>
            <button onclick="filtrWorkerWithRestore()">Сбросить фильтр</button>
        </div>
        <div style="display: inline-block; margin-left: 140px;">
            <label for="date-range-select">Выберите период:</label><select id="date-range-select">
            <option value="month">Текущий месяц</option>
            <option value="quarter">Текущий квартал</option>
            <option value="week">Текущая неделя</option>
            <option value="year">Текущий год</option>
        </select><br>
            <div><label for="start-date">Начальная дата:</label><input type="date" id="start-date"><br>
                <label for="end-date">Конечная дата:</label>
                <input type="date" id="end-date">
                <button id="submit-btn">Вывод</button>
                <div id="output"></div>
            </div>
        </div>
    </div>
</div>
<div style="text-align: center;">
    <p id="allStats" style="font-size: 200%; margin-bottom: 10px;letter-spacing: 2px;">Всего заявок: 10 С прошлого
        месяца: 3<br>
        Новых заявок: 5 Выполнено: 2<br>Исполнено: 10% Трудозатраты: 500<br>
        Оценка 50/35/25.<br></p>
    <p style="font-size: 14px; margin-top: 5px; margin-left: 120px;">(Хорошо/удовлетворительно/плохо)</p>
</div>
<div id="fortablediv">
    <table id="table-class" class="table-class">
        <c:forEach var="Tasks" items="${Tasks.getTasks()}">
        <tbody class="taskTr" id="table${Tasks.getTaskNumber()}" style="overflow-x: auto;">
        <tr>
            <th>Ссылка</th>
            <th>Контрагент</th>
            <th>Состояние заявки</th>
            <th>Тип задачи</th>
            <th>Важность</th>
            <th>Содержание</th>
            <th>СрокДо</th>
            <th>Трудоемкость</th>
            <th>ID</th>
            <th>Дата Выполнено</th>
            <th>Содержание ЛУВР</th>
            <th>Дата</th>
            <th>Отдел</th>
            <th>Автор заявки</th>
        </tr>
        <tr>
            <td>${Tasks.getTaskUrl()}</td>
            <td id="taskPartner"${Tasks.getTaskNumber()} class="taskPartner">${Tasks.getTaskPartner()}</td>
            <td id="statusid${Tasks.getTaskNumber()}"
                class="taskStatus">${Tasks.getTaskStatus()}</td>
            <td>${Tasks.getTypeTask()}</td>
            <td>${Tasks.getTaskImportance()}</td>
            <td id="TaskContent${Tasks.getTaskNumber()}" class="short-text expandable">${Tasks.getTaskContent()}</td>
            <td id="deadline${Tasks.getTaskNumber()}"
                class="deadlines">${Tasks.getTaskDeadline()}</td>
            <td class="taskIntensity">${Tasks.getTaskIntensity()}</td>
            <td>${Tasks.getTaskId()}</td>
            <td class="taskDataDone">${Tasks.getTaskDataDone()}</td>
            <td>${Tasks.getTaskContentLVR()}</td>
            <td class="taskData">${Tasks.getTaskData()}</td>
            <td class="taskDepartment">${Tasks.getTaskDepartment()}</td>
            <td class="taskAuthor">${Tasks.getTaskAuthor()}</td>
            <td hidden>${Tasks.getNameTask()}</td>
        </tr>
        </c:forEach>
        </tr>
        </tbody>
    </table>
</div>
<script src="${contextPath}/resources/js/stats.js">
</script>
<script>
    //Вывод количества оценок по заявкам
    var good = 0, middle = 0, bad = 0;
    <c:forEach var="rating" items="${rating}">
    <c:if test="${rating.getRating()=='Хорошо'}">
    good++;
    </c:if>
    <c:if test="${rating.getRating()=='Удовлетворительно'}">
    middle++;
    </c:if>
    <c:if test="${rating.getRating()=='Плохо'}">
    bad++;
    </c:if>
    </c:forEach>
    allStats.innerHTML += ' Оценки ' + good + '/' + middle + '/' + bad + '<br>';
    //конец вывода
</script>
</body>
</html>