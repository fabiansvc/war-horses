package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Board;
import model.Horse;
import model.Minimax;

public class FXMLGameController implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Label amoutRedHorse;
    @FXML
    private Label amoutGreenHorse;
    @FXML
    private Label resultLabel;
    @FXML
    private Label winnerLabel;
    @FXML
    private Button startBtn;

    private CanvasWarHorsesController canvasWarHorsesController;
    private Board board;
    private Horse greenHorse;
    private Horse redHorse;
    private GraphicsContext gc;
    private Minimax minimax;
    private boolean turn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new Board();
        board.printBoard();
        gc = canvas.getGraphicsContext2D();

        redHorse = new Horse(
                new Image("/resources/horses/redHorse.png"),
                board.getPositionRedHorse(),
                new Image("/resources/boxes/redBox.png"),
                1,
                6
        );

        greenHorse = new Horse(
                new Image("/resources/horses/greenHorse.png"),
                board.getPositionGreenHorse(),
                new Image("/resources/boxes/greenBox.png"),
                2,
                5
        );

        canvasWarHorsesController = new CanvasWarHorsesController(canvas, board, gc);
        canvasWarHorsesController.setCanvas(new Image("/resources/bonus/bonus.png"), redHorse.getImage(), greenHorse.getImage());

        turn = false;
        boardChessListener();

    }

    private void boardChessListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.getPosiblesMovements(board.getBoard());

            if (turn && posiblesMovementsGreenHorse.size() > 0) {
                int row = (int) event.getSceneY() / 60;
                int col = (int) event.getSceneX() / 60;
                if (board.getBoard()[row][col] == 2) {
                    canvasWarHorsesController.placePosiblesMovements(greenHorse, posiblesMovementsGreenHorse);
                    posiblesMovementsListener(greenHorse, posiblesMovementsGreenHorse);
                }
            }
        });
    }

    private void posiblesMovementsListener(Horse horse, ArrayList<int[]> posiblesMovements) {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;
            for (int i = 0; i < posiblesMovements.size(); i++) {
                int rowMovementClicked = posiblesMovements.get(i)[0];
                int colMovementClicked = posiblesMovements.get(i)[1];

                if (rowMovementClicked >= 0 && colMovementClicked >= 0 && rowMovementClicked < 8 && colMovementClicked < 8) {
                    if (row == rowMovementClicked && col == colMovementClicked) {
                        canvasWarHorsesController.printPosiblesMovements(horse, posiblesMovements);
                        canvasWarHorsesController.updateBoard(rowMovementClicked, colMovementClicked, horse);
                        turn = false;
                        tick();
                    }
                }
            }
        });
    }

    public void setGameLevel(String gameLevel) {
        int depth = -1;
        switch (gameLevel.toLowerCase()) {
            case "easy":
                depth = 2;
                break;
            case "medium":
                depth = 4;
                break;
            case "hard":
                depth = 6;
                break;
            default:
                throw new AssertionError();
        }
        minimax = new Minimax(depth);
    }

    @FXML
    public void onStartHandle(ActionEvent event) {
        startBtn.setVisible(false);
        tick();
    }

    @FXML
    public void onReturnHandle(ActionEvent event) {

    }

    private void tick() {
        ArrayList<int[]> posiblesMovementsRedHorse = redHorse.getPosiblesMovements(board.getBoard());
        ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.getPosiblesMovements(board.getBoard());

        if (!turn && posiblesMovementsRedHorse.size() > 0) {
            int[] movementRedHorse = minimax.getMovementMachine(board.getBoard(), board.getPositionRedHorse().clone(), board.getPositionGreenHorse().clone());
            board.setPositionRedHorse(movementRedHorse);
            canvasWarHorsesController.updateBoard(movementRedHorse[0], movementRedHorse[1], redHorse);
        }

        int amountTabsRedHorse = board.getValuesBoxbyHorse(redHorse.getValueHorse(), redHorse.getValueBox(), board.getBoard());
        int amoutTabsGreenHorse = board.getValuesBoxbyHorse(greenHorse.getValueHorse(), greenHorse.getValueBox(), board.getBoard());
        updateScore(amountTabsRedHorse, amoutTabsGreenHorse);

        if (posiblesMovementsGreenHorse.size() > 0) {
            boardChessListener();
            turn = true;
        } else if (posiblesMovementsRedHorse.size() > 0) {
            tick();
        } else if (posiblesMovementsGreenHorse.size() == 0 && posiblesMovementsRedHorse.size() == 0) {
            gameOver(amountTabsRedHorse, amoutTabsGreenHorse);
        }
    }

    private void updateScore(int amountTabsRedHorse, int amoutTabsGreenHorse) {
        amoutRedHorse.setText(String.valueOf(amountTabsRedHorse));
        amoutGreenHorse.setText(String.valueOf(amoutTabsGreenHorse));
    }

    private void gameOver(int amountTabsRedHorse, int amoutTabsGreenHorse) {
        String message = "";

        if (amoutTabsGreenHorse > amountTabsRedHorse) {
            message = "Green horse has won";
        } else if (amoutTabsGreenHorse < amountTabsRedHorse) {
            message = "Red horse has won";
        } else {
            message = "There is a tie";
        }
        resultLabel.setVisible(true);
        winnerLabel.setText(message);
    }
}
