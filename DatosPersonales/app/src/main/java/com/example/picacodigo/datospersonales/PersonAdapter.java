package com.example.picacodigo.datospersonales;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by victor on 12/11/14.
 */
public class PersonAdapter extends ArrayAdapter {

    private final Activity context;
    private final int resource;
    private final Person[]people;

    public PersonAdapter(Activity context, int resource, Person[]people) {
        super(context, resource, people);
        this.context = context;
        this.resource = resource;
        this.people = people;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(resource, null);

        TextView textView = ((TextView)view.findViewById(R.id.nameTextView));
        Person person = people[position];
        String name = person.getName();
        textView.setText(name);

        return view;
    }
}
