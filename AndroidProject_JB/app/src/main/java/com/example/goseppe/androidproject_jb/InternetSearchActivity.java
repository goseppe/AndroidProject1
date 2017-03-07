package com.example.goseppe.androidproject_jb;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InternetSearchActivity extends AppCompatActivity {

    EditText internetSearch;
    ListView listView;
    Button searchBtn;
    ArrayList<MyFilms> allFilms;
    ArrayAdapter<MyFilms> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_internet);

        listView = (ListView) findViewById(R.id.myIntLV);
        allFilms = new ArrayList<>();
        adapter = new ArrayAdapter<MyFilms>(this, R.layout.films_item, allFilms);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyFilms films = allFilms.get(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                //   intent.setData(Uri.parse(films.link))
            }
        });

        listView.setAdapter(adapter);
        searchBtn = (Button) findViewById(R.id.goBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetSearch = (EditText) findViewById(R.id.intSearchET);
                DownloadFilms downloadFilms = new DownloadFilms();
                downloadFilms.execute();
            }
        });


    }

    public  class DownloadFilms extends AsyncTask<String, Void, ArrayList<MyFilms>> {

        private String URL = "http://www.omdbapi.com/?s="+internetSearch.getText().toString()+"&type=movie";

        ArrayList<MyFilms> movie = new ArrayList<>();

        @Override
        protected ArrayList<MyFilms> doInBackground(String... params) {

            HttpURLConnection connection = null;
            String poster;

            BufferedReader reader;
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(String.format(URL));
                connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
//                    return "Error from server!";
                    return null;


                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();

                while(line != null){
                    builder.append(line);
                    line = reader.readLine();
                }


                JSONObject root = new JSONObject(builder.toString());
                JSONArray list = root.getJSONArray("Search");
                for (int i=0;i<list.length();i++){
                    JSONObject current = list.getJSONObject(i);
                    movie.add(new MyFilms(current.getString("Title"),current.getString("Poster")));
                }

                return movie;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null)
                    connection.disconnect();
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<MyFilms> resultJSON) {
            super.onPostExecute(resultJSON);

            adapter.clear();
            adapter.addAll(resultJSON);
            }
        }
    }