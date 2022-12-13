/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Fabian and Lady
 */

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WarHorsesStage extends Stage  {
    
    private FXMLLoader loader;
    /**
     * Constructor de WarHorsesStage
     */
    public WarHorsesStage() {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/FXMLMain.fxml"));
            Parent root = loader.load();
            System.out.println(root.getClass().getName());
            Scene scene = new Scene(root);
            setScene(scene);
            setTitle("War Horses");
            getIcons().add(new Image("/resources/icon.png"));
            setResizable(false);
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteInstance() {
        WarHorsesStageHolder.INSTANCE.close();
        WarHorsesStageHolder.INSTANCE = null;
    }

    public static WarHorsesStage getInstance() {
        return WarHorsesStageHolder.INSTANCE = new WarHorsesStage();
    }

    private static class WarHorsesStageHolder {

        private static WarHorsesStage INSTANCE;
    }
}
