/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

/**
 *
 * @author Usuario
 * @param <T> Value to compare.
 * @param <I> Identifier.
 */
public class TreeWithID <T extends Comparable, I>{
    private NodeWithID<T, I> root;
    
    public TreeWithID(){
        this.root = new NodeWithID ();
    }
    
    public TreeWithID(NodeWithID root){
        this.root = root;
    }
    
    public TreeWithID(T value, I id){
        this.root = new NodeWithID (value, id);
    }
    
    public void addBranch(T value, I identifier){
        this.root.addBranch(value, identifier);
    }

    public NodeWithID<T, I> getRoot() {
        return root;
    }
    
    public void print (String separator){
        print(this.root, separator);
    }
    
    private void print(NodeWithID<T, I> node, String separator){
        System.out.println(separator + node);
        for (NodeWithID<T, I> branch : node.getBranches()) {
            print(branch, separator + separator);
        }
    }
    
}
