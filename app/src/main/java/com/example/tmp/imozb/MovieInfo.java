package com.example.tmp.imozb;

import android.net.Uri;

import java.net.MalformedURLException;

/**
 * Movie info class that contains all the info movie info  retrived from the api
 */
public class MovieInfo {

    public String title;
    public String posterPath;
    public String movieUrl;
    public String discription;

    // Constructor of the class
    public MovieInfo(String mTitle, String mPosterPath, String mDiscription) throws MalformedURLException {
        title = mTitle;
        posterPath = mPosterPath;
        movieUrl = postUrlBuilder(mPosterPath);
        discription = mDiscription;
    }

    // Build the Url off each movie poster
    public String postUrlBuilder(String path) throws MalformedURLException {
        final String BASE_URL = "image.tmdb.org";
        final String API_PARAM = "api_key";
        // size of poster
        final String SIZE_PARAM = "w185";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(BASE_URL)
                .appendPath("t")
                .appendPath("p")
                .appendPath(SIZE_PARAM)
                .appendEncodedPath(path)
                .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_DB_API_KEY);
        String urlKey = builder.build().toString();

        return urlKey;

    }
}
