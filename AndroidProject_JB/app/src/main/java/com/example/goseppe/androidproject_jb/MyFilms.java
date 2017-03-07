package com.example.goseppe.androidproject_jb;

/**
 * Created by goseppe on 2/19/2017.
 */

public class MyFilms {

    private int id;
    private String subject;
    private String body;
    private String url;
    private String image;


    public MyFilms(int id, String subject, String body, String url, String image) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.image = image;
    }

    public MyFilms(String subject,String image) {
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.image = image;
    }

    @Override
    public String toString() {
        return subject;
    }
}
