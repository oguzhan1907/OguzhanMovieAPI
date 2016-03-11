package com.example.tmp.imozb.grid;

import android.util.Log;

import com.example.tmp.imozb.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by tmp on 23/02/16.
 */
public class DicoverParse {

    String discoverStr;
    boolean hasNext;

    public DicoverParse(String mDiscoverParse){
        discoverStr =  mDiscoverParse;
    }

    public  ArrayList<MovieItem> jsonParse () throws JSONException, MalformedURLException {

        JSONObject infoObject = new JSONObject(discoverStr);

        JSONArray infoArray = infoObject.getJSONArray("results");

        ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();

        for (int i = 0; infoArray.length()>i;i++){
            JSONObject movieObjet = infoArray.getJSONObject(i);


            // Create movie objects with that contains all the info
            MovieItem movieItem = new MovieItem(movieObjet.getString("title")
                    ,movieObjet.getString("poster_path")
                    ,movieObjet.getString("overview")


            );

            Log.v("Title", movieObjet.getString("title"));


            movieItems.add(movieItem);
        }

        return movieItems;
    }



}
