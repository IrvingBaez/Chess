/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Board.Board.State;
import Board.Position;
import Board.Position.Column;
import Pieces.Piece.Color;
import Players.*;
import Strategies.*;
import View.GameView;
import View.LableRender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author stamp
 */
public class GameController implements ActionListener{
    private final GameView view;
    private final JTable table;
    private final Game game;
    private final int cellSize;
    private Color playerColor;
    
    public GameController(ChessPlayer playerOne, ChessPlayer playerTwo) {
        this.view = new GameView();
        this.table = view.getjTblBoard();
        this.cellSize = (view.getSize().height-65)/8;
        this.playerColor = Color.WHITE;
        this.alignHumanPlayers();
        
        if(playerOne instanceof HumanPlayer){
            ((HumanPlayer) playerOne).setView(this.view);
        }
        if(playerTwo instanceof HumanPlayer){
            ((HumanPlayer) playerTwo).setView(view);
        }
        
        this.game = new Game(playerOne, playerTwo);
        
        view.getjBtnFlipBoard().addActionListener(this);
        
        Long time0 = System.nanoTime();
        Long time1 = System.nanoTime();
        Long time2;
        
        do {
            prepareSpace();
            getBoardInfo();
            drawIcons();
            time2 = System.nanoTime();
            System.out.print("Moved in: " + (double)(time2 - time1)/1000000000 + " segs.\n");
            System.out.print("Total time: " + (double)(time2 - time0)/1000000000 + " segs.\n");
            if(game.getMoveNumber()%2 == 1){
                System.out.print("\nTurn: " + (game.getTurnNumber()));
            }
            time1 = time2;
            this.game.play();
        } while(game.getState() == State.LIVE || 
                game.getState() == State.WHITECHECK ||
                game.getState() == State.BLACKCHECK);
        
        prepareSpace();
        getBoardInfo();
        drawIcons();
        System.out.println(game.getState());
        game.printFENList();
        game.printMoveList();
    }
    
    private void prepareSpace(){
        table.getTableHeader().setUI(null);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(cellSize);
        
        for (int i = 0; i < 8; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);    
        }
        
        view.setVisible(true);
    }
    
    private void getBoardInfo(){
        //TableColumnCellRenderer renderer = new TableColumnCellRenderer();
        //table.setDefaultRenderer(Object.class, renderer);
        
        emptyView();
        
        for (int col = 0; col < 8; col++) {
            for (int row = 1; row <= 8; row++) {
                if(!(game.getBoard().isEmpty(new Position(Column.values()[col], row))))
                    switch (this.playerColor){
                        case WHITE:
                            table.setValueAt(game.getBoard().getOnPosition(
                                    new Position(Column.values()[col], row)).getSymbol(), 8-row, col);
                            break;
                        case BLACK:
                            table.setValueAt(game.getBoard().getOnPosition(
                                    new Position(Column.values()[col], row)).getSymbol(), row-1, 7 - col);
                    }
            }
        }
    }
    
    private void drawIcons(){
        JLabel whitePawn = new JLabel();
        whitePawn.setIcon(new ImageIcon("Pieces/WhitePawn.png"));
        
        JLabel whiteRook = new JLabel();
        whiteRook.setIcon(new ImageIcon("Pieces/WhiteRook.png"));
        
        JLabel whiteKnight = new JLabel();
        whiteKnight.setIcon(new ImageIcon("Pieces/WhiteKnight.png"));
        
        JLabel whiteBishop = new JLabel();
        whiteBishop.setIcon(new ImageIcon("Pieces/WhiteBishop.png"));
        
        JLabel whiteQueen = new JLabel();
        whiteQueen.setIcon(new ImageIcon("Pieces/WhiteQueen.png"));
        
        JLabel whiteKing = new JLabel();
        whiteKing.setIcon(new ImageIcon("Pieces/WhiteKing.png"));
        
        JLabel blackPawn = new JLabel();
        blackPawn.setIcon(new ImageIcon("Pieces/BlackPawn.png"));
        
        JLabel blackRook = new JLabel();
        blackRook.setIcon(new ImageIcon("Pieces/BlackRook.png"));
        
        JLabel blackKnight = new JLabel();
        blackKnight.setIcon(new ImageIcon("Pieces/BlackKnight.png"));
        
        JLabel blackBishop = new JLabel();
        blackBishop.setIcon(new ImageIcon("Pieces/BlackBishop.png"));
        
        JLabel blackQueen = new JLabel();
        blackQueen.setIcon(new ImageIcon("Pieces/BlackQueen.png"));
        
        JLabel blackKing = new JLabel();
        blackKing.setIcon(new ImageIcon("Pieces/BlackKing.png"));
        
         for (int col = 0; col < 8; col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(new LableRender());
            for (int row = 0; row < 8; row++) {
                switch(table.getValueAt(col, row).toString()){
                    case "Pw":
                        table.setValueAt(whitePawn, col, row);
                        break;
                    case "Rw":
                        table.setValueAt(whiteRook, col, row);
                        break;
                    case "Nw":
                        table.setValueAt(whiteKnight, col, row);
                        break;
                    case "Bw":
                        table.setValueAt(whiteBishop, col, row);
                        break;
                    case "Qw":
                        table.setValueAt(whiteQueen, col, row);
                        break;
                    case "Kw":
                        table.setValueAt(whiteKing, col, row);
                        break;
                    case "Pb":
                        table.setValueAt(blackPawn, col, row);
                        break;
                    case "Rb":
                        table.setValueAt(blackRook, col, row);
                        break;
                    case "Nb":
                        table.setValueAt(blackKnight, col, row);
                        break;
                    case "Bb":
                        table.setValueAt(blackBishop, col, row);
                        break;
                    case "Qb":
                        table.setValueAt(blackQueen, col, row);
                        break;
                    case "Kb":
                        table.setValueAt(blackKing, col, row);
                        break;
                }
            }
        }
    }
    
    private void emptyView(){
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                table.setValueAt("", col, row);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.view.getjBtnFlipBoard()){
            this.changePlayerView();
            this.getBoardInfo();
        }
    }
    
    private void changePlayerView(){
        switch(this.playerColor){
            case WHITE:
                this.playerColor = Color.BLACK;
                break;
            case BLACK:
                this.playerColor = Color.WHITE;
        }
        this.alignHumanPlayers();
    }
    
    private void alignHumanPlayers(){
        switch (this.playerColor){
            case WHITE:
                HumanPlayer.setBoardFlipped(false);
                break;
            case BLACK:
                HumanPlayer.setBoardFlipped(true);
        }
    }
    
    public static void main(String[] args) {
        ChessPlayer player1 = new HumanPlayer();
        //ChessPlayer player2 = new HumanPlayer();
        
        //ChessPlayer player1 = new RandomMover();
        //ChessPlayer player2 = new RandomMover();

        //ChessPlayer player1 = new ComputerMinimax(2, new MaterialThenSpace());
        
        
        ChessPlayer player2 = new AlphaBetaPruning(3, new MaterialGrabber());
        //ChessPlayer player2 = new ComputerMinimax(3, new MaterialGrabber());
        
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n\nGAME " + i + "\n");
            GameController control = new GameController(player1, player2);
            control.view.setVisible(false);
        }
    }
}