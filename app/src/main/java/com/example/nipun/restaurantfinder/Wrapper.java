package com.example.nipun.restaurantfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nipun on 3/21/16.
 */
public class Wrapper implements Serializable {


    ArrayList<Restaurant> rest_list;
    public ArrayList<Restaurant> getRest_list() {
        return rest_list;
    }



    public void setRest_list(ArrayList<Restaurant> rest_list) {
        this.rest_list = rest_list;
    }




}
