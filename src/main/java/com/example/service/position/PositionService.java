package com.example.service.position;

import com.example.entity.Account;
import com.example.entity.DriverDetail;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.account.OurUserDetailsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    private static final double EARTH_RADIUS_KM = 6371.01;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public double[] geocode(String address) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" +
                    java.net.URLEncoder.encode(address, "UTF-8");

            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            if (jsonArray.isEmpty()) {
                throw new Exception("No results found for the address: " + address);
            }

            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            double lat = jsonObject.get("lat").getAsDouble();
            double lon = jsonObject.get("lon").getAsDouble();
            return new double[]{lat, lon};
        }
    }


    public double calculateDistanceByName(String address1, String address2) {
        double distance = 0.0;
        try {
            double[] location1 = geocode(address1);
            double[] location2 = geocode(address2);
            distance = haversine(location1[0], location1[1], location2[0], location2[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance;
    }
    public boolean checkHadPosition(Account account) {
        return (account.getLatitude()!= 0 && account.getLongitude()!= 0);
    }
    public Account calculateNearestDriver(Account account) {
        List<Account> driverDetailList = ourUserDetailsService.getAllAccountByRoleId(3);
        Account driverNearest = new Account();
        double minDistance = 0;
        if ("CUSTOMER".equals(account.getRole().getRoleName())) {
            for (Account driver : driverDetailList) {
                double distanceCurrent = haversine(account.getLatitude(),account.getLongitude(),driver.getLatitude(),driver.getLongitude());
                if((minDistance > distanceCurrent) || (minDistance == 0)) {
                    if (checkHadPosition(driver)) {
                        minDistance = distanceCurrent;
                        driverNearest = driver;
                    }
                }
            }
        }
        return driverNearest;
    }
}

