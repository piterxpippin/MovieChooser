package org.weeia.moviechooser;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.app.usage.UsageEvents;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;
        //import org.json.simple.parser.JSONParser;
        import org.w3c.dom.NodeList;


public class MovieInfo extends AppCompatActivity {
    public static String urlBase = "http://www.omdbapi.com/";

    public MovieInfo(){

    }


    public void getImageUrl(String movieID) {
        String stringUrl = createURL(movieID);
        new DownloadWebpageTask().execute(stringUrl);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            InputStream is = null;
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";

            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {}
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;



        try {
            URL url = new URL(myurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String str = stringBuilder.toString();

                JSONObject obj = new JSONObject(str);
                String n = obj.getString("Poster");

                System.out.println(n);
                MainActivity.proposal_1 = BitmapFactory.decodeStream((InputStream) new URL(n).getContent());
                MainActivity.imageView_1.setImageBitmap(MainActivity.proposal_1);
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }



    }

    private static String createURL(String number){
        String url = new String ();
        url = urlBase + "?i=tt" + number + "&plot=short&r=json";
        return url;
    }
}