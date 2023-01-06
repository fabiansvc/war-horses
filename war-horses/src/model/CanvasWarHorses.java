package model;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasWarHorses {

    private Canvas canvas;
    private BoardChess boardChess;
    private GraphicsContext gc;

    public CanvasWarHorses(Canvas canvas, BoardChess boardChess, GraphicsContext gc) {
        this.canvas = canvas;
        this.boardChess = boardChess;
        this.gc = gc;
    }

    public void placePosiblesMovements(Horse horse) {
        for (int i = 0; i < horse.getPosiblesMovements().size(); i++) {
            int rowMovement = horse.getPosiblesMovements().get(i)[0];
            int colMovement = horse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = boardChess.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.GOLDENROD);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
            }
        }
    }


    public void clearPosiblesMovements(Horse horse) {
        for (int i = 0; i < horse.getPosiblesMovements().size(); i++) {
            int rowMovement = horse.getPosiblesMovements().get(i)[0];
            int colMovement = horse.getPosiblesMovements().get(i)[1];

            if (rowMovement >= 0 && colMovement >= 0 && rowMovement < 8 && colMovement < 8) {
                int tab = boardChess.getBoard()[rowMovement][colMovement];
                if (tab == 0 || tab == 3) {
                    gc.setStroke(Color.WHITE);
                    gc.strokeRect(colMovement * 60 + 6, rowMovement * 60 + 6, 48, 48);
                }
                
            }
        }
        horse.getPosiblesMovements().clear();
    }

    public void updateBoard(int row, int col, Horse horse) {        
        if(boardChess.getBoard()[row][col] == 3){
            ArrayList<int[]> neighbors = getNeighbors(row, col);
            
            for (int i = 0; i < neighbors.size(); i++) {
                int rowNeighbor = neighbors.get(i)[0];
                int colNeighbor = neighbors.get(i)[1];
                if(rowNeighbor >= 0 && rowNeighbor < 8 && colNeighbor >= 0 && colNeighbor < 8){
                    gc.drawImage(horse.getBox(), colNeighbor * 60,rowNeighbor * 60, 60, 60);
                    boardChess.getBoard()[rowNeighbor][colNeighbor] = 5;                    
                }                   
            }
        }
        
        boardChess.getBoard()[horse.getPosition()[0]][horse.getPosition()[1]] = 5;
        gc.drawImage(horse.getBox(), horse.getPosition()[1] * 60, horse.getPosition()[0] * 60, 60, 60);

        gc.drawImage(horse.getImage(), col * 60, row * 60, 60, 60);
        boardChess.getBoard()[row][col] = 2;

        int[] positionGreenHorse = {row, col};
        boardChess.setPositionGreenHorse(positionGreenHorse);
        horse.setPosition(positionGreenHorse);
        boardChess.showBoard();
      
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
