let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;
let id = 1;
last_id = 1;
let last_timestamp = 0;


function update() {

    id = input.value;
    console.log("ID: " + id);
    console.log("Last ID: " + last_id);
    character.timestamp = last_timestamp;
    if (id != last_id) {
        last_id = id;
        last_timestamp = 0;
    }
    check_for_updates();
    console.log("Last timestamp: " + last_timestamp);
    output.innerHTML = "Name: " + character.name;
    setTimeout(update, 1000 / 0.5);
}

token = get_token();

username = get_username();

console.log("Token: " + token);

console.log("Username: " + username);