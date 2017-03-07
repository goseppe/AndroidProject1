package com.example.goseppe.androidproject_jb;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1, textView2, textView3, subjectDisplay, bodyDisplay, urlDisplay;
    EditText subjectET, bodyET, urlET;
    ImageView DownloadedImg;
    private int id;
    private String imgString1;
    private DBHelper myHelper;
    private Button BtEdit1, BtEdit2, BtEdit3, BtCancel, BTcancel1, BTcancel2, BTcancel3, saveBtn;
    private SimpleCursorAdapter myAdapter;
    ViewSwitcher switcher, switcher2, switcher3;
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
        subjectET = (EditText)findViewById(R.id.switchSubjectET);
        bodyET = (EditText)findViewById(R.id.switchBodyET);
        urlET = (EditText)findViewById(R.id.switchUrlET);


        id = getIntent().getIntExtra("_id",-1);
        myHelper= new DBHelper(this);

        cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME , null, DBConstants.COLUMN_ID + "=?", new String[]{"" + id}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        subjectDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_SUBJECT)));
        bodyDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_BODY)));
        urlDisplay.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_URL)));

        BtEdit1 = (Button)findViewById(R.id.BtEdit1);
        BtEdit1.setOnClickListener(this);
        BtEdit2 = (Button)findViewById(R.id.BtEdit2);
        BtEdit2.setOnClickListener(this);
        BtEdit3 = (Button)findViewById(R.id.BtEdit3);
        BtEdit3.setOnClickListener(this);
        BtCancel = (Button)findViewById(R.id.cancelBtn);
        BtCancel.setOnClickListener(this);
        BTcancel1 = (Button)findViewById(R.id.BtCancel1);
        BTcancel1.setOnClickListener(this);
        BTcancel1.setVisibility(View.INVISIBLE);
        BTcancel2 = (Button)findViewById(R.id.BtCancel2);
        BTcancel2.setOnClickListener(this);
        BTcancel2.setVisibility(View.INVISIBLE);
        BTcancel3 = (Button)findViewById(R.id.BtCancel3);
        BTcancel3.setOnClickListener(this);
        BTcancel3.setVisibility(View.INVISIBLE);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.BtEdit1:
                switcher = (ViewSwitcher)findViewById(R.id.my_switcher);
                switcher.showNext();
                subjectET.setText(subjectDisplay.getText());
                BtEdit1.setVisibility(View.INVISIBLE);
                BTcancel1.setVisibility(View.VISIBLE);
                break;
            case R.id.BtEdit2:
                switcher2 = (ViewSwitcher)findViewById(R.id.my_switcher2);
                switcher2.showNext();
                bodyET = (EditText)switcher2.findViewById(R.id.switchBodyET);
                bodyET.setText(subjectDisplay.getText());
                BtEdit2.setVisibility(View.INVISIBLE);
                BTcancel2.setVisibility(View.VISIBLE);
                break;
            case R.id.BtEdit3:
                switcher3 = (ViewSwitcher)findViewById(R.id.my_switcher3);
                switcher3.showNext();
                urlET = (EditText)switcher3.findViewById(R.id.switchUrlET);
                BtEdit3.setVisibility(View.INVISIBLE);
                BTcancel3.setVisibility(View.VISIBLE);
                break;
            case R.id.BtCancel1:
                switcher.showPrevious();
                BtEdit1.setVisibility(View.VISIBLE);
                BTcancel1.setVisibility(View.INVISIBLE);
                break;

            case R.id.BtCancel2:
                switcher.showPrevious();
                BtEdit2.setVisibility(View.VISIBLE);
                BTcancel2.setVisibility(View.INVISIBLE);
                break;
            case R.id.BtCancel3:
                switcher.showPrevious();
                BtEdit3.setVisibility(View.VISIBLE);
                BTcancel3.setVisibility(View.INVISIBLE);
                break;

            case R.id.saveBtn:
                String subject = subjectET.getText().toString();
                String body = bodyET.getText().toString();
                String url = urlET.getText().toString();

                // DisplayActivity.DownloadImgTask downloadImgTask = new DisplayActivity.DownloadImgTask();
               // downloadImgTask.execute(url);

               //String image = imgString1;

                myHelper.updateData(id, subject, body, url);
                Intent main = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(main);
                break;
            case R.id.cancelBtn:
                Intent cancelIntent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(cancelIntent);
                break;

        }
    }
    class DownloadImgTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DisplayActivity.this);
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
            imgString1=DBConstants.encodeToBase64(downloadedImage, Bitmap.CompressFormat.JPEG, 100);

            dialog.dismiss();

        }
    }




}
