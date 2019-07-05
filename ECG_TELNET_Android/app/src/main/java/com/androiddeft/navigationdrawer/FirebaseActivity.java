package com.androiddeft.navigationdrawer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


//ajouter un nouveau personne au base de donnée
public class FirebaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    // Creation ImageView.
    ImageView SelectImage;
    Uri FilePathUri;

    private static int RESULT_LOAD_IMAGE = 1;

    int Image_Request_Code = 7;


//declarer les champs
    EditText name,pname,email,date,num,rmq,refe;
    DatePickerDialog datePickerDialog;
    TextView sup;
    Spinner spinner1;
    CheckBox First,Second;

    private ListView lv;

    private Details dataModel;
    private Bitmap bp;
    private byte[] photo;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New ECG PERSON");
        name = (EditText) findViewById(R.id.nom);
        pname = (EditText) findViewById(R.id.prenom);
        email = (EditText) findViewById(R.id.email);
        num = (EditText) findViewById(R.id.mobile);
        rmq = (EditText) findViewById(R.id.remarque);
        refe = (EditText) findViewById(R.id.ref);
        date = (EditText) findViewById(R.id.datee);
        sup = (TextView) findViewById(R.id.sup);
        spinner1 = (Spinner) findViewById(R.id.spin);
        First = (CheckBox)findViewById(R.id.m);
        Second = (CheckBox)findViewById(R.id.mme);
        SelectImage = (ImageView) findViewById(R.id.img);

        db = new DBHelper(this);



        //sélectionner une image en cliquant sur imageview
        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Créer intent.
                Intent intent = new Intent();

                // Définition du type d’intention en tant qu’image pour sélectionner une image dans la mémoire du téléphone.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);


            }


            public String GetFileExtension(Uri uri) {

                ContentResolver contentResolver = getContentResolver();

                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

                // Renvoyer le fichier Extension.
                return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

            }





        });


        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                String m="";
                if(First.isChecked()){
                    m=First.getText().toString();
                }else{
                    m=Second.getText().toString();

                }



                    String ename=name.getText().toString();
                    String epname=pname.getText().toString();
                    String eemail=email.getText().toString();
                    Integer eenum= Integer.valueOf(num.getText().toString());
                    String ermq=rmq.getText().toString();
                    Integer eref= Integer.valueOf(refe.getText().toString());
                    String edate=date.getText().toString();
                    String rooms = spinner1.getSelectedItem().toString();//nationality
                    byte[] n=imageViewToByte(SelectImage);






                db.addContacts(new Details(ename,epname,m, edate,eemail,eenum,rooms,ermq,eref,n));
                Toast.makeText(getApplicationContext(),"Person_ECG_ADDED",Toast.LENGTH_LONG).show();



                Intent newAct = new Intent(getApplicationContext(), SelectedListActivity.class);
                startActivity(newAct);




            }

            public byte[] imageViewToByte(ImageView selectImage) {
                Bitmap bitmap = ((BitmapDrawable)selectImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }







            //Convertissez et redimensionnez votre image à 400 dp pour un téléchargement plus rapide de vos images vers une base de données.
            protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

                try {

                    // Décoder la taille de l'image
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

                    // La nouvelle taille à laquelle nous voulons évoluer
                     // final int REQUIRED_SIZE = size;

                     // Trouver la valeur d'échelle correcte. Ce devrait être le pouvoir de 2.
                    int width_tmp = o.outWidth, height_tmp = o.outHeight;
                    int scale = 1;
                    while (true) {
                        if (width_tmp / 2 < REQUIRED_SIZE
                                || height_tmp / 2 < REQUIRED_SIZE) {
                            break;
                        }
                        width_tmp /= 2;
                        height_tmp /= 2;
                        scale *= 2;
                    }

                    // Décoder avec inSampleSize
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }



        });




        // element spinner
        Spinner spinner = (Spinner) findViewById(R.id.spin);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Tunisie");
        categories.add("France");
        categories.add("Russie");
        categories.add("Belgique");
        categories.add("Spain");
        categories.add("Argentine");

        // Creation adapteur spiner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);


        // Style de présentation déroulant - affichage de liste avec radio buttom
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attacher l'adaptateur de données à spinner
        spinner.setAdapter(dataAdapter);



        // initie le sélecteur de date et un bouton
        date = (EditText) findViewById(R.id.datee);
        //effectuer un événement de clic sur le texte modifié
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // instance de la classe calendar et récupère la date actuelle, le mois et l'année du calendrier
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // année actuelle
                int mMonth = c.get(Calendar.MONTH); // mois actuelle
                int mDay = c.get(Calendar.DAY_OF_MONTH); // jour actuelle
                // dialogue sélecteur de date
                datePickerDialog = new DatePickerDialog(FirebaseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Sur la sélection d'un élément tournant
        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        FirebaseActivity.super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Obtenir l'image sélectionnée dans Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Configuration de l'image sélectionnée bitmap dans ImageView.
                SelectImage.setImageBitmap(bitmap);

                // Après avoir sélectionné l'image, choisissez le bouton au-dessus du texte.
                 // ChooseButton.setText ("Image sélectionnée");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }



}
