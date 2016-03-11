package com.example.tmp.imozb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_TITLE = "title";
    public final static String EXTRA_MESSAGE_DETAIL = "detail";
    public final static String EXTRA_MESSAGE_URL = "url";
    private ArrayList<MovieItem> movies = new ArrayList<MovieItem>();



    private String sort;

    private boolean initialLoadComplete = false;
    private int nextPage = 1;
    private boolean isLoadingMovies = false;
    public SharedPreferences sortPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sorting prefernece
//        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
//        sortPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        sort  = sortPrefs.getString("sort", "jooduuuh");

        // gridview init
//        grid = (GridView) findViewById(R.id.list_view);
//        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                {
//                    if (!isLoadingMovies && initialLoadComplete && firstVisibleItem + visibleItemCount >= totalItemCount) {
//
//                        isLoadingMovies = true;
//
//                        RequestDiscover discover = new RequestDiscover();
//                        discover.execute();
//
//
//                    }
//                }
//            }
//        });
//
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
//
//                detailIntent.putExtra(EXTRA_MESSAGE_TITLE, movies.get(position).title);
//                detailIntent.putExtra(EXTRA_MESSAGE_DETAIL, movies.get(position).discription);
//                detailIntent.putExtra(EXTRA_MESSAGE_URL, movies.get(position).movieUrl);
//                startActivity(detailIntent);
//            }
//        });


//        RequestDiscover discover = new RequestDiscover();
//        discover.execute();
    }

    @Override
   protected void onStart() {
       super.onStart();

//        sort  = sortPrefs.getString("sort", "jooduuuh");
//
//        Log.v("OnStart", sort);
//        //movies.clear();
//        if (!isLoadingMovies && initialLoadComplete) {
//            movies.clear();
//            isLoadingMovies = true;
//            RequestDiscover discover = new RequestDiscover();
//            discover.execute();
//        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.setting_menu){
//            Intent settingsIntent = new Intent (this, SettingsActivity.class);
//            startActivity(settingsIntent);
//        }
//        return super.onOptionsItemSelected(item);
//    }



}
