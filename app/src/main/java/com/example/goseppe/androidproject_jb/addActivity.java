package com.example.goseppe.androidproject_jb;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.id;

public class addActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addBtn, showBtn, cancelBtn;
    private EditText subjectET, bodyET, urlET;
    ImageView DownloadedImg;
    private String imgUrl, imgSting;
    private DBHelper myHelper;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        subjectET = (EditText) findViewById(R.id.editSubjectET);
        bodyET = (EditText) findViewById(R.id.manualBodyET);
        urlET = (EditText) findViewById(R.id.manualUrlET);

        myHelper = new DBHelper(this);
        database = myHelper.getWritableDatabase();
        DownloadedImg = (ImageView) findViewById(R.id.filmImg);

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        showBtn = (Button) findViewById(R.id.showBtn);
        showBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showBtn:
                imgUrl = ((EditText) findViewById(R.id.manualUrlET)).getText().toString();
                DownloadImgTask downloadImgTask = new DownloadImgTask();
                downloadImgTask.execute(imgUrl);
                break;
            case R.id.addBtn:
                String subject = subjectET.getText().toString();
                String body = bodyET.getText().toString();
                String url = urlET.getText().toString();
                String image = imgSting;
                myHelper.insertData(subject, body, url, image);
                Intent main = new Intent(addActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
            case R.id.cancelBtn:
                Intent cancelIntent = new Intent(addActivity.this, MainActivity.class);
                startActivity(cancelIntent);
                break;

        }
    }

    class DownloadImgTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(addActivity.this);
            dialog.setTitle("connecting");
            dialog.setMessage("please wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap= null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                // open a connection
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = (InputStream) url.getContent();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap downloadedImage) {

            DownloadedImg.setImageBitmap(downloadedImage);
            imgSting=encodeToBase64(downloadedImage, Bitmap.CompressFormat.JPEG, 100);

            dialog.dismiss();

        }
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

}