package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuWindow extends Stage
{
    private int currentParticleIndex = 0;
    private double[][] particleInitializers;
    private String[] particleInitializersLabelText = {"Mass (kg * 10^15): ", "Init Vel X (m/s): ", "Init Vel Y (m/s): ", "Init Pos X (m): ", "Init Pos Y (m): "};

    private GridPane gridPaneOptionSel;
    private Label labelCurrentParticle;
    private Button buttonCreateSim;
    private TextField fieldNumParticles;
    private ComboBox<String> comboBoxCurrParticle;
    private TextField[] planetInfoFields;
    private Label[] planetInfoLabels;

    public MenuWindow(double w, double h)
    {
        GridPane menuGridPane = new GridPane();
        menuGridPane.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start");
        startButton.setFont(Font.font(50));
        menuGridPane.add(startButton, 0, 0);
        startButton.setOnAction(event ->
        {
            createMenuGUI();
        });

        setHeight(400);
        setWidth(400);
        setTitle("Start");
        setScene(new Scene(menuGridPane));
        show();
    }

    private void createMenuGUI()
    {
        initMenuGUIComponents();
        createUserInputHandlers();

        setScene(new Scene(gridPaneOptionSel));
    }

    private void initMenuGUIComponents()
    {
        gridPaneOptionSel = new GridPane();
        gridPaneOptionSel.setAlignment(Pos.CENTER);
        gridPaneOptionSel.setPadding(new Insets(3));
        gridPaneOptionSel.setHgap(10);
        gridPaneOptionSel.setVgap(10);

        Label labelNumParticles = new Label("# of particles: ");
        gridPaneOptionSel.getChildren().add(labelNumParticles);
        gridPaneOptionSel.setConstraints(labelNumParticles, 0, 0);

        fieldNumParticles = new TextField("0");
        gridPaneOptionSel.getChildren().add(fieldNumParticles);
        gridPaneOptionSel.setConstraints(fieldNumParticles, 1, 0);

        labelCurrentParticle = new Label("Current Particle: ");
        labelCurrentParticle.setVisible(false);
        gridPaneOptionSel.getChildren().add(labelCurrentParticle);
        gridPaneOptionSel.setConstraints(labelCurrentParticle, 0, 1);

        comboBoxCurrParticle = new ComboBox<>();
        comboBoxCurrParticle.setVisible(false);
        gridPaneOptionSel.getChildren().add(comboBoxCurrParticle);
        gridPaneOptionSel.setConstraints(comboBoxCurrParticle, 1, 1);

        buttonCreateSim = new Button("Create simulation from user input");
        buttonCreateSim.setVisible(false);
        gridPaneOptionSel.getChildren().add(buttonCreateSim);
        gridPaneOptionSel.setConstraints(buttonCreateSim, 0, particleInitializersLabelText.length + 2);
    }

    private void createUserInputHandlers()
    {
        planetInfoLabels = new Label[particleInitializersLabelText.length];
        planetInfoFields = new TextField[particleInitializersLabelText.length];

        for (int i = 0; i < particleInitializersLabelText.length; i++)
        {
            planetInfoLabels[i] = new Label(particleInitializersLabelText[i]);
            planetInfoLabels[i].setVisible(false);
            gridPaneOptionSel.getChildren().add(planetInfoLabels[i]);
            gridPaneOptionSel.setConstraints(planetInfoLabels[i], 0, 2 + i);

            planetInfoFields[i] = new TextField("0");
            planetInfoFields[i].setVisible(false);
            gridPaneOptionSel.getChildren().add(planetInfoFields[i]);
            gridPaneOptionSel.setConstraints(planetInfoFields[i], 1, 2 + i);

            final int temp = i;
            planetInfoFields[i].setOnKeyTyped(event ->
            {
                try
                {
                    particleInitializers[currentParticleIndex][temp] = Double.parseDouble(planetInfoFields[temp].getText());
                }
                catch (Exception e)
                {

                }
            });
        }

        buttonCreateSim.setOnAction(event ->
        {
            createSimWindow();
        });

        fieldNumParticles.setOnKeyTyped(event ->
        {
            try
            {
                if (Integer.parseInt(fieldNumParticles.getText()) >= 1)
                {
                    showUserInputOptions();
                }
            }
            catch (Exception e)
            {
                hideUserInputOptions();
            }
        });

        comboBoxCurrParticle.setOnAction(event ->
                {
                    currentParticleIndex = Integer.parseInt(comboBoxCurrParticle.getValue());

                    if (comboBoxCurrParticle.isVisible())
                    {
                        for (int i = 0; i < particleInitializersLabelText.length; i++)
                            planetInfoFields[i].setText(particleInitializers[currentParticleIndex][i] + "");
                    }
                }
        );

        createReadCSVButton(gridPaneOptionSel);
    }

    private void showUserInputOptions()
    {
        int numParticles = Integer.parseInt(fieldNumParticles.getText());
        particleInitializers = new double[numParticles][particleInitializersLabelText.length];

        ArrayList<String> tempStringParticleIdentifierCombo = new ArrayList<>();

        for (int i = 0; i < particleInitializersLabelText.length; i++)
        {
            planetInfoFields[i].setVisible(true);
            planetInfoLabels[i].setVisible(true);
        }

        for (int i = 0; i < numParticles; i++)
        {
            tempStringParticleIdentifierCombo.add(i + "");

            for (int ii = 0; ii < particleInitializersLabelText.length; ii++)
                particleInitializers[i][ii] = 0;
        }

        buttonCreateSim.setVisible(true);
        labelCurrentParticle.setVisible(true);
        comboBoxCurrParticle.setVisible(true);
        ObservableList tempObList = FXCollections.observableList(tempStringParticleIdentifierCombo);
        comboBoxCurrParticle.setItems(tempObList);
    }

    private void hideUserInputOptions()
    {
        for (int i = 0; i < particleInitializersLabelText.length; i++)
        {
            planetInfoFields[i].setVisible(false);
            planetInfoLabels[i].setVisible(false);
        }
        buttonCreateSim.setVisible(false);
        labelCurrentParticle.setVisible(false);
        comboBoxCurrParticle.setVisible(false);
    }

    private void createSimWindow()
    {
        double[][] particlePassTemp = new double[particleInitializers.length][particleInitializers[0].length];

        for (int i = 0; i < particlePassTemp.length; i++)
        {
            for (int ii = 0; ii < particlePassTemp[0].length; ii++)
                particlePassTemp[i][ii] = particleInitializers[i][ii];
        }

        for (int i = 0; i < particlePassTemp.length; i++)
            particlePassTemp[i][0] *= 1000000000000000.0;

        SimulationWindow simulationWindow = new SimulationWindow(particlePassTemp);
    }

    private void createReadCSVButton(GridPane gridPaneOptionSel)
    {
        Button readCSVButton = new Button("Create simulation from .csv file");
        readCSVButton.setVisible(true);
        gridPaneOptionSel.getChildren().add(readCSVButton);
        gridPaneOptionSel.setConstraints(readCSVButton, 0, particleInitializersLabelText.length + 3);

        readCSVButton.setOnAction(event ->
        {
            FileChooser csvFileChooser = new FileChooser();
            csvFileChooser.setTitle("Choose valid CSV file");
            csvFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File csvFile = csvFileChooser.showOpenDialog(this);

            if (csvFile != null)
                createSimFromCSVFile(csvFile);
        });
    }

    private void createSimFromCSVFile(File csvFile)
    {
        List<List<String>> lines = new ArrayList<>();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null)
            {
                String[] values = currentLine.split(",");
                lines.add(Arrays.asList(values));
            }

            ArrayList<Integer> validIndexes = getIndexesOfValidLinesFromCSV(lines);
            if (validIndexes.size() > 0)
            {
                particleInitializers = new double[validIndexes.size()][particleInitializersLabelText.length];

                for (int i = 0; i < particleInitializers.length; i++)
                {
                    for (int j = 0; j < particleInitializers[0].length; j++)
                        particleInitializers[i][j] = Double.parseDouble(lines.get(validIndexes.get(i)).get(j));
                }

                createSimWindow();
            }
            else
            {
                AlertWindow errorBox = new AlertWindow("Error", "Selected CSV has no valid lines!\nPlease try again.");
            }
        }
        catch (Exception e)
        {

        }
    }

    private ArrayList<Integer> getIndexesOfValidLinesFromCSV(List<List<String>> lines)
    {
        ArrayList<Integer> validIndexes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).size() >= particleInitializersLabelText.length)
            {
                try
                {
                    for (int j = 0; j < lines.get(0).size(); j++)
                        Double.parseDouble(lines.get(i).get(j));

                    validIndexes.add(i);
                }
                catch (Exception e)
                {

                }
            }
        }
        return validIndexes;
    }
}
