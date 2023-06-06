user_id = 0;
username = "";
id=0;
function get_token() {
    let token = document.cookie.split(";").filter((item) => {
        return item.includes("token");
    })[0];
    if (token == undefined) {
        return "";
    }
    token = token.split("=")[1];
    return token;
}

token = get_token();
let url_1 = window.location.href;
if (token == "") {
    if (!url_1.includes("login.html") && !url_1.includes("register.html") && !url_1.includes("login-email.html")) {
        window.location.replace("login.html");
    }
} else {
    if (url_1.includes("login.html") || url_1.includes("register.html") || url_1.includes("login-email.html")) {
        window.location.replace("index.html");
    }
}


function get_username() {
    let username = document.cookie.split(";").filter((item) => {
        return item.includes("username");
    })[0];
    if (username == undefined) {
        return "";
    }
    username = username.split("=")[1];
    return username;
}

function get_user_id() {
    fetch(address + "/users/me", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Access-Control-Allow-Origin": address,
            "Content-Type": "application/json"
        }
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
            console.log(response_data.id);
            user_id = response_data.id;
            username = response_data.username;
        });
    })
}


function check_token_front(token) {
    if (token.length < 10) {
        return false;
    }
    else {
        return true;
    }
}