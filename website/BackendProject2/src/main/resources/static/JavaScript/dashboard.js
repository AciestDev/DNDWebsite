"use strict"

const mainContainer = document.querySelector("#myCharacters");

document.addEventListener("DOMContentLoaded", async () => {
    const userResponse = await fetch("/api/me");

    if (!userResponse.ok) {
        window.location.href = "Websites/register.html";
        return;
    }

    const userData = await userResponse.json();

    mainContainer.querySelector("h1").textContent = `welcome back ${userData.username}!`;
    loadUserCharacters();
});

async function loadUserCharacters() {
    const response = await fetch("/api/characters/my-characters");

    if (!response.ok) {
        console.error("Failed to load characters");
        const cardsContainer = mainContainer.querySelector("div");
        cardsContainer.textContent = response.message;
        return;
    }

    const characters = await response.json();
    const cardsContainer = mainContainer.querySelector("div");

    if (characters.length === 0) {
        cardsContainer.innerHTML = "<p>You don't have any characters yet. <a>Create one!</a></p>";
    }

    characters.forEach(character => {
        const characterCard = document.createElement("div");
        characterCard.className = "character-card";

        const characterName = document.createElement("h2");
        characterName.textContent = character.name;


        //const characterInfo = document.createElement("p");
        //characterInfo.textContent = `Level ${character.level} ${character.characterClass}`;

        const editLink = document.createElement("a");
        editLink.href = `characterCreation.html?id=${character.id}`;
        editLink.textContent = "Edit Character";
        editLink.className = "edit-link";

        characterCard.appendChild(characterName);
        //characterCard.appendChild(characterInfo);
        characterCard.appendChild(editLink);

        cardsContainer.appendChild(characterCard);
    });
}