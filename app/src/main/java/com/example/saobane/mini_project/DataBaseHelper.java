package com.example.saobane.mini_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;


/**
 * Created by Saobane on 14/11/2016.
 */
// CLASSE DE CREATION SUPPRESSION DES TABLES et de la BDD
public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {


        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE utilisateurs (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOM TEXT, PRENOM TEXT, IDENTIFIANT TEXT NOT NULL, MOTDEPASSE TEXT NOT NULL,SCORE TEXT, IMAGEURI BLOB );");
        db.execSQL("CREATE TABLE trajets (ID INTEGER PRIMARY KEY AUTOINCREMENT, DEPART TEXT NOT NULL, ARRIVEE TEXTNOT NULL, DATE TEXT NOT NULL, HEURE TEXT NOT NULL,RETARD TEXT, AUTOROUTE INT NOT NULL, CONTRIBUTION TEXT NOT NULL,ID_USER INTEGER NOT NULL,FOREIGN KEY (ID_USER) REFERENCES utilisateurs(ID));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS utilisateurs");
        db.execSQL("DROP TABLE IF EXISTS trajets");

        onCreate(db);
    }


}
