package com.example.tmp.imozb;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieItemView extends RelativeLayout {
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;

    public static MovieItemView inflate(ViewGroup parent) {
        MovieItemView movieItemView = (MovieItemView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_view, parent, false);
        return movieItemView;
    }

    public MovieItemView(Context c) {
        this(c, null);
    }

    public MovieItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.movie_item_view_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        mTitleTextView = (TextView) findViewById(R.id.item_titleTextView);
        mDescriptionTextView = (TextView) findViewById(R.id.item_descriptionTextView);
        mImageView = (ImageView) findViewById(R.id.item_imageView);
    }

    public void setItem(MovieItem movieItem) {
        mTitleTextView.setText(movieItem.getTitle());
        mDescriptionTextView.setText(movieItem.getDescription());

        // TODO: set up image URL
        Picasso
                .with(getContext())
                .load(movieItem.getPosterUrl())
                .into(mImageView);
    }
    
    public ImageView getImageView () {
        return mImageView;
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getDescriptionTextView() {
        return mDescriptionTextView;
    }
}
