package Board;

import Pieces.Piece;

public class Box {
    private Piece piece;
    private boolean empty;
    
    public Box(){
        this.empty = true;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.empty = false;
    }
    
    public void removePiece(){
        this.piece = null;
        this.empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }
}