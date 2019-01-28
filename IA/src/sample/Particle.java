package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Collection;

public class Particle extends Circle
{
    private double mass = 1;
    private double velocityX = 0;
    private double velocityY = 0;
    private double accelX = 0;
    private double accelY = 0;
    private double positionX = 0;
    private double positionY = 0;
    private static int fps = 60;
    public static final double UNIVERSAL_GRAVITATIONAL_CONST = 6.67408 * Math.pow(10,-11);
    public final double PARTICLE_ID;

    public Particle(double mass, double velocityX, double velocityY, double posX, double posY)
    {
        super(posX, posY, 5);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = posX;
        this.positionY = posY;
        this.PARTICLE_ID = Math.random();
    }

    public Particle(double mass, double posX, double posY)
    {
        super(posX, posY, 5);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        this.positionX = posX;
        this.positionY = posY;
        PARTICLE_ID = Math.random();
    }

    public Particle(double mass)
    {
        super(5);
        super.setFill(Paint.valueOf(generateRandomHexColor()));
        this.mass = mass;
        PARTICLE_ID = Math.random();
    }

    public Particle()
    {
        PARTICLE_ID = Math.random();
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

    private void updateAcceleration(Collection<Particle> allParticles)
    {
        accelX = 0;
        accelY = 0;

        for (Particle temp : allParticles)
        {
            if (temp.PARTICLE_ID != PARTICLE_ID)
            {
                double magDist = Math.sqrt(Math.pow(positionX - temp.getPositionX(), 2) + Math.pow(positionY - temp.getPositionY(), 2));
                double forceGrav = UNIVERSAL_GRAVITATIONAL_CONST * mass * temp.getMass() / Math.pow(magDist, 2);
                double angleBtwn = Math.atan2(temp.getPositionY() - positionY, temp.getPositionX() - positionX);
                accelY += Math.sin(angleBtwn) * forceGrav / mass;
                accelX += Math.cos(angleBtwn) * forceGrav / mass;
            }
        }
    }

    private void updateVelocities()
    {
        velocityX += accelX/fps;
        velocityY += accelY/fps;
    }

    public void updatePosition(Collection<Particle> allParticles)
    {
        updateAcceleration(allParticles);
        updateVelocities();

        positionX += velocityX * 1/fps;
        positionY += velocityY * 1/fps;

        super.setCenterX(positionX);
        super.setCenterY(positionY);
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public void setVelocityX(double velocityX)
    {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY)
    {
        this.velocityY = velocityY;
    }

    public void setAccelX(double accelX)
    {
        this.accelX = accelX;
    }

    public void setAccelY(double accelY)
    {
        this.accelY = accelY;
    }

    public void setPositionX(double positionX)
    {
        this.positionX = positionX;
        super.setCenterX(positionX);
    }

    public void setPositionY(double positionY)
    {
        this.positionY = positionY;
        super.setCenterY(positionY);
    }

    public void setFps(int fps)
    {
        this.fps = fps;
    }

    public double getMass()
    {
        return mass;
    }

    public double getVelocityX()
    {
        return velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public double getAccelX()
    {
        return accelX;
    }

    public double getAccelY()
    {
        return accelY;
    }

    public double getPositionX()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY;
    }

    public int getFps()
    {
        return fps;
    }
}
