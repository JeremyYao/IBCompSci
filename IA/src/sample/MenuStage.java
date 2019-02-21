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
import javafx.stage.Stage;

import java.util.ArrayList;

public class MenuStage extends Stage
{
    private Scene menuScene;
    private int numParticles = 0, currentParticleIndex = 0;
    private double[][] particleInitFieldsPassArry;

    public MenuStage(double w, double h)
    {
        super();
        GridPane menuGridPane = new GridPane();
        menuGridPane.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start");
        startButton.setFont(Font.font("COMIC SANS MS", 44));
        startButton.setOnAction(event ->
        {
            initOptionSelect();
        });
        menuGridPane.add(startButton, 0, 0);
        this.setHeight(400);
        this.setWidth(400);
        this.setTitle("Start");
        this.setScene(new Scene(menuGridPane));
        this.show();
    }

    public void initOptionSelect()
    {
        GridPane gridPaneOptionSel = new GridPane();
        gridPaneOptionSel.setAlignment(Pos.CENTER);
        gridPaneOptionSel.setPadding(new Insets(3));
        gridPaneOptionSel.setHgap(10);
        gridPaneOptionSel.setVgap(10);

        Label labelNumParticles = new Label("# of particles: ");
        gridPaneOptionSel.getChildren().add(labelNumParticles);
        gridPaneOptionSel.setConstraints(labelNumParticles, 0, 0);

        TextField fieldNumParticles = new TextField("0");
        gridPaneOptionSel.getChildren().add(fieldNumParticles);
        gridPaneOptionSel.setConstraints(fieldNumParticles, 1, 0);

        Label labelCurrentParticle = new Label("Current Particle: ");
        labelCurrentParticle.setVisible(false);
        gridPaneOptionSel.getChildren().add(labelCurrentParticle);
        gridPaneOptionSel.setConstraints(labelCurrentParticle, 0, 1);

        ComboBox<String> particleComboBox = new ComboBox<>();
        particleComboBox.setVisible(false);
        gridPaneOptionSel.getChildren().add(particleComboBox);
        gridPaneOptionSel.setConstraints(particleComboBox, 1, 1);

        /**Initialize fields for particle initialization
         * /
         */

        String[] particleInfoArry = {"Mass (kg * 10^15): ", "Init Vel X (m/s): ", "Init Vel Y (m/s): ", "Init Pos X (m): ", "Init Pos Y (m): "};
        Label[] planetInfoLabels = new Label[particleInfoArry.length];
        TextField[] planetInfoFields = new TextField[particleInfoArry.length];

        for (int i = 0; i < particleInfoArry.length; i++)
        {
            planetInfoLabels[i] = new Label(particleInfoArry[i]);
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
                    particleInitFieldsPassArry[currentParticleIndex][temp] = Double.parseDouble(planetInfoFields[temp].getText());
                }
                catch (Exception e)
                {

                }
            });
        }

        Button buttonCreateSim = new Button("Create Sim");
        buttonCreateSim.setVisible(false);
        gridPaneOptionSel.getChildren().add(buttonCreateSim);
        gridPaneOptionSel.setConstraints(buttonCreateSim, 0, particleInfoArry.length + 2);
        buttonCreateSim.setOnAction(event ->
        {
            createSim();
        });

        fieldNumParticles.setOnKeyTyped(event ->
        {
            try
            {
                if (Integer.parseInt(fieldNumParticles.getText()) >= 1)
                {

                    numParticles = Integer.parseInt(fieldNumParticles.getText());
                    particleInitFieldsPassArry = new double[numParticles][particleInfoArry.length];

                    ArrayList<String> tempStringCombo = new ArrayList<>();
                    for (int i = 0; i < particleInfoArry.length; i++)
                    {
                        planetInfoFields[i].setVisible(true);
                        planetInfoLabels[i].setVisible(true);
                    }

                    for (int i = 0; i < numParticles; i++)
                    {
                        tempStringCombo.add(i + "");

                        for (int ii = 0; ii < particleInfoArry.length; ii++)
                            particleInitFieldsPassArry [i][ii] = 0;
                    }
                    buttonCreateSim.setVisible(true);
                    labelCurrentParticle.setVisible(true);
                    particleComboBox.setVisible(true);
                    ObservableList tempObList = FXCollections.observableList(tempStringCombo);
                    particleComboBox.setItems(tempObList);
                }
            }
            catch (Exception e)
            {
                for (int i = 0; i < particleInfoArry.length; i++)
                {
                    planetInfoFields[i].setVisible(false);
                    planetInfoLabels[i].setVisible(false);
                }
                buttonCreateSim.setVisible(false);
                labelCurrentParticle.setVisible(false);
                particleComboBox.setVisible(false);
            }
        });

        particleComboBox.setOnAction(event ->
                {
                    currentParticleIndex = Integer.parseInt(particleComboBox.getValue());
                    if(particleComboBox.isVisible())
                    {
                        for (int i = 0; i < particleInfoArry.length; i++)
                        {
                            planetInfoFields[i].setText(particleInitFieldsPassArry[currentParticleIndex][i] + "");
                        }
                    }
                }
        );

        menuScene = new Scene(gridPaneOptionSel);
        this.setScene(menuScene);
    }

    private void createSim()
    {
        double[][] particlePassTemp = new double[particleInitFieldsPassArry.length][particleInitFieldsPassArry[0].length];

        for (int i = 0; i < particlePassTemp.length; i++)
        {
            for (int ii = 0; ii < particlePassTemp[0].length; ii++)
            {
                particlePassTemp[i][ii] = particleInitFieldsPassArry[i][ii];
            }
        }

        for (int i = 0; i < particlePassTemp.length; i++)
        {
            particlePassTemp[i][0] *= 1000000000000000.0;
            System.out.println( particlePassTemp[i][0]);
        }
        SimulationStage simulationStage = new SimulationStage(particlePassTemp);
    }
}
