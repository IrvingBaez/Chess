/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

import Board.Board;

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
    
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
