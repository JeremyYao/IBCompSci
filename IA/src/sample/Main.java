package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    private MenuStage menuStage;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        menuStage = new MenuStage(400,400);
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
