package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimulationStage extends Stage
{
    private Timeline timeline;
    private Scene simScene;
    private Pane simPane;
    private Particle[] allParticles;

    public SimulationStage(double [][] inputPlanetFields)
    {
        super();
        simPane = new Pane();
        simScene = new Scene(simPane, Paint.valueOf("#000000"));
        this.setScene(simScene);
        this.setTitle("Simulation");
        this.show();
        allParticles = new Particle[inputPlanetFields.length];

        for (int i = 0; i < allParticles.length; i++)
            allParticles[i] = new Particle(inputPlanetFields[i][0], inputPlanetFields[i][1], inputPlanetFields[i][2], inputPlanetFields[i][3], inputPlanetFields[i][4]);

        printPlanetInfo();

        this.setOnCloseRequest(event ->
        {
            timeline.stop();
            timeline = null;
        });

        run();
    }

    private void printPlanetInfo()
    {
        System.out.println("IN planetinfo");
        System.out.println(allParticles.length);
        for(int j = 0; j < allParticles.length; j++)
        {
            System.out.println(allParticles[j].getMass());
            System.out.println(allParticles[j].getVelocityX());
        }
    }

    private void run()
    {
        simPane.getChildren().addAll(allParticles);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1.0/60.0), event ->
        {
            for (Particle temp : allParticles)
            {
                temp.updatePosition(allParticles);
                temp.relocate(temp.getPositionX(), temp.getPositionY());
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
