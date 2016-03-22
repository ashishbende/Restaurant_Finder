package com.example.nipun.restaurantfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        int position;
        Wrapper wrapper = new Wrapper();
        wrapper = (Wrapper) getIntent().getSerializableExtra("list");
        position = getIntent().getIntExtra("position" , 0);
       String pos = String.valueOf(position);
        ArrayList<Restaurant> myList = new ArrayList<Restaurant>();
        myList = wrapper.getRest_list();
        Restaurant rest = new Restaurant();
        rest = myList.get(1);
        Log.i("Restname" , rest.getBusinessName());
        Log.i("PPOS", pos);



    }
}
