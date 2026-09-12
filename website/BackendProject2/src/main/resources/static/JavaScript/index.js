const formHeader = document.querySelector(".main-page-login h2");
const submitButton = document.getElementById("login-button");
const switchLink = document.getElementById("switch-login");
const loginForm = document.getElementById("login-form");
const responseMessage = document.getElementById("responseMessage");

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




loginForm.addEventListener("submit", async function(event) {
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
        responseMessage.textContent = data.message;
        responseMessage.style.color = response.ok ? "green" : "red";

    } catch (error) {
        responseMessage.style.color = "red";
        responseMessage.textContent = "Could not connect to the server.";
    }
});