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
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import view.coordinator.Coordinator;
import view.form.FrmLogin;

/**
 *
 * @author nikol
 */
public class LoginController {

    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
    }

    private void addActionListener() {
        frmLogin.getBtnPrijava().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }

            private void login() {
                resetErrors();
                try {
                    String email = frmLogin.getTxtEmail().getText().trim();
                    String sifra = String.copyValueOf(frmLogin.getTxtSifra().getPassword());

                    validateForm(email, sifra);

                    Putnik putnik = Communication.getInstance().logovanje(email, sifra);
                    JOptionPane.showMessageDialog(
                            frmLogin,
                            putnik.getIme() + ", uspesno ste se prijavili na sistem!",
                            "Uspesno logovanje", JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    frmLogin.dispose();
                    GlobalniPodaci.getInstance().add(TipoviPodataka.ULOGOVANI_KORISNIK, putnik);
                    if(putnik.getEmail().equals("admin@gmail.com")){
                        GlobalniPodaci.getInstance().add(TipoviPodataka.ADMIN_ACCESS, true);
                    }else{
                        GlobalniPodaci.getInstance().add(TipoviPodataka.ADMIN_ACCESS, false);
                    }
                    Coordinator.getInstance().openMainForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmLogin, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void resetErrors() {
                frmLogin.getGreskaEmail().setText("");
                frmLogin.getGreskaSifra().setText("");
            }

            private void validateForm(String email, String sifra) throws Exception {
                String errorMessage = "";
                if (email.isEmpty()) {
                    frmLogin.getGreskaEmail().setText("Polje email ne sme da bude prazno!");
                    errorMessage += "Polje email ne sme da bude prazno!\n";
                } else if (!isEmail(email)) {
                    frmLogin.getGreskaEmail().setText("Email mora da bude napisan u odgovarajucem formatu!");
                    errorMessage += "Email mora da bude napisan u odgovarajucem formatu!\n";
                }
                if (sifra.isEmpty()) {
                    frmLogin.getGreskaSifra().setText("Polje sifra ne sme da bude prazno!");
                    errorMessage += "Polje sifra ne sme da bude prazno!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }

            private boolean isEmail(String string) {
                String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.]+[c]+[o]+[m]+$";
                return Pattern.compile(regexPattern).matcher(string).matches();
            }
        });
    }

}
