/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nikol
 */
public class Termin implements Serializable {

    private int terminID;
    private Double cena;
    private Date datumPolaska;
    private Date datumPovratka;

    private Putovanje putovanje;
    private List<Rezervacija> rezervacije;

    public Termin() {
        this.rezervacije = new ArrayList<>();
    }

    public Termin(int terminID, Double cena, Date datumPolaska, Date datumPovratka, Putovanje putovanje) {
        this.terminID = terminID;
        this.cena = cena;
        this.datumPolaska = datumPolaska;
        this.datumPovratka = datumPovratka;
        this.putovanje = putovanje;
        this.rezervacije = new ArrayList<>();
    }

    public int getTerminID() {
        return terminID;
    }

    public void setTerminID(int terminID) {
        this.terminID = terminID;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Date getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(Date datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public Date getDatumPovratka() {
        return datumPovratka;
    }

    public void setDatumPovratka(Date datumPovratka) {
        this.datumPovratka = datumPovratka;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
    
    
    
    
}
