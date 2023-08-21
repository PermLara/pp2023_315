const url = "http://localhost:8080/api/user/"

currentUser = fetch(url).then((response) => response.json())

currentUser.then((user) => {
    let roles = "";
    user.roles.forEach((name) => {
        roles += " ";
        roles += name.role.replace("ROLE_", "");
    });
    document.getElementById("navbar-email").innerHTML = user.email;
    document.getElementById("navbar-roles").innerHTML = roles;
});

currentUser.then((user) =>{
    let roles = "";
    user.roles.forEach((name) => {
        roles += " ";
        roles += name.role.replace("ROLE_", "");
    });

    let result = "";
    result += `<tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                   </tr>`;
    document.getElementById("user-table-body").innerHTML = result;
})