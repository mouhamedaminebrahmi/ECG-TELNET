package com.androiddeft.navigationdrawer;

import android.content.ClipData;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
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

//afficher profil
public class SelectedListActivity extends AppCompatActivity {

    ArrayList<Details> imageArry = new ArrayList<Details>();
    private ArrayList listItems = new ArrayList();
    dataAdapter adapter;
    List<Details> contacts;
    Details sm;

    EditText inputSearch;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LIST ECG PERSON RECORD");
        final DBHelper db = new DBHelper(this);


        String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID");
        Toast.makeText(getApplicationContext(),sessionId,Toast.LENGTH_LONG).show();

        inputSearch.setText(sessionId);

        contacts = db.getRefe();
        for (Details cn : contacts) {
            String log = "ID:" + cn.getId() + " Name: " + cn.getNom()
                    + " ,Image: " + cn.getImage();

            // log
            Log.d("Result: ", log);
            //add contacts data in arrayList
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
                Intent intent = new Intent(SelectedListActivity.this, ProfilActivity.class);



                ImageView image =(ImageView)findViewById(R.id.imgIcon);




                Drawable drawable=image.getDrawable();
                Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();



                intent.putExtra("picture", sm.getImage());
                intent.putExtra("name", sm.getNom());
                intent.putExtra("civilite", sm.getCivilite());
                intent.putExtra("email", sm.getEmail());
                intent.putExtra("age", sm.getDatee());
                intent.putExtra("number", sm.getNumber());
                intent.putExtra("natio", sm.getNationality());
                intent.putExtra("rmqq", sm.getRemarque());
                intent.putExtra("reff", sm.getReference());


                startActivity(intent);


            }
        });



        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long arg3) {

                Cursor cursor = null;
                cursor = (Cursor) arg0.getItemAtPosition(position);
                sm=adapter.getItem(position);

                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(SelectedListActivity.this);
                alertDialog.setTitle("Delete");


                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        db.deleteSelectedItem(sm.getId());
                        adapter.notifyDataSetChanged();
                        Intent intent= getIntent();
                        finish();
                        startActivity(intent);
                    }


                });

                alertDialog.show();
                return true;

            }

        });
    }}
