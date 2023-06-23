<%--
  Created by IntelliJ IDEA.
  User: User_RIT
  Date: 23.06.2023
  Time: 8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html><html><head>
<meta charset="utf-8">
<title>Пример прокрутки строки таблицы с PerfectScrollbar</title>
<link rel="stylesheet" type="text/css"
      href="https://cdnjs.cloudflare.com/ajax/libs/jquery.perfect-scrollbar/1.5.1/css/perfect-scrollbar.min.css">
<style>    table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
    border: 1px solid #ddd;
}

th {
    background-color: #f2f2f2;
}

.scroll-table-row {
    max-height: 100px;
    overflow-y: scroll;
}  </style>
</head><body>
<table>
    <thead>
    <tr>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Возраст</th>
    </tr>
    </thead>
    <tbody class="scroll-table-row">
    <tr class="perfect-scrollbar-content">
        <td>Иван</td>
        <td>Иванов</td>
        <td >
            <div>
                <ul>
                    <li>25</li>
                    <li>28</li>
                    <li>31</li>
                    <li>34</li>
                    <li>37</li>
                    <li>40</li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td>Петр</td>
        <td>Петров</td>
        <td>30</td>
    </tr>
    <tr>
        <td>Сергей</td>
        <td>Сергеев</td>
        <td>35</td>
    </tr>
    </tbody>
</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.perfect-scrollbar/1.5.1/perfect-scrollbar.min.js"></script>
<script>    jQuery(function ($) {
    $('.scroll-table-row').each(function () {
        var $row = $(this);
        $row.children('.perfect-scrollbar-content').perfectScrollbar();
    });
});  </script>
</body>
</html>

