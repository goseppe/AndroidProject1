package com.example.goseppe.androidproject_jb;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by goseppe on 3/2/2017.
 */

public class Film implements Parcelable {

    private int id;
    private String subject;
    private String body;
    private String url;

    public Film(int id, String subject, String body, String url) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    protected Film(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        body = in.readString();
        url = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(body);
        dest.writeString(url);
    }
}
