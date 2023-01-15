package model;

/**
 *
 * @author Fabián Valencia
 */

public class Node {
    private Node parent;
    private String type;
    private int depth;
    private double utility;
    private int [][] status;
    private int[] positionRedHorse, positionGreenHorse;

    public Node(Node parent, String type, int depth, double utility, int[][] status, int[] positionRedHorse, int[] positionGreenHorse) {
        this.parent = parent;
        this.type = type;
        this.depth = depth;
        this.utility = utility;
        this.status = status;
        this.positionRedHorse = positionRedHorse;
        this.positionGreenHorse = positionGreenHorse;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getUtility() {
        return utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public int[][] getStatus() {
        return status;
    }

    public void setStatus(int[][] status) {
        this.status = status;
    }

    public int[] getPositionRedHorse() {
        return positionRedHorse;
    }

    public void setPositionRedHorse(int[] positionRedHorse) {
        this.positionRedHorse = positionRedHorse;
    }

    public int[] getPositionGreenHorse() {
        return positionGreenHorse;
    }

    public void setPositionGreenHorse(int[] positionGreenHorse) {
        this.positionGreenHorse = positionGreenHorse;
    }

      
}
