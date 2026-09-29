async function fetchOxygenLevel() {
    const cityInput = document.getElementById("city");
    const result = document.getElementById("result");
    const button = document.getElementById("checkButton");
    const city = cityInput.value.trim();

    if (!city) {
        result.className = "error";
        result.textContent = "Please enter a city name.";
        return;
    }

    button.disabled = true;
    result.className = "";
    result.textContent = "Fetching data...";

    try {
        const response = await fetch(`/api/oxygen/${encodeURIComponent(city)}`);
        const data = await response.json();

        if (!response.ok || !data.success) {
            throw new Error(data.error || "Unable to fetch data.");
        }

        result.className = "success";
        result.innerHTML = `
            <strong>${escapeHtml(data.city)}</strong><br>
            Ozone (O₃): <strong>${Number(data.ozone).toFixed(2)} ${escapeHtml(data.unit)}</strong><br>
            Coordinates: ${Number(data.latitude).toFixed(4)}, ${Number(data.longitude).toFixed(4)}
        `;
    } catch (error) {
        result.className = "error";
        result.textContent = error.message || "Error fetching data. Check the backend.";
        console.error(error);
    } finally {
        button.disabled = false;
    }
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

document.getElementById("city").addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        fetchOxygenLevel();
    }
});
