package com.example.tmp.imozb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * ImageAdapter that populates the grid with movie posters
 */
public class ImageAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<MovieInfo> movies;
    public ArrayList<String> movieUrls = new ArrayList<String>();

    private LayoutInflater inflater;

    // Constructor of the class
    public ImageAdapter(Context mcontext, ArrayList<MovieInfo> wMovies) {
        super(mcontext, R.layout.grid_view, wMovies);
        context = mcontext;
        movies = wMovies;
        movieUrls.addAll(getPosterUrls(wMovies));
        inflater = LayoutInflater.from(context);


    }

    // Gets urls form the MovieItemclass
    private ArrayList<String> getPosterUrls(ArrayList<MovieInfo> wMovies) {

        ArrayList<String> tempMovieUrls = new ArrayList<String>();

        for (int i = 0; wMovies.size() > i; i++) {
            tempMovieUrls.add(wMovies.get(i).movieUrl);

        }
        return tempMovieUrls;

    }

    // Positiions the items of the grid and recyles the views
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_view, parent, false);
        }

        // Movieposter loader library
        Picasso
                .with(context)
                .load(movieUrls.get(position))
                .fit()
                .centerCrop()
                .into((ImageView) convertView);

        return convertView;
    }

}