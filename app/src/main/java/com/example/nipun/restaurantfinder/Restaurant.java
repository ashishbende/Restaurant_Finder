package com.example.nipun.restaurantfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nipun on 3/18/16.
 */



public class Restaurant implements Serializable {
    private String businessName, imageUrl,ratingUrl;
    //private ArrayList<String> displayAddress;
    private String displayAddress;

    public Restaurant(){

    }

    public Restaurant(String businessName, String imageUrl, String ratingUrl, String displayAddress) {
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

    public String getdisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public String getRating() {
        return ratingUrl;
    }

    public void setRating(String ratingUrl) {
        this.ratingUrl = ratingUrl;
    }


}

