let address = "http://93.175.234.30:1290";

class Login_data {
    login = "";
    password = "";
}

let login_data = new Login_data();
let login_input = document.getElementById("login-input");
let password_input = document.getElementById("password-input");
let password_confirm_input = document.getElementById("password-input-confirm");


let login_button = document.getElementById("login-button");
if (login_button != null) {
    login_button.addEventListener("click", login);
}
let register_button = document.getElementById("register-button");
if (register_button != null) {
    register_button.addEventListener("click", register);
}

function send_login_data(login_data) {
    fetch(address + "/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(login_data)
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
            //save token to cookie
            document.cookie = "token=" + response_data.token;
            //save username to cookie
            document.cookie = "username=" + response_data.username;
            //redirect to main page
            window.location.replace("index.html");
        });
    })
}

function send_register_data(login_data) {
    fetch(address + "/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(login_data)
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
        });
    })
}

function login() {
    login_data.login = login_input.value;
    login_data.password = password_input.value;
    if (login_data.login == "" || login_data.password == "") {
        alert("Login or password is empty");
        return;
    }
    send_login_data(login_data);

}
function register() {
    login_data.login = login_input.value;
    login_data.password = password_input.value;
    login_data.password_confirm = password_confirm_input.value;
    if (login_data.login == "" || login_data.password == "" || login_data.password_confirm == "") {
        alert("Login or password is empty");
        return;
    } else if (login_data.password != login_data.password_confirm) {
        alert("Passwords do not match");
        return;
    }
    send_register_data(login_data);
}

