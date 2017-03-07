package com.example.goseppe.androidproject_jb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by goseppe on 2/19/2017.
 */

public class Film implements Parcelable{

    private int id;
    private String subject;
    private String body;
    private String url;
    private String image;

    //empty constructor
    public Film(){}

    public Film(String subject, String image) {
        this.subject = subject;
        this.image = image;
    }

    public Film(String subject, String body, String image) {
        this.subject = subject;
        this.image = image;
    }

    public Film(int id, String subject, String body, String url, String image) {

        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.image = image;
    }

    protected Film(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        body = in.readString();
        url = in.readString();
        image = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return subject+"***";
    }

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
        dest.writeString(image);
    }
}
