package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Board;

/**
 * FXML Controller class
 *
 * @author Fabián Valencia
 */
public class FXMLGameController implements Initializable {
    private String gameLevel;
    private Board board;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new Board();
        board.showBoard();
    }    
    
    public void setGameLevel(String gameLevel){
        this.gameLevel = gameLevel;
       
    }
  
    
    
}
