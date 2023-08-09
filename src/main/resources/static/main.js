const url = 'http://localhost:8080/api/admin';
getAdminPage();
editInDB();
deleteInDB();

function getAdminPage() {

    fetch(url)
        .then(response => response.json())
        .then(listUsers => loadTable(listUsers))
}

function loadTable(listUsers) {
    let response = ''
    for (let user of listUsers) {
        response +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.username}</td>
                <td id=${'role' + user.id}>${user.roles.map(role => role.role.replaceAll("ROLE_","")).join(' ')}</td>
                <td>
                    <button class="btn btn-info" type="button" id="buttonEdit"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger" type="button" id="buttonDelete"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button>
                </td>
            </tr>`
    }
    document.getElementById('admin-table-body').innerHTML = response
}


function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}

function hideVPiIllsTabContent() {
    document.getElementById("v-pills-tabContent").style.display = 'none'
    document.getElementById("v-pills-user").style.display = 'block'
}

function hideVPiIllsUser() {
    document.getElementById("v-pills-user").style.display = 'none'
    document.getElementById("v-pills-tabContent").style.display = 'block'
}

function setDisplayVPiIllsUser() {
    if (document.getElementById("principalUserRoles").innerText.includes('ADMIN')) {
        document.getElementById("v-pills-admin-tab").click()
    }
}


document.getElementById('create-form').addEventListener('submit', e => {
    e.preventDefault();
    let options = document.getElementById('create-roles').options;
    let rolesToArray = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            rolesToArray.push({id: options[i].value, role: 'ROLE_' + options[i].innerHTML})
        }
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            firstName: document.getElementById('create-firstName').value,
            lastName: document.getElementById('create-lastName').value,
            username: document.getElementById('create-username').value,
            password: document.getElementById('create-password').value,
            roles: rolesToArray
        })
    })
        .then(response => {
            if (response.ok) {
                fetch(url)
                    .then(res => res.json())
                    .then(listUsers => {
                        loadTable(listUsers)
                    })
                document.getElementById("all-users-tab").click()
            }
        })
})

function editModal(id) {
    let editForm = document.forms["editForm"];

    fetch(url + '/' + id, {
        method: "GET",
        headers: {

            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(response => {
        response.json()
            .then(u => {
                editForm.elements.id.value = u.id;
                editForm.firstName.value = u.firstName;
                editForm.lastName.value = u.lastName;
                editForm.username.value = u.username;
                editForm.password.value = "****";
                let options = editForm.roles.options;
                constructRoles(u, options);
            })
    })
}

function constructRoles(u, options) {
    for (let i = 0; i < options.length; i++) {
        let roleName = options[i].innerHTML;
        for (let j = 0; j < u.roles.length; j++) {
            if (u.roles[j].role === "ROLE_" + roleName) {
                options[i].selected = true;
                break;
            }
            options[i].selected = false;
        }
    }
}

function editInDB() {
    document.getElementById("edit-form").addEventListener('submit', e => {
        e.preventDefault();
        const id = document.getElementById("editId").value;
        let editForm = document.forms["editForm"];
        let options = editForm.roles.options;
        let rolesToArray = [];
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                rolesToArray.push({id: options[i].value, role: 'ROLE_' + options[i].innerHTML});
            }

        }

        fetch(url + "/" + id,
            {
                method: "PATCH",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({
                    id: id,
                    firstName: editForm.firstName.value,
                    lastName: editForm.lastName.value,
                    username: editForm.username.value,
                    password: editForm.password.value,
                    roles: rolesToArray
                })
            }).then((response) => {
            if (response.ok) {
                closeModal()
                fetch(url)
                    .then(res => res.json())
                    .then(listUsers => {
                        loadTable(listUsers)
                    })
                document.getElementById("all-users-tab").click()
            }
        })
    })
}

function deleteModal(id) {

    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(response => {
        response.json().then(u => {
            document.getElementById('delete-id').value = u.id;
            document.getElementById('delete-firstName').value = u.firstName;
            document.getElementById('delete-lastName').value = u.lastName;
            document.getElementById('delete-username').value = u.username;
            let options = document.forms["deleteForm"].roles.options;
            constructRoles(u, options);
        })
    })
}

function deleteInDB() {
    document.getElementById('delete-form').addEventListener('submit', e => {
        e.preventDefault()
        const id = document.getElementById("delete-id").value

        fetch(url + "/" + id,
            {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/json"
                }
            }).then((response) => {
            if (response.ok) {
                closeModal()
                fetch(url)
                    .then(res => res.json())
                    .then(listUsers => {
                        loadTable(listUsers)
                    })
                document.getElementById("all-users-tab").click()
            }
        })
    })
}
