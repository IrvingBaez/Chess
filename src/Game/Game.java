package Game;

import Board.Board;
import Board.Board.State;
import Board.Box;
import Board.Move;
import Board.Position;
import Board.Position.Column;
import Pieces.*;
import Pieces.Piece.Color;
import Players.ChessPlayer;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Game {
    private Board board;
    private final ArrayList<Piece> pieces;
    private Piece.Color turnColor;
    private State state;
    
    private int moveNumber;
    private int halfMoves;
    private final ArrayList<String> listOfFEN = new ArrayList<>();
    private final ArrayList<Move> listOfMoves = new ArrayList<>();
    
    private final ChessPlayer playerOne;
    private final ChessPlayer playerTwo;
    
    public Game(ChessPlayer playerOne, ChessPlayer playerTwo){
        this.moveNumber = 1;
        this.halfMoves = 0;
        this.pieces = new ArrayList<>();
        
        this.turnColor = Piece.Color.WHITE;
        this.state = State.LIVE;
        
        this.playerOne = playerOne;
        this.playerOne.setPlayerColor(Color.WHITE);
        this.playerTwo = playerTwo;
        this.playerTwo.setPlayerColor(Color.BLACK);
        
        this.setInitialPosition();
        this.board = new Board(Piece.Color.WHITE, pieces);
        this.board.setTurn(Color.WHITE);
        this.addToFENList(this.generateFEN());
        
        playerOne.setGame(this);
        playerTwo.setGame(this);
    }
    
    public Game(ChessPlayer playerOne, ChessPlayer playerTwo, String fen){
        StringTokenizer tokenizer = new StringTokenizer(fen, " ");
        
        String boardFen = tokenizer.nextToken();
        String turn = tokenizer.nextToken();
        String castlingRights = tokenizer.nextToken();
        String enPassant = tokenizer.nextToken();
        String halfMovesFen = tokenizer.nextToken();
        String turnNumber = tokenizer.nextToken();
        
        this.moveNumber = 2 * Integer.valueOf(turnNumber);
        this.halfMoves = Integer.valueOf(halfMovesFen);
        this.pieces = new ArrayList<>();
        
        this.playerOne = playerOne;
        this.playerOne.setPlayerColor(Color.WHITE);
        this.playerTwo = playerTwo;
        this.playerTwo.setPlayerColor(Color.BLACK);
        
        this.board = new Board(boardFen);
        
        switch(turn){
            case "w":
                this.board.setTurn(Color.WHITE);
                break;
            case "b":
                this.board.setTurn(Color.BLACK);
                break;
        }
        
        this.turnColor = board.getTurn();
        if(this.turnColor == Color.WHITE)
            moveNumber++;
        this.addToFENList(this.generateFEN());
        
        playerOne.setGame(this);
        playerTwo.setGame(this);
    }
    
    public void play(){
        this.findState();
        
        if(state == State.LIVE || 
                state == State.WHITECHECK ||
                state == State.BLACKCHECK){
            switch(this.turnColor){
                case WHITE:
                    System.out.println(playerOne);
                    this.move(this.playerOne.move());
                    break;
                case BLACK:
                    System.out.println(playerTwo);
                    this.move(this.playerTwo.move());
            }
        }
        if(this.getTurnNumber() == 100){
            this.state = State.INVALID;
        }
    }
    
    private void move(Move move){
        Piece piece = move.getPiece();
        Position destination = move.getDestination();
        
        if(piece.getColor() == turnColor && piece.movesContains(destination)){
            if(piece instanceof Pawn || !board.isEmpty(move.getDestination())){
                halfMoves = 0;
            }else{
                this.halfMoves++;
            }
            this.board.move(new Move(piece, piece.getPosition(), destination));
            this.changeTurn();
            this.addToFENList(this.generateFEN());
            this.listOfMoves.add(move);
            this.moveNumber++;
        }
    }
    
    private void changeTurn(){
        switch (turnColor) {
            case WHITE:
                this.turnColor = Color.BLACK;
                this.board.setTurn(Color.BLACK);
                break;
            case BLACK:
                this.turnColor = Color.WHITE;
                this.board.setTurn(Color.WHITE);
                break;
        }
    }
    
    private void setInitialPosition(){
        //Pawns
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(Color.WHITE, new Position(Column.values()[i], 2)));
            pieces.add(new Pawn(Color.BLACK, new Position(Column.values()[i], 7)));
        }
        
        //Rooks
        pieces.add(new Rook(Color.WHITE, new Position(Column.a, 1)));
        pieces.add(new Rook(Color.WHITE, new Position(Column.h, 1)));
        pieces.add(new Rook(Color.BLACK, new Position(Column.a, 8)));
        pieces.add(new Rook(Color.BLACK, new Position(Column.h, 8)));
        
        //Knights
        pieces.add(new Knight(Color.WHITE, new Position(Column.b, 1)));
        pieces.add(new Knight(Color.WHITE, new Position(Column.g, 1)));
        pieces.add(new Knight(Color.BLACK, new Position(Column.b, 8)));
        pieces.add(new Knight(Color.BLACK, new Position(Column.g, 8)));
        
        //Bishops
        pieces.add(new Bishop(Color.WHITE, new Position(Column.c, 1)));
        pieces.add(new Bishop(Color.WHITE, new Position(Column.f, 1)));
        pieces.add(new Bishop(Color.BLACK, new Position(Column.c, 8)));
        pieces.add(new Bishop(Color.BLACK, new Position(Column.f, 8)));
        
        //Royalty
        pieces.add(new Queen(Color.WHITE, new Position(Column.d, 1)));
        pieces.add(new King(Color.WHITE, new Position(Column.e, 1)));
        pieces.add(new Queen(Color.BLACK, new Position(Column.d, 8)));
        pieces.add(new King(Color.BLACK, new Position(Column.e, 8)));
    }
    
    private void addToFENList(String fen){
        int repetitions = 0;
        
        String fenBoard = fen.substring(0, fen.length()-3);
        for(String s : listOfFEN){
            s = s.substring(0, s.length()-3);
            
            if(s.equals(fenBoard)){
                repetitions++;
            }
        }
        
        listOfFEN.add(fen);
        
        if(repetitions >= 2){
            System.out.println("\nTRIPLE REPETITION");
            this.state = State.STALEMATE;
        }
    }
    
    public void printFENList() {
        this.listOfFEN.forEach((fen) -> {
            System.out.println(fen);
        });
    }
    
    public void printMoveList(){
        int counter = 1;
        Piece piece;
        
        for (Move move : listOfMoves) {
            piece = move.getPiece();
            
            if(piece.getColor() == Color.WHITE){
                System.out.print("\n" + counter + ". ");
            }else{
                System.out.print(" ");
                counter++;
            }
            
            if(!(piece instanceof Pawn)){
                System.out.print(piece.getSymbol().substring(0, 1));
            }
            System.out.print(move.getDestination());
        }
    }

    private String generateFEN(){
        String fen = "";
        Piece piece;
        int empty = 0;
        Box[][] boxes = board.getBoxes();
        
        //Board
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if(boxes[col][row].isEmpty()){
                    empty++;
                }else{
                    piece = boxes[col][row].getPiece();
                    if (empty > 0){
                        fen += empty;
                        empty = 0;
                    }
                    if (piece.getColor() == Color.BLACK){
                        fen += piece.getSymbol().substring(0, 1).toLowerCase();
                    }else{
                        fen += piece.getSymbol().substring(0, 1);
                    }
                }
            }
            if (empty > 0){
                fen += empty;
                empty = 0;
            }
            if(row > 0) fen += "/";
        }
        
        //turn
        switch (this.turnColor) {
            case WHITE:
                fen += " w";
                break;
            case BLACK:
                fen += " b";
                break;
        }
        
        //Castling
        fen += " -";
        
        //En Passant
        fen += " -";
        
        //Halfmove since captures or pawn advance
        fen += " " + this.halfMoves;
        
        //Turn number
        fen += " " + this.getTurnNumber();
        
        return fen;
    }
    
    private void findState(){
        this.state = this.board.getState();
    }
    
    public Board getBoard() {
        return board;
    }

    public Color getTurnColor() {
        return turnColor;
    }
    
    public int getTurnNumber(){
        return (moveNumber+1)/2;
    }

    public State getState() {
        return state;
    }

    public int getMoveNumber() {
        return moveNumber;
    }
}