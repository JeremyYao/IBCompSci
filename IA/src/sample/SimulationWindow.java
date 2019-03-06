package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SimulationWindow extends Stage
{
    private Timeline timeline;
    private Scene simScene;
    private Pane simPane;
    private Particle[] allParticles;

    public SimulationWindow(double [][] inputPlanetFields)
    {
        simPane = new Pane();
        simScene = new Scene(simPane, 700,700, Paint.valueOf("#000000"));

        setScene(simScene);
        setTitle("Simulation");
        show();
        setAlwaysOnTop(true);
        allParticles = new Particle[inputPlanetFields.length];

        for (int i = 0; i < allParticles.length; i++)
            allParticles[i] = new Particle(inputPlanetFields[i][0], inputPlanetFields[i][1], inputPlanetFields[i][2], inputPlanetFields[i][3], inputPlanetFields[i][4]);

        setOnCloseRequest(event ->
        {
            timeline.stop();
            timeline = null;
        });

        run();
    }

    private void run()
    {
        simPane.getChildren().addAll(allParticles);

        timeline = new Timeline(new KeyFrame(Duration.millis(1000.0/60.0), event ->
        {
            for (Particle temp : allParticles)
                temp.updatePosition(allParticles);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
