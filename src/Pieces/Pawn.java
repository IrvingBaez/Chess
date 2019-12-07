package Pieces;

import Board.Board;
import Board.Move;
import Board.Position;
import Board.Position.Column;
import java.util.ArrayList;

public class Pawn extends Piece{
    
    public Pawn(Color color, Position position) {
        super(1, color, "P", position);
    }
    
    @Override
    public void fillSight() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.sight = new ArrayList<>();
        
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        
        switch(this.color){
            case WHITE:
                inSightIfValid(col + 1, row + 1);
                inSightIfValid(col - 1, row + 1);
                break;
            case BLACK:
                inSightIfValid(col + 1, row - 1);
                inSightIfValid(col - 1, row - 1);
        }
    }

    @Override
    public void fillMoves() {
        if(board.getWhiteKing() == null || board.getBlackKing() == null){
            return;
        }
        
        this.moves = new ArrayList<>();
        
        int row = this.position.getRow();
        int col = this.position.getColumnIndex();
        
        for(Position pos : this.sight){
            if(!board.isEmpty(pos) && board.getOnPosition(pos).getColor() != color && !(board.getOnPosition(pos) instanceof King)){
                this.addMove(pos);
            }
            if(board.getEnPassantPos() != null && board.getEnPassantPawn().color != this.color && board.getEnPassantPos().equals(pos)){
                this.addMove(pos);
            }
        }
        
        switch(this.color){
            case WHITE:
                canStep(col, row + 1);
                
                if(this.position.getRowIndex() == 1){
                    if(board.isEmpty(new Position(Column.values()[col], 3))){
                        canStep(col, 4);
                    }
                }
                
                break;
            case BLACK:
                canStep(col, row - 1);
                
                if(this.position.getRowIndex() == 6){
                    if(board.isEmpty(new Position(Column.values()[col], 6))){
                        canStep(col, 5);
                    }
                }
                break;
        }
    }
    
    private boolean canStep(int col, int row){
        if(col < 0 || col > 7 || row < 1 || row > 8){
            return false;
        }
        
        Position pos = new Position(Column.values()[col], row);
        
        if(this.board.isEmpty(pos)){
            this.addMove(pos);
            return true;
        }else{
            return false;
        }
    }
    
    private void addMove(Position pos){
        Board boardCopy = this.board.copyAndMove(new Move(this, this.position, pos));
        
        String location = "Pawn.addmove()\n\t";
        
        if(this.board == null){
            System.out.println(location + "Board is null.");
        }
        if(boardCopy == null){
            System.out.println(location + "boardCopy is null.");
        }
        if(board.getBlackKing() == null || board.getWhiteKing() == null){
            System.out.println(location + "a King in board is null.");
        }
        if(boardCopy.getBlackKing() == null || boardCopy.getWhiteKing() == null){
            System.out.println(location + "a King in boardCopy is null.");
        }
        
        switch (this.color) {
            case WHITE:
                if(!boardCopy.getWhiteKing().isInCheck()){
                    this.moves.add(pos);
                }
                break;
            case BLACK:
                if(!boardCopy.getBlackKing().isInCheck()){
                    this.moves.add(pos);
                }
                break;
        }
    }
}