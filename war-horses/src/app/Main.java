package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainStage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       MainStage.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
