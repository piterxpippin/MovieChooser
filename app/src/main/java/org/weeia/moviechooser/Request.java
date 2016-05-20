package org.weeia.moviechooser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import moviefinder.dao.elasticsearch.ElasticSearchMovieSource;
import moviefinder.domain.Movie;
import moviefinder.domain.MovieType;

public class Request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ElasticSearchMovieSource elasticSearchMovieSource= new ElasticSearchMovieSource("192.168.0.108",9300);


        //List<Movie> moviesWithGivenGenreSortedByNumberOfVotes = elasticSearchMovieSource.getMoviesWithGivenGenreSortedByNumberOfVotes(MovieType.DRAMA, 0, 0, 50);
        //elasticSearchMovieSource.getMoviesWithGivenGenreAndMinimumNumberOfRatings(MovieType.DRAMA,50,0,50);
        List<Movie> moviesWithGivenGenreAndAverageRatingStratingFrom = elasticSearchMovieSource.getMoviesWithGivenGenreAndAverageRatingStratingFrom(MovieType.DRAMA, 0.0f, 0, 50, 50);
        System.out.print(moviesWithGivenGenreAndAverageRatingStratingFrom);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
