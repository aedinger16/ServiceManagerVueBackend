package com.aedinger.employeeservice.daos;

import lombok.Data;

import java.util.List;

@Data
public class LocationRessource {
    private String place_id;
    private String licence;
    private String osm_type;
    private String osm_id;
    private List<String> boundingbox;
    private String lat;
    private String lon;
    private String display_name;
    private String type;
    private double importance;
    private String icon;
}
