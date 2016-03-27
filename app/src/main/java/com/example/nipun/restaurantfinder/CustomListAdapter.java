package com.example.nipun.restaurantfinder;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Restaurant> restaurantsItems;
    private ImageView image;
    private ImageView ratingImage;


    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Restaurant> restaurantsItems) {
        this.activity = activity;
        this.restaurantsItems = restaurantsItems;
    }

    @Override
    public int getCount() {
        return restaurantsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return restaurantsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        //convertView.findViewById(R.id.thumbnail);
		/*TextView title = (TextView) convertView.findViewById(R.id.);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);*/
        TextView businessname = (TextView) convertView.findViewById(R.id.businessname);
        TextView address = (TextView) convertView.findViewById(R.id.address);

        // getting movie data for the row
        //Movie m = movieItems.get(position);

        Restaurant r = restaurantsItems.get(position);
        businessname.setText(position+1+"."+r.getBusinessName());
        String displayAddress = r.getdisplayAddress().toString();
        address.setText(displayAddress.substring(1, displayAddress.length() - 1));

        image = (ImageView) convertView.findViewById(R.id.imageurl);
        ratingImage = (ImageView) convertView.findViewById(R.id.rating);




        Picasso.with(activity)
                .load(r.getImageUrl())
                .into(image);

        Picasso.with(activity)
                .load(r.getRating())
                .into(ratingImage);



        return convertView;
    }


    public void clear(){

        restaurantsItems.clear();
    }


}
