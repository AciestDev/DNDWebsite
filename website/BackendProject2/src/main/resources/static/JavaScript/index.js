const inputUsername = document.getElementById("username");
const inputPassword = document.getElementById("password");
const loginForm = document.getElementById("login-form");
let loginButton = document.getElementById("signUp");
let switcher = document.getElementById("switch-login");

function switchFunction() {
    let currLoginButton = loginButton.nodeValue();
    let currSwitch = switcher.nodeValue();
    
    if (currLoginButton === "Sign up!" && currSwitch === "already have an account?") {
        currLoginButton = currLoginButton.nodeValue("login");
        currSwitch = currLoginButton.nodeValue("need an account? Register");
    }
    
}

switcher.addEventListener("click", switchFunction);