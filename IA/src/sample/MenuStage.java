package sample;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuStage extends Stage
{
    private Button startButton;
    public MenuStage(double w, double h)
    {
        super();
        GridPane menuGridPane = new GridPane();
        startButton = new Button("Start");
        startButton.setOnAction(event ->
        {
            goToOptionSelect();
        });
        menuGridPane.add(startButton,0,0);
        this.setHeight(400);
        this.setWidth(400);
        this.setTitle("Start");
        this.setScene(new Scene(menuGridPane));
        this.show();
    }

    public void goToOptionSelect()
    {
        GridPane gridPaneOptionSel = new GridPane();
        Scene sceneOptionSel = new Scene(gridPaneOptionSel);
        this.setScene(sceneOptionSel);

    }
}
