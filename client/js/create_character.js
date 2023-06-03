token = ""
token = get_token();
username = get_username();
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

let character1 = new character();

character1.name = character_name;

function create_character() {
    character1.name = character_name_element.value;
    console.log(character1.name);
    create_character_server(character1);
}

create_button.addEventListener("click", create_character);