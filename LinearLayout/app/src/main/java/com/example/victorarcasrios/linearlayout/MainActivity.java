package com.example.victorarcasrios.linearlayout;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Hashtable;


public class MainActivity extends ActionBarActivity {

    private HashMap colors = new HashMap<Integer, Integer>(){{
        put(R.id.redRB, Color.RED);
        put(R.id.greenRB, Color.GREEN);
        put(R.id.blueRB, Color.BLUE);
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void setColor(View view){
        TextView textView = (TextView)findViewById(R.id.textView);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();

        textView.setBackgroundColor((Integer) colors.get(id));
    }

    public void clearTextView(View view){
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setBackgroundColor(Color.WHITE);
    }
}
