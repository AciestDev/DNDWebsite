"use strict"

const loginForm = document.querySelector("form");
const inputUsername = loginForm.querySelector("#username");
const inputPassword = loginForm.querySelector("#password");



function handleLogin(event) {
    event.preventDefault();
    const username = inputUsername.value.trim();
    const password = inputPassword.value.trim();

    if (username === "admin" && password === "dndpass") {
        window.location.href = "../Websites/register.html"
    } else {
        const errMessage = document.querySelector(".error-message");
        errMessage.textContent = "Username or password not correct, try again";
    }
}

loginForm.addEventListener("submit", handleLogin);