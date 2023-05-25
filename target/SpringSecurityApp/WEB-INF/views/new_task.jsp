<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <!DOCTYPE html>
    <html lang="en">
<head>
    <title>Rit.Новая заявка</title>
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/image/111.png">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/new_task-css.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <img src="${contextPath}/resources/image/logo.png" alt="Логотип">
            <h1>Создание заявки</h1>
        </div>
        <form id="logoutForm" method="POST" action="${contextPath}/logout?${_csrf.parameterName}=${_csrf.token}">
        </form>
        <a href="/welcome" class="btn">Назад</a>
        <h2><a class="btn" onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h2>
    </div>
</div>
<form:form action="/new_tasker?${_csrf.parameterName}=${_csrf.token}" class="obratnuj-zvonok" method="post" modelAttribute="newTask" enctype="multipart/form-data">
    <div class="form-zvonok">
  <h1>Форма заявки</h1>
    <spring:bind path="TaskContent">
  <label>Наименования заявки</label>
        <div class="form-group">
      <form:input type="text" path="nameTask" class="form-control"></form:input>
        </div>
    </spring:bind>
      <spring:bind path="TaskContent">
    <label>Содержание</label>
      <div class="form-group">
    <form:textarea type="text" path="TaskContent" class="form-control"></form:textarea>
      </div>
          <div><label>Важность</label>
              <form:select class="select-css" path="TaskImportance" >
                  <c:forEach var="listImportance" items="${listImportance}">
                      <option>${listImportance}</option>
                  </c:forEach>
              </form:select>
          </div>
      </spring:bind>
    <spring:bind path="file">
        <div class="file-input">
            <form:input type="file" onchange="showFileName()" path="file" id="file" cssClass="file-input__input"></form:input>
            <label id="file-selected" for="file" class="file-input__label" style="word-wrap: break-word; word-break: break-word; max-width: 200px;">Выбрать файл</label>
        </div>
    </spring:bind>
    <input  class="bot-send-mail" value="Отправить" type="submit">
    </div>
</form:form>
<script src="${contextPath}/resources/js/new_task-js.js"></script>
    </body>
    </html>