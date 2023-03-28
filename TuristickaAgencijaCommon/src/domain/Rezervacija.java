/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import domain.util.StatusRezervacije;
import java.io.Serializable;

/**
 *
 * @author nikol
 */
public class Rezervacija implements Serializable{
    private Putnik putnik;
    private Putovanje putovanje;
    private Termin termin;
    private int brojRezervacije;
    private StatusRezervacije status;

    public Rezervacija() {
    }

    public Rezervacija(Putnik putnik, Putovanje putovanje, Termin termin, int brojRezervacije, StatusRezervacije status) {
        this.putnik = putnik;
        this.putovanje = putovanje;
        this.termin = termin;
        this.brojRezervacije = brojRezervacije;
        this.status = status;
    }

    public Putnik getPutnik() {
        return putnik;
    }

    public void setPutnik(Putnik putnik) {
        this.putnik = putnik;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public int getBrojRezervacije() {
        return brojRezervacije;
    }

    public void setBrojRezervacije(int brojRezervacije) {
        this.brojRezervacije = brojRezervacije;
    }

    public StatusRezervacije getStatus() {
        return status;
    }

    public void setStatus(StatusRezervacije status) {
        this.status = status;
    }
    
    
    
}
