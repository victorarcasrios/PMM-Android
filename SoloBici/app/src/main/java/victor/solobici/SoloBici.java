package victor.solobici;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class SoloBici extends ActionBarActivity {


    private Button bAcercaDe;
    private Button bJuego;
    private Button bSalir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_bici);

        // BOTON JUEGO
        bJuego = (Button) findViewById(R.id.Boton01);
        bJuego.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lanzarJuego();
            }
        });

        // BOTON CONFIGURAR
        ((Button)findViewById(R.id.Boton02)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarConfiguracion();
            }
        });

        // BOTON ACERCA DE
        bAcercaDe = (Button) findViewById(R.id.Boton03);
        bAcercaDe.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lanzarAcercaDe();
            }
        });

        // BOTON SALIR
        bSalir = (Button) findViewById(R.id.Boton04);
        bSalir.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lanzarSalir();
            }
        });
    }

    //Metodo que activa la pantalla Juego
    public void lanzarJuego(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }

    private void lanzarConfiguracion(){
        Intent i = new Intent(this, EjemploPreferencias.class);
        startActivity(i);
    }

    //Metodo que activa la pantalla AcercaDe
    public void lanzarAcercaDe(){
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }


    //Metodo que activa la pantalla AcercaDe
    public void lanzarSalir(){
        finish();
    }
}
