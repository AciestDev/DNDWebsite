"use strict";

const form = document.querySelector("#character-creation-form");

document.addEventListener("DOMContentLoaded", async () => {

    const urlParams = new URLSearchParams(window.location.search);
    const characterId = urlParams.get("id");

    if (characterId) {
        const response = await fetch(`/api/characters/${characterId}`);
        const character = await response.json();

        form.elements["name"].value = character.name;
        // Populate additional fields later
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(form);
        const payload = Object.fromEntries(formData.entries());

        const isUpdate = Boolean(characterId);
        const endpoint = isUpdate ? `/api/characters/${characterId}` : "/api/characters";
        const httpMethod = isUpdate ? "PUT" : "POST";

        try {
            const response = await fetch(endpoint, {
                method: httpMethod,
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(payload)
            });

            const savedStatus = document.querySelector("#saved-status");
            if (response.ok) {
                savedStatus.textContent = response.statusText;
            } else {
                savedStatus.textContent = response.statusText;
                console.error("Failed to save character");
            }
        } catch (error) {
            console.error("Network error:", error);
        }
    });
});