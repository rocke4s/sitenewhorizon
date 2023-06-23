function showFileNames() {
    let fileInput = document.getElementById('file');
    let output = document.getElementById('files-selected');
    let strFiles ='';
    for(var x=0;x<fileInput.files.length;x++)
    {
        strFiles += fileInput.files[x].name+"<br>";
    }
    if(fileInput.files.length>1)
    {
        output.innerHTML = 'Выбранныe файлы:'+strFiles;
    }
    else {
        output.innerHTML = 'Выбранный файл:'+strFiles;
    }
}