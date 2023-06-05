token = get_token();
username = get_username();
userId = get_user_id();
console.log("Token: " + token);
console.log("Username: " + username);
console.log("User ID: " + userId);
id = 1;

local_character = new character();

if (userId != undefined) {
    get_all_user_character_ids(userId);
    get_character_server(id);
}

