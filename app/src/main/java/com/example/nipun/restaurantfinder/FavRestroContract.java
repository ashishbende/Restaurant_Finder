package com.example.nipun.restaurantfinder;

import android.provider.BaseColumns;

/**
 * Created by ashish on 3/25/16.
 */
public class FavRestroContract {

    public FavRestroContract(){}

    public static abstract class RestaurantEntry implements BaseColumns{
        public static final String TABLE_NAME="favorite_restaurants";
        public static final String COLUMN_NAME_RESTAURANT_NAME = "businessName";
        public static final String COLUMN_NAME_RESTAURANT_IMAGE = "imageUrl";
        public static final String COLUMN_NAME_RESTAURANT_RATING_URL = "ratingUrl";
        public static final String COLUMN_NAME_RESTAURANT_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_NAME_RESTAURANT_REVIEWS_COUNT = "reviewCount";
        public static final String COLUMN_NAME_RESTAURANT_ADDRESS = "displayAddress";
        public static final String COLUMN_NAME_RESTAURANT_LATITUDE = "latitude";
        public static final String COLUMN_NAME_RESTAURANT_LONGITUDE = "longitude";

    }
}
