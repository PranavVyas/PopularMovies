package com.pro.vyas.pranav.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter.*;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.text_title_detail) TextView tvTitle;
    @BindView(R.id.text_genre_detail) TextView tvGenre;
    @BindView(R.id.text_rating_detail) TextView tvRating;
    @BindView(R.id.text_release_detail) TextView tvRelease;
    @BindView(R.id.text_synopsis_detail) TextView tvSynopsis;
    @BindView(R.id.text_total_votes_detail) TextView tvTotalVotes;
    @BindView(R.id.image_poster_detail) ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent.hasExtra(KEY_TITLE)){
            tvTitle.setText(intent.getStringExtra(KEY_TITLE));
        }
        if(intent.hasExtra(KEY_RELEASE_DATE)){
            tvRelease.setText(intent.getStringExtra(KEY_RELEASE_DATE));
        }
        if(intent.hasExtra(KEY_GENRE)){
            tvGenre.setText(intent.getStringExtra(KEY_GENRE));
        }
        if(intent.hasExtra(KEY_VOTE_AVERAGE)){
            tvRating.setText(intent.getStringExtra(KEY_VOTE_AVERAGE));
        }
        if(intent.hasExtra(KEY_RATING)){
            tvTotalVotes.setText(intent.getStringExtra(KEY_RATING));
        }
        if(intent.hasExtra(KEY_OVERVIEW)){
            tvSynopsis.setText(intent.getStringExtra(KEY_OVERVIEW));
        }
        if(intent.hasExtra(KEY_POSTER_PATH)){
            Picasso.get()
                    .load(intent.getStringExtra(KEY_POSTER_PATH))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_ticketlower)
                    .into(ivPoster);
        }
    }
}
