let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;
let id;
let last_timestamp = 0;

function inject(){
    fetch("http://localhost:1290/characters/inject?id="+id, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (response_data) => {
            last_timestamp = response_data.timestamp;
        });
    }
    );
}

function check_for_updates(){
    fetch("http://localhost:1290/characters/check-update?id="+id+"&timestamp="+last_timestamp, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (response_data) => {
            console.log(value.timestamp);
            if (last_timestamp != response_data.timestamp){
                last_timestamp = response_data.timestamp;
            }else{
                console.log("No updates");
            }
        });
    });
}
function send_update(param_name, value){
    fetch("http://localhost:1290/characters/send-update?id="+id+"&param-name="+param_name+"&value="+value, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (response_data) => {
            last_timestamp = response_data.timestamp; 
            console.log(last_timestamp);
        });
    });
}

function update(){
    check_for_updates();


    console.log("Last timestamp: "+last_timestamp);
    setTimeout(update, 1000/10);
}
update();