"use strict"
const inputUsername = document.getElementById("username");
const inputPassword = document.getElementById("password");
const loginForm = document.getElementById("login-form");


function handleLogin(event) {
    event.preventDefault();
    const username = inputUsername.value.trim();
    const password = inputPassword.value.trim();

    if (username === "admin" && password === "dndiscool") {
        window.location.href = "../Websites/register.html"
    }
}

loginForm.addEventListener("submit", handleLogin);