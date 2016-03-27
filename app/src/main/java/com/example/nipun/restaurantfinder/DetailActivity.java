package com.example.nipun.restaurantfinder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
* Created by Ashish on 03/24
 */

public class DetailActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private List<Restaurant> restaurantList;
    public static ArrayList<String> latList=new ArrayList<>(), lonList=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        Wrapper restaurants = (Wrapper)i.getSerializableExtra("restaurants");
        int position = i.getIntExtra("CurrentElementPosition",0);

        try{
            latList = i.getStringArrayListExtra("latitude");
            lonList = i.getStringArrayListExtra("longitude");

        }catch(Exception e){

        }
        restaurantList=restaurants.getRest_list();



//        Toast.makeText(this,businessName,Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_actionbar_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),restaurants,position);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
        //finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private Restaurant res;
        private int sectionNumber;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,Restaurant restaurant) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable("currentRestaurant",restaurant);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            res = getArguments()!=null?(Restaurant)getArguments().getSerializable("currentRestaurant"):null;
            sectionNumber = getArguments()!=null?getArguments().getInt(ARG_SECTION_NUMBER):0;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");

            final Button favButton = (Button) rootView.findViewById(R.id.heart);
            favButton.setTypeface(font);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favButton.getText().equals(getString(R.string.heart_empty))) {
                        favButton.setText(getString(R.string.heart_fill));
                        FavoriteDatabaseHelper favDbHelp = new FavoriteDatabaseHelper(getContext());

                        try {
                            if(res.getLocation()!=null) {
                                if(favDbHelp.insertRow(res.getBusinessName(), res.getImageUrl(),
                                        res.getRating(), res.getPhoneNumber(), res.getReviewCount(),
                                        res.getdisplayAddress().toString(),
                                        res.getLocation().coordinate().latitude(),
                                        res.getLocation().coordinate().latitude()
                                )){
                                    Toast.makeText(getActivity(), res.getBusinessName() + " is added to favList", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "Already deleted. Use Search to Insert Again", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            Toast.makeText(getActivity(), "Already deleted, Use Search to Insert Again", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        favButton.setText(getString(R.string.heart_empty));
                        FavoriteDatabaseHelper favDbHelp = new FavoriteDatabaseHelper(getContext());
                        if(favDbHelp.deleteRow(res.getBusinessName())){
                            Toast.makeText(getActivity(), res.getBusinessName() + " is removed to favList", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
            TextView businessName = (TextView) rootView.findViewById(R.id.businessNameText);
            businessName.setText("Business Name: " + res.getBusinessName());
            TextView reviewCount = (TextView) rootView.findViewById(R.id.reviewCountTextView);
            reviewCount.setText("Reviews: " + res.getReviewCount());
            TextView phoneNumber= (TextView) rootView.findViewById(R.id.phoneNumberTextView);
            phoneNumber.setText("Phone Number: " + res.getPhoneNumber());

            TextView addressView= (TextView) rootView.findViewById(R.id.addressTextView);
            addressView.setText("Address: "+res.getdisplayAddress().toString());
            ImageView businessImage = (ImageView) rootView.findViewById(R.id.businessImageView);
            Picasso.with(getActivity()).load(res.getImageUrl()).resize(400,400).into(businessImage);

            ImageView ratingsImage = (ImageView) rootView.findViewById(R.id.ratingsImageView);
            Picasso.with(getActivity()).load(res.getRating()).resize(250,50).into(ratingsImage);

            ImageView snippetImage = (ImageView) rootView.findViewById(R.id.snippetImageview);
            Picasso.with(getActivity()).load(res.getSnippet()).resize(400,400).into(snippetImage);

            ImageView googleStaticImage = (ImageView) rootView.findViewById(R.id.googleStaticMapImage);

            String googleApiLatitude,googleApiLongitude;
            try{
                 googleApiLatitude = res.getLocation().coordinate().latitude().toString();
                 googleApiLongitude = res.getLocation().coordinate().longitude().toString();
                favButton.setVisibility(View.VISIBLE);

            }catch(NullPointerException e){

                googleApiLatitude = DetailActivity.latList.get(sectionNumber-1);
                googleApiLongitude = DetailActivity.lonList.get(sectionNumber-1);
                favButton.setText(getString(R.string.heart_fill));
            }

            String url = "http://maps.google.com/maps/api/staticmap?center="
                          + googleApiLatitude + ","
                          + googleApiLongitude + "&zoom=15&size=200x200&sensor=false";

            Picasso.with(getActivity())
                    .load(url).resize(400,400)
                    .into(googleStaticImage);

            //googleStaticImage.setImageURI(Uri.parse(url));


            try{
                FavoriteDatabaseHelper favDbHelper = new FavoriteDatabaseHelper(getActivity());
                String[] projection ={FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE,
                        FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LATITUDE

                };

                String selection = FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME+"=?";
                String[] selectionArgs = {res.getBusinessName()};
                Cursor cursor = favDbHelper.fetchRows(projection,selection,selectionArgs,null);

                if(cursor.moveToNext()){
                    favButton.setText(R.string.heart_fill);
                }else{
                    favButton.setText(R.string.heart_empty);
                }

            }catch(Exception e){
                Log.e("Cursor Error", e.getMessage());
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Wrapper restaurant;
        private List<Restaurant> restaurantList;
        private  int position;
        public SectionsPagerAdapter(FragmentManager fm,Wrapper restaurant, int position) {
            super(fm);
            this.restaurantList = restaurant.getRest_list();
            this.position = position;

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, restaurantList.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            int count = restaurantList.size();
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
