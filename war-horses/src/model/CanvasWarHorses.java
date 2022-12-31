package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasWarHorses {

    private Canvas canvas;
    private BoardChess boardChess;
    private GraphicsContext gc;
    private RedHorse redHorse;
    private GreenHorse greenHorse;

    public CanvasWarHorses(Canvas canvas, BoardChess boardChess, GraphicsContext gc, RedHorse redHorse, GreenHorse greenHorse) {
        this.canvas = canvas;
        this.boardChess = boardChess;
        this.gc = gc;
        this.redHorse = redHorse;
        this.greenHorse = greenHorse;
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

    public void listener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;
            if (boardChess.getBoard()[row][col] == 2) {
                greenHorse.findPosiblesMovements();
                placePosiblesMovements();
                posiblesMovementsListener();
            }
        });
    }

    private void placePosiblesMovements() {
        for (int i = 0; i < greenHorse.getPosiblesMovements().size(); i++) {
            int rowMovement = greenHorse.getPosiblesMovements().get(i)[0];
            int colMovement = greenHorse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = boardChess.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.GOLDENROD);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }
    }

    private void posiblesMovementsListener() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int row = (int) event.getSceneY() / 60;
            int col = (int) event.getSceneX() / 60;

            for (int i = 0; i < greenHorse.getPosiblesMovements().size(); i++) {
                int rowMovementClicked = greenHorse.getPosiblesMovements().get(i)[0];
                int colMovementClicked = greenHorse.getPosiblesMovements().get(i)[1];

                if (rowMovementClicked >= 0 && colMovementClicked >= 0 && rowMovementClicked < 8 && colMovementClicked < 8) {
                    if (row == rowMovementClicked && col == colMovementClicked) {
                        clearPosiblesMovements();
                        updateBoard(row, col);
                        listener();
                    }
                }
            }
        });
    }

    private void clearPosiblesMovements() {
        for (int i = 0; i < greenHorse.getPosiblesMovements().size(); i++) {
            int rowMovement = greenHorse.getPosiblesMovements().get(i)[0];
            int colMovement = greenHorse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = boardChess.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.WHITE);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }
        greenHorse.getPosiblesMovements().clear();
    }

    private void updateBoard(int row, int col) {
        boardChess.getBoard()[greenHorse.getPosition()[0]][greenHorse.getPosition()[1]] = 5;
        gc.drawImage(greenHorse.getBox(), greenHorse.getPosition()[1] * 60, greenHorse.getPosition()[0] * 60, 60, 60);

        gc.drawImage(greenHorse.getImage(), col * 60, row * 60, 60, 60);
        boardChess.getBoard()[row][col] = 2;

        int[] positionGreenHorse = {row, col};
        boardChess.setPositionGreenHorse(positionGreenHorse);
        greenHorse.setPosition(positionGreenHorse);
        boardChess.showBoard();
    }

}
