/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form.component.table;

import domain.Putnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nikol
 */
public class PretragaPutnikaTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Ime", "Prezime", "Email"};
    private final List<Putnik> putnici;
    private boolean editable;

    public PretragaPutnikaTableModel(List<Putnik> putnici, boolean editable) {
        for(int i = 0; i < putnici.size(); i++){
            if(putnici.get(i).getIme().equals("admin")){
                putnici.remove(i);
                break;
            }
        }
        this.putnici = putnici;
        this.editable = editable;
    }

    @Override
    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "n/a";
        }
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        if (putnici == null) {
            return 0;
        }
        return putnici.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putnik putnik = putnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return putnik.getPutnikID();
            case 1:
                return putnik.getIme();
            case 2:
                return putnik.getPrezime();
            case 3:
                return putnik.getEmail();
            default:
                return "n/a";
        }
    }

    public void dodajPutnika(Putnik putnik) {
        putnici.add(putnik);
        fireTableRowsInserted(putnici.size() - 1, putnici.size() - 1);
    }

    public Putnik getPutnik(int row) {
        return putnici.get(row);
    }

}
