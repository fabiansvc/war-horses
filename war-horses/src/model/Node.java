package model;

/**
 *
 * @author Fabián Valencia
 */

import java.util.ArrayList; 

public class Node {
    
    private String type;
    private int depth;
    private double utility;
    private int [][] board;
    private ArrayList<int []> state;

    public Node(String type, int depth, double utility, int[][] board, ArrayList<int[]> state) {
        this.type = type;
        this.depth = depth;
        this.utility = utility;
        this.board = board;
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }

    public double getUtility() {
        return utility;
    }

    public int[][] getBoard() {
        return board;
    }

    public ArrayList<int[]> getState() {
        return state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setState(ArrayList<int[]> state) {
        this.state = state;
    }
      
}
