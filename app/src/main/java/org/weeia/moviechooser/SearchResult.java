package org.weeia.moviechooser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import moviefinder.domain.Movie;
import moviefinder.domain.MovieType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        final ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie detailedDescription = listOfMovies.get(position);

                long imdbId = detailedDescription.getImdbId();
                JSONObject movieInfo = getJson(createUrl(imdbId));
                Intent myIntent = new Intent(getBaseContext(), MovieDetailsActivity.class);
                myIntent.putExtra("movieInfo", movieInfo.toString());
                startActivity(myIntent);
            }
        });
    }

    private String createUrl(Long imdbMovieId) {
        return "http://www.omdbapi.com/?i=tt" + imdbMovieId + "&plot=short&r=json";
    }

    protected JSONObject getJson(String url) {
        JSONObject jsonObject = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

            jsonObject = new JSONObject(stringBuilder.toString());

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    private ArrayList<Movie> listOfMovies = null;
    private ArrayAdapter<String> adapter = null;
    private ArrayList<String> movieInfo = null;

}
