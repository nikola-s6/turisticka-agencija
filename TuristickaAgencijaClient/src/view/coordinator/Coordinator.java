/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.coordinator;

import data.GlobalniPodaci;
import domain.Rezervacija;
import view.controler.KreiranjePutovanjaController;
import view.controler.LoginController;
import view.controler.MainController;
import view.controler.PretragaPutnikaController;
import view.controler.PrikazPutnikaController;
import view.controler.PutovanjeController;
import view.controler.RezervacijeController;
import view.form.FrmKreiranjePutovanja;
import view.form.FrmLogin;
import view.form.FrmMain;
import view.form.FrmPretragaPutnika;
import view.form.FrmPrikazPutnika;
import view.form.FrmPutovanje;
import view.form.FrmRezervacije;
import view.form.util.FormMode;

/**
 *
 * @author nikol
 */
public class Coordinator {

    private static Coordinator instance;
    private final MainController mainController;

    private Coordinator() {
        this.mainController = new MainController(new FrmMain());
    }

    public static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }

    public void openMainForm() {
        mainController.openFonm();
    }

    public void openLoginForm() {
//        this.mainController = new MainController(new FrmMain());
        instance = new Coordinator();
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }

    public void openKreiranjePutnika() {
        PrikazPutnikaController ppc = new PrikazPutnikaController(new FrmPrikazPutnika(mainController.getFrmMain(), true));
        ppc.openForm(FormMode.FORM_ADD);
    }

    public void openPretrazivanjePutnika() {
        PretragaPutnikaController ppc = new PretragaPutnikaController(new FrmPretragaPutnika(mainController.getFrmMain(), true));
        ppc.openForm();
    }

    public void openPrikazPutnika() {
        PrikazPutnikaController pp = new PrikazPutnikaController(new FrmPrikazPutnika(mainController.getFrmMain(), true));
        pp.openForm(FormMode.FORM_SEARCH);
    }
    
    public void openKreiranjePutovanja(){
        KreiranjePutovanjaController kp = new KreiranjePutovanjaController(new FrmKreiranjePutovanja(mainController.getFrmMain(), true));
        kp.openForm();
    }
    
    public void openPretrazivanjePutovanja(){
        PutovanjeController pk = new PutovanjeController(new FrmPutovanje(mainController.getFrmMain(), true));
        pk.openForm();
    }
    
    public void openRezervisanje(){
        RezervacijeController rc = new RezervacijeController(new FrmRezervacije(mainController.getFrmMain(), true));
        rc.openForm(FormMode.FORM_ADD);
    }

    public void openPretragaRezervacija(){
        RezervacijeController rc = new RezervacijeController(new FrmRezervacije(mainController.getFrmMain(), true));
        rc.openForm(FormMode.FORM_SEARCH);
    }
}
