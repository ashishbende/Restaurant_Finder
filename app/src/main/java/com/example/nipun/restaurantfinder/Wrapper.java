package com.example.nipun.restaurantfinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 3/22/16.
 */
public class Wrapper implements Serializable {

    private ArrayList<Restaurant> rest_list;

    public Wrapper(List<Restaurant> restaurants){
        this.rest_list = (ArrayList<Restaurant>)restaurants;
    }

    public ArrayList<Restaurant> getRest_list() {
        return rest_list;
    }



    public void setRest_list(ArrayList<Restaurant> rest_list) {
        this.rest_list = rest_list;
    }
}
