package Pieces;

import Board.Position;
import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(Color color, Position position) {
        super(9, color, "Q", position);
    }

    @Override
    public void fillSight() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.sight = new ArrayList<>();
        
        this.checkBishop();
        this.checkRook();
    }
    
    private void checkBishop(){
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
    
    private void checkRook(){
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
