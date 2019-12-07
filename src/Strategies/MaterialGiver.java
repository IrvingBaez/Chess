package Strategies;

import Board.Board;
import Pieces.Piece;

public class MaterialGiver extends ChessStrategy{
    
    public MaterialGiver() {
        name = "Material Giver";
    }
    
    @Override
    public double analize(Board board) {
        this.board = board;
        if(checkMate())
            return value;
        
        for(Piece piece : board.getPieces()){
            switch (piece.getColor()) {
                case WHITE:
                    value += piece.getValue();
                    break;
                case BLACK:
                    value -= piece.getValue();
                    break;
            }
        }
        return -value;
    }
    
}
