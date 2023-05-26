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
            method: 'POST'
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
    socket.onmessage = function(event) {
        console.log(event.data);
        var parsed= JSON.parse(event.data);
        if(parsed.message!=undefined)
        {
            var ull = document.getElementById("messageArea"+parsed.NumberTask);
            const li = document.createElement('li');
            li.innerHTML = parsed.Name+": "+parsed.message;
            messageList = event.data;
            ull.appendChild(li);
        }
        if(parsed.ChangeType=="Изменение срока")
        {
            var sidebarUl = document.getElementById("sidebar-ul");
            const li = document.createElement('li');
            li.innerHTML = '<span class="change-type"><u>'+parsed.ChangeType+'</u></span><br>' +
                ' <span class="task-title">'+parsed.NameTask+'</span><br>' +
                ' <span class="change">Срок изменился на '+parsed.Until+'</span>' +
                ' <hr><span class="time">'+parsed.Time+'</span>';
            sidebarUl.appendChild(li);
            sidebar.classList.toggle('active')
        }
        if(parsed.ChangeType=="Изменение статуса")
        {
            var sidebarUl = document.getElementById("sidebar-ul");
            const li = document.createElement('li');
            li.innerHTML = '<span class="change-type"><u>'+parsed.ChangeType+'</u></span><br>' +
                ' <span class="task-title">'+parsed.NameTask+'</span><br>' +
                ' <span class="change">'+parsed.Until+'</span>' +
                ' <hr><span class="time">'+parsed.Time+'</span>';
            sidebarUl.appendChild(li);
            sidebar.classList.toggle('active')
        }
    };
}, 1000);

const sidebarToggle = document.querySelector('.sidebar-toggle')
const sidebar = document.querySelector('.sidebar')

sidebarToggle.addEventListener('click', function() {
    sidebar.classList.toggle('active')
})