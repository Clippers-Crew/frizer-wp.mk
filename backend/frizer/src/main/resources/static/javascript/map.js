document.addEventListener("DOMContentLoaded", function () {
    if (typeof L === 'undefined') {
        console.error('Leaflet library not loaded');
        return;
    }

    // Initializes map
    const map = L.map("map");

    // Sets initial coordinates and zoom level, Center of Macedonia
    map.setView([41.6086, 21.7453], 9);

    // Sets map data source and associates with map
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    function createPopup(id, name) {
        const popupContent = `
            <b>${name}</b></br>
            <a href="/salons/${id}">
                <button>Details</button>
            </a>
        `;
        return L.popup().setContent(popupContent);
    }

    function createUserPopup(message) {
        const popupContent = `
            <b>${message}</b>
        `;
        return L.popup().setContent(popupContent);
    }

    let marker_helper, circle_helper;

    function drawLocations(salons) {
        console.log("Drawing salons:", salons); // Print salons to console
        const accuracy = 200;

        salons.forEach(salon => {
            let [id, lat, lng, name] = salon.split("|");

            // Skip salons with null latitude or longitude
            if (lat === 'null' || lng === 'null') {
                console.warn(`Skipping salon with null coordinates: ${name}`);
                return;
            }

            id = parseInt(id);
            lat = parseFloat(lat);
            lng = parseFloat(lng);

            marker_helper = L.marker([lat, lng]).addTo(map);
            circle_helper = L.circle([lat, lng], { radius: accuracy }).addTo(map);

            marker_helper.bindPopup(createPopup(id, name));
            circle_helper.bindPopup(createPopup(id, name));
        });
    }

    function addMarkerForUserLocation() {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    const accuracy = 500;
                    const { latitude, longitude } = position.coords;

                    let greenIcon = new L.Icon({
                        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                        iconSize: [25, 41],
                        iconAnchor: [12, 41],
                        popupAnchor: [1, -34],
                        shadowSize: [41, 41]
                    });

                    const userMarker = L.marker([latitude, longitude], { icon: greenIcon }).addTo(map);
                    const circle_helper = L.circle([latitude, longitude], { radius: accuracy, color: 'green' }).addTo(map);

                    userMarker.bindPopup(createUserPopup("Your location")).openPopup();
                    circle_helper.bindPopup(createUserPopup("Your radius"));
                },
                function (error) {
                    console.error("Error getting user location:", error);
                },
                { timeout: 10000 } // Set a timeout of 10 seconds
            );
        }
    }

    try {
        var parsedSalons = JSON.parse(salons);
        console.log("Parsed salons:", parsedSalons); // Print parsed salons to console
        drawLocations(parsedSalons);
    } catch (e) {
        console.error("Failed to parse salons JSON: ", e);
    }

    addMarkerForUserLocation();
});
