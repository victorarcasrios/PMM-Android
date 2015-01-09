package com.example.victorarcasrios.drawmethis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements Spinner.OnItemSelectedListener, View.OnClickListener{

    final private String[] shapes = {"Círculo", "Rectángulo"};
    private char selectedShape = 'C';
    private Spinner spinner = null;
    private EditText firstEditText = null;
    private EditText secondEditText = null;
    private Button drawIt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);
        firstEditText = (EditText)findViewById(R.id.firstEditText);
        secondEditText = (EditText)findViewById(R.id.secondEditText);
        drawIt = (Button)findViewById(R.id.drawIt);

        spinner.setOnItemSelectedListener(this);
        drawIt.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, shapes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        drawIt.setVisibility(View.VISIBLE);
        switch(selectedShape = shapes[i].charAt(0)){
            case 'C':
                circleForm();
                break;

            case 'R':
                rectForm();
                break;
        }
    }

    public void circleForm(){
        firstEditText.setHint(R.string.enter_circle_radio);
        firstEditText.setVisibility(View.VISIBLE);
        secondEditText.setVisibility(View.INVISIBLE);
    }

    private void rectForm() {
        firstEditText.setHint(R.string.enter_rectangle_base);
        secondEditText.setHint(R.string.enter_rectangle_height);
        firstEditText.setVisibility(View.VISIBLE);
        secondEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(isEmpty(firstEditText))
            return;

        Intent intent = new Intent(this, SecondActivity.class);
        switch(selectedShape){
            case 'C':
                intent.putExtra("figure",
                        new Circle(Integer.parseInt(firstEditText.getText().toString())));
                break;
            case 'R':
                if(isEmpty(secondEditText))
                    return;

                intent.putExtra("figure", new Rectangle(
                        Integer.parseInt(firstEditText.getText().toString()),
                        Integer.parseInt(secondEditText.getText().toString())
                ));
                break;
        }
        startActivity(intent);
    }

    public void showRequiredValuesToast(){
        Toast.makeText(this, "Todos los valores pedidos son obligatorios", Toast.LENGTH_LONG).show();
    }

    public boolean isEmpty(EditText editText){
       boolean isEmpty = editText.getText().toString().matches("");
            if(isEmpty)
                showRequiredValuesToast();
       return isEmpty;
    }
}
