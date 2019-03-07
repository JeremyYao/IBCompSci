package source;

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

    /**
     * Constructs a new SimulationWindow object and displays a window containing a running simulation
     *
     * @param inputPlanetFields a two dimensional double array of dimension x * 5, where x is an integer greater than 0
     *                          denoting how many Particles to simulate.
     *                          Used to initialize all instances of Particle within Particle array allParticles
     */
    public SimulationWindow(double [][] inputPlanetFields)
    {
        simPane = new Pane();
        simScene = new Scene(simPane, 700,700, Paint.valueOf("#000000"));

        setScene(simScene);
        setTitle("Simulation");
        show();
        setAlwaysOnTop(true);
        allParticles = new Particle[inputPlanetFields.length];

        //Initialize all Particles within allParticles needed to be simulated.
        for (int i = 0; i < allParticles.length; i++)
            allParticles[i] = new Particle(inputPlanetFields[i][0], inputPlanetFields[i][1], inputPlanetFields[i][2], inputPlanetFields[i][3], inputPlanetFields[i][4]);

        //Stops the simulation from running in the background when user
        //closes the window.
        setOnCloseRequest(event ->
        {
            timeline.stop();
            timeline = null;
        });

        run();
    }

    /**
     * Continuously updates the positions of each Particle at a desired frequency (60 Hz) during the duration of
     * the simulation being open on the user's system.
     */
    private void run()
    {
        simPane.getChildren().addAll(allParticles);

        timeline = new Timeline(new KeyFrame(Duration.millis(1000.0/Particle.getUpdateFreq()), event ->
        {
            for (Particle temp : allParticles)
                temp.updatePosition(allParticles);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
