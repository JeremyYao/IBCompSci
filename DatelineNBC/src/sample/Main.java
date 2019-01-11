package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox all = new VBox();
        Pane mainPane = new Pane();
        Scene mainScene = new Scene(mainPane);
        mainPane.getChildren().addAll();
        HBox inputDateHBox = new HBox();
        inputDateHBox.setSpacing(10);
        String[] monthsArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        monthsArray
                );
        ComboBox comboBox = new ComboBox(options);
        TextField dayField = new TextField();
        dayField.setFont(Font.font("Comic Sans MS", 12));
        dayField.setMaxWidth(30);
        dayField.setMinWidth(30);

        TextField yearField = new TextField();
        yearField.setFont(Font.font("Comic Sans MS", 12));
        yearField.setMaxWidth(50);
        yearField.setMinWidth(50);

        Button getDOWButton = new Button("get day");
        getDOWButton.setFont(Font.font("Comic Sans MS", 12));
        inputDateHBox.getChildren().addAll(comboBox, dayField, yearField, getDOWButton);

        HBox outPutHBox = new HBox();
        TextField outPutField = new TextField("");
        outPutField.setMinWidth(100);
        outPutField.setMaxWidth(100);
        outPutField.setFont(Font.font("Comic Sans MS", 12));

        getDOWButton.setOnAction(event ->
        {
            int m = 0;

            for (int i = 0; i < monthsArray.length; i++)
            {
                if(comboBox.getValue().equals(monthsArray[i]))
                {
                    m = i + 1;
                }
            }
        outPutField.setText(Dater.getDayofWeek(m, Integer.parseInt(dayField.getText()), Integer.parseInt(yearField.getText())));
        });

        outPutHBox.getChildren().add(outPutField);
        all.getChildren().addAll(inputDateHBox, outPutHBox);
        mainPane.getChildren().add(all);
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
