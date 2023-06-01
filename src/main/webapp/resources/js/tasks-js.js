$.getJSON("/clientall", function(messages){
    messages.forEach(function(objects){
        console.log(objects);
        if (objects.message !== undefined) {
            var ul = document.getElementById("messageArea" + objects.numberTask);
            var li = document.createElement('li');
            if(!(objects.userSenders === null || objects.userSenders ===undefined))
            {
                li.innerHTML = objects.userSenders + ": " + objects.message;
                ul.appendChild(li);
            }
            if(!(objects.userRecipient === null || objects.userRecipient ===undefined))
            {
                li.innerHTML = objects.userRecipient + ": " + objects.message;
                ul.appendChild(li);
            }
            messageList = objects.data;
        }
    });
});


var tdElements = document.getElementsByTagName('td');
for (var i = 0; i < tdElements.length; i++) {
    var tdElement = tdElements[i];
    if (tdElement.innerHTML === 'Проверено' || tdElement.innerHTML === 'Отменено') {
        var detailsElement = tdElement.closest('details');
        if (detailsElement) {
            detailsElement.style.display = 'none';

        }
    }
}
const forms = document.querySelectorAll('.myForm');
forms.forEach(form => {
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const uidDoc_8 = form.elements.uidDoc_8.value;
        const myInput = document.getElementById("uidDoc_88");
        myInput.value = uidDoc_8;
        const saveNameTask = form.elements.NameTasks.value;
        const myInput2 = document.getElementById("NameTasker");
        myInput2.value = saveNameTask;
        fetch('/change_status?uidDoc_8='+uidDoc_8, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error(error);
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

function hideEnding(stat)
{
    var tdElements = document.getElementsByTagName('td');
    var togLabel = document.getElementById("togLabel");
    for (var i = 0; i < tdElements.length; i++) {
        var tdElement = tdElements[i];
        if (tdElement.innerHTML === 'Проверено' || tdElement.innerHTML === 'Отменено') {
            var detailsElement = tdElement.closest('details');
            if (detailsElement) {
                if(stat.checked) {
                    togLabel.innerHTML = 'Показать все задачи';
                    detailsElement.style.display = 'none';
                }
                else
                {
                    togLabel.innerHTML = 'Скрыть неактивные задачи';
                    detailsElement.style.display = '';
                }
            }
        }
    }
}

setInterval(function() {
    $.getJSON("/client", function(messages2){
        messages2.forEach(function(objects) {
            console.log(objects);
            if (objects.message !== undefined) {
                var ul = document.getElementById("messageArea" + objects.numberTask);
                var li = document.createElement('li');
                if(!(objects.userSenders === null || objects.userSenders ===undefined))
                {
                    li.innerHTML = objects.userSenders + ": " + objects.message;
                    ul.appendChild(li);
                }
                    if(!(objects.userRecipient === null || objects.userRecipient ===undefined))
                {
                    li.innerHTML = objects.userRecipient + ": " + objects.message;
                    ul.appendChild(li);
                }
                messageList = objects.data;
            }
        });
    });
    $.getJSON("/newchanges", function(mess) {
        console.log(mess);
        mess.forEach(function(object) {
            var sidebarUl = document.getElementById('sidebar-ul');
            var statusid = document.getElementById('statusid'+object.numberTask);
            var deadline = document.getElementById('deadline'+object.numberTask);
            var li = document.createElement('li');
            if (object.changetype=="Изменение срока") {
                li.innerHTML = '<span class="change-type"><u>'+object.changetype+'</u></span><br>' +
                    ' <span class="task-title">'+object.nameTask+'</span><br>' +
                    ' <span class="change">Срок изменился на '+object.change+'</span>' +
                    ' <hr><span class="time">'+object.time+'</span>';
                deadline.style.backgroundColor = "#FF6347";

            }
            if (object.changetype=="Изменение статуса")
            {
                statusid.style.backgroundColor = "#FF6347";
                li.innerHTML = '<span class="change-type"><u>'+object.changetype+'</u></span><br>' +
                    ' <span class="task-title">'+object.nameTask+'</span><br>' +
                    ' <span class="change">'+object.change+'</span>' +
                    ' <hr><span class="time">'+object.time+'</span>';
            }
            li.style.backgroundColor = "#FF6347";
            sidebarUl.appendChild(li);
            sidebar.classList.toggle('active');
        });
    });
}, 500);


const sidebar = document.querySelector('.sidebar')
var sidebarUl = document.getElementById("sidebar-ul");
function ShowModal(ids)
{
    var elementsShow = document.getElementsByClassName("buttonShowM");
    var elementsHide = document.getElementsByClassName('buttonHideM');
    for (var i = 0; i < elementsShow.length; i++) {
        elementsShow[i].hidden = true;
        elementsHide[i].hidden = false;
    }
    $.getJSON("/changesall", function(mess) {
        sidebarUl.innerHTML="";
        mess.forEach(function(object) {
            var li = document.createElement('li');
        if(object.numberTask===ids) {
            if (object.changetype === 'Изменение срока') {
                li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
                    ' <span class="task-title">' + object.nameTask + '</span><br>' +
                    ' <span class="change">Срок изменился на ' + object.change + '</span>' +
                    ' <hr><span class="time">' + object.time + '</span>';
            }
            if (object.changetype === 'Изменение статуса') {
                li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
                    ' <span class="task-title">' + object.nameTask + '</span><br>' +
                    ' <span class="change">' + object.change + '</span>' +
                    ' <hr><span class="time">' + object.time + '</span>';
            }
            sidebarUl.appendChild(li);
        }
        });
        sidebar.classList.toggle('active');
    });
}
function HideModal()
{
    sidebar.classList.toggle('active')
    var elementsShow = document.getElementsByClassName("buttonShowM");
    var elementsHide = document.getElementsByClassName('buttonHideM');
    for (var i = 0; i < elementsShow.length; i++) {
        elementsShow[i].hidden = false;
        elementsHide[i].hidden = true;
    }
}