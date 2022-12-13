package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.WarHorsesStage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       WarHorsesStage.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
