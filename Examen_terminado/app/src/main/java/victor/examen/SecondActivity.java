package victor.examen;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends ActionBarActivity implements View.OnClickListener{

    private Destiny destiny = null;
    private boolean is_urgente;
    private boolean have_gift;
    private boolean have_target;
    private double weight;
    private double final_price;
    private double base_total;
    private boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        saved = false;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        destiny = (Destiny) bundle.getSerializable("destiny");
        is_urgente = bundle.getBoolean("is_urgente");
        have_gift = bundle.getBoolean("have_gift");
        have_target = bundle.getBoolean("have_target");
        weight = bundle.getDouble("weight");

        setImage(destiny.getArea());
        setResume();
    }

    private void setImage(char area){
        int id_src = R.drawable.paq_mundo1;
        switch (String.valueOf(area).toUpperCase().charAt(0) ){
            case 'A':
                id_src = R.drawable.asia_oceania;
                break;
            case 'B':
                id_src = R.drawable.america_africa;
                break;
            case 'C':
                id_src = R.drawable.europa;
                break;
        }
        ((ImageView)findViewById(R.id.image)).setImageResource(id_src);
    }

    private void setResume(){
        String output = String.format("Zona: %s (%s)\n Tarifa: %s\nPeso: %.2fKg\n\nDecoración: %s\nCOSTE FINAL: %s",
                destiny.getArea(), destiny.getContinent(),
                (is_urgente) ? "Urgente" : "Normal",
                weight,
                getDecoration(),
                getFinalPriceResume()
            );
        ((TextView)findViewById(R.id.output)).setText(output);
    }

    private String getDecoration(){
        if( have_gift && have_target )
            return "Con caja regalo y dedicatoria";
        else{
            if( have_gift )
                return "Con caja regalo";
            if( have_target )
                return "Con dedicatoria";
            return "";
        }
    }

    private String getFinalPriceResume(){
        calculateFinalPrice();
        return String.format("%.2f ( %.2f + ( %.2f * %.2f ) %s )",
                final_price, base_total, getKgPrice(), weight,
                (!is_urgente) ? "" : String.format(" + ( %.2f  * 0/3 )", base_total)
                );
    }

    private void calculateFinalPrice(){
        base_total = destiny.getPrice() + getKgPrice()*Math.round(weight);
        if( is_urgente )
            final_price = (base_total + (base_total * 0.3) );
        else
            final_price = base_total;
    }

    private Double getKgPrice(){
        if( weight > 5 && weight < 10)
            return 1.5;
        else if( weight >= 10 )
            return 2.0;
        else return 1.0;
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
        if (id == R.id.azafata) {
            Intent intent = new Intent(this, Azafata.class);
            startActivity(intent);
        }else if(id == R.id.save){
            save();
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(){
        if(saved){
            Toast.makeText(this, "¡Ya has registrado el envío!\nNo puede registrarlo otra vez", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db =openOrCreateDatabase("main_db", this.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS package(id INTEGER PRIMARY KEY AUTOINCREMENT, destiny VARCHAR(20), price DECIMAL(10,2));");
        db.execSQL( String.format("INSERT INTO package VALUES (NULL, '(%s) %s', '%.2f');",
                destiny.getArea(), destiny.getContinent(), final_price) );
        Toast.makeText(this, "Registro guardado", Toast.LENGTH_LONG).show();
        saved = true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Azafata.class);
        startActivity(intent);
    }
}
