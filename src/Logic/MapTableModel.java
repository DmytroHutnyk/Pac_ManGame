package Logic;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MapTableModel extends AbstractTableModel {
    private JPanel[][] elements;

    public MapTableModel(JPanel[][] elements) {
        this.elements = elements;
    }

    @Override
    public int getRowCount() {
        return elements.length;
    }

    @Override
    public int getColumnCount() {
        return elements[1].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return elements[rowIndex][columnIndex];
    }
}
