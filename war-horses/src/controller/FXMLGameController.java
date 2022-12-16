package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Fabián Valencia
 */
public class FXMLGameController implements Initializable {
    private String gameLevel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void setGameLevel(String gameLevel){
        this.gameLevel = gameLevel;
    }
    
}
