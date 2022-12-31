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
import model.CanvasWarHorses;
import model.GreenHorse;
import model.RedHorse;

public class FXMLGameController implements Initializable {
    @FXML
    private Canvas canvas;
    private CanvasWarHorses canvasWarHorses;


    private BoardChess boardChesse;
    private GreenHorse greenHorse;
    private RedHorse redHorse;
    
    private String gameLevel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardChesse = new BoardChess();
        greenHorse = new GreenHorse(
                new Image("/resources/horses/greenHorse.png"),
                boardChesse.getPositionGreenHorse(),
                new Image("/resources/boxes/greenBox.png"),
                new ArrayList<int[]>()
        );

        redHorse = new RedHorse(
                new Image("/resources/horses/redHorse.png"),
                boardChesse.getPositionRedHorse(),
                new Image("/resources/boxes/redBox.png"),
                new ArrayList<int[]>()
        );
        
        canvasWarHorses = new CanvasWarHorses(canvas, boardChesse, canvas.getGraphicsContext2D(), redHorse, greenHorse);
        
        canvasWarHorses.setCanvas();
        canvasWarHorses.listener();
    }
    
    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

}
