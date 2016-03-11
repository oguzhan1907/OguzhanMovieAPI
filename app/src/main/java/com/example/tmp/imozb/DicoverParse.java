package com.example.tmp.imozb;

import android.util.Log;

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

    public  ArrayList<MovieInfo> jsonParse () throws JSONException, MalformedURLException {

        JSONObject infoObject = new JSONObject(discoverStr);

        JSONArray infoArray = infoObject.getJSONArray("results");

        ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();

        for (int i = 0; infoArray.length()>i;i++){
            JSONObject movieObjet = infoArray.getJSONObject(i);

            // Create movie objects with that contains all the info
            MovieInfo movieTemp = new MovieInfo(movieObjet.getString("title")
                    ,movieObjet.getString("poster_path")
                    ,movieObjet.getString("overview")


            );

            Log.v("Title", movieObjet.getString("title"));


            movies.add(movieTemp);
        }

        return movies;
    }



}
