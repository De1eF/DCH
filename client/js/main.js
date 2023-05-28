let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;

document.getElementById("send").addEventListener("click", function () {

    fetch("http://localhost:1290/square/"+input.value, {method: "GET"}).then(async (response)=> {
        await response.json().then(async (value) => {
            console.log(value.value);
            number = value.value;
            
        });
    });

   
});
function update(){
    output.innerHTML = "Your number is "+number;
    setTimeout(update, 1/10);
}

update();