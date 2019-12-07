package Pieces;

import Board.Position;
import java.util.ArrayList;

public class King extends Piece{
    private boolean shortCastling;
    private boolean longCastling;
    
    public King(Color color, Position position) {
        super(1000, color, "K", position);
        this.shortCastling = true;
        this.longCastling = true;
    }

    @Override
    public void fillSight() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.sight = new ArrayList<>();
        
        int row = position.getRow();
        int col = position.getColumnIndex();
        
        inSightIfValid(col + 1, row + 1);
        inSightIfValid(col + 1, row);
        inSightIfValid(col + 1, row - 1);
        inSightIfValid(col, row + 1);
        inSightIfValid(col, row - 1);
        inSightIfValid(col - 1, row + 1);
        inSightIfValid(col - 1, row);
        inSightIfValid(col - 1, row - 1);
    }
    
    public boolean isInCheck(){
        /*
        Parece que se usa más de lo necesario.
        Buscar piezas amenazantes en lugar de la lista completa puede eliminar
        el cálculo de miras en el constructor de boards.
        */
        return(rookCheck() || bishopCheck() || knightCheck() || pawnCheck() || kingCheck());
    }
    
    public void checkCastlingRights(){
        /*
        Validar y agregar ambos enroques.
        Reglas:
            1.- No se ha perdido el derecho (Mover rey, mover torre).
            2.- Casillas entre rey y torre vacías.
            3.- El rey no pasa por casillas amenazadas.
        */
    }
    
    private boolean rookCheck(){
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        
        for (int i = 1; row + i <= 8; i++) {
            Piece piece = board.getOnPosition(col, row + i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Rook || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; col + i <= 7; i++) {
            Piece piece = board.getOnPosition(col + i, row);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Rook || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; col - i >= 0; i++) {
            Piece piece = board.getOnPosition(col - i, row);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Rook || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; row - i >= 1; i++) {
            Piece piece = board.getOnPosition(col, row - i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Rook || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        return false;
    }
    
    private boolean bishopCheck(){
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        
        for (int i = 1; (col + i <= 7) && (row + i <= 8); i++) {
            Piece piece = board.getOnPosition(col + i, row + i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Bishop || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; (col - i >= 0) && (row + i <= 8); i++) {
            Piece piece = board.getOnPosition(col - i, row + i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Bishop || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; (col + i <= 7) && (row - i >= 1); i++) {
            Piece piece = board.getOnPosition(col + i, row - i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Bishop || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        for (int i = 1; (col - i >= 0) && (row - i >= 1); i++) {
            Piece piece = board.getOnPosition(col - i, row - i);
            if(piece != null){
                if (piece.color != this.color && (piece instanceof Bishop || piece instanceof Queen)){
                    return true;
                }
                break;
            }
        }
        
        return false;
    }
    
    private boolean knightCheck(){
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        Piece piece;
        
        piece = board.getOnPosition(col + 2, row + 1);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col + 2, row - 1);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col + 1, row + 2);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col + 1, row - 2);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 1, row + 2);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 1, row - 2);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 2, row + 1);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 2, row - 1);
        if (piece instanceof Knight && piece.color != this.color)
            return true;
        
        return false;
    }

    private boolean pawnCheck(){
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        Piece piece;
        
        switch (color) {
             case WHITE:
                piece = board.getOnPosition(col + 1, row + 1);
                if (piece instanceof Pawn && piece.color == Color.BLACK)
                    return true;
                
                piece = board.getOnPosition(col - 1, row + 1);
                if (piece instanceof Pawn && piece.color == Color.BLACK)
                    return true;
                break;
            case BLACK:
                piece = board.getOnPosition(col + 1, row - 1);
                if (piece instanceof Pawn && piece.color == Color.WHITE)
                    return true;
                
                piece = board.getOnPosition(col - 1, row - 1);
                if (piece instanceof Pawn && piece.color == Color.WHITE)
                    return true;
                break;
        }
        
        return false;
    }
    
    private boolean kingCheck(){
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        Piece piece;
        
        piece = board.getOnPosition(col + 1, row + 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col + 1, row);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col + 1, row - 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col, row + 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col, row - 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 1, row + 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 1, row);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        piece = board.getOnPosition(col - 1, row - 1);
        if (piece instanceof King && piece.color != this.color)
            return true;
        
        return false;
    }
    
    public void removeShortCastling() {
        this.shortCastling = false;
    }

    public void removeLongCastling() {
        this.longCastling = false;
    }
}