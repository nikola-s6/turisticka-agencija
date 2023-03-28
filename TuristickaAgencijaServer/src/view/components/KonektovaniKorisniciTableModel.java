/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.components;

import domain.Putnik;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nikol
 */
public class KonektovaniKorisniciTableModel extends AbstractTableModel {

    private List<Putnik> ulogovaniKorisnici;
    private String[] kolone = new String[]{"Email", "Vreme konektovanja"};
    private Map<Putnik, Date> timestamp;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static KonektovaniKorisniciTableModel instance;

    private KonektovaniKorisniciTableModel() {
        ulogovaniKorisnici = new ArrayList<>();
        timestamp = new HashMap<>();
    }

    public static KonektovaniKorisniciTableModel getInstance() {
        if (instance == null) {
            instance = new KonektovaniKorisniciTableModel();
        }
        return instance;
    }

    @Override
    public int getRowCount() {
        return ulogovaniKorisnici.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putnik putnik = ulogovaniKorisnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return putnik.getEmail();
            case 1:
                return format.format(timestamp.get(putnik));
            default:
                throw new AssertionError();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    

    public void dodajKorisnikaUTabelu(Putnik putnik) {
        ulogovaniKorisnici.add(putnik);
        timestamp.put(putnik, new Date());
        fireTableDataChanged();
    }

    public void izbrisiKorisnikaIzTabele(Putnik putnik) {
        ulogovaniKorisnici.remove(putnik);
        timestamp.remove(putnik);
        fireTableDataChanged();
    }

}
