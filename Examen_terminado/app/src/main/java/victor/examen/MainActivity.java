package victor.examen;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, Spinner.OnItemSelectedListener{

    private Button submit;
    private RadioGroup tarifa = null;
    private CheckBox giftExtra = null;
    private CheckBox targetExtra = null;
    private EditText weight = null;
    private Spinner spinner = null;

    private int selected_destiny = 0;
    private Destiny[] destinies = new Destiny[]{
            new Destiny('A', "Asia y Oceanía", 30),
            new Destiny('B', "América y África", 20),
            new Destiny('C', "Europa", 10)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<Destiny> adapter = new ArrayAdapter<Destiny>(
                this, android.R.layout.simple_list_item_1, destinies);
        spinner = ((Spinner)findViewById(R.id.spinner));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tarifa = (RadioGroup)findViewById(R.id.tipo_tarifa);
        giftExtra = (CheckBox)findViewById(R.id.gift_extra);
        targetExtra = (CheckBox)findViewById(R.id.target_extra);
        weight = (EditText)findViewById(R.id.weight);

        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
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
        if (id == R.id.azafata) {
            Intent intent = new Intent(this, Azafata.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        boolean is_urgente = tarifa.getCheckedRadioButtonId() == R.id.urgente;
        boolean have_gift = giftExtra.isChecked();
        boolean have_target = giftExtra.isChecked();
        double weight = Double.parseDouble(String.valueOf(this.weight.getText()));

        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("bundle", bundle);

        bundle.putSerializable("destiny", destinies[selected_destiny]);
        bundle.putBoolean("is_urgente", is_urgente);
        bundle.putBoolean("have_gift", have_gift);
        bundle.putBoolean("have_target", have_target);
        bundle.putDouble("weight", weight);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_destiny = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
