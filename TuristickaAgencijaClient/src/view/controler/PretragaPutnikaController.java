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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import view.coordinator.Coordinator;
import view.form.FrmPretragaPutnika;
import view.form.component.table.PretragaPutnikaTableModel;

/**
 *
 * @author nikol
 */
public class PretragaPutnikaController {

    private final FrmPretragaPutnika form;

    public PretragaPutnikaController(FrmPretragaPutnika f) {
        this.form = f;
        addActionListeners();
    }

    public void openForm() {
        prepareView();
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnPretrazi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imePrezime = form.getTxtImePrezime().getText().trim();
                List<Putnik> putnici = pronadjiPutnike(imePrezime);
                PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
                form.getTbPutnici().setModel(model);
            }
        });

        form.getBtnDetalji().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = form.getTbPutnici().getSelectedRow();
                if (row >= 0) {
                    Putnik putnik = ((PretragaPutnikaTableModel) form.getTbPutnici().getModel()).getPutnik(row);
                    GlobalniPodaci.getInstance().add(TipoviPodataka.PRIKAZ_PUTNIKA, putnik);
                    Coordinator.getInstance().openPrikazPutnika();
                    ((PretragaPutnikaTableModel) form.getTbPutnici().getModel()).fireTableDataChanged();
                }
            }
        });
    }

    private void prepareView() {
        List<Putnik> putnici = pronadjiPutnike("");
        PretragaPutnikaTableModel model = new PretragaPutnikaTableModel(putnici, false);
        form.getTbPutnici().setModel(model);
    }

    private List<Putnik> pronadjiPutnike(String string) {
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
                if (putnici.size() == 0) {
                    JOptionPane.showMessageDialog(form, "Sistem ne moze da nadje putnike po zadatoj vrednosti", "Greska pretrage putnika", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form, "Sistem je nasao putnike po zadatoj vrednosti", "Potvrda pretrage putnika", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Greska pretrage putnika", JOptionPane.ERROR_MESSAGE);
        }
        return putnici;
    }

}
