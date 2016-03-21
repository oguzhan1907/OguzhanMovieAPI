package com.example.tmp.imozb;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * AsyncTask class that fetches the movie data from the API and passes it to the main activity
 * or parse class when done in a background thread
 */
public class RequestDiscover extends AsyncTask<Void, Void, String> {

    private MainActivity mainActivity;
    // Logging variable
    private final String LOG_TAG = RequestDiscover.class.getSimpleName();

    // Public constructor
    public RequestDiscover(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     *
     * Function that makes the API connection and fetches tha data in
     * a background thread
     */
    @Override
    protected String doInBackground(Void... params) {

        // Connection and reader instances that have to be closed
        // as last action in finally and thus have to declared here
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String discoverJsonStr = null;

        try {

            // Base URL of the API
            final String BASE_URL = "api.themoviedb.org";
            // API key parameter of API call
            final String API_PARAM = "api_key";

            // Save URI Builder
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(BASE_URL)
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("page", "" + mainActivity.nextPage)
                    .appendQueryParameter("sort_by", mainActivity.sort)
                    .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_DB_API_KEY);
            String urlKey = builder.build().toString();

            URL url = new URL(urlKey);

            // Create request to connection met movie db with GET METHOD
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            if (urlConnection.getResponseCode() != 200){
                Log.e(LOG_TAG, "Error Connecting response code :" + urlConnection.getResponseCode());
                return null;
            }
            urlConnection.connect();

            // Direct data from the Url Connection to a inputsream
            InputStream inputstream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputstream == null) {
                return null;
            }
            // Directs stream into a BufferReader
            reader = new BufferedReader(new InputStreamReader(inputstream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Makes it possible to read the JSON for debugging purposses
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            // Turns the buffer into a string
            discoverJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error" + e);

        } finally {
            // Close connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // close reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }

        // Returns the retrived json string
        return discoverJsonStr;
    }

    /**
     * Follows the actions when the doInBackground function is done
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // Pass the results to a parse function
        DicoverParse parse = new DicoverParse(s);
        try {

            // Boaling is loading is false so new request can be made
            // movies being added to the addapter after being parsed
            // and adapter being set
            mainActivity.isLoadingMovies = false;
            mainActivity.movies.addAll(parse.jsonParse());
            mainActivity.grid.setAdapter(mainActivity.adapter = new ImageAdapter(mainActivity, mainActivity.movies));


            mainActivity.initialLoadComplete = true;
            mainActivity.nextPage++;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
