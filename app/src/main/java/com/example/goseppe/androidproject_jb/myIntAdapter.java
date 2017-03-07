package com.example.goseppe.androidproject_jb;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by goseppe on 3/3/2017.
 */

public class myIntAdapter extends ArrayAdapter<Film> {

    private TextView subject;
    private ImageView image;

    public myIntAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.films_item, null);

        subject = (TextView) convertView.findViewById(R.id.SubjectTV);
        image = (ImageView) convertView.findViewById(R.id.listIV);

        Film film = getItem(position);
        subject.setText(film.getSubject());
        setImage(film);

        return convertView;
    }


    private void setImage(Film film) {
        if (film.getImage().equals("N/A")){
            image.setImageResource(R.drawable.na);
        }
        else
            Picasso.with(getContext()).load(film.getImage()).fit().centerInside().into(image);

    }
}