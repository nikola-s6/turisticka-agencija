/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controler;

import communication.Communication;
import domain.Grad;
import domain.Putovanje;
import domain.Termin;
import domain.Usputni;
import domain.util.Ponuda;
import domain.util.Prevoz;
import domain.util.Smestaj;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.TableModel;
import javax.swing.text.TableView;
import view.form.FrmKreiranjePutovanja;
import view.form.component.table.TerminiPutovanjaTableModel;
import view.form.component.table.UsputniGradoviTableModel;

/**
 *
 * @author nikol
 */
public class KreiranjePutovanjaController {

    private final FrmKreiranjePutovanja form;
//    private List<Usputni> usputni;
//    private List<Termin> termini;
    private List<Grad> gradovi;

    public KreiranjePutovanjaController(FrmKreiranjePutovanja frmKreiranjePutovanja) {
        this.form = frmKreiranjePutovanja;
//        this.termini = new ArrayList<>();
//        this.usputni = new ArrayList<>();
        this.gradovi = new ArrayList<>();
        addActionListeners();
    }

    private void addActionListeners() {
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
                    JOptionPane.showMessageDialog(form, "Sistem je zapamtio putovanje", "Potvrda kreiranja putovanja", JOptionPane.INFORMATION_MESSAGE);

                    Object[] options = {"Da", "Ne"};
                    int i = JOptionPane.showOptionDialog(form, "Da li zelite da nastavite sa kreiranjem novih putovanja", "Kreiranje putovanja", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (i == 0) {
                        initComponents();
                    } else {
                        form.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska kreiranja putovanja", JOptionPane.ERROR_MESSAGE);

                }

            }

            private Putovanje createPutovanje() {
                Putovanje putovanje = new Putovanje();
                putovanje.setNaziv(form.getTxtNaziv().getText().trim());
                putovanje.setKratakOpis(form.getTaKratakOpis().getText().trim());
                putovanje.setPocetniGrad((Grad) form.getCbPocetniGrad().getSelectedItem());
                putovanje.setKrajnjiGrad((Grad) form.getCbKrajnjiGrad().getSelectedItem());
                putovanje.setPonuda((Ponuda) form.getCbPonuda().getSelectedItem());
                putovanje.setPrevoz((Prevoz) form.getCbPrevoz().getSelectedItem());
                putovanje.setSmestaj((Smestaj) form.getCbSmestaj().getSelectedItem());
                
                List<Usputni> ug = ((UsputniGradoviTableModel) form.gettUsputniGradovi().getModel()).getListUsputni();
                for(int i=0; i<ug.size();i++){
                    Usputni u = ug.get(i);
                    u.setPutovanje(putovanje);
                    u.setRedniBroj(i+1);
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
        ucitajListuGradova();
        resetFields();
        resetTables();
        initComboBox();
    }

    private void ucitajListuGradova() {
        try {
            this.gradovi = Communication.getInstance().ucitajListuGradova();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja gradova", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void resetTables() {
//        usputni.clear();
//        termini.clear();
        List<Usputni> usputni = new ArrayList<>();
        UsputniGradoviTableModel usputniModel = new UsputniGradoviTableModel(usputni, true);
        form.gettUsputniGradovi().setModel(usputniModel);
        TableColumn tcUsputni = form.gettUsputniGradovi().getColumnModel().getColumn(0);
        JComboBox comboGradovi = new JComboBox(gradovi.toArray());
        tcUsputni.setCellEditor(new DefaultCellEditor(comboGradovi));

        List<Termin> termini = new ArrayList<>();
        TerminiPutovanjaTableModel terminiModel = new TerminiPutovanjaTableModel(termini, true);
        form.gettTermini().setModel(terminiModel);
    }

    private void resetFields() {
        form.getTxtNaziv().setText("");
        form.getTaKratakOpis().setText("");
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
}
