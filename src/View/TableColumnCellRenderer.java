package View;



import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableColumnCellRenderer implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
        
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = RENDERER.getTableCellRendererComponent(table, "", true, true, 0, 0);
        
        if ((column + row)%2 == 1) {
            Color color = Color.BLACK;
            c.setBackground(color);
        }else{
            Color color = Color.WHITE;
            c.setBackground(color);
        }
        
        return c;
    }
}