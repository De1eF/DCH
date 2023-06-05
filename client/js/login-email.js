let address = "";

let url = window.location.href;

function get_backend_address() {
    let backend_address = "";
    //remove everything after second :
    backend_address = url.substring(0, url.indexOf(":", url.indexOf(":") + 1));
    //add 5500 port
    backend_address += ":1290";
    return backend_address;
}
address = get_backend_address();

let token = url;
//remove everything before "?token="
token = token.substring(token.indexOf("?token=") + 7);
console.log(token);

token_good = check_token_front(token);

if (token_good) {
    get_user_id(token);

    document.cookie = "token=" + token;
    document.cookie = "username=" + username;
    document.cookie = "userId=" + userId;
}