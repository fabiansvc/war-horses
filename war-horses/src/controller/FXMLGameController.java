package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Board;
import model.Horse;
import model.Minimax;

public class FXMLGameController implements Initializable {
    
    @FXML
    private Canvas canvas;
    private CanvasWarHorsesController canvasWarHorsesController;
    private Board board;
    private Horse greenHorse;
    private Horse redHorse;
    private GraphicsContext gc;
    private Minimax minimax;
    private boolean turn;
    private boolean statusGame;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new Board();
        board.printBoard();
        gc = canvas.getGraphicsContext2D();
        
        redHorse = new Horse(
                new Image("/resources/horses/redHorse.png"),
                board.getPositionRedHorse(),
                new Image("/resources/boxes/redBox.png"),
                new ArrayList<int[]>(),
                1,
                6
        );
        
        greenHorse = new Horse(
                new Image("/resources/horses/greenHorse.png"),
                board.getPositionGreenHorse(),
                new Image("/resources/boxes/greenBox.png"),
                new ArrayList<int[]>(),
                2,
                5
        );
        
        canvasWarHorsesController = new CanvasWarHorsesController(canvas, board, gc);
        canvasWarHorsesController.setCanvas(new Image("/resources/bonus/bonus.png"), redHorse.getImage(), greenHorse.getImage());
        
        turn = false;
        statusGame = true;
        
        boardChessListener();
        
    }
    
    private void boardChessListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            greenHorse.findPosiblesMovements();
            ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.correctPosiblesMovements(board.getBoard());
            
            if (turn && posiblesMovementsGreenHorse.size() > 0) {
                int row = (int) event.getSceneY() / 60;
                int col = (int) event.getSceneX() / 60;
                if (board.getBoard()[row][col] == 2) {
                    greenHorse.findPosiblesMovements();
                    canvasWarHorsesController.placePosiblesMovements(greenHorse);
                    posiblesMovementsListener(greenHorse);
                    
                }
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
                        canvasWarHorsesController.printPosiblesMovements(horse);
                        horse.getPosiblesMovements().clear();
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
    void initHandle(ActionEvent event) {
        tick();
    }
    
    private void tick() {
        redHorse.findPosiblesMovements();
        ArrayList<int[]> posiblesMovementsRedHorse = redHorse.correctPosiblesMovements(board.getBoard());
        
        showPosibleMovements(posiblesMovementsRedHorse);
        System.out.println("---------------------------------");
        greenHorse.findPosiblesMovements();
        ArrayList<int[]> posiblesMovementsGreenHorse = greenHorse.correctPosiblesMovements(board.getBoard());
        
        if (!turn && posiblesMovementsRedHorse.size() > 0) {   
            int[] movementRedHorse = minimax.movementMachine(board.getBoard(), board.getPositionRedHorse().clone(), board.getPositionGreenHorse().clone());
            board.setPositionRedHorse(movementRedHorse);
            canvasWarHorsesController.updateBoard(movementRedHorse[0], movementRedHorse[1], redHorse);
        }
        
        if (posiblesMovementsGreenHorse.size() > 0) {
            boardChessListener();
            turn = true;
        } else {
            tick();
        }
        
    }
    
    private void showPosibleMovements(ArrayList<int[]> posiblesMovements) {
        for (int i = 0; i < posiblesMovements.size(); i++) {
            
            int rowMovement = posiblesMovements.get(i)[0];
            int colMovement = posiblesMovements.get(i)[1];
            System.out.println(rowMovement + "," + colMovement);
        }
    }
    
}
