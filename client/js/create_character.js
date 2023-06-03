token = ""
token = get_token();
username = get_username();
console.log("Token: " + token);
console.log("Username: " + username);

class character {
    userId = 0;
    name = "";
    paramsMap = {};
}

let character1 = new character();

//generate random name
character1.name = "Character" + Math.floor(Math.random() * 1000);

create_character_server(character1);