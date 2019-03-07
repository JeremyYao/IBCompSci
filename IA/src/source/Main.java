package source;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    private MenuWindow menuWindow;

    /**
     * Creates new instance of menuWindow and starts the application
     * */
    public void start(Stage primaryStage) throws Exception
    {
        menuWindow = new MenuWindow();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
