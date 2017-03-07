package com.example.goseppe.androidproject_jb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by goseppe on 2/20/2017.
 */

public class DBConstants {

    public static final String TABLE_NAME = "filmslist";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_IMAGE = "image";


public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
        {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        }

public static Bitmap decodeBase64(String input)
        {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }
}


