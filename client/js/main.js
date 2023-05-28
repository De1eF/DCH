let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;
let id = 1;
last_id = 1;
let last_timestamp = 0;

let adress = "http://93.175.234.30:1290";

class Character {
    timestamp = 0;
    name;
}

let character = new Character();

function parse_character_params(params) {
    console.log(params);
    character.name = params.name;
    console.log("Not implemented");
}
//networking
function check_for_updates() {
    fetch(adress + "/characters/check-update/" + id + "?timestamp=" + last_timestamp, {
        method: "GET"
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            if (last_timestamp != response_data.timestamp) {
                last_timestamp = response_data.timestamp;
                parse_character_params(response_data.object);
            } else {
                console.log("No updates");
            }
        });
    })
}
//save 
function send_and_save() {
    console.log("Sending and saving");
    console.log(typeof (JSON.stringify(character)));
    console.log(JSON.stringify(character));
    fetch(adress + "/characters/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(character)
    }).then(async (response) => {
    }
    )
}
function send_param() {
    fetch(adress + "/characters/update/" + id, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(character)
    }).then(async (response) => {
    }
    )
}

//update
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
update();