/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import view.controler.MainController;
import view.coordinator.Coordinator;
import view.form.FrmKreiranjePutovanja;
import view.form.FrmLogin;
import view.form.FrmPretragaPutnika;
import view.form.FrmPrikazPutnika;
import view.form.FrmPutovanje;
import view.form.FrmRezervacije;

/**
 *
 * @author nikol
 */
public class Main {
    
    public static void main(String[] args) {
        Coordinator.getInstance().openLoginForm();
    }
}
