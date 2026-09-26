"use strict"
const form = document.getElementById("login-form");
const formHeader = form.querySelector("h2");
const submitButton = form.querySelector("button");
const switchLink = form.querySelector("a");
const responseMessage = form.querySelector("#responseMessage");

let isRegisterMode = true;

switchLink.addEventListener("click", () => {
    isRegisterMode = !isRegisterMode;

    if (isRegisterMode) {
        formHeader.textContent = "Create your account here!";
        submitButton.textContent = "Sign up!";
        switchLink.textContent = "already have an account? Login";
    } else {
        formHeader.textContent = "Welcome back! Login below.";
        submitButton.textContent = "Login";
        switchLink.textContent = "need an account? Register";
    }
});




form.addEventListener("submit", async function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const jsonObject = {
        username: username,
        password: password,
    }

    const endpoint = isRegisterMode ? "/api/register" : "/api/login";

    try {
        const response = await fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(jsonObject)
        });

        const data = await response.json();

        if (response.ok) {
            window.location.href = "../Websites/dashboard.html";
        } else {
            responseMessage.style.color = "red";
            responseMessage.textContent = data.message;
        }

    } catch (error) {
        responseMessage.style.color = "red";
        responseMessage.textContent = "Could not connect to the server.";
    }
});