<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Новая заявка</title>
    <style type="text/css">

        .obratnuj-zvonok{
            width: 100%;
            max-width: 350px;
        }
        .form-zvonok{
            margin-left: 220%;
            width: 100%;
            display: flex;
            flex-direction: column;
            padding: 0 20px;
            box-sizing: border-box;
        }
        .form-zvonok div{

            padding: 15px 0;
        }
        .bot-send-mail{
            box-sizing: border-box;
            width: 100%;
        }
        .form-zvonok label,.form-zvonok input{
            display: block;
            width: 100%;
            box-sizing: border-box;
        }
        .form-zvonok label{
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-zvonok input{
            padding: 10px 15px;
            margin-top: 10px;
        }
        .form-zvonok label span{
            color: red;
        }
        .form-zvonok .bot-send-mail{
            padding: 15px;
            margin-top: 10px;
            background: none;
            border: none;
            text-transform: uppercase;
            color: #fff;
            font-weight: bold;
            background-color: #009b97;
            cursor: pointer;
            border: 3px #009b97 solid;
            border-radius: 5px;
        }
        .form-zvonok .bot-send-mail:hover{
            color: #009b97;
            background-color: #fff;
        }
        .file_input_textbox {height:25px; width:200px; float:left; }
        .file_input_div {position: relative; width:80px; height:26px; }
        .file_input_button {width: 80px; position:absolute; top:0px; border:1px solid #F0F0EE;padding:2px 8px 2px 8px; font-weight:bold; height:25px; margin:0px; margin-right:5px; }
        .file_input_button_hover{ border:1px solid #0A246A; background-color:#B2BBD0;padding:2px 8px 2px 8px;font-weight:bold;}
        .file_input_hidden {font-size:45px;position:absolute;right:0px;top:0px;cursor:pointer; opacity:0; filter:alpha(opacity=0); -ms-filter:"alpha(opacity=0)"; -khtml-opacity:0; -moz-opacity:0; }
        .select-css {
            display: block;
            font-size: 16px;
            font-family: sans-serif;
            font-weight: 700;
            color: #444;
            line-height: 1.3;
            padding: .6em 1.4em .5em .8em; width: 100%;
            max-width: 100%;
            box-sizing: border-box;
            margin: 0;
            border: 1px solid #aaa;
            box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
            border-radius: .5em;
            -moz-appearance: none;
            -webkit-appearance: none;
            appearance: none;
            background-color: #fff;
            background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23007CB2%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E'), linear-gradient(to bottom, #ffffff 0%,#e5e5e5 100%);
            background-repeat: no-repeat, repeat;
            background-position: right .7em top 50%, 0 0;
            background-size: .65em auto, 100%;
        }
        .select-css::-ms-expand { display: none; }
        .select-css:hover { border-color: #888; }
        .select-css:focus { border-color: #aaa;
            box-shadow: 0 0 1px 3px rgba(59, 153, 252, .7);
            box-shadow: 0 0 0 3px -moz-mac-focusring;
            color: #222;
            outline: none;
        }
        .select-css option { font-weight:normal; }
        *[dir="rtl"] .select-css, :root:lang(ar) .select-css, :root:lang(iw) .select-css {
            background-position: left .7em top 50%, 0 0;
            padding: .6em .8em .5em 1.4em;
        }
        .btn {
            display: inline-block; /* Строчно-блочный элемент */
            background: #8C959D; /* Серый цвет фона */
            color: #fff; /* Белый цвет текста */
            padding: 1rem 1.5rem; /* Поля вокруг текста */
            text-decoration: none; /* Убираем подчёркивание */
            border-radius: 3px; /* Скругляем уголки */
        }

        .file-input {
            position: relative;
            width: 200px;
        }

        .file-input__input {
            position: absolute;
            z-index: -1;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            opacity: 0;
        }

        .file-input__label {
            display: inline-block;
            color: #444;
            border-radius: 4px;
            cursor: pointer;
        }

        .file-input__label::before {
            content: 'Загрузить';
            display: inline-block;
            padding: 8px 16px;
            background-color: #fff;
            color: #444;
            border: 1px solid #888;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 4px;
        }

        .file-input__label:hover::before {
            color: #fff;
            background-color: #444;
            border-color: #444;
        }
    </style>
</head>
<body>
<sec:authorize access="hasRole('ROLE_USER')"> <!-- Проверяем, что пользователь авторизован -->
    <a href="/welcome" class="btn">Назад</a> <!-- Отображаем ссылку на профиль -->
</sec:authorize>
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
          </spring:bind>
      <spring:bind path="TaskContent">
    <label>Содержание</label>
      <div class="form-group">
    <form:textarea type="text" path="TaskContent" class="form-control"></form:textarea>
      </div>
          <div><label>Важность</label>
              <form:select class="select-css" path="TaskImportance" >
                  <c:forEach var="listImportance" items="${listImportance}">
                      <option>
                              ${listImportance}</option>
                  </c:forEach>
              </form:select>
          </div>
      </spring:bind>
    <spring:bind path="file">
        <div class="file-input">
            <form:input type="file" path="file" id="file" cssClass="file-input__input"></form:input>
            <label for="file" class="file-input__label">Choose file</label>
        </div>
    </spring:bind>
    <input  class="bot-send-mail" value="Отправить" type="submit">
    </div>
</form:form>
</body>
</html>