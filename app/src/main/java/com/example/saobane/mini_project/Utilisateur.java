package com.example.saobane.mini_project;

/**
 * Created by Saobane on 14/11/2016.
 */

public class Utilisateur {

    public int id;



    public String nom,prenom,identifiant,motDePasse,score/*,imageUri*/;
    public byte[] imageUri;



    public Utilisateur(String identifiant, String motDePasse) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.nom = "";
        this.prenom = "";
        this.score="1";

    }



    public Utilisateur() {
    }

    public byte[] getImageUri() {
        return imageUri;
    }

    public void setImageUri(byte[] imageUri1) {
        this.imageUri = imageUri1;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

   /* public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
