

token = get_token();
username = get_username();
get_user_id();
console.log("Token: " + token);
console.log("Username: " + username);
console.log("User ID: " + user_id);

local_character = new character();

if (user_id != undefined) {
    get_all_user_character_ids(user_id);
    get_character_server(id);
} else {
    console.log("User ID undefined");
}
