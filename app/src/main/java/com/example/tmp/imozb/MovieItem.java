package com.example.tmp.imozb;

import android.net.Uri;

import java.net.MalformedURLException;

/**
 * Created by tmp on 22/02/16.
 */
public class MovieItem {

    private String mTitle;
    private String mPosterUrl;
    private String mMovieUrl;
    private String mDescription;

    public MovieItem(String title,String posterUrl, String description ) throws MalformedURLException {
        super();
        mTitle = title;
        mPosterUrl = posterUrl;
        mMovieUrl = postUrlBuilder(posterUrl);
        mDescription = description;
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

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setImageUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}

