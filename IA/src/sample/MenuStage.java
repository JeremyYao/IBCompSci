package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuStage extends Stage
{
    private Scene menuScene;
    private Button startButton;
    private short numParticles = 0;
    public MenuStage(double w, double h)
    {
        super();
        GridPane menuGridPane = new GridPane();
        menuGridPane.setAlignment(Pos.CENTER);

        startButton = new Button("Start");
        startButton.setFont(Font.font("COMIC SANS MS", 44));
        startButton.setOnAction(event ->
        {
            initOptionSelect();
        });
        menuGridPane.add(startButton,0,0);
        this.setHeight(400);
        this.setWidth(400);
        this.setTitle("Start");
        this.setScene(new Scene(menuGridPane));
        this.show();
    }

    public void initOptionSelect()
    {
        GridPane gridPaneOptionSel = new GridPane();
        gridPaneOptionSel.setPadding(new Insets(3));
        gridPaneOptionSel.setHgap(10);
        gridPaneOptionSel.setVgap(10);

        Label labelNumParticles = new Label("# of particles: ");
        gridPaneOptionSel.getChildren().add(labelNumParticles);
        gridPaneOptionSel.setConstraints(labelNumParticles, 0, 0);

        TextField fieldNumParticles = new TextField("0");
        gridPaneOptionSel.getChildren().add(fieldNumParticles);
        gridPaneOptionSel.setConstraints(fieldNumParticles, 1, 0);

        /**Initialize fields for particle initialization
         * /
         */

        String[] particleInfoArry = {"Mass (kg): ", "Init Vel X (m/s): ", "Init Vel Y (m/s): ", "Init Pos X (m): ", "Init Pos Y (m): "};
        Label[] planetInfoLabels = new Label[particleInfoArry.length];
        TextField[] planetInfoFields = new TextField[particleInfoArry.length];

        for (int i = 0; i < particleInfoArry.length; i++)
        {
            planetInfoLabels[i] = new Label(particleInfoArry[i]);
            planetInfoLabels[i].setVisible(true);
            gridPaneOptionSel.getChildren().add(planetInfoLabels[i]);
            gridPaneOptionSel.setConstraints(planetInfoLabels[i], 0, 1 + i);

            planetInfoFields[i] = new TextField("0");
            planetInfoFields[i].setVisible(true);
            gridPaneOptionSel.getChildren().add(planetInfoFields[i]);
            gridPaneOptionSel.setConstraints(planetInfoFields[i], 1, 1 + i);
        }

        fieldNumParticles.setOnKeyTyped(event ->
        {
            if (Integer.parseInt(fieldNumParticles.getText()) >= 1)
            {

            }
            else
            {

            }

        });

        menuScene = new Scene(gridPaneOptionSel);
        this.setScene(menuScene);
    }
}
