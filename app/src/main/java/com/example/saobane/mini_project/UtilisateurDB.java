package com.example.saobane.mini_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;

/**
 * Created by Saobane on 14/11/2016.
 */
//ACCES TABLE UTILISATEURS ET OPERATIONS SUR CETTE TABLE
public class UtilisateurDB {

    public static final int VERSION_DB=1;
    public static final String DATABASE_NAME="mini_project1.db";
    public static final String TABLE_UTILISATEURS="utilisateurs";

    public static final String COL_ID="ID";
    public static final int NUM_COL_ID=0;

    public static final String COL_NOM="NOM";
    public static final int NUM_COL_NOM=1;

    public static final String COL_PRENOM="PRENOM";
    public static final int NUM_COL_PRENOM=2;

    public static final String COL_IDENTIFIANT="IDENTIFIANT";
    public static final int NUM_COL_IDENTIFIANT=3;

    public static final String COL_MOTDEPASSE="MOTDEPASSE";
    public static final int NUM_COL_MOTDEPASSE=4;

    public static final String COL_SCORE="SCORE";
    public static final int NUM_COL_SCORE=5;

    public static final String COL_IMAGEURI="IMAGEURI";
    public static final int NUM_COL_IMAGEURI=6;

    private SQLiteDatabase bdd;
    private DataBaseHelper myDb;

    public UtilisateurDB(Context context) {

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

    public long ajouterUtilisateur(Utilisateur U){

        ContentValues values =new ContentValues();
        values.put(COL_NOM,U.getNom());
        values.put(COL_PRENOM,U.getPrenom());
        values.put(COL_IDENTIFIANT,U.getIdentifiant());
        values.put(COL_MOTDEPASSE,U.getMotDePasse());
        values.put(COL_SCORE,U.getScore());
        values.put(COL_IMAGEURI,U.getImageUri());

        return bdd.insert(TABLE_UTILISATEURS,null,values);

    }

    public int majUtilisateur(int id, Utilisateur U){
        ContentValues values =new ContentValues();
        values.put(COL_NOM,U.getNom());
        values.put(COL_PRENOM,U.getPrenom());
        values.put(COL_IDENTIFIANT,U.getIdentifiant());
        values.put(COL_MOTDEPASSE,U.getMotDePasse());

        values.put(COL_SCORE,U.getScore());
        values.put(COL_IMAGEURI,U.getImageUri());


        return bdd.update(TABLE_UTILISATEURS,values,COL_ID+"="+id,null);

    }

    public int supprimerUtilisateurParSonId(int id){

        return  bdd.delete(TABLE_UTILISATEURS,COL_ID+"="+id,null);
    }


    public boolean isUtilisateurExist(String identifiant){

        String query = "SELECT * FROM " + TABLE_UTILISATEURS + " WHERE " + COL_IDENTIFIANT + " LIKE \"" + identifiant +"\"";
        Cursor cursor = bdd.rawQuery(query, null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();

            return false;
        }
        cursor.close();
        return true;

    }

    public int updateScore(int id,float score){


        Utilisateur u=getUtilisateurParSonIdentifiant(id);
        ContentValues values =new ContentValues();
        values.put(COL_NOM,u.getNom());
        values.put(COL_PRENOM,u.getPrenom());
        values.put(COL_IDENTIFIANT,u.getIdentifiant());
        values.put(COL_MOTDEPASSE,u.getMotDePasse());
        Double scr=Double.parseDouble(u.getScore());
        Double scr1=Double.parseDouble(new Float(score).toString());
Double scoreFinale=scr+scr1;



byte[] img=retreiveImageFromDB(u.getIdentifiant(),u.getMotDePasse());

        values.put(COL_SCORE,scoreFinale.toString());
        values.put(COL_IMAGEURI,img);



        return bdd.update(TABLE_UTILISATEURS,values,COL_ID+"="+id,null);


    }

    public Utilisateur getUtilisateurParSonIdentifiant(int id){

        Cursor c=bdd.query(TABLE_UTILISATEURS,new String[]{ COL_ID,COL_NOM,COL_PRENOM,COL_IDENTIFIANT,COL_MOTDEPASSE,COL_SCORE }, COL_ID+" LIKE\""+id+"\"",null,null,null,null);
        // Cursor cr=bdd.rawQuery("select * from "+TABLE_UTILISATEURS+" WHERE "+COL_IDENTIFIANT+" LIKE\""+identifiant+"\"",null);
        return cursortoUtilisateur(c);

    }
    public Utilisateur getUtilisateurParSonIdentifiant(String identifiant,String motDePasse){

        Cursor c=bdd.query(TABLE_UTILISATEURS,new String[]{ COL_ID,COL_NOM,COL_PRENOM,COL_IDENTIFIANT,COL_MOTDEPASSE,COL_SCORE }, COL_IDENTIFIANT+" LIKE\""+identifiant+"\""+" AND "+COL_MOTDEPASSE+" LIKE\""+motDePasse+"\"",null,null,null,null);
       // Cursor cr=bdd.rawQuery("select * from "+TABLE_UTILISATEURS+" WHERE "+COL_IDENTIFIANT+" LIKE\""+identifiant+"\"",null);
        return cursortoUtilisateur(c);

    }

    private Utilisateur cursortoUtilisateur(Cursor c){

            if(c.getCount()==0) {
                return null;
            }
        else {

                c.moveToFirst();
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(c.getInt(NUM_COL_ID));
                utilisateur.setNom(c.getString(NUM_COL_NOM));
                utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
                utilisateur.setIdentifiant(c.getString(NUM_COL_IDENTIFIANT));
                utilisateur.setMotDePasse(c.getString(NUM_COL_MOTDEPASSE));
                utilisateur.setScore(c.getString(NUM_COL_SCORE));
                //utilisateur.setImageUri(retreiveImageFromDB( identifiant, motDePasse));


                c.close();
                return utilisateur;
            }



    }


    // Get the image from SQLite DB
    // We will just get the last image we just saved for convenience...
    public byte[] retreiveImageFromDB(String identifiant,String motDePasse) {

        String query = "SELECT " + COL_IMAGEURI + " FROM " + TABLE_UTILISATEURS + " WHERE " + COL_IDENTIFIANT + " LIKE \"" + identifiant +"\""+ " AND " + COL_MOTDEPASSE + " LIKE \"" +motDePasse + "\"";
        Cursor cursor = bdd.rawQuery(query, null);
       /* Cursor cur = bdd.query(true, "utilisateurs", new String[]{"IMAGEURI",},
                null, null, null, null,
                "id" + " DESC", "1");*/
        if (cursor.moveToNext()) {
            byte[] blob = cursor.getBlob(0);
            cursor.close();
            return blob;
        }
        cursor.close();
        return null;
    }

}
