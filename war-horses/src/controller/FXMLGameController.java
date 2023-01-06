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
import model.Horse;

public class FXMLGameController implements Initializable {
    @FXML
    private Canvas canvas;
    private CanvasWarHorsesController canvasWarHorsesController;
    private BoardChess boardChess;
    private Horse greenHorse;
    private Horse redHorse;
    private GraphicsContext gc;
    private String gameLevel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardChess = new BoardChess();        
        gc = canvas.getGraphicsContext2D();
        
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

        canvasWarHorsesController = new CanvasWarHorsesController(canvas, boardChess, gc);
        canvasWarHorsesController.setCanvas(new Image("/resources/bonus/bonus.png"), redHorse.getImage(), greenHorse.getImage());
        boardChessListener();
    }

    private void boardChessListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;
            if (boardChess.getBoard()[row][col] == 2) {
                greenHorse.findPosiblesMovements();
                canvasWarHorsesController.placePosiblesMovements(greenHorse);
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
                        canvasWarHorsesController.clearPosiblesMovements(horse);
                        canvasWarHorsesController.updateBoard(rowMovementClicked, colMovementClicked, horse);
                        boardChessListener();
                    }
                }
            }
        });
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }
}
