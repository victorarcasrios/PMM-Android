package com.example.victorarcasrios.drawmethis;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SecondActivity extends ActionBarActivity {

    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = (TextView)findViewById(R.id.textView);

        Intent intent = this.getIntent();
        Figure figure = intent.getParcelableExtra("figure");

        drawFigure(figure);
    }

    public void drawFigure(Figure figure){
        switch(figure.getType()){
            case 'R':
                drawRectangle((Rectangle)figure);
                break;
            case 'C':
                drawCircle((Circle)figure);
                break;
        }
    }

    public void drawRectangle(Rectangle rectangle){
        textView.setText(String.format(">>Rectángulo\nBase: %d\nAltura: %d\n",
                rectangle.getBase(), rectangle.getHeight()));
    }

    public void drawCircle(Circle circle){
        textView.setText(String.format(">>Círculo\nRadio: %d\n", circle.getRadius()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
}
