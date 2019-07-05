package com.androiddeft.navigationdrawer;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import android.content.Intent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


//liste des personnes enregistres
public class RecordListActivity extends AppCompatActivity {

    ArrayList<Details> imageArry = new ArrayList<Details>();
    private ArrayList listItems = new ArrayList();
    dataAdapter adapter;
    List<Details> contacts;
    Details sm;
    Dialog myDialog;

    EditText inputSearch;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        myDialog = new Dialog(this);


        final int SECONDS = 1*1000;
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setMessage("Loading...");
        dlg.setCancelable(false);
        dlg.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dlg.dismiss();
            }
        }, SECONDS);




        inputSearch = (EditText) findViewById(R.id.inputSearch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PROFILE");
        final DBHelper db = new DBHelper(this);



        String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID");
        Toast.makeText(getApplicationContext(),"Authentication Ref Number : " +sessionId,Toast.LENGTH_LONG).show();

        inputSearch.setText(sessionId);

        contacts = db.getAllContacts(sessionId);
        for (Details cn : contacts) {
            String log = "ID:" + cn.getId() + " Name: " + cn.getNom()
                    + " ,Image: " + cn.getImage();

            //  log
            Log.d("Result: ", log);
            // ajouter des données de contacts dans arrayList
            imageArry.add(cn);

        }

        for (Details cnn : contacts) {
            String log = "ID:" + cnn.getId() ;

            listItems.add(cnn);

        }
        adapter = new dataAdapter(this, R.layout.custom_list_layout, imageArry);
        final ListView dataList = (ListView) findViewById(R.id.list33);

        dataList.setAdapter(adapter);








        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = null;
                cursor = (Cursor) parent.getItemAtPosition(position);
                sm=adapter.getItem(position);
                 final Intent intent = new Intent(RecordListActivity.this, ProfilActivity.class);



               ImageView image =(ImageView)findViewById(R.id.imgIcon);




                Drawable drawable=image.getDrawable();
                Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();


                //obtenir les paramatres par intent
                intent.putExtra("picture", sm.getImage());
                intent.putExtra("name", sm.getNom());
                intent.putExtra("civilite", sm.getCivilite());
                intent.putExtra("email", sm.getEmail());
                intent.putExtra("age", sm.getDatee());
                intent.putExtra("number", sm.getNumber());
                intent.putExtra("natio", sm.getNationality());
                intent.putExtra("rmqq", sm.getRemarque());
                intent.putExtra("reff", sm.getReference());



                //classification des resultats de profil chaque fois apres le prédiction
                Button go;
                ImageView positive,negative;
                final TextView message,messagen,txtclosee;
                final int[] counter = {0};
                final int[] snowDensity = new int[1];
               //initialiser Préférences partagées
                final SharedPreferences[] sharedpreferences = new SharedPreferences[1];
                final SharedPreferences[] sharedpreferences1 = new SharedPreferences[1];
                final int[] savedCount = new int[1];
                final int[] savedCount1 = new int[1];
                final String mypreference = "mypref";
                final String mypreference1 = "mypref1";
                  final String Name = "nameKey";
                  final String Email = "emailKey";

                myDialog.setContentView(R.layout.show_profil);

                sharedpreferences[0] = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                sharedpreferences1[0] = getSharedPreferences(mypreference1,
                        Context.MODE_PRIVATE);

                go =(Button) myDialog.findViewById(R.id.go);
                positive =(ImageView) myDialog.findViewById(R.id.positive);
                negative =(ImageView) myDialog.findViewById(R.id.negative);
                message =(TextView) myDialog.findViewById(R.id.message);
                messagen =(TextView) myDialog.findViewById(R.id.messagen);
                txtclosee =(TextView) myDialog.findViewById(R.id.txtclosee);

                final SharedPreferences[] settings = {getSharedPreferences(mypreference, 0)};
                savedCount[0] = settings[0].getInt("SNOW_DENSITY", 0); //0 is the default value
                message.setText(String.valueOf(savedCount[0])+ " Positive Predictive person");
                //utiliser Préférences partagées pour stockées les classement positifs
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        settings[0] = getSharedPreferences(mypreference, 0);
                        SharedPreferences.Editor editor = settings[0].edit();
                        editor.putInt("SNOW_DENSITY", savedCount[0]+1);
                        editor.commit();

                        Toast toast = Toast.makeText(getApplicationContext(), "Positive predict added !!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastContentView = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.yes1);
                        toastContentView.addView(imageView, 0);
                        toast.show();



                    }
                });



                //obtenir les classements enregistrées
                final SharedPreferences[] settings1 = {getSharedPreferences(mypreference1, 0)};
                savedCount1[0] = settings1[0].getInt("NEGATIVE", 0); //0 est la valeur par default
                messagen.setText(String.valueOf(savedCount1[0])+ " Negative Predictive person");
                //utiliser Préférences partagées pour stockées les classement positifs
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        //enregistrer dans Préférences partagées
                        settings1[0] = getSharedPreferences(mypreference1, 0);
                        SharedPreferences.Editor editor = settings1[0].edit();
                        editor.putInt("NEGATIVE", savedCount1[0]+1);
                        editor.commit();

                        Toast toast = Toast.makeText(getApplicationContext(), "Negative predict added !!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastContentView = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.nn1);
                        toastContentView.addView(imageView, 0);
                        toast.show();

                    }
                });

                //fermer boite de dialog
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        startActivity(intent);
                    }
                });

                txtclosee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();

                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }
        });













    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//Every time an action button is clicked, return the identifier for that menu item//

        int id = item.getItemId();

//If the id is "my_action"//

        if (id == R.id.my_action) {

//Display a Toast//

            Intent i = new Intent(RecordListActivity.this, DeviceListActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
