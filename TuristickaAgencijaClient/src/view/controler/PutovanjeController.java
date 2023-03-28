/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controler;

import com.sun.java.accessibility.util.AWTEventMonitor;
import communication.Communication;
import data.GlobalniPodaci;
import data.type.TipoviPodataka;
import domain.Grad;
import domain.Putnik;
import domain.Putovanje;
import domain.Rezervacija;
import domain.Termin;
import domain.Usputni;
import domain.util.Ponuda;
import domain.util.Prevoz;
import domain.util.Smestaj;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import view.form.FrmPutovanje;
import view.form.component.table.PutovanjaTableModel;
import view.form.component.table.RezervacijeTableModel;
import view.form.component.table.TerminiPutovanjaTableModel;
import view.form.component.table.UsputniGradoviTableModel;

/**
 *
 * @author nikol
 */
public class PutovanjeController {

    private final FrmPutovanje form;
    private List<Grad> gradovi;

    public PutovanjeController(FrmPutovanje frmPutovanje) {
        this.form = frmPutovanje;
        this.gradovi = new ArrayList<>();
        addActionListeners();
    }

    private void addActionListeners() {
        form.getBtnUcitajPutovanja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String putovanje = form.getTxtPretragaPutovanja().getText().trim();
                List<Putovanje> putovanja = ucitajPutovanja(putovanje);
                PutovanjaTableModel model = new PutovanjaTableModel(putovanja, false);
                form.getTbPretragaPutovanja().setModel(model);
            }
        });

        form.getTbPretragaPutovanja().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = form.getTbPretragaPutovanja().getSelectedRow();
                    Putovanje putovanjeNepotpuno = ((PutovanjaTableModel) form.getTbPretragaPutovanja().getModel()).getPutovanje(row);
                    Putovanje putovanje = Communication.getInstance().ucitajPutovanje(putovanjeNepotpuno);

                    populateFields(putovanje);
                    JOptionPane.showMessageDialog(form, "Sistem je ucitao putovanje", "Potvrda ucitavanja putovanja", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja putovanja", JOptionPane.ERROR_MESSAGE);

                }
            }

            private void populateFields(Putovanje putovanje) {
                form.getTxtId().setText(Integer.toString(putovanje.getPutovanjeID()));
                form.getTxtNaziv().setText(putovanje.getNaziv());
                form.getTaKratakOpis().setText(putovanje.getKratakOpis());
                form.getCbPonuda().setSelectedItem(putovanje.getPonuda());
                form.getCbPrevoz().setSelectedItem(putovanje.getPrevoz());
                form.getCbSmestaj().setSelectedItem(putovanje.getSmestaj());
                form.getCbPocetniGrad().setSelectedItem(putovanje.getPocetniGrad());
                form.getCbKrajnjiGrad().setSelectedItem(putovanje.getKrajnjiGrad());

                form.gettTermini().setModel(new TerminiPutovanjaTableModel(putovanje.getTermini(), false));
                ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).fireTableDataChanged();

                form.gettUsputniGradovi().setModel(new UsputniGradoviTableModel(putovanje.getUsputniGradovi(), false));
                ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).fireTableDataChanged();
            }
        });

        form.gettTermini().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = form.gettTermini().getSelectedRow();
                Termin termin = ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).getTermin(row);

                if (!(termin.getDatumPolaska() == null || termin.getDatumPovratka() == null || termin.getCena() == null)) {
                    try {
                        List<Rezervacija> rezervacije = Communication.getInstance().ucitajRezervacije(termin);
                        RezervacijeTableModel rezervacijeModel = new RezervacijeTableModel(rezervacije, false);
                        form.gettRezervacije().setModel(rezervacijeModel);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja rezervacija", JOptionPane.ERROR_MESSAGE);

                    }

                }
            }
        });

        form.getBtnIzmena().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields();
            }

            private void enableFields() {
                form.getTxtNaziv().setEditable(true);
                form.getTaKratakOpis().setEditable(true);
                form.getCbKrajnjiGrad().setEnabled(true);
                form.getCbPocetniGrad().setEnabled(true);
                form.getCbPonuda().setEnabled(true);
                form.getCbPrevoz().setEnabled(true);
                form.getCbSmestaj().setEnabled(true);
                form.getBtnDodajTermin().setEnabled(true);
                form.getBtnObrisiTermin().setEnabled(true);
                form.getBtnDodajUsputni().setEnabled(true);
                form.getBtnObrisiUsputni().setEnabled(true);
                form.getBtnSacuvaj().setEnabled(true);

                ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).setEditable(true);
                ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).setEditable(true);
                TableColumn tcUsputni = form.gettUsputniGradovi().getColumnModel().getColumn(0);
                JComboBox comboGradovi = new JComboBox(gradovi.toArray());
                tcUsputni.setCellEditor(new DefaultCellEditor(comboGradovi));
            }
        });

        form.getBtnDodajUsputni().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usputni u = new Usputni();
                ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).dodajUsputni(u);
            }
        });

        form.getBtnObrisiUsputni().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = form.gettUsputniGradovi().getSelectedRow();
                if (row >= 0) {
                    ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).obrisiUsputni(row);
                }
            }
        });

        form.getBtnDodajTermin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Termin termin = new Termin();
                ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).dodajTermin(termin);
            }
        });

        form.getBtnObrisiTermin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = form.gettTermini().getSelectedRow();
                if (row >= 0) {
                    ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).obrisiTermin(row);
                }
            }
        });

        form.getBtnSacuvaj().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Putovanje putovanje = createPutovanje();

                try {
                    Communication.getInstance().zapamtiPutovanje(putovanje);
                    JOptionPane.showMessageDialog(form, "Sistem je zapamtio putovanje", "Potvrda izmene putovanja", JOptionPane.INFORMATION_MESSAGE);
                    disableFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska izmene putovanja", JOptionPane.ERROR_MESSAGE);
                }
            }

            private Putovanje createPutovanje() {
                Putovanje putovanje = new Putovanje();
                putovanje.setPutovanjeID(Integer.parseInt(form.getTxtId().getText().trim()));
                putovanje.setNaziv(form.getTxtNaziv().getText().trim());
                putovanje.setKratakOpis(form.getTaKratakOpis().getText().trim());
                putovanje.setPocetniGrad((Grad) form.getCbPocetniGrad().getSelectedItem());
                putovanje.setKrajnjiGrad((Grad) form.getCbKrajnjiGrad().getSelectedItem());
                putovanje.setPonuda((Ponuda) form.getCbPonuda().getSelectedItem());
                putovanje.setPrevoz((Prevoz) form.getCbPrevoz().getSelectedItem());
                putovanje.setSmestaj((Smestaj) form.getCbSmestaj().getSelectedItem());

                List<Usputni> ug = ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).getListUsputni();
                for (int i = 0; i < ug.size(); i++) {
                    Usputni u = ug.get(i);
                    u.setPutovanje(putovanje);
                    u.setRedniBroj(i + 1);
                }

                List<Termin> termini = ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).getListaTermina();
                for (Termin termin : termini) {
                    termin.setPutovanje(putovanje);
                }

                putovanje.setUsputniGradovi(ug);
                putovanje.setTermini(termini);

                return putovanje;
            }
        });
    }

    public void openForm() {
        initComponents();
        form.setVisible(true);
    }

    private void initComponents() {
        if (!(Boolean) GlobalniPodaci.getInstance().get(TipoviPodataka.ADMIN_ACCESS)) {
            putnikHide();
        }
        ucitajListuGradova();
        resetTables();
        initComboBox();
        disableFields();
    }

    private void putnikHide() {
        form.getBtnIzmena().setVisible(false);
        form.getBtnSacuvaj().setVisible(false);
        form.getpRezervacije().setVisible(false);
        form.getBtnDodajTermin().setVisible(false);
        form.getBtnDodajUsputni().setVisible(false);
        form.getBtnObrisiTermin().setVisible(false);
        form.getBtnObrisiUsputni().setVisible(false);
    }

    private void disableFields() {
        form.getTxtId().setEditable(false);
        form.getTxtNaziv().setEditable(false);
        form.getTaKratakOpis().setEditable(false);
        form.getCbKrajnjiGrad().setEnabled(false);
        form.getCbPocetniGrad().setEnabled(false);
        form.getCbPonuda().setEnabled(false);
        form.getCbPrevoz().setEnabled(false);
        form.getCbSmestaj().setEnabled(false);
        form.getBtnDodajTermin().setEnabled(false);
        form.getBtnObrisiTermin().setEnabled(false);
        form.getBtnDodajUsputni().setEnabled(false);
        form.getBtnObrisiUsputni().setEnabled(false);
        form.getBtnSacuvaj().setEnabled(false);

        ((TerminiPutovanjaTableModel) form.gettTermini().getModel()).setEditable(false);
        ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).setEditable(false);
    }

    private void ucitajListuGradova() {
        try {
            this.gradovi = Communication.getInstance().ucitajListuGradova();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja gradova", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void resetTables() {
        List<Usputni> usputni = new ArrayList<>();
        UsputniGradoviTableModel usputniModel = new UsputniGradoviTableModel(usputni, false);
        form.gettUsputniGradovi().setModel(usputniModel);
        TableColumn tcUsputni = form.gettUsputniGradovi().getColumnModel().getColumn(0);
        JComboBox comboGradovi = new JComboBox(gradovi.toArray());
        tcUsputni.setCellEditor(new DefaultCellEditor(comboGradovi));

        List<Termin> termini = new ArrayList<>();
        TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(termini, false);
        form.gettTermini().setModel(terminiModel);

        List<Putovanje> putovanja = ucitajPutovanja("");
        PutovanjaTableModel putovanjaModel = new PutovanjaTableModel(putovanja, false);
        form.getTbPretragaPutovanja().setModel(putovanjaModel);

        List<Rezervacija> rezrvacije = new ArrayList<>();
        RezervacijeTableModel rezervacijeModel = new RezervacijeTableModel(rezrvacije, false);
        form.gettRezervacije().setModel(rezervacijeModel);
    }

    private void initComboBox() {
        form.getCbPonuda().removeAll();
        form.getCbSmestaj().removeAll();
        form.getCbPrevoz().removeAll();
        form.getCbPocetniGrad().removeAll();
        form.getCbKrajnjiGrad().removeAll();

        form.getCbPonuda().setModel(new DefaultComboBoxModel(Ponuda.values()));
        form.getCbSmestaj().setModel(new DefaultComboBoxModel(Smestaj.values()));
        form.getCbPrevoz().setModel(new DefaultComboBoxModel(Prevoz.values()));

        try {
            form.getCbPocetniGrad().setModel(new DefaultComboBoxModel(gradovi.toArray()));
            form.getCbKrajnjiGrad().setModel(new DefaultComboBoxModel(gradovi.toArray()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja gradova", JOptionPane.ERROR_MESSAGE);

        }
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
                if (putovanja.size() != 0) {
                    JOptionPane.showMessageDialog(form, "Sistem je nasao putovanja po zadatoj vrednosti", "Potvrda pretrage putnika", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Greska pretrage putovanja", JOptionPane.ERROR_MESSAGE);

        }
        if (putovanja.size() == 0) {
            JOptionPane.showMessageDialog(form, "Sistem ne moze da nadje putovanja po zadatoj vrednosti", "Greska pretrage putnika", JOptionPane.ERROR_MESSAGE);
        }
        return putovanja;
    }

}
