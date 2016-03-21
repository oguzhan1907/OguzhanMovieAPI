package com.example.tmp.imozb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Main acitity that manages the gridview and listens to item clicks
 * and opens detail activity and passes the right information
 */
public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_TITLE = "title";
    public final static String EXTRA_MESSAGE_DETAIL = "detail";
    public final static String EXTRA_MESSAGE_URL = "url";
    public ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();

    // String to store sortinng prefs
    public String sort;
    public SharedPreferences sortPrefs;

    // Create grid and adapter instances
    public GridView grid;
    public ImageAdapter adapter;

    // Variables to handle correct loading and loading multiple pages
    public boolean initialLoadComplete = false;
    public int nextPage = 1;
    public boolean isLoadingMovies = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets Sorting prefernece
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        sortPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sort = sortPrefs.getString("sort", "hoi");

        // gridview init and attach
        grid = (GridView) findViewById(R.id.list_view);
        // When end of list is reaches
        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * Load extra items when the end of the list is reached
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                {
                    if (!isLoadingMovies && initialLoadComplete && firstVisibleItem + visibleItemCount >= totalItemCount) {

                        isLoadingMovies = true;

                        RequestDiscover discover = new RequestDiscover(MainActivity.this);
                        discover.execute();
                    }
                }
            }
        });

        // When item is clicked
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *  Creatyes Detail activity when item is clicked
             *  and passes the information through an intent
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);

                detailIntent.putExtra(EXTRA_MESSAGE_TITLE, movies.get(position).title);
                detailIntent.putExtra(EXTRA_MESSAGE_DETAIL, movies.get(position).discription);
                detailIntent.putExtra(EXTRA_MESSAGE_URL, movies.get(position).movieUrl);
                startActivity(detailIntent);
            }
        });

        // Request data from API
        RequestDiscover discover = new RequestDiscover(this);
        discover.execute();
    }

    /**
     * Whe is opened again check if the preferences have changed
     */
    @Override
    protected void onStart() {
        super.onStart();
        sort = sortPrefs.getString("sort", "hoi");

        if (!isLoadingMovies && initialLoadComplete) {
            movies.clear();
            isLoadingMovies = true;
            RequestDiscover discover = new RequestDiscover(this);
            discover.execute();
        }
    }

    /**
     * Inflates Settings options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    /**
     * Directs to Settings activity when button is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting_menu) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
