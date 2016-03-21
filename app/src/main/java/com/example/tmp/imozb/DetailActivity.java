package com.example.tmp.imozb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * When Poster is clicked this detailed information screen opens
 * containing an movies poster, title and description
 */
public class DetailActivity extends AppCompatActivity {

    TextView title;
    ImageView poster;
    TextView description;

    final String OLD_SIZE_PARAM = "w185";
    final String NEW_SIZE_PARAM = "w500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Gets the movie inforamation passed throug the on item clicklister with
        // an intent.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_MESSAGE_TITLE)) {

            // Assign ID
            title = (TextView) findViewById(R.id.detail_title);
            poster = (ImageView) findViewById(R.id.detai_image);
            description = (TextView) findViewById(R.id.detail_desciption);

            // Set text
            title.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE_TITLE));
            description.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE_DETAIL));

            String resize = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_URL);
            resize = resize.replace(OLD_SIZE_PARAM, NEW_SIZE_PARAM);

            // Set Image with imageloader library
            Picasso
                    .with(getApplicationContext())
                    .load(resize)
                    .into(poster);


        }

    }
}
