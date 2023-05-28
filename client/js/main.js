
let input = document.getElementById("input");
let output = document.getElementById("output");

document.getElementById("send").addEventListener("click", function () {

    output.innerHTML = "Your number is "+input.value;
});