package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Fabian Valencia
 */
public class Board {

    // 0: empty, 1:red horse, 2: green horse, 3: bonus.
    private int[][] board;
    private ArrayList<int[]> bonusPosition;

    public Board() {
        board = new int[8][8];
        bonusPosition = new ArrayList<int[]>();

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

    private void addBonusPosition(int row, int col) {
        int[] position = new int[2];
        position[0] = row;
        position[1] = col;
        bonusPosition.add(position);
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

    private boolean isCorrectedBonusPosition(ArrayList<int[]> bonusPosition, ArrayList<int[]> tempBonusNeigboors) {
        for (int i = 0; i < bonusPosition.size(); i++) {
            int[] position = bonusPosition.get(i);

            for (int j = 0; j < tempBonusNeigboors.size(); j++) {
                int[] tempBonusNeigboor = tempBonusNeigboors.get(j);
                if (tempBonusNeigboor[0] * tempBonusNeigboor[1] >= 0 && tempBonusNeigboor[0] == position[0] && tempBonusNeigboor[1] == position[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    private void placeHorses() {
        int i = 1;
        while (i<3) {
            int row = (int) (Math.random() * 8);
            int col = (int) (Math.random() * 8);
            if (board[row][col] != 3 && board[row][col] == 0) {
                board[row][col] = i;
                i++;
            }
        }
    }

    public void showBoard() {
        for (int[] ints : board) {
            System.out.println(Arrays.toString(ints));
        }
    }

}
