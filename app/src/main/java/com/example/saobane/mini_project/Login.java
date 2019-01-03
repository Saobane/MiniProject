package com.example.saobane.mini_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//ACTIVITY LOGIN
public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bConnexion;
    EditText etIdentifiant, etMotDePasse;
    TextView lienDeConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etIdentifiant=(EditText)findViewById(R.id.etIdentifiant);
        etMotDePasse=(EditText)findViewById(R.id.etMotDePasse);
        bConnexion=(Button)findViewById(R.id.bConnexion);
        lienDeConnexion=(TextView)findViewById(R.id.lienDeConnexion);

        bConnexion.setOnClickListener(this);
        lienDeConnexion.setOnClickListener(this);

    }
//ACTION DES CLICKS DE L'ACTIVITES
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bConnexion:
                UtilisateurDB utilisateursdb = new UtilisateurDB(this);
                utilisateursdb.open();
                String mdp=etMotDePasse.getText().toString(),id=etIdentifiant.getText().toString();


            Utilisateur u= utilisateursdb.getUtilisateurParSonIdentifiant(id,mdp);

                if(  u!=null  )
                {
                    Toast.makeText(this, "Vous etes connecté", Toast.LENGTH_SHORT).show();


                    u.setImageUri(utilisateursdb.retreiveImageFromDB(id,mdp));
                    Intent intent = new Intent(v.getContext(),MainActivity.class);

                    intent.putExtra("identifiant",etIdentifiant.getText().toString());
                    intent.putExtra("nom",u.getNom().toString());
                    intent.putExtra("prenom",u.getPrenom().toString());
                    int gt=u.getId();
                    intent.putExtra("id_user", u.getId());


                    intent.putExtra("password",etMotDePasse.getText().toString());

                    if(u.getScore().toString().equals(null) ) {
                        intent.putExtra("score", "0");
                    }
                    else
                    {
                        intent.putExtra("score", u.getScore().toString());
                    }


                    if( u.getImageUri()==null) {
                        intent.putExtra("imageUri", "No image");
                    }
                    else {
                        intent.putExtra("imageUri", u.getImageUri());

                    }












                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(this, "Identifiant ou Mot de passe incorrect", Toast.LENGTH_SHORT).show();

                }
                utilisateursdb.close();


                break;
            case R.id.lienDeConnexion:

                     startActivity(new Intent(this,Register.class));

                break;

        }
    }
}
