package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextView lbl_id, lbl_title, lbl_overview, lbl_release_date, lbl_genre;
    private ImageView img_poster;
    private String movie_id = "", movie_genre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showMovieDetails);

        lbl_id = findViewById(R.id.lbl_id_movie_details);
        lbl_title = findViewById(R.id.lbl_title_movie_details);
        lbl_overview = findViewById(R.id.lbl_overview_movie_details);
        lbl_release_date = findViewById(R.id.lbl_releasedate_movie_details);
        lbl_genre = findViewById(R.id.lbl_genres_movie_details);
        img_poster = findViewById(R.id.img_poster_movie_details);
        lbl_id.setText(movie_id);
    }

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String overview = movies.getOverview();
            String release_date = movies.getRelease_date();
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();

            lbl_title.setText(title);
            lbl_overview.setText(overview);
            lbl_release_date.setText(release_date);
            Glide.with(MovieDetailsActivity.this).load(img_path).into(img_poster);
            for (int i = 0; i<movies.getGenres().size(); i++){
                if(i == movies.getGenres().size() - 1){
                    movie_genre += movies.getGenres().get(i).getName();
                }else{
                    movie_genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            lbl_genre.setText(movie_genre);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

}