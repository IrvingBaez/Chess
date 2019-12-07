package Players;

import View.GameView;
import Board.Move;
import Board.Position;
import Board.Position.Column;
import Pieces.Piece;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;

public class HumanPlayer extends ChessPlayer implements MouseListener{
    private GameView view;
    private JTable table;
    public static boolean boardFlipped;
    
    private int columnIndex;
    private int rowIndex;
    private Piece selectedPiece;
    
    private volatile Move move;

    public HumanPlayer() {
        this.move = null;
    }
    
    @Override
    public Move move() {
        System.out.println("HumanPlayer.move()");
        System.out.println("\t" + this + " moving.");
        this.table.addMouseListener(this);
        while (this.move == null){
            
        }
        Move holder = this.move;
        this.move = null;
        this.table.removeMouseListener(this);
        
        return holder;
    }
    
    public void setView(GameView view){
        this.view = view;
        this.table = view.getjTblBoard();
    }

    public static void setBoardFlipped(boolean boardFlipped) {
        HumanPlayer.boardFlipped = boardFlipped;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(this.game.getTurnColor() != this.playerColor){
            System.out.println("Game.mouseClicked()");
            System.out.println("\tNot this player's turn.");
            System.out.println("\tGame turn: " + this.game.getTurnColor());
            System.out.println("\tPlayer color: " + this.playerColor);
            return;
        }
        
        int col = view.getCol();
        int row = view.getRow();
        
        if(boardFlipped){
            this.columnIndex = 7 - col;
            this.rowIndex = row;
        }else{
            this.columnIndex = col;
            this.rowIndex = 7 - row;
        }
        
        Position selectedPosition = new Position(Column.values()[columnIndex], rowIndex + 1);
        
        if(this.selectedPiece == null){
            if(!(this.game.getBoard().getBoxes()[columnIndex][rowIndex].isEmpty())){
                this.selectedPiece = this.game.getBoard().getOnPosition(selectedPosition);
                System.out.println("\nPiece Selected");
                System.out.println(selectedPiece.getMoves().size() + " possible moves.");
                this.printPositions(selectedPiece.getMoves());
                System.out.println(selectedPiece.getSight().size() + " boxes threatened.");
                this.printPositions(selectedPiece.getSight());
            }
        }else{
            this.move = new Move(selectedPiece, selectedPiece.getPosition(), selectedPosition);
            this.selectedPiece = null;
            System.out.println("\nPiece erased");
        }
    }
    
    private void printPositions(ArrayList<Position> positions){
        for(Position pos: positions){
            System.out.println(pos.toString());
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public String toString() {
        return "Human Player playing " + this.playerColor;
    }
}
