
let input = document.getElementById("input");
let output = document.getElementById("output");
let number = 0;

document.getElementById("send").addEventListener("click", function () {

    fetch("http://localhost:1290/square/"+input.value, {method: "GET"}).then(function (response) {
        console.log(response);
        number = response;
        return response;
    });

    output.innerHTML = "Your number is "+number;

});