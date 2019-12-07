/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import Board.Board;

/**
 *
 * @author stamp
 */
public class MaterialThenSpace extends ChessStrategy{

    public MaterialThenSpace() {
        name = "Material Then Space";
    }
    
    @Override
    public double analize(Board board) {
        this.board = board;
        if(checkMate())
            return value;
        
        value += new MaterialGrabber().analize(board);
        value += (new SpaceGrabber().analize(board))/100;
        
        return value;
    }
}
