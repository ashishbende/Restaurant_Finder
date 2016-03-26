package com.example.nipun.restaurantfinder;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        Wrapper restaurants = (Wrapper)i.getSerializableExtra("restaurants");
        int position = i.getIntExtra("CurrentElementPosition",0);
        restaurantList=restaurants.getRest_list();



//        Toast.makeText(this,businessName,Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.location_picker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),restaurants,position);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);


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
                        Toast.makeText(getActivity(), res.getBusinessName() + " is added to favList", Toast.LENGTH_LONG).show();
                    }else{
                        favButton.setText(getString(R.string.heart_empty));
                        Toast.makeText(getActivity(), res.getBusinessName() + " is removed to favList", Toast.LENGTH_LONG).show();

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
            Picasso.with(getActivity()).load(res.getImageUrl()).into(businessImage);

            ImageView ratingsImage = (ImageView) rootView.findViewById(R.id.ratingsImageView);
            Picasso.with(getActivity()).load(res.getRating()).into(ratingsImage);

            ImageView snippetImage = (ImageView) rootView.findViewById(R.id.snippetImageview);
            Picasso.with(getActivity()).load(res.getSnippet()).into(snippetImage);

            ImageView googleStaticImage = (ImageView) rootView.findViewById(R.id.googleStaticMapImage);
            String googleApiLatitude = res.getLocation().coordinate().latitude().toString();
            String googleApiLongitude = res.getLocation().coordinate().longitude().toString();
            String url = "http://maps.google.com/maps/api/staticmap?center="
                          + googleApiLatitude + ","
                          + googleApiLongitude + "&zoom=15&size=200x200&sensor=false";

            Picasso.with(getActivity())
                    .load(url)
                    .into(googleStaticImage);

            //googleStaticImage.setImageURI(Uri.parse(url));


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
