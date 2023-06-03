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
let url = window.location.href;
if (token == "") {
    if (!url.includes("login.html") && !url.includes("register.html")) {
        window.location.replace("login.html");
    }
} else {
    if (url.includes("login.html") || url.includes("register.html")) {
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
            "Authorization": "Bearer " + token
        }
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
            user_id = response_data.id;
        });
    })
}
