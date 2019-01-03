package com.example.saobane.mini_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Saobane on 09/12/2016.
 */
//ACCES TABLE TRAJET ET DIFFERENTES OPERATIONS SUR LA TABLE
public class TrajetDB {

    public static final int VERSION_DB=1;
    public static final String DATABASE_NAME="mini_project1.db";
    public static final String TABLE_TRAJETS="trajets";

    public static final String COL_ID="ID";
    public static final int NUM_COL_ID=0;

    public static final String COL_DEPART="DEPART";
    public static final int NUM_COL_DEPART=1;

    public static final String COL_ARRIVEE="ARRIVEE";
    public static final int NUM_COL_ARRIVEE=2;

    public static final String COL_DATE="DATE";
    public static final int NUM_COL_DATE=3;

    public static final String COL_HEURE="HEURE";
    public static final int NUM_COL_HEURE=4;

    public static final String COL_RETARD="RETARD";
    public static final int NUM_COL_RETARD=5;

    public static final String COL_AUTOROUTE="AUTOROUTE";
    public static final int NUM_COL_AUTOROUTE=6;

    public static final String COL_CONTRIBUTION="CONTRIBUTION";
    public static final int NUM_COL_CONTRIBUTION=7;

    public static final String COL_ID_USER="ID_USER";
    public static final int NUM_COL_ID_USER=8;

    private SQLiteDatabase bdd;
    private DataBaseHelper myDb;

    public TrajetDB(Context context) {

        myDb=new DataBaseHelper(context,DATABASE_NAME,null,VERSION_DB);
    }

    public void open(){

        bdd=myDb.getWritableDatabase();
    }
    public void close(){

        bdd.close();
    }

    public SQLiteDatabase getBDD(){

        return  bdd;
    }

    public long ajouterTrajet(Trajet tr){

        ContentValues values =new ContentValues();
        values.put(COL_DEPART,tr.getDepart());
        values.put(COL_ARRIVEE,tr.getArrivee());
        values.put(COL_DATE,tr.getDate());
        values.put(COL_HEURE,tr.getHeure());
        values.put(COL_RETARD,tr.getRetard());
        values.put(COL_AUTOROUTE,tr.getAuto());
        values.put(COL_CONTRIBUTION,tr.getContribution());
        values.put(COL_ID_USER,tr.getIdUser());


        return bdd.insert(TABLE_TRAJETS,null,values);

    }

    public int majUtilisateur(int id, Trajet tr){
        ContentValues values =new ContentValues();
        values.put(COL_DEPART,tr.getDepart());
        values.put(COL_ARRIVEE,tr.getArrivee());
        values.put(COL_DATE,tr.getDate());
        values.put(COL_HEURE,tr.getHeure());
        values.put(COL_RETARD,tr.getRetard());
        values.put(COL_AUTOROUTE,tr.getAuto());
        values.put(COL_CONTRIBUTION,tr.getContribution());
        values.put(COL_ID_USER,tr.getIdUser());


        return bdd.update(TABLE_TRAJETS,values,COL_ID+"="+id,null);

    }


    public int getMyTrajetsCount(int id_user){

//
        String query = "SELECT * FROM " + TABLE_TRAJETS + " WHERE " + COL_ID_USER + " = " + id_user;
        Cursor cursor=bdd.rawQuery(query,null);
        int d=cursor.getCount();


        return d;
    }

    public ArrayList<String> getAllTrajets(){

        open();
        //  String query = "SELECT * FROM " + TABLE_TRAJETS + " WHERE "+ COL_DEPART +" LIKE\""+ departS+"\"";

        Cursor cursor=bdd.query(TABLE_TRAJETS,new String[]{ COL_ID,COL_DEPART,COL_ARRIVEE,COL_DATE,COL_HEURE,COL_CONTRIBUTION },null,null,null,null,null);



        String s;
        ArrayList list=new ArrayList();

        while(cursor.moveToNext()){
            String depart = cursor.getString(1);
            String arrivee = cursor.getString(2);
            String date = cursor.getString(3);
            String heure = cursor.getString(4);
            String contrib=cursor.getString(5);
            s = ""+depart+" => "+arrivee+ " Date: "+date+" Heure "+heure+" ---"+" Contribution: "+contrib;
            list.add(s);
        }
        close();
        return list;
    }
    public ArrayList<String> getAllTrajets(int id_user){

open();
        String query = "SELECT * FROM " + TABLE_TRAJETS + " WHERE " + COL_ID_USER + " = " + id_user;
        Cursor cursor=bdd.rawQuery(query,null);

String s;
        ArrayList list=new ArrayList();

        while(cursor.moveToNext()){
            String depart = cursor.getString(1);
            String arrivee = cursor.getString(2);
            String date = cursor.getString(3);
            s = ""+depart+" => "+arrivee+ " Date: "+date;
            list.add(s);
        }
        close();
        return list;
    }
    public double getMontant(int id_user){

        open();
        String query = "SELECT * FROM " + TABLE_TRAJETS + " WHERE " + COL_ID_USER + " = " + id_user;
        Cursor cursor=bdd.rawQuery(query,null);

        Double montant=0.0 ;
        String s;
        String[] montantTab=new String[cursor.getCount()];


        int i=0;

        while(cursor.moveToNext()){
            montantTab[i] = cursor.getString(7);

            montant=montant+Double.parseDouble(montantTab[i]);
            i++;


        }
        close();
        return montant;
    }
    public ArrayList<String> getAllTrajetsSeach(String departS,String arriveeS, String dateS){

        open();
       //  String query = "SELECT * FROM " + TABLE_TRAJETS + " WHERE "+ COL_DEPART +" LIKE\""+ departS+"\"";

        Cursor cursor=bdd.query(TABLE_TRAJETS,new String[]{ COL_ID,COL_DEPART,COL_ARRIVEE,COL_DATE,COL_HEURE,COL_CONTRIBUTION }, COL_DEPART+" LIKE\""+departS+"\""+" AND "+COL_ARRIVEE+" LIKE\""+arriveeS+"\""+" AND "+COL_DATE+" LIKE\""+dateS+"\"",null,null,null,null);



        String s;
        ArrayList list=new ArrayList();

        while(cursor.moveToNext()){
            String depart = cursor.getString(1);
            String arrivee = cursor.getString(2);
            String date = cursor.getString(3);
            String heure = cursor.getString(4);
            String contrib=cursor.getString(5);
            s = ""+depart+" => "+arrivee+ " Date: "+date+" Heure "+heure+" ---"+" Contribution: "+contrib;
            list.add(s);
        }
        close();
        return list;
    }

    /*public int supprimerTrajetParSonId(int id){

        return  bdd.delete(TABLE_UTILISATEURS,COL_ID+"="+id,null);
    }

***/



}
