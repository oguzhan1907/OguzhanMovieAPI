package com.example.tmp.imozb;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class MovieItemAdapter extends ArrayAdapter<MovieItem> {
    
    public MovieItemAdapter(Context c, List<MovieItem> movieItems) {
        super(c, 0, movieItems);


    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieItemView movieItemView = (MovieItemView)convertView;
        if (null == movieItemView)
            movieItemView = MovieItemView.inflate(parent);
        movieItemView.setItem(getItem(position));
        return movieItemView;
    }

}
