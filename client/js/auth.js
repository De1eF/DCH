//on button press alert the user that login is not implemented yet
//on button press
let address = "http://93.175.234.30:1290";

class Login_data {
    login = "";
    password = "";
}

let login_button = document.getElementById("login-button");
let login_data = new Login_data();
let login_input = document.getElementById("login-input");
let password_input = document.getElementById("password-input");


function send_login_data(login_data) {
    fetch(address + "/login", {
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
    send_login_data(login_data);

}

login_button.addEventListener("click", login);
