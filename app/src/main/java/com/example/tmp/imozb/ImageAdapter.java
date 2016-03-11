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
 * Created by tmp on 22/02/16.
 */
public class ImageAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<MovieInfo> movies;
    public ArrayList<String> movieUrls = new ArrayList<String>();

    //public String[] imageUrls;
    private LayoutInflater inflater;

    public ImageAdapter(Context mcontext, ArrayList<MovieInfo> wMovies) {
        super(mcontext, R.layout.grid_view, wMovies);

        context = mcontext;
        movies = wMovies;
        movieUrls.addAll(getPosterUrls(wMovies));
        //imageUrls = movieUrls.toArray(new String[movieUrls.size()]);
        inflater = LayoutInflater.from(context);


    }


    private ArrayList<String> getPosterUrls(ArrayList<MovieInfo> wMovies) {

        ArrayList<String> tempMovieUrls = new ArrayList<String>();

        for (int i = 0; wMovies.size() > i; i++) {
            tempMovieUrls.add(wMovies.get(i).movieUrl);

        }
        return tempMovieUrls;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_view, parent, false);
        }

        Picasso
                .with(context)
                .load(movieUrls.get(position))
                .fit()
                .centerCrop()
                .into((ImageView) convertView);

        return convertView;
    }

    public ArrayList<MovieInfo> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieInfo> movies) {
        this.movies = movies;
    }
}