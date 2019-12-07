package Board;

import Exceptions.PieceException;
import Pieces.Piece;

public class Move {
    private final Piece piece;
    private final Position origen;
    private final Position destination;

    public Move(Piece piece, Position origen, Position destination) {
        if(piece == null){
            throw new PieceException("Null piece: " + this);
        }
        
        this.piece = piece;
        this.origen = origen;
        this.destination = destination;
    }
    
    public boolean equals(Move move){
        if(this.piece != move.piece){
            return false;
        }
        return this.destination.equals(move.destination);
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getOrigen() {
        return origen;
    }

    public Position getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Moving " + piece.getSymbol().charAt(0) +
                " From: " + origen + 
                " To: " + destination;
    }
}