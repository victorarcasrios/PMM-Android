package victor.solobici;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

/**
 * Created by victor on 17/01/15.
 */
public class VistaJuego extends View {

    //	COCHES	//
    private Vector<Grafico> Coches;	                //Vector con los Coches
    private int numCoches = 5;		                //Numero inicial de Coches
    private int numMotos = 3;		                //Fragmentos/Motos en que se dividir un Coche

    // BICI //
    private Grafico bici;
    private int giroBici;	                        //Incremento direccion de la bici
    private float aceleracionBici;                  //Aumento de velocidad en la bici
    private static final int PASO_GIRO_BICI = 5;
    private static final float PASO_ACELERACION_BICI = 0.5f;

    // THREAD Y TIEMPO //
    private HiloJuego hiloJuego;                    //Hilo encargado de procesar el tiempo
    private static int PERIODO_PROCESO = 50;        //Tiempo que debe transcurrir para procesar cambios (ms)
    private long ultimoProceso = 0;                 //Momento en el que se realiza el ultimo proceso

    // PANTALLA TÁCTIL //
    private float mX=0, mY=0;                       // las coordenadas del último evento.
    private boolean disparo=false;

    // RUEDA //
    private Grafico rueda;
    private static int VELOCIDAD_RUEDA = 12;
    private boolean ruedaActiva;
    private int distanciaRueda;

    // VARIABLES GLOBALES //
    private boolean corriendo = false;              //Controlar si la aplicación está en segundo plano
    private boolean pausa;                          //Controlar si la aplicación está en pausa

    public VistaJuego(Context contexto, AttributeSet atributos) {
        super(contexto, atributos);
        Drawable graficoBici, graficoCoche, graficoRueda;

        // Establecemos los recursos gráficos
        graficoCoche = contexto.getResources().getDrawable(R.drawable.coche);
        graficoBici = contexto.getResources().getDrawable(R.drawable.crazycatlady);
        graficoRueda = contexto.getResources().getDrawable(R.drawable.rainbowcat);

        // COCHES
        Coches = new Vector<Grafico>();
        for (int i=0; i<numCoches; i++) {
            Grafico coche = new Grafico(this, graficoCoche);
            coche.setIncX(Math.random() * 4 - 2);
            coche.setIncY(Math.random() * 4 - 2);
            coche.setAngulo((int) (Math.random() * 360));
            coche.setRotacion((int) (Math.random() * 8 - 4));
            Coches.add(coche);
        }

        // BICI
        bici = new Grafico(this, graficoBici);
        corriendo = true;

        // RUEDA
        rueda = new Grafico(this, graficoRueda);
        ruedaActiva = false;

    }

    // DIBUJO INICIAL
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // COCHES aleatoriamente dibujados
        for (Grafico coche: Coches) {
            do {
                coche.setPosX(Math.random()*(w-coche.getAncho()));
                coche.setPosY(Math.random()*(h-coche.getAlto()));
            } while (coche.distancia(bici) < (w+h)/5);
        }

        // BICI en el centro
        bici.setPosX((w-bici.getAncho())/2);
        bici.setPosY((h-bici.getAlto())/2);

        // Empezamos un nuevo juego
        hiloJuego = new HiloJuego();
        hiloJuego.start();
    }

    // Método que dibuja
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Grafico coche: Coches) {
            coche.dibujaGrafico(canvas);
        }

        bici.dibujaGrafico(canvas);

        if(ruedaActiva)
            rueda.dibujaGrafico(canvas);
    }

    protected synchronized void actualizaMovimiento() {
        long ahora = System.currentTimeMillis();
        // No hacemos nada si el período de proceso no se ha cumplido.
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
            return;
        }
        // Para una ejecución en tiempo real calculamos retardo
        double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
        // Actualizamos la posición de la bici
        bici.setAngulo((int) (bici.getAngulo() + giroBici * retardo));
        double nIncX = bici.getIncX() + aceleracionBici
                * Math.cos(Math.toRadians(bici.getAngulo())) * retardo;
        double nIncY = bici.getIncY() + aceleracionBici
                * Math.sin(Math.toRadians(bici.getAngulo())) * retardo;
        if (Grafico.distanciaE(0, 0, nIncX, nIncY) <= Grafico.getMaxVelocidad()) {
            bici.setIncX(nIncX);
            bici.setIncY(nIncY);
        }
        bici.incrementaPos();
        bici.setIncX(0);
        bici.setIncY(0);

        //Movemos los coches
        for (Grafico coche : Coches) {
            coche.incrementaPos();
        }
        ultimoProceso = ahora;

        if (ruedaActiva) { //Movemos la rueda
            rueda.incrementaPos();
            distanciaRueda--;
            if (distanciaRueda<0) {
                ruedaActiva = false;
            } else {
                for (int i=0; i<Coches.size(); i++) {
                    if (rueda.verificaColision(Coches.elementAt(i))) {
                        destruyeCoche(i);
                        i=Coches.size();
                        ruedaActiva=false;
                    }
                }
            }
        }

    }

    private class HiloJuego extends Thread {
        @Override
        public void run() {
            while (true) {
                while(corriendo) {
                    actualizaMovimiento();
                }
            }
        }
    }

    private void destruyeCoche(int i) {
        Coches.remove(i);
        ruedaActiva = false;
        //Activamos el sonido de explosión
        MediaPlayer miMediaPlayer =
                MediaPlayer.create(getContext(), R.raw.explosion);
        miMediaPlayer.start();
    }

    private void lanzarRueda() {
        rueda.setPosX(bici.getPosX() + bici.getAncho()/2 - rueda.getAncho()/2);
        rueda.setPosY(bici.getPosY() + bici.getAlto()/2 - rueda.getAlto()/2);
        rueda.setAngulo(bici.getAngulo());
        rueda.setIncX(Math.cos(Math.toRadians(rueda.getAngulo())) * VELOCIDAD_RUEDA);
        rueda.setIncY(Math.sin(Math.toRadians(rueda.getAngulo())) * VELOCIDAD_RUEDA);
        distanciaRueda = (int)Math.min(
                this.getWidth() / Math.abs(rueda.getIncX()),
                this.getHeight() / Math.abs(rueda.getIncY())) - 2;
        ruedaActiva = true;
    }

    public HiloJuego getHilo(){
        return hiloJuego;
    }

    // EVENT HANDLERS DE LA PANTALLA TACTIL

    @Override
    public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
        super.onKeyDown(codigoTecla, evento);
        //Procesamos la pulsación
        boolean pulsacion=true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionBici=+PASO_ACELERACION_BICI;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                giroBici=-PASO_GIRO_BICI;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroBici=+PASO_GIRO_BICI;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                lanzarRueda();
                break;
            default:
                //Si estamos aquí no hemos pulsado nada que nos interese
                pulsacion=false;
                break;
        }
        return pulsacion;
    }

    @Override
    public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
        super.onKeyUp(codigoTecla, evento);
        //Procesamos la pulsación
        boolean pulsacion=true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                //aceleracionBici=0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroBici=0;
                break;
            default:
                //Si estamos aquí, no hemos pulsado nada que interese
                pulsacion=false;
                break;
        }
        return pulsacion;
    }

    @Override
    public boolean onTouchEvent(MotionEvent evento) {
        super.onTouchEvent(evento);
        //Obtenemos la posición de la pulsación
        float x=evento.getX();
        float y=evento.getY();
        switch (evento.getAction()) {
            //Al comenzar pulsación (ACTION_DOWN) se activa la variable disparo
            case MotionEvent.ACTION_DOWN:
                disparo=true;
                break;
            //Comprobar pulsación continuada con desplazamiento hor/ver.
            //Si es asi, desactivamos disparo: se tratará de un movimiento
            //se trata de un movimiento en vez de  un disparo.
            case MotionEvent.ACTION_MOVE:
                float dx=Math.abs(x-mX);
                float dy=Math.abs(y-mY);
                //Un desplazamiento del dedo horizontal hace girar la bici.
                if (dy<6 && dx>6)
                {
                    giroBici = Math.round((x-mX)/2);
                    disparo = false;
                } else //Un desplazamiento vertical produce una aceleración.
                    if (dx<6 && dy>6)
                    {
                        aceleracionBici = Math.round((mY-y)/25);
                        disparo = false;
                    }
                break;
            //Si se levanta el dedo (ACTION_UP) sin haberse producido desplazamiento horizontal o vertical
            //disparo estará activado y lo que hacemos es disparar
            case MotionEvent.ACTION_UP:
                giroBici = 0;
                aceleracionBici = 0;
                if (disparo){
                    lanzarRueda();
                }
                break;
        }
        mX=x; mY=y;
        return true;
    }

    // SETTERS

    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }
}