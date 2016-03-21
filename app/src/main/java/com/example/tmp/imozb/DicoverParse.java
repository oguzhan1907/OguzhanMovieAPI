package com.example.tmp.imozb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Parses the JSON file and creates MoivieInfo Objects
 */
public class DicoverParse {

    String discoverStr;

    // Constructor of class
    public DicoverParse(String mDiscoverParse) {
        discoverStr = mDiscoverParse;
    }

    /**
     * Parse the JSON String into MovieInfor objecst as long as the string is and returns
     * the items
     */
    public ArrayList<MovieInfo> jsonParse() throws JSONException, MalformedURLException {

        JSONObject infoObject = new JSONObject(discoverStr);

        JSONArray infoArray = infoObject.getJSONArray("results");

        ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();

        for (int i = 0; infoArray.length() > i; i++) {
            JSONObject movieObjet = infoArray.getJSONObject(i);

            // Create movie objects with that contains all the info
            MovieInfo movieTemp = new MovieInfo(movieObjet.getString("title")
                    , movieObjet.getString("poster_path")
                    , movieObjet.getString("overview")

            );

            movies.add(movieTemp);
        }

        return movies;
    }

}
