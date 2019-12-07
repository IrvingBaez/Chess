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
public class Rook extends Piece{
    public Rook(Color color, Position position) {
        super(5, color, "R", position);
    }

    @Override
    public void fillSight() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.sight = new ArrayList<>();
        
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col, row + i)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col, row - i)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col + i, row)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col - i, row)){
                break;
            }
        }
    }
}
