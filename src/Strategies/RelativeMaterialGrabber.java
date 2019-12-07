/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import Board.Board;
import Pieces.Piece;

/**
 *
 * @author stamp
 */
public class RelativeMaterialGrabber extends ChessStrategy{
    
    public RelativeMaterialGrabber() {
        name = "Relative Material Grabber";
    }
    
    @Override
    public double analize(Board board) {
        this.board = board;
        if(checkMate())
            return value;
        
        double white = 0;
        double black = 0;
        
        for(Piece piece : board.getPieces()){
            switch (piece.getColor()) {
                case WHITE:
                    white += piece.getValue();
                    break;
                case BLACK:
                    black += piece.getValue();
                    break;
            }
        }
        return white/black;
    }
}