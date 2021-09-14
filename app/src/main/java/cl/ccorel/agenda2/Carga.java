package cl.ccorel.agenda2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Carga extends AppCompatActivity {

    TextView app_name, desarrollador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga);

        //cambio de fuente
        String ubicacion = "fuentes/sans_medio.ttf";
        Typeface tf = Typeface.createFromAsset(Carga.this.getAssets(),ubicacion);

        //inicializando
        app_name = findViewById(R.id.app_name);
        desarrollador = findViewById(R.id.desarrollador);

        final int DURACION = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // codigo que se ejecutara
                Intent intent = new Intent(Carga.this, MainActivityAdministrador.class);
                startActivity(intent);
                finish();

            }
        },DURACION);

        app_name.setTypeface(tf);
        desarrollador.setTypeface(tf);

    }
}