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
import model.BoardChess;
import model.CanvasWarHorses;
import model.Horse;

public class FXMLGameController implements Initializable {

    @FXML
    private Canvas canvas;
    private CanvasWarHorses canvasWarHorses;
    private BoardChess boardChess;
    private Horse greenHorse;
    private Horse redHorse;
    private GraphicsContext gc;

    private String gameLevel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardChess = new BoardChess();
        greenHorse = new Horse(
                new Image("/resources/horses/greenHorse.png"),
                boardChess.getPositionGreenHorse(),
                new Image("/resources/boxes/greenBox.png"),
                new ArrayList<int[]>()
        );

        redHorse = new Horse(
                new Image("/resources/horses/redHorse.png"),
                boardChess.getPositionRedHorse(),
                new Image("/resources/boxes/redBox.png"),
                new ArrayList<int[]>()
        );

        gc = canvas.getGraphicsContext2D();

        canvasWarHorses = new CanvasWarHorses(canvas, boardChess, gc);
        setCanvas();
        BoardChessListener();
    }

    public void setCanvas() {
        canvas.setHeight(480);
        canvas.setWidth(480);

        for (int i = 0; i <= boardChess.getBoard().length; i++) {
            gc.strokeLine(0, i * 60, 480, i * 60);
            gc.strokeLine(i * 60, 0, i * 60, 480);
        }

        for (int i = 0; i < boardChess.getBoard().length; i++) {
            for (int j = 0; j < boardChess.getBoard().length; j++) {
                if (boardChess.getBoard()[i][j] == 3) {
                    gc.drawImage(new Image("/resources/bonus/bonus.png"), j * 60 + 6, i * 60 + 6, 48, 48);
                } else if (boardChess.getBoard()[i][j] == 1) {
                    gc.drawImage(redHorse.getImage(), j * 60, i * 60, 60, 60);
                } else if (boardChess.getBoard()[i][j] == 2) {
                    gc.drawImage(greenHorse.getImage(), j * 60, i * 60, 60, 60);
                }
            }
        }
    }

    private void BoardChessListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;
            if (boardChess.getBoard()[row][col] == 2) {
                greenHorse.findPosiblesMovements();
                canvasWarHorses.placePosiblesMovements(greenHorse);
                posiblesMovementsListener(greenHorse);                
            }
        });
    }

    private void posiblesMovementsListener(Horse horse) {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;
            for (int i = 0; i < horse.getPosiblesMovements().size(); i++) {
                int rowMovementClicked = horse.getPosiblesMovements().get(i)[0];
                int colMovementClicked = horse.getPosiblesMovements().get(i)[1];

                if (rowMovementClicked >= 0 && colMovementClicked >= 0 && rowMovementClicked < 8 && colMovementClicked < 8) {
                    if (row == rowMovementClicked && col == colMovementClicked) {
                        canvasWarHorses.clearPosiblesMovements(horse);
                        canvasWarHorses.updateBoard(rowMovementClicked, colMovementClicked, horse);
                        BoardChessListener();
                    }
                }
            }
        });
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

}
