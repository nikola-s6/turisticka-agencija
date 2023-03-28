/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form.component.table;

import domain.Putovanje;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nikol
 */
public class PutovanjaTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Naziv", "Pocetna destinacija", "Krajnja destinacija"};
    private Class[] classes = new Class[]{Object.class, Object.class, Object.class};
    private boolean editable;
    private List<Putovanje> putovanja;

    public PutovanjaTableModel(List<Putovanje> putovanja, boolean editable) {
        this.putovanja = putovanja;
        this.editable = editable;
    }

    @Override
    public int getRowCount() {
        if (putovanja == null) {
            return 0;
        }
        return putovanja.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "n/a";
        }
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putovanje putovanje = putovanja.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return putovanje.getNaziv();
            case 1:
                return putovanje.getPocetniGrad().getNaziv() + ", " + putovanje.getPocetniGrad().getDrzava();
            case 2:
                return putovanje.getKrajnjiGrad().getNaziv() + ", " + putovanje.getKrajnjiGrad().getDrzava();
            default:
                return "n/a";
        }
    }

    public Putovanje getPutovanje(int row){
        return putovanja.get(row);
    }
    
    public List<Putovanje> getPutovanja(){
        return putovanja;
    }
}
