package Players;

import Minimax.MinimaxProcessor;
import Minimax.TreeWithID;
import Minimax.ChessNode;
import Board.Board;
import Board.Move;
import Board.Position;
import Pieces.Piece;
import Strategies.ChessStrategy;
import java.util.ArrayList;
import java.util.Collections;
import Minimax.MinimaxProcessor.Mode;
import Pieces.Piece.Color;
import java.util.Random;

public class ComputerMinimax extends ChessPlayer{
    private TreeWithID<Double, Move> tree;
    private final int depth;
    private final ChessStrategy strat;
    private ArrayList<FenWithVal> calculated;
    private boolean stopSearching;
    private double currentValue;
    private final double step;
    
    private int calc;
    private int saved;
    private boolean print;
    
    public ComputerMinimax(int depth, ChessStrategy strategy){
        this.depth = depth;
        this.strat = strategy;
        this.calculated = new ArrayList<>();
        this.stopSearching = false;
        this.currentValue = 0;
        this.step = Double.POSITIVE_INFINITY;
        
        calc = 0;
        saved = 0;
    }
    
    public ComputerMinimax(int depth, ChessStrategy strategy, double step){
        this.depth = depth;
        this.strat = strategy;
        this.calculated = new ArrayList<>();
        this.stopSearching = false;
        this.currentValue = 0;
        this.step = step;
        
        calc = 0;
        saved = 0;
    }
    
    @Override
    public Move move (){
        this.stopSearching = false;
        
        if(this.game == null){
            System.out.println("No board recieved.");
            return null;
        }
        
        calc = 0;
        saved = 0;
        Board board = this.game.getBoard();
        this.currentValue = strat.analize(board);
        
        ChessNode root = null;
        if(board.getLastMove() != null && root != null){
            root = (ChessNode)root.getBranch(board.getLastMove());
        }else{
            root= new ChessNode(board);
        }
        this.tree = new TreeWithID(root);
        this.constructTree(root);
        
        //debugging.
        print = false;
        
        MinimaxProcessor processor;
        
        switch (this.playerColor){
            case WHITE:
                processor = new MinimaxProcessor(Mode.MAX, tree);
                break;
            case BLACK:
                processor = new MinimaxProcessor(Mode.MIN, tree);
                break;
            default:
                return null;
        }
        Move move = (Move)processor.getSecuence().get(0);
        
        System.out.println(move);
        System.out.println("Positions calculated: " + calc);
        System.out.println("Positions saved: " + saved);
        System.out.println("Current value: " + currentValue);
        System.out.println("Analized value: " + root.getValue());
        
        if(print){
            this.tree.print("\n");
            print = false;
        }
        
        root = (ChessNode)root.getBranch(move);
        return move;
    }
    
    private void constructTree(ChessNode node){
        if(stopSearching)
               return;
        Board board = node.getBoard();
        ArrayList<Piece> pieces = node.getBoard().getPieces();
        this.sortPieces(pieces);
        
        if(board.getMoves(node.getBoard().getTurn()).isEmpty()){
            node.setValue(strat.analize(board));
            shouldStop(strat.analize(board));
            if(stopSearching){
                return;
            }
        }
        
        for(Piece piece : pieces){
            if(piece.getColor() == node.getBoard().getTurn()){
                for(Position pos : piece.getMoves()){
                    if(stopSearching)
                        return;
                    Move move = new Move(piece, piece.getPosition(), pos);
                    Board newBoard = board.copyAndMove(move);
                    switch (board.getTurn()) {
                        case WHITE:
                            newBoard.setTurn(Color.BLACK);
                            break;
                        case BLACK:
                            newBoard.setTurn(Color.WHITE);
                            break;
                    }
                    
                    if(node.getDepth() < this.depth){
                        newBoard.fillSights();
                        newBoard.fillMoves();
                        ChessNode newBranch = new ChessNode(null, move, newBoard);
                        node.addBranch(newBranch);
                        constructTree(newBranch);
                    }else{
                        String fen = newBoard.getPositionFen();
                        double value = calculatedVal(fen);
                        if(Double.isNaN(value)){
                            value = strat.analize(newBoard);
                            this.addToCalculated(new FenWithVal(fen, value));
                            calc++;
                            node.addBranch(new ChessNode(value, move, newBoard));
                        }else{
                            node.addBranch(new ChessNode(value, move, newBoard));
                        }
                        shouldStop(value);
                        if (stopSearching){
                            System.out.println(newBoard.getPositionFen());
                            this.print = true;
                        }
                    }
                }
            }
        }
    }
    
    private void sortPieces(ArrayList<Piece> pieces){
        if(new Random().nextDouble() < 0.1){
            Collections.shuffle(pieces);
        }else{
            Collections.sort(pieces);
            Collections.reverse(pieces);
            Collections.shuffle(pieces.subList(pieces.size()/2, pieces.size()));
        }
    }
    
    private void shouldStop(double value){
        switch(this.playerColor){
            case WHITE:
                if(value - currentValue >= this.step)
                    stopSearching = true;
                break;
            case BLACK:
                if(currentValue - value >= this.step)
                    stopSearching = true;
        }
    }
    
    private void addToCalculated(FenWithVal fen){
        this.calculated.add(fen);
        
        if(calculated.size() > 20000){
            calculated.remove(0);
        }
    }
    
    private double calculatedVal(String fen){
        for (FenWithVal fenWithVal : calculated) {
            if(fenWithVal.fen.equals(fen)){
                saved++;
                return fenWithVal.val;
            }
        }
        return Double.NaN;
    }
    
    private class FenWithVal{
        //Validar cuando se incluyan otros valores finales FEN
        
        private final String fen;
        private final double val;
        
        public FenWithVal(String fen, double val) {
            this.fen = fen;
            this.val = val;
        }
    }

    @Override
    public String toString() {
        return "\nComputer Minimax playing " + this.playerColor + "\nRunning " + strat + "\nWith depth = " + depth;
    }
}