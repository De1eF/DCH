let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;
let id=1;
let last_timestamp = 0;


class Character{
    id;
    name;
}

let character = new Character();

function parse_character_params(params){
    console.log(params);
    console.log("Not implemented");
}



function check_for_updates(){
    fetch("http://93.175.234.30:1290/characters/check-update?id="+id, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (response_data) => {
            if (last_timestamp != response_data.timestamp){
                last_timestamp = response_data.timestamp;
                parse_character_params(response_data.params);
            }else{
                console.log("No updates");
            }
        });
    });
}
function send_update(param_name, value){
    fetch("http://93.175.234.30:1290/characters/send-update?id="+id+"&param-name="+param_name+"&value="+value, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (response_data) => {
            last_timestamp = response_data.timestamp; 
            console.log(last_timestamp);
        });
    });
}

function update(){
    check_for_updates();


    console.log("Last timestamp: "+last_timestamp);
    setTimeout(update, 1000/1);
}
update();