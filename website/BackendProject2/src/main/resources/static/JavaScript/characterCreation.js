"use strict";

const form = document.querySelector("#character-creation-form");

document.addEventListener("DOMContentLoaded", async () => {

    const urlParams = new URLSearchParams(window.location.search);
    const characterId = urlParams.get("id");

    if (characterId) {
        const response = await fetch(`/api/characters/${characterId}`);
        const character = await response.json();

        const deleteButton = document.querySelector("#delete-character-btn");
        deleteButton.removeAttribute("hidden");
        deleteButton.addEventListener("click", async () => {
            if (!confirm("Are you sure you want to delete this character?")) return;
            const deleteResponse = await fetch(`/api/characters/${characterId}`, {
                method: "DELETE",
            });
            if (deleteResponse.ok) {
                window.location.href = "../Websites/Dashboard.html";
            }
        })

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
                savedStatus.textContent = "successfully saved";
                console.log(response.statusText);
                if (!isUpdate) {
                    const userData = await response.json();
                    console.log(userData);
                    window.location.href = "../Websites/characterCreation.html?id=" + userData.id;
                }
            } else {
                const body = await response.text();
                console.error("Save failed:", response.status, body);
                savedStatus.textContent = `Failed (${response.status}): ${body}`;
            }
        } catch (error) {
            console.error("Network error:", error);
        }
    });
});