package com.example.nipun.restaurantfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yelp.clientlib.entities.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ListView favListView;
    private CustomListAdapter adapter;
    private FavoriteDatabaseHelper favoriteDatabaseHelper;
    private List<Restaurant> favRestaurantList = new ArrayList<>();
    private ArrayList<String> latList =new ArrayList<>(),longList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFavorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        favListView = (ListView) findViewById(R.id.favList);

        try{
            favoriteDatabaseHelper = new FavoriteDatabaseHelper(getApplicationContext());
            String[] projection ={FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME,
                                  FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE,
                                  FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL,
                                  FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER,
                                  FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT,
                                  FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS,
                    FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE,
                    FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LATITUDE


            };
            Cursor cursor = favoriteDatabaseHelper.fetchRows(projection,null,null,null);
            while(cursor.moveToNext()){
                String businessName = cursor.getString(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME));
                String imageUrl = cursor.getString(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE));
                String ratingUrl = cursor.getString(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER));
                double reviewCount = cursor.getDouble(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT));
                String address = cursor.getString(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS));
                ArrayList<String> displayAddress  = new ArrayList<>(Arrays.asList(address.split(" ")));
                double latitude = cursor.getDouble(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE));
                latList.add(latitude+"");
                longList.add(longitude+"");
                Restaurant res = new Restaurant(businessName, imageUrl, ratingUrl, phoneNumber,reviewCount, displayAddress);
                favRestaurantList.add(res);
            }
        }catch(Exception e){
            Log.e("Cursor Error: ",e.toString());
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent detailIntent = new Intent(FavoriteActivity.this, DetailActivity.class);
                Log.d("View Value: ", view.toString());

                Wrapper arrayWrapper = new Wrapper(favRestaurantList);
                detailIntent.putExtra("restaurants",arrayWrapper);
                detailIntent.putExtra("CurrentElementPosition",position);
                detailIntent.putStringArrayListExtra("latitude",latList);
                detailIntent.putStringArrayListExtra("longitude", longList);
                detailIntent.putExtra("CurrentObject",favRestaurantList.get(position));

                startActivity(detailIntent);
            }

        };


        /*favListView = (ListView) findViewById(R.id.list);*/
        favListView.setOnItemClickListener(itemClickListener);

        adapter = new CustomListAdapter(this,favRestaurantList);
        favListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }




}
