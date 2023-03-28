/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author nikol
 */
public class Grad implements Serializable{
    private int gradID;
    private String naziv;
    private String drzava;

    public Grad() {
    }

    public Grad(int gradID, String naziv, String drzava) {
        this.gradID = gradID;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public int getGradID() {
        return gradID;
    }

    public void setGradID(int gradID) {
        this.gradID = gradID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return this.naziv + ", " + this.drzava;
    }
    
    
}
