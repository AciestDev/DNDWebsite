"use strict"

const mainDashboard = document.querySelector("#dashboard");
const userContainer = mainDashboard.querySelector("#myCharacters");

document.addEventListener("DOMContentLoaded", async () => {
    const userResponse = await fetch("/api/me");

    if (!userResponse.data) {
        window.location.href = "/Websites/register.html";
        return;
    }

    const userData = await userResponse.json();

    userContainer.querySelector("h1").textContent = `welcome ${userData.username}!`;
    await loadUserCharacters();
});

async function loadUserCharacters() {
    try {
        const cardsContainer = userContainer.querySelector(".loaded-characters-container");
        cardsContainer.clear();
        const response = await fetch("/api/characters/my-characters");


        if (!response.ok) {
            console.log("")
            console.error("Failed to load characters");
            const noCharactersFound = document.createElement("p");
            noCharactersFound.classList.add("noCharactersFound");
            noCharactersFound.textContent = "No characters were found. Please contact the doo doo " +
                "developer that clearly doesn't know what he's doing";
            return;
        }

        const characters = await response.json();
        const characterLimit = mainDashboard.querySelector(".max-character");

        if (characters.length === 0) {
            cardsContainer.innerHTML = "<p>You don't have any characters yet. <a href=\"/Websites/characterCreation.html\">Create one!</a></p>";
        } else if (characters.length === 7) {
            const newCharacterLink = mainDashboard.querySelector("a");
            newCharacterLink.addAttribute = "hidden";
        } else {
            characterLimit.textContent = characters.length + "/7";

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
    } catch (error) {
        console.error(error);
    }
}