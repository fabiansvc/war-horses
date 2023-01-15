package controller;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Board;
import model.Horse;

public class CanvasWarHorsesController {

    private Canvas canvas;
    private GraphicsContext gc;
    private Board board;

    public CanvasWarHorsesController(Canvas canvas, Board boardChess, GraphicsContext gc) {
        this.canvas = canvas;
        this.board = boardChess;
        this.gc = gc;
    }

    public void setCanvas(Image bonusImage, Image redHorseImage, Image greenHorseImage) {
        canvas.setHeight(480);
        canvas.setWidth(480);

        for (int i = 0; i <= board.getBoard().length; i++) {
            gc.strokeLine(0, i * 60, 480, i * 60);
            gc.strokeLine(i * 60, 0, i * 60, 480);
        }

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                if (board.getBoard()[i][j] == 3) {
                    gc.drawImage(bonusImage, j * 60 + 6, i * 60 + 6, 48, 48);
                } else if (board.getBoard()[i][j] == 1) {
                    gc.drawImage(redHorseImage, j * 60, i * 60, 60, 60);
                } else if (board.getBoard()[i][j] == 2) {
                    gc.drawImage(greenHorseImage, j * 60, i * 60, 60, 60);
                }
            }
        }
    }

    public void placePosiblesMovements(Horse horse) {
        for (int i = 0; i < horse.getPosiblesMovements().size(); i++) {
            int rowMovement = horse.getPosiblesMovements().get(i)[0];
            int colMovement = horse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = board.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.GOLDENROD);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }
    }

    public void printPosiblesMovements(Horse horse) {
        for (int i = 0; i < horse.getPosiblesMovements().size(); i++) {
            int rowMovement = horse.getPosiblesMovements().get(i)[0];
            int colMovement = horse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = board.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.WHITE);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }

    }

    public void updateBoard(int row, int col, Horse horse) {

        int tab = board.getBoard()[row][col];

        if (tab == 3) {
            
            ArrayList<int[]> neighbors = getNeighbors(row, col);

            for (int i = 0; i < neighbors.size(); i++) {
                int rowNeighbor = neighbors.get(i)[0];
                int colNeighbor = neighbors.get(i)[1];
                if (rowNeighbor >= 0 && rowNeighbor < 8 && colNeighbor >= 0 && colNeighbor < 8 && board.getBoard()[rowNeighbor][colNeighbor] == 0) {
                    gc.drawImage(horse.getBox(), colNeighbor * 60, rowNeighbor * 60, 60, 60);
                    board.getBoard()[rowNeighbor][colNeighbor] = horse.getValueBox();
                }
            }
        }

        if (tab == 0 || tab == 3) {
//            System.out.println("llego");
            board.getBoard()[horse.getPosition()[0]][horse.getPosition()[1]] = horse.getValueBox();
            gc.drawImage(horse.getBox(), horse.getPosition()[1] * 60, horse.getPosition()[0] * 60, 60, 60);

            gc.drawImage(horse.getImage(), col * 60, row * 60, 60, 60);
            board.getBoard()[row][col] = horse.getValueHorse();

            int[] positionGreenHorse = {row, col};
            board.setPositionGreenHorse(positionGreenHorse);
            horse.setPosition(positionGreenHorse);
//            board.showBoard();  
        }

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
}
