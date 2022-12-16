package view;

/**
 *
 * @author Fabián Valencia
 */

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainStage extends Stage  {
    
    private FXMLLoader loader;
    
    public MainStage() {
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
        MainStageHolder.INSTANCE.close();
        MainStageHolder.INSTANCE = null;
    }

    public static MainStage getInstance() {
        return MainStageHolder.INSTANCE = new MainStage();
    }

    private static class MainStageHolder {

        private static MainStage INSTANCE;
    }
}
