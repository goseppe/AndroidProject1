package com.example.goseppe.androidproject_jb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper myHelper;
    private Cursor cursor;
    private Button AddBtn;
    private int pos;
    private ListView listView;
    private SimpleCursorAdapter myAdapter;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.myLV);
        myHelper = new DBHelper(this);
        cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME, null, null, null, null, null, null);
        database= myHelper.getWritableDatabase();

        String[] from = new String[]{DBConstants.COLUMN_SUBJECT};
        int[] to = new int[]{R.id.SubjectTV};

        myAdapter = new SimpleCursorAdapter(this, R.layout.films_item, cursor, from, to);
        listView.setAdapter(myAdapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos= position;
                cursor.moveToPosition(position);
                Intent displayIntent = new Intent(MainActivity.this, DisplayActivity.class);
                displayIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)) );
                //displayIntent.putExtra("position", position);
                startActivity(displayIntent);
            }
        });

        AddBtn = (Button)findViewById(R.id.AddBtn1);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder addBuilder = new AlertDialog.Builder(MainActivity.this);
                addBuilder.setTitle("ADD NEW FILM");
                addBuilder.setMessage("Do you want to add Manualy or Automatic film?");
                addBuilder.setNegativeButton("Manualy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, addActivity.class);
                        startActivity(intent);
                    }
                })
                        .setPositiveButton("Automatic", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, InternetSearchActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(true);
                AlertDialog addFilmDialog = addBuilder.create();
                addFilmDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eraseAll:
                AlertDialog.Builder deleteAllBuilder = new AlertDialog.Builder(MainActivity.this);
                deleteAllBuilder.setTitle("DELETE ALL ITEMS");
                deleteAllBuilder.setMessage("Are you sure you want to delete all the item");
                deleteAllBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myHelper.getWritableDatabase().delete(DBConstants.TABLE_NAME, null, null);
                                cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME, null, null, null, null, null, null);
                                myAdapter.swapCursor(cursor);

                            }
                        })
                        .setCancelable(true);
                AlertDialog deleteFilm = deleteAllBuilder.create();
                deleteFilm.show();
                break;
            case R.id.exitApp:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.contextEdit:
                cursor.moveToPosition(pos);
                Intent displayIntent = new Intent(MainActivity.this, DisplayActivity.class);
                displayIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)) );
                //displayIntent.putExtra("position", position);
                startActivity(displayIntent);

                break;
            case R.id.contextDelete:
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(MainActivity.this);
                deleteBuilder.setTitle("DELETE ITEM");
                deleteBuilder.setMessage("Are you sure you want to delete ");
                deleteBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myHelper.getWritableDatabase().delete(DBConstants.TABLE_NAME, "_id= ?", new String[]{"" + cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID))});
                                cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME, null, null, null, null, null, null);
                                myAdapter.swapCursor(cursor);
                            }
                        })
                        .setCancelable(true);
                AlertDialog deleteFilm = deleteBuilder.create();
                deleteFilm.show();

        }
        return super.onContextItemSelected(item);
    }
}