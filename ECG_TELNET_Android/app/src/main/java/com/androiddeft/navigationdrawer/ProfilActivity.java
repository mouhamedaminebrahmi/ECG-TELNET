package com.androiddeft.navigationdrawer;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//afficher les details d'un personne
public class ProfilActivity extends Activity {



    /*
     * Cette variable permettra de conserver une référence sur le bouton de
     * l'interface
     */
    Dialog myDialog;
     Button b1;
     TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        myDialog = new Dialog(this);
        /*
         * Récupère une référence sur le bouton en utilisant son identifiant
         */
        Button b1=(Button)findViewById(R.id.button3);
        //récupérer l'image du profil
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        ImageView image = (ImageView) findViewById(R.id.photo);
        image.setImageBitmap(bmp);
        //récupere les intents
        String s1 = getIntent().getStringExtra("name");
        String s2 = getIntent().getStringExtra("civilite");
        String s3 = getIntent().getStringExtra("email");
        String s4 = getIntent().getStringExtra("age");



        TextView txt1=(TextView)findViewById(R.id.textView2);
        TextView txt2=(TextView)findViewById(R.id.age);
        TextView txt3=(TextView)findViewById(R.id.mail);
        TextView txt4=(TextView)findViewById(R.id.civi);


        txt1.setText(s1);
        txt2.setText(s4);
        txt3.setText(s3);
        txt4.setText(s2);




    }

    //afficher popup contient des informations suplémentaires sur un profil
    public void ShowPopup(View view) {

        TextView txtclose,txt5,txt6,txt7,txt8;


        myDialog.setContentView(R.layout.custompopup);

        txt5=(TextView) myDialog.findViewById(R.id.num);
        txt6=(TextView) myDialog.findViewById(R.id.natio);
        txt7=(TextView) myDialog.findViewById(R.id.rmq);
        txt8=(TextView) myDialog.findViewById(R.id.reff);

        int number = getIntent().getExtras().getInt("number");
        int number2 = getIntent().getExtras().getInt("reff");
        String s6 = getIntent().getStringExtra("natio");
        String s7 = getIntent().getStringExtra("rmqq");


        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        ImageView image = (ImageView) myDialog.findViewById(R.id.picc);
        image.setImageBitmap(bmp);

        txt8.setText(String.valueOf(number2));
        txt5.setText(String.valueOf(number));
        txt6.setText(s6);
        txt7.setText(s7);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
