/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Position;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(3, color, "N", position);
    }

    @Override
    public void fillSight() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.sight = new ArrayList<>();
        
        int row = this.position.getRow();
        int column = this.position.getColumnIndex();
        
        inSightIfValid(column + 2, row + 1);
        inSightIfValid(column + 2, row - 1);
        
        inSightIfValid(column + 1, row + 2);
        inSightIfValid(column + 1, row - 2);
        
        inSightIfValid(column - 1, row + 2);
        inSightIfValid(column - 1, row - 2);
        
        inSightIfValid(column - 2, row + 1);
        inSightIfValid(column - 2, row - 1);
    }
}
