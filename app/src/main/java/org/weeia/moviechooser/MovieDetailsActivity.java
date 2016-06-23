package org.weeia.moviechooser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import moviefinder.domain.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                movieInfo = new JSONObject((String) extras.getSerializable("movieInfo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            ((TextView) findViewById(R.id.titleDescLabel)).setText(getMovieTitle());
            ((TextView) findViewById(R.id.yearDescLabel)).setText(getYear());
            ((TextView) findViewById(R.id.releasedDescLabel)).setText(getReleased());
            ((TextView) findViewById(R.id.runtimeDescLabel)).setText(getRuntime());
            ((TextView) findViewById(R.id.genreDescLabel)).setText(getGenre());
            ((TextView) findViewById(R.id.directorDescLabel)).setText(getDirector());
            ((TextView) findViewById(R.id.writerDescLabel)).setText(getWriter());
            ((TextView) findViewById(R.id.actorsDescLabel)).setText(getActors());
            ((TextView) findViewById(R.id.plotDescLabel)).setText(getPlot());
            ((TextView) findViewById(R.id.countryDescLabel)).setText(getCountry());
            ((TextView) findViewById(R.id.imdbRatingDescLabel)).setText(getimdbRating());

            new DownloadImageTask((ImageView) findViewById(R.id.posterView)).execute(getPoster());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMovieTitle() throws org.json.JSONException {
        String str = movieInfo.getString("Title");
        return str;
    }

    private String getYear() throws org.json.JSONException {
        return movieInfo.getString("Year");
    }

    private String getReleased() throws org.json.JSONException {
        return movieInfo.getString("Released");
    }

    private String getRuntime() throws org.json.JSONException {
        return movieInfo.getString("Runtime");
    }

    private String getGenre() throws org.json.JSONException {
        return movieInfo.getString("Genre");
    }

    private String getDirector() throws org.json.JSONException {
        return movieInfo.getString("Director");
    }

    private String getWriter() throws org.json.JSONException {
        return movieInfo.getString("Writer");
    }

    private String getActors() throws org.json.JSONException {
        return movieInfo.getString("Actors");
    }

    private String getPlot() throws org.json.JSONException {
        return movieInfo.getString("Plot");
    }

    private String getCountry() throws org.json.JSONException {
        return movieInfo.getString("Country");
    }

    private String getPoster() throws org.json.JSONException {
        return movieInfo.getString("Poster");
    }

    private String getimdbRating() throws org.json.JSONException {
        return movieInfo.getString("imdbRating");
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap poster = null;
            try {
                poster = BitmapFactory.decodeStream((InputStream) new URL(urls[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return poster;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

        private ImageView bmImage;
    }



    private JSONObject movieInfo;
}
