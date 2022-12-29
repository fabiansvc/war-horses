package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.BoardChess;

/**
 * FXML Controller class
 *
 * @author Fabián Valencia
 */
public class FXMLGameController implements Initializable {

    @FXML
    private Canvas canvas;
    private String gameLevel;
    private BoardChess boardChesse;
    private int[][] board;
    private ArrayList<int[]> greenHorseMovements;
    private Image greenHorseImage, redHorseImage, bonusImage;
    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardChesse = new BoardChess();
        board = boardChesse.getBoard();
        greenHorseMovements = new ArrayList<>();

        canvas.setHeight(480);
        canvas.setWidth(480);
        gc = canvas.getGraphicsContext2D();

        loadImages();
        createBoard();
        placeTabs();
        canvasEventListener();
    }

    private void loadImages() {
        greenHorseImage = new Image("/resources/horses/greenHorse.png");
        redHorseImage = new Image("/resources/horses/redHorse.png");
        bonusImage = new Image("/resources/bonus/bonus.png");
    }

    private void createBoard() {
        for (int i = 0; i <= board.length; i++) {
            gc.strokeLine(0, i * 60, 480, i * 60);
            gc.strokeLine(i * 60, 0, i * 60, 480);
        }
    }

    private void placeTabs() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 3) {
                    gc.drawImage(bonusImage, j * 60 + 6, i * 60 + 6, 48, 48);
                } else if (board[i][j] == 1) {
                    gc.drawImage(redHorseImage, j * 60, i * 60, 60, 60);
                } else if (board[i][j] == 2) {
                    gc.drawImage(greenHorseImage, j * 60, i * 60, 60, 60);
                }
            }
        }
    }

    private void canvasEventListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;

            if (board[row][col] == 2) {
                setGreenHorseMovements(row, col);
                placeIndicators();
            }
        });
    }

    private void setGreenHorseMovements(int row, int col) {
        int[] topLeft = new int[2];
        topLeft[0] = row - 1;
        topLeft[1] = col - 2;
        greenHorseMovements.add(topLeft);

        int[] bottomLeft = new int[2];
        bottomLeft[0] = row + 1;
        bottomLeft[1] = col - 2;
        greenHorseMovements.add(bottomLeft);

        int[] leftTop = new int[2];
        leftTop[0] = row - 2;
        leftTop[1] = col - 1;
        greenHorseMovements.add(leftTop);

        int[] rightTop = new int[2];
        rightTop[0] = row - 2;
        rightTop[1] = col + 1;
        greenHorseMovements.add(rightTop);

        int[] topRight = new int[2];
        topRight[0] = row - 1;
        topRight[1] = col + 2;
        greenHorseMovements.add(topRight);

        int[] bottomRight = new int[2];
        bottomRight[0] = row + 1;
        bottomRight[1] = col + 2;
        greenHorseMovements.add(bottomRight);

        int[] leftBottom = new int[2];
        leftBottom[0] = row + 2;
        leftBottom[1] = col - 1;
        greenHorseMovements.add(leftBottom);

        int[] rightBottom = new int[2];
        rightBottom[0] = row + 2;
        rightBottom[1] = col + 1;
        greenHorseMovements.add(rightBottom);
    }

    private void placeIndicators() {
        for (int i = 0; i < greenHorseMovements.size(); i++) {
            int rowMovement = greenHorseMovements.get(i)[0];
            int colMovement = greenHorseMovements.get(i)[1];
            
            if (rowMovement * colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = board[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {      
                    gc.setStroke(Color.GOLDENROD);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

}
