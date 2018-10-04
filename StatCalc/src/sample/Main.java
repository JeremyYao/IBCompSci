package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{
    private Button enterButtonX, clearArrayButton, meanButton, modeButton, rangeButton, medianButton, stdButton, createBoxWhiskerButton, createHistogramButton, enterButtonY, createScatterButton;
    private Pane menuPane, boxWhiskerPane, histogramPane, scatterPlotPane;
    private Scene menuScene, boxWhiskerScene, histogramScene, scatterPlotScene;
    private TextField userInputTextFieldX, userInputTextFieldY;
    private TextArea userInputTextArea, outputTextArea, userInputTextAreaY;
    private ArrayList<Double> userInputtedArrayListX, userInputtedArrayListY;
    private HBox arithmeticRow, dataInputRowX, dataInputRowY;
    private VBox menuColumn;

    private StatCalcLogic statCalcX;

    @Override
    public void start(Stage primaryStage)
    {
        statCalcX = new StatCalcLogic(new double[]{0.0});
        userInputtedArrayListX = new ArrayList<>();
        userInputtedArrayListY = new ArrayList<>();

        menuPane = new Pane();
        menuScene = new Scene(menuPane, 300, 300);
        boxWhiskerPane = new Pane();
        boxWhiskerScene = new Scene(boxWhiskerPane, 500, 500);
        histogramPane = new Pane();
        histogramScene = new Scene(histogramPane, 500, 500);
        scatterPlotPane = new Pane();
        scatterPlotScene = new Scene(scatterPlotPane, 500, 500);

        Image elon = new Image(getClass().getResourceAsStream("Elon.jpg"));
        Label elonLabel = new Label();
        elonLabel.setGraphic(new ImageView(elon));
        elonLabel.setLayoutY(-150);
        elonLabel.setLayoutX(-150);

        setupGUIComponents(primaryStage);

        menuPane.getChildren().addAll(elonLabel, menuColumn);

        primaryStage.setTitle("Jeremy's StatCalc");
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setupGUIComponents(Stage primaryStage)
    {
        dataInputRowX = new HBox();
        arithmeticRow = new HBox();
        menuColumn = new VBox();
        dataInputRowY = new HBox();
        menuColumn.setLayoutX(10);
        menuColumn.setLayoutY(10);

        userInputTextFieldX = new TextField();
        userInputTextFieldX.setMaxWidth(80);

        userInputTextArea = new TextArea("X input data");
        userInputTextArea.setMaxWidth(280);
        userInputTextArea.setMaxHeight(10);

        outputTextArea = new TextArea("Output");
        outputTextArea.setMaxWidth(280);
        outputTextArea.setMaxHeight(10);

        enterButtonX = new Button("Enter Data (X)");
        enterButtonX.setOnAction(event ->
        {
            try
            {
                userInputtedArrayListX.add(Double.parseDouble(userInputTextFieldX.getText()));
                userInputTextArea.setText(userInputtedArrayListX.toString());
                userInputTextFieldX.clear();
            }
            catch (Exception e)
            {

            }
        });

        clearArrayButton = new Button("Clear Data (X and Y)");
        clearArrayButton.setOnAction(event ->
        {
            userInputTextArea.setText("CLEARED");
            userInputTextAreaY.setText("CLEARED");
            userInputtedArrayListX.add(0.0);
            userInputtedArrayListY.add(0.0);
            userInputtedArrayListY.clear();
            userInputtedArrayListX.clear();
        });

        dataInputRowX.getChildren().addAll(userInputTextFieldX, enterButtonX, clearArrayButton);

        meanButton = new Button("Mean");
        meanButton.setOnAction(event ->
        {
            statCalcX.setData(userInputtedArrayListX);
            outputTextArea.setText(statCalcX.calcMean() + "");
        });

        modeButton = new Button("Mode");
        modeButton.setOnAction(event ->
        {
            statCalcX.setData(userInputtedArrayListX);
            if (!statCalcX.calcMode().isEmpty())
                outputTextArea.setText(statCalcX.calcMode().toString());
            else
                outputTextArea.setText("Such mode doesn't exist!");
        });

        medianButton = new Button("Median");
        medianButton.setOnAction(event ->
        {
            statCalcX.setData(userInputtedArrayListX);
            outputTextArea.setText(statCalcX.calcMedian() + "");
        });

        rangeButton = new Button("Range");
        rangeButton.setOnAction(event ->
        {
            statCalcX.setData(userInputtedArrayListX);
            outputTextArea.setText(statCalcX.calcRange() + "");
        });

        stdButton = new Button("STD");
        stdButton.setOnAction(event ->
        {
            statCalcX.setData(userInputtedArrayListX);
            outputTextArea.setText(statCalcX.calcSTD() + "");
        });

        createBoxWhiskerButton = new Button("Create Box and Whisker Plot");
        createBoxWhiskerButton.setOnAction(event ->
        {
            setupBoxWhiskerScene(primaryStage);
            primaryStage.setScene(boxWhiskerScene);
        });

        HBox histoHBox = new HBox();

        Label histoLabel = new Label("Bars: ");
        histoLabel.setTextFill(Paint.valueOf("WHITE"));

        TextField histoTextField = new TextField();

        createHistogramButton = new Button("Create Histogram");
        createHistogramButton.setOnAction(event ->
        {
            try
            {
                setupHistogramScene(primaryStage, Integer.parseInt(histoTextField.getText()));
            }
            catch (Exception e)
            {
                setupHistogramScene(primaryStage, 10);
            }
            primaryStage.setScene(histogramScene);
        });

        histoHBox.getChildren().addAll(histoLabel,histoTextField, createHistogramButton);

        userInputTextFieldY = new TextField();
        userInputTextFieldY.setMaxWidth(80);

        enterButtonY = new Button("Enter Data (Y)");
        userInputTextAreaY = new TextArea("Display y data");
        userInputTextAreaY.setMaxWidth(280);
        userInputTextAreaY.setMaxHeight(10);
        enterButtonY.setOnAction(event ->
        {
            try
            {
                userInputtedArrayListY.add(Double.parseDouble(userInputTextFieldY.getText()));
                userInputTextAreaY.setText(userInputtedArrayListY.toString());
                userInputTextFieldY.clear();
            }
            catch (Exception e)
            {

            }
        });
        dataInputRowY.getChildren().addAll(userInputTextFieldY, enterButtonY);

        createScatterButton = new Button("Create Scatterplot from X and Y");
        createScatterButton.setOnAction(event ->
        {
            setupScatterPlot(primaryStage);
            primaryStage.setScene(scatterPlotScene);
        });

        arithmeticRow.getChildren().addAll(meanButton, modeButton, rangeButton, medianButton, stdButton);
        menuColumn.getChildren().addAll(dataInputRowX, userInputTextArea, outputTextArea, arithmeticRow, createBoxWhiskerButton, histoHBox, dataInputRowY, userInputTextAreaY, createScatterButton);
    }

    public void setupBoxWhiskerScene(Stage primaryStage)
    {
        Button backButton_box = new Button("<--- Back to menu");
        backButton_box.setOnAction(event ->
        {
            primaryStage.setScene(menuScene);
            boxWhiskerPane.getChildren().clear();
        });
        backButton_box.setLayoutX(10);
        backButton_box.setLayoutY(10);

        if (userInputtedArrayListX.size() > 1)
        {
            statCalcX.setData(userInputtedArrayListX);
            double min = statCalcX.getData()[0];
            double median = statCalcX.calcMedian();
            double max = statCalcX.getData()[statCalcX.getData().length - 1];
            double q1 = statCalcX.calcFirstQuartile();
            double q3 = statCalcX.calcThirdQuartile();
            double range = statCalcX.calcRange();

            double minPosX = 20;
            double maxPosX = boxWhiskerScene.getWidth() - 20;
            double q1Pos = maxPosX - (maxPosX - minPosX) * ((max - q1) / range);
            double medianPosX = maxPosX - (maxPosX - minPosX) * ((max - median) / range);
            double q3Pos = maxPosX - (maxPosX - minPosX) * ((max - q3) / range);

            double y = boxWhiskerScene.getHeight() / 2;
            double labelY = y + 35;

            Line middleLineLeft = new Line(minPosX, y, q1Pos, y);
            Line middleLineRight = new Line(maxPosX, y, q3Pos, y);

            Line minLine = new Line(minPosX, y + 30, minPosX, y - 30);
            Label minLabel = new Label(min + "");
            minLabel.setLayoutX(minPosX);
            minLabel.setLayoutY(labelY);

            Line medianLine = new Line(medianPosX, y + 30, medianPosX, y - 30);
            Label medianLabel = new Label(median + "");
            medianLabel.setLayoutX(medianPosX);
            medianLabel.setLayoutY(labelY + 15);

            Line maxLine = new Line(maxPosX, y + 30, maxPosX, y - 30);
            Label maxLabel = new Label(max + "");
            maxLabel.setLayoutX(maxPosX);
            maxLabel.setLayoutY(labelY);

            Line q1Line = new Line(q1Pos, y + 30, q1Pos, y - 30);
            Label q1Label = new Label(q1 + "");
            q1Label.setLayoutX(q1Pos);
            q1Label.setLayoutY(labelY + 30);

            Line q3Line = new Line(q3Pos, y + 30, q3Pos, y - 30);
            Label q3Label = new Label(q3 + "");
            q3Label.setLayoutX(q3Pos);
            q3Label.setLayoutY(labelY + 30);

            Line topIQRLine = new Line(q1Pos, y + 30, q3Pos, y + 30);
            Line bottomIQRLine = new Line(q1Pos, y - 30, q3Pos, y - 30);

            boxWhiskerPane.getChildren().addAll(middleLineRight, middleLineLeft, minLine, medianLine, maxLine, q1Line, medianLabel, minLabel, maxLabel, q1Label, q3Line, q3Label, topIQRLine, bottomIQRLine);
        }
        else
        {
            Label errorLabel = new Label("ERROR, PLEASE MAKE SURE YOUR\nX HAS 2 OR MORE ELEMENTS");
            errorLabel.setFont(Font.font("Comic Sans MS", 30));
            errorLabel.setTextFill(Color.RED);
            errorLabel.setLayoutX(10);
            errorLabel.setLayoutY(histogramScene.getHeight() / 2);
            boxWhiskerPane.getChildren().add(errorLabel);
        }
        boxWhiskerPane.getChildren().add(backButton_box);
    }

    public void setupHistogramScene(Stage primaryStage, int bars_In)
    {
        Button backButton_histo = new Button("<--- Back to menu");
        backButton_histo.setOnAction(event ->
        {
            primaryStage.setScene(menuScene);
            histogramPane.getChildren().clear();
        });
        backButton_histo.setLayoutX(10);
        backButton_histo.setLayoutY(10);

        if (!userInputtedArrayListX.isEmpty())
        {
            double minPosX = 20;
            double maxPosX = boxWhiskerScene.getWidth() - 20;
            double minPosY = 20;
            double maxPosY = 420;

            statCalcX.setData(userInputtedArrayListX);
            int bars = bars_In;
            double intervals = statCalcX.calcRange() / bars;
            Line xAxisLine = new Line(minPosX, maxPosY, maxPosX, maxPosY);
            Line yAxisLine = new Line(minPosX, maxPosY, minPosX, minPosY);

            Rectangle[] barArray = new Rectangle[bars];
            Label[] barLabelArray = new Label[bars];
            int[] occurencesOverIntervals = new int[bars];
            int occurences;

            for(int i = 0; i < bars; i++)
            {
                occurences = 0;
                for (int j = 0; j < statCalcX.getData().length; j++)
                {
                    if (statCalcX.getData()[j] >= statCalcX.getData()[0] + i * intervals && statCalcX.getData()[j] < statCalcX.getData()[0] + i * intervals + intervals)
                        occurences++;
                }
                occurencesOverIntervals[i] = occurences;
            }
            occurences = 0;

            for (int i = statCalcX.getData().length - 1; i >= 0; i--)
            {
                if(statCalcX.getData()[i] == statCalcX.getData()[statCalcX.getData().length-1])
                    occurences++;
                else
                {
                    occurencesOverIntervals[occurencesOverIntervals.length-1] += occurences;
                    break;
                }
            }

            for (int i = 0; i < bars; i++)
            {
                barArray[i] = new Rectangle(minPosX + (double)i/bars * (maxPosX-minPosX), maxPosY - 29* occurencesOverIntervals[i], (maxPosX - minPosX)/(bars), 29*occurencesOverIntervals[i]);
                barLabelArray[i] = new Label("" + (float)(statCalcX.getData()[0] + i * intervals));
                barLabelArray[i].setLayoutY(maxPosY);
                barLabelArray[i].setLayoutX(minPosX + (double)i/bars * (maxPosX-minPosX) - 6);
                histogramPane.getChildren().addAll(barArray[i], barLabelArray[i]);
                barArray[i].setFill(Paint.valueOf("#F52887"));
                barArray[i].setStroke(Paint.valueOf("BLACK"));
            }

            Label lastNumLabel = new Label(statCalcX.getData()[statCalcX.getData().length-1] + "");
            lastNumLabel.setLayoutY(maxPosY);
            lastNumLabel.setLayoutX(maxPosX - 6);

            int maxoccurences = 0;
            for (int i = 0; i < occurencesOverIntervals.length; i++)
            {
                if(maxoccurences < occurencesOverIntervals[i])
                {
                    maxoccurences = occurencesOverIntervals[i];
                }
            }

            for (int i = 0; i <= maxoccurences; i++)
            {
                Label yAxisLabel = new Label(i + "-");
                yAxisLabel.setLayoutX(minPosX-10);
                yAxisLabel.setLayoutY(maxPosY - i *29-10);
                histogramPane.getChildren().add(yAxisLabel);
            }
            histogramPane.getChildren().addAll(xAxisLine, yAxisLine, lastNumLabel);
        }
        else
        {
            Label errorLabel = new Label("ERROR, PLEASE MAKE SURE YOUR\nX ACTUALLY HAS DATA");
            errorLabel.setFont(Font.font("Comic Sans MS", 30));
            errorLabel.setLayoutX(10);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setLayoutY(histogramScene.getHeight() / 2);
            histogramPane.getChildren().add(errorLabel);
        }

        histogramPane.getChildren().addAll(backButton_histo);
    }

    public void setupScatterPlot(Stage primaryStage)
    {
        Button backButton_scatter = new Button("<--- Back to menu");
        backButton_scatter.setOnAction(event ->
        {
            primaryStage.setScene(menuScene);
            scatterPlotPane.getChildren().clear();
        });
        backButton_scatter.setLayoutX(10);
        backButton_scatter.setLayoutY(10);

        if(userInputtedArrayListX.size() == userInputtedArrayListY.size() && !userInputtedArrayListY.isEmpty())
        {
            statCalcX.setData(userInputtedArrayListX);
            StatCalcLogic statCalcY = new StatCalcLogic(userInputtedArrayListY);

            NumberAxis xAxis = new NumberAxis();
            xAxis.setUpperBound(statCalcX.getData()[statCalcX.getData().length-1]);
            xAxis.setLowerBound(statCalcX.getData()[0]);
            xAxis.setAutoRanging(false);
            NumberAxis yAxis = new NumberAxis();
            yAxis.setUpperBound(statCalcY.getData()[statCalcY.getData().length-1]);
            yAxis.setLowerBound(statCalcY.getData()[0]);
            yAxis.setAutoRanging(false);

            ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
            XYChart.Series series = new XYChart.Series();

            double bconst = statCalcX.calcCorrelationCoeff(statCalcY) * statCalcY.calcSTD()/statCalcX.calcSTD();
            double a = statCalcY.calcMean() - bconst * statCalcX.calcMean();

            for (int i = 0; i < userInputtedArrayListY.size(); i++)
            {
                series.getData().add(new XYChart.Data<>(userInputtedArrayListX.get(i), userInputtedArrayListY.get(i)));
            }

            scatterChart.getData().addAll(series);
            scatterChart.setOpacity(.6);

            LineChart lineChart = new LineChart(xAxis, yAxis);

            XYChart.Series seriesBestFit = new XYChart.Series();

            seriesBestFit.getData().add(new XYChart.Data<>(statCalcX.getData()[0], statCalcX.getData()[0] * bconst + a));
            seriesBestFit.getData().add(new XYChart.Data<>(statCalcX.getData()[statCalcX.getData().length-1], statCalcX.getData()[statCalcX.getData().length-1] * bconst + a));
            lineChart.getData().addAll(seriesBestFit);
            lineChart.setOpacity(.6);

            scatterPlotPane.getChildren().addAll(scatterChart,lineChart);
        }
        else
        {
            Label errorLabel = new Label("ERROR, PLEASE MAKE SURE YOUR\nX AND Y ACTUALLY HAS DATA");
            errorLabel.setFont(Font.font("Comic Sans MS", 30));
            errorLabel.setLayoutX(10);
            errorLabel.setTextFill(Color.BLACK);
            errorLabel.setLayoutY(scatterPlotPane.getHeight() / 2);
            scatterPlotPane.getChildren().add(errorLabel);
        }
        scatterPlotPane.getChildren().add(backButton_scatter);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
