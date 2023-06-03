let address = "http://93.175.234.30:1290";

function send_and_save() {
    console.log("Sending and saving");
    console.log(typeof (JSON.stringify(character)));
    console.log(JSON.stringify(character));
    fetch(address + "/characters/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(character)
    })
}
function send_param(n_id) {
    fetch(address + "/characters/update/" + n_id, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(character)
    })
}
function check_for_updates() {
    fetch(address + "/characters/check-update/" + id + "?timestamp=" + last_timestamp, {
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

function create_character_server(new_character) {
    fetch(address + "/characters", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(new_character)
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
        });
    }
    )
}
function get_character_server(id) {
    fetch(address + "/characters/" + id, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
            parse_character_params(response_data);
        });
    })
}

function get_all_user_character_ids(user_id) {
    fetch(address + "/characters/for-user/" + user_id, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(async (response) => {
        await response.json().then(async (response_data) => {
            console.log(response_data);
            id = response_data[0];
        });
    })
}