/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import Board.Board;
import Board.Position;
import Pieces.King;
import Pieces.Piece;

/**
 *
 * @author stamp
 */
public class Stalker extends ChessStrategy{
    
    public Stalker() {
        name = "Stalker";
    }
    
    @Override
    public double analize(Board board) {
        this.board = board;
        if(checkMate())
            return value;
        
        Piece whiteKing = board.getWhiteKing();
        Piece blackKing = board.getBlackKing();
        
        for (Piece piece : board.getPieces()) {
            if(piece instanceof King)
                continue;
            switch (piece.getColor()) {
                case WHITE:
                    value += piece.getValue()*closeness(piece.getPosition(), blackKing.getPosition());
                    break;
                case BLACK:
                    value -= piece.getValue()*closeness(piece.getPosition(), whiteKing.getPosition());
                    break;
            }
        }
        return value;
    }
    
    private double closeness(Position pos1, Position pos2){
        int row = Math.abs(pos1.getColumnIndex() - pos2.getColumnIndex());
        int col = Math.abs(pos1.getRowIndex() - pos2.getRowIndex());
        return Math.sqrt(9 - Math.max(col, row));
    }
    
}
