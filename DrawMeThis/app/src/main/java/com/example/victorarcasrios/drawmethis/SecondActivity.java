package com.example.victorarcasrios.drawmethis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class SecondActivity extends ActionBarActivity {

    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Picture picture = null;
        Intent intent = this.getIntent();
        Figure figure = intent.getParcelableExtra("figure");

        setContentView(picture = new Picture(this, figure));
    }

    public void drawRectangle(Rectangle rectangle){
        textView.setText(String.format(">>Rectángulo\nBase: %d\nAltura: %d\n",
                rectangle.getBase(), rectangle.getHeight()));
    }

    public void drawCircle(Circle circle){
        textView.setText(String.format(">>Círculo\nRadio: %d\n", circle.getRadius()));
    }

    private class Picture extends View {

        private Context context;
        private Figure figure;
        private Paint paint = new Paint();

        public Picture(Context context, Figure figure) {
            super(context);
            this.context = context;
            this.figure = figure;
            paint.setStrokeWidth(15);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            switch (figure.getType()) {
                case 'R':
                    drawRectangle(canvas);
                    break;
                case 'C':
                    drawCircle(canvas);
                    break;
            }
        }

        private void drawCircle(Canvas canvas){
            canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, ((Circle)figure).getRadius(), paint);
        }

        private void drawRectangle(Canvas canvas){
            Rectangle rectangle = (Rectangle)figure;
            canvas.drawRect(
                    (canvas.getWidth()/2)-(rectangle.getBase()/2),
                    (canvas.getHeight()/2)-(rectangle.getHeight()/2),
                    (canvas.getWidth()/2)+(rectangle.getBase()/2),
                    (canvas.getHeight()/2)+(rectangle.getHeight()/2),
                    paint);
        }
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
