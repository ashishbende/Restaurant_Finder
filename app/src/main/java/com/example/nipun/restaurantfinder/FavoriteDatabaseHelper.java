package com.example.nipun.restaurantfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashish on 3/25/16.
 */
public class FavoriteDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String COMMA_SEPARATOR=",";
    private static final String TEXT_TYPE=" TEXT";
    private static final String DB_NAME="favoriteRestaurant.db";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +FavRestroContract.RestaurantEntry.TABLE_NAME+" ("+
            FavRestroContract.RestaurantEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME+ TEXT_TYPE+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE+ TEXT_TYPE+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL + TEXT_TYPE+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER + TEXT_TYPE+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT+ " INTEGER"+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS + TEXT_TYPE+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LATITUDE + " INTEGER"+ COMMA_SEPARATOR+
            FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE + " INTEGER )";


    private static final String SQL_DROP_TABLE="DROP TABLE IF EXISTS "+FavRestroContract.RestaurantEntry.TABLE_NAME;
    private SQLiteDatabase db = null;


    public FavoriteDatabaseHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDowngrade(db, oldVersion, newVersion);
    }

    public void makeDBWritable(){
        db = this.getWritableDatabase();
    }

    public void makeDBReadable(){
        db = this.getReadableDatabase();
    }


    public boolean insertRow(String businessName,String image,String ratingsUrl, String phoneNumber, double reviewCount, String address,double latitude, double longitude){
        makeDBWritable();

        ContentValues values = new ContentValues();

                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME,businessName);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE, image);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL,ratingsUrl);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER,phoneNumber);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT,reviewCount);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS,address);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE,latitude);
                values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE,longitude);

        long newRowId = db.insert(FavRestroContract.RestaurantEntry.TABLE_NAME,null,values);

        return newRowId!=-1?true:false;
    }




    public boolean updateRow(String businessName,String image,String ratingsUrl, String phoneNumber, double reviewCount, String address,double latitude, double longitude){
        makeDBWritable();

        ContentValues values = new ContentValues();

        if(businessName!=null)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME,businessName);
        if(image!=null)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_IMAGE, image);
        if(ratingsUrl!=null)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_RATING_URL,ratingsUrl);
        if(phoneNumber!=null)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_PHONE_NUMBER,phoneNumber);
        if(reviewCount>=0)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_REVIEWS_COUNT,reviewCount);
        if(address!=null)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_ADDRESS, address);
        if(latitude!=0)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LATITUDE,latitude);
        if(longitude!=0)
        values.put(FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_LONGITUDE,longitude);


        String selection = FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME +" = ?";
        String[] selectionArgs = {String.valueOf(businessName)};

        return db.update(FavRestroContract.RestaurantEntry.TABLE_NAME,values,selection,selectionArgs)>0?true:false;
    }

    public Cursor fetchRows(String[] projection, String selection, String[] selectionArgs,String sortOrder){
        makeDBReadable();
        return db.query(FavRestroContract.RestaurantEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
    }

    public boolean deleteRow(String businessName){
        makeDBWritable();
        String selection = FavRestroContract.RestaurantEntry.COLUMN_NAME_RESTAURANT_NAME+" = ?";
        String[] selectionArgs = {String.valueOf(businessName)};
        return db.delete(FavRestroContract.RestaurantEntry.TABLE_NAME, selection, selectionArgs)>0?true:false;
    }


}
