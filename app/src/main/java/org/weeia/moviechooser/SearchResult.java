package org.weeia.moviechooser;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import moviefinder.domain.Movie;
import moviefinder.domain.MovieType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;



public class SearchResult extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search results");

        fillUpListViewWithMovieDescriptions();
        setItemClickListenerForListView();
    }

    private void fillUpListViewWithMovieDescriptions() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getListOfMovies(extras);
            createMovieDescriptionList();
            setupAdapterForListView();
        }
    }

    private void getListOfMovies(Bundle extras) {
        listOfMovies = (ArrayList<Movie>) extras.getSerializable("listOfMovies");
    }

    private void createMovieDescriptionList() {
        movieInfo = new ArrayList<String>();
        for (Movie movie : listOfMovies) {
            String movieDescription = createMovieDescription(movie);
            movieInfo.add(movieDescription);
        }
        Collections.sort(movieInfo);
    }

    @NonNull
    private String createMovieDescription(Movie movie) {
        Set<MovieType> movieTypes = movie.getMovieTypes();
        StringBuilder movieTypesEnumeration = new StringBuilder();
        for (MovieType movieType : movieTypes) {
            movieTypesEnumeration.append(capitalize(movieType.name()));
            movieTypesEnumeration.append(", ");
        }
        String genres = movieTypesEnumeration.toString();
        return movie.getTitle() + " (" + genres.substring(0, genres.length() - 2) + ")";
    }

    private void setupAdapterForListView() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movieInfo);
        setListAdapter(adapter);
    }

    private String capitalize(String toCapitalize) {
        return Character.toUpperCase(toCapitalize.charAt(0)) +
               toCapitalize.substring(1).toLowerCase();
    }

    private void setItemClickListenerForListView() {
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Po tapnięciu elementu listy, zostaniesz przekierowany na " +
                        "stronę z opisem filmu.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ArrayList<Movie> listOfMovies = null;
    private ArrayAdapter<String> adapter = null;
    private ArrayList<String> movieInfo = null;

}
