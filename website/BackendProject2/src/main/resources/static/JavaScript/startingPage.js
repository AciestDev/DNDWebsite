"use strict"

const loginForm = document.querySelector("form");
const inputUsername = loginForm.querySelector("#username");
const inputPassword = document.querySelector("#password");



function handleLogin(event) {
    event.preventDefault();
    const username = inputUsername.value.trim();
    const password = inputPassword.value.trim();

    if (username === "admin" && password === "dndpass") {
        window.location.href = "../Websites/register.html"
    }
}

loginForm.addEventListener("submit", handleLogin);