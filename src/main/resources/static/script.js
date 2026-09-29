const cityInput = document.getElementById("cityInput");
const searchBtn = document.getElementById("searchBtn");

const welcomeState = document.getElementById("welcomeState");
const loadingState = document.getElementById("loadingState");
const resultState = document.getElementById("resultState");
const errorState = document.getElementById("errorState");

const cityName = document.getElementById("cityName");
const ozoneValue = document.getElementById("ozoneValue");
const latitude = document.getElementById("latitude");
const longitude = document.getElementById("longitude");

const errorMessage = document.getElementById("errorMessage");


// Show only one state
function showState(state) {

    welcomeState.classList.add("d-none");
    loadingState.classList.add("d-none");
    resultState.classList.add("d-none");
    errorState.classList.add("d-none");

    state.classList.remove("d-none");
}


// Get air quality
async function checkAirQuality() {

    const city = cityInput.value.trim();

    if (city === "") {

        showState(errorState);

        errorMessage.innerText =
            "Please enter a city name.";

        cityInput.focus();

        return;
    }


    showState(loadingState);

    searchBtn.disabled = true;

    searchBtn.innerHTML =
        '<span class="spinner-border spinner-border-sm me-2"></span>Checking...';


    try {

        const response =
            await fetch(`/api/oxygen/${encodeURIComponent(city)}`);


        if (!response.ok) {
            throw new Error("City not found");
        }


        const data = await response.json();


        if (data.error) {
            throw new Error(data.error);
        }


        cityName.innerText =
            data.city || city;


        ozoneValue.innerText =
            Number(data.oxygen).toFixed(1);


        latitude.innerText =
            data.latitude !== undefined
                ? Number(data.latitude).toFixed(4)
                : "--";


        longitude.innerText =
            data.longitude !== undefined
                ? Number(data.longitude).toFixed(4)
                : "--";


        showState(resultState);

    }

    catch (error) {

        console.error(error);

        errorMessage.innerText =
            error.message ||
            "Unable to retrieve air quality data.";

        showState(errorState);

    }

    finally {

        searchBtn.disabled = false;

        searchBtn.innerHTML =
            '<i class="bi bi-search me-1"></i> Check Air';

    }

}


// Search button
searchBtn.addEventListener("click", checkAirQuality);


// Press ENTER
cityInput.addEventListener("keydown", function (event) {

    if (event.key === "Enter") {
        checkAirQuality();
    }

});


// Quick city buttons
document.querySelectorAll(".city-btn").forEach(function (button) {

    button.addEventListener("click", function () {

        cityInput.value = button.innerText;

        checkAirQuality();

    });

});