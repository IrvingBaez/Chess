/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minimax;

import java.util.ArrayList;

/**
 *
 * @author A14014852
 * @param <T>
 * Content of the node, can be unknown.
 * @param <I>
 * Identifier of the node, is always known.
 */
public class NodeWithID <T extends Comparable, I> {
    protected T value;
    protected final I identifier;
    protected NodeWithID<T, I> parent;
    protected NodeWithID<T, I> bestBranch;
    protected ArrayList<NodeWithID<T, I>> branches;
    
    public NodeWithID(){
        this.value = null;
        this.parent = null;
        this.identifier = null;
        this.branches = new ArrayList();
    }
    
    public NodeWithID(T value, I id){
        this.value = value;
        this.parent = null;
        this.identifier = id;
        this.branches = new ArrayList();
    }
    
    public NodeWithID(NodeWithID parent, T value, I id){
        this.value = value;
        this.parent = parent;
        this.identifier = id;
        this.branches = new ArrayList();
    }
    
    public void addBranch(T value, I id){
        NodeWithID node = new NodeWithID(this, value, id);
        node.setParent(this);
        this.branches.add(node);
    }
    
    public void addBranch(NodeWithID branch){
        branch.setParent(this);
        this.branches.add(branch);
    }
    
    public int getDepth(){
        int depth = 0;
        
        NodeWithID node = this;
        while(node.parent!=null){
            depth++;
            node = node.getParent();
        }
        
        return depth;
    }

    public NodeWithID<T, I> getParent() {
        return parent;
    }

    public void setParent(NodeWithID<T, I> padre) {
        this.parent = padre;
    }

    public NodeWithID<T, I> getBestBranch() {
        return bestBranch;
    }

    public void setBestBranch(NodeWithID<T, I> bestBranch) {
        this.bestBranch = bestBranch;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<NodeWithID<T, I>> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<NodeWithID<T, I>> hijos) {
        this.branches = hijos;
    }
    
    public I getIdentifier() {
        return identifier;
    }
    
    public NodeWithID<T, I> getBranch(I id){
        for(NodeWithID branch : this.branches){
            if(branch.identifier == id){
                return branch;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(identifier) + ": " + String.valueOf(value);
    }
}