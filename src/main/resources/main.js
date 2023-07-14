const url_admin = 'http://localhost:8080/api/admin';

function getAdminPage() {
    console.log("2:hello_from_resources_main.js");
    fetch(url_admin)
        .then(response => response.json())
        .then(listUsers => loadTable(listUsers));
}

function loadTable(listUsers) {
    let response = '';
    console.log("1:hello_from_resources_main.js");
    for (let user of listUsers) {
        response +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.username}</td>
                <td id=${'role' + user.id}>${user.roles.map(role => role.role).join(' ')}</td>
            </tr>`;
    }
    document.getElementById('admin-table-body').innerHTML = response;
}

getAdminPage();


$(document).ready(function () {

    //самый простой тест того, что страница делает запрос к рест-контроллеру
    //для проверки обращения ставим брейкпоинт в рест-контроллере
    $.ajax("/api/admin");

    // показывает алерт с возвращенным значением
    $.ajax("/api/admin",
        {
            dataType: "json", //или, например, "text"
            success: function (msg) { //msg - то, что придет с сервера, респонз
                alert("Прибыли данные: " + msg);
            }
        })
})