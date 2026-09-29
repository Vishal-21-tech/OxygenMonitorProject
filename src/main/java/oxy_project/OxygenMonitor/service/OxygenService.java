package oxy_project.OxygenMonitor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OxygenService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openweather.api.key:}")
    private String apiKey;

    public OxygenService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getOxygenLevel(String city) {
        Map<String, Object> result = new LinkedHashMap<>();
        String cleanedCity = city == null ? "" : city.trim();

        if (cleanedCity.isEmpty()) {
            return error(HttpStatus.BAD_REQUEST.value(), "City name is required");
        }

        if (apiKey == null || apiKey.isBlank() || apiKey.equals("YOUR_OPENWEATHER_API_KEY")) {
            return error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "OpenWeather API key is not configured. Set OPENWEATHER_API_KEY before starting the application.");
        }

        try {
            String geoUrl = UriComponentsBuilder
                    .fromHttpUrl("https://api.openweathermap.org/geo/1.0/direct")
                    .queryParam("q", cleanedCity)
                    .queryParam("limit", 1)
                    .queryParam("appid", apiKey)
                    .toUriString();

            String geoResponse = restTemplate.getForObject(geoUrl, String.class);
            JsonNode locations = objectMapper.readTree(geoResponse == null ? "[]" : geoResponse);

            if (!locations.isArray() || locations.isEmpty()) {
                return error(HttpStatus.NOT_FOUND.value(), "City not found: " + cleanedCity);
            }

            JsonNode location = locations.get(0);
            double lat = location.path("lat").asDouble();
            double lon = location.path("lon").asDouble();

            String airQualityUrl = UriComponentsBuilder
                    .fromHttpUrl("https://api.openweathermap.org/data/2.5/air_pollution")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid", apiKey)
                    .toUriString();

            String airResponse = restTemplate.getForObject(airQualityUrl, String.class);
            JsonNode airData = objectMapper.readTree(airResponse == null ? "{}" : airResponse);
            JsonNode list = airData.path("list");

            if (!list.isArray() || list.isEmpty()) {
                return error(HttpStatus.BAD_GATEWAY.value(), "No air-quality data returned for " + cleanedCity);
            }

            JsonNode components = list.get(0).path("components");
            if (!components.has("o3")) {
                return error(HttpStatus.BAD_GATEWAY.value(), "Ozone data is unavailable for " + cleanedCity);
            }

            double ozone = components.get("o3").asDouble();

            result.put("success", true);
            result.put("city", cleanedCity);
            result.put("latitude", lat);
            result.put("longitude", lon);
            result.put("ozone", ozone);
            result.put("unit", "μg/m³");
            result.put("description", "Ozone (O₃) concentration. This is not atmospheric oxygen (O₂) percentage.");
            return result;

        } catch (RestClientException ex) {
            return error(HttpStatus.BAD_GATEWAY.value(), "Unable to reach OpenWeather right now");
        } catch (Exception ex) {
            return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to process air-quality data");
        }
    }

    private Map<String, Object> error(int status, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", false);
        result.put("status", status);
        result.put("error", message);
        return result;
    }
}
