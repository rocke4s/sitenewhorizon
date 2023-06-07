$.getJSON("/clientall", function(messages){
    messages.forEach(function(objects){
        if (objects.message !== undefined) {
            var ul = document.getElementById("messageArea" + objects.numberTask);
            if(ul!=null)
            {
                var li = document.createElement('li');
                if(!(objects.userSenders === null || objects.userSenders ===undefined))
                {
                    li.innerHTML = "["+objects.dateSend+"] "+objects.userSenders + ": " + objects.message;
                    ul.appendChild(li);
                }
                if(!(objects.userRecipient === null || objects.userRecipient ===undefined))
                {
                    li.innerHTML = "["+objects.dateSend+"] "+objects.userRecipient + ": " + objects.message;
                    ul.appendChild(li);
                }
                messageList = objects.data;
            }
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
let listul = null;
function ShowChat(ids)
{
    listul = document.getElementById('messageArea'+ids);
    document.getElementById('id_'+ids).hidden= false;
    listul.scrollTop = listul.scrollHeight;
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
            if (objects.message !== undefined) {
                var ul = document.getElementById("messageArea" + objects.numberTask);
                if(ul!=null)
                {
                    var li = document.createElement('li');
                    if(!(objects.userSenders === null || objects.userSenders ===undefined))
                    {
                        li.innerHTML = "["+objects.dateSend+"] "+objects.userSenders + ": " + objects.message;
                        ul.appendChild(li);
                    }
                    if(!(objects.userRecipient === null || objects.userRecipient ===undefined))
                    {
                        li.innerHTML = "["+objects.dateSend+"] "+objects.userRecipient + ": " + objects.message;
                        ul.appendChild(li);
                    }
                    messageList = objects.data;
                }
            }
        });
    });
    $.getJSON("/newchanges", function(mess) {
        mess.forEach(function(object) {
            var sidebarUl = document.getElementById('sidebar-ul');
            var statusid = document.getElementById('statusid'+object.numberTask);
            var deadline = document.getElementById('deadline'+object.numberTask);
            var nametask = document.getElementById('id'+object.nameTask);
            var buttonshow = document.getElementById('buttonShowM'+object.numberTask);
            var sumrTaskid = document.getElementById('sumr'+object.numberTask);
            var changeLogId = document.getElementById('changeLogId'+object.id);
            buttonshow.style.backgroundColor = "#FF6347";
            sumrTaskid.style.backgroundColor = "#FF6347";
            var li = document.createElement('li');
            // if(nametask!=null && nametask.innerHTML === object.nameTask) {
            //             if (object.changetype == "Изменение срока") {
            //                 li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
            //                     ' <span class="task-title">' + object.nameTask + '</span><br>' +
            //                     ' <span class="change">' + object.change + '</span>' +
            //                     ' <hr id="changeLogId'+object.id+'"><span class="time">' + object.time + '</span>';
            //
            //             }
            //             if (object.changetype == "Изменение статуса") {
            //                 li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
            //                     ' <span class="task-title">' + object.nameTask + '</span><br>' +
            //                     ' <span class="change">' + object.change + '</span>' +
            //                     ' <hr id="changeLogId'+object.id+'"><span class="time">' + object.time + '</span>';
            //             }
            //             li.style.backgroundColor = "#FF6347";
            //             sidebarUl.appendChild(li);
            // }
            if (object.changetype=="Изменение срока") {
                deadline.style.backgroundColor = "#FF6347";
            }
            if (object.changetype=="Изменение статуса")
            {
                statusid.style.backgroundColor = "#FF6347";
            }
        });
    });
}, 500);


const sidebar = document.querySelector('.sidebar');
var sidebarUl = document.getElementById("sidebar-ul");
function ShowModal(ids,nametask)
{
    var elementsShow = document.getElementsByClassName("buttonShowM");
    var elementsHide = document.getElementsByClassName('buttonHideM');
    for (var i = 0; i < elementsShow.length; i++) {
        elementsShow[i].hidden = true;
        elementsHide[i].hidden = false;
    }
    $.getJSON("/changesall", function(mess) {
        sidebarUl.innerHTML="";
        sidebarUl.innerHTML='<h2 id="id'+nametask+'">'+nametask+'</h2>';
        mess.forEach(function(object) {
            var li = document.createElement('li');
            var statusid = document.getElementById('statusid'+ids);
            var deadline = document.getElementById('deadline'+ids);
            var buttonshow = document.getElementById('buttonShowM'+ids);
            var sumrTaskid = document.getElementById('sumr'+ids);
        if(object.numberTask===ids) {
                if (object.changetype === 'Изменение срока') {
                    li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
                        ' <span id="id'+object.nameTask+'" class="task-title">' + object.nameTask + '</span><br>' +
                        ' <span class="change">' + object.change + '</span>' +
                        ' <hr id="changeLogId'+object.id+'"><span class="time">' + object.time + '</span>';
                }
                if (object.changetype === 'Изменение статуса') {
                    li.innerHTML = '<span class="change-type"><u>' + object.changetype + '</u></span><br>' +
                        ' <span id="id'+object.nameTask+'" class="task-title">' + object.nameTask + '</span><br>' +
                        ' <span class="change">' + object.change + '</span>' +
                        ' <hr id="changeLogId'+object.id+'"><span class="time">' + object.time + '</span>';
                }
                if(object.isNewChanges==="new")
                {
                    sumrTaskid.style.backgroundColor = "#FF6347";
                    li.style.backgroundColor = "#FF6347";
                    buttonshow.style.backgroundColor = "#FF6347";
                    if (object.changetype=="Изменение срока") {
                        deadline.style.backgroundColor = "#FF6347";
                    }
                    if (object.changetype=="Изменение статуса")
                    {
                        statusid.style.backgroundColor = "#FF6347";
                    }
                }
                sidebarUl.appendChild(li);
        }
        });
    });
    sidebar.classList.toggle('active');
}
function HideModal(numberDoc)
{
    sidebar.classList.toggle('active')
    var statusid = document.getElementById('statusid'+numberDoc);
    var deadline = document.getElementById('deadline'+numberDoc);
    var elementsShow = document.getElementsByClassName("buttonShowM");
    var buttonShow = document.getElementById("buttonShowM"+numberDoc);
    var elementsHide = document.getElementsByClassName('buttonHideM');
    var sumrTaskid = document.getElementById('sumr'+numberDoc);
    sumrTaskid.style.backgroundColor = "#69c773";
    deadline.style.backgroundColor= "#f8f9fa";
    statusid.style.backgroundColor= "#f8f9fa";
    buttonShow.style.backgroundColor= "#555";
    for (var i = 0; i < elementsShow.length; i++) {
        elementsShow[i].hidden = false;
        elementsHide[i].hidden = true;
    }
    $.ajax({
        method: 'GET',
        url: '/stopred',
        headers: {
            "NumberTask": encodeURIComponent(''+numberDoc)
        }
    })
        .then(function(response) {
            console.log(response);
        })
        .catch(function(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 404) {
            } else {
                console.error(error);
            }
        });
}
const modal = document.querySelector('#modals');
const closeModalButton = document.querySelector('#close-modals');
closeModalButton.addEventListener('click', () => {
    modal.style.display = 'none';
    modal.hidden = true;
});
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
        modal.hidden = true;
    }
});
function sendMessage1(numberDoc,uidDoc) {
    var date = new Date();
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    const time = date.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
    var newDate = day+"."+month+"."+year+" "+time;
    $.ajax({
        method: 'GET',
        url: '/worker',
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
    document.querySelector('#id_' + numberDoc + ' input[type="text"]').value = "";
}
function showFullText() {
    const modal = document.querySelector('#modals');
    modal.style.display = 'block';
    modal.hidden = false;
    const fullText = document.querySelector('#full-text');
    fullText.innerHTML = document.getElementById(this.id).innerHTML.replace(/<button[^>]*>.*<\/button>/gi, "");
}
var elements = document.getElementsByClassName('short-text');
for (var i = 0; i < elements.length; i++) {
    if (elements[i].innerHTML.length > 200) {
        var button = document.createElement("button");
        button.setAttribute("class", "read-more-button");
        button.innerHTML = "Прочитать полностью";
        elements[i].style = "overflow: hidden; text-overflow: ellipsis; white-space: nowrap; max-width: 200px;"
        button.id = elements[i].id;
        button.onclick =showFullText;
        elements[i].appendChild(button);
    }
}