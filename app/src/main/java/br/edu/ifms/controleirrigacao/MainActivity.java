package br.edu.ifms.controleirrigacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

import br.edu.ifms.controleirrigacao.threads.BlueToothConnection;

public class MainActivity extends AppCompatActivity {

    private TextView labelBluetooth;
    private Button btnControl;
    private BlueToothConnection bc;
    private Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        state = false;

        setContentView(R.layout.activity_main);

        labelBluetooth    = findViewById(R.id.lblBluetothEstado);
        btnControl = findViewById(R.id.btnBluetooth);

        Log.i("MainActivity", "tentando conectar");

        bc = new BlueToothConnection();

        bc.start();


        /* Um descanso rápido, para evitar bugs esquisitos.
         */
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }

        if( bc.isConnected() )
            labelBluetooth.setText("conectado!");
        else
            labelBluetooth.setText("não conectado!");

        //irrigationControl.setShowText(true);
        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MainActivity", "botão clicado!");

                if(!state){
                    Log.i("MainActivity", "ativando irrigação!");
                    try {
                        bc.write("A".getBytes());

                        btnControl.setBackground(getResources().getDrawable(R.drawable.pause));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Log.i("MainActivity", "desativando irrigação!");
                    try {
                        bc.write("D".getBytes());

                        btnControl.setBackground(getResources().getDrawable(R.drawable.play));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                state = !state;
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();

        bc.cancel();
    }
}
