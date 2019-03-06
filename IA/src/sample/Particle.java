package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Particle extends Circle
{
    private double mass;
    private double velocityX;
    private double velocityY;
    private double accelX;
    private double accelY;
    private double positionX;
    private double positionY;
    public static final double UNIVERSAL_GRAVITATIONAL_CONST = 6.67408 * Math.pow(10,-11);
    public final double PARTICLE_ID;

    public Particle(double mass, double velocityX, double velocityY, double posX, double posY)
    {
        super(posX, posY, Math.pow(mass/Math.pow(10, 15), 1.0/4.69) * 4);
        setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = posX;
        this.positionY = posY;
        this.PARTICLE_ID = Math.random();
    }

    public static String generateRandomHexColor()
    {
        String finalColorHex = "#";
        for (int i = 0; i < 3; i++)
        {
            int randInt = (int)(Math.random() * 256);

            if (randInt > 15)
                finalColorHex += Integer.toHexString(randInt);
            else
                finalColorHex += "0" + Integer.toHexString(randInt);
        }
        return finalColorHex;
    }

    private void updateAcceleration(Particle[] allParticles)
    {
        accelX = 0;
        accelY = 0;

        for (Particle temp : allParticles)
        {
            if (temp.PARTICLE_ID != this.PARTICLE_ID)
            {
                double magDist = Math.sqrt(Math.pow((this.positionX - temp.getPositionX()), 2) +  Math.pow((this.positionY - temp.getPositionY()), 2));
                double angleBtwn = Math.atan2(temp.getPositionY() - this.positionY, temp.getPositionX() - this.positionX);
                accelY += Math.sin(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONST * temp.getMass() / Math.pow(magDist, 2);
                accelX += Math.cos(angleBtwn) * UNIVERSAL_GRAVITATIONAL_CONST * temp.getMass() / Math.pow(magDist, 2);
            }
        }
    }

    private void updateVelocity()
    {
        velocityX += accelX/60.0;
        velocityY += accelY/60.0;
    }

    public void updatePosition(Particle[] allParticles)
    {
        updateAcceleration(allParticles);
        updateVelocity();

        positionX += velocityX/60.0;
        positionY += velocityY/60.0;

        relocate(positionX, 695 - positionY);
    }

    public double getMass()
    {
        return mass;
    }

    public double getPositionX()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY;
    }
}
