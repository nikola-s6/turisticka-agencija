/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import domain.util.Ponuda;
import domain.util.Prevoz;
import domain.util.Smestaj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nikol
 */
public class Putovanje implements Serializable{
    private int putovanjeID;
    private String naziv;
    private Grad pocetniGrad;
    private Grad krajnjiGrad;
    private Prevoz prevoz;
    private Smestaj smestaj;
    private Ponuda ponuda;
    private String kratakOpis;
    
    private List<Termin> termini;
    private List<Usputni> usputniGradovi;

    public Putovanje() {
        this.termini = new ArrayList<>();
        this.usputniGradovi = new ArrayList<>();
    }

    public Putovanje(int putovanjeID, String naziv, Grad pocetniGrad, Grad krajnjiGrad, Smestaj smestaj, Ponuda ponuda, String kratakOpis) {
        this.putovanjeID = putovanjeID;
        this.naziv = naziv;
        this.pocetniGrad = pocetniGrad;
        this.krajnjiGrad = krajnjiGrad;
        this.smestaj = smestaj;
        this.ponuda = ponuda;
        this.kratakOpis = kratakOpis;
        this.termini = new ArrayList<>();
        this.usputniGradovi = new ArrayList<>();
    }

    public int getPutovanjeID() {
        return putovanjeID;
    }

    public void setPutovanjeID(int putovanjeID) {
        this.putovanjeID = putovanjeID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Grad getPocetniGrad() {
        return pocetniGrad;
    }

    public void setPocetniGrad(Grad pocetniGrad) {
        this.pocetniGrad = pocetniGrad;
    }

    public Grad getKrajnjiGrad() {
        return krajnjiGrad;
    }

    public void setKrajnjiGrad(Grad krajnjiGrad) {
        this.krajnjiGrad = krajnjiGrad;
    }

    public Prevoz getPrevoz() {
        return prevoz;
    }

    public void setPrevoz(Prevoz prevoz) {
        this.prevoz = prevoz;
    }

    public Smestaj getSmestaj() {
        return smestaj;
    }

    public void setSmestaj(Smestaj smestaj) {
        this.smestaj = smestaj;
    }

    public Ponuda getPonuda() {
        return ponuda;
    }

    public void setPonuda(Ponuda ponuda) {
        this.ponuda = ponuda;
    }

    public String getKratakOpis() {
        return kratakOpis;
    }

    public void setKratakOpis(String kratakOpis) {
        this.kratakOpis = kratakOpis;
    }

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
    }

    public List<Usputni> getUsputniGradovi() {
        return usputniGradovi;
    }

    public void setUsputniGradovi(List<Usputni> usputniGradovi) {
        this.usputniGradovi = usputniGradovi;
    }
    
    
    
    
    
}
