package com.example.victorarcasrios.trespantallas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class Actividad1 extends ActionBarActivity implements View.OnClickListener{

    private static final SparseIntArray REQUEST_CODES = new SparseIntArray(){{
        put(R.id.toSecondScreen, 2);
        put(R.id.toThirdScreen, 3);
    }};
    private static final SparseArray <Class> ACTIVITIES = new SparseArray<Class>(){{
        put(R.id.toSecondScreen, Actividad2.class);
        put(R.id.toThirdScreen, Actividad3.class);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);

        ((Button)findViewById(R.id.toSecondScreen)).setOnClickListener(this);
        ((Button)findViewById(R.id.toThirdScreen)).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad1, menu);
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
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, ACTIVITIES.get(id));
        startActivityForResult(intent, REQUEST_CODES.get(id));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        SparseArray<String> requestCode_ActivityName = new SparseArray<String>(){{
            put(2, getString(R.string.second_screen));
            put(3, getString(R.string.third_screen));
        }};

        if(resultCode == RESULT_OK){
            String returnMessage = String.format("%s, %s %s",
                    getString(R.string.first_screen),
                    getString(R.string.back_from),
                    requestCode_ActivityName.get(requestCode)
            );
            ((TextView)findViewById(R.id.firstScreenTV)).setText(returnMessage);
        }
    }
}