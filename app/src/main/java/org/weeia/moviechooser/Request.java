package org.weeia.moviechooser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import moviefinder.dao.MovieSource;
import moviefinder.dao.RestSource;
import moviefinder.domain.Movie;
import moviefinder.domain.MovieType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Request extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "org.weeia.moviechooser.MOVIES";

    MovieSource movieSource;
    List<Movie> movieList = new ArrayList<Movie>();
    MovieType movieType;
    RadioButton exactRate;
    RatingBar ratingbar;
    EditText title;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exactRate = (RadioButton) findViewById(R.id.exactRate);
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        title = (EditText) findViewById(R.id.editText_title);
        ratingbar.setRating(4.0f);


        //List<Integer> w= Lists.newArrayList();

        movieSource = new RestSource("http://192.168.0.108:9200/");


        //List<Movie> moviesWithGivenGenreSortedByNumberOfVotes = movieSource.getMoviesWithGivenGenreSortedByNumberOfVotes(MovieType.DRAMA, 0, 0, 50);
        //movieSource.getMoviesWithGivenGenreAndMinimumNumberOfRatings(MovieType.DRAMA,50,0,50);
        //List<Movie> moviesWithGivenGenreAndAverageRatingStratingFrom = movieSource.getMoviesWithGivenGenreAndAverageRatingStratingFrom(MovieType.DRAMA, 0.0f, 0, 50, 50);
        //System.out.print(moviesWithGivenGenreAndAverageRatingStratingFrom);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_request);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().contentEquals("")) {
                    if (exactRate.isChecked()) {
                        try{
                            movieList.clear();
                            movieList = movieSource.getMoviesWithGivenGenreAndAverageRatingStratingFrom(movieType, ratingbar.getRating(), 0, 50, 20);
                        } catch (Exception e){
                            Snackbar.make(view, "nic nie znaleziono", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        try {
                            movieList.clear();
                            movieList = movieSource.getMoviesWithGivenGenreAndMinimumNumberOfRatings(movieType, Math.round(ratingbar.getRating()), 0, 50);
                        } catch ( Exception e ) {
                            Snackbar.make(view, "nic nie znaleziono", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                } else {
                    try {
                        System.out.print(title.getText().toString());
                        Movie movieByTitle = movieSource.getMovieByTitle(title.getText().toString());
                        movieList.add(movieByTitle);
                        Snackbar.make(view, movieByTitle.getTitle().toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } catch ( Exception e ) {
                        Snackbar.make(view, "nic nie znaleziono", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }

                if ( movieList.size() < 1 ) {
                    Snackbar.make(view, "nic nie znaleziono", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), SearchResult.class);
                    intent.putExtra("listOfMovies", (Serializable) movieList);
                    startActivity(intent);
                }


            }
        });
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_comedy:
                if (checked) {
                    movieType = MovieType.COMEDY;
                }
                break;
            case R.id.radio_drama:
                if (checked) {
                    movieType = MovieType.DRAMA;
                }
                break;
            case R.id.radio_thriller:
                if (checked) {
                    movieType = MovieType.THRILLER;
                }
                break;
            case R.id.radio_crime:
                if (checked) {
                    movieType = MovieType.CRIME;
                }
                break;
            case R.id.radio_war:
                if (checked) {
                    movieType = MovieType.WAR;
                }
                break;
            case R.id.radio_action:
                if (checked) {
                    movieType = MovieType.ACTION;
                }
                break;
            case R.id.radio_adventure:
                if (checked) {
                    movieType = MovieType.ADVENTURE;
                }
                break;
            case R.id.radio_romance:
                if (checked) {
                    movieType = MovieType.ROMANCE;
                }
                break;
            case R.id.radio_sci_fi:
                if (checked) {
                    movieType = MovieType.SCI_FI;
                }
                break;
            case R.id.radio_film_noir:
                if (checked) {
                    movieType = MovieType.FILM_NOIR;
                }
                break;
            case R.id.radio_imax:
                if (checked) {
                    movieType = MovieType.IMAX;
                }
                break;
            case R.id.radio_mystery:
                if (checked) {
                    movieType = MovieType.MYSTERY;
                }
                break;
            case R.id.radio_western:
                if (checked) {
                    movieType = MovieType.WESTERN;
                }
                break;
            case R.id.radio_fantasy:
                if (checked) {
                    movieType = MovieType.FANTASY;
                }
                break;
            case R.id.radio_animation:
                if (checked) {
                    movieType = MovieType.ANIMATION;
                }
                break;
            case R.id.radio_children:
                if (checked) {
                    movieType = MovieType.CHILDREN;
                }
                break;
            case R.id.radio_musical:
                if (checked) {
                    movieType = MovieType.MUSICAL;
                }
                break;
            case R.id.radio_horror:
                if (checked) {
                    movieType = MovieType.HORROR;
                }
                break;
            case R.id.radio_documentary:
                if (checked) {
                    movieType = MovieType.DOCUMENTARY;
                }
                break;
        }
    }


}
