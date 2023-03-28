/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controler;

import communication.Communication;
import data.GlobalniPodaci;
import data.type.TipoviPodataka;
import domain.Putnik;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.coordinator.Coordinator;
import view.form.FrmMain;

/**
 *
 * @author nikol
 */
public class MainController {

    private final FrmMain form;

    public MainController(FrmMain frmMain) {
        this.form = frmMain;
        addActionListener();
    }

    public void openFonm() {
        Putnik putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.ULOGOVANI_KORISNIK);
        if (!(Boolean) GlobalniPodaci.getInstance().get(TipoviPodataka.ADMIN_ACCESS)) {
            hideElements();
            form.getLblPutnik().setText(putnik.getIme() + " " + putnik.getPrezime());
        } else {
            form.getLblPutnik().setText(putnik.getIme());
            hideElementsAdmin();
        }
        form.setVisible(true);
    }

    private void addActionListener() {
        form.getMiKreiranjePutnika().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openKreiranjePutnika();
            }
        });

        form.getMiPretrazivanjePutnika().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openPretrazivanjePutnika();
            }
        });

        form.getMiPodaci().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openPrikazPutnika();
            }
        });

        form.getMiLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalniPodaci.getInstance().reset();
                try {
                    Communication.getInstance().logout();
                    Coordinator.getInstance().openLoginForm();
                    form.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, "Neuspesna odjava sa sistema", "Greska odjave", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        form.getMiKreiranjePutovanja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openKreiranjePutovanja();
            }
        });

        form.getMiPretrazivanjePutovanja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openPretrazivanjePutovanja();
            }
        });

        form.getMiKreiranjeRezervacije().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openRezervisanje();
            }
        });

        form.getMiPretrazivanjeRezervacije().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openPretragaRezervacija();
            }
        });
    }

    private void hideElements() {
        form.getMmPutnik().setVisible(false);
        form.getMiKreiranjePutovanja().setVisible(false);
        form.getMiKreiranjeRezervacije().setVisible(false);
    }

    private void hideElementsAdmin() {
        form.getMiPodaci().setVisible(false);
    }

    public FrmMain getFrmMain() {
        return form;
    }

}
