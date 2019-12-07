/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 * @param <T> Value to compare.
 * @param <I> Identifier.
 */
public class MinimaxProcessor <T extends Comparable, I>{
    private Mode mode;
    private final Mode phase;
    private final TreeWithID<T, I> tree;
    private final ArrayList<I> sequence;
    
    public enum Mode {
        MIN, MAX
    }
    
    public MinimaxProcessor(Mode phase, TreeWithID tree) {
        this.phase = phase;
        this.tree = tree;
        this.sequence = new ArrayList<>();
        this.process();
    }
    
    public void process(){
        this.process(tree.getRoot());
    }
    
    private T process(NodeWithID currentNode){
        findMode(currentNode);
        if(currentNode.getValue() == null){
            if(currentNode.getBranches().isEmpty()){
                System.out.println("Invalid tree: node without value nor branches");
                this.tree.print("\t");
                return null;
            }
            
            NodeWithID bestBranch = (NodeWithID)currentNode.getBranches().get(0);
            
            for(Object o : currentNode.getBranches()){
                NodeWithID branch = (NodeWithID)o;
                
                switch (this.mode){
                    case MAX:
                        if(process(branch).compareTo(process(bestBranch)) == 1){
                            bestBranch = branch;
                        }
                        break;
                    case MIN:
                        if(process(branch).compareTo(process(bestBranch)) == -1){
                            bestBranch = branch;
                        }
                        break;
                }
            }
            currentNode.setValue(bestBranch.getValue());
            currentNode.setBestBranch(bestBranch);
        }
        return (T)currentNode.getValue();
    }
    
    private void findMode(NodeWithID node){
        switch (node.getDepth()%2){
            case 0:
                this.mode = phase;
                this.switchMode();
                break;
            case 1:
                this.mode = phase;
        }
    }
    
    private void switchMode(){
        switch (this.mode){
            case MIN:
                this.mode = Mode.MAX;
                break;
            case MAX:
                this.mode = Mode.MIN;
        }
    }
    
    public ArrayList<I> getSecuence() {
        NodeWithID node = this.tree.getRoot();
        
        try{
            while(!node.getBranches().isEmpty()){
                this.sequence.add((I)node.getIdentifier());
                node = node.getBestBranch();
            }
            this.sequence.add((I)node.getIdentifier());
            
        }catch(NullPointerException e){
            System.out.println("Invalid Tree: node with value and branches.");
        }
        
        this.sequence.remove(0);
        
        return sequence;
    }
    
    public static void main(String[] args) {
        TreeWithID<Integer, String> tree = new TreeWithID<>(null, "0");
        tree.addBranch(null, "0-1");
            tree.getRoot().getBranches().get(0).addBranch(1, "0-1-1");
            tree.getRoot().getBranches().get(0).addBranch(2, "0-1-2");
            tree.getRoot().getBranches().get(0).addBranch(3, "0-1-3");
        tree.addBranch(null, "0-2");
            tree.getRoot().getBranches().get(1).addBranch(4, "0-2-1");
            tree.getRoot().getBranches().get(1).addBranch(6, "0-2-2");
            tree.getRoot().getBranches().get(1).addBranch(9, "0-2-3");
        tree.addBranch(null, "0-3");
            tree.getRoot().getBranches().get(2).addBranch(6, "0-3-1");
            tree.getRoot().getBranches().get(2).addBranch(4, "0-3-2");
            tree.getRoot().getBranches().get(2).addBranch(10, "0-3-3");
        tree.addBranch(null, "0-4");
            tree.getRoot().getBranches().get(3).addBranch(null, "0-4-1");
                tree.getRoot().getBranches().get(3).getBranches().get(0).addBranch(5, "0-4-1-1");
                tree.getRoot().getBranches().get(3).getBranches().get(0).addBranch(6, "0-4-1-2");
            tree.getRoot().getBranches().get(3).addBranch(8, "0-4-2");
            tree.getRoot().getBranches().get(3).addBranch(7, "0-4-3");
        tree.addBranch(null, "0-5");
            tree.getRoot().getBranches().get(4).addBranch(9, "0-5-1");
            tree.getRoot().getBranches().get(4).addBranch(3, "0-5-2");
            tree.getRoot().getBranches().get(4).addBranch(2, "0-5-3");
        
        MinimaxProcessor processor = new MinimaxProcessor(Mode.MAX, tree);
        processor.process();
        
        tree.print("\t");
        
        System.out.println("Steps:");
        processor.getSecuence().forEach((o) -> {
            System.out.println(o.toString());
        });
        
        System.out.println("\nValue of root: ");
        System.out.println(tree.getRoot());
    }
}