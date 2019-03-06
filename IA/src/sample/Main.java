package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    private MenuWindow menuWindow;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        menuWindow = new MenuWindow(400,400);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
