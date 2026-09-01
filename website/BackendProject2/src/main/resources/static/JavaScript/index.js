let switcher = document.getElementById("switch-login").value;
let loginButton = document.getElementById("login-button").value;

function switchFunction() {

    if (switcher === "already have an account? Login") {
        currLoginButton = currLoginButton.nodeValue("login");
        currSwitch = currLoginButton.nodeValue("need an account? Register");
    } else {
        currLoginButton = currLoginButton.nodeValue("login");
        currSwitch = currLoginButton.nodeValue("already have an account? Login");
    }

}

switcher.addEventListener("click", switchFunction);




document.getElementById("login-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    const usernameInput = document.getElementById("username").value;
    const passwordInput = document.getElementById("password").value;
    const messageDiv = document.getElementById("responseMessage");

    const formData = new URLSearchParms();
    formData.append("username", usernameInput.value);
    formData.append("password", passwordInput.value);

    if (switcher === "need an account? Register") {
        try {
            const response = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: formData
            });

            const data = await response.json();

            if (response.ok) {
                messageDiv.style.color = "green";
                messageDiv.textContent = data.message;
            } else {
                messageDiv.style.color = "red";
                messageDiv.textContent = data.message;
            }
        } catch (error) {
            messageDiv.style.color = "red";
            messageDiv.textContent = "Could not connect to the server.";
        }
    } else {
        try {
            const response = await fetch("/api/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: formData
            });

            const data = await response.json();

            if (response.ok) {
                messageDiv.style.color = "green";
                messageDiv.textContent = data.message;
            } else {
                messageDiv.style.color = "red";
                messageDiv.textContent = data.message;
            }
        } catch (error) {
            messageDiv.style.color = "red";
            messageDiv.textContent = "Could not connect to the server.";
        }
    }

});