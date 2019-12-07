package Pieces;

import Board.Board;
import Board.Move;
import Board.Position;
import Board.Position.Column;
import java.util.ArrayList;

public abstract class Piece implements Comparable{
    protected int value;
    protected Color color;
    protected String symbol;
    protected Board board;
    protected Position position;
    protected ArrayList<Position> sight;
    protected ArrayList<Position> moves;
    
    public enum Color {
        BLACK, WHITE
    }
    
    public Piece(int value, Color color, String symbol, Position position) {
        this.value = value;
        this.color = color;
        this.position = position;
        this.sight = new ArrayList<>();
        this.moves = new ArrayList<>();
        
        switch (this.color){
            case WHITE:
                symbol += "w";
                break;
            case BLACK:
                symbol += "b";
        }
        this.symbol = symbol;
    }
    
    public abstract void fillSight();
    
    protected boolean inSightIfValid (int col, int row){
        if(col < 0 || col > 7 || row < 1 || row > 8){
            return false;
        }
        
        Position pos = new Position(Column.values()[col], row);
        this.sight.add(pos);
        
        if(board.isEmpty(pos)){
            return true;
        }
        
        return false;
    }
    
    public void fillMoves(){
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.moves = new ArrayList<>();
        Board boardCopy = Board.copyBoard(this.board);
        Position currentPos = this.position;
        Piece takenPiece;
        boolean validMove;
        
        for(Position pos : this.sight){
            validMove = true;
            takenPiece = null;
            boardCopy.resetEnPassant();
            
            if(!boardCopy.isEmpty(pos)){
                takenPiece = boardCopy.getOnPosition(pos);
                if(takenPiece.getColor() == this.color || takenPiece instanceof King)
                    continue;
            }

            boardCopy.move(new Move(this, currentPos, pos));
            
            switch (this.color) {
                case WHITE:
                    if(boardCopy.getWhiteKing().isInCheck()){
                        validMove = false;
                    }
                    break;
                case BLACK:
                    if(boardCopy.getBlackKing().isInCheck()){
                        validMove = false;
                    }
                    break;
            }
            
            boardCopy.move(new Move(this, pos, currentPos));
            if(takenPiece != null){
                boardCopy.getPieces().add(takenPiece);
                boardCopy.getBoxes()[pos.getColumnIndex()][pos.getRowIndex()].setPiece(takenPiece);
            }
            
            if(validMove){
                moves.add(pos);
            }
        }
    }
    
    public boolean movesContains(Position position){
        for(Position pos : this.moves){
            if(pos.equals(position)){
                return true;
            }
        }    
        return false;
    }
    
    public boolean sightContains(Position position){
        for(Position pos : this.sight){
            if(pos.equals(position)){
                return true;
            }
        }    
        return false;
    }
    
    public void printValidMoves(){
        this.sight.forEach((pos) -> {
            System.out.println(pos.toString());
        });
    }
    
    public boolean equals(Piece piece){
        if(!(piece.position.equals(this.position))){
            return false;
        }
        
        if (!(piece.getClass().equals(this.getClass()))){
            return false;
        }
        
        return piece.color == this.color;
    }
    
    @Override
    public int compareTo(Object t) {
        Piece p = (Piece)t;
        return(value - p.value);
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.sight = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Position> getSight() {
        return sight;
    }

    public ArrayList<Position> getMoves() {
        return moves;
    }
    
    @Override
    public String toString() {
        return (this.symbol + " in " + this.position);
    }
}