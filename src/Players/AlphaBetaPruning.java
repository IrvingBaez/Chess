package Players;

import Board.Move;
import Minimax.ChessNode;
import Pieces.Piece;
import Strategies.ChessStrategy;

/**
 *
 * @author stamp
 */
public class AlphaBetaPruning extends ChessPlayer {
    private final ChessStrategy strategy;
    private final int depth;
    private boolean maxing;

    public AlphaBetaPruning(int depth, ChessStrategy strategy) {
        this.strategy = strategy;
        this.depth = depth;   
    }
    
    @Override
    public Move move() {
        if(this.game == null){
            System.out.println("No board recieved.");
            return null;
        }
        this.maxing = (this.game.getBoard().getTurn() == Piece.Color.WHITE);
        ChessNode root = new ChessNode(this.game.getBoard());
        Move move = (Move)alphabeta(root, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxing).getIdentifier();
        System.out.println(move);
        return move;
    }
    
    private ChessNode alphabeta(ChessNode node, int depth, double alpha, double beta, boolean maxing){
        double value;
        ChessNode best = node;
        
        node.fillBranches();
        
        if(depth == 0 || node.getBranches().isEmpty()){
            node.setValue(strategy.analize(node.getBoard()));
            return node;
        }
        
        if(maxing){
            value = Double.NEGATIVE_INFINITY;
            for(Object o : node.getBranches()){
                double branchValue = (double)alphabeta((ChessNode)o, depth - 1, alpha, beta, false).getValue();
                if(value < branchValue){
                    value = branchValue;
                    if(maxing){
                        best = (ChessNode)o;
                        best.setValue(value);
                    }
                }
                
                alpha = Math.max(value, alpha);
                if(alpha >= beta)
                    break;
            }
        }else{
            value = Double.POSITIVE_INFINITY;
            for(Object o : node.getBranches()){
                double branchValue = (double)alphabeta((ChessNode)o, depth - 1, alpha, beta, true).getValue();
                if(value > branchValue){
                    value = branchValue;
                    if(!maxing){
                        best = (ChessNode)o;
                        best.setValue(value);
                    }
                }
                
                beta = Math.min(value, beta);
                if(alpha >= beta)
                    break;
            }
        }
        
        return best;
    }
    
    @Override
    public String toString() {
        return "\nAlphaBetaPruning playing " + this.playerColor + "\nRunning " + strategy + "\nWith depth = " + depth;
    }
}