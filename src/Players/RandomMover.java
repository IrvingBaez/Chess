/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import Board.Move;
import Board.Position;
import Pieces.Piece;
import Pieces.Piece.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author stamp
 */
public class RandomMover extends ChessPlayer {

    @Override
    public Move move() {
        ArrayList<Piece> pieces = this.game.getBoard().getPieces();
        ArrayList<Move> moves = new ArrayList<>();
        Move moving;
        Random random = new Random();
        
        switch (playerColor) {
            case WHITE:
                for(Piece piece : pieces){
                    if(piece.getColor() == Color.WHITE){
                        for(Position pos : piece.getMoves()){
                            moves.add(new Move(piece, piece.getPosition(), pos));
                        } 
                    }
                }
                break;
            case BLACK:
                 for(Piece piece : pieces){
                    if(piece.getColor() == Color.BLACK){
                        for(Position pos : piece.getMoves()){
                            moves.add(new Move(piece, piece.getPosition(), pos));
                        } 
                    }
                }   
        }
        
        moving = moves.get(random.nextInt(moves.size()));
        return moving;
    }

    @Override
    public String toString() {
        return "Random Mover playing " + this.playerColor;
    }
}
