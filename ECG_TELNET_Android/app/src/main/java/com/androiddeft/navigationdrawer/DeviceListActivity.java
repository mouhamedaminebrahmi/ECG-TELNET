package com.androiddeft.navigationdrawer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Set;


public class DeviceListActivity extends AppCompatActivity {
    // Débogage pour LOGCAT
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;
    Dialog myDialog;
  
    //  textview pour l'état de la connexion

    TextView textView1,info;
    
    // EXTRA chaîne à envoyer à la secondactivity

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Champs membres
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
/*
représente un emplacement spécifique dans votre base de données
et peut être utilisé pour lire ou écrire des données dans cet emplacement de base de données.
 */

//référencer la racine ou l'emplacement enfant dans votre base de données
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);
        myDialog = new Dialog(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("test");


    }
    
    @Override
    public void onResume() 
    {
    	super.onResume();
    	//*************** 
    	checkBTState();

    	textView1 = (TextView) findViewById(R.id.connecting);
    	textView1.setTextSize(40);
    	textView1.setText(" ");

        // Initialise l'adaptateur de matrice pour les périphériques couplés
    	mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name,R.id.txtt);

        // Rechercher et configurer le ListView pour les appareils jumelés
    	ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
    	pairedListView.setAdapter(mPairedDevicesArrayAdapter);
    	pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Obtenir l'adaptateur Bluetooth local
    	mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Obtient un ensemble de périphériques actuellement jumelés et les ajoute à 'pairedDevices'
    	Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // Ajoute des périphériques précédemment associés à la matrice
    	if (pairedDevices.size() > 0) {
    		findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
    		for (BluetoothDevice device : pairedDevices) {
    			mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
    		}
    	} else {
    		String noDevices = getResources().getText(R.string.none_paired).toString();
    		mPairedDevicesArrayAdapter.add(noDevices);
    	}
  }


    // Configurer un écouteur sur la liste pour la liste (surnommé ceci - incertain)

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, final View v, int arg2, long arg3) {
            Button go;
            TextView txtclosee;
            //afficher dialog box
            myDialog.setContentView(R.layout.firebise_delete);


            go =(Button) myDialog.findViewById(R.id.go);
            txtclosee =(TextView) myDialog.findViewById(R.id.txtclosee);
            //close dialog box
            txtclosee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();

                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();

            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();      	//textView1.setText("Connecting...");
go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //vérifier si la base de données et vide
                if (snapshot.hasChild("test")) {
                    ref.child("test").removeValue();
                    //supprimer le champs test
                    ref = FirebaseDatabase.getInstance().getReference()
                            .child("test");
                    ref.removeValue();

                }else{


                    //afficher box de confirmation de suppression avec une image
                    Toast toast = Toast.makeText(getApplicationContext(), "No ecg records !!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    LinearLayout toastContentView = (LinearLayout) toast.getView();
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setImageResource(R.drawable.w);
                    toastContentView.addView(imageView, 0);
                    toast.show();

                    //afficher un ProgressDialog pour 10 seconde le temps la connection avec BT module établie
                    final int SECONDS = 10*1000;
                    final ProgressDialog dlg = new ProgressDialog(DeviceListActivity.this);
                    dlg.setMessage("connecting...");
                    dlg.setCancelable(false);
                    dlg.show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            dlg.dismiss();
                        }
                    }, SECONDS);
                    // Obtenir l'adresse MAC du périphérique, qui correspond aux 17 derniers caractères de la vue

                    TextView selectedText=(TextView) v.findViewById(R.id.txtt);

                    String info = selectedText.getText().toString();
                    String address = info.substring(info.length() - 17);

                    // Intention de commencer l'activité suivante en prenant un extra qui est l'adresse MAC.
                    Intent i = new Intent(DeviceListActivity.this, SecondnActivity.class);
                    i.putExtra(EXTRA_DEVICE_ADDRESS, address);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


















    }
});

        }
    };

    private void checkBTState() {
        // Vérifier que le périphérique a Bluetooth et qu'il est allumé
    	 mBtAdapter= BluetoothAdapter.getDefaultAdapter(); // VÉRIFIEZ QUE CELA FONCTIONNE !!!
        if(mBtAdapter==null) { 
        	Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
          if (mBtAdapter.isEnabled()) {
            Log.d(TAG, "...Bluetooth ON...");
          } else {
              // invite l'utilisateur à activer Bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
 
            }
          }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gonfle le menu; cela ajoute des éléments à la barre d’action si elle est présente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gérer les éléments de la barre d'action en cliquant ici. La barre d'action sera
        // gère automatiquement les clics sur le bouton Accueil / Haut, si longtemps
        // lorsque vous spécifiez une activité parent dans AndroidManifest.xml.
        int id = item.getItemId();

        //supprimer firebase depuis bare action
        if (id == R.id.my_action) {

            //Obtient un DatabaseReference pour le chemin fourni.
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild("test")) {
                        ref.child("test").removeValue();
                        //supprimer le champs test
                        ref = FirebaseDatabase.getInstance().getReference()
                                .child("test");
                        ref.removeValue();

                    }else{


                        //afficher box de confirmation de suppression avec une image
                        Toast toast = Toast.makeText(getApplicationContext(), "No ecg records !!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastContentView = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.w);
                        toastContentView.addView(imageView, 0);
                        toast.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}