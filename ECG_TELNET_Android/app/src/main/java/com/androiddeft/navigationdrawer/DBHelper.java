package com.androiddeft.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Toutes les variables statiques
// version de la base de données
    private static final int DATABASE_VERSION = 1;

    // nom de la base de données
    private static final String DATABASE_NAME = "DBNAME.db";

    // nom des champs de la base de données
    public static final String TABLE_CONTACTS = "details";
    public static final String ID_DETAILS="id";
    public static final String NOM_DETAILS="nom";
    public static final String PRENOM_DETAILS="prenom";
    public static final String CIVILITE_DETAILS="civilite";
    public static final String DATEE_DETAILS="datee";
    public static final String EMAIL_DETAILS="email";
    public static final String NUMBER_DETAILS="number";
    public static final String NATIONALITY_DETAILS="nationality";
    public static final String REMARQUE_DETAILS="remarque";
    public static final String REFERENCE_DETAILS="reference";
    public static final String IMAAGE_DETAILS="image";
    SQLiteDatabase mDB;
    Context mContext;

    //creation des tables
    private static final String CREATE_DETAILS = "CREATE TABLE " + TABLE_CONTACTS + " (" +
            ID_DETAILS + " INTEGER PRIMARY KEY, " +
            NOM_DETAILS + " TEXT, " +
            PRENOM_DETAILS + " TEXT, " +
            CIVILITE_DETAILS + " TEXT, " +
            DATEE_DETAILS + " TEXT, " +
            EMAIL_DETAILS + " TEXT, " +
            NUMBER_DETAILS + " INTEGER, " +
            NATIONALITY_DETAILS + " TEXT, " +
            REMARQUE_DETAILS + " TEXT, " +
            REFERENCE_DETAILS + " INTEGER, " +
            IMAAGE_DETAILS + " BLOB);";






    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mDB = this.getWritableDatabase();
    }

//creation de la base de données
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_DETAILS);
    }
//Mise à niveau de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS + ";");
        onCreate(db);
    }
    /**
     *  Les CRUD(Ajouter, selectionner, supprimer) Operations.
     */

    //ajouter une personne dans la base de données
    public void addContacts(Details c){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(NOM_DETAILS, c.getNom());
        values.put(PRENOM_DETAILS, c.getPrenom());
        values.put(CIVILITE_DETAILS, c.getCivilite());
        values.put(DATEE_DETAILS, c.getDatee());
        values.put(EMAIL_DETAILS, c.getEmail());
        values.put(NUMBER_DETAILS, c.getNumber());
        values.put(NATIONALITY_DETAILS, c.getNationality());
        values.put(REMARQUE_DETAILS, c.getRemarque());
        values.put(REFERENCE_DETAILS, c.getReference());
        values.put(IMAAGE_DETAILS, c.getImage());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();//Fermeture de la connexion à la base de données
    }


    // obtenir tous les personnes
    public List<Details> getAllContacts(String inputText) {
        List<Details> contactList = new ArrayList<Details>();
        // selectionner personne par referance
        String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+REFERENCE_DETAILS+" like '%"+inputText+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // en boucle à travers toutes les lignes et en ajoutant à la liste
        if (cursor.moveToFirst()) {
            do {
                Details contact = new Details();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNom(cursor.getString(1));
                contact.setPrenom(cursor.getString(2));
                contact.setCivilite(cursor.getString(3));
                contact.setDatee(cursor.getString(4));
                contact.setEmail(cursor.getString(5));
                contact.setNumber(Integer.parseInt(cursor.getString(6)));
                contact.setNationality(cursor.getString(7));
                contact.setRemarque(cursor.getString(8));
                contact.setReference(Integer.parseInt(cursor.getString(9)));
                contact.setImage(cursor.getBlob(10));
                // Ajouter personne to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // fermer l'insertion de données de la base de données
        db.close();
        // retourner la liste des personnes
        return contactList;

    }
    // obtenir tous les personnes
    public List<Details> getRefe() {
        List<Details> contactList = new ArrayList<Details>();
        //Sélectionner toutes les requêtes
        String selectQuery = "SELECT * FROM "+TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // en boucle à travers toutes les lignes et en ajoutant à la liste
        if (cursor.moveToFirst()) {
            do {
                Details contact = new Details();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNom(cursor.getString(1));
                contact.setPrenom(cursor.getString(2));
                contact.setCivilite(cursor.getString(3));
                contact.setDatee(cursor.getString(4));
                contact.setEmail(cursor.getString(5));
                contact.setNumber(Integer.parseInt(cursor.getString(6)));
                contact.setNationality(cursor.getString(7));
                contact.setRemarque(cursor.getString(8));
                contact.setReference(Integer.parseInt(cursor.getString(9)));
                contact.setImage(cursor.getBlob(10));
                // Ajouter personne to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // fermer l'insertion de données de la base de données
        db.close();
        // retourner la liste des personnes
        return contactList;

    }





//supprimer personne
    public void deleteSelectedItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_CONTACTS + " Where id = " + id);


    }


}
