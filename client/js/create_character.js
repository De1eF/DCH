token = ""
token = get_token();
username = get_username();
userId = get_user_id();
console.log("Token: " + token);
console.log("Username: " + username);

create_button = document.getElementById("create-button");


character_name_element = document.getElementById("character-name");
character_name = character_name_element.value;

class character {
    userId = 0;
    name = "";
    paramsMap = {};
}



function create_character() {
    let character1 = new character();
    character1.name = character_name_element.value;
    character1.userId = userId;
    console.log(character1.name);
    create_character_server(character1);
}

create_button.addEventListener("click", create_character);