package com.example.nipun.restaurantfinder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Coordinate;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout mContentLayout;

    private ListView listView;
    private CustomListAdapter adapter;
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private CoordinateOptions coordinate;
    private static final int PLACE_PICKER_REQUEST = 1;
   /* private TextView smName;
    private TextView smAddress;
    private TextView smAttributions;*/
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    public Place place;
    String toastMsg;
    String sortingCriteria="0";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_actionbar_icon);



        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent detailIntent = new Intent(SearchActivity.this, DetailActivity.class);
                Log.d("View Value: ", view.toString());

                Wrapper arrayWrapper = new Wrapper(restaurantList);
                detailIntent.putExtra("restaurants",arrayWrapper);
                detailIntent.putExtra("CurrentElementPosition",position);
                detailIntent.putExtra("CurrentObject",restaurantList.get(position));

                //        r = new Restaurant(r.getBusinessName(),r.getImageUrl(),r.getRating(),r.getPhoneNumber(),r.getReviewCount(),r.getdisplayAddress())
                startActivity(detailIntent);
            }

        };


        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(itemClickListener);
        // listView.setAdapter(null);
        adapter = new CustomListAdapter(this, restaurantList);
        listView.setAdapter(adapter);

        handleIntent(getIntent());


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.relevance:
                if (checked)
                    // Sort by Relavance
                    sortingCriteria = "0";
                handleIntent(getIntent());

                break;
            case R.id.distance:
                if (checked)
                    // Sort By Distance
                    sortingCriteria = "1";
                handleIntent(getIntent());
                break;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {

        setIntent(intent);
        handleIntent(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //SearchView searchView =
        //      (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(),
                        SearchActivity.class)));


        return true;
    }


    private void handleIntent(Intent intent) {
        Log.i("SearchActivity: ", "Inside handleIntent Method");

        String query="";
        double latitude=0,longitude=0;
        if(intent.getAction()!=null) {

            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                query = intent.getStringExtra(SearchManager.QUERY);
                //location = intent.getStringExtra("Place");
                Log.i("Query", query);

            } else if (intent.getAction().equals("DEFAULT_SEARCH")) {

                query = intent.getStringExtra("query");
                latitude = intent.getDoubleExtra("latitude", 0);
                longitude = intent.getDoubleExtra("longitude", 0);

            }
        }
            if (restaurantList != null)
                restaurantList.clear();
            if (adapter != null)
                adapter.notifyDataSetChanged();
            if(latitude==0 && longitude ==0) {
                //settting default location as San Jose
                latitude =37.7577;
                longitude =-122.4376;
            }

           // Log.i(" Handle intent ", "Location is " + place.getName());
        CoordinateOptions currentCoordinate = CoordinateOptions.builder()
                .latitude(latitude)
                .longitude(longitude).build();
        if(query!=null) {
            doMySearch(currentCoordinate, query);
        }else{
            Toast.makeText(this,"Please input your query",Toast.LENGTH_LONG).show();
        }

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.getlocation) {


            try {
                PlacePicker.IntentBuilder intentBuilder =
                        new PlacePicker.IntentBuilder();
                intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                Intent intent = intentBuilder.build(SearchActivity.this);
                startActivityForResult(intent, PLACE_PICKER_REQUEST);

            } catch (GooglePlayServicesRepairableException
                    | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }


            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            place = PlacePicker.getPlace(this, data);
            toastMsg = String.format("Place: %s", place.getName());
            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Search Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nipun.restaurantfinder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Search Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nipun.restaurantfinder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class MyAsyncTask extends AsyncTask<Call<SearchResponse>, Void, ArrayList<Business>> {
        Call<SearchResponse> call;

        @Override
        protected ArrayList<Business> doInBackground(Call<SearchResponse>... calls) {
            for (Call<SearchResponse> call : calls) {
                try {

                    SearchResponse searchResponse = call.execute().body();
/*
                    int totalNumberOfResult = searchResponse.total();
*/
                    ArrayList<Business> result = searchResponse.businesses();

                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Business> result) {


            if (result != null) {
                StringBuilder response1 = new StringBuilder();
                for (Business YelpObj : result) {

                    Restaurant restaurant = new Restaurant();
                    restaurant.setImageUrl(YelpObj.imageUrl());
                    restaurant.setBusinessName(YelpObj.name());
                    restaurant.setRating(YelpObj.ratingImgUrlLarge());
                    restaurant.setDisplayAddress(YelpObj.location().displayAddress());
                    restaurant.setPhoneNumber(YelpObj.phone());
                    restaurant.setReviewCount(YelpObj.reviewCount());
                    //ArrayList<String>
                    restaurant.setDisplayAddress(YelpObj.location().displayAddress());
                    restaurant.setImageUrl(YelpObj.imageUrl());
                    restaurant.setSnippet(YelpObj.snippetImageUrl());
                    restaurant.setLocation(YelpObj.location());
                    //Log.i("Yelp response :", response1.toString());
                    restaurantList.add(restaurant);
                }

                //Log.i("Response", response1.toString());

                adapter.notifyDataSetChanged();

            }
        }
    }


    private void doMySearch(CoordinateOptions coordinate, String query) {


        YelpAPI yelpAPI = getYelpAPI();
        try {
            requestYelpSearch(yelpAPI, query,coordinate);
        } catch (IOException e) {

            Log.i("Exception", "Message" + e.getMessage());
            e.printStackTrace();
        }


    }


    private YelpAPI getYelpAPI() {


        String consumerkey = getResources().getString(R.string.consumerKey);
        String consumerSecrete = getResources().getString(R.string.consumerSecret);
        String token = getResources().getString(R.string.token);
        String tokenSecret = getResources().getString(R.string.tokenSecret);

        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerkey, consumerSecrete, token, tokenSecret);
        // YelpAPIFactory apiFactory = new YelpAPIFactory(R.string.consumerKey,R.string.consumerSecret,R.string.token,R.string.tokenSecret);
        YelpAPI yelpAPI = apiFactory.createAPI();
        return yelpAPI;
    }


    private void requestYelpSearch(YelpAPI YelpAPI, String searchString,CoordinateOptions coordinate) throws IOException {
        Map<String, String> params = new HashMap<>();

        Log.i("Search string",searchString);
        Log.i("Sorting string",sortingCriteria);

        params.put("category_filter", "restaurants");
        params.put("term", searchString);
        params.put("sort", sortingCriteria);
        params.put("limit", "20");
        params.put("radius_filter", "16093");
        params.put("lang", "en");


        if(coordinate == null){
            coordinate = CoordinateOptions.builder().latitude(37.3413170).longitude(-121.8978294).build();
        }

        Call<SearchResponse> call = YelpAPI.search(coordinate, params);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(call);

    }


}






