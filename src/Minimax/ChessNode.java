/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

import Board.Board;
import Board.Move;
import Board.Position;
import Pieces.Piece;

/**
 *
 * @author Usuario
 */
public class ChessNode extends NodeWithID{
    private Board board;

    public ChessNode(Board board) {
        super();
        this.board = board;
    }

    public ChessNode(Comparable value, Object id, Board board) {
        super(value, id);
        this.board = board;
    }

    public ChessNode(NodeWithID parent, Comparable value, Object id, Board board) {
        super(parent, value, id);
        this.board = board;
    }
    
    public void fillBranches(){
        this.board.fillMoves();
        for(Piece piece : this.board.getPieces()){
            if(piece.getColor() != this.board.getTurn())
                continue;
            for(Position pos : piece.getMoves()){
                Move move = new Move(piece, piece.getPosition(), pos);
                Board newBoard = board.copyAndMove(move);
                ChessNode newBranch = new ChessNode(null, move, newBoard);
                this.addBranch(newBranch);
                newBranch.setParent(this);
            }
        }
    }
    
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
