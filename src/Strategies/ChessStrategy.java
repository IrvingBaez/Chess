package Strategies;

import Board.Board;

public abstract class ChessStrategy {
    protected Board board;
    protected Double value;
    protected String name = "";
    
    public abstract double analize(Board board);
    
    protected boolean checkMate(){
        this.value = (double)0;
        
        if(board.getBlackMoves().isEmpty() && board.getBlackKing().isInCheck()){
            value = Double.POSITIVE_INFINITY;
            return true;
        }
        
        if(board.getWhiteMoves().isEmpty() && board.getWhiteKing().isInCheck()){
            value = Double.NEGATIVE_INFINITY;
            return true;
        }
        
        switch (board.getTurn()){
            case WHITE:
                if(board.getWhiteMoves().isEmpty() && !board.getWhiteKing().isInCheck()){
                    value = 0.0;
                    return true;
                }
            case BLACK:
                if(board.getBlackMoves().isEmpty() && !board.getBlackKing().isInCheck()){
                    value = 0.0;
                    return true;
                }
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "Strategy: " + name;
    }
}