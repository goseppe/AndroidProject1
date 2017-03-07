package com.example.goseppe.androidproject_jb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, subjectDisplay, bodyDisplay, urlDisplay;
    ImageView DownloadedImg;
    private int id;
    private String imgString1;
    private DBHelper myHelper;
    private Button EditBtn, ReturnBtn;
    private SimpleCursorAdapter myAdapter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        textView1= (TextView)findViewById(R.id.TextView1);
        textView2= (TextView)findViewById(R.id.TextView2);
        textView3= (TextView)findViewById(R.id.TextView3);
        subjectDisplay= (TextView)findViewById(R.id.subjectDisplayTV);
        bodyDisplay= (TextView)findViewById(R.id.bodyDisplayTV);
        urlDisplay= (TextView)findViewById(R.id.urlDisplayTV);


        id = getIntent().getIntExtra("_id",-1);
        myHelper= new DBHelper(this);

        cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME , null, DBConstants.COLUMN_ID + "=?", new String[]{"" + id}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        subjectDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_SUBJECT)));
        bodyDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_BODY)));
        urlDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_URL)));


        EditBtn = (Button)findViewById(R.id.editBtn);
        ReturnBtn = (Button)findViewById(R.id.returnBtn);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(DisplayActivity.this, EditActivity.class);
                startActivity(cancelIntent);
            }
        });
        ReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(cancelIntent);
            }
        });
    }


        /*    case R.id.saveBtn:
                EditText subjectET = (EditText)findViewById(R.id.switchSubjectET);
                String subject = subjectET.getText().toString();

                EditText bodyET = (EditText)findViewById(R.id.switchBodyET);
                String body = bodyET.getText().toString();

                EditText urlET = (EditText)findViewById(R.id.switchUrlET);
                String url = urlET.getText().toString();

                DisplayActivity.DownloadImgTask downloadImgTask = new DisplayActivity.DownloadImgTask();
                downloadImgTask.execute(url);

               // String image = imgString1;

                myHelper.updateData(id, subject, body, url, imgString1);
                Intent main = new Intent(DisplayActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;   */

    }

