//вывод количества заявок в общем
const count = document.getElementsByClassName('taskTr').length;
const allStats = document.getElementById('allStats');
allStats.innerHTML = 'Всего заявок: ' + count;
//конец вывода

//вывод количества заявок с прошлого месяца
let taskData = document.getElementsByClassName('taskData');
let taskStatus = document.getElementsByClassName('taskStatus');
let taskIntensity = document.getElementsByClassName('taskIntensity');
const currentDate = new Date();
const startOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
var count2 = 0;
for (var y = 0; y < taskStatus.length; y++) {
    if (taskStatus.item(y).innerHTML !== "Проверено" || taskStatus.item(y).innerHTML !== "Отменено") {
        const dateString = taskData.item(y).innerHTML;
        const dateParts = dateString.split(' ');
        const date = dateParts[0].split('.');
        const time = dateParts[1].split(':');
        const year = parseInt(date[2], 10);
        const month = parseInt(date[1], 10) - 1;
        const day = parseInt(date[0], 10);
        const hours = parseInt(time[0], 10);
        const minutes = parseInt(time[1], 10);
        const seconds = parseInt(time[2], 10);
        const checkDate = new Date(year, month, day, hours, minutes, seconds);
        if (checkDate >= startOfMonth) {
        } else {
            count2++;
        }
    }
}
allStats.innerHTML += ' С прошлого месяца: ' + count2 + '<br>';
//конец вывода
var count3 = 0;
//вывод количества новых заявок
for (var x = 0; x < taskData.length; x++) {
    const dateString = taskData.item(x).innerHTML;
    const dateParts = dateString.split(' ');
    const date = dateParts[0].split('.');
    const time = dateParts[1].split(':');
    const year = parseInt(date[2], 10);
    const month = parseInt(date[1], 10) - 1;
    const day = parseInt(date[0], 10);
    const hours = parseInt(time[0], 10);
    const minutes = parseInt(time[1], 10);
    const seconds = parseInt(time[2], 10);
    const checkDate = new Date(year, month, day, hours, minutes, seconds);
    if (checkDate.getMonth() === currentDate.getMonth()) {
        count3++;
    } else {
    }
}
allStats.innerHTML += ' Новых заявок: ' + count3;
//конец вывода

//вывод количества выполненных
var count4 = 0;
for (var y = 0; y < taskStatus.length; y++) {
    if (taskStatus.item(y).innerHTML === "Проверено") {
        const dateString = taskData.item(y).innerHTML;
        const dateParts = dateString.split(' ');
        const date = dateParts[0].split('.');
        const time = dateParts[1].split(':');
        const year = parseInt(date[2], 10);
        const month = parseInt(date[1], 10) - 1;
        const day = parseInt(date[0], 10);
        const hours = parseInt(time[0], 10);
        const minutes = parseInt(time[1], 10);
        const seconds = parseInt(time[2], 10);
        const checkDate = new Date(year, month, day, hours, minutes, seconds);
        count4++;
    }
}
allStats.innerHTML += ' Выполнено: ' + count4 + '<br>';
//конец вывода

//вывод % исполнения задач
var percent = 0;
percent = (count4 / count) * 100;
allStats.innerHTML += ' Исполнение: ' + percent.toFixed(0) + '%';
//конец вывода

//вывод трудозатрат
var count5 = 0;
for (var x = 0; x < taskData.length; x++) {
    if (taskStatus.item(x).innerHTML === "Проверено") {
        count5 += parseInt(taskIntensity.item(x).innerHTML);
    }
}
allStats.innerHTML += ' Трудозатраты: ' + count5 + '<br>';
//конец вывода

//Фильтрация заявок
let taskDepartment = document.getElementsByClassName('taskDepartment');
let taskAuthor = document.getElementsByClassName('taskAuthor');
let taskPartner = document.getElementsByClassName('taskPartner');

function filtrOrg() {//по организации
    var table = document.getElementById("table-class");
    var tbodi = table.tBodies;
    var arr = [];
    for (let i = 0; i < tbodi.length; i++) {
        arr[i] = [];
        for (let j = 0; j < 2; j++) {
            if (j === 0) {
                arr[i][j] = tbodi[i].rows[1].cells[1].innerHTML;
            } else {
                arr[i][j] = tbodi[i].rows[1].cells[0].innerHTML;
            }
        }
    }
    arr.sort(compareFirstValues);
    let sortedArr = arr.map(elem => elem[1]);
    for (var k = 0; k < sortedArr.length; k++) {
        for (var j = 0; j < sortedArr.length; j++) {
            if (tbodi[j].rows[1].cells[0].innerHTML === sortedArr[k]) {
                table.appendChild(tbodi[j]);
            }
        }
    }
}

function compareFirstValues(a, b) {
    if (a[0] > b[0]) {
        return 1;
    }
    if (a[0] < b[0]) {
        return -1;
    }
    return 0;
}

function filtrDepart() {//по отделу
    var table = document.getElementById("table-class");
    var tbodi = table.tBodies;
    var arr = [];
    for (let i = 0; i < tbodi.length; i++) {
        arr[i] = [];
        for (let j = 0; j < 2; j++) {
            if (j === 0) {
                arr[i][j] = tbodi[i].rows[1].cells[12].innerHTML;
            } else {
                arr[i][j] = tbodi[i].rows[1].cells[0].innerHTML;
            }
        }
    }
    arr.sort(compareFirstValues);
    let sortedArr = arr.map(elem => elem[1]);
    for (var k = 0; k < sortedArr.length; k++) {
        for (var j = 0; j < sortedArr.length; j++) {
            if (tbodi[j].rows[1].cells[0].innerHTML === sortedArr[k]) {
                table.appendChild(tbodi[j]);
            }
        }
    }
}

function filtrWorker() {// по сотруднику
    var table = document.getElementById("table-class");
    var tbodi = table.tBodies;
    var arr = [];
    for (let i = 0; i < tbodi.length; i++) {
        arr[i] = [];
        for (let j = 0; j < 2; j++) {
            if (j === 0) {
                arr[i][j] = tbodi[i].rows[1].cells[13].innerHTML;
            } else {
                arr[i][j] = tbodi[i].rows[1].cells[0].innerHTML;
            }
        }
    }
    arr.sort(compareFirstValues);
    let sortedArr = arr.map(elem => elem[1]);
    for (var k = 0; k < sortedArr.length; k++) {
        for (var j = 0; j < sortedArr.length; j++) {
            if (tbodi[j].rows[1].cells[0].innerHTML === sortedArr[k]) {
                table.appendChild(tbodi[j]);
            }
        }
    }
}

//конец вывода

//Выборка периода заявок по дате
const dateRangeSelect = document.getElementById('date-range-select');
const startDateInput = document.getElementById('start-date');
const endDateInput = document.getElementById('end-date');
const submitBtn = document.getElementById('submit-btn');
const outputDiv = document.getElementById('output');
dateRangeSelect.addEventListener('change', () => {
    const today = moment().startOf('day');
    let startDate;
    let endDate;
    switch (dateRangeSelect.value) {
        case 'month':
            startDate = moment().startOf('month');
            endDate = today;
            break;
        case 'quarter':
            startDate = moment().startOf('quarter');
            endDate = today;
            break;
        case 'week':
            startDate = moment().startOf('isoWeek');
            endDate = today;
            break;
        case 'year':
            startDate = moment().startOf('year');
            endDate = today;
            break;
        default:
            startDate = moment().subtract(7, 'days');
            endDate = today;
            break;
    }
    startDateInput.value = startDate.format('YYYY-MM-DD');
    endDateInput.value = endDate.format('YYYY-MM-DD');
});
submitBtn.addEventListener('click', () => {
    const startDate = moment(startDateInput.value);
    const endDate = moment(endDateInput.value);
    var table = document.getElementById("table-class");
    var rows = table.tBodies;
    if (startDate.isAfter(endDate)) {
        outputDiv.textContent = 'Конечная дата не может быть раньше Начальной!';
        return;
    }
    const data = [{date: '2021-01-01', value: 10}, {date: '2021-01-02', value: 20}, {
        date: '2021-01-03',
        value: 15
    }, {date: '2021-01-04', value: 30}, {date: '2021-01-05', value: 25}];
    const filteredData = data.filter(item => {
        const itemDate = moment(item.date);
        return itemDate.isBetween(startDate, endDate, null, '[]');
    });
    let output = '';
    filteredData.forEach(item => {
        output += `<div>Date: ${item.date}, Value: ${item.value}</div>`;
    });
    outputDiv.innerHTML = output;
// Проходимся циклом по всем строкам таблицы
    for (var i = 0; i < rows.length; i++) {
        // Получаем значение ячейки с датой
        var dateString = rows[i].rows[1].cells[11].innerHTML;
        // Преобразуем строку в объект Date
        var date = new Date(dateString.replace(/(\d{2}).(\d{2}).(\d{4}) (\d{2}):(\d{2}):(\d{2})/, '$3-$2-$1T$4:$5:$6Z'));
        // Если дата не входит в промежуток, скрываем строку
        if (date < startDate || date > endDate) {
            rows[i].style.display = "none";
        } else {
            rows[i].style.display = "";
        }
    }
});
//конец вывода

//Поиск по задачам
function handleSearch() {
    var tableId = [];
    var searchQuery = document.getElementById('searchbox').value;
    // находим все теги td на странице
    var tds = document.getElementsByTagName('td');
    // проходимся по всем найденным тегам td
    var x = 0;
    for (var i = 0; i < tds.length; i++) {
        var td = tds[i];
        // если содержимое тега td содержит искомый текст, то
        if (searchQuery && td.textContent.includes(searchQuery)) {
            // находим родительский тег table
            var table = td.closest('tbody');
            // получаем id тега table
            tableId[x] = table.getAttribute('id');
            x++;
        }
    }
    var tableElements = document.getElementsByClassName('taskTr');
    if (searchQuery != "") {
        var tdElements = document.getElementsByTagName('td');
        for (var i = 0; i < tdElements.length; i++) {
            var tdElement = tdElements[i];
            var tableElement = tdElement.closest('tbody');
            if (tableElement) {
                tableElement.style.display = 'none';
            }
        }
        for (var z = 0; z < tableId.length; z++) {
            for (var i = 0; i < tableElements.length; i++) {
                if (tableElements[i].id === document.getElementById(tableId[z]).id) {
                    tableElements[i].style.display = '';
                }
            }
        }
    } else if (searchQuery === "") {
        var tdElements = document.getElementsByTagName('td');
        for (var i = 0; i < tdElements.length; i++) {
            var tdElement = tdElements[i];
            var tableElement = tdElement.closest('tbody');
            if (tableElement) {
                tableElement.style.display = '';
            }
        }

    }
}

//Конец поиска