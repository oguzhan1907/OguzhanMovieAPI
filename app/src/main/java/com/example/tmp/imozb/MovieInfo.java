package com.example.tmp.imozb;

import android.net.Uri;

import java.net.MalformedURLException;

/**
 * Created by tmp on 22/02/16.
 */
public class MovieInfo {

    public String title;
    public String posterPath;
    public String movieUrl;
    public String discription;

    public MovieInfo(String mTitle,String mPosterPath, String mDiscription) throws MalformedURLException {
        title = mTitle;
        posterPath = mPosterPath;
        movieUrl = postUrlBuilder(mPosterPath);
        discription = mDiscription;
    }

    public String postUrlBuilder (String path) throws MalformedURLException {
        //http://image.tmdb.org/t/p/.
        final String BASE_URL = "image.tmdb.org";
        //final String dicover /3/discover/movie
        final String API_PARAM = "api_key";
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
