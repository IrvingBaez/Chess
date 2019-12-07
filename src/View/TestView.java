/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author stamp
 */
public class TestView {
    public static void main(String[] args) {
        GameView view = new GameView();
        JTable table = view.getjTblBoard();
        int cellSize = (view.getSize().height-65)/8;
        
        table.getTableHeader().setUI(null);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(cellSize);
        
        for (int i = 0; i < 8; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);    
        }
        
        view.setVisible(true);
    }
}
