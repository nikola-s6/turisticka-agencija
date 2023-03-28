/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controler;

import communication.Communication;
import data.GlobalniPodaci;
import data.type.TipoviPodataka;
import domain.Grad;
import domain.Putnik;
import domain.Putovanje;
import domain.Rezervacija;
import domain.Termin;
import domain.util.StatusRezervacije;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.form.FrmRezervacije;
import view.form.component.table.PretragaPutnikaTableModel;
import view.form.component.table.PutovanjaTableModel;
import view.form.component.table.RezervacijeTableModel;
import view.form.component.table.TerminiPutovanjaTableModel;
import view.form.util.FormMode;

/**
 *
 * @author nikol
 */
public class RezervacijeController {

    private final FrmRezervacije form;
    private FormMode mode;

    public RezervacijeController(FrmRezervacije frm) {
        this.form = frm;
    }

    public void openForm(FormMode mode) {
        this.mode = mode;
        if (!(Boolean) GlobalniPodaci.getInstance().get(TipoviPodataka.ADMIN_ACCESS)) {
            putnikInit();
        } else {
            if (mode.equals(FormMode.FORM_ADD)) {
                initComponentsAdding();
            } else {
                initComponentsSearching();
            }
        }
        addActionListeners();
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnPretraziPutnika().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imePrezime = form.getTxtPretragaPutnik().getText().trim();
                List<Putnik> putnici = ucitajPutnike(imePrezime);
                PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
                form.gettPutnici().setModel(model);
            }
        });

        form.getBtnPretraziPutovanja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String putovanje = form.getTxtPretragaPutovanja().getText().trim();
                List<Putovanje> putovanja = ucitajPutovanja(putovanje);
                PutovanjaTableModel model = new PutovanjaTableModel(putovanja, false);
                form.gettPutovanja().setModel(model);
            }
        });

        form.gettPutovanja().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (mode.equals(FormMode.FORM_ADD)) {
                    ucitajSveTermine();
                } else {
                    ucitajTermineKorisnika();
                }
            }

            private void ucitajSveTermine() {
                try {
                    int row = form.gettPutovanja().getSelectedRow();
                    Putovanje putovanjeNepotpuno = ((PutovanjaTableModel) form.gettPutovanja().getModel()).getPutovanje(row);
                    Putovanje putovanje = Communication.getInstance().ucitajPutovanje(putovanjeNepotpuno);

                    TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(putovanje.getTermini(), false);
                    form.gettTermini().setModel(terminiModel);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja termina", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void ucitajTermineKorisnika() {
                int rowPutovanje = form.gettPutovanja().getSelectedRow();
                Putovanje putovanje = ((PutovanjaTableModel) form.gettPutovanja().getModel()).getPutovanje(rowPutovanje);

                Putnik putnik;
//                da ulogovanikorisnik ne bi morao da klikne prvo na tabelu korisnika
                if (!(Boolean) GlobalniPodaci.getInstance().get(TipoviPodataka.ADMIN_ACCESS)) {
                    putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.ULOGOVANI_KORISNIK);
                } else {
                    int rowPutnik = form.gettPutnici().getSelectedRow();
                    putnik = ((PretragaPutnikaTableModel) form.gettPutnici().getModel()).getPutnik(rowPutnik);
                }
                try {
                    List<Rezervacija> rezervacije = Communication.getInstance().pronadjiRezervacije(putnik, putovanje);
                    RezervacijeTableModel model = new RezervacijeTableModel(rezervacije, false);
                    form.gettTermini().setModel(model);
                    if (rezervacije.size() == 0) {
                        JOptionPane.showMessageDialog(form, "Sistem ne moze da ucita rezervacije po zadatoj vrednosti", "Greska ucitavanja rezervacija", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(form, "Sistem je ucitao rezervacije", "Potvrda pretrage rezervacija", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja rezervacija", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        form.gettPutnici().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!mode.equals(FormMode.FORM_ADD)) {
                    resetTableRezervacije();
                    int row = form.gettPutnici().getSelectedRow();
                    Putnik putnik = ((PretragaPutnikaTableModel) form.gettPutnici().getModel()).getPutnik(row);
                    try {
                        List<Putovanje> putovanja = Communication.getInstance().pronadjiRezervacijePutnika(putnik);

                        PutovanjaTableModel putovanjaModel = new PutovanjaTableModel(putovanja, false);
                        form.gettPutovanja().setModel(putovanjaModel);
                        JOptionPane.showMessageDialog(form, "Sistem je nasao rezeravcije po zadatoj vrednosti", "Potvrda pretrage rezervacija", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska pretrage rezervacija", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            private void resetTableRezervacije() {
                List<Rezervacija> rezervacije = new ArrayList<>();
                RezervacijeTableModel model = new RezervacijeTableModel(rezervacije, false);
                form.gettTermini().setModel(model);
            }
        });

        form.getBtnRRezervisi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowPutnik = form.gettPutnici().getSelectedRow();
                Putnik putnik = ((PretragaPutnikaTableModel) form.gettPutnici().getModel()).getPutnik(rowPutnik);

                int rowPutovanje = form.gettPutovanja().getSelectedRow();
                Putovanje putovanje = ((PutovanjaTableModel) form.gettPutovanja().getModel()).getPutovanje(rowPutovanje);

                int rowTermin = form.gettTermini().getSelectedRow();
                Termin termin = ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).getTermin(rowTermin);

                try {
                    validateRezervacija(putnik, putovanje, termin);

                    Rezervacija rezervacija = new Rezervacija();
                    rezervacija.setPutnik(putnik);
                    rezervacija.setPutovanje(putovanje);
                    rezervacija.setTermin(termin);
                    rezervacija.setStatus(StatusRezervacije.NEAKTIVAN);

                    rezervacija = Communication.getInstance().zapamtiRezervaciju(rezervacija);
                    JOptionPane.showMessageDialog(form, "Sistem je zapamtio rezervaciju", "Potvrda rezervacije", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        Communication.getInstance().obradiRezervaciju(rezervacija);
                        JOptionPane.showMessageDialog(form, "Sistem je obradio rezervaciju", "Potvrda obrade", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska obrade", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja podataka", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void validateRezervacija(Putnik putnik, Putovanje putovanje, Termin termin) throws Exception {
                String errorMessage = "";
                if (putnik == null) {
                    errorMessage += "Putnik mora biti selektovan!\n";
                }
                if (putovanje == null) {
                    errorMessage += "Putovanje mora biti selektovano!\n";
                }
                if (termin == null) {
                    errorMessage += "Termin mora biti selektovan!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });

        form.getBtnObradi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    zove se tabela termini jer kod dodavanja ovde stoje termini, ali kod pretrage i izmene tu stoje rezervacije
                    int row = form.gettTermini().getSelectedRow();
                    Rezervacija rezervacija = ((RezervacijeTableModel) form.gettTermini().getModel()).getRezervacija(row);
                    Communication.getInstance().obradiRezervaciju(rezervacija);
                    ((RezervacijeTableModel) form.gettTermini().getModel()).obradi(row);
                    JOptionPane.showMessageDialog(form, "Sistem je obradio rezervaciju", "Potvrda obrade", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska obrade", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        form.getBtnStorniraj().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    zove se tabela termini jer kod dodavanja ovde stoje termini, ali kod pretrage i izmene tu stoje rezervacije
                    int row = form.gettTermini().getSelectedRow();
                    Rezervacija rezervacija = ((RezervacijeTableModel) form.gettTermini().getModel()).getRezervacija(row);
                    Communication.getInstance().stornirajRezervaciju(rezervacija);
                    ((RezervacijeTableModel) form.gettTermini().getModel()).storniraj(row);
                    JOptionPane.showMessageDialog(form, "Rezervacija uspesno stornirana", "Potvrda storniranja", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska obrade", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void putnikInit() {
        try {
            form.getBtnRRezervisi().setVisible(false);
            form.getBtnPretraziPutovanja().setVisible(false);
            form.getTxtPretragaPutovanja().setVisible(false);
            form.getlblPretragaPutovanja().setVisible(false);
            form.getBtnStorniraj().setVisible(false);
            form.getBtnObradi().setVisible(false);
            form.getBtnPretraziPutnika().setVisible(false);
            form.getTxtPretragaPutnik().setVisible(false);
            form.getlblPutnik().setVisible(false);

            List<Termin> termini = new ArrayList<>();
            TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(termini, false);
            form.gettTermini().setModel(terminiModel);

            Putnik putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.ULOGOVANI_KORISNIK);
            List<Putnik> putnici = new ArrayList<>();
            putnici.add(putnik);
            PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
            form.gettPutnici().setModel(model);

            List<Putovanje> putovanja = Communication.getInstance().pronadjiRezervacijePutnika(putnik);
            PutovanjaTableModel putovanjaModel = new PutovanjaTableModel(putovanja, false);
            form.gettPutovanja().setModel(putovanjaModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja podataka", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void initComponentsSearching() {
        form.getBtnRRezervisi().setVisible(false);
        form.getBtnPretraziPutovanja().setVisible(false);
        form.getTxtPretragaPutovanja().setVisible(false);
        form.getlblPretragaPutovanja().setVisible(false);

        List<Putnik> putnici = ucitajPutnike("");
        PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
        form.gettPutnici().setModel(model);

        List<Putovanje> putovanja = new ArrayList<>();
        PutovanjaTableModel putovanjaModel = new PutovanjaTableModel(putovanja, false);
        form.gettPutovanja().setModel(putovanjaModel);

        List<Termin> termini = new ArrayList<>();
        TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(termini, false);
        form.gettTermini().setModel(terminiModel);
    }

    private void initComponentsAdding() {
        form.getBtnStorniraj().setVisible(false);
        form.getBtnObradi().setVisible(false);

        List<Putnik> putnici = ucitajPutnike("");
        PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
        form.gettPutnici().setModel(model);

        List<Putovanje> putovanja = ucitajPutovanja("");
        PutovanjaTableModel putovanjaModel = new PutovanjaTableModel(putovanja, false);
        form.gettPutovanja().setModel(putovanjaModel);

        List<Termin> termini = new ArrayList<>();
        TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(termini, false);
        form.gettTermini().setModel(terminiModel);
    }

    private List<Putnik> ucitajPutnike(String string) {
        List<Putnik> putnici = new ArrayList<>();
        try {
            if (string.equals("")) {
//            ucitavanje svih putnika
                putnici = Communication.getInstance().ucitajListuPutnika();
            } else {
//                ucitavanje putnika koji odgovaraju kriterijumu
                if (!(string.contains(" ") && (string.split(" ").length == 2))) {
                    throw new Exception("U polje je potrebno upisati ime i prezime!");
                }
                String[] imePrezime = string.split(" ");
                Putnik putnik = new Putnik();
                putnik.setIme(imePrezime[0]);
                putnik.setPrezime(imePrezime[1]);
                putnici = Communication.getInstance().pronadjiPutnike(putnik);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Greska pretrage putnika", JOptionPane.ERROR_MESSAGE);
        }
        return putnici;
    }

    private List<Putovanje> ucitajPutovanja(String s) {
        List<Putovanje> putovanja = new ArrayList<>();
        try {
            if (s.equals("")) {
                putovanja = Communication.getInstance().ucitajListuPutovanja();
            } else {
                Grad g = new Grad();
                g.setNaziv(s);
                putovanja = Communication.getInstance().pronadjiPutovanja(g);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Greska pretrage putovanja", JOptionPane.ERROR_MESSAGE);

        }
        return putovanja;
    }

}
