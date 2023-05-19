function showFileName()
{
    let fileInput = document.getElementById('file');
    let fileName = fileInput.files[0].name;
    let output = document.getElementById('file-selected');
    output.innerHTML = `Выбранный файл: ${fileName}`;
}