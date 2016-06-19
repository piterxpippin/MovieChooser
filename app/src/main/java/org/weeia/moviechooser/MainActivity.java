package org.weeia.moviechooser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //public static ImageView imageView_1;
    //public static ImageView imageView_2;
    public static String urlBase = "http://www.omdbapi.com/";

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logopasek2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        new DownloadImageTask((ImageView) findViewById(R.id.proposal_1))
                .execute(createURL("1216496"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_2))
                .execute(createURL("0803096"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_3))
                .execute(createURL("2277860"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_4))
                .execute(createURL("1489889"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_5))
                .execute(createURL("4034354"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_6))
                .execute(createURL("5278506"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_7))
                .execute(createURL("4738360"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_8))
                .execute(createURL("1780798"));

        new DownloadImageTask((ImageView) findViewById(R.id.proposal_9))
                .execute(createURL("3065204"));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

// Leaving this part just for an example of SearchResult usage. Feel free to remove soon
//                    Intent myIntent = new Intent(getBaseContext(), SearchResult.class);
//                    ArrayList<Movie> listOfMovies = createExemplaryListOfMovies();
//                    myIntent.putExtra("listOfMovies", listOfMovies);
//                    startActivity(myIntent);
//                }
//
//                @NonNull
//                private ArrayList<Movie> createExemplaryListOfMovies() {
//                    ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
//
//                    HashSet<MovieType> movieType = new HashSet<MovieType>();
//                    movieType.add(MovieType.ADVENTURE);
//
//                    HashSet<MovieType> movieType2 = new HashSet<MovieType>();
//                    movieType2.add(MovieType.ADVENTURE);
//                    movieType2.add(MovieType.CHILDREN);
//
//                    listOfMovies.add(new Movie(1, "Indiana Jones", movieType));
//                    listOfMovies.add(new Movie(2, "Goonies", movieType2));
//
//                    return listOfMovies;
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_search){
            startActivity(new Intent(this, Request.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item_1) {
            // Handle the camera action
        } else if (id == R.id.nav_item_2) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String str = stringBuilder.toString();

                JSONObject obj = new JSONObject(str);
                final String n = obj.getString("Poster");

                mIcon11 = BitmapFactory.decodeStream((InputStream) new URL(n).getContent());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private static String createURL(String number){
        String url = new String ();
        url = urlBase + "?i=tt" + number + "&plot=short&r=json";
        return url;
    }
}

