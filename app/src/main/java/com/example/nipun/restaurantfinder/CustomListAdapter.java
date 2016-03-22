package com.example.nipun.restaurantfinder;

/**
 * Created by Nipun on 3/18/16.
 */
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
        businessname.setText(r.getBusinessName());
        String displayAddress = r.getdisplayAddress();
        address.setText(displayAddress);

        image = (ImageView) convertView.findViewById(R.id.imageurl);
        ratingImage = (ImageView) convertView.findViewById(R.id.rating);




        Picasso.with(activity)
                .load(r.getImageUrl())
                .into(image);

        Picasso.with(activity)
                .load(r.getRating())
                .into(ratingImage);

      /*  MyAsyncTaskForImage myAsyncTaskForImage = new MyAsyncTaskForImage();
        myAsyncTaskForImage.execute(r.getImageUrl());

        MyAsyncTaskForRating myAsyncTaskForRating = new MyAsyncTaskForRating();
        myAsyncTaskForRating.execute(r.getRating());*/

        // thumbnail image
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        //title.setText(m.getTitle());

        // rating
        //rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
		/*String genreStr = "";
		for (String str : m.getGenre()) {
			genreStr += str + ", ";
		}*/
        //genreStr = genreStr.length() > 0 ? genreStr.substring(0,
        //		genreStr.length() - 2) : genreStr;
        //genre.setText(genreStr);

        // release year
        //year.setText(String.valueOf(m.getYear()));

        return convertView;
    }


    public void clear(){

        restaurantsItems.clear();







    }
    private class MyAsyncTaskForImage extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap;
            for(String str:params){
                try {
                    URL imageUrl = new URL(str);
                    HttpURLConnection connectionImage = (HttpURLConnection) imageUrl.openConnection();
                    connectionImage.setDoInput(true);
                    connectionImage.connect();
                    InputStream inputImage = connectionImage.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputImage);
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Bitmap b){
            image.setImageBitmap(b);
        }

    }

    private class MyAsyncTaskForRating extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap;

            for(String str:params){
                try {
                    URL ratingUrl = new URL(str);
                    HttpURLConnection connectionRating = (HttpURLConnection) ratingUrl.openConnection();
                    connectionRating.setDoInput(true);
                    connectionRating.connect();
                    InputStream inputRating = connectionRating.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputRating);
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Bitmap b){
            ratingImage.setImageBitmap(b);
        }

    }

	/*private class MyAsyncTask extends AsyncTask<String,Void,Bitmap[]> {
		private LayoutInflater inflater;
		private View convertView;
		@Override
		protected Bitmap[] doInBackground(String... params) {
			Bitmap[] bitmaps = new Bitmap[params.length];

			//for(String str:params){

			try {

				Log.i("Para",params[0]);
				Log.i("Para",params[1]);
				URL imageUrl = new URL(params[0]);
				URL ratingUrl = new URL(params[1]);

				HttpURLConnection connectionImage = (HttpURLConnection) imageUrl.openConnection();
				HttpURLConnection connectionRating = (HttpURLConnection) ratingUrl.openConnection();

				connectionImage.setDoInput(true);
				connectionRating.setDoInput(true);

				connectionImage.connect();
				connectionRating.connect();

				InputStream inputImage = connectionImage.getInputStream();
				InputStream inputRating = connectionRating.getInputStream();
				//InputStream input = new java.net.URL(str).openStream();

				bitmaps[0] = BitmapFactory.decodeStream(inputImage);
				bitmaps[1] = BitmapFactory.decodeStream(inputRating);

				//arrayListImages.add(b);
				//arrayListImages.add(b1);

				return bitmaps;
			} catch (Exception e) {
				e.printStackTrace();
			}

			//}
			return null;
		}

		protected void onPostExecute(Bitmap[] b){
			if(b[0] != null)
				image.setImageBitmap(b[0]);

			if(b[1] != null)
				ratingImage.setImageBitmap(b[1]);

		}

	}*/
}
