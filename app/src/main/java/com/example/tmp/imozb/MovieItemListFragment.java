package com.example.tmp.imozb;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmp.imozb.grid.DicoverParse;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by tmp on 11/03/16.
 */
public class MovieItemListFragment extends ListFragment {

    public final static String EXTRA_MESSAGE_TITLE = "title";
    public final static String EXTRA_MESSAGE_DETAIL = "detail";
    public final static String EXTRA_MESSAGE_URL = "url";


    private String sort;


    ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);


//        for (int i = 0; i < 100; i++) {
//            String url = String.format("http://www.google.com/image/%d.png", i);
//            String title = String.format("Item %d", i);
//            String description = String.format("Description of Item %d", i);
//            MovieItem movieItem = new MovieItem(title, url, description);
//            movieItems.add(movieItem);
//        }

        RequestDiscover discover = new RequestDiscover();
        discover.execute();

        return v;
    }


    public class RequestDiscover extends AsyncTask<Void, Void, String> {
        private boolean initialLoadComplete = false;
        private int nextPage = 1;
        private boolean isLoadingMovies = false;


        private final String LOG_TAG = RequestDiscover.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String discoverJsonStr = null;

            try {
                final String BASE_URL = "api.themoviedb.org";
                //final String dicover /3/discover/movie
                final String API_PARAM = "api_key";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority(BASE_URL)
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("page", "" + nextPage)
                        .appendQueryParameter("sort_by", sort)
                        .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_DB_API_KEY);
                String urlKey = builder.build().toString();

                Log.v("URL", urlKey);

                URL url = new URL(urlKey);

                // Create request to connection met movie db
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputstream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputstream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputstream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                discoverJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error" + e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }

            return discoverJsonStr;
        }

        @Override
        protected void onPostExecute(String jSonArray) {
            super.onPostExecute(jSonArray);
            DicoverParse parse = new DicoverParse(jSonArray);
            try {
                isLoadingMovies = false;
                movieItems.addAll(parse.jsonParse());

                setListAdapter(new MovieItemAdapter(getActivity(), movieItems));

                initialLoadComplete = true;
                nextPage++;

                ///movies.poster.iterator().p
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
}
