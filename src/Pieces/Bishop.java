package Pieces;

import Board.Position;
import Board.Position.Column;
import java.util.ArrayList;

public class Bishop extends Piece{

    public Bishop(Color color, Position position) {
        super(3, color, "B", position);
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
            if(!inSightIfValid(col + i, row + i)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col + i, row - i)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col - i, row + i)){
                break;
            }
        }
        
        for (int i = 1; i <= 8; i++) {
            if(!inSightIfValid(col - i, row - i)){
                break;
            }
        }
    }
}