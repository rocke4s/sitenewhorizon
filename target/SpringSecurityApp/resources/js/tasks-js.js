$.getJSON("/clientall", function(messages) {
    messages.forEach(function(object) {
        // var parsed = JSON.parse(object);
        if (object.message != undefined) {
            var ull = document.getElementById("messageArea" + object.numberDoc);
            const li = document.createElement('li');
            if(object.userRecipient != undefined)
            {
                li.innerHTML = object.userRecipient + ": " + object.message;
            } else if (object.userSenders != undefined)
            {
                li.innerHTML = object.userSenders + ": " + object.message;
            }
            messageList = object.data;
            ull.appendChild(li);
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
    $.getJSON("/client", function(messages) {
        messages.forEach(function(object) {
            // var parsed = JSON.parse(object);
            if (object.message != undefined) {
                var ull = document.getElementById("messageArea" + object.numberDoc);
                const li = document.createElement('li');
                if(object.userSenders != undefined)
                {
                    li.innerHTML = object.userSenders + ": " + object.message;
                }
                if(object.userRecipient != undefined)
                {
                    li.innerHTML = object.userRecipient + ": " + object.message;
                }
                messageList = object.data;
                ull.appendChild(li);
            }
        });
    });
}, 1000);


const sidebar = document.querySelector('.sidebar')
function ShowModal(ids)
{
    sidebar.classList.toggle('active')
    document.getElementById('buttonShowM'+ids).hidden = true;
    document.getElementById('buttonHideM'+ids).hidden = false;
}
function HideModal(ids)
{
    sidebar.classList.toggle('active')
    document.getElementById('buttonShowM'+ids).hidden = false;
    document.getElementById('buttonHideM'+ids).hidden = true;
}
setInterval(function() {
    $.getJSON("/newchanges", function(mess) {
        mess.forEach(function(object) {
            if (object.changetype=="Изменение срока") {
                var sidebarUl = document.getElementById("sidebar-ul");
                const li = document.createElement('li');
                li.innerHTML = '<span class="change-type"><u>'+object.changetype+'</u></span><br>' +
                    ' <span class="task-title">'+object.nameTask+'</span><br>' +
                    ' <span class="change">Срок изменился на '+object.change+'</span>' +
                    ' <hr><span class="time">'+object.time+'</span>';
                sidebarUl.appendChild(li);
                sidebar.classList.toggle('active')
            }
            if (object.changetype=="Изменение статуса")
            {
                var sidebarUl = document.getElementById("sidebar-ul");
                const li = document.createElement('li');
                li.innerHTML = '<span class="change-type"><u>'+object.changetype+'</u></span><br>' +
                    ' <span class="task-title">'+object.nameTask+'</span><br>' +
                    ' <span class="change">'+object.change+'</span>' +
                    ' <hr><span class="time">'+object.time+'</span>';
                sidebarUl.appendChild(li);
                sidebar.classList.toggle('active')
            }
        });
    });
}, 1000);