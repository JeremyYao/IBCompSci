package sample;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationStage extends Stage
{
    private Scene simScene;
    public SimulationStage(double [][] inputPlanetFields)
    {
        super();
        this.setTitle("Simulation");
        this.show();
    }
}
