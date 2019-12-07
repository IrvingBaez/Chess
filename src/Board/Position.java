package Board;

import Exceptions.PositionException;
import java.util.ArrayList;

public class Position {
    private final int row;
    private final Column column;
        
    public enum Column{
        a, b, c, d, e, f, g, h
    }
    
    public Position (Column column, int row){
        if(row < 1 || row > 8){
            throw new PositionException("Index out of range. Row = " + row);
        }
        
        this.column = column;
        this.row = row;
    }
    
    public boolean equals(Position pos){
        if(this.column.equals(pos.column)){
            return this.row == pos.row;
        }else{
            return false;
        }
    }
    
    public static boolean positionInList(ArrayList<Position> list, Position position){
        if(list == null){
            return false;
        }
        
        for (Position pos : list) {
            if(position.equals(pos)){
                return true;
            }
        }
        return false;
    }

    public int getRow() {
        return row;
    }
    
    public int getRowIndex() {
        return row - 1;
    }

    public Column getColumn() {
        return column;
    }
    
    public int getColumnIndex() {
        return Column.valueOf(column.toString()).ordinal();
    }

    @Override
    public String toString() {
        return column.toString() + row;
    }
}