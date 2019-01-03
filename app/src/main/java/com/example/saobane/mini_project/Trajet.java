package com.example.saobane.mini_project;

/**
 * Created by Saobane on 09/12/2016.
 */

//CLASSE TRAJET

public class Trajet {





    public String depart,arrivee,date,heure,retard,contribution;
    int auto;
    int idUser;



    public Trajet() {
    }

    public Trajet(String depart, String arrivee, String date, String heure, String retard, String contribution, int auto,int idUser) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.date = date;
        this.heure = heure;
        this.retard = retard;
        this.contribution = contribution;
        this.auto = auto;
        this.idUser=idUser;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getRetard() {
        return retard;
    }

    public void setRetard(String retard) {
        this.retard = retard;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
