
console.log(url);

let token = url;
//remove everything before "?token="
token = token.substring(token.indexOf("?token=") + 7);
console.log(token);

get_user_id(token);

document.cookie = "token=" + token;
document.cookie = "username=" + username;
document.cookie = "userId=" + userId;