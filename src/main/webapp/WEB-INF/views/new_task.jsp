<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Новая заявка</title>
</head>
<body>
<form:form action="/new_tasker?${_csrf.parameterName}=${_csrf.token}" method="post" modelAttribute="newTask" enctype="multipart/form-data">
  <h1>Форма заявки</h1>
    <spring:bind path="TaskContent">
  <div><label>Наименования заявки</label>
      <form:input type="text" path="nameTask" class="form-control"></form:input>
  </div>
    </spring:bind>
          <spring:bind path="TaskContent">
  <div><label>Важность</label>
    <form:select path="TaskImportance" >
      <c:forEach var="listImportance" items="${listImportance}">
      <option>
              ${listImportance}</option>
      </c:forEach>
    </form:select>
  </div>
          </spring:bind>
  </div>
      <spring:bind path="TaskContent">
  <div>
    <label>Содержание</label>
    <form:textarea type="text" path="TaskContent" class="form-control"></form:textarea>
  </div>
      </spring:bind>
    <spring:bind path="file">
        <div class="form-group">
            <form:input type="file" path="file" class="form-control"></form:input>
        </div>
    </spring:bind>
    <input value="Отправить" type="submit">
</form:form>
</body>
</html>