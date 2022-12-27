package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.BoardChess;

/**
 * FXML Controller class
 *
 * @author Fabián Valencia
 */
public class FXMLGameController implements Initializable {
    private String gameLevel;
    private BoardChess boardChesse;
    private int [][] board;

    private Image greenHorseImage, redHorseImage, bonusImage;
    
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardChesse = new BoardChess(); 
        board = boardChesse.getBoard();
        
        canvas.setHeight(480);
        canvas.setWidth(480);        
        gc = canvas.getGraphicsContext2D();
        
        loadImages();
        createBoard();
        placeTabs();
    }    
    
    private void loadImages(){
        greenHorseImage = new Image("/resources/horses/greenHorse.png");
        redHorseImage = new Image("/resources/horses/redHorse.png");
        bonusImage = new Image("/resources/bonus/bonus.png");
    }
    
    private void createBoard(){
        for (int i = 0; i <= board.length; i++) {
            gc.strokeLine(0, i*60, 480, i*60);
            gc.strokeLine(i*60, 0, i*60, 480);             
        }                     
    }    
    
    private void placeTabs(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == 3){
                    gc.drawImage(bonusImage, i*60 + 6, j*60 + 6, 48, 48);
                }else if(board[i][j] == 1){
                    gc.drawImage(redHorseImage, i*60 + 6, j*60 + 6, 48, 48);
                }else if(board[i][j] == 2){
                    gc.drawImage(greenHorseImage, i*60 + 6, j*60 + 6, 48, 48);
                }                  
            }           
        }    
    }
    
    public void setGameLevel(String gameLevel){
        this.gameLevel = gameLevel;  
    }
}
