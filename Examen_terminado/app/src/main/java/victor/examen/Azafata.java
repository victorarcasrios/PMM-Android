package victor.examen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Azafata extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Picture picture = null;
        setContentView(picture = new Picture(this));
    }


    private class Picture extends View {

        Paint paint = null;
        Paint thin_paint = null;
        Paint letters_paint = null;
        Path path = null;
        Path clothes_path = null;

        public Picture(Context context) {
            super(context);
            context = context;
            paint = new Paint();
            paint.setStrokeWidth(15);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            thin_paint = new Paint();
            thin_paint.setStrokeWidth(8);
            thin_paint.setStyle(Paint.Style.STROKE);
            thin_paint.setColor(Color.BLUE);
            letters_paint = new Paint();
            letters_paint.setTextSize(40);
            letters_paint.setStrokeWidth(4);
            letters_paint.setStyle(Paint.Style.STROKE);
            letters_paint.setColor(Color.GREEN);
            path = new Path();
            clothes_path = new Path();
        }

        protected void onDraw(Canvas canvas){
            canvas.drawColor(Color.WHITE);
            canvas.drawCircle(canvas.getWidth()/2, (int)(canvas.getHeight()*0.25), 50, paint);

            // Ojos
            canvas.drawCircle(canvas.getWidth()/2-15, (float) (canvas.getHeight()*0.25-20), 5, paint);
            canvas.drawCircle(canvas.getWidth()/2+15, (float) (canvas.getHeight()*0.25-20), 5, paint);

            // Boca
            path.moveTo( canvas.getWidth()/2-20, (float) (canvas.getHeight()*0.25+20) );
            path.lineTo(canvas.getWidth()/2, (float) (canvas.getHeight()*0.25+40) );
            path.moveTo(canvas.getWidth()/2, (float) (canvas.getHeight()*0.25+40) );
            path.lineTo(canvas.getWidth()/2+20, (float) (canvas.getHeight()*0.25+20) );

            // Gorro
            clothes_path.moveTo( canvas.getWidth()/2-30, (float) (canvas.getHeight()*0.25-45) );
            clothes_path.lineTo( canvas.getWidth()/2, (float) (canvas.getHeight()*0.25-100) );
            clothes_path.moveTo( canvas.getWidth()/2, (float) (canvas.getHeight()*0.25-100) );
            clothes_path.lineTo( canvas.getWidth()/2+30, (float) (canvas.getHeight()*0.25-45));

            // Tronco
            clothes_path.moveTo( canvas.getWidth()/2-20, (float) (canvas.getHeight()*0.25+45) );
            clothes_path.lineTo( canvas.getWidth()/2-80, (float) (canvas.getHeight()*0.60) );
            clothes_path.moveTo( canvas.getWidth()/2-80, (float) (canvas.getHeight()*0.60) );
            clothes_path.lineTo( canvas.getWidth()/2+80, (float) (canvas.getHeight()*0.60) );
            clothes_path.moveTo( canvas.getWidth()/2+80, (float) (canvas.getHeight()*0.60) );
            clothes_path.lineTo( canvas.getWidth()/2+20, (float) (canvas.getHeight()*0.25+45) );

            // Brazos
            path.moveTo( canvas.getWidth()/2-30, (float) (canvas.getHeight()*0.25+50) );
            path.lineTo( canvas.getWidth()/2-85, (float) (canvas.getHeight()*0.52));
            path.moveTo( canvas.getWidth()/2+30, (float) (canvas.getHeight()*0.25+50) );
            path.lineTo( canvas.getWidth()/2+85, (float) (canvas.getHeight()*0.52));

            // Piernas
            path.moveTo( canvas.getWidth()/2-20, (float) (canvas.getHeight()*0.60) );
            path.lineTo( canvas.getWidth()/2-30, (float) (canvas.getHeight()*0.80) );
            path.moveTo( canvas.getWidth()/2+20, (float) (canvas.getHeight()*0.60) );
            path.lineTo( canvas.getWidth()/2+30, (float) (canvas.getHeight()*0.80) );

            canvas.drawPath(path, paint);
            canvas.drawPath(clothes_path, thin_paint);

            canvas.drawText("Creado por Víctor Arcas", (int)(canvas.getWidth()*0.05),
                    (int)(canvas.getHeight()*0.90), letters_paint);
        }
    }


}
