package com.example.nipun.restaurantfinder;

import com.yelp.clientlib.entities.Location;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Restaurant implements Serializable {
    private String businessName;
    private String imageUrl;
    private String ratingUrl;
    private String phoneNumber;

    private String snippet;
    private ArrayList<String> displayAddress;
    private double reviewCount;


    private Location location;


    public Restaurant(){

    }

    public Restaurant(String businessName, String imageUrl, String ratingUrl,String phoneNumber, double reviewCount, ArrayList<String> displayAddress) {
        this.businessName = businessName;
        this.imageUrl = imageUrl;
        this.displayAddress = displayAddress;
        this.ratingUrl = ratingUrl;
        this.phoneNumber = phoneNumber;
        this.reviewCount = reviewCount;
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


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(double reviewCount) {
        this.reviewCount = reviewCount;
    }
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

