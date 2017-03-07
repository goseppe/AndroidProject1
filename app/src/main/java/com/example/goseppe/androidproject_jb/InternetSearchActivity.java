package com.example.goseppe.androidproject_jb;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;


public class InternetSearchActivity extends AppCompatActivity {

    ArrayList<Film> myFilms;
    myIntAdapter adapter;
    ListView listView;
    EditText searchET;
    Button searchBtn;
    String nfilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        searchET =(EditText)findViewById(R.id.internetSearchET);
        searchBtn = (Button)findViewById(R.id.InternetSearchBtn);
        listView= (ListView) findViewById(R.id.myIntLV);
        myFilms = new ArrayList<Film>();
        adapter = new myIntAdapter(this, R.layout.films_item);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nfilm=searchET.getText().toString().replaceAll(" ", "+");
                Downloader d= new Downloader();
                d.execute();
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent sendToEdit = new Intent(InternetSearchActivity.this,addActivity.class);
                        sendToEdit.putExtra("_id", adapter.getItem(position).getId());
                        sendToEdit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(sendToEdit);
                    }
                });

            }
        });
    }


    public class Downloader extends AsyncTask<String ,Void, ArrayList<Film>>
    {
        private String URL = "http://www.omdbapi.com/?s="+nfilm+"&type=movie";

        @Override
        protected ArrayList<Film> doInBackground(String... params) {

            BufferedReader input = null;
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(String.format(URL));
                connection = (HttpURLConnection) url.openConnection();
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = input.readLine()) != null) {
                    response.append(line + "\n");
                }

                JSONObject obj = new JSONObject(response.toString());
                JSONArray list = obj.getJSONArray("Search");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject current = list.getJSONObject(i);
                    myFilms.add(new Film(current.getString("Title").toUpperCase(), current.getString("Poster")));
                }
                return myFilms;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
                return myFilms;

            }
        }
        @Override
        protected void onPostExecute(ArrayList<Film> jsonText) {
            super.onPostExecute(jsonText);


            adapter.clear();
            adapter.addAll(jsonText);


        }
        }
    }
