package source;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlertWindow extends Stage
{
    /**
     * Constructs AlertWindow object and displays a new alert pop-up window.
     *
     * @param title The title of the window
     * @param message Intended message for user to read
     */
    public AlertWindow(String title, String message)
    {
        setTitle(title);
        setWidth(300);
        setHeight(150);
        setAlwaysOnTop(true);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(1));

        //Creates the message to be displayed
        Label messageLabel = new Label(message);

        //Creates Button object to close out of window
        Button okayButton = new Button("Okay");
        okayButton.setOnAction(event ->
        {
            close();
        });

        //Adds and displays message and okayButton in the window.
        gridPane.getChildren().addAll(messageLabel, okayButton);
        gridPane.setConstraints(messageLabel, 0,0);
        gridPane.setConstraints(okayButton, 1,1);

        setScene(new Scene(gridPane));
        show();
    }
}
