package com.aedinger.employeeservice.services;

import com.aedinger.employeeservice.daos.LocationRessource;
import com.aedinger.employeeservice.daos.LongitudeLatitude;
import com.aedinger.employeeservice.exceptions.LocationDataServiceException;
import com.aedinger.employeeservice.exceptions.LocationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocationIQDataService {
    private static final String LOCATION_IQ_URL = "https://us1.locationiq.com/v1/search.php?key={apiKey}&q={searchString}&format=json";
    private static final String LOCATION_IQ_URL_LONG_LAT = "https://us1.locationiq.com/v1/reverse.php?key={apiKey}&lat={LATITUDE}&lon={LONGITUDE}&format=json";
    private static final String API_KEY = "8e177faf1c1ddf";

    public LongitudeLatitude getLongitudeLatitudeByAddress(String address) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> vars = new HashMap<>();
        vars.put("apiKey", API_KEY);
        vars.put("searchString", address);
        try{
            ResponseEntity<LocationRessource[]> response =
                    restTemplate.getForEntity(LOCATION_IQ_URL, LocationRessource[].class, vars);
            LongitudeLatitude result = new LongitudeLatitude();
            LocationRessource[] locations = response.getBody();
            result.setLongitude(locations[0].getLon());
            result.setLatitude(locations[0].getLat());
            return result;
        } catch (RestClientResponseException e) {
            if(e.getRawStatusCode() == 400) {
                throw new LocationNotFoundException("The location was not found");
            } else {
                // throw new location exception
            }
        }
        return null;
    }
    public String getAddress(String longitude, String latitude) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("apiKey", API_KEY);
        vars.put("LONGITUDE", longitude);
        vars.put("LATITUDE", latitude);
        try {
            ResponseEntity<LocationRessource> response =
                    restTemplate.getForEntity(LOCATION_IQ_URL_LONG_LAT, LocationRessource.class, vars);
            String result = "";
            LocationRessource location = response.getBody();
            result = location.getDisplay_name();
            return result;
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == 400) {
                throw new LocationNotFoundException("this location was not found!");
            } else {
                throw new LocationDataServiceException("getAddress: Bad request of location");
            }
        }
    }
}


