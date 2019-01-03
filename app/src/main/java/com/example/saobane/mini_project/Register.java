package com.example.saobane.mini_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //ACTIVITE S'INSCRIRE
private static final int RESULT_LOAD_IMAGE=1;
    private static final int SELECT_PICTURE = 100;
    Button bEnregistrer;
    String ImageDecode;
    EditText etNom,etPrenom,etIdentifiant,etMotDePasse;
    Utilisateur utilisateur= new Utilisateur();;
    ImageView imgUpload;
    UtilisateurDB utilisateursdb1 = new UtilisateurDB(this);


    String score,imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgUpload=(ImageView) findViewById(R.id.ivUpload);

        etNom=(EditText)findViewById(R.id.etNom);
        etPrenom=(EditText)findViewById(R.id.etPrenom);
        etIdentifiant=(EditText)findViewById(R.id.etIdentifiant);
        etMotDePasse=(EditText)findViewById(R.id.etMotDePasse);
        bEnregistrer=(Button)findViewById(R.id.bEnregistrer);


        imgUpload.setOnClickListener(this);

        bEnregistrer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bEnregistrer:

                String identifiant=etIdentifiant.getText().toString();
                String motDePasse=etMotDePasse.getText().toString();


                UtilisateurDB utilisateursdb = new UtilisateurDB(this);
                utilisateursdb.open();



                if(identifiant.isEmpty() || motDePasse.isEmpty())
                {
                    Toast.makeText(this, "Veuillez saisir au moins l'identifiant et le mot de passe", Toast.LENGTH_SHORT).show();

                }

                else
                {


                    if(utilisateursdb.isUtilisateurExist(identifiant)==true)
                    {
                        Toast.makeText(this, "L'identifiant existe déjà", Toast.LENGTH_SHORT).show();
                    }
                        else {

                        utilisateur.setNom(etNom.getText().toString());
                        utilisateur.setPrenom(etPrenom.getText().toString());


                        utilisateur.setIdentifiant(etIdentifiant.getText().toString());
                        utilisateur.setMotDePasse(etMotDePasse.getText().toString());

                        utilisateur.setScore("5");

                        utilisateursdb.ajouterUtilisateur(utilisateur);
                        Toast.makeText(this, "Le compte a bien été créé", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,Login.class));
                        finish();


                    }

                }

                    utilisateursdb.close();


                break;
            case R.id.ivUpload:

                /*Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);**/


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),RESULT_LOAD_IMAGE);
                break;

        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE && resultCode== RESULT_OK && data !=null){

            Uri selectedImage=data.getData();


            String imgUri=selectedImage.getPath().toString();
            utilisateur.setImageUri(imgUri);

            imgUpload.setImageURI(selectedImage);


        }




    }*/
@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {

                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {

                    // Saving to Database...
                 //   if (saveImageInDB(selectedImageUri)) {
                      //  showMessage("Image Saved in Database...");


                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                            byte[] imgbyte=Utils.getImageBytes(bitmap);

                            utilisateur.setImageUri(imgbyte);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    imgUpload.setImageURI(selectedImageUri);
                  //  }

                    // Reading from Database after 3 seconds just to show the message
            /*        new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
                                //showMessage("Image Loaded from Database...");
                            }
                        }
                    }, 3000);
                */}




            }
        }
    }

/*
    Boolean saveImageInDB(Uri selectedImageUri) {

        try {
            utilisateursdb1.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            utilisateursdb1.insertImage(inputData);
            utilisateursdb1.close();
            return true;
        } catch (IOException ioe) {
            Log.e("MainActivity", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            utilisateursdb1.close();
            return false;
        }

    }

    Boolean loadImageFromDB() {
        try {
            utilisateursdb1.open();
            byte[] bytes = utilisateursdb1.retreiveImageFromDB();
            utilisateursdb1.close();
            // Show Image from DB in ImageView
            imgUpload.setImageBitmap(Utils.getImage(bytes));
            return true;
        } catch (Exception e) {
            Log.e("MainActivity", "<loadImageFromDB> Error : " + e.getLocalizedMessage());
            utilisateursdb1.close();
            return false;
        }
    }
    */
}
