package com.example.goseppe.androidproject_jb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

   // private int id;
    private EditText subjectET, bodyET, urlET;
    private DBHelper myHelper;
    private Button UpdateBtn, CancelBtn;
    private int id;
    String subject, body, url;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id = getIntent().getIntExtra("_id", -1);
        myHelper = new DBHelper(this);
        //Intent myIntent = getIntent();
        //Film film1= myIntent.getParcelableExtra("myFilm");
        cursor = myHelper.getReadableDatabase().query(DBConstants.TABLE_NAME, null, DBConstants.COLUMN_ID + "=?", new String[]{"" + id}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        subjectET.setText (cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_SUBJECT)));
        bodyET.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_BODY)));
        urlET.setText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_URL)));


        subjectET = (EditText)findViewById(R.id.subjectET);
        subject = subjectET.getText().toString();
        bodyET = (EditText)findViewById(R.id.bodyET);
        body = bodyET.getText().toString();
        urlET = (EditText)findViewById(R.id.urlET);
        url = urlET.getText().toString();

        UpdateBtn = (Button)findViewById(R.id.updateBtn);
        UpdateBtn.setOnClickListener(this);
        CancelBtn = (Button)findViewById(R.id.cancelUpdateBtn);
        CancelBtn.setOnClickListener(this);

       }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateBtn:
               // myHelper.updateData(id, subject, body, url, image);
                Intent main = new Intent(EditActivity.this, MainActivity.class);
                startActivity(main);
                break;
            case R.id.cancelBtn:
                finish();
                break;
        }

    }
}