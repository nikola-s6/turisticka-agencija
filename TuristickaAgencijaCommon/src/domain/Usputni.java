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
public class Usputni implements Serializable{
    private Grad grad;
    private Putovanje putovanje;
    private int redniBroj;

    public Usputni() {
    }

    public Usputni(Grad grad, Putovanje putovanje, int redniBroj) {
        this.grad = grad;
        this.putovanje = putovanje;
        this.redniBroj = redniBroj;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }
    
    
}
