package Board;

import Board.Position.Column;
import Pieces.*;
import Pieces.Piece.Color;
import static Pieces.Piece.Color.WHITE;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Board implements Cloneable {
    final private Box[][] boxes;
    private ArrayList<Position> whiteSight;
    private ArrayList<Position> blackSight;
    private ArrayList<Position> whiteMoves;
    private ArrayList<Position> blackMoves;
    private ArrayList<Piece> pieces;

    private King whiteKing;
    private King blackKing;
    private State state;
    private Piece.Color turn;
    private PawnEnPassant enPassant;
    private Move lastMove;
    
    public enum State{
        LIVE, STALEMATE, WHITECHECK, WHITEMATE, BLACKCHECK, BLACKMATE, INVALID
    }

    public Board (Piece.Color turn, ArrayList<Piece> pieces){
        this.boxes = new Box[8][8];
        this.turn = turn;
        this.pieces = pieces;
        this.initializeBoard();
        this.lastMove = null;
    }
    
    public Board (String fen){
        this.boxes = new Box[8][8];
        this.boardFromFEN(fen);
        this.initializeBoard();
    }
    
    /**
     * Makes the move on the board, it doesn't set the turn.
     * @param move the move to make on the board.
     */
    public void move(Move move){
        int iniCol = move.getOrigen().getColumnIndex();
        int iniRow = move.getOrigen().getRowIndex();
        int endCol = move.getDestination().getColumnIndex();
        int endRow = move.getDestination().getRowIndex();
        Piece piece = this.boxes[iniCol][iniRow].getPiece();
        
        //Levantar pieza a mover.
        this.boxes[iniCol][iniRow].removePiece();
        
        //Quitar piza capturada.
        if(!this.isEmpty(move.getDestination())){
            this.pieces.remove(this.getOnPosition(move.getDestination()));
        }
        
        //Comportamiento exclusivo de movimientos de peones.
        if(piece instanceof Pawn){
            if(endRow == 7 || endRow == 0){
                //Coronación.
                this.pieces.remove(piece);
                piece = new Queen(piece.getColor(), move.getDestination());
                piece.setBoard(this);
                this.pieces.add(piece);
                //Se permite EnPassant.
            }else if(iniRow == 1 && endRow == 3){
                this.enPassant = new PawnEnPassant((Pawn)piece, new Position(Position.Column.values()[endCol],3));
            }else if(iniRow == 6 && endRow == 4){
                this.enPassant = new PawnEnPassant((Pawn)piece, new Position(Position.Column.values()[endCol],6));
            }
            
            //Se captura EnPassant
            if(enPassant != null && move.getDestination().equals(enPassant.pos)){
                this.pieces.remove(this.getOnPosition(enPassant.pawn.getPosition()));
                this.boxes[enPassant.pawn.getPosition().getColumnIndex()][enPassant.pawn.getPosition().getRowIndex()].removePiece();
                System.out.println("\n\nBoard.Move() line 77: EnPassant.");
                this.printBoard();
                this.enPassant = null;
            }
        }
        
        //Se pone la pieza en su casilla.
        this.boxes[endCol][endRow].setPiece(piece);
        piece.setPosition(move.getDestination());
        
        //
        if(!(piece instanceof Pawn)){
            this.enPassant = null;
        }
        
        this.lastMove = move;
    }
    
    /**
     * Finds and sets the state of the board, this can be: LIVE, WHITECHECK,
     * BLACKCHECK, WHITEMATE, BLACKMATE, STALEMATE or INVALID.
     * 
     * Only the first three allow the game to continue.
     */
    private void findState(){
        this.state = State.LIVE;
        this.fillSights();
        this.fillMoves();
    
        switch (this.turn) {
            case WHITE:
                if(this.whiteKing.isInCheck()){
                    if(this.whiteMoves.isEmpty()){
                        this.state = State.BLACKMATE;
                    }else{
                        this.state = State.BLACKCHECK;
                    }
                }else{
                    if(this.whiteMoves.isEmpty()){
                        this.state = State.STALEMATE;
                    }else{
                        this.state = State.LIVE;
                    }
                }
                break;
            case BLACK:
                if(this.blackKing.isInCheck()){
                    if(this.blackMoves.isEmpty()){
                        this.state = State.WHITEMATE;
                    }else{
                        this.state = State.WHITECHECK;
                    }
                }else{
                    if(this.blackMoves.isEmpty()){
                        this.state = State.STALEMATE;
                    }else{
                        this.state = State.LIVE;
                    }
                }
        }
    }
    
    /**
     * Makes a move in a copy of the board.
     * @param move Move to make.
     * @return Board with move made and next turn-color set.
     */
    public Board copyAndMove(Move move){
        Board boardCopy = Board.copyBoard(this);
        boardCopy.move(move);
        
        if(this.turn == Piece.Color.WHITE)
            boardCopy.setTurn(Piece.Color.BLACK);
        else
            boardCopy.setTurn(Piece.Color.WHITE);
        
        return boardCopy;
    }
    
    /**
     * Makes a copy of the given board.
     * @param original board to copy.
     * @return board copy.
     */
    public static Board copyBoard(Board original){
        ArrayList<Piece> piecesCopy = new ArrayList<>();
        Class[] args = {Piece.Color.class, Position.class};
        
        original.pieces.forEach((piece) -> {
            try {
                Piece copy = piece.getClass().getDeclaredConstructor(args).newInstance(piece.getColor(), piece.getPosition());
                piecesCopy.add(copy);
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                System.out.println("Board.copyBoard() ERROR");
            }
        });
        return new Board(original.turn, piecesCopy);
    }

    /**
     * Sets the pieces of the board on their position.
     * This method is meant to be used only on this class constructor.
     */
    private void initializeBoard(){
        //Se inicializa el arreglo.
        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                boxes[col][row] = new Box();
            }
        }
        
        //Las piezas se colocan en posición y se guardan los reyes.
        for(Piece piece : this.pieces){
            int col = piece.getPosition().getColumnIndex();
            int row = piece.getPosition().getRowIndex();
            
            piece.setBoard(this);
            this.boxes[col][row].setPiece(piece);
            
            if(piece instanceof King){
                switch(piece.getColor()){
                    case WHITE:
                        this.whiteKing = (King)piece;
                        break;
                    case BLACK:
                        this.blackKing = (King)piece;
                }
            }
        }
    }
    
    /**
     * Fills the sights of all the pieces in the board.
     * 
     * Used to calculate threats.
     */
    private void fillSights(){
        this.whiteSight = new ArrayList();
        this.blackSight = new ArrayList();
        
        for(Piece piece : pieces){
            piece.fillSight();
            if(piece.sightContains(piece.getPosition())){
                System.out.println("Board.fillSights");
                System.out.println("\tPiece sight contains itself.");
            }
            for(Position position : piece.getSight()){
                switch(piece.getColor()){
                    case WHITE:
                        this.whiteSight.add(position);
                        break;
                    case BLACK:
                        this.blackSight.add(position);
                        break;
                }
            }
        }
    }
    
    /**
     * Fills the moves from all the pieces in the board.
     * 
     * This method always fills the sights first.
     */
    public void fillMoves(){
        this.whiteMoves = new ArrayList();
        this.blackMoves = new ArrayList();
        
        for(Piece piece : pieces){
            piece.fillSight();
            piece.fillMoves();
            for(Position position : piece.getMoves()){
                switch(piece.getColor()){
                    case WHITE:
                        this.whiteMoves.add(position);
                        break;
                    case BLACK:
                        this.blackMoves.add(position);
                }
            }
        }
    }

    /**
     * Prints the board in console for debbugging purposes.
     */
    public void printBoard(){
        //Right order to read the array.
        System.out.println();
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if(boxes[col][row].isEmpty()){
                    System.out.print("__");
                }else{
                    System.out.print(boxes[col][row].getPiece().getSymbol());
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Checks if a position is empty in the board.
     * @param pos position to check.
     * @return true if the position is empty, false otherwise.
     */
    public boolean isEmpty(Position pos){
        int col = pos.getColumnIndex();
        int row = pos.getRowIndex();
        
        return boxes[col][row].isEmpty();
    }
    
    /**
     * Gets the piece from a position.
     * @param pos position containing the piece.
     * @return the piece in the position give.
     */
    public Piece getOnPosition(Position pos){
        int col = pos.getColumnIndex();
        int row = pos.getRowIndex();
        
        return boxes[col][row].getPiece();
    }
    
    /**
     * Gets the piece from a position.
     * @param col position column index.
     * @param row position row index.
     * @return piece in the given position.
     */
    public Piece getOnPosition(int col, int row){
        if(col < 0 || col > 7 || row < 1 || row > 8){
            return null;
        }
        
        return boxes[col][row-1].getPiece();
    }
    
    /**
     * Creates a FEN encoding of this board.
     * @return FEN code.
     */
    public String getPositionFen(){
        String fen = "";
        Piece piece;
        int empty = 0;
        Box[][] boxesC = this.getBoxes();
        
        //Board
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if(boxesC[col][row].isEmpty()){
                    empty++;
                }else{
                    piece = boxesC[col][row].getPiece();
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
        switch (this.turn) {
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
        
        return fen;
    }
    
    public State getState() {
        this.findState();
        return state;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public void setTurn(Piece.Color turn) {
        this.turn = turn;
    }

    public Piece.Color getTurn() {
        return turn;
    }
    
    public Move getLastMove(){
        return lastMove;
    }

    public ArrayList<Position> getWhiteSight() {
        if(whiteSight == null){
            this.fillSights();
        }
        return whiteSight;
    }

    public ArrayList<Position> getBlackSight() {
        if(blackSight == null){
            this.fillSights();
        }
        return blackSight;
    }

    public ArrayList<Position> getWhiteMoves() {
        if(whiteMoves == null || whiteMoves.size() == 0){
            this.fillMoves();
        }
        return whiteMoves;
    }

    public ArrayList<Position> getBlackMoves() {
        if(blackMoves == null || blackMoves.size() == 0){
            this.fillMoves();
        }
        return blackMoves;
    }

    public King getWhiteKing() {
        return whiteKing;
    }

    public King getBlackKing() {
        return blackKing;
    }
    
    public ArrayList<Position> getMoves(Piece.Color color){
        if(color == Piece.Color.WHITE) {
            return this.getWhiteMoves();
        }else{
            return this.getBlackMoves();
        }
    }
    
    /**
     * Creates a position on the board given a FEN string.
     * This method is meant to be called in the constructor of this class.
     * @param fen The string to turn into position.
     */
    private void boardFromFEN(String fen){
        pieces = new ArrayList<>();
        Position pos;
        int colIndex = 0;
        int rowIndex = 7;
        
        while(!fen.isEmpty()){
            pos = new Position(Column.values()[colIndex], rowIndex + 1);
            switch (fen.charAt(0)) {
                case 'K':
                    this.whiteKing = new King(Color.WHITE, pos);
                    pieces.add(whiteKing);
                    colIndex++;
                    break;
                case 'Q':
                    pieces.add(new Queen(Color.WHITE, pos));
                    colIndex++;
                    break;
                case 'R':
                    pieces.add(new Rook(Color.WHITE, pos));
                    colIndex++;
                    break;
                case 'B':
                    pieces.add(new Bishop(Color.WHITE, pos));
                    colIndex++;
                    break;
                case 'N':
                    pieces.add(new Knight(Color.WHITE, pos));
                    colIndex++;
                    break;
                case 'P':
                    pieces.add(new Pawn(Color.WHITE, pos));
                    colIndex++;
                    break;
                case 'k':
                    this.blackKing = new King(Color.BLACK, pos);
                    pieces.add(blackKing);
                    colIndex++;
                    break;
                case 'q':
                    pieces.add(new Queen(Color.BLACK, pos));
                    colIndex++;
                    break;
                case 'r':
                    pieces.add(new Rook(Color.BLACK, pos));
                    colIndex++;
                    break;
                case 'b':
                    pieces.add(new Bishop(Color.BLACK, pos));
                    colIndex++;
                    break;
                case 'n':
                    pieces.add(new Knight(Color.BLACK, pos));
                    colIndex++;
                    break;
                case 'p':
                    pieces.add(new Pawn(Color.BLACK, pos));
                    colIndex++;
                    break;
                case '/':
                    colIndex = 0;
                    rowIndex--;
                    break;
                default:
                    colIndex += Character.getNumericValue(fen.charAt(0));
            }
            if(colIndex>7){
                colIndex=0;
            }
            fen = fen.substring(1);
        }
    }
    
    public Position getEnPassantPos(){
        if(this.enPassant == null)
            return null;
        return this.enPassant.pos;
    }
    
    public Pawn getEnPassantPawn(){
        if(this.enPassant == null)
            return null;
        return this.enPassant.pawn;
    }
    
    public void resetEnPassant(){
        this.enPassant = null;
    }
    
    //Recibir enPassant de FEN.
    private class PawnEnPassant{
        private final Pawn pawn;
        private final Position pos;
        
        private PawnEnPassant(Pawn pawn, Position pos){
            this.pawn = pawn;
            this.pos = pos;
        }
    }
}