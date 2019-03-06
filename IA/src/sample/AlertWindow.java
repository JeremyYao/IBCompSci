package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlertWindow extends Stage
{
    public AlertWindow(String title, String message)
    {
        setTitle(title);
        setWidth(300);
        setHeight(150);
        setAlwaysOnTop(true);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(1));
        
        Label messageLabel = new Label(message);
        Button okayButton = new Button("Okay");
        okayButton.setOnAction(event ->
        {
            close();
        });

        gridPane.getChildren().addAll(messageLabel, okayButton);
        gridPane.setConstraints(messageLabel, 0,0);
        gridPane.setConstraints(okayButton, 1,1);

        setScene(new Scene(gridPane));
        show();
    }
}
