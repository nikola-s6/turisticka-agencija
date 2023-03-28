/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controler;

import communication.Communication;
import data.GlobalniPodaci;
import data.type.TipoviPodataka;
import domain.Putnik;
import domain.util.Pol;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import view.form.FrmPrikazPutnika;
import view.form.util.FormMode;

/**
 *
 * @author nikol
 */
public class PrikazPutnikaController {

    private final FrmPrikazPutnika form;
    private FormMode mode;

    public PrikazPutnikaController(FrmPrikazPutnika f) {
        this.form = f;
    }

    public void openForm(FormMode mode) {
        this.mode = mode;
        initComboBox();
        if (!(Boolean) GlobalniPodaci.getInstance().get(TipoviPodataka.ADMIN_ACCESS)) {
//            putnik je pristupio svojim podacima
            frmPutnikInit();
        } else {
//            admin
            if (mode.equals(FormMode.FORM_ADD)) {
                frmAddInit();
            } else {
                try {
                    frmPrikazInit();
                    JOptionPane.showMessageDialog(form, "Sistem je ucitao putnika", "Potvrda ucitavanja putnika", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja putnika", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        addActionListeners(); //radim tek ovde jer moram da znam koji je mod
        form.setVisible(true);
    }

    private void frmAddInit() {
        form.getLblId().setVisible(false);
        form.getTxtId().setVisible(false);
        form.getBtnObrisi().setVisible(false);
        form.getBtnOmoguciIzmenu().setVisible(false);

        form.getTxtId().setText("");
        form.getTxtIme().setText("");
        form.getTxtPrezime().setText("");
        form.getTxtEmail().setText("");
        form.getTxtBrojTelefona().setText("");
        form.getCbPol().setSelectedItem(Pol.MUSKI);
        form.getTxtSifra().setText("");
    }

    private void frmPutnikInit() {
        form.getBtnObrisi().setVisible(false);
        form.getBtnOmoguciIzmenu().setVisible(false);
        form.getBtnSacuvaj().setVisible(false);
        form.getTxtSifra().setVisible(false);
        form.getLblSifra().setVisible(false);

        form.getTxtId().setEditable(false);
        form.getTxtIme().setEditable(false);
        form.getTxtPrezime().setEditable(false);
        form.getTxtEmail().setEditable(false);
        form.getTxtBrojTelefona().setEditable(false);
        form.getCbPol().setEnabled(false);

        Putnik putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.ULOGOVANI_KORISNIK);

        form.getTxtId().setText(Integer.toString(putnik.getPutnikID()));
        form.getTxtIme().setText(putnik.getIme());
        form.getTxtPrezime().setText(putnik.getPrezime());
        form.getTxtEmail().setText(putnik.getEmail());
        form.getTxtBrojTelefona().setText(putnik.getBrojTelefona());
        form.getCbPol().setSelectedItem(putnik.getPol());
    }

    private void frmPrikazInit() throws Exception {
        form.getBtnObrisi().setEnabled(false);
        form.getBtnSacuvaj().setEnabled(false);
        form.getTxtSifra().setVisible(false);
        form.getLblSifra().setVisible(false);

        form.getTxtId().setEditable(false);
        form.getTxtIme().setEditable(false);
        form.getTxtPrezime().setEditable(false);
        form.getTxtEmail().setEditable(false);
        form.getTxtBrojTelefona().setEditable(false);
        form.getCbPol().setEnabled(false);

        Putnik p = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.PRIKAZ_PUTNIKA);

        Putnik putnik;
        try {
            putnik = Communication.getInstance().ucitajPutnika(p);
            form.getTxtId().setText(Integer.toString(putnik.getPutnikID()));
            form.getTxtIme().setText(putnik.getIme());
            form.getTxtPrezime().setText(putnik.getPrezime());
            form.getTxtEmail().setText(putnik.getEmail());
            form.getTxtBrojTelefona().setText(putnik.getBrojTelefona());
            form.getCbPol().setSelectedItem(putnik.getPol());
//            ne zelim da mi se pojavljuje poruka i nakom izmene i zato je premestena gore da bi se prikazivala samo prvi put kada se ucita putnik
//            JOptionPane.showMessageDialog(form, "Sistem je ucitao putnika", "Potvrda ucitavanja putnika", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            throw ex;
//            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska ucitavanja putnika", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComboBox() {
        form.getCbPol().removeAll();
        form.getCbPol().setModel(new DefaultComboBoxModel(Pol.values()));
    }

    private void addActionListeners() {

        form.getBtnOmoguciIzmenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.getBtnObrisi().setEnabled(true);
                form.getBtnSacuvaj().setEnabled(true);
                enableFields();
            }

            private void enableFields() {
                form.getTxtIme().setEditable(true);
                form.getTxtPrezime().setEditable(true);
                form.getTxtEmail().setEditable(true);
                form.getTxtBrojTelefona().setEditable(true);
                form.getCbPol().setEnabled(true);
            }
        });

        form.getBtnObrisi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Putnik putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.PRIKAZ_PUTNIKA);
                try {
                    Communication.getInstance().obrisiPutnika(putnik);
                    JOptionPane.showMessageDialog(form, "Sistem je obrisao putnika", "Potvrda brisanja putnika", JOptionPane.INFORMATION_MESSAGE);
                    form.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska brisanja putnika", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        form.getBtnSacuvaj().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mode.equals(FormMode.FORM_ADD)) {
                    try {
                        validateFields(Boolean.TRUE);
                        Putnik putnik = createPutnik();
                        Communication.getInstance().zapamtiPutnika(putnik);
                        JOptionPane.showMessageDialog(form, "Sistem je zapamtio putnika", "Potvrda kreiranja putnika", JOptionPane.INFORMATION_MESSAGE);

                        Object[] options = {"Da", "Ne"};
                        int i = JOptionPane.showOptionDialog(form, "Da li zelite da nastavite sa kreiranjem novih putnika", "Kreiranje putnika", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (i == 0) {
                            frmAddInit();
                        } else {
                            form.dispose();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    try {
                        validateFields(Boolean.FALSE);
                        changeValues();
                        Communication.getInstance().zapamtiPutnika((Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.PRIKAZ_PUTNIKA));
                        JOptionPane.showMessageDialog(form, "Sistem je zapamtio putnika", "Potvrda izmene putnika", JOptionPane.INFORMATION_MESSAGE);
//                        form.dispose();
                        frmPrikazInit();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(form, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            private Putnik createPutnik() {
                Putnik putnik = new Putnik();

                putnik.setIme(form.getTxtIme().getText().trim());
                putnik.setPrezime(form.getTxtPrezime().getText().trim());
                putnik.setEmail(form.getTxtEmail().getText().trim());
                putnik.setBrojTelefona(form.getTxtBrojTelefona().getText().trim());
                putnik.setPol((Pol) form.getCbPol().getSelectedItem());
                putnik.setSifra(String.copyValueOf(form.getTxtSifra().getPassword()));

                return putnik;
            }

            private void validateFields(Boolean sifra) throws Exception {
                String errorMessage = "";
                if (form.getTxtIme().getText().isEmpty()) {
                    errorMessage += "Polje ime ne sme da bude prazno\n";
                }
                if (form.getTxtPrezime().getText().isEmpty()) {
                    errorMessage += "Polje prezime ne sme da bude prazno\n";
                }
                if (form.getTxtEmail().getText().isEmpty()) {
                    errorMessage += "Polje email ne sme da bude prazno";
                } else if (!isEmail(form.getTxtEmail().getText())) {
                    errorMessage += "Polje email moda da bude u odgovarajucem formatu\n";
                }
                if (form.getTxtBrojTelefona().getText().isEmpty()) {
                    errorMessage += "Polje broj telefona ne sme da bude prazno\n";
                }
                if (sifra) {
                    String s = String.copyValueOf(form.getTxtSifra().getPassword());
                    if (s.isEmpty()) {
                        errorMessage += "Polje sifra ne sme biti prazno";
                    }
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }

            private boolean isEmail(String string) {
                String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.]+[c]+[o]+[m]+$";
                return Pattern.compile(regexPattern).matcher(string).matches();
            }

            private void changeValues() {
                Putnik putnik = (Putnik) GlobalniPodaci.getInstance().get(TipoviPodataka.PRIKAZ_PUTNIKA);

                putnik.setIme(form.getTxtIme().getText().trim());
                putnik.setPrezime(form.getTxtPrezime().getText().trim());
                putnik.setEmail(form.getTxtEmail().getText().trim());
                putnik.setBrojTelefona(form.getTxtBrojTelefona().getText().trim());
                putnik.setPol((Pol) form.getCbPol().getSelectedItem());
            }
        });
    }
}
