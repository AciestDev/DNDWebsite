"use strict"

const mainContainer = document.querySelector("#myCharacters");

document.addEventListener("DOMContentLoaded", async () => {
    const userResponse = await fetch("/api/me");

    if (!userResponse.ok) {
        window.location.href = "/login.html";
        return;
    }

    const userData = await userResponse.json();

    mainContainer.querySelector("h1").textContent = `welcome back ${userData.name}!`;
    loadUserCharacters();
});

async function loadUserCharacters() {
    const response = await fetch("/api/characters/my-characters");
    const characters = await response.json();

    const cardsContainer = mainContainer.querySelector("div");
    cardsContainer.innerHTML = "";

    if (characters.length === 0) {
        cardsContainer.innerHTML = "<p>You don't have any characters yet. <a>Create one!</a></p>";
    }

    characters.forEach(character => {
        const characterCard = document.createElement("div");
        characterCard.className = "character-card";

        const characterName = document.createElement("h2");
        characterName.textContent = character.name;

        const characterInfo = document.createElement("p");
        characterInfo.textContent = `Level ${character.level} ${character.characterClass}`;

        characterCard.appendChild(characterName);
        characterCard.appendChild(characterInfo);

        cardsContainer.appendChild(characterCard);
    });
}

document.getElementById("logoutBtn").addEventListener("click", async () => {
    await fetch("/api/logout", { method: "POST" });
    window.location.href = "/login.html";
});