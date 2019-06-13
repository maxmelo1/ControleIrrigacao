package br.edu.ifms.controleirrigacao;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.TextView;

import br.edu.ifms.controleirrigacao.threads.BlueToothConnection;

public class MainActivity extends AppCompatActivity {

    private TextView labelBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelBluetooth = (TextView) findViewById(R.id.lblBluetothEstado);

        BlueToothConnection bc = new BlueToothConnection();

        bc.start();


        /* Um descanso rápido, para evitar bugs esquisitos.
         */
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }

        labelBluetooth.setText("conectado!");


    }
}
