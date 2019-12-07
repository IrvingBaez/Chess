package Strategies;

import Board.Board;

public class SpaceGrabber extends ChessStrategy{

    public SpaceGrabber() {
        name = "Space Grabber";
    }
    
    @Override
    public double analize(Board board) {
        this.board = board;
        if(checkMate())
            return value;
        
        value += board.getWhiteSight().size();
        value -= board.getBlackSight().size();
        
        return value;
    }    
}