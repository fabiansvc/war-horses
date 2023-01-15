package model;

import java.util.ArrayList;
import model.Node;

public class Minimax {

    private int gameLevel;
    private Board board;
    private Horse greenHorse;
    private Horse redHorse;
    private ArrayList<Node> root;

    public Minimax(int gameLevel) {
        this.gameLevel = gameLevel;
        board = new Board();
        greenHorse = new Horse(null, null, null, new ArrayList<int[]>(), 2, 5);
        redHorse = new Horse(null, null, null, new ArrayList<int[]>(), 1, 6);
        root = new ArrayList<Node>();
    }

    public int[] movementMachine(int[][] status, int[] positionRedHorse, int[] positionGreenHorse) {
        Node nodeMax = new Node(
                null,
                "max", 0,
                Double.POSITIVE_INFINITY, 
                status,
                positionRedHorse,
                positionGreenHorse);
        
        root.add(nodeMax);
        double utilityMax = maxValue(nodeMax, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        int movementRedHorse[] = new int[2];

        for (int i = 0; i < root.size(); i++) {
           
            if (root.get(i).getUtility() == utilityMax && root.get(i).getParent() == nodeMax) {
                movementRedHorse = root.get(i).getPositionRedHorse();
                break;
            }
        }

        return movementRedHorse;
    }

    private double maxValue(Node nodeMax, double alpha, double beta) {
        redHorse.setPosition(nodeMax.getPositionRedHorse());
        redHorse.findPosiblesMovements();
        ArrayList<int[]> posiblesMovementsRedHorse = redHorse.correctPosiblesMovements(nodeMax.getStatus());

        greenHorse.setPosition(nodeMax.getPositionRedHorse());
        greenHorse.findPosiblesMovements();        
        ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.correctPosiblesMovements(nodeMax.getStatus());

        if (nodeMax.getDepth() == gameLevel
                || (posiblesMovementsGreenHorse.size() == 0 && posiblesMovementsRedHorse.size() == 0)) {
            int cantTabsRedHorse = board.getValuesBox(redHorse.getValueHorse(), redHorse.getValueBox(), nodeMax.getStatus());
            int cantTabsGreenHorse = board.getValuesBox(greenHorse.getValueHorse(), greenHorse.getValueBox(), nodeMax.getStatus());
            
            if(cantTabsGreenHorse > cantTabsGreenHorse){
                nodeMax.setUtility(-1);
            }else if(cantTabsGreenHorse < cantTabsGreenHorse){
                nodeMax.setUtility(1);
            }else{
                 nodeMax.setUtility(0);
            }
        } else {
            if (posiblesMovementsRedHorse.size() > 0) {

                for (int i = 0; i < posiblesMovementsRedHorse.size(); i++) {

                    int rowMovement = posiblesMovementsRedHorse.get(i)[0];
                    int colMovement = posiblesMovementsRedHorse.get(i)[1];
                    int[][] newStatus = board.updateBoard(
                            rowMovement,
                            colMovement,
                            nodeMax.getStatus().clone(),
                            redHorse);
                    Node nodeMin = new Node(nodeMax,
                            "min",
                            nodeMax.getDepth() + 1,
                            Double.NEGATIVE_INFINITY,
                            newStatus,
                            posiblesMovementsRedHorse.get(i).clone(),
                            nodeMax.getPositionGreenHorse().clone());
                    root.add(nodeMin);
                    alpha = minValue(nodeMin, alpha, beta);

                    if (alpha >= beta) {
                        nodeMax.setUtility(beta);
                        break;
                    } else {
                        nodeMax.setUtility(alpha);
                    }
                }
            } else {
                Node nodeMin = new Node(nodeMax,
                        "min",
                        nodeMax.getDepth() + 1,
                        Double.NEGATIVE_INFINITY,
                        nodeMax.getStatus(),
                        nodeMax.getPositionRedHorse().clone(),
                        nodeMax.getPositionGreenHorse().clone());
                root.add(nodeMin);
                alpha = minValue(nodeMin, alpha, beta);
                nodeMax.setUtility(alpha);
            }

        }
        return nodeMax.getUtility();
    }

    private double minValue(Node nodeMin, double alpha, double beta) {

        redHorse.setPosition(nodeMin.getPositionRedHorse());
        redHorse.findPosiblesMovements();     
        ArrayList<int[]> posiblesMovementsRedHorse = redHorse.correctPosiblesMovements(nodeMin.getStatus());

        greenHorse.setPosition(nodeMin.getPositionRedHorse());
        greenHorse.findPosiblesMovements();
        ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.correctPosiblesMovements(nodeMin.getStatus());

        if (nodeMin.getDepth() == gameLevel
                || (posiblesMovementsGreenHorse.size() == 0 && posiblesMovementsRedHorse.size() == 0)) {
            int cantTabsRedHorse = board.getValuesBox(redHorse.getValueHorse(), redHorse.getValueBox(), nodeMin.getStatus());
            int cantTabsGreenHorse = board.getValuesBox(greenHorse.getValueHorse(), greenHorse.getValueBox(), nodeMin.getStatus());
            
            if(cantTabsGreenHorse > cantTabsGreenHorse){
                nodeMin.setUtility(-1);
            }else if(cantTabsGreenHorse < cantTabsGreenHorse){
                nodeMin.setUtility(1);
            }else{
                 nodeMin.setUtility(0);
            }
        } else {
            if (posiblesMovementsGreenHorse.size() > 0) {
                for (int i = 0; i < posiblesMovementsGreenHorse.size(); i++) {
                    int rowMovement = posiblesMovementsGreenHorse.get(i)[0];
                    int colMovement = posiblesMovementsGreenHorse.get(i)[1];
//                    System.out.println("Min: " + rowMovement + "," + colMovement);
                    int[][] newStatus = board.updateBoard(
                            rowMovement,
                            colMovement,
                            nodeMin.getStatus().clone(),
                            greenHorse);
                    Node nodeMax = new Node(
                            nodeMin,
                            "max",
                            nodeMin.getDepth() + 1,
                            Double.POSITIVE_INFINITY,
                            newStatus,
                            nodeMin.getPositionRedHorse().clone(),
                            posiblesMovementsGreenHorse.get(i).clone());
                    root.add(nodeMax);
                    beta = maxValue(nodeMax, alpha, beta);

                    if (alpha >= beta) {
                        nodeMin.setUtility(alpha);
                        break;
                    } else {
                        nodeMin.setUtility(beta);
                    }
                }
            } else {
                Node nodeMax = new Node(
                        nodeMin,
                        "max",
                        nodeMin.getDepth() + 1,
                        Double.POSITIVE_INFINITY,
                        nodeMin.getStatus(),
                        nodeMin.getPositionRedHorse().clone(),
                        nodeMin.getPositionGreenHorse().clone());
                root.add(nodeMax);
                beta = maxValue(nodeMax, alpha, beta);
                nodeMin.setUtility(beta);
            }
        }
        return nodeMin.getUtility();
    }
}
