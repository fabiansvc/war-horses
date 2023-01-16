package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    // 0: Empty, 1: Red horse, 2: Green horse, 3: Bonus.
    private int[][] board;
    private ArrayList<int[]> bonusPosition;
    private int[] positionGreenHorse, positionRedHorse;

    public Board() {
    }

    public void printBoard() {
        board = new int[8][8];
        bonusPosition = new ArrayList<int[]>();
        positionGreenHorse = new int[2];
        positionRedHorse = new int[2];

        placeBonus();
        placeHorses();
    }

    private void placeBonus() {
        int row = (int) (Math.random() * 8);
        int col = (int) (Math.random() * 8);
        board[row][col] = 3;
        addBonusPosition(row, col);

        int i = 0;
        while (i < 2) {
            row = (int) (Math.random() * 8);
            col = (int) (Math.random() * 8);

            if (!isCorrectedBonusPosition(bonusPosition, getBonusNeighboors(row, col))) {
                board[row][col] = 3;
                addBonusPosition(row, col);
                i++;
            }
        }
    }

    private boolean isCorrectedBonusPosition(ArrayList<int[]> bonusPosition, ArrayList<int[]> tempBonusNeigboors) {
        for (int i = 0; i < bonusPosition.size(); i++) {
            int[] position = bonusPosition.get(i);

            for (int j = 0; j < tempBonusNeigboors.size(); j++) {
                int[] tempBonusNeigboor = tempBonusNeigboors.get(j);

                if (tempBonusNeigboor[0] >= 0 && tempBonusNeigboor[1] >= 0 && tempBonusNeigboor[0] == position[0] && tempBonusNeigboor[1] == position[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<int[]> getBonusNeighboors(int row, int col) {
        ArrayList<int[]> bonusNeigboors = new ArrayList();

        int[] center = new int[2];
        center[0] = row;
        center[1] = col;
        bonusNeigboors.add(center);

        int[] left = new int[2];
        left[0] = row;
        left[1] = col - 1;
        bonusNeigboors.add(left);

        int[] topLeft = new int[2];
        topLeft[0] = row - 1;
        topLeft[1] = col - 1;
        bonusNeigboors.add(topLeft);

        int[] top = new int[2];
        top[0] = row - 1;
        top[1] = col;
        bonusNeigboors.add(top);

        int[] topRight = new int[2];
        topRight[0] = row - 1;
        topRight[1] = col + 1;
        bonusNeigboors.add(topRight);

        int[] right = new int[2];
        right[0] = row;
        right[1] = col + 1;
        bonusNeigboors.add(right);

        int[] bottomRight = new int[2];
        bottomRight[0] = row + 1;
        bottomRight[1] = col + 1;
        bonusNeigboors.add(bottomRight);

        int[] bottom = new int[2];
        bottom[0] = row + 1;
        bottom[1] = col;
        bonusNeigboors.add(bottom);

        int[] bottomLeft = new int[2];
        bottomLeft[0] = row + 1;
        bottomLeft[1] = col - 1;
        bonusNeigboors.add(bottomLeft);

        return bonusNeigboors;
    }

    private void addBonusPosition(int row, int col) {
        int[] position = {row, col};
        bonusPosition.add(position);
    }

    private void placeHorses() {
        int i = 1;
        while (i < 3) {
            int row = (int) (Math.random() * 8);
            int col = (int) (Math.random() * 8);
            if (board[row][col] != 3 && board[row][col] == 0) {
                board[row][col] = i;
                int[] position = {row, col};

                if (i == 1) {
                    positionRedHorse = position;
                } else {
                    positionGreenHorse = position;
                }
                i++;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getPositionGreenHorse() {
        return positionGreenHorse;
    }

    public void setPositionGreenHorse(int[] positionGreenHorse) {
        this.positionGreenHorse = positionGreenHorse;
    }

    public int[] getPositionRedHorse() {
        return positionRedHorse;
    }

    public void setPositionRedHorse(int[] positionRedHorse) {
        this.positionRedHorse = positionRedHorse;
    }

    public int[][] updateBoard(int row, int col, int[][] status, Horse horse) {

        int tab = status[row][col];
        if (tab == 3) {
            ArrayList<int[]> neighbors = getNeighbors(row, col);

            for (int i = 0; i < neighbors.size(); i++) {
                int rowNeighbor = neighbors.get(i)[0];
                int colNeighbor = neighbors.get(i)[1];
                if (rowNeighbor >= 0 && rowNeighbor < 8 && colNeighbor >= 0 && colNeighbor < 8 && status[rowNeighbor][colNeighbor] == 0) {
                    int[] valueRow = status[rowNeighbor].clone();
                    valueRow[colNeighbor] = horse.getValueBox();
                    status[rowNeighbor] = valueRow;
                }
            }
        }

        if (tab == 0 || tab == 3) {
            int[] valueRow = status[horse.getPosition()[0]].clone();
            valueRow[horse.getPosition()[1]] = horse.getValueBox();
            status[horse.getPosition()[0]] = valueRow;

            int[] valueRowHorse = status[row].clone();
            valueRowHorse[col] = horse.getValueHorse();
            status[row] = valueRowHorse;

        }

        return status;

    }

    private ArrayList<int[]> getNeighbors(int row, int col) {
        ArrayList<int[]> neighbors = new ArrayList();

        int[] left = new int[2];
        left[0] = row;
        left[1] = col - 1;
        neighbors.add(left);

        int[] top = new int[2];
        top[0] = row - 1;
        top[1] = col;
        neighbors.add(top);

        int[] right = new int[2];
        right[0] = row;
        right[1] = col + 1;
        neighbors.add(right);

        int[] bottom = new int[2];
        bottom[0] = row + 1;
        bottom[1] = col;
        neighbors.add(bottom);

        return neighbors;
    }

    public int getValuesBoxbyHorse(int valueHorse, int valueBox, int[][] board) {
        int val = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == valueHorse || board[i][j] == valueBox) {
                    val++;
                }
            }
        }
        return val;
    }

    public void showBoard() {
        for (int[] ints : board) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
