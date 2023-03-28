/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import domain.util.Pol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nikol
 */
public class Putnik implements Serializable{
    private int putnikID;
    private String ime;
    private String prezime;
    private Pol pol;
    private String email;
    private String brojTelefona;
    private String sifra;
    
    private List<Rezervacija> rezervacije;

    public Putnik() {
        this.rezervacije = new ArrayList<>();
    }

    public Putnik(int putnikID, String ime, String prezime, Pol pol, String email, String brojTelefona, String sifra) {
        this.putnikID = putnikID;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.sifra = sifra;
        this.rezervacije = new ArrayList<>();
    }

    public int getPutnikID() {
        return putnikID;
    }

    public void setPutnikID(int putnikID) {
        this.putnikID = putnikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Pol getPol() {
        return pol;
    }

    public void setPol(Pol pol) {
        this.pol = pol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[id: ");
        sb.append(putnikID);
        sb.append(", ime: ");
        sb.append(ime);
        sb.append(", prezime: ");
        sb.append(prezime);
        sb.append(", pol: ");
        sb.append(pol.toString().toLowerCase());
        sb.append(", email: ");
        sb.append(email);
        sb.append(", broj telefona: ");
        sb.append(brojTelefona);
        sb.append("]");
        return sb.toString();
    }

    
}
