package source;

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
    private int currentParticleIndex = 0; //Current index of Particle user is giving data for.

    //Text for getting user input for each Particle's parameters.
    private String[] particleParameterLabelsText = {"Mass (kg * 10^15): ", "Initial Velocity X (m/s): ", "Initial Velocity Y (m/s): ", "Initial Position X (m): ", "Initial Position Y (m): "};
    private TextField[] particleParameterFields;
    private Label[] particleParameterLabels;
    private double[][] particleParameters;

    private GridPane gridPaneUserInput;
    private Label labelCurrentParticle;
    private Button buttonCreateSim;
    private TextField fieldNumParticles;
    private ComboBox<String> comboBoxCurrParticle;

    /**
     * Constructs a new MenuWindow object and displays a start button to start the program.
     */
    public MenuWindow()
    {
        GridPane menuGridPane = new GridPane();
        menuGridPane.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start");
        startButton.setFont(Font.font(50));
        menuGridPane.add(startButton, 0, 0);

        //Switch current scene to the menu scene when startButton is pressed
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

    /**
     * Initializes all the GUI components and handlers for user input and particle parameters.
     * Also tells the window to display the user input GUI for creating the simulation.
     */
    private void createMenuGUI()
    {
        initMenuGUIComponents();
        createUserInputHandlers();

        setScene(new Scene(gridPaneUserInput));
    }

    /**
     * Initialize GUI components for taking user input and particle parameters.
     */
    private void initMenuGUIComponents()
    {
        gridPaneUserInput = new GridPane();
        gridPaneUserInput.setAlignment(Pos.CENTER);
        gridPaneUserInput.setPadding(new Insets(3));
        gridPaneUserInput.setHgap(10);
        gridPaneUserInput.setVgap(10);

        Label labelNumParticles = new Label("Number of Particles: ");
        gridPaneUserInput.getChildren().add(labelNumParticles);
        gridPaneUserInput.setConstraints(labelNumParticles, 0, 0);

        fieldNumParticles = new TextField("0");
        gridPaneUserInput.getChildren().add(fieldNumParticles);
        gridPaneUserInput.setConstraints(fieldNumParticles, 1, 0);

        labelCurrentParticle = new Label("Current Particle: ");
        labelCurrentParticle.setVisible(false);
        gridPaneUserInput.getChildren().add(labelCurrentParticle);
        gridPaneUserInput.setConstraints(labelCurrentParticle, 0, 1);

        comboBoxCurrParticle = new ComboBox<>();
        comboBoxCurrParticle.setVisible(false);
        gridPaneUserInput.getChildren().add(comboBoxCurrParticle);
        gridPaneUserInput.setConstraints(comboBoxCurrParticle, 1, 1);

        buttonCreateSim = new Button("Create simulation from user input");
        buttonCreateSim.setVisible(false);
        gridPaneUserInput.getChildren().add(buttonCreateSim);
        gridPaneUserInput.setConstraints(buttonCreateSim, 0, particleParameterLabelsText.length + 2);
    }

    /**
     * Initialize handlers for GUI components to store, and manipulate user inputted data
     * and given particle parameters.
     */
    private void createUserInputHandlers()
    {
        particleParameterLabels = new Label[particleParameterLabelsText.length];
        particleParameterFields = new TextField[particleParameterLabelsText.length];

        //Create labels and fields for getting user input for each particle's parameters.
        for (int i = 0; i < particleParameterLabelsText.length; i++)
        {
            particleParameterLabels[i] = new Label(particleParameterLabelsText[i]);
            particleParameterLabels[i].setVisible(false);
            gridPaneUserInput.getChildren().add(particleParameterLabels[i]);
            gridPaneUserInput.setConstraints(particleParameterLabels[i], 0, 2 + i);

            particleParameterFields[i] = new TextField("0");
            particleParameterFields[i].setVisible(false);
            gridPaneUserInput.getChildren().add(particleParameterFields[i]);
            gridPaneUserInput.setConstraints(particleParameterFields[i], 1, 2 + i);

            final int temp = i;
            //Store user's input for the current selected particle's parameter.
            particleParameterFields[i].setOnKeyTyped(event ->
            {
                try
                {
                    particleParameters[currentParticleIndex][temp] = Double.parseDouble(particleParameterFields[temp].getText());
                }
                catch (NumberFormatException e)
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
            //display user input options for construction multiple instances of Particle
            //when the number of particles wanted is greater or equal to one and is an integer.
            try
            {
                if (Integer.parseInt(fieldNumParticles.getText()) >= 1)
                {
                    showUserInputOptions();
                }
            }
            //Hide user input options when number of particles wanted if less than 1 or isn't an integer
            catch (NumberFormatException e)
            {
                hideUserInputOptions();
            }
        });

        comboBoxCurrParticle.setOnAction(event ->
                {
                    currentParticleIndex = Integer.parseInt(comboBoxCurrParticle.getValue());

                    //Sets the text of all the fields to the parameters
                    //the user entered when user interacts with the drop-down for the current particle.
                    if (comboBoxCurrParticle.isVisible())
                    {
                        for (int i = 0; i < particleParameterLabelsText.length; i++)
                            particleParameterFields[i].setText(particleParameters[currentParticleIndex][i] + "");
                    }
                }
        );

        createReadCSVButton(gridPaneUserInput);
    }

    /**
     * Sets visible associated GUI components and input options for constructing the instance(s) of Particle.
     */
    private void showUserInputOptions()
    {
        int numParticles = Integer.parseInt(fieldNumParticles.getText());
        particleParameters = new double[numParticles][particleParameterLabelsText.length];

        ArrayList<String> tempComboItemCurrentParticles = new ArrayList<>();

        for (int i = 0; i < particleParameterLabelsText.length; i++)
        {
            particleParameterFields[i].setVisible(true);
            particleParameterLabels[i].setVisible(true);
        }

        for (int i = 0; i < numParticles; i++)
        {
            //Create arrayList of numerical Strings strings starting at 0
            tempComboItemCurrentParticles.add(i + "");

            //Reset all stored values for initializing instance(s) of Particle
            for (int ii = 0; ii < particleParameterLabelsText.length; ii++)
                particleParameters[i][ii] = 0;
        }

        buttonCreateSim.setVisible(true);
        labelCurrentParticle.setVisible(true);
        comboBoxCurrParticle.setVisible(true);

        ObservableList tempObList = FXCollections.observableList(tempComboItemCurrentParticles);
        comboBoxCurrParticle.setItems(tempObList);
    }

    /**
     * Hides associated GUI components and input options for constructing the instance(s) of Particle.
     */
    private void hideUserInputOptions()
    {
        for (int i = 0; i < particleParameterLabelsText.length; i++)
        {
            particleParameterFields[i].setVisible(false);
            particleParameterLabels[i].setVisible(false);
        }
        buttonCreateSim.setVisible(false);
        labelCurrentParticle.setVisible(false);
        comboBoxCurrParticle.setVisible(false);
    }

    /**
     * Pass modified stored particle parameters into new instance of SimulationWindow
     */
    private void createSimWindow()
    {
        //Create copy of currently stored particle parameters.
        double[][] particlePassTemp = new double[particleParameters.length][particleParameters[0].length];
        for (int i = 0; i < particlePassTemp.length; i++)
        {
            for (int ii = 0; ii < particlePassTemp[0].length; ii++)
                particlePassTemp[i][ii] = particleParameters[i][ii];
        }

        //Multiply the mass by 10^15 for each particle.
        for (int i = 0; i < particlePassTemp.length; i++)
            particlePassTemp[i][0] *= 1000000000000000.0;

        SimulationWindow simulationWindow = new SimulationWindow(particlePassTemp);
    }

    /**
     * Initialize GUI component and handlers for the reading in .CSVs
     *
     * @param gridPaneOptionSel Pane where the read .CSV button will be added.
     */
    private void createReadCSVButton(GridPane gridPaneOptionSel)
    {
        Button readCSVButton = new Button("Create simulation from .csv file");
        readCSVButton.setVisible(true);
        gridPaneOptionSel.getChildren().add(readCSVButton);
        gridPaneOptionSel.setConstraints(readCSVButton, 0, particleParameterLabelsText.length + 3);

        readCSVButton.setOnAction(event ->
        {
            //Set parameters so that user can only select .csv files.
            FileChooser csvFileChooser = new FileChooser();
            csvFileChooser.setTitle("Choose valid CSV file");
            csvFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File csvFile = csvFileChooser.showOpenDialog(this);

            //Check to see if user selected a CSV file.
            if (csvFile != null)
                createSimFromCSVFile(csvFile);
        });
    }

    /**
     * Attempt to create a new instance of SimulationWindow from .CSV file.
     *
     * @param csvFile csv file in the form of an File object to be read.
     */
    private void createSimFromCSVFile(File csvFile)
    {
        List<List<String>> lines = new ArrayList<>();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
            String currentLine;

            //Stores the values inside the CSV into a two-dimensional list of Strings
            while ((currentLine = bufferedReader.readLine()) != null)
            {
                String[] values = currentLine.split(",");
                lines.add(Arrays.asList(values));
            }

            //Get lines of the .CSV file that can be used to initialize Particle
            ArrayList<Integer> validLines = getIndexesOfValidLinesFrom2DStringList(lines);

            //Store the valid parameters and create the simulation if there are valid lines
            if (validLines.size() > 0)
            {
                particleParameters = new double[validLines.size()][particleParameterLabelsText.length];

                for (int i = 0; i < particleParameters.length; i++)
                {
                    for (int j = 0; j < particleParameters[0].length; j++)
                        particleParameters[i][j] = Double.parseDouble(lines.get(validLines.get(i)).get(j));
                }

                createSimWindow();
            }
            else
            {
                AlertWindow noLinesErrorWindow = new AlertWindow("[!] Error [!]", "Selected CSV has no valid lines!\nPlease try again.");
            }
        }
        catch (Exception e)
        {
            AlertWindow noReadErrorWindow = new AlertWindow("[!] Error [!]", "Selected CSV wasn't able to be read\nPlease try again.");
        }
    }

    /**
     * Determines which lines inside a 2-dimensional list of Strings, from a CSV, that can be used to initialize instances of Particle.
     *
     * @param lines 2-dimensional list of Strings, where the first dimension indicates the line.
     * @return ArrayList<Integer> An arraylist containing all the valid lines that can be used to construct instances
     *                            of Particle.
     */
    private ArrayList<Integer> getIndexesOfValidLinesFrom2DStringList(List<List<String>> lines)
    {
        ArrayList<Integer> validIndexes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).size() >= particleParameterLabelsText.length)
            {
                try
                {
                    for (int j = 0; j < lines.get(0).size(); j++)
                        Double.parseDouble(lines.get(i).get(j));

                    validIndexes.add(i);
                }
                catch (NumberFormatException e)
                {

                }
            }
        }
        return validIndexes;
    }
}
