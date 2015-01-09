package com.example.picacodigo.datospersonales;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    boolean spinnerHasBeenUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Person[] people = {
                new Person("Victor", 22),
                new Person("Paco", 54),
                new Person("Adrian", 26),
                new Person("Agustin", 39)
        };

        spinnerHasBeenUsed = false;

        final Spinner select = (Spinner)findViewById(R.id.select);
        PersonAdapter adapter = new PersonAdapter(this, android.R.layout.simple_spinner_item, people);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        select.setAdapter(adapter);

        select.setOnItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!spinnerHasBeenUsed){
            spinnerHasBeenUsed = true;
            return;
        }

        Intent intent = new Intent(this, PersonalData.class);
        Bundle data = new Bundle();
        data.putString("name", parent.getItemAtPosition(position).toString());
        intent.putExtras(data);
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}