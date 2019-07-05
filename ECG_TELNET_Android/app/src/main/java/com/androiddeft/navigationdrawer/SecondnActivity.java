package com.androiddeft.navigationdrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class SecondnActivity extends Activity {
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

  TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
  TextView t1,t2;
  Handler bluetoothIn;
Button b0,b1;
  final int handlerState = 0;        				 //utilisé pour identifier le message du gestionnaire
  private BluetoothAdapter btAdapter = null;
  private BluetoothSocket btSocket = null;
  private StringBuilder recDataString = new StringBuilder();
   
  private ConnectedThread mConnectedThread;
    
  // Service SPP UUID - cela devrait fonctionner pour la plupart des appareils
  private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  
  // Chaîne pour l'adresse MAC
  private static String address;

    FirebaseDatabase database;
    DatabaseReference ref;

@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_second);
//AFFICHER un progressdialog de 10 second pour attendre l'envoie de signal
    final int SECONDS = 10*1000;
    final ProgressDialog dlg = new ProgressDialog(this);
    dlg.setMessage("Waiting until signal proceced...");
    dlg.setCancelable(false);
    dlg.show();
    new Handler().postDelayed(new Runnable() {
        public void run() {
            dlg.dismiss();
        }
    }, SECONDS);



    //initialiser firebase
    database = FirebaseDatabase.getInstance();
    ref = database.getReference("test");
    //afficher les valeurs de signal dans un graph view
    GraphView graph = (GraphView) findViewById(R.id.graph);
    // donnée
    series = new LineGraphSeries<DataPoint>();
    graph.addSeries(series);
    // personnaliser une petite fenêtre
    Viewport viewport = graph.getViewport();
    viewport.setYAxisBoundsManual(true);
    viewport.setMinY(-100);
    viewport.setMaxY(1000);


    viewport.setScalableY(false);

    viewport.setScrollableY(false);


    //définir manuellement les limites y
    viewport.setXAxisBoundsManual(true);
    viewport.setMaxX(50);
    viewport.setMinX(-50);




    txtString = (TextView) findViewById(R.id.txtString);
    txtStringLength = (TextView) findViewById(R.id.testView1);


    sensorView0 = (Button) findViewById(R.id.sensorView0);
    b1 = (Button) findViewById(R.id.b2);



    bluetoothIn = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == handlerState) {										//si le message est ce que nous voulons
            	String readMessage = (String) msg.obj;                                                                // msg.arg1 = octets du thread de connexion
                recDataString.append(readMessage);      								//continuer à ajouter à la chaîne jusqu'à ce que ~
                int endOfLineIndex = recDataString.indexOf("~");                    // déterminer la fin de ligne
                if (endOfLineIndex > 0) {                                           // assurez-vous qu'il y a des données avant ~
                    String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extrait de la chaîne


                    if (recDataString.charAt(0) == '#')			//si cela commence par # nous savons que c’est ce que nous recherchons
                    {
                    	final String sensor0 = recDataString.substring(1, endOfLineIndex);             //obtenir la valeur du capteur de la chaîne entre les indices 1-5


                        if (sensor0=="0~"){


                            Intent intent= getIntent();
                            finish();
                            startActivity(intent);

                        }else {


                            sensorView0.setText(sensor0);    //mettre à jour les textviews avec les valeurs du capteur
                            series.appendData(new DataPoint(lastX++, Double.parseDouble(sensorView0.getText().toString())), true, 100);

                            //afficher le graph avec les valeurs recues
                            series.setTitle("Ecg Signal");
                            series.setColor(Color.RED);

                            //envoyer les valeurs vers Firebase
                            String userId = ref.push().getKey();
                            ref.child(userId).setValue(Double.parseDouble(sensorView0.getText().toString()));

                        }
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i =new Intent(getApplicationContext(),WebviewPREDICTActivity.class);
       startActivity(i);

    }
});





                    }
                    recDataString.delete(0, recDataString.length()); 					//effacer toutes les données de chaîne

                }


            }
        }
    };
      
    btAdapter = BluetoothAdapter.getDefaultAdapter();       // obtenir l'adaptateur Bluetooth
    checkBTState();





}


   
  private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
      
      return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
      //crée une connexion sortante sécurisée avec un périphérique BT à l'aide de l'UUID
  }
    
  @Override
  public void onResume() {
    super.onResume();
    
    //Obtenir l'adresse MAC de DeviceListActivity via intent
    Intent intent = getIntent();
    
    //Obtenir l'adresse MAC de DeviceListActivty via EXTRA
    address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

    //créer un appareil et définir l'adresse MAC
    BluetoothDevice device = btAdapter.getRemoteDevice(address);


     
    try {
        btSocket = createBluetoothSocket(device);
    } catch (IOException e) {
    	Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
    }  
    // Établissez la connexion de prise Bluetooth.
    try 
    {
      btSocket.connect();
    } catch (IOException e) {
      try 
      {
        btSocket.close();
      } catch (IOException e2)
      {

      }
    } 
    mConnectedThread = new ConnectedThread(btSocket);
    mConnectedThread.start();
      // J'envoie un caractère lors de la reprise. Début de la transmission pour vérifier que le périphérique est connecté
      //   // Si ce n'est pas une exception sera levée dans la méthode write et finish () sera appelée
    mConnectedThread.write("x");
  }
  
  @Override
  public void onPause() 
  {
    super.onPause();
    try
    {
        // Ne laissez pas les prises Bluetooth ouvertes lorsque vous quittez une activité
      btSocket.close();
    } catch (IOException e2) {

    }
  }

    // Vérifie que le périphérique Android Bluetooth est disponible et demande à être activé s'il est désactivé
  private void checkBTState() {
 
    if(btAdapter==null) { 
    	Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
    } else {
      if (btAdapter.isEnabled()) {
      } else {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);
      }
    }
  }
  
  //// crée une nouvelle classe pour le fil de connexion
  private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

      // création du fil de connexion
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                // Créer des flux d'E / S pour la connexion
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
      
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        
      
        public void run() {
            byte[] buffer = new byte[256];  
            int bytes;

            // Reste en boucle pour écouter les messages reçus
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//// lire les octets du tampon d'entrée
                    String readMessage = new String(buffer, 0, bytes);
                    // Envoyer les octets obtenus à l'activité d'interface utilisateur via un gestionnaire
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget(); 
                } catch (IOException e) {
                    break;
                }
            }
        }
      // méthode d'écriture
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //convertir la chaîne entrée en octets
            try {
                mmOutStream.write(msgBuffer);                //écrire des octets sur une connexion BT via un flux sortant
            } catch (IOException e) {
            	//si vous ne pouvez pas écrire, fermez l'application
            	Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            	finish();
            	
              }
        	}
    	}
}
    
