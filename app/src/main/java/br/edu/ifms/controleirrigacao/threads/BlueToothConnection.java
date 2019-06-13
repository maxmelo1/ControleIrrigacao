package br.edu.ifms.controleirrigacao.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class BlueToothConnection extends Thread {

    private BluetoothAdapter mBluetoothAdapter;

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    private OutputStream os;

    private final String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private final String HC_UUID = "00:21:13:01:04:03";

    public BlueToothConnection(){

        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = mBluetoothAdapter.getRemoteDevice(HC_UUID);

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        } catch (IOException e) { }
        mmSocket = tmp;

    }


    @Override
    public void run() {

        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();

            os = mmSocket.getOutputStream();


        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);



    }

    public void write(byte b) throws IOException {
        os.write(b);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

}
