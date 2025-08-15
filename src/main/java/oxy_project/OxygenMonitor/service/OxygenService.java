package oxy_project.OxygenMonitor.service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OxygenService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct?q={city}&limit=1&appid=";
    private final String AIR_QUALITY_URL = "https://api.openweathermap.org/data/2.5/air_pollution?lat={lat}&lon={lon}&appid=";

    public Map<String, Object> getOxygenLevel(String city) {
        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Get City Coordinates
        String geoUrl = GEO_URL + apiKey;
        String geoResponse = restTemplate.getForObject(geoUrl.replace("{city}", city), String.class);

        JSONArray geoArray = new JSONArray(geoResponse);
        if (geoArray.isEmpty()) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("error", "City not found");
            return errorResult;
        }

        JSONObject location = geoArray.getJSONObject(0);
        double lat = location.getDouble("lat");
        double lon = location.getDouble("lon");

        // Step 2: Fetch Air Pollution Data
        String airQualityUrl = AIR_QUALITY_URL + apiKey;
        String airResponse = restTemplate.getForObject(airQualityUrl.replace("{lat}", String.valueOf(lat)).replace("{lon}", String.valueOf(lon)), String.class);

        JSONObject airData = new JSONObject(airResponse);
        double oxygenLevel = airData.getJSONArray("list").getJSONObject(0).getJSONObject("components").getDouble("o3"); // Ozone (O₃)

        // Return Response
        Map<String, Object> result = new HashMap<>();
        result.put("city", city);
        result.put("oxygen", oxygenLevel);
        return result;
    }
}
