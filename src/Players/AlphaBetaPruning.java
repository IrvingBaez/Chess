package Players;

import Board.Board;
import Board.Move;
import Minimax.ChessNode;
import Strategies.ChessStrategy;

/**
 *
 * @author stamp
 */
public class AlphaBetaPruning extends ChessPlayer {
    ChessStrategy strategy;
    int depth;

    public AlphaBetaPruning(ChessStrategy strategy, int depth) {
        this.strategy = strategy;
        this.depth = depth;
    }
    
    @Override
    public Move move() {
        if(this.game == null){
            System.out.println("No board recieved.");
            return null;
        }
        
        ChessNode root = new ChessNode(this.game.getBoard());
        return (Move)alphabeta(root, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true).getIdentifier();
    }
    
    private ChessNode alphabeta(ChessNode node, int depth, double alpha, double beta, boolean maxing){
        double value;
        ChessNode best = node;
        
        if(depth == 0 || node.getBranches().size() == 0){
            node.setValue(strategy.analize(node.getBoard()));
            return node;
        }
        
        node.fillBranches();
        
        if(maxing){
            value = Double.NEGATIVE_INFINITY;
            for(Object o : node.getBranches()){
                if(value < (double)alphabeta((ChessNode)o, depth - 1, alpha, beta, false).getValue())
                    best = (ChessNode)o;
                
                alpha = Math.max(value, alpha);
                if(alpha >= beta)
                    break;
            }
        }else{
            value = Double.POSITIVE_INFINITY;
            for(Object o : node.getBranches()){
                if(value > (double)alphabeta((ChessNode)o, depth - 1, alpha, beta, true).getValue());
                    best = (ChessNode)o;
                
                beta = Math.min(value, beta);
                if(alpha >= beta)
                    break;
            }
        }
        
        return best;
    }
}