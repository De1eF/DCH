token = get_token();
username = get_username();
user_id = -1;
get_user_id(token);

function wait_for_data() {
    if (token == "" || username == "" || user_id == -1) {

    } else {
        console.log("Token: " + token);
        console.log("Username: " + username);
        console.log("User ID: " + user_id);
        get_all_user_character_ids(user_id);
        return
    }
    setTimeout(wait_for_data, 1000 / 10);
}
wait_for_data();

function update() {

    console.log("Updating");
    setTimeout(update, 1000 / 1);
}
update();