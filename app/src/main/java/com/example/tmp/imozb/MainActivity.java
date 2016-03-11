package com.example.tmp.imozb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_TITLE = "title";
    public final static String EXTRA_MESSAGE_DETAIL = "detail";
    public final static String EXTRA_MESSAGE_URL = "url";
    private ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();



    private String sort;

    private GridView grid;
    public ImageAdapter adapter;

    private boolean initialLoadComplete = false;
    private int nextPage = 1;
    private boolean isLoadingMovies = false;
    public SharedPreferences sortPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sorting prefernece
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        sortPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sort  = sortPrefs.getString("sort", "jooduuuh");

        // gridview init
        grid = (GridView) findViewById(R.id.list_view);
        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                {
                    if (!isLoadingMovies && initialLoadComplete && firstVisibleItem + visibleItemCount >= totalItemCount) {

                        isLoadingMovies = true;

                        RequestDiscover discover = new RequestDiscover();
                        discover.execute();


                    }
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);

                detailIntent.putExtra(EXTRA_MESSAGE_TITLE, movies.get(position).title);
                detailIntent.putExtra(EXTRA_MESSAGE_DETAIL, movies.get(position).discription);
                detailIntent.putExtra(EXTRA_MESSAGE_URL, movies.get(position).movieUrl);
                startActivity(detailIntent);
            }
        });


        RequestDiscover discover = new RequestDiscover();
        discover.execute();
    }

    @Override
   protected void onStart() {
       super.onStart();

        //sortPrefs.registerOnSharedPreferenceChangeListener();
        sort  = sortPrefs.getString("sort", "jooduuuh");

        Log.v("OnStart", sort);
        //movies.clear();
        if (!isLoadingMovies && initialLoadComplete) {
            movies.clear();
            isLoadingMovies = true;
            RequestDiscover discover = new RequestDiscover();
            discover.execute();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting_menu){
            Intent settingsIntent = new Intent (this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class RequestDiscover extends AsyncTask<Void, Void, String> {

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DicoverParse parse = new DicoverParse(s);
            try {
                isLoadingMovies = false;
//                if (movies.size()==0){
                movies.addAll(parse.jsonParse());
                grid.setAdapter(adapter = new ImageAdapter(MainActivity.this, movies));
//                }else {
//                    movies.addAll(parse.jsonParse());
//                    notifyDataSetChanged();
//                }


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
