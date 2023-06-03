//delete token and username from cookies
document.cookie = "token=;";
document.cookie = "username=;";
window.location.replace("login.html");