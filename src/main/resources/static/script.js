function fetchOxygenLevel() {
    const city = document.getElementById("city").value;
    if (!city) {
        alert("Please enter a city name!");
        return;
    }

    fetch(`http://localhost:8080/api/oxygen/${city}`)
        .then(response => response.json())
        .then(data => {
            if (data && data.oxygenLevel) {
                document.getElementById("result").innerText =
                    `Oxygen Level in ${city}: ${data.oxygenLevel}%`;
            } else {
                document.getElementById("result").innerText =
                    `No data available for ${city}`;
            }
        })
        .catch(error => {
            document.getElementById("result").innerText =
                "Error fetching data. Check backend!";
            console.error("Error:", error);
        });
}
