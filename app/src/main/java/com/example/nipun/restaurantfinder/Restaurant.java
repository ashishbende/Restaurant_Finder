package com.example.nipun.restaurantfinder;

import java.util.ArrayList;

/**
 * Created by Nipun on 3/18/16.
 */



public class Restaurant {
    private String businessName, imageUrl,ratingUrl;
    private ArrayList<String> displayAddress;

    public Restaurant(){

    }

    public Restaurant(String businessName, String imageUrl, String ratingUrl, ArrayList<String> displayAddress) {
        this.businessName = businessName;
        this.imageUrl = imageUrl;
        this.displayAddress = displayAddress;
        this.ratingUrl = ratingUrl;

    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getdisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(ArrayList<String> displayAddress) {
        this.displayAddress = displayAddress;
    }

    public String getRating() {
        return ratingUrl;
    }

    public void setRating(String ratingUrl) {
        this.ratingUrl = ratingUrl;
    }


}

