package com.example.service.position;

import com.google.maps.GeoApiContext;
import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DirectionService {

    @Value("${google.api.key}")
    private String apiKey;

    public String calculateDistance(double originLat, double originLng, double destLat, double destLng) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            DirectionsResult result = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)  // Hoặc bạn có thể chọn mode khác như WALKING, BICYCLING
                    .origin(new com.google.maps.model.LatLng(originLat, originLng))
                    .destination(new com.google.maps.model.LatLng(destLat, destLng))
                    .await();

            if (result.routes != null && result.routes.length > 0) {
                // Lấy tổng quãng đường từ kết quả
                long distanceInMeters = result.routes[0].legs[0].distance.inMeters;
                return "Distance: " + distanceInMeters + " meters";
            }
            return "No route found";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            context.shutdown();
        }
    }
    private static final String DISTANCE_MATRIX_API_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";

    public String calculateDistance2(double originLat, double originLng, double destLat, double destLng) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(DISTANCE_MATRIX_API_URL)
                .queryParam("origins", originLat + "," + originLng)
                .queryParam("destinations", destLat + "," + destLng)
                .queryParam("key", apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        return response; // Trả về chuỗi JSON chứa kết quả
    }
}

